package com.android.camera.data.data.config;

import android.annotation.TargetApi;
import android.util.Range;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.List;

@TargetApi(21)
public class ComponentManuallyISO extends ComponentData {
    private static final String TAG = "ComponentManuallyISO";
    private ComponentDataItem[] mFullItems;

    public ComponentManuallyISO(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    private ComponentDataItem[] getFullItems() {
        if (this.mFullItems != null) {
            return this.mFullItems;
        }
        this.mFullItems = new ComponentDataItem[]{new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_auto, "0"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso100, "100"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso200, ComponentConfigFlash.FLASH_VALUE_MANUAL_OFF), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso400, "400"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso800, "800"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso1600, "1600"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso3200, "3200")};
        return this.mFullItems;
    }

    /* access modifiers changed from: protected */
    public boolean checkValueValid(String str) {
        return Util.isStringValueContained((Object) str, (int) R.array.pref_camera_iso_entryvalues);
    }

    public String getComponentValue(int i) {
        String componentValue = super.getComponentValue(i);
        List<ComponentDataItem> items = getItems();
        if (items.isEmpty()) {
            return componentValue;
        }
        String str = items.get(items.size() - 1).mValue;
        return Integer.parseInt(componentValue) > Integer.parseInt(str) ? str : componentValue;
    }

    public String getDefaultValue(int i) {
        return "0";
    }

    public int getDisplayTitleString() {
        return R.string.pref_camera_iso_title;
    }

    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    public String getKey(int i) {
        return CameraSettings.KEY_QC_ISO;
    }

    public List<ComponentDataItem> reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        if (this.mItems == null) {
            this.mItems = new ArrayList();
        } else {
            this.mItems.clear();
        }
        if (cameraCapabilities == null) {
            Log.w(TAG, "initItems: CameraCapabilities is null!!!");
            return this.mItems;
        }
        ComponentDataItem[] fullItems = getFullItems();
        this.mItems.add(fullItems[0]);
        Range<Integer> isoRange = cameraCapabilities.getIsoRange();
        if (isoRange != null) {
            int intValue = isoRange.getLower().intValue();
            int intValue2 = isoRange.getUpper().intValue();
            for (int i3 = 1; i3 < fullItems.length; i3++) {
                ComponentDataItem componentDataItem = fullItems[i3];
                int parseInt = Integer.parseInt(componentDataItem.mValue);
                if (i3 == 0 || (intValue <= parseInt && parseInt <= intValue2)) {
                    this.mItems.add(componentDataItem);
                }
            }
        }
        return this.mItems;
    }

    /* access modifiers changed from: protected */
    public void resetComponentValue(int i) {
        setComponentValue(i, getDefaultValue(i));
    }
}
