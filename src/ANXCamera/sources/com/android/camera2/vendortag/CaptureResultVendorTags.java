package com.android.camera2.vendortag;

import android.hardware.camera2.CaptureResult;
import android.util.Log;
import com.android.camera2.vendortag.struct.AECFrameControl;
import com.android.camera2.vendortag.struct.AFFrameControl;
import com.android.camera2.vendortag.struct.AWBFrameControl;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import com.mi.config.b;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class CaptureResultVendorTags {
    public static final VendorTag<CaptureResult.Key<AECFrameControl>> AEC_FRAME_CONTROL = create($$Lambda$CaptureResultVendorTags$rjrG3rvuVtblTn7kHdCKB_znVro.INSTANCE, AECFrameControl.class);
    public static final VendorTag<CaptureResult.Key<Float>> AEC_LUX = create($$Lambda$CaptureResultVendorTags$wgXBHWXFRYLp3Sxk3SVkJlowyE.INSTANCE, Float.class);
    public static final VendorTag<CaptureResult.Key<AFFrameControl>> AF_FRAME_CONTROL = create($$Lambda$CaptureResultVendorTags$hth0M04ez8M4hUoGsbGapRJ6zts.INSTANCE, AFFrameControl.class);
    public static final VendorTag<CaptureResult.Key<Byte>> AI_HDR_DETECTED = create($$Lambda$CaptureResultVendorTags$tG6Wdi2lcwNBvwkFp8iRfh0KYvI.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<Integer>> AI_SCENE_DETECTED = create($$Lambda$CaptureResultVendorTags$WmSnBKbYus1Wr01jZnE7mCbGpo.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Byte>> AI_SCENE_ENABLE = create($$Lambda$CaptureResultVendorTags$OdCuZrd4_h7cv6y69gMdpQeXZhE.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<int[]>> AUTOZOOM_ACTIVE_OBJECTS = create($$Lambda$CaptureResultVendorTags$gkHbFAOrhasb4HI9rHH7hhSLLQ.INSTANCE, int[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_BOUNDS = create($$Lambda$CaptureResultVendorTags$obbPMBHb_GBdD6Y0MtMtPjA_WsE.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_DELAYED_TARGET_BOUNDS_STABILIZED = create($$Lambda$CaptureResultVendorTags$O6wZjn_i1vbVBVFsfP_KvcUM_Jg.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_DELAYED_TARGET_BOUNDS_ZOOMED = create($$Lambda$CaptureResultVendorTags$rUXpgGl87MdgLgVApjzX8B_XDnk.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_OBJECT_BOUNDS_STABILIZED = create($$Lambda$CaptureResultVendorTags$WbrtGPoqpxhIhhawjqNRIuYAVs.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_OBJECT_BOUNDS_ZOOMED = create($$Lambda$CaptureResultVendorTags$kjs1PdFVdzaSEM6TmZMxgx9948.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<int[]>> AUTOZOOM_PAUSED_OBJECTS = create($$Lambda$CaptureResultVendorTags$m2HVGXr8zKsKR5Qroz_OFeZ8L8.INSTANCE, int[].class);
    public static final VendorTag<CaptureResult.Key<int[]>> AUTOZOOM_SELECTED_OBJECTS = create($$Lambda$CaptureResultVendorTags$CxYVF2xg6PT6jKjBTtLpr1EpfjU.INSTANCE, int[].class);
    public static final VendorTag<CaptureResult.Key<Integer>> AUTOZOOM_STATUS = create($$Lambda$CaptureResultVendorTags$xXUbfJUUbFQPqnErWIU9EaJxRKM.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_TARGET_BOUNDS_STABILIZED = create($$Lambda$CaptureResultVendorTags$EN2LOEiGDSAZgW0iI7UkCY4Q3c.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_TARGET_BOUNDS_ZOOMED = create($$Lambda$CaptureResultVendorTags$3R_nG2QKafRX8jVLYTVEsegkvfg.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<AWBFrameControl>> AWB_FRAME_CONTROL = create($$Lambda$CaptureResultVendorTags$7ZqrdUEbyiNHXk2lH5HYalAtso.INSTANCE, AWBFrameControl.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_BLUSHER = create($$Lambda$CaptureResultVendorTags$V3OEZ7LJdxUShSpOwNRXfOjTBXc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_BODY_SLIM = create($$Lambda$CaptureResultVendorTags$NFyFSdb9tWEwXtJduF_2VsXOI38.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_BODY_SLIM_COUNT = create($$Lambda$CaptureResultVendorTags$34eF1roKS4r7WP1SW4_IaRLxYXM.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_CHIN = create($$Lambda$CaptureResultVendorTags$5tKT0IisicMATZT4c6qX0QZ9QOc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_ENLARGE_EYE = create($$Lambda$CaptureResultVendorTags$fG9Zh3e_2FW_ZyNEkBunBVpmHAs.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_EYEBROW_DYE = create($$Lambda$CaptureResultVendorTags$c6uapKCJDTRgaVA8CxculdIcf24.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_HAIRLINE = create($$Lambda$CaptureResultVendorTags$OuDdYNL5o2nKYD_avIyoEIwM7M.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_HEAD_SLIM = create($$Lambda$CaptureResultVendorTags$Ej6br5bkLZZNkVjWTjIfda0d3qo.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_JELLY_LIPS = create($$Lambda$CaptureResultVendorTags$dZED62aBR9DC3ozPodEvXjgdogw.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_LEG_SLIM = create($$Lambda$CaptureResultVendorTags$IDGVJujY810IDtteaDmvKM7Pmho.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<String>> BEAUTY_LEVEL = create($$Lambda$CaptureResultVendorTags$epwX1VZVjFjsaGj2RvZM3mNJcM.INSTANCE, String.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_LIPS = create($$Lambda$CaptureResultVendorTags$Pwi4CJC_LlaqCQdr5h0bo4Wkg0.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_NECK = create($$Lambda$CaptureResultVendorTags$Hc2nw1ixOMsG1BQtqB8nhy4IUmo.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_NOSE = create($$Lambda$CaptureResultVendorTags$ECtPEcOVBYrkInIH68dthnUKF5A.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_PUPIL_LINE = create($$Lambda$CaptureResultVendorTags$EmL98sLBYpqWaOOec9Sgv0kz_A.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_RISORIUS = create($$Lambda$CaptureResultVendorTags$0pkYDXcQpTrjtN72bGtCqgTXe4.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_SHOULDER_SLIM = create($$Lambda$CaptureResultVendorTags$jQt4ICS0DuQEHquDYVe265KMKE.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_SKIN_COLOR = create($$Lambda$CaptureResultVendorTags$8eJ_H6yp3YCrTi4oIT8u_EI4Oo4.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_SKIN_SMOOTH = create($$Lambda$CaptureResultVendorTags$IUig401iOhYNDeswArldJ9n3Do.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_SLIM_FACE = create($$Lambda$CaptureResultVendorTags$OQ124yUz0vineh4s4APfJYZMVXU.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_SLIM_NOSE = create($$Lambda$CaptureResultVendorTags$b2giw9_5WeoT5fTeW0nm53oJAQ.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_SMILE = create($$Lambda$CaptureResultVendorTags$ysoTaj_udYYdNVLQ72bNPLNJsbY.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BUTT_SLIM = create($$Lambda$CaptureResultVendorTags$GEk2a4ogcou8jQXYw46aWEGUT_4.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> CONTROL_ENABLE_REMOSAIC = create($$Lambda$CaptureResultVendorTags$9T3te7aodIEO02LZJs9pU8dero.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Byte>> DEPURPLE = create($$Lambda$CaptureResultVendorTags$FzcgA46TwVZNE3aWTR42N0NJs.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<byte[]>> DISTORTION_FPC_DATA = create($$Lambda$CaptureResultVendorTags$ir38tJlXi02OtDRSJEM_6MRvMyQ.INSTANCE, byte[].class);
    public static VendorTag<CaptureResult.Key<byte[]>> EXIF_INFO_VALUES = create($$Lambda$CaptureResultVendorTags$lDwByunsprm8VFkF7hOfLgdzhIk.INSTANCE, byte[].class);
    public static final VendorTag<CaptureResult.Key<Integer>> EYE_LIGHT_STRENGTH = create($$Lambda$CaptureResultVendorTags$okmTDnWRCMJwkgqJP_Ym3qDZgI.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> EYE_LIGHT_TYPE = create($$Lambda$CaptureResultVendorTags$AmpgzHDWjWZYRSSfa_2h4su0Q_U.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Byte>> FAST_ZOOM_RESULT = create($$Lambda$CaptureResultVendorTags$dzkwNWaIGMOgVGNoHg5YRAftN4.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> FRONT_SINGLE_CAMERA_BOKEH = create($$Lambda$CaptureResultVendorTags$pdSnVGJCGFO949t_GCp8DAJC6Mg.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<byte[]>> HDR_CHECKTER_EV_VALUES = create($$Lambda$CaptureResultVendorTags$SemhuzrqF0FcMqv9z7iLczUv1NQ.INSTANCE, byte[].class);
    public static final VendorTag<CaptureResult.Key<Byte>> HDR_MOTION_DETECTED = create($$Lambda$CaptureResultVendorTags$Zujd2ZE_wmgLPqjYHRiMVoGKsJA.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> IS_HDR_ENABLE = create($$Lambda$CaptureResultVendorTags$4U7cfWta4XmtEtjWBhpDYbfexlM.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> IS_SR_ENABLE = create($$Lambda$CaptureResultVendorTags$cPGoGP7EIGrqfLSg7Js5MfZTY2E.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Integer>> LENS_DIRTY_DETECTED = create($$Lambda$CaptureResultVendorTags$s36JHM_WQjgkON1fRi51pUl_lgk.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> MFNR_ENABLED = create($$Lambda$CaptureResultVendorTags$rTLN40INgJK6gCrI157zeeIUJOc.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<MarshalQueryableASDScene.ASDScene[]>> NON_SEMANTIC_SCENE = create($$Lambda$CaptureResultVendorTags$Lem3ZaTp9HwlxH_953seSQkT1iU.INSTANCE, MarshalQueryableASDScene.ASDScene[].class);
    public static final VendorTag<CaptureResult.Key<Boolean>> REAR_BOKEH_ENABLE = create($$Lambda$CaptureResultVendorTags$_FpHSFsZyBzvIZkvE4GjhjxDDI.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> REMOSAIC_DETECTED = create($$Lambda$CaptureResultVendorTags$XnywwN95mCSqQiOFMHR6xxInAR0.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<byte[]>> SAT_DBG_INFO = create($$Lambda$CaptureResultVendorTags$sUrO55GQYjpu84S7QVYWSlogVs.INSTANCE, byte[].class);
    public static final VendorTag<CaptureResult.Key<Boolean>> SAT_FALLBACK_DETECTED = create($$Lambda$CaptureResultVendorTags$O4rB_0lRUT0KBSl5pegHdqcx4s.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Integer>> SAT_MATER_CAMERA_ID = create($$Lambda$CaptureResultVendorTags$JyX3upISOZenUPTJPPkoQ3GdWk.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> SCENE_DETECTION_RESULT = create($$Lambda$CaptureResultVendorTags$GeqBNibkmkBefp4c8WJ3eSfnL8k.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<MarshalQueryableASDScene.ASDScene[]>> SEMANTIC_SCENE = create($$Lambda$CaptureResultVendorTags$NEaTnq0Y9rW_BAUydwPAwo76450.INSTANCE, MarshalQueryableASDScene.ASDScene[].class);
    public static final VendorTag<CaptureResult.Key<Byte>> SENSOR_HDR_ENABLE = create($$Lambda$CaptureResultVendorTags$x7Qx9TgJuy2e8D8uZGKCriN8jZI.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<MarshalQueryableASDScene.ASDScene[]>> STATE_SCENE = create($$Lambda$CaptureResultVendorTags$FMVkUgDAkUXf6XgqfAnjtl8h3tU.INSTANCE, MarshalQueryableASDScene.ASDScene[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> STATISTICS_FACE_AGE = create($$Lambda$CaptureResultVendorTags$lDJ9sfH058UvAuhqy0o54sCHAQ.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> STATISTICS_FACE_FACESCORE = create($$Lambda$CaptureResultVendorTags$riImCeHHQfhFkoFN9AdV99796fA.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> STATISTICS_FACE_GENDER = create($$Lambda$CaptureResultVendorTags$8oipjfJZASIq4mn1UgGg3B7NRU.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<byte[]>> STATISTICS_FACE_INFO = create($$Lambda$CaptureResultVendorTags$OlHjV9j7LqyeufP3O8LFg0SbG00.INSTANCE, byte[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> STATISTICS_FACE_PROP = create($$Lambda$CaptureResultVendorTags$DSVU7AMrJ1uNiQZEyXsLefP6dw.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<Boolean>> SUPER_NIGHT_SCENE_ENABLED = create($$Lambda$CaptureResultVendorTags$gTJp84cCJV5AFlJrMo1AbrgafA.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> SUPER_RESOLUTION_ENABLED = create($$Lambda$CaptureResultVendorTags$pf4l1R1NVBJ7M5FrwPBtGIO8lgg.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> SW_MFNR_ENABLED = create($$Lambda$CaptureResultVendorTags$qhHsVHT7aVcpPCJvWRW0grXenFo.INSTANCE, Boolean.class);
    private static final String TAG = "CaptureResultVendorTags";
    public static final VendorTag<CaptureResult.Key<Byte>> ULTRA_WIDE_LENS_DISTORTION_CORRECTION_LEVEL = create($$Lambda$CaptureResultVendorTags$HKxnamZlhu2sjye4cU27C8nNkOw.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<Integer>> ULTRA_WIDE_RECOMMENDED_RESULT = create($$Lambda$CaptureResultVendorTags$C3cUR4FDnAAZSkTPaLki20OsJv8.INSTANCE, Integer.class);
    public static final int VALUE_SAT_MATER_CAMERA_ID_TELE = 3;
    public static final int VALUE_SAT_MATER_CAMERA_ID_ULTRA_WIDE = 1;
    public static final int VALUE_SAT_MATER_CAMERA_ID_WIDE = 2;
    public static final int VALUE_VIDEO_RECORD_STATE_IDLE = 2;
    public static final int VALUE_VIDEO_RECORD_STATE_PROCESS = 1;
    public static final VendorTag<CaptureResult.Key<Integer>> VIDEO_RECORD_STATE = create($$Lambda$CaptureResultVendorTags$l0l1MrJgs9q3NsoeWm576Lu5j98.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> WHOLE_BODY_SLIM = create($$Lambda$CaptureResultVendorTags$hRVH8va7xJNvDBMUQvPbQQ1pEM.INSTANCE, Integer.class);
    private static Constructor<CaptureResult.Key> resultConstructor;

    private static <T> VendorTag<CaptureResult.Key<T>> create(final Supplier<String> supplier, final Class<T> cls) {
        return new VendorTag<CaptureResult.Key<T>>() {
            /* access modifiers changed from: protected */
            public CaptureResult.Key<T> create() {
                return CaptureResultVendorTags.resultKey(getName(), cls);
            }

            public String getName() {
                return (String) supplier.get();
            }
        };
    }

    static /* synthetic */ String lambda$static$0() {
        return "com.vidhance.autozoom.bounds";
    }

    static /* synthetic */ String lambda$static$1() {
        return "com.vidhance.autozoom.target_bounds_stabilized";
    }

    static /* synthetic */ String lambda$static$10() {
        return "com.vidhance.autozoom.delayed_target_bounds_zoomed";
    }

    static /* synthetic */ String lambda$static$11() {
        return "xiaomi.beauty.beautyLevelApplied";
    }

    static /* synthetic */ String lambda$static$12() {
        return "xiaomi.beauty.skinColorRatio";
    }

    static /* synthetic */ String lambda$static$13() {
        return "xiaomi.beauty.slimFaceRatio";
    }

    static /* synthetic */ String lambda$static$14() {
        return "xiaomi.beauty.skinSmoothRatio";
    }

    static /* synthetic */ String lambda$static$15() {
        return "xiaomi.beauty.enlargeEyeRatio";
    }

    static /* synthetic */ String lambda$static$16() {
        return "xiaomi.beauty.noseRatio";
    }

    static /* synthetic */ String lambda$static$17() {
        return "xiaomi.beauty.risoriusRatio";
    }

    static /* synthetic */ String lambda$static$18() {
        return "xiaomi.beauty.lipsRatio";
    }

    static /* synthetic */ String lambda$static$19() {
        return "xiaomi.beauty.chinRatio";
    }

    static /* synthetic */ String lambda$static$2() {
        return "com.vidhance.autozoom.target_bounds_zoomed";
    }

    static /* synthetic */ String lambda$static$20() {
        return "xiaomi.beauty.neckRatio";
    }

    static /* synthetic */ String lambda$static$21() {
        return "xiaomi.beauty.smileRatio";
    }

    static /* synthetic */ String lambda$static$22() {
        return "xiaomi.beauty.slimNoseRatio";
    }

    static /* synthetic */ String lambda$static$23() {
        return "xiaomi.beauty.hairlineRatio";
    }

    static /* synthetic */ String lambda$static$24() {
        return "xiaomi.beauty.eyeBrowDyeRatio";
    }

    static /* synthetic */ String lambda$static$25() {
        return "xiaomi.beauty.pupilLineRatio";
    }

    static /* synthetic */ String lambda$static$26() {
        return "xiaomi.beauty.lipGlossRatio";
    }

    static /* synthetic */ String lambda$static$27() {
        return "xiaomi.beauty.blushRatio";
    }

    static /* synthetic */ String lambda$static$28() {
        return "xiaomi.beauty.eyeLightType";
    }

    static /* synthetic */ String lambda$static$29() {
        return "xiaomi.beauty.eyeLightStrength";
    }

    static /* synthetic */ String lambda$static$3() {
        return "com.vidhance.autozoom.status";
    }

    static /* synthetic */ String lambda$static$30() {
        return "xiaomi.supernight.enabled";
    }

    static /* synthetic */ String lambda$static$31() {
        return "xiaomi.beauty.headSlimRatio";
    }

    static /* synthetic */ String lambda$static$32() {
        return "xiaomi.beauty.bodySlimRatio";
    }

    static /* synthetic */ String lambda$static$33() {
        return "xiaomi.beauty.shoulderSlimRatio";
    }

    static /* synthetic */ String lambda$static$34() {
        return "xiaomi.beauty.legSlimRatio";
    }

    static /* synthetic */ String lambda$static$35() {
        return "xiaomi.beauty.oneKeySlimRatio";
    }

    static /* synthetic */ String lambda$static$36() {
        return "xiaomi.beauty.buttPlumpSlimRatio";
    }

    static /* synthetic */ String lambda$static$37() {
        return "xiaomi.bokeh.enabled";
    }

    static /* synthetic */ String lambda$static$38() {
        return "xiaomi.remosaic.enabled";
    }

    static /* synthetic */ String lambda$static$39() {
        return "xiaomi.distortion.ultraWideDistortionLevel";
    }

    static /* synthetic */ String lambda$static$4() {
        return "com.vidhance.autozoom.active_objects";
    }

    static /* synthetic */ String lambda$static$40() {
        return "xiaomi.depurple.enabled";
    }

    static /* synthetic */ String lambda$static$41() {
        return "xiaomi.bokehrear.enabled";
    }

    static /* synthetic */ String lambda$static$42() {
        return "xiaomi.superResolution.enabled";
    }

    static /* synthetic */ String lambda$static$43() {
        return "xiaomi.mfnr.enabled";
    }

    static /* synthetic */ String lambda$static$44() {
        return "xiaomi.swmf.enabled";
    }

    static /* synthetic */ String lambda$static$45() {
        return "xiaomi.video.recordState";
    }

    static /* synthetic */ String lambda$static$46() {
        return "xiaomi.faceAnalyzeResult.result";
    }

    static /* synthetic */ String lambda$static$47() {
        return "xiaomi.faceAnalyzeResult.age";
    }

    static /* synthetic */ String lambda$static$48() {
        return "xiaomi.faceAnalyzeResult.gender";
    }

    static /* synthetic */ String lambda$static$49() {
        return "xiaomi.faceAnalyzeResult.score";
    }

    static /* synthetic */ String lambda$static$5() {
        return "com.vidhance.autozoom.selected_objects";
    }

    static /* synthetic */ String lambda$static$50() {
        return "xiaomi.faceAnalyzeResult.prop";
    }

    static /* synthetic */ String lambda$static$51() {
        return "org.quic.camera2.statsconfigs.AECIsInsensorHDR";
    }

    static /* synthetic */ String lambda$static$52() {
        return "xiaomi.scene.result";
    }

    static /* synthetic */ String lambda$static$53() {
        return b.isMTKPlatform() ? "com.xiaomi.statsconfigs.AecLux" : "com.qti.chi.statsaec.AecLux";
    }

    static /* synthetic */ String lambda$static$54() {
        return "xiaomi.ai.asd.enabled";
    }

    static /* synthetic */ String lambda$static$55() {
        return "xiaomi.ai.asd.sceneDetected";
    }

    static /* synthetic */ String lambda$static$56() {
        return "xiaomi.hdr.hdrDetected";
    }

    static /* synthetic */ String lambda$static$57() {
        return "xiaomi.ai.add.lensDirtyDetected";
    }

    static /* synthetic */ String lambda$static$58() {
        return b.isMTKPlatform() ? "xiaomi.camera.awb.colorTemperature" : "org.quic.camera2.statsconfigs.AWBFrameControl";
    }

    static /* synthetic */ String lambda$static$59() {
        return "org.quic.camera2.statsconfigs.AECFrameControl";
    }

    static /* synthetic */ String lambda$static$6() {
        return "com.vidhance.autozoom.paused_objects";
    }

    static /* synthetic */ String lambda$static$60() {
        return "org.quic.camera2.statsconfigs.AFFrameControl";
    }

    static /* synthetic */ String lambda$static$61() {
        return "xiaomi.smoothTransition.result";
    }

    static /* synthetic */ String lambda$static$62() {
        return "xiaomi.hdr.hdrChecker";
    }

    static /* synthetic */ String lambda$static$63() {
        return "xiaomi.debugInfo.info";
    }

    static /* synthetic */ String lambda$static$64() {
        return "xiaomi.ai.misd.ultraWideRecommended";
    }

    static /* synthetic */ String lambda$static$65() {
        return "xiaomi.beauty.bodySlimCnt";
    }

    static /* synthetic */ String lambda$static$66() {
        return "xiaomi.superResolution.enabled";
    }

    static /* synthetic */ String lambda$static$67() {
        return "xiaomi.hdr.enabled";
    }

    static /* synthetic */ String lambda$static$68() {
        return "xiaomi.remosaic.detected";
    }

    static /* synthetic */ String lambda$static$69() {
        return "xiaomi.ai.misd.SemanticScene";
    }

    static /* synthetic */ String lambda$static$7() {
        return "com.vidhance.autozoom.object_bounds_stabilized";
    }

    static /* synthetic */ String lambda$static$70() {
        return "xiaomi.ai.misd.NonSemanticScene";
    }

    static /* synthetic */ String lambda$static$71() {
        return "xiaomi.ai.misd.StateScene";
    }

    static /* synthetic */ String lambda$static$72() {
        return "xiaomi.distortion.distortioFpcData";
    }

    static /* synthetic */ String lambda$static$73() {
        return "xiaomi.smoothTransition.masterCameraId";
    }

    static /* synthetic */ String lambda$static$74() {
        return "xiaomi.smoothTransition.detected";
    }

    static /* synthetic */ String lambda$static$75() {
        return "xiaomi.sat.dbg.satDbgInfo";
    }

    static /* synthetic */ String lambda$static$76() {
        return "xiaomi.ai.misd.hdrmotionDetected";
    }

    static /* synthetic */ String lambda$static$8() {
        return "com.vidhance.autozoom.object_bounds_zoomed";
    }

    static /* synthetic */ String lambda$static$9() {
        return "com.vidhance.autozoom.delayed_target_bounds_stabilized";
    }

    public static void preload() {
        Log.d(TAG, "preloading...");
    }

    /* access modifiers changed from: private */
    public static <T> CaptureResult.Key<T> resultKey(String str, Class<T> cls) {
        try {
            if (resultConstructor == null) {
                resultConstructor = CaptureResult.Key.class.getConstructor(new Class[]{String.class, cls.getClass()});
                resultConstructor.setAccessible(true);
            }
            return resultConstructor.newInstance(new Object[]{str, cls});
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            Log.d(TAG, "Cannot find/call Key constructor: " + e.getMessage());
            return null;
        }
    }
}
