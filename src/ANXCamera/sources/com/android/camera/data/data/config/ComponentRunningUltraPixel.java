package com.android.camera.data.data.config;

import android.content.res.Resources;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.Size;
import android.util.SparseBooleanArray;
import com.android.camera.CameraAppImpl;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.List;

public class ComponentRunningUltraPixel extends ComponentData {
    private static final String TAG = ComponentRunningUltraPixel.class.getSimpleName();
    public static final String ULTRA_PIXEL_OFF = "OFF";
    public static final String ULTRA_PIXEL_ON_FRONT_32M = "FRONT_0x1";
    public static final String ULTRA_PIXEL_ON_REAR_108M = "REAR_0x3";
    public static final String ULTRA_PIXEL_ON_REAR_48M = "REAR_0x2";
    public static final String ULTRA_PIXEL_ON_REAR_64M = "REAR_0x1";
    private String mCloseTipString = null;
    private int mCurrentMode;
    private SparseBooleanArray mIsClosed;
    @DrawableRes
    private int mMenuDrawable = -1;
    private String mMenuString = null;
    private String mOpenTipString = null;

    public @interface UltraPixelSupport {
    }

    public ComponentRunningUltraPixel(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    private void add108M() {
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_ultra_pixel_photography_108mp, (int) R.drawable.ic_ultra_pixel_photography_108mp_highlight, resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_108mp)}), "OFF"));
        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_ultra_pixel_photography_108mp, (int) R.drawable.ic_ultra_pixel_photography_108mp_highlight, resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_108mp)}), ULTRA_PIXEL_ON_REAR_108M));
        initUltraPixelResource(ULTRA_PIXEL_ON_REAR_108M);
    }

    private void add48M() {
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_menu_ultra_pixel_photography_48mp, (int) R.drawable.ic_menu_ultra_pixel_photography_48mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_48mp)}), "OFF"));
        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_menu_ultra_pixel_photography_48mp, (int) R.drawable.ic_menu_ultra_pixel_photography_48mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_48mp)}), ULTRA_PIXEL_ON_REAR_48M));
        initUltraPixelResource(ULTRA_PIXEL_ON_REAR_48M);
    }

    private void add64M() {
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_menu_ultra_pixel_photography_64mp, (int) R.drawable.ic_menu_ultra_pixel_photography_64mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_64mp)}), "OFF"));
        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_menu_ultra_pixel_photography_64mp, (int) R.drawable.ic_menu_ultra_pixel_photography_64mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_64mp)}), ULTRA_PIXEL_ON_REAR_64M));
        initUltraPixelResource(ULTRA_PIXEL_ON_REAR_64M);
    }

    public static String getNoSupportZoomTip() {
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        switch (DataRepository.dataItemFeature().gV()) {
            case 1:
                return resources.getString(R.string.ultra_pixel_zoom_no_support_tip, new Object[]{resources.getString(R.string.ultra_pixel_48mp)});
            case 2:
                return resources.getString(R.string.ultra_pixel_zoom_no_support_tip, new Object[]{resources.getString(R.string.ultra_pixel_64mp)});
            case 3:
                return resources.getString(R.string.ultra_pixel_zoom_no_support_tip, new Object[]{resources.getString(R.string.ultra_pixel_108mp)});
            default:
                return resources.getString(R.string.ultra_pixel_zoom_no_support_tip, new Object[]{resources.getString(R.string.ultra_pixel_48mp)});
        }
    }

    public static String[] getUltraPixelSwitchTipsString() {
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        switch (DataRepository.dataItemFeature().gV()) {
            case 1:
                return new String[]{resources.getString(R.string.accessibility_ultra_pixel_photography_off, new Object[]{resources.getString(R.string.ultra_pixel_48mp)}), resources.getString(R.string.accessibility_ultra_pixel_photography_on, new Object[]{resources.getString(R.string.ultra_pixel_48mp)})};
            case 2:
                return new String[]{resources.getString(R.string.accessibility_ultra_pixel_photography_off, new Object[]{resources.getString(R.string.ultra_pixel_64mp)}), resources.getString(R.string.accessibility_ultra_pixel_photography_on, new Object[]{resources.getString(R.string.ultra_pixel_64mp)})};
            case 3:
                return new String[]{resources.getString(R.string.accessibility_ultra_pixel_photography_off, new Object[]{resources.getString(R.string.ultra_pixel_108mp)}), resources.getString(R.string.accessibility_ultra_pixel_photography_on, new Object[]{resources.getString(R.string.ultra_pixel_108mp)})};
            default:
                return new String[]{resources.getString(R.string.accessibility_ultra_pixel_photography_off, new Object[]{resources.getString(R.string.ultra_pixel_48mp)}), resources.getString(R.string.accessibility_ultra_pixel_photography_on, new Object[]{resources.getString(R.string.ultra_pixel_48mp)})};
        }
    }

    public static int[] getUltraPixelTopMenuResources() {
        switch (DataRepository.dataItemFeature().gV()) {
            case 1:
                return new int[]{R.drawable.ic_ultra_pixel_photography_48mp, R.drawable.ic_ultra_pixel_photography_48mp_highlight};
            case 2:
                return new int[]{R.drawable.ic_ultra_pixel_photography_64mp, R.drawable.ic_ultra_pixel_photography_64mp_highlight};
            case 3:
                return new int[]{R.drawable.ic_ultra_pixel_photography_108mp, R.drawable.ic_ultra_pixel_photography_108mp_highlight};
            default:
                return new int[]{R.drawable.ic_ultra_pixel_photography_48mp, R.drawable.ic_ultra_pixel_photography_48mp_highlight};
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0098  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00cb  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00fe  */
    private void initUltraPixelResource(@UltraPixelSupport String str) {
        char c;
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        int hashCode = str.hashCode();
        if (hashCode != -1379357773) {
            switch (hashCode) {
                case -70725170:
                    if (str.equals(ULTRA_PIXEL_ON_REAR_64M)) {
                        c = 2;
                        break;
                    }
                case -70725169:
                    if (str.equals(ULTRA_PIXEL_ON_REAR_48M)) {
                        c = 1;
                        break;
                    }
                case -70725168:
                    if (str.equals(ULTRA_PIXEL_ON_REAR_108M)) {
                        c = 3;
                        break;
                    }
            }
        } else if (str.equals(ULTRA_PIXEL_ON_FRONT_32M)) {
            c = 0;
            switch (c) {
                case 0:
                    this.mMenuDrawable = R.drawable.ic_menu_ultra_pixel_photography_32mp;
                    this.mOpenTipString = resources.getString(R.string.ultra_pixel_photography_open_tip, new Object[]{resources.getString(R.string.ultra_pixel_32mp)});
                    this.mCloseTipString = resources.getString(R.string.ultra_pixel_photography_close_tip, new Object[]{resources.getString(R.string.ultra_pixel_32mp)});
                    this.mMenuString = resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_32mp)});
                    return;
                case 1:
                    this.mMenuDrawable = R.drawable.ic_menu_ultra_pixel_photography_48mp;
                    this.mOpenTipString = resources.getString(R.string.ultra_pixel_photography_open_tip, new Object[]{resources.getString(R.string.ultra_pixel_48mp)});
                    this.mCloseTipString = resources.getString(R.string.ultra_pixel_photography_close_tip, new Object[]{resources.getString(R.string.ultra_pixel_48mp)});
                    this.mMenuString = resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_48mp)});
                    return;
                case 2:
                    this.mMenuDrawable = R.drawable.ic_menu_ultra_pixel_photography_64mp;
                    this.mOpenTipString = resources.getString(R.string.ultra_pixel_photography_open_tip, new Object[]{resources.getString(R.string.ultra_pixel_64mp)});
                    this.mCloseTipString = resources.getString(R.string.ultra_pixel_photography_close_tip, new Object[]{resources.getString(R.string.ultra_pixel_64mp)});
                    this.mMenuString = resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_64mp)});
                    return;
                case 3:
                    this.mMenuDrawable = R.drawable.ic_ultra_pixel_photography_108mp;
                    this.mOpenTipString = resources.getString(R.string.ultra_pixel_photography_open_tip, new Object[]{resources.getString(R.string.ultra_pixel_108mp)});
                    this.mCloseTipString = resources.getString(R.string.ultra_pixel_photography_close_tip, new Object[]{resources.getString(R.string.ultra_pixel_108mp)});
                    this.mMenuString = resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_108mp)});
                    return;
                default:
                    Log.d(TAG, "Unknown ultra pixel size: " + str);
                    return;
            }
        }
        c = 65535;
        switch (c) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    @UltraPixelSupport
    public String getCurrentSupportUltraPixel() {
        return ((ComponentDataItem) this.mItems.get(1)).mValue;
    }

    @NonNull
    public String getDefaultValue(int i) {
        return "OFF";
    }

    public int getDisplayTitleString() {
        return 0;
    }

    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    public String getKey(int i) {
        return "pref_ultra_pixel";
    }

    @DrawableRes
    public int getMenuDrawable() {
        return this.mMenuDrawable;
    }

    public String getMenuString() {
        return this.mMenuString;
    }

    public String getUltraPixelCloseTip() {
        return this.mCloseTipString;
    }

    public String getUltraPixelOpenTip() {
        return this.mOpenTipString;
    }

    public boolean isClosed() {
        if (this.mIsClosed == null) {
            return false;
        }
        if (this.mCurrentMode == 165) {
            this.mCurrentMode = 163;
        }
        return this.mIsClosed.get(this.mCurrentMode);
    }

    public boolean isFront32MPSwitchOn() {
        return ULTRA_PIXEL_ON_FRONT_32M.equals(getComponentValue(160));
    }

    public boolean isRear108MPSwitchOn() {
        if (isClosed()) {
            return false;
        }
        return ULTRA_PIXEL_ON_REAR_108M.equals(getComponentValue(160));
    }

    public boolean isRear48MPSwitchOn() {
        if (isClosed()) {
            return false;
        }
        return ULTRA_PIXEL_ON_REAR_48M.equals(getComponentValue(160));
    }

    public boolean isRear64MPSwitchOn() {
        if (isClosed()) {
            return false;
        }
        return ULTRA_PIXEL_ON_REAR_64M.equals(getComponentValue(160));
    }

    public boolean isRearSwitchOn() {
        return isRear48MPSwitchOn() || isRear64MPSwitchOn() || isRear108MPSwitchOn();
    }

    public boolean isSwitchOn() {
        if (isClosed()) {
            return false;
        }
        return !"OFF".equals(getComponentValue(160));
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        if (this.mItems == null) {
            this.mItems = new ArrayList();
        } else {
            this.mItems.clear();
        }
        this.mCurrentMode = i;
        if (cameraCapabilities != null) {
            Resources resources = CameraAppImpl.getAndroidContext().getResources();
            if (i == 163 || i == 165) {
                if (i2 == 0 && !DataRepository.dataItemFeature().ht()) {
                    int gV = DataRepository.dataItemFeature().gV();
                    if (gV > -1) {
                        switch (gV) {
                            case 1:
                                add48M();
                                return;
                            case 2:
                                add64M();
                                return;
                            default:
                                return;
                        }
                    }
                } else if (i2 == 1) {
                    int gW = DataRepository.dataItemFeature().gW();
                    Size ic = DataRepository.dataItemFeature().ic();
                    if (gW > -1 && cameraCapabilities.isUltraPixelPhotographySupported(ic) && gW == 0) {
                        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_menu_ultra_pixel_photography_32mp, (int) R.drawable.ic_menu_ultra_pixel_photography_32mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_32mp)}), "OFF"));
                        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_menu_ultra_pixel_photography_32mp, (int) R.drawable.ic_menu_ultra_pixel_photography_32mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_32mp)}), ULTRA_PIXEL_ON_FRONT_32M));
                        initUltraPixelResource(ULTRA_PIXEL_ON_FRONT_32M);
                    }
                }
            } else if (i != 167) {
                if (i == 175 && i2 == 0) {
                    int gV2 = DataRepository.dataItemFeature().gV();
                    if (gV2 > -1) {
                        switch (gV2) {
                            case 1:
                                add48M();
                                return;
                            case 2:
                                add64M();
                                return;
                            case 3:
                                add108M();
                                return;
                            default:
                                return;
                        }
                    }
                }
            } else if (i2 == 0) {
                int gV3 = DataRepository.dataItemFeature().gV();
                Size ib = DataRepository.dataItemFeature().ib();
                if (gV3 > -1 && cameraCapabilities.isUltraPixelPhotographySupported(ib)) {
                    switch (gV3) {
                        case 1:
                            this.mItems.add(new ComponentDataItem((int) R.drawable.ic_ultra_pixel_photography_48mp, (int) R.drawable.ic_ultra_pixel_photography_48mp_highlight, resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_48mp)}), "OFF"));
                            this.mItems.add(new ComponentDataItem((int) R.drawable.ic_ultra_pixel_photography_48mp, (int) R.drawable.ic_ultra_pixel_photography_48mp_highlight, resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_48mp)}), ULTRA_PIXEL_ON_REAR_48M));
                            initUltraPixelResource(ULTRA_PIXEL_ON_REAR_48M);
                            return;
                        case 2:
                            this.mItems.add(new ComponentDataItem((int) R.drawable.ic_ultra_pixel_photography_64mp, (int) R.drawable.ic_ultra_pixel_photography_64mp_highlight, resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_64mp)}), "OFF"));
                            this.mItems.add(new ComponentDataItem((int) R.drawable.ic_ultra_pixel_photography_64mp, (int) R.drawable.ic_ultra_pixel_photography_64mp_highlight, resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_64mp)}), ULTRA_PIXEL_ON_REAR_64M));
                            initUltraPixelResource(ULTRA_PIXEL_ON_REAR_64M);
                            return;
                        case 3:
                            add108M();
                            return;
                        default:
                            String str = TAG;
                            Log.d(str, "Unknown rearPixel index: " + gV3);
                            return;
                    }
                }
            }
        }
    }

    public void setClosed(boolean z) {
        if (this.mIsClosed == null) {
            this.mIsClosed = new SparseBooleanArray();
        }
        if (this.mCurrentMode == 165) {
            this.mCurrentMode = 163;
        }
        this.mIsClosed.put(this.mCurrentMode, z);
    }

    public void switchOff() {
        setComponentValue(160, "OFF");
    }

    public void switchOn(@UltraPixelSupport String str) {
        setClosed(false);
        setComponentValue(160, str);
    }

    public void switchOnCurrentSupported(int i, int i2, CameraCapabilities cameraCapabilities) {
        if (isEmpty() || this.mItems.size() < 2) {
            reInit(i, i2, cameraCapabilities);
        }
        if (isEmpty()) {
            Log.e("UltraPixel:", "CameraCapabilities not supported");
            return;
        }
        setClosed(false);
        setComponentValue(160, getCurrentSupportUltraPixel());
    }
}
