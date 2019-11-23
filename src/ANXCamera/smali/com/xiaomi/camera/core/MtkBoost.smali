.class public Lcom/xiaomi/camera/core/MtkBoost;
.super Ljava/lang/Object;
.source "MtkBoost.java"

# interfaces
.implements Lcom/xiaomi/camera/core/BaseBoostFramework;


# static fields
.field private static final BOOST_CLASS_NAME:Ljava/lang/String; = "android.util.MtkBoostFramework"

.field private static final BOOST_METHOD_NAME:Ljava/lang/String; = "perfLockAcquire"

.field private static final BOOST_STOP_NAME:Ljava/lang/String; = "perfLockRelease"

.field private static final TAG:Ljava/lang/String; = "MtkBoost"

.field private static isInited:Z

.field private static mBoostClass:Ljava/lang/Class;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/Class<",
            "*>;"
        }
    .end annotation
.end field

.field private static final mFBoostParamVal:[I

.field private static mStartBoost:Ljava/lang/reflect/Method;

.field private static mStopBoost:Ljava/lang/reflect/Method;


# instance fields
.field private mPerf:Ljava/lang/Object;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    const/4 v0, 0x0

    sput-object v0, Lcom/xiaomi/camera/core/MtkBoost;->mStartBoost:Ljava/lang/reflect/Method;

    sput-object v0, Lcom/xiaomi/camera/core/MtkBoost;->mStopBoost:Ljava/lang/reflect/Method;

    sput-object v0, Lcom/xiaomi/camera/core/MtkBoost;->mBoostClass:Ljava/lang/Class;

    const/16 v0, 0x8

    new-array v0, v0, [I

    fill-array-data v0, :array_0

    sput-object v0, Lcom/xiaomi/camera/core/MtkBoost;->mFBoostParamVal:[I

    return-void

    nop

    :array_0
    .array-data 4
        0x400000
        0x1e8480
        0x400100
        0x1e8480
        0x1000000
        0x0
        0xc00000
        0x0
    .end array-data
.end method

.method public constructor <init>()V
    .locals 7

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/xiaomi/camera/core/MtkBoost;->mPerf:Ljava/lang/Object;

    const-class v0, Lcom/xiaomi/camera/core/MtkBoost;

    monitor-enter v0

    :try_start_0
    sget-boolean v1, Lcom/xiaomi/camera/core/MtkBoost;->isInited:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    const/4 v2, 0x1

    const/4 v3, 0x0

    if-nez v1, :cond_1

    :try_start_1
    const-string v1, "android.util.MtkBoostFramework"

    invoke-static {v1}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v1

    sput-object v1, Lcom/xiaomi/camera/core/MtkBoost;->mBoostClass:Ljava/lang/Class;

    sget-object v1, Lcom/xiaomi/camera/core/MtkBoost;->mBoostClass:Ljava/lang/Class;

    if-eqz v1, :cond_0

    sget-object v1, Lcom/xiaomi/camera/core/MtkBoost;->mBoostClass:Ljava/lang/Class;

    const-string v4, "perfLockAcquire"

    const/4 v5, 0x2

    new-array v5, v5, [Ljava/lang/Class;

    sget-object v6, Ljava/lang/Integer;->TYPE:Ljava/lang/Class;

    aput-object v6, v5, v3

    const-class v6, [I

    aput-object v6, v5, v2

    invoke-virtual {v1, v4, v5}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v1

    sput-object v1, Lcom/xiaomi/camera/core/MtkBoost;->mStartBoost:Ljava/lang/reflect/Method;

    sget-object v1, Lcom/xiaomi/camera/core/MtkBoost;->mBoostClass:Ljava/lang/Class;

    const-string v4, "perfLockRelease"

    new-array v5, v3, [Ljava/lang/Class;

    invoke-virtual {v1, v4, v5}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v1

    sput-object v1, Lcom/xiaomi/camera/core/MtkBoost;->mStopBoost:Ljava/lang/reflect/Method;
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    :cond_0
    goto :goto_0

    :catch_0
    move-exception v1

    :try_start_2
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    :goto_0
    sput-boolean v2, Lcom/xiaomi/camera/core/MtkBoost;->isInited:Z

    :cond_1
    monitor-exit v0
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    :try_start_3
    sget-object v0, Lcom/xiaomi/camera/core/MtkBoost;->mBoostClass:Ljava/lang/Class;

    if-eqz v0, :cond_2

    sget-object v0, Lcom/xiaomi/camera/core/MtkBoost;->mBoostClass:Ljava/lang/Class;

    new-array v1, v3, [Ljava/lang/Class;

    invoke-virtual {v0, v1}, Ljava/lang/Class;->getConstructor([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;

    move-result-object v0

    invoke-virtual {v0, v2}, Ljava/lang/reflect/Constructor;->setAccessible(Z)V

    new-array v1, v3, [Ljava/lang/Object;

    invoke-virtual {v0, v1}, Ljava/lang/reflect/Constructor;->newInstance([Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    iput-object v0, p0, Lcom/xiaomi/camera/core/MtkBoost;->mPerf:Ljava/lang/Object;
    :try_end_3
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_3} :catch_1

    :cond_2
    goto :goto_1

    :catch_1
    move-exception v0

    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    :goto_1
    return-void

    :catchall_0
    move-exception v1

    :try_start_4
    monitor-exit v0
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_0

    throw v1
.end method

.method private startBoostInternal(I)Z
    .locals 5

    const-string v0, "MtkBoost"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v2, "startBoostInternal "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/xiaomi/camera/core/MtkBoost;->mPerf:Ljava/lang/Object;

    const/4 v1, 0x0

    if-nez v0, :cond_0

    const-string p1, "MtkBoost"

    const-string v0, "not init boost pref"

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return v1

    :cond_0
    :try_start_0
    sget-object v0, Lcom/xiaomi/camera/core/MtkBoost;->mStartBoost:Ljava/lang/reflect/Method;

    if-eqz v0, :cond_1

    const-string v0, "MtkBoost"

    const-string v2, "ready to boost"

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    sget-object v0, Lcom/xiaomi/camera/core/MtkBoost;->mStartBoost:Ljava/lang/reflect/Method;

    const/4 v2, 0x1

    invoke-virtual {v0, v2}, Ljava/lang/reflect/Method;->setAccessible(Z)V

    sget-object v0, Lcom/xiaomi/camera/core/MtkBoost;->mStartBoost:Ljava/lang/reflect/Method;

    iget-object v3, p0, Lcom/xiaomi/camera/core/MtkBoost;->mPerf:Ljava/lang/Object;

    const/4 v4, 0x2

    new-array v4, v4, [Ljava/lang/Object;

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    aput-object p1, v4, v1

    sget-object p1, Lcom/xiaomi/camera/core/MtkBoost;->mFBoostParamVal:[I

    aput-object p1, v4, v2

    invoke-virtual {v0, v3, v4}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return v2

    :cond_1
    goto :goto_0

    :catch_0
    move-exception p1

    const-string v0, "MtkBoost"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v3, "start boost exception "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    :goto_0
    return v1
.end method

.method private stopBoostInternal()V
    .locals 4

    const-string v0, "MtkBoost"

    const-string/jumbo v1, "stopBoostInternal:"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/xiaomi/camera/core/MtkBoost;->mPerf:Ljava/lang/Object;

    if-nez v0, :cond_0

    const-string v0, "MtkBoost"

    const-string v1, "not init boost pref, not need to stop"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_0
    :try_start_0
    sget-object v0, Lcom/xiaomi/camera/core/MtkBoost;->mStopBoost:Ljava/lang/reflect/Method;

    if-eqz v0, :cond_1

    const-string v0, "MtkBoost"

    const-string v1, "ready to stop boost"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    sget-object v0, Lcom/xiaomi/camera/core/MtkBoost;->mStopBoost:Ljava/lang/reflect/Method;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Ljava/lang/reflect/Method;->setAccessible(Z)V

    sget-object v0, Lcom/xiaomi/camera/core/MtkBoost;->mStopBoost:Ljava/lang/reflect/Method;

    iget-object v1, p0, Lcom/xiaomi/camera/core/MtkBoost;->mPerf:Ljava/lang/Object;

    const/4 v2, 0x0

    new-array v2, v2, [Ljava/lang/Object;

    invoke-virtual {v0, v1, v2}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    :cond_1
    goto :goto_0

    :catch_0
    move-exception v0

    const-string v1, "MtkBoost"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v3, "stop boost exception "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    :goto_0
    return-void
.end method


# virtual methods
.method public startBoost()Z
    .locals 1

    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lcom/xiaomi/camera/core/MtkBoost;->startBoost(I)Z

    move-result v0

    return v0
.end method

.method public startBoost(I)Z
    .locals 3

    invoke-direct {p0, p1}, Lcom/xiaomi/camera/core/MtkBoost;->startBoostInternal(I)Z

    move-result p1

    const-string v0, "MtkBoost"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v2, "startBoost: isBoostStarted "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return p1
.end method

.method public stopBoost()V
    .locals 0

    invoke-direct {p0}, Lcom/xiaomi/camera/core/MtkBoost;->stopBoostInternal()V

    return-void
.end method
