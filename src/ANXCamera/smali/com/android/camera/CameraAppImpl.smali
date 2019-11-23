.class public Lcom/android/camera/CameraAppImpl;
.super Lmiui/external/Application;
.source "CameraAppImpl.java"


# static fields
.field private static sApplicationDelegate:Lcom/android/camera/CameraApplicationDelegate;


# instance fields
.field private isMimojiNeedUpdate:Z

.field private sLaunched:Z


# direct methods
.method public constructor <init>()V
    .locals 1

    invoke-direct {p0}, Lmiui/external/Application;-><init>()V

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/CameraAppImpl;->sLaunched:Z

    iput-boolean v0, p0, Lcom/android/camera/CameraAppImpl;->isMimojiNeedUpdate:Z

    return-void
.end method

.method public static getAndroidContext()Landroid/content/Context;
    .locals 1

    invoke-static {}, Lcom/android/camera/CameraApplicationDelegate;->getAndroidContext()Landroid/content/Context;

    move-result-object v0

    return-object v0
.end method


# virtual methods
.method public addActivity(Landroid/app/Activity;)V
    .locals 1

    sget-object v0, Lcom/android/camera/CameraAppImpl;->sApplicationDelegate:Lcom/android/camera/CameraApplicationDelegate;

    invoke-virtual {v0, p1}, Lcom/android/camera/CameraApplicationDelegate;->addActivity(Landroid/app/Activity;)V

    return-void
.end method

.method public closeAllActivitiesBut(Landroid/app/Activity;)V
    .locals 1

    sget-object v0, Lcom/android/camera/CameraAppImpl;->sApplicationDelegate:Lcom/android/camera/CameraApplicationDelegate;

    invoke-virtual {v0, p1}, Lcom/android/camera/CameraApplicationDelegate;->closeAllActivitiesBut(Landroid/app/Activity;)V

    return-void
.end method

.method public containsResumedCameraInStack()Z
    .locals 1

    sget-object v0, Lcom/android/camera/CameraAppImpl;->sApplicationDelegate:Lcom/android/camera/CameraApplicationDelegate;

    invoke-virtual {v0}, Lcom/android/camera/CameraApplicationDelegate;->containsResumedCameraInStack()Z

    move-result v0

    return v0
.end method

.method public getActivity(I)Landroid/app/Activity;
    .locals 1

    sget-object v0, Lcom/android/camera/CameraAppImpl;->sApplicationDelegate:Lcom/android/camera/CameraApplicationDelegate;

    invoke-virtual {v0, p1}, Lcom/android/camera/CameraApplicationDelegate;->getActivity(I)Landroid/app/Activity;

    move-result-object p1

    return-object p1
.end method

.method public getActivityCount()I
    .locals 1

    sget-object v0, Lcom/android/camera/CameraAppImpl;->sApplicationDelegate:Lcom/android/camera/CameraApplicationDelegate;

    invoke-virtual {v0}, Lcom/android/camera/CameraApplicationDelegate;->getActivityCount()I

    move-result v0

    return v0
.end method

.method public isApplicationFirstLaunched()Z
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/CameraAppImpl;->sLaunched:Z

    if-eqz v0, :cond_0

    iget-boolean v0, p0, Lcom/android/camera/CameraAppImpl;->sLaunched:Z

    xor-int/lit8 v0, v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/CameraAppImpl;->sLaunched:Z

    iget-boolean v0, p0, Lcom/android/camera/CameraAppImpl;->sLaunched:Z

    xor-int/lit8 v0, v0, 0x1

    return v0

    :cond_0
    iget-boolean v0, p0, Lcom/android/camera/CameraAppImpl;->sLaunched:Z

    return v0
.end method

.method public isMainIntentActivityLaunched()Z
    .locals 1

    sget-object v0, Lcom/android/camera/CameraAppImpl;->sApplicationDelegate:Lcom/android/camera/CameraApplicationDelegate;

    invoke-virtual {v0}, Lcom/android/camera/CameraApplicationDelegate;->isMainIntentActivityLaunched()Z

    move-result v0

    return v0
.end method

.method public isMimojiNeedUpdate()Z
    .locals 2

    iget-boolean v0, p0, Lcom/android/camera/CameraAppImpl;->isMimojiNeedUpdate:Z

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    iput-boolean v1, p0, Lcom/android/camera/CameraAppImpl;->isMimojiNeedUpdate:Z

    const/4 v0, 0x1

    return v0

    :cond_0
    return v1
.end method

.method public isNeedRestore()Z
    .locals 1

    sget-object v0, Lcom/android/camera/CameraAppImpl;->sApplicationDelegate:Lcom/android/camera/CameraApplicationDelegate;

    invoke-virtual {v0}, Lcom/android/camera/CameraApplicationDelegate;->getSettingsFlag()Z

    move-result v0

    return v0
.end method

.method public onCreateApplicationDelegate()Lcom/android/camera/CameraApplicationDelegate;
    .locals 3

    sget-object v0, Lcom/android/camera/CameraAppImpl;->sApplicationDelegate:Lcom/android/camera/CameraApplicationDelegate;

    if-nez v0, :cond_0

    new-instance v0, Lcom/android/camera/CameraApplicationDelegate;

    invoke-direct {v0, p0}, Lcom/android/camera/CameraApplicationDelegate;-><init>(Lcom/android/camera/CameraAppImpl;)V

    sput-object v0, Lcom/android/camera/CameraAppImpl;->sApplicationDelegate:Lcom/android/camera/CameraApplicationDelegate;

    :cond_0
    const-string v0, "rx2.purge-period-seconds"

    const-string v1, "3600"

    invoke-static {v0, v1}, Ljava/lang/System;->setProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    invoke-static {}, Lcom/android/camera2/vendortag/struct/MarshalQueryableRegister;->preload()V

    invoke-static {}, Lcom/android/camera2/vendortag/CameraCharacteristicsVendorTags;->preload()V

    invoke-static {}, Lcom/android/camera2/vendortag/CaptureRequestVendorTags;->preload()V

    invoke-static {}, Lcom/android/camera2/vendortag/CaptureResultVendorTags;->preload()V

    nop

    :try_start_0
    invoke-virtual {p0}, Lcom/android/camera/CameraAppImpl;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v0

    invoke-virtual {p0}, Lcom/android/camera/CameraAppImpl;->getPackageName()Ljava/lang/String;

    move-result-object v1

    const/16 v2, 0x80

    invoke-virtual {v0, v1, v2}, Landroid/content/pm/PackageManager;->getApplicationInfo(Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;

    move-result-object v0

    iget-object v0, v0, Landroid/content/pm/ApplicationInfo;->metaData:Landroid/os/Bundle;

    const-string v1, "com.xiaomi.camera.parallel.enable"

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Landroid/os/Bundle;->getInt(Ljava/lang/String;I)I

    move-result v0

    const/4 v1, 0x1

    if-ne v0, v1, :cond_1

    goto :goto_0

    :cond_1
    move v1, v2

    :goto_0
    if-eqz v1, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/CameraAppImpl;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f0a0011

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getInteger(I)I

    move-result v0

    invoke-virtual {p0}, Lcom/android/camera/CameraAppImpl;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v2, 0x7f0a0012

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getInteger(I)I

    move-result v1

    invoke-static {v0, v1}, Lcom/xiaomi/camera/imagecodec/ImagePool;->init(II)V

    invoke-static {}, Lcom/android/camera/parallel/AlgoConnector;->getInstance()Lcom/android/camera/parallel/AlgoConnector;

    move-result-object v0

    invoke-virtual {v0, p0}, Lcom/android/camera/parallel/AlgoConnector;->startService(Landroid/content/Context;)V
    :try_end_0
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    :cond_2
    goto :goto_1

    :catch_0
    move-exception v0

    invoke-virtual {v0}, Landroid/content/pm/PackageManager$NameNotFoundException;->printStackTrace()V

    :goto_1
    invoke-static {}, Lcom/android/camera/CrashHandler;->getInstance()Lcom/android/camera/CrashHandler;

    move-result-object v0

    invoke-virtual {v0, p0}, Lcom/android/camera/CrashHandler;->init(Lmiui/external/Application;)V

    invoke-static {p0}, Lcom/android/camera/network/util/NetworkUtils;->bind(Landroid/app/Application;)V

    invoke-static {p0}, Lcom/miui/filtersdk/BeautificationSDK;->init(Landroid/content/Context;)V

    sget-object v0, Lcom/android/camera/CameraAppImpl;->sApplicationDelegate:Lcom/android/camera/CameraApplicationDelegate;

    return-object v0
.end method

.method public bridge synthetic onCreateApplicationDelegate()Lmiui/external/ApplicationDelegate;
    .locals 1

    invoke-virtual {p0}, Lcom/android/camera/CameraAppImpl;->onCreateApplicationDelegate()Lcom/android/camera/CameraApplicationDelegate;

    move-result-object v0

    return-object v0
.end method

.method public removeActivity(Landroid/app/Activity;)V
    .locals 1

    sget-object v0, Lcom/android/camera/CameraAppImpl;->sApplicationDelegate:Lcom/android/camera/CameraApplicationDelegate;

    invoke-virtual {v0, p1}, Lcom/android/camera/CameraApplicationDelegate;->removeActivity(Landroid/app/Activity;)V

    return-void
.end method

.method public resetRestoreFlag()V
    .locals 1

    sget-object v0, Lcom/android/camera/CameraAppImpl;->sApplicationDelegate:Lcom/android/camera/CameraApplicationDelegate;

    invoke-virtual {v0}, Lcom/android/camera/CameraApplicationDelegate;->resetRestoreFlag()V

    return-void
.end method
