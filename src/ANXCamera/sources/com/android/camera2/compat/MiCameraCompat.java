package com.android.camera2.compat;

import android.graphics.Rect;
import android.hardware.camera2.CameraCharacteristics.Key;
import android.hardware.camera2.CaptureRequest.Builder;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.params.StreamConfiguration;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera2.vendortag.VendorTag;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene.ASDScene;
import com.mi.config.b;
import java.util.HashSet;

public class MiCameraCompat {
    private static final MiCameraCompatBaseImpl IMPL;

    static {
        if (b.Ai()) {
            IMPL = new MiCameraCompatQcomImpl();
        } else if (b.isMTKPlatform()) {
            IMPL = new MiCameraCompatMtkImpl();
        } else {
            IMPL = new MiCameraCompatBaseImpl();
        }
    }

    public static void applyASDEnable(Builder builder, boolean z) {
        IMPL.applyASDEnable(builder, z);
    }

    public static void applyASDScene(Builder builder, int i) {
        IMPL.applyASDScene(builder, i);
    }

    public static void applyAiScenePeriod(Builder builder, int i) {
        IMPL.applyAiScenePeriod(builder, i);
    }

    public static void applyAutoZoomMode(Builder builder, int i) {
        IMPL.applyAutoZoomMode(builder, i);
    }

    public static void applyAutoZoomScaleOffset(Builder builder, float f2) {
        IMPL.applyAutoZoomScaleOffset(builder, f2);
    }

    public static void applyBackSoftLight(Builder builder, byte b2) {
        IMPL.applyBackSoftLight(builder, b2);
    }

    public static void applyBackwardCaptureHint(Builder builder, byte b2) {
        IMPL.applyBackwardCaptureHint(builder, b2);
    }

    public static void applyBeautyParameter(Builder builder, HashSet<String> hashSet, BeautyValues beautyValues) {
        IMPL.applyBeautyParameter(builder, hashSet, beautyValues);
    }

    public static void applyBurstFps(Builder builder, int i) {
        IMPL.applyBurstFps(builder, i);
    }

    public static void applyBurstHint(Builder builder, int i) {
        IMPL.applyBurstHint(builder, i);
    }

    public static void applyCameraAi30Enable(Builder builder, boolean z) {
        IMPL.applyCameraAi30Enable(builder, z);
    }

    public static void applyContrast(Builder builder, int i) {
        IMPL.applyContrast(builder, i);
    }

    public static void applyCustomAWB(Builder builder, int i) {
        IMPL.applyCustomWB(builder, i);
    }

    public static void applyCustomWaterMark(Builder builder, String str) {
        IMPL.applyCustomWaterMark(builder, str);
    }

    public static void applyDepurpleEnable(Builder builder, boolean z) {
        IMPL.applyDepurpleEnable(builder, z);
    }

    public static void applyDeviceOrientation(Builder builder, int i) {
        IMPL.applyDeviceOrientation(builder, i);
    }

    public static void applyExposureMeteringMode(Builder builder, int i) {
        IMPL.applyExposureMeteringMode(builder, i);
    }

    public static void applyExposureTime(Builder builder, long j) {
        IMPL.applyExposureTime(builder, j);
    }

    public static void applyEyeLight(Builder builder, int i, int i2) {
        IMPL.applyEyeLight(builder, i, i2);
    }

    public static void applyFNumber(Builder builder, String str) {
        IMPL.applyFNumber(builder, str);
    }

    public static void applyFaceAgeAnalyzeEnable(Builder builder, boolean z) {
        IMPL.applyFaceAnalyzeAge(builder, z);
    }

    public static void applyFaceDetection(Builder builder, boolean z) {
        IMPL.applyFaceDetection(builder, z);
    }

    public static void applyFaceScoreEnable(Builder builder, boolean z) {
        IMPL.applyFaceScore(builder, z);
    }

    public static void applyFaceWaterMark(Builder builder, String str) {
        IMPL.applyFaceWaterMark(builder, str);
    }

    public static void applyFlawDetectEnable(Builder builder, boolean z) {
        IMPL.applyFlawDetectEnable(builder, z);
    }

    public static void applyFrontMirror(Builder builder, boolean z) {
        IMPL.applyFrontMirror(builder, z);
    }

    public static void applyHDR(Builder builder, boolean z) {
        IMPL.applyHDR(builder, z);
    }

    public static void applyHDRCheckerEnable(Builder builder, boolean z) {
        IMPL.applyHDRCheckerEnable(builder, z);
    }

    public static void applyHFRDeflicker(Builder builder, boolean z) {
        IMPL.applyHFRDeflicker(builder, z);
    }

    public static void applyHHT(Builder builder, boolean z) {
        IMPL.applyHHT(builder, z);
    }

    public static void applyHdrBracketMode(Builder builder, byte b2) {
        IMPL.applyHdrBracketMode(builder, b2);
    }

    public static void applyHighFpsVideoRecordingMode(Builder builder, boolean z) {
        IMPL.applyHighFpsVideoRecordingMode(builder, z);
    }

    public static void applyISO(Builder builder, int i) {
        IMPL.applyISO(builder, i);
    }

    public static void applyIsHfrPreview(Builder builder, boolean z) {
        IMPL.applyIsHfrPreview(builder, z);
    }

    public static void applyLensDirtyDetect(Builder builder, boolean z) {
        IMPL.applyLensDirtyDetect(builder, z);
    }

    public static void applyMacroMode(Builder builder, boolean z) {
        IMPL.applyMacroMode(builder, z);
    }

    public static void applyMfnrEnable(Builder builder, boolean z) {
        IMPL.applyMfnr(builder, z);
    }

    public static void applyMiBokehEnable(Builder builder, boolean z) {
        IMPL.applyMiBokeh(builder, z);
    }

    public static void applyMultiFrameInputNum(Builder builder, int i) {
        IMPL.applyMultiFrameInputNum(builder, i);
    }

    public static void applyNormalWideLDC(Builder builder, boolean z) {
        IMPL.applyNormalWideLDC(builder, z);
    }

    public static void applyOnTripodModeStatus(Builder builder, ASDScene[] aSDSceneArr) {
        IMPL.applyOnTripodModeStatus(builder, aSDSceneArr);
    }

    public static void applyParallelProcessEnable(Builder builder, boolean z) {
        IMPL.applyParallelProcessEnable(builder, z);
    }

    public static void applyParallelProcessPath(Builder builder, String str) {
        IMPL.applyParallelProcessPath(builder, str);
    }

    public static void applyPortraitLighting(Builder builder, int i) {
        IMPL.applyPortraitLighting(builder, i);
    }

    public static void applyPostProcessCropRegion(Builder builder, Rect rect) {
        IMPL.applyPostProcessCropRegion(builder, rect);
    }

    public static void applyQuickPreview(Builder builder, boolean z) {
        IMPL.applyQuickPreview(builder, z);
    }

    public static void applyRawReprocessHint(Builder builder, boolean z) {
        IMPL.applyRawReprocessHint(builder, z);
    }

    public static void applyRearBokehEnable(Builder builder, boolean z) {
        IMPL.applyRearBokeh(builder, z);
    }

    public static void applyRemosaicEnabled(Builder builder, boolean z) {
        IMPL.applyRemosaicEnabled(builder, z);
    }

    public static void applyRemosaicHint(Builder builder, boolean z) {
        IMPL.applyRemosaicHint(builder, z);
    }

    public static void applySatIsZooming(Builder builder, boolean z) {
        IMPL.applySatIsZooming(builder, z);
    }

    public static void applySaturation(Builder builder, int i) {
        IMPL.applySaturation(builder, i);
    }

    public static void applyScreenLightHint(Builder builder, byte b2) {
        IMPL.applyScreenLightHint(builder, b2);
    }

    public static void applySharpness(Builder builder, int i) {
        IMPL.applySharpness(builder, i);
    }

    public static void applySlowMotionVideoRecordingMode(Builder builder, int[] iArr) {
        IMPL.applySlowMotionVideoRecordingMode(builder, iArr);
    }

    public static void applySmoothTransition(Builder builder, boolean z) {
        IMPL.applySmoothTransition(builder, z);
    }

    public static void applySnapshotTorch(Builder builder, boolean z) {
        IMPL.applySnapshotTorch(builder, z);
    }

    public static void applyStFastZoomIn(Builder builder, boolean z) {
        IMPL.applyStFastZoomIn(builder, z);
    }

    public static void applySuperNightScene(Builder builder, boolean z) {
        IMPL.applySuperNightScene(builder, z);
    }

    public static void applySuperResolution(Builder builder, boolean z) {
        IMPL.applySuperResolution(builder, z);
    }

    public static void applySwMfnrEnable(Builder builder, boolean z) {
        IMPL.applySwMfnr(builder, z);
    }

    public static void applyTimeWaterMark(Builder builder, String str) {
        IMPL.applyTimeWaterMark(builder, str);
    }

    public static void applyUltraPixelPortrait(Builder builder, boolean z) {
        IMPL.applyUltraPixelPortrait(builder, z);
    }

    public static void applyUltraWideLDC(Builder builder, boolean z) {
        IMPL.applyUltraWideLDC(builder, z);
    }

    public static void applyVideoStreamState(Builder builder, boolean z) {
        IMPL.applyVideoStreamState(builder, z);
    }

    public static void applyWaterMarkAppliedList(Builder builder, String str) {
        IMPL.applyWaterMarkAppliedList(builder, str);
    }

    public static void applyZsd(Builder builder, boolean z) {
        IMPL.applyZsd(builder, z);
    }

    public static void applyZsl(Builder builder, boolean z) {
        IMPL.applyZsl(builder, z);
    }

    public static void copyAiSceneFromCaptureResultToRequest(CaptureResult captureResult, Builder builder) {
        IMPL.copyAiSceneFromCaptureResultToRequest(captureResult, builder);
    }

    public static void copyFpcDataFromCaptureResultToRequest(CaptureResult captureResult, Builder builder) {
        IMPL.copyFpcDataFromCaptureResultToRequest(captureResult, builder);
    }

    public static VendorTag<Key<StreamConfiguration[]>> getDefaultSteamConfigurationsTag() {
        return IMPL.getDefaultSteamConfigurationsTag();
    }
}
