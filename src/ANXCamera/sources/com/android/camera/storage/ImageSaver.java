package com.android.camera.storage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureResult;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraScreenNail;
import com.android.camera.CameraSettings;
import com.android.camera.Exif;
import com.android.camera.JpegEncodingQualityMappings;
import com.android.camera.Thumbnail;
import com.android.camera.Util;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.draw_mode.DrawJPEGAttribute;
import com.android.camera.effect.renders.SnapshotEffectRender;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol.ActionProcessing;
import com.android.camera.ui.ScreenHint;
import com.android.camera.watermark.WaterMarkData;
import com.android.gallery3d.exif.ExifInterface;
import com.xiaomi.camera.base.Constants.ShotType;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.ParallelCallback;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.ParallelTaskDataParameter;
import com.xiaomi.camera.core.PictureInfo;
import com.xiaomi.camera.liveshot.CircularMediaRecorder.VideoClipSavingCallback;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageSaver implements ParallelCallback, SaverCallback, VideoClipSavingCallback {
    private static final Executor CAMERA_SAVER_EXECUTOR;
    private static final int HOST_STATE_DESTROY = 2;
    private static final int HOST_STATE_PAUSE = 1;
    private static final int HOST_STATE_RESUME = 0;
    private static final Executor PREVIEW_SAVER_EXECUTOR;
    private static final int QUEUE_BUSY_SIZE = 40;
    private static final String TAG = "ImageSaver";
    private static final BlockingQueue<Runnable> mPreviewRequestQueue = new LinkedBlockingQueue(32);
    private static final BlockingQueue<Runnable> mSaveRequestQueue = new LinkedBlockingQueue(128);
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable runnable) {
            StringBuilder sb = new StringBuilder();
            sb.append("camera-saver-");
            sb.append(this.mCount.getAndIncrement());
            Thread thread = new Thread(runnable, sb.toString());
            thread.setPriority(10);
            return thread;
        }
    };
    private Context mContext;
    private SnapshotEffectRender mEffectProcessor;
    private final Object mEffectProcessorLock = new Object();
    private Handler mHandler;
    private int mHostState;
    private volatile boolean mIsBusy;
    private boolean mIsCaptureIntent;
    private Uri mLastImageUri;
    private final Queue<ParallelTaskData> mLiveShotPendingTaskQueue = new ConcurrentLinkedQueue();
    private MemoryManager mMemoryManager;
    private Thumbnail mPendingThumbnail;
    /* access modifiers changed from: private */
    public WeakReference<ImageSaverCallback> mSaverCallback;
    private ParallelTaskData mStoredTaskData;
    private ThumbnailUpdater mUpdateThumbnail = new ThumbnailUpdater();
    private final Object mUpdateThumbnailLock = new Object();

    public interface ImageSaverCallback {
        CameraScreenNail getCameraScreenNail();

        int getDisplayRotation();

        ScreenHint getScreenHint();

        com.android.camera.ThumbnailUpdater getThumbnailUpdater();

        boolean isActivityPaused();

        void onNewUriArrived(Uri uri, String str);
    }

    private class ThumbnailUpdater implements Runnable {
        private boolean mNeedAnimation = true;

        public ThumbnailUpdater() {
        }

        public void run() {
            ImageSaverCallback imageSaverCallback = (ImageSaverCallback) ImageSaver.this.mSaverCallback.get();
            if (imageSaverCallback != null) {
                imageSaverCallback.getScreenHint().updateHint();
            }
            ImageSaver.this.updateThumbnail(this.mNeedAnimation);
        }

        public void setNeedAnimation(boolean z) {
            this.mNeedAnimation = z;
        }
    }

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 4, 10, TimeUnit.SECONDS, mSaveRequestQueue, sThreadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        CAMERA_SAVER_EXECUTOR = threadPoolExecutor;
        ThreadPoolExecutor threadPoolExecutor2 = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, mPreviewRequestQueue, sThreadFactory);
        threadPoolExecutor2.allowCoreThreadTimeOut(true);
        PREVIEW_SAVER_EXECUTOR = threadPoolExecutor2;
    }

    public ImageSaver(ImageSaverCallback imageSaverCallback, Handler handler, boolean z) {
        this.mSaverCallback = new WeakReference<>(imageSaverCallback);
        this.mHandler = handler;
        this.mIsCaptureIntent = z;
        this.mMemoryManager = new MemoryManager();
        this.mMemoryManager.initMemory();
        this.mContext = CameraAppImpl.getAndroidContext();
    }

    private void addSaveRequest(SaveRequest saveRequest) {
        addSaveRequest(saveRequest, false);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:14|15) */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r2.mIsBusy = true;
        com.android.camera.log.Log.w(TAG, "stop snapshot due to thread pool is full");
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x0030 */
    private void addSaveRequest(SaveRequest saveRequest, boolean z) {
        synchronized (this) {
            if (2 == this.mHostState) {
                Log.v(TAG, "addSaveRequest: host is being destroyed.");
            }
            if (isSaveQueueFull()) {
                this.mIsBusy = true;
            }
            addUsedMemory(saveRequest.getSize());
            saveRequest.setContextAndCallback(this.mContext, this);
            if (z) {
                PREVIEW_SAVER_EXECUTOR.execute(saveRequest);
            } else {
                CAMERA_SAVER_EXECUTOR.execute(saveRequest);
            }
        }
    }

    private DrawJPEGAttribute getDrawJPEGAttribute(byte[] bArr, int i, int i2, int i3, boolean z, int i4, int i5, Location location, String str, int i6, int i7, float f2, String str2, boolean z2, boolean z3, String str3, List<WaterMarkData> list, boolean z4, PictureInfo pictureInfo, int i8, int i9) {
        int i10 = i4;
        int i11 = i5;
        Location location2 = location;
        DrawJPEGAttribute drawJPEGAttribute = new DrawJPEGAttribute(bArr, z, i10 > i11 ? Math.max(i, i2) : Math.min(i, i2), i11 > i10 ? Math.max(i, i2) : Math.min(i, i2), i4, i5, i3, EffectController.getInstance().copyEffectRectAttribute(), location2 == null ? null : new Location(location2), str, System.currentTimeMillis(), i6, i7, f2, pictureInfo.isFrontMirror(), str2, z2, pictureInfo, list, CameraSettings.isDualCameraWaterMarkOpen() || CameraSettings.isFrontCameraWaterMarkOpen(), z3, CameraSettings.isTimeWaterMarkOpen() ? str3 : null, z4, i8, i9);
        return drawJPEGAttribute;
    }

    private void initEffectProcessorLocked() {
        if (this.mEffectProcessor == null) {
            this.mEffectProcessor = new SnapshotEffectRender((ImageSaverCallback) this.mSaverCallback.get(), this.mIsCaptureIntent);
            this.mEffectProcessor.setImageSaver(this);
            this.mEffectProcessor.setQuality(JpegEncodingQualityMappings.getQualityNumber(CameraSettings.getJpegQuality(false)));
        }
    }

    private void insertImageSaveRequest(ParallelTaskData parallelTaskData) {
        addSaveRequest(new ImageSaveRequest(parallelTaskData, this));
    }

    private void insertParallelSaveRequest(ParallelTaskData parallelTaskData) {
        addSaveRequest(new ParallelSaveRequest(parallelTaskData, this));
    }

    private void insertParallelTaskData(ParallelTaskData parallelTaskData, @Nullable CaptureResult captureResult, @Nullable CameraCharacteristics cameraCharacteristics) {
        switch (parallelTaskData.getParallelType()) {
            case ShotType.INTENT_PARALLEL_DUAL_SHOT /*-7*/:
            case ShotType.INTENT_PARALLEL_SINGLE_PORTRAIT /*-6*/:
            case ShotType.INTENT_PARALLEL_SINGLE_SHOT /*-5*/:
                processParallelIntentResult(parallelTaskData);
                return;
            case -4:
                insertImageSaveRequest(parallelTaskData);
                return;
            case -3:
            case -2:
                processIntentResult(parallelTaskData);
                return;
            case -1:
                insertPreviewSaveRequest(parallelTaskData);
                return;
            case 0:
            case 2:
            case 10:
                break;
            case 1:
                insertRawImageSaveRequest(parallelTaskData, captureResult, cameraCharacteristics);
                break;
            case 5:
            case 6:
            case 7:
            case 8:
                insertParallelSaveRequest(parallelTaskData);
                return;
            case 9:
                insertImageSaveRequest(parallelTaskData);
                return;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown shot type: ");
                sb.append(parallelTaskData.getParallelType());
                throw new RuntimeException(sb.toString());
        }
        if (!parallelTaskData.isShot2Gallery()) {
            insertImageSaveRequest(parallelTaskData);
        } else {
            insertParallelSaveRequest(parallelTaskData);
        }
    }

    private void insertPreviewSaveRequest(ParallelTaskData parallelTaskData) {
        addSaveRequest(new PreviewSaveRequest(parallelTaskData, this), true);
    }

    private boolean isLastImageForThumbnail() {
        return true;
    }

    private void processIntentResult(ParallelTaskData parallelTaskData) {
        ImageSaveRequest imageSaveRequest = new ImageSaveRequest(parallelTaskData, this);
        imageSaveRequest.setSaverCallback(this);
        imageSaveRequest.parserParallelTaskData();
        showCaptureResultOnCover(parallelTaskData, imageSaveRequest.width, imageSaveRequest.orientation);
    }

    private void processParallelIntentResult(ParallelTaskData parallelTaskData) {
        ParallelSaveRequest parallelSaveRequest = new ParallelSaveRequest(parallelTaskData, this);
        parallelSaveRequest.setSaverCallback(this);
        parallelSaveRequest.parserParallelTaskData();
        showCaptureResultOnCover(parallelTaskData, parallelSaveRequest.width, parallelSaveRequest.orientation);
    }

    private void releaseEffectProcessor() {
        if (!ModuleManager.isCapture() && !ModuleManager.isPortraitModule()) {
            synchronized (this.mEffectProcessorLock) {
                if (this.mEffectProcessor != null) {
                    this.mEffectProcessor.releaseIfNeeded();
                    this.mEffectProcessor = null;
                }
            }
        }
    }

    private void releaseResourcesIfQueueEmpty() {
        releaseEffectProcessor();
        if (this.mHostState == 2 && mSaveRequestQueue.size() <= 0 && mPreviewRequestQueue.size() <= 0 && this.mLiveShotPendingTaskQueue.size() <= 0) {
            synchronized (this.mEffectProcessorLock) {
                if (this.mEffectProcessor != null) {
                    this.mEffectProcessor.releaseIfNeeded();
                    this.mEffectProcessor = null;
                }
            }
            this.mStoredTaskData = null;
        }
    }

    private void showCaptureResultOnCover(ParallelTaskData parallelTaskData, int i, int i2) {
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        this.mStoredTaskData = parallelTaskData;
        ImageSaverCallback imageSaverCallback = (ImageSaverCallback) this.mSaverCallback.get();
        Bitmap createBitmap = Thumbnail.createBitmap(parallelTaskData.getJpegImageData(), i2 + (360 - dataParameter.getShootOrientation()) + (imageSaverCallback == null ? 0 : imageSaverCallback.getDisplayRotation()), false, Integer.highestOneBit((int) Math.round(((double) i) / ((double) dataParameter.getPreviewSize().getWidth()))));
        if (createBitmap != null && imageSaverCallback != null) {
            imageSaverCallback.getCameraScreenNail().renderBitmapToCanvas(createBitmap);
        }
    }

    /* access modifiers changed from: private */
    public void updateThumbnail(boolean z) {
        Thumbnail thumbnail;
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("updateThumbnail needAnimation:");
        sb.append(z);
        Log.d(str, sb.toString());
        synchronized (this.mUpdateThumbnailLock) {
            this.mHandler.removeCallbacks(this.mUpdateThumbnail);
            thumbnail = this.mPendingThumbnail;
            this.mPendingThumbnail = null;
        }
        if (thumbnail != null) {
            ImageSaverCallback imageSaverCallback = (ImageSaverCallback) this.mSaverCallback.get();
            if (imageSaverCallback != null) {
                imageSaverCallback.getThumbnailUpdater().setThumbnail(thumbnail, true, z);
                if (imageSaverCallback.isActivityPaused()) {
                    imageSaverCallback.getThumbnailUpdater().saveThumbnailToFile();
                }
            }
        }
    }

    public void addImage(byte[] bArr, boolean z, String str, String str2, long j, Uri uri, Location location, int i, int i2, ExifInterface exifInterface, int i3, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String str3, PictureInfo pictureInfo, int i4) {
        Uri uri2 = uri;
        String str4 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("isParallelProcess: parallel=");
        sb.append(z6);
        sb.append(" uri=");
        sb.append(uri2);
        sb.append(" algo=");
        sb.append(str3);
        Log.d(str4, sb.toString());
        if (str2 != null && uri2 == null) {
            uri2 = this.mLastImageUri;
        }
        Uri uri3 = uri2;
        PerformanceTracker.trackImageSaver(bArr, 0);
        ImageSaveRequest imageSaveRequest = new ImageSaveRequest(bArr, z, str, str2, j, uri3, location, i, i2, exifInterface, i3, z2, z3, z4, z5, z6, str3, pictureInfo, i4);
        addSaveRequest(imageSaveRequest);
    }

    public synchronized void addUsedMemory(int i) {
        this.mMemoryManager.addUsedMemory(i);
    }

    public void addVideo(String str, ContentValues contentValues, boolean z) {
        synchronized (this) {
            if (2 == this.mHostState) {
                Log.v(TAG, "addVideo: host is being destroyed.");
            }
            addSaveRequest(new VideoSaveRequest(str, contentValues, z));
        }
    }

    public Uri addVideoSync(String str, ContentValues contentValues, boolean z) {
        Uri uri;
        synchronized (this) {
            if (2 == this.mHostState) {
                Log.v(TAG, "addVideo: host is being destroyed.");
            }
            VideoSaveRequest videoSaveRequest = new VideoSaveRequest(str, contentValues, z);
            videoSaveRequest.setContextAndCallback(this.mContext, this);
            videoSaveRequest.save();
            uri = videoSaveRequest.mUri;
        }
        return uri;
    }

    public int getBurstDelay() {
        return this.mMemoryManager.getBurstDelay();
    }

    public byte[] getStoredJpegData() {
        return this.mStoredTaskData.getJpegImageData();
    }

    public float getSuitableBurstShotSpeed() {
        return 0.66f;
    }

    public void insertRawImageSaveRequest(ParallelTaskData parallelTaskData, CaptureResult captureResult, CameraCharacteristics cameraCharacteristics) {
        String str;
        byte[] rawImageData = parallelTaskData.getRawImageData();
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        if (parallelTaskData.isShot2Gallery()) {
            str = Util.getFileTitleFromPath(parallelTaskData.getSavePath());
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(Util.createJpegName(System.currentTimeMillis()));
            sb.append(parallelTaskData.getDataParameter().getSuffix());
            str = sb.toString();
        }
        String str2 = str;
        int width = dataParameter.getRawSize().getWidth();
        int height = dataParameter.getRawSize().getHeight();
        int intValue = ((Integer) captureResult.get(CaptureResult.JPEG_ORIENTATION)).intValue();
        String str3 = TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("insertRawImageSaveRequest title = ");
        sb2.append(str2);
        sb2.append(", orientation = ");
        sb2.append(intValue);
        Log.d(str3, sb2.toString());
        PerformanceTracker.trackImageSaver(rawImageData, 0);
        RawImageSaveRequest rawImageSaveRequest = new RawImageSaveRequest(rawImageData, captureResult, cameraCharacteristics, str2, width, height, intValue);
        addSaveRequest(rawImageSaveRequest);
    }

    public boolean isBusy() {
        return this.mIsBusy;
    }

    public boolean isNeedSlowDown() {
        return this.mMemoryManager.isNeedSlowDown();
    }

    public boolean isNeedStopCapture() {
        return this.mMemoryManager.isNeedStopCapture();
    }

    public boolean isPendingSave() {
        return this.mLiveShotPendingTaskQueue.size() > 0 || mSaveRequestQueue.size() > 0 || mPreviewRequestQueue.size() > 0;
    }

    public synchronized boolean isSaveQueueFull() {
        boolean isSaveQueueFull;
        isSaveQueueFull = this.mMemoryManager.isSaveQueueFull();
        this.mIsBusy |= isSaveQueueFull;
        return isSaveQueueFull;
    }

    public boolean needThumbnail(boolean z) {
        boolean z2;
        synchronized (this) {
            if (z) {
                try {
                    if (isLastImageForThumbnail() && !this.mIsCaptureIntent) {
                        z2 = true;
                    }
                } finally {
                }
            }
            z2 = false;
        }
        return z2;
    }

    public void notifyNewMediaData(Uri uri, String str, int i) {
        if (!this.mIsCaptureIntent) {
            synchronized (this) {
                if (i == 1) {
                    this.mContext.sendBroadcast(new Intent("android.hardware.action.NEW_VIDEO", uri));
                    ImageSaverCallback imageSaverCallback = (ImageSaverCallback) this.mSaverCallback.get();
                    if (imageSaverCallback != null) {
                        imageSaverCallback.onNewUriArrived(uri, str);
                    }
                } else if (i == 2) {
                    Util.broadcastNewPicture(this.mContext, uri);
                    this.mLastImageUri = uri;
                    ImageSaverCallback imageSaverCallback2 = (ImageSaverCallback) this.mSaverCallback.get();
                    if (imageSaverCallback2 != null) {
                        imageSaverCallback2.onNewUriArrived(uri, str);
                    }
                }
            }
        }
    }

    public void onHostDestroy() {
        synchronized (this) {
            this.mHostState = 2;
            releaseResourcesIfQueueEmpty();
        }
        synchronized (this.mUpdateThumbnailLock) {
            this.mHandler.removeCallbacksAndMessages(null);
            this.mPendingThumbnail = null;
        }
        Log.v(TAG, "onHostDestroy");
    }

    public void onHostPause() {
        synchronized (this) {
            this.mHostState = 1;
        }
        synchronized (this.mUpdateThumbnailLock) {
            this.mHandler.removeCallbacksAndMessages(null);
            this.mPendingThumbnail = null;
        }
        Log.v(TAG, "onHostPause");
    }

    public void onHostResume(boolean z) {
        synchronized (this) {
            this.mIsCaptureIntent = z;
            this.mHostState = 0;
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onHostResume: isCapture=");
            sb.append(this.mIsCaptureIntent);
            Log.v(str, sb.toString());
        }
    }

    public void onModuleDestroy() {
        releaseEffectProcessor();
    }

    public boolean onParallelProcessFinish(ParallelTaskData parallelTaskData, @Nullable CaptureResult captureResult, @Nullable CameraCharacteristics cameraCharacteristics) {
        int i;
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onParallelProcessFinish: path: ");
        sb.append(parallelTaskData.getSavePath());
        Log.i(str, sb.toString());
        String str2 = TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("onParallelProcessFinish: live: ");
        sb2.append(parallelTaskData.isLiveShotTask());
        Log.i(str2, sb2.toString());
        String str3 = "onParallelProcessFinish: insert: ";
        if (parallelTaskData.isLiveShotTask()) {
            byte[] microVideoData = parallelTaskData.getMicroVideoData();
            String str4 = ", task: ";
            if (microVideoData != null) {
                String str5 = TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str3);
                sb3.append(parallelTaskData.hashCode());
                Log.d(str5, sb3.toString());
                if (this.mLiveShotPendingTaskQueue.remove(parallelTaskData)) {
                    int length = microVideoData.length;
                    reduceUsedMemory(length);
                    String str6 = TAG;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("onParallelProcessFinish: memory[-]: ");
                    sb4.append(length);
                    sb4.append(str4);
                    sb4.append(parallelTaskData.hashCode());
                    Log.d(str6, sb4.toString());
                }
                if (parallelTaskData.getJpegImageData() != null) {
                    insertParallelTaskData(parallelTaskData, null, null);
                } else {
                    Log.e(TAG, "onParallelProcessFinish: error: jpeg data is null");
                    return false;
                }
            } else {
                String str7 = TAG;
                StringBuilder sb5 = new StringBuilder();
                sb5.append("onParallelProcessFinish: enqueue: ");
                sb5.append(parallelTaskData.hashCode());
                Log.d(str7, sb5.toString());
                this.mLiveShotPendingTaskQueue.offer(parallelTaskData);
                byte[] jpegImageData = parallelTaskData.getJpegImageData();
                if (jpegImageData != null) {
                    i = jpegImageData.length;
                    addUsedMemory(i);
                } else {
                    i = 0;
                }
                String str8 = TAG;
                StringBuilder sb6 = new StringBuilder();
                sb6.append("onParallelProcessFinish: memory[+]: ");
                sb6.append(i);
                sb6.append(str4);
                sb6.append(parallelTaskData.hashCode());
                Log.d(str8, sb6.toString());
            }
            String str9 = TAG;
            StringBuilder sb7 = new StringBuilder();
            sb7.append("onParallelProcessFinish: pending: ");
            sb7.append(this.mLiveShotPendingTaskQueue.size());
            Log.d(str9, sb7.toString());
            return false;
        }
        String str10 = TAG;
        StringBuilder sb8 = new StringBuilder();
        sb8.append(str3);
        sb8.append(parallelTaskData.hashCode());
        Log.d(str10, sb8.toString());
        insertParallelTaskData(parallelTaskData, captureResult, cameraCharacteristics);
        return false;
    }

    public void onSaveFinish(int i) {
        synchronized (this) {
            reduceUsedMemory(i);
            if (!isSaveQueueFull() && mSaveRequestQueue.size() < 40 && mPreviewRequestQueue.size() < 40) {
                this.mIsBusy = false;
            }
            releaseResourcesIfQueueEmpty();
        }
    }

    public void onVideoClipSavingCancelled(@Nullable Object obj) {
        Log.d(TAG, "onVideoClipSavingCancelled: video = 0, timestamp = -1");
        onVideoClipSavingCompleted(obj, new byte[0], -1);
    }

    public void onVideoClipSavingCompleted(@Nullable Object obj, @NonNull byte[] bArr, long j) {
        if (!ParallelTaskData.class.isInstance(obj)) {
            Log.d(TAG, "onVideoClipSavingCompleted: Oops, corresponding task is not found");
            return;
        }
        ParallelTaskData parallelTaskData = (ParallelTaskData) obj;
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onVideoClipSavingCompleted: video: ");
        sb.append(bArr.length);
        sb.append(", timestamp = ");
        sb.append(j);
        Log.d(str, sb.toString());
        parallelTaskData.fillVideoData(bArr, j);
        String str2 = "onVideoClipSavingCompleted: memory[-]: ";
        String str3 = ", task: ";
        if (parallelTaskData.isJpegDataReady()) {
            if (this.mLiveShotPendingTaskQueue.remove(parallelTaskData)) {
                int length = parallelTaskData.getJpegImageData().length;
                reduceUsedMemory(length);
                String str4 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str2);
                sb2.append(length);
                sb2.append(str3);
                sb2.append(parallelTaskData.hashCode());
                Log.d(str4, sb2.toString());
            }
            insertParallelTaskData(parallelTaskData, null, null);
        } else if (parallelTaskData.isPictureFilled()) {
            Log.e(TAG, "onVideoClipSavingCompleted: get error jpeg data, ignore this liveshot");
            if (this.mLiveShotPendingTaskQueue.remove(parallelTaskData)) {
                int length2 = parallelTaskData.getJpegImageData().length;
                reduceUsedMemory(length2);
                String str5 = TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str2);
                sb3.append(length2);
                sb3.append(str3);
                sb3.append(parallelTaskData.hashCode());
                Log.d(str5, sb3.toString());
            }
        } else {
            String str6 = TAG;
            StringBuilder sb4 = new StringBuilder();
            sb4.append("onVideoClipSavingCompleted: enqueue: ");
            sb4.append(parallelTaskData.hashCode());
            Log.d(str6, sb4.toString());
            this.mLiveShotPendingTaskQueue.offer(parallelTaskData);
            int length3 = bArr.length;
            addUsedMemory(length3);
            String str7 = TAG;
            StringBuilder sb5 = new StringBuilder();
            sb5.append("onVideoClipSavingCompleted: memory[+]: ");
            sb5.append(length3);
            sb5.append(str3);
            sb5.append(parallelTaskData.hashCode());
            Log.d(str7, sb5.toString());
        }
        String str8 = TAG;
        StringBuilder sb6 = new StringBuilder();
        sb6.append("onVideoClipSavingCompleted: pending: ");
        sb6.append(this.mLiveShotPendingTaskQueue.size());
        Log.d(str8, sb6.toString());
    }

    public void onVideoClipSavingException(@Nullable Object obj, @NonNull Throwable th) {
        Log.d(TAG, "onVideoClipSavingException: video = 0, timestamp = -1");
        onVideoClipSavingCompleted(obj, new byte[0], -1);
    }

    public void postHideThumbnailProgressing() {
        synchronized (this.mUpdateThumbnailLock) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    ActionProcessing actionProcessing = (ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
                    if (actionProcessing != null) {
                        actionProcessing.updateLoading(true);
                    }
                }
            });
        }
    }

    public void postUpdateThumbnail(Thumbnail thumbnail, boolean z) {
        synchronized (this.mUpdateThumbnailLock) {
            Log.d(TAG, "postUpdateThumbnail");
            this.mPendingThumbnail = thumbnail;
            this.mUpdateThumbnail.setNeedAnimation(z);
            this.mHandler.post(this.mUpdateThumbnail);
        }
    }

    public void processorJpegSync(boolean z, DrawJPEGAttribute... drawJPEGAttributeArr) {
        synchronized (this.mEffectProcessorLock) {
            initEffectProcessorLocked();
            if (this.mEffectProcessor != null) {
                for (DrawJPEGAttribute drawJPEGAttribute : drawJPEGAttributeArr) {
                    if (drawJPEGAttribute != null) {
                        this.mEffectProcessor.processorJpegSync(drawJPEGAttribute, z);
                    }
                }
            } else {
                Log.d(TAG, "processorJpegSync(): mEffectProcessor is null");
            }
        }
    }

    public synchronized void reduceUsedMemory(int i) {
        this.mMemoryManager.reduceUsedMemory(i);
    }

    public void releaseStoredJpegData() {
        this.mStoredTaskData.releaseImageData();
    }

    public void saveStoredData() {
        int i;
        int i2;
        ParallelTaskData parallelTaskData = this.mStoredTaskData;
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        String createJpegName = Util.createJpegName(System.currentTimeMillis());
        int width = dataParameter.getPictureSize().getWidth();
        int height = dataParameter.getPictureSize().getHeight();
        int orientation = Exif.getOrientation(this.mStoredTaskData.getJpegImageData());
        if ((dataParameter.getJpegRotation() + orientation) % 180 == 0) {
            i2 = width;
            i = height;
        } else {
            i = width;
            i2 = height;
        }
        addImage(this.mStoredTaskData.getJpegImageData(), parallelTaskData.isNeedThumbnail(), createJpegName, null, System.currentTimeMillis(), null, dataParameter.getLocation(), i2, i, null, orientation, false, false, true, false, false, dataParameter.getAlgorithmName(), dataParameter.getPictureInfo(), -1);
    }

    public void updateImage(String str, String str2) {
        ImageSaveRequest imageSaveRequest = new ImageSaveRequest();
        imageSaveRequest.title = str;
        imageSaveRequest.oldTitle = str2;
        addSaveRequest(imageSaveRequest);
    }

    public void updatePreviewThumbnailUri(int i, Uri uri) {
        synchronized (this.mUpdateThumbnailLock) {
            ImageSaverCallback imageSaverCallback = (ImageSaverCallback) this.mSaverCallback.get();
            Thumbnail thumbnail = null;
            if (imageSaverCallback != null) {
                thumbnail = imageSaverCallback.getThumbnailUpdater().getThumbnail();
            }
            if (thumbnail != null) {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("previewThumbnailHash:");
                sb.append(i);
                sb.append(" current thumbnail hash:");
                sb.append(thumbnail.hashCode());
                Log.d(str, sb.toString());
                if (i <= 0 || thumbnail.hashCode() == i) {
                    thumbnail.setUri(uri);
                }
            }
        }
    }
}
