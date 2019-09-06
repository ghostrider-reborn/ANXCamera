package com.android.camera2;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureRequest.Builder;
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
import com.android.camera2.Camera2Proxy.PictureCallback;
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

    private void applyAlgoParameter(Builder builder) {
        MiCameraCompat.applySwMfnrEnable(builder, false);
        MiCameraCompat.applyHDR(builder, false);
        MiCameraCompat.applySuperResolution(builder, false);
        MiCameraCompat.applyMultiFrameInputNum(builder, 1);
        if (b.isMTKPlatform()) {
            if (this.mMiCamera.getCapabilities().getCameraId() == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
                MiCameraCompat.copyFpcDataFromCaptureResultToRequest(this.mPreviewCaptureResult, builder);
                Rect rect = (Rect) builder.get(CaptureRequest.SCALER_CROP_REGION);
                builder.set(CaptureRequest.SCALER_CROP_REGION, this.mActiveArraySize);
                MiCameraCompat.applyPostProcessCropRegion(builder, rect);
            }
            MiCameraCompat.copyAiSceneFromCaptureResultToRequest(this.mPreviewCaptureResult, builder);
        }
    }

    private boolean shouldDoQCFA() {
        boolean z = false;
        if (!this.mMiCamera.getCameraConfigs().isHDREnabled() && !this.mMiCamera.isBeautyOn()) {
            if (CameraSettings.isFrontCamera() && !b.Gn) {
                return false;
            }
            if (this.mMiCamera.getCapabilities().isRemosaicDetecedSupported()) {
                return CaptureResultParser.isRemosaicDetected(this.mPreviewCaptureResult);
            }
            Integer num = (Integer) this.mPreviewCaptureResult.get(CaptureResult.SENSOR_SENSITIVITY);
            StringBuilder sb = new StringBuilder();
            sb.append("shouldDoQCFA: iso = ");
            sb.append(num);
            Log.d(TAG, sb.toString());
            if (num != null && num.intValue() <= 200) {
                z = true;
            }
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public CaptureCallback generateCaptureCallback() {
        return new CaptureCallback() {
            long mCaptureTimestamp = -1;

            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                StringBuilder sb = new StringBuilder();
                sb.append("onCaptureCompleted: frameNumber=");
                sb.append(totalCaptureResult.getFrameNumber());
                String sb2 = sb.toString();
                String str = MiCamera2ShotParallelStill.TAG;
                Log.d(str, sb2);
                MiCamera2ShotParallelStill miCamera2ShotParallelStill = MiCamera2ShotParallelStill.this;
                miCamera2ShotParallelStill.mMiCamera.onCapturePictureFinished(true, miCamera2ShotParallelStill);
                Boolean bool = (Boolean) VendorTagHelper.getValue((CaptureResult) totalCaptureResult, CaptureResultVendorTags.IS_HDR_ENABLE);
                if (bool != null && bool.booleanValue()) {
                    Log.e(str, "onCaptureCompleted: HDR error");
                }
                Boolean bool2 = (Boolean) VendorTagHelper.getValue((CaptureResult) totalCaptureResult, CaptureResultVendorTags.IS_SR_ENABLE);
                if (bool2 != null && bool2.booleanValue()) {
                    Log.e(str, "onCaptureCompleted: SR error");
                }
                MiCamera2ShotParallelStill.this.mStillCaptureResult = totalCaptureResult;
                AlgoConnector.getInstance().getLocalBinder().onCaptureCompleted(CameraDeviceUtil.getCustomCaptureResult(MiCamera2ShotParallelStill.this.mStillCaptureResult), true);
                if (b.isMTKPlatform()) {
                    ImagePool.getInstance().trimPoolBuffer();
                }
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                StringBuilder sb = new StringBuilder();
                sb.append("onCaptureFailed: reason=");
                sb.append(captureFailure.getReason());
                Log.e(MiCamera2ShotParallelStill.TAG, sb.toString());
                MiCamera2ShotParallelStill miCamera2ShotParallelStill = MiCamera2ShotParallelStill.this;
                miCamera2ShotParallelStill.mMiCamera.onCapturePictureFinished(false, miCamera2ShotParallelStill);
                if (this.mCaptureTimestamp != -1) {
                    AlgoConnector.getInstance().getLocalBinder().onCaptureFailed(this.mCaptureTimestamp, captureFailure.getReason());
                }
            }

            public void onCaptureStarted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, long j, long j2) {
                StringBuilder sb = new StringBuilder();
                sb.append("onCaptureStarted: timestamp=");
                sb.append(j);
                sb.append(" frameNumber=");
                sb.append(j2);
                String sb2 = sb.toString();
                String str = MiCamera2ShotParallelStill.TAG;
                Log.d(str, sb2);
                super.onCaptureStarted(cameraCaptureSession, captureRequest, j, j2);
                PictureCallback pictureCallback = MiCamera2ShotParallelStill.this.getPictureCallback();
                if (pictureCallback != null) {
                    ParallelTaskData parallelTaskData = new ParallelTaskData(MiCamera2ShotParallelStill.this.mMiCamera.getId(), j, MiCamera2ShotParallelStill.this.mMiCamera.getCameraConfigs().getShotType(), MiCamera2ShotParallelStill.this.mMiCamera.getCameraConfigs().getShotPath());
                    MiCamera2ShotParallelStill miCamera2ShotParallelStill = MiCamera2ShotParallelStill.this;
                    ParallelTaskData onCaptureStart = pictureCallback.onCaptureStart(parallelTaskData, miCamera2ShotParallelStill.mCapturedImageSize, miCamera2ShotParallelStill.isQuickShotAnimation());
                    if (onCaptureStart != null) {
                        onCaptureStart.setAlgoType(MiCamera2ShotParallelStill.this.mAlgoType);
                        onCaptureStart.setBurstNum(1);
                        this.mCaptureTimestamp = j;
                        AlgoConnector.getInstance().getLocalBinder().onCaptureStarted(onCaptureStart);
                        return;
                    }
                    Log.w(str, "onCaptureStarted: null task data");
                    return;
                }
                Log.w(str, "onCaptureStarted: null picture callback");
            }
        };
    }

    /* access modifiers changed from: protected */
    public Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        Builder createCaptureRequest = this.mMiCamera.getCameraDevice().createCaptureRequest(2);
        boolean isQcfaEnable = this.mMiCamera.isQcfaEnable();
        String str = TAG;
        if (isQcfaEnable) {
            Surface wideRemoteSurface = (this.mMiCamera.alwaysUseRemosaicSize() || this.mShouldDoQcfaCapture) ? this.mMiCamera.getWideRemoteSurface() : this.mMiCamera.getQcfaRemoteSurface();
            Size surfaceSize = SurfaceUtils.getSurfaceSize(wideRemoteSurface);
            configParallelSession(surfaceSize);
            Log.d(str, String.format(Locale.ENGLISH, "[QCFA]add surface %s to capture request, size is: %s", new Object[]{wideRemoteSurface, surfaceSize}));
            createCaptureRequest.addTarget(wideRemoteSurface);
        } else {
            if (isIn3OrMoreSatMode()) {
                Surface mainCaptureSurface = getMainCaptureSurface();
                Size surfaceSize2 = SurfaceUtils.getSurfaceSize(mainCaptureSurface);
                Log.d(str, String.format(Locale.ENGLISH, "[SAT]add surface %s to capture request, size is: %s", new Object[]{mainCaptureSurface, surfaceSize2}));
                createCaptureRequest.addTarget(mainCaptureSurface);
                int i = 513;
                if (mainCaptureSurface == this.mMiCamera.getUltraWideRemoteSurface()) {
                    i = 3;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("[SAT]combinationMode: ");
                sb.append(i);
                Log.d(str, sb.toString());
                configParallelSession(surfaceSize2, i);
            } else {
                for (Surface surface : this.mMiCamera.getRemoteSurfaceList()) {
                    Log.d(str, String.format(Locale.ENGLISH, "add surface %s to capture request, size is: %s", new Object[]{surface, SurfaceUtils.getSurfaceSize(surface)}));
                    createCaptureRequest.addTarget(surface);
                }
                this.mCapturedImageSize = this.mMiCamera.getPictureSize();
            }
            if (!b.isMTKPlatform()) {
                int i2 = this.mOperationMode;
                if (!(i2 == 36865 || i2 == 36867)) {
                    Surface previewSurface = this.mMiCamera.getPreviewSurface();
                    Log.d(str, String.format(Locale.ENGLISH, "add preview surface %s to capture request, size is: %s", new Object[]{previewSurface, SurfaceUtils.getSurfaceSize(previewSurface)}));
                    createCaptureRequest.addTarget(previewSurface);
                }
            }
        }
        createCaptureRequest.set(CaptureRequest.CONTROL_AF_MODE, (Integer) this.mMiCamera.getPreviewRequestBuilder().get(CaptureRequest.CONTROL_AF_MODE));
        this.mMiCamera.applySettingsForCapture(createCaptureRequest, 3);
        if (this.mShouldDoQcfaCapture) {
            MiCameraCompat.applyMfnrEnable(createCaptureRequest, false);
        }
        if (b.isMTKPlatform() && this.mMiCamera.isQcfaEnable()) {
            Log.d(str, "enable remosaic capture hint");
            MiCameraCompat.applyRemosaicHint(createCaptureRequest, true);
            if (this.mShouldDoQcfaCapture) {
                Log.d(str, "enable remosaic capture request");
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
        StringBuilder sb = new StringBuilder();
        sb.append("prepare: qcfa = ");
        sb.append(this.mShouldDoQcfaCapture);
        Log.d(TAG, sb.toString());
        if (this.mShouldDoQcfaCapture) {
            this.mAlgoType = 6;
        }
    }

    /* access modifiers changed from: protected */
    public void startSessionCapture() {
        String str = TAG;
        PerformanceTracker.trackPictureCapture(0);
        try {
            CaptureCallback generateCaptureCallback = generateCaptureCallback();
            Builder generateRequestBuilder = generateRequestBuilder();
            applyAlgoParameter(generateRequestBuilder);
            this.mMiCamera.getCaptureSession().capture(generateRequestBuilder.build(), generateCaptureCallback, this.mCameraHandler);
        } catch (CameraAccessException e2) {
            e2.printStackTrace();
            Log.e(str, "Cannot capture a still picture");
            this.mMiCamera.notifyOnError(e2.getReason());
        } catch (IllegalStateException e3) {
            Log.e(str, "Failed to capture a still picture, IllegalState", e3);
            this.mMiCamera.notifyOnError(256);
        } catch (IllegalArgumentException e4) {
            Log.e(str, "Failed to capture a still picture, IllegalArgument", e4);
            this.mMiCamera.notifyOnError(256);
        }
    }
}
