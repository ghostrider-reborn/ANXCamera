package com.android.camera.data.data.config;

import android.text.TextUtils;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera2.CameraCapabilities;
import com.mi.config.b;
import java.util.ArrayList;
import java.util.List;

public class ComponentManuallyWB extends ComponentData {
    public static final String MANUAL_WHITEBALANCE_VALUE = "pref_qc_manual_whitebalance_k_value_key";

    public ComponentManuallyWB(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    /* access modifiers changed from: protected */
    public boolean checkValueValid(String str) {
        for (ComponentDataItem componentDataItem : getItems()) {
            if (TextUtils.equals(str, componentDataItem.mValue)) {
                return true;
            }
        }
        return false;
    }

    public int getCustomWB() {
        return this.mParentDataItem.getInt("pref_qc_manual_whitebalance_k_value_key", CameraAppImpl.getAndroidContext().getResources().getInteger(R.integer.default_manual_whitebalance_value));
    }

    public String getDefaultValue(int i) {
        return "1";
    }

    public int getDisplayTitleString() {
        return R.string.pref_camera_whitebalance_title;
    }

    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    public String getKey(int i) {
        return CameraSettings.KEY_WHITE_BALANCE;
    }

    public List<ComponentDataItem> reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        if (this.mItems == null) {
            this.mItems = new ArrayList();
        } else {
            this.mItems.clear();
        }
        this.mItems.add(new ComponentDataItem(-1, -1, (int) R.string.pref_camera_whitebalance_entry_auto, "1"));
        this.mItems.add(new ComponentDataItem((int) R.drawable.bg_white_balance_incandescent, (int) R.drawable.ic_white_balance_incandescent, -1, "2"));
        this.mItems.add(new ComponentDataItem((int) R.drawable.bg_white_balance_sunlight, (int) R.drawable.ic_white_balance_sunlight, -1, "5"));
        this.mItems.add(new ComponentDataItem((int) R.drawable.bg_white_balance_fluorescent, (int) R.drawable.ic_white_balance_fluorescent, -1, "3"));
        this.mItems.add(new ComponentDataItem((int) R.drawable.bg_white_balance_cloudy, (int) R.drawable.ic_white_balance_cloudy, -1, "6"));
        if (b.jl() && DataRepository.dataItemFeature().gu()) {
            this.mItems.add(new ComponentDataItem((int) R.drawable.bg_white_balance_manual, (int) R.drawable.ic_white_balance_manual, -1, "manual"));
        }
        return this.mItems;
    }

    /* access modifiers changed from: protected */
    public void resetComponentValue(int i) {
        setComponentValue(i, getDefaultValue(i));
    }

    public void setCustomWB(int i) {
        this.mParentDataItem.editor().putInt("pref_qc_manual_whitebalance_k_value_key", i).apply();
    }
}
