package com.android.camera.fragment.top;

import android.animation.ObjectAnimator;
import android.content.res.TypedArray;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.camera.ActivityBase;
import com.android.camera.Camera;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.constant.DurationConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.config.ComponentConfigFlash;
import com.android.camera.data.data.config.ComponentConfigHdr;
import com.android.camera.data.data.config.ComponentConfigMeter;
import com.android.camera.data.data.config.ComponentConfigUltraWide;
import com.android.camera.data.data.config.ComponentRunningUltraPixel;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.config.SupportedConfigFactory;
import com.android.camera.data.data.config.SupportedConfigs;
import com.android.camera.data.data.config.TopConfigItem;
import com.android.camera.effect.FilterInfo;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.fragment.music.FragmentLiveMusic;
import com.android.camera.fragment.top.ExpandAdapter;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.MimojiStatusManager;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtil;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import miui.view.animation.CubicEaseInOutInterpolator;

public class FragmentTopConfig extends BaseFragment implements View.OnClickListener, ExpandAdapter.ExpandListener, ModeProtocol.HandleBackTrace, ModeProtocol.HandleBeautyRecording, ModeProtocol.TopAlert {
    private static final int EXPAND_STATE_CENTER = 2;
    private static final int EXPAND_STATE_LEFT = 0;
    private static final int EXPAND_STATE_LEFT_FROM_SIBLING = 1;
    private static final int EXPAND_STATE_RIGHT = 4;
    private static final int EXPAND_STATE_RIGHT_FROM_SIBLING = 3;
    private static final String TAG = "FragmentTopConfig";
    public static final int TIP_HINT_DURATION_2S = 2000;
    public static final int TIP_HINT_DURATION_3S = 3000;
    private int[] mAiSceneResources;
    private int[] mAutoZoomResources;
    private List<ImageView> mConfigViews;
    private int mCurrentAiSceneLevel;
    private SparseBooleanArray mDisabledFunctionMenu;
    private int mDisplayRectTopMargin;
    private RecyclerView mExpandView;
    private int[] mFilterResources;
    private FragmentTopAlert mFragmentTopAlert;
    private FragmentTopConfigExtra mFragmentTopConfigExtra;
    private boolean mIsRTL;
    private boolean mIsShowExtraMenu;
    private boolean mIsShowTopLyingDirectHint;
    private LastAnimationComponent mLastAnimationComponent;
    private int[] mLightingResource;
    private int[] mLiveMusicSelectResources;
    private ObjectAnimator mLiveShotAnimator;
    private int[] mLiveShotResource;
    private int[] mMacroResources;
    private int[] mMiMovieResources;
    private View mMimojiCreateLayout;
    private int[] mSuperEISResources;
    private SupportedConfigs mSupportedConfigs;
    private View mTopConfigMenu;
    private int mTopDrawableWidth;
    private ViewGroup mTopExtraParent;
    private int mTotalWidth;
    private int[] mUltraPixelPhotographyIconResources;
    private int[] mUltraPixelPhotographyTipResources;
    private String[] mUltraPixelPhotographyTipString;
    private int[] mUltraPixelPortraitResources;
    private int[] mUltraWideBokehResources;
    private int[] mUltraWideResource;
    private int[] mVideoBokehResource;
    private boolean mVideoRecordingStarted;
    private int mViewPadding;

    private void alertHDR(int i, boolean z, boolean z2, boolean z3) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            if (z3) {
                if (i != 0) {
                    this.mLastAnimationComponent.reverse(true);
                } else if (z2) {
                    ImageView topImage = getTopImage(194);
                    if (topImage != null) {
                        topImage.performClick();
                    }
                }
            }
            topAlert.alertHDR(i, z);
        }
    }

    private void alertTopMusicHint(int i, String str) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.alertMusicTip(i, str);
        }
    }

    private void expandExtra(ComponentData componentData, View view, int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6 = 1;
        if (!this.mLastAnimationComponent.reverse(true)) {
            ExpandAdapter expandAdapter = new ExpandAdapter(componentData, this);
            int dimensionPixelSize = i == 214 ? getResources().getDimensionPixelSize(R.dimen.expanded_meter_text_item_width) * componentData.getItems().size() : getResources().getDimensionPixelSize(R.dimen.expanded_text_item_width) * componentData.getItems().size();
            this.mExpandView.getLayoutParams().width = dimensionPixelSize;
            this.mExpandView.setAdapter(expandAdapter);
            int i7 = 0;
            this.mExpandView.setVisibility(0);
            this.mExpandView.setTag(Integer.valueOf(i));
            int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.panel_imageview_button_padding_width) * 3;
            boolean z = ((FrameLayout.LayoutParams) view.getLayoutParams()).gravity == 8388611;
            if (z) {
                i6 = 0;
            }
            if (this.mIsRTL) {
                i6 = 4 - i6;
            }
            switch (i6) {
                case 0:
                    this.mLastAnimationComponent.setExpandGravity(5);
                    i2 = view.getRight() - dimensionPixelSize2;
                    i5 = view.getWidth() + 0;
                    break;
                case 1:
                    this.mLastAnimationComponent.setExpandGravity(3);
                    i2 = view.getRight();
                    i5 = view.getWidth() + 0;
                    break;
                case 2:
                    i4 = getView().getWidth() - view.getWidth();
                    i2 = (view.getLeft() - dimensionPixelSize) - dimensionPixelSize2;
                    i3 = i4 - dimensionPixelSize;
                    break;
                case 3:
                    i4 = getView().getWidth() - view.getWidth();
                    i2 = (view.getLeft() - dimensionPixelSize) - dimensionPixelSize2;
                    i3 = i4 - dimensionPixelSize;
                    break;
                case 4:
                    this.mLastAnimationComponent.setExpandGravity(3);
                    i4 = getView().getWidth() - view.getWidth();
                    i2 = dimensionPixelSize2 + (view.getLeft() - dimensionPixelSize);
                    i3 = i4 - dimensionPixelSize;
                    break;
                default:
                    i2 = 0;
                    i4 = 0;
                    i3 = 0;
                    break;
            }
            i3 = i5;
            i4 = 0;
            this.mLastAnimationComponent.mRecyclerView = this.mExpandView;
            this.mLastAnimationComponent.mReverseLeft = view.getLeft();
            this.mLastAnimationComponent.mReverseRecyclerViewLeft = i2;
            this.mLastAnimationComponent.hideOtherViews(i, this.mConfigViews);
            if (!z) {
                this.mLastAnimationComponent.mAnchorView = view;
                this.mLastAnimationComponent.translateAnchorView(i4 - view.getLeft());
            }
            if (this.mIsRTL) {
                i7 = getView().getWidth() - dimensionPixelSize;
            }
            this.mLastAnimationComponent.showExtraView(i2 - i7, i3 - i7);
        }
    }

    private Drawable getAiSceneDrawable(int i) {
        TypedArray obtainTypedArray = getResources().obtainTypedArray(R.array.ai_scene_drawables);
        Drawable drawable = (i < 0 || i >= obtainTypedArray.length()) ? null : obtainTypedArray.getDrawable(i);
        obtainTypedArray.recycle();
        return drawable;
    }

    private int[] getAiSceneResources() {
        return new int[]{R.drawable.ic_new_ai_scene_off, R.drawable.ic_new_ai_scene_on};
    }

    private int[] getAutoZoomResources() {
        return new int[]{R.drawable.ic_autozoom_off, R.drawable.ic_autozoom_on};
    }

    private int[] getFilterResources() {
        return new int[]{R.drawable.ic_new_effect_button_normal, R.drawable.ic_new_effect_button_selected};
    }

    @DrawableRes
    private int getFocusPeakImageResource() {
        boolean isSwitchOn = DataRepository.dataItemRunning().isSwitchOn("pref_camera_peak_key");
        return "zh".equals(Locale.getDefault().getLanguage()) ? isSwitchOn ? R.drawable.ic_new_config_focus_peak_ch_on : R.drawable.ic_new_config_foucs_peak_ch_off : isSwitchOn ? R.drawable.ic_new_config_focus_peak_en_on : R.drawable.ic_new_config_focus_peak_en_off;
    }

    private int getInitialMargin(TopConfigItem topConfigItem, ImageView imageView) {
        int configsSize = this.mSupportedConfigs == null ? 0 : this.mSupportedConfigs.getConfigsSize();
        if (configsSize <= 0) {
            return 0;
        }
        int i = topConfigItem.index;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.gravity = 0;
        int i2 = GravityCompat.START;
        int i3 = GravityCompat.END;
        switch (configsSize) {
            case 1:
                layoutParams.leftMargin = 0;
                if (topConfigItem.gravity != 0) {
                    i3 = topConfigItem.gravity;
                }
                layoutParams.gravity = i3;
                imageView.setLayoutParams(layoutParams);
                return 0;
            case 2:
                if (i == 0) {
                    layoutParams.leftMargin = 0;
                    if (topConfigItem.gravity != 0) {
                        i2 = topConfigItem.gravity;
                    }
                    layoutParams.gravity = i2;
                } else if (i == 1) {
                    layoutParams.leftMargin = 0;
                    if (topConfigItem.gravity != 0) {
                        i3 = topConfigItem.gravity;
                    }
                    layoutParams.gravity = i3;
                }
                imageView.setLayoutParams(layoutParams);
                return 0;
            default:
                if (i == 0) {
                    layoutParams.leftMargin = 0;
                    layoutParams.gravity = GravityCompat.START;
                    imageView.setLayoutParams(layoutParams);
                    return 0;
                }
                int i4 = configsSize - 1;
                if (i != i4) {
                    return (((this.mTotalWidth - (this.mViewPadding * 2)) / i4) * i) + this.mViewPadding;
                }
                layoutParams.leftMargin = 0;
                layoutParams.gravity = GravityCompat.END;
                imageView.setLayoutParams(layoutParams);
                return 0;
        }
    }

    private int[] getLightingResources() {
        return new int[]{R.drawable.ic_new_lighting_off, R.drawable.ic_new_lighting_on};
    }

    private int[] getLiveShotResources() {
        return new int[]{R.drawable.ic_motionphoto, R.drawable.ic_motionphoto_highlight};
    }

    private int[] getMacroResources() {
        return new int[]{R.drawable.ic_config_macro_mode_off, R.drawable.ic_config_macro_mode_on};
    }

    private int[] getMiMovieResources() {
        return new int[]{R.drawable.ic_config_mimovie_off, R.drawable.ic_config_mimovie_on};
    }

    private int getMoreResources() {
        return R.drawable.ic_new_more;
    }

    private int[] getMusicSelectResources() {
        return new int[]{R.drawable.ic_live_music_normal, R.drawable.ic_live_music_selected};
    }

    private int getPortraitResources() {
        return R.drawable.ic_new_portrait_button_normal;
    }

    private int getSettingResources() {
        return R.drawable.ic_new_config_setting;
    }

    private int[] getSuperEISResources() {
        return new int[]{R.drawable.ic_config_super_eis_off, R.drawable.ic_config_super_eis_on};
    }

    private FragmentTopAlert getTopAlert() {
        if (this.mFragmentTopAlert == null) {
            Log.d(TAG, "getTopAlert(): fragment is null");
            return null;
        } else if (this.mFragmentTopAlert.isAdded()) {
            return this.mFragmentTopAlert;
        } else {
            Log.d(TAG, "getTopAlert(): fragment is not added yet");
            return null;
        }
    }

    private FragmentTopConfigExtra getTopExtra() {
        return (FragmentTopConfigExtra) getChildFragmentManager().findFragmentByTag(String.valueOf(245));
    }

    private int[] getUltraPixelPortraitResources() {
        return new int[]{R.drawable.ic_config_ultrapixelportrait_off, R.drawable.ic_config_ultrapixelportrait_on};
    }

    private int[] getUltraWideBokehResources() {
        return new int[]{R.drawable.ic_ultra_wide_bokeh, R.drawable.ic_ultra_wide_bokeh_highlight};
    }

    private int[] getUltraWideResources() {
        return new int[]{R.drawable.icon_config_ultra_wide_off, R.drawable.icon_config_ultra_wide_on};
    }

    private int[] getVideoBokehResources() {
        return new int[]{R.drawable.ic_new_portrait_button_normal, R.drawable.ic_new_portrait_button_on};
    }

    private void initTopView() {
        ImageView imageView = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_00);
        ImageView imageView2 = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_01);
        ImageView imageView3 = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_02);
        ImageView imageView4 = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_03);
        ImageView imageView5 = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_04);
        ImageView imageView6 = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_05);
        ImageView imageView7 = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_06);
        ImageView imageView8 = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_07);
        ImageView imageView9 = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_08);
        ImageView imageView10 = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_09);
        ImageView imageView11 = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_10);
        imageView.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        imageView5.setOnClickListener(this);
        imageView6.setOnClickListener(this);
        imageView7.setOnClickListener(this);
        imageView8.setOnClickListener(this);
        imageView9.setOnClickListener(this);
        imageView10.setOnClickListener(this);
        imageView11.setOnClickListener(this);
        this.mConfigViews = new ArrayList();
        this.mConfigViews.add(imageView);
        this.mConfigViews.add(imageView2);
        this.mConfigViews.add(imageView3);
        this.mConfigViews.add(imageView4);
        this.mConfigViews.add(imageView5);
        this.mConfigViews.add(imageView6);
        this.mConfigViews.add(imageView7);
        this.mConfigViews.add(imageView8);
        this.mConfigViews.add(imageView9);
        this.mConfigViews.add(imageView10);
        this.mConfigViews.add(imageView11);
    }

    public static /* synthetic */ void lambda$reInitAlert$0(FragmentTopConfig fragmentTopConfig, FragmentTopAlert fragmentTopAlert, boolean z) {
        if (fragmentTopAlert != null) {
            fragmentTopAlert.setShow(true);
            fragmentTopConfig.reConfigCommonTip();
            fragmentTopConfig.reConfigTipOfFlash(z);
            fragmentTopConfig.reConfigTipOfHdr(z);
            fragmentTopAlert.updateMusicHint();
            fragmentTopConfig.alertUpdateValue(4);
            fragmentTopConfig.updateLyingDirectHint(false, true);
            ModeProtocol.TopConfigProtocol topConfigProtocol = (ModeProtocol.TopConfigProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(193);
            if (topConfigProtocol != null) {
                switch (fragmentTopConfig.mCurrentMode) {
                    case 162:
                    case 163:
                        topConfigProtocol.reShowMoon();
                        break;
                }
            }
            ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
            if (configChanges != null) {
                configChanges.reCheckVideoUltraClearTip();
                configChanges.reCheckParameterResetTip(true);
                configChanges.reCheckRaw();
                configChanges.reCheckMacroMode();
                configChanges.reCheckSubtitleMode();
            }
        }
    }

    private void reConfigCommonTip() {
        if (CameraSettings.isHandGestureOpen() && DataRepository.dataItemRunning().getHandGestureRunning()) {
            alertTopHint(0, (int) R.string.hand_gesture_tip);
        }
        if (CameraSettings.isUltraPixelOn()) {
            alertTopHint(0, DataRepository.dataItemRunning().getComponentUltraPixel().getUltraPixelOpenTip());
        }
        if (!"-1".equals(CameraSettings.getEyeLightType())) {
            alertTopHint(0, (int) R.string.eye_light);
        }
        if (CameraSettings.isUltraPixelPortraitFrontOn()) {
            alertTopHint(0, (int) R.string.ultra_pixel_portrait_hint);
        }
        if (CameraSettings.isSuperEISEnabled(this.mCurrentMode)) {
            alertTopHint(0, (int) R.string.super_eis);
        }
    }

    private void reConfigTipOfFlash(boolean z) {
        if (!isExtraMenuShowing()) {
            ComponentConfigFlash componentFlash = DataRepository.dataItemConfig().getComponentFlash();
            if (!componentFlash.isEmpty()) {
                String componentValue = componentFlash.getComponentValue(this.mCurrentMode);
                if ("1".equals(componentValue) || ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_ON.equals(componentValue)) {
                    alertFlash(0, "1", false, z);
                } else if ("2".equals(componentValue)) {
                    alertFlash(0, "2", false, z);
                } else if ("5".equals(componentValue)) {
                    alertFlash(0, "5", false, z);
                } else {
                    alertFlash(8, "1", false, z);
                }
            }
        }
    }

    private void reConfigTipOfHdr(boolean z) {
        ComponentConfigHdr componentHdr = DataRepository.dataItemConfig().getComponentHdr();
        if (!componentHdr.isEmpty()) {
            String componentValue = componentHdr.getComponentValue(this.mCurrentMode);
            if ("on".equals(componentValue) || "normal".equals(componentValue)) {
                alertHDR(0, false, false, z);
            } else if ("live".equals(componentValue)) {
                alertHDR(0, true, false, z);
            } else {
                alertHDR(8, false, false, z);
            }
        }
    }

    private void reConfigTipOfSubtitle() {
        if (DataRepository.dataItemRunning().getComponentRunningSubtitle().isEnabled(this.mCurrentMode)) {
            alertSubtitleHint(0, R.string.pref_video_subtitle);
        } else {
            alertSubtitleHint(8, R.string.pref_video_subtitle);
        }
    }

    private void resetImages() {
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        this.mSupportedConfigs = SupportedConfigFactory.getSupportedTopConfigs(this.mCurrentMode, DataRepository.dataItemGlobal().getCurrentCameraId(), DataRepository.dataItemGlobal().isNormalIntent());
        for (int i = 0; i < this.mConfigViews.size(); i++) {
            ImageView imageView = this.mConfigViews.get(i);
            imageView.setEnabled(true);
            imageView.setColorFilter((ColorFilter) null);
            TopConfigItem configItem = this.mSupportedConfigs.getConfigItem(i);
            boolean topImageResource = setTopImageResource(configItem, imageView, this.mCurrentMode, dataItemConfig, false);
            TopConfigItem topConfigItem = (TopConfigItem) imageView.getTag();
            if (topConfigItem == null || topConfigItem.configItem != configItem.configItem) {
                imageView.setTag(configItem);
                imageView.clearAnimation();
                imageView.setVisibility(0);
                if (topImageResource) {
                    ViewCompat.setAlpha(imageView, 0.0f);
                    ViewCompat.animate(imageView).alpha(1.0f).setDuration(150).setStartDelay(150).start();
                } else {
                    imageView.setVisibility(4);
                }
            } else {
                imageView.setTag(configItem);
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x0338, code lost:
        r11 = null;
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:174:0x033f, code lost:
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x0340, code lost:
        r6 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x0341, code lost:
        r11 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x0342, code lost:
        if (r6 <= 0) goto L_0x03d0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x0344, code lost:
        r12 = getResources().getDrawable(r6);
        r9.margin = getInitialMargin(r9, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:179:0x0354, code lost:
        if (r9.margin <= 0) goto L_0x0388;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0056, code lost:
        r3 = com.android.camera.R.string.autozoom_hint;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x0356, code lost:
        r13 = (android.widget.FrameLayout.LayoutParams) r10.getLayoutParams();
        r9.margin -= (r12.getIntrinsicWidth() / 2) + r8.mViewPadding;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x036d, code lost:
        if (r8.mIsRTL == false) goto L_0x0381;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x036f, code lost:
        r13.leftMargin = ((r8.mTotalWidth - r9.margin) - r12.getIntrinsicWidth()) - (r8.mViewPadding * 2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:0x0381, code lost:
        r13.leftMargin = r9.margin;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:184:0x0385, code lost:
        r10.setLayoutParams(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x038c, code lost:
        if (r9.configItem != 177) goto L_0x0392;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:187:0x038e, code lost:
        r10.setImageDrawable((android.graphics.drawable.Drawable) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:188:0x0392, code lost:
        r10.setImageDrawable(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:190:0x0399, code lost:
        if (r9.configItem != 193) goto L_0x03b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:192:0x039d, code lost:
        if (r8.mCurrentMode != 167) goto L_0x03ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:194:0x03a3, code lost:
        if (com.android.camera.CameraSettings.isFlashSupportedInManualMode() != false) goto L_0x03ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:195:0x03a5, code lost:
        r10.setAlpha(0.4f);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:196:0x03ac, code lost:
        r10.setAlpha(1.0f);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:198:0x03b5, code lost:
        if (com.android.camera.Util.isAccessible() != false) goto L_0x03bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:200:0x03bb, code lost:
        if (com.android.camera.Util.isSetContentDesc() == false) goto L_0x03d0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:201:0x03bd, code lost:
        if (r3 <= 0) goto L_0x03c7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:202:0x03bf, code lost:
        r10.setContentDescription(getString(r3));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:204:0x03cb, code lost:
        if (android.text.TextUtils.isEmpty(r11) != false) goto L_0x03d0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:205:0x03cd, code lost:
        r10.setContentDescription(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:206:0x03d0, code lost:
        return true;
     */
    private boolean setTopImageResource(TopConfigItem topConfigItem, ImageView imageView, int i, DataItemConfig dataItemConfig, boolean z) {
        int i2;
        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        int i3 = topConfigItem.configItem;
        int i4 = R.string.accessibility_filter_open_panel;
        int i5 = 0;
        switch (i3) {
            case 176:
                return false;
            case 177:
                i2 = R.drawable.ic_new_config_flash_off;
            default:
                switch (i3) {
                    case 193:
                        ComponentConfigFlash componentFlash = dataItemConfig.getComponentFlash();
                        if (!componentFlash.isEmpty()) {
                            i5 = componentFlash.getValueSelectedDrawableIgnoreClose(i);
                            i4 = componentFlash.getValueSelectedStringIdIgnoreClose(i);
                            if (z) {
                                if (z && this.mCurrentMode == 167) {
                                    reConfigTipOfFlash(true);
                                    break;
                                }
                            } else {
                                reConfigTipOfFlash(true);
                                break;
                            }
                        }
                        break;
                    case 194:
                        ComponentConfigHdr componentHdr = dataItemConfig.getComponentHdr();
                        if (!componentHdr.isEmpty()) {
                            i5 = componentHdr.getValueSelectedDrawableIgnoreClose(i);
                            i4 = componentHdr.getValueSelectedStringIdIgnoreClose(i);
                            if (!z) {
                                reConfigTipOfHdr(true);
                                break;
                            }
                        }
                        break;
                    case 195:
                        i5 = getPortraitResources();
                        i4 = R.string.accessibility_protrait;
                        break;
                    case 196:
                        int parseInt = Integer.parseInt(z ? DataRepository.getInstance().backUp().getBackupFilter(i, DataRepository.dataItemGlobal().getCurrentCameraId()) : DataRepository.dataItemRunning().getComponentConfigFilter().getComponentValue(i));
                        i2 = (parseInt == FilterInfo.FILTER_ID_NONE || parseInt <= 0) ? this.mFilterResources[0] : this.mFilterResources[1];
                        if (actionProcessing != null) {
                            if (actionProcessing.isShowFilterView()) {
                                i4 = R.string.accessibility_filter_close_panel;
                                break;
                            }
                        }
                        break;
                    case 197:
                        i5 = getMoreResources();
                        i4 = R.string.accessibility_more;
                        break;
                    default:
                        switch (i3) {
                            case 199:
                                i5 = getFocusPeakImageResource();
                                i4 = R.string.accessibility_foucs_peak;
                                break;
                            case 200:
                                String componentValue = dataItemConfig.getComponentBokeh().getComponentValue(i);
                                i5 = "on".equals(componentValue) ? R.drawable.ic_portrait_button_on : R.drawable.ic_portrait_button_normal;
                                i4 = "on".equals(componentValue) ? R.string.accessibility_bokeh_on : R.string.accessibility_bokeh_normal;
                                break;
                            case 201:
                                i5 = CameraSettings.getAiSceneOpen(i) ? this.mAiSceneResources[1] : this.mAiSceneResources[0];
                                i4 = CameraSettings.getAiSceneOpen(i) ? R.string.accessibility_mimovie_on : R.string.accessibility_mimovie_off;
                                break;
                            default:
                                switch (i3) {
                                    case 203:
                                        i2 = DataRepository.dataItemRunning().getComponentRunningLighting().isSwitchOn(i) ? this.mLightingResource[1] : this.mLightingResource[0];
                                        if (actionProcessing != null) {
                                            if (!actionProcessing.isShowLightingView()) {
                                                i4 = R.string.accessibility_camera_lighting_open_panel;
                                                break;
                                            } else {
                                                i4 = R.string.accessibility_camera_lighting_close_panel;
                                                break;
                                            }
                                        }
                                    case 204:
                                        i5 = dataItemConfig.getComponentConfigSlowMotion().getImageResource();
                                        i4 = dataItemConfig.getComponentConfigSlowMotion().getContentDesc();
                                        break;
                                    case 205:
                                        ComponentConfigUltraWide componentConfigUltraWide = dataItemConfig.getComponentConfigUltraWide();
                                        if (!componentConfigUltraWide.isEmpty()) {
                                            i5 = componentConfigUltraWide.getValueSelectedDrawableIgnoreClose(i);
                                            i4 = componentConfigUltraWide.getValueSelectedStringIdIgnoreClose(i);
                                            break;
                                        }
                                        break;
                                    case 206:
                                        boolean isLiveShotOn = CameraSettings.isLiveShotOn();
                                        i5 = isLiveShotOn ? this.mLiveShotResource[1] : this.mLiveShotResource[0];
                                        i4 = isLiveShotOn ? R.string.accessibility_camera_liveshot_on : R.string.accessibility_camera_liveshot_off;
                                        break;
                                    case 207:
                                        boolean backupSwitchState = z ? DataRepository.getInstance().backUp().getBackupSwitchState(i, "pref_ultra_wide_bokeh_enabled", DataRepository.dataItemGlobal().getCurrentCameraId()) : DataRepository.dataItemRunning().isSwitchOn("pref_ultra_wide_bokeh_enabled");
                                        i5 = backupSwitchState ? this.mUltraWideBokehResources[1] : this.mUltraWideBokehResources[0];
                                        i4 = backupSwitchState ? R.string.accessibility_camera_ultra_wide_bokeh_on : R.string.accessibility_camera_ultra_wide_bokeh_off;
                                        break;
                                    default:
                                        switch (i3) {
                                            case 214:
                                                ComponentConfigMeter componentConfigMeter = dataItemConfig.getComponentConfigMeter();
                                                if (!componentConfigMeter.isEmpty()) {
                                                    i5 = componentConfigMeter.getValueSelectedDrawableIgnoreClose(i);
                                                    i4 = componentConfigMeter.getValueSelectedStringIdIgnoreClose(i);
                                                    break;
                                                }
                                                break;
                                            case 215:
                                                i5 = CameraSettings.isUltraPixelPortraitFrontOn() ? this.mUltraPixelPortraitResources[1] : this.mUltraPixelPortraitResources[0];
                                                i4 = R.string.ultra_pixel_portrait_hint;
                                                break;
                                            case 216:
                                                ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
                                                i2 = (baseDelegate == null || baseDelegate.getActiveFragment(R.id.bottom_action) != 65523) ? R.drawable.ic_config_vv_normal : R.drawable.ic_config_vv_on;
                                                break;
                                            case 217:
                                                i2 = R.drawable.ic_back;
                                            case 218:
                                                i5 = CameraSettings.isSuperEISEnabled(i) ? this.mSuperEISResources[1] : this.mSuperEISResources[0];
                                                i4 = R.string.super_eis;
                                                break;
                                            default:
                                                switch (i3) {
                                                    case 209:
                                                        boolean isUltraPixelOn = CameraSettings.isUltraPixelOn();
                                                        int i6 = isUltraPixelOn ? this.mUltraPixelPhotographyIconResources[1] : this.mUltraPixelPhotographyIconResources[0];
                                                        CharSequence charSequence = isUltraPixelOn ? this.mUltraPixelPhotographyTipString[1] : this.mUltraPixelPhotographyTipString[0];
                                                        i4 = 0;
                                                        i5 = i6;
                                                        break;
                                                    case 212:
                                                        i5 = DataRepository.dataItemRunning().getComponentRunningShine().getTopConfigEntryRes(i);
                                                        break;
                                                    case 220:
                                                        i5 = CameraSettings.isSubtitleEnabled(i) ? this.mAutoZoomResources[1] : this.mAutoZoomResources[0];
                                                        i4 = R.string.pref_video_subtitle;
                                                        if (!z) {
                                                            reConfigTipOfSubtitle();
                                                            break;
                                                        }
                                                        break;
                                                    case 225:
                                                        i5 = getSettingResources();
                                                        i4 = R.string.accessibility_setting;
                                                        break;
                                                    case 239:
                                                        i5 = i != 174 ? CameraSettings.isFaceBeautyOn(this.mCurrentMode, (BeautyValues) null) : CameraSettings.isLiveBeautyOpen() ? R.drawable.ic_beauty_on : R.drawable.ic_beauty_off;
                                                        i4 = R.string.accessibility_beauty_panel_open;
                                                        break;
                                                    case 243:
                                                        boolean isVideoBokehOn = CameraSettings.isVideoBokehOn();
                                                        Log.d(TAG, "setTopImageResource: VIDEO_BOKEH isSwitchOn = " + isVideoBokehOn);
                                                        i5 = isVideoBokehOn ? this.mVideoBokehResource[1] : this.mVideoBokehResource[0];
                                                        i4 = isVideoBokehOn ? R.string.pref_camera_video_bokeh_on : R.string.pref_camera_video_bokeh_off;
                                                        break;
                                                    case 245:
                                                        String[] currentLiveMusic = CameraSettings.getCurrentLiveMusic();
                                                        if (!currentLiveMusic[1].isEmpty()) {
                                                            alertTopMusicHint(0, currentLiveMusic[1]);
                                                            i2 = this.mLiveMusicSelectResources[1];
                                                        } else {
                                                            i2 = this.mLiveMusicSelectResources[0];
                                                        }
                                                    case 251:
                                                        i5 = CameraSettings.isMiMovieOpen(i) ? this.mMiMovieResources[1] : this.mMiMovieResources[0];
                                                        i4 = CameraSettings.getAiSceneOpen(i) ? R.string.accessibility_ai_scene_on : R.string.accessibility_ai_scene_off;
                                                        break;
                                                    case 253:
                                                        i5 = CameraSettings.isAutoZoomEnabled(i) ? this.mAutoZoomResources[1] : this.mAutoZoomResources[0];
                                                        break;
                                                    case 255:
                                                        i5 = CameraSettings.isMacroModeEnabled(i) ? this.mMacroResources[1] : this.mMacroResources[0];
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                        }
                }
        }
    }

    private void showMenu() {
        this.mTopConfigMenu.setVisibility(8);
        hideSwitchHint();
        hideAlert();
        this.mFragmentTopConfigExtra = new FragmentTopConfigExtra();
        this.mFragmentTopConfigExtra.setDegree(this.mDegree);
        FragmentUtils.addFragmentWithTag(getChildFragmentManager(), (int) R.id.top_config_extra, (Fragment) this.mFragmentTopConfigExtra, this.mFragmentTopConfigExtra.getFragmentTag());
        this.mIsShowExtraMenu = true;
    }

    public void alertAiDetectTipHint(int i, int i2, long j) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertRecommendTipHint(i, i2, j);
        }
    }

    public void alertAiSceneSelector(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertAiSceneSelector(i);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.reConfigQrCodeTip();
            }
        }
    }

    public void alertFlash(int i, String str, boolean z) {
        alertFlash(i, str, z, true);
    }

    public void alertFlash(int i, String str, boolean z, boolean z2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            if (z2) {
                if (i != 0) {
                    this.mLastAnimationComponent.reverse(true);
                } else if (z) {
                    ImageView topImage = getTopImage(193);
                    if (topImage != null) {
                        topImage.performClick();
                    }
                }
            }
            topAlert.alertFlash(i, str);
        }
    }

    public void alertHDR(int i, boolean z, boolean z2) {
        alertHDR(i, z, z2, true);
    }

    public void alertLightingHint(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertLightingHint(i);
        }
    }

    public void alertLightingTitle(boolean z) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertLightingTitle(z);
        }
    }

    public void alertMimojiFaceDetect(boolean z, int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertMimojiFaceDetect(z, i);
        }
    }

    public void alertMoonModeSelector(int i, boolean z) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertMoonSelector(getResources().getString(R.string.ai_scene_top_tip), getResources().getString(R.string.ai_scene_top_moon_off), i, z);
            if (this.mCurrentMode == 163) {
                ModeProtocol.CameraModuleSpecial cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195);
                if (cameraModuleSpecial != null) {
                    cameraModuleSpecial.showOrHideChip(i != 0);
                }
            }
        }
    }

    public void alertMusicClose(boolean z) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.alertMusicClose(z);
        }
    }

    public void alertParameterResetTip(boolean z, int i, @StringRes int i2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.alertParameterResetTip(z, i, i2, getResources().getDimensionPixelSize(R.dimen.video_ultra_tip_margin_top) + Util.getDisplayRect(getContext(), 0).top, !(this.mFragmentTopConfigExtra == null || !this.mFragmentTopConfigExtra.isAdded()));
        }
    }

    public void alertSubtitleHint(int i, int i2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertSubtitleHint(i, i2);
        }
    }

    public void alertSwitchHint(int i, @StringRes int i2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertSwitchHint(i, i2);
        }
    }

    public void alertSwitchHint(int i, String str) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertSwitchHint(i, str);
        }
    }

    public void alertTopHint(int i, @StringRes int i2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertTopHint(i, i2);
        }
    }

    public void alertTopHint(int i, int i2, long j) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertTopHint(i, i2, j);
        }
    }

    public void alertTopHint(int i, String str) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertTopHint(i, str);
        }
    }

    public void alertUpdateValue(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertUpdateValue(i);
        }
    }

    public void alertVideoUltraClear(int i, @StringRes int i2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.alertVideoUltraClear(i, i2, Util.getDisplayRect(getContext(), 0).top + getResources().getDimensionPixelSize(R.dimen.video_ultra_tip_margin_top), !(this.mFragmentTopConfigExtra == null || !this.mFragmentTopConfigExtra.isAdded()));
        }
    }

    public void clearAlertStatus() {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.clearAlertStatus();
        }
    }

    public void clearVideoUltraClear() {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.clearVideoUltraClear();
        }
    }

    public void directHideLyingDirectHint() {
    }

    public void disableMenuItem(boolean z, int... iArr) {
        if (iArr != null) {
            for (int i : iArr) {
                this.mDisabledFunctionMenu.put(i, z);
                if (z) {
                    ImageView topImage = getTopImage(i);
                    if (topImage != null) {
                        AlphaOutOnSubscribe.directSetResult(topImage);
                    }
                }
            }
        }
    }

    public void enableMenuItem(boolean z, int... iArr) {
        if (this.mDisabledFunctionMenu != null && this.mDisabledFunctionMenu.size() != 0) {
            for (int i : iArr) {
                this.mDisabledFunctionMenu.delete(i);
                if (z) {
                    ImageView topImage = getTopImage(i);
                    if (topImage != null) {
                        AlphaInOnSubscribe.directSetResult(topImage);
                    }
                }
            }
        }
    }

    public boolean getAlertIsShow() {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert == null) {
            return false;
        }
        return topAlert.isShow();
    }

    public int getCurrentAiSceneLevel() {
        return this.mCurrentAiSceneLevel;
    }

    public int getFragmentInto() {
        return 244;
    }

    /* access modifiers changed from: protected */
    public int getLayoutResourceId() {
        return R.layout.fragment_top_config;
    }

    public ImageView getTopImage(int i) {
        for (ImageView next : this.mConfigViews) {
            TopConfigItem topConfigItem = (TopConfigItem) next.getTag();
            if (topConfigItem != null && topConfigItem.configItem == i) {
                return next;
            }
        }
        return null;
    }

    public void hideAlert() {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.clear(true);
            topAlert.setShow(false);
        }
    }

    public void hideConfigMenu() {
        Completable.create(new AlphaOutOnSubscribe(this.mTopConfigMenu)).subscribe();
    }

    public void hideExtraMenu() {
        onBackEvent(6);
    }

    public void hideSwitchHint() {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.hideSwitchHint();
        }
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        this.mAiSceneResources = getAiSceneResources();
        this.mMiMovieResources = getMiMovieResources();
        this.mAutoZoomResources = getAutoZoomResources();
        this.mUltraWideResource = getUltraWideResources();
        this.mUltraWideBokehResources = getUltraWideBokehResources();
        this.mUltraPixelPhotographyIconResources = ComponentRunningUltraPixel.getUltraPixelTopMenuResources();
        this.mUltraPixelPhotographyTipString = ComponentRunningUltraPixel.getUltraPixelSwitchTipsString();
        this.mLiveShotResource = getLiveShotResources();
        this.mLightingResource = getLightingResources();
        this.mVideoBokehResource = getVideoBokehResources();
        this.mFilterResources = getFilterResources();
        this.mLiveMusicSelectResources = getMusicSelectResources();
        this.mMacroResources = getMacroResources();
        this.mUltraPixelPortraitResources = getUltraPixelPortraitResources();
        this.mSuperEISResources = getSuperEISResources();
        this.mIsRTL = Util.isLayoutRTL(getContext());
        this.mLastAnimationComponent = new LastAnimationComponent();
        this.mDisabledFunctionMenu = new SparseBooleanArray(1);
        this.mTopExtraParent = (ViewGroup) view.findViewById(R.id.top_config_extra);
        this.mTopConfigMenu = view.findViewById(R.id.top_config_menu);
        ((ViewGroup.MarginLayoutParams) this.mTopConfigMenu.getLayoutParams()).topMargin = Util.isNotchDevice ? Util.sStatusBarHeight : getResources().getDimensionPixelOffset(R.dimen.top_control_panel_extra_margin_top);
        initTopView();
        this.mExpandView = (RecyclerView) view.findViewById(R.id.top_config_expand_view);
        LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getContext(), "top_config_expand_view");
        linearLayoutManagerWrapper.setOrientation(0);
        this.mExpandView.setLayoutManager(linearLayoutManagerWrapper);
        this.mViewPadding = getResources().getDimensionPixelSize(R.dimen.panel_imageview_button_padding_width);
        this.mTopDrawableWidth = getResources().getDrawable(R.drawable.ic_new_config_flash_off).getIntrinsicWidth();
        this.mTotalWidth = Util.sWindowWidth;
        if (((ActivityBase) getContext()).getCameraIntentManager().isFromScreenSlide().booleanValue()) {
            Util.startScreenSlideAlphaInAnimation(this.mTopConfigMenu);
        }
        provideAnimateElement(this.mCurrentMode, (List<Completable>) null, 2);
    }

    public void insertConfigItem(int i) {
        resetImages();
    }

    public boolean isContainAlertRecommendTip(int... iArr) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert == null) {
            return false;
        }
        return topAlert.isContainAlertRecommendTip(iArr);
    }

    public boolean isCurrentRecommendTipText(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (!isTopAlertShowing(topAlert)) {
            return false;
        }
        return topAlert.isCurrentRecommendTipText(i);
    }

    public boolean isExtraMenuShowing() {
        return this.mFragmentTopConfigExtra != null && this.mFragmentTopConfigExtra.isAdded() && this.mIsShowExtraMenu;
    }

    public boolean isShowBacklightSelector() {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert == null) {
            return false;
        }
        return topAlert.isShowBacklightSelector();
    }

    public boolean isTopAlertShowing(FragmentTopAlert fragmentTopAlert) {
        return fragmentTopAlert != null && fragmentTopAlert.isShow() && !isExtraMenuShowing();
    }

    public boolean needViewClear() {
        return true;
    }

    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            configChanges.reCheckMutexConfigs(this.mCurrentMode);
            configChanges.reCheckFocusPeakConfig();
            configChanges.reCheckUltraPixel();
            configChanges.reCheckUltraPixelPortrait();
            configChanges.reCheckLiveShot();
            configChanges.reCheckHandGesture();
            configChanges.reCheckVideoUltraClearTip();
            configChanges.reCheckParameterResetTip(true);
            configChanges.reCheckRaw();
            configChanges.reCheckMacroMode();
            configChanges.reCheckSubtitleMode();
            configChanges.reCheckFrontBokenTip();
            configChanges.reCheckBeauty();
            configChanges.reCheckEyeLight();
            configChanges.reCheckSuperEIS();
            configChanges.reCheckMiMovie();
        }
    }

    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
        this.mDisplayRectTopMargin = Util.getDisplayRect(getContext()).top;
        int i3 = this.mCurrentMode;
        int i4 = 7;
        if (this.mResetType != 7) {
            i4 = 2;
        }
        provideAnimateElement(i3, (List<Completable>) null, i4);
        if (this.mFragmentTopAlert == null) {
            this.mFragmentTopAlert = new FragmentTopAlert();
            this.mFragmentTopAlert.setShow(!isExtraMenuShowing());
            this.mFragmentTopAlert.setDegree(this.mDegree);
            FragmentUtils.addFragmentWithTag(getChildFragmentManager(), (int) R.id.top_alert, (Fragment) this.mFragmentTopAlert, this.mFragmentTopAlert.getFragmentTag());
        }
    }

    public void onAngleChanged(float f) {
    }

    public boolean onBackEvent(int i) {
        boolean z = false;
        if (this.mLastAnimationComponent.reverse(i != 4)) {
            return true;
        }
        boolean z2 = this.mIsShowExtraMenu;
        FragmentTopConfigExtra topExtra = getTopExtra();
        if (topExtra == null) {
            return false;
        }
        switch (i) {
            case 1:
            case 2:
                if (this.mIsShowExtraMenu) {
                    topExtra.animateOut();
                    Completable.create(new AlphaInOnSubscribe(this.mTopConfigMenu).setStartDelayTime(200)).subscribe();
                    break;
                } else {
                    return false;
                }
            case 6:
                topExtra.animateOut();
                Completable.create(new AlphaInOnSubscribe(this.mTopConfigMenu).setStartDelayTime(200)).subscribe();
                break;
            case 7:
                z = z2;
                break;
            default:
                FragmentUtils.removeFragmentByTag(getChildFragmentManager(), String.valueOf(245));
                this.mTopConfigMenu.setVisibility(0);
                break;
        }
        if (!(i == 4 || i == 7)) {
            reInitAlert(true);
        }
        this.mIsShowExtraMenu = z;
        return true;
    }

    public void onBeautyRecordingStart() {
        onBackEvent(5);
        ViewCompat.animate(this.mTopConfigMenu).alpha(0.0f).start();
    }

    public void onBeautyRecordingStop() {
        ViewCompat.animate(this.mTopConfigMenu).alpha(1.0f).start();
    }

    public void onClick(View view) {
        Log.d(TAG, "top config onclick");
        if (isEnableClick()) {
            ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
            if (configChanges != null) {
                ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
                if (cameraAction != null && cameraAction.isDoingAction()) {
                    return;
                }
                if (!CameraSettings.isFrontCamera() || !((Camera) getContext()).isScreenSlideOff()) {
                    TopConfigItem topConfigItem = (TopConfigItem) view.getTag();
                    if (topConfigItem != null) {
                        if (this.mDisabledFunctionMenu.size() <= 0 || this.mDisabledFunctionMenu.indexOfKey(topConfigItem.configItem) < 0) {
                            ModeProtocol.CameraClickObservable cameraClickObservable = (ModeProtocol.CameraClickObservable) ModeCoordinatorImpl.getInstance().getAttachProtocol(227);
                            int i = topConfigItem.configItem;
                            switch (i) {
                                case 193:
                                    ComponentConfigFlash componentFlash = ((DataItemConfig) DataRepository.provider().dataConfig()).getComponentFlash();
                                    if (componentFlash.disableUpdate()) {
                                        int disableReasonString = componentFlash.getDisableReasonString();
                                        if (disableReasonString != 0) {
                                            ToastUtils.showToast(CameraAppImpl.getAndroidContext(), disableReasonString);
                                        }
                                        Log.w(TAG, "ignore click flash for disable update");
                                    } else if (this.mCurrentMode != 171 || !DataRepository.dataItemFeature().ie() || !CameraSettings.isBackCamera()) {
                                        expandExtra(componentFlash, view, topConfigItem.configItem);
                                    } else {
                                        String componentValue = componentFlash.getComponentValue(this.mCurrentMode);
                                        String str = componentValue == "0" ? "5" : "0";
                                        componentFlash.setComponentValue(this.mCurrentMode, str);
                                        onExpandValueChange(componentFlash, componentValue, str);
                                    }
                                    if (cameraClickObservable != null) {
                                        cameraClickObservable.subscribe(161);
                                        return;
                                    }
                                    return;
                                case 194:
                                    expandExtra(((DataItemConfig) DataRepository.provider().dataConfig()).getComponentHdr(), view, topConfigItem.configItem);
                                    if (cameraClickObservable != null) {
                                        cameraClickObservable.subscribe(162);
                                        return;
                                    }
                                    return;
                                case 195:
                                    configChanges.onConfigChanged(195);
                                    return;
                                case 196:
                                    configChanges.onConfigChanged(196);
                                    return;
                                case 197:
                                    showMenu();
                                    if (cameraClickObservable != null) {
                                        cameraClickObservable.subscribe(164);
                                        return;
                                    }
                                    return;
                                default:
                                    switch (i) {
                                        case 199:
                                            configChanges.onConfigChanged(199);
                                            ((ImageView) view).setImageResource(getFocusPeakImageResource());
                                            return;
                                        case 200:
                                            DataItemConfig dataItemConfig = (DataItemConfig) DataRepository.provider().dataConfig();
                                            dataItemConfig.getComponentBokeh().toggle(this.mCurrentMode);
                                            String componentValue2 = dataItemConfig.getComponentBokeh().getComponentValue(this.mCurrentMode);
                                            CameraStatUtil.tarckBokenChanged(this.mCurrentMode, componentValue2);
                                            updateConfigItem(200);
                                            if (dataItemConfig.reConfigHdrIfBokehChanged(this.mCurrentMode, componentValue2)) {
                                                updateConfigItem(194);
                                            }
                                            configChanges.configBokeh(componentValue2);
                                            return;
                                        case 201:
                                            configChanges.onConfigChanged(201);
                                            if (cameraClickObservable != null) {
                                                cameraClickObservable.subscribe(166);
                                                return;
                                            }
                                            return;
                                        default:
                                            switch (i) {
                                                case 203:
                                                    configChanges.onConfigChanged(203);
                                                    return;
                                                case 204:
                                                    configChanges.onConfigChanged(204);
                                                    return;
                                                case 205:
                                                    configChanges.onConfigChanged(205);
                                                    return;
                                                case 206:
                                                    configChanges.onConfigChanged(206);
                                                    if (cameraClickObservable != null) {
                                                        cameraClickObservable.subscribe(163);
                                                        return;
                                                    }
                                                    return;
                                                case 207:
                                                    configChanges.onConfigChanged(207);
                                                    return;
                                                default:
                                                    switch (i) {
                                                        case 214:
                                                            CameraStatUtil.trackMeterClick();
                                                            expandExtra(((DataItemConfig) DataRepository.provider().dataConfig()).getComponentConfigMeter(), view, topConfigItem.configItem);
                                                            return;
                                                        case 215:
                                                            configChanges.onConfigChanged(215);
                                                            return;
                                                        case 216:
                                                            CameraStatUtil.trackVVIconClick();
                                                            configChanges.onConfigChanged(216);
                                                            return;
                                                        case 217:
                                                            configChanges.onConfigChanged(217);
                                                            return;
                                                        case 218:
                                                            configChanges.onConfigChanged(218);
                                                            return;
                                                        default:
                                                            switch (i) {
                                                                case 209:
                                                                    configChanges.onConfigChanged(209);
                                                                    return;
                                                                case 212:
                                                                    configChanges.onConfigChanged(212);
                                                                    return;
                                                                case 220:
                                                                    configChanges.onConfigChanged(220);
                                                                    return;
                                                                case 225:
                                                                    configChanges.showSetting();
                                                                    return;
                                                                case 239:
                                                                    configChanges.onConfigChanged(239);
                                                                    return;
                                                                case 243:
                                                                    configChanges.onConfigChanged(243);
                                                                    return;
                                                                case 245:
                                                                    Fragment fragmentByTag = FragmentUtils.getFragmentByTag(getFragmentManager(), FragmentLiveMusic.TAG);
                                                                    CameraStatUtil.trackLiveMusicClick();
                                                                    if (fragmentByTag == null) {
                                                                        FragmentLiveMusic fragmentLiveMusic = new FragmentLiveMusic();
                                                                        fragmentLiveMusic.setStyle(2, R.style.TTMusicDialogFragment);
                                                                        getFragmentManager().beginTransaction().add((Fragment) fragmentLiveMusic, FragmentLiveMusic.TAG).commitAllowingStateLoss();
                                                                        return;
                                                                    }
                                                                    return;
                                                                case 251:
                                                                    configChanges.onConfigChanged(251);
                                                                    if (cameraClickObservable != null) {
                                                                        cameraClickObservable.subscribe(169);
                                                                        return;
                                                                    }
                                                                    return;
                                                                case 253:
                                                                    configChanges.onConfigChanged(253);
                                                                    return;
                                                                case 255:
                                                                    configChanges.onConfigChanged(255);
                                                                    return;
                                                                default:
                                                                    return;
                                                            }
                                                    }
                                            }
                                    }
                            }
                        }
                    }
                }
            }
        }
    }

    public void onExpandValueChange(ComponentData componentData, String str, String str2) {
        if (isEnableClick()) {
            DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
            ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
            if (configChanges != null) {
                int displayTitleString = componentData.getDisplayTitleString();
                if (displayTitleString == R.string.pref_camera_flashmode_title) {
                    if (componentData.getDisplayTitleString() == R.string.pref_camera_flashmode_title && !((str != "5" && str2 != "5") || str2 == "0" || str2 == ComponentConfigFlash.FLASH_VALUE_MANUAL_OFF)) {
                        CameraStatUtil.trackFlashChanged(this.mCurrentMode, "0");
                        configChanges.configBackSoftLightSwitch("0");
                    }
                    CameraStatUtil.trackFlashChanged(this.mCurrentMode, str2);
                    updateConfigItem(193);
                    if (dataItemConfig.reConfigHhrIfFlashChanged(this.mCurrentMode, str2)) {
                        updateConfigItem(194);
                    }
                    configChanges.configFlash(str2);
                } else if (displayTitleString == R.string.pref_camera_autoexposure_title) {
                    CameraStatUtil.trackPreferenceChange(componentData.getKey(this.mCurrentMode), str2);
                    updateConfigItem(214);
                    configChanges.configMeter(str2);
                } else if (displayTitleString == R.string.pref_camera_hdr_title) {
                    CameraStatUtil.trackHdrChanged(this.mCurrentMode, str2);
                    updateConfigItem(194);
                    configChanges.restoreMutexFlash(SupportedConfigFactory.CLOSE_BY_AI);
                    if (dataItemConfig.reConfigFlashIfHdrChanged(this.mCurrentMode, str2)) {
                        updateConfigItem(193);
                    }
                    if (dataItemConfig.reConfigBokehIfHdrChanged(this.mCurrentMode, str2)) {
                        updateConfigItem(200);
                    }
                    configChanges.configHdr(str2);
                }
                this.mLastAnimationComponent.reverse(true);
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        boolean z;
        int i3 = this.mCurrentMode;
        boolean z2 = i2 == 3;
        MimojiStatusManager mimojiStatusManager = DataRepository.dataItemLive().getMimojiStatusManager();
        if (this.mCurrentMode != 177 || !mimojiStatusManager.IsInMimojiCreate() || i2 == 3) {
            super.provideAnimateElement(i, list, i2);
            if (isInModeChanging() || i2 == 3) {
                this.mIsShowTopLyingDirectHint = false;
            }
            if (i3 != 169) {
                switch (i3) {
                    case 161:
                        if (i == 161) {
                            z = false;
                            break;
                        }
                    case 162:
                        break;
                    default:
                        z = true;
                        break;
                }
            }
            z = (i == 162 || i == 169) ? false : true;
            if (z) {
                onBackEvent(i2 == 7 ? 7 : 4);
            } else if (isExtraMenuShowing()) {
                refreshExtraMenu();
            }
            if (isExtraMenuShowing() && i2 == 7) {
                this.mFragmentTopConfigExtra.provideAnimateElement(i, list, i2);
            }
            if (z2) {
                if (!DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiPreview()) {
                    enableMenuItem(true, 197, 193);
                }
                this.mDisabledFunctionMenu.clear();
            }
            FragmentTopAlert topAlert = getTopAlert();
            if (topAlert != null) {
                topAlert.provideAnimateElement(i, list, i2);
            }
            DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
            int currentCameraId = DataRepository.dataItemGlobal().getCurrentCameraId();
            if (z2 && this.mTopConfigMenu.getVisibility() != 0) {
                AlphaInOnSubscribe.directSetResult(this.mTopConfigMenu);
            }
            this.mSupportedConfigs = SupportedConfigFactory.getSupportedTopConfigs(this.mCurrentMode, currentCameraId, DataRepository.dataItemGlobal().isNormalIntent());
            if (this.mSupportedConfigs != null) {
                for (int i4 = 0; i4 < this.mConfigViews.size(); i4++) {
                    ImageView imageView = this.mConfigViews.get(i4);
                    imageView.setEnabled(true);
                    TopConfigItem configItem = this.mSupportedConfigs.getConfigItem(i4);
                    boolean topImageResource = setTopImageResource(configItem, imageView, i, dataItemConfig, list != null);
                    if (!topImageResource || this.mDisabledFunctionMenu.indexOfKey(configItem.configItem) < 0 || !this.mDisabledFunctionMenu.get(configItem.configItem)) {
                        TopConfigItem topConfigItem = (TopConfigItem) imageView.getTag();
                        if (topConfigItem == null || topConfigItem.configItem != configItem.configItem) {
                            imageView.setTag(configItem);
                            if (list == null) {
                                if (topImageResource) {
                                    AlphaInOnSubscribe.directSetResult(imageView);
                                } else {
                                    AlphaOutOnSubscribe.directSetResult(imageView);
                                }
                            } else if (topImageResource) {
                                AlphaInOnSubscribe alphaInOnSubscribe = new AlphaInOnSubscribe(imageView);
                                if (this.mCurrentMode == 167 && !CameraSettings.isFlashSupportedInManualMode() && 193 == configItem.configItem) {
                                    alphaInOnSubscribe.setTargetAlpha(0.4f);
                                }
                                alphaInOnSubscribe.setStartDelayTime(150).setDurationTime(150);
                                list.add(Completable.create(alphaInOnSubscribe));
                            } else if (i3 == 165 || this.mCurrentMode == 165) {
                                AlphaOutOnSubscribe.directSetResult(imageView);
                            } else {
                                list.add(Completable.create(new AlphaOutOnSubscribe(imageView).setDurationTime(150)));
                            }
                        } else {
                            imageView.setTag(configItem);
                        }
                    }
                }
                return;
            }
            return;
        }
        FragmentTopAlert topAlert2 = getTopAlert();
        if (topAlert2 != null) {
            topAlert2.provideAnimateElement(i, list, i2);
        }
    }

    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        FragmentTopConfigExtra topExtra = getTopExtra();
        if (topExtra != null) {
            topExtra.provideRotateItem(list, i);
        }
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.provideRotateItem(list, i);
        }
    }

    public void reInitAlert(boolean z) {
        FragmentTopAlert topAlert = getTopAlert();
        if (!CameraSettings.isHandGestureOpen() || DataRepository.dataItemRunning().getHandGestureRunning()) {
            AndroidSchedulers.mainThread().scheduleDirect(new Runnable(topAlert, z) {
                private final /* synthetic */ FragmentTopAlert f$1;
                private final /* synthetic */ boolean f$2;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                }

                public final void run() {
                    FragmentTopConfig.lambda$reInitAlert$0(FragmentTopConfig.this, this.f$1, this.f$2);
                }
            }, this.mIsShowExtraMenu ? 120 : 0, TimeUnit.MILLISECONDS);
        }
    }

    public void refreshExtraMenu() {
        if (this.mFragmentTopConfigExtra != null && this.mFragmentTopConfigExtra.isAdded()) {
            this.mFragmentTopConfigExtra.reFresh();
        }
    }

    /* access modifiers changed from: protected */
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
        modeCoordinator.attachProtocol(172, this);
    }

    public void removeConfigItem(int i) {
        resetImages();
    }

    public void removeExtraMenu(int i) {
        onBackEvent(i);
    }

    public void rotate() {
    }

    public void setAiSceneImageLevel(int i) {
        if (i == 25) {
            i = 23;
        }
        this.mCurrentAiSceneLevel = i;
        Drawable aiSceneDrawable = getAiSceneDrawable(i);
        ImageView topImage = getTopImage(201);
        if (aiSceneDrawable != null && topImage != null) {
            topImage.setImageDrawable(aiSceneDrawable);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.reConfigQrCodeTip();
            }
        }
    }

    public void setClickEnable(boolean z) {
        super.setClickEnable(z);
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.setClickEnable(z);
        }
    }

    public void setRecordingTimeState(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.setRecordingTimeState(i);
        } else {
            FragmentTopAlert.setPendingRecordingState(i);
        }
    }

    public void setShow(boolean z) {
        if (getTopAlert() != null) {
            getTopAlert().setShow(z);
        }
    }

    public void showConfigMenu() {
        Completable.create(new AlphaInOnSubscribe(this.mTopConfigMenu)).subscribe();
    }

    public void startLiveShotAnimation() {
        ImageView topImage = getTopImage(206);
        if (topImage != null) {
            Drawable drawable = topImage.getDrawable();
            if (drawable instanceof LayerDrawable) {
                RotateDrawable rotateDrawable = (RotateDrawable) ((LayerDrawable) drawable).getDrawable(0);
                if (this.mLiveShotAnimator == null || this.mLiveShotAnimator.getTarget() != rotateDrawable) {
                    this.mLiveShotAnimator = ObjectAnimator.ofInt(rotateDrawable, "level", new int[]{0, DurationConstant.DURATION_VIDEO_RECORDING_CIRCLE});
                    this.mLiveShotAnimator.setDuration(1000);
                    this.mLiveShotAnimator.setInterpolator(new CubicEaseInOutInterpolator());
                }
                if (this.mLiveShotAnimator.isRunning()) {
                    this.mLiveShotAnimator.cancel();
                }
                this.mLiveShotAnimator.start();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        unRegisterBackStack(modeCoordinator, this);
        modeCoordinator.detachProtocol(172, this);
    }

    public void updateConfigItem(int... iArr) {
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        for (int topImage : iArr) {
            ImageView topImage2 = getTopImage(topImage);
            if (topImage2 != null) {
                setTopImageResource((TopConfigItem) topImage2.getTag(), topImage2, this.mCurrentMode, dataItemConfig, false);
            }
        }
    }

    public void updateContentDescription() {
        ImageView topImage = getTopImage(196);
        if (topImage != null) {
            topImage.setContentDescription(getString(R.string.accessibility_filter_open_panel));
        }
    }

    public void updateLyingDirectHint(boolean z, boolean z2) {
        if (!z2) {
            this.mIsShowTopLyingDirectHint = z;
        }
        if (!isExtraMenuShowing()) {
            FragmentTopAlert topAlert = getTopAlert();
            if (isTopAlertShowing(topAlert)) {
                topAlert.updateLyingDirectHint(this.mIsShowTopLyingDirectHint);
            }
        }
    }

    public void updateRecordingTime(String str) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.updateRecordingTime(str);
        }
    }
}
