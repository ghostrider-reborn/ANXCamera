package com.android.camera.data.data.config;

import android.text.TextUtils;
import android.util.Range;
import android.util.Size;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ComponentConfigSlowMotionQuality extends ComponentData {
    public static final String QUALITY_1080P = "6";
    public static final String QUALITY_720P = "5";
    public static final String SIZE_FPS_1080_120 = "1920x1080:120";
    public static final String SIZE_FPS_1080_240 = "1920x1080:240";
    private static final String TAG = "ComponentConfigSlowMotionQuality";
    public static String mCurrentFps = "slow_motion_120";

    public ComponentConfigSlowMotionQuality(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    public static ArrayList<String> getSupportedHfrSettings(CameraCapabilities cameraCapabilities) {
        Size[] supportedHighSpeedVideoSize;
        ArrayList<String> arrayList = new ArrayList<>();
        for (Size size : cameraCapabilities.getSupportedHighSpeedVideoSize()) {
            if (size.getWidth() == 1920 || size.getWidth() == 1280) {
                for (Range upper : cameraCapabilities.getSupportedHighSpeedVideoFPSRange(size)) {
                    String format = String.format(Locale.ENGLISH, "%dx%d:%d", new Object[]{Integer.valueOf(size.getWidth()), Integer.valueOf(size.getHeight()), upper.getUpper()});
                    if (!arrayList.contains(format)) {
                        arrayList.add(format);
                    }
                }
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public boolean checkValueValid(String str) {
        for (ComponentDataItem componentDataItem : getItems()) {
            if (TextUtils.equals(str, componentDataItem.mValue)) {
                return true;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("checkValueValid: invalid value: ");
        sb.append(str);
        Log.d(TAG, sb.toString());
        return false;
    }

    public boolean disableUpdate() {
        List<ComponentDataItem> list = this.mItems;
        return list == null || list.size() == 1;
    }

    public String getComponentValue(int i) {
        String defaultValue = getDefaultValue(i);
        String string = this.mParentDataItem.getString(getKey(i), defaultValue);
        if (string == null || string.equals(defaultValue) || checkValueValid(string)) {
            return string;
        }
        String simpleName = ComponentConfigSlowMotionQuality.class.getSimpleName();
        StringBuilder sb = new StringBuilder();
        sb.append("Items do not have this ");
        sb.append(string);
        sb.append(",so return defaultValue = ");
        sb.append(defaultValue);
        Log.e(simpleName, sb.toString());
        return defaultValue;
    }

    public String getDefaultValue(int i) {
        return "5";
    }

    public int getDisplayTitleString() {
        return R.string.pref_video_quality_title;
    }

    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    public String getKey(int i) {
        return "pref_video_new_slow_motion_key";
    }

    public String getNextValue(int i) {
        String persistValue = getPersistValue(i);
        int size = this.mItems.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (TextUtils.equals(((ComponentDataItem) this.mItems.get(i2)).mValue, persistValue)) {
                return ((ComponentDataItem) this.mItems.get((i2 + 1) % size)).mValue;
            }
        }
        return getDefaultValue(i);
    }

    public List<ComponentDataItem> reInit(int i, int i2, CameraCapabilities cameraCapabilities, ComponentConfigSlowMotion componentConfigSlowMotion) {
        List<ComponentDataItem> list = this.mItems;
        if (list == null) {
            this.mItems = new ArrayList();
        } else {
            list.clear();
        }
        if (i == 172) {
            ArrayList supportedHfrSettings = getSupportedHfrSettings(cameraCapabilities);
            boolean contains = supportedHfrSettings.contains("1920x1080:240");
            boolean contains2 = supportedHfrSettings.contains(SIZE_FPS_1080_120);
            mCurrentFps = componentConfigSlowMotion.getComponentValue(i);
            String str = mCurrentFps;
            char c2 = 65535;
            int hashCode = str.hashCode();
            if (hashCode != -1150307548) {
                if (hashCode != -1150306525) {
                    if (hashCode == -1150299736 && str.equals(ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_960)) {
                        c2 = 0;
                    }
                } else if (str.equals(ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_240)) {
                    c2 = 1;
                }
            } else if (str.equals(ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_120)) {
                c2 = 2;
            }
            String str2 = "6";
            String str3 = "5";
            if (c2 != 0) {
                if (c2 != 1) {
                    if (c2 == 2) {
                        if (contains2) {
                            this.mItems.add(new ComponentDataItem((int) R.drawable.ic_config_slow_1080p_120fps, (int) R.drawable.ic_config_slow_1080p_120fps, (int) R.string.pref_new_slow_motion_video_quality_entry_1080p, str2));
                            this.mItems.add(new ComponentDataItem((int) R.drawable.ic_config_slow_720p_120fps, (int) R.drawable.ic_config_slow_720p_120fps, (int) R.string.pref_new_slow_motion_video_quality_entry_720p, str3));
                        } else {
                            this.mItems.add(new ComponentDataItem((int) R.drawable.ic_config_slow_720p_120fps_disable, (int) R.drawable.ic_config_slow_720p_120fps_disable, (int) R.string.pref_new_slow_motion_video_quality_entry_720p, str3));
                            if (DataRepository.dataItemFeature().sc()) {
                                setComponentValue(i, str3);
                            }
                        }
                    }
                } else if (contains) {
                    this.mItems.add(new ComponentDataItem((int) R.drawable.ic_config_slow_1080p_240fps, (int) R.drawable.ic_config_slow_1080p_240fps, (int) R.string.pref_new_slow_motion_video_quality_entry_1080p, str2));
                    this.mItems.add(new ComponentDataItem((int) R.drawable.ic_config_slow_720p_240fps, (int) R.drawable.ic_config_slow_720p_240fps, (int) R.string.pref_new_slow_motion_video_quality_entry_720p, str3));
                } else {
                    this.mItems.add(new ComponentDataItem((int) R.drawable.ic_config_slow_720p_240fps_disable, (int) R.drawable.ic_config_slow_720p_240fps_disable, (int) R.string.pref_new_slow_motion_video_quality_entry_720p, str3));
                }
            } else if (contains) {
                this.mItems.add(new ComponentDataItem((int) R.drawable.ic_config_slow_1080p_960fps, (int) R.drawable.ic_config_slow_1080p_960fps, (int) R.string.pref_new_slow_motion_video_quality_entry_1080p, str2));
                this.mItems.add(new ComponentDataItem((int) R.drawable.ic_config_slow_720p_960fps, (int) R.drawable.ic_config_slow_720p_960fps, (int) R.string.pref_new_slow_motion_video_quality_entry_720p, str3));
            } else {
                this.mItems.add(new ComponentDataItem((int) R.drawable.ic_config_slow_720p_960fps_disable, (int) R.drawable.ic_config_slow_720p_960fps_disable, (int) R.string.pref_new_slow_motion_video_quality_entry_720p, str3));
            }
        }
        return this.mItems;
    }
}
