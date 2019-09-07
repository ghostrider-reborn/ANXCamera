package miui.os;

import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.SystemProperties;
import miui.util.FeatureParser;

public class DeviceFeature {
    public static final int BACKLIGHT_BIT = FeatureParser.getInteger("BACKLIGHT_BIT", 0);
    public static final boolean PERSIST_SCREEN_EFFECT = SystemProperties.getBoolean("sys.persist_screen_effect", false);
    public static final boolean SCREEN_EFFECT_CONFLICT = ((SystemProperties.getInt("ro.df.effect.conflict", 0) == 0 || SystemProperties.getInt("ro.vendor.df.effect.conflict", 0) == 0) ? false : false);
    public static final boolean SUPPORT_3D_GESTURE;
    public static final boolean SUPPORT_AUTO_BRIGHTNESS_OPTIMIZE = ((FeatureParser.getBoolean("support_autobrightness_optimize", false) && VERSION.SDK_INT > 23) || SystemProperties.getBoolean("sys.autobrightness_optimize", false));
    public static final boolean SUPPORT_CAMERA_ANIMATION;
    public static final boolean SUPPORT_DISPLAYFEATURE_CALLBACK = FeatureParser.getBoolean("SUPPORT_DISPLAYFEATURE_CALLBACK", true);
    public static final boolean SUPPORT_DISPLAYFEATURE_HIDL = SystemProperties.getBoolean("sys.displayfeature_hidl", false);
    public static final boolean SUPPORT_GAME_MODE = FeatureParser.getBoolean("support_touchfeature_gamemode", false);
    public static final boolean SUPPORT_LAB_GESTURE = ((!"sagit".equals(Build.DEVICE) || Build.IS_STABLE_VERSION) ? false : false);
    public static final boolean SUPPORT_NIGHT_LIGHT = FeatureParser.getBoolean("SUPPORT_NIGHT_LIGHT", true);
    public static final boolean SUPPORT_NIGHT_LIGHT_ADJ = FeatureParser.getBoolean("SUPPORT_NIGHT_LIGHT_ADJ", true);
    public static final boolean SUPPORT_PAPERMODE_ANIMATION = FeatureParser.getBoolean("support_papermode_animation", false);
    public static final Resources SYSTEM_RESOURCES = Resources.getSystem();

    static {
        boolean z;
        boolean z2 = false;
        if (!"pyxis".equals(Build.DEVICE)) {
            if (!"vela".equals(Build.DEVICE)) {
                z = false;
                SUPPORT_CAMERA_ANIMATION = z;
                if ("cepheus".equals(Build.DEVICE) && !Build.IS_INTERNATIONAL_BUILD) {
                    z2 = false;
                }
                SUPPORT_3D_GESTURE = z2;
            }
        }
        z = false;
        SUPPORT_CAMERA_ANIMATION = z;
        z2 = false;
        SUPPORT_3D_GESTURE = z2;
    }

    public static final boolean hasMirihiSupport() {
        if (!"perseus".equals(Build.DEVICE)) {
            if (!"andromeda".equals(Build.DEVICE)) {
                return false;
            }
        }
        return true;
    }

    public static final boolean hasPopupCameraSupport() {
        if (!"raphael".equals(Build.DEVICE)) {
            if (!"davinci".equals(Build.DEVICE)) {
                if (!"raphaelin".equals(Build.DEVICE)) {
                    if (!"davinciin".equals(Build.DEVICE)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static final boolean hasSupportAudioPromity() {
        return SystemProperties.getBoolean("ro.vendor.audio.us.proximity", false) || SystemProperties.getBoolean("ro.audio.us.proximity", false);
    }
}
