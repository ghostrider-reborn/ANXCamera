package com.android.camera.module.loader;

import com.android.camera.Camera;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.effect.EffectController;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.module.impl.component.TimeSpeedModel;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.Camera2OpenManager;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import java.util.List;

public class FunctionModuleSetup extends Func1Base<BaseModule, BaseModule> {
    private static final String TAG = FunctionModuleSetup.class.getSimpleName();

    public FunctionModuleSetup(int i) {
        super(i);
    }

    public NullHolder<BaseModule> apply(@NonNull NullHolder<BaseModule> nullHolder) throws Exception {
        String str = TAG;
        Log.d(str, "apply: module isPresent = " + nullHolder.isPresent());
        if (!nullHolder.isPresent()) {
            return nullHolder;
        }
        BaseModule baseModule = nullHolder.get();
        if (baseModule.isDeparted()) {
            return NullHolder.ofNullable(baseModule, 225);
        }
        EffectController.getInstance().reset();
        Camera activity = baseModule.getActivity();
        DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        switch (this.mTargetMode) {
            case 162:
                dataItemRunning.switchOff("pref_video_speed_fast_key");
                break;
            case 163:
                dataItemRunning.switchOn("pref_camera_square_mode_key");
                break;
            case 165:
                dataItemRunning.switchOn("pref_camera_square_mode_key");
                break;
            case 167:
                dataItemRunning.switchOn("pref_camera_manual_mode_key");
                break;
            case 169:
                dataItemRunning.switchOn("pref_video_speed_fast_key");
                break;
            case 171:
                if (dataItemGlobal.getCurrentCameraId() != 0) {
                    dataItemRunning.switchOff("pref_camera_portrait_mode_key");
                    break;
                } else {
                    dataItemRunning.switchOn("pref_camera_portrait_mode_key");
                    break;
                }
            case 174:
                if (activity.startFromKeyguard()) {
                    DataRepository.dataItemLive().setRecordSegmentTimeInfo((List<TimeSpeedModel>) null);
                    break;
                }
                break;
            case 175:
                int currentCameraId = dataItemGlobal.getCurrentCameraId();
                dataItemRunning.getComponentUltraPixel().switchOnCurrentSupported(175, currentCameraId, Camera2DataContainer.getInstance().getCapabilitiesByBogusCameraId(currentCameraId, 175));
                break;
        }
        if (baseModule.isDeparted()) {
            return NullHolder.ofNullable(baseModule, 225);
        }
        try {
            baseModule.setCameraDevice(Camera2OpenManager.getInstance().getCurrentCamera2Device());
            baseModule.onCreate(this.mTargetMode, dataItemGlobal.getCurrentCameraId());
            if (baseModule.isCreated()) {
                baseModule.registerProtocol();
                baseModule.onResume();
            }
            return nullHolder;
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Module init error: ", e);
            baseModule.setDeparted();
            return NullHolder.ofNullable(null, 237);
        }
    }

    public Scheduler getWorkThread() {
        return GlobalConstant.sCameraSetupScheduler;
    }
}
