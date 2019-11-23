package com.android.camera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.android.camera.CameraSettings;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera2.Camera2Proxy;
import com.ss.android.ttve.common.TEDefine;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.ParallelCallback;
import com.xiaomi.camera.core.ParallelTaskData;

public class MiCamera2ShotStill extends MiCamera2Shot<ParallelTaskData> {
    /* access modifiers changed from: private */
    public static final String TAG = MiCamera2ShotStill.class.getSimpleName();
    /* access modifiers changed from: private */
    public TotalCaptureResult mCaptureResult;
    /* access modifiers changed from: private */
    public ParallelTaskData mCurrentParallelTaskData;
    private boolean mHasDepth;
    private boolean mIsIntent;
    private boolean mNeedCaptureResult;

    public MiCamera2ShotStill(MiCamera2 miCamera2) {
        super(miCamera2);
    }

    private void notifyResultData(ParallelTaskData parallelTaskData, @Nullable CaptureResult captureResult, @Nullable CameraCharacteristics cameraCharacteristics) {
        ParallelCallback parallelCallback = getParallelCallback();
        if (parallelCallback == null) {
            Log.w(TAG, "notifyResultData: null parallel callback");
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        this.mCurrentParallelTaskData.setPreviewThumbnailHash(this.mPreviewThumbnailHash);
        parallelCallback.onParallelProcessFinish(parallelTaskData, captureResult, cameraCharacteristics);
        String str = TAG;
        Log.d(str, "mJpegCallbackFinishTime = " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
    }

    /* access modifiers changed from: protected */
    public CameraCaptureSession.CaptureCallback generateCaptureCallback() {
        return new CameraCaptureSession.CaptureCallback() {
            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                String access$000 = MiCamera2ShotStill.TAG;
                Log.d(access$000, "onCaptureCompleted: " + totalCaptureResult.getFrameNumber());
                if (MiCamera2ShotStill.this.mMiCamera.getSuperNight()) {
                    MiCamera2ShotStill.this.mMiCamera.setAWBLock(false);
                }
                MiCamera2ShotStill.this.mMiCamera.onCapturePictureFinished(true, MiCamera2ShotStill.this);
                MiCamera2ShotStill.this.mMiCamera.setCaptureEnable(true);
                TotalCaptureResult unused = MiCamera2ShotStill.this.mCaptureResult = totalCaptureResult;
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                String access$000 = MiCamera2ShotStill.TAG;
                Log.e(access$000, "onCaptureFailed: reason=" + captureFailure.getReason());
                if (MiCamera2ShotStill.this.mMiCamera.getSuperNight()) {
                    MiCamera2ShotStill.this.mMiCamera.setAWBLock(false);
                }
                MiCamera2ShotStill.this.mMiCamera.onCapturePictureFinished(false, MiCamera2ShotStill.this);
                MiCamera2ShotStill.this.mMiCamera.setCaptureEnable(true);
            }

            public void onCaptureStarted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, long j, long j2) {
                super.onCaptureStarted(cameraCaptureSession, captureRequest, j, j2);
                if ((!CameraSettings.isSupportedZslShutter() || CameraSettings.isUltraPixelOn()) && !CameraSettings.getPlayToneOnCaptureStart()) {
                    Camera2Proxy.PictureCallback pictureCallback = MiCamera2ShotStill.this.getPictureCallback();
                    if (pictureCallback != null) {
                        pictureCallback.onCaptureShutter(false);
                    } else {
                        Log.w(MiCamera2ShotStill.TAG, "onCaptureStarted: null picture callback");
                    }
                }
                if (0 == MiCamera2ShotStill.this.mCurrentParallelTaskData.getTimestamp()) {
                    MiCamera2ShotStill.this.mCurrentParallelTaskData.setTimestamp(j);
                }
                String access$000 = MiCamera2ShotStill.TAG;
                Log.d(access$000, "onCaptureStarted: mCurrentParallelTaskData: " + MiCamera2ShotStill.this.mCurrentParallelTaskData.getTimestamp());
            }
        };
    }

    /* access modifiers changed from: protected */
    public CaptureRequest.Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        CaptureRequest.Builder createCaptureRequest = this.mMiCamera.getCameraDevice().createCaptureRequest(2);
        ImageReader photoImageReader = this.mMiCamera.getPhotoImageReader();
        createCaptureRequest.addTarget(photoImageReader.getSurface());
        String str = TAG;
        Log.d(str, "size=" + photoImageReader.getWidth() + "x" + photoImageReader.getHeight());
        if (!isInQcfaMode() || Camera2DataContainer.getInstance().getBokehFrontCameraId() == this.mMiCamera.getId()) {
            createCaptureRequest.addTarget(this.mMiCamera.getPreviewSurface());
        }
        if (this.mMiCamera.isConfigRawStream()) {
            createCaptureRequest.addTarget(this.mMiCamera.getRawSurface());
        }
        if (this.mHasDepth) {
            createCaptureRequest.addTarget(this.mMiCamera.getDepthImageReader().getSurface());
            createCaptureRequest.addTarget(this.mMiCamera.getPortraitRawImageReader().getSurface());
        }
        createCaptureRequest.set(CaptureRequest.CONTROL_AF_MODE, (Integer) this.mMiCamera.getPreviewRequestBuilder().get(CaptureRequest.CONTROL_AF_MODE));
        this.mMiCamera.applySettingsForCapture(createCaptureRequest, 3);
        if (this.mMiCamera.useLegacyFlashStrategy() && this.mMiCamera.isNeedFlashOn()) {
            this.mMiCamera.pausePreview();
        }
        return createCaptureRequest;
    }

    /* access modifiers changed from: protected */
    public long getTimeStamp() {
        if (this.mCurrentParallelTaskData == null) {
            return 0;
        }
        return this.mCurrentParallelTaskData.getTimestamp();
    }

    /* access modifiers changed from: protected */
    public void notifyResultData(ParallelTaskData parallelTaskData) {
        notifyResultData(parallelTaskData, (CaptureResult) null, (CameraCharacteristics) null);
    }

    /* access modifiers changed from: protected */
    public void onImageReceived(Image image, int i) {
        Camera2Proxy.PictureCallback pictureCallback = getPictureCallback();
        if (pictureCallback == null || this.mCurrentParallelTaskData == null) {
            String str = TAG;
            Log.w(str, "onImageReceived: something wrong happened when image received: callback = " + pictureCallback + " mCurrentParallelTaskData = " + this.mCurrentParallelTaskData);
            image.close();
            return;
        }
        if (0 == this.mCurrentParallelTaskData.getTimestamp()) {
            Log.w(TAG, "onImageReceived: image arrived first");
            this.mCurrentParallelTaskData.setTimestamp(image.getTimestamp());
        }
        String str2 = TAG;
        Log.d(str2, "onImageReceived mCurrentParallelTaskData timestamp:" + this.mCurrentParallelTaskData.getTimestamp() + " image timestamp:" + image.getTimestamp());
        byte[] firstPlane = Util.getFirstPlane(image);
        String str3 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onImageReceived: dataLen=");
        sb.append(firstPlane == null ? TEDefine.FACE_BEAUTY_NULL : Integer.valueOf(firstPlane.length));
        sb.append(" resultType = ");
        sb.append(i);
        sb.append(" timeStamp=");
        sb.append(image.getTimestamp());
        sb.append(" holder=");
        sb.append(hashCode());
        Log.d(str3, sb.toString());
        image.close();
        this.mCurrentParallelTaskData.fillJpegData(firstPlane, i);
        if (!(this.mNeedCaptureResult ? this.mCurrentParallelTaskData.isJpegDataReady() && this.mCaptureResult != null : this.mCurrentParallelTaskData.isJpegDataReady())) {
            return;
        }
        if (this.mIsIntent) {
            notifyResultData(this.mCurrentParallelTaskData);
            pictureCallback.onPictureTakenFinished(true);
            return;
        }
        pictureCallback.onPictureTakenFinished(true);
        notifyResultData(this.mCurrentParallelTaskData, this.mCaptureResult, this.mMiCamera.getCapabilities().getCameraCharacteristics());
    }

    /* access modifiers changed from: protected */
    public void prepare() {
        if (this.mMiCamera.getSuperNight()) {
            this.mMiCamera.setAWBLock(true);
        }
        switch (this.mMiCamera.getCameraConfigs().getShotType()) {
            case -3:
                this.mHasDepth = true;
                this.mIsIntent = true;
                return;
            case -2:
                this.mIsIntent = true;
                return;
            case 1:
                this.mNeedCaptureResult = true;
                return;
            case 2:
                this.mHasDepth = true;
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void startSessionCapture() {
        try {
            this.mCurrentParallelTaskData = generateParallelTaskData(0);
            if (this.mCurrentParallelTaskData == null) {
                Log.w(TAG, "startSessionCapture: null task data");
                return;
            }
            this.mCurrentParallelTaskData.setShot2Gallery(this.mMiCamera.getCameraConfigs().isShot2Gallery());
            CameraCaptureSession.CaptureCallback generateCaptureCallback = generateCaptureCallback();
            CaptureRequest.Builder generateRequestBuilder = generateRequestBuilder();
            PerformanceTracker.trackPictureCapture(0);
            this.mMiCamera.getCaptureSession().capture(generateRequestBuilder.build(), generateCaptureCallback, this.mCameraHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
            Log.e(TAG, "Cannot capture a still picture");
            this.mMiCamera.notifyOnError(e.getReason());
        } catch (IllegalStateException e2) {
            Log.e(TAG, "Failed to capture a still picture, IllegalState", e2);
            this.mMiCamera.notifyOnError(256);
        }
    }
}
