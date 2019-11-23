package com.android.camera2;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.utils.SurfaceUtils;
import android.support.annotation.NonNull;
import android.util.Size;
import android.view.Surface;
import com.android.camera.CameraSettings;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.compat.MiCameraCompat;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.VendorTagHelper;
import com.mi.config.b;
import com.xiaomi.camera.base.CameraDeviceUtil;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.imagecodec.ImagePool;
import java.util.Locale;

@TargetApi(21)
public class MiCamera2ShotParallelStill extends MiCamera2ShotParallel<ParallelTaskData> {
    private static final String TAG = "ShotParallelStill";
    /* access modifiers changed from: private */
    public int mAlgoType;
    private final int mOperationMode;
    private boolean mShouldDoQcfaCapture;
    /* access modifiers changed from: private */
    public CaptureResult mStillCaptureResult;

    public MiCamera2ShotParallelStill(@NonNull MiCamera2 miCamera2, @NonNull CaptureResult captureResult) {
        super(miCamera2);
        this.mPreviewCaptureResult = captureResult;
        this.mOperationMode = miCamera2.getCapabilities().getOperatingMode();
    }

    private void applyAlgoParameter(CaptureRequest.Builder builder) {
        MiCameraCompat.applySwMfnrEnable(builder, false);
        MiCameraCompat.applyHDR(builder, false);
        MiCameraCompat.applySuperResolution(builder, false);
        MiCameraCompat.applyMultiFrameInputNum(builder, 1);
        if (b.isMTKPlatform()) {
            if (this.mMiCamera.getCapabilities().getCameraId() == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
                MiCameraCompat.copyFpcDataFromCaptureResultToRequest(this.mPreviewCaptureResult, builder);
                builder.set(CaptureRequest.SCALER_CROP_REGION, this.mActiveArraySize);
                MiCameraCompat.applyPostProcessCropRegion(builder, (Rect) builder.get(CaptureRequest.SCALER_CROP_REGION));
            }
            MiCameraCompat.copyAiSceneFromCaptureResultToRequest(this.mPreviewCaptureResult, builder);
        } else if (isIn3OrMoreSatMode()) {
            CaptureRequestBuilder.applySmoothTransition(builder, this.mMiCamera.getCapabilities(), false);
        }
    }

    private boolean shouldDoQCFA() {
        if (this.mMiCamera.getCameraConfigs().isHDREnabled() || this.mMiCamera.isBeautyOn()) {
            return false;
        }
        if (CameraSettings.isFrontCamera() && !b.sK) {
            return false;
        }
        if (this.mMiCamera.getCapabilities().isRemosaicDetecedSupported()) {
            return CaptureResultParser.isRemosaicDetected(this.mPreviewCaptureResult);
        }
        Integer num = (Integer) this.mPreviewCaptureResult.get(CaptureResult.SENSOR_SENSITIVITY);
        Log.d(TAG, "shouldDoQCFA: iso = " + num);
        return num != null && num.intValue() <= 200;
    }

    /* access modifiers changed from: protected */
    public CameraCaptureSession.CaptureCallback generateCaptureCallback() {
        return new CameraCaptureSession.CaptureCallback() {
            long mCaptureTimestamp = -1;

            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                Log.d(MiCamera2ShotParallelStill.TAG, "onCaptureCompleted: frameNumber=" + totalCaptureResult.getFrameNumber());
                MiCamera2ShotParallelStill.this.mMiCamera.onCapturePictureFinished(true, MiCamera2ShotParallelStill.this);
                Boolean bool = (Boolean) VendorTagHelper.getValue((CaptureResult) totalCaptureResult, CaptureResultVendorTags.IS_HDR_ENABLE);
                if (bool != null && bool.booleanValue()) {
                    Log.e(MiCamera2ShotParallelStill.TAG, "onCaptureCompleted: HDR error");
                }
                Boolean bool2 = (Boolean) VendorTagHelper.getValue((CaptureResult) totalCaptureResult, CaptureResultVendorTags.IS_SR_ENABLE);
                if (bool2 != null && bool2.booleanValue()) {
                    Log.e(MiCamera2ShotParallelStill.TAG, "onCaptureCompleted: SR error");
                }
                CaptureResult unused = MiCamera2ShotParallelStill.this.mStillCaptureResult = totalCaptureResult;
                AlgoConnector.getInstance().getLocalBinder().onCaptureCompleted(CameraDeviceUtil.getCustomCaptureResult(MiCamera2ShotParallelStill.this.mStillCaptureResult), true);
                ImagePool.getInstance().trimPoolBuffer();
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                Log.e(MiCamera2ShotParallelStill.TAG, "onCaptureFailed: reason=" + captureFailure.getReason());
                MiCamera2ShotParallelStill.this.mMiCamera.onCapturePictureFinished(false, MiCamera2ShotParallelStill.this);
                if (this.mCaptureTimestamp != -1) {
                    AlgoConnector.getInstance().getLocalBinder().onCaptureFailed(this.mCaptureTimestamp, captureFailure.getReason());
                }
            }

            public void onCaptureStarted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, long j, long j2) {
                Log.d(MiCamera2ShotParallelStill.TAG, "onCaptureStarted: timestamp=" + j + " frameNumber=" + j2);
                super.onCaptureStarted(cameraCaptureSession, captureRequest, j, j2);
                Camera2Proxy.PictureCallback pictureCallback = MiCamera2ShotParallelStill.this.getPictureCallback();
                if (pictureCallback != null) {
                    ParallelTaskData parallelTaskData = new ParallelTaskData(MiCamera2ShotParallelStill.this.mMiCamera.getId(), j, MiCamera2ShotParallelStill.this.mMiCamera.getCameraConfigs().getShotType(), MiCamera2ShotParallelStill.this.mMiCamera.getCameraConfigs().getShotPath());
                    ParallelTaskData onCaptureStart = pictureCallback.onCaptureStart(parallelTaskData, MiCamera2ShotParallelStill.this.mCapturedImageSize, MiCamera2ShotParallelStill.this.isQuickShotAnimation());
                    if (onCaptureStart != null) {
                        onCaptureStart.setAlgoType(MiCamera2ShotParallelStill.this.mAlgoType);
                        onCaptureStart.setBurstNum(1);
                        this.mCaptureTimestamp = j;
                        AlgoConnector.getInstance().getLocalBinder().onCaptureStarted(onCaptureStart);
                        return;
                    }
                    Log.w(MiCamera2ShotParallelStill.TAG, "onCaptureStarted: null task data");
                    return;
                }
                Log.w(MiCamera2ShotParallelStill.TAG, "onCaptureStarted: null picture callback");
            }
        };
    }

    /* access modifiers changed from: protected */
    public CaptureRequest.Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        CaptureRequest.Builder createCaptureRequest = this.mMiCamera.getCameraDevice().createCaptureRequest(2);
        if (this.mMiCamera.isQcfaEnable()) {
            Surface wideRemoteSurface = (this.mMiCamera.alwaysUseRemosaicSize() || this.mShouldDoQcfaCapture) ? this.mMiCamera.getWideRemoteSurface() : this.mMiCamera.getQcfaRemoteSurface();
            Size surfaceSize = SurfaceUtils.getSurfaceSize(wideRemoteSurface);
            configParallelSession(surfaceSize);
            Log.d(TAG, String.format(Locale.ENGLISH, "[QCFA]add surface %s to capture request, size is: %s", new Object[]{wideRemoteSurface, surfaceSize}));
            createCaptureRequest.addTarget(wideRemoteSurface);
        } else {
            if (isIn3OrMoreSatMode() || isInMultiSurfaceSatMode()) {
                Surface mainCaptureSurface = getMainCaptureSurface();
                Size surfaceSize2 = SurfaceUtils.getSurfaceSize(mainCaptureSurface);
                Log.d(TAG, String.format(Locale.ENGLISH, "[SAT]add surface %s to capture request, size is: %s", new Object[]{mainCaptureSurface, surfaceSize2}));
                createCaptureRequest.addTarget(mainCaptureSurface);
                int i = 513;
                if (mainCaptureSurface == this.mMiCamera.getUltraWideRemoteSurface()) {
                    i = 3;
                }
                Log.d(TAG, "[SAT]combinationMode: " + i);
                configParallelSession(surfaceSize2, i);
            } else {
                for (Surface next : this.mMiCamera.getRemoteSurfaceList()) {
                    Log.d(TAG, String.format(Locale.ENGLISH, "add surface %s to capture request, size is: %s", new Object[]{next, SurfaceUtils.getSurfaceSize(next)}));
                    createCaptureRequest.addTarget(next);
                }
                this.mCapturedImageSize = this.mMiCamera.getPictureSize();
            }
            if (!(b.isMTKPlatform() || this.mOperationMode == 36865 || this.mOperationMode == 36867)) {
                Surface previewSurface = this.mMiCamera.getPreviewSurface();
                Log.d(TAG, String.format(Locale.ENGLISH, "add preview surface %s to capture request, size is: %s", new Object[]{previewSurface, SurfaceUtils.getSurfaceSize(previewSurface)}));
                createCaptureRequest.addTarget(previewSurface);
            }
        }
        createCaptureRequest.set(CaptureRequest.CONTROL_AF_MODE, (Integer) this.mMiCamera.getPreviewRequestBuilder().get(CaptureRequest.CONTROL_AF_MODE));
        this.mMiCamera.applySettingsForCapture(createCaptureRequest, 3);
        if (this.mShouldDoQcfaCapture) {
            MiCameraCompat.applyMfnrEnable(createCaptureRequest, false);
        }
        if (b.isMTKPlatform() && this.mMiCamera.isQcfaEnable()) {
            Log.d(TAG, "enable remosaic capture hint");
            MiCameraCompat.applyRemosaicHint(createCaptureRequest, true);
            if (this.mShouldDoQcfaCapture) {
                Log.d(TAG, "enable remosaic capture request");
                MiCameraCompat.applyRemosaicEnabled(createCaptureRequest, true);
            }
        }
        CaptureRequestBuilder.applyFlawDetectEnable(this.mMiCamera.getCapabilities(), createCaptureRequest, this.mMiCamera.getCameraConfigs().isFlawDetectEnable());
        return createCaptureRequest;
    }

    /* access modifiers changed from: protected */
    public void prepare() {
        this.mAlgoType = 0;
        if (this.mMiCamera.isQcfaEnable()) {
            this.mShouldDoQcfaCapture = shouldDoQCFA();
        }
        Log.d(TAG, "prepare: qcfa = " + this.mShouldDoQcfaCapture);
        if (this.mShouldDoQcfaCapture) {
            this.mAlgoType = 6;
        }
    }

    /* access modifiers changed from: protected */
    public void startSessionCapture() {
        PerformanceTracker.trackPictureCapture(0);
        try {
            CameraCaptureSession.CaptureCallback generateCaptureCallback = generateCaptureCallback();
            CaptureRequest.Builder generateRequestBuilder = generateRequestBuilder();
            applyAlgoParameter(generateRequestBuilder);
            this.mMiCamera.getCaptureSession().capture(generateRequestBuilder.build(), generateCaptureCallback, this.mCameraHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
            Log.e(TAG, "Cannot capture a still picture");
            this.mMiCamera.notifyOnError(e.getReason());
        } catch (IllegalStateException e2) {
            Log.e(TAG, "Failed to capture a still picture, IllegalState", e2);
            this.mMiCamera.notifyOnError(256);
        } catch (IllegalArgumentException e3) {
            Log.e(TAG, "Failed to capture a still picture, IllegalArgument", e3);
            this.mMiCamera.notifyOnError(256);
        }
    }
}
