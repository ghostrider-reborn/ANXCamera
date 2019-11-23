package com.android.camera.module.impl.component;

import com.android.camera.ActivityBase;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.config.ComponentManuallyDualLens;
import com.android.camera.data.data.config.ComponentManuallyET;
import com.android.camera.data.data.config.ComponentManuallyFocus;
import com.android.camera.data.data.config.ComponentManuallyISO;
import com.android.camera.data.data.config.ComponentManuallyWB;
import com.android.camera.data.data.config.SupportedConfigFactory;
import com.android.camera.effect.EffectController;
import com.android.camera.module.BaseModule;
import com.android.camera.module.loader.StartControl;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtil;
import com.mi.config.b;
import java.util.ArrayList;
import java.util.List;

public class ManuallyValueChangeImpl implements ModeProtocol.ManuallyValueChanged {
    private static final String TAG = ManuallyValueChangeImpl.class.getSimpleName();
    private ActivityBase mActivity;

    public ManuallyValueChangeImpl(ActivityBase activityBase) {
        this.mActivity = activityBase;
    }

    public static ManuallyValueChangeImpl create(ActivityBase activityBase) {
        return new ManuallyValueChangeImpl(activityBase);
    }

    public BaseModule getBaseModule() {
        return (BaseModule) this.mActivity.getCurrentModule();
    }

    public void onBokehFNumberValueChanged(String str) {
        CameraSettings.writeFNumber(str);
        getBaseModule().updatePreferenceInWorkThread(48);
    }

    public void onDualLensSwitch(ComponentManuallyDualLens componentManuallyDualLens, int i) {
        HybridZoomingSystem.clearZoomRatioHistory();
        String componentValue = componentManuallyDualLens.getComponentValue(i);
        String next = componentManuallyDualLens.next(componentValue, i);
        if (i != 167 || !HybridZoomingSystem.IS_4_OR_MORE_SAT) {
            componentValue = next;
        }
        componentManuallyDualLens.setComponentValue(i, componentValue);
        CameraSettings.setUltraWideConfig(i, ComponentManuallyDualLens.LENS_ULTRA.equalsIgnoreCase(componentValue));
        if (!ComponentManuallyDualLens.LENS_WIDE.equalsIgnoreCase(componentValue)) {
            CameraSettings.switchOffUltraPixel();
        }
        CameraStatUtil.trackLensChanged(componentValue);
        this.mActivity.onModeSelected(StartControl.create(i).setResetType(5).setViewConfigType(2).setNeedBlurAnimation(true));
    }

    public void onDualLensZooming(boolean z) {
        BaseModule baseModule = getBaseModule();
        if (baseModule.isAlive() && !CameraSettings.isZoomByCameraSwitchingSupported() && baseModule.getActualCameraId() == Camera2DataContainer.getInstance().getSATCameraId()) {
            baseModule.notifyZooming(z);
        }
    }

    public void onDualZoomHappened(boolean z) {
        BaseModule baseModule = getBaseModule();
        if (baseModule.isAlive() && !CameraSettings.isZoomByCameraSwitchingSupported() && baseModule.getActualCameraId() == Camera2DataContainer.getInstance().getSATCameraId()) {
            baseModule.notifyDualZoom(z);
        }
    }

    public void onDualZoomValueChanged(float f, int i) {
        if (getBaseModule().isAlive()) {
            getBaseModule().onZoomRatioChanged(f, i);
        }
    }

    public void onETValueChanged(ComponentManuallyET componentManuallyET, String str) {
        CameraStatUtil.trackExposureTimeChanged(str);
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (!CameraSettings.isFlashSupportedInManualMode()) {
            DataRepository.dataItemConfig().getComponentFlash().setComponentValue(getBaseModule().getModuleIndex(), "0");
            configChanges.closeMutexElement(SupportedConfigFactory.CLOSE_BY_MANUAL_MODE, 193);
        } else {
            configChanges.restoreMutexFlash(SupportedConfigFactory.CLOSE_BY_MANUAL_MODE);
        }
        getBaseModule().updatePreferenceInWorkThread(16, 20, 30, 34, 10);
    }

    public void onFocusValueChanged(ComponentManuallyFocus componentManuallyFocus, String str, String str2) {
        if (!CameraSettings.getMappingFocusMode(Integer.valueOf(str).intValue()).equals(CameraSettings.getMappingFocusMode(Integer.valueOf(str2).intValue()))) {
            CameraSettings.setFocusModeSwitching(true);
            boolean equals = str2.equals(componentManuallyFocus.getDefaultValue(167));
            if (b.jw()) {
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (equals) {
                    topAlert.removeConfigItem(199);
                    EffectController.getInstance().setDrawPeaking(false);
                } else {
                    topAlert.insertConfigItem(199);
                    if (DataRepository.dataItemRunning().isSwitchOn("pref_camera_peak_key")) {
                        EffectController.getInstance().setDrawPeaking(true);
                    }
                }
            }
        }
        getBaseModule().updatePreferenceInWorkThread(14);
    }

    public void onISOValueChanged(ComponentManuallyISO componentManuallyISO, String str) {
        CameraStatUtil.trackIsoChanged(str);
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (!CameraSettings.isFlashSupportedInManualMode()) {
            DataRepository.dataItemConfig().getComponentFlash().setComponentValue(getBaseModule().getModuleIndex(), "0");
            configChanges.closeMutexElement(SupportedConfigFactory.CLOSE_BY_MANUAL_MODE, 193);
        } else {
            configChanges.restoreMutexFlash(SupportedConfigFactory.CLOSE_BY_MANUAL_MODE);
        }
        getBaseModule().updatePreferenceInWorkThread(15, 10);
    }

    public void onWBValueChanged(ComponentManuallyWB componentManuallyWB, String str, boolean z) {
        if (!z) {
            componentManuallyWB.getKey(167);
        }
        if (z) {
            str = "manual";
        }
        CameraStatUtil.trackAwbChanged(str);
        getBaseModule().updatePreferenceInWorkThread(6);
    }

    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(174, this);
    }

    public void resetManuallyParameters(List<ComponentData> list) {
        if (list != null && list.size() != 0) {
            ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                ComponentData componentData = list.get(i);
                if (componentData instanceof ComponentManuallyWB) {
                    arrayList.add(6);
                } else if (componentData instanceof ComponentManuallyISO) {
                    configChanges.restoreMutexFlash(SupportedConfigFactory.CLOSE_BY_MANUAL_MODE);
                    arrayList.add(15);
                    arrayList.add(10);
                } else if (componentData instanceof ComponentManuallyET) {
                    configChanges.restoreMutexFlash(SupportedConfigFactory.CLOSE_BY_MANUAL_MODE);
                    arrayList.add(16);
                    arrayList.add(30);
                    arrayList.add(34);
                    arrayList.add(20);
                    arrayList.add(10);
                } else if (componentData instanceof ComponentManuallyFocus) {
                    arrayList.add(14);
                    if (b.jw()) {
                        ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).removeConfigItem(199);
                        EffectController.getInstance().setDrawPeaking(false);
                    }
                }
            }
            int[] iArr = new int[arrayList.size()];
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                iArr[i2] = ((Integer) arrayList.get(i2)).intValue();
            }
            getBaseModule().updatePreferenceInWorkThread((int[]) iArr.clone());
        }
    }

    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(174, this);
    }

    public void updateSATIsZooming(boolean z) {
        getBaseModule().updateSATZooming(z);
    }
}
