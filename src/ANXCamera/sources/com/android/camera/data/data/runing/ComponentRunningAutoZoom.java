package com.android.camera.data.data.runing;

import android.support.annotation.NonNull;
import android.util.ArrayMap;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.List;
import java.util.Map;

public class ComponentRunningAutoZoom extends ComponentData {
    private int mCameraId;
    private boolean mIsNormalIntent;
    private Map<String, Boolean> mValues = new ArrayMap();

    public ComponentRunningAutoZoom(DataItemRunning dataItemRunning) {
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
        if (i == 162 || i == 169) {
            return "pref_camera_auto_zoom";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("pref_camera_auto_zoom_");
        sb.append(Integer.toHexString(i));
        return sb.toString();
    }

    public int getResIcon(boolean z) {
        return z ? R.drawable.ic_autozoom_menu_on : R.drawable.ic_autozoom_menu_off;
    }

    public int getResText() {
        return R.string.autozoom_hint;
    }

    public boolean isEnabled(int i) {
        boolean z = false;
        if (!DataRepository.dataItemFeature().Cb()) {
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
