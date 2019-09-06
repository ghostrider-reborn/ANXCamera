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
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera2.Camera2Proxy.PictureCallback;
import com.mi.config.b;

public class MiCamera2ShotBurst extends MiCamera2Shot<byte[]> {
    /* access modifiers changed from: private */
    public static final String TAG = "MiCamera2ShotBurst";
    private int mBurstNum;
    /* access modifiers changed from: private */
    public int mLatestSequenceId = 0;

    public MiCamera2ShotBurst(MiCamera2 miCamera2, int i) {
        super(miCamera2);
        this.mBurstNum = i;
    }

    /* access modifiers changed from: protected */
    public CaptureCallback generateCaptureCallback() {
        return new CaptureCallback() {
            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                MiCamera2ShotBurst.this.mMiCamera.updateFrameNumber(totalCaptureResult.getFrameNumber());
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                String access$000 = MiCamera2ShotBurst.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("onCaptureFailed: ");
                sb.append(captureFailure.getReason());
                Log.e(access$000, sb.toString());
            }

            public void onCaptureSequenceAborted(@NonNull CameraCaptureSession cameraCaptureSession, int i) {
                super.onCaptureSequenceAborted(cameraCaptureSession, i);
                String access$000 = MiCamera2ShotBurst.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("onCaptureSequenceAborted: ");
                sb.append(i);
                Log.w(access$000, sb.toString());
                if (MiCamera2ShotBurst.this.mLatestSequenceId == i) {
                    MiCamera2ShotBurst.this.mMiCamera.setAWBLock(false);
                    MiCamera2ShotBurst.this.mMiCamera.resumePreview();
                }
                PictureCallback pictureCallback = MiCamera2ShotBurst.this.getPictureCallback();
                if (pictureCallback != null) {
                    pictureCallback.onPictureTakenFinished(false);
                } else {
                    Log.w(MiCamera2ShotBurst.TAG, "onCaptureSequenceAborted: null picture callback");
                }
            }

            public void onCaptureSequenceCompleted(@NonNull CameraCaptureSession cameraCaptureSession, int i, long j) {
                String access$000 = MiCamera2ShotBurst.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("onCaptureSequenceCompleted: sequenceId=");
                sb.append(i);
                sb.append(" mLatestSequenceId = ");
                sb.append(MiCamera2ShotBurst.this.mLatestSequenceId);
                sb.append(" frameNumber=");
                sb.append(j);
                Log.d(access$000, sb.toString());
                if (MiCamera2ShotBurst.this.mLatestSequenceId == i) {
                    MiCamera2ShotBurst.this.mMiCamera.setAWBLock(false);
                    MiCamera2ShotBurst.this.mMiCamera.resumePreview();
                    PictureCallback pictureCallback = MiCamera2ShotBurst.this.getPictureCallback();
                    if (pictureCallback != null) {
                        pictureCallback.onPictureTakenFinished(true);
                    } else {
                        Log.w(MiCamera2ShotBurst.TAG, "onCaptureSequenceCompleted: null picture callback");
                    }
                }
            }
        };
    }

    /* access modifiers changed from: protected */
    public Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        Builder builder;
        if (b.isMTKPlatform()) {
            builder = this.mMiCamera.getCameraDevice().createCaptureRequest(1);
            builder.set(CaptureRequest.CONTROL_CAPTURE_INTENT, Integer.valueOf(1));
        } else {
            builder = this.mMiCamera.getCameraDevice().createCaptureRequest(2);
            builder.set(CaptureRequest.CONTROL_CAPTURE_INTENT, Integer.valueOf(2));
        }
        builder.addTarget(this.mMiCamera.getPhotoImageReader().getSurface());
        builder.addTarget(this.mMiCamera.getPreviewSurface());
        this.mMiCamera.applySettingsForCapture(builder, 4);
        return builder;
    }

    /* access modifiers changed from: protected */
    public void notifyResultData(byte[] bArr) {
        PictureCallback pictureCallback = getPictureCallback();
        if (pictureCallback != null) {
            pictureCallback.onPictureTaken(bArr);
        } else {
            Log.w(TAG, "notifyResultData: null picture callback");
        }
    }

    /* access modifiers changed from: protected */
    public void onImageReceived(Image image, int i) {
        PictureCallback pictureCallback = getPictureCallback();
        if (pictureCallback == null) {
            image.close();
        } else if (!pictureCallback.onPictureTakenImageConsumed(image)) {
            byte[] firstPlane = Util.getFirstPlane(image);
            image.close();
            notifyResultData(firstPlane);
        }
    }

    /* access modifiers changed from: protected */
    public void prepare() {
    }

    /* access modifiers changed from: protected */
    public void startSessionCapture() {
        Log.d(TAG, "startSessionCapture");
        this.mMiCamera.pausePreview();
        try {
            this.mLatestSequenceId = this.mMiCamera.getCaptureSession().setRepeatingRequest(generateRequestBuilder().build(), generateCaptureCallback(), this.mCameraHandler);
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("repeating sequenceId: ");
            sb.append(this.mLatestSequenceId);
            Log.d(str, sb.toString());
        } catch (CameraAccessException e2) {
            e2.printStackTrace();
            this.mMiCamera.notifyOnError(e2.getReason());
        } catch (IllegalStateException e3) {
            Log.e(TAG, "Failed to capture burst, IllegalState", e3);
            this.mMiCamera.notifyOnError(256);
        }
    }
}
