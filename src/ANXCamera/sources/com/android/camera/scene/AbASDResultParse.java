package com.android.camera.scene;

import android.support.annotation.StringRes;
import com.android.camera.R;
import com.android.camera.module.BaseModule;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import java.lang.ref.WeakReference;

public abstract class AbASDResultParse implements IResultParse<MarshalQueryableASDScene.ASDScene[]> {
    private ModeProtocol.DualController mDualController = ((ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182));
    protected final WeakReference<BaseModule> mModule;
    private final ModeProtocol.BottomPopupTips mTips = ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175));
    private ModeProtocol.TopAlert mTopAlert;

    public AbASDResultParse(WeakReference<BaseModule> weakReference) {
        this.mModule = weakReference;
    }

    /* access modifiers changed from: protected */
    public boolean closeTip() {
        if (this.mTips == null) {
            return false;
        }
        BaseModule baseModule = (BaseModule) this.mModule.get();
        if (baseModule == null) {
            return false;
        }
        baseModule.getHandler().post(new Runnable() {
            public final void run() {
                AbASDResultParse.this.mTips.directlyHideTips();
            }
        });
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isIntercept() {
        if (this.mTopAlert == null) {
            this.mTopAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        }
        BaseModule baseModule = (BaseModule) this.mModule.get();
        if (baseModule == null) {
            return true;
        }
        if (baseModule.getBogusCameraId() != 0) {
            FunctionMiAlgoASDEngine.LOGD("no back camera!");
            return true;
        } else if (1.0f != baseModule.getZoomRatio()) {
            FunctionMiAlgoASDEngine.LOGD("zoom > 1x!");
            return true;
        } else if (MiAlgoAsdSceneProfile.isAlreadyTip()) {
            FunctionMiAlgoASDEngine.LOGD("A tip has occurred this time.!");
            return true;
        } else {
            if (!(this.mTopAlert == null || baseModule == null)) {
                if (this.mTopAlert.isCurrentRecommendTipText(baseModule.isFrontCamera() ? R.string.lens_dirty_detected_title_front : R.string.lens_dirty_detected_title_back)) {
                    FunctionMiAlgoASDEngine.LOGD("dirty tip is visible!");
                    return true;
                }
            }
            if (!(this.mDualController != null && this.mDualController.isSlideVisible())) {
                return false;
            }
            FunctionMiAlgoASDEngine.LOGD("Zoom bar is in effect, no prompt！");
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public boolean showTip(int i, @StringRes int i2, int i3) {
        if (i2 <= 0 || this.mTips == null) {
            return false;
        }
        BaseModule baseModule = (BaseModule) this.mModule.get();
        if (baseModule == null) {
            return false;
        }
        ModeProtocol.CameraModuleSpecial cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195);
        if (cameraModuleSpecial != null) {
            cameraModuleSpecial.showOrHideChip(false);
        }
        baseModule.getHandler().post(new Runnable(i, i2, i3) {
            private final /* synthetic */ int f$1;
            private final /* synthetic */ int f$2;
            private final /* synthetic */ int f$3;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
            }

            public final void run() {
                AbASDResultParse.this.mTips.showTips(this.f$1, this.f$2, this.f$3);
            }
        });
        return true;
    }
}
