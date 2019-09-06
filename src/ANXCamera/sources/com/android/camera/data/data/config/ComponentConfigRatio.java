package com.android.camera.data.data.config;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera2.CameraCapabilities;
import com.mi.config.b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComponentConfigRatio extends ComponentData {
    public static final String RATIO_16X9 = "16x9";
    public static final String RATIO_1X1 = "1x1";
    public static final String RATIO_4X3 = "4x3";
    public static final String RATIO_FULL_18X9 = "18x9";
    public static final String RATIO_FULL_18_7_5X9 = "18.75x9";
    public static final String RATIO_FULL_195X9 = "19.5x9";
    public static final String RATIO_FULL_19X9 = "19x9";
    public final float mCurrentScreenRatio = (((float) Util.sWindowHeight) / ((float) Util.sWindowWidth));
    @CameraRatio
    private String mDefaultValue = RATIO_4X3;
    private String mForceValue;
    private ArrayList<String> sEntryValues = new ArrayList<>();
    private boolean sSupport18_7_5x9;
    private boolean sSupport18x9;
    private boolean sSupport195x9;
    private boolean sSupport19x9;

    public @interface CameraRatio {
    }

    public ComponentConfigRatio(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    public String getComponentValue(int i) {
        String str = this.mForceValue;
        if (str == null || i == 165) {
            str = super.getComponentValue(i);
        }
        if (165 == i) {
            String str2 = RATIO_1X1;
            if (str == str2) {
                return str2;
            }
        }
        for (ComponentDataItem componentDataItem : getItems()) {
            if (TextUtils.equals(str, componentDataItem.mValue)) {
                return str;
            }
        }
        return this.mDefaultValue;
    }

    @NonNull
    public String getDefaultValue(int i) {
        if (DataRepository.dataItemGlobal().getDisplayMode() == 2) {
            this.mDefaultValue = RATIO_16X9;
        }
        return this.mDefaultValue;
    }

    public int getDisplayTitleString() {
        return R.string.pref_camera_picturesize_title_simple_mode;
    }

    public String[] getFullSupportRatioValues() {
        this.sEntryValues.clear();
        this.sEntryValues.add(RATIO_4X3);
        this.sEntryValues.add(RATIO_16X9);
        if (b.Vh()) {
            this.sEntryValues.add(RATIO_FULL_18X9);
        }
        if (DataRepository.dataItemFeature().Hc()) {
            this.sEntryValues.add(RATIO_FULL_19X9);
        }
        if (DataRepository.dataItemFeature().Gc()) {
            this.sEntryValues.add(RATIO_FULL_195X9);
        }
        if (DataRepository.dataItemFeature().Fc()) {
            this.sEntryValues.add(RATIO_FULL_18_7_5X9);
        }
        String[] strArr = new String[this.sEntryValues.size()];
        this.sEntryValues.toArray(strArr);
        return strArr;
    }

    public List<ComponentDataItem> getItems() {
        if (this.mItems == null) {
            synchronized (this.mItemsLock) {
                if (this.mItems == null) {
                    reInit(DataRepository.dataItemGlobal().getCurrentMode(), DataRepository.dataItemGlobal().getCurrentCameraId(), null);
                }
            }
        }
        return this.mItems;
    }

    public String getKey(int i) {
        return i != 165 ? "pref_camera_picturesize_key" : "is_square";
    }

    public int getMappingModeByRatio(int i) {
        if (i != 163 && i != 165) {
            return i;
        }
        if (isSquareModule()) {
            return 165;
        }
        String componentValue = getComponentValue(i);
        char c2 = 65535;
        if (componentValue.hashCode() == 50858 && componentValue.equals(RATIO_1X1)) {
            c2 = 0;
        }
        return c2 != 0 ? 163 : 165;
    }

    public String getNextValue(int i) {
        String persistValue = getPersistValue(i);
        List<ComponentDataItem> list = this.mItems;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (TextUtils.equals(((ComponentDataItem) this.mItems.get(i2)).mValue, persistValue)) {
                    return ((ComponentDataItem) this.mItems.get((i2 + 1) % size)).mValue;
                }
            }
        }
        return getDefaultValue(i);
    }

    public String getPictureSizeRatioString(int i) {
        String str = this.mForceValue;
        return str != null ? str : getComponentValue(i);
    }

    public void initSensorRatio(List<CameraSize> list, int i, int i2) {
        CameraSize cameraSize = new CameraSize();
        int i3 = -1;
        int i4 = 0;
        for (int i5 = 0; i5 < list.size(); i5++) {
            cameraSize.parseSize((CameraSize) list.get(i5));
            if (i4 < cameraSize.area()) {
                i4 = cameraSize.area();
                i3 = i5;
            }
        }
        cameraSize.parseSize((CameraSize) list.get(i3));
        if (((double) Math.abs(cameraSize.getRatio() - 1.3333333f)) < 0.02d) {
            this.mDefaultValue = RATIO_4X3;
        } else {
            this.mDefaultValue = RATIO_16X9;
        }
        reInit(i, i2, null);
    }

    public boolean isSquareModule() {
        String componentValue = getComponentValue(165);
        return componentValue != null && componentValue.equals(RATIO_1X1);
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        int i3 = i;
        if (b.Vh()) {
            this.sSupport18x9 = true;
        } else {
            this.sSupport18x9 = false;
        }
        if (DataRepository.dataItemFeature().Gc()) {
            this.sSupport195x9 = true;
        } else {
            this.sSupport195x9 = false;
        }
        if (DataRepository.dataItemFeature().Hc()) {
            this.sSupport19x9 = true;
        } else {
            this.sSupport19x9 = false;
        }
        if (DataRepository.dataItemFeature().Fc()) {
            this.sSupport18_7_5x9 = true;
        } else {
            this.sSupport18_7_5x9 = false;
        }
        ArrayList arrayList = new ArrayList();
        this.mForceValue = null;
        String str = RATIO_FULL_18_7_5X9;
        String str2 = RATIO_FULL_19X9;
        String str3 = RATIO_FULL_18X9;
        String str4 = RATIO_16X9;
        String str5 = RATIO_FULL_195X9;
        String str6 = RATIO_4X3;
        switch (i3) {
            case 163:
            case 173:
                break;
            case 165:
            case 175:
                this.mForceValue = str6;
                break;
            case 166:
                this.mForceValue = str4;
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_16_9, (int) R.drawable.ic_config_16_9, (int) R.string.pref_camera_picturesize_entry_9_16, str4));
                break;
            case 167:
                if (CameraSettings.isUltraPixelOn()) {
                    this.mForceValue = str6;
                }
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_4_3, (int) R.drawable.ic_config_4_3, (int) R.string.pref_camera_picturesize_entry_3_4, str6));
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_16_9, (int) R.drawable.ic_config_16_9, (int) R.string.pref_camera_picturesize_entry_9_16, str4));
                if (!this.sSupport18_7_5x9) {
                    if (!this.sSupport18x9) {
                        if (!this.sSupport195x9) {
                            if (this.sSupport19x9) {
                                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, str2));
                                break;
                            }
                        } else {
                            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, str5));
                            break;
                        }
                    } else {
                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, str3));
                        break;
                    }
                } else if (DataRepository.dataItemGlobal().getDisplayMode() != 2) {
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, str));
                    break;
                } else {
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, str5));
                    break;
                }
                break;
            case 171:
                if (i2 == 0 && DataRepository.dataItemFeature().jb()) {
                    this.mForceValue = str6;
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_4_3, (int) R.drawable.ic_config_4_3, (int) R.string.pref_camera_picturesize_entry_3_4, str6));
                    break;
                } else {
                    if (DataRepository.dataItemGlobal().getDisplayMode() == 1) {
                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_4_3, (int) R.drawable.ic_config_4_3, (int) R.string.pref_camera_picturesize_entry_3_4, str6));
                    }
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_16_9, (int) R.drawable.ic_config_16_9, (int) R.string.pref_camera_picturesize_entry_9_16, str4));
                    if (!this.sSupport18_7_5x9) {
                        if (!this.sSupport18x9) {
                            if (!this.sSupport195x9) {
                                if (this.sSupport19x9) {
                                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, str2));
                                    break;
                                }
                            } else {
                                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, str5));
                                break;
                            }
                        } else {
                            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, str3));
                            break;
                        }
                    } else if (DataRepository.dataItemGlobal().getDisplayMode() != 2) {
                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, str));
                        break;
                    } else {
                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, str5));
                        break;
                    }
                }
                break;
            case 176:
                this.mForceValue = str6;
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_4_3, (int) R.drawable.ic_config_4_3, (int) R.string.pref_camera_picturesize_entry_3_4, str6));
                break;
            case 177:
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_4_3, (int) R.drawable.ic_config_4_3, (int) R.string.pref_camera_picturesize_entry_3_4, str6));
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_16_9, (int) R.drawable.ic_config_16_9, (int) R.string.pref_camera_picturesize_entry_9_16, str4));
                if (!this.sSupport18x9) {
                    if (!this.sSupport195x9) {
                        if (this.sSupport19x9) {
                            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, str2));
                            break;
                        }
                    } else {
                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, str5));
                        break;
                    }
                } else {
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, str3));
                    break;
                }
                break;
        }
        if (i2 == 0) {
            if (CameraSettings.isUltraPixelOn()) {
                this.mForceValue = str6;
            }
        } else if (CameraSettings.isUltraPixelOn()) {
            this.mForceValue = str6;
        }
        if (DataRepository.dataItemGlobal().getDisplayMode() == 1) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_4_3, (int) R.drawable.ic_config_4_3, (int) R.string.pref_camera_picturesize_entry_3_4, str6));
        }
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_16_9, (int) R.drawable.ic_config_16_9, (int) R.string.pref_camera_picturesize_entry_9_16, str4));
        if (this.sSupport18_7_5x9) {
            if (DataRepository.dataItemGlobal().getDisplayMode() == 2) {
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, str5));
            } else {
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, str));
            }
        } else if (this.sSupport18x9) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, str3));
        } else if (this.sSupport195x9) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, str5));
        } else if (this.sSupport19x9) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, str2));
        }
        if ((i3 == 165 || i3 == 163) && DataRepository.dataItemGlobal().getDisplayMode() == 1) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_1_1, (int) R.drawable.ic_config_1_1, (int) R.string.pref_camera_picturesize_entry_1_1, RATIO_1X1));
        }
        this.mItems = Collections.unmodifiableList(arrayList);
    }

    public void setComponentValue(int i, String str) {
        String str2 = RATIO_1X1;
        if (i == 165 && str != str2) {
            i = 163;
        }
        if (str == str2) {
            super.setComponentValue(165, str);
            return;
        }
        super.setComponentValue(165, null);
        super.setComponentValue(i, str);
    }

    public boolean supportRatioSwitch() {
        List<ComponentDataItem> list = this.mItems;
        return list != null && list.size() > 1;
    }
}
