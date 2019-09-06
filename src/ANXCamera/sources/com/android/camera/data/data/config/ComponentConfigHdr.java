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

public class ComponentConfigHdr extends ComponentData {
    public static final String HDR_VALUE_AUTO = "auto";
    public static final String HDR_VALUE_LIVE = "live";
    public static final String HDR_VALUE_NORMAL = "normal";
    public static final String HDR_VALUE_OFF = "off";
    public static final String HDR_VALUE_ON = "on";
    private boolean mAutoSupported;
    private boolean mIsClosed;

    public ComponentConfigHdr(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
        this.mItems.add(new ComponentDataItem(getConfigHDROffRes(), getConfigHDROffRes(), (int) R.string.pref_camera_hdr_entry_off, "off"));
    }

    private int getConfigHDRAutoRes() {
        return R.drawable.ic_new_config_hdr_auto;
    }

    private int getConfigHDRLiveRes() {
        return R.drawable.ic_new_config_hdr_live;
    }

    private int getConfigHDRNormalRes() {
        return R.drawable.ic_new_config_hdr_normal;
    }

    private int getConfigHDROffRes() {
        return R.drawable.ic_new_config_hdr_off;
    }

    public void clearClosed() {
        this.mIsClosed = false;
    }

    public String getComponentValue(int i) {
        String str = "off";
        return (!isClosed() && !isEmpty()) ? super.getComponentValue(i) : str;
    }

    public String getDefaultValue(int i) {
        String str = "off";
        if (isClosed() || isEmpty() || CameraSettings.isFrontCamera()) {
            return str;
        }
        String Ea = DataRepository.dataItemFeature().Ea();
        String string = CameraAppImpl.getAndroidContext().getResources().getString(R.string.pref_hdr_value_default);
        if (TextUtils.isEmpty(Ea)) {
            Ea = string;
        }
        String str2 = "auto";
        if (!TextUtils.isEmpty(Ea)) {
            char c2 = 65535;
            int hashCode = Ea.hashCode();
            String str3 = "on";
            if (hashCode != 3551) {
                if (hashCode != 109935) {
                    if (hashCode == 3005871 && Ea.equals(str2)) {
                        c2 = 0;
                    }
                } else if (Ea.equals(str)) {
                    c2 = 2;
                }
            } else if (Ea.equals(str3)) {
                c2 = 1;
            }
            if (c2 == 0) {
                if (this.mAutoSupported) {
                    str = str2;
                }
                return str;
            } else if (c2 == 1) {
                return str3;
            } else {
                if (c2 == 2) {
                    return str;
                }
            }
        }
        return this.mAutoSupported ? str2 : str;
    }

    public int getDisplayTitleString() {
        return R.string.pref_camera_hdr_title;
    }

    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    public String getKey(int i) {
        if (i != 160) {
            return (i == 162 || i == 169) ? CameraSettings.KEY_VIDEO_HDR : CameraSettings.KEY_CAMERA_HDR;
        }
        throw new RuntimeException("unspecified hdr");
    }

    public String getPersistValue(int i) {
        return super.getComponentValue(i);
    }

    public int getValueSelectedDrawableIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        if ("off".equals(componentValue)) {
            return getConfigHDROffRes();
        }
        if ("auto".equals(componentValue)) {
            return getConfigHDRAutoRes();
        }
        if ("normal".equals(componentValue)) {
            return getConfigHDRNormalRes();
        }
        if ("live".equals(componentValue)) {
            return getConfigHDRLiveRes();
        }
        if ("on".equals(componentValue)) {
            return getConfigHDRNormalRes();
        }
        return -1;
    }

    public int getValueSelectedStringIdIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        if ("off".equals(componentValue)) {
            return R.string.accessibility_hdr_off;
        }
        if ("auto".equals(componentValue)) {
            return R.string.accessibility_hdr_auto;
        }
        if ("normal".equals(componentValue)) {
            return R.string.accessibility_hdr_on;
        }
        if ("live".equals(componentValue)) {
            return R.string.accessibility_hdr_live;
        }
        if ("on".equals(componentValue)) {
            return R.string.accessibility_hdr_on;
        }
        return -1;
    }

    public boolean isClosed() {
        return this.mIsClosed;
    }

    public boolean isSupportAutoHdr() {
        return this.mAutoSupported;
    }

    public List<ComponentDataItem> reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        List<ComponentDataItem> list = this.mItems;
        if (list == null) {
            this.mItems = new ArrayList();
        } else {
            list.clear();
        }
        this.mAutoSupported = false;
        if (!cameraCapabilities.isSupportHdr()) {
            return this.mItems;
        }
        if (i == 163 || i == 165) {
            this.mItems.add(new ComponentDataItem(getConfigHDROffRes(), getConfigHDROffRes(), (int) R.string.pref_camera_hdr_entry_off, "off"));
            if (cameraCapabilities.isSupportAutoHdr()) {
                this.mAutoSupported = true;
                this.mItems.add(new ComponentDataItem(getConfigHDRAutoRes(), getConfigHDRAutoRes(), (int) R.string.pref_camera_hdr_entry_auto, "auto"));
            }
            String str = "normal";
            if (b.tm || !b.Yi()) {
                this.mItems.add(new ComponentDataItem(getConfigHDRNormalRes(), getConfigHDRNormalRes(), (int) R.string.pref_simple_hdr_entry_on, str));
            } else {
                if (!b.IS_MI2A) {
                    this.mItems.add(new ComponentDataItem(getConfigHDRNormalRes(), getConfigHDRNormalRes(), (int) R.string.pref_camera_hdr_entry_normal, str));
                }
                this.mItems.add(new ComponentDataItem(getConfigHDRLiveRes(), getConfigHDRLiveRes(), (int) R.string.pref_camera_hdr_entry_live, "live"));
            }
        }
        return this.mItems;
    }

    public void setClosed(boolean z) {
        this.mIsClosed = z;
    }

    public void setComponentValue(int i, String str) {
        setClosed(false);
        super.setComponentValue(i, str);
    }
}
