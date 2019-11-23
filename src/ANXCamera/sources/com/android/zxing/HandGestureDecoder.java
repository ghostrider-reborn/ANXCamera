package com.android.zxing;

import android.graphics.Rect;
import android.graphics.YuvImage;
import com.android.camera.CameraSettings;
import com.android.camera.data.DataRepository;
import com.android.camera.handgesture.HandGesture;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.storage.Storage;
import io.reactivex.BackpressureStrategy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import miui.os.Environment;

public class HandGestureDecoder extends Decoder {
    private static boolean DEBUG = false;
    private static int DETECTION_FRAMES_PER_SECOND = 16;
    public static final String TAG = "HandGestureDecoder";
    private int mCameraId;
    private AtomicInteger mContinuousInterval = new AtomicInteger(0);
    private HandGesture mHandGesture;
    private boolean mTargetDetected = false;
    private int mTipShowInterval = DETECTION_FRAMES_PER_SECOND;
    private boolean mTipVisible = true;
    private boolean mTriggeringPhoto = false;

    HandGestureDecoder() {
        this.mSubjects = PublishSubject.create();
        this.mHandGesture = new HandGesture();
        this.mDecodeMaxCount = 5000;
        this.mDecodeAutoInterval = (long) (1000 / DETECTION_FRAMES_PER_SECOND);
        this.mEnable = true;
        this.mSubjects.toFlowable(BackpressureStrategy.LATEST).observeOn(Schedulers.computation()).map(new Function() {
            public final Object apply(Object obj) {
                return HandGestureDecoder.lambda$new$0(HandGestureDecoder.this, (PreviewImage) obj);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() {
            public final void accept(Object obj) {
                HandGestureDecoder.lambda$new$1(HandGestureDecoder.this, (Integer) obj);
            }
        });
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0092 A[SYNTHETIC, Splitter:B:23:0x0092] */
    private void dumpPreviewImage(PreviewImage previewImage) {
        FileOutputStream fileOutputStream = null;
        try {
            long timestamp = previewImage.getTimestamp();
            int width = previewImage.getWidth();
            int height = previewImage.getHeight();
            FileOutputStream fileOutputStream2 = new FileOutputStream(Environment.getExternalStorageDirectory() + "/DCIM/Camera/hand_" + String.valueOf(timestamp) + Storage.JPEG_SUFFIX);
            try {
                Log.d(TAG, "PreviewImage timestamp: [" + timestamp + "]");
                YuvImage yuvImage = new YuvImage(previewImage.getData(), 17, width, height, (int[]) null);
                yuvImage.compressToJpeg(new Rect(0, 0, width, height), 100, fileOutputStream2);
            } catch (IOException e) {
                e = e;
                fileOutputStream = fileOutputStream2;
            } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                }
                throw th;
            }
            try {
                fileOutputStream2.close();
            } catch (IOException e2) {
                Log.e(TAG, "Close stream failed!", e2);
            }
        } catch (IOException e3) {
            e = e3;
            try {
                Log.e(TAG, "Dump preview Image failed!", e);
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Throwable th2) {
                th = th2;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e4) {
                        Log.e(TAG, "Close stream failed!", e4);
                    }
                }
                throw th;
            }
        }
    }

    public static /* synthetic */ Integer lambda$new$0(HandGestureDecoder handGestureDecoder, PreviewImage previewImage) throws Exception {
        Log.d(TAG, "HandGestureDecoder: decode E");
        switch (previewImage.getPreviewStatus()) {
            case 1:
                try {
                    handGestureDecoder.mHandGesture.init(previewImage.getCameraId());
                    break;
                } catch (Exception e) {
                    break;
                }
            case 2:
                if (!(previewImage.getData() == null || previewImage.getData().length == 0)) {
                    if (DEBUG) {
                        handGestureDecoder.dumpPreviewImage(previewImage);
                    }
                    try {
                        return Integer.valueOf(handGestureDecoder.decode(previewImage));
                    } catch (Exception e2) {
                        break;
                    }
                }
            case 3:
                try {
                    handGestureDecoder.mHandGesture.unInit();
                    break;
                } catch (Exception e3) {
                    break;
                }
        }
        return -1;
    }

    public static /* synthetic */ void lambda$new$1(HandGestureDecoder handGestureDecoder, Integer num) throws Exception {
        Log.d(TAG, "Detected rect left = " + num + " " + handGestureDecoder.mTipShowInterval);
        if (num.intValue() >= 0) {
            handGestureDecoder.mTargetDetected = true;
        } else {
            handGestureDecoder.mTargetDetected = false;
            handGestureDecoder.mContinuousInterval.set(0);
        }
        if (!handGestureDecoder.mTriggeringPhoto) {
            Log.d(TAG, "Continuous interval: " + handGestureDecoder.mContinuousInterval.get());
            if (handGestureDecoder.mContinuousInterval.get() > 0) {
                handGestureDecoder.mContinuousInterval.getAndDecrement();
            } else if (handGestureDecoder.mTargetDetected) {
                Log.d(TAG, "Triggering countdown...");
                ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
                if (cameraAction != null && !cameraAction.isDoingAction()) {
                    cameraAction.onShutterButtonClick(100);
                    handGestureDecoder.mTriggeringPhoto = true;
                    handGestureDecoder.mContinuousInterval.set(3 * DETECTION_FRAMES_PER_SECOND);
                    DataRepository.dataItemRunning().setHandGestureRunning(!handGestureDecoder.mTriggeringPhoto);
                    handGestureDecoder.mTipVisible = false;
                    handGestureDecoder.mTipShowInterval = DETECTION_FRAMES_PER_SECOND;
                }
            }
            if (!handGestureDecoder.mTipVisible && handGestureDecoder.mTipShowInterval <= 0) {
                DataRepository.dataItemRunning().setHandGestureRunning(true);
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null && !topAlert.isExtraMenuShowing()) {
                    topAlert.reInitAlert(true);
                }
                handGestureDecoder.mTipVisible = true;
            }
            if (handGestureDecoder.mTipShowInterval > 0) {
                handGestureDecoder.mTipShowInterval--;
            }
        }
    }

    /* access modifiers changed from: protected */
    public int decode(PreviewImage previewImage) {
        int orientation = previewImage.getOrientation();
        if (orientation == -1) {
            orientation = 0;
        }
        return this.mHandGesture.detectGesture(previewImage.getData(), previewImage.getWidth(), previewImage.getHeight(), this.mCameraId == 1 ? 270 - orientation : (90 + orientation) % 360);
    }

    public void init(int i) {
        reset();
        this.mCameraId = i;
        this.mSubjects.onNext(new PreviewImage(1, i));
        DataRepository.dataItemRunning().setHandGestureRunning(true);
    }

    public boolean needPreviewFrame() {
        return CameraSettings.isHandGestureOpen() && super.needPreviewFrame();
    }

    public void onPreviewFrame(PreviewImage previewImage) {
        if (this.mSubjects != null) {
            this.mSubjects.onNext(previewImage);
        }
    }

    public void quit() {
        super.quit();
        this.mSubjects.onNext(new PreviewImage(3, this.mCameraId));
        this.mSubjects.onComplete();
    }

    public void reset() {
        Log.d(TAG, "Reset");
        this.mDecodingCount.set(0);
        this.mTriggeringPhoto = false;
    }

    public void startDecode() {
        this.mDecoding = true;
        this.mDecodingCount.set(0);
    }
}
