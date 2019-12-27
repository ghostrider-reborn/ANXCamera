package com.android.camera.data.data.global;

import android.content.Intent;
import android.support.v4.util.Pair;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraSettings;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.DataItemBase;
import com.android.camera.data.provider.DataProvider;
import com.android.camera.log.Log;
import com.mi.config.a;
import com.mi.config.b;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class DataItemGlobal extends DataItemBase {
    public static final String CTA_CAN_CONNECT_NETWORK_BY_IMPUNITY = "can_connect_network";
    public static final String DATA_COMMON_AI_SCENE_HINT = "pref_camera_first_ai_scene_use_hint_shown_key";
    public static final String DATA_COMMON_CURRENT_CAMERA_ID = "pref_camera_id_key";
    public static final String DATA_COMMON_CURRENT_MODE = "pref_camera_mode_key_intent_";
    public static final String DATA_COMMON_CUSTOM_WATERMARK_VERSION = "pref_custom_watermark_version";
    public static final String DATA_COMMON_DEVICE_WATERMARK = "pref_dualcamera_watermark_key";
    public static final String DATA_COMMON_DUALCAMERA_USERDEFINE_WATERMARK = "user_define_watermark_key";
    public static final String DATA_COMMON_FIRST_USE_HINT = "pref_camera_first_use_hint_shown_key";
    public static final String DATA_COMMON_FOCUS_SHOOT = "pref_camera_focus_shoot_key";
    public static final String DATA_COMMON_FRONT_CAM_ROTATE_HINT = "pref_front_camera_first_use_hint_shown_key";
    public static final String DATA_COMMON_MACRO_MODE_HINT = "pref_camera_first_macro_mode_use_hint_shown_key";
    private static final String DATA_COMMON_OPEN_TIME = "pref_camera_open_time";
    public static final String DATA_COMMON_PORTRAIT_HINT = "pref_camera_first_portrait_use_hint_shown_key";
    public static final String DATA_COMMON_TIKTOK_MORE_BUTTON_SHOW_APP = "pref_camera_tiktok_more_show_app_key";
    public static final String DATA_COMMON_TIKTOK_MORE_BUTTON_SHOW_MARKET = "pref_camera_tiktok_more_show_market_key";
    public static final String DATA_COMMON_TIME_WATER_MARK = "pref_time_watermark_key";
    public static final String DATA_COMMON_ULTRA_WIDE_HINT = "pref_camera_first_ultra_wide_use_hint_shown_key";
    public static final String DATA_COMMON_ULTRA_WIDE_SAT_HINT = "pref_camera_first_ultra_wide_sat_use_hint_shown_key";
    public static final int INTENT_TYPE_IMAGE = 1;
    public static final int INTENT_TYPE_NORMAL = 0;
    public static final int INTENT_TYPE_SCAN_QR = 3;
    public static final int INTENT_TYPE_UNSPECIFIED = -1;
    public static final int INTENT_TYPE_VIDEO = 2;
    public static final int INTENT_TYPE_VOICE_CONTROL = 4;
    public static final String KEY = "camera_settings_global";
    private static final String TAG = "DataItemGlobal";
    public static List<String> sUseHints = new ArrayList();
    private a mDataItemFeature;
    private int mIntentType = 0;
    private boolean mIsForceMainBackCamera;
    private Boolean mIsTimeOut;
    private int mLastCameraId;
    private boolean mMimojiStandAlone;
    private ComponentModuleList mModuleList;
    private boolean mRetriedIfCameraError;
    private boolean mStartFromKeyguard;

    @Retention(RetentionPolicy.SOURCE)
    public @interface IntentType {
    }

    static {
        sUseHints.add("pref_camera_first_use_hint_shown_key");
        sUseHints.add("pref_camera_first_ai_scene_use_hint_shown_key");
        sUseHints.add("pref_camera_first_ultra_wide_use_hint_shown_key");
        sUseHints.add("pref_camera_first_portrait_use_hint_shown_key");
        sUseHints.add("pref_front_camera_first_use_hint_shown_key");
        sUseHints.add(CameraSettings.KEY_RECORD_LOCATION);
    }

    public DataItemGlobal(a aVar) {
        this.mDataItemFeature = aVar;
        this.mMimojiStandAlone = this.mDataItemFeature.Xb();
        this.mLastCameraId = getCurrentCameraId();
        this.mModuleList = new ComponentModuleList(this);
    }

    private boolean determineTimeOut() {
        if (CameraSettings.retainCameraMode()) {
            return false;
        }
        return isActualTimeOut();
    }

    private int getCurrentCameraId(int i) {
        switch (i) {
            case 166:
            case 167:
            case 172:
            case 173:
            case 175:
                break;
            case 171:
                if (this.mDataItemFeature.rc()) {
                    return Integer.valueOf(getString("pref_camera_id_key", String.valueOf(getDefaultCameraId(i)))).intValue();
                }
                break;
            case 176:
                return 1;
            default:
                return Integer.valueOf(getString("pref_camera_id_key", String.valueOf(getDefaultCameraId(i)))).intValue();
        }
        return 0;
    }

    private int getCurrentMode(int i) {
        return getInt(DATA_COMMON_CURRENT_MODE + i, getDefaultMode(i));
    }

    private int getCurrentModeForFrontCamera(int i) {
        int currentMode = getCurrentMode(i);
        switch (currentMode) {
            case 166:
            case 167:
            case 173:
            case 175:
                break;
            case 169:
            case 172:
                return 162;
            case 171:
                if (this.mDataItemFeature.rc()) {
                    return currentMode;
                }
                break;
            default:
                return currentMode;
        }
        return 163;
    }

    private int getDefaultCameraId(int i) {
        return 0;
    }

    private boolean isActualTimeOut() {
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis - getLong(DATA_COMMON_OPEN_TIME, currentTimeMillis) > 30000 || this.mIsTimeOut == null;
    }

    public boolean getCTACanCollect() {
        return getBoolean(CTA_CAN_CONNECT_NETWORK_BY_IMPUNITY, false);
    }

    public ComponentModuleList getComponentModuleList() {
        return this.mModuleList;
    }

    public int getCurrentCameraId() {
        return getCurrentCameraId(getCurrentMode());
    }

    public int getCurrentMode() {
        return getCurrentMode(this.mIntentType);
    }

    public int getDataBackUpKey(int i) {
        if (i == 165) {
            i = ComponentModuleList.getTransferredMode(i);
        }
        int i2 = i | ((this.mIntentType + 2) << 8);
        return this.mStartFromKeyguard ? i2 | 65536 : i2;
    }

    public int getDefaultMode(int i) {
        if (i != 1) {
            if (i != 2) {
                return (i == 3 || !this.mMimojiStandAlone) ? 163 : 177;
            }
            return 162;
        }
        return 163;
    }

    public int getDisplayMode() {
        return (!DataRepository.dataItemFeature().Hb() || DataRepository.dataItemGlobal().getCurrentCameraId() != 1) ? 1 : 2;
    }

    public int getIntentType() {
        return this.mIntentType;
    }

    public int getLastCameraId() {
        return this.mLastCameraId;
    }

    public boolean getStartFromKeyguard() {
        return this.mStartFromKeyguard;
    }

    public boolean isFirstShowCTAConCollect() {
        return !contains(CTA_CAN_CONNECT_NETWORK_BY_IMPUNITY);
    }

    public boolean isForceMainBackCamera() {
        return this.mIsForceMainBackCamera;
    }

    public boolean isGlobalSwitchOn(String str) {
        return getBoolean(str, false);
    }

    public boolean isIntentAction() {
        return this.mIntentType != 0;
    }

    public boolean isNormalIntent() {
        return this.mIntentType == 0;
    }

    public boolean isRetriedIfCameraError() {
        return this.mRetriedIfCameraError;
    }

    public boolean isTiktokMoreButtonEnabled(boolean z) {
        return getBoolean(z ? DATA_COMMON_TIKTOK_MORE_BUTTON_SHOW_APP : DATA_COMMON_TIKTOK_MORE_BUTTON_SHOW_MARKET, b.Hn ? true : z);
    }

    public boolean isTimeOut() {
        Boolean bool = this.mIsTimeOut;
        return bool == null || bool.booleanValue();
    }

    public boolean isTransient() {
        return false;
    }

    public boolean matchCustomWatermarkVersion() {
        String Aa = this.mDataItemFeature.Aa();
        if (!contains(DATA_COMMON_CUSTOM_WATERMARK_VERSION)) {
            return !this.mDataItemFeature.l(c.tt);
        }
        if (arrayMapContainsKey(DATA_COMMON_CUSTOM_WATERMARK_VERSION)) {
            arrayMapRemove(DATA_COMMON_CUSTOM_WATERMARK_VERSION);
        }
        String string = getString(DATA_COMMON_CUSTOM_WATERMARK_VERSION, "");
        int indexOf = string.indexOf(58);
        if (indexOf > 0) {
            String substring = string.substring(0, indexOf);
            String substring2 = string.substring(indexOf + 1);
            if (substring.equals(b.km + b.Sh()) && substring2.equals(Aa)) {
                return true;
            }
        }
        Log.w(TAG, "mismatch custom watermark version: " + string);
        return false;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x018f, code lost:
        if (r6 == 168) goto L_0x01a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x0193, code lost:
        if (r6 != 170) goto L_0x0196;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x019a, code lost:
        if (isActualTimeOut() == false) goto L_0x01a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x019e, code lost:
        if (r6 != 179) goto L_0x01a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x01a1, code lost:
        r11 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x01ab, code lost:
        if (com.android.camera.data.DataRepository.dataItemFeature().nd() == false) goto L_0x01af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x01ad, code lost:
        r11 = 172;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x01af, code lost:
        com.android.camera.log.Log.d(TAG, java.lang.String.format("parseIntent timeOut = %s, intentChanged = %s, action = %s, pendingOpenId = %s, pendingOpenModule = %s, intentCameraId = %s", new java.lang.Object[]{java.lang.Boolean.valueOf(r10), java.lang.Boolean.valueOf(r5), r3, java.lang.Integer.valueOf(r8), java.lang.Integer.valueOf(r11), java.lang.Integer.valueOf(r15)}));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x01dd, code lost:
        if (r20 != false) goto L_0x0207;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x01df, code lost:
        r0.mIsTimeOut = java.lang.Boolean.valueOf(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x01e5, code lost:
        if (r5 == false) goto L_0x01f2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x01e7, code lost:
        r0.mIntentType = r4;
        r0.mStartFromKeyguard = r1;
        r0.mModuleList.setIntentType(r0.mIntentType);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x01f6, code lost:
        if (r11 == getCurrentMode()) goto L_0x01fe;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x01f8, code lost:
        setCurrentMode(r11);
        com.android.camera.module.ModuleManager.setActiveModuleIndex(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x0202, code lost:
        if (r8 == getCurrentCameraId()) goto L_0x0207;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x0204, code lost:
        setCameraId(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x0214, code lost:
        return new android.support.v4.util.Pair<>(java.lang.Integer.valueOf(r8), java.lang.Integer.valueOf(r11));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x007f, code lost:
        r4 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0086, code lost:
        if (r18.booleanValue() == false) goto L_0x007f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0089, code lost:
        r3 = com.android.camera.CameraIntentManager.getInstance(r17);
        r4 = r3.getCameraModeId();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0093, code lost:
        if (r4 != 160) goto L_0x00a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0099, code lost:
        if (determineTimeOut() == false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x009b, code lost:
        r4 = getDefaultMode(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00a0, code lost:
        r4 = getCurrentMode(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        r3 = r3.isUseFrontCamera();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00ad, code lost:
        if (r3.isOnlyForceOpenMainBackCamera() != false) goto L_0x00af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00af, code lost:
        setForceMainBackCamera(true);
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00b8, code lost:
        if (determineTimeOut() != false) goto L_0x00ba;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00ba, code lost:
        r3 = getDefaultCameraId(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00bf, code lost:
        r3 = getCurrentCameraId(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00c3, code lost:
        com.android.camera.log.Log.d(TAG, "intent from voice control assist : pendingOpenId = " + r3 + ";pendingOpenModule = " + r4);
        r0.mIntentType = 0;
        r0.mStartFromKeyguard = r1;
        r0.mModuleList.setIntentType(r0.mIntentType);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00ee, code lost:
        if (r4 == getCurrentMode()) goto L_0x00f6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00f0, code lost:
        setCurrentMode(r4);
        com.android.camera.module.ModuleManager.setActiveModuleIndex(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00fa, code lost:
        if (r3 == getCurrentCameraId()) goto L_0x00ff;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00fc, code lost:
        setCameraId(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x010c, code lost:
        return new android.support.v4.util.Pair<>(java.lang.Integer.valueOf(r3), java.lang.Integer.valueOf(r4));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0112, code lost:
        r15 = com.android.camera.CameraIntentManager.getInstance(r17).getCameraFacing();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x011a, code lost:
        if (r15 == -1) goto L_0x011f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x011c, code lost:
        setCameraIdTransient(r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x011f, code lost:
        if (r21 == false) goto L_0x0129;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0125, code lost:
        if (determineTimeOut() == false) goto L_0x0129;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0127, code lost:
        r10 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0129, code lost:
        r10 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x012c, code lost:
        if (r0.mIntentType != r4) goto L_0x0135;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0130, code lost:
        if (r0.mStartFromKeyguard == r1) goto L_0x0133;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0133, code lost:
        r5 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0135, code lost:
        r5 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0136, code lost:
        r6 = 163;
        r11 = 162;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x013e, code lost:
        if ("android.media.action.STILL_IMAGE_CAMERA".equals(r3) == false) goto L_0x0145;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0140, code lost:
        r8 = getCurrentCameraId(163);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0149, code lost:
        if ("android.media.action.VIDEO_CAMERA".equals(r3) == false) goto L_0x0151;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x014b, code lost:
        r8 = getCurrentCameraId(162);
        r6 = 162;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x0151, code lost:
        if (r10 == false) goto L_0x017e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x0153, code lost:
        r8 = getDefaultMode(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0157, code lost:
        if (r15 >= 0) goto L_0x015e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0159, code lost:
        r9 = getDefaultCameraId(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x015e, code lost:
        r9 = getCurrentCameraId(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0162, code lost:
        if (r8 != 163) goto L_0x017b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0176, code lost:
        if (((com.android.camera.data.data.config.DataItemConfig) com.android.camera.data.DataRepository.provider().dataConfig(r9, r4)).getComponentConfigRatio().isSquareModule() == false) goto L_0x017b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0178, code lost:
        r6 = 165;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x017b, code lost:
        r6 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x017c, code lost:
        r8 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x017e, code lost:
        if (r15 == 1) goto L_0x0185;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0180, code lost:
        r6 = getCurrentMode(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x0185, code lost:
        r6 = getCurrentModeForFrontCamera(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0189, code lost:
        r8 = getCurrentCameraId(r6);
     */
    public Pair<Integer, Integer> parseIntent(Intent intent, Boolean bool, boolean z, boolean z2, boolean z3) {
        char c2;
        int i;
        boolean z4 = z;
        setForceMainBackCamera(false);
        if (DataRepository.dataItemFeature().sb() && Util.isScreenSlideOff(CameraAppImpl.getAndroidContext())) {
            setCameraId(0);
        }
        String action = intent.getAction();
        if (action == null) {
            action = "<unknown>";
        }
        switch (action.hashCode()) {
            case -1960745709:
                if (action.equals("android.media.action.IMAGE_CAPTURE")) {
                    c2 = 0;
                    break;
                }
            case -1528697361:
                if (action.equals(CameraIntentManager.ACTION_VOICE_CONTROL)) {
                    c2 = 6;
                    break;
                }
            case -1449841107:
                if (action.equals(CameraIntentManager.ACTION_QR_CODE_ZXING)) {
                    c2 = 3;
                    break;
                }
            case 464109999:
                if (action.equals("android.media.action.STILL_IMAGE_CAMERA")) {
                    c2 = 4;
                    break;
                }
            case 701083699:
                if (action.equals("android.media.action.VIDEO_CAPTURE")) {
                    c2 = 1;
                    break;
                }
            case 1130890360:
                if (action.equals("android.media.action.VIDEO_CAMERA")) {
                    c2 = 5;
                    break;
                }
            case 1280056183:
                if (action.equals(CameraIntentManager.ACTION_QR_CODE_CAPTURE)) {
                    c2 = 2;
                    break;
                }
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
                i = 1;
                break;
            case 1:
                i = 2;
                break;
            case 2:
            case 3:
                i = 3;
                break;
            case 4:
            case 5:
                break;
            case 6:
                break;
        }
    }

    public String provideKey() {
        return "camera_settings_global";
    }

    public void reInit() {
        this.mModuleList.reInit();
        DataProvider.ProviderEditor editor = editor();
        this.mIsTimeOut = false;
        editor.putLong(DATA_COMMON_OPEN_TIME, System.currentTimeMillis());
        editor.putLong(CameraSettings.KEY_OPEN_CAMERA_FAIL, 0);
        int currentCameraId = getCurrentCameraId(getCurrentMode());
        this.mLastCameraId = currentCameraId;
        editor.putString("pref_camera_id_key", String.valueOf(currentCameraId));
        Log.d(TAG, "reInit: mLastCameraId = " + this.mLastCameraId + ", currentCameraId = " + currentCameraId);
        editor.apply();
    }

    public void resetAll() {
        this.mIsTimeOut = null;
        editor().clear().putInt(CameraSettings.KEY_VERSION, 4).apply();
    }

    public void resetTimeOut() {
        this.mIsTimeOut = false;
        editor().putLong(DATA_COMMON_OPEN_TIME, System.currentTimeMillis()).apply();
    }

    public void setCTACanCollect(boolean z) {
        editor().putBoolean(CTA_CAN_CONNECT_NETWORK_BY_IMPUNITY, z).apply();
    }

    public void setCameraId(int i) {
        this.mLastCameraId = getCurrentCameraId(getCurrentMode());
        editor().putString("pref_camera_id_key", String.valueOf(i)).apply();
        Log.d(TAG, "setCameraId: mLastCameraId = " + this.mLastCameraId + ", cameraId = " + i);
    }

    public void setCameraIdTransient(int i) {
        this.mLastCameraId = getCurrentCameraId(getCurrentMode());
        putString("pref_camera_id_key", String.valueOf(i));
        Log.d(TAG, "setCameraIdTransient: mLastCameraId = " + this.mLastCameraId + ", cameraId = " + i);
    }

    public void setCurrentMode(int i) {
        DataProvider.ProviderEditor editor = editor();
        editor.putInt(DATA_COMMON_CURRENT_MODE + this.mIntentType, i).apply();
    }

    public void setForceMainBackCamera(boolean z) {
        this.mIsForceMainBackCamera = z;
    }

    public void setRetriedIfCameraError(boolean z) {
        this.mRetriedIfCameraError = z;
    }

    public void setStartFromKeyguard(boolean z) {
        this.mStartFromKeyguard = z;
    }

    public void updateCustomWatermarkVersion() {
        String Aa = this.mDataItemFeature.Aa();
        editor().putString(DATA_COMMON_CUSTOM_WATERMARK_VERSION, b.km + b.Sh() + ":" + Aa).apply();
        Log.i(TAG, "custom watermark version updated: " + r0);
    }
}
