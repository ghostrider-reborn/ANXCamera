package com.android.camera.scene;

import android.content.res.Resources;
import com.android.camera.CameraAppImpl;
import com.android.camera.data.DataRepository;
import com.android.camera.module.BaseModule;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import java.lang.ref.WeakReference;

public class NoneSemanticsClassResultParse extends AbASDResultParse {
    private static final int SUPER_NIGHT_VALUE_MASK = 255;

    public NoneSemanticsClassResultParse(WeakReference<BaseModule> weakReference) {
        super(weakReference);
    }

    private void showSuperNightTip(int i, int i2) {
        int sceneTipResId = MiAlgoAsdSceneProfile.getSceneTipResId(i, i2);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
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
        } else if (MiAlgoAsdSceneProfile.isCheckSceneEnable(i, i2) && sceneTipResId > 0 && showTip(14, sceneTipResId, 2)) {
            MiAlgoAsdSceneProfile.setTipEnable(i, true);
        }
    }

    private void updateASDScene(MarshalQueryableASDScene.ASDScene aSDScene) {
        int i = aSDScene.type;
        int i2 = aSDScene.value;
        if (i == 3 && !isIntercept()) {
            int i3 = i2 & 255;
            if (MiAlgoAsdSceneProfile.isSceneChange(i, i3) && !DataRepository.dataItemFeature().ic()) {
                showSuperNightTip(i, i3);
            }
        }
    }

    public void parseMiAlgoAsdResult(MarshalQueryableASDScene.ASDScene[] aSDSceneArr) {
        if (aSDSceneArr != null && aSDSceneArr.length > 0) {
            FunctionMiAlgoASDEngine.LOGD("(NoneSemantics)scenes size:" + aSDSceneArr.length);
            for (MarshalQueryableASDScene.ASDScene aSDScene : aSDSceneArr) {
                FunctionMiAlgoASDEngine.LOGD("(NoneSemantics)-->" + aSDScene.toString());
                updateASDScene(aSDSceneArr[r1]);
            }
        }
    }
}
