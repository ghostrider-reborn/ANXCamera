package com.android.camera.scene;

import android.content.res.Resources.NotFoundException;
import com.android.camera.CameraAppImpl;
import com.android.camera.data.DataRepository;
import com.android.camera.module.BaseModule;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol.BottomPopupTips;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene.ASDScene;
import java.lang.ref.WeakReference;

public class NoneSemanticsClassResultParse extends AbASDResultParse {
    private static final int SUPER_NIGHT_VALUE_MASK = 255;

    public NoneSemanticsClassResultParse(WeakReference<BaseModule> weakReference) {
        super(weakReference);
    }

    private void showSuperNightTip(int i, int i2) {
        int sceneTipResId = MiAlgoAsdSceneProfile.getSceneTipResId(i, i2);
        BottomPopupTips bottomPopupTips = (BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
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
            return;
        }
        if (MiAlgoAsdSceneProfile.isCheckSceneEnable(i, i2) && sceneTipResId > 0 && showTip(14, sceneTipResId, 2)) {
            MiAlgoAsdSceneProfile.setTipEnable(i, true);
        }
    }

    private void updateASDScene(ASDScene aSDScene) {
        int i = aSDScene.type;
        int i2 = aSDScene.value;
        if (i == 3 && !isIntercept()) {
            int i3 = i2 & 255;
            if (MiAlgoAsdSceneProfile.isSceneChange(i, i3) && !DataRepository.dataItemFeature().ic()) {
                showSuperNightTip(i, i3);
            }
        }
    }

    public void parseMiAlgoAsdResult(ASDScene[] aSDSceneArr) {
        if (aSDSceneArr != null && aSDSceneArr.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("(NoneSemantics)scenes size:");
            sb.append(aSDSceneArr.length);
            FunctionMiAlgoASDEngine.LOGD(sb.toString());
            for (ASDScene aSDScene : aSDSceneArr) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("(NoneSemantics)-->");
                sb2.append(aSDScene.toString());
                FunctionMiAlgoASDEngine.LOGD(sb2.toString());
                updateASDScene(aSDScene);
            }
        }
    }
}
