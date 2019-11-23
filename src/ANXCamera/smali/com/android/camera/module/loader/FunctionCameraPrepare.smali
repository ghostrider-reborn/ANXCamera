.class public Lcom/android/camera/module/loader/FunctionCameraPrepare;
.super Lcom/android/camera/module/loader/Func1Base;
.source "FunctionCameraPrepare.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/android/camera/module/loader/Func1Base<",
        "Lcom/android/camera/Camera;",
        "Lcom/android/camera/module/BaseModule;",
        ">;"
    }
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String; = "FunctionCameraPrepare"


# instance fields
.field private baseModule:Lcom/android/camera/module/BaseModule;

.field private mNeedReConfigureData:Z

.field private mResetType:I


# direct methods
.method public constructor <init>(IIZLcom/android/camera/module/BaseModule;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/module/loader/Func1Base;-><init>(I)V

    iput p2, p0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mResetType:I

    iput-boolean p3, p0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mNeedReConfigureData:Z

    iput-object p4, p0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->baseModule:Lcom/android/camera/module/BaseModule;

    return-void
.end method

.method private reconfigureData()V
    .locals 17

    move-object/from16 v0, p0

    iget-boolean v1, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mNeedReConfigureData:Z

    if-nez v1, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/DataItemConfig;->editor()Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v0

    const-string v1, "pref_camera_zoom_key"

    invoke-interface {v0, v1}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v0

    invoke-interface {v0}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->apply()V

    return-void

    :cond_0
    invoke-static {}, Lcom/android/camera/CameraSettings;->upgradeGlobalPreferences()V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v3

    invoke-virtual {v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getLastCameraId()I

    move-result v4

    invoke-virtual {v3}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentFlash()Lcom/android/camera/data/data/config/ComponentConfigFlash;

    move-result-object v5

    invoke-virtual {v3}, Lcom/android/camera/data/data/config/DataItemConfig;->editor()Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v6

    invoke-virtual {v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->editor()Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v7

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v8

    invoke-virtual {v8}, Lcom/android/camera/data/data/extra/DataItemLive;->editor()Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v8

    invoke-static {}, Lcom/android/camera/data/DataRepository;->getInstance()Lcom/android/camera/data/DataRepository;

    move-result-object v9

    invoke-virtual {v9}, Lcom/android/camera/data/DataRepository;->backUp()Lcom/android/camera/data/backup/DataBackUp;

    move-result-object v9

    const-string v10, "pref_camera_zoom_key"

    invoke-interface {v6, v10}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v10

    const-string v11, "pref_camera_exposure_key"

    invoke-interface {v10, v11}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    invoke-virtual {v2}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningAutoZoom()Lcom/android/camera/data/data/runing/ComponentRunningAutoZoom;

    move-result-object v10

    if-eqz v10, :cond_2

    invoke-virtual {v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getIntentType()I

    move-result v13

    if-nez v13, :cond_1

    const/4 v13, 0x1

    goto :goto_0

    :cond_1
    nop

    const/4 v13, 0x0

    :goto_0
    invoke-virtual {v10, v13}, Lcom/android/camera/data/data/runing/ComponentRunningAutoZoom;->reInitIntentType(Z)V

    :cond_2
    invoke-virtual {v2}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningSuperEIS()Lcom/android/camera/data/data/runing/ComponentRunningSuperEIS;

    move-result-object v10

    if-eqz v10, :cond_3

    invoke-virtual {v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getIntentType()I

    move-result v10

    :cond_3
    iget v10, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    invoke-virtual {v5, v10}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getPersistValue(I)Ljava/lang/String;

    move-result-object v10

    invoke-virtual {v5, v10}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->isValidFlashValue(Ljava/lang/String;)Z

    move-result v13

    if-nez v13, :cond_4

    iget v10, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    invoke-virtual {v5, v10}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getKey(I)Ljava/lang/String;

    move-result-object v10

    invoke-interface {v6, v10}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    goto :goto_1

    :cond_4
    const-string v13, "2"

    invoke-virtual {v10, v13}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v10

    if-nez v10, :cond_5

    iget v10, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    invoke-virtual {v5, v10}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getPersistValue(I)Ljava/lang/String;

    move-result-object v10

    const-string v13, "5"

    invoke-virtual {v10, v13}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v10

    if-eqz v10, :cond_6

    :cond_5
    iget v10, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    invoke-virtual {v5, v10}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getKey(I)Ljava/lang/String;

    move-result-object v10

    iget v13, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    invoke-virtual {v5, v13}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getDefaultValue(I)Ljava/lang/String;

    move-result-object v13

    invoke-interface {v6, v10, v13}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->putString(Ljava/lang/String;Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    :cond_6
    :goto_1
    invoke-virtual {v3}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigRatio()Lcom/android/camera/data/data/config/ComponentConfigRatio;

    move-result-object v10

    iget v13, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    const/16 v14, 0xa3

    const/16 v15, 0xa7

    const/16 v12, 0xa5

    if-eq v13, v14, :cond_7

    iget v13, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    if-eq v13, v12, :cond_7

    iget v13, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    if-eq v13, v15, :cond_7

    iget v13, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    const/16 v14, 0xad

    if-eq v13, v14, :cond_7

    iget v13, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    const/16 v14, 0xaf

    if-eq v13, v14, :cond_7

    iget v13, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    const/16 v14, 0xab

    if-ne v13, v14, :cond_b

    :cond_7
    nop

    invoke-virtual {v10}, Lcom/android/camera/data/data/config/ComponentConfigRatio;->getFullSupportRatioValues()[Ljava/lang/String;

    move-result-object v13

    iget v14, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    invoke-virtual {v10, v14}, Lcom/android/camera/data/data/config/ComponentConfigRatio;->getPersistValue(I)Ljava/lang/String;

    move-result-object v10

    array-length v14, v13

    const/4 v11, 0x0

    :goto_2
    if-ge v11, v14, :cond_9

    aget-object v15, v13, v11

    invoke-static {v15, v10}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v15

    if-eqz v15, :cond_8

    nop

    nop

    const/4 v11, 0x1

    goto :goto_3

    :cond_8
    add-int/lit8 v11, v11, 0x1

    const/16 v15, 0xa7

    goto :goto_2

    :cond_9
    const/4 v11, 0x0

    :goto_3
    if-nez v11, :cond_b

    iget v11, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    if-eq v11, v12, :cond_a

    const-string v11, "1x1"

    invoke-virtual {v10, v11}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v10

    if-nez v10, :cond_b

    :cond_a
    const-string v10, "FunctionCameraPrepare"

    const-string v11, "reconfigureData: clear DATA_CONFIG_RATIO"

    invoke-static {v10, v11}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const-string v10, "pref_camera_picturesize_key"

    invoke-interface {v6, v10}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    :cond_b
    iget v10, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    const/16 v11, 0xac

    if-ne v10, v11, :cond_e

    nop

    invoke-virtual {v3}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigSlowMotion()Lcom/android/camera/data/data/config/ComponentConfigSlowMotion;

    move-result-object v10

    invoke-virtual {v10, v11}, Lcom/android/camera/data/data/config/ComponentConfigSlowMotion;->getComponentValue(I)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10}, Lcom/android/camera/data/data/config/ComponentConfigSlowMotion;->getSupportAllFPS()[Ljava/lang/String;

    move-result-object v10

    array-length v12, v10

    const/4 v13, 0x0

    :goto_4
    if-ge v13, v12, :cond_d

    aget-object v14, v10, v13

    invoke-static {v14, v11}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v14

    if-eqz v14, :cond_c

    nop

    nop

    const/4 v10, 0x1

    goto :goto_5

    :cond_c
    add-int/lit8 v13, v13, 0x1

    goto :goto_4

    :cond_d
    const/4 v10, 0x0

    :goto_5
    if-nez v10, :cond_e

    const-string v10, "FunctionCameraPrepare"

    const-string v11, "reconfigureData: clear DATA_CONFIG_NEW_SLOW_MOTION_KEY"

    invoke-static {v10, v11}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const-string v10, "key_new_slow_motion"

    invoke-interface {v6, v10}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    :cond_e
    iget v10, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    const/16 v11, 0xa7

    if-ne v10, v11, :cond_f

    invoke-static {}, Lcom/android/camera/CameraAppImpl;->getAndroidContext()Landroid/content/Context;

    move-result-object v10

    const v11, 0x7f0900b1

    invoke-virtual {v10, v11}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v10

    const-string v11, "pref_qc_camera_iso_key"

    invoke-virtual {v3, v11, v10}, Lcom/android/camera/data/data/config/DataItemConfig;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v10

    const v11, 0x7f0f001a

    invoke-static {v10, v11}, Lcom/android/camera/Util;->isStringValueContained(Ljava/lang/Object;I)Z

    move-result v10

    if-nez v10, :cond_f

    const-string v10, "pref_qc_camera_iso_key"

    invoke-interface {v6, v10}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    :cond_f
    invoke-static {}, Lcom/mi/config/b;->jl()Z

    move-result v10

    if-nez v10, :cond_10

    const-string v10, "pref_focus_position_key"

    invoke-interface {v6, v10}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    const-string v10, "pref_qc_camera_exposuretime_key"

    invoke-interface {v6, v10}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    :cond_10
    invoke-static {}, Lcom/android/camera/Util;->isLabOptionsVisible()Z

    move-result v10

    if-nez v10, :cond_11

    const-string v10, "pref_camera_facedetection_key"

    invoke-interface {v7, v10}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v10

    const-string v11, "pref_camera_portrait_with_facebeauty_key"

    invoke-interface {v10, v11}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v10

    const-string v11, "pref_camera_facedetection_auto_hidden_key"

    invoke-interface {v10, v11}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v10

    const-string v11, "pref_camera_dual_enable_key"

    invoke-interface {v10, v11}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v10

    const-string v11, "pref_camera_dual_sat_enable_key"

    invoke-interface {v10, v11}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v10

    const-string v11, "pref_camera_mfnr_sat_enable_key"

    invoke-interface {v10, v11}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v10

    const-string v11, "pref_camera_sr_enable_key"

    invoke-interface {v10, v11}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    :cond_11
    const-string v10, "pref_camera_antibanding_key"

    const-string v11, "1"

    invoke-virtual {v1, v10, v11}, Lcom/android/camera/data/data/global/DataItemGlobal;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v10

    invoke-static {v10}, Lcom/android/camera/Util;->isValidValue(Ljava/lang/String;)Z

    move-result v10

    if-nez v10, :cond_12

    const-string v10, "pref_camera_antibanding_key"

    invoke-interface {v7, v10}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    :cond_12
    invoke-static {}, Lcom/mi/config/b;->kj()Z

    move-result v10

    if-nez v10, :cond_13

    const-string v10, "pref_fingerprint_capture_key"

    invoke-interface {v7, v10}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    :cond_13
    iget v10, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mResetType:I

    packed-switch v10, :pswitch_data_0

    :goto_6
    const/4 v10, 0x0

    goto/16 :goto_a

    :pswitch_0
    iget v3, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    sparse-switch v3, :sswitch_data_0

    invoke-virtual {v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId()I

    move-result v12

    goto :goto_8

    :sswitch_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v3

    invoke-virtual {v3}, Lcom/mi/config/a;->hw()Z

    move-result v3

    if-eqz v3, :cond_14

    invoke-virtual {v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId()I

    move-result v12

    goto :goto_8

    :cond_14
    nop

    goto :goto_7

    :sswitch_1
    invoke-virtual {v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId()I

    move-result v12

    goto :goto_8

    :sswitch_2
    nop

    goto :goto_7

    :sswitch_3
    nop

    nop

    :goto_7
    const/4 v12, 0x0

    goto :goto_8

    :sswitch_4
    invoke-virtual {v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId()I

    move-result v12

    if-nez v12, :cond_15

    invoke-interface {v9}, Lcom/android/camera/data/backup/DataBackUp;->removeOtherVideoMode()V

    goto :goto_8

    :sswitch_5
    invoke-virtual {v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId()I

    move-result v12

    nop

    :cond_15
    :goto_8
    invoke-virtual {v1, v12}, Lcom/android/camera/data/data/global/DataItemGlobal;->setCameraIdTransient(I)V

    iget v3, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    invoke-virtual {v1, v3}, Lcom/android/camera/data/data/global/DataItemGlobal;->getDataBackUpKey(I)I

    move-result v3

    invoke-interface {v9, v2, v3, v12}, Lcom/android/camera/data/backup/DataBackUp;->revertRunning(Lcom/android/camera/data/data/runing/DataItemRunning;II)V

    goto :goto_6

    :pswitch_1
    invoke-direct {v0, v5, v6}, Lcom/android/camera/module/loader/FunctionCameraPrepare;->resetFlash(Lcom/android/camera/data/data/config/ComponentConfigFlash;Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V

    invoke-virtual {v3}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentHdr()Lcom/android/camera/data/data/config/ComponentConfigHdr;

    move-result-object v5

    invoke-direct {v0, v5, v6}, Lcom/android/camera/module/loader/FunctionCameraPrepare;->resetHdr(Lcom/android/camera/data/data/config/ComponentConfigHdr;Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V

    invoke-virtual {v3}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigBeauty()Lcom/android/camera/data/data/config/ComponentConfigBeauty;

    move-result-object v5

    invoke-direct {v0, v5, v6}, Lcom/android/camera/module/loader/FunctionCameraPrepare;->resetBeautyTransientAndVideoPersist(Lcom/android/camera/data/data/config/ComponentConfigBeauty;Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V

    invoke-virtual {v3}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigUltraWide()Lcom/android/camera/data/data/config/ComponentConfigUltraWide;

    move-result-object v5

    invoke-direct {v0, v5, v6}, Lcom/android/camera/module/loader/FunctionCameraPrepare;->resetUltraWide(Lcom/android/camera/data/data/config/ComponentConfigUltraWide;Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V

    invoke-virtual {v3}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigUltraWide()Lcom/android/camera/data/data/config/ComponentConfigUltraWide;

    move-result-object v5

    invoke-virtual {v3}, Lcom/android/camera/data/data/config/DataItemConfig;->getManuallyDualLens()Lcom/android/camera/data/data/config/ComponentManuallyDualLens;

    move-result-object v10

    invoke-direct {v0, v5, v10, v6}, Lcom/android/camera/module/loader/FunctionCameraPrepare;->resetLensType(Lcom/android/camera/data/data/config/ComponentConfigUltraWide;Lcom/android/camera/data/data/config/ComponentManuallyDualLens;Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V

    invoke-virtual {v3}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigMiMovie()Lcom/android/camera/data/data/config/ComponentConfigMiMovie;

    move-result-object v5

    invoke-direct {v0, v5, v6}, Lcom/android/camera/module/loader/FunctionCameraPrepare;->resetMiMovie(Lcom/android/camera/data/data/config/ComponentConfigMiMovie;Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V

    invoke-virtual {v3}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigSlowMotion()Lcom/android/camera/data/data/config/ComponentConfigSlowMotion;

    move-result-object v3

    iget v5, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    invoke-virtual {v3, v5}, Lcom/android/camera/data/data/config/ComponentConfigSlowMotion;->getKey(I)Ljava/lang/String;

    move-result-object v3

    invoke-interface {v6, v3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    invoke-virtual {v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId()I

    move-result v3

    if-nez v3, :cond_16

    invoke-direct {v0, v6}, Lcom/android/camera/module/loader/FunctionCameraPrepare;->resetBeautyBackLevel(Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V

    invoke-direct {v0, v6}, Lcom/android/camera/module/loader/FunctionCameraPrepare;->resetBeautyCaptureFigure(Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->provider()Lcom/android/camera/data/provider/DataProvider;

    move-result-object v3

    const/4 v5, 0x1

    invoke-interface {v3, v5}, Lcom/android/camera/data/provider/DataProvider;->dataConfig(I)Lcom/android/camera/data/provider/DataProvider$ProviderEvent;

    move-result-object v3

    check-cast v3, Lcom/android/camera/data/data/config/DataItemConfig;

    const/4 v10, 0x0

    goto :goto_9

    :cond_16
    invoke-static {}, Lcom/android/camera/data/DataRepository;->provider()Lcom/android/camera/data/provider/DataProvider;

    move-result-object v3

    const/4 v10, 0x0

    invoke-interface {v3, v10}, Lcom/android/camera/data/provider/DataProvider;->dataConfig(I)Lcom/android/camera/data/provider/DataProvider$ProviderEvent;

    move-result-object v3

    check-cast v3, Lcom/android/camera/data/data/config/DataItemConfig;

    :goto_9
    invoke-virtual {v3}, Lcom/android/camera/data/data/config/DataItemConfig;->editor()Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v5

    invoke-virtual {v3}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentFlash()Lcom/android/camera/data/data/config/ComponentConfigFlash;

    move-result-object v11

    invoke-direct {v0, v11, v5}, Lcom/android/camera/module/loader/FunctionCameraPrepare;->resetFlash(Lcom/android/camera/data/data/config/ComponentConfigFlash;Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V

    invoke-virtual {v3}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentHdr()Lcom/android/camera/data/data/config/ComponentConfigHdr;

    move-result-object v11

    invoke-direct {v0, v11, v5}, Lcom/android/camera/module/loader/FunctionCameraPrepare;->resetHdr(Lcom/android/camera/data/data/config/ComponentConfigHdr;Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V

    invoke-virtual {v3}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentBokeh()Lcom/android/camera/data/data/config/ComponentConfigBokeh;

    move-result-object v11

    invoke-direct {v0, v11, v5}, Lcom/android/camera/module/loader/FunctionCameraPrepare;->resetFrontBokenh(Lcom/android/camera/data/data/config/ComponentConfigBokeh;Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V

    invoke-virtual {v3}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigBeauty()Lcom/android/camera/data/data/config/ComponentConfigBeauty;

    move-result-object v11

    invoke-direct {v0, v11, v5}, Lcom/android/camera/module/loader/FunctionCameraPrepare;->resetBeautyTransientAndVideoPersist(Lcom/android/camera/data/data/config/ComponentConfigBeauty;Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V

    invoke-virtual {v3}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigMiMovie()Lcom/android/camera/data/data/config/ComponentConfigMiMovie;

    move-result-object v11

    invoke-direct {v0, v11, v6}, Lcom/android/camera/module/loader/FunctionCameraPrepare;->resetMiMovie(Lcom/android/camera/data/data/config/ComponentConfigMiMovie;Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V

    invoke-direct {v0, v3}, Lcom/android/camera/module/loader/FunctionCameraPrepare;->resetBeautyVideoFront(Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V

    invoke-interface {v5}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->apply()V

    invoke-virtual {v2}, Lcom/android/camera/data/data/runing/DataItemRunning;->clearArrayMap()V

    invoke-interface {v9}, Lcom/android/camera/data/backup/DataBackUp;->clearBackUp()V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/camera/data/data/extra/DataItemLive;->clearAll()V

    invoke-virtual {v2}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningMacroMode()Lcom/android/camera/data/data/config/ComponentRunningMacroMode;

    move-result-object v3

    invoke-direct {v0, v3}, Lcom/android/camera/module/loader/FunctionCameraPrepare;->resetMacroMode(Lcom/android/camera/data/data/config/ComponentRunningMacroMode;)V

    invoke-virtual {v2}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningAutoZoom()Lcom/android/camera/data/data/runing/ComponentRunningAutoZoom;

    move-result-object v3

    invoke-direct {v0, v3}, Lcom/android/camera/module/loader/FunctionCameraPrepare;->resetAutoZoom(Lcom/android/camera/data/data/runing/ComponentRunningAutoZoom;)V

    invoke-virtual {v2}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningSubtitle()Lcom/android/camera/data/data/runing/ComponentRunningSubtitle;

    move-result-object v3

    invoke-direct {v0, v3}, Lcom/android/camera/module/loader/FunctionCameraPrepare;->resetSubtitle(Lcom/android/camera/data/data/runing/ComponentRunningSubtitle;)V

    invoke-virtual {v2}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningSuperEIS()Lcom/android/camera/data/data/runing/ComponentRunningSuperEIS;

    move-result-object v2

    invoke-direct {v0, v2}, Lcom/android/camera/module/loader/FunctionCameraPrepare;->resetSuperEIS(Lcom/android/camera/data/data/runing/ComponentRunningSuperEIS;)V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v2

    invoke-virtual {v2}, Lcom/mi/config/a;->gC()Z

    move-result v2

    if-eqz v2, :cond_17

    const-string v2, "pref_live_music_path_key"

    invoke-interface {v8, v2}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v2

    const-string v3, "pref_live_music_hint_key"

    invoke-interface {v2, v3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v2

    const-string v3, "pref_live_sticker_key"

    invoke-interface {v2, v3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v2

    const-string v3, "pref_live_sticker_name_key"

    invoke-interface {v2, v3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v2

    const-string v3, "pref_live_sticker_hint_key"

    invoke-interface {v2, v3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v2

    const-string v3, "pref_live_speed_key"

    invoke-interface {v2, v3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v2

    const-string v3, "key_live_filter"

    invoke-interface {v2, v3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v2

    const-string v3, "key_live_shrink_face_ratio"

    invoke-interface {v2, v3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v2

    const-string v3, "key_live_enlarge_eye_ratio"

    invoke-interface {v2, v3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v2

    const-string v3, "key_live_smooth_strength"

    invoke-interface {v2, v3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v2

    const-string v3, "pref_live_beauty_status"

    invoke-interface {v2, v3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    :cond_17
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v2

    invoke-virtual {v2}, Lcom/mi/config/a;->gD()Z

    move-result v2

    if-eqz v2, :cond_18

    const-string v2, "pref_mimoji_index"

    invoke-interface {v8, v2}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object v2

    const-string v3, "pref_mimoji_pannel_state"

    invoke-interface {v2, v3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    goto :goto_a

    :pswitch_2
    const/4 v10, 0x0

    iget v3, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    invoke-virtual {v1, v3}, Lcom/android/camera/data/data/global/DataItemGlobal;->getDataBackUpKey(I)I

    move-result v3

    invoke-virtual {v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId()I

    move-result v11

    invoke-interface {v9, v2, v3, v11}, Lcom/android/camera/data/backup/DataBackUp;->revertRunning(Lcom/android/camera/data/data/runing/DataItemRunning;II)V

    iget v2, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    invoke-virtual {v5, v2}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getPersistValue(I)Ljava/lang/String;

    move-result-object v2

    const-string v3, "2"

    invoke-virtual {v2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_18

    iget v2, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    invoke-virtual {v5, v2}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getKey(I)Ljava/lang/String;

    move-result-object v2

    iget v3, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    invoke-virtual {v5, v3}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getDefaultValue(I)Ljava/lang/String;

    move-result-object v3

    invoke-interface {v6, v2, v3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->putString(Ljava/lang/String;Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    :cond_18
    :goto_a
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v2

    invoke-virtual {v2}, Lcom/mi/config/a;->gK()Z

    move-result v12

    iget v0, v0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mResetType:I

    const/4 v2, 0x4

    if-ne v0, v2, :cond_19

    invoke-virtual {v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId()I

    move-result v0

    if-ne v4, v0, :cond_19

    goto :goto_b

    :cond_19
    move v10, v12

    :goto_b
    if-eqz v10, :cond_1a

    const-string v0, "pref_lens_dirty_detect_enabled_key"

    const/4 v1, 0x1

    invoke-interface {v6, v0, v1}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->putBoolean(Ljava/lang/String;Z)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    :cond_1a
    invoke-interface {v6}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->apply()V

    invoke-interface {v7}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->apply()V

    invoke-interface {v8}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->apply()V

    return-void

    :pswitch_data_0
    .packed-switch 0x2
        :pswitch_2
        :pswitch_1
        :pswitch_0
        :pswitch_0
        :pswitch_1
        :pswitch_2
    .end packed-switch

    :sswitch_data_0
    .sparse-switch
        0xa1 -> :sswitch_5
        0xa2 -> :sswitch_4
        0xa6 -> :sswitch_3
        0xa7 -> :sswitch_2
        0xa9 -> :sswitch_1
        0xab -> :sswitch_0
        0xae -> :sswitch_5
    .end sparse-switch
.end method

.method private resetAutoZoom(Lcom/android/camera/data/data/runing/ComponentRunningAutoZoom;)V
    .locals 0

    invoke-virtual {p1}, Lcom/android/camera/data/data/runing/ComponentRunningAutoZoom;->clearArrayMap()V

    return-void
.end method

.method private resetBeautyBackLevel(Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V
    .locals 5

    sget-object v0, Lcom/android/camera/constant/BeautyConstant;->BEAUTY_CATEGORY_LEVEL:[Ljava/lang/String;

    array-length v1, v0

    const/4 v2, 0x0

    :goto_0
    if-ge v2, v1, :cond_0

    aget-object v3, v0, v2

    invoke-static {v3}, Lcom/android/camera/constant/BeautyConstant;->wrappedSettingKeyForCapture(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    invoke-interface {p1, v4}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    invoke-static {v3}, Lcom/android/camera/constant/BeautyConstant;->wrappedSettingKeyForVideo(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    invoke-interface {p1, v4}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    invoke-static {v3}, Lcom/android/camera/constant/BeautyConstant;->wrappedSettingKeyForPortrait(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    invoke-interface {p1, v4}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    invoke-static {v3}, Lcom/android/camera/constant/BeautyConstant;->wrappedSettingKeyForFun(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-interface {p1, v3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :cond_0
    return-void
.end method

.method private resetBeautyCaptureFigure(Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V
    .locals 4

    sget-object v0, Lcom/android/camera/constant/BeautyConstant;->BEAUTY_CATEGORY_BACK_FIGURE:[Ljava/lang/String;

    array-length v1, v0

    const/4 v2, 0x0

    :goto_0
    if-ge v2, v1, :cond_0

    aget-object v3, v0, v2

    invoke-static {v3}, Lcom/android/camera/constant/BeautyConstant;->wrappedSettingKeyForCapture(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-interface {p1, v3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :cond_0
    return-void
.end method

.method private resetBeautyTransientAndVideoPersist(Lcom/android/camera/data/data/config/ComponentConfigBeauty;Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V
    .locals 3

    invoke-virtual {p1}, Lcom/android/camera/data/data/config/ComponentConfigBeauty;->clearClosed()V

    nop

    const/16 v0, 0xa2

    invoke-virtual {p1, v0}, Lcom/android/camera/data/data/config/ComponentConfigBeauty;->getPersistValue(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1, v0}, Lcom/android/camera/data/data/config/ComponentConfigBeauty;->getDefaultValue(I)Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_0

    invoke-virtual {p1, v0}, Lcom/android/camera/data/data/config/ComponentConfigBeauty;->getKey(I)Ljava/lang/String;

    move-result-object p1

    invoke-interface {p2, p1, v2}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->putString(Ljava/lang/String;Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    :cond_0
    return-void
.end method

.method private resetBeautyVideoFront(Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V
    .locals 4

    sget-object v0, Lcom/android/camera/constant/BeautyConstant;->BEAUTY_CATEGORY_LEVEL:[Ljava/lang/String;

    array-length v1, v0

    const/4 v2, 0x0

    :goto_0
    if-ge v2, v1, :cond_0

    aget-object v3, v0, v2

    invoke-static {v3}, Lcom/android/camera/constant/BeautyConstant;->wrappedSettingKeyForVideo(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-interface {p1, v3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->remove(Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :cond_0
    return-void
.end method

.method private resetFlash(Lcom/android/camera/data/data/config/ComponentConfigFlash;Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V
    .locals 2

    iget v0, p0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    invoke-virtual {p1, v0}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getPersistValue(I)Ljava/lang/String;

    move-result-object v0

    const-string v1, "3"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_0

    iget v0, p0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    invoke-virtual {p1, v0}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getKey(I)Ljava/lang/String;

    move-result-object v0

    iget v1, p0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    invoke-virtual {p1, v1}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getDefaultValue(I)Ljava/lang/String;

    move-result-object p1

    invoke-interface {p2, v0, p1}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->putString(Ljava/lang/String;Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    :cond_0
    return-void
.end method

.method private resetFrontBokenh(Lcom/android/camera/data/data/config/ComponentConfigBokeh;Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V
    .locals 1

    iget p2, p0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    invoke-virtual {p1, p2}, Lcom/android/camera/data/data/config/ComponentConfigBokeh;->getPersistValue(I)Ljava/lang/String;

    move-result-object p2

    const-string v0, "on"

    invoke-virtual {v0, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p2

    if-eqz p2, :cond_0

    iget p2, p0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    const-string v0, "off"

    invoke-virtual {p1, p2, v0}, Lcom/android/camera/data/data/config/ComponentConfigBokeh;->setComponentValue(ILjava/lang/String;)V

    :cond_0
    return-void
.end method

.method private resetHdr(Lcom/android/camera/data/data/config/ComponentConfigHdr;Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V
    .locals 2

    iget v0, p0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    invoke-virtual {p1, v0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getPersistValue(I)Ljava/lang/String;

    move-result-object v0

    const-string v1, "auto"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_0

    const-string v1, "off"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_0

    iget v0, p0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    invoke-virtual {p1, v0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getKey(I)Ljava/lang/String;

    move-result-object v0

    iget v1, p0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->mTargetMode:I

    invoke-virtual {p1, v1}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getDefaultValue(I)Ljava/lang/String;

    move-result-object p1

    invoke-interface {p2, v0, p1}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->putString(Ljava/lang/String;Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    :cond_0
    return-void
.end method

.method private resetLensType(Lcom/android/camera/data/data/config/ComponentConfigUltraWide;Lcom/android/camera/data/data/config/ComponentManuallyDualLens;Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V
    .locals 0

    if-eqz p1, :cond_0

    if-eqz p2, :cond_0

    invoke-virtual {p2, p1, p3}, Lcom/android/camera/data/data/config/ComponentManuallyDualLens;->resetLensType(Lcom/android/camera/data/data/config/ComponentConfigUltraWide;Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V

    :cond_0
    return-void
.end method

.method private resetMacroMode(Lcom/android/camera/data/data/config/ComponentRunningMacroMode;)V
    .locals 0

    invoke-virtual {p1}, Lcom/android/camera/data/data/config/ComponentRunningMacroMode;->clearArrayMap()V

    return-void
.end method

.method private resetMiMovie(Lcom/android/camera/data/data/config/ComponentConfigMiMovie;Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V
    .locals 1

    if-eqz p1, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object p2

    invoke-virtual {p2}, Lcom/mi/config/a;->ga()Z

    move-result p2

    if-eqz p2, :cond_0

    const/16 p2, 0xab

    const/4 v0, 0x0

    invoke-virtual {p1, p2, v0}, Lcom/android/camera/data/data/config/ComponentConfigMiMovie;->setMiMovie(IZ)V

    :cond_0
    return-void
.end method

.method private resetSubtitle(Lcom/android/camera/data/data/runing/ComponentRunningSubtitle;)V
    .locals 0

    invoke-virtual {p1}, Lcom/android/camera/data/data/runing/ComponentRunningSubtitle;->clearArrayMap()V

    return-void
.end method

.method private resetSuperEIS(Lcom/android/camera/data/data/runing/ComponentRunningSuperEIS;)V
    .locals 0

    invoke-virtual {p1}, Lcom/android/camera/data/data/runing/ComponentRunningSuperEIS;->clearArrayMap()V

    return-void
.end method

.method private resetUltraWide(Lcom/android/camera/data/data/config/ComponentConfigUltraWide;Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V
    .locals 0

    if-eqz p1, :cond_0

    invoke-virtual {p1, p2}, Lcom/android/camera/data/data/config/ComponentConfigUltraWide;->resetUltraWide(Lcom/android/camera/data/provider/DataProvider$ProviderEditor;)V

    :cond_0
    return-void
.end method


# virtual methods
.method public apply(Lcom/android/camera/module/loader/NullHolder;)Lcom/android/camera/module/loader/NullHolder;
    .locals 2
    .param p1    # Lcom/android/camera/module/loader/NullHolder;
        .annotation build Lio/reactivex/annotations/NonNull;
        .end annotation
    .end param
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/camera/module/loader/NullHolder<",
            "Lcom/android/camera/Camera;",
            ">;)",
            "Lcom/android/camera/module/loader/NullHolder<",
            "Lcom/android/camera/module/BaseModule;",
            ">;"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    invoke-virtual {p1}, Lcom/android/camera/module/loader/NullHolder;->isPresent()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    const/16 p1, 0xea

    invoke-static {v1, p1}, Lcom/android/camera/module/loader/NullHolder;->ofNullable(Ljava/lang/Object;I)Lcom/android/camera/module/loader/NullHolder;

    move-result-object p1

    return-object p1

    :cond_0
    invoke-static {}, Lcom/android/camera/permission/PermissionManager;->checkCameraLaunchPermissions()Z

    move-result v0

    if-nez v0, :cond_1

    const/16 p1, 0xe5

    invoke-static {v1, p1}, Lcom/android/camera/module/loader/NullHolder;->ofNullable(Ljava/lang/Object;I)Lcom/android/camera/module/loader/NullHolder;

    move-result-object p1

    return-object p1

    :cond_1
    invoke-virtual {p1}, Lcom/android/camera/module/loader/NullHolder;->get()Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Lcom/android/camera/Camera;

    invoke-virtual {p1}, Lcom/android/camera/Camera;->isFinishing()Z

    move-result v0

    if-eqz v0, :cond_2

    const-string p1, "FunctionCameraPrepare"

    const-string v0, "activity is finishing, the content of BaseModuleHolder is set to null"

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/16 p1, 0xeb

    invoke-static {v1, p1}, Lcom/android/camera/module/loader/NullHolder;->ofNullable(Ljava/lang/Object;I)Lcom/android/camera/module/loader/NullHolder;

    move-result-object p1

    return-object p1

    :cond_2
    invoke-virtual {p1}, Lcom/android/camera/Camera;->changeRequestOrientation()V

    iget-object p1, p0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->baseModule:Lcom/android/camera/module/BaseModule;

    invoke-virtual {p1}, Lcom/android/camera/module/BaseModule;->isDeparted()Z

    move-result p1

    if-eqz p1, :cond_3

    iget-object p1, p0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->baseModule:Lcom/android/camera/module/BaseModule;

    const/16 v0, 0xe1

    invoke-static {p1, v0}, Lcom/android/camera/module/loader/NullHolder;->ofNullable(Ljava/lang/Object;I)Lcom/android/camera/module/loader/NullHolder;

    move-result-object p1

    return-object p1

    :cond_3
    invoke-direct {p0}, Lcom/android/camera/module/loader/FunctionCameraPrepare;->reconfigureData()V

    iget-object p1, p0, Lcom/android/camera/module/loader/FunctionCameraPrepare;->baseModule:Lcom/android/camera/module/BaseModule;

    invoke-static {p1}, Lcom/android/camera/module/loader/NullHolder;->ofNullable(Ljava/lang/Object;)Lcom/android/camera/module/loader/NullHolder;

    move-result-object p1

    return-object p1
.end method

.method public bridge synthetic apply(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0
    .param p1    # Ljava/lang/Object;
        .annotation build Lio/reactivex/annotations/NonNull;
        .end annotation
    .end param
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    check-cast p1, Lcom/android/camera/module/loader/NullHolder;

    invoke-virtual {p0, p1}, Lcom/android/camera/module/loader/FunctionCameraPrepare;->apply(Lcom/android/camera/module/loader/NullHolder;)Lcom/android/camera/module/loader/NullHolder;

    move-result-object p1

    return-object p1
.end method

.method public getWorkThread()Lio/reactivex/Scheduler;
    .locals 1

    sget-object v0, Lcom/android/camera/constant/GlobalConstant;->sCameraSetupScheduler:Lio/reactivex/Scheduler;

    return-object v0
.end method
