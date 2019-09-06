package com.android.camera2;

import android.graphics.Rect;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureRequest.Builder;
import android.hardware.camera2.params.MeteringRectangle;
import android.util.Range;
import android.util.Rational;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import com.android.camera2.compat.MiCameraCompat;
import com.android.camera2.vendortag.CaptureRequestVendorTags;
import com.mi.config.b;

public class CaptureRequestBuilder {
    private static final long MAX_REALTIME_EXPOSURE_TIME = 125000000;
    private static final String TAG = "CaptureRequestBuilder";

    static void applyAELock(Builder builder, boolean z) {
        if (builder != null) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applyAELock: ");
            sb.append(z);
            Log.d(str, sb.toString());
            builder.set(CaptureRequest.CONTROL_AE_LOCK, Boolean.valueOf(z));
        }
    }

    static void applyAERegions(Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            MeteringRectangle[] aERegions = cameraConfigs.getAERegions();
            if (aERegions != null) {
                builder.set(CaptureRequest.CONTROL_AE_REGIONS, aERegions);
            } else {
                builder.set(CaptureRequest.CONTROL_AE_REGIONS, MiCamera2.ZERO_WEIGHT_3A_REGION);
            }
        }
    }

    static void applyAFRegions(Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            MeteringRectangle[] aFRegions = cameraConfigs.getAFRegions();
            if (aFRegions != null) {
                builder.set(CaptureRequest.CONTROL_AF_REGIONS, aFRegions);
            } else {
                builder.set(CaptureRequest.CONTROL_AF_REGIONS, MiCamera2.ZERO_WEIGHT_3A_REGION);
            }
        }
    }

    public static void applyASDScene(CameraCapabilities cameraCapabilities, Builder builder, CameraConfigs cameraConfigs) {
        if (!(builder == null || cameraConfigs == null)) {
            if (!cameraCapabilities.isASDSceneSupported()) {
                Log.d(TAG, "applyASDScene(): unsupported");
                return;
            }
            int aSDScene = cameraConfigs.getASDScene();
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applyASDScene: ");
            sb.append(aSDScene);
            Log.d(str, sb.toString());
            MiCameraCompat.applyASDScene(builder, aSDScene);
        }
    }

    static void applyAWBLock(Builder builder, boolean z) {
        if (builder != null) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applyAWBLock: ");
            sb.append(z);
            Log.d(str, sb.toString());
            builder.set(CaptureRequest.CONTROL_AWB_LOCK, Boolean.valueOf(z));
        }
    }

    static void applyAWBMode(Builder builder, int i) {
        if (builder != null) {
            builder.set(CaptureRequest.CONTROL_AWB_MODE, Integer.valueOf(i));
        }
    }

    static void applyAiSceneDetectEnable(Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            MiCameraCompat.applyASDEnable(builder, cameraConfigs.isAiSceneDetectEnabled());
        }
    }

    static void applyAiSceneDetectPeriod(Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            MiCameraCompat.applyAiScenePeriod(builder, cameraConfigs.getAiSceneDetectPeriod());
        }
    }

    static void applyAntiBanding(Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            builder.set(CaptureRequest.CONTROL_AE_ANTIBANDING_MODE, Integer.valueOf(cameraConfigs.getAntiBanding()));
        }
    }

    static void applyAntiShake(Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null) {
            boolean isEISEnabled = cameraConfigs.isEISEnabled();
            boolean isOISEnabled = cameraConfigs.isOISEnabled();
            if (!isEISEnabled || !isOISEnabled || !Util.isDebugOsBuild()) {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("EIS: ");
                String str2 = "on";
                String str3 = "off";
                sb.append(isEISEnabled ? str2 : str3);
                Log.v(str, sb.toString());
                builder.set(CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE, Integer.valueOf(isEISEnabled ? 1 : 0));
                if (cameraCapabilities.isSupportOIS()) {
                    String str4 = TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("OIS: ");
                    if (isEISEnabled || !isOISEnabled) {
                        str2 = str3;
                    }
                    sb2.append(str2);
                    Log.v(str4, sb2.toString());
                    builder.set(CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE, Integer.valueOf((isEISEnabled || !isOISEnabled) ? 0 : 1));
                }
                return;
            }
            throw new RuntimeException("EIS&OIS are both on");
        }
    }

    static void applyAutoZoomMode(Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null) {
            MiCameraCompat.applyAutoZoomMode(builder, cameraConfigs.getAutoZoomMode());
        }
    }

    static void applyAutoZoomScaleOffset(Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null) {
            MiCameraCompat.applyAutoZoomScaleOffset(builder, cameraConfigs.getAutoZoomScaleOffset());
        }
    }

    public static void applyBackSoftLight(CameraCapabilities cameraCapabilities, Builder builder, boolean z) {
        if (builder != null) {
            if (!cameraCapabilities.isBackSoftLightSupported()) {
                Log.d(TAG, "applyBackSoftLight(): unsupported");
                return;
            }
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applyBackSoftLight(): ");
            sb.append(z);
            Log.d(str, sb.toString());
            MiCameraCompat.applyBackSoftLight(builder, z ? (byte) 1 : 0);
        }
    }

    public static void applyBackwardCaptureHint(CameraCapabilities cameraCapabilities, Builder builder, boolean z) {
        if (builder != null) {
            if (!cameraCapabilities.isBackwardCaptureSupported()) {
                Log.d(TAG, "applyBackwardCaptureHint(): unsupported");
                return;
            }
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applyBackwardCaptureHint(): ");
            sb.append(z);
            Log.d(str, sb.toString());
            MiCameraCompat.applyBackwardCaptureHint(builder, z ? (byte) 1 : 0);
        }
    }

    static void applyBeautyValues(Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportBeauty() && cameraConfigs.getBeautyValues() != null) {
            MiCameraCompat.applyBeautyParameter(builder, cameraCapabilities.getCaptureRequestVendorKeys(), cameraConfigs.getBeautyValues());
        }
    }

    static void applyCameraAi30Enable(Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportCameraAi30()) {
            MiCameraCompat.applyCameraAi30Enable(builder, cameraConfigs.isCameraAi30Enabled());
        }
    }

    static void applyColorEffect(Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            builder.set(CaptureRequest.CONTROL_EFFECT_MODE, Integer.valueOf(cameraConfigs.getColorEffect()));
        }
    }

    static void applyContrast(Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportContrast()) {
            MiCameraCompat.applyContrast(builder, cameraConfigs.getContrastLevel());
        }
    }

    static void applyCustomAWB(Builder builder, int i) {
        MiCameraCompat.applyCustomAWB(builder, i);
    }

    static void applyDepurpleEnable(Builder builder, CameraCapabilities cameraCapabilities, boolean z) {
        if (builder != null) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applyDepurpleEnable: isSupport = ");
            sb.append(cameraCapabilities.isSupportDepurple());
            sb.append(", enadled = ");
            sb.append(z);
            Log.d(str, sb.toString());
            if (cameraCapabilities.isSupportDepurple()) {
                MiCameraCompat.applyDepurpleEnable(builder, z);
            }
        }
    }

    static void applyDeviceOrientation(Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportDeviceOrientation()) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applyDeviceOrientation: ");
            sb.append(cameraConfigs.getDeviceOrientation());
            Log.d(str, sb.toString());
            MiCameraCompat.applyDeviceOrientation(builder, cameraConfigs.getDeviceOrientation());
        }
    }

    static void applyExposureCompensation(Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null) {
            int exposureCompensationIndex = cameraConfigs.getExposureCompensationIndex();
            if (b.Ij() && ModuleManager.isManualModule() && i == 1 && cameraConfigs.getISO() == 0 && cameraConfigs.getExposureTime() > MAX_REALTIME_EXPOSURE_TIME) {
                double log = Math.log((double) ((float) (((double) cameraConfigs.getExposureTime()) / 1.25E8d))) / Math.log(2.0d);
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("applyExposureCompensation: EV = ");
                sb.append(log);
                Log.d(str, sb.toString());
                Rational exposureCompensationRational = cameraCapabilities.getExposureCompensationRational();
                exposureCompensationIndex = Math.min((int) ((log * ((double) exposureCompensationRational.getDenominator())) / ((double) exposureCompensationRational.getNumerator())), cameraCapabilities.getMaxExposureCompensation());
            }
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("applyExposureCompensation: ");
            sb2.append(exposureCompensationIndex);
            Log.d(str2, sb2.toString());
            builder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, Integer.valueOf(exposureCompensationIndex));
        }
    }

    static void applyExposureMeteringMode(Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            MiCameraCompat.applyExposureMeteringMode(builder, cameraConfigs.getExposureMeteringMode());
        }
    }

    static void applyExposureTime(Builder builder, int i, CameraConfigs cameraConfigs) {
        if (builder != null) {
            long exposureTime = cameraConfigs.getExposureTime();
            if (b.Ij() && i == 1) {
                exposureTime = Math.min(exposureTime, MAX_REALTIME_EXPOSURE_TIME);
            }
            if (b.Ij() || i == 3) {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("applyExposureTime: ");
                sb.append(exposureTime);
                Log.d(str, sb.toString());
                MiCameraCompat.applyExposureTime(builder, exposureTime);
            }
        }
    }

    static void applyEyeLight(Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportEyeLight()) {
            int eyeLightType = cameraConfigs.getEyeLightType();
            if (eyeLightType < 0) {
                MiCameraCompat.applyEyeLight(builder, 0, 0);
            } else {
                MiCameraCompat.applyEyeLight(builder, eyeLightType, 100);
            }
        }
    }

    static void applyFNumber(Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null) {
            if (!cameraCapabilities.isSupportBokehAdjust()) {
                Log.d(TAG, "set f number on unsupported devices");
            } else if (cameraConfigs.getFNumber() != null) {
                MiCameraCompat.applyFNumber(builder, cameraConfigs.getFNumber());
            }
        }
    }

    static void applyFaceAgeAnalyze(Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportFaceAgeAnalyze()) {
            MiCameraCompat.applyFaceAgeAnalyzeEnable(builder, cameraConfigs.isFaceAgeAnalyzeEnabled());
        }
    }

    static void applyFaceDetection(Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            MiCameraCompat.applyFaceDetection(builder, cameraConfigs.isFaceDetectionEnabled());
        }
    }

    static void applyFaceScore(Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportFaceScore()) {
            MiCameraCompat.applyFaceScoreEnable(builder, cameraConfigs.isFaceScoreEnabled());
        }
    }

    public static void applyFlawDetectEnable(CameraCapabilities cameraCapabilities, Builder builder, boolean z) {
        if (builder != null && cameraCapabilities != null && cameraCapabilities.isTagDefined(CaptureRequestVendorTags.FLAW_DETECT_ENABLE.getName())) {
            MiCameraCompat.applyFlawDetectEnable(builder, z);
        }
    }

    static void applyFocusDistance(Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null && cameraConfigs.getFocusMode() == 0) {
            builder.set(CaptureRequest.LENS_FOCUS_DISTANCE, Float.valueOf(cameraConfigs.getFocusDistance()));
        }
    }

    static void applyFocusMode(Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            builder.set(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(cameraConfigs.getFocusMode()));
            applyAFRegions(builder, cameraConfigs);
            applyAERegions(builder, cameraConfigs);
        }
    }

    static void applyFpsRange(Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            Range previewFpsRange = cameraConfigs.getPreviewFpsRange();
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applyFpsRange: fpsRange = ");
            sb.append(previewFpsRange);
            Log.d(str, sb.toString());
            if (previewFpsRange != null) {
                builder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, previewFpsRange);
            }
        }
    }

    static void applyFrontMirror(Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportFrontMirror() && i == 3) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applyFrontMirror: ");
            sb.append(cameraConfigs.isFrontMirror());
            Log.d(str, sb.toString());
            MiCameraCompat.applyFrontMirror(builder, cameraConfigs.isFrontMirror());
        }
    }

    static void applyHDR(Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder == null || !cameraCapabilities.isSupportHdr()) {
            return;
        }
        if (i != 3) {
            MiCameraCompat.applyHDR(builder, false);
        } else {
            MiCameraCompat.applyHDR(builder, cameraConfigs.isHDREnabled());
        }
    }

    static void applyHDRCheckerEnable(Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && i == 1 && cameraCapabilities.isSupportAutoHdr()) {
            MiCameraCompat.applyHDRCheckerEnable(builder, cameraConfigs.isHDRCheckerEnabled());
        }
    }

    public static void applyHFRDeflicker(Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraConfigs != null && cameraCapabilities.isSupportHFRDeflicker()) {
            boolean isHFRDeflicker = cameraConfigs.isHFRDeflicker();
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applyHFRDeflicker: ");
            sb.append(isHFRDeflicker);
            Log.d(str, sb.toString());
            MiCameraCompat.applyHFRDeflicker(builder, isHFRDeflicker);
        }
    }

    static void applyHHT(Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && i == 3 && cameraCapabilities.isSupportHHT()) {
            MiCameraCompat.applyHHT(builder, cameraConfigs.isHHTEnabled());
        }
    }

    static void applyHwMfnr(Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder == null || !cameraCapabilities.isSupportMfnr()) {
            return;
        }
        if (i != 3) {
            MiCameraCompat.applyMfnrEnable(builder, false);
            return;
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("applyMfnrEnable: ");
        sb.append(cameraConfigs.isMfnrEnabled());
        Log.d(str, sb.toString());
        MiCameraCompat.applyMfnrEnable(builder, cameraConfigs.isMfnrEnabled());
    }

    static void applyIso(Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null) {
            int iso = cameraConfigs.getISO();
            if (b.Ij() && i == 1 && iso > 0 && cameraConfigs.getExposureTime() > MAX_REALTIME_EXPOSURE_TIME) {
                iso = Math.min((int) (((float) iso) * ((float) (((double) cameraConfigs.getExposureTime()) / 1.25E8d))), cameraCapabilities.getMaxIso());
            }
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applyIso: ");
            sb.append(iso);
            Log.d(str, sb.toString());
            MiCameraCompat.applyISO(builder, iso);
        }
    }

    static void applyLensDirtyDetect(Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportLensDirtyDetect()) {
            MiCameraCompat.applyLensDirtyDetect(builder, cameraConfigs.isLensDirtyDetectEnabled());
        }
    }

    public static void applyMacroMode(Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraConfigs != null && cameraCapabilities.isSupportMacroMode()) {
            boolean isMacroMode = cameraConfigs.isMacroMode();
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applyMacroMode: ");
            sb.append(isMacroMode);
            Log.d(str, sb.toString());
            MiCameraCompat.applyMacroMode(builder, isMacroMode);
        }
    }

    static void applyMiBokeh(Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportMiBokeh()) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applyMiBokeh: ");
            sb.append(cameraConfigs.isMiBokehEnabled());
            Log.d(str, sb.toString());
            MiCameraCompat.applyMiBokehEnable(builder, cameraConfigs.isMiBokehEnabled());
        }
    }

    private void applyNoiseReduction(Builder builder) {
        if (builder != null) {
            builder.set(CaptureRequest.NOISE_REDUCTION_MODE, Integer.valueOf(2));
        }
    }

    static void applyNormalWideLDC(Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportNormalWideLDC()) {
            boolean normalWideLDCEnabled = cameraConfigs.getNormalWideLDCEnabled();
            if (i == 4) {
                normalWideLDCEnabled = false;
            }
            MiCameraCompat.applyNormalWideLDC(builder, normalWideLDCEnabled);
        }
    }

    public static void applyOnTripodModeStatus(Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null && cameraConfigs != null && cameraConfigs.getOnTripodScenes() != null) {
            MiCameraCompat.applyOnTripodModeStatus(builder, cameraConfigs.getOnTripodScenes());
        }
    }

    static void applyPortraitLighting(Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null) {
            if ((i == 3 || DataRepository.dataItemFeature().wa()) && cameraCapabilities.isSupportPortraitLighting()) {
                MiCameraCompat.applyPortraitLighting(builder, cameraConfigs.getPortraitLightingPattern());
            }
        }
    }

    static void applyRearBokeh(Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportRearBokeh()) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applyRearBokeh: ");
            sb.append(cameraConfigs.isRearBokehEnabled());
            Log.d(str, sb.toString());
            MiCameraCompat.applyRearBokehEnable(builder, cameraConfigs.isRearBokehEnabled());
        }
    }

    public static void applySatIsZooming(Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null && cameraConfigs != null) {
            MiCameraCompat.applySatIsZooming(builder, cameraConfigs.isSatIsZooming());
        }
    }

    static void applySaturation(Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            MiCameraCompat.applySaturation(builder, cameraConfigs.getSaturationLevel());
        }
    }

    static void applySceneMode(Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            int sceneMode = cameraConfigs.getSceneMode();
            if (!"-1".equals(String.valueOf(sceneMode))) {
                builder.set(CaptureRequest.CONTROL_SCENE_MODE, Integer.valueOf(sceneMode));
                builder.set(CaptureRequest.CONTROL_MODE, Integer.valueOf(2));
            } else {
                builder.set(CaptureRequest.CONTROL_MODE, Integer.valueOf(1));
            }
        }
    }

    public static void applyScreenLightHint(CameraCapabilities cameraCapabilities, Builder builder, boolean z) {
        if (builder != null) {
            if (!cameraCapabilities.isScreenLightHintSupported()) {
                Log.d(TAG, "applyScreenLightHint(): unsupported");
                return;
            }
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applyScreenLightHint(): ");
            sb.append(z);
            Log.d(str, sb.toString());
            MiCameraCompat.applyScreenLightHint(builder, z ? (byte) 1 : 0);
        }
    }

    public static void applySessionParameters(Builder builder, CaptureSessionConfigurations captureSessionConfigurations) {
        if (builder != null && captureSessionConfigurations != null) {
            captureSessionConfigurations.apply(builder);
        }
    }

    static void applySharpness(Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            MiCameraCompat.applySharpness(builder, cameraConfigs.getSharpnessLevel());
        }
    }

    public static void applySmoothTransition(CameraCapabilities cameraCapabilities, Builder builder, boolean z) {
        if (builder != null && cameraCapabilities.isTagDefined(CaptureRequestVendorTags.ST_ENABLED.getName())) {
            MiCameraCompat.applySmoothTransition(builder, z);
        }
    }

    static void applySuperNightScene(Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && i == 3 && cameraCapabilities.isSupportSuperNight()) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applySuperNightScene: ");
            sb.append(cameraConfigs.isSuperNightEnabled());
            Log.d(str, sb.toString());
            MiCameraCompat.applySuperNightScene(builder, cameraConfigs.isSuperNightEnabled());
        }
    }

    static void applySuperResolution(Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs, boolean z) {
        if (builder != null && cameraCapabilities.isSupportSuperResolution()) {
            boolean isSuperResolutionEnabled = cameraConfigs.isSuperResolutionEnabled();
            String str = ", applyType = ";
            String str2 = "applySuperResolution: ";
            if (!b.isMTKPlatform()) {
                if (!z || ModuleManager.isManualModule()) {
                    isSuperResolutionEnabled &= i == 3;
                }
                String str3 = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(isSuperResolutionEnabled);
                sb.append(str);
                sb.append(i);
                Log.d(str3, sb.toString());
                MiCameraCompat.applySuperResolution(builder, isSuperResolutionEnabled);
            } else if (i != 3) {
                String str4 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("applySuperResolution: ignored for applyType(");
                sb2.append(i);
                sb2.append(")");
                Log.d(str4, sb2.toString());
            } else {
                String str5 = TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str2);
                sb3.append(isSuperResolutionEnabled);
                sb3.append(str);
                sb3.append(i);
                Log.d(str5, sb3.toString());
                MiCameraCompat.applySuperResolution(builder, isSuperResolutionEnabled);
            }
        }
    }

    static void applySwMfnr(Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder == null || !cameraCapabilities.isSupportSwMfnr()) {
            return;
        }
        if (i != 3) {
            MiCameraCompat.applySwMfnrEnable(builder, false);
            return;
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("applySwMfnrEnable: ");
        sb.append(cameraConfigs.isSwMfnrEnabled());
        Log.d(str, sb.toString());
        MiCameraCompat.applySwMfnrEnable(builder, cameraConfigs.isSwMfnrEnabled());
    }

    public static void applyUltraPixelPortrait(Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraConfigs != null && cameraCapabilities.isSupportUltraPixelPortrait()) {
            boolean isUltraPixelPortraitEnabled = cameraConfigs.isUltraPixelPortraitEnabled();
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applyUltraPixelPortrait: ");
            sb.append(isUltraPixelPortraitEnabled);
            Log.d(str, sb.toString());
            MiCameraCompat.applyUltraPixelPortrait(builder, isUltraPixelPortraitEnabled);
        }
    }

    static void applyUltraWideLDC(Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportUltraWideLDC()) {
            MiCameraCompat.applyUltraWideLDC(builder, cameraConfigs.isUltraWideLDCEnabled());
        }
    }

    static void applyVideoFlash(Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            if (2 == cameraConfigs.getFlashMode()) {
                builder.set(CaptureRequest.FLASH_MODE, Integer.valueOf(2));
            } else {
                builder.set(CaptureRequest.FLASH_MODE, Integer.valueOf(0));
            }
        }
    }

    static void applyVideoFpsRange(Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            Range videoFpsRange = cameraConfigs.getVideoFpsRange();
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applyVideoFpsRange: fpsRange = ");
            sb.append(videoFpsRange);
            Log.d(str, sb.toString());
            if (videoFpsRange != null) {
                builder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, videoFpsRange);
            }
        }
    }

    static void applyWaterMark(Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder == null || !cameraCapabilities.isSupportWatermark()) {
            return;
        }
        if (i != 3) {
            if (i == 4) {
                MiCameraCompat.applyWaterMarkAppliedList(builder, "");
            }
        } else if (!DataRepository.dataItemFeature().Uc()) {
            String join = Util.join(",", cameraConfigs.getWaterMarkAppliedList());
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applyWaterMark appliedList:");
            sb.append(join);
            Log.d(str, sb.toString());
            MiCameraCompat.applyWaterMarkAppliedList(builder, join);
            if (DataRepository.dataItemFeature().hd() && cameraCapabilities.isSupportCustomWatermark() && join.contains("device")) {
                String watermarkFileName = Util.getWatermarkFileName();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Util.WATERMARK_STORAGE_DIRECTORY);
                sb2.append(watermarkFileName);
                MiCameraCompat.applyCustomWaterMark(builder, sb2.toString());
            }
            if (join.contains("watermark")) {
                MiCameraCompat.applyTimeWaterMark(builder, cameraConfigs.getTimeWaterMarkValue());
            }
            if (join.contains("beautify")) {
                MiCameraCompat.applyFaceWaterMark(builder, cameraConfigs.getFaceWaterMarkFormat());
            }
        }
    }

    static void applyZoomRatio(Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null) {
            float zoomRatio = cameraConfigs != null ? cameraConfigs.getZoomRatio() : 1.0f;
            Rect activeArraySize = cameraCapabilities.getActiveArraySize();
            Rect cropRegion = HybridZoomingSystem.toCropRegion(zoomRatio, activeArraySize);
            builder.set(CaptureRequest.SCALER_CROP_REGION, cropRegion);
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applyZoomRatio(): cameraId = ");
            sb.append(cameraCapabilities.getCameraId());
            sb.append(", zoomRatio = ");
            sb.append(zoomRatio);
            sb.append(", activeArraySize = ");
            sb.append(activeArraySize);
            sb.append(", cropRegion = ");
            sb.append(cropRegion);
            Log.v(str, sb.toString());
        }
    }

    static void applyZsl(Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            boolean isZslEnabled = cameraConfigs.isZslEnabled();
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("applyZsl(): ");
            sb.append(isZslEnabled);
            Log.v(str, sb.toString());
            MiCameraCompat.applyZsl(builder, isZslEnabled);
        }
    }
}
