package com.android.camera2.vendortag;

import android.hardware.camera2.CaptureResult.Key;
import android.util.Log;
import com.android.camera2.vendortag.struct.AECFrameControl;
import com.android.camera2.vendortag.struct.AFFrameControl;
import com.android.camera2.vendortag.struct.AWBFrameControl;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene.ASDScene;
import com.mi.config.b;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class CaptureResultVendorTags {
    public static final VendorTag<Key<AECFrameControl>> AEC_FRAME_CONTROL = create(Hb.INSTANCE, AECFrameControl.class);
    public static final VendorTag<Key<Float>> AEC_LUX = create(C0094zb.INSTANCE, Float.class);
    public static final VendorTag<Key<AFFrameControl>> AF_FRAME_CONTROL = create(Wb.INSTANCE, AFFrameControl.class);
    public static final VendorTag<Key<Byte>> AI_HDR_DETECTED = create(Ub.INSTANCE, Byte.class);
    public static final VendorTag<Key<Integer>> AI_SCENE_DETECTED = create(jc.INSTANCE, Integer.class);
    public static final VendorTag<Key<Byte>> AI_SCENE_ENABLE = create(Lb.INSTANCE, Byte.class);
    public static final VendorTag<Key<int[]>> AUTOZOOM_ACTIVE_OBJECTS = create(_b.INSTANCE, int[].class);
    public static final VendorTag<Key<float[]>> AUTOZOOM_BOUNDS = create(ec.INSTANCE, float[].class);
    public static final VendorTag<Key<float[]>> AUTOZOOM_DELAYED_TARGET_BOUNDS_STABILIZED = create(Pb.INSTANCE, float[].class);
    public static final VendorTag<Key<float[]>> AUTOZOOM_DELAYED_TARGET_BOUNDS_ZOOMED = create(hc.INSTANCE, float[].class);
    public static final VendorTag<Key<float[]>> AUTOZOOM_OBJECT_BOUNDS_STABILIZED = create(C0085wb.INSTANCE, float[].class);
    public static final VendorTag<Key<float[]>> AUTOZOOM_OBJECT_BOUNDS_ZOOMED = create(cc.INSTANCE, float[].class);
    public static final VendorTag<Key<int[]>> AUTOZOOM_PAUSED_OBJECTS = create(dc.INSTANCE, int[].class);
    public static final VendorTag<Key<int[]>> AUTOZOOM_SELECTED_OBJECTS = create(Db.INSTANCE, int[].class);
    public static final VendorTag<Key<Integer>> AUTOZOOM_STATUS = create(ic.INSTANCE, Integer.class);
    public static final VendorTag<Key<float[]>> AUTOZOOM_TARGET_BOUNDS_STABILIZED = create(Fb.INSTANCE, float[].class);
    public static final VendorTag<Key<float[]>> AUTOZOOM_TARGET_BOUNDS_ZOOMED = create(C0091yb.INSTANCE, float[].class);
    public static final VendorTag<Key<AWBFrameControl>> AWB_FRAME_CONTROL = create(Vb.INSTANCE, AWBFrameControl.class);
    public static final VendorTag<Key<Integer>> BEAUTY_BODY_SLIM_COUNT = create(Zb.INSTANCE, Integer.class);
    public static final VendorTag<Key<byte[]>> DISTORTION_FPC_DATA = create(gc.INSTANCE, byte[].class);
    public static final VendorTag<Key<Byte>> FAST_ZOOM_RESULT = create(Tb.INSTANCE, Byte.class);
    public static final VendorTag<Key<byte[]>> HDR_CHECKTER_EV_VALUES = create(Cb.INSTANCE, byte[].class);
    public static final VendorTag<Key<Byte>> HDR_MOTION_DETECTED = create(Ib.INSTANCE, Byte.class);
    public static final VendorTag<Key<Boolean>> IS_HDR_ENABLE = create(Ob.INSTANCE, Boolean.class);
    public static final VendorTag<Key<Boolean>> IS_SR_ENABLE = create(Gb.INSTANCE, Boolean.class);
    public static final VendorTag<Key<Integer>> LENS_DIRTY_DETECTED = create(Rb.INSTANCE, Integer.class);
    public static final VendorTag<Key<ASDScene[]>> NON_SEMANTIC_SCENE = create(ac.INSTANCE, ASDScene[].class);
    public static final VendorTag<Key<Boolean>> REMOSAIC_DETECTED = create(bc.INSTANCE, Boolean.class);
    public static final VendorTag<Key<byte[]>> SAT_DBG_INFO = create(Kb.INSTANCE, byte[].class);
    public static final VendorTag<Key<Integer>> SAT_MATER_CAMERA_ID = create(Bb.INSTANCE, Integer.class);
    public static final VendorTag<Key<Integer>> SCENE_DETECTION_RESULT = create(Sb.INSTANCE, Integer.class);
    public static final VendorTag<Key<ASDScene[]>> SEMANTIC_SCENE = create(Mb.INSTANCE, ASDScene[].class);
    public static final VendorTag<Key<Byte>> SENSOR_HDR_ENABLE = create(C0088xb.INSTANCE, Byte.class);
    public static final VendorTag<Key<ASDScene[]>> STATE_SCENE = create(Jb.INSTANCE, ASDScene[].class);
    public static final VendorTag<Key<float[]>> STATISTICS_FACE_AGE = create(Qb.INSTANCE, float[].class);
    public static final VendorTag<Key<float[]>> STATISTICS_FACE_FACESCORE = create(Yb.INSTANCE, float[].class);
    public static final VendorTag<Key<float[]>> STATISTICS_FACE_GENDER = create(Nb.INSTANCE, float[].class);
    public static final VendorTag<Key<byte[]>> STATISTICS_FACE_INFO = create(Ab.INSTANCE, byte[].class);
    public static final VendorTag<Key<float[]>> STATISTICS_FACE_PROP = create(Eb.INSTANCE, float[].class);
    private static final String TAG = "CaptureResultVendorTags";
    public static final VendorTag<Key<Integer>> ULTRA_WIDE_RECOMMENDED_RESULT = create(fc.INSTANCE, Integer.class);
    public static final int VALUE_SAT_MATER_CAMERA_ID_TELE = 3;
    public static final int VALUE_SAT_MATER_CAMERA_ID_ULTRA_WIDE = 1;
    public static final int VALUE_SAT_MATER_CAMERA_ID_WIDE = 2;
    public static final int VALUE_VIDEO_RECORD_STATE_IDLE = 2;
    public static final int VALUE_VIDEO_RECORD_STATE_PROCESS = 1;
    public static final VendorTag<Key<Integer>> VIDEO_RECORD_STATE = create(Xb.INSTANCE, Integer.class);
    private static Constructor<Key> resultConstructor;

    static /* synthetic */ String Dd() {
        return "com.vidhance.autozoom.bounds";
    }

    static /* synthetic */ String Ed() {
        return "com.vidhance.autozoom.target_bounds_stabilized";
    }

    static /* synthetic */ String Fd() {
        return "com.vidhance.autozoom.delayed_target_bounds_zoomed";
    }

    static /* synthetic */ String Gd() {
        return "xiaomi.video.recordState";
    }

    static /* synthetic */ String Hd() {
        return "xiaomi.faceAnalyzeResult.result";
    }

    static /* synthetic */ String Id() {
        return "xiaomi.faceAnalyzeResult.age";
    }

    static /* synthetic */ String Jd() {
        return "xiaomi.faceAnalyzeResult.gender";
    }

    static /* synthetic */ String Kd() {
        return "xiaomi.faceAnalyzeResult.score";
    }

    static /* synthetic */ String Ld() {
        return "xiaomi.faceAnalyzeResult.prop";
    }

    static /* synthetic */ String Md() {
        return "org.quic.camera2.statsconfigs.AECIsInsensorHDR";
    }

    static /* synthetic */ String Nd() {
        return "xiaomi.scene.result";
    }

    static /* synthetic */ String Od() {
        return b.isMTKPlatform() ? "com.xiaomi.statsconfigs.AecLux" : "com.qti.chi.statsaec.AecLux";
    }

    static /* synthetic */ String Pd() {
        return "com.vidhance.autozoom.target_bounds_zoomed";
    }

    static /* synthetic */ String Qd() {
        return "xiaomi.ai.asd.enabled";
    }

    static /* synthetic */ String Rd() {
        return "xiaomi.ai.asd.sceneDetected";
    }

    static /* synthetic */ String Sd() {
        return "com.vidhance.autozoom.status";
    }

    static /* synthetic */ String Td() {
        return "com.vidhance.autozoom.active_objects";
    }

    static /* synthetic */ String Ud() {
        return "com.vidhance.autozoom.selected_objects";
    }

    static /* synthetic */ String Vd() {
        return "com.vidhance.autozoom.paused_objects";
    }

    static /* synthetic */ String Wd() {
        return "com.vidhance.autozoom.object_bounds_stabilized";
    }

    static /* synthetic */ String Xd() {
        return "com.vidhance.autozoom.object_bounds_zoomed";
    }

    static /* synthetic */ String Yd() {
        return "com.vidhance.autozoom.delayed_target_bounds_stabilized";
    }

    static /* synthetic */ String ce() {
        return "xiaomi.hdr.hdrDetected";
    }

    private static <T> VendorTag<Key<T>> create(final Supplier<String> supplier, final Class<T> cls) {
        return new VendorTag<Key<T>>() {
            /* access modifiers changed from: protected */
            public Key<T> create() {
                return CaptureResultVendorTags.resultKey(getName(), cls);
            }

            public String getName() {
                return (String) supplier.get();
            }
        };
    }

    static /* synthetic */ String de() {
        return "xiaomi.ai.add.lensDirtyDetected";
    }

    static /* synthetic */ String ee() {
        return b.isMTKPlatform() ? "xiaomi.camera.awb.colorTemperature" : "org.quic.camera2.statsconfigs.AWBFrameControl";
    }

    static /* synthetic */ String fe() {
        return "org.quic.camera2.statsconfigs.AECFrameControl";
    }

    static /* synthetic */ String ge() {
        return "org.quic.camera2.statsconfigs.AFFrameControl";
    }

    static /* synthetic */ String he() {
        return "xiaomi.smoothTransition.result";
    }

    static /* synthetic */ String ie() {
        return "xiaomi.hdr.hdrChecker";
    }

    static /* synthetic */ String je() {
        return "xiaomi.ai.misd.ultraWideRecommended";
    }

    static /* synthetic */ String ke() {
        return "xiaomi.beauty.bodySlimCnt";
    }

    static /* synthetic */ String le() {
        return "xiaomi.superResolution.enabled";
    }

    static /* synthetic */ String me() {
        return "xiaomi.hdr.enabled";
    }

    static /* synthetic */ String ne() {
        return "xiaomi.remosaic.detected";
    }

    static /* synthetic */ String oe() {
        return "xiaomi.ai.misd.SemanticScene";
    }

    static /* synthetic */ String pe() {
        return "xiaomi.ai.misd.NonSemanticScene";
    }

    public static void preload() {
        Log.d(TAG, "preloading...");
    }

    static /* synthetic */ String qe() {
        return "xiaomi.ai.misd.StateScene";
    }

    static /* synthetic */ String re() {
        return "xiaomi.distortion.distortioFpcData";
    }

    /* access modifiers changed from: private */
    public static <T> Key<T> resultKey(String str, Class<T> cls) {
        try {
            if (resultConstructor == null) {
                resultConstructor = Key.class.getConstructor(new Class[]{String.class, cls.getClass()});
                resultConstructor.setAccessible(true);
            }
            return (Key) resultConstructor.newInstance(new Object[]{str, cls});
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot find/call Key constructor: ");
            sb.append(e2.getMessage());
            Log.d(TAG, sb.toString());
            return null;
        }
    }

    static /* synthetic */ String se() {
        return "xiaomi.smoothTransition.masterCameraId";
    }

    static /* synthetic */ String te() {
        return "xiaomi.sat.dbg.satDbgInfo";
    }

    static /* synthetic */ String ue() {
        return "xiaomi.ai.misd.hdrmotionDetected";
    }
}
