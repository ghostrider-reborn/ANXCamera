package com.android.camera2.vendortag;

import android.graphics.Rect;
import android.hardware.camera2.CameraCharacteristics.Key;
import android.hardware.camera2.params.StreamConfiguration;
import android.util.Log;
import com.android.camera2.vendortag.struct.SlowMotionVideoConfiguration;
import com.mi.config.b;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class CameraCharacteristicsVendorTags {
    public static final VendorTag<Key<Boolean>> ADAPTIVE_SNAPSHOT_SIZE_IN_SAT_MODE_SUPPORTED = create(C0074t.INSTANCE, Boolean.class);
    public static final VendorTag<Key<int[]>> AI_SCENE_AVAILABLE_MODES = create(C0036g.INSTANCE, int[].class);
    public static final VendorTag<Key<Boolean>> BEAUTY_MAKEUP = create(C0030e.INSTANCE, Boolean.class);
    public static final VendorTag<Key<Byte>> BEAUTY_VERSION = create(C0054m.INSTANCE, Byte.class);
    public static final VendorTag<Key<byte[]>> CAM_CALIBRATION_DATA = create(C0080v.INSTANCE, byte[].class);
    public static final VendorTag<Key<int[]>> CUSTOM_HFR_FPS_TABLE = create(C0039h.INSTANCE, int[].class);
    public static final VendorTag<Key<Byte>> EIS_PREVIEW_SUPPORTED = create(C0057n.INSTANCE, Byte.class);
    public static final VendorTag<Key<int[]>> EXTRA_HIGH_SPEED_VIDEO_CONFIGURATIONS = create(C0021b.INSTANCE, int[].class);
    public static final VendorTag<Key<Integer>> EXTRA_HIGH_SPEED_VIDEO_NUMBER = create(C0018a.INSTANCE, Integer.class);
    public static final VendorTag<Key<Byte>> FOVC_SUPPORTED = create(C0066q.INSTANCE, Byte.class);
    public static final VendorTag<Key<Byte>> IS_QCFA_SENSOR = create(C0060o.INSTANCE, Byte.class);
    public static final VendorTag<Key<Byte>> MFNR_BOKEH_SUPPORTED = create(C0042i.INSTANCE, Byte.class);
    public static final VendorTag<Key<Float>> MI_ALGO_ASD_VERSION = create(C0024c.INSTANCE, Float.class);
    public static final VendorTag<Key<Rect>> QCFA_ACTIVE_ARRAY_SIZE = create(C0027d.INSTANCE, Rect.class);
    public static final VendorTag<Key<StreamConfiguration[]>> QCFA_STREAM_CONFIGURATIONS = create(C0063p.INSTANCE, StreamConfiguration[].class);
    public static final VendorTag<Key<StreamConfiguration[]>> SCALER_AVAILABLE_LIMIT_STREAM_CONFIGURATIONS = create(C0045j.INSTANCE, StreamConfiguration[].class);
    public static final VendorTag<Key<StreamConfiguration[]>> SCALER_AVAILABLE_SR_STREAM_CONFIGURATIONS = create(r.INSTANCE, StreamConfiguration[].class);
    public static final VendorTag<Key<StreamConfiguration[]>> SCALER_AVAILABLE_STREAM_CONFIGURATIONS = create(C0033f.INSTANCE, StreamConfiguration[].class);
    public static final VendorTag<Key<Integer>> SCREEN_LIGHT_BRIGHTNESS = create(C0077u.INSTANCE, Integer.class);
    public static final VendorTag<Key<SlowMotionVideoConfiguration[]>> SLOW_MOTION_VIDEO_CONFIGURATIONS = create(C0071s.INSTANCE, SlowMotionVideoConfiguration[].class);
    private static final String TAG = "CameraCharacteristicsVendorTags";
    public static final VendorTag<Key<Byte>> TELE_OIS_SUPPORTED = create(C0048k.INSTANCE, Byte.class);
    public static final VendorTag<Key<Boolean>> VIDEO_BEAUTY = create(C0051l.INSTANCE, Boolean.class);
    private static Constructor<Key> characteristicsConstructor;

    static /* synthetic */ String Dd() {
        return b.isMTKPlatform() ? "com.xiaomi.ai.asd.availableSceneMode" : "xiaomi.ai.asd.availableSceneMode";
    }

    static /* synthetic */ String Ed() {
        return b.isMTKPlatform() ? "com.xiaomi.qcfa.supported" : "org.codeaurora.qcamera3.quadra_cfa.is_qcfa_sensor";
    }

    static /* synthetic */ String Fd() {
        return b.isMTKPlatform() ? "com.xiaomi.scaler.availableSuperResolutionStreamConfigurations" : "xiaomi.scaler.availableSuperResolutionStreamConfigurations";
    }

    static /* synthetic */ String Gd() {
        return "com.xiaomi.camera.algoup.dualCalibrationData";
    }

    static /* synthetic */ String Hd() {
        return b.isMTKPlatform() ? "com.xiaomi.capabilities.videoStabilization.previewSupported" : "xiaomi.capabilities.videoStabilization.previewSupported";
    }

    static /* synthetic */ String Id() {
        return b.isMTKPlatform() ? "com.xiaomi.flash.screenLight.brightness" : "xiaomi.flash.screenLight.brightness";
    }

    static /* synthetic */ String Jd() {
        return b.isMTKPlatform() ? "com.xiaomi.capabilities.mfnr_bokeh_supported" : "xiaomi.capabilities.mfnr_bokeh_supported";
    }

    static /* synthetic */ String Kd() {
        return "com.xiaomi.camera.supportedfeatures.fovcEnable";
    }

    static /* synthetic */ String Ld() {
        return "com.xiaomi.camera.supportedfeatures.beautyVersion";
    }

    static /* synthetic */ String Md() {
        return b.isMTKPlatform() ? "com.xiaomi.capabilities.satAdaptiveSnapshotSizeSupported" : "xiaomi.capabilities.satAdaptiveSnapshotSizeSupported";
    }

    static /* synthetic */ String Nd() {
        return "org.codeaurora.qcamera3.additional_hfr_video_sizes.valid_number";
    }

    static /* synthetic */ String Od() {
        return "com.mediatek.smvrfeature.availableSmvrModes";
    }

    static /* synthetic */ String Pd() {
        return "com.xiaomi.camera.supportedfeatures.videoBeauty";
    }

    static /* synthetic */ String Qd() {
        return "xiaomi.ai.misd.MiAlgoAsdVersion";
    }

    static /* synthetic */ String Rd() {
        return "com.xiaomi.camera.supportedfeatures.TeleOisSupported";
    }

    static /* synthetic */ String Sd() {
        return "com.xiaomi.camera.supportedfeatures.beautyMakeup";
    }

    static /* synthetic */ String Td() {
        return "org.codeaurora.qcamera3.additional_hfr_video_sizes.hfr_video_size";
    }

    static /* synthetic */ String Ud() {
        return b.isMTKPlatform() ? "com.mediatek.streamingfeature.availableHfpsMaxResolutions" : "org.quic.camera2.customhfrfps.info.CustomHFRFpsTable";
    }

    static /* synthetic */ String Vd() {
        return b.isMTKPlatform() ? "com.xiaomi.scaler.availableStreamConfigurations" : "xiaomi.scaler.availableStreamConfigurations";
    }

    static /* synthetic */ String Wd() {
        return b.isMTKPlatform() ? "com.xiaomi.scaler.availableLimitStreamConfigurations" : "xiaomi.scaler.availableLimitStreamConfigurations";
    }

    static /* synthetic */ String Xd() {
        return "org.codeaurora.qcamera3.quadra_cfa.activeArraySize";
    }

    static /* synthetic */ String Yd() {
        return "org.codeaurora.qcamera3.quadra_cfa.availableStreamConfigurations";
    }

    /* access modifiers changed from: private */
    public static <T> Key<T> characteristicsKey(String str, Class<T> cls) {
        try {
            if (characteristicsConstructor == null) {
                characteristicsConstructor = Key.class.getConstructor(new Class[]{String.class, cls.getClass()});
                characteristicsConstructor.setAccessible(true);
            }
            return (Key) characteristicsConstructor.newInstance(new Object[]{str, cls});
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot find/call Key constructor: ");
            sb.append(e2.getMessage());
            Log.d(TAG, sb.toString());
            return null;
        }
    }

    private static <T> VendorTag<Key<T>> create(final Supplier<String> supplier, final Class<T> cls) {
        return new VendorTag<Key<T>>() {
            /* access modifiers changed from: protected */
            public Key<T> create() {
                return CameraCharacteristicsVendorTags.characteristicsKey(getName(), cls);
            }

            public String getName() {
                return (String) supplier.get();
            }
        };
    }

    public static void preload() {
        Log.d(TAG, "preloading...");
    }
}
