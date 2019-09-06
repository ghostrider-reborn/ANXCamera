package com.android.camera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureRequest.Builder;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.Surface;
import com.android.camera.CameraSettings;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera2.Camera2Proxy.PictureCallback;
import com.android.camera2.compat.MiCameraCompat;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.ParallelCallback;
import com.xiaomi.camera.core.ParallelTaskData;
import java.util.ArrayList;

public class MiCamera2ShotRawBurst extends MiCamera2Shot<ParallelTaskData> {
    public static final int BASE_EV_INDEX = 3;
    public static final int[] EV_LIST = {-24, -12, 0, 0, 0, 0, 0, 0};
    private static final String TAG = "SuperNightRawBurst";
    /* access modifiers changed from: private */
    public ParallelTaskData mCurrentParallelTaskData = null;
    /* access modifiers changed from: private */
    public final SuperNightReprocessHandler mReprocessHandler;

    public MiCamera2ShotRawBurst(MiCamera2 miCamera2, SuperNightReprocessHandler superNightReprocessHandler) {
        super(miCamera2);
        this.mReprocessHandler = superNightReprocessHandler;
    }

    /* access modifiers changed from: protected */
    public CaptureCallback generateCaptureCallback() {
        return new CaptureCallback() {
            public void onCaptureBufferLost(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull Surface surface, long j) {
                super.onCaptureBufferLost(cameraCaptureSession, captureRequest, surface, j);
                StringBuilder sb = new StringBuilder();
                sb.append("onCaptureBufferLost:<RAW>: frameNumber = ");
                sb.append(j);
                Log.e(MiCamera2ShotRawBurst.TAG, sb.toString());
                MiCamera2ShotRawBurst.this.mReprocessHandler.cancel();
                if (MiCamera2ShotRawBurst.this.mMiCamera.getSuperNight()) {
                    MiCamera2ShotRawBurst.this.mMiCamera.setAWBLock(false);
                }
                MiCamera2ShotRawBurst miCamera2ShotRawBurst = MiCamera2ShotRawBurst.this;
                miCamera2ShotRawBurst.mMiCamera.onCapturePictureFinished(false, miCamera2ShotRawBurst);
                MiCamera2ShotRawBurst.this.mMiCamera.setCaptureEnable(true);
            }

            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                StringBuilder sb = new StringBuilder();
                sb.append("onCaptureCompleted:<RAW>: ");
                sb.append(totalCaptureResult.getFrameNumber());
                String sb2 = sb.toString();
                String str = MiCamera2ShotRawBurst.TAG;
                Log.d(str, sb2);
                MiCamera2ShotRawBurst miCamera2ShotRawBurst = MiCamera2ShotRawBurst.this;
                if (miCamera2ShotRawBurst.mDeparted) {
                    Log.d(str, "onCaptureCompleted:<RAW>: ignored as has departed");
                } else {
                    miCamera2ShotRawBurst.mReprocessHandler.queueCaptureResult(totalCaptureResult);
                }
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                StringBuilder sb = new StringBuilder();
                sb.append("onCaptureFailed:<RAW>: reason = ");
                sb.append(captureFailure.getReason());
                Log.e(MiCamera2ShotRawBurst.TAG, sb.toString());
                MiCamera2ShotRawBurst.this.mReprocessHandler.cancel();
                if (MiCamera2ShotRawBurst.this.mMiCamera.getSuperNight()) {
                    MiCamera2ShotRawBurst.this.mMiCamera.setAWBLock(false);
                }
                MiCamera2ShotRawBurst miCamera2ShotRawBurst = MiCamera2ShotRawBurst.this;
                miCamera2ShotRawBurst.mMiCamera.onCapturePictureFinished(false, miCamera2ShotRawBurst);
                MiCamera2ShotRawBurst.this.mMiCamera.setCaptureEnable(true);
            }

            public void onCaptureStarted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, long j, long j2) {
                StringBuilder sb = new StringBuilder();
                sb.append("onCaptureStarted:<RAW>: ");
                sb.append(j2);
                String sb2 = sb.toString();
                String str = MiCamera2ShotRawBurst.TAG;
                Log.d(str, sb2);
                super.onCaptureStarted(cameraCaptureSession, captureRequest, j, j2);
                if (!CameraSettings.isSupportedZslShutter() && !CameraSettings.getPlayToneOnCaptureStart()) {
                    PictureCallback pictureCallback = MiCamera2ShotRawBurst.this.getPictureCallback();
                    if (pictureCallback != null) {
                        pictureCallback.onCaptureShutter(false);
                    } else {
                        Log.w(str, "onCaptureStarted:<RAW>: null picture callback");
                    }
                }
                if (0 == MiCamera2ShotRawBurst.this.mCurrentParallelTaskData.getTimestamp()) {
                    MiCamera2ShotRawBurst.this.mCurrentParallelTaskData.setTimestamp(j);
                }
            }
        };
    }

    /* access modifiers changed from: protected */
    public Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        Builder createCaptureRequest = this.mMiCamera.getCameraDevice().createCaptureRequest(2);
        createCaptureRequest.addTarget(this.mMiCamera.getRawImageReader().getSurface());
        createCaptureRequest.set(CaptureRequest.CONTROL_AF_MODE, (Integer) this.mMiCamera.getPreviewRequestBuilder().get(CaptureRequest.CONTROL_AF_MODE));
        this.mMiCamera.applySettingsForCapture(createCaptureRequest, 3);
        return createCaptureRequest;
    }

    /* access modifiers changed from: protected */
    public void notifyResultData(ParallelTaskData parallelTaskData) {
        ParallelCallback parallelCallback = getParallelCallback();
        String str = TAG;
        if (parallelCallback == null) {
            Log.w(str, "notifyResultData: null parallel callback");
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        this.mCurrentParallelTaskData.setPreviewThumbnailHash(this.mPreviewThumbnailHash);
        parallelCallback.onParallelProcessFinish(this.mCurrentParallelTaskData, null, null);
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        StringBuilder sb = new StringBuilder();
        sb.append("mJpegCallbackFinishTime = ");
        sb.append(currentTimeMillis2);
        sb.append("ms");
        Log.d(str, sb.toString());
    }

    /* access modifiers changed from: protected */
    public void onImageReceived(Image image, int i) {
        PictureCallback pictureCallback = getPictureCallback();
        String str = TAG;
        if (pictureCallback == null || this.mCurrentParallelTaskData == null || this.mDeparted) {
            StringBuilder sb = new StringBuilder();
            sb.append("something wrong happened when image received: callback = ");
            sb.append(pictureCallback);
            sb.append(" mCurrentParallelTaskData = ");
            sb.append(this.mCurrentParallelTaskData);
            Log.w(str, sb.toString());
            image.close();
            return;
        }
        if (i == 0) {
            Log.w(str, "onImageReceived:<JPEG>");
            if (0 == this.mCurrentParallelTaskData.getTimestamp()) {
                Log.w(str, "onImageReceived<JPEG>: image arrived first");
                this.mCurrentParallelTaskData.setTimestamp(image.getTimestamp());
            }
            byte[] firstPlane = Util.getFirstPlane(image);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("onImageReceived:<JPEG>: size = ");
            sb2.append(firstPlane == null ? 0 : firstPlane.length);
            sb2.append(", timeStamp = ");
            sb2.append(image.getTimestamp());
            Log.d(str, sb2.toString());
            image.close();
            this.mCurrentParallelTaskData.fillJpegData(firstPlane, i);
            if (this.mCurrentParallelTaskData.isJpegDataReady()) {
                pictureCallback.onPictureTakenFinished(true);
                notifyResultData(this.mCurrentParallelTaskData);
            }
        } else if (i == 3) {
            Log.w(str, "onImageReceived:<RAW>");
            this.mReprocessHandler.queueImage(image);
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Unknown image result type: ");
            sb3.append(i);
            throw new IllegalArgumentException(sb3.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void prepare() {
        this.mReprocessHandler.prepare(this);
        if (this.mMiCamera.getSuperNight()) {
            this.mMiCamera.setAWBLock(true);
        }
    }

    /* access modifiers changed from: protected */
    public void startSessionCapture() {
        String str = TAG;
        try {
            this.mCurrentParallelTaskData = generateParallelTaskData(0);
            if (this.mCurrentParallelTaskData == null) {
                Log.w(str, "startSessionCapture: null task data");
                return;
            }
            this.mCurrentParallelTaskData.setShot2Gallery(this.mMiCamera.getCameraConfigs().isShot2Gallery());
            CaptureCallback generateCaptureCallback = generateCaptureCallback();
            Builder generateRequestBuilder = generateRequestBuilder();
            ArrayList arrayList = new ArrayList();
            int i = 0;
            while (i < EV_LIST.length) {
                MiCameraCompat.applyRawReprocessHint(generateRequestBuilder, i == 3);
                generateRequestBuilder.set(CaptureRequest.CONTROL_AE_LOCK, Boolean.TRUE);
                generateRequestBuilder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, Integer.valueOf(EV_LIST[i]));
                arrayList.add(generateRequestBuilder.build());
                i++;
            }
            PerformanceTracker.trackPictureCapture(0);
            Log.d(str, "start capture burst");
            this.mMiCamera.getCaptureSession().captureBurst(arrayList, generateCaptureCallback, this.mCameraHandler);
        } catch (CameraAccessException e2) {
            e2.printStackTrace();
            Log.e(str, "Cannot capture a still picture");
            this.mMiCamera.notifyOnError(e2.getReason());
        } catch (IllegalStateException e3) {
            Log.e(str, "Failed to capture a still picture, IllegalState", e3);
            this.mMiCamera.notifyOnError(256);
        }
    }
}
