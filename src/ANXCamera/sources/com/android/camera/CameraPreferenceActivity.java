package com.android.camera;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.provider.MiuiSettings;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigSlowMotion;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
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

public class CameraPreferenceActivity extends BasePreferenceActivity {
    public static final String FROM_WHERE = "from_where";
    public static final String IS_IMAGE_CAPTURE_INTENT = "IsCaptureIntent";
    protected static final String PREF_KEY_ANXBATTLE = "pref_anxbattle";
    protected static final String PREF_KEY_ANXCAMERA = "pref_anxcamera";
    protected static final String PREF_KEY_PRIVACY = "pref_privacy";
    protected static final String PREF_KEY_RESTORE = "pref_restore";
    public static final String REMOVE_KEYS = "remove_keys";
    public static final String TAG = "CameraPreferenceActivity";
    /* access modifiers changed from: private */
    public CompatibilityUtils.PackageInstallerListener mAppInstalledListener = new CompatibilityUtils.PackageInstallerListener() {
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

    private void bringUpDoubleConfirmDlg(final Preference preference, final String str) {
        if (this.mDoubleConfirmActionChooseDialog == null) {
            final boolean snapBoolValue = getSnapBoolValue(str);
            DetachableClickListener wrap = DetachableClickListener.wrap(new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == -1) {
                        AlertDialog unused = CameraPreferenceActivity.this.mDoubleConfirmActionChooseDialog = null;
                        CameraStatUtil.trackPreferenceChange(CameraSettings.KEY_CAMERA_SNAP, str);
                        Preference preference = preference;
                        if (preference instanceof CheckBoxPreference) {
                            ((CheckBoxPreference) preference).setChecked(snapBoolValue);
                        } else if (preference instanceof PreviewListPreference) {
                            ((PreviewListPreference) preference).setValue(str);
                        }
                        Settings.Secure.putString(CameraPreferenceActivity.this.getContentResolver(), MiuiSettings.Key.LONG_PRESS_VOLUME_DOWN, CameraSettings.getMiuiSettingsKeyForStreetSnap(str));
                    } else if (i == -2) {
                        CameraPreferenceActivity.this.mDoubleConfirmActionChooseDialog.dismiss();
                        AlertDialog unused2 = CameraPreferenceActivity.this.mDoubleConfirmActionChooseDialog = null;
                    }
                }
            });
            this.mDoubleConfirmActionChooseDialog = new AlertDialog.Builder(this).setTitle(R.string.title_snap_double_confirm).setMessage(R.string.message_snap_double_confirm).setPositiveButton(R.string.snap_confirmed, wrap).setNegativeButton(R.string.snap_cancel, wrap).setCancelable(false).create();
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
            removeFromGroup(this.mPreferenceGroup, CameraSettings.KEY_LONG_PRESS_SHUTTER);
            removeFromGroup(this.mPreferenceGroup, CameraSettings.KEY_FINGERPRINT_CAPTURE);
            removeFromGroup(this.mPreferenceGroup, CameraSettings.KEY_LONG_PRESS_SHUTTER);
        }
        int i = this.mFromWhere;
        if (i == 161 || i == 174 || i == 179 || i == 169 || i == 172 || i == 177) {
            removeFromGroup(findPreference, CameraSettings.KEY_FRONT_MIRROR);
            removeFromGroup(findPreference2, CameraSettings.KEY_FRONT_MIRROR);
            return;
        }
        boolean vc = DataRepository.dataItemFeature().vc();
        String str = TAG;
        Log.d(str, "filterByConfig: isSupportVideoFrontMirror = " + vc);
        if (vc) {
            removeFromGroup(findPreference2, CameraSettings.KEY_FRONT_MIRROR);
        } else {
            removeFromGroup(findPreference, CameraSettings.KEY_FRONT_MIRROR);
        }
        if (b.hi() && this.mFromWhere == 162) {
            removeFromGroup(findPreference, CameraSettings.KEY_FRONT_MIRROR);
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
        if (b.Ln) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_PROXIMITY_LOCK);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_RETAIN_CAMERA_MODE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_SNAP);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_LONG_PRESS_SHUTTER_FEATURE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_SCAN_QRCODE);
        }
        if (!b.mj()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_MOVIE_SOLID);
        }
        if (!b.yj() && !CameraSettings.isSupportedDualCameraWaterMark()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_WATERMARK);
            removePreference(this.mPreferenceGroup, "pref_dualcamera_watermark_key");
            removePreference(this.mPreferenceGroup, "pref_time_watermark_key");
        } else if (b.yj() && !CameraSettings.isSupportedDualCameraWaterMark()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_WATERMARK);
            removePreference(this.mPreferenceGroup, "pref_dualcamera_watermark_key");
        } else if (b.yj() || !CameraSettings.isSupportedDualCameraWaterMark() || DataRepository.dataItemFeature().Db()) {
            removePreference(this.mPreferenceGroup, "pref_dualcamera_watermark_key");
            removePreference(this.mPreferenceGroup, "pref_time_watermark_key");
        } else {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_WATERMARK);
            removePreference(this.mPreferenceGroup, "pref_time_watermark_key");
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
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_LONG_PRESS_SHUTTER_FEATURE);
        }
        if (!b.pj()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAPTURE_WHEN_STABLE);
        }
        if (!b.aj()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_ASD_NIGHT);
        }
        if (!b.rj()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_SNAP);
        }
        if (!b.Ni()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_GROUPSHOT_PRIMITIVE);
        }
        if (!CameraSettings.isSupportedPortrait()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_PORTRAIT_WITH_FACEBEAUTY);
        }
        if (!b.isSupportedOpticalZoom() && !DataRepository.dataItemFeature().nc()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_DUAL_ENABLE);
        }
        if (!b.isSupportedOpticalZoom()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_DUAL_SAT_ENABLE);
        }
        if (!b.isSupportSuperResolution()) {
            removePreference(this.mPreferenceGroup, "pref_camera_super_resolution_key");
        }
        if (!DataRepository.dataItemFeature().Jc()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_PARALLEL_PROCESS_ENABLE);
        }
        if (!CameraSettings.isSupportQuickShot()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_QUICK_SHOT_ENABLE);
        }
        if (b.Ej()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_FACE_DETECTION);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_FACE_DETECTION_AUTO_HIDDEN);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_PARALLEL_PROCESS_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_QUICK_SHOT_ANIM_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_TOUCH_FOCUS_DELAY_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_QUICK_SHOT_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_DUAL_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_DUAL_SAT_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_FRONT_MIRROR);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_QC_SHARPNESS);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_QC_CONTRAST);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_QC_SATURATION);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_AUTOEXPOSURE);
        }
        if (!ProximitySensorLock.supported()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_PROXIMITY_LOCK);
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
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_SCAN_QRCODE);
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
        if (i <= 1) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_PHOTO_ASSISTANCE_TIPS);
            if (!DataRepository.dataItemFeature().Zb()) {
                removePreference(this.mPreferenceGroup, CameraSettings.KEY_PICTURE_FLAW_TIP);
            }
            if (!DataRepository.dataItemFeature().Pb()) {
                removePreference(this.mPreferenceGroup, CameraSettings.KEY_LENS_DIRTY_TIP);
            }
            if (!DataRepository.dataItemFeature().Rc()) {
                removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_LYING_TIP_SWITCH);
                return;
            }
            return;
        }
        removePreference(this.mPreferenceGroup, CameraSettings.KEY_PICTURE_FLAW_TIP);
        removePreference(this.mPreferenceGroup, CameraSettings.KEY_LENS_DIRTY_TIP);
        removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_LYING_TIP_SWITCH);
    }

    private void filterByFrom() {
        removePreference(this.mPreferenceGroup, CameraSettings.KEY_AUTOEXPOSURE);
        removePreference(this.mPreferenceGroup, CameraSettings.KEY_VIDEO_AUTOEXPOSURE);
        int i = this.mFromWhere;
        if (i == 163 || i == 165 || i == 166 || i == 176 || i == 167 || i == 173 || i == 175 || i == 171) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_VOLUME_VIDEO_FUNCTION);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_VOLUME_LIVE_FUNCTION);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CATEGORY_CAMCORDER_SETTING);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_VIDEO_AUTOEXPOSURE);
        } else if (i == 161 || i == 174 || i == 179 || i == 162 || i == 169 || i == 172) {
            int i2 = this.mFromWhere;
            if (i2 == 174 || i2 == 179) {
                removePreference(this.mPreferenceGroup, CameraSettings.KEY_VOLUME_VIDEO_FUNCTION);
            } else {
                removePreference(this.mPreferenceGroup, CameraSettings.KEY_VOLUME_LIVE_FUNCTION);
            }
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_VOLUME_CAMERA_FUNCTION);
            removeNonVideoPreference();
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_AUTOEXPOSURE);
        } else {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_VOLUME_VIDEO_FUNCTION);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_VOLUME_LIVE_FUNCTION);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_VIDEO_AUTOEXPOSURE);
        }
        if (DataRepository.dataItemFeature().nd()) {
            String componentValue = DataRepository.dataItemConfig().getComponentConfigSlowMotion().getComponentValue(172);
            if (!DataRepository.dataItemFeature().xb() || !ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_960.equals(componentValue) || this.mFromWhere != 172) {
                removePreference(this.mPreferenceGroup, CameraSettings.KEY_6MW_STATUS);
                return;
            }
            return;
        }
        removePreference(this.mPreferenceGroup, CameraSettings.KEY_6MW_STATUS);
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
        if (Util.isStringValueContained((Object) string, previewListPreference.getEntryValues())) {
            return string;
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(previewListPreference.getKey(), value);
        edit.apply();
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
        updateConflictPreference((Preference) null);
    }

    /* access modifiers changed from: private */
    public void installQRCodeReceiver() {
        new AsyncTask<Void, Void, Void>() {
            /* access modifiers changed from: protected */
            public Void doInBackground(Void... voidArr) {
                Log.v(CameraPreferenceActivity.TAG, "install...");
                CameraPreferenceActivity cameraPreferenceActivity = CameraPreferenceActivity.this;
                Util.installPackage(cameraPreferenceActivity, "com.xiaomi.scanner", cameraPreferenceActivity.mAppInstalledListener, false, true);
                return null;
            }
        }.execute(new Void[0]);
    }

    private static HashMap<String, Boolean> readKeptValues(boolean z) {
        HashMap<String, Boolean> hashMap = new HashMap<>(6);
        hashMap.put(CameraSettings.KEY_CAMERA_FIRST_USE_PERMISSION_SHOWN, Boolean.valueOf(DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_CAMERA_FIRST_USE_PERMISSION_SHOWN, true)));
        if (z) {
            for (String next : DataItemGlobal.sUseHints) {
                if (DataRepository.dataItemGlobal().contains(next)) {
                    hashMap.put(next, Boolean.valueOf(DataRepository.dataItemGlobal().getBoolean(next, false)));
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
        Preference findPreference3 = this.mPreferenceGroup.findPreference(PREF_KEY_ANXCAMERA);
        if (findPreference3 != null) {
            findPreference3.setOnPreferenceClickListener(this);
        }
        Preference findPreference4 = this.mPreferenceGroup.findPreference("pref_checkupdate");
        if (findPreference4 != null) {
            findPreference4.setOnPreferenceClickListener(this);
        }
        Preference findPreference5 = this.mPreferenceGroup.findPreference(PREF_KEY_ANXBATTLE);
        if (findPreference5 != null) {
            findPreference5.setOnPreferenceClickListener(this);
        }
        Preference findPreference6 = this.mPreferenceGroup.findPreference(CameraSettings.KEY_PRIORITY_STORAGE);
        if (findPreference6 != null) {
            findPreference6.setOnPreferenceClickListener(this);
        }
        Preference findPreference7 = this.mPreferenceGroup.findPreference(CameraSettings.KEY_FACE_DETECTION);
        if (findPreference7 != null) {
            findPreference7.setOnPreferenceClickListener(this);
        }
        Preference findPreference8 = this.mPreferenceGroup.findPreference(CameraSettings.KEY_SCAN_QRCODE);
        if (findPreference8 != null) {
            findPreference8.setOnPreferenceClickListener(this);
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
        HashMap<String, Boolean> readKeptValues = readKeptValues(z);
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
        String string = Settings.Secure.getString(getContentResolver(), MiuiSettings.Key.LONG_PRESS_VOLUME_DOWN);
        if (MiuiSettings.Key.LONG_PRESS_VOLUME_DOWN_STREET_SNAP_PICTURE.equals(string) || MiuiSettings.Key.LONG_PRESS_VOLUME_DOWN_STREET_SNAP_MOVIE.equals(string)) {
            Settings.Secure.putString(getContentResolver(), MiuiSettings.Key.LONG_PRESS_VOLUME_DOWN, "none");
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
        for (String next : hashMap.keySet()) {
            DataRepository.dataItemGlobal().putBoolean(next, hashMap.get(next).booleanValue());
        }
    }

    private void updateEntries() {
        PreviewListPreference previewListPreference = (PreviewListPreference) this.mPreferenceGroup.findPreference(CameraSettings.KEY_ANTIBANDING);
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) this.mPreferenceGroup.findPreference(CameraSettings.KEY_AUTO_CHROMA_FLASH);
        PreviewListPreference previewListPreference2 = (PreviewListPreference) this.mPreferenceGroup.findPreference(CameraSettings.KEY_CAMERA_SNAP);
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
            String string3 = Settings.Secure.getString(getContentResolver(), MiuiSettings.Key.LONG_PRESS_VOLUME_DOWN);
            if (MiuiSettings.Key.LONG_PRESS_VOLUME_DOWN_PAY.equals(string3) || "none".equals(string3)) {
                previewListPreference2.setValue(getString(R.string.pref_camera_snap_value_off));
            } else {
                String string4 = DataRepository.dataItemGlobal().getString(CameraSettings.KEY_CAMERA_SNAP, (String) null);
                if (string4 != null) {
                    Settings.Secure.putString(getContentResolver(), MiuiSettings.Key.LONG_PRESS_VOLUME_DOWN, CameraSettings.getMiuiSettingsKeyForStreetSnap(string4));
                    DataRepository.dataItemGlobal().editor().remove(CameraSettings.KEY_CAMERA_SNAP).apply();
                    previewListPreference2.setValue(string4);
                } else if (MiuiSettings.Key.LONG_PRESS_VOLUME_DOWN_STREET_SNAP_PICTURE.equals(string3)) {
                    previewListPreference2.setValue(getString(R.string.pref_camera_snap_value_take_picture));
                } else if (MiuiSettings.Key.LONG_PRESS_VOLUME_DOWN_STREET_SNAP_MOVIE.equals(string3)) {
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
            if (sharedPreferences.getBoolean(CameraSettings.KEY_CAMERA_LYING_TIP_SWITCH, true) || sharedPreferences.getBoolean(CameraSettings.KEY_PICTURE_FLAW_TIP, getResources().getBoolean(R.bool.pref_pic_flaw_tip_default))) {
                valuePreference.setValue(getString(R.string.pref_photo_assistance_tips_on));
            } else {
                valuePreference.setValue(getString(R.string.pref_photo_assistance_tips_off));
            }
        }
    }

    private void updateQRCodeEntry() {
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) this.mPreferenceGroup.findPreference(CameraSettings.KEY_SCAN_QRCODE);
        if (checkBoxPreference != null && this.mPreferences.getBoolean(CameraSettings.KEY_SCAN_QRCODE, checkBoxPreference.isChecked()) && !CameraSettings.isQRCodeReceiverAvailable(this)) {
            Log.v(TAG, "disable QRCodeScan");
            SharedPreferences.Editor edit = this.mPreferences.edit();
            edit.putBoolean(CameraSettings.KEY_SCAN_QRCODE, false);
            edit.apply();
            checkBoxPreference.setChecked(false);
        }
    }

    private void updateWaterMark(SharedPreferences sharedPreferences, ValuePreference valuePreference) {
        if ((!CameraSettings.isSupportedDualCameraWaterMark() || !sharedPreferences.getBoolean("pref_dualcamera_watermark_key", b.v(CameraSettings.getBool(R.bool.pref_device_watermark_default)))) && !sharedPreferences.getBoolean("pref_time_watermark_key", false)) {
            valuePreference.setValue(getString(R.string.pref_watermark_off));
        } else {
            valuePreference.setValue(getString(R.string.pref_watermark_on));
        }
    }

    /* access modifiers changed from: protected */
    public void filterByIntent() {
        ArrayList<String> stringArrayListExtra = getIntent().getStringArrayListExtra(REMOVE_KEYS);
        if (stringArrayListExtra != null) {
            Iterator<String> it = stringArrayListExtra.iterator();
            while (it.hasNext()) {
                removePreference(this.mPreferenceGroup, it.next());
            }
        }
    }

    /* access modifiers changed from: protected */
    public int getPreferenceXml() {
        return R.xml.camera_other_preferences;
    }

    public void onBackPressed() {
        resetTimeOutFlag();
        super.onBackPressed();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mFromWhere = getIntent().getIntExtra(FROM_WHERE, 0);
        if (getIntent().getBooleanExtra(CameraIntentManager.EXTRA_START_WHEN_LOCKED, false)) {
            getWindow().addFlags(524288);
        }
        CameraSettings.upgradeGlobalPreferences();
        Storage.initStorage(this);
        initializeActivity();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        resetTimeOutFlag();
        finish();
        return true;
    }

    public boolean onPreferenceChange(Preference preference, Object obj) {
        if (!preference.getKey().equals(CameraSettings.KEY_CAMERA_SNAP) || obj == null) {
            return super.onPreferenceChange(preference, obj);
        }
        String string = getString(R.string.pref_camera_snap_value_off);
        if (obj instanceof Boolean) {
            string = getSnapStringValue(((Boolean) obj).booleanValue());
        } else if (obj instanceof String) {
            string = (String) obj;
        }
        if ((string.equals(getString(R.string.pref_camera_snap_value_take_picture)) || string.equals(getString(R.string.pref_camera_snap_value_take_movie))) && MiuiSettings.Key.LONG_PRESS_VOLUME_DOWN_PAY.equals(Settings.Secure.getString(getContentResolver(), MiuiSettings.Key.LONG_PRESS_VOLUME_DOWN))) {
            bringUpDoubleConfirmDlg(preference, string);
            return false;
        }
        Settings.Secure.putString(getContentResolver(), MiuiSettings.Key.LONG_PRESS_VOLUME_DOWN, CameraSettings.getMiuiSettingsKeyForStreetSnap(string));
        CameraStatUtil.trackPreferenceChange(CameraSettings.KEY_CAMERA_SNAP, string);
        return true;
    }

    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals(PREF_KEY_RESTORE)) {
            RotateDialogController.showSystemAlertDialog(this, getString(R.string.confirm_restore_title), getString(R.string.confirm_restore_message), getString(17039370), new Runnable() {
                public void run() {
                    CameraPreferenceActivity.this.restorePreferences();
                }
            }, getString(17039360), (Runnable) null);
            return true;
        } else if (preference.getKey().equals(PREF_KEY_PRIVACY)) {
            ActivityLauncher.launchPrivacyPolicyWebpage(this);
            return true;
        } else if (preference.getKey().equals(PREF_KEY_ANXBATTLE)) {
            ActivityLauncher.launchANXBattle(this);
            return true;
        } else if (preference.getKey().equals("pref_checkupdate")) {
            ActivityLauncher.launchANXCameraWebpage(this);
            return true;
        } else if (preference.getKey().equals(PREF_KEY_ANXCAMERA)) {
            ActivityLauncher.launchANXCameraChat(this);
            return true;
        } else if (preference.getKey().equals(CameraSettings.KEY_WATERMARK)) {
            Intent intent = new Intent(this, WatermarkActivity.class);
            if (getIntent().getBooleanExtra(CameraIntentManager.EXTRA_START_WHEN_LOCKED, false)) {
                intent.putExtra(CameraIntentManager.EXTRA_START_WHEN_LOCKED, true);
            }
            this.mGoToActivity = true;
            startActivity(intent);
            return true;
        } else if (preference.getKey().equals(CameraSettings.KEY_PHOTO_ASSISTANCE_TIPS)) {
            Intent intent2 = new Intent(this, PhotoAssistanceTipsActivity.class);
            try {
                if (getIntent().getBooleanExtra(CameraIntentManager.EXTRA_START_WHEN_LOCKED, false)) {
                    intent2.putExtra(CameraIntentManager.EXTRA_START_WHEN_LOCKED, true);
                }
                this.mGoToActivity = true;
                startActivity(intent2);
            } catch (Exception unused) {
            }
            return true;
        } else {
            if (CameraSettings.KEY_PRIORITY_STORAGE.equals(preference.getKey())) {
                PriorityStorageBroadcastReceiver.setPriorityStorage(((CheckBoxPreference) preference).isChecked());
            } else if (CameraSettings.KEY_SCAN_QRCODE.equals(preference.getKey()) && !CameraSettings.isQRCodeReceiverAvailable(this)) {
                RotateDialogController.showSystemAlertDialog(this, getString(R.string.confirm_install_scanner_title), getString(R.string.confirm_install_scanner_message), getString(R.string.install_confirmed), new Runnable() {
                    public void run() {
                        CameraPreferenceActivity.this.installQRCodeReceiver();
                    }
                }, getString(17039360), (Runnable) null);
                return true;
            }
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void onRestart() {
        super.onRestart();
        if (this.mGoToActivity) {
            updateWaterMark(this.mPreferences, (ValuePreference) this.mWatermark);
            updatePhotoAssistanceTips(this.mPreferences, (ValuePreference) this.mPhotoAssistanceTips);
            this.mGoToActivity = false;
            return;
        }
        finish();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
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
            if (this.mPreferenceGroup.findPreference(CameraSettings.KEY_VOLUME_VIDEO_FUNCTION) != null && isAutoZoomEnabled) {
                removePreference(this.mPreferenceGroup, CameraSettings.KEY_VOLUME_VIDEO_FUNCTION);
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
                    if (!b.hi() || !CameraSettings.KEY_FRONT_MIRROR.equals(previewListPreference.getKey()) || sharedPreferences.getString(CameraSettings.KEY_FRONT_MIRROR, (String) null) != null) {
                        previewListPreference.setValue(getFilterValue(previewListPreference, sharedPreferences));
                    } else {
                        String string = getString(R.string.pref_front_mirror_entryvalue_off);
                        previewListPreference.setValue(string);
                        previewListPreference.setDefaultValue(string);
                    }
                    preference.setPersistent(false);
                } else if (preference instanceof CheckBoxPreference) {
                    CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
                    checkBoxPreference.setChecked(sharedPreferences.getBoolean(checkBoxPreference.getKey(), checkBoxPreference.isChecked()));
                    preference.setPersistent(false);
                } else if (preference instanceof PreferenceGroup) {
                    updatePreferences((PreferenceGroup) preference, sharedPreferences);
                } else {
                    Log.v(TAG, "no need update preference for " + preference.getKey());
                }
            }
        }
    }
}
