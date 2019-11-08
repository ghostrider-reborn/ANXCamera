package com.android.camera.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.camera.ActivityBase;
import com.android.camera.Camera;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FragmentAnimationFactory;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.animation.type.TranslateYOnSubscribe;
import com.android.camera.constant.DurationConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.mimoji.FragmentMimoji;
import com.android.camera.fragment.top.FragmentTopAlert;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol.ActionProcessing;
import com.android.camera.protocol.ModeProtocol.BaseDelegate;
import com.android.camera.protocol.ModeProtocol.BokehFNumberController;
import com.android.camera.protocol.ModeProtocol.BottomMenuProtocol;
import com.android.camera.protocol.ModeProtocol.BottomPopupTips;
import com.android.camera.protocol.ModeProtocol.CameraAction;
import com.android.camera.protocol.ModeProtocol.CameraModuleSpecial;
import com.android.camera.protocol.ModeProtocol.ConfigChanges;
import com.android.camera.protocol.ModeProtocol.DualController;
import com.android.camera.protocol.ModeProtocol.HandleBackTrace;
import com.android.camera.protocol.ModeProtocol.MakeupProtocol;
import com.android.camera.protocol.ModeProtocol.ManuallyAdjust;
import com.android.camera.protocol.ModeProtocol.MiBeautyProtocol;
import com.android.camera.protocol.ModeProtocol.ModeCoordinator;
import com.android.camera.protocol.ModeProtocol.TopAlert;
import com.android.camera.protocol.ModeProtocol.VerticalProtocol;
import com.android.camera.statistic.CameraStat;
import com.android.camera.statistic.CameraStatUtil;
import com.mi.config.b;
import io.reactivex.Completable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import miui.view.animation.BackEaseOutInterpolator;

public class FragmentBottomPopupTips extends BaseFragment implements OnClickListener, BottomPopupTips, HandleBackTrace {
    private static final int ANIM_DELAY_SHOW = 3;
    private static final int ANIM_DIRECT_HIDE = 1;
    private static final int ANIM_DIRECT_SHOW = 0;
    private static final int ANIM_HIDE = 4;
    private static final int ANIM_SHOW = 2;
    private static final int CALL_TYPE_NOTIFY = 1;
    private static final int CALL_TYPE_PROVIDE = 0;
    private static final int CENTER_TIP_IMAGE_MIMOJI = 34;
    private static final int CENTER_TIP_IMAGE_SPEED = 33;
    public static final int FRAGMENT_INFO = 65522;
    private static final int LEFT_TIP_IMAGE_CLOSE = 20;
    private static final int LEFT_TIP_IMAGE_LIGHTING = 19;
    private static final int LEFT_TIP_IMAGE_STICKER = 18;
    private static final int LEFT_TIP_IMAGE_ULTRA_WIDE = 21;
    private static final long MAX_RED_DOT_TIME = 86400000;
    private static final int MSG_HIDE_TIP = 1;
    private static final int TIP_IMAGE_INVALID = -1;
    private static final int TIP_IMAGE_STICKER = 2;
    private static final int TIP_SHINE = 3;
    private boolean INIT_TAG = false;
    private int mBottomTipHeight;
    private View mCenterRedDot;
    private ImageView mCenterTipImage;
    private ImageView mCloseIv;
    private int mCloseType = 0;
    private String mCurrentTipMessage;
    /* access modifiers changed from: private */
    public int mCurrentTipType;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == 1) {
                FragmentBottomPopupTips.this.mTipMessage.setVisibility(8);
                if (FragmentBottomPopupTips.this.mCurrentMode == 163) {
                    CameraModuleSpecial cameraModuleSpecial = (CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195);
                    if (cameraModuleSpecial != null) {
                        cameraModuleSpecial.showOrHideChip(true);
                    }
                }
                if (FragmentBottomPopupTips.this.mLastTipType == 6 && FragmentBottomPopupTips.this.mCurrentTipType != 8 && !FragmentBottomPopupTips.this.isPortraitHintVisible()) {
                    FragmentBottomPopupTips fragmentBottomPopupTips = FragmentBottomPopupTips.this;
                    fragmentBottomPopupTips.showTips(fragmentBottomPopupTips.mLastTipType, FragmentBottomPopupTips.this.mLastTipMessage, 4);
                } else if (FragmentBottomPopupTips.this.mLastTipType == 10 && CameraSettings.isEyeLightOpen()) {
                    FragmentBottomPopupTips fragmentBottomPopupTips2 = FragmentBottomPopupTips.this;
                    fragmentBottomPopupTips2.showTips(fragmentBottomPopupTips2.mLastTipType, FragmentBottomPopupTips.this.mLastTipMessage, 4);
                } else if ((FragmentBottomPopupTips.this.mLastTipType != 18 || !CameraSettings.isMacroModeEnabled(FragmentBottomPopupTips.this.mCurrentMode)) && (FragmentBottomPopupTips.this.mLastTipType != 8 || !CameraSettings.isMacroModeEnabled(FragmentBottomPopupTips.this.mCurrentMode))) {
                    FragmentBottomPopupTips.this.updateLyingDirectHint(false, true);
                }
                FragmentBottomPopupTips.this.mLastTipType = 4;
            }
        }
    };
    private boolean mIsShowLeftImageIntro;
    private boolean mIsShowLyingDirectHint;
    /* access modifiers changed from: private */
    public String mLastTipMessage;
    /* access modifiers changed from: private */
    public int mLastTipType;
    /* access modifiers changed from: private */
    public FrameLayout mLeftImageIntro;
    private AnimatorSet mLeftImageIntroAnimator;
    private TextView mLeftImageIntroContent;
    private int mLeftImageIntroRadius;
    private int mLeftImageIntroWidth;
    private ImageView mLeftTipImage;
    private TextView mLightingPattern;
    private TextView mLyingDirectHint;
    /* access modifiers changed from: private */
    public TextView mMimojiTextview;
    private View mPortraitSuccessHint;
    /* access modifiers changed from: private */
    public TextView mQrCodeButton;
    private View mRootView;
    private ViewGroup mSpeedTipImage;
    private ImageView mTipImage;
    /* access modifiers changed from: private */
    public TextView mTipMessage;
    private int stringLightingRes;

    @Retention(RetentionPolicy.SOURCE)
    @interface TipImageType {
    }

    private void adjustViewBackground(int i) {
        int uiStyle = DataRepository.dataItemRunning().getUiStyle();
        if (uiStyle == 1 || uiStyle == 3) {
            this.mQrCodeButton.setBackgroundResource(R.drawable.btn_camera_mode_exit_full_screen);
            this.mMimojiTextview.setBackgroundResource(R.drawable.btn_camera_mode_exit);
            return;
        }
        this.mQrCodeButton.setBackgroundResource(R.drawable.btn_camera_mode_exit);
        this.mMimojiTextview.setBackgroundResource(R.drawable.btn_camera_mode_exit);
    }

    private int checkLeftImageTipClose(int i) {
        ActionProcessing actionProcessing = (ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        if (actionProcessing == null || !actionProcessing.isShowLightingView()) {
            return i;
        }
        return 20;
    }

    private void closeFilter() {
        showCloseTip(1, false);
        ConfigChanges configChanges = (ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            configChanges.showOrHideFilter();
        }
    }

    private void closeLight() {
        showCloseTip(2, false);
        ConfigChanges configChanges = (ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            configChanges.showOrHideLighting(false);
        }
        updateLeftTipImage();
    }

    /* access modifiers changed from: private */
    public void directHideLeftImageIntro() {
        this.mIsShowLeftImageIntro = false;
        AnimatorSet animatorSet = this.mLeftImageIntroAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        if (this.mLeftImageIntro.getVisibility() == 0) {
            AlphaOutOnSubscribe.directSetResult(this.mLeftImageIntro);
        }
    }

    private int getLeftImageIntroWide() {
        this.mLeftImageIntroContent.measure(0, 0);
        int measuredWidth = this.mLeftImageIntroContent.getMeasuredWidth();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.popup_tip_beauty_intro_left_padding);
        return measuredWidth + dimensionPixelSize + getResources().getDimensionPixelSize(R.dimen.popup_tip_beauty_intro_right_padding) + ((this.mLeftImageIntroRadius - dimensionPixelSize) * 2);
    }

    private int getTipBottomMargin(int i) {
        int dimensionPixelSize;
        int i2;
        int dimensionPixelSize2;
        DualController dualController = (DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        ManuallyAdjust manuallyAdjust = (ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181);
        MiBeautyProtocol miBeautyProtocol = (MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        BokehFNumberController bokehFNumberController = (BokehFNumberController) ModeCoordinatorImpl.getInstance().getAttachProtocol(210);
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.beauty_extra_tip_bottom_margin);
        int i3 = this.mCurrentMode;
        if (i3 == 165) {
            if (CameraSettings.isUltraWideConfigOpen(i3)) {
                ImageView imageView = this.mCenterTipImage;
                if (!(imageView == null || imageView.getVisibility() == 0) || HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                    dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.manually_indicator_layout_height) / 2;
                    i2 = i / 2;
                }
            }
            int i4 = Util.sWindowWidth;
            return getResources().getDimensionPixelSize(R.dimen.tips_margin_bottom_normal) + ((((int) (((float) i4) / 0.75f)) - i4) / 2);
        } else if (manuallyAdjust != null && manuallyAdjust.visibleHeight() > 0) {
            return manuallyAdjust.visibleHeight();
        } else {
            if (this.mCenterTipImage.getVisibility() == 0) {
                if (miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow()) {
                    return this.mTipImage.getHeight();
                }
                dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.beauty_fragment_height);
            } else if (dualController != null && dualController.isZoomVisible()) {
                return dualController.visibleHeight();
            } else {
                if (bokehFNumberController == null || !bokehFNumberController.isFNumberVisible()) {
                    MakeupProtocol makeupProtocol = (MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
                    if (makeupProtocol != null && makeupProtocol.isSeekBarVisible()) {
                        return getResources().getDimensionPixelSize(R.dimen.beautycamera_popup_fragment_height) + getResources().getDimensionPixelSize(R.dimen.beauty_fragment_height);
                    } else if (miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow()) {
                        dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.manually_indicator_layout_height) / 2;
                        i2 = i / 2;
                    } else {
                        dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.beauty_fragment_height);
                    }
                } else {
                    return ((int) this.mRootView.getTranslationY()) + bokehFNumberController.visibleHeight();
                }
            }
            return dimensionPixelSize2 + dimensionPixelSize3;
        }
        return dimensionPixelSize - i2;
    }

    private void hideAllTipImage() {
        hideTipImage();
        hideLeftTipImage();
        directHideLeftImageIntro();
        hideSpeedTipImage();
        hideZoomTipImage(this.mCurrentMode);
        hideCenterTipImage();
    }

    private boolean hideTip(View view) {
        if (view.getVisibility() == 8) {
            return false;
        }
        view.setVisibility(8);
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0021, code lost:
        if (com.android.camera.HybridZoomingSystem.IS_3_OR_MORE_SAT == false) goto L_0x0047;
     */
    private void hideZoomTipImage(int i) {
        if (i != 165) {
            if (i != 166) {
                if (i != 173) {
                    if (i != 174) {
                        switch (i) {
                            case 161:
                            case 162:
                                break;
                            case 163:
                                break;
                        }
                    }
                }
            } else if (!DataRepository.dataItemFeature().Yb()) {
                return;
            }
        }
        DualController dualController = (DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null) {
            dualController.hideZoomButton();
            TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.alertUpdateValue(2);
            }
        }
    }

    private boolean isPortraitSuccessHintVisible() {
        return this.mPortraitSuccessHint.getVisibility() == 0;
    }

    private void onLeftImageClick(View view) {
        ConfigChanges configChanges = (ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        switch (((Integer) view.getTag()).intValue()) {
            case 19:
                if (configChanges != null) {
                    configChanges.onConfigChanged(203);
                    return;
                }
                return;
            case 20:
                int i = this.mCloseType;
                if (i == 1) {
                    closeFilter();
                    return;
                } else if (i == 2) {
                    closeLight();
                    return;
                } else {
                    return;
                }
            case 21:
                if (configChanges != null && !HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                    configChanges.onConfigChanged(205);
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void reConfigBottomTipOf960FPS() {
        if (CameraSettings.isFPS960(this.mCurrentMode)) {
            showTips(9, (int) R.string.fps960_toast, 4);
        }
    }

    private void reConfigBottomTipOfMacro() {
        if (!CameraSettings.isMacroModeEnabled(this.mCurrentMode)) {
        }
    }

    private void reConfigBottomTipOfMimoji() {
        if (this.mCurrentMode == 177 && DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiPreview()) {
            if (DataRepository.dataItemLive().getMimojiStatusManager().getMimojiPannelState()) {
                hideAllTipImage();
            } else {
                showTips(19, (int) R.string.mimoji_tips, 2);
            }
        }
    }

    private void reIntTipViewMarginBottom(View view, int i) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
        int tipBottomMargin = getTipBottomMargin(i);
        if (marginLayoutParams.bottomMargin != tipBottomMargin) {
            marginLayoutParams.bottomMargin = tipBottomMargin;
            view.setLayoutParams(marginLayoutParams);
        }
    }

    /* access modifiers changed from: private */
    public void setBeautyIntroButtonWidth(View view, int i) {
        if (view != null) {
            LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = i;
            view.setLayoutParams(layoutParams);
        }
    }

    private void showBeauty(int i) {
        ConfigChanges configChanges = (ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            configChanges.showOrHideShine();
        }
    }

    private void showLiveSpeed() {
        CameraStatUtil.trackLiveClick(CameraStat.KEY_LIVE_SPEED);
        BottomMenuProtocol bottomMenuProtocol = (BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197);
        if (bottomMenuProtocol != null) {
            bottomMenuProtocol.onSwitchLiveActionMenu(165);
        }
        BaseDelegate baseDelegate = (BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            baseDelegate.delegateEvent(13);
        }
    }

    private void showLiveSticker() {
        CameraStatUtil.trackLiveClick(CameraStat.KEY_LIVE_STICKER);
        BottomMenuProtocol bottomMenuProtocol = (BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197);
        if (bottomMenuProtocol != null) {
            bottomMenuProtocol.onSwitchLiveActionMenu(164);
        }
        BaseDelegate baseDelegate = (BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            baseDelegate.delegateEvent(12);
        }
        BottomPopupTips bottomPopupTips = (BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.directlyHideTips();
        }
    }

    private void showMimojiPanel() {
        BottomMenuProtocol bottomMenuProtocol = (BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197);
        if (bottomMenuProtocol != null) {
            bottomMenuProtocol.onSwitchLiveActionMenu(166);
        }
        BaseDelegate baseDelegate = (BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            baseDelegate.delegateEvent(14);
        }
        BottomPopupTips bottomPopupTips = (BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.directlyHideTips();
        }
    }

    private void startLeftImageIntroAnim(int i) {
        if (i != 1) {
            directShowOrHideLeftTipImage(false);
            this.mLeftImageIntro.setVisibility(0);
            if (this.mLeftImageIntroAnimator == null) {
                ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{this.mLeftImageIntroWidth, this.mLeftImageIntroRadius * 2});
                ofInt.setDuration(300);
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mLeftImageIntroContent, "alpha", new float[]{1.0f, 0.0f});
                ofFloat.setDuration(250);
                ofInt.addUpdateListener(new AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        FragmentBottomPopupTips fragmentBottomPopupTips = FragmentBottomPopupTips.this;
                        fragmentBottomPopupTips.setBeautyIntroButtonWidth(fragmentBottomPopupTips.mLeftImageIntro, ((Integer) valueAnimator.getAnimatedValue()).intValue());
                    }
                });
                this.mLeftImageIntroAnimator = new AnimatorSet();
                this.mLeftImageIntroAnimator.playTogether(new Animator[]{ofInt, ofFloat});
                this.mLeftImageIntroAnimator.setInterpolator(new PathInterpolator(0.25f, 0.1f, 0.25f, 0.1f));
                this.mLeftImageIntroAnimator.setStartDelay(FragmentTopAlert.HINT_DELAY_TIME);
                this.mLeftImageIntroAnimator.addListener(new AnimatorListenerAdapter() {
                    private boolean cancelled;

                    public void onAnimationCancel(Animator animator) {
                        this.cancelled = true;
                    }

                    public void onAnimationEnd(Animator animator) {
                        if (FragmentBottomPopupTips.this.canProvide() && !this.cancelled) {
                            FragmentBottomPopupTips.this.directHideLeftImageIntro();
                            FragmentBottomPopupTips.this.updateLeftTipImage();
                        }
                    }

                    public void onAnimationStart(Animator animator) {
                        this.cancelled = false;
                    }
                });
            } else {
                this.mLeftImageIntro.setAlpha(1.0f);
                this.mLeftImageIntroContent.clearAnimation();
                this.mLeftImageIntroAnimator.cancel();
            }
            this.mLeftImageIntroAnimator.start();
            CameraSettings.addPopupUltraWideIntroShowTimes();
        }
    }

    /* JADX WARNING: type inference failed for: r13v0, types: [int] */
    /* JADX WARNING: type inference failed for: r13v1 */
    /* JADX WARNING: type inference failed for: r13v2 */
    /* JADX WARNING: type inference failed for: r13v3 */
    /* JADX WARNING: type inference failed for: r13v4 */
    /* JADX WARNING: type inference failed for: r13v5 */
    /* JADX WARNING: type inference failed for: r13v6 */
    /* JADX WARNING: type inference failed for: r13v7 */
    /* JADX WARNING: type inference failed for: r13v8 */
    /* JADX WARNING: type inference failed for: r13v9 */
    /* JADX WARNING: type inference failed for: r13v10 */
    /* JADX WARNING: type inference failed for: r13v11 */
    /* JADX WARNING: type inference failed for: r13v12 */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0052, code lost:
        if (com.android.camera.data.DataRepository.dataItemLive().getMimojiStatusManager().getMimojiPannelState() == false) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0125, code lost:
        if (r5 != false) goto L_0x0143;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r13v3
  assigns: []
  uses: []
  mth insns count: 142
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0145  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x018b  */
    /* JADX WARNING: Unknown variable types count: 5 */
    private void updateCenterTipImage(int i, int i2, List<Completable> list) {
        int i3;
        ? r13;
        boolean z;
        ? r132;
        ? r133;
        ? r134;
        int i4 = i;
        int i5 = i2;
        List<Completable> list2 = list;
        String str = FragmentMimoji.CLOSE_STATE;
        if (i4 != 174) {
            if (i4 == 177) {
                if (((ActivityBase) getActivity()).startFromKeyguard()) {
                    if (!this.INIT_TAG) {
                        this.INIT_TAG = true;
                        DataRepository.dataItemLive().getMimojiStatusManager().setCurrentMimojiState(str);
                    }
                } else if (DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiPreview()) {
                }
                i3 = 34;
            }
            i3 = -1;
        } else {
            i3 = 18;
        }
        FrameLayout frameLayout = (FrameLayout) this.mRootView.findViewById(R.id.popup_center_tip_layout);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
        boolean z2 = false;
        if (i3 != -1) {
            ? r135 = 2131231000;
            if (i3 == 18) {
                if (!"".equals(CameraSettings.getCurrentLiveSticker())) {
                    r135 = R.drawable.ic_live_sticker_on;
                }
                if (this.mCenterRedDot != null) {
                    boolean tTLiveStickerNeedRedDot = CameraSettings.getTTLiveStickerNeedRedDot();
                    long liveStickerRedDotTime = CameraSettings.getLiveStickerRedDotTime();
                    long currentTimeMillis = System.currentTimeMillis();
                    if ((liveStickerRedDotTime <= 0 || currentTimeMillis - liveStickerRedDotTime < MAX_RED_DOT_TIME) && tTLiveStickerNeedRedDot) {
                        this.mCenterRedDot.setVisibility(0);
                    }
                }
                if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                    layoutParams.gravity = 81;
                    r133 = r132;
                } else {
                    layoutParams.gravity = 8388691;
                    r133 = r132;
                }
            } else if (i3 != 34) {
                z = true;
                r13 = 0;
            } else {
                String currentMimojiState = DataRepository.dataItemLive().getMimojiStatusManager().getCurrentMimojiState();
                if (!FragmentMimoji.ADD_STATE.equals(currentMimojiState) && !str.equals(currentMimojiState)) {
                    r135 = R.drawable.ic_live_sticker_on;
                }
                layoutParams.gravity = 81;
                r133 = r134;
            }
            z = true;
            r13 = r133;
        } else {
            View view = this.mCenterRedDot;
            if (view != null) {
                view.setVisibility(8);
            }
            z = false;
            r13 = 0;
        }
        if (r13 > 0) {
            frameLayout.requestLayout();
            this.mCenterTipImage.setImageResource(r13);
        }
        updateImageBgColor(i4, this.mCenterTipImage, false);
        if (this.mCenterTipImage.getTag() == null || ((Integer) this.mCenterTipImage.getTag()).intValue() != i3) {
            if (z) {
                ViewCompat.setRotation(this.mCenterTipImage, (float) this.mDegree);
            }
            this.mCenterTipImage.setTag(Integer.valueOf(i3));
            if (list2 != null) {
                if (z) {
                    if (i5 != 165) {
                        z2 = true;
                    } else if (!b.isSupportedOpticalZoom()) {
                        z2 = true;
                    }
                } else if (!(i5 == 165 || this.mCurrentMode == 165)) {
                    z2 = true;
                }
                if (z2) {
                    AlphaInOnSubscribe.directSetResult(this.mCenterTipImage);
                } else if (z2) {
                    AlphaOutOnSubscribe.directSetResult(this.mCenterTipImage);
                } else if (z2) {
                    list2.add(Completable.create(new AlphaInOnSubscribe(this.mCenterTipImage)));
                } else if (z2) {
                    list2.add(Completable.create(new AlphaInOnSubscribe(this.mCenterTipImage).setStartDelayTime(150).setDurationTime(150)));
                } else if (z2) {
                    list2.add(Completable.create(new AlphaOutOnSubscribe(this.mCenterTipImage)));
                }
            }
            z2 = true;
            if (z2) {
            }
        }
    }

    private void updateImageBgColor(int i, View view, boolean z) {
        int i2;
        int i3;
        if (z) {
            i3 = R.drawable.bg_popup_indicator;
            i2 = R.drawable.square_module_bg_popup_indicator;
        } else {
            i3 = R.drawable.bg_popup_indicator_no_stroke;
            i2 = R.drawable.square_module_bg_popup_indicator_no_stroke;
        }
        if (i != 165) {
            view.setBackgroundResource(i3);
        } else {
            view.setBackgroundResource(i2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:101:0x015d, code lost:
        if (r2 != false) goto L_0x017c;
     */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x015d  */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x0162  */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x017e  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x01c4  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00c6  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x010e  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0113  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x0136  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0147  */
    private void updateLeftTipImage(int i, int i2, int i3, List<Completable> list) {
        int i4;
        int checkLeftImageTipClose;
        int i5;
        boolean z;
        int i6;
        int currentCameraId = DataRepository.dataItemGlobal().getCurrentCameraId();
        boolean isNormalIntent = DataRepository.dataItemGlobal().isNormalIntent();
        boolean z2 = false;
        boolean z3 = DataRepository.dataItemFeature().isSupportUltraWide() && !HybridZoomingSystem.IS_3_OR_MORE_SAT;
        if (i2 != 165) {
            if (i2 != 169) {
                if (i2 != 171) {
                    if (i2 != 174) {
                        switch (i2) {
                            case 161:
                                break;
                            case 162:
                                if (!CameraSettings.isMacroModeEnabled(i2) && currentCameraId == 0 && isNormalIntent && z3 && !CameraSettings.isAutoZoomEnabled(i2) && !CameraSettings.isSuperEISEnabled(i2)) {
                                    if (this.mIsShowLeftImageIntro) {
                                        startLeftImageIntroAnim(i);
                                        return;
                                    }
                                }
                                break;
                            case 163:
                                break;
                        }
                    }
                    if (z3 && currentCameraId == 0) {
                        if (this.mIsShowLeftImageIntro) {
                            startLeftImageIntroAnim(i);
                            return;
                        }
                        i4 = 21;
                        checkLeftImageTipClose = checkLeftImageTipClose(i4);
                        i5 = R.drawable.ic_live_sticker_normal;
                        if (checkLeftImageTipClose == -1) {
                            i5 = 0;
                            i6 = 0;
                            z = false;
                        } else if (checkLeftImageTipClose != 21) {
                            if (checkLeftImageTipClose != 34) {
                                if (checkLeftImageTipClose == 18) {
                                    if (!"".equals(CameraSettings.getCurrentLiveSticker())) {
                                        i5 = R.drawable.ic_live_sticker_on;
                                    }
                                } else if (checkLeftImageTipClose != 19) {
                                    z = true;
                                    i5 = 0;
                                    i6 = 0;
                                } else {
                                    i5 = R.drawable.ic_light;
                                    i6 = R.string.accessibility_lighting_panel_on;
                                    z = true;
                                }
                            }
                            z = true;
                            i6 = 0;
                        } else {
                            boolean isUltraWideConfigOpen = CameraSettings.isUltraWideConfigOpen(this.mCurrentMode);
                            z = true;
                            int i7 = isUltraWideConfigOpen ? R.drawable.icon_config_ultra_wide_on : R.drawable.icon_config_ultra_wide_off;
                            i6 = isUltraWideConfigOpen ? R.string.accessibility_ultra_wide_on : R.string.accessibility_ultra_wide_off;
                            i5 = i7;
                        }
                        if (i5 > 0) {
                            this.mLeftTipImage.setImageResource(i5);
                        }
                        if (i6 > 0 && Util.isAccessible()) {
                            this.mLeftTipImage.setContentDescription(getString(i6));
                        }
                        updateImageBgColor(i2, this.mLeftTipImage, false);
                        if (this.mLeftTipImage.getTag() != null || ((Integer) this.mLeftTipImage.getTag()).intValue() != checkLeftImageTipClose) {
                            if (z) {
                                ViewCompat.setRotation(this.mLeftTipImage, (float) this.mDegree);
                            }
                            this.mLeftTipImage.setTag(Integer.valueOf(checkLeftImageTipClose));
                            if (list != null) {
                                if (z) {
                                    if (i3 != 165) {
                                        z2 = true;
                                    } else if (!b.isSupportedOpticalZoom()) {
                                        z2 = true;
                                    }
                                    directHideLeftImageIntro();
                                } else if (!(i3 == 165 || this.mCurrentMode == 165)) {
                                    z2 = true;
                                }
                                if (z2) {
                                    AlphaInOnSubscribe.directSetResult(this.mLeftTipImage);
                                } else if (z2) {
                                    AlphaOutOnSubscribe.directSetResult(this.mLeftTipImage);
                                } else if (z2) {
                                    list.add(Completable.create(new AlphaInOnSubscribe(this.mLeftTipImage)));
                                } else if (z2) {
                                    list.add(Completable.create(new AlphaInOnSubscribe(this.mLeftTipImage).setStartDelayTime(150).setDurationTime(150)));
                                } else if (z2) {
                                    list.add(Completable.create(new AlphaOutOnSubscribe(this.mLeftTipImage)));
                                }
                            }
                            z2 = true;
                            if (z2) {
                            }
                        }
                        return;
                    }
                } else if (isNormalIntent && (currentCameraId == 0 ? DataRepository.dataItemFeature().Xc() : !(currentCameraId != 1 || !DataRepository.dataItemFeature().Yc()))) {
                    i4 = 19;
                    checkLeftImageTipClose = checkLeftImageTipClose(i4);
                    i5 = R.drawable.ic_live_sticker_normal;
                    if (checkLeftImageTipClose == -1) {
                    }
                    if (i5 > 0) {
                    }
                    this.mLeftTipImage.setContentDescription(getString(i6));
                    updateImageBgColor(i2, this.mLeftTipImage, false);
                    if (this.mLeftTipImage.getTag() != null) {
                    }
                    if (z) {
                    }
                    this.mLeftTipImage.setTag(Integer.valueOf(checkLeftImageTipClose));
                    if (list != null) {
                    }
                    z2 = true;
                    if (z2) {
                    }
                }
                i4 = -1;
                checkLeftImageTipClose = checkLeftImageTipClose(i4);
                i5 = R.drawable.ic_live_sticker_normal;
                if (checkLeftImageTipClose == -1) {
                }
                if (i5 > 0) {
                }
                this.mLeftTipImage.setContentDescription(getString(i6));
                updateImageBgColor(i2, this.mLeftTipImage, false);
                if (this.mLeftTipImage.getTag() != null) {
                }
                if (z) {
                }
                this.mLeftTipImage.setTag(Integer.valueOf(checkLeftImageTipClose));
                if (list != null) {
                }
                z2 = true;
                if (z2) {
                }
            }
            if (currentCameraId == 0 && isNormalIntent && z3 && !CameraSettings.isAutoZoomEnabled(i2)) {
                if (this.mIsShowLeftImageIntro) {
                    startLeftImageIntroAnim(i);
                    return;
                }
                i4 = 21;
                checkLeftImageTipClose = checkLeftImageTipClose(i4);
                i5 = R.drawable.ic_live_sticker_normal;
                if (checkLeftImageTipClose == -1) {
                }
                if (i5 > 0) {
                }
                this.mLeftTipImage.setContentDescription(getString(i6));
                updateImageBgColor(i2, this.mLeftTipImage, false);
                if (this.mLeftTipImage.getTag() != null) {
                }
                if (z) {
                }
                this.mLeftTipImage.setTag(Integer.valueOf(checkLeftImageTipClose));
                if (list != null) {
                }
                z2 = true;
                if (z2) {
                }
            }
            i4 = -1;
            checkLeftImageTipClose = checkLeftImageTipClose(i4);
            i5 = R.drawable.ic_live_sticker_normal;
            if (checkLeftImageTipClose == -1) {
            }
            if (i5 > 0) {
            }
            this.mLeftTipImage.setContentDescription(getString(i6));
            updateImageBgColor(i2, this.mLeftTipImage, false);
            if (this.mLeftTipImage.getTag() != null) {
            }
            if (z) {
            }
            this.mLeftTipImage.setTag(Integer.valueOf(checkLeftImageTipClose));
            if (list != null) {
            }
            z2 = true;
            if (z2) {
            }
        }
        if (!CameraSettings.isMacroModeEnabled(i2) && !CameraSettings.isUltraPixelRearOn() && z3 && currentCameraId == 0) {
            if (this.mIsShowLeftImageIntro) {
                startLeftImageIntroAnim(i);
                return;
            }
            i4 = 21;
            checkLeftImageTipClose = checkLeftImageTipClose(i4);
            i5 = R.drawable.ic_live_sticker_normal;
            if (checkLeftImageTipClose == -1) {
            }
            if (i5 > 0) {
            }
            this.mLeftTipImage.setContentDescription(getString(i6));
            updateImageBgColor(i2, this.mLeftTipImage, false);
            if (this.mLeftTipImage.getTag() != null) {
            }
            if (z) {
            }
            this.mLeftTipImage.setTag(Integer.valueOf(checkLeftImageTipClose));
            if (list != null) {
            }
            z2 = true;
            if (z2) {
            }
        }
        i4 = -1;
        checkLeftImageTipClose = checkLeftImageTipClose(i4);
        i5 = R.drawable.ic_live_sticker_normal;
        if (checkLeftImageTipClose == -1) {
        }
        if (i5 > 0) {
        }
        this.mLeftTipImage.setContentDescription(getString(i6));
        updateImageBgColor(i2, this.mLeftTipImage, false);
        if (this.mLeftTipImage.getTag() != null) {
        }
        if (z) {
        }
        this.mLeftTipImage.setTag(Integer.valueOf(checkLeftImageTipClose));
        if (list != null) {
        }
        z2 = true;
        if (z2) {
        }
    }

    private void updateLightingPattern(boolean z, boolean z2) {
        if (z) {
            this.stringLightingRes = -1;
        }
        if (!DataRepository.dataItemRunning().getComponentRunningLighting().getComponentValue(171).equals("0")) {
            if (isLandScape()) {
                starAnimatetViewGone(this.mLightingPattern, z2);
            } else if (this.stringLightingRes > 0) {
                startAnimateViewVisible(this.mLightingPattern, z2);
            }
        }
    }

    private void updateSpeedTipImage(int i, int i2, List<Completable> list) {
        boolean z;
        boolean z2;
        int i3;
        int i4 = i != 174 ? -1 : 33;
        boolean z3 = false;
        if (i4 == -1) {
            z = true;
            i3 = 0;
            z2 = false;
        } else if (i4 != 33) {
            z2 = true;
            z = true;
            i3 = 0;
        } else {
            i3 = R.layout.bottom_popup_tips_center_live_speed;
            z2 = true;
            z = false;
        }
        boolean z4 = this.mSpeedTipImage.getTag() == null || ((Integer) this.mSpeedTipImage.getTag()).intValue() != i4;
        if (z4) {
            this.mSpeedTipImage.removeAllViews();
            if (i3 > 0) {
                this.mSpeedTipImage.addView(LayoutInflater.from(getContext()).inflate(i3, null));
            }
        }
        if (i4 == 33) {
            ((TextView) this.mSpeedTipImage.findViewById(R.id.live_speed_text)).setText(CameraSettings.getCurrentLiveSpeedText());
            ImageView imageView = (ImageView) this.mSpeedTipImage.findViewById(R.id.icon);
            if (CameraSettings.getCurrentLiveSpeed().equals(String.valueOf(2))) {
                imageView.setImageResource(R.drawable.ic_live_speed_normal);
            } else {
                imageView.setImageResource(R.drawable.ic_live_speed_mod);
            }
        }
        if (z4) {
            if (!z2 || !z) {
                ViewCompat.setRotation(this.mSpeedTipImage, 0.0f);
            } else {
                ViewCompat.setRotation(this.mSpeedTipImage, (float) this.mDegree);
            }
            this.mSpeedTipImage.setTag(Integer.valueOf(i4));
            if (list == null) {
                if (!z2) {
                    z3 = true;
                }
            } else if (!z2) {
                z3 = true;
            } else if (i2 != 163) {
                z3 = true;
            } else if (!b.isSupportedOpticalZoom()) {
                z3 = true;
            }
            if (!z3) {
                AlphaInOnSubscribe.directSetResult(this.mSpeedTipImage);
            } else if (z3) {
                AlphaOutOnSubscribe.directSetResult(this.mSpeedTipImage);
            } else if (z3) {
                list.add(Completable.create(new AlphaInOnSubscribe(this.mSpeedTipImage)));
            } else if (z3) {
                list.add(Completable.create(new AlphaInOnSubscribe(this.mSpeedTipImage).setStartDelayTime(150).setDurationTime(150)));
            } else if (z3) {
                list.add(Completable.create(new AlphaOutOnSubscribe(this.mSpeedTipImage)));
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002a, code lost:
        if (com.android.camera.CameraSettings.isSuperEISEnabled(r12) == false) goto L_0x003c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0039, code lost:
        if (com.android.camera.data.DataRepository.dataItemRunning().supportPopShineEntry() != false) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0044, code lost:
        if (com.android.camera.data.DataRepository.dataItemRunning().supportPopShineEntry() == false) goto L_0x006c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0046, code lost:
        r3 = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0050, code lost:
        if (com.android.camera.data.DataRepository.dataItemRunning().supportPopShineEntry() != false) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0069, code lost:
        if (com.android.camera.data.DataRepository.dataItemRunning().supportPopShineEntry() == false) goto L_0x006c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00ef, code lost:
        if (r9 != false) goto L_0x00f1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00a2  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00db  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00ef  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00f5  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x010e  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0154  */
    private void updateTipImage(int i, int i2, List<Completable> list) {
        int i3;
        boolean z;
        int i4;
        int i5;
        boolean z2;
        if (i != 165) {
            if (i != 171) {
                if (i != 174) {
                    if (i != 176) {
                        if (i != 177) {
                            switch (i) {
                                case 162:
                                    if (!CameraSettings.isAutoZoomEnabled(i)) {
                                        if (!CameraSettings.isMacroModeEnabled(i)) {
                                            break;
                                        }
                                    }
                                    break;
                                case 161:
                                    break;
                                case 163:
                                    break;
                            }
                        } else {
                            updateCenterTipImage(i, i2, list);
                        }
                    }
                    i3 = -1;
                    boolean z3 = true;
                    if (i3 == -1) {
                        if (i3 == 34) {
                            i5 = DataRepository.dataItemRunning().getComponentRunningShine().getBottomEntryRes(i);
                        } else if (i3 == 2) {
                            i5 = R.drawable.ic_beauty_sticker;
                        } else if (i3 != 3) {
                            z = true;
                            i5 = 0;
                            i4 = 0;
                        } else {
                            i5 = DataRepository.dataItemRunning().getComponentRunningShine().getBottomEntryRes(i);
                            i4 = R.string.accessibility_beauty_panel_open;
                            z = true;
                        }
                        z = true;
                        i4 = 0;
                    } else {
                        i5 = 0;
                        i4 = 0;
                        z = false;
                    }
                    if (i5 > 0) {
                        this.mTipImage.setImageResource(i5);
                    }
                    if (i4 > 0 && Util.isAccessible()) {
                        this.mTipImage.setContentDescription(getString(i4));
                    }
                    updateImageBgColor(i, this.mTipImage, false);
                    if (this.mTipImage.getTag() != null || ((Integer) this.mTipImage.getTag()).intValue() != i3) {
                        if (z) {
                            ViewCompat.setRotation(this.mTipImage, (float) this.mDegree);
                        }
                        this.mTipImage.setTag(Integer.valueOf(i3));
                        if (list == null) {
                            if (!z) {
                                if (!(i2 == 165 || this.mCurrentMode == 165)) {
                                    z2 = true;
                                }
                                z2 = true;
                            } else if (i2 != 165) {
                                z2 = true;
                            } else if (!b.isSupportedOpticalZoom()) {
                                z2 = true;
                            }
                            if (!z2) {
                                ActionProcessing actionProcessing = (ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
                                if (actionProcessing == null || !actionProcessing.isShowLightingView()) {
                                    z3 = false;
                                }
                                if (z3) {
                                    this.mTipImage.setTag(Integer.valueOf(-1));
                                }
                                if (!z3) {
                                    AlphaInOnSubscribe.directSetResult(this.mTipImage);
                                }
                            } else if (z2) {
                                AlphaOutOnSubscribe.directSetResult(this.mTipImage);
                            } else if (z2) {
                                list.add(Completable.create(new AlphaInOnSubscribe(this.mTipImage)));
                            } else if (z2) {
                                list.add(Completable.create(new AlphaInOnSubscribe(this.mTipImage).setStartDelayTime(150).setDurationTime(150)));
                            } else if (z2) {
                                list.add(Completable.create(new AlphaOutOnSubscribe(this.mTipImage)));
                            }
                        }
                        z2 = false;
                        if (!z2) {
                        }
                    }
                    return;
                }
            }
        }
        if (!CameraSettings.isMacroModeEnabled(i)) {
            if (!CameraSettings.isUltraPixelPortraitFrontOn()) {
            }
        }
        i3 = -1;
        boolean z32 = true;
        if (i3 == -1) {
        }
        if (i5 > 0) {
        }
        this.mTipImage.setContentDescription(getString(i4));
        updateImageBgColor(i, this.mTipImage, false);
        if (this.mTipImage.getTag() != null) {
        }
        if (z) {
        }
        this.mTipImage.setTag(Integer.valueOf(i3));
        if (list == null) {
        }
        z2 = false;
        if (!z2) {
        }
    }

    public boolean containTips(@StringRes int i) {
        return this.mTipMessage.getVisibility() == 0 && getString(i).equals(this.mTipMessage.getText().toString());
    }

    public void directHideCenterTipImage() {
        ImageView imageView = this.mCenterTipImage;
        if (imageView != null && imageView.getVisibility() != 4) {
            this.mCenterTipImage.setVisibility(8);
        }
    }

    public void directHideLyingDirectHint() {
        this.mLyingDirectHint.setVisibility(8);
    }

    public void directHideTipImage() {
        if (this.mTipImage.getVisibility() != 4) {
            this.mTipImage.setTag(Integer.valueOf(-1));
            this.mTipImage.setVisibility(4);
        }
    }

    public void directShowLeftImageIntro() {
        if (CameraSettings.isShowUltraWideIntro()) {
            this.mIsShowLeftImageIntro = true;
        }
        int i = this.mCurrentMode;
        updateLeftTipImage(0, i, i, null);
    }

    public void directShowOrHideLeftTipImage(boolean z) {
        ImageView imageView = this.mLeftTipImage;
        if (imageView != null) {
            if (z) {
                updateLeftTipImage();
                this.mLeftTipImage.setVisibility(0);
            } else {
                imageView.setTag(Integer.valueOf(-1));
                this.mLeftTipImage.setVisibility(4);
            }
        }
    }

    public void directlyHideTips() {
        ViewCompat.animate(this.mTipMessage).cancel();
        this.mHandler.removeCallbacksAndMessages(null);
        if (this.mTipMessage.getVisibility() == 0) {
            this.mTipMessage.setVisibility(8);
            CameraModuleSpecial cameraModuleSpecial = (CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195);
            if (cameraModuleSpecial != null) {
                cameraModuleSpecial.showOrHideChip(true);
            }
            if (this.mLastTipType == 6 && !isPortraitHintVisible()) {
                showTips(this.mLastTipType, this.mLastTipMessage, 4);
            }
            this.mLastTipType = 4;
        }
    }

    public void directlyShowTips(int i, @StringRes int i2) {
        ViewCompat.animate(this.mTipMessage).cancel();
        this.mHandler.removeCallbacksAndMessages(null);
        if (this.mTipMessage.getVisibility() != 0) {
            this.mLastTipType = this.mCurrentTipType;
            this.mLastTipMessage = this.mCurrentTipMessage;
            this.mCurrentTipType = i;
            this.mCurrentTipMessage = getString(i2);
            AlphaInOnSubscribe.directSetResult(this.mTipMessage);
            this.mTipMessage.setText(i2);
        }
    }

    public String getCurrentBottomTipMsg() {
        return this.mCurrentTipMessage;
    }

    public int getCurrentBottomTipType() {
        return this.mCurrentTipType;
    }

    public int getFragmentInto() {
        return 65522;
    }

    /* access modifiers changed from: protected */
    public int getLayoutResourceId() {
        return R.layout.fragment_bottom_popup_tips;
    }

    public void hideCenterTipImage() {
        ImageView imageView = this.mCenterTipImage;
        if (imageView != null && imageView.getVisibility() != 4) {
            this.mCenterTipImage.setTag(Integer.valueOf(-1));
            Completable.create(new AlphaOutOnSubscribe(this.mCenterTipImage)).subscribe();
            View view = this.mCenterRedDot;
            if (view != null) {
                view.setVisibility(8);
            }
        }
    }

    public void hideLeftTipImage() {
        ImageView imageView = this.mLeftTipImage;
        if (imageView != null && imageView.getVisibility() != 4) {
            this.mLeftTipImage.setTag(Integer.valueOf(-1));
            Completable.create(new AlphaOutOnSubscribe(this.mLeftTipImage)).subscribe();
        }
    }

    public void hideMimojiTip() {
        if (this.mMimojiTextview.getVisibility() != 8) {
            this.mMimojiTextview.setVisibility(8);
        }
    }

    public void hideQrCodeTip() {
        if (this.mQrCodeButton.getVisibility() != 8) {
            this.mQrCodeButton.setVisibility(8);
            String tag = getTag();
            StringBuilder sb = new StringBuilder();
            sb.append("  hideQrCodeTip  time  : ");
            sb.append(System.currentTimeMillis());
            Log.w(tag, sb.toString());
        }
    }

    public void hideSpeedTipImage() {
        ViewGroup viewGroup = this.mSpeedTipImage;
        if (viewGroup != null && viewGroup.getVisibility() != 4) {
            this.mSpeedTipImage.setTag(Integer.valueOf(-1));
            Completable.create(new AlphaOutOnSubscribe(this.mSpeedTipImage)).subscribe();
        }
    }

    public void hideTipImage() {
        ImageView imageView = this.mTipImage;
        if (imageView != null && imageView.getVisibility() != 4) {
            this.mTipImage.setTag(Integer.valueOf(-1));
            Completable.create(new AlphaOutOnSubscribe(this.mTipImage)).subscribe();
        }
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        this.mRootView = view;
        this.mTipImage = (ImageView) view.findViewById(R.id.popup_tip_image);
        ((FrameLayout.LayoutParams) this.mTipImage.getLayoutParams()).gravity = 8388693;
        this.mLeftTipImage = (ImageView) view.findViewById(R.id.popup_left_tip_image);
        ((FrameLayout.LayoutParams) this.mLeftTipImage.getLayoutParams()).gravity = 8388691;
        this.mLeftTipImage.setImageResource(R.drawable.ic_new_effect_button_normal);
        this.mLeftTipImage.setOnClickListener(this);
        this.mSpeedTipImage = (ViewGroup) view.findViewById(R.id.popup_speed_tip_image);
        this.mSpeedTipImage.setOnClickListener(this);
        ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.popup_center_tip_layout);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
        if (!HybridZoomingSystem.IS_3_OR_MORE_SAT || !CameraSettings.isSupportedOpticalZoom()) {
            layoutParams.gravity = 81;
        } else {
            layoutParams.gravity = 8388691;
        }
        this.mCenterTipImage = (ImageView) viewGroup.findViewById(R.id.popup_center_tip_image);
        this.mCenterTipImage.setOnClickListener(this);
        this.mCenterRedDot = viewGroup.findViewById(R.id.popup_center_red_dot);
        this.mLeftImageIntro = (FrameLayout) view.findViewById(R.id.popup_left_tip_intro);
        this.mLeftImageIntro.setOnClickListener(this);
        this.mLeftImageIntroContent = (TextView) view.findViewById(R.id.popup_left_tip_intro_text);
        this.mLeftImageIntroRadius = getResources().getDimensionPixelSize(R.dimen.popup_tip_beauty_intro_radius);
        this.mLeftImageIntroWidth = getLeftImageIntroWide();
        this.mQrCodeButton = (TextView) view.findViewById(R.id.popup_tips_qrcode);
        this.mMimojiTextview = (TextView) view.findViewById(R.id.popup_tips_mimoji);
        this.mLyingDirectHint = (TextView) view.findViewById(R.id.bottom_lying_direct_hint_text);
        this.mTipMessage = (TextView) view.findViewById(R.id.popup_tips_message);
        this.mPortraitSuccessHint = view.findViewById(R.id.portrait_success_hint);
        this.mLightingPattern = (TextView) view.findViewById(R.id.lighting_pattern);
        ((MarginLayoutParams) view.getLayoutParams()).bottomMargin = Util.getBottomHeight(getResources());
        this.mTipImage.setOnClickListener(this);
        this.mQrCodeButton.setOnClickListener(this);
        adjustViewBackground(this.mCurrentMode);
        provideAnimateElement(this.mCurrentMode, null, 2);
        if (((ActivityBase) getContext()).getCameraIntentManager().isFromScreenSlide().booleanValue()) {
            Util.startScreenSlideAlphaInAnimation(this.mTipImage);
        }
        this.mCloseIv = (ImageView) view.findViewById(R.id.close_iv);
        this.mCloseIv.setOnClickListener(this);
        this.mBottomTipHeight = getResources().getDimensionPixelSize(R.dimen.portrait_hint_height);
    }

    public boolean isLightingHintVisible() {
        VerticalProtocol verticalProtocol = (VerticalProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(198);
        return (verticalProtocol != null ? verticalProtocol.isAnyViewVisible() : false) || this.mLightingPattern.getVisibility() == 0;
    }

    public boolean isPortraitHintVisible() {
        return this.mPortraitSuccessHint.getVisibility() == 0;
    }

    public boolean isQRTipVisible() {
        TextView textView = this.mQrCodeButton;
        return textView != null && textView.getVisibility() == 0;
    }

    public boolean isTipShowing() {
        TextView textView = this.mTipMessage;
        return textView != null && textView.getVisibility() == 0;
    }

    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        reConfigBottomTipOfUltraWide();
        reConfigBottomTipOfMacro();
        reConfigBottomTipOf960FPS();
        reConfigBottomTipOfMimoji();
    }

    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
        if (i == 2) {
            directlyHideTips();
        } else if (i == 3) {
            adjustViewBackground(this.mCurrentMode);
        }
        int i3 = this.mCurrentMode;
        updateTipImage(i3, i3, null);
        int i4 = this.mCurrentMode;
        updateLeftTipImage(1, i4, i4, null);
        int i5 = this.mCurrentMode;
        updateSpeedTipImage(i5, i5, null);
        int i6 = this.mCurrentMode;
        updateCenterTipImage(i6, i6, null);
    }

    public boolean onBackEvent(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    int i2 = this.mCurrentTipType;
                    if (i2 == 6 || i2 == 7 || i2 == 11 || i2 == 9 || i2 == 12 || i2 == 18 || i2 == 10 || i2 == 13 || i2 == 17) {
                        return false;
                    }
                }
                hideTip(this.mTipMessage);
                hideTip(this.mPortraitSuccessHint);
                hideTip(this.mQrCodeButton);
                hideTip(this.mLightingPattern);
                this.mHandler.removeCallbacksAndMessages(null);
            }
            return false;
        }
        if (this.mCurrentTipType == 9) {
            return false;
        }
        hideTip(this.mTipMessage);
        hideTip(this.mPortraitSuccessHint);
        hideTip(this.mQrCodeButton);
        hideTip(this.mLightingPattern);
        this.mHandler.removeCallbacksAndMessages(null);
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00af, code lost:
        onLeftImageClick(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00b3, code lost:
        r5 = ((java.lang.Integer) r5.getTag()).intValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00bf, code lost:
        if (r5 == 18) goto L_0x00cd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00c3, code lost:
        if (r5 == 34) goto L_0x00c6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00c6, code lost:
        hideAllTipImage();
        showMimojiPanel();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00cd, code lost:
        hideAllTipImage();
        showLiveSticker();
        r4 = r4.mCenterRedDot;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00d5, code lost:
        if (r4 == null) goto L_0x00f0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00d7, code lost:
        r4.setVisibility(8);
        com.android.camera.CameraSettings.setTTLiveStickerNeedRedDot(false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00f0, code lost:
        return;
     */
    public void onClick(View view) {
        if (isEnableClick()) {
            CameraAction cameraAction = (CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            if (cameraAction != null && cameraAction.isDoingAction()) {
                return;
            }
            if (!CameraSettings.isFrontCamera() || !((Camera) getContext()).isScreenSlideOff()) {
                int id = view.getId();
                String str = CameraStat.CATEGORY_COUNTER;
                switch (id) {
                    case R.id.close_iv /*2131296314*/:
                        int i = this.mCloseType;
                        if (i != 1) {
                            if (i == 2) {
                                closeLight();
                                break;
                            }
                        } else {
                            closeFilter();
                            break;
                        }
                        break;
                    case R.id.popup_center_tip_image /*2131296495*/:
                        break;
                    case R.id.popup_left_tip_image /*2131296497*/:
                        break;
                    case R.id.popup_left_tip_intro /*2131296498*/:
                        view.setTag(Integer.valueOf(21));
                        CameraSettings.setPopupUltraWideIntroClicked();
                        directHideLeftImageIntro();
                        break;
                    case R.id.popup_speed_tip_image /*2131296500*/:
                        if (((Integer) view.getTag()).intValue() == 33) {
                            hideAllTipImage();
                            showLiveSpeed();
                            break;
                        }
                        break;
                    case R.id.popup_tip_image /*2131296501*/:
                        int intValue = ((Integer) view.getTag()).intValue();
                        hideAllTipImage();
                        CameraSettings.setPopupTipBeautyIntroClicked();
                        BaseDelegate baseDelegate = (BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
                        if (intValue != 2) {
                            if (intValue == 3) {
                                hideQrCodeTip();
                                CameraStat.recordCountEvent(str, CameraStat.KEY_BEAUTY_BUTTON);
                                showBeauty(this.mCurrentMode);
                                break;
                            }
                        } else {
                            baseDelegate.delegateEvent(4);
                            break;
                        }
                        break;
                    case R.id.popup_tips_qrcode /*2131296504*/:
                        hideQrCodeTip();
                        CameraStat.recordCountEvent(str, CameraStat.KEY_QRCODE_DETECTED);
                        ((CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195)).showQRCodeResult();
                        break;
                }
            }
        }
    }

    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        if (i2 == 3 || this.mCurrentMode != i) {
            this.mCloseIv.setVisibility(8);
            this.mCloseType = 0;
        }
        int i3 = this.mCurrentMode;
        super.provideAnimateElement(i, list, i2);
        if (isInModeChanging() || i2 == 3) {
            this.mIsShowLyingDirectHint = false;
            directHideLyingDirectHint();
        }
        onBackEvent(4);
        updateTipBottomMargin(0, false);
        updateTipImage(i, i3, list);
        updateLeftTipImage(0, i, i3, list);
        updateSpeedTipImage(i, i3, list);
        updateCenterTipImage(i, i3, list);
    }

    /* access modifiers changed from: protected */
    public Animation provideEnterAnimation(int i) {
        if (i == 240 || i == getFragmentInto()) {
            return null;
        }
        return FragmentAnimationFactory.wrapperAnimation(161);
    }

    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        if (this.mTipImage.getVisibility() == 0) {
            list.add(this.mTipImage);
        }
        ImageView imageView = this.mLeftTipImage;
        if (imageView != null && imageView.getVisibility() == 0) {
            list.add(this.mLeftTipImage);
        }
        ImageView imageView2 = this.mCenterTipImage;
        if (imageView2 != null && imageView2.getVisibility() == 0) {
            list.add(this.mCenterTipImage);
        }
        ViewGroup viewGroup = this.mSpeedTipImage;
        if (!(viewGroup == null || viewGroup.getVisibility() != 0 || ((Integer) this.mSpeedTipImage.getTag()).intValue() == 33)) {
            list.add(this.mSpeedTipImage);
        }
        ImageView imageView3 = this.mCenterTipImage;
        if (imageView3 != null && imageView3.getVisibility() == 0) {
            list.add(this.mCenterTipImage);
        }
        updateLightingPattern(false, true);
    }

    public void reConfigBottomTipOfUltraWide() {
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            int i = this.mCurrentMode;
            if (!(163 == i || 165 == i || 162 == i || 169 == i || 174 == i || 161 == i)) {
                return;
            }
        } else {
            int i2 = this.mCurrentMode;
            if (!(163 == i2 || 165 == i2 || 162 == i2)) {
                return;
            }
        }
        boolean isAutoZoomEnabled = CameraSettings.isAutoZoomEnabled(this.mCurrentMode);
        if ((162 != this.mCurrentMode || !isAutoZoomEnabled) && !CameraSettings.isSuperEISEnabled(this.mCurrentMode) && !CameraSettings.isMacroModeEnabled(this.mCurrentMode)) {
            if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                if (HybridZoomingSystem.toFloat(HybridZoomingSystem.getZoomRatioHistory(this.mCurrentMode, "1.0"), 1.0f) >= 1.0f) {
                    return;
                }
            } else if (!CameraSettings.isUltraWideConfigOpen(this.mCurrentMode)) {
                return;
            }
            MiBeautyProtocol miBeautyProtocol = (MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            if (miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow()) {
                if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                    showTips(13, (int) R.string.ultra_wide_open_tip, 4);
                } else if (CameraSettings.shouldShowUltraWideSatTip(this.mCurrentMode)) {
                    showTips(13, (int) R.string.ultra_wide_open_tip_sat, 2);
                }
            }
        }
    }

    public boolean reConfigQrCodeTip() {
        if (this.mCurrentMode == 163) {
            BottomPopupTips bottomPopupTips = (BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            boolean z = bottomPopupTips != null && bottomPopupTips.isTipShowing() && (TextUtils.equals(this.mCurrentTipMessage, getString(R.string.ultra_wide_recommend_tip_hint)) || TextUtils.equals(this.mCurrentTipMessage, getString(R.string.ultra_wide_recommend_tip_hint_sat)));
            boolean z2 = HybridZoomingSystem.toDecimal(CameraSettings.readZoom()) == 0.6f || CameraSettings.isUltraWideConfigOpen(this.mCurrentMode);
            DualController dualController = (DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
            boolean z3 = dualController != null && dualController.isSlideVisible();
            MakeupProtocol makeupProtocol = (MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
            boolean z4 = makeupProtocol != null && makeupProtocol.isSeekBarVisible();
            MiBeautyProtocol miBeautyProtocol = (MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            boolean z5 = miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow();
            TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            int currentAiSceneLevel = topAlert.getCurrentAiSceneLevel();
            boolean z6 = topAlert.getAlertIsShow() && (currentAiSceneLevel == -1 || currentAiSceneLevel == 23 || currentAiSceneLevel == 24 || currentAiSceneLevel == 35 || currentAiSceneLevel == -35);
            if (CameraSettings.isTiltShiftOn() || CameraSettings.isGroupShotOn() || CameraSettings.isGradienterOn() || z2 || z5 || z4 || z3 || z || z6) {
                hideQrCodeTip();
                return true;
            }
        }
        return false;
    }

    public void reInitTipImage() {
        provideAnimateElement(this.mCurrentMode, null, 2);
        reConfigBottomTipOfMacro();
        reConfigBottomTipOf960FPS();
    }

    /* access modifiers changed from: protected */
    public void register(ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(175, this);
        registerBackStack(modeCoordinator, this);
        boolean z = DataRepository.dataItemGlobal().getBoolean("pref_camera_first_ultra_wide_use_hint_shown_key", true);
        if (CameraSettings.isShowUltraWideIntro() && !z) {
            this.mIsShowLeftImageIntro = true;
        }
    }

    public void selectBeautyTipImage(boolean z) {
        if (z) {
            this.mTipImage.setImageResource(R.drawable.ic_beauty_on);
        } else {
            this.mTipImage.setImageResource(R.drawable.ic_beauty_normal);
        }
    }

    public void setLightingPattern(String str) {
        char c2;
        this.stringLightingRes = -1;
        switch (str.hashCode()) {
            case 48:
                if (str.equals("0")) {
                    c2 = 0;
                    break;
                }
            case 49:
                if (str.equals("1")) {
                    c2 = 1;
                    break;
                }
            case 50:
                if (str.equals("2")) {
                    c2 = 2;
                    break;
                }
            case 51:
                if (str.equals("3")) {
                    c2 = 3;
                    break;
                }
            case 52:
                if (str.equals("4")) {
                    c2 = 4;
                    break;
                }
            case 53:
                if (str.equals("5")) {
                    c2 = 5;
                    break;
                }
            case 54:
                if (str.equals("6")) {
                    c2 = 6;
                    break;
                }
            case 55:
                if (str.equals("7")) {
                    c2 = 7;
                    break;
                }
            case 56:
                if (str.equals("8")) {
                    c2 = 8;
                    break;
                }
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
                this.stringLightingRes = -1;
                break;
            case 1:
                this.stringLightingRes = R.string.lighting_pattern_nature;
                break;
            case 2:
                this.stringLightingRes = R.string.lighting_pattern_stage;
                break;
            case 3:
                this.stringLightingRes = R.string.lighting_pattern_movie;
                break;
            case 4:
                this.stringLightingRes = R.string.lighting_pattern_rainbow;
                break;
            case 5:
                this.stringLightingRes = R.string.lighting_pattern_shutter;
                break;
            case 6:
                this.stringLightingRes = R.string.lighting_pattern_dot;
                break;
            case 7:
                this.stringLightingRes = R.string.lighting_pattern_leaf;
                break;
            case 8:
                this.stringLightingRes = R.string.lighting_pattern_holi;
                break;
        }
        if (this.stringLightingRes == -1) {
            AlphaOutOnSubscribe.directSetResult(this.mLightingPattern);
            return;
        }
        this.mCurrentTipType = 12;
        hideTip(this.mTipMessage);
        hideTip(this.mPortraitSuccessHint);
        directHideTipImage();
        reIntTipViewMarginBottom(this.mLightingPattern, this.mBottomTipHeight);
        this.mLightingPattern.setText(this.stringLightingRes);
        if (!isLandScape()) {
            AlphaInOnSubscribe.directSetResult(this.mLightingPattern);
        }
    }

    public void setPortraitHintVisible(int i) {
        if (i != 0 || !isLightingHintVisible()) {
            this.mLastTipType = i == 0 ? 7 : 4;
            directlyHideTips();
            if (i == 0) {
                reIntTipViewMarginBottom(this.mPortraitSuccessHint, this.mBottomTipHeight);
            }
            this.mPortraitSuccessHint.setVisibility(i);
        }
    }

    public void showCloseTip(int i, boolean z) {
        if (!z) {
            this.mCloseType = 0;
        } else if (this.mCurrentMode != 167) {
            this.mCloseType = i;
        } else {
            return;
        }
        showOrHideCloseImage(z);
    }

    public void showMimoji() {
        DataRepository.dataItemLive().getMimojiStatusManager().setMimojiPannelState(true);
        hideAllTipImage();
        showMimojiPanel();
    }

    public void showMimojiTip() {
        if (this.mMimojiTextview.getVisibility() != 0) {
            hideTip(this.mTipMessage);
            TextView textView = this.mMimojiTextview;
            reIntTipViewMarginBottom(textView, textView.getBackground().getIntrinsicHeight());
            AlphaInOnSubscribe.directSetResult(this.mMimojiTextview);
            Completable.create(new AlphaOutOnSubscribe(this.mMimojiTextview).setStartDelayTime(3000)).subscribe();
            if (Util.isAccessible()) {
                this.mMimojiTextview.postDelayed(new Runnable() {
                    public void run() {
                        FragmentBottomPopupTips.this.mMimojiTextview.sendAccessibilityEvent(128);
                    }
                }, 100);
            }
        }
    }

    public void showOrHideCloseImage(boolean z) {
        ImageView imageView = this.mLeftTipImage;
        if (imageView != null) {
            imageView.setImageResource(R.drawable.ic_manually_indicator);
            if (z) {
                if (Util.isAccessible() && this.mCloseType == 2) {
                    this.mLeftTipImage.setContentDescription(getString(R.string.accessibility_lighting_panel_off));
                }
                this.mLeftTipImage.setTag(Integer.valueOf(20));
                Completable.create(new AlphaInOnSubscribe(this.mLeftTipImage)).subscribe();
            } else {
                this.mLeftTipImage.setTag(Integer.valueOf(-1));
                Completable.create(new AlphaOutOnSubscribe(this.mLeftTipImage)).subscribe();
            }
        }
    }

    public void showQrCodeTip() {
        if (!reConfigQrCodeTip() && this.mQrCodeButton.getVisibility() != 0) {
            directHideLyingDirectHint();
            hideTip(this.mTipMessage);
            TextView textView = this.mQrCodeButton;
            reIntTipViewMarginBottom(textView, textView.getBackground().getIntrinsicHeight());
            AlphaInOnSubscribe.directSetResult(this.mQrCodeButton);
            if (Util.isAccessible()) {
                this.mQrCodeButton.postDelayed(new Runnable() {
                    public void run() {
                        FragmentBottomPopupTips.this.mQrCodeButton.sendAccessibilityEvent(128);
                    }
                }, 100);
            }
        }
    }

    public void showTips(int i, @StringRes int i2, int i3) {
        showTips(i, getString(i2), i3);
    }

    public void showTips(final int i, final int i2, final int i3, int i4) {
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                FragmentBottomPopupTips.this.showTips(i, i2, i3);
            }
        }, (long) i4);
    }

    public void showTips(int i, String str, int i2) {
        int i3;
        if (i == 6 && this.mCurrentMode != 171) {
            return;
        }
        if (!(i == 10 && CameraSettings.getBogusCameraId() == 0) && !isLightingHintVisible()) {
            if (isPortraitSuccessHintVisible()) {
                hideTip(this.mPortraitSuccessHint);
            }
            this.mLastTipType = this.mCurrentTipType;
            this.mLastTipMessage = this.mCurrentTipMessage;
            if (!TextUtils.equals(this.mLastTipMessage, getString(R.string.mimoji_start_create)) || !TextUtils.equals(str, getString(R.string.mimoji_check_normal))) {
                this.mCurrentTipType = i;
                this.mCurrentTipMessage = str;
                hideTip(this.mQrCodeButton);
                directHideLyingDirectHint();
                reIntTipViewMarginBottom(this.mTipMessage, this.mBottomTipHeight);
                AlphaInOnSubscribe.directSetResult(this.mTipMessage);
                this.mTipMessage.setText(str);
                if (Util.isAccessible()) {
                    this.mTipMessage.setContentDescription(this.mCurrentTipMessage);
                    this.mTipMessage.postDelayed(new Runnable() {
                        public void run() {
                            if (FragmentBottomPopupTips.this.isAdded()) {
                                FragmentBottomPopupTips.this.mTipMessage.sendAccessibilityEvent(4);
                            }
                        }
                    }, 3000);
                }
                switch (i2) {
                    case 1:
                        i3 = 1000;
                        break;
                    case 2:
                        i3 = 5000;
                        break;
                    case 3:
                        i3 = DurationConstant.DURATION_VIDEO_RECORDING_FUN;
                        break;
                    case 5:
                        i3 = 2000;
                        break;
                    case 6:
                        i3 = 3000;
                        break;
                    case 7:
                        i3 = DurationConstant.DURATION_LANDSCAPE_HINT;
                        break;
                    default:
                        i3 = 0;
                        break;
                }
                this.mHandler.removeCallbacksAndMessages(null);
                if (i3 > 0) {
                    this.mHandler.sendEmptyMessageDelayed(1, (long) i3);
                }
                if (this.mCurrentMode == 163) {
                    CameraModuleSpecial cameraModuleSpecial = (CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195);
                    if (cameraModuleSpecial != null) {
                        cameraModuleSpecial.showOrHideChip(false);
                    }
                }
            }
        }
    }

    public void showTipsWithOrientation(int i, int i2, int i3, int i4, int i5) {
        if (i5 == 0) {
            showTips(i, i2, i3, i4);
        } else if (i5 != 1) {
            if (i5 == 2 && !isLandScape()) {
                showTips(i, i2, i3, i4);
            }
        } else if (isLandScape()) {
            showTips(i, i2, i3, i4);
        }
    }

    /* access modifiers changed from: protected */
    public void unRegister(ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        this.mHandler.removeCallbacksAndMessages(null);
        modeCoordinator.detachProtocol(175, this);
        unRegisterBackStack(modeCoordinator, this);
        this.mIsShowLeftImageIntro = false;
    }

    public void updateLeftTipImage() {
        int i = this.mCurrentMode;
        updateLeftTipImage(1, i, i, null);
    }

    public void updateLyingDirectHint(boolean z, boolean z2) {
        if (!z2) {
            this.mIsShowLyingDirectHint = z;
        }
        if (this.mIsShowLyingDirectHint) {
            DualController dualController = (DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
            boolean z3 = true;
            boolean z4 = dualController != null && dualController.isSlideVisible();
            MakeupProtocol makeupProtocol = (MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
            boolean z5 = makeupProtocol != null && makeupProtocol.isSeekBarVisible();
            MiBeautyProtocol miBeautyProtocol = (MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            if (miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow()) {
                z3 = false;
            }
            if (isTipShowing() || z4 || z5 || z3 || this.mQrCodeButton.getVisibility() == 0 || this.mLyingDirectHint.getVisibility() == 0) {
                this.mLyingDirectHint.setVisibility(8);
                return;
            }
            this.mLyingDirectHint.setRotation(180.0f);
            reIntTipViewMarginBottom(this.mLyingDirectHint, this.mBottomTipHeight);
            this.mLyingDirectHint.setVisibility(0);
            CameraStatUtil.trackLyingDirectShow(180);
        } else if (this.mLyingDirectHint.getVisibility() == 0) {
            this.mLyingDirectHint.setVisibility(8);
        }
    }

    public void updateTipBottomMargin(int i, boolean z) {
        if (this.mRootView.getPaddingTop() < i) {
            this.mRootView.setPadding(0, (int) (((float) i) * 1.2f), 0, 0);
        }
        if (!z) {
            TranslateYOnSubscribe.directSetResult(this.mRootView, -i);
        } else if (((float) i) < ViewCompat.getTranslationY(this.mRootView)) {
            Completable.create(new TranslateYOnSubscribe(this.mRootView, -i).setInterpolator(new OvershootInterpolator())).subscribe();
        } else {
            Completable.create(new TranslateYOnSubscribe(this.mRootView, -i).setInterpolator(new BackEaseOutInterpolator())).subscribe();
        }
    }

    public void updateTipImage() {
        int i = this.mCurrentMode;
        updateTipImage(i, i, null);
    }
}
