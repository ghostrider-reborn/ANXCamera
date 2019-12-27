package com.android.camera.data.data.runing;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.effect.FilterInfo;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera2.CameraCapabilities;
import com.mi.config.b;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ComponentRunningShine extends ComponentData {
    public static final int ENTRY_NONE = -1;
    public static final int ENTRY_POPUP_BEAUTY = 5;
    public static final int ENTRY_POPUP_SHINE = 4;
    public static final int ENTRY_TOP_BEAUTY = 2;
    public static final int ENTRY_TOP_FILTER = 3;
    public static final int ENTRY_TOP_SHINE = 1;
    public static final String SHINE_BEAUTY_LEVEL_SMOOTH = "2";
    public static final String SHINE_BEAUTY_LEVEL_SWITCH = "1";
    public static final String SHINE_EYE_LIGHT = "9";
    public static final String SHINE_FIGURE = "6";
    public static final String SHINE_FILTER = "7";
    public static final String SHINE_LIGHTING = "8";
    public static final String SHINE_LIVE_BEAUTY = "11";
    public static final String SHINE_LIVE_FILTER = "10";
    public static final String SHINE_LIVE_SPEED = "13";
    public static final String SHINE_LIVE_STICKER = "12";
    public static final String SHINE_MAKEUP = "5";
    public static final String SHINE_MODEL_ADVANCE = "3";
    public static final String SHINE_MODEL_REMODELING = "4";
    private BeautyValues mBeautyValues;
    private int mBeautyVersion;
    private boolean mCurrentStatus;
    @ShineType
    private String mCurrentType;
    @ShineType
    private String mDefaultType;
    private boolean mIsClosed;
    @ShineEntry
    private int mShineEntry;
    private boolean mSupportBeautyBody;
    private boolean mSupportBeautyLevel;
    private boolean mSupportBeautyMakeUp;
    private boolean mSupportBeautyModel;
    private boolean mSupportSmoothLevel;
    private TypeElementsBeauty mTypeElementsBeauty = new TypeElementsBeauty(this);

    public @interface ShineEntry {
    }

    public @interface ShineType {
    }

    public ComponentRunningShine(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    private ComponentDataItem generateBeautyLevelItem(boolean z) {
        return b.Pi() ? new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_3d_beauty, "1") : new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_beauty, "1");
    }

    private ComponentDataItem generateFigureItem() {
        return new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, isSmoothDependBeautyVersion() ? R.string.beauty_fragment_tab_name_3d_beauty : R.string.beauty_body, "6");
    }

    private ComponentDataItem generateFilterItem() {
        return new ComponentDataItem((int) R.drawable.ic_new_effect_button_normal, (int) R.drawable.ic_new_effect_button_selected, (int) R.string.pref_camera_coloreffect_title, "7");
    }

    private ComponentDataItem generateMakeupItem() {
        return new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_3d_makeup, "5");
    }

    private ComponentDataItem generateModelItem() {
        if (!b.Pi()) {
            return new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_makeup, "3");
        }
        return new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, isSmoothDependBeautyVersion() ? R.string.beauty_fragment_tab_name_3d_beauty : R.string.beauty_fragment_tab_name_3d_remodeling, "4");
    }

    private ComponentDataItem generateSmoothLevelItem(boolean z) {
        return new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_3d_beauty, "2");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00a2 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00b4  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00d2  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x001e A[SYNTHETIC] */
    public boolean determineStatus(int i) {
        char c2;
        if (this.mBeautyValues == null) {
            this.mBeautyValues = new BeautyValues();
        }
        boolean z = false;
        if (isClosed()) {
            this.mCurrentStatus = false;
        } else {
            Boolean bool = null;
            Boolean bool2 = null;
            for (ComponentDataItem next : this.mItems) {
                if (next != null) {
                    String str = next.mValue;
                    int hashCode = str.hashCode();
                    if (hashCode == 1567) {
                        if (str.equals("10")) {
                            c2 = 8;
                            switch (c2) {
                                case 0:
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                    break;
                                case 5:
                                    break;
                                case 6:
                                    break;
                                case 7:
                                    break;
                                case 8:
                                    break;
                            }
                        }
                    } else if (hashCode == 1568) {
                        if (str.equals(SHINE_LIVE_BEAUTY)) {
                            c2 = 6;
                            switch (c2) {
                                case 0:
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                    if (bool != null) {
                                        break;
                                    } else {
                                        bool = Boolean.valueOf(CameraSettings.isFaceBeautyOn(i, this.mBeautyValues));
                                        break;
                                    }
                                case 5:
                                    if (bool != null) {
                                        break;
                                    } else {
                                        bool = Boolean.valueOf(CameraSettings.isFaceBeautyOn(i, this.mBeautyValues));
                                        break;
                                    }
                                case 6:
                                    if (bool != null) {
                                        break;
                                    } else {
                                        bool = Boolean.valueOf(CameraSettings.isLiveBeautyOpen());
                                        break;
                                    }
                                case 7:
                                    if (bool2 != null) {
                                        break;
                                    } else {
                                        int parseInt = Integer.parseInt(DataRepository.dataItemRunning().getComponentConfigFilter().getComponentValue(i));
                                        if (parseInt != FilterInfo.FILTER_ID_NONE && parseInt > 0) {
                                            bool2 = true;
                                            break;
                                        }
                                    }
                                case 8:
                                    if (bool2 == null && DataRepository.dataItemLive().getLiveFilter() != 0) {
                                        bool2 = true;
                                        break;
                                    }
                            }
                        }
                    } else {
                        switch (hashCode) {
                            case 49:
                                if (str.equals("1")) {
                                    c2 = 0;
                                    break;
                                }
                            case 50:
                                if (str.equals("2")) {
                                    c2 = 5;
                                    break;
                                }
                            case 51:
                                if (str.equals("3")) {
                                    c2 = 1;
                                    break;
                                }
                            case 52:
                                if (str.equals("4")) {
                                    c2 = 2;
                                    break;
                                }
                            case 53:
                                if (str.equals("5")) {
                                    c2 = 3;
                                    break;
                                }
                            case 54:
                                if (str.equals("6")) {
                                    c2 = 4;
                                    break;
                                }
                            case 55:
                                if (str.equals("7")) {
                                    c2 = 7;
                                    break;
                                }
                        }
                    }
                    c2 = 65535;
                    switch (c2) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
                            break;
                        case 7:
                            break;
                        case 8:
                            break;
                    }
                }
            }
            if ((bool != null && bool.booleanValue()) || (bool2 != null && bool2.booleanValue())) {
                z = true;
            }
            this.mCurrentStatus = z;
        }
        return this.mCurrentStatus;
    }

    public int getBeautyVersion() {
        return this.mBeautyVersion;
    }

    @DrawableRes
    public int getBottomEntryRes(int i) {
        this.mCurrentStatus = determineStatus(i);
        int i2 = this.mShineEntry;
        return i2 != 4 ? i2 != 5 ? R.drawable.ic_shine_off : this.mCurrentStatus ? R.drawable.ic_beauty_on : R.drawable.ic_beauty_off : this.mCurrentStatus ? R.drawable.ic_beauty_tips_on : R.drawable.ic_beauty_tips_normal;
    }

    public boolean getCurrentStatus() {
        return this.mCurrentStatus;
    }

    @ShineType
    public String getCurrentType() {
        return this.mCurrentType;
    }

    @NonNull
    public String getDefaultValue(int i) {
        return this.mDefaultType;
    }

    public int getDisplayTitleString() {
        return 0;
    }

    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    public String getKey(int i) {
        return null;
    }

    @DrawableRes
    public int getTopConfigEntryRes(int i) {
        this.mCurrentStatus = determineStatus(i);
        int i2 = this.mShineEntry;
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? R.drawable.ic_shine_off : this.mCurrentStatus ? R.drawable.ic_new_effect_button_selected : R.drawable.ic_new_effect_button_normal : this.mCurrentStatus ? R.drawable.ic_beauty_on : R.drawable.ic_beauty_off : this.mCurrentStatus ? R.drawable.ic_shine_on : R.drawable.ic_shine_off;
    }

    public int getTopConfigItem() {
        int i = this.mShineEntry;
        if (i == 1 || i == 2 || i == 3) {
            return 212;
        }
        throw new RuntimeException("unknown Shine");
    }

    public TypeElementsBeauty getTypeElementsBeauty() {
        return this.mTypeElementsBeauty;
    }

    public boolean isClosed() {
        return this.mIsClosed;
    }

    public boolean isLegacyBeautyVersion() {
        return this.mBeautyVersion == 1;
    }

    public boolean isSmoothDependBeautyVersion() {
        return this.mBeautyVersion == 3;
    }

    public void reInit() {
        List<ComponentDataItem> list = this.mItems;
        if (list == null) {
            this.mItems = new CopyOnWriteArrayList();
        } else {
            list.clear();
        }
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        reInit();
        this.mBeautyVersion = cameraCapabilities.getBeautyVersion();
        boolean z = true;
        if (this.mBeautyVersion < 0) {
            if (b.Pi()) {
                this.mBeautyVersion = 2;
            } else {
                this.mBeautyVersion = 1;
            }
        }
        this.mShineEntry = -1;
        this.mDefaultType = null;
        this.mSupportBeautyLevel = false;
        this.mSupportSmoothLevel = false;
        this.mSupportBeautyModel = false;
        this.mSupportBeautyMakeUp = false;
        this.mSupportBeautyBody = false;
        switch (i) {
            case 161:
                if (!cameraCapabilities.isSupportVideoBeauty()) {
                    this.mShineEntry = 3;
                    this.mItems.add(generateFilterItem());
                    break;
                } else {
                    this.mShineEntry = 4;
                    if (i2 == 0) {
                        this.mDefaultType = "7";
                        if (!isSmoothDependBeautyVersion()) {
                            this.mSupportBeautyLevel = true;
                            this.mItems.add(generateBeautyLevelItem(i2 == 1));
                            if (DataRepository.dataItemFeature().isSupportShortVideoBeautyBody()) {
                                this.mSupportBeautyBody = true;
                                this.mItems.add(generateFigureItem());
                            }
                        } else {
                            this.mSupportSmoothLevel = true;
                            if (DataRepository.dataItemFeature().isSupportShortVideoBeautyBody()) {
                                this.mSupportBeautyBody = true;
                                this.mItems.add(generateFigureItem());
                            } else {
                                List<ComponentDataItem> list = this.mItems;
                                if (i2 != 1) {
                                    z = false;
                                }
                                list.add(generateSmoothLevelItem(z));
                            }
                        }
                    } else if (!isSmoothDependBeautyVersion()) {
                        this.mSupportBeautyLevel = true;
                        List<ComponentDataItem> list2 = this.mItems;
                        if (i2 != 1) {
                            z = false;
                        }
                        list2.add(generateBeautyLevelItem(z));
                    } else {
                        this.mSupportSmoothLevel = true;
                        List<ComponentDataItem> list3 = this.mItems;
                        if (i2 != 1) {
                            z = false;
                        }
                        list3.add(generateSmoothLevelItem(z));
                    }
                    this.mItems.add(generateFilterItem());
                    break;
                }
            case 162:
            case 169:
                if (cameraCapabilities.isSupportVideoBeauty()) {
                    this.mShineEntry = 2;
                    if (isSmoothDependBeautyVersion()) {
                        this.mSupportSmoothLevel = true;
                        List<ComponentDataItem> list4 = this.mItems;
                        if (i2 != 1) {
                            z = false;
                        }
                        list4.add(generateSmoothLevelItem(z));
                        break;
                    } else {
                        this.mSupportBeautyLevel = true;
                        List<ComponentDataItem> list5 = this.mItems;
                        if (i2 != 1) {
                            z = false;
                        }
                        list5.add(generateBeautyLevelItem(z));
                        break;
                    }
                }
                break;
            case 163:
            case 165:
                if (!CameraSettings.isUltraPixelRearOn()) {
                    if (!isSmoothDependBeautyVersion()) {
                        this.mSupportBeautyLevel = true;
                        this.mItems.add(generateBeautyLevelItem(i2 == 1));
                    } else {
                        this.mSupportSmoothLevel = true;
                    }
                    if (i2 == 0) {
                        this.mShineEntry = 1;
                        this.mDefaultType = "7";
                        if (DataRepository.dataItemFeature().isSupportBeautyBody()) {
                            this.mSupportBeautyBody = true;
                            this.mItems.add(generateFigureItem());
                        } else if (isSmoothDependBeautyVersion()) {
                            this.mItems.add(generateSmoothLevelItem(false));
                        }
                    } else {
                        this.mShineEntry = 4;
                        if (!DataRepository.dataItemFeature().db()) {
                            this.mSupportBeautyModel = true;
                            this.mItems.add(generateModelItem());
                            if (DataRepository.dataItemFeature().Kc() && cameraCapabilities.isSupportBeautyMakeup()) {
                                this.mSupportBeautyMakeUp = true;
                                this.mItems.add(generateMakeupItem());
                            }
                        } else if (isSmoothDependBeautyVersion()) {
                            this.mItems.add(generateSmoothLevelItem(true));
                        }
                    }
                } else if (i2 == 0) {
                    this.mShineEntry = 3;
                } else {
                    this.mShineEntry = 4;
                }
                this.mItems.add(generateFilterItem());
                break;
            case 167:
            case 175:
                this.mShineEntry = 3;
                this.mItems.add(generateFilterItem());
                break;
            case 171:
                this.mShineEntry = 4;
                if (!isSmoothDependBeautyVersion()) {
                    this.mSupportBeautyLevel = true;
                    List<ComponentDataItem> list6 = this.mItems;
                    if (i2 != 1) {
                        z = false;
                    }
                    list6.add(generateBeautyLevelItem(z));
                } else {
                    this.mSupportSmoothLevel = true;
                    List<ComponentDataItem> list7 = this.mItems;
                    if (i2 != 1) {
                        z = false;
                    }
                    list7.add(generateSmoothLevelItem(z));
                }
                this.mItems.add(generateFilterItem());
                break;
            case 174:
                this.mShineEntry = 4;
                this.mDefaultType = "10";
                this.mItems.add(new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_3d_beauty, SHINE_LIVE_BEAUTY));
                this.mItems.add(new ComponentDataItem((int) R.drawable.ic_new_effect_button_normal, (int) R.drawable.ic_new_effect_button_selected, (int) R.string.pref_camera_coloreffect_title, "10"));
                break;
            case 176:
                this.mShineEntry = 4;
                if (isSmoothDependBeautyVersion()) {
                    this.mSupportSmoothLevel = true;
                    List<ComponentDataItem> list8 = this.mItems;
                    if (i2 != 1) {
                        z = false;
                    }
                    list8.add(generateSmoothLevelItem(z));
                    break;
                } else {
                    this.mSupportBeautyLevel = true;
                    List<ComponentDataItem> list9 = this.mItems;
                    if (i2 != 1) {
                        z = false;
                    }
                    list9.add(generateBeautyLevelItem(z));
                    break;
                }
            case 177:
                this.mSupportSmoothLevel = true;
                break;
        }
        if (this.mDefaultType == null && !this.mItems.isEmpty()) {
            this.mDefaultType = this.mItems.get(0).mValue;
        }
        this.mCurrentType = this.mDefaultType;
    }

    public void setClosed(boolean z) {
        this.mIsClosed = z;
    }

    public void setCurrentType(@ShineType String str) {
        this.mCurrentType = str;
    }

    public boolean supportBeautyBody() {
        return this.mSupportBeautyBody;
    }

    public boolean supportBeautyLevel() {
        return this.mSupportBeautyLevel;
    }

    public boolean supportBeautyMakeUp() {
        return this.mSupportBeautyMakeUp;
    }

    public boolean supportBeautyModel() {
        return this.mSupportBeautyModel;
    }

    public boolean supportPopUpEntry() {
        int i = this.mShineEntry;
        return i == 4 || i == 5;
    }

    public boolean supportSmoothLevel() {
        return this.mSupportSmoothLevel;
    }

    public boolean supportTopConfigEntry() {
        int i = this.mShineEntry;
        return i == 1 || i == 2 || i == 3;
    }
}
