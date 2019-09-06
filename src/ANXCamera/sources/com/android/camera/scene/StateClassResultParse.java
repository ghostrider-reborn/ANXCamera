package com.android.camera.scene;

import com.android.camera.module.BaseModule;
import com.android.camera.module.Camera2Module;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene.ASDScene;
import java.lang.ref.WeakReference;

public class StateClassResultParse extends AbASDResultParse {
    public StateClassResultParse(WeakReference<BaseModule> weakReference) {
        super(weakReference);
    }

    private void updateASDScene(ASDScene aSDScene) {
        int i = aSDScene.type;
        int i2 = aSDScene.value;
        if (i == 4) {
            if (!MiAlgoAsdSceneProfile.isSceneChange(i, i2)) {
                FunctionMiAlgoASDEngine.LOGD("ON_TRIPOD scene no change");
                return;
            }
            ASDScene[] aSDSceneArr = {aSDScene};
            BaseModule baseModule = (BaseModule) this.mModule.get();
            if (baseModule != null && (baseModule instanceof Camera2Module)) {
                Camera2Module camera2Module = (Camera2Module) baseModule;
                camera2Module.setAsdScenes(aSDSceneArr);
                camera2Module.updatePreferenceInWorkThread(59);
            }
        }
    }

    public void parseMiAlgoAsdResult(ASDScene[] aSDSceneArr) {
        if (aSDSceneArr != null && aSDSceneArr.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("(StateClass)scenes size:");
            sb.append(aSDSceneArr.length);
            FunctionMiAlgoASDEngine.LOGD(sb.toString());
            for (ASDScene aSDScene : aSDSceneArr) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("(StateClass)->");
                sb2.append(aSDScene.toString());
                FunctionMiAlgoASDEngine.LOGD(sb2.toString());
                updateASDScene(aSDScene);
            }
        }
    }
}
