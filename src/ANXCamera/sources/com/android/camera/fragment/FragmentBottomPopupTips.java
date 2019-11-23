package com.android.camera.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStat;
import com.android.camera.statistic.CameraStatUtil;
import com.mi.config.b;
import io.reactivex.Completable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import miui.view.animation.BackEaseOutInterpolator;

public class FragmentBottomPopupTips extends BaseFragment implements View.OnClickListener, ModeProtocol.BottomPopupTips, ModeProtocol.HandleBackTrace {
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
                    ModeProtocol.CameraModuleSpecial cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195);
                    if (cameraModuleSpecial != null) {
                        cameraModuleSpecial.showOrHideChip(true);
                    }
                }
                if (FragmentBottomPopupTips.this.mLastTipType == 6 && FragmentBottomPopupTips.this.mCurrentTipType != 8 && !FragmentBottomPopupTips.this.isPortraitHintVisible()) {
                    FragmentBottomPopupTips.this.showTips(FragmentBottomPopupTips.this.mLastTipType, FragmentBottomPopupTips.this.mLastTipMessage, 4);
                } else if (FragmentBottomPopupTips.this.mLastTipType == 10 && CameraSettings.isEyeLightOpen()) {
                    FragmentBottomPopupTips.this.showTips(FragmentBottomPopupTips.this.mLastTipType, FragmentBottomPopupTips.this.mLastTipMessage, 4);
                } else if ((FragmentBottomPopupTips.this.mLastTipType != 18 || !CameraSettings.isMacroModeEnabled(FragmentBottomPopupTips.this.mCurrentMode)) && (FragmentBottomPopupTips.this.mLastTipType != 8 || !CameraSettings.isMacroModeEnabled(FragmentBottomPopupTips.this.mCurrentMode))) {
                    FragmentBottomPopupTips.this.updateLyingDirectHint(false, true);
                }
                int unused = FragmentBottomPopupTips.this.mLastTipType = 4;
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
        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        if (actionProcessing == null || !actionProcessing.isShowLightingView()) {
            return i;
        }
        return 20;
    }

    private void closeFilter() {
        showCloseTip(1, false);
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            configChanges.showOrHideFilter();
        }
    }

    private void closeLight() {
        showCloseTip(2, false);
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            configChanges.showOrHideLighting(false);
        }
        updateLeftTipImage();
    }

    /* access modifiers changed from: private */
    public void directHideLeftImageIntro() {
        this.mIsShowLeftImageIntro = false;
        if (this.mLeftImageIntroAnimator != null) {
            this.mLeftImageIntroAnimator.cancel();
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
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        ModeProtocol.ManuallyAdjust manuallyAdjust = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181);
        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        ModeProtocol.BokehFNumberController bokehFNumberController = (ModeProtocol.BokehFNumberController) ModeCoordinatorImpl.getInstance().getAttachProtocol(210);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.beauty_extra_tip_bottom_margin);
        if (this.mCurrentMode == 165) {
            if (CameraSettings.isUltraWideConfigOpen(this.mCurrentMode) && ((this.mCenterTipImage != null && this.mCenterTipImage.getVisibility() != 0) || HybridZoomingSystem.IS_3_OR_MORE_SAT)) {
                return (getResources().getDimensionPixelSize(R.dimen.manually_indicator_layout_height) / 2) - (i / 2);
            }
            return getResources().getDimensionPixelSize(R.dimen.tips_margin_bottom_normal) + ((((int) (((float) Util.sWindowWidth) / 0.75f)) - Util.sWindowWidth) / 2);
        } else if (manuallyAdjust != null && manuallyAdjust.visibleHeight() > 0) {
            return manuallyAdjust.visibleHeight();
        } else {
            if (this.mCenterTipImage.getVisibility() == 0) {
                return (miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow()) ? this.mTipImage.getHeight() : getResources().getDimensionPixelSize(R.dimen.beauty_fragment_height) + dimensionPixelSize;
            }
            if (dualController != null && dualController.isZoomVisible()) {
                return dualController.visibleHeight();
            }
            if (bokehFNumberController != null && bokehFNumberController.isFNumberVisible()) {
                return bokehFNumberController.getBokehFButtonHeight();
            }
            ModeProtocol.MakeupProtocol makeupProtocol = (ModeProtocol.MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
            if (makeupProtocol == null || !makeupProtocol.isSeekBarVisible()) {
                return (miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow()) ? (getResources().getDimensionPixelSize(R.dimen.manually_indicator_layout_height) / 2) - (i / 2) : getResources().getDimensionPixelSize(R.dimen.beauty_fragment_height) + dimensionPixelSize;
            }
            return getResources().getDimensionPixelSize(R.dimen.beautycamera_popup_fragment_height) + getResources().getDimensionPixelSize(R.dimen.beauty_fragment_height);
        }
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

    private void hideZoomTipImage(int i) {
        switch (i) {
            case 161:
            case 162:
            case 174:
                break;
            case 163:
            case 165:
            case 173:
                break;
            case 166:
                if (!DataRepository.dataItemFeature().gN()) {
                    return;
                }
                break;
            default:
                return;
        }
        if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            return;
        }
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null) {
            dualController.hideZoomButton();
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.alertUpdateValue(2);
            }
        }
    }

    private boolean isPortraitSuccessHintVisible() {
        return this.mPortraitSuccessHint.getVisibility() == 0;
    }

    private void onLeftImageClick(View view) {
        int intValue = ((Integer) view.getTag()).intValue();
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        switch (intValue) {
            case 19:
                if (configChanges != null) {
                    configChanges.onConfigChanged(203);
                    return;
                }
                return;
            case 20:
                switch (this.mCloseType) {
                    case 1:
                        closeFilter();
                        return;
                    case 2:
                        closeLight();
                        return;
                    default:
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
        if (CameraSettings.isMacroModeEnabled(this.mCurrentMode)) {
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
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int tipBottomMargin = getTipBottomMargin(i);
        if (marginLayoutParams.bottomMargin != tipBottomMargin) {
            marginLayoutParams.bottomMargin = tipBottomMargin;
            view.setLayoutParams(marginLayoutParams);
        }
    }

    /* access modifiers changed from: private */
    public void setBeautyIntroButtonWidth(View view, int i) {
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = i;
            view.setLayoutParams(layoutParams);
        }
    }

    private void showBeauty(int i) {
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            configChanges.showOrHideShine();
        }
    }

    private void showLiveSpeed() {
        CameraStatUtil.trackLiveClick(CameraStat.KEY_LIVE_SPEED);
        ModeProtocol.BottomMenuProtocol bottomMenuProtocol = (ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197);
        if (bottomMenuProtocol != null) {
            bottomMenuProtocol.onSwitchLiveActionMenu(165);
        }
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            baseDelegate.delegateEvent(13);
        }
    }

    private void showLiveSticker() {
        CameraStatUtil.trackLiveClick(CameraStat.KEY_LIVE_STICKER);
        ModeProtocol.BottomMenuProtocol bottomMenuProtocol = (ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197);
        if (bottomMenuProtocol != null) {
            bottomMenuProtocol.onSwitchLiveActionMenu(164);
        }
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            baseDelegate.delegateEvent(12);
        }
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.directlyHideTips();
        }
    }

    private void showMimojiPanel() {
        ModeProtocol.BottomMenuProtocol bottomMenuProtocol = (ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197);
        if (bottomMenuProtocol != null) {
            bottomMenuProtocol.onSwitchLiveActionMenu(166);
        }
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            baseDelegate.delegateEvent(14);
        }
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
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
                ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        FragmentBottomPopupTips.this.setBeautyIntroButtonWidth(FragmentBottomPopupTips.this.mLeftImageIntro, ((Integer) valueAnimator.getAnimatedValue()).intValue());
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

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0053, code lost:
        if (com.android.camera.data.DataRepository.dataItemLive().getMimojiStatusManager().getMimojiPannelState() == false) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x012e, code lost:
        if (r5 != false) goto L_0x0131;
     */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0153  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0162  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x017b  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x018a  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0190  */
    /* JADX WARNING: Removed duplicated region for block: B:83:? A[RETURN, SYNTHETIC] */
    private void updateCenterTipImage(int i, int i2, List<Completable> list) {
        int i3;
        int i4;
        boolean z;
        int i5 = i;
        int i6 = i2;
        List<Completable> list2 = list;
        char c = 1;
        if (i5 != 174) {
            if (i5 == 177) {
                if (((ActivityBase) getActivity()).startFromKeyguard()) {
                    if (!this.INIT_TAG) {
                        this.INIT_TAG = true;
                        DataRepository.dataItemLive().getMimojiStatusManager().setCurrentMimojiState(FragmentMimoji.CLOSE_STATE);
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
        if (i3 != -1) {
            i4 = R.drawable.ic_live_sticker_normal;
            if (i3 == 18) {
                if (!"".equals(CameraSettings.getCurrentLiveSticker())) {
                    i4 = R.drawable.ic_live_sticker_on;
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
                } else {
                    layoutParams.gravity = 8388691;
                }
            } else if (i3 != 34) {
                z = true;
                i4 = 0;
            } else {
                String currentMimojiState = DataRepository.dataItemLive().getMimojiStatusManager().getCurrentMimojiState();
                if (!FragmentMimoji.ADD_STATE.equals(currentMimojiState) && !FragmentMimoji.CLOSE_STATE.equals(currentMimojiState)) {
                    i4 = R.drawable.ic_live_sticker_on;
                }
                layoutParams.gravity = 81;
            }
            z = true;
        } else {
            if (this.mCenterRedDot != null) {
                this.mCenterRedDot.setVisibility(8);
            }
            z = false;
            i4 = 0;
        }
        if (i4 > 0) {
            frameLayout.requestLayout();
            this.mCenterTipImage.setImageResource(i4);
        }
        updateImageBgColor(i5, this.mCenterTipImage, false);
        if (this.mCenterTipImage.getTag() == null || ((Integer) this.mCenterTipImage.getTag()).intValue() != i3) {
            if (z) {
                ViewCompat.setRotation(this.mCenterTipImage, (float) this.mDegree);
            }
            this.mCenterTipImage.setTag(Integer.valueOf(i3));
            if (list2 != null) {
                if (z) {
                    if (i6 != 165) {
                        c = 2;
                    } else if (!b.isSupportedOpticalZoom()) {
                        c = 3;
                    }
                } else if (!(i6 == 165 || this.mCurrentMode == 165)) {
                    c = 4;
                }
                switch (c) {
                    case 0:
                        AlphaInOnSubscribe.directSetResult(this.mCenterTipImage);
                        return;
                    case 1:
                        AlphaOutOnSubscribe.directSetResult(this.mCenterTipImage);
                        return;
                    case 2:
                        list2.add(Completable.create(new AlphaInOnSubscribe(this.mCenterTipImage)));
                        return;
                    case 3:
                        list2.add(Completable.create(new AlphaInOnSubscribe(this.mCenterTipImage).setStartDelayTime(150).setDurationTime(150)));
                        return;
                    case 4:
                        list2.add(Completable.create(new AlphaOutOnSubscribe(this.mCenterTipImage)));
                        return;
                    default:
                        return;
                }
            }
            c = 0;
            switch (c) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
        }
    }

    private void updateImageBgColor(int i, View view, boolean z) {
        int i2;
        int i3;
        if (z) {
            i2 = R.drawable.bg_popup_indicator;
            i3 = R.drawable.square_module_bg_popup_indicator;
        } else {
            i2 = R.drawable.bg_popup_indicator_no_stroke;
            i3 = R.drawable.square_module_bg_popup_indicator_no_stroke;
        }
        if (i != 165) {
            view.setBackgroundResource(i2);
        } else {
            view.setBackgroundResource(i3);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0081, code lost:
        if (com.android.camera.data.DataRepository.dataItemFeature().fK() != false) goto L_0x00c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x008c, code lost:
        if (com.android.camera.data.DataRepository.dataItemFeature().fJ() != false) goto L_0x00c3;
     */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x016d  */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x0190  */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x019f  */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x01b8  */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x01c7  */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x01cd  */
    /* JADX WARNING: Removed duplicated region for block: B:125:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x0119  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0120  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0143  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0154  */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0167  */
    private void updateLeftTipImage(int i, int i2, int i3, List<Completable> list) {
        int i4;
        int checkLeftImageTipClose;
        int i5;
        boolean z;
        int i6;
        char c;
        int currentCameraId = DataRepository.dataItemGlobal().getCurrentCameraId();
        boolean isNormalIntent = DataRepository.dataItemGlobal().isNormalIntent();
        char c2 = 1;
        boolean z2 = DataRepository.dataItemFeature().isSupportUltraWide() && !HybridZoomingSystem.IS_3_OR_MORE_SAT;
        if (i2 != 165) {
            if (i2 == 169) {
                if (currentCameraId == 0 && isNormalIntent && z2 && !CameraSettings.isAutoZoomEnabled(i2)) {
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
                    if (list == null) {
                    }
                    switch (c2) {
                        case 0:
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
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
                if (list == null) {
                }
                switch (c2) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            } else {
                if (i2 == 171) {
                    if (isNormalIntent) {
                        i4 = 19;
                        switch (currentCameraId) {
                            case 0:
                                break;
                            case 1:
                                break;
                            default:
                                i4 = -1;
                                break;
                        }
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
                        if (list == null) {
                        }
                        switch (c2) {
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                            case 4:
                                break;
                        }
                    }
                } else {
                    if (i2 != 174) {
                        switch (i2) {
                            case 161:
                                break;
                            case 162:
                                if (!CameraSettings.isMacroModeEnabled(i2) && currentCameraId == 0 && isNormalIntent && z2 && !CameraSettings.isAutoZoomEnabled(i2) && !CameraSettings.isSuperEISEnabled(i2)) {
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
                    if (z2 && currentCameraId == 0) {
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
                                switch (checkLeftImageTipClose) {
                                    case 18:
                                        if (!"".equals(CameraSettings.getCurrentLiveSticker())) {
                                            i5 = R.drawable.ic_live_sticker_on;
                                            break;
                                        }
                                        break;
                                    case 19:
                                        i5 = R.drawable.ic_light;
                                        i6 = R.string.accessibility_lighting_panel_on;
                                        z = true;
                                        break;
                                    default:
                                        z = true;
                                        i5 = 0;
                                        i6 = 0;
                                        break;
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
                            if (list == null) {
                                if (z) {
                                    c2 = 0;
                                }
                            } else if (z) {
                                if (i3 != 165) {
                                    c = 2;
                                } else if (b.isSupportedOpticalZoom()) {
                                    c2 = 0;
                                    directHideLeftImageIntro();
                                } else {
                                    c = 3;
                                }
                                c2 = c;
                                directHideLeftImageIntro();
                            } else if (!(i3 == 165 || this.mCurrentMode == 165)) {
                                c2 = 4;
                            }
                            switch (c2) {
                                case 0:
                                    AlphaInOnSubscribe.directSetResult(this.mLeftTipImage);
                                    return;
                                case 1:
                                    AlphaOutOnSubscribe.directSetResult(this.mLeftTipImage);
                                    return;
                                case 2:
                                    list.add(Completable.create(new AlphaInOnSubscribe(this.mLeftTipImage)));
                                    return;
                                case 3:
                                    list.add(Completable.create(new AlphaInOnSubscribe(this.mLeftTipImage).setStartDelayTime(150).setDurationTime(150)));
                                    return;
                                case 4:
                                    list.add(Completable.create(new AlphaOutOnSubscribe(this.mLeftTipImage)));
                                    return;
                                default:
                                    return;
                            }
                        } else {
                            return;
                        }
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
                if (list == null) {
                }
                switch (c2) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        }
        if (!CameraSettings.isMacroModeEnabled(i2) && !CameraSettings.isUltraPixelRearOn() && z2 && currentCameraId == 0) {
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
            if (list == null) {
            }
            switch (c2) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
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
        if (list == null) {
        }
        switch (c2) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
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

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00b5, code lost:
        if (r4 != false) goto L_0x00b8;
     */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00d2  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00e1  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00fa  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0109  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x010f  */
    /* JADX WARNING: Removed duplicated region for block: B:55:? A[RETURN, SYNTHETIC] */
    private void updateSpeedTipImage(int i, int i2, List<Completable> list) {
        boolean z;
        boolean z2;
        int i3;
        int i4 = i != 174 ? -1 : 33;
        char c = 1;
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
        boolean z3 = this.mSpeedTipImage.getTag() == null || ((Integer) this.mSpeedTipImage.getTag()).intValue() != i4;
        if (z3) {
            this.mSpeedTipImage.removeAllViews();
            if (i3 > 0) {
                this.mSpeedTipImage.addView(LayoutInflater.from(getContext()).inflate(i3, (ViewGroup) null));
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
        if (z3) {
            if (!z2 || !z) {
                ViewCompat.setRotation(this.mSpeedTipImage, 0.0f);
            } else {
                ViewCompat.setRotation(this.mSpeedTipImage, (float) this.mDegree);
            }
            this.mSpeedTipImage.setTag(Integer.valueOf(i4));
            if (list != null) {
                if (!z2) {
                    c = 4;
                } else if (i2 != 163) {
                    c = 2;
                } else if (!b.isSupportedOpticalZoom()) {
                    c = 3;
                }
                switch (c) {
                    case 0:
                        AlphaInOnSubscribe.directSetResult(this.mSpeedTipImage);
                        return;
                    case 1:
                        AlphaOutOnSubscribe.directSetResult(this.mSpeedTipImage);
                        return;
                    case 2:
                        list.add(Completable.create(new AlphaInOnSubscribe(this.mSpeedTipImage)));
                        return;
                    case 3:
                        list.add(Completable.create(new AlphaInOnSubscribe(this.mSpeedTipImage).setStartDelayTime(150).setDurationTime(150)));
                        return;
                    case 4:
                        list.add(Completable.create(new AlphaOutOnSubscribe(this.mSpeedTipImage)));
                        return;
                    default:
                        return;
                }
            }
            c = 0;
            switch (c) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0022, code lost:
        if (com.android.camera.data.DataRepository.dataItemRunning().supportPopShineEntry() != false) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0035, code lost:
        if (com.android.camera.CameraSettings.isSuperEISEnabled(r11) == false) goto L_0x0038;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0040, code lost:
        if (com.android.camera.data.DataRepository.dataItemRunning().supportPopShineEntry() == false) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0043, code lost:
        r3 = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004d, code lost:
        if (com.android.camera.data.DataRepository.dataItemRunning().supportPopShineEntry() != false) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0066, code lost:
        if (com.android.camera.data.DataRepository.dataItemRunning().supportPopShineEntry() == false) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00ef, code lost:
        if (r8 != false) goto L_0x00f2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00ff, code lost:
        if (com.mi.config.b.isSupportedOpticalZoom() != false) goto L_0x00f2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0113  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0122  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x013b  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x014a  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0150  */
    /* JADX WARNING: Removed duplicated region for block: B:87:? A[RETURN, SYNTHETIC] */
    private void updateTipImage(int i, int i2, List<Completable> list) {
        boolean z;
        int i3;
        int i4;
        char c = 3;
        if (i != 165) {
            if (i != 171) {
                if (i != 174) {
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
                        default:
                            switch (i) {
                                case 176:
                                    break;
                                case 177:
                                    updateCenterTipImage(i, i2, list);
                                    break;
                            }
                    }
                }
            }
        }
        if (!CameraSettings.isMacroModeEnabled(i)) {
            if (!CameraSettings.isUltraPixelPortraitFrontOn()) {
            }
        }
        int i5 = -1;
        boolean z2 = true;
        if (i5 != -1) {
            if (i5 != 34) {
                switch (i5) {
                    case 2:
                        i4 = R.drawable.ic_beauty_sticker;
                        break;
                    case 3:
                        i4 = DataRepository.dataItemRunning().getComponentRunningShine().getBottomEntryRes(i);
                        i3 = R.string.accessibility_beauty_panel_open;
                        z = true;
                        break;
                    default:
                        z = true;
                        i4 = 0;
                        i3 = 0;
                        break;
                }
            } else {
                i4 = DataRepository.dataItemRunning().getComponentRunningShine().getBottomEntryRes(i);
            }
            z = true;
            i3 = 0;
        } else {
            i4 = 0;
            i3 = 0;
            z = false;
        }
        if (i4 > 0) {
            this.mTipImage.setImageResource(i4);
        }
        if (i3 > 0 && Util.isAccessible()) {
            this.mTipImage.setContentDescription(getString(i3));
        }
        updateImageBgColor(i, this.mTipImage, false);
        if (this.mTipImage.getTag() == null || ((Integer) this.mTipImage.getTag()).intValue() != i5) {
            if (z) {
                ViewCompat.setRotation(this.mTipImage, (float) this.mDegree);
            }
            this.mTipImage.setTag(Integer.valueOf(i5));
            if (list != null) {
                if (!z) {
                    if (!(i2 == 165 || this.mCurrentMode == 165)) {
                        c = 4;
                    }
                    c = 1;
                } else if (i2 != 165) {
                    c = 2;
                }
                switch (c) {
                    case 0:
                        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
                        if (actionProcessing == null || !actionProcessing.isShowLightingView()) {
                            z2 = false;
                        }
                        if (z2) {
                            this.mTipImage.setTag(-1);
                        }
                        if (!z2) {
                            AlphaInOnSubscribe.directSetResult(this.mTipImage);
                            return;
                        }
                        return;
                    case 1:
                        AlphaOutOnSubscribe.directSetResult(this.mTipImage);
                        return;
                    case 2:
                        list.add(Completable.create(new AlphaInOnSubscribe(this.mTipImage)));
                        return;
                    case 3:
                        list.add(Completable.create(new AlphaInOnSubscribe(this.mTipImage).setStartDelayTime(150).setDurationTime(150)));
                        return;
                    case 4:
                        list.add(Completable.create(new AlphaOutOnSubscribe(this.mTipImage)));
                        return;
                    default:
                        return;
                }
            }
            c = 0;
            switch (c) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
        }
    }

    public boolean containTips(@StringRes int i) {
        return this.mTipMessage.getVisibility() == 0 && getString(i).equals(this.mTipMessage.getText().toString());
    }

    public void directHideCenterTipImage() {
        if (this.mCenterTipImage != null && this.mCenterTipImage.getVisibility() != 4) {
            this.mCenterTipImage.setVisibility(8);
        }
    }

    public void directHideLyingDirectHint() {
        this.mLyingDirectHint.setVisibility(8);
    }

    public void directHideTipImage() {
        if (this.mTipImage.getVisibility() != 4) {
            this.mTipImage.setTag(-1);
            this.mTipImage.setVisibility(4);
        }
    }

    public void directShowLeftImageIntro() {
        if (CameraSettings.isShowUltraWideIntro()) {
            this.mIsShowLeftImageIntro = true;
        }
        updateLeftTipImage(0, this.mCurrentMode, this.mCurrentMode, (List<Completable>) null);
    }

    public void directShowOrHideLeftTipImage(boolean z) {
        if (this.mLeftTipImage != null) {
            if (z) {
                updateLeftTipImage();
                this.mLeftTipImage.setVisibility(0);
                return;
            }
            this.mLeftTipImage.setTag(-1);
            this.mLeftTipImage.setVisibility(4);
        }
    }

    public void directlyHideTips() {
        ViewCompat.animate(this.mTipMessage).cancel();
        this.mHandler.removeCallbacksAndMessages((Object) null);
        if (this.mTipMessage.getVisibility() == 0) {
            this.mTipMessage.setVisibility(8);
            ModeProtocol.CameraModuleSpecial cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195);
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
        this.mHandler.removeCallbacksAndMessages((Object) null);
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
        if (this.mCenterTipImage != null && this.mCenterTipImage.getVisibility() != 4) {
            this.mCenterTipImage.setTag(-1);
            Completable.create(new AlphaOutOnSubscribe(this.mCenterTipImage)).subscribe();
            if (this.mCenterRedDot != null) {
                this.mCenterRedDot.setVisibility(8);
            }
        }
    }

    public void hideLeftTipImage() {
        if (this.mLeftTipImage != null && this.mLeftTipImage.getVisibility() != 4) {
            this.mLeftTipImage.setTag(-1);
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
            Log.w(tag, "  hideQrCodeTip  time  : " + System.currentTimeMillis());
        }
    }

    public void hideSpeedTipImage() {
        if (this.mSpeedTipImage != null && this.mSpeedTipImage.getVisibility() != 4) {
            this.mSpeedTipImage.setTag(-1);
            Completable.create(new AlphaOutOnSubscribe(this.mSpeedTipImage)).subscribe();
        }
    }

    public void hideTipImage() {
        if (this.mTipImage != null && this.mTipImage.getVisibility() != 4) {
            this.mTipImage.setTag(-1);
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
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin = Util.getBottomHeight(getResources());
        this.mTipImage.setOnClickListener(this);
        this.mQrCodeButton.setOnClickListener(this);
        adjustViewBackground(this.mCurrentMode);
        provideAnimateElement(this.mCurrentMode, (List<Completable>) null, 2);
        if (((ActivityBase) getContext()).getCameraIntentManager().isFromScreenSlide().booleanValue()) {
            Util.startScreenSlideAlphaInAnimation(this.mTipImage);
        }
        this.mCloseIv = (ImageView) view.findViewById(R.id.close_iv);
        this.mCloseIv.setOnClickListener(this);
        this.mBottomTipHeight = getResources().getDimensionPixelSize(R.dimen.portrait_hint_height);
    }

    public boolean isLightingHintVisible() {
        ModeProtocol.VerticalProtocol verticalProtocol = (ModeProtocol.VerticalProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(198);
        return (verticalProtocol != null ? verticalProtocol.isAnyViewVisible() : false) || this.mLightingPattern.getVisibility() == 0;
    }

    public boolean isPortraitHintVisible() {
        return this.mPortraitSuccessHint.getVisibility() == 0;
    }

    public boolean isQRTipVisible() {
        return this.mQrCodeButton != null && this.mQrCodeButton.getVisibility() == 0;
    }

    public boolean isTipShowing() {
        return this.mTipMessage != null && this.mTipMessage.getVisibility() == 0;
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
        switch (i) {
            case 2:
                directlyHideTips();
                break;
            case 3:
                adjustViewBackground(this.mCurrentMode);
                break;
        }
        updateTipImage(this.mCurrentMode, this.mCurrentMode, (List<Completable>) null);
        updateLeftTipImage(1, this.mCurrentMode, this.mCurrentMode, (List<Completable>) null);
        updateSpeedTipImage(this.mCurrentMode, this.mCurrentMode, (List<Completable>) null);
        updateCenterTipImage(this.mCurrentMode, this.mCurrentMode, (List<Completable>) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003c, code lost:
        if (r3.mCurrentTipType != 9) goto L_0x003f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003e, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0043, code lost:
        if (r3.mCurrentTipType != 21) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0045, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0046, code lost:
        hideTip(r3.mTipMessage);
        hideTip(r3.mPortraitSuccessHint);
        hideTip(r3.mQrCodeButton);
        hideTip(r3.mLightingPattern);
        r3.mHandler.removeCallbacksAndMessages((java.lang.Object) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0061, code lost:
        return false;
     */
    public boolean onBackEvent(int i) {
        switch (i) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                if (this.mCurrentTipType == 6 || this.mCurrentTipType == 7 || this.mCurrentTipType == 11 || this.mCurrentTipType == 9 || this.mCurrentTipType == 12 || this.mCurrentTipType == 18 || this.mCurrentTipType == 10 || this.mCurrentTipType == 13 || this.mCurrentTipType == 17) {
                    return false;
                }
            case 4:
                break;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0089, code lost:
        r3 = ((java.lang.Integer) r3.getTag()).intValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0095, code lost:
        if (r3 == 18) goto L_0x00a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0099, code lost:
        if (r3 == 34) goto L_0x009c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x009c, code lost:
        hideAllTipImage();
        showMimojiPanel();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00a3, code lost:
        hideAllTipImage();
        showLiveSticker();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00ab, code lost:
        if (r2.mCenterRedDot == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00ad, code lost:
        r2.mCenterRedDot.setVisibility(8);
        com.android.camera.CameraSettings.setTTLiveStickerNeedRedDot(false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00b9, code lost:
        onLeftImageClick(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        return;
     */
    public void onClick(View view) {
        if (isEnableClick()) {
            ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            if (cameraAction != null && cameraAction.isDoingAction()) {
                return;
            }
            if (!CameraSettings.isFrontCamera() || !((Camera) getContext()).isScreenSlideOff()) {
                switch (view.getId()) {
                    case R.id.popup_tip_image:
                        int intValue = ((Integer) view.getTag()).intValue();
                        hideAllTipImage();
                        CameraSettings.setPopupTipBeautyIntroClicked();
                        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
                        switch (intValue) {
                            case 2:
                                baseDelegate.delegateEvent(4);
                                return;
                            case 3:
                                hideQrCodeTip();
                                CameraStat.recordCountEvent(CameraStat.CATEGORY_COUNTER, CameraStat.KEY_BEAUTY_BUTTON);
                                showBeauty(this.mCurrentMode);
                                return;
                            default:
                                return;
                        }
                    case R.id.popup_left_tip_image:
                        break;
                    case R.id.popup_center_tip_image:
                        break;
                    case R.id.popup_left_tip_intro:
                        view.setTag(21);
                        CameraSettings.setPopupUltraWideIntroClicked();
                        directHideLeftImageIntro();
                        break;
                    case R.id.popup_speed_tip_image:
                        if (((Integer) view.getTag()).intValue() == 33) {
                            hideAllTipImage();
                            showLiveSpeed();
                            break;
                        }
                        break;
                    case R.id.popup_tips_qrcode:
                        hideQrCodeTip();
                        CameraStat.recordCountEvent(CameraStat.CATEGORY_COUNTER, CameraStat.KEY_QRCODE_DETECTED);
                        ((ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195)).showQRCodeResult();
                        return;
                    case R.id.close_iv:
                        switch (this.mCloseType) {
                            case 1:
                                closeFilter();
                                return;
                            case 2:
                                closeLight();
                                return;
                            default:
                                return;
                        }
                    default:
                        return;
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
        if (i2 == 3) {
            list = null;
        }
        updateLeftRightTipImageOfMiMovie(i, i3, list);
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
        if (this.mLeftTipImage != null && this.mLeftTipImage.getVisibility() == 0) {
            list.add(this.mLeftTipImage);
        }
        if (this.mCenterTipImage != null && this.mCenterTipImage.getVisibility() == 0) {
            list.add(this.mCenterTipImage);
        }
        if (!(this.mSpeedTipImage == null || this.mSpeedTipImage.getVisibility() != 0 || ((Integer) this.mSpeedTipImage.getTag()).intValue() == 33)) {
            list.add(this.mSpeedTipImage);
        }
        if (this.mCenterTipImage != null && this.mCenterTipImage.getVisibility() == 0) {
            list.add(this.mCenterTipImage);
        }
        updateLightingPattern(false, true);
    }

    public void reConfigBottomTipOfUltraWide() {
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            if (!(163 == this.mCurrentMode || 165 == this.mCurrentMode || 162 == this.mCurrentMode || 169 == this.mCurrentMode || 174 == this.mCurrentMode || 161 == this.mCurrentMode)) {
                return;
            }
        } else if (!(163 == this.mCurrentMode || 165 == this.mCurrentMode || 162 == this.mCurrentMode)) {
            return;
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
            ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            if (miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow()) {
                return;
            }
            if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                showTips(13, (int) R.string.ultra_wide_open_tip, 4);
            } else if (CameraSettings.shouldShowUltraWideSatTip(this.mCurrentMode)) {
                showTips(13, (int) R.string.ultra_wide_open_tip_sat, 2);
            }
        }
    }

    public boolean reConfigQrCodeTip() {
        if (this.mCurrentMode == 163) {
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            boolean z = bottomPopupTips != null && bottomPopupTips.isTipShowing() && (TextUtils.equals(this.mCurrentTipMessage, getString(R.string.ultra_wide_recommend_tip_hint)) || TextUtils.equals(this.mCurrentTipMessage, getString(R.string.ultra_wide_recommend_tip_hint_sat)));
            boolean z2 = HybridZoomingSystem.toDecimal(CameraSettings.readZoom()) == 0.6f || CameraSettings.isUltraWideConfigOpen(this.mCurrentMode);
            ModeProtocol.MakeupProtocol makeupProtocol = (ModeProtocol.MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
            boolean z3 = makeupProtocol != null && makeupProtocol.isSeekBarVisible();
            ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            boolean z4 = miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow();
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            int currentAiSceneLevel = topAlert.getCurrentAiSceneLevel();
            boolean z5 = topAlert.getAlertIsShow() && (currentAiSceneLevel == -1 || currentAiSceneLevel == 23 || currentAiSceneLevel == 24 || currentAiSceneLevel == 35 || currentAiSceneLevel == -35);
            if (CameraSettings.isTiltShiftOn() || CameraSettings.isGroupShotOn() || CameraSettings.isGradienterOn() || z2 || z4 || z3 || z || z5) {
                hideQrCodeTip();
                return true;
            }
        }
        return false;
    }

    public void reInitTipImage() {
        provideAnimateElement(this.mCurrentMode, (List<Completable>) null, 2);
        reConfigBottomTipOfMacro();
        reConfigBottomTipOf960FPS();
    }

    /* access modifiers changed from: protected */
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
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

    /* JADX WARNING: Can't fix incorrect switch cases order */
    public void setLightingPattern(String str) {
        char c;
        this.stringLightingRes = -1;
        switch (str.hashCode()) {
            case 48:
                if (str.equals("0")) {
                    c = 0;
                    break;
                }
            case 49:
                if (str.equals("1")) {
                    c = 1;
                    break;
                }
            case 50:
                if (str.equals("2")) {
                    c = 2;
                    break;
                }
            case 51:
                if (str.equals("3")) {
                    c = 3;
                    break;
                }
            case 52:
                if (str.equals("4")) {
                    c = 4;
                    break;
                }
            case 53:
                if (str.equals("5")) {
                    c = 5;
                    break;
                }
            case 54:
                if (str.equals("6")) {
                    c = 6;
                    break;
                }
            case 55:
                if (str.equals("7")) {
                    c = 7;
                    break;
                }
            case 56:
                if (str.equals("8")) {
                    c = 8;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
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
        if ((i != 0 || !isLightingHintVisible()) && this.mCurrentTipType != 21) {
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
            reIntTipViewMarginBottom(this.mMimojiTextview, this.mMimojiTextview.getBackground().getIntrinsicHeight());
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
        if (this.mLeftTipImage != null) {
            this.mLeftTipImage.setImageResource(R.drawable.ic_manually_indicator);
            if (z) {
                if (Util.isAccessible() && this.mCloseType == 2) {
                    this.mLeftTipImage.setContentDescription(getString(R.string.accessibility_lighting_panel_off));
                }
                this.mLeftTipImage.setTag(20);
                Completable.create(new AlphaInOnSubscribe(this.mLeftTipImage)).subscribe();
                return;
            }
            this.mLeftTipImage.setTag(-1);
            Completable.create(new AlphaOutOnSubscribe(this.mLeftTipImage)).subscribe();
        }
    }

    public void showQrCodeTip() {
        if (!reConfigQrCodeTip() && this.mQrCodeButton.getVisibility() != 0) {
            directHideLyingDirectHint();
            hideTip(this.mTipMessage);
            reIntTipViewMarginBottom(this.mQrCodeButton, this.mQrCodeButton.getBackground().getIntrinsicHeight());
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
        if (this.mCurrentMode != 171 || this.mTipMessage.getVisibility() == 8 || this.mCurrentTipType != 21) {
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
                    this.mHandler.removeCallbacksAndMessages((Object) null);
                    if (i3 > 0) {
                        this.mHandler.sendEmptyMessageDelayed(1, (long) i3);
                    }
                    if (this.mCurrentMode == 163) {
                        ModeProtocol.CameraModuleSpecial cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195);
                        if (cameraModuleSpecial != null) {
                            cameraModuleSpecial.showOrHideChip(false);
                        }
                    }
                }
            }
        }
    }

    public void showTipsWithOrientation(int i, int i2, int i3, int i4, int i5) {
        switch (i5) {
            case 0:
                showTips(i, i2, i3, i4);
                return;
            case 1:
                if (isLandScape()) {
                    showTips(i, i2, i3, i4);
                    return;
                }
                return;
            case 2:
                if (!isLandScape()) {
                    showTips(i, i2, i3, i4);
                    return;
                }
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        this.mHandler.removeCallbacksAndMessages((Object) null);
        modeCoordinator.detachProtocol(175, this);
        unRegisterBackStack(modeCoordinator, this);
        this.mIsShowLeftImageIntro = false;
    }

    public void updateLeftRightTipImageOfMiMovie(int i, int i2, List<Completable> list) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mTipImage.getLayoutParams();
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mLeftTipImage.getLayoutParams();
        if (list != null) {
            if (layoutParams.rightMargin != 0) {
                layoutParams.rightMargin = 0;
                this.mTipImage.setLayoutParams(layoutParams);
            }
            if (layoutParams2.leftMargin != 0) {
                layoutParams2.leftMargin = 0;
                this.mLeftTipImage.setLayoutParams(layoutParams2);
            }
        } else if (CameraSettings.isMiMovieOpen(this.mCurrentMode)) {
            if (layoutParams.rightMargin != Util.getMiMovieMargin()) {
                layoutParams.rightMargin = Util.getMiMovieMargin();
                this.mTipImage.setLayoutParams(layoutParams);
            }
            if (layoutParams2.leftMargin != Util.getMiMovieMargin()) {
                layoutParams2.leftMargin = Util.getMiMovieMargin();
                this.mLeftTipImage.setLayoutParams(layoutParams2);
            }
        }
    }

    public void updateLeftTipImage() {
        updateLeftTipImage(1, this.mCurrentMode, this.mCurrentMode, (List<Completable>) null);
    }

    public void updateLyingDirectHint(boolean z, boolean z2) {
        if (!z2) {
            this.mIsShowLyingDirectHint = z;
        }
        if (this.mIsShowLyingDirectHint) {
            ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
            boolean z3 = true;
            boolean z4 = dualController != null && dualController.isSlideVisible();
            ModeProtocol.MakeupProtocol makeupProtocol = (ModeProtocol.MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
            boolean z5 = makeupProtocol != null && makeupProtocol.isSeekBarVisible();
            ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
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
        updateTipImage(this.mCurrentMode, this.mCurrentMode, (List<Completable>) null);
    }
}
