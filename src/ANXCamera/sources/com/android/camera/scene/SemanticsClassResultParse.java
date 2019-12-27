package com.android.camera.scene;

import android.content.res.Resources;
import com.android.camera.CameraAppImpl;
import com.android.camera.module.BaseModule;
import com.android.camera.module.Camera2Module;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
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
                if (!(i == 1 || i == 2)) {
                    if (i == 6) {
                        baseModule.showLensDirtyTip();
                        return;
                    } else if (i != 7) {
                        return;
                    }
                }
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
                            }
                        } catch (Resources.NotFoundException unused) {
                        }
                    } else if (MiAlgoAsdSceneProfile.isCheckSceneEnable(i, i2)) {
                        if (sceneTipResId > 0 || 2 == i) {
                            if (2 == i) {
                                camera2Module.showBacklightTip();
                            } else {
                                showTip(14, sceneTipResId, 2);
                            }
                            if (i == 1 || i == 2) {
                                MiAlgoAsdSceneProfile.setTipEnable(1, true);
                                MiAlgoAsdSceneProfile.setTipEnable(2, true);
                                return;
                            }
                            MiAlgoAsdSceneProfile.setTipEnable(i, true);
                        }
                    }
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
