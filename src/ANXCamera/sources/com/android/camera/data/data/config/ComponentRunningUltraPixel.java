package com.android.camera.data.data.config;

import android.content.res.Resources;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
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
    private static final String TAG = "ComponentRunningUltraPixel";
    public static final String ULTRA_PIXEL_OFF = "OFF";
    public static final String ULTRA_PIXEL_ON_FRONT_32M = "FRONT_0x1";
    public static final String ULTRA_PIXEL_ON_REAR_48M = "REAR_0x2";
    public static final String ULTRA_PIXEL_ON_REAR_64M = "REAR_0x1";
    public static final String ULTRA_PIXEL_ON_REAR_TEST = "REAR_0x3";
    private String mCloseTipString = null;
    private boolean mIsClosed;
    @DrawableRes
    private int mMenuDrawable = -1;
    private String mMenuString = null;
    private String mOpenTipString = null;

    public @interface UltraPixelSupport {
    }

    public ComponentRunningUltraPixel(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
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

    private void addTEST() {
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_menu_ultra_pixel_photography_64mp, (int) R.drawable.ic_menu_ultra_pixel_photography_64mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_device)}), "OFF"));
        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_menu_ultra_pixel_photography_64mp, (int) R.drawable.ic_menu_ultra_pixel_photography_64mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_device)}), ULTRA_PIXEL_ON_REAR_TEST));
        initUltraPixelResource(ULTRA_PIXEL_ON_REAR_TEST);
    }

    public static String getNoSupportZoomTip() {
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        int Ja = DataRepository.dataItemFeature().Ja();
        if (Ja == 3) {
            return resources.getString(R.string.ultra_pixel_zoom_no_support_tip, new Object[]{resources.getString(R.string.ultra_pixel_device)});
        } else if (Ja == 48000000) {
            return resources.getString(R.string.ultra_pixel_zoom_no_support_tip, new Object[]{resources.getString(R.string.ultra_pixel_48mp)});
        } else if (Ja != 64144128) {
            return resources.getString(R.string.ultra_pixel_zoom_no_support_tip, new Object[]{resources.getString(R.string.ultra_pixel_48mp)});
        } else {
            return resources.getString(R.string.ultra_pixel_zoom_no_support_tip, new Object[]{resources.getString(R.string.ultra_pixel_64mp)});
        }
    }

    public static String[] getUltraPixelSwitchTipsString() {
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        int Ja = DataRepository.dataItemFeature().Ja();
        if (Ja == 3) {
            return new String[]{resources.getString(R.string.accessibility_ultra_pixel_photography_off, new Object[]{resources.getString(R.string.ultra_pixel_device)}), resources.getString(R.string.accessibility_ultra_pixel_photography_on, new Object[]{resources.getString(R.string.ultra_pixel_device)})};
        } else if (Ja == 48000000) {
            return new String[]{resources.getString(R.string.accessibility_ultra_pixel_photography_off, new Object[]{resources.getString(R.string.ultra_pixel_48mp)}), resources.getString(R.string.accessibility_ultra_pixel_photography_on, new Object[]{resources.getString(R.string.ultra_pixel_48mp)})};
        } else if (Ja != 64144128) {
            return new String[]{resources.getString(R.string.accessibility_ultra_pixel_photography_off, new Object[]{resources.getString(R.string.ultra_pixel_48mp)}), resources.getString(R.string.accessibility_ultra_pixel_photography_on, new Object[]{resources.getString(R.string.ultra_pixel_48mp)})};
        } else {
            return new String[]{resources.getString(R.string.accessibility_ultra_pixel_photography_off, new Object[]{resources.getString(R.string.ultra_pixel_64mp)}), resources.getString(R.string.accessibility_ultra_pixel_photography_on, new Object[]{resources.getString(R.string.ultra_pixel_64mp)})};
        }
    }

    public static int[] getUltraPixelTopMenuResources() {
        int Ja = DataRepository.dataItemFeature().Ja();
        return Ja != 3 ? Ja != 48000000 ? Ja != 64144128 ? new int[]{R.drawable.ic_ultra_pixel_photography_48mp, R.drawable.ic_ultra_pixel_photography_48mp_highlight} : new int[]{R.drawable.ic_ultra_pixel_photography_64mp, R.drawable.ic_ultra_pixel_photography_64mp_highlight} : new int[]{R.drawable.ic_ultra_pixel_photography_48mp, R.drawable.ic_ultra_pixel_photography_48mp_highlight} : new int[]{R.drawable.ic_ultra_pixel_photography_64mp, R.drawable.ic_ultra_pixel_photography_64mp_highlight};
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0102  */
    private void initUltraPixelResource(@UltraPixelSupport String str) {
        char c2;
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        int hashCode = str.hashCode();
        if (hashCode != -1379357773) {
            switch (hashCode) {
                case -70725170:
                    if (str.equals(ULTRA_PIXEL_ON_REAR_64M)) {
                        c2 = 2;
                        break;
                    }
                case -70725169:
                    if (str.equals(ULTRA_PIXEL_ON_REAR_48M)) {
                        c2 = 1;
                        break;
                    }
                case -70725168:
                    if (str.equals(ULTRA_PIXEL_ON_REAR_TEST)) {
                        c2 = 3;
                        break;
                    }
            }
        } else if (str.equals(ULTRA_PIXEL_ON_FRONT_32M)) {
            c2 = 0;
            if (c2 != 0) {
                this.mMenuDrawable = R.drawable.ic_menu_ultra_pixel_photography_32mp;
                this.mOpenTipString = resources.getString(R.string.ultra_pixel_photography_open_tip, new Object[]{resources.getString(R.string.ultra_pixel_32mp)});
                this.mCloseTipString = resources.getString(R.string.ultra_pixel_photography_close_tip, new Object[]{resources.getString(R.string.ultra_pixel_32mp)});
                this.mMenuString = resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_32mp)});
                return;
            } else if (c2 == 1) {
                this.mMenuDrawable = R.drawable.ic_menu_ultra_pixel_photography_48mp;
                this.mOpenTipString = resources.getString(R.string.ultra_pixel_photography_open_tip, new Object[]{resources.getString(R.string.ultra_pixel_48mp)});
                this.mCloseTipString = resources.getString(R.string.ultra_pixel_photography_close_tip, new Object[]{resources.getString(R.string.ultra_pixel_48mp)});
                this.mMenuString = resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_48mp)});
                return;
            } else if (c2 == 2) {
                this.mMenuDrawable = R.drawable.ic_menu_ultra_pixel_photography_64mp;
                this.mOpenTipString = resources.getString(R.string.ultra_pixel_photography_open_tip, new Object[]{resources.getString(R.string.ultra_pixel_64mp)});
                this.mCloseTipString = resources.getString(R.string.ultra_pixel_photography_close_tip, new Object[]{resources.getString(R.string.ultra_pixel_64mp)});
                this.mMenuString = resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_64mp)});
                return;
            } else if (c2 != 3) {
                Log.d(TAG, "Unknown ultra pixel size: " + str);
                return;
            } else {
                this.mMenuDrawable = R.drawable.ic_menu_ultra_pixel_photography_64mp;
                this.mOpenTipString = resources.getString(R.string.ultra_pixel_photography_open_tip, new Object[]{resources.getString(R.string.ultra_pixel_device)});
                this.mCloseTipString = resources.getString(R.string.ultra_pixel_photography_close_tip, new Object[]{resources.getString(R.string.ultra_pixel_device)});
                this.mMenuString = resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_device)});
                return;
            }
        }
        c2 = 65535;
        if (c2 != 0) {
        }
    }

    @UltraPixelSupport
    public String getCurrentSupportUltraPixel() {
        return this.mItems.get(1).mValue;
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
        return this.mIsClosed;
    }

    public boolean isFront32MPSwitchOn() {
        return ULTRA_PIXEL_ON_FRONT_32M.equals(getComponentValue(160));
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
        return isRear48MPSwitchOn() || isRear64MPSwitchOn() || isRearTESTPSwitchOn();
    }

    public boolean isRearTESTPSwitchOn() {
        if (isClosed()) {
            return false;
        }
        return ULTRA_PIXEL_ON_REAR_TEST.equals(getComponentValue(160));
    }

    public boolean isSwitchOn() {
        if (isClosed()) {
            return false;
        }
        return !"OFF".equals(getComponentValue(160));
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        List<ComponentDataItem> list = this.mItems;
        if (list == null) {
            this.mItems = new ArrayList();
        } else {
            list.clear();
        }
        if (cameraCapabilities != null) {
            Resources resources = CameraAppImpl.getAndroidContext().getResources();
            if (i == 163 || i == 165) {
                if (i2 == 0 && !DataRepository.dataItemFeature().fd()) {
                    int Ja = DataRepository.dataItemFeature().Ja();
                    if (Ja <= 0) {
                        return;
                    }
                    if (Ja == 48000000) {
                        add48M();
                    } else if (Ja == 64144128) {
                        add64M();
                    }
                } else if (i2 == 1) {
                    int Da = DataRepository.dataItemFeature().Da();
                    if (Da > 0 && cameraCapabilities.isUltraPixelPhotographySupported(Da) && Da == 32275200) {
                        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_menu_ultra_pixel_photography_32mp, (int) R.drawable.ic_menu_ultra_pixel_photography_32mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_32mp)}), "OFF"));
                        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_menu_ultra_pixel_photography_32mp, (int) R.drawable.ic_menu_ultra_pixel_photography_32mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_32mp)}), ULTRA_PIXEL_ON_FRONT_32M));
                        initUltraPixelResource(ULTRA_PIXEL_ON_FRONT_32M);
                    }
                }
            } else if (i != 167) {
                if (i == 175 && i2 == 0) {
                    int Ja2 = DataRepository.dataItemFeature().Ja();
                    if (Ja2 <= 0) {
                        return;
                    }
                    if (Ja2 == 3) {
                        addTEST();
                    } else if (Ja2 == 48000000) {
                        add48M();
                    } else if (Ja2 == 64144128) {
                        add64M();
                    }
                }
            } else if (i2 == 0) {
                int Ja3 = DataRepository.dataItemFeature().Ja();
                if (Ja3 > 0 && cameraCapabilities.isUltraPixelPhotographySupported(Ja3)) {
                    if (Ja3 == 3) {
                        addTEST();
                    } else if (Ja3 == 48000000) {
                        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_ultra_pixel_photography_48mp, (int) R.drawable.ic_ultra_pixel_photography_48mp_highlight, resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_48mp)}), "OFF"));
                        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_ultra_pixel_photography_48mp, (int) R.drawable.ic_ultra_pixel_photography_48mp_highlight, resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_48mp)}), ULTRA_PIXEL_ON_REAR_48M));
                        initUltraPixelResource(ULTRA_PIXEL_ON_REAR_48M);
                    } else if (Ja3 != 64144128) {
                        String str = TAG;
                        Log.d(str, "Unknown rearPixel size: " + Ja3);
                    } else {
                        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_ultra_pixel_photography_64mp, (int) R.drawable.ic_ultra_pixel_photography_64mp_highlight, resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_64mp)}), "OFF"));
                        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_ultra_pixel_photography_64mp, (int) R.drawable.ic_ultra_pixel_photography_64mp_highlight, resources.getString(R.string.pref_menu_ultra_pixel_photography, new Object[]{resources.getString(R.string.ultra_pixel_64mp)}), ULTRA_PIXEL_ON_REAR_64M));
                        initUltraPixelResource(ULTRA_PIXEL_ON_REAR_64M);
                    }
                }
            }
        }
    }

    public void setClosed(boolean z) {
        this.mIsClosed = z;
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
        } else {
            setComponentValue(160, getCurrentSupportUltraPixel());
        }
    }
}
