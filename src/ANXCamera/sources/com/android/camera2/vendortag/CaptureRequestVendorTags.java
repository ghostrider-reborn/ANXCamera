package com.android.camera2.vendortag;

import android.graphics.Rect;
import android.hardware.camera2.CaptureRequest;
import android.util.Log;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import com.mi.config.b;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class CaptureRequestVendorTags {
    public static final VendorTag<CaptureRequest.Key<Boolean>> AI_SCENE = create(C0067qa.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AI_SCENE_APPLY = create(K.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AI_SCENE_PERIOD = create(C0037ga.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_APPLY_IN_PREVIEW = create(Ra.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<float[]>> AUTOZOOM_CENTER_OFFSET = create(C0025ca.INSTANCE, float[].class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_FORCE_LOCK = create(Z.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Float>> AUTOZOOM_MINIMUM_SCALING = create(C0049ka.INSTANCE, Float.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_MODE = create(C0035fb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Float>> AUTOZOOM_SCALE_OFFSET = create(B.INSTANCE, Float.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_SELECT = create(Sa.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<float[]>> AUTOZOOM_START = create(M.INSTANCE, float[].class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_STOP = create(C0086x.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_UNSELECT = create(C0019aa.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> BACKWARD_CAPTURE_HINT = create(C0090ya.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> BACK_SOFT_LIGHT = create(C0087xa.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_BLUSHER = create(C0034fa.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_BODY_SLIM = create(Fa.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_CHIN = create(Y.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_ENLARGE_EYE = create(X.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_EYEBROW_DYE = create(S.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_HAIRLINE = create(Ma.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_HEAD_SLIM = create(L.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_JELLY_LIPS = create(Xa.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_LEG_SLIM = create(Wa.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<String>> BEAUTY_LEVEL = create(Ia.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_LIPS = create(C0062ob.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_NECK = create(La.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_NOSE = create(C0028da.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_PUPIL_LINE = create(C0076tb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_RISORIUS = create(C0083w.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SHOULDER_SLIM = create(C0068qb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SKIN_COLOR = create(G.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SKIN_SMOOTH = create(C0020ab.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SLIM_FACE = create(Ja.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SLIM_NOSE = create(V.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SMILE = create(Ka.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<String>> BOKEH_F_NUMBER = create(C0031ea.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BURST_CAPTURE_HINT = create(C0072sa.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BURST_SHOOT_FPS = create(C0069ra.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BUTT_SLIM = create(Da.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> CAMERA_AI_30 = create(Va.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> CONTRAST_LEVEL = create(C0040ha.INSTANCE, Integer.class);
    public static VendorTag<CaptureRequest.Key<Integer>> CONTROL_AI_SCENE_MODE = create(C0078ua.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<byte[]>> CONTROL_DISTORTION_FPC_DATA = create(C0043ia.INSTANCE, byte[].class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> CONTROL_ENABLE_REMOSAIC = create(Ta.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<int[]>> CONTROL_QUICK_PREVIEW = create(Ca.INSTANCE, int[].class);
    public static final int[] CONTROL_QUICK_PREVIEW_OFF = {0};
    public static final int[] CONTROL_QUICK_PREVIEW_ON = {1};
    public static final VendorTag<CaptureRequest.Key<int[]>> CONTROL_REMOSAIC_HINT = create(C0059nb.INSTANCE, int[].class);
    public static final int[] CONTROL_REMOSAIC_HINT_OFF = {0};
    public static final int[] CONTROL_REMOSAIC_HINT_ON = {1};
    public static final VendorTag<CaptureRequest.Key<String>> CUSTOM_WATERMARK_TEXT = create(C0065pb.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> DEFLICKER_ENABLED = create(C0050kb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> DEPURPLE = create(C0058na.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> DEVICE_ORIENTATION = create(C0044ib.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> EXPOSURE_METERING = create(C0079ub.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> EYE_LIGHT_STRENGTH = create(C0055ma.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> EYE_LIGHT_TYPE = create(T.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> FACE_AGE_ANALYZE_ENABLED = create(Qa.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> FACE_SCORE_ENABLED = create(C0082vb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> FLAW_DETECT_ENABLE = create(Ga.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> FRONT_MIRROR = create(C0084wa.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> FRONT_SINGLE_CAMERA_BOKEH = create(C0041hb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> HDR_BRACKET_MODE = create(Ya.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> HDR_CHECKER_ENABLE = create(C0029db.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> HDR_ENABLED = create(C0023bb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> HFPSVR_MODE = create(Ha.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> HHT_ENABLED = create(D.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> HINT_FOR_RAW_REPROCESS = create(C0052la.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Long>> ISO_EXP = create(F.INSTANCE, Long.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> IS_HFR_PREVIEW = create(_a.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> LENS_DIRTY_DETECT = create(Aa.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> MACRO_MODE = create(C0056mb.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> MFNR_ENABLED = create(W.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> MTK_EXPOSURE_METERING_MODE = create(C0093za.INSTANCE, Byte.class);
    public static final byte MTK_EXPOSURE_METERING_MODE_AVERAGE = 2;
    public static final byte MTK_EXPOSURE_METERING_MODE_CENTER_WEIGHT = 0;
    public static final byte MTK_EXPOSURE_METERING_MODE_SOPT = 1;
    public static final VendorTag<CaptureRequest.Key<Integer>> MULTIFRAME_INPUTNUM = create(N.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> NORMAL_WIDE_LENS_DISTORTION_CORRECTION_LEVEL = create(P.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<MarshalQueryableASDScene.ASDScene[]>> ON_TRIPOD_MODE = create(I.INSTANCE, MarshalQueryableASDScene.ASDScene[].class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> PARALLEL_ENABLED = create(Ba.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<byte[]>> PARALLEL_PATH = create(Za.INSTANCE, byte[].class);
    public static final VendorTag<CaptureRequest.Key<Integer>> PORTRAIT_LIGHTING = create(E.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Rect>> POST_PROCESS_CROP_REGION = create(C0038gb.INSTANCE, Rect.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> REAR_BOKEH_ENABLE = create(Ea.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> RECORDING_END_STREAM = create(Oa.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> SANPSHOT_FLIP_MODE = create(C0053lb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> SATURATION = create(C0092z.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SAT_IS_ZOOMING = create(Pa.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> SCREEN_LIGHT_HINT = create(C0075ta.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> SELECT_PRIORITY = create(Q.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> SHARPNESS_CONTROL = create(C0070rb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<int[]>> SMVR_MODE = create(C0064pa.INSTANCE, int[].class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SNAP_SHOT_TORCH = create(Na.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> ST_ENABLED = create(C0081va.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> ST_FAST_ZOOM_IN = create(O.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SUPER_NIGHT_SCENE_ENABLED = create(A.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SUPER_RESOLUTION_ENABLED = create(U.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SW_MFNR_ENABLED = create(C0032eb.INSTANCE, Boolean.class);
    private static final String TAG = "CaptureRequestVendorTags";
    public static VendorTag<CaptureRequest.Key<Boolean>> ULTRA_PIXEL_PORTRAIT_ENABLED = create(C0089y.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> ULTRA_WIDE_LENS_DISTORTION_CORRECTION_LEVEL = create(C0047jb.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> USE_CUSTOM_WB = create(C0022ba.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> USE_ISO_VALUE = create(C.INSTANCE, Integer.class);
    public static final int VALUE_HFPSVR_MODE_OFF = 0;
    public static final int VALUE_HFPSVR_MODE_ON = 1;
    public static final int VALUE_SANPSHOT_FLIP_MODE_OFF = 0;
    public static final int VALUE_SANPSHOT_FLIP_MODE_ON = 1;
    public static final int VALUE_SELECT_PRIORITY_EXP_TIME_PRIORITY = 1;
    public static final int VALUE_SELECT_PRIORITY_ISO_PRIORITY = 0;
    public static final int[] VALUE_SMVR_MODE_120FPS = {120, 4};
    public static final int[] VALUE_SMVR_MODE_240FPS = {240, 8};
    public static final int VALUE_VIDEO_RECORD_CONTROL_PREPARE = 0;
    public static final int VALUE_VIDEO_RECORD_CONTROL_START = 1;
    public static final int VALUE_VIDEO_RECORD_CONTROL_STOP = 2;
    public static final byte VALUE_ZSL_CAPTURE_MODE_OFF = 0;
    public static final byte VALUE_ZSL_CAPTURE_MODE_ON = 1;
    public static final VendorTag<CaptureRequest.Key<Integer>> VIDEO_RECORD_CONTROL = create(H.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<String>> WATERMARK_APPLIEDTYPE = create(J.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<String>> WATERMARK_AVAILABLETYPE = create(Ua.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<String>> WATERMARK_FACE = create(C0026cb.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<String>> WATERMARK_TIME = create(C0061oa.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> WHOLE_BODY_SLIM = create(C0073sb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> ZSL_CAPTURE_MODE = create(C0046ja.INSTANCE, Byte.class);
    private static Constructor<CaptureRequest.Key> requestConstructor;

    static /* synthetic */ String Ae() {
        return "xiaomi.watermark.typeApplied";
    }

    static /* synthetic */ String Af() {
        return "com.mediatek.3afeature.aeMeteringMode";
    }

    static /* synthetic */ String Be() {
        return "xiaomi.watermark.time";
    }

    static /* synthetic */ String Ce() {
        return "xiaomi.watermark.face";
    }

    static /* synthetic */ String Dd() {
        return "com.vidhance.autozoom.mode";
    }

    static /* synthetic */ String De() {
        return "xiaomi.snapshotTorch.enabled";
    }

    static /* synthetic */ String Ed() {
        return "com.vidhance.autozoom.applyinpreview";
    }

    static /* synthetic */ String Ee() {
        return "xiaomi.flip.enabled";
    }

    static /* synthetic */ String Fd() {
        return "xiaomi.video.recordControl";
    }

    static /* synthetic */ String Fe() {
        return "xiaomi.burst.captureHint";
    }

    static /* synthetic */ String Gd() {
        return "xiaomi.hdr.enabled";
    }

    static /* synthetic */ String Ge() {
        return "xiaomi.burst.shootFPS";
    }

    static /* synthetic */ String Hd() {
        return "xiaomi.hdr.hdrChecker.enabled";
    }

    static /* synthetic */ String He() {
        return "xiaomi.beauty.eyeBrowDyeRatio";
    }

    static /* synthetic */ String Id() {
        return "xiaomi.parallel.path";
    }

    static /* synthetic */ String Ie() {
        return "xiaomi.beauty.pupilLineRatio";
    }

    static /* synthetic */ String Jd() {
        return "xiaomi.parallel.enabled";
    }

    static /* synthetic */ String Je() {
        return "xiaomi.beauty.lipGlossRatio";
    }

    static /* synthetic */ String Kd() {
        return "xiaomi.hht.enabled";
    }

    static /* synthetic */ String Ke() {
        return "xiaomi.beauty.blushRatio";
    }

    static /* synthetic */ String Ld() {
        return "xiaomi.node.hfr.deflicker.enabled";
    }

    static /* synthetic */ String Le() {
        return "xiaomi.beauty.eyeLightType";
    }

    static /* synthetic */ String Md() {
        return "xiaomi.superportrait.enabled";
    }

    static /* synthetic */ String Me() {
        return "xiaomi.beauty.eyeLightStrength";
    }

    static /* synthetic */ String Nd() {
        return "xiaomi.superResolution.enabled";
    }

    static /* synthetic */ String Ne() {
        return "xiaomi.supernight.enabled";
    }

    static /* synthetic */ String Od() {
        return "xiaomi.mfnr.enabled";
    }

    static /* synthetic */ String Oe() {
        return "xiaomi.beauty.headSlimRatio";
    }

    static /* synthetic */ String Pd() {
        return "com.vidhance.autozoom.minimumscaling";
    }

    static /* synthetic */ String Pe() {
        return "xiaomi.beauty.bodySlimRatio";
    }

    static /* synthetic */ String Qd() {
        return "xiaomi.swmf.enabled";
    }

    static /* synthetic */ String Qe() {
        return "xiaomi.beauty.shoulderSlimRatio";
    }

    static /* synthetic */ String Rd() {
        return "xiaomi.bokeh.enabled";
    }

    static /* synthetic */ String Re() {
        return "xiaomi.beauty.legSlimRatio";
    }

    static /* synthetic */ String Sd() {
        return "com.vidhance.autozoom.stop";
    }

    static /* synthetic */ String Se() {
        return "xiaomi.beauty.oneKeySlimRatio";
    }

    static /* synthetic */ String Td() {
        return "com.vidhance.autozoom.start_region";
    }

    static /* synthetic */ String Te() {
        return "xiaomi.beauty.buttPlumpSlimRatio";
    }

    static /* synthetic */ String Ud() {
        return "com.vidhance.autozoom.select";
    }

    static /* synthetic */ String Ue() {
        return "xiaomi.distortion.distortionLevelApplied";
    }

    static /* synthetic */ String Vd() {
        return "com.vidhance.autozoom.unselect";
    }

    static /* synthetic */ String Ve() {
        return "xiaomi.distortion.ultraWideDistortionLevel";
    }

    static /* synthetic */ String Wd() {
        return "com.vidhance.autozoom.force_lock";
    }

    static /* synthetic */ String We() {
        return "xiaomi.snapshot.front.ScreenLighting.enabled";
    }

    static /* synthetic */ String Xd() {
        return "com.vidhance.autozoom.center_offset";
    }

    static /* synthetic */ String Xe() {
        return "xiaomi.softlightMode.enabled";
    }

    static /* synthetic */ String Yd() {
        return "com.vidhance.autozoom.scale_offset";
    }

    static /* synthetic */ String Ye() {
        return "xiaomi.snapshot.backwardfetchframe.enabled";
    }

    static /* synthetic */ String Zd() {
        return "com.mediatek.configure.setting.initrequest";
    }

    static /* synthetic */ String Ze() {
        return "org.codeaurora.qcamera3.iso_exp_priority.select_priority";
    }

    static /* synthetic */ String _d() {
        return "xiaomi.ai.asd.sceneDetected";
    }

    static /* synthetic */ String _e() {
        return "org.codeaurora.qcamera3.iso_exp_priority.use_iso_exp_priority";
    }

    static /* synthetic */ String ae() {
        return "xiaomi.ai.misd.StateScene";
    }

    static /* synthetic */ String af() {
        return "org.codeaurora.qcamera3.iso_exp_priority.use_iso_value";
    }

    static /* synthetic */ String be() {
        return "xiaomi.ai.flaw.enabled";
    }

    static /* synthetic */ String bf() {
        return b.isMTKPlatform() ? "xiaomi.camera.awb.cct" : "com.qti.stats.awbwrapper.AWBCCT";
    }

    static /* synthetic */ String ce() {
        return "xiaomi.bokehrear.enabled";
    }

    static /* synthetic */ String cf() {
        return "org.codeaurora.qcamera3.saturation.use_saturation";
    }

    private static <T> VendorTag<CaptureRequest.Key<T>> create(final Supplier<String> supplier, final Class<T> cls) {
        return new VendorTag<CaptureRequest.Key<T>>() {
            /* access modifiers changed from: protected */
            public CaptureRequest.Key<T> create() {
                return CaptureRequestVendorTags.requestKey(getName(), cls);
            }

            public String getName() {
                return (String) supplier.get();
            }
        };
    }

    static /* synthetic */ String de() {
        return "xiaomi.bokeh.fNumberApplied";
    }

    static /* synthetic */ String df() {
        return "org.codeaurora.qcamera3.sharpness.strength";
    }

    static /* synthetic */ String ee() {
        return "xiaomi.smoothTransition.enabled";
    }

    static /* synthetic */ String ef() {
        return "org.codeaurora.qcamera3.exposure_metering.exposure_metering_mode";
    }

    static /* synthetic */ String fe() {
        return "xiaomi.smoothTransition.fastZoomIn";
    }

    static /* synthetic */ String ff() {
        return "org.quic.camera.recording.endOfStream";
    }

    static /* synthetic */ String ge() {
        return "xiaomi.ai.add.enabled";
    }

    static /* synthetic */ String gf() {
        return "xiaomi.ai.asd.enabled";
    }

    static /* synthetic */ String he() {
        return "xiaomi.portrait.lighting";
    }

    static /* synthetic */ String hf() {
        return "xiaomi.ai.asd.sceneApplied";
    }

    static /* synthetic */ String ie() {
        return "xiaomi.ai.segment.enabled";
    }

    /* renamed from: if  reason: not valid java name */
    static /* synthetic */ String m0if() {
        return "xiaomi.ai.asd.period";
    }

    static /* synthetic */ String je() {
        return "xiaomi.faceGenderAndAge.enabled";
    }

    static /* synthetic */ String jf() {
        return "org.codeaurora.qcamera3.contrast.level";
    }

    static /* synthetic */ String ke() {
        return "xiaomi.faceScore.enabled";
    }

    static /* synthetic */ String kf() {
        return "xiaomi.hfrPreview.isHFRPreview";
    }

    static /* synthetic */ String le() {
        return "xiaomi.device.orientation";
    }

    static /* synthetic */ String lf() {
        return "org.codeaurora.qcamera3.ae_bracket.mode";
    }

    static /* synthetic */ String me() {
        return "xiaomi.beauty.beautyLevelApplied";
    }

    static /* synthetic */ String mf() {
        return "xiaomi.multiframe.inputNum";
    }

    static /* synthetic */ String ne() {
        return "xiaomi.beauty.skinColorRatio";
    }

    static /* synthetic */ String nf() {
        return "xiaomi.depurple.enabled";
    }

    static /* synthetic */ String oe() {
        return "xiaomi.beauty.slimFaceRatio";
    }

    static /* synthetic */ String of() {
        return "xiaomi.MacroMode.enabled";
    }

    static /* synthetic */ String pe() {
        return "xiaomi.beauty.skinSmoothRatio";
    }

    static /* synthetic */ String pf() {
        return "xiaomi.watermark.custom";
    }

    public static void preload() {
        Log.d(TAG, "preloading...");
    }

    static /* synthetic */ String qe() {
        return "xiaomi.beauty.enlargeEyeRatio";
    }

    static /* synthetic */ String qf() {
        return "xiaomi.satIsZooming.satIsZooming";
    }

    static /* synthetic */ String re() {
        return "xiaomi.beauty.noseRatio";
    }

    static <T> CaptureRequest.Key<T> requestKey(String str, Class<T> cls) {
        try {
            if (requestConstructor == null) {
                requestConstructor = CaptureRequest.Key.class.getConstructor(new Class[]{String.class, cls.getClass()});
                requestConstructor.setAccessible(true);
            }
            return requestConstructor.newInstance(new Object[]{str, cls});
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e2) {
            Log.d(TAG, "Cannot find/call Key constructor: " + e2.getMessage());
            return null;
        }
    }

    static /* synthetic */ String rf() {
        return "com.mediatek.streamingfeature.hfpsMode";
    }

    static /* synthetic */ String se() {
        return "xiaomi.beauty.risoriusRatio";
    }

    static /* synthetic */ String sf() {
        return "com.mediatek.smvrfeature.smvrMode";
    }

    static /* synthetic */ String te() {
        return "xiaomi.beauty.lipsRatio";
    }

    static /* synthetic */ String tf() {
        return "com.mediatek.control.capture.zsl.mode";
    }

    static /* synthetic */ String ue() {
        return "xiaomi.beauty.chinRatio";
    }

    static /* synthetic */ String uf() {
        return "com.mediatek.control.capture.flipmode";
    }

    static /* synthetic */ String ve() {
        return "xiaomi.beauty.neckRatio";
    }

    static /* synthetic */ String vf() {
        return "com.mediatek.control.capture.remosaicenable";
    }

    static /* synthetic */ String we() {
        return "xiaomi.beauty.smileRatio";
    }

    static /* synthetic */ String wf() {
        return "xiaomi.remosaic.enabled";
    }

    static /* synthetic */ String xe() {
        return "xiaomi.beauty.slimNoseRatio";
    }

    static /* synthetic */ String xf() {
        return "xiaomi.distortion.distortioFpcData";
    }

    static /* synthetic */ String ye() {
        return "xiaomi.beauty.hairlineRatio";
    }

    static /* synthetic */ String yf() {
        return "com.mediatek.control.capture.hintForRawReprocess";
    }

    static /* synthetic */ String ze() {
        return "xiaomi.watermark.availableType";
    }

    static /* synthetic */ String zf() {
        return "xiaomi.superResolution.cropRegionMtk";
    }
}
