.class public Lcom/android/camera/fragment/top/ExtraAdapter;
.super Landroid/support/v7/widget/RecyclerView$Adapter;
.source "ExtraAdapter.java"

# interfaces
.implements Landroid/view/View$OnTouchListener;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Landroid/support/v7/widget/RecyclerView$Adapter<",
        "Lcom/android/camera/fragment/CommonRecyclerViewHolder;",
        ">;",
        "Landroid/view/View$OnTouchListener;"
    }
.end annotation


# instance fields
.field private mDataItemConfig:Lcom/android/camera/data/data/config/DataItemConfig;

.field private mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

.field private mDegree:I

.field private mImageNormalColor:I

.field private mOnClickListener:Landroid/view/View$OnClickListener;

.field private mSelectedColor:I

.field private mSupportedConfigs:Lcom/android/camera/data/data/config/SupportedConfigs;

.field private mTAG:I

.field private mTextNormalColor:I

.field private mUnableClickColor:I


# direct methods
.method public constructor <init>(Lcom/android/camera/data/data/config/SupportedConfigs;Landroid/view/View$OnClickListener;)V
    .locals 1

    invoke-direct {p0}, Landroid/support/v7/widget/RecyclerView$Adapter;-><init>()V

    const/4 v0, -0x1

    iput v0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mTAG:I

    iput-object p1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mSupportedConfigs:Lcom/android/camera/data/data/config/SupportedConfigs;

    iput-object p2, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mOnClickListener:Landroid/view/View$OnClickListener;

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemConfig:Lcom/android/camera/data/data/config/DataItemConfig;

    const p1, -0x4c000001

    iput p1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mTextNormalColor:I

    const p1, -0x141415

    iput p1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mImageNormalColor:I

    const p1, -0xe66d19

    iput p1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mSelectedColor:I

    const p1, 0x47ffffff

    iput p1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mUnableClickColor:I

    return-void
.end method


# virtual methods
.method public getItemCount()I
    .locals 1

    iget-object v0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mSupportedConfigs:Lcom/android/camera/data/data/config/SupportedConfigs;

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/SupportedConfigs;->getLength()I

    move-result v0

    return v0
.end method

.method public bridge synthetic onBindViewHolder(Landroid/support/v7/widget/RecyclerView$ViewHolder;I)V
    .locals 0

    check-cast p1, Lcom/android/camera/fragment/CommonRecyclerViewHolder;

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/fragment/top/ExtraAdapter;->onBindViewHolder(Lcom/android/camera/fragment/CommonRecyclerViewHolder;I)V

    return-void
.end method

.method public onBindViewHolder(Lcom/android/camera/fragment/CommonRecyclerViewHolder;I)V
    .locals 13

    iget-object v0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mSupportedConfigs:Lcom/android/camera/data/data/config/SupportedConfigs;

    invoke-virtual {v0, p2}, Lcom/android/camera/data/data/config/SupportedConfigs;->getConfig(I)I

    move-result p2

    iget-object v0, p1, Lcom/android/camera/fragment/CommonRecyclerViewHolder;->itemView:Landroid/view/View;

    invoke-static {p2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/view/View;->setTag(Ljava/lang/Object;)V

    iget-object v0, p1, Lcom/android/camera/fragment/CommonRecyclerViewHolder;->itemView:Landroid/view/View;

    iget-object v1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mOnClickListener:Landroid/view/View$OnClickListener;

    invoke-virtual {v0, v1}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    nop

    nop

    nop

    nop

    nop

    nop

    nop

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentMode()I

    move-result v0

    const/16 v1, 0xa0

    const/4 v2, -0x1

    const/4 v3, 0x0

    const/4 v4, 0x1

    const/4 v5, 0x0

    packed-switch p2, :pswitch_data_0

    :pswitch_0
    move v0, v2

    move v1, v0

    move v6, v3

    move v7, v6

    :goto_0
    move v8, v4

    goto/16 :goto_9

    :pswitch_1
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningMacroMode()Lcom/android/camera/data/data/config/ComponentRunningMacroMode;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/config/ComponentRunningMacroMode;->isSwitchOn(I)Z

    move-result v0

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/config/ComponentRunningMacroMode;->getResIcon(Z)I

    move-result v6

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/ComponentRunningMacroMode;->getResText()I

    move-result v1

    goto/16 :goto_3

    :pswitch_2
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningAutoZoom()Lcom/android/camera/data/data/runing/ComponentRunningAutoZoom;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/runing/ComponentRunningAutoZoom;->isEnabled(I)Z

    move-result v0

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/runing/ComponentRunningAutoZoom;->getResIcon(Z)I

    move-result v6

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/ComponentRunningAutoZoom;->getResText()I

    move-result v1

    goto/16 :goto_3

    :pswitch_3
    const v0, 0x7f0200be

    const v1, 0x7f090294

    iget-object v6, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    const-string v7, "pref_hand_gesture"

    invoke-virtual {v6, v7}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v6

    goto/16 :goto_5

    :pswitch_4
    nop

    invoke-static {}, Lcom/android/camera/Util;->isGlobalVersion()Z

    move-result v0

    if-eqz v0, :cond_0

    const v0, 0x7f0200b3

    const v1, 0x7f09017f

    goto/16 :goto_2

    :cond_0
    const v0, 0x7f0200b2

    const v1, 0x7f09017e

    goto/16 :goto_2

    :pswitch_5
    const v0, 0x7f0200d8

    const v1, 0x7f0901db

    iget-object v6, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    const-string v7, "pref_camera_super_resolution_key"

    invoke-virtual {v6, v7}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v6

    goto/16 :goto_5

    :pswitch_6
    const v0, 0x7f0200b7

    const v1, 0x7f090135

    invoke-static {}, Lcom/android/camera/CameraSettings;->isDualCameraWaterMarkOpen()Z

    move-result v6

    goto/16 :goto_5

    :pswitch_7
    iget-object v1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemConfig:Lcom/android/camera/data/data/config/DataItemConfig;

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigBeauty()Lcom/android/camera/data/data/config/ComponentConfigBeauty;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/config/ComponentConfigBeauty;->isSwitchOn(I)Z

    move-result v6

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/config/ComponentConfigBeauty;->getValueSelectedDrawable(I)I

    move-result v7

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/config/ComponentConfigBeauty;->getValueDisplayString(I)I

    move-result v0

    goto/16 :goto_1

    :pswitch_8
    const v0, 0x7f0200bc

    const v1, 0x7f090156

    iget-object v6, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    const-string v7, "pref_camera_show_gender_age_key"

    invoke-virtual {v6, v7}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v6

    goto/16 :goto_5

    :pswitch_9
    const v1, 0x7f090053

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v6

    invoke-virtual {v6}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigRaw()Lcom/android/camera/data/data/config/ComponentConfigRaw;

    move-result-object v6

    invoke-virtual {v6, v0}, Lcom/android/camera/data/data/config/ComponentConfigRaw;->isSwitchOn(I)Z

    move-result v0

    if-eqz v0, :cond_1

    const v6, 0x7f02015c

    goto/16 :goto_3

    :cond_1
    const v6, 0x7f02015b

    goto/16 :goto_3

    :pswitch_a
    const v0, 0x7f0200c3

    const v1, 0x7f090193

    iget-object v6, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    const-string v7, "pref_camera_magic_mirror_key"

    invoke-virtual {v6, v7}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v6

    goto/16 :goto_5

    :pswitch_b
    const v0, 0x7f0200bd

    const v1, 0x7f0901d7

    iget-object v6, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    const-string v7, "pref_camera_groupshot_mode_key"

    invoke-virtual {v6, v7}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v6

    goto/16 :goto_5

    :pswitch_c
    const v0, 0x7f0200c8

    const v1, 0x7f0901d9

    iget-object v6, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    const-string v7, "pref_camera_scenemode_setting_key"

    invoke-virtual {v6, v7}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v6

    goto/16 :goto_5

    :pswitch_d
    const v0, 0x7f0200b9

    const v1, 0x7f09015c

    invoke-static {}, Lcom/android/camera/module/ModuleManager;->isFastMotionModule()Z

    move-result v6

    goto/16 :goto_5

    :pswitch_e
    const v0, 0x7f0200c2

    const v1, 0x7f0901da

    iget-object v6, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    const-string v7, "pref_camera_ubifocus_key"

    invoke-virtual {v6, v7}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v6

    goto/16 :goto_5

    :pswitch_f
    const v0, 0x7f0200bf

    const v1, 0x7f0901d6

    iget-object v6, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    const-string v7, "pref_camera_hand_night_key"

    invoke-virtual {v6, v7}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v6

    goto/16 :goto_5

    :pswitch_10
    const v0, 0x7f0200d3

    const v1, 0x7f0901d5

    iget-object v6, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    const-string v7, "pref_camera_gradienter_key"

    invoke-virtual {v6, v7}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v6

    goto/16 :goto_5

    :pswitch_11
    iget-object v0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningTiltValue()Lcom/android/camera/data/data/runing/ComponentRunningTiltValue;

    move-result-object v0

    iget-object v6, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    const-string v7, "pref_camera_tilt_shift_mode"

    invoke-virtual {v6, v7}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v6

    if-eqz v6, :cond_2

    nop

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/runing/ComponentRunningTiltValue;->getValueDisplayString(I)I

    move-result v7

    nop

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/runing/ComponentRunningTiltValue;->getValueSelectedDrawable(I)I

    move-result v0

    move v8, v4

    move v1, v7

    move v7, v0

    goto :goto_4

    :cond_2
    const v0, 0x7f0901d8

    const v1, 0x7f0200d9

    nop

    move v7, v1

    move v8, v4

    move v1, v0

    goto :goto_4

    :pswitch_12
    iget-object v0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningTimer()Lcom/android/camera/data/data/runing/ComponentRunningTimer;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/ComponentRunningTimer;->isSwitchOn()Z

    move-result v6

    nop

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/runing/ComponentRunningTimer;->getValueSelectedDrawable(I)I

    move-result v7

    nop

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/runing/ComponentRunningTimer;->getValueDisplayString(I)I

    move-result v0

    nop

    :goto_1
    move v1, v0

    goto :goto_6

    :pswitch_13
    const v0, 0x7f0200b8

    const v1, 0x7f0901d4

    nop

    :goto_2
    move v7, v0

    move v0, v2

    move v6, v3

    goto/16 :goto_0

    :pswitch_14
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningSubtitle()Lcom/android/camera/data/data/runing/ComponentRunningSubtitle;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/runing/ComponentRunningSubtitle;->isEnabled(I)Z

    move-result v0

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/runing/ComponentRunningSubtitle;->getResIcon(Z)I

    move-result v6

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/ComponentRunningSubtitle;->getResText()I

    move-result v1

    :goto_3
    move v8, v4

    move v7, v6

    move v6, v0

    :goto_4
    move v0, v2

    goto/16 :goto_9

    :pswitch_15
    const v0, 0x7f0200c7

    const v1, 0x7f090121

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v6

    const-string v7, "pref_camera_referenceline_key"

    invoke-virtual {v6, v7, v3}, Lcom/android/camera/data/data/global/DataItemGlobal;->getBoolean(Ljava/lang/String;Z)Z

    move-result v6

    nop

    :goto_5
    move v7, v0

    :goto_6
    move v0, v2

    goto/16 :goto_0

    :pswitch_16
    iget-object v6, p1, Lcom/android/camera/fragment/CommonRecyclerViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {v6, p0}, Landroid/view/View;->setOnTouchListener(Landroid/view/View$OnTouchListener;)V

    iget-object v6, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemConfig:Lcom/android/camera/data/data/config/DataItemConfig;

    invoke-virtual {v6}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigVideoQuality()Lcom/android/camera/data/data/config/ComponentConfigVideoQuality;

    move-result-object v6

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v7

    invoke-virtual {v7, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v1

    check-cast v1, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;

    if-eqz v1, :cond_3

    const v7, 0x7f0e002a

    invoke-interface {v1, v7}, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;->getActiveFragment(I)I

    move-result v1

    const v7, 0xfff3

    if-ne v1, v7, :cond_3

    const-string v0, "6"

    invoke-virtual {v6, v0}, Lcom/android/camera/data/data/config/ComponentConfigVideoQuality;->getForceItem(Ljava/lang/String;)Lcom/android/camera/data/data/ComponentDataItem;

    move-result-object v0

    iget v0, v0, Lcom/android/camera/data/data/ComponentDataItem;->mIconRes:I

    invoke-virtual {v6}, Lcom/android/camera/data/data/config/ComponentConfigVideoQuality;->getDisplayTitleString()I

    move-result v1

    nop

    nop

    move v6, v0

    move v0, v2

    move v8, v3

    goto :goto_7

    :cond_3
    invoke-virtual {v6, v0}, Lcom/android/camera/data/data/config/ComponentConfigVideoQuality;->getValueSelectedDrawable(I)I

    move-result v1

    invoke-virtual {v6}, Lcom/android/camera/data/data/config/ComponentConfigVideoQuality;->getDisplayTitleString()I

    move-result v7

    invoke-virtual {v6}, Lcom/android/camera/data/data/config/ComponentConfigVideoQuality;->disableUpdate()Z

    move-result v8

    xor-int/2addr v8, v4

    invoke-virtual {v6, v0}, Lcom/android/camera/data/data/config/ComponentConfigVideoQuality;->getValueDisplayString(I)I

    move-result v0

    move v6, v1

    move v1, v7

    :goto_7
    goto :goto_8

    :pswitch_17
    iget-object v1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemConfig:Lcom/android/camera/data/data/config/DataItemConfig;

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigSlowMotionQuality()Lcom/android/camera/data/data/config/ComponentConfigSlowMotionQuality;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/config/ComponentConfigSlowMotionQuality;->getValueSelectedDrawable(I)I

    move-result v6

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/ComponentConfigSlowMotionQuality;->getDisplayTitleString()I

    move-result v7

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/ComponentConfigSlowMotionQuality;->disableUpdate()Z

    move-result v8

    xor-int/2addr v8, v4

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/config/ComponentConfigSlowMotionQuality;->getValueDisplayString(I)I

    move-result v0

    nop

    nop

    move v1, v7

    :goto_8
    move v7, v6

    move v6, v3

    move v3, v4

    goto :goto_9

    :pswitch_18
    iget-object v1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemConfig:Lcom/android/camera/data/data/config/DataItemConfig;

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigRatio()Lcom/android/camera/data/data/config/ComponentConfigRatio;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/config/ComponentConfigRatio;->getValueSelectedDrawable(I)I

    move-result v6

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/ComponentConfigRatio;->getDisplayTitleString()I

    move-result v7

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/config/ComponentConfigRatio;->getValueDisplayString(I)I

    move-result v0

    nop

    nop

    move v8, v4

    move v1, v7

    move v7, v6

    move v6, v3

    move v3, v8

    goto :goto_9

    :pswitch_19
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentUltraPixel()Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->getMenuDrawable()I

    move-result v1

    nop

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->getMenuString()Ljava/lang/String;

    move-result-object v5

    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelOn()Z

    move-result v0

    nop

    move v6, v0

    move v7, v1

    move v0, v2

    move v1, v0

    goto/16 :goto_0

    :goto_9
    const v9, 0x7f0e00b2

    invoke-virtual {p1, v9}, Lcom/android/camera/fragment/CommonRecyclerViewHolder;->getView(I)Landroid/view/View;

    move-result-object v9

    check-cast v9, Landroid/widget/TextView;

    const v10, 0x7f0e00b1

    invoke-virtual {p1, v10}, Lcom/android/camera/fragment/CommonRecyclerViewHolder;->getView(I)Landroid/view/View;

    move-result-object v10

    check-cast v10, Lcom/android/camera/ui/ColorImageView;

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v9}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    move-result-object v12

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/CharSequence;)Ljava/lang/StringBuilder;

    if-eq v0, v2, :cond_4

    iget-object v12, p1, Lcom/android/camera/fragment/CommonRecyclerViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {v12}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v12

    invoke-virtual {v12, v0}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v11, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    :cond_4
    if-eqz v6, :cond_5

    iget-object v0, p1, Lcom/android/camera/fragment/CommonRecyclerViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {v0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v3, 0x7f0900fb

    invoke-virtual {v0, v3}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v11, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    goto :goto_a

    :cond_5
    if-nez v3, :cond_6

    iget-object v0, p1, Lcom/android/camera/fragment/CommonRecyclerViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {v0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v3, 0x7f0900fc

    invoke-virtual {v0, v3}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v11, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    :cond_6
    :goto_a
    iget-object v0, p1, Lcom/android/camera/fragment/CommonRecyclerViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {v0, v11}, Landroid/view/View;->setContentDescription(Ljava/lang/CharSequence;)V

    invoke-virtual {v9, v4}, Landroid/widget/TextView;->setSelected(Z)V

    if-eq v1, v2, :cond_7

    invoke-virtual {v9, v1}, Landroid/widget/TextView;->setText(I)V

    goto :goto_b

    :cond_7
    invoke-virtual {v9, v5}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    :goto_b
    iget-object v0, p1, Lcom/android/camera/fragment/CommonRecyclerViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {v0, v8}, Landroid/view/View;->setEnabled(Z)V

    if-eqz v8, :cond_9

    if-eqz v6, :cond_8

    iget v0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mSelectedColor:I

    goto :goto_c

    :cond_8
    iget v0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mTextNormalColor:I

    goto :goto_c

    :cond_9
    iget v0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mUnableClickColor:I

    :goto_c
    if-eqz v6, :cond_a

    iget v1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mSelectedColor:I

    goto :goto_d

    :cond_a
    iget v1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mImageNormalColor:I

    :goto_d
    invoke-virtual {v9, v0}, Landroid/widget/TextView;->setTextColor(I)V

    invoke-virtual {v10, v1}, Lcom/android/camera/ui/ColorImageView;->setColor(I)V

    invoke-virtual {v10, v7}, Lcom/android/camera/ui/ColorImageView;->setImageResource(I)V

    invoke-static {}, Lcom/android/camera/Util;->isAccessible()Z

    move-result v0

    if-nez v0, :cond_b

    invoke-static {}, Lcom/android/camera/Util;->isSetContentDesc()Z

    move-result v0

    if-eqz v0, :cond_c

    :cond_b
    iget v0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mTAG:I

    if-ne v0, p2, :cond_c

    iget-object p2, p1, Lcom/android/camera/fragment/CommonRecyclerViewHolder;->itemView:Landroid/view/View;

    new-instance v0, Lcom/android/camera/fragment/top/ExtraAdapter$1;

    invoke-direct {v0, p0, p1}, Lcom/android/camera/fragment/top/ExtraAdapter$1;-><init>(Lcom/android/camera/fragment/top/ExtraAdapter;Lcom/android/camera/fragment/CommonRecyclerViewHolder;)V

    const-wide/16 v1, 0x64

    invoke-virtual {p2, v0, v1, v2}, Landroid/view/View;->postDelayed(Ljava/lang/Runnable;J)Z

    :cond_c
    return-void

    :pswitch_data_0
    .packed-switch 0xd1
        :pswitch_19
        :pswitch_18
        :pswitch_0
        :pswitch_0
        :pswitch_17
        :pswitch_16
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_15
        :pswitch_14
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_13
        :pswitch_12
        :pswitch_0
        :pswitch_11
        :pswitch_10
        :pswitch_f
        :pswitch_e
        :pswitch_0
        :pswitch_d
        :pswitch_c
        :pswitch_b
        :pswitch_a
        :pswitch_9
        :pswitch_8
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_3
        :pswitch_2
        :pswitch_0
        :pswitch_1
    .end packed-switch
.end method

.method public bridge synthetic onCreateViewHolder(Landroid/view/ViewGroup;I)Landroid/support/v7/widget/RecyclerView$ViewHolder;
    .locals 0

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/fragment/top/ExtraAdapter;->onCreateViewHolder(Landroid/view/ViewGroup;I)Lcom/android/camera/fragment/CommonRecyclerViewHolder;

    move-result-object p1

    return-object p1
.end method

.method public onCreateViewHolder(Landroid/view/ViewGroup;I)Lcom/android/camera/fragment/CommonRecyclerViewHolder;
    .locals 2

    invoke-virtual {p1}, Landroid/view/ViewGroup;->getContext()Landroid/content/Context;

    move-result-object p2

    invoke-static {p2}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p2

    const v0, 0x7f040042

    const/4 v1, 0x0

    invoke-virtual {p2, v0, p1, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object p1

    iget p2, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDegree:I

    if-eqz p2, :cond_0

    iget p2, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDegree:I

    int-to-float p2, p2

    invoke-static {p1, p2}, Landroid/support/v4/view/ViewCompat;->setRotation(Landroid/view/View;F)V

    :cond_0
    new-instance p2, Lcom/android/camera/fragment/CommonRecyclerViewHolder;

    invoke-direct {p2, p1}, Lcom/android/camera/fragment/CommonRecyclerViewHolder;-><init>(Landroid/view/View;)V

    return-object p2
.end method

.method public onTouch(Landroid/view/View;Landroid/view/MotionEvent;)Z
    .locals 1

    const v0, 0x7f0e00b1

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    const/4 v0, 0x0

    if-nez p1, :cond_0

    return v0

    :cond_0
    invoke-virtual {p2}, Landroid/view/MotionEvent;->getAction()I

    move-result p2

    packed-switch p2, :pswitch_data_0

    goto :goto_0

    :pswitch_0
    const/high16 p2, 0x3f800000    # 1.0f

    invoke-virtual {p1, p2}, Landroid/view/View;->setScaleX(F)V

    invoke-virtual {p1, p2}, Landroid/view/View;->setScaleY(F)V

    goto :goto_0

    :pswitch_1
    const p2, 0x3f6e147b    # 0.93f

    invoke-virtual {p1, p2}, Landroid/view/View;->setScaleX(F)V

    invoke-virtual {p1, p2}, Landroid/view/View;->setScaleY(F)V

    :pswitch_2
    return v0

    :goto_0
    return v0

    nop

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_1
        :pswitch_0
        :pswitch_2
        :pswitch_0
    .end packed-switch
.end method

.method public setNewDegree(I)V
    .locals 0

    iput p1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDegree:I

    return-void
.end method

.method public setOnClictTag(I)V
    .locals 0

    iput p1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mTAG:I

    return-void
.end method
