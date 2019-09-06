package com.android.camera.fragment.top;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
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
import com.android.camera.protocol.ModeProtocol.ConfigChanges;
import com.android.camera.protocol.ModeProtocol.DualController;
import com.android.camera.protocol.ModeProtocol.LiveConfigChanges;
import com.android.camera.protocol.ModeProtocol.TopAlert;
import com.android.camera.ui.ToggleSwitch;
import com.android.camera.ui.ToggleSwitch.OnCheckedChangeListener;
import com.android.camera2.Camera2Proxy;
import com.mi.config.b;
import io.reactivex.Completable;
import java.util.ArrayList;
import java.util.List;

public class FragmentTopAlert extends BaseFragment {
    public static final int FRAGMENT_INFO = 253;
    public static final long HINT_DELAY_TIME = 2000;
    private static final String TAG = "FragmentTopAlert";
    private static int sPendingRecordingTimeState;
    /* access modifiers changed from: private */
    public ToggleSwitch mAiSceneSelectView;
    private Runnable mAlertAiDetectTipHitRunable = new TopAlertRunnable() {
        /* access modifiers changed from: 0000 */
        public void runToSafe() {
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.removeViewToTipLayout(fragmentTopAlert.mRecommendTip);
        }
    };
    private long mAlertAiSceneSwitchHintTime;
    private int mAlertImageType = -1;
    private TextView mAlertRecordingText;
    private Runnable mAlertTopHintHideRunnable = new Runnable() {
        public void run() {
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.removeViewToToastLayout(fragmentTopAlert.mPermanentTip);
        }
    };
    private AlphaAnimation mBlingAnimation;
    private LayoutTransition mCustomStubTransition;
    private LayoutTransition mCustomToastTransition;
    private Handler mHandler;
    private boolean mIsShowTopLyingDirectHint;
    private TextView mLiveMusiHintText;
    private ImageView mLiveMusicClose;
    private LinearLayout mLiveMusicHintLayout;
    private TextView mLyingDirectHintText;
    /* access modifiers changed from: private */
    public TextView mPermanentTip;
    private ImageView mRawTip;
    /* access modifiers changed from: private */
    public TextView mRecommendTip;
    private boolean mShow;
    private Runnable mShowAction = new TopAlertRunnable() {
        public void runToSafe() {
            LayoutParams layoutParams = new LayoutParams(-2, FragmentTopAlert.this.getResources().getDimensionPixelOffset(R.dimen.ai_scene_selector_layout_height));
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.addViewToTipLayout(fragmentTopAlert.mAiSceneSelectView, layoutParams);
        }
    };
    private String mStateValueText = "";
    private boolean mStateValueTextFromLighting;
    /* access modifiers changed from: private */
    public TextView mToastAiSwitchTip;
    private ImageView mToastTipFlashHDR;
    private LinearLayout mToastTopTipLayout;
    private int mTopHintTextResource = 0;
    private LinearLayout mTopTipLayout;
    private TextView mVideoUltraClearTip;
    public final Runnable mViewHideRunnable = new TopAlertRunnable() {
        /* access modifiers changed from: 0000 */
        public void runToSafe() {
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.removeViewToToastLayout(fragmentTopAlert.mToastAiSwitchTip);
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

        /* access modifiers changed from: 0000 */
        public abstract void runToSafe();
    }

    static /* synthetic */ void a(ToggleSwitch toggleSwitch, boolean z) {
        ConfigChanges configChanges = (ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (z) {
            if (configChanges != null) {
                configChanges.onConfigChanged(248);
            }
        } else if (configChanges != null) {
            configChanges.onConfigChanged(249);
        }
    }

    private void addViewToTipLayout(View view) {
        addViewToTipLayout(view, null);
    }

    /* access modifiers changed from: private */
    public void addViewToTipLayout(View view, LayoutParams layoutParams) {
        if (view != null) {
            LinearLayout linearLayout = this.mTopTipLayout;
            if (linearLayout != null && linearLayout.indexOfChild(view) == -1) {
                if (this.mTopTipLayout.getLayoutTransition() != customStubViewTransition()) {
                    this.mTopTipLayout.setLayoutTransition(customStubViewTransition());
                }
                try {
                    this.mTopTipLayout.addView(view);
                } catch (Exception unused) {
                    Log.w(TAG, "The specified child already has a parent. You must call removeView() on the child's parent first");
                }
                if (layoutParams == null) {
                    layoutParams = new LayoutParams(-2, -2);
                }
                view.setLayoutParams(layoutParams);
            }
        }
    }

    private void addViewToTipLayout(View view, LayoutParams layoutParams, Runnable runnable, Runnable runnable2) {
        if (view != null) {
            LinearLayout linearLayout = this.mTopTipLayout;
            if (linearLayout != null && linearLayout.indexOfChild(view) == -1) {
                if (this.mTopTipLayout.getLayoutTransition() != customStubViewTransition()) {
                    this.mTopTipLayout.setLayoutTransition(customStubViewTransition());
                }
                if (runnable != null) {
                    runnable.run();
                }
                try {
                    this.mTopTipLayout.addView(view);
                } catch (IllegalStateException unused) {
                    Log.w(TAG, "The specified child already has a parent. You must call removeView() on the child's parent first");
                }
                if (layoutParams == null) {
                    layoutParams = new LayoutParams(-2, -2);
                }
                view.setLayoutParams(layoutParams);
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        }
    }

    private void addViewToTipLayout(View view, Runnable runnable, Runnable runnable2) {
        addViewToTipLayout(view, null, runnable, runnable2);
    }

    private void addViewToToastLayout(View view) {
        addViewToToastLayout(view, -1);
    }

    private void addViewToToastLayout(View view, int i) {
        if (view != null) {
            LinearLayout linearLayout = this.mToastTopTipLayout;
            if (linearLayout != null && linearLayout.indexOfChild(view) == -1) {
                if (this.mTopTipLayout.getLayoutTransition() != customStubViewTransition()) {
                    this.mTopTipLayout.setLayoutTransition(customStubViewTransition());
                }
                if (i < 0) {
                    this.mToastTopTipLayout.addView(view);
                } else {
                    this.mToastTopTipLayout.addView(view, i);
                }
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                layoutParams.width = -2;
                layoutParams.height = -2;
                view.setLayoutParams(layoutParams);
                if (this.mToastTopTipLayout.getChildCount() > 0) {
                    this.mToastTopTipLayout.setVisibility(0);
                }
            }
        }
    }

    private void alertAiSceneSelector(String str, String str2, int i, OnCheckedChangeListener onCheckedChangeListener, boolean z) {
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

    static /* synthetic */ void b(ToggleSwitch toggleSwitch, boolean z) {
        ConfigChanges configChanges = (ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (z) {
            if (configChanges != null) {
                configChanges.onConfigChanged(246);
            }
        } else if (configChanges != null) {
            configChanges.onConfigChanged(247);
        }
    }

    static /* synthetic */ void c(DialogInterface dialogInterface, int i) {
    }

    private LayoutTransition customStubViewTransition() {
        if (this.mCustomStubTransition == null) {
            this.mCustomStubTransition = new LayoutTransition();
            String str = "alpha";
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(null, str, new float[]{0.0f, 1.0f});
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(null, str, new float[]{1.0f, 0.0f});
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
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(null, "alpha", new float[]{0.0f, 1.0f});
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

    private String getZoomRatioTipText() {
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
            if ((HybridZoomingSystem.IS_2_SAT || (!HybridZoomingSystem.IS_3_OR_MORE_SAT && !CameraSettings.isSupportedOpticalZoom())) && id == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
                return null;
            }
            int i = this.mCurrentMode;
            if (i == 167 || i == 166 || i == 179) {
                return null;
            }
            if (DataRepository.dataItemFeature().dc() && this.mCurrentMode == 172) {
                return null;
            }
        }
        if (decimal == 0.6f && HybridZoomingSystem.IS_3_OR_MORE_SAT && id == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            return null;
        }
        if (id == Camera2DataContainer.getInstance().getAuxCameraId() && decimal <= 2.0f && this.mCurrentMode != 167) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("x ");
        sb.append(decimal);
        return sb.toString();
    }

    private void initHandler() {
        this.mHandler = new Handler();
    }

    private TextView initHorizonDirectTipText() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.top_tip_lying_direct_hint_layout, null);
    }

    private LinearLayout initMusicTipText() {
        return (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.top_tip_music_layout, null);
    }

    private TextView initPermanentTip() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.permanent_top_tip_layout, null);
    }

    private TextView initRecommendTipText() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.recommend_top_tip_layout, null);
    }

    private void initToastTipLayout() {
        setToastTipLayoutParams();
        this.mToastTopTipLayout.setVisibility(8);
        this.mToastTipFlashHDR = initToastTopTipImage();
        this.mToastAiSwitchTip = initToastTopTipText();
    }

    private ImageView initToastTopTipImage() {
        return (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.toast_top_tip_image_layout, null);
    }

    private TextView initToastTopTipText() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.toast_top_tip_text_layout, null);
    }

    private ToggleSwitch initTopTipToggleSwitch() {
        return (ToggleSwitch) LayoutInflater.from(getContext()).inflate(R.layout.top_tip_toggleswitch_layout, null);
    }

    private TextView initZoomTipText() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.zoom_top_tip_layout, null);
    }

    /* access modifiers changed from: private */
    public void removeViewToTipLayout(View view) {
        if (view != null) {
            LinearLayout linearLayout = this.mTopTipLayout;
            if (linearLayout != null && linearLayout.indexOfChild(view) != -1) {
                if (this.mTopTipLayout.getLayoutTransition() != customStubViewTransition()) {
                    this.mTopTipLayout.setLayoutTransition(customStubViewTransition());
                }
                this.mTopTipLayout.removeView(view);
                if (this.mTopTipLayout.getChildCount() <= 0) {
                    this.mTopTipLayout.removeAllViews();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void removeViewToToastLayout(View view) {
        if (view != null) {
            LinearLayout linearLayout = this.mToastTopTipLayout;
            if (linearLayout != null && linearLayout.indexOfChild(view) != -1) {
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
    }

    public static void setPendingRecordingState(int i) {
        sPendingRecordingTimeState = i;
    }

    private void setToastTipLayoutParams() {
        LinearLayout linearLayout = this.mToastTopTipLayout;
        if (linearLayout != null) {
            linearLayout.setDividerDrawable(getResources().getDrawable(R.drawable.top_tip_horizontal_divider));
            this.mToastTopTipLayout.setShowDividers(2);
            this.mToastTopTipLayout.setLayoutTransition(customToastLayoutTransition());
            this.mToastTopTipLayout.setGravity(17);
        }
    }

    private void setViewMargin(View view, int i) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.topMargin = i;
        view.setLayoutParams(marginLayoutParams);
        ViewCompat.setTranslationY(view, 0.0f);
    }

    private void showCloseConfirm() {
        Builder builder = new Builder(getContext());
        builder.setMessage(R.string.live_music_close_message);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.live_music_close_sure_message, new f(this));
        builder.setNegativeButton(R.string.live_music_close_cancel_message, a.INSTANCE);
        builder.show();
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
            }
        } else {
            this.mStateValueTextFromLighting = false;
            removeViewToTipLayout(this.mZoomTip);
        }
    }

    private void updateTopHint(int i, String str, long j) {
        this.mToastTopTipLayout.removeCallbacks(this.mAlertTopHintHideRunnable);
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
        alertAiSceneSelector(getResources().getString(R.string.text_ai_scene_selector_text_on), getResources().getString(R.string.text_ai_scene_selector_text_off), i, i == 0 ? h.INSTANCE : null, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0047 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0048  */
    public void alertFlash(int i, String str) {
        boolean z;
        int i2 = -1;
        if (i == 0) {
            int hashCode = str.hashCode();
            if (hashCode != 49) {
                if (hashCode != 50) {
                    if (hashCode == 53 && str.equals("5")) {
                        z = true;
                        if (z) {
                            i2 = 1;
                        } else if (z) {
                            i2 = 2;
                        } else if (z) {
                            i2 = 5;
                        }
                        if (this.mAlertImageType == i2) {
                            this.mAlertImageType = i2;
                            if (CameraSettings.isFrontCamera() && b.Ki()) {
                                i2 = 1;
                            }
                            if (i2 == 1) {
                                this.mToastTipFlashHDR.setImageResource(R.drawable.ic_alert_flash);
                                addViewToToastLayout(this.mToastTipFlashHDR, 0);
                            } else if (i2 == 2) {
                                this.mToastTipFlashHDR.setImageResource(R.drawable.ic_alert_flash_torch);
                                addViewToToastLayout(this.mToastTipFlashHDR, 0);
                            } else if (i2 != 5) {
                                removeViewToToastLayout(this.mToastTipFlashHDR);
                            } else {
                                this.mToastTipFlashHDR.setImageResource(R.drawable.ic_alert_flash_back_soft_light);
                                addViewToToastLayout(this.mToastTipFlashHDR, 0);
                            }
                        } else {
                            return;
                        }
                    }
                } else if (str.equals("2")) {
                    z = true;
                    if (z) {
                    }
                    if (this.mAlertImageType == i2) {
                    }
                }
            } else if (str.equals("1")) {
                z = false;
                if (z) {
                }
                if (this.mAlertImageType == i2) {
                }
            }
            z = true;
            if (z) {
            }
            if (this.mAlertImageType == i2) {
            }
        } else {
            int i3 = this.mAlertImageType;
            if (i3 == 2 || i3 == 1 || i3 == 5) {
                this.mAlertImageType = -1;
                removeViewToToastLayout(this.mToastTipFlashHDR);
            }
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
        } else {
            int i3 = this.mAlertImageType;
            if (i3 == 4 || i3 == 3) {
                this.mAlertImageType = -1;
                removeViewToToastLayout(this.mToastTipFlashHDR);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x001d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0022  */
    public void alertLightingHint(int i) {
        int i2;
        if (i != -1) {
            if (i == 3) {
                i2 = R.string.lighting_hint_too_close;
            } else if (i == 4) {
                i2 = R.string.lighting_hint_too_far;
            } else if (i == 5) {
                i2 = R.string.lighting_hint_needed;
            }
            if (i2 != -1) {
                this.mStateValueText = "";
            } else {
                this.mStateValueText = getResources().getString(i2);
            }
            updateStateText(true);
        }
        i2 = -1;
        if (i2 != -1) {
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
        alertAiSceneSelector(str, str2, i, i == 0 ? d.INSTANCE : null, z);
    }

    public void alertMusicClose(boolean z) {
        ImageView imageView = this.mLiveMusicClose;
        if (imageView == null) {
            return;
        }
        if (z) {
            imageView.setAlpha(1.0f);
            this.mLiveMusicClose.setClickable(true);
            return;
        }
        imageView.setAlpha(0.4f);
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

    public void alertRaw(int i, int i2, boolean z) {
        if (this.mRawTip.getVisibility() != i) {
            if (i != 0 || !z) {
                this.mRawTip.setVisibility(i);
            } else {
                this.mRawTip.setVisibility(i);
                ViewCompat.setAlpha(this.mRawTip, 0.0f);
                ViewCompat.animate(this.mRawTip).alpha(1.0f).setDuration(320).start();
            }
            setViewMargin(this.mRawTip, i2);
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

    public void alertSwitchHint(int i, @StringRes int i2) {
        alertSwitchHint(i, getString(i2));
    }

    public void alertSwitchHint(int i, String str) {
        this.mToastAiSwitchTip.setTag(Integer.valueOf(i));
        this.mToastAiSwitchTip.setText(str);
        this.mToastAiSwitchTip.setContentDescription(str);
        addViewToToastLayout(this.mToastAiSwitchTip);
        this.mAlertAiSceneSwitchHintTime = System.currentTimeMillis();
        this.mHandler.postDelayed(new c(this), 300);
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
        DualController dualController = (DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController == null || !dualController.isZoomVisible() || CameraSettings.isMacroModeEnabled(this.mCurrentMode)) {
            String zoomRatioTipText = getZoomRatioTipText();
            if (zoomRatioTipText != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.mStateValueText);
                sb.append(zoomRatioTipText);
                this.mStateValueText = sb.toString();
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
                this.mVideoUltraClearTip.setText(i2);
                this.mVideoUltraClearTip.setContentDescription(getString(i2));
            }
        }
    }

    public /* synthetic */ void b(DialogInterface dialogInterface, int i) {
        removeViewToTipLayout(this.mLiveMusicHintLayout);
        LiveConfigChanges liveConfigChanges = (LiveConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(201);
        if (liveConfigChanges != null && !liveConfigChanges.isRecording() && !liveConfigChanges.isRecordingPaused()) {
            liveConfigChanges.closeBGM();
            ((TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(245);
        }
    }

    public /* synthetic */ void c(View view) {
        showCloseConfirm();
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
        TextView textView = this.mVideoUltraClearTip;
        if (!(textView == null || textView.getVisibility() == 8)) {
            this.mVideoUltraClearTip.setVisibility(8);
        }
        ImageView imageView = this.mRawTip;
        if (imageView != null && imageView.getVisibility() != 8) {
            this.mRawTip.setVisibility(8);
        }
    }

    public void clearAlertStatus() {
        this.mStateValueText = "";
        this.mStateValueTextFromLighting = false;
        removeViewToTipLayout(this.mZoomTip);
    }

    public /* synthetic */ void fa() {
        if (isAdded()) {
            this.mToastAiSwitchTip.sendAccessibilityEvent(32768);
        }
    }

    public /* synthetic */ void ga() {
        this.mTopTipLayout.setLayoutTransition(null);
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
        this.mRawTip = (ImageView) view.findViewById(R.id.raw_tip);
        if (Util.isNotchDevice) {
            setViewMargin(this.mAlertRecordingText, Util.sStatusBarHeight);
            setViewMargin(this.mRawTip, Util.sStatusBarHeight);
        }
        ViewCompat.setAlpha(this.mAlertRecordingText, 0.0f);
        int i = sPendingRecordingTimeState;
        if (i != 0) {
            setRecordingTimeState(i);
            setPendingRecordingState(0);
        }
        this.mTopTipLayout = (LinearLayout) view.findViewById(R.id.top_tip_layout);
        this.mToastTopTipLayout = (LinearLayout) this.mTopTipLayout.findViewById(R.id.top_toast_layout);
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.mTopTipLayout.getLayoutParams();
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
        this.mLiveMusicClose.setOnClickListener(new g(this));
        this.mPermanentTip = initPermanentTip();
        this.mLyingDirectHintText = initHorizonDirectTipText();
    }

    public boolean isCurrentRecommendTipText(int i) {
        if (i <= 0) {
            return false;
        }
        String string = getResources().getString(i);
        return !TextUtils.isEmpty(string) && isShowTopLayoutSpecifyTip(this.mRecommendTip) && string.equals(this.mRecommendTip.getText());
    }

    public boolean isHDRTipShowing() {
        return this.mAlertImageType == 3;
    }

    public boolean isShow() {
        return this.mShow;
    }

    public boolean isShowTopLayoutSpecifyTip(View view) {
        if (view != null) {
            LinearLayout linearLayout = this.mTopTipLayout;
            return (linearLayout == null || linearLayout.indexOfChild(view) == -1) ? false : true;
        }
        return false;
    }

    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.reInitAlert(false);
        }
    }

    public void onStop() {
        super.onStop();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        AlphaAnimation alphaAnimation = this.mBlingAnimation;
        if (alphaAnimation != null) {
            alphaAnimation.cancel();
            this.mBlingAnimation = null;
        }
    }

    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        int i3 = this.mCurrentMode;
        super.provideAnimateElement(i, list, i2);
        boolean z = true;
        clear((i3 == i || (i3 == 163 && i == 165) || (i3 == 165 && i == 163)) ? false : true);
        if (i2 == 7) {
            z = false;
        }
        setShow(z);
    }

    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        updateAlertStatusView(true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0078, code lost:
        if (r4 != 177) goto L_0x008a;
     */
    public void setRecordingTimeState(int i) {
        if (i == 1) {
            int i2 = this.mCurrentMode;
            if (i2 != 161) {
                if (i2 == 162 || i2 == 169 || i2 == 172) {
                    this.mAlertRecordingText.setText("00:00");
                    Completable.create(new AlphaInOnSubscribe(this.mAlertRecordingText)).subscribe();
                }
            }
            this.mAlertRecordingText.setText("00:15");
            Completable.create(new AlphaInOnSubscribe(this.mAlertRecordingText)).subscribe();
        } else if (i == 2) {
            AlphaAnimation alphaAnimation = this.mBlingAnimation;
            if (alphaAnimation != null) {
                alphaAnimation.cancel();
            }
            Completable.create(new AlphaOutOnSubscribe(this.mAlertRecordingText)).subscribe();
        } else if (i == 3) {
            if (this.mBlingAnimation == null) {
                this.mBlingAnimation = new AlphaAnimation(1.0f, 0.0f);
                this.mBlingAnimation.setDuration(400);
                this.mBlingAnimation.setStartOffset(100);
                this.mBlingAnimation.setInterpolator(new DecelerateInterpolator());
                this.mBlingAnimation.setRepeatMode(2);
                this.mBlingAnimation.setRepeatCount(-1);
            }
            this.mAlertRecordingText.startAnimation(this.mBlingAnimation);
        } else if (i == 4) {
            this.mBlingAnimation.cancel();
        }
    }

    public void setShow(boolean z) {
        this.mShow = z;
    }

    public void updateLyingDirectHint(boolean z) {
        if (z && this.mTopTipLayout.indexOfChild(this.mLyingDirectHintText) == -1) {
            addViewToTipLayout(this.mLyingDirectHintText, new b(this), e.INSTANCE);
        } else if (!z && this.mTopTipLayout.indexOfChild(this.mLyingDirectHintText) != -1) {
            removeViewToTipLayout(this.mLyingDirectHintText);
        }
    }

    public void updateMusicHint() {
        if (this.mCurrentMode != 174) {
            alertMusicTip(8, null);
            return;
        }
        String[] currentLiveMusic = CameraSettings.getCurrentLiveMusic();
        if (this.mLiveMusicHintLayout.getVisibility() == 0) {
            alertMusicTip(0, this.mLiveMusiHintText.getText().toString());
        } else if (!currentLiveMusic[1].isEmpty()) {
            alertMusicTip(0, currentLiveMusic[1]);
        }
    }

    public void updateRecordingTime(String str) {
        this.mAlertRecordingText.setText(str);
    }
}
