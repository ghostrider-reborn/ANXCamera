package com.android.camera.fragment.top;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.android.camera.data.data.runing.ComponentRunningSubtitle;
import com.android.camera.data.data.runing.ComponentRunningTiltValue;
import com.android.camera.data.data.runing.ComponentRunningTimer;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.android.camera.module.ModuleManager;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;

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

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x018d, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0196, code lost:
        r7 = r0;
        r0 = -1;
        r6 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x01af, code lost:
        r8 = true;
        r7 = r6;
        r6 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x01b2, code lost:
        r0 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x01c6, code lost:
        r7 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x01c7, code lost:
        r0 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0032, code lost:
        r8 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x022d, code lost:
        r7 = r6;
        r6 = false;
        r3 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0267, code lost:
        r9 = (android.widget.TextView) r14.getView(com.android.camera.R.id.extra_item_text);
        r10 = (com.android.camera.ui.ColorImageView) r14.getView(com.android.camera.R.id.extra_item_image);
        r11 = new java.lang.StringBuilder();
        r11.append(r9.getText());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0285, code lost:
        if (r0 == -1) goto L_0x0294;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0287, code lost:
        r11.append(r14.itemView.getResources().getString(r0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0294, code lost:
        if (r6 == false) goto L_0x02a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0296, code lost:
        r11.append(r14.itemView.getResources().getString(com.android.camera.R.string.accessibility_open));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x02a7, code lost:
        if (r3 != false) goto L_0x02b9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x02a9, code lost:
        r11.append(r14.itemView.getResources().getString(com.android.camera.R.string.accessibility_closed));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x02b9, code lost:
        r14.itemView.setContentDescription(r11);
        r9.setSelected(true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x02c1, code lost:
        if (r1 == -1) goto L_0x02c7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x02c3, code lost:
        r9.setText(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x02c7, code lost:
        r9.setText(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x02ca, code lost:
        r14.itemView.setEnabled(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x02cf, code lost:
        if (r8 == false) goto L_0x02d9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x02d1, code lost:
        if (r6 == false) goto L_0x02d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x02d3, code lost:
        r0 = r13.mSelectedColor;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x02d6, code lost:
        r0 = r13.mTextNormalColor;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x02d9, code lost:
        r0 = r13.mUnableClickColor;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x02db, code lost:
        if (r6 == false) goto L_0x02e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x02dd, code lost:
        r1 = r13.mSelectedColor;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x02e0, code lost:
        r1 = r13.mImageNormalColor;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x02e2, code lost:
        r9.setTextColor(r0);
        r10.setColor(r1);
        r10.setImageResource(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x02ef, code lost:
        if (com.android.camera.Util.isAccessible() != false) goto L_0x02f7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x02f5, code lost:
        if (com.android.camera.Util.isSetContentDesc() == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x02f9, code lost:
        if (r13.mTAG != r15) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x02fb, code lost:
        r14.itemView.postDelayed(new com.android.camera.fragment.top.ExtraAdapter.AnonymousClass1(r13), 100);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:?, code lost:
        return;
     */
    public void onBindViewHolder(final CommonRecyclerViewHolder commonRecyclerViewHolder, int i) {
        boolean z;
        int i2;
        boolean z2;
        int i3;
        int i4;
        int i5;
        int i6;
        boolean z3;
        int i7;
        int i8;
        int i9;
        boolean z4;
        int i10;
        int i11;
        boolean z5;
        int i12;
        int i13;
        int i14;
        int config = this.mSupportedConfigs.getConfig(i);
        commonRecyclerViewHolder.itemView.setTag(Integer.valueOf(config));
        commonRecyclerViewHolder.itemView.setOnClickListener(this.mOnClickListener);
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        boolean z6 = false;
        String str = null;
        switch (config) {
            case 209:
                ComponentRunningUltraPixel componentUltraPixel = DataRepository.dataItemRunning().getComponentUltraPixel();
                int menuDrawable = componentUltraPixel.getMenuDrawable();
                str = componentUltraPixel.getMenuString();
                z2 = CameraSettings.isUltraPixelOn();
                i2 = menuDrawable;
                i5 = -1;
                i3 = -1;
                break;
            case 210:
                ComponentConfigRatio componentConfigRatio = this.mDataItemConfig.getComponentConfigRatio();
                int valueSelectedDrawable = componentConfigRatio.getValueSelectedDrawable(currentMode);
                int displayTitleString = componentConfigRatio.getDisplayTitleString();
                i4 = componentConfigRatio.getValueDisplayString(currentMode);
                z = true;
                i3 = displayTitleString;
                i2 = valueSelectedDrawable;
                z2 = false;
                z6 = true;
                break;
            case 213:
                ComponentConfigSlowMotionQuality componentConfigSlowMotionQuality = this.mDataItemConfig.getComponentConfigSlowMotionQuality();
                i6 = componentConfigSlowMotionQuality.getValueSelectedDrawable(currentMode);
                int displayTitleString2 = componentConfigSlowMotionQuality.getDisplayTitleString();
                z3 = !componentConfigSlowMotionQuality.disableUpdate();
                i4 = componentConfigSlowMotionQuality.getValueDisplayString(currentMode);
                i7 = displayTitleString2;
                break;
            case 214:
                commonRecyclerViewHolder.itemView.setOnTouchListener(this);
                ComponentConfigVideoQuality componentConfigVideoQuality = this.mDataItemConfig.getComponentConfigVideoQuality();
                ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
                if (baseDelegate != null && baseDelegate.getActiveFragment(R.id.bottom_action) == 65523) {
                    int i15 = componentConfigVideoQuality.getForceItem("6").mIconRes;
                    i7 = componentConfigVideoQuality.getDisplayTitleString();
                    i6 = i15;
                    i4 = -1;
                    z3 = false;
                    break;
                } else {
                    int valueSelectedDrawable2 = componentConfigVideoQuality.getValueSelectedDrawable(currentMode);
                    int displayTitleString3 = componentConfigVideoQuality.getDisplayTitleString();
                    z3 = !componentConfigVideoQuality.disableUpdate();
                    i4 = componentConfigVideoQuality.getValueDisplayString(currentMode);
                    i6 = valueSelectedDrawable2;
                    i7 = displayTitleString3;
                    break;
                }
                break;
            case 219:
                i9 = R.drawable.ic_config_reference_line;
                i8 = R.string.pref_camera_referenceline_title;
                z4 = DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_REFERENCE_LINE, false);
                break;
            case 220:
                ComponentRunningSubtitle componentRunningSubtitle = DataRepository.dataItemRunning().getComponentRunningSubtitle();
                z5 = componentRunningSubtitle.isEnabled(currentMode);
                i10 = componentRunningSubtitle.getResIcon(z5);
                i11 = componentRunningSubtitle.getResText();
                break;
            case 225:
                i13 = R.drawable.ic_config_extra_setting;
                i3 = R.string.config_name_setting;
                break;
            case 226:
                ComponentRunningTimer componentRunningTimer = this.mDataItemRunning.getComponentRunningTimer();
                z2 = componentRunningTimer.isSwitchOn();
                i2 = componentRunningTimer.getValueSelectedDrawable(160);
                i14 = componentRunningTimer.getValueDisplayString(160);
                break;
            case 228:
                ComponentRunningTiltValue componentRunningTiltValue = this.mDataItemRunning.getComponentRunningTiltValue();
                boolean z7 = this.mDataItemRunning.isSwitchOn("pref_camera_tilt_shift_mode");
                if (!z7) {
                    i12 = R.drawable.ic_config_tilt;
                    z = true;
                    i11 = R.string.config_name_tilt;
                    break;
                } else {
                    z = true;
                    i11 = componentRunningTiltValue.getValueDisplayString(160);
                    i12 = componentRunningTiltValue.getValueSelectedDrawable(160);
                    break;
                }
            case 229:
                i9 = R.drawable.ic_config_straighten;
                i8 = R.string.config_name_straighten;
                z4 = this.mDataItemRunning.isSwitchOn("pref_camera_gradienter_key");
                break;
            case 230:
                i9 = R.drawable.ic_config_hht;
                i8 = R.string.config_name_hht;
                z4 = this.mDataItemRunning.isSwitchOn("pref_camera_hand_night_key");
                break;
            case 231:
                i9 = R.drawable.ic_config_magic;
                i8 = R.string.config_name_magic;
                z4 = this.mDataItemRunning.isSwitchOn("pref_camera_ubifocus_key");
                break;
            case 233:
                i9 = R.drawable.ic_config_fast_motion;
                i8 = R.string.pref_video_speed_fast_title;
                z4 = ModuleManager.isFastMotionModule();
                break;
            case 234:
                i9 = R.drawable.ic_config_scene;
                i8 = R.string.config_name_scene;
                z4 = this.mDataItemRunning.isSwitchOn("pref_camera_scenemode_setting_key");
                break;
            case 235:
                i9 = R.drawable.ic_config_group;
                i8 = R.string.config_name_group;
                z4 = this.mDataItemRunning.isSwitchOn("pref_camera_groupshot_mode_key");
                break;
            case 236:
                i9 = R.drawable.ic_config_magic_mirror;
                i8 = R.string.pref_camera_magic_mirror_title;
                z4 = this.mDataItemRunning.isSwitchOn("pref_camera_magic_mirror_key");
                break;
            case 237:
                i11 = R.string.pref_camera_picture_format_entry_raw;
                z5 = DataRepository.dataItemConfig().getComponentConfigRaw().isSwitchOn(currentMode);
                if (!z5) {
                    i10 = R.drawable.ic_raw_off;
                    break;
                } else {
                    i10 = R.drawable.ic_raw_on;
                    break;
                }
            case 238:
                i9 = R.drawable.ic_config_gender_age;
                i8 = R.string.pref_camera_show_gender_age_config_title;
                z4 = this.mDataItemRunning.isSwitchOn("pref_camera_show_gender_age_key");
                break;
            case 239:
                ComponentConfigBeauty componentConfigBeauty = this.mDataItemConfig.getComponentConfigBeauty();
                z2 = componentConfigBeauty.isSwitchOn(currentMode);
                i2 = componentConfigBeauty.getValueSelectedDrawable(currentMode);
                i14 = componentConfigBeauty.getValueDisplayString(currentMode);
                break;
            case 240:
                i9 = R.drawable.ic_config_dual_watermark;
                i8 = R.string.pref_camera_device_watermark_title;
                z4 = CameraSettings.isDualCameraWaterMarkOpen();
                break;
            case 241:
                i9 = R.drawable.ic_config_super_resolution;
                i8 = R.string.config_name_super_resolution;
                z4 = this.mDataItemRunning.isSwitchOn("pref_camera_super_resolution_key");
                break;
            case 242:
                if (!Util.isGlobalVersion()) {
                    i13 = R.drawable.ic_config_ai_detect_unselected;
                    i3 = R.string.pref_ai_detect;
                    break;
                } else {
                    i13 = R.drawable.ic_config_ai_glens;
                    i3 = R.string.pref_google_lens;
                    break;
                }
            case 252:
                i9 = R.drawable.ic_config_hand_gesture;
                i8 = R.string.hand_gesture_tip;
                z4 = this.mDataItemRunning.isSwitchOn("pref_hand_gesture");
                break;
            case 253:
                ComponentRunningAutoZoom componentRunningAutoZoom = DataRepository.dataItemRunning().getComponentRunningAutoZoom();
                z5 = componentRunningAutoZoom.isEnabled(currentMode);
                i10 = componentRunningAutoZoom.getResIcon(z5);
                i11 = componentRunningAutoZoom.getResText();
                break;
            case 255:
                ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
                z5 = componentRunningMacroMode.isSwitchOn(currentMode);
                i10 = componentRunningMacroMode.getResIcon(z5);
                i11 = componentRunningMacroMode.getResText();
                break;
            default:
                i5 = -1;
                i3 = -1;
                z2 = false;
                i2 = 0;
                break;
        }
    }

    public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_top_config_extra_item, viewGroup, false);
        if (this.mDegree != 0) {
            ViewCompat.setRotation(inflate, (float) this.mDegree);
        }
        return new CommonRecyclerViewHolder(inflate);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0025, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0026, code lost:
        return false;
     */
    public boolean onTouch(View view, MotionEvent motionEvent) {
        View findViewById = view.findViewById(R.id.extra_item_image);
        if (findViewById != null) {
            switch (motionEvent.getAction()) {
                case 0:
                    findViewById.setScaleX(0.93f);
                    findViewById.setScaleY(0.93f);
                    break;
                case 1:
                case 3:
                    findViewById.setScaleX(1.0f);
                    findViewById.setScaleY(1.0f);
                    break;
                case 2:
                    break;
            }
        } else {
            return false;
        }
    }

    public void setNewDegree(int i) {
        this.mDegree = i;
    }

    public void setOnClictTag(int i) {
        this.mTAG = i;
    }
}
