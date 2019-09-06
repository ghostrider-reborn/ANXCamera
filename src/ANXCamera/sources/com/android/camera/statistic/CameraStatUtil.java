package com.android.camera.statistic;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.provider.MiuiSettings.System;
import android.util.SparseArray;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.MutexModeManager;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigFlash;
import com.android.camera.data.data.config.ComponentConfigSlowMotion;
import com.android.camera.data.data.config.ComponentConfigVideoQuality;
import com.android.camera.data.data.config.ComponentManuallyDualLens;
import com.android.camera.effect.FilterInfo;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.miui.filtersdk.filter.helper.FilterType;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CameraStatUtil {
    private static final String AUTO = "auto";
    public static final String AUTO_OFF = "auto-off";
    public static final String AUTO_ON = "auto-on";
    public static final String NONE = "none";
    private static final String OTHERS = "others";
    private static final String TAG = "CameraStatUtil";
    private static HashMap<String, String> sBeautyTypeToName = new HashMap<>();
    private static SparseArray<String> sCameraModeIdToName = new SparseArray<>();
    private static SparseArray<String> sExposureTimeLessThan1sToName = new SparseArray<>();
    private static SparseArray<String> sFilterTypeToName = new SparseArray<>();
    private static SparseArray<String> sPictureQualityIndexToName = new SparseArray<>();
    private static SparseArray<String> sTriggerModeIdToName = new SparseArray<>();

    static {
        sCameraModeIdToName.put(161, "小视频");
        sCameraModeIdToName.put(174, "Live视频");
        sCameraModeIdToName.put(177, "萌拍");
        sCameraModeIdToName.put(163, "拍照");
        sCameraModeIdToName.put(165, CameraStat.PARAM_SQUARE);
        sCameraModeIdToName.put(167, "手动");
        sCameraModeIdToName.put(171, "人像");
        String str = "全景";
        sCameraModeIdToName.put(166, str);
        sCameraModeIdToName.put(176, str);
        sCameraModeIdToName.put(172, "慢动作");
        String str2 = "录像";
        sCameraModeIdToName.put(162, str2);
        sCameraModeIdToName.put(169, str2);
        sCameraModeIdToName.put(173, "夜景");
        sCameraModeIdToName.put(175, "超清");
        sTriggerModeIdToName.put(10, "拍照键");
        sTriggerModeIdToName.put(20, CameraStat.ZOOM_MODE_VOLUME);
        sTriggerModeIdToName.put(30, "指纹");
        sTriggerModeIdToName.put(40, "相机键");
        sTriggerModeIdToName.put(50, "dpad键");
        sTriggerModeIdToName.put(60, "物体追踪");
        sTriggerModeIdToName.put(70, "声控快门");
        sTriggerModeIdToName.put(80, "长按屏幕");
        sTriggerModeIdToName.put(90, "曝光环");
        sTriggerModeIdToName.put(100, CameraStat.PARAM_PALM_SHUTTER);
        sPictureQualityIndexToName.put(0, "最低");
        sPictureQualityIndexToName.put(1, "更低");
        sPictureQualityIndexToName.put(2, "低");
        sPictureQualityIndexToName.put(3, "标准");
        sPictureQualityIndexToName.put(4, "高");
        sPictureQualityIndexToName.put(5, "更高");
        sPictureQualityIndexToName.put(6, "最高");
        sExposureTimeLessThan1sToName.put(0, "auto");
        sExposureTimeLessThan1sToName.put(1000, "1/1000s");
        sExposureTimeLessThan1sToName.put(2000, "1/500s");
        String str3 = "1/250s";
        sExposureTimeLessThan1sToName.put(System.STATUS_BAR_UPDATE_NETWORK_SPEED_INTERVAL_DEFAULT, str3);
        sExposureTimeLessThan1sToName.put(5000, str3);
        sExposureTimeLessThan1sToName.put(BaseModule.LENS_DIRTY_DETECT_HINT_DURATION, "1/125s");
        sExposureTimeLessThan1sToName.put(16667, "1/60s");
        sExposureTimeLessThan1sToName.put(33333, "1/30s");
        sExposureTimeLessThan1sToName.put(66667, "1/15s");
        sExposureTimeLessThan1sToName.put(125000, "1/8s");
        sExposureTimeLessThan1sToName.put(250000, "1/4s");
        sExposureTimeLessThan1sToName.put(500000, "1/2s");
        sExposureTimeLessThan1sToName.put(1000000, "1s");
        sExposureTimeLessThan1sToName.put(2000000, "2s");
        sExposureTimeLessThan1sToName.put(4000000, "4s");
        sExposureTimeLessThan1sToName.put(8000000, "8s");
        sExposureTimeLessThan1sToName.put(16000000, "16s");
        sExposureTimeLessThan1sToName.put(32000000, "32s");
        sBeautyTypeToName.put("pref_beautify_skin_color_ratio_key", CameraStat.PARAM_BEAUTY_SKIN_COLOR);
        sBeautyTypeToName.put("pref_beautify_slim_face_ratio_key", CameraStat.PARAM_BEAUTY_SLIM_FACE);
        sBeautyTypeToName.put("pref_beautify_enlarge_eye_ratio_key", CameraStat.PARAM_BEAUTY_ENLARGE_EYE);
        sBeautyTypeToName.put("pref_beautify_skin_smooth_ratio_key", CameraStat.PARAM_BEAUTY_SKIN_SMOOTH);
        sBeautyTypeToName.put("pref_beautify_nose_ratio_key", CameraStat.PARAM_BEAUTY_NOSE);
        sBeautyTypeToName.put("pref_beautify_risorius_ratio_key", CameraStat.PARAM_BEAUTY_RISORIUS);
        sBeautyTypeToName.put("pref_beautify_lips_ratio_key", CameraStat.PARAM_BEAUTY_LIPS);
        sBeautyTypeToName.put("pref_beautify_chin_ratio_key", CameraStat.PARAM_BEAUTY_CHIN);
        sBeautyTypeToName.put("pref_beautify_neck_ratio_key", CameraStat.PARAM_BEAUTY_NECK);
        sBeautyTypeToName.put("pref_beautify_eyebrow_dye_ratio_key", CameraStat.PARAM_BEAUTY_EYEBROW_DYE);
        sBeautyTypeToName.put("pref_beautify_pupil_line_ratio_key", CameraStat.PARAM_BEAUTY_PUPIL_LINE);
        sBeautyTypeToName.put("pref_beautify_jelly_lips_ratio_key", CameraStat.PARAM_BEAUTY_JELLY_LIPS);
        sBeautyTypeToName.put("pref_beautify_blusher_ratio_key", CameraStat.PARAM_BEAUTY_BLUSHER);
        sBeautyTypeToName.put("pref_beautify_smile_ratio_key", CameraStat.PARAM_BEAUTY_SMILE);
        sBeautyTypeToName.put("pref_beautify_slim_nose_ratio_key", CameraStat.PARAM_BEAUTY_SLIM_NOSE);
        sFilterTypeToName.put(FilterType.N_BERRY.ordinal(), "浆果");
        sFilterTypeToName.put(FilterType.N_COOKIE.ordinal(), "曲奇");
        sFilterTypeToName.put(FilterType.N_DELICACY.ordinal(), "美味");
        sFilterTypeToName.put(FilterType.N_FADE.ordinal(), "褪色");
        sFilterTypeToName.put(FilterType.N_FILM.ordinal(), "胶片(拍照)");
        sFilterTypeToName.put(FilterType.N_KOIZORA.ordinal(), "恋空");
        sFilterTypeToName.put(FilterType.N_LATTE.ordinal(), "拿铁");
        sFilterTypeToName.put(FilterType.N_LIGHT.ordinal(), "浮光");
        sFilterTypeToName.put(FilterType.N_LIVELY.ordinal(), "生动");
        sFilterTypeToName.put(FilterType.N_QUIET.ordinal(), "静谧");
        sFilterTypeToName.put(FilterType.N_SODA.ordinal(), "汽水");
        sFilterTypeToName.put(FilterType.N_WARM.ordinal(), "暖阳");
        sFilterTypeToName.put(FilterType.B_FAIRYTALE.ordinal(), "童话");
        sFilterTypeToName.put(FilterType.B_JAPANESE.ordinal(), "日系");
        sFilterTypeToName.put(FilterType.B_MINT.ordinal(), "薄荷");
        sFilterTypeToName.put(FilterType.B_MOOD.ordinal(), "心境");
        sFilterTypeToName.put(FilterType.B_NATURE.ordinal(), "自然");
        sFilterTypeToName.put(FilterType.B_PINK.ordinal(), "粉嫩");
        sFilterTypeToName.put(FilterType.B_ROMANCE.ordinal(), "浪漫");
        sFilterTypeToName.put(FilterType.B_MAZE.ordinal(), "迷宫");
        sFilterTypeToName.put(FilterType.B_WHITEANDBLACK.ordinal(), "黑白(人像)");
        sFilterTypeToName.put(FilterType.S_FILM.ordinal(), "胶片(录像)");
        sFilterTypeToName.put(FilterType.S_YEARS.ordinal(), "那些年");
        sFilterTypeToName.put(FilterType.S_POLAROID.ordinal(), "拍立得");
        sFilterTypeToName.put(FilterType.S_FOREST.ordinal(), "小森林");
        sFilterTypeToName.put(FilterType.S_BYGONE.ordinal(), "往事");
        sFilterTypeToName.put(FilterType.S_WHITEANDBLACK.ordinal(), "黑白(录像)");
        sFilterTypeToName.put(FilterType.N_WHITEANDBLACK.ordinal(), "黑白(拍照)");
    }

    private static String addCameraSuffix(String str) {
        if (str == null) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("_");
        sb.append(CameraSettings.isFrontCamera() ? "front" : "back");
        return sb.toString();
    }

    private static void addUltraPixelParameter(boolean z, Map<String, String> map) {
        boolean isUltraPixelOn = CameraSettings.isUltraPixelOn();
        String str = "on";
        String str2 = "off";
        if (!z) {
            int Ja = DataRepository.dataItemFeature().Ja();
            if (Ja == 48000000) {
                if (!isUltraPixelOn) {
                    str = str2;
                }
                map.put(CameraStat.PARAM_ULTRA_PIXEL_48MP, str);
            } else if (Ja == 64144128) {
                if (!isUltraPixelOn) {
                    str = str2;
                }
                map.put(CameraStat.PARAM_ULTRA_PIXEL_64MP, str);
            }
        } else if (DataRepository.dataItemFeature().Da() == 32275200) {
            if (!isUltraPixelOn) {
                str = str2;
            }
            map.put(CameraStat.PARAM_ULTRA_PIXEL_32MP, str);
        }
    }

    private static String antiBandingToName(String str) {
        String str2 = OTHERS;
        if (str == null) {
            Log.e(TAG, "null antiBanding");
            return str2;
        }
        char c2 = 65535;
        switch (str.hashCode()) {
            case 48:
                if (str.equals("0")) {
                    c2 = 0;
                    break;
                }
                break;
            case 49:
                if (str.equals("1")) {
                    c2 = 1;
                    break;
                }
                break;
            case 50:
                if (str.equals("2")) {
                    c2 = 2;
                    break;
                }
                break;
            case 51:
                if (str.equals("3")) {
                    c2 = 3;
                    break;
                }
                break;
        }
        if (c2 == 0) {
            return "off";
        }
        if (c2 == 1) {
            return "50hz";
        }
        if (c2 == 2) {
            return "60hz";
        }
        if (c2 == 3) {
            return "auto";
        }
        String str3 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("unexpected antiBanding ");
        sb.append(str);
        Log.e(str3, sb.toString());
        return str2;
    }

    private static String autoExposureToName(String str) {
        if (str != null) {
            Resources resources = CameraAppImpl.getAndroidContext().getResources();
            if (str.equals(resources.getString(R.string.pref_camera_autoexposure_value_frameaverage))) {
                return "平均测光";
            }
            if (str.equals(resources.getString(R.string.pref_camera_autoexposure_value_centerweighted))) {
                return "中心权重";
            }
            if (str.equals(resources.getString(R.string.pref_camera_autoexposure_value_spotmetering))) {
                return "中点测光";
            }
        }
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("unexpected auto exposure ");
        sb.append(str);
        Log.e(str2, sb.toString());
        return OTHERS;
    }

    private static String autoWhiteBalanceToName(String str) {
        String str2 = OTHERS;
        if (str == null) {
            Log.e(TAG, "null awb");
            return str2;
        }
        char c2 = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -1081415738) {
            if (hashCode != 53) {
                if (hashCode != 54) {
                    switch (hashCode) {
                        case 49:
                            if (str.equals("1")) {
                                c2 = 1;
                                break;
                            }
                            break;
                        case 50:
                            if (str.equals("2")) {
                                c2 = 2;
                                break;
                            }
                            break;
                        case 51:
                            if (str.equals("3")) {
                                c2 = 3;
                                break;
                            }
                            break;
                    }
                } else if (str.equals("6")) {
                    c2 = 5;
                }
            } else if (str.equals("5")) {
                c2 = 4;
            }
        } else if (str.equals("manual")) {
            c2 = 0;
        }
        if (c2 != 0) {
            if (c2 == 1) {
                str = "auto";
            } else if (c2 == 2) {
                return "incandescent";
            } else {
                if (c2 == 3) {
                    return "fluorescent";
                }
                if (c2 == 4) {
                    return "daylight";
                }
                if (c2 == 5) {
                    return "cloudy-daylight";
                }
                String str3 = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("unexpected awb ");
                sb.append(str);
                Log.e(str3, sb.toString());
                return str2;
            }
        }
        return str;
    }

    private static String burstShotNumToName(int i) {
        return divideTo10Section(i);
    }

    public static String cameraIdToName(boolean z) {
        return z ? CameraStat.VALUE_FRONT_CAMERA : CameraStat.VALUE_BACK_CAMERA;
    }

    private static String contrastToName(String str) {
        return pictureQualityToName(R.array.pref_camera_contrast_entryvalues, str);
    }

    private static String divideTo10Section(int i) {
        if (i == 0) {
            return "0";
        }
        switch (i > 0 ? (i - 1) / 10 : 0) {
            case 0:
                return "1+";
            case 1:
                return "10+";
            case 2:
                return "20+";
            case 3:
                return "30+";
            case 4:
                return "40+";
            case 5:
                return "50+";
            case 6:
                return "60+";
            case 7:
                return "70+";
            case 8:
                return "80+";
            default:
                return "90+";
        }
    }

    private static String exposureTimeToName(String str) {
        if (str != null) {
            try {
                int parseLong = (int) (Long.parseLong(str) / 1000);
                if (parseLong < 1000000) {
                    String str2 = (String) sExposureTimeLessThan1sToName.get(parseLong);
                    if (str2 != null) {
                        return str2;
                    }
                } else {
                    int i = parseLong / 1000000;
                    StringBuilder sb = new StringBuilder();
                    sb.append(i);
                    sb.append("s");
                    return sb.toString();
                }
            } catch (NumberFormatException unused) {
                String str3 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("invalid exposure time ");
                sb2.append(str);
                Log.e(str3, sb2.toString());
            }
        }
        String str4 = TAG;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("unexpected exposure time ");
        sb3.append(str);
        Log.e(str4, sb3.toString());
        return "auto";
    }

    public static String faceBeautyRatioToName(int i) {
        return i == 0 ? "0" : divideTo10Section(i);
    }

    private static String filterIdToName(int i) {
        String str = "none";
        if (FilterInfo.FILTER_ID_NONE == i) {
            return str;
        }
        int category = FilterInfo.getCategory(i);
        if (category == 1 || category == 2 || category == 3) {
            String str2 = (String) sFilterTypeToName.get(FilterInfo.getIndex(i));
            if (str2 != null) {
                return str2;
            }
        }
        String str3 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("unexpected filter id: ");
        sb.append(Integer.toHexString(i));
        Log.e(str3, sb.toString());
        return str;
    }

    private static String flashModeToName(String str) {
        String str2 = OTHERS;
        if (str == null) {
            Log.e(TAG, "null flash mode");
            return str2;
        }
        char c2 = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 48626) {
            if (hashCode != 48628) {
                switch (hashCode) {
                    case 48:
                        if (str.equals("0")) {
                            c2 = 5;
                            break;
                        }
                        break;
                    case 49:
                        if (str.equals("1")) {
                            c2 = 1;
                            break;
                        }
                        break;
                    case 50:
                        if (str.equals("2")) {
                            c2 = 4;
                            break;
                        }
                        break;
                    case 51:
                        if (str.equals("3")) {
                            c2 = 0;
                            break;
                        }
                        break;
                }
            } else if (str.equals(ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_AUTO)) {
                c2 = 2;
            }
        } else if (str.equals(ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_ON)) {
            c2 = 3;
        }
        if (c2 == 0) {
            return "auto";
        }
        if (c2 == 1) {
            return "on";
        }
        if (c2 == 2) {
            return "screen-light-auto";
        }
        if (c2 == 3) {
            return "screen-light-on";
        }
        if (c2 == 4) {
            return "torch";
        }
        if (c2 == 5) {
            return "off";
        }
        String str3 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("unexpected flash mode ");
        sb.append(str);
        Log.e(str3, sb.toString());
        return str2;
    }

    private static String focusPositionToName(int i) {
        return 1000 == i ? "auto" : divideTo10Section((1000 - i) / 10);
    }

    private static String getDualZoomName(int i) {
        if (i == 166 || i == 167) {
            String cameraLensType = CameraSettings.getCameraLensType(i);
            if (ComponentManuallyDualLens.LENS_ULTRA.equals(cameraLensType)) {
                return HybridZoomingSystem.STRING_ZOOM_RATIO_ULTR;
            }
            if (ComponentManuallyDualLens.LENS_TELE.equals(cameraLensType)) {
                return HybridZoomingSystem.STRING_ZOOM_RATIO_TELE;
            }
            if (ComponentManuallyDualLens.LENS_WIDE.equals(cameraLensType)) {
                return HybridZoomingSystem.STRING_ZOOM_RATIO_WIDE;
            }
        }
        return HybridZoomingSystem.toString(CameraSettings.readZoom());
    }

    private static int indexOfString(String[] strArr, String str) {
        if (!(strArr == null || str == null)) {
            for (int i = 0; i < strArr.length; i++) {
                if (str.equals(strArr[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static String isoToName(String str) {
        if (str != null) {
            String str2 = "auto";
            if (str2.equalsIgnoreCase(str)) {
                return str2;
            }
            if (str.toUpperCase(Locale.ENGLISH).indexOf("ISO") > -1) {
                str = str.substring(3);
            }
        }
        return str;
    }

    public static String modeIdToName(int i) {
        String str = (String) sCameraModeIdToName.get(i);
        return str == null ? "未知" : str;
    }

    private static String pictureQualityToName(int i, String str) {
        String[] stringArray = CameraAppImpl.getAndroidContext().getResources().getStringArray(i);
        if (sPictureQualityIndexToName.size() >= stringArray.length) {
            int indexOfString = indexOfString(stringArray, str);
            if (indexOfString <= -1) {
                return OTHERS;
            }
            return (String) sPictureQualityIndexToName.get(indexOfString + ((sPictureQualityIndexToName.size() - stringArray.length) / 2));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("picture quality array size is smaller than values size ");
        sb.append(str.length());
        throw new RuntimeException(sb.toString());
    }

    private static long round(long j, int i) {
        if (i <= 0) {
            return j;
        }
        long j2 = (long) i;
        return ((j + ((long) (i / 2))) / j2) * j2;
    }

    private static String saturationToName(String str) {
        return pictureQualityToName(R.array.pref_camera_saturation_entryvalues, str);
    }

    private static String sharpnessToName(String str) {
        return pictureQualityToName(R.array.pref_camera_sharpness_entryvalues, str);
    }

    public static String slowMotionConfigToName(String str) {
        return ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_120.equals(str) ? "点击至120" : ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_240.equals(str) ? "点击至240" : "点击至960";
    }

    private static String slowMotionQualityIdToName(String str) {
        String str2 = OTHERS;
        if (str == null) {
            return str2;
        }
        char c2 = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 53) {
            if (hashCode == 54 && str.equals("6")) {
                c2 = 1;
            }
        } else if (str.equals("5")) {
            c2 = 0;
        }
        return c2 != 0 ? c2 != 1 ? str2 : "1080p" : "720p";
    }

    public static void tarckBokenChanged(int i, String str) {
    }

    private static String timeLapseIntervalToName(int i) {
        if (i < 1000) {
            return String.format(Locale.ENGLISH, "%.2fs", new Object[]{Float.valueOf(((float) i) / 1000.0f)});
        }
        return String.format(Locale.ENGLISH, "%ds", new Object[]{Integer.valueOf(i / 1000)});
    }

    public static void track(String str, String str2, String... strArr) {
    }

    public static void trackAISceneChanged(int i, int i2, Resources resources) {
    }

    public static void trackAwbChanged(String str) {
    }

    public static void trackBeautyBodyCapture() {
    }

    public static void trackBeautyBodyCounter(String str) {
    }

    public static void trackBeautyBodyCounterPort(String str) {
    }

    private static void trackBeautyBodySlim(String str, String str2, String str3) {
    }

    public static void trackBeautyInfo(int i, String str, BeautyValues beautyValues) {
    }

    public static void trackBeautySwitchChanged(int i, String str) {
    }

    public static void trackBokehTaken() {
    }

    public static void trackBroadcastKillService() {
    }

    public static void trackCameraError(String str) {
    }

    public static void trackCameraErrorDialogShow() {
    }

    public static void trackConfigChange(String str, String str2, boolean z, boolean z2, boolean z3) {
    }

    public static void trackDirectionChanged(int i) {
    }

    public static void trackDualWaterMarkChanged(boolean z) {
    }

    public static void trackDualZoomChanged(int i, String str) {
    }

    public static void trackEvAdjusted() {
    }

    public static void trackExposureTimeChanged(String str) {
    }

    public static void trackFilterChanged(int i, int i2, boolean z) {
    }

    public static void trackFlashChanged(int i, String str) {
    }

    public static void trackFocusPositionChanged(int i) {
    }

    public static void trackGeneralInfo(int i, boolean z, int i2, int i3, boolean z2, MutexModeManager mutexModeManager, String str) {
    }

    public static void trackGoogleLensOobeContinue(boolean z) {
    }

    public static void trackGoogleLensPicker() {
    }

    public static void trackGoogleLensPickerValue(boolean z) {
    }

    public static void trackGoogleLensTouchAndHold() {
    }

    public static void trackGotoGallery(int i) {
    }

    public static void trackGotoSettings(int i) {
    }

    public static void trackHdrChanged(int i, String str) {
    }

    public static void trackIsoChanged(String str) {
    }

    public static void trackLensChanged(String str) {
    }

    public static void trackLightingChanged(int i, String str) {
    }

    public static void trackLiveBeautyClick(String str) {
    }

    public static void trackLiveBeautyCounter(String str) {
    }

    public static void trackLiveClick(String str) {
    }

    public static void trackLiveMusicClick() {
    }

    public static void trackLiveRecordingParams(boolean z, String str, boolean z2, String str2, boolean z3, String str3, String str4, boolean z4, int i, int i2, int i3, int i4, boolean z5) {
    }

    public static void trackLiveStickerDownload(String str, boolean z) {
    }

    public static void trackLiveStickerMore(boolean z) {
    }

    public static void trackLiveVideoParams(int i, float f2, boolean z, boolean z2, boolean z3) {
    }

    public static void trackLostCount(int i) {
    }

    public static void trackLyingDirectPictureTaken(int i) {
    }

    public static void trackLyingDirectShow(int i) {
    }

    private static void trackMacroModeTaken(int i) {
    }

    public static void trackMeterClick() {
    }

    public static void trackMimojiCaptureOrRecord(Map map, String str, boolean z, boolean z2) {
    }

    public static void trackMimojiClick(String str) {
    }

    public static void trackMimojiCount(String str) {
    }

    public static void trackMimojiSavePara(String str, Map map) {
    }

    public static void trackModeSwitch() {
    }

    public static void trackMoonMode(boolean z, boolean z2) {
    }

    public static void trackNewSlowMotionVideoRecorded(String str, int i, int i2, int i3, long j) {
    }

    public static void trackPauseVideoRecording(boolean z) {
    }

    public static void trackPictureSize(int i, String str) {
    }

    public static void trackPictureTaken(int i, boolean z, int i2, boolean z2, boolean z3, String str) {
    }

    public static void trackPictureTakenInManual(int i, String str, String str2, String str3, int i2) {
    }

    public static void trackPictureTakenInPanorama(Context context, int i) {
    }

    public static void trackPictureTakenInWideSelfie(int i, String str, String str2) {
    }

    public static void trackPocketModeEnter(String str) {
    }

    public static void trackPocketModeExit(String str) {
    }

    public static void trackPocketModeSensorDelay() {
    }

    public static void trackPreferenceChange(String str, Object obj) {
    }

    public static void trackSelectObject(boolean z) {
    }

    public static void trackSlowMotionQuality(String str, String str2, String str3) {
    }

    public static void trackSnapInfo(boolean z) {
    }

    public static void trackStartAppCost(long j) {
    }

    public static void trackTakePictureCost(long j, boolean z, int i) {
    }

    public static void trackTiltShiftChanged(String str) {
    }

    public static void trackTimerChanged(String str) {
    }

    private static void trackUltraWide(String str) {
    }

    public static void trackUltraWideFunTaken() {
    }

    public static void trackUltraWideManualTaken(int i) {
    }

    public static void trackUltraWidePictureTaken() {
    }

    public static void trackUltraWideVideoTaken() {
    }

    public static void trackUserDefineWatermark() {
    }

    public static void trackVideoModeChanged(String str) {
    }

    public static void trackVideoQuality(String str, boolean z, String str2) {
    }

    public static void trackVideoRecorded(boolean z, int i, boolean z2, boolean z3, boolean z4, String str, int i2, int i3, int i4, int i5, BeautyValues beautyValues, long j) {
    }

    public static void trackVideoSnapshot(boolean z) {
    }

    public static void trackVoiceControl(Intent intent) {
    }

    public static void trackZoomAdjusted(String str) {
    }

    private static String triggerModeToName(int i) {
        return (String) sTriggerModeIdToName.get(i);
    }

    private static String videoQualityToName(String str) {
        if (String.valueOf(8).equals(str)) {
            return "4k";
        }
        if (String.valueOf(6).equals(str)) {
            return "1080p";
        }
        if (String.valueOf(5).equals(str)) {
            return "720p";
        }
        if (String.valueOf(4).equals(str)) {
            return "480p";
        }
        if (ComponentConfigVideoQuality.QUALITY_4K_60FPS.equals(str)) {
            return "4k,60fps";
        }
        if (ComponentConfigVideoQuality.QUALITY_1080P_60FPS.equals(str)) {
            return "1080p,60fps";
        }
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("unexpected video quality: ");
        sb.append(str);
        Log.e(str2, sb.toString());
        return OTHERS;
    }
}
