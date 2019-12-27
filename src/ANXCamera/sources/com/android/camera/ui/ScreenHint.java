package com.android.camera.ui;

import android.app.Activity;
import android.app.AlertDialog;
import com.android.camera.CameraSettings;
import com.android.camera.LocationManager;
import com.android.camera.OnScreenHint;
import com.android.camera.R;
import com.android.camera.RotateDialogController;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.data.provider.DataProvider;
import com.android.camera.permission.PermissionManager;
import com.android.camera.storage.Storage;
import com.mi.config.b;

public class ScreenHint {
    private final Activity mActivity;
    private OnScreenHint mStorageHint;
    private long mStorageSpace;
    private AlertDialog mSystemChoiceDialog;

    public ScreenHint(Activity activity) {
        this.mActivity = activity;
    }

    /* access modifiers changed from: private */
    public void recordLocation(boolean z) {
        DataProvider.ProviderEditor editor = DataRepository.dataItemGlobal().editor();
        editor.putBoolean(CameraSettings.KEY_RECORD_LOCATION, z);
        editor.apply();
        LocationManager.instance().recordLocation(z);
    }

    public void cancelHint() {
        OnScreenHint onScreenHint = this.mStorageHint;
        if (onScreenHint != null) {
            onScreenHint.cancel();
            this.mStorageHint = null;
        }
    }

    public void dismissSystemChoiceDialog() {
        AlertDialog alertDialog = this.mSystemChoiceDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mSystemChoiceDialog = null;
        }
    }

    public void hideToast() {
        RotateTextToast instance = RotateTextToast.getInstance();
        if (instance != null) {
            instance.show(0, 0);
        }
    }

    public boolean isScreenHintVisible() {
        OnScreenHint onScreenHint = this.mStorageHint;
        return onScreenHint != null && onScreenHint.getHintViewVisibility() == 0;
    }

    public void recordFirstUse(boolean z) {
        DataProvider.ProviderEditor editor = DataRepository.dataItemGlobal().editor();
        editor.putBoolean("pref_camera_first_use_hint_shown_key", z);
        editor.putBoolean(CameraSettings.KEY_CAMERA_CONFIRM_LOCATION_SHOWN, z);
        editor.apply();
    }

    public void showConfirmMessage(int i, int i2) {
        Activity activity = this.mActivity;
        RotateDialogController.showSystemAlertDialog(activity, activity.getString(i), this.mActivity.getString(i2), this.mActivity.getString(17039370), (Runnable) null, (String) null, (Runnable) null);
    }

    public void showFirstUseHint() {
        AlertDialog alertDialog = this.mSystemChoiceDialog;
        if (alertDialog != null) {
            if (!alertDialog.isShowing()) {
                dismissSystemChoiceDialog();
            } else {
                return;
            }
        }
        DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
        boolean z = dataItemGlobal.getBoolean("pref_camera_first_use_hint_shown_key", true);
        if (!PermissionManager.checkCameraLocationPermissions()) {
            z = false;
        }
        if (z) {
            boolean contains = dataItemGlobal.contains(CameraSettings.KEY_RECORD_LOCATION);
            if (b.ij() && !contains && z) {
                AnonymousClass1 r8 = new Runnable() {
                    public void run() {
                        ScreenHint.this.recordLocation(true);
                        ScreenHint.this.recordFirstUse(false);
                        ScreenHint.this.dismissSystemChoiceDialog();
                    }
                };
                AnonymousClass2 r9 = new Runnable() {
                    public void run() {
                        ScreenHint.this.recordLocation(false);
                        ScreenHint.this.recordFirstUse(false);
                        ScreenHint.this.dismissSystemChoiceDialog();
                    }
                };
                Activity activity = this.mActivity;
                this.mSystemChoiceDialog = RotateDialogController.showSystemChoiceDialog(activity, activity.getString(R.string.confirm_anxcamera_title), this.mActivity.getString(R.string.confirm_anxcamera_message), this.mActivity.getString(R.string.confirm_location_alert), this.mActivity.getString(R.string.start_capture), r8, r9);
            }
        }
    }

    public void showObjectTrackHint() {
        DataProvider.ProviderEditor editor = DataRepository.dataItemGlobal().editor();
        editor.putBoolean(CameraSettings.KEY_CAMERA_OBJECT_TRACK_HINT_SHOWN, false);
        editor.apply();
        RotateTextToast.getInstance(this.mActivity).show(R.string.object_track_enable_toast, 0);
    }

    public void updateHint() {
        Storage.switchStoragePathIfNeeded();
        this.mStorageSpace = Storage.getAvailableSpace();
        if (DataRepository.dataItemFeature().va()) {
            Storage.getAvailableSpace(Storage.RAW_DIRECTORY);
        }
        long j = this.mStorageSpace;
        String string = j == -1 ? this.mActivity.getString(R.string.no_storage) : j == -2 ? this.mActivity.getString(R.string.preparing_sd) : j == -3 ? this.mActivity.getString(R.string.access_sd_fail) : j < Storage.LOW_STORAGE_THRESHOLD ? Storage.isPhoneStoragePriority() ? this.mActivity.getString(R.string.spaceIsLow_content_primary_storage_priority) : this.mActivity.getString(R.string.spaceIsLow_content_external_storage_priority) : null;
        if (string != null) {
            OnScreenHint onScreenHint = this.mStorageHint;
            if (onScreenHint == null) {
                this.mStorageHint = OnScreenHint.makeText(this.mActivity, string);
            } else {
                onScreenHint.setText(string);
            }
            this.mStorageHint.show();
            return;
        }
        OnScreenHint onScreenHint2 = this.mStorageHint;
        if (onScreenHint2 != null) {
            onScreenHint2.cancel();
            this.mStorageHint = null;
        }
    }
}
