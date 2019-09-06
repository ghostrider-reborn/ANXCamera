package com.android.camera.data.data.runing;

import android.support.annotation.NonNull;
import android.util.ArrayMap;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.List;
import java.util.Map;

public class ComponentRunningSuperEIS extends ComponentData {
    private int mCameraId;
    private boolean mIsNormalIntent;
    private Map<String, Boolean> mValues = new ArrayMap();

    public ComponentRunningSuperEIS(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    public void clearArrayMap() {
        this.mValues.clear();
    }

    @NonNull
    public String getDefaultValue(int i) {
        return Boolean.toString(false);
    }

    public int getDisplayTitleString() {
        return 0;
    }

    public List<ComponentDataItem> getItems() {
        return null;
    }

    public String getKey(int i) {
        if (i == 162) {
            return "pref_camera_super_eis";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("pref_camera_super_eis_");
        sb.append(Integer.toHexString(i));
        return sb.toString();
    }

    public boolean isEnabled(int i) {
        boolean z = false;
        if (!DataRepository.dataItemFeature().hc()) {
            return false;
        }
        if (this.mCameraId == 0 && i == 162) {
            if (!this.mIsNormalIntent) {
                return false;
            }
            Boolean bool = (Boolean) this.mValues.get(getKey(i));
            if (bool != null) {
                z = bool.booleanValue();
            }
        }
        return z;
    }

    public void reInit(int i, boolean z) {
        this.mCameraId = i;
        this.mIsNormalIntent = z;
    }

    public void reInitIntentType(boolean z) {
        this.mIsNormalIntent = z;
    }

    public void setEnabled(int i, boolean z) {
        this.mValues.put(getKey(i), Boolean.valueOf(z));
    }
}
