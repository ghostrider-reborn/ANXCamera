package com.android.camera.fragment.top;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.RotateDialogController;
import com.android.camera.Util;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.data.DataRepository;
import com.android.camera.effect.FilterInfo;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.Camera2OpenManager;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtil;
import com.android.camera.ui.ToggleSwitch;
import com.android.camera2.Camera2Proxy;
import com.mi.config.b;
import io.reactivex.Completable;
import java.util.ArrayList;
import java.util.List;

public class FragmentTopAlert extends BaseFragment implements View.OnClickListener {
    public static final int FRAGMENT_INFO = 253;
    public static final long HINT_DELAY_TIME = 2000;
    private static final String TAG = FragmentTopAlert.class.getSimpleName();
    private static int sPendingRecordingTimeState = 0;
    /* access modifiers changed from: private */
    public ToggleSwitch mAiSceneSelectView;
    private Runnable mAlertAiDetectTipHitRunable = new TopAlertRunnable() {
        /* access modifiers changed from: package-private */
        public void runToSafe() {
            FragmentTopAlert.this.removeViewToTipLayout(FragmentTopAlert.this.mRecommendTip);
        }
    };
    private long mAlertAiSceneSwitchHintTime;
    /* access modifiers changed from: private */
    public AlertDialog mAlertDialog;
    private int mAlertImageType = -1;
    private TextView mAlertRecordingText;
    private Runnable mAlertTopHintHideRunnable = new Runnable() {
        public void run() {
            FragmentTopAlert.this.removeViewToToastLayout(FragmentTopAlert.this.mPermanentTip);
        }
    };
    private AlphaAnimation mBlingAnimation;
    private LayoutTransition mCustomStubTransition;
    private LayoutTransition mCustomToastTransition;
    private Handler mHandler;
    private TextView mLiveMusiHintText;
    private ImageView mLiveMusicClose;
    private LinearLayout mLiveMusicHintLayout;
    private TextView mLyingDirectHintText;
    private TextView mManualParameterResetTip;
    /* access modifiers changed from: private */
    public TextView mPermanentTip;
    /* access modifiers changed from: private */
    public TextView mRecommendTip;
    private boolean mShow;
    private Runnable mShowAction = new TopAlertRunnable() {
        public void runToSafe() {
            FragmentTopAlert.this.addViewToTipLayout(FragmentTopAlert.this.mAiSceneSelectView, new LinearLayout.LayoutParams(-2, FragmentTopAlert.this.getResources().getDimensionPixelOffset(R.dimen.ai_scene_selector_layout_height)));
        }
    };
    private String mStateValueText = "";
    private boolean mStateValueTextFromLighting;
    private TextView mSubtitleTip;
    /* access modifiers changed from: private */
    public TextView mToastAiSwitchTip;
    private ImageView mToastTipFlashHDR;
    private LinearLayout mToastTopTipLayout;
    private int mTopHintTextResource = 0;
    private LinearLayout mTopTipLayout;
    private TextView mVideoUltraClearTip;
    public final Runnable mViewHideRunnable = new TopAlertRunnable() {
        /* access modifiers changed from: package-private */
        public void runToSafe() {
            FragmentTopAlert.this.removeViewToToastLayout(FragmentTopAlert.this.mToastAiSwitchTip);
        }
    };
    private TextView mZoomTip;

    private abstract class TopAlertRunnable implements Runnable {
        private TopAlertRunnable() {
        }

        public void run() {
            if (FragmentTopAlert.this.isAdded()) {
                runToSafe();
            }
        }

        /* access modifiers changed from: package-private */
        public abstract void runToSafe();
    }

    private void addViewToTipLayout(View view) {
        addViewToTipLayout(view, (LinearLayout.LayoutParams) null);
    }

    /* access modifiers changed from: private */
    public void addViewToTipLayout(View view, LinearLayout.LayoutParams layoutParams) {
        if (view != null && this.mTopTipLayout != null && this.mTopTipLayout.indexOfChild(view) == -1) {
            if (this.mTopTipLayout.getLayoutTransition() != customStubViewTransition()) {
                this.mTopTipLayout.setLayoutTransition(customStubViewTransition());
            }
            try {
                this.mTopTipLayout.addView(view);
            } catch (Exception e) {
                Log.w(TAG, "The specified child already has a parent. You must call removeView() on the child's parent first");
            }
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(-2, -2);
            }
            view.setLayoutParams(layoutParams);
        }
    }

    private void addViewToTipLayout(View view, LinearLayout.LayoutParams layoutParams, Runnable runnable, Runnable runnable2) {
        if (view != null && this.mTopTipLayout != null && this.mTopTipLayout.indexOfChild(view) == -1) {
            if (this.mTopTipLayout.getLayoutTransition() != customStubViewTransition()) {
                this.mTopTipLayout.setLayoutTransition(customStubViewTransition());
            }
            if (runnable != null) {
                runnable.run();
            }
            try {
                this.mTopTipLayout.addView(view);
            } catch (IllegalStateException e) {
                Log.w(TAG, "The specified child already has a parent. You must call removeView() on the child's parent first");
            }
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(-2, -2);
            }
            view.setLayoutParams(layoutParams);
            if (runnable2 != null) {
                runnable2.run();
            }
        }
    }

    private void addViewToTipLayout(View view, Runnable runnable, Runnable runnable2) {
        addViewToTipLayout(view, (LinearLayout.LayoutParams) null, runnable, runnable2);
    }

    private void addViewToToastLayout(View view) {
        addViewToToastLayout(view, -1);
    }

    private void addViewToToastLayout(View view, int i) {
        if (view != null && this.mToastTopTipLayout != null && this.mToastTopTipLayout.indexOfChild(view) == -1) {
            if (this.mTopTipLayout.getLayoutTransition() != customStubViewTransition()) {
                this.mTopTipLayout.setLayoutTransition(customStubViewTransition());
            }
            if (i < 0) {
                this.mToastTopTipLayout.addView(view);
            } else {
                this.mToastTopTipLayout.addView(view, i);
            }
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.width = -2;
            layoutParams.height = -2;
            view.setLayoutParams(layoutParams);
            if (this.mToastTopTipLayout.getChildCount() > 0) {
                this.mToastTopTipLayout.setVisibility(0);
            }
        }
    }

    private void alertAiSceneSelector(String str, String str2, int i, ToggleSwitch.OnCheckedChangeListener onCheckedChangeListener, boolean z) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            this.mAiSceneSelectView.setTextOnAndOff(str, str2);
        }
        if (i == 0) {
            long currentTimeMillis = HINT_DELAY_TIME - (System.currentTimeMillis() - this.mAlertAiSceneSwitchHintTime);
            if (CameraSettings.getShaderEffect() == FilterInfo.FILTER_ID_NONE) {
                Handler handler = this.mHandler;
                Runnable runnable = this.mShowAction;
                if (currentTimeMillis < 0) {
                    currentTimeMillis = 0;
                }
                handler.postDelayed(runnable, currentTimeMillis);
            }
        } else {
            this.mTopTipLayout.removeCallbacks(this.mShowAction);
            removeViewToTipLayout(this.mAiSceneSelectView);
        }
        this.mAiSceneSelectView.setOnCheckedChangeListener(onCheckedChangeListener);
        this.mAiSceneSelectView.setChecked(z);
    }

    private LayoutTransition customStubViewTransition() {
        if (this.mCustomStubTransition == null) {
            this.mCustomStubTransition = new LayoutTransition();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat((Object) null, "alpha", new float[]{0.0f, 1.0f});
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat((Object) null, "alpha", new float[]{1.0f, 0.0f});
            this.mCustomStubTransition.setStartDelay(2, 0);
            this.mCustomStubTransition.setDuration(2, 250);
            this.mCustomStubTransition.setAnimator(2, ofFloat);
            this.mCustomStubTransition.setStartDelay(3, 0);
            this.mCustomStubTransition.setDuration(3, 10);
            this.mCustomStubTransition.setAnimator(3, ofFloat2);
        }
        return this.mCustomStubTransition;
    }

    private LayoutTransition customToastLayoutTransition() {
        if (this.mCustomToastTransition == null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat((Object) null, "alpha", new float[]{0.0f, 1.0f});
            this.mCustomToastTransition = new LayoutTransition();
            this.mCustomToastTransition.setStartDelay(2, 0);
            this.mCustomToastTransition.setDuration(2, 250);
            this.mCustomToastTransition.setAnimator(2, ofFloat);
            this.mCustomToastTransition.setStartDelay(3, 0);
            this.mCustomToastTransition.setDuration(3, 10);
        }
        return this.mCustomToastTransition;
    }

    private int getAlertTopMargin() {
        return Util.getDisplayRect(getContext(), DataRepository.dataItemGlobal().getDisplayMode() == 2 ? 1 : 0).top + getResources().getDimensionPixelSize(R.dimen.top_tip_margin_top);
    }

    private String getZoomRatioTipText(boolean z) {
        float decimal = HybridZoomingSystem.toDecimal(CameraSettings.readZoom());
        Camera2Proxy currentCamera2Device = Camera2OpenManager.getInstance().getCurrentCamera2Device();
        if (currentCamera2Device == null) {
            return null;
        }
        int id = currentCamera2Device.getId();
        if (decimal == 1.0f) {
            if (id == Camera2DataContainer.getInstance().getMainBackCameraId() || id == Camera2DataContainer.getInstance().getSATCameraId() || id == Camera2DataContainer.getInstance().getBokehCameraId() || id == Camera2DataContainer.getInstance().getUltraWideBokehCameraId() || id == Camera2DataContainer.getInstance().getFrontCameraId() || id == Camera2DataContainer.getInstance().getBokehFrontCameraId() || id == Camera2DataContainer.getInstance().getStandaloneMacroCameraId()) {
                return null;
            }
            if (((HybridZoomingSystem.IS_2_SAT || (!HybridZoomingSystem.IS_3_OR_MORE_SAT && !CameraSettings.isSupportedOpticalZoom())) && id == Camera2DataContainer.getInstance().getUltraWideCameraId()) || this.mCurrentMode == 167 || this.mCurrentMode == 166 || this.mCurrentMode == 179) {
                return null;
            }
            if (DataRepository.dataItemFeature().hZ() && this.mCurrentMode == 172) {
                return null;
            }
            if (DataRepository.dataItemFeature().hH() && id == Camera2DataContainer.getInstance().getUltraWideCameraId() && z) {
                return null;
            }
        }
        if (decimal == 0.6f && HybridZoomingSystem.IS_3_OR_MORE_SAT && id == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            return null;
        }
        if (id == Camera2DataContainer.getInstance().getAuxCameraId() && decimal <= 2.0f && this.mCurrentMode != 167) {
            return null;
        }
        return "x " + decimal;
    }

    private void initHandler() {
        this.mHandler = new Handler();
    }

    private TextView initHorizonDirectTipText() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.top_tip_lying_direct_hint_layout, (ViewGroup) null);
    }

    private LinearLayout initMusicTipText() {
        return (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.top_tip_music_layout, (ViewGroup) null);
    }

    private TextView initPermanentTip() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.permanent_top_tip_layout, (ViewGroup) null);
    }

    private TextView initRecommendTipText() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.recommend_top_tip_layout, (ViewGroup) null);
    }

    private TextView initSubtitleTip() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.subtitle_top_tip_layout, (ViewGroup) null);
    }

    private void initToastTipLayout() {
        setToastTipLayoutParams();
        this.mToastTopTipLayout.setVisibility(8);
        this.mToastTipFlashHDR = initToastTopTipImage();
        this.mToastAiSwitchTip = initToastTopTipText();
    }

    private ImageView initToastTopTipImage() {
        return (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.toast_top_tip_image_layout, (ViewGroup) null);
    }

    private TextView initToastTopTipText() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.toast_top_tip_text_layout, (ViewGroup) null);
    }

    private ToggleSwitch initTopTipToggleSwitch() {
        return (ToggleSwitch) LayoutInflater.from(getContext()).inflate(R.layout.top_tip_toggleswitch_layout, (ViewGroup) null);
    }

    private TextView initZoomTipText() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.zoom_top_tip_layout, (ViewGroup) null);
    }

    static /* synthetic */ void lambda$alertAiSceneSelector$1(ToggleSwitch toggleSwitch, boolean z) {
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (z) {
            if (configChanges != null) {
                configChanges.onConfigChanged(248);
            }
        } else if (configChanges != null) {
            configChanges.onConfigChanged(249);
        }
    }

    static /* synthetic */ void lambda$alertMoonSelector$2(ToggleSwitch toggleSwitch, boolean z) {
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (z) {
            if (configChanges != null) {
                configChanges.onConfigChanged(246);
            }
        } else if (configChanges != null) {
            configChanges.onConfigChanged(247);
        }
    }

    public static /* synthetic */ void lambda$alertSwitchHint$3(FragmentTopAlert fragmentTopAlert) {
        if (fragmentTopAlert.isAdded()) {
            fragmentTopAlert.mToastAiSwitchTip.sendAccessibilityEvent(32768);
        }
    }

    public static /* synthetic */ void lambda$showCloseConfirm$6(FragmentTopAlert fragmentTopAlert, DialogInterface dialogInterface, int i) {
        fragmentTopAlert.mAlertDialog = null;
        fragmentTopAlert.removeViewToTipLayout(fragmentTopAlert.mLiveMusicHintLayout);
        ModeProtocol.LiveConfigChanges liveConfigChanges = (ModeProtocol.LiveConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(201);
        if (liveConfigChanges != null && !liveConfigChanges.isRecording() && !liveConfigChanges.isRecordingPaused()) {
            liveConfigChanges.closeBGM();
            ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(245);
        }
    }

    /* access modifiers changed from: private */
    public void removeViewToTipLayout(View view) {
        if (view != null && this.mTopTipLayout != null && this.mTopTipLayout.indexOfChild(view) != -1) {
            if (this.mTopTipLayout.getLayoutTransition() != customStubViewTransition()) {
                this.mTopTipLayout.setLayoutTransition(customStubViewTransition());
            }
            this.mTopTipLayout.removeView(view);
            if (this.mTopTipLayout.getChildCount() <= 0) {
                this.mTopTipLayout.removeAllViews();
            }
        }
    }

    /* access modifiers changed from: private */
    public void removeViewToToastLayout(View view) {
        if (view != null && this.mToastTopTipLayout != null && this.mToastTopTipLayout.indexOfChild(view) != -1) {
            if (this.mTopTipLayout.getLayoutTransition() != customStubViewTransition()) {
                this.mTopTipLayout.setLayoutTransition(customStubViewTransition());
            }
            this.mToastTopTipLayout.removeView(view);
            if (this.mToastTopTipLayout.getChildCount() <= 0) {
                this.mToastTopTipLayout.removeAllViews();
                setToastTipLayoutParams();
                this.mToastTopTipLayout.setVisibility(8);
            }
        }
    }

    public static void setPendingRecordingState(int i) {
        sPendingRecordingTimeState = i;
    }

    private void setToastTipLayoutParams() {
        if (this.mToastTopTipLayout != null) {
            this.mToastTopTipLayout.setDividerDrawable(getResources().getDrawable(R.drawable.top_tip_horizontal_divider));
            this.mToastTopTipLayout.setShowDividers(2);
            this.mToastTopTipLayout.setLayoutTransition(customToastLayoutTransition());
            this.mToastTopTipLayout.setGravity(17);
        }
    }

    private void setViewMargin(View view, int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.topMargin = i;
        view.setLayoutParams(marginLayoutParams);
        ViewCompat.setTranslationY(view, 0.0f);
    }

    /* access modifiers changed from: private */
    public void showCloseConfirm() {
        if (this.mAlertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            this.mAlertDialog = builder.create();
            builder.setMessage(R.string.live_music_close_message);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.live_music_close_sure_message, new DialogInterface.OnClickListener() {
                public final void onClick(DialogInterface dialogInterface, int i) {
                    FragmentTopAlert.lambda$showCloseConfirm$6(FragmentTopAlert.this, dialogInterface, i);
                }
            });
            builder.setNegativeButton(R.string.live_music_close_cancel_message, new DialogInterface.OnClickListener() {
                public final void onClick(DialogInterface dialogInterface, int i) {
                    FragmentTopAlert.this.mAlertDialog = null;
                }
            });
            builder.show();
        }
    }

    private void showManualParameterResetDialog() {
        if (this.mAlertDialog == null) {
            this.mAlertDialog = RotateDialogController.showSystemAlertDialog(getContext(), (String) null, getString(R.string.confirm_reset_manually_parameter_message), getString(R.string.reset_manually_parameter_confirmed), new Runnable() {
                public void run() {
                    AlertDialog unused = FragmentTopAlert.this.mAlertDialog = null;
                    ModeProtocol.ManuallyAdjust manuallyAdjust = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181);
                    if (manuallyAdjust != null) {
                        manuallyAdjust.resetManually();
                    }
                    ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                    if (configChanges != null) {
                        configChanges.resetMeter();
                    }
                    FragmentTopAlert.this.alertParameterResetTip(false, 8, 0, 0, false);
                    CameraStatUtil.trackManuallyResetDialogOk();
                }
            }, getString(17039360), new Runnable() {
                public void run() {
                    CameraStatUtil.trackManuallyResetDialogCancel();
                    AlertDialog unused = FragmentTopAlert.this.mAlertDialog = null;
                }
            });
        }
    }

    private void updateAlertStatusView(boolean z) {
        if (!DataRepository.dataItemRunning().getComponentRunningLighting().getComponentValue(171).equals("0")) {
            if (isLandScape()) {
                starAnimatetViewGone(this.mZoomTip, z);
            } else if (!TextUtils.isEmpty(this.mStateValueText)) {
                startAnimateViewVisible(this.mZoomTip, z);
            }
        }
    }

    private void updateStateText(boolean z) {
        if (!TextUtils.isEmpty(this.mStateValueText)) {
            this.mStateValueTextFromLighting = z;
            this.mZoomTip.setText(this.mStateValueText);
            if (this.mTopTipLayout.indexOfChild(this.mZoomTip) != -1 && this.mZoomTip.getVisibility() == 0) {
                return;
            }
            if (!z || !isLandScape()) {
                addViewToTipLayout(this.mZoomTip);
                return;
            }
            return;
        }
        this.mStateValueTextFromLighting = false;
        removeViewToTipLayout(this.mZoomTip);
    }

    private void updateTopHint(int i, String str, long j) {
        this.mHandler.removeCallbacks(this.mAlertTopHintHideRunnable);
        if (i == 0) {
            this.mPermanentTip.setText(str);
            this.mPermanentTip.setContentDescription(str);
            addViewToToastLayout(this.mPermanentTip);
            if (j > 0) {
                this.mHandler.postDelayed(this.mAlertTopHintHideRunnable, j);
                return;
            }
            return;
        }
        removeViewToToastLayout(this.mPermanentTip);
    }

    public void alertAiSceneSelector(int i) {
        alertAiSceneSelector(getResources().getString(R.string.text_ai_scene_selector_text_on), getResources().getString(R.string.text_ai_scene_selector_text_off), i, i == 0 ? $$Lambda$FragmentTopAlert$wBjfYLjxrFdyaQ4y4rxtK6qEog.INSTANCE : null, false);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0046 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0047  */
    public void alertFlash(int i, String str) {
        boolean z;
        int i2 = 2;
        if (i == 0) {
            int hashCode = str.hashCode();
            if (hashCode != 53) {
                switch (hashCode) {
                    case 49:
                        if (str.equals("1")) {
                            z = false;
                            break;
                        }
                    case 50:
                        if (str.equals("2")) {
                            z = true;
                            break;
                        }
                }
            } else if (str.equals("5")) {
                z = true;
                switch (z) {
                    case false:
                        i2 = 1;
                        break;
                    case true:
                        break;
                    case true:
                        i2 = 5;
                        break;
                    default:
                        i2 = -1;
                        break;
                }
                if (this.mAlertImageType == i2) {
                    this.mAlertImageType = i2;
                    if (CameraSettings.isFrontCamera() && b.kf()) {
                        i2 = 1;
                    }
                    if (i2 != 5) {
                        switch (i2) {
                            case 1:
                                this.mToastTipFlashHDR.setImageResource(R.drawable.ic_alert_flash);
                                addViewToToastLayout(this.mToastTipFlashHDR, 0);
                                return;
                            case 2:
                                this.mToastTipFlashHDR.setImageResource(R.drawable.ic_alert_flash_torch);
                                addViewToToastLayout(this.mToastTipFlashHDR, 0);
                                return;
                            default:
                                removeViewToToastLayout(this.mToastTipFlashHDR);
                                return;
                        }
                    } else {
                        this.mToastTipFlashHDR.setImageResource(R.drawable.ic_alert_flash_back_soft_light);
                        addViewToToastLayout(this.mToastTipFlashHDR, 0);
                        return;
                    }
                } else {
                    return;
                }
            }
            z = true;
            switch (z) {
                case false:
                    break;
                case true:
                    break;
                case true:
                    break;
            }
            if (this.mAlertImageType == i2) {
            }
        } else if (this.mAlertImageType == 2 || this.mAlertImageType == 1 || this.mAlertImageType == 5) {
            this.mAlertImageType = -1;
            removeViewToToastLayout(this.mToastTipFlashHDR);
        }
    }

    public void alertHDR(int i, boolean z) {
        int i2 = 3;
        if (i == 0) {
            if (z) {
                i2 = 4;
            }
            if (this.mAlertImageType != i2) {
                this.mAlertImageType = i2;
                this.mToastTipFlashHDR.setImageResource(z ? R.drawable.ic_alert_hdr_live : R.drawable.ic_alert_hdr);
                addViewToToastLayout(this.mToastTipFlashHDR, 0);
            }
        } else if (this.mAlertImageType == 4 || this.mAlertImageType == 3) {
            this.mAlertImageType = -1;
            removeViewToToastLayout(this.mToastTipFlashHDR);
        }
    }

    public void alertLightingHint(int i) {
        int i2;
        if (i != -1) {
            switch (i) {
                case 3:
                    i2 = R.string.lighting_hint_too_close;
                    break;
                case 4:
                    i2 = R.string.lighting_hint_too_far;
                    break;
                case 5:
                    i2 = R.string.lighting_hint_needed;
                    break;
            }
        }
        i2 = -1;
        if (i2 == -1) {
            this.mStateValueText = "";
        } else {
            this.mStateValueText = getResources().getString(i2);
        }
        updateStateText(true);
    }

    public void alertLightingTitle(boolean z) {
        if (z) {
            this.mToastAiSwitchTip.setText(R.string.lighting_hint_title);
            this.mToastAiSwitchTip.setVisibility(0);
            addViewToToastLayout(this.mToastAiSwitchTip);
            this.mTopTipLayout.removeCallbacks(this.mViewHideRunnable);
            this.mHandler.postDelayed(this.mViewHideRunnable, HINT_DELAY_TIME);
        } else if (this.mToastAiSwitchTip.getVisibility() != 8) {
            this.mTopTipLayout.removeCallbacks(this.mViewHideRunnable);
            removeViewToToastLayout(this.mToastAiSwitchTip);
        }
    }

    public void alertMimojiFaceDetect(boolean z, int i) {
        if (z) {
            this.mToastAiSwitchTip.setText(i);
            this.mToastAiSwitchTip.setVisibility(0);
            addViewToToastLayout(this.mToastAiSwitchTip);
        } else if (this.mToastAiSwitchTip.getVisibility() != 8) {
            this.mTopTipLayout.removeCallbacks(this.mViewHideRunnable);
            removeViewToToastLayout(this.mToastAiSwitchTip);
        }
    }

    public void alertMoonSelector(String str, String str2, int i, boolean z) {
        alertAiSceneSelector(str, str2, i, i == 0 ? $$Lambda$FragmentTopAlert$XNMEcQFO7bMMo3Q5uQLIGAKRGw.INSTANCE : null, z);
    }

    public void alertMusicClose(boolean z) {
        if (this.mLiveMusicClose == null) {
            return;
        }
        if (z) {
            this.mLiveMusicClose.setAlpha(1.0f);
            this.mLiveMusicClose.setClickable(true);
            return;
        }
        this.mLiveMusicClose.setAlpha(0.4f);
        this.mLiveMusicClose.setClickable(false);
    }

    public void alertMusicTip(int i, String str) {
        if (i != 0 || TextUtils.isEmpty(str)) {
            removeViewToTipLayout(this.mLiveMusicHintLayout);
            return;
        }
        this.mLiveMusiHintText.setText(str);
        addViewToTipLayout(this.mLiveMusicHintLayout);
    }

    public void alertParameterResetTip(boolean z, int i, @StringRes int i2, int i3, boolean z2) {
        if (this.mManualParameterResetTip.getVisibility() != i) {
            if (i != 0 || !z2) {
                this.mManualParameterResetTip.setVisibility(i);
            } else {
                this.mManualParameterResetTip.setVisibility(i);
                ViewCompat.setAlpha(this.mManualParameterResetTip, 0.0f);
                ViewCompat.animate(this.mManualParameterResetTip).alpha(1.0f).setDuration(320).start();
            }
            if (i == 0) {
                setViewMargin(this.mManualParameterResetTip, i3);
                String string = getString(i2);
                if (Util.isEnglishOrNum(string)) {
                    this.mManualParameterResetTip.setTextSize(0, (float) getResources().getDimensionPixelSize(R.dimen.top_left_english_tip_size));
                } else {
                    this.mManualParameterResetTip.setTextSize(0, (float) getResources().getDimensionPixelSize(R.dimen.top_left_chinese_tip_size));
                }
                this.mManualParameterResetTip.setText(i2);
                this.mManualParameterResetTip.setContentDescription(string);
                if (!z) {
                    CameraStatUtil.trackManuallyResetShow();
                }
            }
        }
    }

    public void alertRecommendTipHint(int i, @StringRes int i2, long j) {
        if (i == 0) {
            this.mRecommendTip.setText(i2);
            this.mRecommendTip.setContentDescription(getString(i2));
            this.mTopTipLayout.removeCallbacks(this.mAlertAiDetectTipHitRunable);
            addViewToTipLayout(this.mRecommendTip);
            if (j >= 0) {
                this.mHandler.postDelayed(this.mAlertAiDetectTipHitRunable, j);
                return;
            }
            return;
        }
        this.mTopTipLayout.removeCallbacks(this.mAlertAiDetectTipHitRunable);
        removeViewToTipLayout(this.mRecommendTip);
    }

    public void alertSubtitleHint(int i, int i2) {
        if (i == 0) {
            this.mSubtitleTip.setText(getString(i2));
            this.mSubtitleTip.setContentDescription(getString(i2));
            addViewToToastLayout(this.mSubtitleTip);
            return;
        }
        removeViewToToastLayout(this.mSubtitleTip);
    }

    public void alertSwitchHint(int i, @StringRes int i2) {
        alertSwitchHint(i, getString(i2));
    }

    public void alertSwitchHint(int i, String str) {
        this.mToastAiSwitchTip.setTag(Integer.valueOf(i));
        this.mToastAiSwitchTip.setText(str);
        this.mToastAiSwitchTip.setContentDescription(str);
        addViewToToastLayout(this.mToastAiSwitchTip);
        this.mAlertAiSceneSwitchHintTime = System.currentTimeMillis();
        this.mHandler.postDelayed(new Runnable() {
            public final void run() {
                FragmentTopAlert.lambda$alertSwitchHint$3(FragmentTopAlert.this);
            }
        }, 300);
        this.mHandler.removeCallbacks(this.mViewHideRunnable);
        this.mHandler.postDelayed(this.mViewHideRunnable, HINT_DELAY_TIME);
    }

    public void alertTopHint(int i, @StringRes int i2) {
        alertTopHint(i, i2, 0);
    }

    public void alertTopHint(int i, @StringRes int i2, long j) {
        if (i2 > 0 && i == 0) {
            this.mTopHintTextResource = i2;
        } else if (i == 8) {
            this.mTopHintTextResource = 0;
        }
        String str = null;
        if (this.mTopHintTextResource == 0) {
            i = 8;
        } else {
            str = getString(i2);
        }
        updateTopHint(i, str, j);
    }

    public void alertTopHint(int i, String str) {
        if (TextUtils.isEmpty(str) && i == 0) {
            i = 8;
        }
        updateTopHint(i, str, 0);
    }

    public void alertUpdateValue(int i) {
        this.mStateValueText = "";
        boolean isMacroModeEnabled = CameraSettings.isMacroModeEnabled(this.mCurrentMode);
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController == null || !dualController.isZoomVisible() || isMacroModeEnabled) {
            String zoomRatioTipText = getZoomRatioTipText(isMacroModeEnabled);
            if (zoomRatioTipText != null) {
                this.mStateValueText += zoomRatioTipText;
            }
            updateStateText(false);
        }
    }

    public void alertVideoUltraClear(int i, @StringRes int i2, int i3, boolean z) {
        if (this.mVideoUltraClearTip.getVisibility() != i) {
            if (i != 0 || !z) {
                this.mVideoUltraClearTip.setVisibility(i);
            } else {
                this.mVideoUltraClearTip.setVisibility(i);
                ViewCompat.setAlpha(this.mVideoUltraClearTip, 0.0f);
                ViewCompat.animate(this.mVideoUltraClearTip).alpha(1.0f).setDuration(320).start();
            }
            setViewMargin(this.mVideoUltraClearTip, i3);
            if (i == 0) {
                String string = getString(i2);
                if (Util.isEnglishOrNum(string)) {
                    this.mVideoUltraClearTip.setTextSize(0, (float) getResources().getDimensionPixelSize(R.dimen.top_left_english_tip_size));
                } else {
                    this.mVideoUltraClearTip.setTextSize(0, (float) getResources().getDimensionPixelSize(R.dimen.top_left_chinese_tip_size));
                }
                this.mVideoUltraClearTip.setText(i2);
                this.mVideoUltraClearTip.setContentDescription(string);
            }
        }
    }

    public void clear(boolean z) {
        clearAlertStatus();
        this.mAlertImageType = -1;
        int childCount = this.mToastTopTipLayout.getChildCount();
        ArrayList<View> arrayList = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mToastTopTipLayout.getChildAt(i);
            Object tag = childAt.getTag();
            if (tag == null || !(tag == null || !(tag instanceof Integer) || ((Integer) tag).intValue() == 2)) {
                arrayList.add(childAt);
            }
            if (z) {
                arrayList.add(childAt);
            }
        }
        for (View removeView : arrayList) {
            this.mToastTopTipLayout.removeView(removeView);
        }
        if (this.mToastTopTipLayout.getChildCount() <= 0) {
            this.mToastTopTipLayout.removeAllViews();
            setToastTipLayoutParams();
            this.mToastTopTipLayout.setVisibility(8);
        }
        arrayList.clear();
        int childCount2 = this.mTopTipLayout.getChildCount();
        for (int i2 = 0; i2 < childCount2; i2++) {
            View childAt2 = this.mTopTipLayout.getChildAt(i2);
            if (i2 != 0) {
                arrayList.add(childAt2);
            } else {
                setToastTipLayoutParams();
            }
        }
        for (View removeView2 : arrayList) {
            this.mTopTipLayout.removeView(removeView2);
        }
        clearVideoUltraClear();
        if (this.mManualParameterResetTip != null && this.mManualParameterResetTip.getVisibility() != 8) {
            this.mManualParameterResetTip.setVisibility(8);
        }
    }

    public void clearAlertStatus() {
        this.mStateValueText = "";
        this.mStateValueTextFromLighting = false;
        removeViewToTipLayout(this.mZoomTip);
    }

    public void clearVideoUltraClear() {
        if (this.mVideoUltraClearTip != null && this.mVideoUltraClearTip.getVisibility() != 8) {
            this.mVideoUltraClearTip.setVisibility(8);
        }
    }

    public int getFragmentInto() {
        return 253;
    }

    /* access modifiers changed from: protected */
    public int getLayoutResourceId() {
        return R.layout.fragment_top_alert;
    }

    public void hideSwitchHint() {
        removeViewToToastLayout(this.mToastAiSwitchTip);
        if (this.mTopTipLayout.getVisibility() == 0) {
            this.mTopTipLayout.removeCallbacks(this.mViewHideRunnable);
        }
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        initHandler();
        this.mAlertRecordingText = (TextView) view.findViewById(R.id.alert_recording_time_view);
        this.mVideoUltraClearTip = (TextView) view.findViewById(R.id.video_ultra_clear_tip);
        this.mManualParameterResetTip = (TextView) view.findViewById(R.id.reset_manually_parameter_tip);
        this.mManualParameterResetTip.setOnClickListener(this);
        if (Util.isNotchDevice) {
            setViewMargin(this.mAlertRecordingText, Util.sStatusBarHeight);
        }
        ViewCompat.setAlpha(this.mAlertRecordingText, 0.0f);
        if (sPendingRecordingTimeState != 0) {
            setRecordingTimeState(sPendingRecordingTimeState);
            setPendingRecordingState(0);
        }
        this.mTopTipLayout = (LinearLayout) view.findViewById(R.id.top_tip_layout);
        this.mToastTopTipLayout = (LinearLayout) this.mTopTipLayout.findViewById(R.id.top_toast_layout);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mTopTipLayout.getLayoutParams();
        marginLayoutParams.topMargin = getAlertTopMargin();
        this.mTopTipLayout.setLayoutParams(marginLayoutParams);
        this.mTopTipLayout.setDividerDrawable(getResources().getDrawable(R.drawable.top_tip_vertical_divider));
        this.mTopTipLayout.setShowDividers(2);
        initToastTipLayout();
        this.mAiSceneSelectView = initTopTipToggleSwitch();
        this.mRecommendTip = initRecommendTipText();
        this.mZoomTip = initZoomTipText();
        updateAlertStatusView(false);
        this.mLiveMusicHintLayout = initMusicTipText();
        this.mLiveMusiHintText = (TextView) this.mLiveMusicHintLayout.findViewById(R.id.live_music_title_hint);
        this.mLiveMusicClose = (ImageView) this.mLiveMusicHintLayout.findViewById(R.id.live_music_close);
        this.mLiveMusicClose.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                FragmentTopAlert.this.showCloseConfirm();
            }
        });
        this.mPermanentTip = initPermanentTip();
        this.mSubtitleTip = initSubtitleTip();
        this.mLyingDirectHintText = initHorizonDirectTipText();
    }

    public boolean isContainAlertRecommendTip(@StringRes int... iArr) {
        if (this.mTopTipLayout == null || this.mRecommendTip == null || this.mTopTipLayout.indexOfChild(this.mRecommendTip) == -1 || iArr == null || iArr.length <= 0) {
            return false;
        }
        for (int i : iArr) {
            if (i > 0 && getResources().getString(i).equals(this.mRecommendTip.getText())) {
                return true;
            }
        }
        return false;
    }

    public boolean isCurrentRecommendTipText(int i) {
        if (i <= 0) {
            return false;
        }
        String string = getResources().getString(i);
        return !TextUtils.isEmpty(string) && isShowTopLayoutSpecifyTip(this.mRecommendTip) && string.equals(this.mRecommendTip.getText());
    }

    public boolean isShow() {
        return this.mShow;
    }

    public boolean isShowBacklightSelector() {
        return (this.mTopTipLayout == null || this.mTopTipLayout.indexOfChild(this.mAiSceneSelectView) == -1 || !getResources().getString(R.string.text_ai_scene_selector_text_on).equals(this.mAiSceneSelectView.getTextOn())) ? false : true;
    }

    public boolean isShowTopLayoutSpecifyTip(View view) {
        return (view == null || this.mTopTipLayout == null || this.mTopTipLayout.indexOfChild(view) == -1) ? false : true;
    }

    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.reInitAlert(false);
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.reset_manually_parameter_tip) {
            ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            if (cameraAction == null || !cameraAction.isDoingAction()) {
                CameraStatUtil.trackManuallyResetClick();
                showManualParameterResetDialog();
            }
        }
    }

    public void onStop() {
        super.onStop();
        if (this.mAlertDialog != null) {
            this.mAlertDialog.dismiss();
            this.mAlertDialog = null;
        }
        if (this.mHandler != null) {
            this.mHandler.removeCallbacksAndMessages((Object) null);
        }
        if (this.mBlingAnimation != null) {
            this.mBlingAnimation.cancel();
            this.mBlingAnimation = null;
        }
    }

    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        int i3 = this.mCurrentMode;
        super.provideAnimateElement(i, list, i2);
        clear((i3 == i || (i3 == 163 && i == 165) || (i3 == 165 && i == 163)) ? false : true);
        setShow(true);
    }

    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        updateAlertStatusView(true);
    }

    public void setRecordingTimeState(int i) {
        switch (i) {
            case 1:
                int i2 = this.mCurrentMode;
                if (!(i2 == 169 || i2 == 172)) {
                    if (i2 != 177) {
                        switch (i2) {
                            case 161:
                                break;
                            case 162:
                                break;
                        }
                    }
                    this.mAlertRecordingText.setText("00:15");
                    Completable.create(new AlphaInOnSubscribe(this.mAlertRecordingText)).subscribe();
                    return;
                }
                this.mAlertRecordingText.setText("00:00");
                Completable.create(new AlphaInOnSubscribe(this.mAlertRecordingText)).subscribe();
                return;
            case 2:
                if (this.mBlingAnimation != null) {
                    this.mBlingAnimation.cancel();
                }
                Completable.create(new AlphaOutOnSubscribe(this.mAlertRecordingText)).subscribe();
                return;
            case 3:
                if (this.mBlingAnimation == null) {
                    this.mBlingAnimation = new AlphaAnimation(1.0f, 0.0f);
                    this.mBlingAnimation.setDuration(400);
                    this.mBlingAnimation.setStartOffset(100);
                    this.mBlingAnimation.setInterpolator(new DecelerateInterpolator());
                    this.mBlingAnimation.setRepeatMode(2);
                    this.mBlingAnimation.setRepeatCount(-1);
                }
                this.mAlertRecordingText.startAnimation(this.mBlingAnimation);
                return;
            case 4:
                this.mBlingAnimation.cancel();
                return;
            default:
                return;
        }
    }

    public void setShow(boolean z) {
        this.mShow = z;
    }

    public void updateLyingDirectHint(boolean z) {
        if (z && this.mTopTipLayout.indexOfChild(this.mLyingDirectHintText) == -1) {
            addViewToTipLayout(this.mLyingDirectHintText, new Runnable() {
                public final void run() {
                    FragmentTopAlert.this.mTopTipLayout.setLayoutTransition((LayoutTransition) null);
                }
            }, $$Lambda$FragmentTopAlert$Xrm_hKnVP2W969y_W2pasM_2YI.INSTANCE);
        } else if (!z && this.mTopTipLayout.indexOfChild(this.mLyingDirectHintText) != -1) {
            removeViewToTipLayout(this.mLyingDirectHintText);
        }
    }

    public void updateMusicHint() {
        if (this.mCurrentMode != 174) {
            alertMusicTip(8, (String) null);
            return;
        }
        String[] currentLiveMusic = CameraSettings.getCurrentLiveMusic();
        if (!currentLiveMusic[1].isEmpty()) {
            alertMusicTip(0, currentLiveMusic[1]);
        }
    }

    public void updateRecordingTime(String str) {
        this.mAlertRecordingText.setText(str);
    }
}
