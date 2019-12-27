package com.android.camera.module;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.utils.SurfaceUtils;
import android.location.Location;
import android.media.AudioManager;
import android.media.CamcorderProfile;
import android.media.CameraProfile;
import android.media.MediaCodec;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.MiuiSettings;
import android.support.annotation.MainThread;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.media.MediaPlayer2;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Range;
import android.util.Size;
import android.util.TypedValue;
import android.view.Surface;
import android.view.View;
import android.widget.Toast;
import com.android.camera.AutoLockManager;
import com.android.camera.Camera;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.Exif;
import com.android.camera.FileCompat;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.LocationManager;
import com.android.camera.R;
import com.android.camera.ThermalHelper;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.constant.AutoFocus;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigSlowMotion;
import com.android.camera.data.provider.DataProvider;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.fragment.top.FragmentTopAlert;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.FocusManager2;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStat;
import com.android.camera.statistic.CameraStatUtil;
import com.android.camera.statistic.ScenarioTrackUtil;
import com.android.camera.storage.ImageSaver;
import com.android.camera.storage.Storage;
import com.android.camera.ui.ObjectView;
import com.android.camera.ui.PopupManager;
import com.android.camera.ui.RotateTextToast;
import com.android.camera.ui.zoom.ZoomingAction;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.autozoom.AutoZoomCaptureResult;
import com.android.gallery3d.exif.ExifInterface;
import com.mi.config.b;
import com.miui.extravideo.interpolation.VideoInterpolator;
import com.ss.android.ugc.effectmanager.link.model.configuration.LinkSelectorConfiguration;
import com.xiaomi.camera.core.PictureInfo;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import miui.os.Build;
import miui.reflect.Method;

public class VideoModule extends VideoBase implements Camera2Proxy.VideoRecordStateCallback, ModeProtocol.AutoZoomModuleProtocol, ModeProtocol.TopConfigProtocol, MediaRecorder.OnErrorListener, MediaRecorder.OnInfoListener, ObjectView.ObjectViewListener {
    private static final HashMap<String, Integer> HEVC_VIDEO_ENCODER_BITRATE = new HashMap<>();
    private static final int MAX_DURATION_4K = 480000;
    private static final int RESET_VIDEO_AUTO_FOCUS_TIME = 3000;
    public static final Size SIZE_1080 = new Size(1920, MiuiSettings.ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_END_DEAULT);
    public static final Size SIZE_720 = new Size(1280, Util.LIMIT_SURFACE_WIDTH);
    private static final long START_OFFSET_MS = 450;
    private static final int VIDEO_HFR_FRAME_RATE_120 = 120;
    private static final int VIDEO_HFR_FRAME_RATE_240 = 240;
    public static final long VIDEO_MAX_SINGLE_FILE_SIZE = 3670016000L;
    public static final long VIDEO_MIN_SINGLE_FILE_SIZE = Math.min(8388608, Storage.LOW_STORAGE_THRESHOLD);
    private static final int VIDEO_NORMAL_FRAME_RATE = 30;
    private AtomicBoolean isAutoZoomTracking = new AtomicBoolean(false);
    private AtomicBoolean isShowOrHideUltraWideHint = new AtomicBoolean(false);
    private Disposable mAutoZoomDataDisposable;
    /* access modifiers changed from: private */
    public FlowableEmitter<CaptureResult> mAutoZoomEmitter;
    private Disposable mAutoZoomUiDisposable;
    /* access modifiers changed from: private */
    public ModeProtocol.AutoZoomViewProtocol mAutoZoomViewProtocol;
    private boolean mCaptureTimeLapse;
    private CountDownTimer mCountDownTimer;
    private volatile int mCurrentFileNumber;
    private Boolean mDumpOrig960 = null;
    private boolean mFovcEnabled;
    /* access modifiers changed from: private */
    public int mFrameRate;
    private int mHfrFPSLower;
    private int mHfrFPSUpper;
    private final Object mLock = new Object();
    /* access modifiers changed from: private */
    public MediaRecorder mMediaRecorder;
    /* access modifiers changed from: private */
    public boolean mMediaRecorderPostProcessing;
    private boolean mMediaRecorderWorking;
    private String mNextVideoFileName;
    private ContentValues mNextVideoValues;
    private long mPauseClickTime = 0;
    private volatile boolean mPendingStopRecorder;
    private CamcorderProfile mProfile;
    /* access modifiers changed from: private */
    public int mQuality = 5;
    private boolean mQuickCapture;
    private Surface mRecorderSurface;
    private String mRecordingTime;
    private boolean mRecordingTimeCountsDown;
    private String mSlowModeFps;
    /* access modifiers changed from: private */
    public boolean mSnapshotInProgress;
    /* access modifiers changed from: private */
    public String mSpeed = "normal";
    private boolean mSplitWhenReachMaxSize;
    /* access modifiers changed from: private */
    public CountDownLatch mStopRecorderDone;
    private int mTimeBetweenTimeLapseFrameCaptureMs = 0;
    /* access modifiers changed from: private */
    public ModeProtocol.TopAlert mTopAlert;
    private int mTrackLostCount;
    private long mVideoRecordTime = 0;
    private long mVideoRecordedDuration;

    private final class JpegPictureCallback extends Camera2Proxy.PictureCallbackWrapper {
        Location mLocation;

        public JpegPictureCallback(Location location) {
            this.mLocation = location;
        }

        private void storeImage(byte[] bArr, Location location) {
            long currentTimeMillis = System.currentTimeMillis();
            int orientation = Exif.getOrientation(bArr);
            ImageSaver imageSaver = VideoModule.this.mActivity.getImageSaver();
            boolean access$1800 = VideoModule.this.needImageThumbnail(12);
            String createJpegName = Util.createJpegName(currentTimeMillis);
            long currentTimeMillis2 = System.currentTimeMillis();
            CameraSize cameraSize = VideoModule.this.mPictureSize;
            imageSaver.addImage(bArr, access$1800, createJpegName, (String) null, currentTimeMillis2, (Uri) null, location, cameraSize.width, cameraSize.height, (ExifInterface) null, orientation, false, false, true, false, false, (String) null, (PictureInfo) null, -1);
        }

        public void onPictureTaken(byte[] bArr) {
            Log.v(VideoBase.TAG, "onPictureTaken");
            boolean unused = VideoModule.this.mSnapshotInProgress = false;
            if (!VideoModule.this.mPaused) {
                storeImage(bArr, this.mLocation);
            }
        }
    }

    static {
        HEVC_VIDEO_ENCODER_BITRATE.put("3840x2160:30", 38500000);
        HEVC_VIDEO_ENCODER_BITRATE.put("1920x1080:30", 15400000);
        HEVC_VIDEO_ENCODER_BITRATE.put("1280x720:30", 10780000);
        HEVC_VIDEO_ENCODER_BITRATE.put("720x480:30", 1379840);
    }

    public VideoModule() {
        super(VideoModule.class.getSimpleName());
    }

    /* access modifiers changed from: private */
    public void consumeAutoZoomData(AutoZoomCaptureResult autoZoomCaptureResult) {
        if (isAlive() && !this.mActivity.isActivityPaused() && this.isAutoZoomTracking.get()) {
            this.mAutoZoomViewProtocol.feedData(autoZoomCaptureResult);
        }
    }

    private void countDownForVideoBokeh() {
        if (this.mMediaRecorderRecording) {
            CountDownTimer countDownTimer = this.mCountDownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            AnonymousClass5 r1 = new CountDownTimer(30450, 1000) {
                public void onFinish() {
                    VideoModule.this.stopVideoRecording(true, false);
                }

                public void onTick(long j) {
                    String millisecondToTimeString = Util.millisecondToTimeString((j + 950) - VideoModule.START_OFFSET_MS, false);
                    if (VideoModule.this.mTopAlert != null) {
                        VideoModule.this.mTopAlert.updateRecordingTime(millisecondToTimeString);
                    }
                }
            };
            this.mCountDownTimer = r1;
            this.mCountDownTimer.start();
        }
    }

    private void forceToNormalMode() {
        DataProvider.ProviderEditor editor = DataRepository.dataItemConfig().editor();
        editor.putString(CameraSettings.KEY_VIDEO_SPEED, "normal");
        editor.apply();
        this.mSpeed = "normal";
    }

    private int getHSRValue() {
        String hSRValue = CameraSettings.getHSRValue(isUltraWideBackCamera());
        if (hSRValue == null || hSRValue.isEmpty() || hSRValue.equals("off")) {
            return 0;
        }
        return Integer.parseInt(hSRValue);
    }

    private int getHevcVideoEncoderBitRate(CamcorderProfile camcorderProfile) {
        String str = camcorderProfile.videoFrameWidth + "x" + camcorderProfile.videoFrameHeight + ":" + camcorderProfile.videoFrameRate;
        if (HEVC_VIDEO_ENCODER_BITRATE.containsKey(str)) {
            return HEVC_VIDEO_ENCODER_BITRATE.get(str).intValue();
        }
        Log.d(VideoBase.TAG, "no pre-defined bitrate for " + str);
        return camcorderProfile.videoBitRate;
    }

    private int getOperatingModeForPreview() {
        if (CameraSettings.isAutoZoomEnabled(this.mModuleIndex)) {
            return CameraCapabilities.SESSION_OPERATION_MODE_AUTO_ZOOM;
        }
        return 0;
    }

    private long getRecorderMaxFileSize(int i) {
        long leftSpace = Storage.getLeftSpace() - Storage.LOW_STORAGE_THRESHOLD;
        if (i > 0) {
            long j = (long) i;
            if (j < leftSpace) {
                leftSpace = j;
            }
        }
        long j2 = VIDEO_MAX_SINGLE_FILE_SIZE;
        if (leftSpace <= VIDEO_MAX_SINGLE_FILE_SIZE || !DataRepository.dataItemFeature().ub()) {
            j2 = VIDEO_MIN_SINGLE_FILE_SIZE;
            if (leftSpace >= j2) {
                j2 = leftSpace;
            }
        }
        long j3 = this.mIntentRequestSize;
        return (j3 <= 0 || j3 >= j2) ? j2 : j3;
    }

    private int getRecorderOrientationHint() {
        int sensorOrientation = this.mCameraCapabilities.getSensorOrientation();
        return this.mOrientation != -1 ? isFrontCamera() ? ((sensorOrientation - this.mOrientation) + MiuiSettings.ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT) % MiuiSettings.ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT : (sensorOrientation + this.mOrientation) % MiuiSettings.ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT : sensorOrientation;
    }

    private float getResourceFloat(int i, float f2) {
        TypedValue typedValue = new TypedValue();
        try {
            this.mActivity.getResources().getValue(i, typedValue, true);
            return typedValue.getFloat();
        } catch (Exception unused) {
            String str = VideoBase.TAG;
            Log.e(str, "Missing resource " + Integer.toHexString(i));
            return f2;
        }
    }

    private long getSpeedRecordVideoLength(long j, double d2) {
        if (d2 == 0.0d) {
            return 0;
        }
        return (long) (((((double) j) / d2) / ((double) getNormalVideoFrameRate())) * 1000.0d);
    }

    private void initAutoZoom() {
        if (DataRepository.dataItemFeature().Cb()) {
            if (CameraSettings.isAutoZoomEnabled(this.mModuleIndex)) {
                startAutoZoom();
            } else {
                stopAutoZoom();
            }
            this.mAutoZoomDataDisposable = Flowable.create(new FlowableOnSubscribe<CaptureResult>() {
                public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) throws Exception {
                    FlowableEmitter unused = VideoModule.this.mAutoZoomEmitter = flowableEmitter;
                }
            }, BackpressureStrategy.DROP).observeOn(GlobalConstant.sCameraSetupScheduler).map(new Function<CaptureResult, AutoZoomCaptureResult>() {
                public AutoZoomCaptureResult apply(CaptureResult captureResult) throws Exception {
                    return new AutoZoomCaptureResult(captureResult);
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<AutoZoomCaptureResult>() {
                public void accept(AutoZoomCaptureResult autoZoomCaptureResult) throws Exception {
                    VideoModule.this.consumeAutoZoomData(autoZoomCaptureResult);
                }
            });
        }
    }

    private void initRecorderSurface() {
        this.mRecorderSurface = MediaCodec.createPersistentInputSurface();
    }

    private boolean initializeObjectTrack(RectF rectF, boolean z) {
        mapTapCoordinate(rectF);
        stopObjectTracking(false);
        return this.mMainProtocol.initializeObjectTrack(rectF, z);
    }

    private boolean initializeRecorder(boolean z) {
        Log.d(VideoBase.TAG, "initializeRecorder>>");
        long currentTimeMillis = System.currentTimeMillis();
        boolean z2 = false;
        if (getActivity() == null) {
            Log.w(VideoBase.TAG, "initializeRecorder: null host");
            return false;
        }
        closeVideoFileDescriptor();
        cleanupEmptyFile();
        if (isCaptureIntent()) {
            parseIntent(this.mActivity.getIntent());
        }
        if (this.mVideoFileDescriptor == null) {
            this.mCurrentVideoValues = genContentValues(this.mOutputFormat, this.mCurrentFileNumber, this.mSlowModeFps, z);
            this.mCurrentVideoFilename = this.mCurrentVideoValues.getAsString("_data");
        }
        if (this.mStopRecorderDone != null) {
            long currentTimeMillis2 = System.currentTimeMillis();
            try {
                this.mStopRecorderDone.await(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            Log.d(VideoBase.TAG, "initializeRecorder: waitTime=" + (System.currentTimeMillis() - currentTimeMillis2));
        }
        long currentTimeMillis3 = System.currentTimeMillis();
        synchronized (this.mLock) {
            if (this.mMediaRecorder == null) {
                this.mMediaRecorder = new MediaRecorder();
            } else {
                this.mMediaRecorder.reset();
                if (BaseModule.DEBUG) {
                    Log.v(VideoBase.TAG, "initializeRecorder: t1=" + (System.currentTimeMillis() - currentTimeMillis3));
                }
            }
        }
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            setupRecorder(this.mMediaRecorder);
            if (this.mVideoFileDescriptor != null) {
                this.mMediaRecorder.setOutputFile(this.mVideoFileDescriptor.getFileDescriptor());
            } else {
                if (Storage.isUseDocumentMode()) {
                    if (z) {
                        parcelFileDescriptor = FileCompat.getParcelFileDescriptor(this.mCurrentVideoFilename, true);
                        this.mMediaRecorder.setOutputFile(parcelFileDescriptor.getFileDescriptor());
                    }
                }
                this.mMediaRecorder.setOutputFile(this.mCurrentVideoFilename);
            }
            this.mMediaRecorder.setInputSurface(this.mRecorderSurface);
            long currentTimeMillis4 = System.currentTimeMillis();
            this.mMediaRecorder.prepare();
            this.mMediaRecorder.setOnErrorListener(this);
            this.mMediaRecorder.setOnInfoListener(this);
            if (BaseModule.DEBUG) {
                Log.v(VideoBase.TAG, "initializeRecorder: t2=" + (System.currentTimeMillis() - currentTimeMillis4));
            }
            Util.closeSilently(parcelFileDescriptor);
            z2 = true;
        } catch (Exception e3) {
            Log.e(VideoBase.TAG, "prepare failed for " + this.mCurrentVideoFilename, e3);
            releaseMediaRecorder();
            Util.closeSilently((Closeable) null);
        } catch (Throwable th) {
            Util.closeSilently((Closeable) null);
            throw th;
        }
        if (BaseModule.DEBUG) {
            showSurfaceInfo(this.mRecorderSurface);
        }
        Log.d(VideoBase.TAG, "initializeRecorder<<time=" + (System.currentTimeMillis() - currentTimeMillis));
        return z2;
    }

    /* access modifiers changed from: private */
    public boolean isActivityResumed() {
        Camera activity = getActivity();
        return activity != null && !activity.isActivityPaused();
    }

    private boolean isDump960Orig() {
        if (this.mDumpOrig960 == null) {
            this.mDumpOrig960 = SystemProperties.getBoolean("camera.record.960origdump", false) ? Boolean.TRUE : Boolean.FALSE;
        }
        return this.mDumpOrig960.booleanValue();
    }

    private boolean isEisOn() {
        int i = this.mQuality;
        boolean z = i != 0 && (!CameraSettings.is4KHigherVideoQuality(i) || isSupport4KUHDEIS()) && !isFaceBeautyOn(this.mBeautyValues) && CameraSettings.isMovieSolidOn() && getHSRValue() != 60 && (isNormalMode() || isFastMode());
        if (z && !b.hi()) {
            z = isBackCamera();
        }
        if (b.mj() && CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
            if (isUltraWideBackCamera()) {
                z = true;
            } else if (isStandaloneMacroCamera()) {
                z = false;
            }
        }
        if (!b.mj() || !CameraSettings.isSuperEISEnabled(this.mModuleIndex)) {
            return z;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public boolean isFPS120() {
        return ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_120.equals(this.mSlowModeFps);
    }

    /* access modifiers changed from: private */
    public boolean isFPS240() {
        return ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_240.equals(this.mSlowModeFps);
    }

    /* access modifiers changed from: private */
    public boolean isFPS960() {
        return ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_960.equals(this.mSlowModeFps);
    }

    private boolean isFastMode() {
        return CameraSettings.VIDEO_SPEED_FAST.equals(this.mSpeed);
    }

    private boolean isNormalMode() {
        return "normal".equals(this.mSpeed);
    }

    private boolean isSplitWhenReachMaxSize() {
        return this.mSplitWhenReachMaxSize;
    }

    private static boolean isSupport4KUHDEIS() {
        return DataRepository.dataItemFeature().isSupport4KUHDEIS();
    }

    private boolean needDisableEISAndOIS() {
        if (CameraSettings.isVideoBokehOn() && isFrontCamera()) {
            Log.d(VideoBase.TAG, "videoStabilization: disabled EIS and OIS when VIDEO_BOKEH is opened");
            return true;
        } else if (!CameraSettings.isAutoZoomEnabled(this.mModuleIndex)) {
            return false;
        } else {
            Log.d(VideoBase.TAG, "videoStabilization: disabled EIS and OIS when AutoZoom is opened");
            return true;
        }
    }

    /* access modifiers changed from: private */
    public boolean needImageThumbnail(int i) {
        return i != 12;
    }

    private void notifyAutoZoomStartUiHint() {
        notifyAutoZoomStopUiHint();
        ModeProtocol.TopAlert topAlert = this.mTopAlert;
        if (topAlert == null || !topAlert.isExtraMenuShowing()) {
            this.mAutoZoomUiDisposable = Observable.timer(FragmentTopAlert.HINT_DELAY_TIME, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                public void accept(Long l) throws Exception {
                    if (VideoModule.this.mTopAlert != null) {
                        VideoModule.this.mTopAlert.alertAiDetectTipHint(0, R.string.autozoom_click_hint, -1);
                    }
                }
            });
        }
    }

    private void notifyAutoZoomStopUiHint() {
        Disposable disposable = this.mAutoZoomUiDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mAutoZoomUiDisposable.dispose();
            this.mAutoZoomUiDisposable = null;
        }
    }

    private void onMaxFileSizeReached() {
        String str = this.mCurrentVideoFilename;
        if (str != null) {
            saveVideo(str, this.mCurrentVideoValues, false, false);
            this.mCurrentVideoValues = null;
            this.mCurrentVideoFilename = null;
        }
    }

    /* access modifiers changed from: private */
    public void onMediaRecorderReleased() {
        Log.d(VideoBase.TAG, "onMediaRecorderReleased>>");
        long currentTimeMillis = System.currentTimeMillis();
        this.mAudioManager.abandonAudioFocus((AudioManager.OnAudioFocusChangeListener) null);
        restoreMusicSound();
        if (isCaptureIntent() && !this.mPaused) {
            if (this.mCurrentVideoUri == null) {
                String str = this.mCurrentVideoFilename;
                if (str != null) {
                    this.mCurrentVideoUri = saveVideo(str, this.mCurrentVideoValues, true, true);
                    String str2 = VideoBase.TAG;
                    Log.d(str2, "onMediaRecorderReleased: outputUri=" + this.mCurrentVideoUri);
                }
            }
            boolean z = this.mCurrentVideoUri != null;
            if (this.mQuickCapture) {
                doReturnToCaller(z);
            } else if (z) {
                showAlert();
            }
        }
        if (this.mCurrentVideoFilename != null) {
            if (!isCaptureIntent()) {
                saveVideo(this.mCurrentVideoFilename, this.mCurrentVideoValues, true, false);
            }
            this.mCurrentVideoFilename = null;
            this.mCurrentVideoValues = null;
        } else if (!this.mPaused && !this.mActivity.isActivityPaused()) {
            this.mActivity.getThumbnailUpdater().getLastThumbnail();
        }
        if (this.mMediaRecorderPostProcessing) {
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onPostSavingFinish();
            }
        }
        this.mActivity.sendBroadcast(new Intent(VideoBase.STOP_VIDEO_RECORDING_ACTION));
        this.mTelephonyManager.listen(this.mPhoneStateListener, 0);
        Log.v(VideoBase.TAG, "listen none");
        enableCameraControls(true);
        if (!AutoFocus.LEGACY_CONTINUOUS_VIDEO.equals(this.mVideoFocusMode)) {
            this.mFocusManager.resetFocusStateIfNeeded();
            if (!this.mPaused && !this.mActivity.isActivityPaused()) {
                this.mMainProtocol.clearFocusView(2);
                setVideoFocusMode(AutoFocus.LEGACY_CONTINUOUS_VIDEO, false);
                updatePreferenceInWorkThread(14);
            }
        }
        keepScreenOnAwhile();
        String str3 = VideoBase.TAG;
        Log.d(str3, "onMediaRecorderReleased<<time=" + (System.currentTimeMillis() - currentTimeMillis));
        ScenarioTrackUtil.trackStopVideoRecordEnd();
        doLaterReleaseIfNeed();
        if (this.mMediaRecorderPostProcessing) {
            this.mMediaRecorderPostProcessing = false;
        }
        this.mMediaRecorderWorking = false;
        this.mHandler.post(new Runnable() {
            public void run() {
                VideoModule.this.handlePendingScreenSlide();
            }
        });
    }

    private void onStartRecorderFail() {
        enableCameraControls(true);
        releaseMediaRecorder();
        this.mAudioManager.abandonAudioFocus((AudioManager.OnAudioFocusChangeListener) null);
        restoreMusicSound();
        ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onFailed();
    }

    private void onStartRecorderSucceed() {
        if (!isFPS960()) {
            enableCameraControls(true);
        }
        this.mActivity.sendBroadcast(new Intent(VideoBase.START_VIDEO_RECORDING_ACTION));
        this.mMediaRecorderWorking = true;
        this.mMediaRecorderRecording = true;
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            int i = this.mModuleIndex;
            if (i != 172 && ((!CameraSettings.isMacroModeEnabled(i) || DataRepository.dataItemFeature().mb()) && !CameraSettings.isAutoZoomEnabled(this.mModuleIndex) && !CameraSettings.isSuperEISEnabled(this.mModuleIndex) && isBackCamera())) {
                updateZoomRatioToggleButtonState(true);
                if (isUltraWideBackCamera()) {
                    if (!DataRepository.dataItemFeature().ad() || !CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                        setMinZoomRatio(0.6f);
                        setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio() * 0.6f);
                    } else {
                        setMinZoomRatio(HybridZoomingSystem.getMinimumStandaloneOpticalZoomRatio());
                        setMaxZoomRatio(HybridZoomingSystem.getMaximumStandaloneOpticalZoomRatio());
                    }
                } else if (isAuxCamera()) {
                    setMinZoomRatio(2.0f);
                    setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                } else {
                    setMinZoomRatio(1.0f);
                    setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                }
            }
        }
        this.mMediaRecorderRecordingPaused = false;
        this.mRecordingStartTime = SystemClock.uptimeMillis();
        this.mPauseClickTime = SystemClock.uptimeMillis();
        this.mRecordingTime = "";
        this.mTelephonyManager.listen(this.mPhoneStateListener, 32);
        Log.v(VideoBase.TAG, "listen call state");
        if (CameraSettings.isVideoBokehOn()) {
            countDownForVideoBokeh();
        } else {
            updateRecordingTime();
        }
        keepScreenOn();
        AutoLockManager.getInstance(this.mActivity).removeMessage();
        trackGeneralInfo(1, false);
        keepPowerSave();
    }

    private void pauseVideoRecording() {
        Log.d(VideoBase.TAG, "pauseVideoRecording");
        if (this.mMediaRecorderRecording && !this.mMediaRecorderRecordingPaused) {
            try {
                pauseMediaRecorder(this.mMediaRecorder);
            } catch (IllegalStateException unused) {
                Log.e(VideoBase.TAG, "failed to pause media recorder");
            }
            this.mVideoRecordedDuration = SystemClock.uptimeMillis() - this.mRecordingStartTime;
            this.mMediaRecorderRecordingPaused = true;
            this.mHandler.removeMessages(42);
            updateRecordingTime();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00e4, code lost:
        if (r5 == false) goto L_0x00e6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00e6, code lost:
        com.android.camera.log.Log.e(com.android.camera.module.VideoBase.TAG, "960fps processing failed. delete the files.");
        r2.delete();
        r3.delete();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00fa, code lost:
        if (0 != 0) goto L_0x00fd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00fd, code lost:
        if (r5 == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        return r4.getAbsolutePath();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        return null;
     */
    public String postProcessVideo(String str) {
        boolean z;
        if (str == null) {
            return null;
        }
        File file = new File(str);
        File file2 = new File(str + ".bak");
        File file3 = Storage.isUseDocumentMode() ? new File(Storage.generatePrimaryFilepath(file.getName())) : new File(Storage.generateFilepath(file.getName()));
        boolean z2 = false;
        try {
            boolean z3 = !Build.IS_INTERNATIONAL_BUILD && CameraSettings.isSupport960VideoEditor();
            if (CameraSettings.is960WatermarkOn(getModuleIndex())) {
                z = VideoInterpolator.doDecodeAndEncodeSyncWithWatermark(file.getAbsolutePath(), file2.getAbsolutePath(), DataRepository.dataItemFeature().Eb(), Util.load960fpsCameraWatermark(this.mActivity), new float[]{getResourceFloat(R.dimen.fps960_watermark_size_ratio, 0.0f), getResourceFloat(R.dimen.fps960_watermark_padding_x_ratio, 0.0f), getResourceFloat(R.dimen.fps960_watermark_padding_y_ratio, 0.0f)}, z3);
            } else {
                Log.d(VideoBase.TAG, "postProcessVideo: start ");
                z = VideoInterpolator.doDecodeAndEncodeSync(file.getAbsolutePath(), file2.getAbsolutePath(), DataRepository.dataItemFeature().Eb(), z3);
                Log.d(VideoBase.TAG, "postProcessVideo: end ");
            }
            if (z && file2.renameTo(file3)) {
                z2 = true;
            }
            if (isDump960Orig()) {
                file.renameTo(new File(str + ".orig.mp4"));
            } else {
                file.delete();
            }
        } catch (Throwable th) {
            if (0 == 0) {
                Log.e(VideoBase.TAG, "960fps processing failed. delete the files.");
                file.delete();
                file2.delete();
            }
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public void releaseMediaRecorder() {
        MediaRecorder mediaRecorder;
        Log.v(VideoBase.TAG, "releaseRecorder");
        synchronized (this.mLock) {
            mediaRecorder = this.mMediaRecorder;
            this.mMediaRecorder = null;
        }
        if (mediaRecorder != null) {
            cleanupEmptyFile();
            long currentTimeMillis = System.currentTimeMillis();
            mediaRecorder.reset();
            String str = VideoBase.TAG;
            Log.v(str, "releaseRecorder: t1=" + (System.currentTimeMillis() - currentTimeMillis));
            long currentTimeMillis2 = System.currentTimeMillis();
            mediaRecorder.release();
            String str2 = VideoBase.TAG;
            Log.v(str2, "releaseRecorder: t2=" + (System.currentTimeMillis() - currentTimeMillis2));
        }
    }

    private void releaseRecorderSurface() {
        Surface surface = this.mRecorderSurface;
        if (surface != null) {
            surface.release();
        }
    }

    private void releaseResources() {
        FlowableEmitter<CaptureResult> flowableEmitter = this.mAutoZoomEmitter;
        if (flowableEmitter != null) {
            flowableEmitter.onComplete();
        }
        Disposable disposable = this.mAutoZoomUiDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mAutoZoomUiDisposable.dispose();
            this.mAutoZoomUiDisposable = null;
        }
        Disposable disposable2 = this.mAutoZoomDataDisposable;
        if (disposable2 != null && !disposable2.isDisposed()) {
            this.mAutoZoomDataDisposable.dispose();
            this.mAutoZoomDataDisposable = null;
        }
        stopTracking(0);
        stopAutoZoom();
        closeCamera();
        releaseMediaRecorder();
    }

    private Uri saveVideo(String str, ContentValues contentValues, boolean z, boolean z2) {
        if (this.mActivity != null) {
            String str2 = VideoBase.TAG;
            Log.w(str2, "saveVideo: path=" + str + " isFinal=" + z);
            contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
            if (z2) {
                return this.mActivity.getImageSaver().addVideoSync(str, contentValues, false);
            }
            this.mActivity.getImageSaver().addVideo(str, contentValues, z);
        } else {
            String str3 = VideoBase.TAG;
            Log.w(str3, "saveVideo: failed to save " + str);
        }
        return null;
    }

    private void setJpegQuality() {
        if (isDeviceAlive()) {
            int jpegEncodingQualityParameter = CameraProfile.getJpegEncodingQualityParameter(this.mBogusCameraId, 2);
            String str = VideoBase.TAG;
            Log.d(str, "jpegQuality=" + jpegEncodingQualityParameter);
            this.mCamera2Device.setJpegQuality(jpegEncodingQualityParameter);
        }
    }

    private boolean setNextOutputFile(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.w(VideoBase.TAG, "setNextOutputFile, filePath is empty");
            return false;
        } else if (!Storage.isUseDocumentMode()) {
            return CompatibilityUtils.setNextOutputFile(this.mMediaRecorder, str);
        } else {
            ParcelFileDescriptor parcelFileDescriptor = null;
            try {
                parcelFileDescriptor = FileCompat.getParcelFileDescriptor(str, true);
                return CompatibilityUtils.setNextOutputFile(this.mMediaRecorder, parcelFileDescriptor.getFileDescriptor());
            } catch (Exception e2) {
                String str2 = VideoBase.TAG;
                Log.d(str2, "open file failed, filePath " + str, e2);
                return false;
            } finally {
                Util.closeSafely(parcelFileDescriptor);
            }
        }
    }

    private void setParameterExtra(MediaRecorder mediaRecorder, String str) {
        Class[] clsArr = {MediaRecorder.class};
        Method method = Util.getMethod(clsArr, "setParameter", "(Ljava/lang/String;)V");
        if (method != null) {
            method.invoke(clsArr[0], mediaRecorder, str);
        }
    }

    private void setSplitWhenReachMaxSize(boolean z) {
        this.mSplitWhenReachMaxSize = z;
    }

    private void setupRecorder(MediaRecorder mediaRecorder) throws FileNotFoundException {
        int i;
        boolean isNormalMode = isNormalMode();
        boolean z = isNormalMode || ((isFPS120() || isFPS240()) && !DataRepository.dataItemFeature().Sa());
        mediaRecorder.setVideoSource(2);
        if (z) {
            mediaRecorder.setAudioSource(5);
        }
        mediaRecorder.setOutputFormat(this.mProfile.fileFormat);
        mediaRecorder.setVideoEncoder(this.mProfile.videoCodec);
        CamcorderProfile camcorderProfile = this.mProfile;
        mediaRecorder.setVideoSize(camcorderProfile.videoFrameWidth, camcorderProfile.videoFrameHeight);
        int hSRValue = getHSRValue();
        if (hSRValue > 0) {
            mediaRecorder.setVideoFrameRate(hSRValue);
            String str = VideoBase.TAG;
            Log.d(str, "setVideoFrameRate: " + hSRValue);
        } else {
            mediaRecorder.setVideoFrameRate(this.mProfile.videoFrameRate);
            String str2 = VideoBase.TAG;
            Log.d(str2, "setVideoFrameRate: " + this.mProfile.videoFrameRate);
        }
        CamcorderProfile camcorderProfile2 = this.mProfile;
        if (5 == camcorderProfile2.videoCodec) {
            i = getHevcVideoEncoderBitRate(camcorderProfile2);
            String str3 = VideoBase.TAG;
            Log.d(str3, "H265 bitrate: " + i);
        } else {
            i = camcorderProfile2.videoBitRate;
            String str4 = VideoBase.TAG;
            Log.d(str4, "H264 bitrate: " + i);
        }
        mediaRecorder.setVideoEncodingBitRate(i);
        if (z) {
            mediaRecorder.setAudioEncodingBitRate(this.mProfile.audioBitRate);
            mediaRecorder.setAudioChannels(this.mProfile.audioChannels);
            mediaRecorder.setAudioSamplingRate(this.mProfile.audioSampleRate);
            mediaRecorder.setAudioEncoder(this.mProfile.audioCodec);
        }
        if (this.mCaptureTimeLapse) {
            mediaRecorder.setCaptureRate(1000.0d / ((double) this.mTimeBetweenTimeLapseFrameCaptureMs));
        } else if (!isNormalMode) {
            if (ModuleManager.isVideoNewSlowMotion() && !DataRepository.dataItemFeature().Sa()) {
                mediaRecorder.setVideoFrameRate(this.mFrameRate);
                mediaRecorder.setVideoEncodingBitRate(Build.VERSION.SDK_INT < 28 ? (int) ((((long) i) * ((long) this.mFrameRate)) / ((long) getNormalVideoFrameRate())) : ((this.mFrameRate / getNormalVideoFrameRate()) / 2) * i);
            }
            mediaRecorder.setCaptureRate((double) this.mFrameRate);
        } else if (hSRValue > 0) {
            mediaRecorder.setVideoFrameRate(hSRValue);
            mediaRecorder.setCaptureRate((double) hSRValue);
        }
        mediaRecorder.setMaxDuration(this.mMaxVideoDurationInMs);
        Location currentLocation = LocationManager.instance().getCurrentLocation();
        if (currentLocation != null) {
            mediaRecorder.setLocation((float) currentLocation.getLatitude(), (float) currentLocation.getLongitude());
        }
        int i2 = SystemProperties.getInt("camera.debug.video_max_size", 0);
        long recorderMaxFileSize = getRecorderMaxFileSize(i2);
        if (recorderMaxFileSize > 0) {
            String str5 = VideoBase.TAG;
            Log.v(str5, "maxFileSize=" + recorderMaxFileSize);
            mediaRecorder.setMaxFileSize(recorderMaxFileSize);
            if (recorderMaxFileSize > VIDEO_MAX_SINGLE_FILE_SIZE) {
                setParameterExtra(mediaRecorder, "param-use-64bit-offset=1");
            }
        }
        if (!DataRepository.dataItemFeature().ub() || (i2 <= 0 && recorderMaxFileSize != VIDEO_MAX_SINGLE_FILE_SIZE)) {
            setSplitWhenReachMaxSize(false);
        } else {
            setSplitWhenReachMaxSize(true);
        }
        if ((isFPS240() || isFPS960()) && !DataRepository.dataItemFeature().Sa()) {
            try {
                setParameterExtra(mediaRecorder, "video-param-i-frames-interval=0.033");
            } catch (Exception e2) {
                Log.e(VideoBase.TAG, e2.getMessage(), e2);
            }
        }
        mediaRecorder.setOrientationHint(getRecorderOrientationHint());
        this.mOrientationCompensationAtRecordStart = this.mOrientationCompensation;
    }

    private boolean shouldApplyUltraWideLDC() {
        if (CameraSettings.shouldUltraWideVideoLDCBeVisibleInMode(this.mModuleIndex) && this.mActualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            return CameraSettings.isUltraWideVideoLDCEnabled();
        }
        return false;
    }

    private void showSurfaceInfo(Surface surface) {
        boolean isValid = surface.isValid();
        boolean isSurfaceForHwVideoEncoder = SurfaceUtils.isSurfaceForHwVideoEncoder(surface);
        Size surfaceSize = SurfaceUtils.getSurfaceSize(surface);
        int surfaceFormat = SurfaceUtils.getSurfaceFormat(surface);
        String str = VideoBase.TAG;
        Log.d(str, "showSurfaceInfo: " + surface + "|" + isValid + "|" + surfaceSize + "|" + surfaceFormat + "|" + isSurfaceForHwVideoEncoder);
    }

    private void startHighSpeedRecordSession() {
        Log.d(VideoBase.TAG, "startHighSpeedRecordSession");
        if (isDeviceAlive()) {
            checkDisplayOrientation();
            this.mCamera2Device.setErrorCallback(this.mErrorCallback);
            this.mCamera2Device.setPictureSize(this.mPreviewSize);
            this.mSurfaceCreatedTimestamp = this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            this.mCamera2Device.startHighSpeedRecordSession(new Surface(this.mActivity.getCameraScreenNail().getSurfaceTexture()), this.mRecorderSurface, new Range(Integer.valueOf(this.mHfrFPSLower), Integer.valueOf(this.mHfrFPSUpper)), this);
            this.mFocusManager.resetFocused();
            showSurfaceInfo(this.mRecorderSurface);
        }
    }

    private void startPreviewAfterRecord() {
        if (isDeviceAlive() && !this.mActivity.isActivityPaused()) {
            unlockAEAF();
            if (isCaptureIntent()) {
                return;
            }
            if (ModuleManager.isVideoNewSlowMotion()) {
                this.mCamera2Device.startHighSpeedRecordPreview();
            } else {
                this.mCamera2Device.startRecordPreview();
            }
        }
    }

    private void startPreviewSession() {
        Log.d(VideoBase.TAG, "startPreviewSession");
        if (isDeviceAlive()) {
            checkDisplayOrientation();
            this.mCamera2Device.setFocusCallback(this);
            this.mCamera2Device.setErrorCallback(this.mErrorCallback);
            this.mCamera2Device.setPictureSize(this.mPreviewSize);
            Surface surface = new Surface(this.mActivity.getCameraScreenNail().getSurfaceTexture());
            this.mSurfaceCreatedTimestamp = this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            this.mCamera2Device.startPreviewSession(surface, false, false, false, getOperatingModeForPreview(), false, this);
        }
    }

    private void startRecordSession() {
        String str = VideoBase.TAG;
        Log.d(str, "startRecordSession: mode=" + this.mSpeed);
        if (isDeviceAlive()) {
            checkDisplayOrientation();
            this.mCamera2Device.setErrorCallback(this.mErrorCallback);
            this.mCamera2Device.setPictureSize(this.mPreviewSize);
            this.mCamera2Device.setVideoSnapshotSize(this.mPictureSize);
            if (this.mAELockOnlySupported) {
                this.mCamera2Device.setFocusCallback(this);
            }
            int operatingMode = getOperatingMode();
            Log.d(VideoBase.TAG, String.format("startRecordSession: operatingMode = 0x%x", new Object[]{Integer.valueOf(operatingMode)}));
            this.mSurfaceCreatedTimestamp = this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            this.mCamera2Device.startRecordSession(new Surface(this.mActivity.getCameraScreenNail().getSurfaceTexture()), this.mRecorderSurface, true, operatingMode, this);
            this.mFocusManager.resetFocused();
            this.mPreviewing = true;
        }
    }

    private boolean startRecorder() {
        if (!initializeRecorder(true)) {
            return false;
        }
        if (DataRepository.dataItemFeature().Wb() && CameraSettings.is4KHigherVideoQuality(this.mQuality)) {
            ThermalHelper.notifyThermal4KRecordStart();
        }
        try {
            this.mMediaRecorder.start();
            this.mMediaRecorderWorking = true;
            return true;
        } catch (IllegalStateException e2) {
            Log.e(VideoBase.TAG, "could not start recorder", e2);
            showConfirmMessage(R.string.confirm_recording_fail_title, R.string.confirm_recording_fail_recorder_busy_alert);
            return false;
        }
    }

    private void startVideoRecordingIfNeeded() {
        if (this.mActivity.getCameraIntentManager().checkCallerLegality()) {
            if (!this.mActivity.getCameraIntentManager().isOpenOnly()) {
                this.mActivity.getIntent().removeExtra(CameraIntentManager.CameraExtras.CAMERA_OPEN_ONLY);
                int timerDurationSeconds = this.mActivity.getCameraIntentManager().getTimerDurationSeconds();
                if (timerDurationSeconds > 60) {
                    Log.e(VideoBase.TAG, "Caller delivered a wrong duration time large as 60s!");
                } else {
                    this.mHandler.postDelayed(new Runnable() {
                        public void run() {
                            VideoModule videoModule = VideoModule.this;
                            videoModule.onShutterButtonClick(videoModule.getTriggerMode());
                        }
                    }, timerDurationSeconds == -1 ? 1500 : (long) (timerDurationSeconds * 1000));
                }
            } else {
                this.mActivity.getIntent().removeExtra(CameraIntentManager.CameraExtras.TIMER_DURATION_SECONDS);
            }
        }
    }

    @SuppressLint({"CheckResult"})
    private void stopRecorder() {
        this.mPendingStopRecorder = false;
        this.mHandler.removeMessages(46);
        if (DataRepository.dataItemFeature().Wb() && CameraSettings.is4KHigherVideoQuality(this.mQuality)) {
            ThermalHelper.notifyThermal4KRecordStop();
        }
        Single.create(new SingleOnSubscribe<Boolean>() {
            public void subscribe(SingleEmitter<Boolean> singleEmitter) throws Exception {
                CountDownLatch unused = VideoModule.this.mStopRecorderDone = new CountDownLatch(1);
                long currentTimeMillis = System.currentTimeMillis();
                ScenarioTrackUtil.trackStopVideoRecordStart(VideoModule.this.mSpeed, VideoModule.this.isFrontCamera());
                try {
                    VideoModule.this.mMediaRecorder.setOnErrorListener((MediaRecorder.OnErrorListener) null);
                    VideoModule.this.mMediaRecorder.setOnInfoListener((MediaRecorder.OnInfoListener) null);
                    VideoModule.this.mMediaRecorder.stop();
                } catch (RuntimeException e2) {
                    Log.e(VideoBase.TAG, "failed to stop media recorder", e2);
                    VideoModule videoModule = VideoModule.this;
                    String str = videoModule.mCurrentVideoFilename;
                    if (str != null) {
                        videoModule.deleteVideoFile(str);
                        VideoModule.this.mCurrentVideoFilename = null;
                    }
                }
                if (!VideoModule.this.mPaused && !VideoModule.this.mActivity.isActivityPaused()) {
                    VideoModule.this.playCameraSound(3);
                }
                VideoModule.this.releaseMediaRecorder();
                VideoModule.this.mStopRecorderDone.countDown();
                String str2 = VideoBase.TAG;
                Log.d(str2, "releaseTime=" + (System.currentTimeMillis() - currentTimeMillis));
                long uptimeMillis = SystemClock.uptimeMillis();
                VideoModule videoModule2 = VideoModule.this;
                long j = uptimeMillis - videoModule2.mRecordingStartTime;
                if (videoModule2.mCamera2Device != null && ModuleManager.isVideoNewSlowMotion() && (VideoModule.this.isFPS120() || VideoModule.this.isFPS240())) {
                    CameraStatUtil.trackNewSlowMotionVideoRecorded(VideoModule.this.isFPS120() ? CameraSettings.VIDEO_MODE_120 : CameraSettings.VIDEO_MODE_240, VideoModule.this.mQuality, VideoModule.this.mCamera2Device.getFlashMode(), VideoModule.this.mFrameRate, j / 1000);
                }
                VideoModule videoModule3 = VideoModule.this;
                if (videoModule3.mCurrentVideoFilename != null && videoModule3.isFPS960()) {
                    if (!VideoModule.this.mMediaRecorderPostProcessing || !VideoModule.this.isActivityResumed()) {
                        String str3 = VideoBase.TAG;
                        Log.d(str3, "uncomplete video.=" + j);
                        VideoModule videoModule4 = VideoModule.this;
                        videoModule4.deleteVideoFile(videoModule4.mCurrentVideoFilename);
                        VideoModule.this.mCurrentVideoFilename = null;
                        CameraStat.recordCountEvent(CameraStat.CATEGORY_COUNTER, CameraStat.KEY_FPS960_TOO_SHORT);
                    } else {
                        long currentTimeMillis2 = System.currentTimeMillis();
                        VideoModule videoModule5 = VideoModule.this;
                        String access$1500 = videoModule5.postProcessVideo(videoModule5.mCurrentVideoFilename);
                        String str4 = VideoBase.TAG;
                        Log.d(str4, "processTime=" + (System.currentTimeMillis() - currentTimeMillis2));
                        if (access$1500 == null) {
                            VideoModule videoModule6 = VideoModule.this;
                            videoModule6.mCurrentVideoFilename = null;
                            videoModule6.mCurrentVideoValues = null;
                            CameraStat.recordCountEvent(CameraStat.CATEGORY_COUNTER, CameraStat.KEY_FPS960_PROCESS_FAILED);
                        } else {
                            VideoModule videoModule7 = VideoModule.this;
                            videoModule7.mCurrentVideoFilename = access$1500;
                            videoModule7.mCurrentVideoValues.put("_data", access$1500);
                            CameraStatUtil.trackNewSlowMotionVideoRecorded(CameraSettings.VIDEO_MODE_960, VideoModule.this.mQuality, VideoModule.this.mCamera2Device.getFlashMode(), 960, 10);
                        }
                    }
                }
                singleEmitter.onSuccess(Boolean.TRUE);
            }
        }).subscribeOn(GlobalConstant.sCameraSetupScheduler).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
            public void accept(Boolean bool) throws Exception {
                VideoModule.this.onMediaRecorderReleased();
            }
        });
    }

    private void updateAutoZoomMode() {
        boolean isAutoZoomEnabled = CameraSettings.isAutoZoomEnabled(this.mModuleIndex);
        if (this.mCamera2Device != null && isAlive()) {
            this.mCamera2Device.setAutoZoomMode(isAutoZoomEnabled ? 1 : 0);
            if (isAutoZoomEnabled) {
                this.mCamera2Device.setAutoZoomScaleOffset(0.0f);
            }
        }
    }

    private void updateFpsRange() {
        if (isDeviceAlive()) {
            if (ModuleManager.isVideoNewSlowMotion()) {
                String str = VideoBase.TAG;
                Log.d(str, "mHfrFPSLower = " + this.mHfrFPSLower + ", mHfrFPSUpper = " + this.mHfrFPSUpper);
                this.mCamera2Device.setVideoFpsRange(new Range(Integer.valueOf(this.mHfrFPSLower), Integer.valueOf(this.mHfrFPSUpper)));
                return;
            }
            Range[] supportedFpsRange = this.mCameraCapabilities.getSupportedFpsRange();
            int i = 0;
            Range range = supportedFpsRange[0];
            int length = supportedFpsRange.length;
            while (true) {
                if (i >= length) {
                    break;
                }
                Range range2 = supportedFpsRange[i];
                int hSRValue = getHSRValue();
                if (hSRValue == 60) {
                    range = new Range(Integer.valueOf(hSRValue), Integer.valueOf(hSRValue));
                    break;
                }
                if (((Integer) range.getUpper()).intValue() < ((Integer) range2.getUpper()).intValue() || (range.getUpper() == range2.getUpper() && ((Integer) range.getLower()).intValue() < ((Integer) range2.getLower()).intValue())) {
                    range = range2;
                }
                i++;
            }
            String str2 = VideoBase.TAG;
            Log.d(str2, "bestRange = " + range);
            this.mCamera2Device.setFpsRange(range);
            this.mCamera2Device.setVideoFpsRange(range);
        }
    }

    private void updateFrontMirror() {
        this.mCamera2Device.setFrontMirror(isFrontCamera() && !b.hi() && DataRepository.dataItemFeature().vc() && CameraSettings.isFrontMirror());
    }

    private void updateHFRDeflicker() {
        if (DataRepository.dataItemFeature().Eb() && isFPS960()) {
            this.mCamera2Device.setHFRDeflickerEnable(true);
        }
    }

    private void updateHfrFPSRange(Size size, int i) {
        Range range = null;
        for (Range range2 : this.mCameraCapabilities.getSupportedHighSpeedVideoFPSRange(size)) {
            if (((Integer) range2.getUpper()).intValue() == i && (range == null || ((Integer) range.getLower()).intValue() < ((Integer) range2.getLower()).intValue())) {
                range = range2;
            }
        }
        this.mHfrFPSLower = ((Integer) range.getLower()).intValue();
        this.mHfrFPSUpper = ((Integer) range.getUpper()).intValue();
    }

    private void updateMacroMode() {
        this.mCamera2Device.setMacroMode(CameraSettings.isMacroModeEnabled(this.mModuleIndex));
    }

    private void updateMutexModePreference() {
        if ("on".equals(DataRepository.dataItemConfig().getComponentHdr().getComponentValue(this.mModuleIndex))) {
            this.mMutexModePicker.setMutexMode(2);
        }
    }

    private void updatePictureAndPreviewSize() {
        int i;
        int i2;
        CamcorderProfile camcorderProfile = this.mProfile;
        double d2 = ((double) camcorderProfile.videoFrameWidth) / ((double) camcorderProfile.videoFrameHeight);
        List<CameraSize> supportedOutputSize = this.mCameraCapabilities.getSupportedOutputSize(MediaRecorder.class);
        CamcorderProfile camcorderProfile2 = this.mProfile;
        CameraSize optimalVideoSnapshotPictureSize = Util.getOptimalVideoSnapshotPictureSize(supportedOutputSize, d2, camcorderProfile2.videoFrameWidth, camcorderProfile2.videoFrameHeight);
        this.mVideoSize = optimalVideoSnapshotPictureSize;
        Log.d(VideoBase.TAG, "videoSize: " + optimalVideoSnapshotPictureSize.toString());
        int i3 = Integer.MAX_VALUE;
        if (b.Fj()) {
            i3 = optimalVideoSnapshotPictureSize.width;
            i = optimalVideoSnapshotPictureSize.height;
        } else {
            i = Integer.MAX_VALUE;
        }
        this.mPictureSize = Util.getOptimalVideoSnapshotPictureSize(this.mCameraCapabilities.getSupportedOutputSize(256), d2, i3, i);
        Log.d(VideoBase.TAG, "pictureSize: " + r3);
        int i4 = optimalVideoSnapshotPictureSize.width;
        if (i4 > Util.sWindowHeight || i4 < 720) {
            i4 = Util.sWindowHeight;
            i2 = Util.sWindowWidth;
        } else {
            i2 = optimalVideoSnapshotPictureSize.height;
        }
        this.mPreviewSize = Util.getOptimalVideoSnapshotPictureSize(this.mCameraCapabilities.getSupportedOutputSize(SurfaceTexture.class), d2, i4, i2);
        CameraSize cameraSize = this.mPreviewSize;
        updateCameraScreenNailSize(cameraSize.width, cameraSize.height);
    }

    private void updateUltraWideLDC() {
        this.mCamera2Device.setUltraWideLDC(shouldApplyUltraWideLDC());
    }

    private void updateVideoStabilization() {
        if (isDeviceAlive()) {
            if (needDisableEISAndOIS()) {
                this.mCamera2Device.setEnableEIS(false);
                this.mCamera2Device.setEnableOIS(false);
                this.mActivity.getCameraScreenNail().setVideoStabilizationCropped(false);
            } else if (isEisOn()) {
                Log.d(VideoBase.TAG, "videoStabilization: EIS");
                this.mCamera2Device.setEnableEIS(true);
                this.mCamera2Device.setEnableOIS(false);
                if (!this.mCameraCapabilities.isEISPreviewSupported()) {
                    this.mActivity.getCameraScreenNail().setVideoStabilizationCropped(true);
                }
            } else {
                Log.d(VideoBase.TAG, "videoStabilization: OIS");
                this.mCamera2Device.setEnableEIS(false);
                this.mCamera2Device.setEnableOIS(true);
                this.mActivity.getCameraScreenNail().setVideoStabilizationCropped(false);
            }
        }
    }

    public void consumePreference(@UpdateConstant.UpdateType int... iArr) {
        for (int i : iArr) {
            if (i == 1) {
                updatePictureAndPreviewSize();
            } else if (i == 3) {
                updateFocusArea();
            } else if (i == 5) {
                updateFace();
            } else if (i == 40) {
                updateFrontMirror();
            } else if (i == 42) {
                continue;
            } else if (i == 55) {
                updateModuleRelated();
            } else if (i == 58) {
                updateBackSoftLightPreference();
            } else if (i == 19) {
                updateFpsRange();
            } else if (i == 20) {
                continue;
            } else if (i == 24) {
                applyZoomRatio();
            } else if (i == 25) {
                focusCenter();
            } else if (i == 46) {
                continue;
            } else if (i != 47) {
                switch (i) {
                    case 9:
                        updateAntiBanding(DataRepository.dataItemFeature().Sa() ? "0" : CameraSettings.getAntiBanding());
                        break;
                    case 10:
                        updateFlashPreference();
                        break;
                    case 11:
                        continue;
                    case 12:
                        setEvValue();
                        break;
                    case 13:
                        updateBeauty();
                        break;
                    case 14:
                        updateVideoFocusMode();
                        break;
                    default:
                        switch (i) {
                            case 29:
                                updateExposureMeteringMode();
                                break;
                            case 30:
                                continue;
                            case 31:
                                updateVideoStabilization();
                                break;
                            default:
                                switch (i) {
                                    case 33:
                                        Camera2Proxy camera2Proxy = this.mCamera2Device;
                                        if (camera2Proxy == null) {
                                            break;
                                        } else {
                                            camera2Proxy.setVideoSnapshotSize(this.mPictureSize);
                                            break;
                                        }
                                    case 34:
                                        continue;
                                    case 35:
                                        updateDeviceOrientation();
                                        break;
                                    default:
                                        switch (i) {
                                            case 50:
                                                continue;
                                            case 51:
                                                updateAutoZoomMode();
                                                break;
                                            case 52:
                                                updateMacroMode();
                                                break;
                                            case 53:
                                                updateHFRDeflicker();
                                                break;
                                            default:
                                                if (!BaseModule.DEBUG) {
                                                    Log.w(VideoBase.TAG, "no consumer for this updateType: " + i);
                                                    break;
                                                } else {
                                                    throw new RuntimeException("no consumer for this updateType: " + i);
                                                }
                                        }
                                }
                        }
                }
            } else {
                updateUltraWideLDC();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doLaterReleaseIfNeed() {
        super.doLaterReleaseIfNeed();
        if (isFPS960() && !this.mActivity.isActivityPaused()) {
            if (isTextureExpired()) {
                Log.d(VideoBase.TAG, "doLaterReleaseIfNeed: restartModule...");
                restartModule();
                return;
            }
            Log.d(VideoBase.TAG, "doLaterReleaseIfNeed: dismissBlurCover...");
            this.mActivity.dismissBlurCover();
        }
    }

    /* access modifiers changed from: protected */
    public boolean enableFaceDetection() {
        return (!DataRepository.dataItemFeature().xc() || !isBackCamera()) && !ModuleManager.isVideoNewSlowMotion() && DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_FACE_DETECTION, getResources().getBoolean(R.bool.pref_camera_facedetection_default));
    }

    /* access modifiers changed from: protected */
    public CamcorderProfile fetchProfile(int i, int i2) {
        return CamcorderProfile.get(i, i2);
    }

    /* access modifiers changed from: protected */
    public int getNormalVideoFrameRate() {
        if (DataRepository.dataItemFeature().Sa()) {
            return 30;
        }
        CamcorderProfile camcorderProfile = this.mProfile;
        if (camcorderProfile != null) {
            return camcorderProfile.videoFrameRate;
        }
        return 30;
    }

    /* access modifiers changed from: protected */
    public int getOperatingMode() {
        int i = 32772;
        boolean z = false;
        if (!isFrontCamera()) {
            int i2 = 61456;
            if (this.mCameraCapabilities.isSupportVideoBeauty() && isFaceBeautyOn(this.mBeautyValues)) {
                i2 = 32777;
            } else if (this.mQuality == 0) {
                i2 = 0;
            }
            if (CameraSettings.isSuperEISEnabled(this.mModuleIndex)) {
                i = CameraCapabilities.SESSION_OPERATION_MODE_VIDEO_SUPEREIS;
            } else if (!isEisOn()) {
                i = i2;
            }
            if (this.mCameraCapabilities.isFovcSupported()) {
                if (i != 0) {
                    z = true;
                }
                this.mFovcEnabled = z;
            }
            if (getHSRValue() == 60) {
                i = 32828;
            }
            if (CameraSettings.isAutoZoomEnabled(this.mModuleIndex)) {
                i = CameraCapabilities.SESSION_OPERATION_MODE_AUTO_ZOOM;
            }
        } else if (CameraSettings.isVideoBokehOn()) {
            i = 32770;
        } else if (!isEisOn()) {
            i = this.mCameraCapabilities.isSupportVideoBeauty() ? 32777 : 0;
        }
        Log.d(VideoBase.TAG, "getOperatingMode(): " + Integer.toHexString(i));
        return i;
    }

    public List<String> getSupportedSettingKeys() {
        ArrayList arrayList = new ArrayList();
        if (isBackCamera()) {
            arrayList.add("pref_video_speed_fast_key");
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public boolean isAEAFLockSupported() {
        return !this.mMediaRecorderRecording || !isFPS960();
    }

    /* access modifiers changed from: protected */
    public boolean isCameraSwitchingDuringZoomingAllowed() {
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            int i = this.mModuleIndex;
            if ((i == 162 || i == 169) && !CameraSettings.isMacroModeEnabled(this.mModuleIndex) && !CameraSettings.isSuperEISEnabled(this.mModuleIndex) && isBackCamera() && !this.mMediaRecorderRecording && !this.mMediaRecorderRecordingPaused) {
                return true;
            }
        }
        return false;
    }

    public boolean isNeedHapticFeedback() {
        return !this.mMediaRecorderRecording || this.mMediaRecorderRecordingPaused;
    }

    public boolean isNeedMute() {
        return this.mObjectTrackingStarted || (this.mMediaRecorderRecording && !this.mMediaRecorderRecordingPaused);
    }

    public boolean isPostProcessing() {
        return this.mMediaRecorderPostProcessing;
    }

    /* access modifiers changed from: protected */
    public boolean isShowHFRDuration() {
        return true;
    }

    public boolean isUnInterruptable() {
        if (!super.isUnInterruptable() && !isNormalMode() && this.mMediaRecorder != null && this.mMediaRecorderWorking) {
            this.mUnInterruptableReason = "recorder release";
        }
        return this.mUnInterruptableReason != null;
    }

    /* access modifiers changed from: protected */
    public boolean isZoomEnabled() {
        if ((!isFPS960() || !this.mMediaRecorderRecording) && !CameraSettings.isAutoZoomEnabled(this.mModuleIndex)) {
            return super.isZoomEnabled();
        }
        return false;
    }

    public boolean onBackPressed() {
        if (!isFrameAvailable()) {
            return false;
        }
        if (this.mPaused || this.mActivity.isActivityPaused()) {
            return true;
        }
        if (this.mStereoSwitchThread != null) {
            return false;
        }
        if (isFPS960() && this.mMediaRecorderPostProcessing) {
            return true;
        }
        if (this.mMediaRecorderRecording) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.mLastBackPressedTime > 3000) {
                this.mLastBackPressedTime = currentTimeMillis;
                ToastUtils.showToast((Context) this.mActivity, (int) R.string.record_back_pressed_hint);
            } else {
                stopVideoRecording(true, false);
            }
            return true;
        } else if (CameraSettings.isStereoModeOn()) {
            return true;
        } else {
            if (!this.isAutoZoomTracking.get()) {
                return super.onBackPressed();
            }
            stopTracking(0);
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void onCameraOpened() {
        super.onCameraOpened();
        updateBeauty();
        readVideoPreferences();
        initializeFocusManager();
        updatePreferenceTrampoline(UpdateConstant.VIDEO_TYPES_INIT);
        if (!initializeRecorder(false)) {
            startPreviewSession();
        } else if (ModuleManager.isVideoNewSlowMotion()) {
            startHighSpeedRecordSession();
        } else {
            startRecordSession();
        }
        initAutoZoom();
    }

    public void onCreate(int i, int i2) {
        super.onCreate(i, i2);
        changeConflictPreference();
        setCaptureIntent(this.mActivity.getCameraIntentManager().isVideoCaptureIntent());
        EffectController.getInstance().setEffect(FilterInfo.FILTER_ID_NONE);
        this.mActivity.getSensorStateManager().setSensorStateListener(this.mSensorStateListener);
        this.mQuickCapture = this.mActivity.getCameraIntentManager().isQuickCapture().booleanValue();
        this.mMainProtocol.setObjectViewListener(this);
        enableCameraControls(false);
        this.mTelephonyManager = (TelephonyManager) this.mActivity.getSystemService("phone");
        this.mVideoFocusMode = AutoFocus.LEGACY_CONTINUOUS_VIDEO;
        this.mAutoZoomViewProtocol = (ModeProtocol.AutoZoomViewProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(214);
        this.mTopAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        initRecorderSurface();
        onCameraOpened();
    }

    public void onDestroy() {
        super.onDestroy();
        releaseRecorderSurface();
        this.mHandler.sendEmptyMessage(45);
    }

    public void onError(MediaRecorder mediaRecorder, int i, int i2) {
        String str = VideoBase.TAG;
        Log.e(str, "MediaRecorder error. what=" + i + " extra=" + i2);
        if (i == 1 || i == 100) {
            if (this.mMediaRecorderRecording) {
                stopVideoRecording(true, false);
            }
            this.mActivity.getScreenHint().updateHint();
        }
    }

    public boolean onGestureTrack(RectF rectF, boolean z) {
        if (this.mInStartingFocusRecording || !isBackCamera() || !b.pj() || CameraSettings.is4KHigherVideoQuality(this.mQuality) || isCaptureIntent()) {
            return false;
        }
        return initializeObjectTrack(rectF, z);
    }

    public void onInfo(MediaRecorder mediaRecorder, int i, int i2) {
        if (!this.mMediaRecorderRecording) {
            Log.w(VideoBase.TAG, "onInfo: ignore event " + i);
            return;
        }
        switch (i) {
            case MediaPlayer2.MEDIA_INFO_BAD_INTERLEAVING:
                stopVideoRecording(true, false);
                return;
            case MediaPlayer2.MEDIA_INFO_NOT_SEEKABLE:
                Log.w(VideoBase.TAG, "reached max size. fileNumber=" + this.mCurrentFileNumber);
                stopVideoRecording(true, false);
                if (!this.mActivity.getScreenHint().isScreenHintVisible()) {
                    Toast.makeText(this.mActivity, R.string.video_reach_size_limit, 1).show();
                    return;
                }
                return;
            case 802:
                boolean isSplitWhenReachMaxSize = isSplitWhenReachMaxSize();
                Log.d(VideoBase.TAG, "max file size is approaching. split: " + isSplitWhenReachMaxSize);
                if (isSplitWhenReachMaxSize) {
                    this.mCurrentFileNumber++;
                    ContentValues genContentValues = genContentValues(this.mOutputFormat, this.mCurrentFileNumber);
                    String asString = genContentValues.getAsString("_data");
                    Log.d(VideoBase.TAG, "nextVideoPath: " + asString);
                    if (setNextOutputFile(asString)) {
                        this.mNextVideoValues = genContentValues;
                        this.mNextVideoFileName = asString;
                        return;
                    }
                    return;
                }
                return;
            case 803:
                Log.d(VideoBase.TAG, "next output file started");
                onMaxFileSizeReached();
                this.mCurrentVideoValues = this.mNextVideoValues;
                this.mCurrentVideoFilename = this.mNextVideoFileName;
                this.mNextVideoValues = null;
                this.mNextVideoFileName = null;
                return;
            default:
                Log.w(VideoBase.TAG, "onInfo what : " + i);
                return;
        }
    }

    public void onObjectStable() {
    }

    public void onPause() {
        if (this.mCamera2Device != null && (this.mFovcEnabled || (this.mCameraCapabilities.isEISPreviewSupported() && isEisOn()))) {
            this.mCamera2Device.notifyVideoStreamEnd();
        }
        super.onPause();
        waitStereoSwitchThread();
        stopObjectTracking(false);
        releaseResources();
        updateStereoSettings(false);
        closeVideoFileDescriptor();
        this.mActivity.getSensorStateManager().reset();
        stopFaceDetection(true);
        resetScreenOn();
        this.mHandler.removeCallbacksAndMessages((Object) null);
        if (!this.mActivity.isActivityPaused() && !CameraSettings.isStereoModeOn()) {
            PopupManager.getInstance(this.mActivity).notifyShowPopup((View) null, 1);
        }
    }

    public void onPauseButtonClick() {
        String str = VideoBase.TAG;
        Log.d(str, "onPauseButtonClick: isRecordingPaused=" + this.mMediaRecorderRecordingPaused + " isRecording=" + this.mMediaRecorderRecording);
        long currentTimeMillis = System.currentTimeMillis();
        if (this.mMediaRecorderRecording && currentTimeMillis - this.mPauseClickTime >= 500) {
            this.mPauseClickTime = currentTimeMillis;
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (this.mMediaRecorderRecordingPaused) {
                try {
                    if (Build.VERSION.SDK_INT >= 24) {
                        CompatibilityUtils.resumeMediaRecorder(this.mMediaRecorder);
                    } else {
                        this.mMediaRecorder.start();
                    }
                    this.mRecordingStartTime = SystemClock.uptimeMillis() - this.mVideoRecordedDuration;
                    this.mVideoRecordedDuration = 0;
                    this.mMediaRecorderRecordingPaused = false;
                    this.mHandler.removeMessages(42);
                    this.mRecordingTime = "";
                    updateRecordingTime();
                    recordState.onResume();
                } catch (IllegalStateException e2) {
                    Log.e(VideoBase.TAG, "failed to resume media recorder", e2);
                    releaseMediaRecorder();
                    recordState.onFailed();
                }
            } else {
                pauseVideoRecording();
                CameraStatUtil.trackPauseVideoRecording(isFrontCamera());
                recordState.onPause();
            }
            Log.d(VideoBase.TAG, "onPauseButtonClick");
        }
    }

    public void onPreviewLayoutChanged(Rect rect) {
        String str = VideoBase.TAG;
        Log.v(str, "onPreviewLayoutChanged: " + rect);
        this.mActivity.onLayoutChange(rect);
        if (this.mFocusManager != null && this.mActivity.getCameraScreenNail() != null) {
            this.mActivity.getCameraScreenNail().setDisplayArea(rect);
            this.mFocusManager.setRenderSize(this.mActivity.getCameraScreenNail().getRenderWidth(), this.mActivity.getCameraScreenNail().getRenderHeight());
            this.mFocusManager.setPreviewSize(rect.width(), rect.height());
        }
    }

    public void onPreviewMetaDataUpdate(CaptureResult captureResult) {
        super.onPreviewMetaDataUpdate(captureResult);
        if (this.isAutoZoomTracking.get()) {
            this.mAutoZoomEmitter.onNext(captureResult);
        }
    }

    public void onPreviewSessionFailed(CameraCaptureSession cameraCaptureSession) {
        super.onPreviewSessionFailed(cameraCaptureSession);
        enableCameraControls(true);
    }

    public void onPreviewSessionSuccess(CameraCaptureSession cameraCaptureSession) {
        super.onPreviewSessionSuccess(cameraCaptureSession);
        if (!isCreated()) {
            Log.w(VideoBase.TAG, "onPreviewSessionSuccess: module is not ready");
            enableCameraControls(true);
            return;
        }
        String str = VideoBase.TAG;
        Log.d(str, "onPreviewSessionSuccess: session=" + cameraCaptureSession);
        this.mFaceDetectionEnabled = false;
        updatePreferenceInWorkThread(UpdateConstant.VIDEO_TYPES_ON_PREVIEW_SUCCESS);
        enableCameraControls(true);
    }

    /* access modifiers changed from: protected */
    @MainThread
    public void onPreviewStart() {
        if (this.mPreviewing) {
            this.mMainProtocol.initializeFocusView(this);
            updateMutexModePreference();
            onShutterButtonFocus(true, 3);
            startVideoRecordingIfNeeded();
        }
    }

    public void onSharedPreferenceChanged() {
        if (!this.mPaused && !this.mActivity.isActivityPaused() && this.mCamera2Device != null) {
            CamcorderProfile camcorderProfile = this.mProfile;
            int i = camcorderProfile.videoFrameWidth;
            int i2 = camcorderProfile.videoFrameHeight;
            readVideoPreferences();
            CamcorderProfile camcorderProfile2 = this.mProfile;
            if (camcorderProfile2.videoFrameWidth != i || camcorderProfile2.videoFrameHeight != i2) {
                Log.d(VideoBase.TAG, String.format(Locale.ENGLISH, "profile size changed [%d %d]->[%d %d]", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(this.mProfile.videoFrameWidth), Integer.valueOf(this.mProfile.videoFrameHeight)}));
                updatePreferenceTrampoline(1);
            }
        }
    }

    public void onShutterButtonClick(int i) {
        String str = VideoBase.TAG;
        Log.v(str, "onShutterButtonClick isRecording=" + this.mMediaRecorderRecording + " inStartingFocusRecording=" + this.mInStartingFocusRecording);
        this.mInStartingFocusRecording = false;
        this.mLastBackPressedTime = 0;
        if (isIgnoreTouchEvent()) {
            Log.w(VideoBase.TAG, "onShutterButtonClick: ignore touch event");
        } else if (isFrontCamera() && this.mActivity.isScreenSlideOff()) {
        } else {
            if (this.mMediaRecorderRecording) {
                stopVideoRecording(true, false);
                return;
            }
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            recordState.onPrepare();
            if (!checkCallingState()) {
                recordState.onFailed();
                return;
            }
            this.mActivity.getScreenHint().updateHint();
            if (Storage.isLowStorageAtLastPoint()) {
                recordState.onFailed();
                return;
            }
            setTriggerMode(i);
            enableCameraControls(false);
            playCameraSound(2);
            if (this.mFocusManager.canRecord()) {
                startVideoRecording();
                return;
            }
            Log.v(VideoBase.TAG, "wait for autoFocus");
            this.mInStartingFocusRecording = true;
            this.mHandler.sendEmptyMessageDelayed(55, 3000);
        }
    }

    public void onShutterButtonFocus(boolean z, int i) {
    }

    public void onSingleTapUp(int i, int i2, boolean z) {
        if (!this.mPaused && this.mCamera2Device != null && !hasCameraException() && this.mCamera2Device.isSessionReady() && !this.mSnapshotInProgress && isInTapableRect(i, i2)) {
            if (!isFrameAvailable()) {
                Log.w(VideoBase.TAG, "onSingleTapUp: frame not available");
            } else if (!isFrontCamera() || !this.mActivity.isScreenSlideOff()) {
                ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
                if (backStack != null && !backStack.handleBackStackFromTapDown(i, i2) && !this.isAutoZoomTracking.get()) {
                    if (this.mObjectTrackingStarted) {
                        stopObjectTracking(false);
                    }
                    unlockAEAF();
                    this.mMainProtocol.setFocusViewType(true);
                    this.mTouchFocusStartingTime = System.currentTimeMillis();
                    Point point = new Point(i, i2);
                    mapTapCoordinate(point);
                    this.mFocusManager.onSingleTapUp(point.x, point.y, z);
                }
            }
        }
    }

    public void onStop() {
        super.onStop();
        this.mHandler.removeCallbacksAndMessages((Object) null);
        exitSavePowerMode();
    }

    public void onTrackLost() {
        notifyAutoZoomStartUiHint();
    }

    public void onTrackLosting() {
        this.mTrackLostCount++;
    }

    public void onUserInteraction() {
        super.onUserInteraction();
        if (this.mMediaRecorderRecording) {
            keepPowerSave();
        }
    }

    public void onVideoRecordStopped() {
        String str = VideoBase.TAG;
        Log.d(str, "onVideoRecordStopped: " + this.mPendingStopRecorder);
        if (this.mPendingStopRecorder) {
            stopRecorder();
            startPreviewAfterRecord();
        }
    }

    /* access modifiers changed from: protected */
    public void onWaitStopCallbackTimeout() {
        stopRecorder();
        startPreviewAfterRecord();
    }

    public void onZoomingActionEnd(int i) {
        String str = VideoBase.TAG;
        Log.d(str, "onZoomingActionEnd(): " + ZoomingAction.toString(i) + " @hash: " + hashCode());
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null && !dualController.isSlideVisible()) {
            if ((i == 2 || i == 1) && !this.mMediaRecorderRecording && !this.mMediaRecorderRecordingPaused && !CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                dualController.setImmersiveModeEnabled(false);
            }
        }
    }

    public void onZoomingActionStart(int i) {
        String str = VideoBase.TAG;
        Log.d(str, "onZoomingActionStart(): " + ZoomingAction.toString(i) + " @hash: " + hashCode());
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && topAlert.isExtraMenuShowing()) {
            topAlert.hideExtraMenu();
        }
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController == null) {
            return;
        }
        if (i != 2 && i != 1) {
            return;
        }
        if (DataRepository.dataItemFeature().mb()) {
            dualController.setImmersiveModeEnabled(false);
        } else {
            dualController.setImmersiveModeEnabled(true);
        }
    }

    /* access modifiers changed from: protected */
    public void pauseMediaRecorder(MediaRecorder mediaRecorder) {
        CompatibilityUtils.pauseMediaRecorder(mediaRecorder);
    }

    public void pausePreview() {
        Log.v(VideoBase.TAG, "pausePreview");
        this.mPreviewing = false;
        if (currentIsMainThread()) {
            stopObjectTracking(false);
        }
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.pausePreview();
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.resetFocused();
        }
    }

    public void reShowMoon() {
        if (CameraSettings.isAutoZoomEnabled(this.mModuleIndex)) {
            if (DataRepository.dataItemFeature().Ta()) {
                ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).alertSwitchHint(2, (int) R.string.autozoom_hint);
            }
            notifyAutoZoomStartUiHint();
        }
    }

    /* access modifiers changed from: protected */
    public void readVideoPreferences() {
        int preferVideoQuality = !ModuleManager.isVideoNewSlowMotion() ? CameraSettings.getPreferVideoQuality(this.mActualCameraId, this.mModuleIndex) : 6;
        try {
            int videoQuality = getActivity().getCameraIntentManager().getVideoQuality();
            preferVideoQuality = videoQuality == 1 ? CameraSettings.getPreferVideoQuality(this.mActualCameraId, this.mModuleIndex) : videoQuality == 0 ? videoQuality : CameraSettings.getPreferVideoQuality(String.valueOf(videoQuality), this.mActualCameraId, this.mModuleIndex);
        } catch (RuntimeException unused) {
        }
        this.mSpeed = DataRepository.dataItemRunning().getVideoSpeed();
        if (ModuleManager.isVideoNewSlowMotion()) {
            this.mSpeed = CameraSettings.VIDEO_MODE_960;
        } else {
            this.mSlowModeFps = null;
        }
        this.mTimeBetweenTimeLapseFrameCaptureMs = 0;
        this.mCaptureTimeLapse = false;
        if (CameraSettings.VIDEO_SPEED_FAST.equals(this.mSpeed)) {
            this.mTimeBetweenTimeLapseFrameCaptureMs = Integer.parseInt(DataRepository.dataItemGlobal().getString(CameraSettings.KEY_VIDEO_TIME_LAPSE_FRAME_INTERVAL, getString(R.string.pref_video_time_lapse_frame_interval_default)));
            this.mCaptureTimeLapse = this.mTimeBetweenTimeLapseFrameCaptureMs != 0;
            if (this.mCaptureTimeLapse) {
                preferVideoQuality += 1000;
                if (preferVideoQuality < 1000 || preferVideoQuality > 1008) {
                    preferVideoQuality += NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
                    this.mCaptureTimeLapse = false;
                    forceToNormalMode();
                    RotateTextToast.getInstance(this.mActivity).show(R.string.time_lapse_error, this.mOrientation);
                }
            }
            this.mQuality = preferVideoQuality % 1000;
        } else if (ModuleManager.isVideoNewSlowMotion()) {
            this.mQuality = 6;
            Size size = SIZE_1080;
            int parseInt = Integer.parseInt(DataRepository.dataItemConfig().getComponentConfigSlowMotionQuality().getComponentValue(172));
            if (parseInt == 5) {
                size = SIZE_720;
                this.mQuality = parseInt;
            }
            this.mSlowModeFps = DataRepository.dataItemConfig().getComponentConfigSlowMotion().getComponentValue(172);
            if (isFPS120()) {
                updateHfrFPSRange(size, 120);
            } else {
                updateHfrFPSRange(size, 240);
            }
            preferVideoQuality = parseInt;
        } else {
            this.mQuality = preferVideoQuality;
        }
        CamcorderProfile camcorderProfile = this.mProfile;
        if (!(camcorderProfile == null || camcorderProfile.quality % 1000 == this.mQuality)) {
            stopObjectTracking(false);
        }
        this.mProfile = fetchProfile(this.mBogusCameraId, preferVideoQuality);
        this.mProfile.videoCodec = CameraSettings.getVideoEncoder();
        this.mOutputFormat = this.mProfile.fileFormat;
        String str = VideoBase.TAG;
        Locale locale = Locale.ENGLISH;
        Object[] objArr = new Object[4];
        objArr[0] = Integer.valueOf(getHSRValue() > 0 ? getHSRValue() : this.mProfile.videoFrameRate);
        objArr[1] = Integer.valueOf(this.mProfile.videoFrameWidth);
        objArr[2] = Integer.valueOf(this.mProfile.videoFrameHeight);
        objArr[3] = Integer.valueOf(this.mProfile.videoCodec);
        Log.d(str, String.format(locale, "frameRate=%d profileSize=%dx%d codec=%d", objArr));
        if (ModuleManager.isVideoNewSlowMotion()) {
            this.mFrameRate = this.mHfrFPSUpper;
        } else {
            this.mFrameRate = this.mProfile.videoFrameRate;
        }
        if (isFPS960()) {
            this.mMaxVideoDurationInMs = 2000;
            return;
        }
        try {
            this.mMaxVideoDurationInMs = this.mActivity.getCameraIntentManager().getVideoDurationTime() * 1000;
        } catch (RuntimeException unused2) {
            if (!DataRepository.dataItemFeature().wb() || !CameraSettings.is4KHigherVideoQuality(this.mQuality) || this.mCaptureTimeLapse) {
                this.mMaxVideoDurationInMs = 0;
            } else {
                this.mMaxVideoDurationInMs = MAX_DURATION_4K;
            }
        }
        int i = this.mMaxVideoDurationInMs;
        if (i != 0 && i < 1000) {
            this.mMaxVideoDurationInMs = 1000;
        }
    }

    public void registerProtocol() {
        super.registerProtocol();
        ModeCoordinatorImpl.getInstance().attachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(167, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(215, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(193, this);
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            getActivity().getImplFactory().initAdditional(getActivity(), 164, 174, 199, 212);
        } else {
            getActivity().getImplFactory().initAdditional(getActivity(), 164, 199, 212);
        }
    }

    /* access modifiers changed from: protected */
    public void resizeForPreviewAspectRatio() {
        if (((this.mCameraCapabilities.getSensorOrientation() - Util.getDisplayRotation(this.mActivity)) + MiuiSettings.ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT) % 180 == 0) {
            ModeProtocol.MainContentProtocol mainContentProtocol = this.mMainProtocol;
            CameraSize cameraSize = this.mVideoSize;
            mainContentProtocol.setPreviewAspectRatio(((float) cameraSize.height) / ((float) cameraSize.width));
            return;
        }
        ModeProtocol.MainContentProtocol mainContentProtocol2 = this.mMainProtocol;
        CameraSize cameraSize2 = this.mVideoSize;
        mainContentProtocol2.setPreviewAspectRatio(((float) cameraSize2.width) / ((float) cameraSize2.height));
    }

    public void resumePreview() {
        Log.v(VideoBase.TAG, "resumePreview");
        this.mPreviewing = true;
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.resumePreview();
        }
    }

    public void setAutoZoomMode(int i) {
        updatePreferenceInWorkThread(51);
    }

    public void setAutoZoomStartCapture(RectF rectF) {
        if (this.mCamera2Device != null && isAlive()) {
            this.mCamera2Device.setAutoZoomStartCapture(new float[]{rectF.left, rectF.top, rectF.width(), rectF.height()});
        }
    }

    public void setAutoZoomStopCapture(int i) {
        if (this.mCamera2Device != null && isAlive()) {
            this.mCamera2Device.setAutoZoomStopCapture(i);
        }
    }

    public void startAiLens() {
    }

    public void startAutoZoom() {
        this.isShowOrHideUltraWideHint.getAndSet(true);
        this.isAutoZoomTracking.getAndSet(false);
        this.mHandler.post(new Runnable() {
            public void run() {
                if (VideoModule.this.mAutoZoomViewProtocol != null) {
                    VideoModule.this.mAutoZoomViewProtocol.onAutoZoomStarted();
                }
            }
        });
        notifyAutoZoomStartUiHint();
    }

    public void startObjectTracking() {
        String str = VideoBase.TAG;
        Log.d(str, "startObjectTracking: started=" + this.mObjectTrackingStarted);
    }

    public void startPreview() {
        String str = VideoBase.TAG;
        Log.v(str, "startPreview: previewing=" + this.mPreviewing);
        checkDisplayOrientation();
        this.mPreviewing = true;
    }

    public void startTracking(RectF rectF) {
        if (this.mCamera2Device != null && isAlive()) {
            ModeProtocol.TopAlert topAlert = this.mTopAlert;
            if (topAlert != null) {
                topAlert.alertAiDetectTipHint(4, 0, 0);
            }
            notifyAutoZoomStopUiHint();
            this.mCamera2Device.setAutoZoomStopCapture(-1);
            this.mCamera2Device.setAutoZoomStartCapture(new float[]{rectF.left, rectF.top, rectF.width(), rectF.height()});
            this.mCamera2Device.setAutoZoomStartCapture(new float[]{0.0f, 0.0f, 0.0f, 0.0f});
            this.isAutoZoomTracking.getAndSet(true);
            CameraStatUtil.trackSelectObject(this.mMediaRecorderRecording);
        }
    }

    /* access modifiers changed from: protected */
    public void startVideoRecording() {
        String str = VideoBase.TAG;
        Log.v(str, "startVideoRecording: mode=" + this.mSpeed);
        if (isDeviceAlive()) {
            ScenarioTrackUtil.trackStartVideoRecordStart(this.mSpeed, isFrontCamera());
            this.mCurrentFileNumber = isCaptureIntent() ? -1 : 0;
            silenceSounds();
            if (!startRecorder()) {
                onStartRecorderFail();
                if (DataRepository.dataItemFeature().Wb() && CameraSettings.is4KHigherVideoQuality(this.mQuality)) {
                    ThermalHelper.notifyThermal4KRecordStop();
                    return;
                }
                return;
            }
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onStart();
            }
            updatePreferenceTrampoline(UpdateConstant.VIDEO_TYPES_RECORD);
            if (ModuleManager.isVideoNewSlowMotion()) {
                this.mCamera2Device.startHighSpeedRecording();
            } else {
                this.mCamera2Device.startRecording();
            }
            Log.v(VideoBase.TAG, "startVideoRecording process done");
            this.mTrackLostCount = 0;
            ScenarioTrackUtil.trackStartVideoRecordEnd();
            onStartRecorderSucceed();
        }
    }

    public void stopAutoZoom() {
        this.isShowOrHideUltraWideHint.getAndSet(false);
        this.isAutoZoomTracking.getAndSet(false);
        this.mHandler.post(new Runnable() {
            public void run() {
                if (VideoModule.this.mAutoZoomViewProtocol != null) {
                    VideoModule.this.mAutoZoomViewProtocol.onAutoZoomStopped();
                }
            }
        });
        notifyAutoZoomStopUiHint();
    }

    public void stopObjectTracking(boolean z) {
        String str = VideoBase.TAG;
        Log.d(str, "stopObjectTracking: started=" + this.mObjectTrackingStarted);
    }

    public void stopTracking(int i) {
        if (this.isAutoZoomTracking.get()) {
            this.isAutoZoomTracking.getAndSet(false);
            if (this.mCamera2Device != null && isAlive()) {
                this.mCamera2Device.setAutoZoomStopCapture(0);
                this.mCamera2Device.setAutoZoomStopCapture(-1);
            }
            this.mAutoZoomViewProtocol.onTrackingStopped(i);
        }
        notifyAutoZoomStartUiHint();
    }

    public void stopVideoRecording(boolean z, boolean z2) {
        long j;
        boolean z3 = z;
        String str = VideoBase.TAG;
        Log.v(str, "stopVideoRecording>>" + this.mMediaRecorderRecording + "|" + z3);
        if (this.mMediaRecorderRecording) {
            if (this.isAutoZoomTracking.get()) {
                stopTracking(0);
            }
            this.mMediaRecorderRecording = false;
            this.mMediaRecorderRecordingPaused = false;
            long currentTimeMillis = System.currentTimeMillis();
            if (isFPS960()) {
                if (FragmentTopAlert.HINT_DELAY_TIME - (SystemClock.uptimeMillis() - this.mRecordingStartTime) <= 100) {
                    this.mMediaRecorderPostProcessing = true;
                }
            }
            if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                int i = this.mModuleIndex;
                if (i != 172 && ((!CameraSettings.isMacroModeEnabled(i) || DataRepository.dataItemFeature().mb()) && !CameraSettings.isAutoZoomEnabled(this.mModuleIndex) && !CameraSettings.isSuperEISEnabled(this.mModuleIndex) && isBackCamera())) {
                    updateZoomRatioToggleButtonState(false);
                    if (!isUltraWideBackCamera()) {
                        setMinZoomRatio(0.6f);
                        setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                    } else if (!DataRepository.dataItemFeature().ad() || !CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                        setMinZoomRatio(0.6f);
                        setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio() * 0.6f);
                    } else {
                        setMinZoomRatio(HybridZoomingSystem.getMinimumStandaloneOpticalZoomRatio());
                        setMaxZoomRatio(HybridZoomingSystem.getMaximumStandaloneOpticalZoomRatio());
                    }
                }
            }
            enableCameraControls(false);
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null) {
                camera2Proxy.stopRecording(z3 ? null : this);
            }
            if (this.mCountDownTimer != null && CameraSettings.isVideoBokehOn()) {
                this.mCountDownTimer.cancel();
            }
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                if (this.mMediaRecorderPostProcessing) {
                    recordState.onPostSavingStart();
                } else {
                    recordState.onFinish();
                }
            }
            if (this.mCamera2Device == null || ModuleManager.isVideoNewSlowMotion()) {
                j = currentTimeMillis;
            } else {
                boolean isAutoZoomEnabled = CameraSettings.isAutoZoomEnabled(this.mModuleIndex);
                j = currentTimeMillis;
                CameraStatUtil.trackVideoRecorded(isFrontCamera(), getModuleIndex(), isAutoZoomEnabled, CameraSettings.isSuperEISEnabled(this.mModuleIndex), CameraSettings.isUltraWideConfigOpen(getModuleIndex()), this.mSpeed, this.mQuality, this.mCamera2Device.getFlashMode(), this.mFrameRate, this.mTimeBetweenTimeLapseFrameCaptureMs, this.mBeautyValues, this.mVideoRecordTime);
                CameraStatUtil.trackUltraWideVideoTaken();
                if (isAutoZoomEnabled) {
                    String str2 = VideoBase.TAG;
                    Log.v(str2, "track count is " + this.mTrackLostCount);
                    CameraStatUtil.trackLostCount(this.mTrackLostCount);
                }
            }
            this.mVideoRecordTime = 0;
            if (z3) {
                stopRecorder();
                startPreviewAfterRecord();
            } else {
                this.mPendingStopRecorder = true;
                this.mHandler.sendEmptyMessageDelayed(46, 300);
            }
            AutoLockManager.getInstance(this.mActivity).hibernateDelayed();
            exitSavePowerMode();
            String str3 = VideoBase.TAG;
            Log.v(str3, "stopVideoRecording<<time=" + (System.currentTimeMillis() - j));
        }
    }

    public boolean takeVideoSnapShoot() {
        if (this.mPaused || this.mActivity.isActivityPaused() || this.mSnapshotInProgress || !this.mMediaRecorderRecording || !isDeviceAlive()) {
            return false;
        }
        if (Storage.isLowStorageAtLastPoint()) {
            Log.w(VideoBase.TAG, "capture: low storage");
            stopVideoRecording(true, false);
            return false;
        } else if (this.mActivity.getImageSaver().isBusy()) {
            Log.w(VideoBase.TAG, "capture: ImageSaver is full");
            RotateTextToast.getInstance(this.mActivity).show(R.string.toast_saving, 0);
            return false;
        } else {
            this.mCamera2Device.setJpegRotation(Util.getJpegRotation(this.mBogusCameraId, this.mOrientation));
            Location currentLocation = LocationManager.instance().getCurrentLocation();
            this.mCamera2Device.setGpsLocation(currentLocation);
            setJpegQuality();
            updateFrontMirror();
            if (!b.vi()) {
                this.mActivity.getCameraScreenNail().animateCapture(getCameraRotation());
            }
            Log.v(VideoBase.TAG, "capture: start");
            this.mCamera2Device.captureVideoSnapshot(new JpegPictureCallback(currentLocation));
            this.mSnapshotInProgress = true;
            CameraStatUtil.trackVideoSnapshot(isFrontCamera());
            return true;
        }
    }

    public void unRegisterProtocol() {
        super.unRegisterProtocol();
        ModeCoordinatorImpl.getInstance().detachProtocol(215, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(167, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(193, this);
        getActivity().getImplFactory().detachAdditional();
    }

    /* access modifiers changed from: protected */
    public void updateRecordingTime() {
        String str;
        long j;
        double d2;
        super.updateRecordingTime();
        if (this.mMediaRecorderRecording && !isFPS960() && !CameraSettings.isVideoBokehOn()) {
            long uptimeMillis = SystemClock.uptimeMillis() - this.mRecordingStartTime;
            if (this.mMediaRecorderRecordingPaused) {
                uptimeMillis = this.mVideoRecordedDuration;
            }
            int i = this.mMaxVideoDurationInMs;
            boolean z = i != 0 && uptimeMillis >= ((long) (i - LinkSelectorConfiguration.MS_OF_ONE_MIN));
            long max = z ? Math.max(0, ((long) this.mMaxVideoDurationInMs) - uptimeMillis) + 999 : uptimeMillis;
            long j2 = 1000;
            if (isNormalMode()) {
                this.mVideoRecordTime = max / 1000;
                str = Util.millisecondToTimeString(max, false);
            } else {
                if (CameraSettings.VIDEO_SPEED_FAST.equals(this.mSpeed)) {
                    d2 = (double) this.mTimeBetweenTimeLapseFrameCaptureMs;
                    j = (long) d2;
                } else if (DataRepository.dataItemFeature().Sa()) {
                    d2 = 1000.0d / ((double) this.mFrameRate);
                    j = (long) ((getNormalVideoFrameRate() * 1000) / this.mFrameRate);
                } else {
                    j = 1000;
                    d2 = 0.0d;
                }
                if (d2 != 0.0d) {
                    str = Util.millisecondToTimeString(getSpeedRecordVideoLength(uptimeMillis, d2), CameraSettings.VIDEO_SPEED_FAST.equals(this.mSpeed));
                    if (str.equals(this.mRecordingTime)) {
                        j2 = (long) d2;
                    }
                } else {
                    str = Util.millisecondToTimeString(max, false);
                }
                j2 = j;
            }
            ModeProtocol.TopAlert topAlert = this.mTopAlert;
            if (topAlert != null) {
                topAlert.updateRecordingTime(str);
            }
            this.mRecordingTime = str;
            if (this.mRecordingTimeCountsDown != z) {
                this.mRecordingTimeCountsDown = z;
            }
            long j3 = 500;
            if (!this.mMediaRecorderRecordingPaused) {
                j3 = j2 - (uptimeMillis % j2);
            }
            this.mHandler.sendEmptyMessageDelayed(42, j3);
        }
    }
}
