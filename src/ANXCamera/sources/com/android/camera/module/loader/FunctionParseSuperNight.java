package com.android.camera.module.loader;

import android.hardware.camera2.CaptureResult;
import com.android.camera.log.Log;
import com.android.camera2.Camera2Proxy.SuperNightCallback;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.VendorTagHelper;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene.ASDScene;
import io.reactivex.functions.Function;
import java.lang.ref.WeakReference;

public class FunctionParseSuperNight implements Function<CaptureResult, CaptureResult> {
    public static final int SUPER_NIGHT = 3;
    public static final String TAG = "FunctionParseSuperNight";
    private boolean mIsSuperNight;
    private WeakReference<SuperNightCallback> mSuperNightCallback;

    public FunctionParseSuperNight(SuperNightCallback superNightCallback) {
        this.mSuperNightCallback = new WeakReference<>(superNightCallback);
    }

    private void updateASDScene(SuperNightCallback superNightCallback, ASDScene aSDScene) {
        int i = aSDScene.type;
        boolean z = (aSDScene.value & 256) != 0;
        if (i == 3 && z != this.mIsSuperNight) {
            this.mIsSuperNight = z;
            superNightCallback.onSuperNightChanged(this.mIsSuperNight);
        }
    }

    public CaptureResult apply(CaptureResult captureResult) throws Exception {
        SuperNightCallback superNightCallback = (SuperNightCallback) this.mSuperNightCallback.get();
        if (superNightCallback == null) {
            return captureResult;
        }
        if (!superNightCallback.isSupportSuperNight()) {
            this.mIsSuperNight = false;
            superNightCallback.onSuperNightChanged(false);
            return captureResult;
        }
        ASDScene[] aSDSceneArr = (ASDScene[]) VendorTagHelper.getValueQuietly(captureResult, CaptureResultVendorTags.NON_SEMANTIC_SCENE);
        String str = TAG;
        if (aSDSceneArr == null || aSDSceneArr.length <= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            sb.append(CaptureResultVendorTags.NON_SEMANTIC_SCENE.getName());
            sb.append(") asd scene result null!!!");
            Log.d(str, sb.toString());
            return captureResult;
        }
        for (ASDScene aSDScene : aSDSceneArr) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("(NoneSemantics)-->");
            sb2.append(aSDScene.toString());
            Log.d(str, sb2.toString());
            updateASDScene(superNightCallback, aSDScene);
        }
        return captureResult;
    }
}
