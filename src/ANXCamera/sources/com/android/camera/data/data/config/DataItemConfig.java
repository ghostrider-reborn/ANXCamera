package com.android.camera.data.data.config;

import android.text.TextUtils;
import com.android.camera.data.data.DataItemBase;
import com.android.camera2.CameraCapabilities;
import com.mi.config.b;

public class DataItemConfig extends DataItemBase {
    public static final String DATA_CONFIG_AI_SCENE = "pref_camera_ai_scene_mode_key";
    public static final String DATA_CONFIG_BEAUTY_CAPTURE = "pref_beautify_level_key_capture";
    public static final String DATA_CONFIG_BEAUTY_VIDEO = "pref_beautify_level_key_video";
    public static final String DATA_CONFIG_FOCUS_MODE = "pref_camera_focus_mode_key";
    public static final String DATA_CONFIG_FOCUS_SWITCHING = "pref_qc_focus_mode_switching_key";
    public static final String DATA_CONFIG_MACRO_SCENE = "pref_camera_macro_scene_mode_key";
    public static final String DATA_CONFIG_NEW_SLOW_MOTION_KEY = "key_new_slow_motion";
    public static final String DATA_CONFIG_RATIO = "pref_camera_picturesize_key";
    public static final String DATA_CONFIG_RATIO_SQUARE = "is_square";
    public static final String DATA_CONFIG_RAW = "pref_camera_raw_key";
    public static final String DATA_CONFIG_SLOW_MOTION_QUALITY = "pref_video_new_slow_motion_key";
    public static final String DATA_CONFIG_STICKER_PATH = "pref_sticker_path_key";
    public static final String DATA_CONFIG_VIDEO_BOKEH = "pref_video_bokeh_key";
    public static final String DATA_CONFIG_VIDEO_QUALITY = "pref_video_quality_key";
    public static final String KEY = "camera_settings_simple_mode_local_";
    private int mCameraId;
    private ComponentConfigBokeh mComponentBokeh = new ComponentConfigBokeh(this);
    private ComponentConfigAi mComponentConfigAi;
    private ComponentConfigBeauty mComponentConfigBeauty;
    private ComponentConfigMeter mComponentConfigMeter;
    private ComponentConfigRatio mComponentConfigRatio;
    private ComponentConfigRaw mComponentConfigRaw;
    private ComponentConfigSlowMotionQuality mComponentConfigSlowMotionQuality;
    private ComponentConfigUltraWide mComponentConfigUltraWide;
    private ComponentConfigVideoQuality mComponentConfigVideoQuality;
    private ComponentConfigFlash mComponentFlash = new ComponentConfigFlash(this);
    private ComponentConfigHdr mComponentHdr = new ComponentConfigHdr(this);
    private int mIntentType;
    private ComponentManuallyDualLens mManuallyDualLens;
    private ComponentManuallyFocus mManuallyFocus;
    private ComponentConfigSlowMotion mSlowMotion;

    public DataItemConfig(int i, int i2) {
        this.mCameraId = i;
        this.mIntentType = i2;
        this.mComponentConfigBeauty = new ComponentConfigBeauty(this, i);
        this.mSlowMotion = new ComponentConfigSlowMotion(this);
        this.mComponentConfigRatio = new ComponentConfigRatio(this);
        this.mComponentConfigAi = new ComponentConfigAi(this);
        this.mComponentConfigRaw = new ComponentConfigRaw(this);
        this.mComponentConfigSlowMotionQuality = new ComponentConfigSlowMotionQuality(this);
        this.mComponentConfigMeter = new ComponentConfigMeter(this);
        this.mComponentConfigVideoQuality = new ComponentConfigVideoQuality(this);
    }

    public static int provideLocalId(int i, int i2) {
        return i2 == 0 ? i : i + 100;
    }

    public ComponentConfigBokeh getComponentBokeh() {
        return this.mComponentBokeh;
    }

    public ComponentConfigAi getComponentConfigAi() {
        return this.mComponentConfigAi;
    }

    public ComponentConfigBeauty getComponentConfigBeauty() {
        return this.mComponentConfigBeauty;
    }

    public ComponentConfigMeter getComponentConfigMeter() {
        return this.mComponentConfigMeter;
    }

    public ComponentConfigRatio getComponentConfigRatio() {
        return this.mComponentConfigRatio;
    }

    public ComponentConfigRaw getComponentConfigRaw() {
        return this.mComponentConfigRaw;
    }

    public ComponentConfigSlowMotion getComponentConfigSlowMotion() {
        return this.mSlowMotion;
    }

    public ComponentConfigSlowMotionQuality getComponentConfigSlowMotionQuality() {
        return this.mComponentConfigSlowMotionQuality;
    }

    public ComponentConfigUltraWide getComponentConfigUltraWide() {
        if (this.mComponentConfigUltraWide == null) {
            this.mComponentConfigUltraWide = new ComponentConfigUltraWide(this);
        }
        return this.mComponentConfigUltraWide;
    }

    public ComponentConfigVideoQuality getComponentConfigVideoQuality() {
        return this.mComponentConfigVideoQuality;
    }

    public ComponentConfigFlash getComponentFlash() {
        return this.mComponentFlash;
    }

    public ComponentConfigHdr getComponentHdr() {
        return this.mComponentHdr;
    }

    public ComponentManuallyDualLens getManuallyDualLens() {
        if (this.mManuallyDualLens == null) {
            this.mManuallyDualLens = new ComponentManuallyDualLens(this);
        }
        return this.mManuallyDualLens;
    }

    public ComponentManuallyFocus getManuallyFocus() {
        if (this.mManuallyFocus == null) {
            this.mManuallyFocus = new ComponentManuallyFocus(this);
        }
        return this.mManuallyFocus;
    }

    public boolean isSwitchOn(String str) {
        return getBoolean(str, false);
    }

    public boolean isTransient() {
        return false;
    }

    public String provideKey() {
        return "camera_settings_simple_mode_local_" + provideLocalId(this.mCameraId, this.mIntentType);
    }

    public boolean reConfigBokehIfHdrChanged(int i, String str) {
        if (TextUtils.isEmpty(str) || "off".equals(str) || !"on".equals(this.mComponentBokeh.getComponentValue(i))) {
            return false;
        }
        this.mComponentBokeh.toggle(i);
        return true;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004f, code lost:
        if (r8 != 3) goto L_0x0075;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0056, code lost:
        if (r1.equals(r0) != false) goto L_0x0075;
     */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0082  */
    public boolean reConfigFlashIfHdrChanged(int i, String str) {
        char c2;
        String persistValue = getComponentFlash().getPersistValue(i);
        switch (str.hashCode()) {
            case -1039745817:
                if (str.equals("normal")) {
                    c2 = 2;
                    break;
                }
            case 3551:
                if (str.equals("on")) {
                    c2 = 1;
                    break;
                }
            case 109935:
                if (str.equals("off")) {
                    c2 = 4;
                    break;
                }
            case 3005871:
                if (str.equals("auto")) {
                    c2 = 0;
                    break;
                }
            case 3322092:
                if (str.equals("live")) {
                    c2 = 3;
                    break;
                }
            default:
                c2 = 65535;
                break;
        }
        String str2 = "0";
        if (c2 != 0) {
            if (c2 != 1) {
                if (c2 != 2) {
                }
            }
        } else if ("1".equals(persistValue) || "2".equals(persistValue)) {
            if (b.Zi()) {
                str2 = "3";
            }
            if (str2 != null || persistValue.equals(str2)) {
                return false;
            }
            getComponentFlash().setComponentValue(i, str2);
            return !getComponentFlash().isEmpty();
        } else if (ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_ON.equals(persistValue)) {
            str2 = ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_AUTO;
            if (str2 != null) {
            }
            return false;
        }
        str2 = null;
        if (str2 != null) {
        }
        return false;
    }

    public boolean reConfigHdrIfBokehChanged(int i, String str) {
        if (!"on".equals(str) || "off".equals(this.mComponentHdr.getComponentValue(i))) {
            return false;
        }
        this.mComponentHdr.setComponentValue(i, "off");
        return true;
    }

    public boolean reConfigHhrIfFlashChanged(int i, String str) {
        String persistValue = getComponentHdr().getPersistValue(i);
        String str2 = "off";
        if ("3".equals(str) || ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_AUTO.equals(str)) {
            if ("normal".equals(persistValue) || "live".equals(persistValue)) {
                if (getComponentHdr().isSupportAutoHdr()) {
                    str2 = "auto";
                }
                if (str2 != null || persistValue.equals(str2)) {
                    return false;
                }
                getComponentHdr().setComponentValue(i, str2);
                return !getComponentHdr().isEmpty();
            }
        } else if (!"1".equals(str)) {
        }
        str2 = null;
        if (str2 != null) {
        }
        return false;
    }

    public void reInitComponent(int i, int i2, CameraCapabilities cameraCapabilities) {
        getComponentConfigUltraWide().reInit(i, i2, cameraCapabilities);
        this.mComponentFlash.reInit(i, i2, cameraCapabilities, getComponentConfigUltraWide());
        this.mComponentHdr.reInit(i, i2, cameraCapabilities);
        this.mComponentBokeh.reInit(i, i2);
        this.mComponentConfigAi.reInit(i, i2, cameraCapabilities);
        this.mComponentConfigRaw.reInit(i, i2, cameraCapabilities);
        this.mComponentConfigRatio.reInit(i, i2, cameraCapabilities);
        this.mComponentConfigSlowMotionQuality.reInit(i, i2, cameraCapabilities, this.mSlowMotion);
        this.mComponentConfigMeter.reInit(i, i2, cameraCapabilities);
        this.mComponentConfigVideoQuality.reInit(i, i2, cameraCapabilities, this.mIntentType);
    }

    public void resetAll() {
        editor().clear().apply();
        getComponentFlash().clearClosed();
        getComponentHdr().clearClosed();
        getComponentConfigBeauty().clearClosed();
    }

    public boolean supportAi() {
        return !this.mComponentConfigAi.isEmpty();
    }

    public boolean supportBokeh() {
        return !this.mComponentBokeh.isEmpty();
    }

    public boolean supportFlash() {
        return !this.mComponentFlash.isEmpty();
    }

    public boolean supportHdr() {
        return !this.mComponentHdr.isEmpty();
    }

    public boolean supportMeter() {
        return !this.mComponentConfigMeter.isEmpty();
    }

    public boolean supportRatio() {
        return this.mComponentConfigRatio.supportRatioSwitch();
    }

    public boolean supportSlowMotion() {
        return this.mSlowMotion.supportSlowMotionSwitch();
    }

    public boolean supportVideoQuality() {
        return !this.mComponentConfigVideoQuality.isEmpty();
    }

    public void switchOff(String str) {
        putBoolean(str, false);
    }

    public void switchOn(String str) {
        putBoolean(str, true);
    }
}
