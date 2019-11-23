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
    public static final String RATIO_FULL_20X9 = "20x9";
    public final float mCurrentScreenRatio = (((float) Util.sWindowHeight) / ((float) Util.sWindowWidth));
    @CameraRatio
    private String mDefaultValue = RATIO_4X3;
    private String mForceValue;
    private ArrayList<String> sEntryValues = new ArrayList<>();
    private boolean sSupport18_7_5x9;
    private boolean sSupport18x9;
    private boolean sSupport195x9;
    private boolean sSupport19x9;
    private boolean sSupport20x9;

    public @interface CameraRatio {
    }

    public ComponentConfigRatio(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    public String getComponentValue(int i) {
        String componentValue = (this.mForceValue == null || i == 165) ? super.getComponentValue(i) : this.mForceValue;
        if (165 == i && componentValue == RATIO_1X1) {
            return RATIO_1X1;
        }
        if (CameraSettings.isMiMovieOpen(i)) {
            return RATIO_16X9;
        }
        for (ComponentDataItem next : getItems()) {
            if (next != null && TextUtils.equals(componentValue, next.mValue)) {
                return componentValue;
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
        if (b.iO()) {
            this.sEntryValues.add(RATIO_FULL_18X9);
        }
        if (DataRepository.dataItemFeature().fV()) {
            this.sEntryValues.add(RATIO_FULL_19X9);
        }
        if (DataRepository.dataItemFeature().fT()) {
            this.sEntryValues.add(RATIO_FULL_195X9);
        }
        if (DataRepository.dataItemFeature().fW()) {
            this.sEntryValues.add(RATIO_FULL_18_7_5X9);
        }
        if (DataRepository.dataItemFeature().fU()) {
            this.sEntryValues.add(RATIO_FULL_20X9);
        }
        String[] strArr = new String[this.sEntryValues.size()];
        this.sEntryValues.toArray(strArr);
        return strArr;
    }

    public List<ComponentDataItem> getItems() {
        if (this.mItems == null) {
            synchronized (this.mItemsLock) {
                if (this.mItems == null) {
                    reInit(DataRepository.dataItemGlobal().getCurrentMode(), DataRepository.dataItemGlobal().getCurrentCameraId(), (CameraCapabilities) null);
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
        char c = 65535;
        if (componentValue.hashCode() == 50858 && componentValue.equals(RATIO_1X1)) {
            c = 0;
        }
        return c != 0 ? 163 : 165;
    }

    public String getNextValue(int i) {
        String persistValue = getPersistValue(i);
        if (this.mItems != null) {
            int size = this.mItems.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (TextUtils.equals(((ComponentDataItem) this.mItems.get(i2)).mValue, persistValue)) {
                    return ((ComponentDataItem) this.mItems.get((i2 + 1) % size)).mValue;
                }
            }
        }
        return getDefaultValue(i);
    }

    public String getPictureSizeRatioString(int i) {
        return this.mForceValue != null ? this.mForceValue : getComponentValue(i);
    }

    public void initSensorRatio(List<CameraSize> list, int i, int i2) {
        CameraSize cameraSize = new CameraSize();
        int i3 = -1;
        int i4 = 0;
        for (int i5 = 0; i5 < list.size(); i5++) {
            cameraSize.parseSize(list.get(i5));
            if (i4 < cameraSize.area()) {
                i4 = cameraSize.area();
                i3 = i5;
            }
        }
        cameraSize.parseSize(list.get(i3));
        if (((double) Math.abs(cameraSize.getRatio() - 1.3333333f)) < 0.02d) {
            this.mDefaultValue = RATIO_4X3;
        } else {
            this.mDefaultValue = RATIO_16X9;
        }
        reInit(i, i2, (CameraCapabilities) null);
    }

    public boolean isSquareModule() {
        String componentValue = getComponentValue(165);
        return componentValue != null && componentValue.equals(RATIO_1X1);
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        if (b.iO()) {
            this.sSupport18x9 = true;
        } else {
            this.sSupport18x9 = false;
        }
        if (DataRepository.dataItemFeature().fT()) {
            this.sSupport195x9 = true;
        } else {
            this.sSupport195x9 = false;
        }
        if (DataRepository.dataItemFeature().fU()) {
            this.sSupport20x9 = true;
        } else {
            this.sSupport20x9 = false;
        }
        if (DataRepository.dataItemFeature().fV()) {
            this.sSupport19x9 = true;
        } else {
            this.sSupport19x9 = false;
        }
        if (DataRepository.dataItemFeature().fW()) {
            this.sSupport18_7_5x9 = true;
        } else {
            this.sSupport18_7_5x9 = false;
        }
        ArrayList arrayList = new ArrayList();
        this.mForceValue = null;
        switch (i) {
            case 163:
            case 173:
                break;
            case 165:
            case 175:
                this.mForceValue = RATIO_4X3;
                break;
            case 166:
                this.mForceValue = RATIO_16X9;
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_16_9, (int) R.drawable.ic_config_16_9, (int) R.string.pref_camera_picturesize_entry_9_16, RATIO_16X9));
                break;
            case 167:
                if (CameraSettings.isUltraPixelOn()) {
                    this.mForceValue = RATIO_4X3;
                }
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_4_3, (int) R.drawable.ic_config_4_3, (int) R.string.pref_camera_picturesize_entry_3_4, RATIO_4X3));
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_16_9, (int) R.drawable.ic_config_16_9, (int) R.string.pref_camera_picturesize_entry_9_16, RATIO_16X9));
                if (!this.sSupport18_7_5x9) {
                    if (!this.sSupport18x9) {
                        if (!this.sSupport195x9) {
                            if (!this.sSupport19x9) {
                                if (this.sSupport20x9) {
                                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_20X9));
                                    break;
                                }
                            } else {
                                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_19X9));
                                break;
                            }
                        } else {
                            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_195X9));
                            break;
                        }
                    } else {
                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_18X9));
                        break;
                    }
                } else if (DataRepository.dataItemGlobal().getDisplayMode() != 2) {
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_18_7_5X9));
                    break;
                } else {
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_195X9));
                    break;
                }
                break;
            case 171:
                if (i2 == 0 && DataRepository.dataItemFeature().gv()) {
                    this.mForceValue = RATIO_4X3;
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_4_3, (int) R.drawable.ic_config_4_3, (int) R.string.pref_camera_picturesize_entry_3_4, RATIO_4X3));
                    break;
                } else {
                    if (DataRepository.dataItemGlobal().getDisplayMode() == 1) {
                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_4_3, (int) R.drawable.ic_config_4_3, (int) R.string.pref_camera_picturesize_entry_3_4, RATIO_4X3));
                    }
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_16_9, (int) R.drawable.ic_config_16_9, (int) R.string.pref_camera_picturesize_entry_9_16, RATIO_16X9));
                    if (!this.sSupport18_7_5x9) {
                        if (!this.sSupport18x9) {
                            if (!this.sSupport195x9) {
                                if (!this.sSupport19x9) {
                                    if (this.sSupport20x9) {
                                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_20X9));
                                        break;
                                    }
                                } else {
                                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_19X9));
                                    break;
                                }
                            } else {
                                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_195X9));
                                break;
                            }
                        } else {
                            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_18X9));
                            break;
                        }
                    } else if (DataRepository.dataItemGlobal().getDisplayMode() != 2) {
                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_18_7_5X9));
                        break;
                    } else {
                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_195X9));
                        break;
                    }
                }
                break;
            case 176:
                this.mForceValue = RATIO_4X3;
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_4_3, (int) R.drawable.ic_config_4_3, (int) R.string.pref_camera_picturesize_entry_3_4, RATIO_4X3));
                break;
            case 177:
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_4_3, (int) R.drawable.ic_config_4_3, (int) R.string.pref_camera_picturesize_entry_3_4, RATIO_4X3));
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_16_9, (int) R.drawable.ic_config_16_9, (int) R.string.pref_camera_picturesize_entry_9_16, RATIO_16X9));
                if (!this.sSupport18x9) {
                    if (!this.sSupport195x9) {
                        if (!this.sSupport19x9) {
                            if (this.sSupport20x9) {
                                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_20X9));
                                break;
                            }
                        } else {
                            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_19X9));
                            break;
                        }
                    } else {
                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_195X9));
                        break;
                    }
                } else {
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_18X9));
                    break;
                }
                break;
        }
        if (i2 == 0) {
            if (CameraSettings.isUltraPixelOn()) {
                this.mForceValue = RATIO_4X3;
            }
        } else if (CameraSettings.isUltraPixelOn()) {
            this.mForceValue = RATIO_4X3;
        }
        if (DataRepository.dataItemGlobal().getDisplayMode() == 1) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_4_3, (int) R.drawable.ic_config_4_3, (int) R.string.pref_camera_picturesize_entry_3_4, RATIO_4X3));
        }
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_16_9, (int) R.drawable.ic_config_16_9, (int) R.string.pref_camera_picturesize_entry_9_16, RATIO_16X9));
        if (this.sSupport18_7_5x9) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_18_7_5X9));
        } else if (this.sSupport18x9) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_18X9));
        } else if (this.sSupport195x9) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_195X9));
        } else if (this.sSupport19x9) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_19X9));
        } else if (this.sSupport20x9) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_20X9));
        }
        if ((i == 165 || i == 163) && DataRepository.dataItemGlobal().getDisplayMode() == 1) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_1_1, (int) R.drawable.ic_config_1_1, (int) R.string.pref_camera_picturesize_entry_1_1, RATIO_1X1));
        }
        this.mItems = Collections.unmodifiableList(arrayList);
    }

    public void setComponentValue(int i, String str) {
        if (i == 165 && str != RATIO_1X1) {
            i = 163;
        }
        if (str == RATIO_1X1) {
            super.setComponentValue(165, str);
            return;
        }
        super.setComponentValue(165, (String) null);
        super.setComponentValue(i, str);
    }

    public boolean supportRatioSwitch() {
        return this.mItems != null && this.mItems.size() > 1;
    }
}
