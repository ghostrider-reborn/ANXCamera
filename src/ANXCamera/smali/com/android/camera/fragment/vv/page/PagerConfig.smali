.class public Lcom/android/camera/fragment/vv/page/PagerConfig;
.super Ljava/lang/Object;
.source "PagerConfig.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "PagerGrid"

.field private static sFlingThreshold:I

.field private static sMillisecondsPreInch:F

.field private static sShowLog:Z


# direct methods
.method static constructor <clinit>()V
    .locals 1

    const/4 v0, 0x0

    sput-boolean v0, Lcom/android/camera/fragment/vv/page/PagerConfig;->sShowLog:Z

    const/16 v0, 0x3e8

    sput v0, Lcom/android/camera/fragment/vv/page/PagerConfig;->sFlingThreshold:I

    const/high16 v0, 0x42700000    # 60.0f

    sput v0, Lcom/android/camera/fragment/vv/page/PagerConfig;->sMillisecondsPreInch:F

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static getFlingThreshold()I
    .locals 1

    sget v0, Lcom/android/camera/fragment/vv/page/PagerConfig;->sFlingThreshold:I

    return v0
.end method

.method public static getMillisecondsPreInch()F
    .locals 1

    sget v0, Lcom/android/camera/fragment/vv/page/PagerConfig;->sMillisecondsPreInch:F

    return v0
.end method

.method public static isShowLog()Z
    .locals 1

    sget-boolean v0, Lcom/android/camera/fragment/vv/page/PagerConfig;->sShowLog:Z

    return v0
.end method

.method public static setFlingThreshold(I)V
    .locals 0

    sput p0, Lcom/android/camera/fragment/vv/page/PagerConfig;->sFlingThreshold:I

    return-void
.end method

.method public static setMillisecondsPreInch(F)V
    .locals 0

    sput p0, Lcom/android/camera/fragment/vv/page/PagerConfig;->sMillisecondsPreInch:F

    return-void
.end method

.method public static setShowLog(Z)V
    .locals 0

    sput-boolean p0, Lcom/android/camera/fragment/vv/page/PagerConfig;->sShowLog:Z

    return-void
.end method
