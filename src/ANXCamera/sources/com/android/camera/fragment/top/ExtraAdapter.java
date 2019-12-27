package com.android.camera.fragment.top;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.constant.ColorConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigBeauty;
import com.android.camera.data.data.config.ComponentConfigRatio;
import com.android.camera.data.data.config.ComponentConfigSlowMotionQuality;
import com.android.camera.data.data.config.ComponentConfigVideoQuality;
import com.android.camera.data.data.config.ComponentRunningMacroMode;
import com.android.camera.data.data.config.ComponentRunningUltraPixel;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.config.SupportedConfigs;
import com.android.camera.data.data.runing.ComponentRunningAutoZoom;
import com.android.camera.data.data.runing.ComponentRunningTiltValue;
import com.android.camera.data.data.runing.ComponentRunningTimer;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.android.camera.module.ModuleManager;
import com.android.camera.ui.ColorImageView;

public class ExtraAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> implements View.OnTouchListener {
    private DataItemConfig mDataItemConfig;
    private DataItemRunning mDataItemRunning;
    private int mDegree;
    private int mImageNormalColor;
    private View.OnClickListener mOnClickListener;
    private int mSelectedColor;
    private SupportedConfigs mSupportedConfigs;
    private int mTAG = -1;
    private int mTextNormalColor;
    private int mUnableClickColor;

    public ExtraAdapter(SupportedConfigs supportedConfigs, View.OnClickListener onClickListener) {
        this.mSupportedConfigs = supportedConfigs;
        this.mOnClickListener = onClickListener;
        this.mDataItemRunning = DataRepository.dataItemRunning();
        this.mDataItemConfig = DataRepository.dataItemConfig();
        this.mTextNormalColor = ColorConstant.COLOR_COMMON_NORMAL;
        this.mImageNormalColor = -1315861;
        this.mSelectedColor = -15101209;
        this.mUnableClickColor = 1207959551;
    }

    public int getItemCount() {
        return this.mSupportedConfigs.getLength();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x023d  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x024c  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x025d  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0279  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x027d  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0287  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x028f  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0293  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0296  */
    public void onBindViewHolder(final CommonRecyclerViewHolder commonRecyclerViewHolder, int i) {
        boolean z;
        int i2;
        boolean z2;
        int i3;
        int i4;
        boolean isUltraPixelOn;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        boolean z3;
        int i11;
        int i12;
        boolean z4;
        int i13;
        int i14;
        int i15;
        boolean z5;
        int config = this.mSupportedConfigs.getConfig(i);
        commonRecyclerViewHolder.itemView.setTag(Integer.valueOf(config));
        commonRecyclerViewHolder.itemView.setOnClickListener(this.mOnClickListener);
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        boolean z6 = false;
        String str = null;
        if (config != 209) {
            if (config != 210) {
                if (config == 213) {
                    ComponentConfigSlowMotionQuality componentConfigSlowMotionQuality = this.mDataItemConfig.getComponentConfigSlowMotionQuality();
                    i10 = componentConfigSlowMotionQuality.getValueSelectedDrawable(currentMode);
                    i9 = componentConfigSlowMotionQuality.getDisplayTitleString();
                    z3 = !componentConfigSlowMotionQuality.disableUpdate();
                    i4 = componentConfigSlowMotionQuality.getValueDisplayString(currentMode);
                } else if (config != 214) {
                    if (config != 225) {
                        if (config != 226) {
                            if (config != 252) {
                                if (config == 253) {
                                    ComponentRunningAutoZoom componentRunningAutoZoom = DataRepository.dataItemRunning().getComponentRunningAutoZoom();
                                    z5 = componentRunningAutoZoom.isEnabled(currentMode);
                                    i15 = componentRunningAutoZoom.getResIcon(z5);
                                    i3 = componentRunningAutoZoom.getResText();
                                } else if (config != 255) {
                                    switch (config) {
                                        case 228:
                                            ComponentRunningTiltValue componentRunningTiltValue = this.mDataItemRunning.getComponentRunningTiltValue();
                                            boolean isSwitchOn = this.mDataItemRunning.isSwitchOn("pref_camera_tilt_shift_mode");
                                            if (isSwitchOn) {
                                                z4 = isSwitchOn;
                                                z = true;
                                                i3 = componentRunningTiltValue.getValueDisplayString(160);
                                                i2 = componentRunningTiltValue.getValueSelectedDrawable(160);
                                                break;
                                            } else {
                                                i13 = R.string.config_name_tilt;
                                                z = true;
                                                i2 = R.drawable.ic_config_tilt;
                                                z4 = isSwitchOn;
                                            }
                                        case 229:
                                            i14 = R.drawable.ic_config_straighten;
                                            i8 = R.string.config_name_straighten;
                                            z2 = this.mDataItemRunning.isSwitchOn("pref_camera_gradienter_key");
                                            break;
                                        case 230:
                                            i14 = R.drawable.ic_config_hht;
                                            i8 = R.string.config_name_hht;
                                            z2 = this.mDataItemRunning.isSwitchOn("pref_camera_hand_night_key");
                                            break;
                                        case 231:
                                            i14 = R.drawable.ic_config_magic;
                                            i8 = R.string.config_name_magic;
                                            z2 = this.mDataItemRunning.isSwitchOn("pref_camera_ubifocus_key");
                                            break;
                                        default:
                                            switch (config) {
                                                case 233:
                                                    i14 = R.drawable.ic_config_fast_motion;
                                                    i8 = R.string.pref_video_speed_fast_title;
                                                    z2 = ModuleManager.isFastMotionModule();
                                                    break;
                                                case 234:
                                                    i14 = R.drawable.ic_config_scene;
                                                    i8 = R.string.config_name_scene;
                                                    z2 = this.mDataItemRunning.isSwitchOn("pref_camera_scenemode_setting_key");
                                                    break;
                                                case 235:
                                                    i14 = R.drawable.ic_config_group;
                                                    i8 = R.string.config_name_group;
                                                    z2 = this.mDataItemRunning.isSwitchOn("pref_camera_groupshot_mode_key");
                                                    break;
                                                case 236:
                                                    i14 = R.drawable.ic_config_magic_mirror;
                                                    i8 = R.string.pref_camera_magic_mirror_title;
                                                    z2 = this.mDataItemRunning.isSwitchOn("pref_camera_magic_mirror_key");
                                                    break;
                                                case 237:
                                                    i3 = R.string.pref_camera_picture_format_entry_raw;
                                                    z5 = DataRepository.dataItemConfig().getComponentConfigRaw().isSwitchOn(currentMode);
                                                    if (!z5) {
                                                        i15 = R.drawable.ic_raw_off;
                                                        break;
                                                    } else {
                                                        i15 = R.drawable.ic_raw_on;
                                                        break;
                                                    }
                                                case 238:
                                                    i14 = R.drawable.ic_config_gender_age;
                                                    i8 = R.string.pref_camera_show_gender_age_config_title;
                                                    z2 = this.mDataItemRunning.isSwitchOn("pref_camera_show_gender_age_key");
                                                    break;
                                                case 239:
                                                    ComponentConfigBeauty componentConfigBeauty = this.mDataItemConfig.getComponentConfigBeauty();
                                                    z2 = componentConfigBeauty.isSwitchOn(currentMode);
                                                    i6 = componentConfigBeauty.getValueSelectedDrawable(currentMode);
                                                    i8 = componentConfigBeauty.getValueDisplayString(currentMode);
                                                    break;
                                                case 240:
                                                    i14 = R.drawable.ic_config_dual_watermark;
                                                    i8 = R.string.pref_camera_device_watermark_title;
                                                    z2 = CameraSettings.isDualCameraWaterMarkOpen();
                                                    break;
                                                case 241:
                                                    i14 = R.drawable.ic_config_super_resolution;
                                                    i8 = R.string.config_name_super_resolution;
                                                    z2 = this.mDataItemRunning.isSwitchOn("pref_camera_super_resolution_key");
                                                    break;
                                                case 242:
                                                    if (!Util.isGlobalVersion()) {
                                                        i11 = R.drawable.ic_config_ai_detect_unselected;
                                                        i12 = R.string.pref_ai_detect;
                                                        break;
                                                    } else {
                                                        i11 = R.drawable.ic_config_ai_glens;
                                                        i12 = R.string.pref_google_lens;
                                                        break;
                                                    }
                                                default:
                                                    isUltraPixelOn = false;
                                                    i5 = 0;
                                                    break;
                                            }
                                    }
                                } else {
                                    ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
                                    z5 = componentRunningMacroMode.isSwitchOn(currentMode);
                                    i15 = componentRunningMacroMode.getResIcon(z5);
                                    i3 = componentRunningMacroMode.getResText();
                                }
                                z = true;
                                i2 = i15;
                                z4 = z5;
                                i4 = -1;
                            } else {
                                i14 = R.drawable.ic_config_hand_gesture;
                                i8 = R.string.hand_gesture_tip;
                                z2 = this.mDataItemRunning.isSwitchOn("pref_hand_gesture");
                            }
                            i6 = i14;
                            i7 = -1;
                            z = true;
                        } else {
                            ComponentRunningTimer componentRunningTimer = this.mDataItemRunning.getComponentRunningTimer();
                            boolean isSwitchOn2 = componentRunningTimer.isSwitchOn();
                            i2 = componentRunningTimer.getValueSelectedDrawable(160);
                            i13 = componentRunningTimer.getValueDisplayString(160);
                            z4 = isSwitchOn2;
                            z = true;
                        }
                        i3 = i13;
                        i4 = -1;
                    } else {
                        i11 = R.drawable.ic_config_extra_setting;
                        i12 = R.string.config_name_setting;
                    }
                    i6 = i11;
                    z2 = false;
                    i7 = -1;
                    z = true;
                } else {
                    commonRecyclerViewHolder.itemView.setOnTouchListener(this);
                    ComponentConfigVideoQuality componentConfigVideoQuality = this.mDataItemConfig.getComponentConfigVideoQuality();
                    i10 = componentConfigVideoQuality.getValueSelectedDrawable(currentMode);
                    i9 = componentConfigVideoQuality.getDisplayTitleString();
                    z3 = !componentConfigVideoQuality.disableUpdate();
                    i4 = componentConfigVideoQuality.getValueDisplayString(currentMode);
                }
                i3 = i9;
                i2 = i10;
                z2 = false;
                z6 = true;
            } else {
                ComponentConfigRatio componentConfigRatio = this.mDataItemConfig.getComponentConfigRatio();
                int valueSelectedDrawable = componentConfigRatio.getValueSelectedDrawable(currentMode);
                int displayTitleString = componentConfigRatio.getDisplayTitleString();
                i4 = componentConfigRatio.getValueDisplayString(currentMode);
                z = true;
                i3 = displayTitleString;
                i2 = valueSelectedDrawable;
                z2 = false;
                z6 = true;
            }
            TextView textView = (TextView) commonRecyclerViewHolder.getView(R.id.extra_item_text);
            ColorImageView colorImageView = (ColorImageView) commonRecyclerViewHolder.getView(R.id.extra_item_image);
            StringBuilder sb = new StringBuilder();
            sb.append(textView.getText());
            if (i4 != -1) {
                sb.append(commonRecyclerViewHolder.itemView.getResources().getString(i4));
            }
            if (!z2) {
                sb.append(commonRecyclerViewHolder.itemView.getResources().getString(R.string.accessibility_open));
            } else if (!z6) {
                sb.append(commonRecyclerViewHolder.itemView.getResources().getString(R.string.accessibility_closed));
            }
            commonRecyclerViewHolder.itemView.setContentDescription(sb);
            textView.setSelected(true);
            if (i3 == -1) {
                textView.setText(i3);
            } else {
                textView.setText(str);
            }
            commonRecyclerViewHolder.itemView.setEnabled(z);
            int i16 = !z ? z2 ? this.mSelectedColor : this.mTextNormalColor : this.mUnableClickColor;
            int i17 = !z2 ? this.mSelectedColor : this.mImageNormalColor;
            textView.setTextColor(i16);
            colorImageView.setColor(i17);
            colorImageView.setImageResource(i2);
            if ((!Util.isAccessible() || Util.isSetContentDesc()) && this.mTAG == config) {
                commonRecyclerViewHolder.itemView.postDelayed(new Runnable() {
                    public void run() {
                        commonRecyclerViewHolder.itemView.sendAccessibilityEvent(128);
                    }
                }, 100);
            }
            return;
        }
        ComponentRunningUltraPixel componentUltraPixel = DataRepository.dataItemRunning().getComponentUltraPixel();
        int menuDrawable = componentUltraPixel.getMenuDrawable();
        str = componentUltraPixel.getMenuString();
        isUltraPixelOn = CameraSettings.isUltraPixelOn();
        i5 = menuDrawable;
        i7 = -1;
        i8 = -1;
        z = true;
        TextView textView2 = (TextView) commonRecyclerViewHolder.getView(R.id.extra_item_text);
        ColorImageView colorImageView2 = (ColorImageView) commonRecyclerViewHolder.getView(R.id.extra_item_image);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(textView2.getText());
        if (i4 != -1) {
        }
        if (!z2) {
        }
        commonRecyclerViewHolder.itemView.setContentDescription(sb2);
        textView2.setSelected(true);
        if (i3 == -1) {
        }
        commonRecyclerViewHolder.itemView.setEnabled(z);
        if (!z) {
        }
        if (!z2) {
        }
        textView2.setTextColor(i16);
        colorImageView2.setColor(i17);
        colorImageView2.setImageResource(i2);
        if (!Util.isAccessible()) {
        }
        commonRecyclerViewHolder.itemView.postDelayed(new Runnable() {
            public void run() {
                commonRecyclerViewHolder.itemView.sendAccessibilityEvent(128);
            }
        }, 100);
    }

    public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_top_config_extra_item, viewGroup, false);
        int i2 = this.mDegree;
        if (i2 != 0) {
            ViewCompat.setRotation(inflate, (float) i2);
        }
        return new CommonRecyclerViewHolder(inflate);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        View findViewById = view.findViewById(R.id.extra_item_image);
        if (findViewById == null) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action == 1 || (action != 2 && action == 3)) {
                findViewById.setScaleX(1.0f);
                findViewById.setScaleY(1.0f);
            }
            return false;
        }
        findViewById.setScaleX(0.93f);
        findViewById.setScaleY(0.93f);
        return false;
    }

    public void setNewDegree(int i) {
        this.mDegree = i;
    }

    public void setOnClictTag(int i) {
        this.mTAG = i;
    }
}
