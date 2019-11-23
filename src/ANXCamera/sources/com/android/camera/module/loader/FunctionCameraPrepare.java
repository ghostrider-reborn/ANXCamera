package com.android.camera.module.loader;

import android.text.TextUtils;
import com.android.camera.Camera;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.backup.DataBackUp;
import com.android.camera.data.data.config.ComponentConfigBeauty;
import com.android.camera.data.data.config.ComponentConfigBokeh;
import com.android.camera.data.data.config.ComponentConfigFlash;
import com.android.camera.data.data.config.ComponentConfigHdr;
import com.android.camera.data.data.config.ComponentConfigMiMovie;
import com.android.camera.data.data.config.ComponentConfigRatio;
import com.android.camera.data.data.config.ComponentConfigSlowMotion;
import com.android.camera.data.data.config.ComponentConfigUltraWide;
import com.android.camera.data.data.config.ComponentManuallyDualLens;
import com.android.camera.data.data.config.ComponentRunningMacroMode;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.data.data.runing.ComponentRunningAutoZoom;
import com.android.camera.data.data.runing.ComponentRunningSubtitle;
import com.android.camera.data.data.runing.ComponentRunningSuperEIS;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.data.provider.DataProvider;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.permission.PermissionManager;
import com.mi.config.b;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;

public class FunctionCameraPrepare extends Func1Base<Camera, BaseModule> {
    private static final String TAG = "FunctionCameraPrepare";
    private BaseModule baseModule;
    private boolean mNeedReConfigureData;
    private int mResetType;

    public FunctionCameraPrepare(int i, int i2, boolean z, BaseModule baseModule2) {
        super(i);
        this.mResetType = i2;
        this.mNeedReConfigureData = z;
        this.baseModule = baseModule2;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    private void reconfigureData() {
        boolean z;
        DataItemConfig dataItemConfig;
        int i;
        boolean z2;
        boolean z3;
        if (!this.mNeedReConfigureData) {
            DataRepository.dataItemConfig().editor().remove(CameraSettings.KEY_ZOOM).apply();
            return;
        }
        CameraSettings.upgradeGlobalPreferences();
        DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        DataItemConfig dataItemConfig2 = DataRepository.dataItemConfig();
        int lastCameraId = dataItemGlobal.getLastCameraId();
        ComponentConfigFlash componentFlash = dataItemConfig2.getComponentFlash();
        DataProvider.ProviderEditor editor = dataItemConfig2.editor();
        DataProvider.ProviderEditor editor2 = dataItemGlobal.editor();
        DataProvider.ProviderEditor editor3 = DataRepository.dataItemLive().editor();
        DataBackUp backUp = DataRepository.getInstance().backUp();
        editor.remove(CameraSettings.KEY_ZOOM).remove(CameraSettings.KEY_EXPOSURE);
        ComponentRunningAutoZoom componentRunningAutoZoom = dataItemRunning.getComponentRunningAutoZoom();
        if (componentRunningAutoZoom != null) {
            componentRunningAutoZoom.reInitIntentType(dataItemGlobal.getIntentType() == 0);
        }
        if (dataItemRunning.getComponentRunningSuperEIS() != null) {
            int intentType = dataItemGlobal.getIntentType();
        }
        String persistValue = componentFlash.getPersistValue(this.mTargetMode);
        if (!componentFlash.isValidFlashValue(persistValue)) {
            editor.remove(componentFlash.getKey(this.mTargetMode));
        } else if (persistValue.equals("2") || componentFlash.getPersistValue(this.mTargetMode).equals("5")) {
            editor.putString(componentFlash.getKey(this.mTargetMode), componentFlash.getDefaultValue(this.mTargetMode));
        }
        ComponentConfigRatio componentConfigRatio = dataItemConfig2.getComponentConfigRatio();
        if (this.mTargetMode == 163 || this.mTargetMode == 165 || this.mTargetMode == 167 || this.mTargetMode == 173 || this.mTargetMode == 175 || this.mTargetMode == 171) {
            String[] fullSupportRatioValues = componentConfigRatio.getFullSupportRatioValues();
            String persistValue2 = componentConfigRatio.getPersistValue(this.mTargetMode);
            int length = fullSupportRatioValues.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    z3 = false;
                    break;
                } else if (TextUtils.equals(fullSupportRatioValues[i2], persistValue2)) {
                    z3 = true;
                    break;
                } else {
                    i2++;
                }
            }
            if (!z3 && (this.mTargetMode == 165 || !persistValue2.equals(ComponentConfigRatio.RATIO_1X1))) {
                Log.d(TAG, "reconfigureData: clear DATA_CONFIG_RATIO");
                editor.remove("pref_camera_picturesize_key");
            }
        }
        if (this.mTargetMode == 172) {
            ComponentConfigSlowMotion componentConfigSlowMotion = dataItemConfig2.getComponentConfigSlowMotion();
            String componentValue = componentConfigSlowMotion.getComponentValue(172);
            String[] supportAllFPS = componentConfigSlowMotion.getSupportAllFPS();
            int length2 = supportAllFPS.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length2) {
                    z2 = false;
                    break;
                } else if (TextUtils.equals(supportAllFPS[i3], componentValue)) {
                    z2 = true;
                    break;
                } else {
                    i3++;
                }
            }
            if (!z2) {
                Log.d(TAG, "reconfigureData: clear DATA_CONFIG_NEW_SLOW_MOTION_KEY");
                editor.remove(DataItemConfig.DATA_CONFIG_NEW_SLOW_MOTION_KEY);
            }
        }
        if (this.mTargetMode == 167 && !Util.isStringValueContained((Object) dataItemConfig2.getString(CameraSettings.KEY_QC_ISO, CameraAppImpl.getAndroidContext().getString(R.string.pref_camera_iso_default)), (int) R.array.pref_camera_iso_entryvalues)) {
            editor.remove(CameraSettings.KEY_QC_ISO);
        }
        if (!b.jl()) {
            editor.remove(CameraSettings.KEY_QC_FOCUS_POSITION);
            editor.remove(CameraSettings.KEY_QC_EXPOSURETIME);
        }
        if (!Util.isLabOptionsVisible()) {
            editor2.remove(CameraSettings.KEY_FACE_DETECTION).remove(CameraSettings.KEY_CAMERA_PORTRAIT_WITH_FACEBEAUTY).remove(CameraSettings.KEY_CAMERA_FACE_DETECTION_AUTO_HIDDEN).remove(CameraSettings.KEY_CAMERA_DUAL_ENABLE).remove(CameraSettings.KEY_CAMERA_DUAL_SAT_ENABLE).remove(CameraSettings.KEY_CAMERA_MFNR_SAT_ENABLE).remove(CameraSettings.KEY_CAMERA_SR_ENABLE);
        }
        if (!Util.isValidValue(dataItemGlobal.getString(CameraSettings.KEY_ANTIBANDING, "1"))) {
            editor2.remove(CameraSettings.KEY_ANTIBANDING);
        }
        if (!b.kj()) {
            editor2.remove(CameraSettings.KEY_FINGERPRINT_CAPTURE);
        }
        switch (this.mResetType) {
            case 2:
            case 7:
                z = false;
                backUp.revertRunning(dataItemRunning, dataItemGlobal.getDataBackUpKey(this.mTargetMode), dataItemGlobal.getCurrentCameraId());
                if (componentFlash.getPersistValue(this.mTargetMode).equals("2")) {
                    editor.putString(componentFlash.getKey(this.mTargetMode), componentFlash.getDefaultValue(this.mTargetMode));
                    break;
                }
                break;
            case 3:
            case 6:
                resetFlash(componentFlash, editor);
                resetHdr(dataItemConfig2.getComponentHdr(), editor);
                resetBeautyTransientAndVideoPersist(dataItemConfig2.getComponentConfigBeauty(), editor);
                resetUltraWide(dataItemConfig2.getComponentConfigUltraWide(), editor);
                resetLensType(dataItemConfig2.getComponentConfigUltraWide(), dataItemConfig2.getManuallyDualLens(), editor);
                resetMiMovie(dataItemConfig2.getComponentConfigMiMovie(), editor);
                editor.remove(dataItemConfig2.getComponentConfigSlowMotion().getKey(this.mTargetMode));
                if (dataItemGlobal.getCurrentCameraId() == 0) {
                    resetBeautyBackLevel(editor);
                    resetBeautyCaptureFigure(editor);
                    dataItemConfig = (DataItemConfig) DataRepository.provider().dataConfig(1);
                    z = false;
                } else {
                    z = false;
                    dataItemConfig = (DataItemConfig) DataRepository.provider().dataConfig(0);
                }
                DataProvider.ProviderEditor editor4 = dataItemConfig.editor();
                resetFlash(dataItemConfig.getComponentFlash(), editor4);
                resetHdr(dataItemConfig.getComponentHdr(), editor4);
                resetFrontBokenh(dataItemConfig.getComponentBokeh(), editor4);
                resetBeautyTransientAndVideoPersist(dataItemConfig.getComponentConfigBeauty(), editor4);
                resetMiMovie(dataItemConfig.getComponentConfigMiMovie(), editor);
                resetBeautyVideoFront(dataItemConfig);
                editor4.apply();
                dataItemRunning.clearArrayMap();
                backUp.clearBackUp();
                DataRepository.dataItemLive().clearAll();
                resetMacroMode(dataItemRunning.getComponentRunningMacroMode());
                resetAutoZoom(dataItemRunning.getComponentRunningAutoZoom());
                resetSubtitle(dataItemRunning.getComponentRunningSubtitle());
                resetSuperEIS(dataItemRunning.getComponentRunningSuperEIS());
                if (DataRepository.dataItemFeature().gC()) {
                    editor3.remove(CameraSettings.KEY_LIVE_MUSIC_PATH).remove(CameraSettings.KEY_LIVE_MUSIC_HINT).remove(CameraSettings.KEY_LIVE_STICKER).remove(CameraSettings.KEY_LIVE_STICKER_NAME).remove(CameraSettings.KEY_LIVE_STICKER_HINT).remove(CameraSettings.KEY_LIVE_SPEED).remove(CameraSettings.KEY_LIVE_FILTER).remove("key_live_shrink_face_ratio").remove("key_live_enlarge_eye_ratio").remove("key_live_smooth_strength").remove(CameraSettings.KEY_LIVE_BEAUTY_STATUS);
                }
                if (DataRepository.dataItemFeature().gD()) {
                    editor3.remove(CameraSettings.KEY_MIMOJI_INDEX).remove(CameraSettings.KEY_MIMOJI_PANNEL_STATE);
                    break;
                }
                break;
            case 4:
            case 5:
                switch (this.mTargetMode) {
                    case 161:
                    case 174:
                        i = dataItemGlobal.getCurrentCameraId();
                        break;
                    case 162:
                        i = dataItemGlobal.getCurrentCameraId();
                        if (i == 0) {
                            backUp.removeOtherVideoMode();
                            break;
                        }
                        break;
                    case 166:
                    case 167:
                        i = 0;
                        break;
                    case 169:
                        i = dataItemGlobal.getCurrentCameraId();
                        break;
                    case 171:
                        if (DataRepository.dataItemFeature().hw()) {
                            i = dataItemGlobal.getCurrentCameraId();
                            break;
                        }
                    default:
                        i = dataItemGlobal.getCurrentCameraId();
                        break;
                }
                dataItemGlobal.setCameraIdTransient(i);
                backUp.revertRunning(dataItemRunning, dataItemGlobal.getDataBackUpKey(this.mTargetMode), i);
                break;
        }
        z = false;
        boolean gK = DataRepository.dataItemFeature().gK();
        if (!(this.mResetType == 4 && lastCameraId == dataItemGlobal.getCurrentCameraId())) {
            z = gK;
        }
        if (z) {
            editor.putBoolean(CameraSettings.KEY_LENS_DIRTY_DETECT_ENABLED, true);
        }
        editor.apply();
        editor2.apply();
        editor3.apply();
    }

    private void resetAutoZoom(ComponentRunningAutoZoom componentRunningAutoZoom) {
        componentRunningAutoZoom.clearArrayMap();
    }

    private void resetBeautyBackLevel(DataProvider.ProviderEditor providerEditor) {
        for (String str : BeautyConstant.BEAUTY_CATEGORY_LEVEL) {
            providerEditor.remove(BeautyConstant.wrappedSettingKeyForCapture(str));
            providerEditor.remove(BeautyConstant.wrappedSettingKeyForVideo(str));
            providerEditor.remove(BeautyConstant.wrappedSettingKeyForPortrait(str));
            providerEditor.remove(BeautyConstant.wrappedSettingKeyForFun(str));
        }
    }

    private void resetBeautyCaptureFigure(DataProvider.ProviderEditor providerEditor) {
        for (String wrappedSettingKeyForCapture : BeautyConstant.BEAUTY_CATEGORY_BACK_FIGURE) {
            providerEditor.remove(BeautyConstant.wrappedSettingKeyForCapture(wrappedSettingKeyForCapture));
        }
    }

    private void resetBeautyTransientAndVideoPersist(ComponentConfigBeauty componentConfigBeauty, DataProvider.ProviderEditor providerEditor) {
        componentConfigBeauty.clearClosed();
        String persistValue = componentConfigBeauty.getPersistValue(162);
        String defaultValue = componentConfigBeauty.getDefaultValue(162);
        if (!TextUtils.equals(persistValue, defaultValue)) {
            providerEditor.putString(componentConfigBeauty.getKey(162), defaultValue);
        }
    }

    private void resetBeautyVideoFront(DataProvider.ProviderEditor providerEditor) {
        for (String wrappedSettingKeyForVideo : BeautyConstant.BEAUTY_CATEGORY_LEVEL) {
            providerEditor.remove(BeautyConstant.wrappedSettingKeyForVideo(wrappedSettingKeyForVideo));
        }
    }

    private void resetFlash(ComponentConfigFlash componentConfigFlash, DataProvider.ProviderEditor providerEditor) {
        if (!componentConfigFlash.getPersistValue(this.mTargetMode).equals("3")) {
            providerEditor.putString(componentConfigFlash.getKey(this.mTargetMode), componentConfigFlash.getDefaultValue(this.mTargetMode));
        }
    }

    private void resetFrontBokenh(ComponentConfigBokeh componentConfigBokeh, DataProvider.ProviderEditor providerEditor) {
        if ("on".equals(componentConfigBokeh.getPersistValue(this.mTargetMode))) {
            componentConfigBokeh.setComponentValue(this.mTargetMode, "off");
        }
    }

    private void resetHdr(ComponentConfigHdr componentConfigHdr, DataProvider.ProviderEditor providerEditor) {
        String persistValue = componentConfigHdr.getPersistValue(this.mTargetMode);
        if (!persistValue.equals("auto") && !persistValue.equals("off")) {
            providerEditor.putString(componentConfigHdr.getKey(this.mTargetMode), componentConfigHdr.getDefaultValue(this.mTargetMode));
        }
    }

    private void resetLensType(ComponentConfigUltraWide componentConfigUltraWide, ComponentManuallyDualLens componentManuallyDualLens, DataProvider.ProviderEditor providerEditor) {
        if (componentConfigUltraWide != null && componentManuallyDualLens != null) {
            componentManuallyDualLens.resetLensType(componentConfigUltraWide, providerEditor);
        }
    }

    private void resetMacroMode(ComponentRunningMacroMode componentRunningMacroMode) {
        componentRunningMacroMode.clearArrayMap();
    }

    private void resetMiMovie(ComponentConfigMiMovie componentConfigMiMovie, DataProvider.ProviderEditor providerEditor) {
        if (componentConfigMiMovie != null && DataRepository.dataItemFeature().ga()) {
            componentConfigMiMovie.setMiMovie(171, false);
        }
    }

    private void resetSubtitle(ComponentRunningSubtitle componentRunningSubtitle) {
        componentRunningSubtitle.clearArrayMap();
    }

    private void resetSuperEIS(ComponentRunningSuperEIS componentRunningSuperEIS) {
        componentRunningSuperEIS.clearArrayMap();
    }

    private void resetUltraWide(ComponentConfigUltraWide componentConfigUltraWide, DataProvider.ProviderEditor providerEditor) {
        if (componentConfigUltraWide != null) {
            componentConfigUltraWide.resetUltraWide(providerEditor);
        }
    }

    public NullHolder<BaseModule> apply(@NonNull NullHolder<Camera> nullHolder) throws Exception {
        if (!nullHolder.isPresent()) {
            return NullHolder.ofNullable(null, 234);
        }
        if (!PermissionManager.checkCameraLaunchPermissions()) {
            return NullHolder.ofNullable(null, 229);
        }
        Camera camera = nullHolder.get();
        if (camera.isFinishing()) {
            Log.d(TAG, "activity is finishing, the content of BaseModuleHolder is set to null");
            return NullHolder.ofNullable(null, 235);
        }
        camera.changeRequestOrientation();
        if (this.baseModule.isDeparted()) {
            return NullHolder.ofNullable(this.baseModule, 225);
        }
        reconfigureData();
        return NullHolder.ofNullable(this.baseModule);
    }

    public Scheduler getWorkThread() {
        return GlobalConstant.sCameraSetupScheduler;
    }
}
