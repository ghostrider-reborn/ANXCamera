.class public Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;
.super Ljava/lang/Object;
.source "VECloudCtrlManager.java"


# static fields
.field private static COMMANDS:[Ljava/lang/String;

.field private static TAG:Ljava/lang/String;

.field private static volatile mTECloudCtrlManager:Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;


# instance fields
.field private mCloudCtrlInvoker:Lcom/ss/android/ttve/common/TECloudCtrlInvoker;

.field private mLogStatus:Z

.field private mWorkpsace:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    const-string v0, "VECloudCtrlManager"

    sput-object v0, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->TAG:Ljava/lang/String;

    const/4 v0, 0x0

    sput-object v0, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->mTECloudCtrlManager:Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;

    const-string/jumbo v0, "vesdk_log_command"

    filled-new-array {v0}, [Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->COMMANDS:[Ljava/lang/String;

    return-void
.end method

.method private constructor <init>()V
    .locals 2

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->mLogStatus:Z

    invoke-static {}, Landroid/os/Environment;->getExternalStorageDirectory()Ljava/io/File;

    move-result-object v1

    invoke-virtual {v1}, Ljava/io/File;->toString()Ljava/lang/String;

    move-result-object v1

    iput-object v1, p0, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->mWorkpsace:Ljava/lang/String;

    iput-boolean v0, p0, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->mLogStatus:Z

    new-instance v0, Lcom/ss/android/ttve/common/TECloudCtrlInvoker;

    invoke-direct {v0}, Lcom/ss/android/ttve/common/TECloudCtrlInvoker;-><init>()V

    iput-object v0, p0, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->mCloudCtrlInvoker:Lcom/ss/android/ttve/common/TECloudCtrlInvoker;

    return-void
.end method

.method private doCommand(Ljava/lang/String;Ljava/lang/String;)I
    .locals 11
    .param p1    # Ljava/lang/String;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p2    # Ljava/lang/String;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    nop

    const/4 v0, -0x1

    :try_start_0
    new-instance v1, Lorg/json/JSONObject;

    invoke-direct {v1, p2}, Lorg/json/JSONObject;-><init>(Ljava/lang/String;)V

    invoke-virtual {p1}, Ljava/lang/String;->hashCode()I

    move-result p2

    const v2, 0x79ce163c

    const/4 v3, 0x0

    if-eq p2, v2, :cond_0

    goto :goto_0

    :cond_0
    const-string/jumbo p2, "vesdk_log_command"

    invoke-virtual {p1, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p2

    if-eqz p2, :cond_1

    move p2, v3

    goto :goto_1

    :cond_1
    :goto_0
    move p2, v0

    :goto_1
    if-eqz p2, :cond_2

    sget-object p2, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->TAG:Ljava/lang/String;

    const-string v1, " json contail invalid command "

    invoke-static {p2, v1}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    const/4 p1, -0x2

    move v0, p1

    goto/16 :goto_6

    :cond_2
    const-string p2, "false"

    const-string v2, "2018-12-08 00:00:00"

    const-string v4, "2018-12-08 00:00:00"

    const-string v5, ""

    const-string v6, ""

    const-string v7, "enable"

    invoke-virtual {v1, v7}, Lorg/json/JSONObject;->has(Ljava/lang/String;)Z

    move-result v7

    if-eqz v7, :cond_3

    const-string p2, "enable"

    invoke-virtual {v1, p2}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p2

    :cond_3
    const-string/jumbo v7, "starttime"

    invoke-virtual {v1, v7}, Lorg/json/JSONObject;->has(Ljava/lang/String;)Z

    move-result v7

    if-eqz v7, :cond_4

    const-string/jumbo v2, "starttime"

    invoke-virtual {v1, v2}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    :cond_4
    const-string v7, "endtime"

    invoke-virtual {v1, v7}, Lorg/json/JSONObject;->has(Ljava/lang/String;)Z

    move-result v7

    if-eqz v7, :cond_5

    const-string v4, "endtime"

    invoke-virtual {v1, v4}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    :cond_5
    const-string v7, "level"

    invoke-virtual {v1, v7}, Lorg/json/JSONObject;->has(Ljava/lang/String;)Z

    move-result v7

    if-eqz v7, :cond_6

    const-string v5, "level"

    invoke-virtual {v1, v5}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    :cond_6
    const-string/jumbo v7, "sign"

    invoke-virtual {v1, v7}, Lorg/json/JSONObject;->has(Ljava/lang/String;)Z

    move-result v7

    if-eqz v7, :cond_7

    const-string/jumbo v6, "sign"

    invoke-virtual {v1, v6}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v6

    :cond_7
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    iget-object v7, p0, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->mCloudCtrlInvoker:Lcom/ss/android/ttve/common/TECloudCtrlInvoker;

    invoke-virtual {v7, v1, v6}, Lcom/ss/android/ttve/common/TECloudCtrlInvoker;->verifyJson(Ljava/lang/String;Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_8

    const-string p2, ""

    invoke-direct {p0, p1, p2}, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->storeCommand(Ljava/lang/String;Ljava/lang/String;)V

    sget-object p2, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->TAG:Ljava/lang/String;

    const-string v1, "Cloud Ctrl Command Json is doctored"

    invoke-static {p2, v1}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    return v0

    :cond_8
    new-instance v1, Ljava/text/SimpleDateFormat;

    const-string/jumbo v6, "yyyy-MM-dd HH:mm:ss"

    invoke-direct {v1, v6}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, v2}, Ljava/text/SimpleDateFormat;->parse(Ljava/lang/String;)Ljava/util/Date;

    move-result-object v2

    invoke-virtual {v2}, Ljava/util/Date;->getTime()J

    move-result-wide v6

    invoke-virtual {v1, v4}, Ljava/text/SimpleDateFormat;->parse(Ljava/lang/String;)Ljava/util/Date;

    move-result-object v1

    invoke-virtual {v1}, Ljava/util/Date;->getTime()J

    move-result-wide v1

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v8

    sget-object v4, Lcom/ss/android/ttve/common/TESpdLogManager$InfoLevel;->LEVEL3:Lcom/ss/android/ttve/common/TESpdLogManager$InfoLevel;

    invoke-virtual {v5}, Ljava/lang/String;->hashCode()I

    move-result v4

    const/16 v10, 0x44

    if-eq v4, v10, :cond_9

    goto :goto_2

    :cond_9
    const-string v4, "D"

    invoke-virtual {v5, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_a

    move v4, v3

    goto :goto_3

    :cond_a
    :goto_2
    move v4, v0

    :goto_3
    if-eqz v4, :cond_b

    sget-object v4, Lcom/ss/android/ttve/common/TESpdLogManager$InfoLevel;->LEVEL3:Lcom/ss/android/ttve/common/TESpdLogManager$InfoLevel;

    goto :goto_4

    :cond_b
    sget-object v4, Lcom/ss/android/ttve/common/TESpdLogManager$InfoLevel;->LEVEL0:Lcom/ss/android/ttve/common/TESpdLogManager$InfoLevel;

    nop

    :goto_4
    iget-boolean v5, p0, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->mLogStatus:Z

    if-nez v5, :cond_d

    const-string/jumbo v5, "true"

    invoke-virtual {p2, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_d

    cmp-long v5, v8, v6

    if-ltz v5, :cond_d

    cmp-long v5, v8, v1

    if-gez v5, :cond_d

    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v1, p0, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->mWorkpsace:Ljava/lang/String;

    invoke-virtual {p2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "/vesdklog"

    invoke-virtual {p2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    sget-object v1, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->TAG:Ljava/lang/String;

    invoke-static {v1, p2}, Lcom/ss/android/vesdk/VELogUtil;->d(Ljava/lang/String;Ljava/lang/String;)V

    invoke-static {}, Lcom/ss/android/ttve/common/TESpdLogManager;->getInstance()Lcom/ss/android/ttve/common/TESpdLogManager;

    move-result-object v1

    invoke-virtual {v4}, Lcom/ss/android/ttve/common/TESpdLogManager$InfoLevel;->ordinal()I

    move-result v2

    const/4 v5, 0x3

    invoke-virtual {v1, p2, v2, v5}, Lcom/ss/android/ttve/common/TESpdLogManager;->initSpdLog(Ljava/lang/String;II)I

    move-result p2

    if-gez p2, :cond_c

    sget-object v1, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, " TESpdLog init fail "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-static {v1, p2}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    const/4 p1, -0x3

    return p1

    :cond_c
    invoke-static {}, Lcom/ss/android/ttve/common/TESpdLogManager;->getInstance()Lcom/ss/android/ttve/common/TESpdLogManager;

    move-result-object p2

    invoke-virtual {p2, v4}, Lcom/ss/android/ttve/common/TESpdLogManager;->setLevel(Lcom/ss/android/ttve/common/TESpdLogManager$InfoLevel;)V

    const/4 p2, 0x1

    iput-boolean p2, p0, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->mLogStatus:Z

    goto :goto_5

    :cond_d
    const-string v4, "false"

    invoke-virtual {p2, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p2

    if-nez p2, :cond_e

    cmp-long p2, v8, v6

    if-gez p2, :cond_10

    cmp-long p2, v8, v1

    if-ltz p2, :cond_10

    :cond_e
    iget-boolean p2, p0, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->mLogStatus:Z

    if-eqz p2, :cond_f

    invoke-static {}, Lcom/ss/android/ttve/common/TESpdLogManager;->getInstance()Lcom/ss/android/ttve/common/TESpdLogManager;

    move-result-object p2

    invoke-virtual {p2}, Lcom/ss/android/ttve/common/TESpdLogManager;->close()V

    iput-boolean v3, p0, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->mLogStatus:Z

    :cond_f
    sget-object p2, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, " expired"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {p2, v1}, Lcom/ss/android/vesdk/VELogUtil;->d(Ljava/lang/String;Ljava/lang/String;)V

    const-string p2, ""

    invoke-direct {p0, p1, p2}, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->storeCommand(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    :cond_10
    :goto_5
    move v0, v3

    :goto_6
    goto :goto_7

    :catch_0
    move-exception p2

    sget-object v1, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, " json parse failed "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p2}, Ljava/lang/Exception;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-static {v1, p2}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    const-string p2, ""

    invoke-direct {p0, p1, p2}, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->storeCommand(Ljava/lang/String;Ljava/lang/String;)V

    nop

    :goto_7
    return v0
.end method

.method public static getInstance()Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;
    .locals 2

    sget-object v0, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->mTECloudCtrlManager:Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;

    if-nez v0, :cond_1

    const-class v0, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;

    monitor-enter v0

    :try_start_0
    sget-object v1, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->mTECloudCtrlManager:Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;

    if-nez v1, :cond_0

    new-instance v1, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;

    invoke-direct {v1}, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;-><init>()V

    sput-object v1, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->mTECloudCtrlManager:Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;

    :cond_0
    monitor-exit v0

    goto :goto_0

    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1

    :cond_1
    :goto_0
    sget-object v0, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->mTECloudCtrlManager:Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;

    return-object v0
.end method

.method private storeCommand(Ljava/lang/String;Ljava/lang/String;)V
    .locals 1
    .param p1    # Ljava/lang/String;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p2    # Ljava/lang/String;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    invoke-static {}, Lcom/ss/android/vesdk/runtime/persistence/VESP;->getInstance()Lcom/ss/android/vesdk/runtime/persistence/VESP;

    move-result-object v0

    invoke-virtual {v0, p1, p2}, Lcom/ss/android/vesdk/runtime/persistence/VESP;->put(Ljava/lang/String;Ljava/lang/Object;)V

    return-void
.end method


# virtual methods
.method public closeCloudControlRes()V
    .locals 1

    iget-boolean v0, p0, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->mLogStatus:Z

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/ss/android/ttve/common/TESpdLogManager;->getInstance()Lcom/ss/android/ttve/common/TESpdLogManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/ss/android/ttve/common/TESpdLogManager;->close()V

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->mLogStatus:Z

    :cond_0
    return-void
.end method

.method public execStoredCommands(Ljava/lang/String;)V
    .locals 5
    .param p1    # Ljava/lang/String;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    iput-object p1, p0, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->mWorkpsace:Ljava/lang/String;

    sget-object p1, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->COMMANDS:[Ljava/lang/String;

    array-length v0, p1

    const/4 v1, 0x0

    :goto_0
    if-ge v1, v0, :cond_1

    aget-object v2, p1, v1

    invoke-static {}, Lcom/ss/android/vesdk/runtime/persistence/VESP;->getInstance()Lcom/ss/android/vesdk/runtime/persistence/VESP;

    move-result-object v3

    const-string v4, ""

    invoke-virtual {v3, v2, v4}, Lcom/ss/android/vesdk/runtime/persistence/VESP;->get(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/String;

    invoke-virtual {v3}, Ljava/lang/String;->isEmpty()Z

    move-result v4

    if-nez v4, :cond_0

    invoke-direct {p0, v2, v3}, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->doCommand(Ljava/lang/String;Ljava/lang/String;)I

    :cond_0
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    :cond_1
    return-void
.end method

.method public storeCloudControlCommand(Landroid/content/Context;Ljava/lang/String;)V
    .locals 2
    .param p1    # Landroid/content/Context;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p2    # Ljava/lang/String;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    sget-object v0, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->TAG:Ljava/lang/String;

    invoke-static {v0, p2}, Lcom/ss/android/vesdk/VELogUtil;->d(Ljava/lang/String;Ljava/lang/String;)V

    :try_start_0
    new-instance v0, Lorg/json/JSONObject;

    invoke-direct {v0, p2}, Lorg/json/JSONObject;-><init>(Ljava/lang/String;)V

    const-string p2, "command"

    invoke-virtual {v0, p2}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p2

    invoke-static {}, Lcom/ss/android/vesdk/runtime/persistence/VESP;->getInstance()Lcom/ss/android/vesdk/runtime/persistence/VESP;

    move-result-object v1

    invoke-virtual {v1, p1}, Lcom/ss/android/vesdk/runtime/persistence/VESP;->init(Landroid/content/Context;)V

    invoke-virtual {v0}, Lorg/json/JSONObject;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p2, p1}, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->storeCommand(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p1

    sget-object p2, Lcom/ss/android/vesdk/runtime/VECloudCtrlManager;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, " json parse failed "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/Exception;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p2, p1}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    :goto_0
    return-void
.end method
