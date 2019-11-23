package com.android.camera.scene;

import android.content.res.Resources;
import com.android.camera.CameraAppImpl;
import com.android.camera.module.BaseModule;
import com.android.camera.module.Camera2Module;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStat;
import com.android.camera.statistic.CameraStatUtil;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import java.lang.ref.WeakReference;

public class SemanticsClassResultParse extends AbASDResultParse {
    public SemanticsClassResultParse(WeakReference<BaseModule> weakReference) {
        super(weakReference);
    }

    private void updateASDScene(MarshalQueryableASDScene.ASDScene aSDScene) {
        int i = aSDScene.type;
        int i2 = aSDScene.value;
        BaseModule baseModule = (BaseModule) this.mModule.get();
        if (baseModule != null && baseModule.getModuleIndex() == 163) {
            Camera2Module camera2Module = (Camera2Module) baseModule;
            int sceneTipResId = MiAlgoAsdSceneProfile.getSceneTipResId(i, i2);
            if (MiAlgoAsdSceneProfile.isSceneChange(i, i2)) {
                ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                switch (i) {
                    case 1:
                    case 2:
                    case 7:
                        if (!isIntercept()) {
                            if (MiAlgoAsdSceneProfile.isTipEnable(i)) {
                                try {
                                    String string = CameraAppImpl.getAndroidContext().getResources().getString(sceneTipResId);
                                    boolean z = false;
                                    if (bottomPopupTips != null && bottomPopupTips.isTipShowing() && string != null && string.equals(bottomPopupTips.getCurrentBottomTipMsg())) {
                                        z = true;
                                    }
                                    if (z && !MiAlgoAsdSceneProfile.isCheckSceneEnable(i, i2)) {
                                        closeTip();
                                        return;
                                    }
                                    return;
                                } catch (Resources.NotFoundException e) {
                                    return;
                                }
                            } else if (MiAlgoAsdSceneProfile.isCheckSceneEnable(i, i2)) {
                                if (sceneTipResId > 0 || 2 == i) {
                                    if (2 == i) {
                                        baseModule.getHandler().post(new Runnable() {
                                            public final void run() {
                                                Camera2Module.this.showBacklightTip();
                                            }
                                        });
                                        CameraStatUtil.trackBacklitTip(CameraStat.CATEGORY_COUNTER);
                                    } else {
                                        showTip(14, sceneTipResId, 2);
                                    }
                                    if (i == 1 || i == 2) {
                                        MiAlgoAsdSceneProfile.setTipEnable(1, true);
                                        MiAlgoAsdSceneProfile.setTipEnable(2, true);
                                        CameraStatUtil.trackPortraitTip(CameraStat.CATEGORY_COUNTER);
                                        return;
                                    }
                                    MiAlgoAsdSceneProfile.setTipEnable(i, true);
                                    return;
                                }
                                return;
                            } else {
                                return;
                            }
                        } else {
                            return;
                        }
                    case 6:
                        baseModule.showLensDirtyTip();
                        CameraStatUtil.trackAsdDirtyTip(baseModule.getBogusCameraId());
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public void parseMiAlgoAsdResult(MarshalQueryableASDScene.ASDScene[] aSDSceneArr) {
        if (aSDSceneArr != null && aSDSceneArr.length > 0) {
            FunctionMiAlgoASDEngine.LOGD("(Semantics)scenes size:" + aSDSceneArr.length);
            for (MarshalQueryableASDScene.ASDScene aSDScene : aSDSceneArr) {
                FunctionMiAlgoASDEngine.LOGD("(Semantics)-->" + aSDScene.toString());
                updateASDScene(aSDSceneArr[r1]);
            }
        }
    }
}
