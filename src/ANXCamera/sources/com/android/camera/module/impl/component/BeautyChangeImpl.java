package com.android.camera.module.impl.component;

import com.android.camera.ActivityBase;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;

public class BeautyChangeImpl implements ModeProtocol.OnFaceBeautyChangedProtocol {
    private static final String TAG = "BeautyChangeImpl";
    private ActivityBase mActivity;

    public BeautyChangeImpl(ActivityBase activityBase) {
        this.mActivity = activityBase;
    }

    public static BeautyChangeImpl create(ActivityBase activityBase) {
        return new BeautyChangeImpl(activityBase);
    }

    private BaseModule getBaseModule() {
        ActivityBase activityBase = this.mActivity;
        if (activityBase == null) {
            return null;
        }
        return (BaseModule) activityBase.getCurrentModule();
    }

    public static void preload() {
        Log.i(TAG, "preload");
    }

    public void onBeautyChanged(boolean z) {
        BaseModule baseModule = getBaseModule();
        if (baseModule != null) {
            int moduleIndex = baseModule.getModuleIndex();
            ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
            if (componentRunningShine.supportTopConfigEntry()) {
                boolean currentStatus = componentRunningShine.getCurrentStatus();
                boolean determineStatus = componentRunningShine.determineStatus(moduleIndex);
                if (currentStatus != determineStatus) {
                    String str = TAG;
                    Log.d(str, "beauty status changed: " + determineStatus);
                    baseModule.getHandler().post(new Runnable() {
                        public void run() {
                            ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(212);
                        }
                    });
                }
            }
            if (!z) {
                getBaseModule().onBeautyParameterChanged();
            }
        }
    }

    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(199, this);
    }

    public void unRegisterProtocol() {
        this.mActivity = null;
        ModeCoordinatorImpl.getInstance().detachProtocol(199, this);
    }
}
