package com.android.camera.scene;

import android.hardware.camera2.CaptureResult;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.SparseArray;
import com.android.camera.CameraSettings;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.VendorTag;
import com.android.camera2.vendortag.VendorTagHelper;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import io.reactivex.functions.Function;
import java.lang.ref.WeakReference;

public class FunctionMiAlgoASDEngine implements Function<CaptureResult, CaptureResult> {
    public static final String TAG = "MI_ALGO_ASD_SCENE";
    private ModeProtocol.BottomPopupTips mBottomPopupTips;
    private boolean mIsMacroModeEnable;
    private WeakReference<BaseModule> mModule;
    private SparseArray<IResultParse> mResultParseList = new SparseArray<>();
    private SparseArray<VendorTag<CaptureResult.Key<MarshalQueryableASDScene.ASDScene[]>>> mVendorTagArray = new SparseArray<>();

    public FunctionMiAlgoASDEngine(BaseModule baseModule) {
        this.mVendorTagArray.put(0, CaptureResultVendorTags.SEMANTIC_SCENE);
        this.mVendorTagArray.put(1, CaptureResultVendorTags.NON_SEMANTIC_SCENE);
        this.mVendorTagArray.put(2, CaptureResultVendorTags.STATE_SCENE);
        this.mModule = new WeakReference<>(baseModule);
        this.mBottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (baseModule != null) {
            this.mIsMacroModeEnable = CameraSettings.isMacroModeEnabled(baseModule.getModuleIndex());
        }
    }

    public static void LOGD(String str) {
        if (!TextUtils.isEmpty(str) && SystemProperties.getBoolean(TAG, false)) {
            Log.d(TAG, str);
        }
    }

    private void parseCaptureResult(CaptureResult captureResult) {
        for (int i = 0; i < this.mVendorTagArray.size(); i++) {
            int keyAt = this.mVendorTagArray.keyAt(i);
            VendorTag valueAt = this.mVendorTagArray.valueAt(i);
            MarshalQueryableASDScene.ASDScene[] aSDSceneArr = (MarshalQueryableASDScene.ASDScene[]) VendorTagHelper.getValueQuietly(captureResult, valueAt);
            if (aSDSceneArr == null) {
                Log.d(TAG, "(" + valueAt.getName() + ") asd scene result null!!!");
            } else {
                parseMiAlgoASDSceneResult(keyAt, aSDSceneArr);
            }
        }
    }

    private void parseMiAlgoASDSceneResult(int i, MarshalQueryableASDScene.ASDScene[] aSDSceneArr) {
        IResultParse iResultParse = this.mResultParseList.get(i);
        if (i != 0) {
            if (i != 1) {
                if (i == 2 && iResultParse == null) {
                    iResultParse = new StateClassResultParse(this.mModule);
                }
            } else if (iResultParse == null) {
                iResultParse = new NoneSemanticsClassResultParse(this.mModule);
            }
        } else if (iResultParse == null) {
            iResultParse = new SemanticsClassResultParse(this.mModule);
        }
        if (iResultParse != null) {
            this.mResultParseList.put(i, iResultParse);
            iResultParse.parseMiAlgoAsdResult(aSDSceneArr);
        }
    }

    public CaptureResult apply(CaptureResult captureResult) {
        BaseModule baseModule = (BaseModule) this.mModule.get();
        if (baseModule == null) {
            return captureResult;
        }
        if (baseModule.getModuleIndex() != 163) {
            LOGD("no capture mode!");
            return captureResult;
        }
        ModeProtocol.BottomPopupTips bottomPopupTips = this.mBottomPopupTips;
        if (bottomPopupTips != null && bottomPopupTips.isQRTipVisible()) {
            LOGD("QR tip is visible!");
            return captureResult;
        } else if (this.mIsMacroModeEnable) {
            return captureResult;
        } else {
            CameraCapabilities cameraCapabilities = baseModule.getCameraCapabilities();
            if (cameraCapabilities == null) {
                return captureResult;
            }
            float miAlgoASDVersion = cameraCapabilities.getMiAlgoASDVersion();
            LOGD("mi algo asd version:" + miAlgoASDVersion);
            if (miAlgoASDVersion < 2.0f) {
                return captureResult;
            }
            parseCaptureResult(captureResult);
            return captureResult;
        }
    }
}
