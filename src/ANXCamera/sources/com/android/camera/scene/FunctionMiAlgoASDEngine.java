package com.android.camera.scene;

import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.CaptureResult.Key;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.SparseArray;
import com.android.camera.CameraSettings;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol.BottomPopupTips;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.VendorTag;
import com.android.camera2.vendortag.VendorTagHelper;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene.ASDScene;
import io.reactivex.functions.Function;
import java.lang.ref.WeakReference;

public class FunctionMiAlgoASDEngine implements Function<CaptureResult, CaptureResult> {
    public static final String TAG = "MI_ALGO_ASD_SCENE";
    private BottomPopupTips mBottomPopupTips;
    private boolean mIsMacroModeEnable;
    private WeakReference<BaseModule> mModule;
    private SparseArray<IResultParse> mResultParseList = new SparseArray<>();
    private SparseArray<VendorTag<Key<ASDScene[]>>> mVendorTagArray = new SparseArray<>();

    public FunctionMiAlgoASDEngine(BaseModule baseModule) {
        this.mVendorTagArray.put(0, CaptureResultVendorTags.SEMANTIC_SCENE);
        this.mVendorTagArray.put(1, CaptureResultVendorTags.NON_SEMANTIC_SCENE);
        this.mVendorTagArray.put(2, CaptureResultVendorTags.STATE_SCENE);
        this.mModule = new WeakReference<>(baseModule);
        this.mBottomPopupTips = (BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (baseModule != null) {
            this.mIsMacroModeEnable = CameraSettings.isMacroModeEnabled(baseModule.getModuleIndex());
        }
    }

    public static void LOGD(String str) {
        if (!TextUtils.isEmpty(str)) {
            String str2 = TAG;
            if (SystemProperties.getBoolean(str2, false)) {
                Log.d(str2, str);
            }
        }
    }

    private void parseCaptureResult(CaptureResult captureResult) {
        for (int i = 0; i < this.mVendorTagArray.size(); i++) {
            int keyAt = this.mVendorTagArray.keyAt(i);
            VendorTag vendorTag = (VendorTag) this.mVendorTagArray.valueAt(i);
            ASDScene[] aSDSceneArr = (ASDScene[]) VendorTagHelper.getValueQuietly(captureResult, vendorTag);
            if (aSDSceneArr == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("(");
                sb.append(vendorTag.getName());
                sb.append(") asd scene result null!!!");
                Log.d(TAG, sb.toString());
            } else {
                parseMiAlgoASDSceneResult(keyAt, aSDSceneArr);
            }
        }
    }

    private void parseMiAlgoASDSceneResult(int i, ASDScene[] aSDSceneArr) {
        IResultParse iResultParse = (IResultParse) this.mResultParseList.get(i);
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
        BottomPopupTips bottomPopupTips = this.mBottomPopupTips;
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
            StringBuilder sb = new StringBuilder();
            sb.append("mi algo asd version:");
            sb.append(miAlgoASDVersion);
            LOGD(sb.toString());
            if (miAlgoASDVersion < 2.0f) {
                return captureResult;
            }
            parseCaptureResult(captureResult);
            return captureResult;
        }
    }
}
