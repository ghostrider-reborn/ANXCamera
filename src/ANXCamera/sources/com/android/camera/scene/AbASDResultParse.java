package com.android.camera.scene;

import android.support.annotation.StringRes;
import com.android.camera.R;
import com.android.camera.module.BaseModule;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import java.lang.ref.WeakReference;

public abstract class AbASDResultParse implements IResultParse<MarshalQueryableASDScene.ASDScene[]> {
    protected final WeakReference<BaseModule> mModule;
    private final ModeProtocol.BottomPopupTips mTips = ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175));
    private ModeProtocol.TopAlert mTopAlert;

    public AbASDResultParse(WeakReference<BaseModule> weakReference) {
        this.mModule = weakReference;
    }

    public /* synthetic */ void Cd() {
        this.mTips.directlyHideTips();
    }

    public /* synthetic */ void a(int i, int i2, int i3) {
        this.mTips.showTips(i, i2, i3);
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
        baseModule.getHandler().post(new b(this));
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
            ModeProtocol.TopAlert topAlert = this.mTopAlert;
            if (topAlert == null || baseModule == null) {
                return false;
            }
            if (!topAlert.isCurrentRecommendTipText(baseModule.isFrontCamera() ? R.string.lens_dirty_detected_title_front : R.string.lens_dirty_detected_title_back)) {
                return false;
            }
            FunctionMiAlgoASDEngine.LOGD("dirty tip is visible!");
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
        baseModule.getHandler().post(new a(this, i, i2, i3));
        return true;
    }
}
