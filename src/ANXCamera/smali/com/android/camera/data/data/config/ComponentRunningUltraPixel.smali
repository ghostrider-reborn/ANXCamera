.class public Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;
.super Lcom/android/camera/data/data/ComponentData;
.source "ComponentRunningUltraPixel.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/data/data/config/ComponentRunningUltraPixel$UltraPixelSupport;
    }
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String;

.field public static final ULTRA_PIXEL_OFF:Ljava/lang/String; = "OFF"

.field public static final ULTRA_PIXEL_ON_FRONT_32M:Ljava/lang/String; = "FRONT_0x1"

.field public static final ULTRA_PIXEL_ON_REAR_108M:Ljava/lang/String; = "REAR_0x3"

.field public static final ULTRA_PIXEL_ON_REAR_48M:Ljava/lang/String; = "REAR_0x2"

.field public static final ULTRA_PIXEL_ON_REAR_64M:Ljava/lang/String; = "REAR_0x1"


# instance fields
.field private mCloseTipString:Ljava/lang/String;

.field private mCurrentMode:I

.field private mIsClosed:Landroid/util/SparseBooleanArray;

.field private mMenuDrawable:I
    .annotation build Landroid/support/annotation/DrawableRes;
    .end annotation
.end field

.field private mMenuString:Ljava/lang/String;

.field private mOpenTipString:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    const-class v0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;

    invoke-virtual {v0}, Ljava/lang/Class;->getSimpleName()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->TAG:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>(Lcom/android/camera/data/data/runing/DataItemRunning;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/data/data/ComponentData;-><init>(Lcom/android/camera/data/data/DataItemBase;)V

    const/4 p1, 0x0

    iput-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mOpenTipString:Ljava/lang/String;

    iput-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mCloseTipString:Ljava/lang/String;

    iput-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mMenuString:Ljava/lang/String;

    const/4 p1, -0x1

    iput p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mMenuDrawable:I

    return-void
.end method

.method private add108M()V
    .locals 11

    invoke-static {}, Lcom/android/camera/CameraAppImpl;->getAndroidContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mItems:Ljava/util/List;

    new-instance v2, Lcom/android/camera/data/data/ComponentDataItem;

    const/4 v3, 0x1

    new-array v4, v3, [Ljava/lang/Object;

    const v5, 0x7f0903ae

    invoke-virtual {v0, v5}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v6

    const/4 v7, 0x0

    aput-object v6, v4, v7

    const v6, 0x7f09026b

    invoke-virtual {v0, v6, v4}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    const-string v8, "OFF"

    const v9, 0x7f020174

    const v10, 0x7f020173

    invoke-direct {v2, v10, v9, v4, v8}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IILjava/lang/String;Ljava/lang/String;)V

    invoke-interface {v1, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object v1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mItems:Ljava/util/List;

    new-instance v2, Lcom/android/camera/data/data/ComponentDataItem;

    new-array v3, v3, [Ljava/lang/Object;

    invoke-virtual {v0, v5}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v4

    aput-object v4, v3, v7

    invoke-virtual {v0, v6, v3}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    const-string v3, "REAR_0x3"

    invoke-direct {v2, v10, v9, v0, v3}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IILjava/lang/String;Ljava/lang/String;)V

    invoke-interface {v1, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    const-string v0, "REAR_0x3"

    invoke-direct {p0, v0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->initUltraPixelResource(Ljava/lang/String;)V

    return-void
.end method

.method private add48M()V
    .locals 10

    invoke-static {}, Lcom/android/camera/CameraAppImpl;->getAndroidContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mItems:Ljava/util/List;

    new-instance v2, Lcom/android/camera/data/data/ComponentDataItem;

    const/4 v3, 0x1

    new-array v4, v3, [Ljava/lang/Object;

    const v5, 0x7f090265

    invoke-virtual {v0, v5}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v6

    const/4 v7, 0x0

    aput-object v6, v4, v7

    const v6, 0x7f09026b

    invoke-virtual {v0, v6, v4}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    const-string v8, "OFF"

    const v9, 0x7f02012d

    invoke-direct {v2, v9, v9, v4, v8}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IILjava/lang/String;Ljava/lang/String;)V

    invoke-interface {v1, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object v1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mItems:Ljava/util/List;

    new-instance v2, Lcom/android/camera/data/data/ComponentDataItem;

    new-array v3, v3, [Ljava/lang/Object;

    invoke-virtual {v0, v5}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v4

    aput-object v4, v3, v7

    invoke-virtual {v0, v6, v3}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    const-string v3, "REAR_0x2"

    invoke-direct {v2, v9, v9, v0, v3}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IILjava/lang/String;Ljava/lang/String;)V

    invoke-interface {v1, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    const-string v0, "REAR_0x2"

    invoke-direct {p0, v0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->initUltraPixelResource(Ljava/lang/String;)V

    return-void
.end method

.method private add64M()V
    .locals 10

    invoke-static {}, Lcom/android/camera/CameraAppImpl;->getAndroidContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mItems:Ljava/util/List;

    new-instance v2, Lcom/android/camera/data/data/ComponentDataItem;

    const/4 v3, 0x1

    new-array v4, v3, [Ljava/lang/Object;

    const v5, 0x7f090266

    invoke-virtual {v0, v5}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v6

    const/4 v7, 0x0

    aput-object v6, v4, v7

    const v6, 0x7f09026b

    invoke-virtual {v0, v6, v4}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    const-string v8, "OFF"

    const v9, 0x7f02012e

    invoke-direct {v2, v9, v9, v4, v8}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IILjava/lang/String;Ljava/lang/String;)V

    invoke-interface {v1, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object v1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mItems:Ljava/util/List;

    new-instance v2, Lcom/android/camera/data/data/ComponentDataItem;

    new-array v3, v3, [Ljava/lang/Object;

    invoke-virtual {v0, v5}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v4

    aput-object v4, v3, v7

    invoke-virtual {v0, v6, v3}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    const-string v3, "REAR_0x1"

    invoke-direct {v2, v9, v9, v0, v3}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IILjava/lang/String;Ljava/lang/String;)V

    invoke-interface {v1, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    const-string v0, "REAR_0x1"

    invoke-direct {p0, v0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->initUltraPixelResource(Ljava/lang/String;)V

    return-void
.end method

.method public static getNoSupportZoomTip()Ljava/lang/String;
    .locals 6

    invoke-static {}, Lcom/android/camera/CameraAppImpl;->getAndroidContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v1

    invoke-virtual {v1}, Lcom/mi/config/a;->gV()I

    move-result v1

    const v2, 0x7f090265

    const/4 v3, 0x0

    const/4 v4, 0x1

    const v5, 0x7f09026c

    packed-switch v1, :pswitch_data_0

    new-array v1, v4, [Ljava/lang/Object;

    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v2

    aput-object v2, v1, v3

    invoke-virtual {v0, v5, v1}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0

    :pswitch_0
    new-array v1, v4, [Ljava/lang/Object;

    const v2, 0x7f0903ae

    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v2

    aput-object v2, v1, v3

    invoke-virtual {v0, v5, v1}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0

    :pswitch_1
    new-array v1, v4, [Ljava/lang/Object;

    const v2, 0x7f090266

    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v2

    aput-object v2, v1, v3

    invoke-virtual {v0, v5, v1}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0

    :pswitch_2
    new-array v1, v4, [Ljava/lang/Object;

    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v2

    aput-object v2, v1, v3

    invoke-virtual {v0, v5, v1}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public static getUltraPixelSwitchTipsString()[Ljava/lang/String;
    .locals 9

    invoke-static {}, Lcom/android/camera/CameraAppImpl;->getAndroidContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v1

    invoke-virtual {v1}, Lcom/mi/config/a;->gV()I

    move-result v1

    const v2, 0x7f090269

    const v3, 0x7f090265

    const v4, 0x7f09026a

    const/4 v5, 0x2

    const/4 v6, 0x1

    const/4 v7, 0x0

    packed-switch v1, :pswitch_data_0

    new-array v1, v5, [Ljava/lang/String;

    new-array v5, v6, [Ljava/lang/Object;

    invoke-virtual {v0, v3}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v8

    aput-object v8, v5, v7

    invoke-virtual {v0, v4, v5}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    aput-object v4, v1, v7

    new-array v4, v6, [Ljava/lang/Object;

    invoke-virtual {v0, v3}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v3

    aput-object v3, v4, v7

    invoke-virtual {v0, v2, v4}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    aput-object v0, v1, v6

    return-object v1

    :pswitch_0
    new-array v1, v5, [Ljava/lang/String;

    new-array v3, v6, [Ljava/lang/Object;

    const v5, 0x7f0903ae

    invoke-virtual {v0, v5}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v8

    aput-object v8, v3, v7

    invoke-virtual {v0, v4, v3}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    aput-object v3, v1, v7

    new-array v3, v6, [Ljava/lang/Object;

    invoke-virtual {v0, v5}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v4

    aput-object v4, v3, v7

    invoke-virtual {v0, v2, v3}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    aput-object v0, v1, v6

    return-object v1

    :pswitch_1
    new-array v1, v5, [Ljava/lang/String;

    new-array v3, v6, [Ljava/lang/Object;

    const v5, 0x7f090266

    invoke-virtual {v0, v5}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v8

    aput-object v8, v3, v7

    invoke-virtual {v0, v4, v3}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    aput-object v3, v1, v7

    new-array v3, v6, [Ljava/lang/Object;

    invoke-virtual {v0, v5}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v4

    aput-object v4, v3, v7

    invoke-virtual {v0, v2, v3}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    aput-object v0, v1, v6

    return-object v1

    :pswitch_2
    new-array v1, v5, [Ljava/lang/String;

    new-array v5, v6, [Ljava/lang/Object;

    invoke-virtual {v0, v3}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v8

    aput-object v8, v5, v7

    invoke-virtual {v0, v4, v5}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    aput-object v4, v1, v7

    new-array v4, v6, [Ljava/lang/Object;

    invoke-virtual {v0, v3}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v3

    aput-object v3, v4, v7

    invoke-virtual {v0, v2, v4}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    aput-object v0, v1, v6

    return-object v1

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public static getUltraPixelTopMenuResources()[I
    .locals 2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->gV()I

    move-result v0

    const/4 v1, 0x2

    packed-switch v0, :pswitch_data_0

    new-array v0, v1, [I

    fill-array-data v0, :array_0

    return-object v0

    :pswitch_0
    new-array v0, v1, [I

    fill-array-data v0, :array_1

    return-object v0

    :pswitch_1
    new-array v0, v1, [I

    fill-array-data v0, :array_2

    return-object v0

    :pswitch_2
    new-array v0, v1, [I

    fill-array-data v0, :array_3

    return-object v0

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch

    :array_0
    .array-data 4
        0x7f020175
        0x7f020176
    .end array-data

    :array_1
    .array-data 4
        0x7f020173
        0x7f020174
    .end array-data

    :array_2
    .array-data 4
        0x7f020177
        0x7f020178
    .end array-data

    :array_3
    .array-data 4
        0x7f020175
        0x7f020176
    .end array-data
.end method

.method private initUltraPixelResource(Ljava/lang/String;)V
    .locals 8
    .param p1    # Ljava/lang/String;
        .annotation build Lcom/android/camera/data/data/config/ComponentRunningUltraPixel$UltraPixelSupport;
        .end annotation
    .end param

    invoke-static {}, Lcom/android/camera/CameraAppImpl;->getAndroidContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {p1}, Ljava/lang/String;->hashCode()I

    move-result v1

    const v2, -0x5237544d

    const/4 v3, 0x0

    const/4 v4, 0x1

    if-eq v1, v2, :cond_0

    packed-switch v1, :pswitch_data_0

    goto :goto_0

    :pswitch_0
    const-string v1, "REAR_0x3"

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_1

    const/4 v1, 0x3

    goto :goto_1

    :pswitch_1
    const-string v1, "REAR_0x2"

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_1

    move v1, v4

    goto :goto_1

    :pswitch_2
    const-string v1, "REAR_0x1"

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_1

    const/4 v1, 0x2

    goto :goto_1

    :cond_0
    const-string v1, "FRONT_0x1"

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_1

    move v1, v3

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v1, -0x1

    :goto_1
    const v2, 0x7f09026b

    const v5, 0x7f090268

    const v6, 0x7f090267

    packed-switch v1, :pswitch_data_1

    sget-object v0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Unknown ultra pixel size: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_2

    :pswitch_3
    const p1, 0x7f020173

    iput p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mMenuDrawable:I

    new-array p1, v4, [Ljava/lang/Object;

    const v1, 0x7f0903ae

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v7

    aput-object v7, p1, v3

    invoke-virtual {v0, v6, p1}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mOpenTipString:Ljava/lang/String;

    new-array p1, v4, [Ljava/lang/Object;

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v6

    aput-object v6, p1, v3

    invoke-virtual {v0, v5, p1}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mCloseTipString:Ljava/lang/String;

    new-array p1, v4, [Ljava/lang/Object;

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v1

    aput-object v1, p1, v3

    invoke-virtual {v0, v2, p1}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mMenuString:Ljava/lang/String;

    goto/16 :goto_2

    :pswitch_4
    const p1, 0x7f02012e

    iput p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mMenuDrawable:I

    new-array p1, v4, [Ljava/lang/Object;

    const v1, 0x7f090266

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v7

    aput-object v7, p1, v3

    invoke-virtual {v0, v6, p1}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mOpenTipString:Ljava/lang/String;

    new-array p1, v4, [Ljava/lang/Object;

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v6

    aput-object v6, p1, v3

    invoke-virtual {v0, v5, p1}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mCloseTipString:Ljava/lang/String;

    new-array p1, v4, [Ljava/lang/Object;

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v1

    aput-object v1, p1, v3

    invoke-virtual {v0, v2, p1}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mMenuString:Ljava/lang/String;

    goto :goto_2

    :pswitch_5
    const p1, 0x7f02012d

    iput p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mMenuDrawable:I

    new-array p1, v4, [Ljava/lang/Object;

    const v1, 0x7f090265

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v7

    aput-object v7, p1, v3

    invoke-virtual {v0, v6, p1}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mOpenTipString:Ljava/lang/String;

    new-array p1, v4, [Ljava/lang/Object;

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v6

    aput-object v6, p1, v3

    invoke-virtual {v0, v5, p1}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mCloseTipString:Ljava/lang/String;

    new-array p1, v4, [Ljava/lang/Object;

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v1

    aput-object v1, p1, v3

    invoke-virtual {v0, v2, p1}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mMenuString:Ljava/lang/String;

    goto :goto_2

    :pswitch_6
    const p1, 0x7f02012c

    iput p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mMenuDrawable:I

    new-array p1, v4, [Ljava/lang/Object;

    const v1, 0x7f090264

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v7

    aput-object v7, p1, v3

    invoke-virtual {v0, v6, p1}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mOpenTipString:Ljava/lang/String;

    new-array p1, v4, [Ljava/lang/Object;

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v6

    aput-object v6, p1, v3

    invoke-virtual {v0, v5, p1}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mCloseTipString:Ljava/lang/String;

    new-array p1, v4, [Ljava/lang/Object;

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v1

    aput-object v1, p1, v3

    invoke-virtual {v0, v2, p1}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mMenuString:Ljava/lang/String;

    nop

    :goto_2
    return-void

    :pswitch_data_0
    .packed-switch -0x4372e32
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x0
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
    .end packed-switch
.end method


# virtual methods
.method public getCurrentSupportUltraPixel()Ljava/lang/String;
    .locals 2
    .annotation build Lcom/android/camera/data/data/config/ComponentRunningUltraPixel$UltraPixelSupport;
    .end annotation

    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mItems:Ljava/util/List;

    const/4 v1, 0x1

    invoke-interface {v0, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/data/data/ComponentDataItem;

    iget-object v0, v0, Lcom/android/camera/data/data/ComponentDataItem;->mValue:Ljava/lang/String;

    return-object v0
.end method

.method public getDefaultValue(I)Ljava/lang/String;
    .locals 0
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    const-string p1, "OFF"

    return-object p1
.end method

.method public getDisplayTitleString()I
    .locals 1

    const/4 v0, 0x0

    return v0
.end method

.method public getItems()Ljava/util/List;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Lcom/android/camera/data/data/ComponentDataItem;",
            ">;"
        }
    .end annotation

    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mItems:Ljava/util/List;

    return-object v0
.end method

.method public getKey(I)Ljava/lang/String;
    .locals 0

    const-string p1, "pref_ultra_pixel"

    return-object p1
.end method

.method public getMenuDrawable()I
    .locals 1
    .annotation build Landroid/support/annotation/DrawableRes;
    .end annotation

    iget v0, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mMenuDrawable:I

    return v0
.end method

.method public getMenuString()Ljava/lang/String;
    .locals 1

    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mMenuString:Ljava/lang/String;

    return-object v0
.end method

.method public getUltraPixelCloseTip()Ljava/lang/String;
    .locals 1

    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mCloseTipString:Ljava/lang/String;

    return-object v0
.end method

.method public getUltraPixelOpenTip()Ljava/lang/String;
    .locals 1

    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mOpenTipString:Ljava/lang/String;

    return-object v0
.end method

.method public isClosed()Z
    .locals 2

    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mIsClosed:Landroid/util/SparseBooleanArray;

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return v0

    :cond_0
    iget v0, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mCurrentMode:I

    const/16 v1, 0xa5

    if-ne v0, v1, :cond_1

    const/16 v0, 0xa3

    iput v0, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mCurrentMode:I

    :cond_1
    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mIsClosed:Landroid/util/SparseBooleanArray;

    iget v1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mCurrentMode:I

    invoke-virtual {v0, v1}, Landroid/util/SparseBooleanArray;->get(I)Z

    move-result v0

    return v0
.end method

.method public isFront32MPSwitchOn()Z
    .locals 2

    const-string v0, "FRONT_0x1"

    const/16 v1, 0xa0

    invoke-virtual {p0, v1}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->getComponentValue(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method public isRear108MPSwitchOn()Z
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->isClosed()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x0

    return v0

    :cond_0
    const-string v0, "REAR_0x3"

    const/16 v1, 0xa0

    invoke-virtual {p0, v1}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->getComponentValue(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method public isRear48MPSwitchOn()Z
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->isClosed()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x0

    return v0

    :cond_0
    const-string v0, "REAR_0x2"

    const/16 v1, 0xa0

    invoke-virtual {p0, v1}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->getComponentValue(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method public isRear64MPSwitchOn()Z
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->isClosed()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x0

    return v0

    :cond_0
    const-string v0, "REAR_0x1"

    const/16 v1, 0xa0

    invoke-virtual {p0, v1}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->getComponentValue(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method public isRearSwitchOn()Z
    .locals 1

    invoke-virtual {p0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->isRear48MPSwitchOn()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->isRear64MPSwitchOn()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->isRear108MPSwitchOn()Z

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

.method public isSwitchOn()Z
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->isClosed()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x0

    return v0

    :cond_0
    const-string v0, "OFF"

    const/16 v1, 0xa0

    invoke-virtual {p0, v1}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->getComponentValue(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    xor-int/lit8 v0, v0, 0x1

    return v0
.end method

.method public reInit(IILcom/android/camera2/CameraCapabilities;)V
    .locals 8

    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mItems:Ljava/util/List;

    if-nez v0, :cond_0

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mItems:Ljava/util/List;

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mItems:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->clear()V

    :goto_0
    iput p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mCurrentMode:I

    if-nez p3, :cond_1

    return-void

    :cond_1
    invoke-static {}, Lcom/android/camera/CameraAppImpl;->getAndroidContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const/16 v1, 0xa3

    const/4 v2, -0x1

    const/4 v3, 0x0

    const v4, 0x7f09026b

    const/4 v5, 0x1

    if-eq p1, v1, :cond_5

    const/16 v1, 0xa5

    if-eq p1, v1, :cond_5

    const/16 v1, 0xa7

    if-eq p1, v1, :cond_4

    const/16 p3, 0xaf

    if-eq p1, p3, :cond_2

    return-void

    :cond_2
    if-nez p2, :cond_a

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object p1

    invoke-virtual {p1}, Lcom/mi/config/a;->gV()I

    move-result p1

    if-le p1, v2, :cond_3

    packed-switch p1, :pswitch_data_0

    goto :goto_1

    :pswitch_0
    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->add108M()V

    goto :goto_1

    :pswitch_1
    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->add64M()V

    goto :goto_1

    :pswitch_2
    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->add48M()V

    nop

    :cond_3
    :goto_1
    goto/16 :goto_4

    :cond_4
    if-nez p2, :cond_a

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object p1

    invoke-virtual {p1}, Lcom/mi/config/a;->gV()I

    move-result p1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object p2

    invoke-virtual {p2}, Lcom/mi/config/a;->ib()Landroid/util/Size;

    move-result-object p2

    if-le p1, v2, :cond_a

    invoke-virtual {p3, p2}, Lcom/android/camera2/CameraCapabilities;->isUltraPixelPhotographySupported(Landroid/util/Size;)Z

    move-result p2

    if-eqz p2, :cond_a

    packed-switch p1, :pswitch_data_1

    sget-object p2, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->TAG:Ljava/lang/String;

    new-instance p3, Ljava/lang/StringBuilder;

    invoke-direct {p3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "Unknown rearPixel index: "

    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p3, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {p3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p2, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_4

    :pswitch_3
    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->add108M()V

    goto/16 :goto_4

    :pswitch_4
    iget-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mItems:Ljava/util/List;

    new-instance p2, Lcom/android/camera/data/data/ComponentDataItem;

    new-array p3, v5, [Ljava/lang/Object;

    const v1, 0x7f090266

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v2

    aput-object v2, p3, v3

    invoke-virtual {v0, v4, p3}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p3

    const-string v2, "OFF"

    const v6, 0x7f020178

    const v7, 0x7f020177

    invoke-direct {p2, v7, v6, p3, v2}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IILjava/lang/String;Ljava/lang/String;)V

    invoke-interface {p1, p2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mItems:Ljava/util/List;

    new-instance p2, Lcom/android/camera/data/data/ComponentDataItem;

    new-array p3, v5, [Ljava/lang/Object;

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v1

    aput-object v1, p3, v3

    invoke-virtual {v0, v4, p3}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p3

    const-string v0, "REAR_0x1"

    invoke-direct {p2, v7, v6, p3, v0}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IILjava/lang/String;Ljava/lang/String;)V

    invoke-interface {p1, p2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    const-string p1, "REAR_0x1"

    invoke-direct {p0, p1}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->initUltraPixelResource(Ljava/lang/String;)V

    goto/16 :goto_4

    :pswitch_5
    iget-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mItems:Ljava/util/List;

    new-instance p2, Lcom/android/camera/data/data/ComponentDataItem;

    new-array p3, v5, [Ljava/lang/Object;

    const v1, 0x7f090265

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v2

    aput-object v2, p3, v3

    invoke-virtual {v0, v4, p3}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p3

    const-string v2, "OFF"

    const v6, 0x7f020176

    const v7, 0x7f020175

    invoke-direct {p2, v7, v6, p3, v2}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IILjava/lang/String;Ljava/lang/String;)V

    invoke-interface {p1, p2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mItems:Ljava/util/List;

    new-instance p2, Lcom/android/camera/data/data/ComponentDataItem;

    new-array p3, v5, [Ljava/lang/Object;

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v1

    aput-object v1, p3, v3

    invoke-virtual {v0, v4, p3}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p3

    const-string v0, "REAR_0x2"

    invoke-direct {p2, v7, v6, p3, v0}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IILjava/lang/String;Ljava/lang/String;)V

    invoke-interface {p1, p2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    const-string p1, "REAR_0x2"

    invoke-direct {p0, p1}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->initUltraPixelResource(Ljava/lang/String;)V

    goto/16 :goto_4

    :cond_5
    if-nez p2, :cond_7

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object p1

    invoke-virtual {p1}, Lcom/mi/config/a;->ht()Z

    move-result p1

    if-nez p1, :cond_7

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object p1

    invoke-virtual {p1}, Lcom/mi/config/a;->gV()I

    move-result p1

    if-le p1, v2, :cond_6

    packed-switch p1, :pswitch_data_2

    goto :goto_2

    :pswitch_6
    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->add64M()V

    goto :goto_2

    :pswitch_7
    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->add48M()V

    nop

    :cond_6
    :goto_2
    goto :goto_4

    :cond_7
    if-ne p2, v5, :cond_a

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object p1

    invoke-virtual {p1}, Lcom/mi/config/a;->gW()I

    move-result p1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object p2

    invoke-virtual {p2}, Lcom/mi/config/a;->ic()Landroid/util/Size;

    move-result-object p2

    if-le p1, v2, :cond_9

    invoke-virtual {p3, p2}, Lcom/android/camera2/CameraCapabilities;->isUltraPixelPhotographySupported(Landroid/util/Size;)Z

    move-result p2

    if-eqz p2, :cond_9

    if-eqz p1, :cond_8

    goto :goto_3

    :cond_8
    iget-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mItems:Ljava/util/List;

    new-instance p2, Lcom/android/camera/data/data/ComponentDataItem;

    new-array p3, v5, [Ljava/lang/Object;

    const v1, 0x7f090264

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v2

    aput-object v2, p3, v3

    invoke-virtual {v0, v4, p3}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p3

    const-string v2, "OFF"

    const v6, 0x7f02012c

    invoke-direct {p2, v6, v6, p3, v2}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IILjava/lang/String;Ljava/lang/String;)V

    invoke-interface {p1, p2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mItems:Ljava/util/List;

    new-instance p2, Lcom/android/camera/data/data/ComponentDataItem;

    new-array p3, v5, [Ljava/lang/Object;

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v1

    aput-object v1, p3, v3

    invoke-virtual {v0, v4, p3}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p3

    const-string v0, "FRONT_0x1"

    invoke-direct {p2, v6, v6, p3, v0}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IILjava/lang/String;Ljava/lang/String;)V

    invoke-interface {p1, p2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    const-string p1, "FRONT_0x1"

    invoke-direct {p0, p1}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->initUltraPixelResource(Ljava/lang/String;)V

    :cond_9
    :goto_3
    nop

    :cond_a
    :goto_4
    return-void

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x1
        :pswitch_5
        :pswitch_4
        :pswitch_3
    .end packed-switch

    :pswitch_data_2
    .packed-switch 0x1
        :pswitch_7
        :pswitch_6
    .end packed-switch
.end method

.method public setClosed(Z)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mIsClosed:Landroid/util/SparseBooleanArray;

    if-nez v0, :cond_0

    new-instance v0, Landroid/util/SparseBooleanArray;

    invoke-direct {v0}, Landroid/util/SparseBooleanArray;-><init>()V

    iput-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mIsClosed:Landroid/util/SparseBooleanArray;

    :cond_0
    iget v0, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mCurrentMode:I

    const/16 v1, 0xa5

    if-ne v0, v1, :cond_1

    const/16 v0, 0xa3

    iput v0, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mCurrentMode:I

    :cond_1
    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mIsClosed:Landroid/util/SparseBooleanArray;

    iget v1, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mCurrentMode:I

    invoke-virtual {v0, v1, p1}, Landroid/util/SparseBooleanArray;->put(IZ)V

    return-void
.end method

.method public switchOff()V
    .locals 2

    const-string v0, "OFF"

    const/16 v1, 0xa0

    invoke-virtual {p0, v1, v0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->setComponentValue(ILjava/lang/String;)V

    return-void
.end method

.method public switchOn(Ljava/lang/String;)V
    .locals 1
    .param p1    # Ljava/lang/String;
        .annotation build Lcom/android/camera/data/data/config/ComponentRunningUltraPixel$UltraPixelSupport;
        .end annotation
    .end param

    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->setClosed(Z)V

    const/16 v0, 0xa0

    invoke-virtual {p0, v0, p1}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->setComponentValue(ILjava/lang/String;)V

    return-void
.end method

.method public switchOnCurrentSupported(IILcom/android/camera2/CameraCapabilities;)V
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->mItems:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    const/4 v1, 0x2

    if-ge v0, v1, :cond_1

    :cond_0
    invoke-virtual {p0, p1, p2, p3}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->reInit(IILcom/android/camera2/CameraCapabilities;)V

    :cond_1
    invoke-virtual {p0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->isEmpty()Z

    move-result p1

    if-eqz p1, :cond_2

    const-string p1, "UltraPixel:"

    const-string p2, "CameraCapabilities not supported"

    invoke-static {p1, p2}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_2
    const/4 p1, 0x0

    invoke-virtual {p0, p1}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->setClosed(Z)V

    const/16 p1, 0xa0

    invoke-virtual {p0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->getCurrentSupportUltraPixel()Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->setComponentValue(ILjava/lang/String;)V

    return-void
.end method
