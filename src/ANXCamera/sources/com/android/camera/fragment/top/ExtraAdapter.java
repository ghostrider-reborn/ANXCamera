package com.android.camera.fragment.top;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
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

public class ExtraAdapter extends Adapter<CommonRecyclerViewHolder> implements OnTouchListener {
    private DataItemConfig mDataItemConfig;
    private DataItemRunning mDataItemRunning;
    private int mDegree;
    private int mImageNormalColor;
    private OnClickListener mOnClickListener;
    private int mSelectedColor;
    private SupportedConfigs mSupportedConfigs;
    private int mTAG = -1;
    private int mTextNormalColor;
    private int mUnableClickColor;

    public ExtraAdapter(SupportedConfigs supportedConfigs, OnClickListener onClickListener) {
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

    /* JADX WARNING: type inference failed for: r7v0, types: [int] */
    /* JADX WARNING: type inference failed for: r1v7, types: [int] */
    /* JADX WARNING: type inference failed for: r7v1 */
    /* JADX WARNING: type inference failed for: r7v2 */
    /* JADX WARNING: type inference failed for: r7v3 */
    /* JADX WARNING: type inference failed for: r6v4, types: [int] */
    /* JADX WARNING: type inference failed for: r7v5 */
    /* JADX WARNING: type inference failed for: r6v6 */
    /* JADX WARNING: type inference failed for: r7v7 */
    /* JADX WARNING: type inference failed for: r6v8, types: [int] */
    /* JADX WARNING: type inference failed for: r6v9, types: [int] */
    /* JADX WARNING: type inference failed for: r7v10 */
    /* JADX WARNING: type inference failed for: r0v33 */
    /* JADX WARNING: type inference failed for: r7v11 */
    /* JADX WARNING: type inference failed for: r0v34 */
    /* JADX WARNING: type inference failed for: r7v12 */
    /* JADX WARNING: type inference failed for: r7v13 */
    /* JADX WARNING: type inference failed for: r7v14, types: [int] */
    /* JADX WARNING: type inference failed for: r0v40 */
    /* JADX WARNING: type inference failed for: r0v41 */
    /* JADX WARNING: type inference failed for: r7v16 */
    /* JADX WARNING: type inference failed for: r6v19 */
    /* JADX WARNING: type inference failed for: r7v17 */
    /* JADX WARNING: type inference failed for: r6v21, types: [int] */
    /* JADX WARNING: type inference failed for: r6v22, types: [int] */
    /* JADX WARNING: type inference failed for: r7v19 */
    /* JADX WARNING: type inference failed for: r0v48, types: [int] */
    /* JADX WARNING: type inference failed for: r7v21 */
    /* JADX WARNING: type inference failed for: r0v49 */
    /* JADX WARNING: type inference failed for: r0v50 */
    /* JADX WARNING: type inference failed for: r0v51 */
    /* JADX WARNING: type inference failed for: r0v52 */
    /* JADX WARNING: type inference failed for: r0v53 */
    /* JADX WARNING: type inference failed for: r0v54 */
    /* JADX WARNING: type inference failed for: r0v55 */
    /* JADX WARNING: type inference failed for: r6v41 */
    /* JADX WARNING: type inference failed for: r6v42 */
    /* JADX WARNING: type inference failed for: r0v57 */
    /* JADX WARNING: type inference failed for: r7v29, types: [int] */
    /* JADX WARNING: type inference failed for: r0v59 */
    /* JADX WARNING: type inference failed for: r0v60 */
    /* JADX WARNING: type inference failed for: r0v62 */
    /* JADX WARNING: type inference failed for: r0v63 */
    /* JADX WARNING: type inference failed for: r7v31 */
    /* JADX WARNING: type inference failed for: r7v32 */
    /* JADX WARNING: type inference failed for: r7v33 */
    /* JADX WARNING: type inference failed for: r6v50 */
    /* JADX WARNING: type inference failed for: r6v51 */
    /* JADX WARNING: type inference failed for: r7v34 */
    /* JADX WARNING: type inference failed for: r0v64 */
    /* JADX WARNING: type inference failed for: r7v35 */
    /* JADX WARNING: type inference failed for: r7v36 */
    /* JADX WARNING: type inference failed for: r7v37 */
    /* JADX WARNING: type inference failed for: r0v65 */
    /* JADX WARNING: type inference failed for: r6v52 */
    /* JADX WARNING: type inference failed for: r6v53 */
    /* JADX WARNING: type inference failed for: r0v66 */
    /* JADX WARNING: type inference failed for: r0v67 */
    /* JADX WARNING: type inference failed for: r0v68 */
    /* JADX WARNING: type inference failed for: r0v69 */
    /* JADX WARNING: type inference failed for: r0v70 */
    /* JADX WARNING: type inference failed for: r0v71 */
    /* JADX WARNING: type inference failed for: r0v72 */
    /* JADX WARNING: type inference failed for: r6v54 */
    /* JADX WARNING: type inference failed for: r6v55 */
    /* JADX WARNING: type inference failed for: r0v73 */
    /* JADX WARNING: type inference failed for: r7v38 */
    /* JADX WARNING: type inference failed for: r0v74 */
    /* JADX WARNING: type inference failed for: r0v75 */
    /* JADX WARNING: type inference failed for: r0v76 */
    /* JADX WARNING: type inference failed for: r0v77 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r7v2
  assigns: []
  uses: []
  mth insns count: 259
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
    /* JADX WARNING: Removed duplicated region for block: B:64:0x023d  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x024c  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x025d  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0279  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x027d  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0287  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x028f  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0293  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0296  */
    /* JADX WARNING: Unknown variable types count: 35 */
    public void onBindViewHolder(final CommonRecyclerViewHolder commonRecyclerViewHolder, int i) {
        boolean z;
        ? r7;
        boolean z2;
        int i2;
        int i3;
        boolean isUltraPixelOn;
        ? r72;
        ? r73;
        ? r74;
        int i4;
        int i5;
        int i6;
        ? r6;
        boolean z3;
        ? r75;
        ? r0;
        int i7;
        ? r76;
        ? r77;
        boolean z4;
        int i8;
        ? r02;
        ? r62;
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
                    ? valueSelectedDrawable = componentConfigSlowMotionQuality.getValueSelectedDrawable(currentMode);
                    i6 = componentConfigSlowMotionQuality.getDisplayTitleString();
                    z3 = !componentConfigSlowMotionQuality.disableUpdate();
                    i3 = componentConfigSlowMotionQuality.getValueDisplayString(currentMode);
                    r6 = valueSelectedDrawable;
                } else if (config != 214) {
                    if (config != 225) {
                        if (config != 226) {
                            if (config != 252) {
                                if (config == 253) {
                                    ComponentRunningAutoZoom componentRunningAutoZoom = DataRepository.dataItemRunning().getComponentRunningAutoZoom();
                                    z5 = componentRunningAutoZoom.isEnabled(currentMode);
                                    ? resIcon = componentRunningAutoZoom.getResIcon(z5);
                                    i2 = componentRunningAutoZoom.getResText();
                                    r62 = resIcon;
                                } else if (config != 255) {
                                    switch (config) {
                                        case 228:
                                            ComponentRunningTiltValue componentRunningTiltValue = this.mDataItemRunning.getComponentRunningTiltValue();
                                            boolean isSwitchOn = this.mDataItemRunning.isSwitchOn("pref_camera_tilt_shift_mode");
                                            if (isSwitchOn) {
                                                z4 = isSwitchOn;
                                                z = true;
                                                i2 = componentRunningTiltValue.getValueDisplayString(160);
                                                r76 = componentRunningTiltValue.getValueSelectedDrawable(160);
                                                break;
                                            } else {
                                                i8 = R.string.config_name_tilt;
                                                z = true;
                                                r77 = R.drawable.ic_config_tilt;
                                                z4 = isSwitchOn;
                                            }
                                        case 229:
                                            i5 = R.string.config_name_straighten;
                                            z2 = this.mDataItemRunning.isSwitchOn("pref_camera_gradienter_key");
                                            r02 = 2131230921;
                                            break;
                                        case 230:
                                            i5 = R.string.config_name_hht;
                                            z2 = this.mDataItemRunning.isSwitchOn("pref_camera_hand_night_key");
                                            r02 = 2131230904;
                                            break;
                                        case 231:
                                            i5 = R.string.config_name_magic;
                                            z2 = this.mDataItemRunning.isSwitchOn("pref_camera_ubifocus_key");
                                            r02 = 2131230907;
                                            break;
                                        default:
                                            switch (config) {
                                                case 233:
                                                    i5 = R.string.pref_video_speed_fast_title;
                                                    z2 = ModuleManager.isFastMotionModule();
                                                    r02 = 2131230898;
                                                    break;
                                                case 234:
                                                    i5 = R.string.config_name_scene;
                                                    z2 = this.mDataItemRunning.isSwitchOn("pref_camera_scenemode_setting_key");
                                                    r02 = 2131230910;
                                                    break;
                                                case 235:
                                                    i5 = R.string.config_name_group;
                                                    z2 = this.mDataItemRunning.isSwitchOn("pref_camera_groupshot_mode_key");
                                                    r02 = 2131230902;
                                                    break;
                                                case 236:
                                                    i5 = R.string.pref_camera_magic_mirror_title;
                                                    z2 = this.mDataItemRunning.isSwitchOn("pref_camera_magic_mirror_key");
                                                    r02 = 2131230908;
                                                    break;
                                                case 237:
                                                    i2 = R.string.pref_camera_picture_format_entry_raw;
                                                    z5 = DataRepository.dataItemConfig().getComponentConfigRaw().isSwitchOn(currentMode);
                                                    if (!z5) {
                                                        r62 = 2131231055;
                                                        break;
                                                    } else {
                                                        r62 = 2131231056;
                                                        break;
                                                    }
                                                case 238:
                                                    i5 = R.string.pref_camera_show_gender_age_config_title;
                                                    z2 = this.mDataItemRunning.isSwitchOn("pref_camera_show_gender_age_key");
                                                    r02 = 2131230901;
                                                    break;
                                                case 239:
                                                    ComponentConfigBeauty componentConfigBeauty = this.mDataItemConfig.getComponentConfigBeauty();
                                                    z2 = componentConfigBeauty.isSwitchOn(currentMode);
                                                    r74 = componentConfigBeauty.getValueSelectedDrawable(currentMode);
                                                    i5 = componentConfigBeauty.getValueDisplayString(currentMode);
                                                    r75 = r74;
                                                    break;
                                                case 240:
                                                    i5 = R.string.pref_camera_device_watermark_title;
                                                    z2 = CameraSettings.isDualCameraWaterMarkOpen();
                                                    r02 = 2131230896;
                                                    break;
                                                case 241:
                                                    i5 = R.string.config_name_super_resolution;
                                                    z2 = this.mDataItemRunning.isSwitchOn("pref_camera_super_resolution_key");
                                                    r02 = 2131230924;
                                                    break;
                                                case 242:
                                                    if (!Util.isGlobalVersion()) {
                                                        i7 = R.string.pref_ai_detect;
                                                        r0 = 2131230891;
                                                        break;
                                                    } else {
                                                        i7 = R.string.pref_google_lens;
                                                        r0 = 2131230892;
                                                        break;
                                                    }
                                                default:
                                                    isUltraPixelOn = false;
                                                    r72 = 0;
                                                    break;
                                            }
                                    }
                                } else {
                                    ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
                                    z5 = componentRunningMacroMode.isSwitchOn(currentMode);
                                    ? resIcon2 = componentRunningMacroMode.getResIcon(z5);
                                    i2 = componentRunningMacroMode.getResText();
                                    r62 = resIcon2;
                                }
                                z = true;
                                r76 = r62;
                                z4 = z5;
                                i3 = -1;
                                r7 = r76;
                            } else {
                                i5 = R.string.hand_gesture_tip;
                                z2 = this.mDataItemRunning.isSwitchOn("pref_hand_gesture");
                                r02 = 2131230903;
                            }
                            r75 = r02;
                            i4 = -1;
                            r73 = r75;
                            z = true;
                            r7 = r73;
                        } else {
                            ComponentRunningTimer componentRunningTimer = this.mDataItemRunning.getComponentRunningTimer();
                            boolean isSwitchOn2 = componentRunningTimer.isSwitchOn();
                            ? valueSelectedDrawable2 = componentRunningTimer.getValueSelectedDrawable(160);
                            i8 = componentRunningTimer.getValueDisplayString(160);
                            z4 = isSwitchOn2;
                            z = true;
                            r77 = valueSelectedDrawable2;
                        }
                        i2 = i8;
                        r76 = r77;
                        i3 = -1;
                        r7 = r76;
                    } else {
                        i7 = R.string.config_name_setting;
                        r0 = 2131230897;
                    }
                    r75 = r0;
                    z2 = false;
                    i4 = -1;
                    r73 = r75;
                    z = true;
                    r7 = r73;
                } else {
                    commonRecyclerViewHolder.itemView.setOnTouchListener(this);
                    ComponentConfigVideoQuality componentConfigVideoQuality = this.mDataItemConfig.getComponentConfigVideoQuality();
                    ? valueSelectedDrawable3 = componentConfigVideoQuality.getValueSelectedDrawable(currentMode);
                    i6 = componentConfigVideoQuality.getDisplayTitleString();
                    z3 = !componentConfigVideoQuality.disableUpdate();
                    i3 = componentConfigVideoQuality.getValueDisplayString(currentMode);
                    r6 = valueSelectedDrawable3;
                }
                i2 = i6;
                r7 = r6;
                z2 = false;
                z6 = true;
            } else {
                ComponentConfigRatio componentConfigRatio = this.mDataItemConfig.getComponentConfigRatio();
                ? valueSelectedDrawable4 = componentConfigRatio.getValueSelectedDrawable(currentMode);
                int displayTitleString = componentConfigRatio.getDisplayTitleString();
                i3 = componentConfigRatio.getValueDisplayString(currentMode);
                z = true;
                i2 = displayTitleString;
                r7 = valueSelectedDrawable4;
                z2 = false;
                z6 = true;
            }
            TextView textView = (TextView) commonRecyclerViewHolder.getView(R.id.extra_item_text);
            ColorImageView colorImageView = (ColorImageView) commonRecyclerViewHolder.getView(R.id.extra_item_image);
            StringBuilder sb = new StringBuilder();
            sb.append(textView.getText());
            if (i3 != -1) {
                sb.append(commonRecyclerViewHolder.itemView.getResources().getString(i3));
            }
            if (!z2) {
                sb.append(commonRecyclerViewHolder.itemView.getResources().getString(R.string.accessibility_open));
            } else if (!z6) {
                sb.append(commonRecyclerViewHolder.itemView.getResources().getString(R.string.accessibility_closed));
            }
            commonRecyclerViewHolder.itemView.setContentDescription(sb);
            textView.setSelected(true);
            if (i2 == -1) {
                textView.setText(i2);
            } else {
                textView.setText(str);
            }
            commonRecyclerViewHolder.itemView.setEnabled(z);
            int i9 = !z ? z2 ? this.mSelectedColor : this.mTextNormalColor : this.mUnableClickColor;
            int i10 = !z2 ? this.mSelectedColor : this.mImageNormalColor;
            textView.setTextColor(i9);
            colorImageView.setColor(i10);
            colorImageView.setImageResource(r7);
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
        ? menuDrawable = componentUltraPixel.getMenuDrawable();
        str = componentUltraPixel.getMenuString();
        isUltraPixelOn = CameraSettings.isUltraPixelOn();
        r72 = menuDrawable;
        i4 = -1;
        i5 = -1;
        r73 = r74;
        z = true;
        r7 = r73;
        TextView textView2 = (TextView) commonRecyclerViewHolder.getView(R.id.extra_item_text);
        ColorImageView colorImageView2 = (ColorImageView) commonRecyclerViewHolder.getView(R.id.extra_item_image);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(textView2.getText());
        if (i3 != -1) {
        }
        if (!z2) {
        }
        commonRecyclerViewHolder.itemView.setContentDescription(sb2);
        textView2.setSelected(true);
        if (i2 == -1) {
        }
        commonRecyclerViewHolder.itemView.setEnabled(z);
        if (!z) {
        }
        if (!z2) {
        }
        textView2.setTextColor(i9);
        colorImageView2.setColor(i10);
        colorImageView2.setImageResource(r7);
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
