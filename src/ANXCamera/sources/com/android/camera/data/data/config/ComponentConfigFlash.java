package com.android.camera.data.data.config;

import android.annotation.TargetApi;
import android.text.TextUtils;
import android.util.SparseArray;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.ThermalDetector;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import com.mi.config.b;
import java.util.ArrayList;
import java.util.List;

@TargetApi(21)
public class ComponentConfigFlash extends ComponentData {
    public static final String FLASH_VALUE_AUTO = "3";
    public static final String FLASH_VALUE_BACK_SOFT_LIGHT = "5";
    public static final String FLASH_VALUE_MANUAL_OFF = "200";
    public static final String FLASH_VALUE_OFF = "0";
    public static final String FLASH_VALUE_ON = "1";
    public static final String FLASH_VALUE_SCREEN_LIGHT_AUTO = "103";
    public static final String FLASH_VALUE_SCREEN_LIGHT_ON = "101";
    public static final String FLASH_VALUE_TORCH = "2";
    private static final String TAG = "ComponentConfigFlash";
    private SparseArray<String> mFlashValuesForSceneMode = new SparseArray<>();
    private boolean mIsBackSoftLightSupported;
    private boolean mIsClosed;
    private boolean mIsHardwareSupported;

    public ComponentConfigFlash(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
        this.mItems.add(new ComponentDataItem(getFlashOffRes(), getFlashOffRes(), (int) R.string.pref_camera_flashmode_entry_off, "0"));
    }

    private String getComponentValueInternal(int i) {
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        if (dataItemRunning.isSwitchOn("pref_camera_scenemode_setting_key")) {
            String flashModeByScene = CameraSettings.getFlashModeByScene(dataItemRunning.getComponentRunningSceneValue().getComponentValue(i));
            if (!TextUtils.isEmpty(flashModeByScene)) {
                return flashModeByScene;
            }
        }
        String defaultValue = getDefaultValue(i);
        String string = this.mParentDataItem.getString(getKey(i), defaultValue);
        if (string != null && !string.equals(defaultValue) && !checkValueValid(string)) {
            String simpleName = ComponentConfigFlash.class.getSimpleName();
            StringBuilder sb = new StringBuilder();
            sb.append("reset invalid value ");
            sb.append(string);
            Log.e(simpleName, sb.toString());
            string = defaultValue;
        }
        return string;
    }

    private int getFlashAutoRes() {
        return R.drawable.ic_new_config_flash_auto;
    }

    private int getFlashBackSoftLightRes() {
        return R.drawable.ic_new_config_flash_back_soft_light;
    }

    private int getFlashBackSoftLightSelectedRes() {
        return R.drawable.ic_new_config_flash_back_soft_light_selected;
    }

    private int getFlashOffRes() {
        return R.drawable.ic_new_config_flash_off;
    }

    private int getFlashOnRes() {
        return R.drawable.ic_new_config_flash_on;
    }

    private int getFlashTorchRes() {
        return R.drawable.ic_new_config_flash_torch;
    }

    /* access modifiers changed from: protected */
    public boolean checkValueValid(String str) {
        for (ComponentDataItem componentDataItem : getItems()) {
            if (TextUtils.equals(str, componentDataItem.mValue)) {
                return true;
            }
        }
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("checkValueValid: invalid value: ");
        sb.append(str);
        Log.d(str2, sb.toString());
        return false;
    }

    public void clearClosed() {
        this.mIsClosed = false;
    }

    public boolean disableUpdate() {
        return ThermalDetector.getInstance().thermalConstrained() && isHardwareSupported();
    }

    public String getComponentValue(int i) {
        String str = "0";
        return (!isClosed() && !isEmpty()) ? getComponentValueInternal(i) : str;
    }

    public String getDefaultValue(int i) {
        return "0";
    }

    public int getDisableReasonString() {
        return CameraSettings.isFrontCamera() ? R.string.close_front_flash_toast : R.string.close_back_flash_toast;
    }

    public int getDisplayTitleString() {
        return R.string.pref_camera_flashmode_title;
    }

    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    public String getKey(int i) {
        if (!(i == 169 || i == 172 || i == 174 || i == 179)) {
            switch (i) {
                case 160:
                    throw new RuntimeException("unspecified flash");
                case 161:
                case 162:
                    break;
                default:
                    return CameraSettings.KEY_FLASH_MODE;
            }
        }
        return CameraSettings.KEY_VIDEOCAMERA_FLASH_MODE;
    }

    public int getValueSelectedDrawableIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        if ("1".equals(componentValue)) {
            return getFlashOnRes();
        }
        if ("3".equals(componentValue)) {
            return getFlashAutoRes();
        }
        if ("0".equals(componentValue)) {
            return (i != 171 || !this.mIsBackSoftLightSupported) ? getFlashOffRes() : getFlashBackSoftLightRes();
        }
        if ("2".equals(componentValue)) {
            return CameraSettings.isFrontCamera() ? getFlashOnRes() : getFlashTorchRes();
        } else if (FLASH_VALUE_SCREEN_LIGHT_AUTO.equals(componentValue)) {
            return getFlashAutoRes();
        } else {
            if (FLASH_VALUE_SCREEN_LIGHT_ON.equals(componentValue)) {
                return getFlashOnRes();
            }
            if ("5".equals(componentValue)) {
                return getFlashBackSoftLightSelectedRes();
            }
            return -1;
        }
    }

    public int getValueSelectedStringIdIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        boolean equals = "1".equals(componentValue);
        int i2 = R.string.accessibility_flash_on;
        if (equals) {
            return R.string.accessibility_flash_on;
        }
        if ("3".equals(componentValue)) {
            return R.string.accessibility_flash_auto;
        }
        if ("0".equals(componentValue)) {
            return R.string.accessibility_flash_off;
        }
        if ("2".equals(componentValue)) {
            if (!CameraSettings.isFrontCamera()) {
                i2 = R.string.accessibility_flash_torch;
            }
            return i2;
        } else if (FLASH_VALUE_SCREEN_LIGHT_AUTO.equals(componentValue)) {
            return R.string.accessibility_flash_auto;
        } else {
            if (FLASH_VALUE_SCREEN_LIGHT_ON.equals(componentValue)) {
                return R.string.accessibility_flash_on;
            }
            if ("5".equals(componentValue)) {
                return R.string.accessibility_flash_back_soft_light;
            }
            return -1;
        }
    }

    public boolean isClosed() {
        return this.mIsClosed;
    }

    public boolean isHardwareSupported() {
        return this.mIsHardwareSupported;
    }

    public boolean isValidFlashValue(String str) {
        return str.matches("^[0-9]+$");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004f, code lost:
        if (r9 != 173) goto L_0x0086;
     */
    public List<ComponentDataItem> reInit(int i, int i2, CameraCapabilities cameraCapabilities, ComponentConfigUltraWide componentConfigUltraWide) {
        List<ComponentDataItem> list = this.mItems;
        if (list == null) {
            this.mItems = new ArrayList();
        } else {
            list.clear();
        }
        boolean z = false;
        this.mIsHardwareSupported = cameraCapabilities.isFlashSupported() && DataRepository.dataItemGlobal().getDisplayMode() == 1;
        if (DataRepository.dataItemFeature().Jb() && i2 == 0 && cameraCapabilities.isBackSoftLightSupported()) {
            z = true;
        }
        this.mIsBackSoftLightSupported = z;
        String str = "5";
        String str2 = "0";
        if (i != 166) {
            if (i == 171) {
                if (this.mIsHardwareSupported && this.mIsBackSoftLightSupported) {
                    this.mItems.add(new ComponentDataItem(getFlashBackSoftLightRes(), getFlashBackSoftLightRes(), (int) R.string.pref_camera_flashmode_entry_off, str2));
                    this.mItems.add(new ComponentDataItem(getFlashBackSoftLightSelectedRes(), getFlashBackSoftLightSelectedRes(), (int) R.string.pref_camera_flashmode_entry_back_soft_light, str));
                    return this.mItems;
                }
            }
        }
        if (i2 == 0) {
            return this.mItems;
        }
        if (!this.mIsHardwareSupported) {
            if (i2 == 1 && b.Si()) {
                String str3 = FLASH_VALUE_SCREEN_LIGHT_ON;
                if (i == 163 || i == 165 || i == 171) {
                    this.mItems.add(new ComponentDataItem(getFlashOffRes(), getFlashOffRes(), (int) R.string.pref_camera_flashmode_entry_off, str2));
                    this.mItems.add(new ComponentDataItem(getFlashAutoRes(), getFlashAutoRes(), (int) R.string.pref_camera_flashmode_entry_auto, FLASH_VALUE_SCREEN_LIGHT_AUTO));
                    this.mItems.add(new ComponentDataItem(getFlashOnRes(), getFlashOnRes(), (int) R.string.pref_camera_flashmode_entry_on, str3));
                }
                if (i == 177) {
                    this.mItems.add(new ComponentDataItem(getFlashOffRes(), getFlashOffRes(), (int) R.string.pref_camera_flashmode_entry_off, str2));
                    this.mItems.add(new ComponentDataItem(getFlashOnRes(), getFlashOnRes(), (int) R.string.pref_camera_flashmode_entry_on, str3));
                }
            }
            return this.mItems;
        }
        this.mItems.add(new ComponentDataItem(getFlashOffRes(), getFlashOffRes(), (int) R.string.pref_camera_flashmode_entry_off, str2));
        String str4 = "2";
        if (!(i == 161 || i == 162 || i == 169 || i == 172 || i == 174)) {
            String str5 = "1";
            if (i == 177) {
                if (CameraSettings.isBackCamera()) {
                    this.mItems.add(new ComponentDataItem(getFlashOnRes(), getFlashOnRes(), (int) R.string.pref_camera_flashmode_entry_on, str5));
                }
                if (CameraSettings.isFrontCamera() && b.Ki()) {
                    this.mItems.add(new ComponentDataItem(getFlashOnRes(), getFlashOnRes(), (int) R.string.pref_camera_flashmode_entry_on, str4));
                } else if (b.zj()) {
                    this.mItems.add(new ComponentDataItem(getFlashTorchRes(), getFlashTorchRes(), (int) R.string.pref_camera_flashmode_entry_torch, str4));
                }
            } else if (i != 179) {
                this.mItems.add(new ComponentDataItem(getFlashAutoRes(), getFlashAutoRes(), (int) R.string.pref_camera_flashmode_entry_auto, "3"));
                if (CameraSettings.isBackCamera()) {
                    this.mItems.add(new ComponentDataItem(getFlashOnRes(), getFlashOnRes(), (int) R.string.pref_camera_flashmode_entry_on, str5));
                }
                if (CameraSettings.isFrontCamera() && b.Ki()) {
                    this.mItems.add(new ComponentDataItem(getFlashOnRes(), getFlashOnRes(), (int) R.string.pref_camera_flashmode_entry_on, str4));
                } else if (b.zj()) {
                    this.mItems.add(new ComponentDataItem(getFlashTorchRes(), getFlashTorchRes(), (int) R.string.pref_camera_flashmode_entry_torch, str4));
                }
                if (this.mIsBackSoftLightSupported) {
                    this.mItems.add(new ComponentDataItem(getFlashBackSoftLightSelectedRes(), getFlashBackSoftLightSelectedRes(), (int) R.string.pref_camera_flashmode_entry_back_soft_light, str));
                }
            }
            return this.mItems;
        }
        this.mItems.add(new ComponentDataItem(getFlashTorchRes(), getFlashTorchRes(), (int) R.string.pref_camera_flashmode_entry_torch, str4));
        if (this.mIsBackSoftLightSupported) {
            this.mItems.add(new ComponentDataItem(getFlashBackSoftLightSelectedRes(), getFlashBackSoftLightSelectedRes(), (int) R.string.pref_camera_flashmode_entry_back_soft_light, str));
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

    public void setSceneModeFlashValue(int i, String str) {
        this.mFlashValuesForSceneMode.put(i, str);
    }
}
