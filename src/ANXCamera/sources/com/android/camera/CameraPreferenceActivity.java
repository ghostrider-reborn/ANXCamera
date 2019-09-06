package com.android.camera;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.provider.MiuiSettings.Key;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigSlowMotion;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.lib.compatibility.util.CompatibilityUtils.PackageInstallerListener;
import com.android.camera.log.Log;
import com.android.camera.statistic.CameraStatUtil;
import com.android.camera.storage.PriorityStorageBroadcastReceiver;
import com.android.camera.storage.Storage;
import com.android.camera.ui.PreviewListPreference;
import com.android.camera.ui.ValuePreference;
import com.android.camera2.DetachableClickListener;
import com.mi.config.b;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import miui.app.ActionBar;

public class CameraPreferenceActivity extends BasePreferenceActivity {
    public static final String FROM_WHERE = "from_where";
    public static final String IS_IMAGE_CAPTURE_INTENT = "IsCaptureIntent";
    protected static final String PREF_KEY_PRIVACY = "pref_privacy";
    protected static final String PREF_KEY_RESTORE = "pref_restore";
    public static final String REMOVE_KEYS = "remove_keys";
    public static final String TAG = "CameraPreferenceActivity";
    /* access modifiers changed from: private */
    public PackageInstallerListener mAppInstalledListener = new PackageInstallerListener() {
        public void onPackageInstalled(String str, boolean z) {
            if (z && TextUtils.equals(str, "com.xiaomi.scanner")) {
                final CheckBoxPreference checkBoxPreference = (CheckBoxPreference) CameraPreferenceActivity.this.mPreferenceGroup.findPreference(CameraSettings.KEY_SCAN_QRCODE);
                if (checkBoxPreference != null) {
                    CameraPreferenceActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            checkBoxPreference.setChecked(true);
                            CameraPreferenceActivity.this.onPreferenceChange(checkBoxPreference, Boolean.TRUE);
                        }
                    });
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public AlertDialog mDoubleConfirmActionChooseDialog = null;
    private int mFromWhere;
    private boolean mGoToActivity;
    private boolean mHasReset;
    private Preference mPhotoAssistanceTips;
    protected PreferenceScreen mPreferenceGroup;
    private Preference mWatermark;

    /* JADX WARNING: type inference failed for: r2v0, types: [android.content.Context, com.android.camera.CameraPreferenceActivity] */
    private void bringUpDoubleConfirmDlg(final Preference preference, final String str) {
        if (this.mDoubleConfirmActionChooseDialog == null) {
            final boolean snapBoolValue = getSnapBoolValue(str);
            DetachableClickListener wrap = DetachableClickListener.wrap(new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == -1) {
                        CameraPreferenceActivity.this.mDoubleConfirmActionChooseDialog = null;
                        CameraStatUtil.trackPreferenceChange(CameraSettings.KEY_CAMERA_SNAP, str);
                        Preference preference = preference;
                        if (preference instanceof CheckBoxPreference) {
                            ((CheckBoxPreference) preference).setChecked(snapBoolValue);
                        } else if (preference instanceof PreviewListPreference) {
                            ((PreviewListPreference) preference).setValue(str);
                        }
                        Secure.putString(CameraPreferenceActivity.this.getContentResolver(), Key.LONG_PRESS_VOLUME_DOWN, CameraSettings.getMiuiSettingsKeyForStreetSnap(str));
                    } else if (i == -2) {
                        CameraPreferenceActivity.this.mDoubleConfirmActionChooseDialog.dismiss();
                        CameraPreferenceActivity.this.mDoubleConfirmActionChooseDialog = null;
                    }
                }
            });
            this.mDoubleConfirmActionChooseDialog = new Builder(this).setTitle(R.string.title_snap_double_confirm).setMessage(R.string.message_snap_double_confirm).setPositiveButton(R.string.snap_confirmed, wrap).setNegativeButton(R.string.snap_cancel, wrap).setCancelable(false).create();
            wrap.clearOnDetach(this.mDoubleConfirmActionChooseDialog);
            this.mDoubleConfirmActionChooseDialog.show();
        }
    }

    private void filterByCloud() {
        for (String removePreference : DataRepository.dataCloudMgr().DataCloudFeature().getAllDisabledFeatures()) {
            removePreference(this.mPreferenceGroup, removePreference);
        }
    }

    private void filterByConfig() {
        Log.d(TAG, "filterByConfig:");
        Preference findPreference = this.mPreferenceGroup.findPreference(CameraSettings.KEY_CATEGORY_DEVICE_SETTING);
        Preference findPreference2 = this.mPreferenceGroup.findPreference(CameraSettings.KEY_CATEGORY_CAPTURE_SETTING);
        if (this.mFromWhere == 177) {
            removeFromGroup(findPreference2, "pref_camera_focus_shoot_key");
            removeFromGroup(this.mPreferenceGroup, CameraSettings.KEY_MOVIE_SOLID);
            removeFromGroup(this.mPreferenceGroup, CameraSettings.KEY_VIDEO_TIME_LAPSE_FRAME_INTERVAL);
            PreferenceScreen preferenceScreen = this.mPreferenceGroup;
            String str = CameraSettings.KEY_LONG_PRESS_SHUTTER;
            removeFromGroup(preferenceScreen, str);
            removeFromGroup(this.mPreferenceGroup, CameraSettings.KEY_FINGERPRINT_CAPTURE);
            removeFromGroup(this.mPreferenceGroup, str);
        }
        int i = this.mFromWhere;
        String str2 = CameraSettings.KEY_FRONT_MIRROR;
        if (i == 161 || i == 174 || i == 179 || i == 169 || i == 172 || i == 177) {
            removeFromGroup(findPreference, str2);
            removeFromGroup(findPreference2, str2);
            return;
        }
        boolean vc = DataRepository.dataItemFeature().vc();
        String str3 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("filterByConfig: isSupportVideoFrontMirror = ");
        sb.append(vc);
        Log.d(str3, sb.toString());
        if (vc) {
            removeFromGroup(findPreference2, str2);
        } else {
            removeFromGroup(findPreference, str2);
        }
        if (b.hi() && this.mFromWhere == 162) {
            removeFromGroup(findPreference, str2);
        }
    }

    private void filterByDeviceCapability() {
        if (!CameraSettings.isH265EncoderSupport()) {
            removeFromGroup(this.mPreferenceGroup, CameraSettings.KEY_VIDEO_ENCODER);
        }
    }

    private void filterByDeviceID() {
        DataRepository.dataItemFeature();
        removePreference(this.mPreferenceGroup, CameraSettings.KEY_LONG_PRESS_SHUTTER);
        boolean z = b.Ln;
        String str = CameraSettings.KEY_SCAN_QRCODE;
        String str2 = CameraSettings.KEY_LONG_PRESS_SHUTTER_FEATURE;
        String str3 = CameraSettings.KEY_CAMERA_SNAP;
        String str4 = CameraSettings.KEY_CAMERA_PROXIMITY_LOCK;
        if (z) {
            removePreference(this.mPreferenceGroup, str4);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_RETAIN_CAMERA_MODE);
            removePreference(this.mPreferenceGroup, str3);
            removePreference(this.mPreferenceGroup, str2);
            removePreference(this.mPreferenceGroup, str);
        }
        if (!b.mj()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_MOVIE_SOLID);
        }
        boolean yj = b.yj();
        String str5 = "pref_time_watermark_key";
        String str6 = "pref_dualcamera_watermark_key";
        String str7 = CameraSettings.KEY_WATERMARK;
        if (!yj && !CameraSettings.isSupportedDualCameraWaterMark()) {
            removePreference(this.mPreferenceGroup, str7);
            removePreference(this.mPreferenceGroup, str6);
            removePreference(this.mPreferenceGroup, str5);
        } else if (b.yj() && !CameraSettings.isSupportedDualCameraWaterMark()) {
            removePreference(this.mPreferenceGroup, str7);
            removePreference(this.mPreferenceGroup, str6);
        } else if (b.yj() || !CameraSettings.isSupportedDualCameraWaterMark() || DataRepository.dataItemFeature().Db()) {
            removePreference(this.mPreferenceGroup, str6);
            removePreference(this.mPreferenceGroup, str5);
        } else {
            removePreference(this.mPreferenceGroup, str7);
            removePreference(this.mPreferenceGroup, str5);
        }
        if (!b.nj()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_SOUND);
        }
        if (!b.ij()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_RECORD_LOCATION);
        }
        if (!Storage.hasSecondaryStorage()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_PRIORITY_STORAGE);
        }
        if (!b.cj()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_AUTO_CHROMA_FLASH);
        }
        if (!b.jj()) {
            removePreference(this.mPreferenceGroup, str2);
        }
        if (!b.pj()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAPTURE_WHEN_STABLE);
        }
        if (!b.aj()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_ASD_NIGHT);
        }
        if (!b.rj()) {
            removePreference(this.mPreferenceGroup, str3);
        }
        if (!b.Ni()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_GROUPSHOT_PRIMITIVE);
        }
        if (!CameraSettings.isSupportedPortrait()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_PORTRAIT_WITH_FACEBEAUTY);
        }
        boolean isSupportedOpticalZoom = b.isSupportedOpticalZoom();
        String str8 = CameraSettings.KEY_CAMERA_DUAL_ENABLE;
        if (!isSupportedOpticalZoom && !DataRepository.dataItemFeature().nc()) {
            removePreference(this.mPreferenceGroup, str8);
        }
        boolean isSupportedOpticalZoom2 = b.isSupportedOpticalZoom();
        String str9 = CameraSettings.KEY_CAMERA_DUAL_SAT_ENABLE;
        if (!isSupportedOpticalZoom2) {
            removePreference(this.mPreferenceGroup, str9);
        }
        if (!b.isSupportSuperResolution()) {
            removePreference(this.mPreferenceGroup, "pref_camera_super_resolution_key");
        }
        boolean Jc = DataRepository.dataItemFeature().Jc();
        String str10 = CameraSettings.KEY_CAMERA_PARALLEL_PROCESS_ENABLE;
        if (!Jc) {
            removePreference(this.mPreferenceGroup, str10);
        }
        boolean isSupportQuickShot = CameraSettings.isSupportQuickShot();
        String str11 = CameraSettings.KEY_CAMERA_QUICK_SHOT_ENABLE;
        if (!isSupportQuickShot) {
            removePreference(this.mPreferenceGroup, str11);
        }
        if (b.Ej()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_FACE_DETECTION);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_FACE_DETECTION_AUTO_HIDDEN);
            removePreference(this.mPreferenceGroup, str10);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_QUICK_SHOT_ANIM_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_TOUCH_FOCUS_DELAY_ENABLE);
            removePreference(this.mPreferenceGroup, str11);
            removePreference(this.mPreferenceGroup, str8);
            removePreference(this.mPreferenceGroup, str9);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_FRONT_MIRROR);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_QC_SHARPNESS);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_QC_CONTRAST);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_QC_SATURATION);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_AUTOEXPOSURE);
        }
        if (!ProximitySensorLock.supported()) {
            removePreference(this.mPreferenceGroup, str4);
        }
        if (!b.Wh()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_FINGERPRINT_CAPTURE);
        }
        if (!CameraSettings.shouldNormalWideLDCBeVisibleInMode(this.mFromWhere) || CameraSettings.isUltraWideConfigOpen(this.mFromWhere) || CameraSettings.isUltraPixelOn() || CameraSettings.isMacroModeEnabled(this.mFromWhere)) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_NORMAL_WIDE_LDC);
        }
        if (!CameraSettings.shouldUltraWideLDCBeVisibleInMode(this.mFromWhere) || ((HybridZoomingSystem.IS_2_SAT && !CameraSettings.isUltraWideConfigOpen(this.mFromWhere)) || ((HybridZoomingSystem.IS_3_OR_MORE_SAT && !CameraSettings.isUltraWideConfigOpen(this.mFromWhere) && this.mFromWhere == 167) || (!CameraSettings.isSupportedOpticalZoom() && !HybridZoomingSystem.IS_3_OR_MORE_SAT && !CameraSettings.isUltraWideConfigOpen(this.mFromWhere))))) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_ULTRA_WIDE_LDC);
        }
        if (!CameraSettings.shouldUltraWideVideoLDCBeVisibleInMode(this.mFromWhere) || !CameraSettings.isUltraWideConfigOpen(this.mFromWhere)) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_ULTRA_WIDE_VIDEO_LDC);
        }
        if (DataRepository.dataItemFeature().Sa()) {
            removePreference(this.mPreferenceGroup, str);
        }
        removeIncompatibleAdvancePreference();
        int i = 0;
        if (DataRepository.dataItemFeature().Zb()) {
            i = 1;
        }
        if (DataRepository.dataItemFeature().Rc()) {
            i++;
        }
        if (DataRepository.dataItemFeature().Pb()) {
            i++;
        }
        String str12 = CameraSettings.KEY_CAMERA_LYING_TIP_SWITCH;
        String str13 = CameraSettings.KEY_LENS_DIRTY_TIP;
        String str14 = CameraSettings.KEY_PICTURE_FLAW_TIP;
        if (i <= 1) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_PHOTO_ASSISTANCE_TIPS);
            if (!DataRepository.dataItemFeature().Zb()) {
                removePreference(this.mPreferenceGroup, str14);
            }
            if (!DataRepository.dataItemFeature().Pb()) {
                removePreference(this.mPreferenceGroup, str13);
            }
            if (!DataRepository.dataItemFeature().Rc()) {
                removePreference(this.mPreferenceGroup, str12);
                return;
            }
            return;
        }
        removePreference(this.mPreferenceGroup, str14);
        removePreference(this.mPreferenceGroup, str13);
        removePreference(this.mPreferenceGroup, str12);
    }

    private void filterByFrom() {
        PreferenceScreen preferenceScreen = this.mPreferenceGroup;
        String str = CameraSettings.KEY_AUTOEXPOSURE;
        removePreference(preferenceScreen, str);
        PreferenceScreen preferenceScreen2 = this.mPreferenceGroup;
        String str2 = CameraSettings.KEY_VIDEO_AUTOEXPOSURE;
        removePreference(preferenceScreen2, str2);
        int i = this.mFromWhere;
        String str3 = CameraSettings.KEY_VOLUME_LIVE_FUNCTION;
        String str4 = CameraSettings.KEY_VOLUME_VIDEO_FUNCTION;
        if (i == 163 || i == 165 || i == 166 || i == 176 || i == 167 || i == 173 || i == 175 || i == 171) {
            removePreference(this.mPreferenceGroup, str4);
            removePreference(this.mPreferenceGroup, str3);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CATEGORY_CAMCORDER_SETTING);
            removePreference(this.mPreferenceGroup, str2);
        } else if (i == 161 || i == 174 || i == 179 || i == 162 || i == 169 || i == 172) {
            int i2 = this.mFromWhere;
            if (i2 == 174 || i2 == 179) {
                removePreference(this.mPreferenceGroup, str4);
            } else {
                removePreference(this.mPreferenceGroup, str3);
            }
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_VOLUME_CAMERA_FUNCTION);
            removeNonVideoPreference();
            removePreference(this.mPreferenceGroup, str);
        } else {
            removePreference(this.mPreferenceGroup, str4);
            removePreference(this.mPreferenceGroup, str3);
            removePreference(this.mPreferenceGroup, str2);
        }
        boolean nd = DataRepository.dataItemFeature().nd();
        String str5 = CameraSettings.KEY_6MW_STATUS;
        if (nd) {
            String componentValue = DataRepository.dataItemConfig().getComponentConfigSlowMotion().getComponentValue(172);
            if (!DataRepository.dataItemFeature().xb() || !ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_960.equals(componentValue) || this.mFromWhere != 172) {
                removePreference(this.mPreferenceGroup, str5);
                return;
            }
            return;
        }
        removePreference(this.mPreferenceGroup, str5);
    }

    private void filterByPreference() {
        if (b._h() || CameraSettings.isMacroModeEnabled(DataRepository.dataItemGlobal().getCurrentMode())) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_MOVIE_SOLID);
        }
        if (!Util.isLabOptionsVisible()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_FACE_DETECTION);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_PORTRAIT_WITH_FACEBEAUTY);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_FACE_DETECTION_AUTO_HIDDEN);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_DUAL_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_DUAL_SAT_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_MFNR_SAT_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_SR_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_PARALLEL_PROCESS_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_QUICK_SHOT_ANIM_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_TOUCH_FOCUS_DELAY_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_QUICK_SHOT_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_LIVE_STICKER_INTERNAL);
        }
        if (b.Tc()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_SCAN_QRCODE);
            return;
        }
        removePreference(this.mPreferenceGroup, CameraSettings.KEY_GOOGLE_LENS_SUGGESTIONS);
        removePreference(this.mPreferenceGroup, CameraSettings.KEY_LONG_PRESS_VIEWFINDER);
    }

    private void filterGroup() {
        filterGroupIfEmpty(CameraSettings.KEY_CATEGORY_DEVICE_SETTING);
        filterGroupIfEmpty(CameraSettings.KEY_CATEGORY_CAMCORDER_SETTING);
        filterGroupIfEmpty(CameraSettings.KEY_CATEGORY_CAPTURE_SETTING);
        filterGroupIfEmpty(CameraSettings.KEY_CATEGORY_ADVANCE_SETTING);
    }

    private void filterGroupIfEmpty(String str) {
        Preference findPreference = this.mPreferenceGroup.findPreference(str);
        if (findPreference != null && (findPreference instanceof PreferenceGroup) && ((PreferenceGroup) findPreference).getPreferenceCount() == 0) {
            removePreference(this.mPreferenceGroup, str);
        }
    }

    private String getFilterValue(PreviewListPreference previewListPreference, SharedPreferences sharedPreferences) {
        String value = previewListPreference.getValue();
        if (sharedPreferences == null) {
            return value;
        }
        String string = sharedPreferences.getString(previewListPreference.getKey(), value);
        if (!Util.isStringValueContained((Object) string, previewListPreference.getEntryValues())) {
            Editor edit = sharedPreferences.edit();
            edit.putString(previewListPreference.getKey(), value);
            edit.apply();
        } else {
            value = string;
        }
        return value;
    }

    private boolean getSnapBoolValue(String str) {
        return str.equals(getString(R.string.pref_camera_snap_value_take_picture)) || str.equals(getString(R.string.pref_camera_snap_value_take_movie));
    }

    private String getSnapStringValue(boolean z) {
        return z ? getString(R.string.pref_camera_snap_value_take_picture) : getString(R.string.pref_camera_snap_value_off);
    }

    private void initializeActivity() {
        this.mPreferenceGroup = getPreferenceScreen();
        PreferenceScreen preferenceScreen = this.mPreferenceGroup;
        if (preferenceScreen != null) {
            preferenceScreen.removeAll();
        }
        addPreferencesFromResource(getPreferenceXml());
        this.mPreferenceGroup = getPreferenceScreen();
        if (this.mPreferenceGroup == null) {
            Log.e(TAG, "fail to init PreferenceGroup");
            finish();
        }
        registerListener();
        filterByCloud();
        filterByPreference();
        filterByFrom();
        filterByDeviceID();
        filterByDeviceCapability();
        filterByIntent();
        filterByConfig();
        filterGroup();
        updateEntries();
        updatePreferences(this.mPreferenceGroup, this.mPreferences);
        updateConflictPreference(null);
    }

    /* access modifiers changed from: private */
    public void installQRCodeReceiver() {
        new AsyncTask<Void, Void, Void>() {
            /* JADX WARNING: type inference failed for: r3v1, types: [android.content.Context, com.android.camera.CameraPreferenceActivity] */
            /* access modifiers changed from: protected */
            /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v1, types: [android.content.Context, com.android.camera.CameraPreferenceActivity]
  assigns: [com.android.camera.CameraPreferenceActivity]
  uses: [com.android.camera.CameraPreferenceActivity, android.content.Context]
  mth insns count: 8
            	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
            	at java.util.ArrayList.forEach(Unknown Source)
            	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
            	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
            	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
            	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
            	at java.util.ArrayList.forEach(Unknown Source)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
            	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
            	at java.util.ArrayList.forEach(Unknown Source)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
            	at jadx.core.ProcessClass.process(ProcessClass.java:30)
            	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
            	at jadx.api.JavaClass.decompile(JavaClass.java:62)
            	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
             */
            /* JADX WARNING: Unknown variable types count: 1 */
            public Void doInBackground(Void... voidArr) {
                Log.v(CameraPreferenceActivity.TAG, "install...");
                ? r3 = CameraPreferenceActivity.this;
                Util.installPackage(r3, "com.xiaomi.scanner", r3.mAppInstalledListener, false, true);
                return null;
            }
        }.execute(new Void[0]);
    }

    private static HashMap<String, Boolean> readKeptValues(boolean z) {
        HashMap<String, Boolean> hashMap = new HashMap<>(6);
        DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
        String str = CameraSettings.KEY_CAMERA_FIRST_USE_PERMISSION_SHOWN;
        hashMap.put(str, Boolean.valueOf(dataItemGlobal.getBoolean(str, true)));
        if (z) {
            for (String str2 : DataItemGlobal.sUseHints) {
                if (DataRepository.dataItemGlobal().contains(str2)) {
                    hashMap.put(str2, Boolean.valueOf(DataRepository.dataItemGlobal().getBoolean(str2, false)));
                }
            }
        }
        return hashMap;
    }

    private void registerListener() {
        registerListener(this.mPreferenceGroup, this);
        Preference findPreference = this.mPreferenceGroup.findPreference(PREF_KEY_RESTORE);
        if (findPreference != null) {
            findPreference.setOnPreferenceClickListener(this);
        }
        Preference findPreference2 = this.mPreferenceGroup.findPreference(PREF_KEY_PRIVACY);
        if (findPreference2 != null) {
            findPreference2.setOnPreferenceClickListener(this);
        }
        Preference findPreference3 = this.mPreferenceGroup.findPreference(CameraSettings.KEY_PRIORITY_STORAGE);
        if (findPreference3 != null) {
            findPreference3.setOnPreferenceClickListener(this);
        }
        Preference findPreference4 = this.mPreferenceGroup.findPreference(CameraSettings.KEY_FACE_DETECTION);
        if (findPreference4 != null) {
            findPreference4.setOnPreferenceClickListener(this);
        }
        Preference findPreference5 = this.mPreferenceGroup.findPreference(CameraSettings.KEY_SCAN_QRCODE);
        if (findPreference5 != null) {
            findPreference5.setOnPreferenceClickListener(this);
        }
        this.mWatermark = this.mPreferenceGroup.findPreference(CameraSettings.KEY_WATERMARK);
        Preference preference = this.mWatermark;
        if (preference != null) {
            preference.setOnPreferenceClickListener(this);
        }
        this.mPhotoAssistanceTips = this.mPreferenceGroup.findPreference(CameraSettings.KEY_PHOTO_ASSISTANCE_TIPS);
        Preference preference2 = this.mPhotoAssistanceTips;
        if (preference2 != null) {
            preference2.setOnPreferenceClickListener(this);
        }
    }

    private void removeIncompatibleAdvancePreference() {
        removePreference(this.mPreferenceGroup, CameraSettings.KEY_QC_CONTRAST);
        removePreference(this.mPreferenceGroup, CameraSettings.KEY_QC_SATURATION);
        removePreference(this.mPreferenceGroup, CameraSettings.KEY_QC_SHARPNESS);
    }

    private void removeNonVideoPreference() {
        removePreference(this.mPreferenceGroup, CameraSettings.KEY_CATEGORY_CAPTURE_SETTING);
    }

    public static void resetPreferences(boolean z) {
        HashMap readKeptValues = readKeptValues(z);
        DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
        int intentType = dataItemGlobal.getIntentType();
        dataItemGlobal.resetAll();
        ((DataItemConfig) DataRepository.provider().dataConfig(0, intentType)).resetAll();
        ((DataItemConfig) DataRepository.provider().dataConfig(1, intentType)).resetAll();
        DataRepository.dataItemRunning().clearArrayMap();
        DataRepository.getInstance().backUp().clearBackUp();
        rewriteKeptValues(readKeptValues);
        Util.generateWatermark2File();
    }

    private void resetSnapSetting() {
        ContentResolver contentResolver = getContentResolver();
        String str = Key.LONG_PRESS_VOLUME_DOWN;
        String string = Secure.getString(contentResolver, str);
        if (Key.LONG_PRESS_VOLUME_DOWN_STREET_SNAP_PICTURE.equals(string) || Key.LONG_PRESS_VOLUME_DOWN_STREET_SNAP_MOVIE.equals(string)) {
            Secure.putString(getContentResolver(), str, "none");
        }
    }

    private void resetTimeOutFlag() {
        if (!this.mHasReset) {
            DataRepository.dataItemGlobal().resetTimeOut();
        }
    }

    /* access modifiers changed from: private */
    public void restorePreferences() {
        this.mHasReset = true;
        resetPreferences(false);
        resetSnapSetting();
        initializeActivity();
        PriorityStorageBroadcastReceiver.setPriorityStorage(getResources().getBoolean(R.bool.priority_storage));
        onSettingChanged(3);
    }

    private static void rewriteKeptValues(HashMap<String, Boolean> hashMap) {
        for (String str : hashMap.keySet()) {
            DataRepository.dataItemGlobal().putBoolean(str, ((Boolean) hashMap.get(str)).booleanValue());
        }
    }

    private void updateEntries() {
        PreviewListPreference previewListPreference = (PreviewListPreference) this.mPreferenceGroup.findPreference(CameraSettings.KEY_ANTIBANDING);
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) this.mPreferenceGroup.findPreference(CameraSettings.KEY_AUTO_CHROMA_FLASH);
        PreferenceScreen preferenceScreen = this.mPreferenceGroup;
        String str = CameraSettings.KEY_CAMERA_SNAP;
        PreviewListPreference previewListPreference2 = (PreviewListPreference) preferenceScreen.findPreference(str);
        PreviewListPreference previewListPreference3 = (PreviewListPreference) this.mPreferenceGroup.findPreference(CameraSettings.KEY_VOLUME_CAMERA_FUNCTION);
        if (previewListPreference != null && Util.isAntibanding60()) {
            String string = getString(R.string.pref_camera_antibanding_entryvalue_60hz);
            previewListPreference.setValue(string);
            previewListPreference.setDefaultValue(string);
        }
        if (checkBoxPreference != null) {
            checkBoxPreference.setChecked(getResources().getBoolean(CameraSettings.getDefaultPreferenceId(R.bool.pref_camera_auto_chroma_flash_default)));
        }
        if (previewListPreference2 != null && b.rj()) {
            String string2 = getString(R.string.pref_camera_snap_default);
            previewListPreference2.setDefaultValue(string2);
            previewListPreference2.setValue(string2);
            ContentResolver contentResolver = getContentResolver();
            String str2 = Key.LONG_PRESS_VOLUME_DOWN;
            String string3 = Secure.getString(contentResolver, str2);
            if (Key.LONG_PRESS_VOLUME_DOWN_PAY.equals(string3) || "none".equals(string3)) {
                previewListPreference2.setValue(getString(R.string.pref_camera_snap_value_off));
            } else {
                String string4 = DataRepository.dataItemGlobal().getString(str, null);
                if (string4 != null) {
                    Secure.putString(getContentResolver(), str2, CameraSettings.getMiuiSettingsKeyForStreetSnap(string4));
                    DataRepository.dataItemGlobal().editor().remove(str).apply();
                    previewListPreference2.setValue(string4);
                } else if (Key.LONG_PRESS_VOLUME_DOWN_STREET_SNAP_PICTURE.equals(string3)) {
                    previewListPreference2.setValue(getString(R.string.pref_camera_snap_value_take_picture));
                } else if (Key.LONG_PRESS_VOLUME_DOWN_STREET_SNAP_MOVIE.equals(string3)) {
                    previewListPreference2.setValue(getString(R.string.pref_camera_snap_value_take_movie));
                }
            }
        }
        int i = this.mFromWhere;
        if (i == 176 || i == 166) {
            String string5 = getString(R.string.pref_camera_volumekey_function_entry_shutter);
            String string6 = getString(R.string.pref_camera_volumekey_function_entry_volume);
            String string7 = getString(R.string.pref_camera_volumekey_function_entryvalue_shutter);
            String string8 = getString(R.string.pref_camera_volumekey_function_entryvalue_volume);
            previewListPreference3.setEntries(new CharSequence[]{string5, string6});
            previewListPreference3.setEntryValues(new CharSequence[]{string7, string8});
            previewListPreference3.setDefaultValue(string5);
            previewListPreference3.setValue(string6);
        }
        CheckBoxPreference checkBoxPreference2 = (CheckBoxPreference) this.mPreferenceGroup.findPreference("pref_dualcamera_watermark_key");
        if (checkBoxPreference2 != null) {
            checkBoxPreference2.setDefaultValue(Boolean.valueOf(b.v(getResources().getBoolean(R.bool.pref_device_watermark_default))));
            checkBoxPreference2.setChecked(b.v(getResources().getBoolean(R.bool.pref_device_watermark_default)));
        }
    }

    private void updatePhotoAssistanceTips(SharedPreferences sharedPreferences, ValuePreference valuePreference) {
        if (sharedPreferences != null && valuePreference != null) {
            if (!sharedPreferences.getBoolean(CameraSettings.KEY_CAMERA_LYING_TIP_SWITCH, true)) {
                if (!sharedPreferences.getBoolean(CameraSettings.KEY_PICTURE_FLAW_TIP, getResources().getBoolean(R.bool.pref_pic_flaw_tip_default))) {
                    valuePreference.setValue(getString(R.string.pref_photo_assistance_tips_off));
                    return;
                }
            }
            valuePreference.setValue(getString(R.string.pref_photo_assistance_tips_on));
        }
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [android.content.Context, com.android.camera.CameraPreferenceActivity, com.android.camera.BasePreferenceActivity] */
    private void updateQRCodeEntry() {
        PreferenceScreen preferenceScreen = this.mPreferenceGroup;
        String str = CameraSettings.KEY_SCAN_QRCODE;
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preferenceScreen.findPreference(str);
        if (checkBoxPreference != null && this.mPreferences.getBoolean(str, checkBoxPreference.isChecked()) && !CameraSettings.isQRCodeReceiverAvailable(this)) {
            Log.v(TAG, "disable QRCodeScan");
            Editor edit = this.mPreferences.edit();
            edit.putBoolean(str, false);
            edit.apply();
            checkBoxPreference.setChecked(false);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0017, code lost:
        if (r3.getBoolean("pref_dualcamera_watermark_key", com.mi.config.b.v(com.android.camera.CameraSettings.getBool(com.android.camera.R.bool.pref_device_watermark_default))) == false) goto L_0x0019;
     */
    private void updateWaterMark(SharedPreferences sharedPreferences, ValuePreference valuePreference) {
        if (CameraSettings.isSupportedDualCameraWaterMark()) {
        }
        if (!sharedPreferences.getBoolean("pref_time_watermark_key", false)) {
            valuePreference.setValue(getString(R.string.pref_watermark_off));
            return;
        }
        valuePreference.setValue(getString(R.string.pref_watermark_on));
    }

    /* access modifiers changed from: protected */
    public void filterByIntent() {
        ArrayList stringArrayListExtra = getIntent().getStringArrayListExtra(REMOVE_KEYS);
        if (stringArrayListExtra != null) {
            Iterator it = stringArrayListExtra.iterator();
            while (it.hasNext()) {
                removePreference(this.mPreferenceGroup, (String) it.next());
            }
        }
    }

    /* access modifiers changed from: protected */
    public int getPreferenceXml() {
        return R.xml.camera_other_preferences;
    }

    public void onBackPressed() {
        resetTimeOutFlag();
        CameraPreferenceActivity.super.onBackPressed();
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [android.content.Context, com.android.camera.CameraPreferenceActivity, miui.preference.PreferenceActivity, com.android.camera.BasePreferenceActivity] */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mFromWhere = getIntent().getIntExtra(FROM_WHERE, 0);
        if (getIntent().getBooleanExtra(CameraIntentManager.EXTRA_START_WHEN_LOCKED, false)) {
            getWindow().addFlags(524288);
        }
        CameraSettings.upgradeGlobalPreferences();
        Storage.initStorage(this);
        initializeActivity();
        if (getIntent().getCharSequenceExtra(":miui:starting_window_label") != null) {
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.setTitle(R.string.pref_camera_settings_category);
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return CameraPreferenceActivity.super.onOptionsItemSelected(menuItem);
        }
        resetTimeOutFlag();
        finish();
        return true;
    }

    public boolean onPreferenceChange(Preference preference, Object obj) {
        String key = preference.getKey();
        String str = CameraSettings.KEY_CAMERA_SNAP;
        if (!key.equals(str) || obj == null) {
            return super.onPreferenceChange(preference, obj);
        }
        String string = getString(R.string.pref_camera_snap_value_off);
        if (obj instanceof Boolean) {
            string = getSnapStringValue(((Boolean) obj).booleanValue());
        } else if (obj instanceof String) {
            string = (String) obj;
        }
        boolean equals = string.equals(getString(R.string.pref_camera_snap_value_take_picture));
        String str2 = Key.LONG_PRESS_VOLUME_DOWN;
        if (equals || string.equals(getString(R.string.pref_camera_snap_value_take_movie))) {
            if (Key.LONG_PRESS_VOLUME_DOWN_PAY.equals(Secure.getString(getContentResolver(), str2))) {
                bringUpDoubleConfirmDlg(preference, string);
                return false;
            }
        }
        Secure.putString(getContentResolver(), str2, CameraSettings.getMiuiSettingsKeyForStreetSnap(string));
        CameraStatUtil.trackPreferenceChange(str, string);
        return true;
    }

    /* JADX WARNING: type inference failed for: r11v0, types: [android.content.Context, com.android.camera.CameraPreferenceActivity, miui.preference.PreferenceActivity] */
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals(PREF_KEY_RESTORE)) {
            RotateDialogController.showSystemAlertDialog(this, getString(R.string.confirm_restore_title), getString(R.string.confirm_restore_message), getString(17039370), new Runnable() {
                public void run() {
                    CameraPreferenceActivity.this.restorePreferences();
                }
            }, getString(17039360), null);
            return true;
        } else if (preference.getKey().equals(PREF_KEY_PRIVACY)) {
            ActivityLauncher.launchPrivacyPolicyWebpage(this);
            return true;
        } else {
            boolean equals = preference.getKey().equals(CameraSettings.KEY_WATERMARK);
            String str = CameraIntentManager.EXTRA_START_WHEN_LOCKED;
            if (equals) {
                Intent intent = new Intent(this, WatermarkActivity.class);
                if (getIntent().getBooleanExtra(str, false)) {
                    intent.putExtra(str, true);
                }
                this.mGoToActivity = true;
                startActivity(intent);
                return true;
            } else if (preference.getKey().equals(CameraSettings.KEY_PHOTO_ASSISTANCE_TIPS)) {
                Intent intent2 = new Intent(this, PhotoAssistanceTipsActivity.class);
                try {
                    if (getIntent().getBooleanExtra(str, false)) {
                        intent2.putExtra(str, true);
                    }
                    this.mGoToActivity = true;
                    startActivity(intent2);
                } catch (Exception unused) {
                }
                return true;
            } else {
                if (CameraSettings.KEY_PRIORITY_STORAGE.equals(preference.getKey())) {
                    PriorityStorageBroadcastReceiver.setPriorityStorage(((CheckBoxPreference) preference).isChecked());
                } else {
                    if (CameraSettings.KEY_SCAN_QRCODE.equals(preference.getKey()) && !CameraSettings.isQRCodeReceiverAvailable(this)) {
                        RotateDialogController.showSystemAlertDialog(this, getString(R.string.confirm_install_scanner_title), getString(R.string.confirm_install_scanner_message), getString(R.string.install_confirmed), new Runnable() {
                            public void run() {
                                CameraPreferenceActivity.this.installQRCodeReceiver();
                            }
                        }, getString(17039360), null);
                        return true;
                    }
                }
                return false;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onRestart() {
        CameraPreferenceActivity.super.onRestart();
        if (this.mGoToActivity) {
            updateWaterMark(this.mPreferences, (ValuePreference) this.mWatermark);
            updatePhotoAssistanceTips(this.mPreferences, (ValuePreference) this.mPhotoAssistanceTips);
            this.mGoToActivity = false;
            return;
        }
        finish();
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [android.content.Context, com.android.camera.CameraPreferenceActivity, miui.preference.PreferenceActivity] */
    /* access modifiers changed from: protected */
    public void onResume() {
        CameraPreferenceActivity.super.onResume();
        updateQRCodeEntry();
        if (Util.isLabOptionsVisible()) {
            Toast.makeText(this, R.string.camera_facedetection_sub_option_hint, 1).show();
        }
    }

    public void updateConflictPreference(Preference preference) {
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) this.mPreferenceGroup.findPreference(CameraSettings.KEY_MOVIE_SOLID);
        boolean isAutoZoomEnabled = CameraSettings.isAutoZoomEnabled(this.mFromWhere);
        boolean isSuperEISEnabled = CameraSettings.isSuperEISEnabled(this.mFromWhere);
        if (this.mFromWhere == 162) {
            if (checkBoxPreference != null) {
                if (isAutoZoomEnabled || isSuperEISEnabled) {
                    checkBoxPreference.setEnabled(false);
                } else {
                    checkBoxPreference.setEnabled(true);
                }
            }
            PreferenceScreen preferenceScreen = this.mPreferenceGroup;
            String str = CameraSettings.KEY_VOLUME_VIDEO_FUNCTION;
            if (preferenceScreen.findPreference(str) != null && isAutoZoomEnabled) {
                removePreference(this.mPreferenceGroup, str);
            }
        }
    }

    public void updatePreferences(PreferenceGroup preferenceGroup, SharedPreferences sharedPreferences) {
        if (preferenceGroup != null) {
            int preferenceCount = preferenceGroup.getPreferenceCount();
            for (int i = 0; i < preferenceCount; i++) {
                Preference preference = preferenceGroup.getPreference(i);
                if (preference instanceof ValuePreference) {
                    if (preference.getKey().equals(CameraSettings.KEY_WATERMARK)) {
                        updateWaterMark(sharedPreferences, (ValuePreference) preference);
                    }
                    if (preference.getKey().equals(CameraSettings.KEY_PHOTO_ASSISTANCE_TIPS)) {
                        updatePhotoAssistanceTips(sharedPreferences, (ValuePreference) preference);
                    }
                } else if (preference instanceof PreviewListPreference) {
                    PreviewListPreference previewListPreference = (PreviewListPreference) preference;
                    if (b.hi()) {
                        String key = previewListPreference.getKey();
                        String str = CameraSettings.KEY_FRONT_MIRROR;
                        if (str.equals(key) && sharedPreferences.getString(str, null) == null) {
                            String string = getString(R.string.pref_front_mirror_entryvalue_off);
                            previewListPreference.setValue(string);
                            previewListPreference.setDefaultValue(string);
                            preference.setPersistent(false);
                        }
                    }
                    previewListPreference.setValue(getFilterValue(previewListPreference, sharedPreferences));
                    preference.setPersistent(false);
                } else if (preference instanceof CheckBoxPreference) {
                    CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
                    checkBoxPreference.setChecked(sharedPreferences.getBoolean(checkBoxPreference.getKey(), checkBoxPreference.isChecked()));
                    preference.setPersistent(false);
                } else if (preference instanceof PreferenceGroup) {
                    updatePreferences((PreferenceGroup) preference, sharedPreferences);
                } else {
                    String str2 = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("no need update preference for ");
                    sb.append(preference.getKey());
                    Log.v(str2, sb.toString());
                }
            }
        }
    }
}
