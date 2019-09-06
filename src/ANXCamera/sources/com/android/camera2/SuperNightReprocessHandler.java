package com.android.camera2;

import android.graphics.Rect;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureRequest.Builder;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.Surface;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.log.Log;
import com.arcsoft.supernight.SuperNightProcess;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public class SuperNightReprocessHandler extends Handler {
    private static final int MSG_CLEANUP = 19;
    private static final int MSG_DATA = 16;
    private static final int MSG_META = 17;
    private static final int MSG_PREPARE = 18;
    private static final String TAG = "SNReprocessHandler";
    private final int mBaseEvIndex = 3;
    /* access modifiers changed from: private */
    public MiCamera2ShotRawBurst mCamera2Shot;
    /* access modifiers changed from: private */
    public final MiCamera2 mCameraDevice;
    private final Handler mCaptureStateHandler;
    private final AtomicBoolean mIsCancelled;
    private final int mMaxInputImageCount = MiCamera2ShotRawBurst.EV_LIST.length;
    private final SuperNightProcess mSuperNightProcess;
    private final ArrayList<Image> mUnprocessedData = new ArrayList<>();
    private final ArrayList<TotalCaptureResult> mUnprocessedMeta = new ArrayList<>();

    public SuperNightReprocessHandler(Looper looper, MiCamera2 miCamera2) {
        super(looper);
        this.mCameraDevice = miCamera2;
        this.mCaptureStateHandler = this.mCameraDevice.getCameraHandler();
        this.mSuperNightProcess = new SuperNightProcess(this.mCameraDevice.getCapabilities().getActiveArraySize());
        this.mIsCancelled = new AtomicBoolean(false);
    }

    private void clearCache() {
        String str = TAG;
        Log.e(str, "clearCache: E");
        this.mUnprocessedMeta.clear();
        Iterator it = this.mUnprocessedData.iterator();
        while (it.hasNext()) {
            ((Image) it.next()).close();
        }
        this.mUnprocessedData.clear();
        Log.e(str, "clearCache: X");
    }

    private boolean convert(Rect rect) {
        int width = ((Image) this.mUnprocessedData.get(0)).getWidth();
        int height = ((Image) this.mUnprocessedData.get(0)).getHeight();
        int i = rect.left;
        int i2 = width - rect.right;
        if (i > i2) {
            rect.right = width - i;
        } else if (i < i2) {
            rect.left = i2;
        }
        int i3 = rect.top;
        int i4 = height - rect.bottom;
        if (i3 > i4) {
            rect.bottom = height - i3;
        } else if (i3 < i4) {
            rect.top = i4;
        }
        int width2 = rect.width() * height;
        int height2 = rect.height() * width;
        if (width2 > height2) {
            int i5 = (int) ((((float) height2) * 0.5f) / ((float) height));
            rect.left = rect.centerX() - i5;
            rect.right = rect.centerX() + i5;
        } else if (width2 < height2) {
            int i6 = (int) ((((float) width2) * 0.5f) / ((float) width));
            rect.top = rect.centerY() - i6;
            rect.bottom = rect.centerY() + i6;
        }
        Rect activeArraySize = this.mCameraDevice.getCapabilities().getActiveArraySize();
        float width3 = ((float) activeArraySize.width()) / ((float) width);
        float height3 = ((float) activeArraySize.height()) / ((float) height);
        rect.left = (int) (((float) rect.left) * width3);
        rect.top = (int) (((float) rect.top) * height3);
        int i7 = activeArraySize.right;
        int i8 = rect.left;
        rect.right = i7 - i8;
        rect.bottom = activeArraySize.bottom - rect.top;
        if (i8 % 2 != 0) {
            rect.left = i8 + 1;
        }
        int i9 = rect.top;
        if (i9 % 2 != 0) {
            rect.top = i9 + 1;
        }
        int i10 = rect.right;
        if (i10 % 2 != 0) {
            rect.right = i10 - 1;
        }
        int i11 = rect.bottom;
        if (i11 % 2 != 0) {
            rect.bottom = i11 - 1;
        }
        return rect.intersect(HybridZoomingSystem.toCropRegion(this.mCameraDevice.getZoomRatio(), activeArraySize));
    }

    private CaptureCallback generateReprocessCaptureCallback() {
        return new CaptureCallback() {
            public void onCaptureBufferLost(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull Surface surface, long j) {
                super.onCaptureBufferLost(cameraCaptureSession, captureRequest, surface, j);
                StringBuilder sb = new StringBuilder();
                sb.append("onCaptureBufferLost:<JPEG>: frameNumber = ");
                sb.append(j);
                Log.e(SuperNightReprocessHandler.TAG, sb.toString());
                if (SuperNightReprocessHandler.this.mCameraDevice.getSuperNight()) {
                    SuperNightReprocessHandler.this.mCameraDevice.setAWBLock(false);
                }
                SuperNightReprocessHandler.this.mCameraDevice.onCapturePictureFinished(false, SuperNightReprocessHandler.this.mCamera2Shot);
                SuperNightReprocessHandler.this.mCameraDevice.setCaptureEnable(true);
            }

            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                StringBuilder sb = new StringBuilder();
                sb.append("onCaptureCompleted:<JPEG>: ");
                sb.append(totalCaptureResult.getFrameNumber());
                Log.d(SuperNightReprocessHandler.TAG, sb.toString());
                if (SuperNightReprocessHandler.this.mCameraDevice.getSuperNight()) {
                    SuperNightReprocessHandler.this.mCameraDevice.setAWBLock(false);
                }
                SuperNightReprocessHandler.this.mCameraDevice.onCapturePictureFinished(true, SuperNightReprocessHandler.this.mCamera2Shot);
                SuperNightReprocessHandler.this.mCameraDevice.setCaptureEnable(true);
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                StringBuilder sb = new StringBuilder();
                sb.append("onCaptureFailed:<JPEG>: reason = ");
                sb.append(captureFailure.getReason());
                Log.e(SuperNightReprocessHandler.TAG, sb.toString());
                if (SuperNightReprocessHandler.this.mCameraDevice.getSuperNight()) {
                    SuperNightReprocessHandler.this.mCameraDevice.setAWBLock(false);
                }
                SuperNightReprocessHandler.this.mCameraDevice.onCapturePictureFinished(false, SuperNightReprocessHandler.this.mCamera2Shot);
                SuperNightReprocessHandler.this.mCameraDevice.setCaptureEnable(true);
            }

            public void onCaptureStarted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, long j, long j2) {
                StringBuilder sb = new StringBuilder();
                sb.append("onCaptureStarted:<JPEG>: ");
                sb.append(j2);
                Log.d(SuperNightReprocessHandler.TAG, sb.toString());
                super.onCaptureStarted(cameraCaptureSession, captureRequest, j, j2);
            }
        };
    }

    private void sendReprocessRequest() {
        int size = this.mUnprocessedData.size();
        int i = this.mMaxInputImageCount;
        String str = TAG;
        if (size == i && this.mUnprocessedMeta.size() == this.mMaxInputImageCount && this.mSuperNightProcess != null && !this.mIsCancelled.get()) {
            Collections.reverse(this.mUnprocessedData);
            Collections.reverse(this.mUnprocessedMeta);
            Log.d(str, "sendReprocessRequest:<SNP>: E");
            this.mSuperNightProcess.init(1793, ((Image) this.mUnprocessedData.get(0)).getWidth(), ((Image) this.mUnprocessedData.get(0)).getHeight(), ((Image) this.mUnprocessedData.get(0)).getPlanes()[0].getRowStride());
            Image dequeueInputImage = this.mCameraDevice.getRawImageWriter().dequeueInputImage();
            Rect rect = new Rect();
            this.mSuperNightProcess.addAllInputInfo(this.mUnprocessedData, this.mUnprocessedMeta, SuperNightProcess.ASVL_PAF_RAW12_GRBG_16B, dequeueInputImage, rect);
            this.mSuperNightProcess.unInit();
            Log.d(str, "sendReprocessRequest:<SNP>: X");
            if (this.mIsCancelled.get()) {
                clearCache();
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("sendReprocessRequest:<CROP>: E ");
            sb.append(rect);
            Log.d(str, sb.toString());
            convert(rect);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("sendReprocessRequest:<CROP>: X ");
            sb2.append(rect);
            Log.d(str, sb2.toString());
            TotalCaptureResult totalCaptureResult = (TotalCaptureResult) this.mUnprocessedMeta.get((this.mMaxInputImageCount - 1) - 3);
            try {
                Log.d(str, "sendReprocessRequest:<CAM>: E");
                this.mCameraDevice.getRawImageWriter().queueInputImage(dequeueInputImage);
                Builder createReprocessCaptureRequest = this.mCameraDevice.getCameraDevice().createReprocessCaptureRequest(totalCaptureResult);
                this.mCameraDevice.applySettingsForJpeg(createReprocessCaptureRequest);
                createReprocessCaptureRequest.addTarget(this.mCameraDevice.getPhotoImageReader().getSurface());
                createReprocessCaptureRequest.set(CaptureRequest.SCALER_CROP_REGION, rect);
                this.mCameraDevice.getCaptureSession().capture(createReprocessCaptureRequest.build(), generateReprocessCaptureCallback(), this.mCaptureStateHandler);
                Log.d(str, "sendReprocessRequest:<CAM>: X");
            } catch (Exception e2) {
                Log.d(str, "sendReprocessRequest:<CAM>", e2);
            } catch (Throwable th) {
                clearCache();
                throw th;
            }
            clearCache();
        } else if (this.mIsCancelled.get()) {
            clearCache();
            Log.d(str, "sendReprocessRequest:<CAM>: CANCELLED");
        }
    }

    public void cancel() {
        if (this.mSuperNightProcess != null) {
            String str = TAG;
            Log.d(str, "cancelSuperNight: E");
            this.mIsCancelled.set(true);
            this.mSuperNightProcess.cancelSuperNight();
            Log.d(str, "cancelSuperNight: X");
        }
        obtainMessage(19).sendToTarget();
    }

    public void handleMessage(Message message) {
        switch (message.what) {
            case 16:
                this.mUnprocessedData.add((Image) message.obj);
                sendReprocessRequest();
                return;
            case 17:
                this.mUnprocessedMeta.add((TotalCaptureResult) message.obj);
                sendReprocessRequest();
                return;
            case 18:
                this.mIsCancelled.set(false);
                clearCache();
                this.mCamera2Shot = (MiCamera2ShotRawBurst) message.obj;
                return;
            case 19:
                clearCache();
                return;
            default:
                return;
        }
    }

    public void prepare(MiCamera2ShotRawBurst miCamera2ShotRawBurst) {
        obtainMessage(18, miCamera2ShotRawBurst).sendToTarget();
    }

    public void queueCaptureResult(TotalCaptureResult totalCaptureResult) {
        obtainMessage(17, totalCaptureResult).sendToTarget();
    }

    public void queueImage(Image image) {
        obtainMessage(16, image).sendToTarget();
    }
}
