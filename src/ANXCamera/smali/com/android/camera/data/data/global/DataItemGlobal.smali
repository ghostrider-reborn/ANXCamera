.class public Lcom/android/camera/data/data/global/DataItemGlobal;
.super Lcom/android/camera/data/data/DataItemBase;
.source "DataItemGlobal.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/data/data/global/DataItemGlobal$IntentType;
    }
.end annotation


# static fields
.field public static final BACK_DISPLAY_MODE:I = 0x2

.field public static final CTA_CAN_CONNECT_NETWORK_BY_IMPUNITY:Ljava/lang/String; = "can_connect_network"

.field public static final DATA_COMMON_AI_SCENE_HINT:Ljava/lang/String; = "pref_camera_first_ai_scene_use_hint_shown_key"

.field public static final DATA_COMMON_CURRENT_CAMERA_ID:Ljava/lang/String; = "pref_camera_id_key"

.field public static final DATA_COMMON_CURRENT_MODE:Ljava/lang/String; = "pref_camera_mode_key_intent_"

.field public static final DATA_COMMON_CUSTOM_WATERMARK_VERSION:Ljava/lang/String; = "pref_custom_watermark_version"

.field public static final DATA_COMMON_DEVICE_WATERMARK:Ljava/lang/String; = "pref_dualcamera_watermark_key"

.field public static final DATA_COMMON_DUALCAMERA_USERDEFINE_WATERMARK:Ljava/lang/String; = "user_define_watermark_key"

.field public static final DATA_COMMON_FIRST_USE_HINT:Ljava/lang/String; = "pref_camera_first_use_hint_shown_key"

.field public static final DATA_COMMON_FOCUS_SHOOT:Ljava/lang/String; = "pref_camera_focus_shoot_key"

.field public static final DATA_COMMON_FRONT_CAM_ROTATE_HINT:Ljava/lang/String; = "pref_front_camera_first_use_hint_shown_key"

.field public static final DATA_COMMON_MACRO_MODE_HINT:Ljava/lang/String; = "pref_camera_first_macro_mode_use_hint_shown_key"

.field private static final DATA_COMMON_OPEN_TIME:Ljava/lang/String; = "pref_camera_open_time"

.field public static final DATA_COMMON_PORTRAIT_HINT:Ljava/lang/String; = "pref_camera_first_portrait_use_hint_shown_key"

.field public static final DATA_COMMON_TIKTOK_MORE_BUTTON_SHOW_APP:Ljava/lang/String; = "pref_camera_tiktok_more_show_app_key"

.field public static final DATA_COMMON_TIKTOK_MORE_BUTTON_SHOW_MARKET:Ljava/lang/String; = "pref_camera_tiktok_more_show_market_key"

.field public static final DATA_COMMON_TIME_WATER_MARK:Ljava/lang/String; = "pref_time_watermark_key"

.field public static final DATA_COMMON_ULTRA_TELE_HINT:Ljava/lang/String; = "pref_camera_first_ultra_tele_use_hint_shown_key"

.field public static final DATA_COMMON_ULTRA_WIDE_HINT:Ljava/lang/String; = "pref_camera_first_ultra_wide_use_hint_shown_key"

.field public static final DATA_COMMON_ULTRA_WIDE_SAT_HINT:Ljava/lang/String; = "pref_camera_first_ultra_wide_sat_use_hint_shown_key"

.field public static final DATA_COMMON_VV_HINT:Ljava/lang/String; = "pref_camera_first_vv_hint_shown_key"

.field public static final FRONT_DISPLAY_MODE:I = 0x1

.field public static final INTENT_TYPE_IMAGE:I = 0x1

.field public static final INTENT_TYPE_NORMAL:I = 0x0

.field public static final INTENT_TYPE_SCAN_QR:I = 0x3

.field public static final INTENT_TYPE_UNSPECIFIED:I = -0x1

.field public static final INTENT_TYPE_VIDEO:I = 0x2

.field public static final INTENT_TYPE_VOICE_CONTROL:I = 0x4

.field public static final KEY:Ljava/lang/String; = "camera_settings_global"

.field private static final TAG:Ljava/lang/String; = "DataItemGlobal"

.field public static sUseHints:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field


# instance fields
.field private mDataItemFeature:Lcom/mi/config/a;

.field private mIntentType:I

.field private mIsForceMainBackCamera:Z

.field private mIsTimeOut:Ljava/lang/Boolean;

.field private mLastCameraId:I

.field private mMimojiStandAlone:Z

.field private mModuleList:Lcom/android/camera/data/data/global/ComponentModuleList;

.field private mRetriedIfCameraError:Z

.field private mStartFromKeyguard:Z


# direct methods
.method static constructor <clinit>()V
    .locals 2

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    sput-object v0, Lcom/android/camera/data/data/global/DataItemGlobal;->sUseHints:Ljava/util/List;

    sget-object v0, Lcom/android/camera/data/data/global/DataItemGlobal;->sUseHints:Ljava/util/List;

    const-string v1, "pref_camera_first_use_hint_shown_key"

    invoke-interface {v0, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    sget-object v0, Lcom/android/camera/data/data/global/DataItemGlobal;->sUseHints:Ljava/util/List;

    const-string v1, "pref_camera_first_ai_scene_use_hint_shown_key"

    invoke-interface {v0, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    sget-object v0, Lcom/android/camera/data/data/global/DataItemGlobal;->sUseHints:Ljava/util/List;

    const-string v1, "pref_camera_first_ultra_wide_use_hint_shown_key"

    invoke-interface {v0, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    sget-object v0, Lcom/android/camera/data/data/global/DataItemGlobal;->sUseHints:Ljava/util/List;

    const-string v1, "pref_camera_first_portrait_use_hint_shown_key"

    invoke-interface {v0, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    sget-object v0, Lcom/android/camera/data/data/global/DataItemGlobal;->sUseHints:Ljava/util/List;

    const-string v1, "pref_front_camera_first_use_hint_shown_key"

    invoke-interface {v0, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    sget-object v0, Lcom/android/camera/data/data/global/DataItemGlobal;->sUseHints:Ljava/util/List;

    const-string v1, "pref_camera_recordlocation_key"

    invoke-interface {v0, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    return-void
.end method

.method public constructor <init>(Lcom/mi/config/a;)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/data/data/DataItemBase;-><init>()V

    const/4 v0, 0x0

    iput v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mIntentType:I

    iput-object p1, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mDataItemFeature:Lcom/mi/config/a;

    iget-object p1, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mDataItemFeature:Lcom/mi/config/a;

    invoke-virtual {p1}, Lcom/mi/config/a;->gE()Z

    move-result p1

    iput-boolean p1, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mMimojiStandAlone:Z

    invoke-virtual {p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId()I

    move-result p1

    iput p1, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mLastCameraId:I

    new-instance p1, Lcom/android/camera/data/data/global/ComponentModuleList;

    invoke-direct {p1, p0}, Lcom/android/camera/data/data/global/ComponentModuleList;-><init>(Lcom/android/camera/data/data/global/DataItemGlobal;)V

    iput-object p1, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mModuleList:Lcom/android/camera/data/data/global/ComponentModuleList;

    return-void
.end method

.method private determineTimeOut()Z
    .locals 1

    invoke-static {}, Lcom/android/camera/CameraSettings;->retainCameraMode()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x0

    return v0

    :cond_0
    invoke-direct {p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->isActualTimeOut()Z

    move-result v0

    return v0
.end method

.method private getCurrentCameraId(I)I
    .locals 2

    const/4 v0, 0x0

    packed-switch p1, :pswitch_data_0

    :pswitch_0
    const-string v0, "pref_camera_id_key"

    invoke-direct {p0, p1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getDefaultCameraId(I)I

    move-result p1

    invoke-static {p1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p0, v0, p1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/Integer;->intValue()I

    move-result p1

    return p1

    :pswitch_1
    const/4 p1, 0x1

    return p1

    :pswitch_2
    iget-object v1, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mDataItemFeature:Lcom/mi/config/a;

    invoke-virtual {v1}, Lcom/mi/config/a;->hw()Z

    move-result v1

    if-eqz v1, :cond_0

    const-string v0, "pref_camera_id_key"

    invoke-direct {p0, p1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getDefaultCameraId(I)I

    move-result p1

    invoke-static {p1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p0, v0, p1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/Integer;->intValue()I

    move-result p1

    return p1

    :cond_0
    return v0

    :pswitch_3
    return v0

    :pswitch_data_0
    .packed-switch 0xa6
        :pswitch_3
        :pswitch_3
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_2
        :pswitch_3
        :pswitch_3
        :pswitch_0
        :pswitch_3
        :pswitch_1
    .end packed-switch
.end method

.method private getCurrentMode(I)I
    .locals 2

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "pref_camera_mode_key_intent_"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0, p1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getDefaultMode(I)I

    move-result p1

    invoke-virtual {p0, v0, p1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getInt(Ljava/lang/String;I)I

    move-result p1

    return p1
.end method

.method private getCurrentModeForFrontCamera(I)I
    .locals 2

    invoke-direct {p0, p1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentMode(I)I

    move-result p1

    const/16 v0, 0xa3

    packed-switch p1, :pswitch_data_0

    :pswitch_0
    goto :goto_1

    :pswitch_1
    iget-object v1, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mDataItemFeature:Lcom/mi/config/a;

    invoke-virtual {v1}, Lcom/mi/config/a;->hw()Z

    move-result v1

    if-nez v1, :cond_0

    goto :goto_0

    :pswitch_2
    const/16 p1, 0xa2

    goto :goto_1

    :pswitch_3
    nop

    nop

    :goto_0
    move p1, v0

    :cond_0
    :goto_1
    return p1

    :pswitch_data_0
    .packed-switch 0xa6
        :pswitch_3
        :pswitch_3
        :pswitch_0
        :pswitch_2
        :pswitch_0
        :pswitch_1
        :pswitch_2
        :pswitch_3
        :pswitch_0
        :pswitch_3
    .end packed-switch
.end method

.method private getDefaultCameraId(I)I
    .locals 0

    nop

    const/4 p1, 0x0

    return p1
.end method

.method private isActualTimeOut()Z
    .locals 4

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    const-string v2, "pref_camera_open_time"

    invoke-virtual {p0, v2, v0, v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getLong(Ljava/lang/String;J)J

    move-result-wide v2

    sub-long/2addr v0, v2

    const-wide/16 v2, 0x7530

    cmp-long v0, v0, v2

    if-gtz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mIsTimeOut:Ljava/lang/Boolean;

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    return v0
.end method


# virtual methods
.method public getCTACanCollect()Z
    .locals 2

    const-string v0, "can_connect_network"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public getComponentModuleList()Lcom/android/camera/data/data/global/ComponentModuleList;
    .locals 1

    iget-object v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mModuleList:Lcom/android/camera/data/data/global/ComponentModuleList;

    return-object v0
.end method

.method public getCurrentCameraId()I
    .locals 1

    invoke-virtual {p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentMode()I

    move-result v0

    invoke-direct {p0, v0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId(I)I

    move-result v0

    return v0
.end method

.method public getCurrentMode()I
    .locals 1

    iget v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mIntentType:I

    invoke-direct {p0, v0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentMode(I)I

    move-result v0

    return v0
.end method

.method public getDataBackUpKey(I)I
    .locals 1

    const/16 v0, 0xa5

    if-ne p1, v0, :cond_0

    invoke-static {p1}, Lcom/android/camera/data/data/global/ComponentModuleList;->getTransferredMode(I)I

    move-result p1

    :cond_0
    iget v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mIntentType:I

    add-int/lit8 v0, v0, 0x2

    shl-int/lit8 v0, v0, 0x8

    or-int/2addr p1, v0

    iget-boolean v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mStartFromKeyguard:Z

    if-eqz v0, :cond_1

    const/high16 v0, 0x10000

    or-int/2addr p1, v0

    :cond_1
    return p1
.end method

.method public getDefaultMode(I)I
    .locals 1

    const/16 v0, 0xa3

    packed-switch p1, :pswitch_data_0

    iget-boolean p1, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mMimojiStandAlone:Z

    if-eqz p1, :cond_0

    const/16 p1, 0xb1

    return p1

    :pswitch_0
    const/16 p1, 0xa2

    return p1

    :pswitch_1
    return v0

    :cond_0
    return v0

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_1
        :pswitch_0
        :pswitch_1
    .end packed-switch
.end method

.method public getDisplayMode()I
    .locals 2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->ia()Z

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId()I

    move-result v0

    if-ne v0, v1, :cond_0

    const/4 v0, 0x2

    return v0

    :cond_0
    return v1
.end method

.method public getIntentType()I
    .locals 1

    iget v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mIntentType:I

    return v0
.end method

.method public getLastCameraId()I
    .locals 1

    iget v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mLastCameraId:I

    return v0
.end method

.method public getStartFromKeyguard()Z
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mStartFromKeyguard:Z

    return v0
.end method

.method public isFirstShowCTAConCollect()Z
    .locals 1

    const-string v0, "can_connect_network"

    invoke-virtual {p0, v0}, Lcom/android/camera/data/data/global/DataItemGlobal;->contains(Ljava/lang/String;)Z

    move-result v0

    xor-int/lit8 v0, v0, 0x1

    return v0
.end method

.method public isForceMainBackCamera()Z
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mIsForceMainBackCamera:Z

    return v0
.end method

.method public isGlobalSwitchOn(Ljava/lang/String;)Z
    .locals 1

    const/4 v0, 0x0

    invoke-virtual {p0, p1, v0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getBoolean(Ljava/lang/String;Z)Z

    move-result p1

    return p1
.end method

.method public isIntentAction()Z
    .locals 1

    iget v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mIntentType:I

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public isNormalIntent()Z
    .locals 1

    iget v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mIntentType:I

    if-nez v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public isRetriedIfCameraError()Z
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mRetriedIfCameraError:Z

    return v0
.end method

.method public isTiktokMoreButtonEnabled(Z)Z
    .locals 1

    sget-boolean v0, Lcom/mi/config/b;->sL:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    move v0, p1

    :goto_0
    if-eqz p1, :cond_1

    const-string p1, "pref_camera_tiktok_more_show_app_key"

    goto :goto_1

    :cond_1
    const-string p1, "pref_camera_tiktok_more_show_market_key"

    :goto_1
    invoke-virtual {p0, p1, v0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getBoolean(Ljava/lang/String;Z)Z

    move-result p1

    return p1
.end method

.method public isTimeOut()Z
    .locals 1

    iget-object v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mIsTimeOut:Ljava/lang/Boolean;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mIsTimeOut:Ljava/lang/Boolean;

    invoke-virtual {v0}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    return v0
.end method

.method public isTransient()Z
    .locals 1

    const/4 v0, 0x0

    return v0
.end method

.method public matchCustomWatermarkVersion()Z
    .locals 8

    iget-object v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mDataItemFeature:Lcom/mi/config/a;

    invoke-virtual {v0}, Lcom/mi/config/a;->hA()Ljava/lang/String;

    move-result-object v0

    const-string v1, "pref_custom_watermark_version"

    invoke-virtual {p0, v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->contains(Ljava/lang/String;)Z

    move-result v1

    const/4 v2, 0x1

    if-nez v1, :cond_0

    iget-object v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mDataItemFeature:Lcom/mi/config/a;

    const-string v1, "c_0x40"

    invoke-virtual {v0, v1}, Lcom/mi/config/a;->L(Ljava/lang/String;)Z

    move-result v0

    xor-int/2addr v0, v2

    return v0

    :cond_0
    const-string v1, "pref_custom_watermark_version"

    invoke-virtual {p0, v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->arrayMapContainsKey(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_1

    const-string v1, "pref_custom_watermark_version"

    invoke-virtual {p0, v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->arrayMapRemove(Ljava/lang/String;)V

    :cond_1
    const-string v1, "pref_custom_watermark_version"

    const-string v3, ""

    invoke-virtual {p0, v1, v3}, Lcom/android/camera/data/data/global/DataItemGlobal;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    const/16 v3, 0x3a

    invoke-virtual {v1, v3}, Ljava/lang/String;->indexOf(I)I

    move-result v3

    const/4 v4, 0x0

    if-lez v3, :cond_2

    invoke-virtual {v1, v4, v3}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v5

    add-int/2addr v3, v2

    invoke-virtual {v1, v3}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v3

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v7, Lcom/mi/config/b;->rp:Ljava/lang/String;

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {}, Lcom/mi/config/b;->getGivenName()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_2

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    return v2

    :cond_2
    const-string v0, "DataItemGlobal"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "mismatch custom watermark version: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return v4
.end method

.method public parseIntent(Landroid/content/Intent;Ljava/lang/Boolean;ZZZ)Landroid/support/v4/util/Pair;
    .locals 18
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/content/Intent;",
            "Ljava/lang/Boolean;",
            "ZZZ)",
            "Landroid/support/v4/util/Pair<",
            "Ljava/lang/Integer;",
            "Ljava/lang/Integer;",
            ">;"
        }
    .end annotation

    move-object/from16 v1, p0

    move/from16 v2, p3

    const/4 v3, 0x0

    invoke-virtual {v1, v3}, Lcom/android/camera/data/data/global/DataItemGlobal;->setForceMainBackCamera(Z)V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->ge()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/android/camera/CameraAppImpl;->getAndroidContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Lcom/android/camera/Util;->isScreenSlideOff(Landroid/content/Context;)Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {v1, v3}, Lcom/android/camera/data/data/global/DataItemGlobal;->setCameraId(I)V

    :cond_0
    invoke-virtual/range {p1 .. p1}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    if-nez v0, :cond_1

    const-string v0, "<unknown>"

    :cond_1
    invoke-virtual {v0}, Ljava/lang/String;->hashCode()I

    move-result v4

    const/4 v5, 0x6

    const/4 v8, -0x1

    const/4 v9, 0x3

    const/4 v10, 0x2

    const/4 v11, 0x1

    sparse-switch v4, :sswitch_data_0

    goto :goto_0

    :sswitch_0
    const-string v4, "com.android.camera.action.QR_CODE_CAPTURE"

    invoke-virtual {v0, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_2

    move v4, v10

    goto :goto_1

    :sswitch_1
    const-string v4, "android.media.action.VIDEO_CAMERA"

    invoke-virtual {v0, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_2

    const/4 v4, 0x5

    goto :goto_1

    :sswitch_2
    const-string v4, "android.media.action.VIDEO_CAPTURE"

    invoke-virtual {v0, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_2

    move v4, v11

    goto :goto_1

    :sswitch_3
    const-string v4, "android.media.action.STILL_IMAGE_CAMERA"

    invoke-virtual {v0, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_2

    const/4 v4, 0x4

    goto :goto_1

    :sswitch_4
    const-string v4, "com.google.zxing.client.android.SCAN"

    invoke-virtual {v0, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_2

    move v4, v9

    goto :goto_1

    :sswitch_5
    const-string v4, "android.media.action.VOICE_COMMAND"

    invoke-virtual {v0, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_2

    move v4, v5

    goto :goto_1

    :sswitch_6
    const-string v4, "android.media.action.IMAGE_CAPTURE"

    invoke-virtual {v0, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_2

    move v4, v3

    goto :goto_1

    :cond_2
    :goto_0
    move v4, v8

    :goto_1
    packed-switch v4, :pswitch_data_0

    nop

    :goto_2
    move v4, v3

    goto/16 :goto_5

    :pswitch_0
    invoke-virtual/range {p2 .. p2}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v4

    if-nez v4, :cond_3

    nop

    goto :goto_2

    :cond_3
    :pswitch_1
    nop

    invoke-static/range {p1 .. p1}, Lcom/android/camera/CameraIntentManager;->getInstance(Landroid/content/Intent;)Lcom/android/camera/CameraIntentManager;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/CameraIntentManager;->getCameraModeId()I

    move-result v0

    const/16 v5, 0xa0

    if-ne v0, v5, :cond_5

    invoke-direct/range {p0 .. p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->determineTimeOut()Z

    move-result v0

    if-eqz v0, :cond_4

    invoke-virtual {v1, v3}, Lcom/android/camera/data/data/global/DataItemGlobal;->getDefaultMode(I)I

    move-result v0

    goto :goto_3

    :cond_4
    invoke-direct {v1, v3}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentMode(I)I

    move-result v0

    :cond_5
    :goto_3
    move v5, v0

    :try_start_0
    invoke-virtual {v4}, Lcom/android/camera/CameraIntentManager;->isUseFrontCamera()Z

    move-result v0
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_4

    :catch_0
    move-exception v0

    invoke-virtual {v4}, Lcom/android/camera/CameraIntentManager;->isOnlyForceOpenMainBackCamera()Z

    move-result v0

    if-eqz v0, :cond_6

    nop

    invoke-virtual {v1, v11}, Lcom/android/camera/data/data/global/DataItemGlobal;->setForceMainBackCamera(Z)V

    move v0, v3

    goto :goto_4

    :cond_6
    invoke-direct/range {p0 .. p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->determineTimeOut()Z

    move-result v0

    if-eqz v0, :cond_7

    invoke-direct {v1, v5}, Lcom/android/camera/data/data/global/DataItemGlobal;->getDefaultCameraId(I)I

    move-result v0

    goto :goto_4

    :cond_7
    invoke-direct {v1, v5}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId(I)I

    move-result v0

    :goto_4
    const-string v4, "DataItemGlobal"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "intent from voice control assist : pendingOpenId = "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v7, ";pendingOpenModule = "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v7, ",newIntentType = "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v4, v6}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iput v3, v1, Lcom/android/camera/data/data/global/DataItemGlobal;->mIntentType:I

    iput-boolean v2, v1, Lcom/android/camera/data/data/global/DataItemGlobal;->mStartFromKeyguard:Z

    iget-object v2, v1, Lcom/android/camera/data/data/global/DataItemGlobal;->mModuleList:Lcom/android/camera/data/data/global/ComponentModuleList;

    iget v3, v1, Lcom/android/camera/data/data/global/DataItemGlobal;->mIntentType:I

    invoke-virtual {v2, v3}, Lcom/android/camera/data/data/global/ComponentModuleList;->setIntentType(I)V

    invoke-virtual/range {p0 .. p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentMode()I

    move-result v2

    if-eq v5, v2, :cond_8

    invoke-virtual {v1, v5}, Lcom/android/camera/data/data/global/DataItemGlobal;->setCurrentMode(I)V

    invoke-static {v5}, Lcom/android/camera/module/ModuleManager;->setActiveModuleIndex(I)V

    :cond_8
    invoke-virtual/range {p0 .. p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId()I

    move-result v2

    if-eq v0, v2, :cond_9

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/global/DataItemGlobal;->setCameraId(I)V

    :cond_9
    new-instance v1, Landroid/support/v4/util/Pair;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-direct {v1, v0, v2}, Landroid/support/v4/util/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    return-object v1

    :pswitch_2
    nop

    nop

    move v4, v9

    goto :goto_5

    :pswitch_3
    nop

    nop

    move v4, v10

    goto :goto_5

    :pswitch_4
    nop

    nop

    move v4, v11

    :goto_5
    invoke-static/range {p1 .. p1}, Lcom/android/camera/CameraIntentManager;->getInstance(Landroid/content/Intent;)Lcom/android/camera/CameraIntentManager;

    move-result-object v12

    invoke-virtual {v12}, Lcom/android/camera/CameraIntentManager;->getCameraFacing()I

    move-result v12

    if-eq v12, v8, :cond_a

    invoke-virtual {v1, v12}, Lcom/android/camera/data/data/global/DataItemGlobal;->setCameraIdTransient(I)V

    :cond_a
    if-eqz p5, :cond_b

    invoke-direct/range {p0 .. p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->determineTimeOut()Z

    move-result v8

    if-eqz v8, :cond_b

    move v8, v11

    goto :goto_6

    :cond_b
    nop

    move v8, v3

    :goto_6
    iget v13, v1, Lcom/android/camera/data/data/global/DataItemGlobal;->mIntentType:I

    if-ne v13, v4, :cond_d

    iget-boolean v13, v1, Lcom/android/camera/data/data/global/DataItemGlobal;->mStartFromKeyguard:Z

    if-eq v13, v2, :cond_c

    goto :goto_7

    :cond_c
    move v13, v3

    goto :goto_8

    :cond_d
    :goto_7
    nop

    move v13, v11

    :goto_8
    const-string v14, "android.media.action.STILL_IMAGE_CAMERA"

    invoke-virtual {v14, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v14

    const/16 v15, 0xa3

    const/16 v7, 0xa2

    if-eqz v14, :cond_e

    nop

    invoke-direct {v1, v15}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId(I)I

    move-result v14

    :goto_9
    move v7, v15

    goto :goto_10

    :cond_e
    const-string v14, "android.media.action.VIDEO_CAMERA"

    invoke-virtual {v14, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v14

    if-eqz v14, :cond_f

    nop

    invoke-direct {v1, v7}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId(I)I

    move-result v14

    goto :goto_10

    :cond_f
    if-eqz v8, :cond_13

    invoke-virtual {v1, v4}, Lcom/android/camera/data/data/global/DataItemGlobal;->getDefaultMode(I)I

    move-result v14

    if-gez v12, :cond_10

    invoke-direct {v1, v14}, Lcom/android/camera/data/data/global/DataItemGlobal;->getDefaultCameraId(I)I

    move-result v16

    :goto_a
    move/from16 v7, v16

    goto :goto_b

    :cond_10
    invoke-direct {v1, v14}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId(I)I

    move-result v16

    goto :goto_a

    :goto_b
    if-ne v14, v15, :cond_12

    invoke-static {}, Lcom/android/camera/data/DataRepository;->provider()Lcom/android/camera/data/provider/DataProvider;

    move-result-object v15

    invoke-interface {v15, v7, v4}, Lcom/android/camera/data/provider/DataProvider;->dataConfig(II)Lcom/android/camera/data/provider/DataProvider$ProviderEvent;

    move-result-object v15

    check-cast v15, Lcom/android/camera/data/data/config/DataItemConfig;

    invoke-virtual {v15}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigRatio()Lcom/android/camera/data/data/config/ComponentConfigRatio;

    move-result-object v15

    invoke-virtual {v15}, Lcom/android/camera/data/data/config/ComponentConfigRatio;->isSquareModule()Z

    move-result v15

    if-eqz v15, :cond_11

    const/16 v14, 0xa5

    :goto_c
    move v15, v14

    goto :goto_d

    :cond_11
    goto :goto_c

    :goto_d
    move v14, v7

    goto :goto_9

    :cond_12
    move/from16 v17, v14

    move v14, v7

    move/from16 v7, v17

    goto :goto_10

    :cond_13
    if-eq v12, v11, :cond_14

    invoke-direct {v1, v4}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentMode(I)I

    move-result v7

    :goto_e
    move v15, v7

    goto :goto_f

    :cond_14
    invoke-direct {v1, v4}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentModeForFrontCamera(I)I

    move-result v7

    goto :goto_e

    :goto_f
    invoke-direct {v1, v15}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId(I)I

    move-result v14

    goto :goto_9

    :goto_10
    const/16 v15, 0xa8

    if-eq v7, v15, :cond_16

    const/16 v15, 0xaa

    if-ne v7, v15, :cond_15

    goto :goto_12

    :cond_15
    invoke-direct/range {p0 .. p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->isActualTimeOut()Z

    move-result v15

    if-eqz v15, :cond_18

    const/16 v15, 0xb3

    if-ne v7, v15, :cond_18

    nop

    :goto_11
    const/16 v7, 0xa2

    goto :goto_13

    :cond_16
    :goto_12
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v7

    invoke-virtual {v7}, Lcom/mi/config/a;->gp()Z

    move-result v7

    if-eqz v7, :cond_17

    const/16 v7, 0xac

    goto :goto_13

    :cond_17
    goto :goto_11

    :cond_18
    :goto_13
    const-string v15, "DataItemGlobal"

    const-string v6, "parseIntent timeOut = %s, intentChanged = %s, action = %s, pendingOpenId = %s, pendingOpenModule = %s, intentCameraId = %s"

    new-array v5, v5, [Ljava/lang/Object;

    invoke-static {v8}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v16

    aput-object v16, v5, v3

    invoke-static {v13}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v3

    aput-object v3, v5, v11

    aput-object v0, v5, v10

    invoke-static {v14}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    aput-object v0, v5, v9

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    const/4 v3, 0x4

    aput-object v0, v5, v3

    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    const/4 v3, 0x5

    aput-object v0, v5, v3

    invoke-static {v6, v5}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v15, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    if-nez p4, :cond_1b

    invoke-static {v8}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v0

    iput-object v0, v1, Lcom/android/camera/data/data/global/DataItemGlobal;->mIsTimeOut:Ljava/lang/Boolean;

    if-eqz v13, :cond_19

    iput v4, v1, Lcom/android/camera/data/data/global/DataItemGlobal;->mIntentType:I

    iput-boolean v2, v1, Lcom/android/camera/data/data/global/DataItemGlobal;->mStartFromKeyguard:Z

    iget-object v0, v1, Lcom/android/camera/data/data/global/DataItemGlobal;->mModuleList:Lcom/android/camera/data/data/global/ComponentModuleList;

    iget v2, v1, Lcom/android/camera/data/data/global/DataItemGlobal;->mIntentType:I

    invoke-virtual {v0, v2}, Lcom/android/camera/data/data/global/ComponentModuleList;->setIntentType(I)V

    :cond_19
    invoke-virtual/range {p0 .. p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentMode()I

    move-result v0

    if-eq v7, v0, :cond_1a

    invoke-virtual {v1, v7}, Lcom/android/camera/data/data/global/DataItemGlobal;->setCurrentMode(I)V

    invoke-static {v7}, Lcom/android/camera/module/ModuleManager;->setActiveModuleIndex(I)V

    :cond_1a
    invoke-virtual/range {p0 .. p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId()I

    move-result v0

    if-eq v14, v0, :cond_1b

    invoke-virtual {v1, v14}, Lcom/android/camera/data/data/global/DataItemGlobal;->setCameraId(I)V

    :cond_1b
    new-instance v0, Landroid/support/v4/util/Pair;

    invoke-static {v14}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-direct {v0, v1, v2}, Landroid/support/v4/util/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    return-object v0

    nop

    :sswitch_data_0
    .sparse-switch
        -0x74de9aed -> :sswitch_6
        -0x5b1e1211 -> :sswitch_5
        -0x566ad1d3 -> :sswitch_4
        0x1ba9c1af -> :sswitch_3
        0x29c9b033 -> :sswitch_2
        0x43680478 -> :sswitch_1
        0x4c4c1b77 -> :sswitch_0
    .end sparse-switch

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_2
        :pswitch_0
        :pswitch_0
        :pswitch_1
    .end packed-switch
.end method

.method public provideKey()Ljava/lang/String;
    .locals 1

    const-string v0, "camera_settings_global"

    return-object v0
.end method

.method public reInit()V
    .locals 5

    iget-object v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mModuleList:Lcom/android/camera/data/data/global/ComponentModuleList;

    invoke-virtual {v0}, Lcom/android/camera/data/data/global/ComponentModuleList;->reInit()V

    invoke-virtual {p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->editor()Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v0

    const/4 v1, 0x0

    invoke-static {v1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    iput-object v1, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mIsTimeOut:Ljava/lang/Boolean;

    const-string v1, "pref_camera_open_time"

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v2

    invoke-interface {v0, v1, v2, v3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->putLong(Ljava/lang/String;J)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    const-string v1, "open_camera_fail_key"

    const-wide/16 v2, 0x0

    invoke-interface {v0, v1, v2, v3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->putLong(Ljava/lang/String;J)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    invoke-virtual {p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentMode()I

    move-result v1

    invoke-direct {p0, v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId(I)I

    move-result v1

    iput v1, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mLastCameraId:I

    const-string v2, "pref_camera_id_key"

    invoke-static {v1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v3

    invoke-interface {v0, v2, v3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->putString(Ljava/lang/String;Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    const-string v2, "DataItemGlobal"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "reInit: mLastCameraId = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v4, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mLastCameraId:I

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v4, ", currentCameraId = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v2, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-interface {v0}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->apply()V

    return-void
.end method

.method public resetAll()V
    .locals 3

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mIsTimeOut:Ljava/lang/Boolean;

    invoke-virtual {p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->editor()Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v0

    invoke-interface {v0}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->clear()Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v0

    const-string v1, "pref_version_key"

    const/4 v2, 0x4

    invoke-interface {v0, v1, v2}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->putInt(Ljava/lang/String;I)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v0

    invoke-interface {v0}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->apply()V

    return-void
.end method

.method public resetTimeOut()V
    .locals 4

    const/4 v0, 0x0

    invoke-static {v0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mIsTimeOut:Ljava/lang/Boolean;

    invoke-virtual {p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->editor()Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v0

    const-string v1, "pref_camera_open_time"

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v2

    invoke-interface {v0, v1, v2, v3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->putLong(Ljava/lang/String;J)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v0

    invoke-interface {v0}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->apply()V

    return-void
.end method

.method public setCTACanCollect(Z)V
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->editor()Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v0

    const-string v1, "can_connect_network"

    invoke-interface {v0, v1, p1}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->putBoolean(Ljava/lang/String;Z)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object p1

    invoke-interface {p1}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->apply()V

    return-void
.end method

.method public setCameraId(I)V
    .locals 3

    invoke-virtual {p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentMode()I

    move-result v0

    invoke-direct {p0, v0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId(I)I

    move-result v0

    iput v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mLastCameraId:I

    invoke-virtual {p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->editor()Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v0

    const-string v1, "pref_camera_id_key"

    invoke-static {p1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v2

    invoke-interface {v0, v1, v2}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->putString(Ljava/lang/String;Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v0

    invoke-interface {v0}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->apply()V

    const-string v0, "DataItemGlobal"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "setCameraId: mLastCameraId = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mLastCameraId:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, ", cameraId = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public setCameraIdTransient(I)V
    .locals 3

    invoke-virtual {p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentMode()I

    move-result v0

    invoke-direct {p0, v0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId(I)I

    move-result v0

    iput v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mLastCameraId:I

    const-string v0, "pref_camera_id_key"

    invoke-static {p1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p0, v0, v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->putString(Ljava/lang/String;Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    const-string v0, "DataItemGlobal"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "setCameraIdTransient: mLastCameraId = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mLastCameraId:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, ", cameraId = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public setCurrentMode(I)V
    .locals 3

    invoke-virtual {p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->editor()Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "pref_camera_mode_key_intent_"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mIntentType:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-interface {v0, v1, p1}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->putInt(Ljava/lang/String;I)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object p1

    invoke-interface {p1}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->apply()V

    return-void
.end method

.method public setForceMainBackCamera(Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mIsForceMainBackCamera:Z

    return-void
.end method

.method public setRetriedIfCameraError(Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mRetriedIfCameraError:Z

    return-void
.end method

.method public setStartFromKeyguard(Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mStartFromKeyguard:Z

    return-void
.end method

.method public updateCustomWatermarkVersion()V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/data/data/global/DataItemGlobal;->mDataItemFeature:Lcom/mi/config/a;

    invoke-virtual {v0}, Lcom/mi/config/a;->hA()Ljava/lang/String;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v2, Lcom/mi/config/b;->rp:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {}, Lcom/mi/config/b;->getGivenName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, ":"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0}, Lcom/android/camera/data/data/global/DataItemGlobal;->editor()Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v1

    const-string v2, "pref_custom_watermark_version"

    invoke-interface {v1, v2, v0}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->putString(Ljava/lang/String;Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v1

    invoke-interface {v1}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->apply()V

    const-string v1, "DataItemGlobal"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "custom watermark version updated: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0}, Lcom/android/camera/log/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method
