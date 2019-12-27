package com.android.camera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.Face;
import android.hardware.camera2.utils.SurfaceUtils;
import android.support.annotation.NonNull;
import android.util.Size;
import android.view.Surface;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.compat.MiCameraCompat;
import com.mi.config.b;
import com.xiaomi.camera.base.CameraDeviceUtil;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.imagecodec.ImagePool;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MiCamera2ShotParallelBurst extends MiCamera2ShotParallel<ParallelTaskData> {
    private static final String TAG = "ShotParallelBurst";
    /* access modifiers changed from: private */
    public int mAlgoType = 0;
    /* access modifiers changed from: private */
    public int mCompletedNum;
    /* access modifiers changed from: private */
    public boolean mFirstNum;
    private int[] mHdrCheckerEvValue;
    private final int mOperationMode;
    /* access modifiers changed from: private */
    public int mSequenceNum;
    private boolean mShouldDoMFNR;
    private boolean mShouldDoQcfaCapture;
    private boolean mShouldDoSR;
    private List<CaptureRequest> requests = new ArrayList();

    public MiCamera2ShotParallelBurst(MiCamera2 miCamera2, CaptureResult captureResult) {
        super(miCamera2);
        this.mPreviewCaptureResult = captureResult;
        this.mOperationMode = this.mMiCamera.getCapabilities().getOperatingMode();
    }

    private void applyAlgoParameter(CaptureRequest.Builder builder, int i, int i2) {
        if (i2 == 1) {
            applyHdrParameter(builder, i);
        } else if (i2 == 2) {
            applyClearShotParameter(builder);
        } else if (i2 == 3) {
            applySuperResolutionParameter(builder);
        }
        if (b.isMTKPlatform()) {
            MiCameraCompat.copyAiSceneFromCaptureResultToRequest(this.mPreviewCaptureResult, builder);
        } else {
            CaptureRequestBuilder.applySmoothTransition(this.mMiCamera.getCapabilities(), builder, false);
        }
    }

    private void applyClearShotParameter(CaptureRequest.Builder builder) {
        MiCameraCompat.applySwMfnrEnable(builder, this.mShouldDoMFNR);
        MiCameraCompat.applyMfnrEnable(builder, false);
        if (b.hi() || b.Kn) {
            CompatibilityUtils.setZsl(builder, true);
        }
    }

    private void applyHdrParameter(CaptureRequest.Builder builder, int i) {
        if (i <= this.mSequenceNum) {
            MiCameraCompat.applyHdrBracketMode(builder, (byte) 1);
            MiCameraCompat.applyMultiFrameInputNum(builder, this.mSequenceNum);
            if (b.isMTKPlatform()) {
                builder.set(CaptureRequest.CONTROL_AE_LOCK, true);
            }
            builder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, Integer.valueOf(this.mHdrCheckerEvValue[i]));
            if (!"tucana".equals(b.km) || this.mMiCamera.getSatMasterCameraId() != 2 || this.mHdrCheckerEvValue[i] < 0 || !isIn3OrMoreSatMode()) {
                MiCameraCompat.applyMfnrEnable(builder, false);
                return;
            }
            Log.d(TAG, "applyHdrParameter enable mfnr EV = " + this.mHdrCheckerEvValue[i]);
            MiCameraCompat.applyMfnrEnable(builder, true);
            return;
        }
        throw new RuntimeException("wrong request index " + i);
    }

    private void applySuperResolutionParameter(CaptureRequest.Builder builder) {
        MiCameraCompat.applyMultiFrameInputNum(builder, this.mSequenceNum);
        MiCameraCompat.applyMfnrEnable(builder, false);
        if (b.isMTKPlatform()) {
            builder.set(CaptureRequest.SCALER_CROP_REGION, this.mActiveArraySize);
            MiCameraCompat.applyPostProcessCropRegion(builder, HybridZoomingSystem.toCropRegion(this.mMiCamera.getZoomRatio(), this.mActiveArraySize));
        }
    }

    private int getGroupShotMaxImage() {
        Face[] faceArr = (Face[]) this.mPreviewCaptureResult.get(CaptureResult.STATISTICS_FACES);
        return Util.clamp((faceArr != null ? faceArr.length : 0) + 1, 2, 4);
    }

    private int getGroupShotNum() {
        if (Util.isMemoryRich(CameraAppImpl.getAndroidContext())) {
            return getGroupShotMaxImage();
        }
        Log.w(TAG, "getGroupShotNum: low memory");
        return 2;
    }

    private void prepareClearShot(int i) {
        if (b.hi()) {
            this.mSequenceNum = 10;
        } else {
            this.mSequenceNum = 5;
        }
    }

    private void prepareGroupShot() {
        this.mSequenceNum = getGroupShotNum();
    }

    private void prepareHdr() {
        Log.d(TAG, "prepareHdr: start");
        byte[] hdrCheckerValues = CaptureResultParser.getHdrCheckerValues(this.mPreviewCaptureResult);
        if (hdrCheckerValues != 0 && hdrCheckerValues.length >= 1) {
            int i = 0;
            if (hdrCheckerValues[0] != 0) {
                this.mSequenceNum = hdrCheckerValues[0];
                int i2 = this.mSequenceNum;
                if (i2 <= 6) {
                    this.mHdrCheckerEvValue = new int[i2];
                    if (i2 > 0 && i2 < 6) {
                        while (i < this.mSequenceNum) {
                            int i3 = i + 1;
                            this.mHdrCheckerEvValue[i] = hdrCheckerValues[i3 * 4];
                            Log.d(TAG, "prepareHdr: evValue[" + i + "]=" + this.mHdrCheckerEvValue[i]);
                            i = i3;
                        }
                        return;
                    }
                    return;
                }
                throw new RuntimeException("wrong sequenceNum " + this.mSequenceNum);
            }
        }
        this.mSequenceNum = 3;
        this.mHdrCheckerEvValue = new int[]{-6, 0, 6};
    }

    private void prepareSR() {
        this.mSequenceNum = DataRepository.dataItemFeature().Ka();
    }

    /* access modifiers changed from: protected */
    public CameraCaptureSession.CaptureCallback generateCaptureCallback() {
        return new CameraCaptureSession.CaptureCallback() {
            long captureTimestamp = -1;

            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                int unused = MiCamera2ShotParallelBurst.this.mCompletedNum = MiCamera2ShotParallelBurst.this.mCompletedNum + 1;
                Log.d(MiCamera2ShotParallelBurst.TAG, "onCaptureCompleted: " + MiCamera2ShotParallelBurst.this.mCompletedNum + "/" + MiCamera2ShotParallelBurst.this.mSequenceNum);
                AlgoConnector.getInstance().getLocalBinder().onCaptureCompleted(CameraDeviceUtil.getCustomCaptureResult(totalCaptureResult), MiCamera2ShotParallelBurst.this.mCompletedNum == 1);
                if (MiCamera2ShotParallelBurst.this.mSequenceNum == MiCamera2ShotParallelBurst.this.mCompletedNum) {
                    MiCamera2ShotParallelBurst miCamera2ShotParallelBurst = MiCamera2ShotParallelBurst.this;
                    miCamera2ShotParallelBurst.mMiCamera.onCapturePictureFinished(true, miCamera2ShotParallelBurst);
                }
                boolean isSREnable = CaptureResultParser.isSREnable(totalCaptureResult);
                Log.d(MiCamera2ShotParallelBurst.TAG, "onCaptureCompleted: isSREnabled = " + isSREnable);
                if (b.isMTKPlatform()) {
                    ImagePool.getInstance().trimPoolBuffer();
                }
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                Log.e(MiCamera2ShotParallelBurst.TAG, "onCaptureFailed: reason=" + captureFailure.getReason() + " timestamp=" + this.captureTimestamp);
                MiCamera2ShotParallelBurst miCamera2ShotParallelBurst = MiCamera2ShotParallelBurst.this;
                miCamera2ShotParallelBurst.mMiCamera.onCapturePictureFinished(false, miCamera2ShotParallelBurst);
                if (this.captureTimestamp != -1) {
                    AlgoConnector.getInstance().getLocalBinder().onCaptureFailed(this.captureTimestamp, captureFailure.getReason());
                }
            }

            public void onCaptureStarted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, long j, long j2) {
                Log.d(MiCamera2ShotParallelBurst.TAG, "onCaptureStarted: timestamp=" + j + " frameNumber=" + j2 + " isFirst=" + MiCamera2ShotParallelBurst.this.mFirstNum);
                super.onCaptureStarted(cameraCaptureSession, captureRequest, j, j2);
                if (MiCamera2ShotParallelBurst.this.mFirstNum) {
                    Camera2Proxy.PictureCallback pictureCallback = MiCamera2ShotParallelBurst.this.getPictureCallback();
                    if (pictureCallback != null) {
                        ParallelTaskData parallelTaskData = new ParallelTaskData(MiCamera2ShotParallelBurst.this.mMiCamera.getId(), j, MiCamera2ShotParallelBurst.this.mMiCamera.getCameraConfigs().getShotType(), MiCamera2ShotParallelBurst.this.mMiCamera.getCameraConfigs().getShotPath());
                        MiCamera2ShotParallelBurst miCamera2ShotParallelBurst = MiCamera2ShotParallelBurst.this;
                        ParallelTaskData onCaptureStart = pictureCallback.onCaptureStart(parallelTaskData, miCamera2ShotParallelBurst.mCapturedImageSize, miCamera2ShotParallelBurst.isQuickShotAnimation());
                        if (onCaptureStart != null) {
                            onCaptureStart.setAlgoType(MiCamera2ShotParallelBurst.this.mAlgoType);
                            onCaptureStart.setBurstNum(MiCamera2ShotParallelBurst.this.mSequenceNum);
                            this.captureTimestamp = j;
                            AlgoConnector.getInstance().getLocalBinder().onCaptureStarted(onCaptureStart);
                        } else {
                            Log.w(MiCamera2ShotParallelBurst.TAG, "onCaptureStarted: null task data");
                        }
                    } else {
                        Log.w(MiCamera2ShotParallelBurst.TAG, "onCaptureStarted: null picture callback");
                    }
                    boolean unused = MiCamera2ShotParallelBurst.this.mFirstNum = false;
                }
            }
        };
    }

    /* access modifiers changed from: protected */
    public CaptureRequest.Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        CaptureRequest.Builder createCaptureRequest = this.mMiCamera.getCameraDevice().createCaptureRequest(2);
        if (this.mMiCamera.isQcfaEnable()) {
            Surface wideRemoteSurface = this.mShouldDoQcfaCapture ? this.mMiCamera.getWideRemoteSurface() : this.mMiCamera.getQcfaRemoteSurface();
            Size surfaceSize = SurfaceUtils.getSurfaceSize(wideRemoteSurface);
            Log.d(TAG, String.format(Locale.ENGLISH, "[QCFA]add surface %s to capture request, size is: %s", new Object[]{wideRemoteSurface, surfaceSize}));
            createCaptureRequest.addTarget(wideRemoteSurface);
            if (b.hi() || b.Kn) {
                createCaptureRequest.addTarget(this.mMiCamera.getPreviewSurface());
            }
            configParallelSession(surfaceSize);
        } else {
            if (isIn3OrMoreSatMode()) {
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
            if (!b.isMTKPlatform() && this.mOperationMode != 36865 && (b.hi() || b.Kn || this.mOperationMode != 36867)) {
                Surface previewSurface = this.mMiCamera.getPreviewSurface();
                Log.d(TAG, String.format(Locale.ENGLISH, "add preview surface %s to capture request, size is: %s", new Object[]{previewSurface, SurfaceUtils.getSurfaceSize(previewSurface)}));
                createCaptureRequest.addTarget(previewSurface);
            }
        }
        createCaptureRequest.set(CaptureRequest.CONTROL_AE_MODE, 1);
        this.mMiCamera.applySettingsForCapture(createCaptureRequest, 3);
        if (!b.isMTKPlatform() || this.mAlgoType == 1) {
            Log.d(TAG, "disable ZSL for HDR");
            CompatibilityUtils.setZsl(createCaptureRequest, false);
        }
        return createCaptureRequest;
    }

    /* access modifiers changed from: protected */
    public void prepare() {
        this.mFirstNum = true;
        this.mShouldDoQcfaCapture = false;
        this.mShouldDoSR = this.mMiCamera.getCameraConfigs().isSuperResolutionEnabled();
        if (this.mMiCamera.getCameraConfigs().isHDREnabled()) {
            this.mAlgoType = 1;
            prepareHdr();
        } else if (CameraSettings.isGroupShotOn()) {
            this.mAlgoType = 5;
            prepareGroupShot();
        } else if (this.mShouldDoSR) {
            this.mAlgoType = 3;
            prepareSR();
        } else {
            Integer num = (Integer) this.mPreviewCaptureResult.get(CaptureResult.SENSOR_SENSITIVITY);
            Log.d(TAG, "prepare: iso = " + num);
            if (b.ei()) {
                this.mShouldDoMFNR = true;
            } else {
                this.mShouldDoMFNR = num != null && num.intValue() >= 800;
            }
            if (this.mShouldDoMFNR) {
                this.mAlgoType = 2;
                prepareClearShot(num.intValue());
            } else {
                this.mAlgoType = 0;
                this.mSequenceNum = 1;
            }
        }
        Log.d(TAG, String.format(Locale.ENGLISH, "prepare: algo=%d captureNum=%d doMFNR=%b doSR=%b", new Object[]{Integer.valueOf(this.mAlgoType), Integer.valueOf(this.mSequenceNum), Boolean.valueOf(this.mShouldDoMFNR), Boolean.valueOf(this.mShouldDoSR)}));
    }

    /* access modifiers changed from: protected */
    public void startSessionCapture() {
        try {
            CameraCaptureSession.CaptureCallback generateCaptureCallback = generateCaptureCallback();
            for (int i = 0; i < this.mSequenceNum; i++) {
                CaptureRequest.Builder generateRequestBuilder = generateRequestBuilder();
                if (b.isMTKPlatform() && this.mMiCamera.getCapabilities().getCameraId() == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
                    MiCameraCompat.copyFpcDataFromCaptureResultToRequest(this.mPreviewCaptureResult, generateRequestBuilder);
                }
                applyAlgoParameter(generateRequestBuilder, i, this.mAlgoType);
                this.requests.add(generateRequestBuilder.build());
            }
            this.mMiCamera.getCaptureSession().captureBurst(this.requests, generateCaptureCallback, this.mCameraHandler);
        } catch (CameraAccessException e2) {
            e2.printStackTrace();
            Log.e(TAG, "Cannot captureBurst");
            this.mMiCamera.notifyOnError(e2.getReason());
        } catch (IllegalStateException e3) {
            Log.e(TAG, "Failed to captureBurst, IllegalState", e3);
            this.mMiCamera.notifyOnError(256);
        } catch (IllegalArgumentException e4) {
            Log.e(TAG, "Failed to capture a still picture, IllegalArgument", e4);
            this.mMiCamera.notifyOnError(256);
        }
    }
}
