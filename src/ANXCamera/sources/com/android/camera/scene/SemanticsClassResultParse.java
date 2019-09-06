package com.android.camera.scene;

import android.content.res.Resources.NotFoundException;
import com.android.camera.CameraAppImpl;
import com.android.camera.module.BaseModule;
import com.android.camera.module.Camera2Module;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol.BottomPopupTips;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene.ASDScene;
import java.lang.ref.WeakReference;

public class SemanticsClassResultParse extends AbASDResultParse {
    public SemanticsClassResultParse(WeakReference<BaseModule> weakReference) {
        super(weakReference);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003b, code lost:
        if (r0 != 7) goto L_0x00a5;
     */
    private void updateASDScene(ASDScene aSDScene) {
        int i = aSDScene.type;
        int i2 = aSDScene.value;
        BaseModule baseModule = (BaseModule) this.mModule.get();
        if (baseModule != null && baseModule.getModuleIndex() == 163) {
            Camera2Module camera2Module = (Camera2Module) baseModule;
            int sceneTipResId = MiAlgoAsdSceneProfile.getSceneTipResId(i, i2);
            if (MiAlgoAsdSceneProfile.isSceneChange(i, i2)) {
                BottomPopupTips bottomPopupTips = (BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                if (!(i == 1 || i == 2)) {
                    if (i == 6) {
                        baseModule.showLensDirtyTip();
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
                        } catch (NotFoundException unused) {
                        }
                    } else if (MiAlgoAsdSceneProfile.isCheckSceneEnable(i, i2) && (sceneTipResId > 0 || 2 == i)) {
                        if (2 == i) {
                            camera2Module.showBacklightTip();
                        } else {
                            showTip(14, sceneTipResId, 2);
                        }
                        if (i == 1 || i == 2) {
                            MiAlgoAsdSceneProfile.setTipEnable(1, true);
                            MiAlgoAsdSceneProfile.setTipEnable(2, true);
                        } else {
                            MiAlgoAsdSceneProfile.setTipEnable(i, true);
                        }
                    }
                }
            }
        }
    }

    public void parseMiAlgoAsdResult(ASDScene[] aSDSceneArr) {
        if (aSDSceneArr != null && aSDSceneArr.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("(Semantics)scenes size:");
            sb.append(aSDSceneArr.length);
            FunctionMiAlgoASDEngine.LOGD(sb.toString());
            for (ASDScene aSDScene : aSDSceneArr) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("(Semantics)-->");
                sb2.append(aSDScene.toString());
                FunctionMiAlgoASDEngine.LOGD(sb2.toString());
                updateASDScene(aSDScene);
            }
        }
    }
}
