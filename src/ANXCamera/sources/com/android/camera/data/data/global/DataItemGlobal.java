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
    public static final int BACK_DISPLAY_MODE = 2;
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
    public static final String DATA_COMMON_ULTRA_TELE_HINT = "pref_camera_first_ultra_tele_use_hint_shown_key";
    public static final String DATA_COMMON_ULTRA_WIDE_HINT = "pref_camera_first_ultra_wide_use_hint_shown_key";
    public static final String DATA_COMMON_ULTRA_WIDE_SAT_HINT = "pref_camera_first_ultra_wide_sat_use_hint_shown_key";
    public static final String DATA_COMMON_VV_HINT = "pref_camera_first_vv_hint_shown_key";
    public static final int FRONT_DISPLAY_MODE = 1;
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
        this.mMimojiStandAlone = this.mDataItemFeature.gE();
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
                return 0;
            case 171:
                if (this.mDataItemFeature.hw()) {
                    return Integer.valueOf(getString("pref_camera_id_key", String.valueOf(getDefaultCameraId(i)))).intValue();
                }
                return 0;
            case 176:
                return 1;
            default:
                return Integer.valueOf(getString("pref_camera_id_key", String.valueOf(getDefaultCameraId(i)))).intValue();
        }
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
                if (this.mDataItemFeature.hw()) {
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
        switch (i) {
            case 1:
            case 3:
                return 163;
            case 2:
                return 162;
            default:
                return this.mMimojiStandAlone ? 177 : 163;
        }
    }

    public int getDisplayMode() {
        return (!DataRepository.dataItemFeature().ia() || DataRepository.dataItemGlobal().getCurrentCameraId() != 1) ? 1 : 2;
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
        return getBoolean(z ? DATA_COMMON_TIKTOK_MORE_BUTTON_SHOW_APP : DATA_COMMON_TIKTOK_MORE_BUTTON_SHOW_MARKET, b.sL ? true : z);
    }

    public boolean isTimeOut() {
        return this.mIsTimeOut == null || this.mIsTimeOut.booleanValue();
    }

    public boolean isTransient() {
        return false;
    }

    public boolean matchCustomWatermarkVersion() {
        String hA = this.mDataItemFeature.hA();
        if (!contains(DATA_COMMON_CUSTOM_WATERMARK_VERSION)) {
            return !this.mDataItemFeature.L(c.vb);
        }
        if (arrayMapContainsKey(DATA_COMMON_CUSTOM_WATERMARK_VERSION)) {
            arrayMapRemove(DATA_COMMON_CUSTOM_WATERMARK_VERSION);
        }
        String string = getString(DATA_COMMON_CUSTOM_WATERMARK_VERSION, "");
        int indexOf = string.indexOf(58);
        if (indexOf > 0) {
            String substring = string.substring(0, indexOf);
            String substring2 = string.substring(indexOf + 1);
            if (substring.equals(b.rp + b.getGivenName()) && substring2.equals(hA)) {
                return true;
            }
        }
        Log.w(TAG, "mismatch custom watermark version: " + string);
        return false;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x01ab, code lost:
        r15 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x01ad, code lost:
        r7 = getCurrentModeForFrontCamera(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x01b2, code lost:
        r14 = getCurrentCameraId(r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x01b9, code lost:
        if (r7 == 168) goto L_0x01ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x01bd, code lost:
        if (r7 != 170) goto L_0x01c0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x01c4, code lost:
        if (isActualTimeOut() == false) goto L_0x01dc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x01c8, code lost:
        if (r7 != 179) goto L_0x01dc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x01cb, code lost:
        r7 = 162;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x01d6, code lost:
        if (com.android.camera.data.DataRepository.dataItemFeature().gp() == false) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x01d8, code lost:
        r7 = 172;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x01dc, code lost:
        com.android.camera.log.Log.d(TAG, java.lang.String.format("parseIntent timeOut = %s, intentChanged = %s, action = %s, pendingOpenId = %s, pendingOpenModule = %s, intentCameraId = %s", new java.lang.Object[]{java.lang.Boolean.valueOf(r8), java.lang.Boolean.valueOf(r13), r0, java.lang.Integer.valueOf(r14), java.lang.Integer.valueOf(r7), java.lang.Integer.valueOf(r12)}));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x020b, code lost:
        if (r22 != false) goto L_0x0235;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x020d, code lost:
        r1.mIsTimeOut = java.lang.Boolean.valueOf(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x0213, code lost:
        if (r13 == false) goto L_0x0220;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x0215, code lost:
        r1.mIntentType = r4;
        r1.mStartFromKeyguard = r2;
        r1.mModuleList.setIntentType(r1.mIntentType);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x0224, code lost:
        if (r7 == getCurrentMode()) goto L_0x022c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x0226, code lost:
        setCurrentMode(r7);
        com.android.camera.module.ModuleManager.setActiveModuleIndex(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x0230, code lost:
        if (r14 == getCurrentCameraId()) goto L_0x0235;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x0232, code lost:
        setCameraId(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x0242, code lost:
        return new android.support.v4.util.Pair<>(java.lang.Integer.valueOf(r14), java.lang.Integer.valueOf(r7));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x007f, code lost:
        r4 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0086, code lost:
        if (r20.booleanValue() == false) goto L_0x007f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x008a, code lost:
        r4 = com.android.camera.CameraIntentManager.getInstance(r19);
        r0 = r4.getCameraModeId();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0095, code lost:
        if (r0 != 160) goto L_0x00a6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x009b, code lost:
        if (determineTimeOut() == false) goto L_0x00a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x009d, code lost:
        r0 = getDefaultMode(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00a2, code lost:
        r0 = getCurrentMode(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00a6, code lost:
        r5 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        r0 = r4.isUseFrontCamera();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00b1, code lost:
        if (r4.isOnlyForceOpenMainBackCamera() != false) goto L_0x00b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00b3, code lost:
        setForceMainBackCamera(true);
        r0 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00bd, code lost:
        if (determineTimeOut() != false) goto L_0x00bf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00bf, code lost:
        r0 = getDefaultCameraId(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00c4, code lost:
        r0 = getCurrentCameraId(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00c8, code lost:
        com.android.camera.log.Log.d(TAG, "intent from voice control assist : pendingOpenId = " + r0 + ";pendingOpenModule = " + r5 + ",newIntentType = " + 0);
        r1.mIntentType = 0;
        r1.mStartFromKeyguard = r2;
        r1.mModuleList.setIntentType(r1.mIntentType);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00fd, code lost:
        if (r5 == getCurrentMode()) goto L_0x0105;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00ff, code lost:
        setCurrentMode(r5);
        com.android.camera.module.ModuleManager.setActiveModuleIndex(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0109, code lost:
        if (r0 == getCurrentCameraId()) goto L_0x010e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x010b, code lost:
        setCameraId(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x011b, code lost:
        return new android.support.v4.util.Pair<>(java.lang.Integer.valueOf(r0), java.lang.Integer.valueOf(r5));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0127, code lost:
        r12 = com.android.camera.CameraIntentManager.getInstance(r19).getCameraFacing();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x012f, code lost:
        if (r12 == -1) goto L_0x0134;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0131, code lost:
        setCameraIdTransient(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0134, code lost:
        if (r23 == false) goto L_0x013e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x013a, code lost:
        if (determineTimeOut() == false) goto L_0x013e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x013c, code lost:
        r8 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x013e, code lost:
        r8 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0142, code lost:
        if (r1.mIntentType != r4) goto L_0x014b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0146, code lost:
        if (r1.mStartFromKeyguard == r2) goto L_0x0149;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0149, code lost:
        r13 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x014b, code lost:
        r13 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x014d, code lost:
        r15 = 163;
        r7 = 162;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0157, code lost:
        if ("android.media.action.STILL_IMAGE_CAMERA".equals(r0) == false) goto L_0x0160;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0159, code lost:
        r14 = getCurrentCameraId(163);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x015e, code lost:
        r7 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0166, code lost:
        if ("android.media.action.VIDEO_CAMERA".equals(r0) == false) goto L_0x016e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x0168, code lost:
        r14 = getCurrentCameraId(162);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x016e, code lost:
        if (r8 == false) goto L_0x01a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0170, code lost:
        r14 = getDefaultMode(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0174, code lost:
        if (r12 >= 0) goto L_0x017d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0176, code lost:
        r16 = getDefaultCameraId(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x017a, code lost:
        r7 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x017d, code lost:
        r16 = getCurrentCameraId(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0182, code lost:
        if (r14 != 163) goto L_0x019f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0196, code lost:
        if (((com.android.camera.data.data.config.DataItemConfig) com.android.camera.data.DataRepository.provider().dataConfig(r7, r4)).getComponentConfigRatio().isSquareModule() == false) goto L_0x019a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x0198, code lost:
        r14 = 165;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x019a, code lost:
        r15 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x019d, code lost:
        r14 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x019f, code lost:
        r17 = r14;
        r14 = r7;
        r7 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x01a5, code lost:
        if (r12 == 1) goto L_0x01ad;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x01a7, code lost:
        r7 = getCurrentMode(r4);
     */
    public Pair<Integer, Integer> parseIntent(Intent intent, Boolean bool, boolean z, boolean z2, boolean z3) {
        char c;
        int i;
        boolean z4 = z;
        setForceMainBackCamera(false);
        if (DataRepository.dataItemFeature().ge() && Util.isScreenSlideOff(CameraAppImpl.getAndroidContext())) {
            setCameraId(0);
        }
        String action = intent.getAction();
        if (action == null) {
            action = "<unknown>";
        }
        switch (action.hashCode()) {
            case -1960745709:
                if (action.equals("android.media.action.IMAGE_CAPTURE")) {
                    c = 0;
                    break;
                }
            case -1528697361:
                if (action.equals(CameraIntentManager.ACTION_VOICE_CONTROL)) {
                    c = 6;
                    break;
                }
            case -1449841107:
                if (action.equals(CameraIntentManager.ACTION_QR_CODE_ZXING)) {
                    c = 3;
                    break;
                }
            case 464109999:
                if (action.equals("android.media.action.STILL_IMAGE_CAMERA")) {
                    c = 4;
                    break;
                }
            case 701083699:
                if (action.equals("android.media.action.VIDEO_CAPTURE")) {
                    c = 1;
                    break;
                }
            case 1130890360:
                if (action.equals("android.media.action.VIDEO_CAMERA")) {
                    c = 5;
                    break;
                }
            case 1280056183:
                if (action.equals(CameraIntentManager.ACTION_QR_CODE_CAPTURE)) {
                    c = 2;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
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
        String hA = this.mDataItemFeature.hA();
        editor().putString(DATA_COMMON_CUSTOM_WATERMARK_VERSION, b.rp + b.getGivenName() + ":" + hA).apply();
        Log.i(TAG, "custom watermark version updated: " + r0);
    }
}
