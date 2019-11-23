.class public Lcom/android/camera/data/data/config/ComponentManuallyISO;
.super Lcom/android/camera/data/data/ComponentData;
.source "ComponentManuallyISO.java"


# annotations
.annotation build Landroid/annotation/TargetApi;
    value = 0x15
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String; = "ComponentManuallyISO"


# instance fields
.field private mFullItems:[Lcom/android/camera/data/data/ComponentDataItem;


# direct methods
.method public constructor <init>(Lcom/android/camera/data/data/config/DataItemConfig;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/data/data/ComponentData;-><init>(Lcom/android/camera/data/data/DataItemBase;)V

    return-void
.end method

.method private getFullItems()[Lcom/android/camera/data/data/ComponentDataItem;
    .locals 6

    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentManuallyISO;->mFullItems:[Lcom/android/camera/data/data/ComponentDataItem;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentManuallyISO;->mFullItems:[Lcom/android/camera/data/data/ComponentDataItem;

    return-object v0

    :cond_0
    const/4 v0, 0x7

    new-array v0, v0, [Lcom/android/camera/data/data/ComponentDataItem;

    const/4 v1, 0x0

    new-instance v2, Lcom/android/camera/data/data/ComponentDataItem;

    const v3, 0x7f0900b3

    const-string v4, "0"

    const/4 v5, -0x1

    invoke-direct {v2, v5, v5, v3, v4}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IIILjava/lang/String;)V

    aput-object v2, v0, v1

    const/4 v1, 0x1

    new-instance v2, Lcom/android/camera/data/data/ComponentDataItem;

    const v3, 0x7f0900b4

    const-string v4, "100"

    invoke-direct {v2, v5, v5, v3, v4}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IIILjava/lang/String;)V

    aput-object v2, v0, v1

    const/4 v1, 0x2

    new-instance v2, Lcom/android/camera/data/data/ComponentDataItem;

    const v3, 0x7f0900b5

    const-string v4, "200"

    invoke-direct {v2, v5, v5, v3, v4}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IIILjava/lang/String;)V

    aput-object v2, v0, v1

    const/4 v1, 0x3

    new-instance v2, Lcom/android/camera/data/data/ComponentDataItem;

    const v3, 0x7f0900b6

    const-string v4, "400"

    invoke-direct {v2, v5, v5, v3, v4}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IIILjava/lang/String;)V

    aput-object v2, v0, v1

    const/4 v1, 0x4

    new-instance v2, Lcom/android/camera/data/data/ComponentDataItem;

    const v3, 0x7f0900b7

    const-string v4, "800"

    invoke-direct {v2, v5, v5, v3, v4}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IIILjava/lang/String;)V

    aput-object v2, v0, v1

    const/4 v1, 0x5

    new-instance v2, Lcom/android/camera/data/data/ComponentDataItem;

    const v3, 0x7f0900b8

    const-string v4, "1600"

    invoke-direct {v2, v5, v5, v3, v4}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IIILjava/lang/String;)V

    aput-object v2, v0, v1

    const/4 v1, 0x6

    new-instance v2, Lcom/android/camera/data/data/ComponentDataItem;

    const v3, 0x7f0900b9

    const-string v4, "3200"

    invoke-direct {v2, v5, v5, v3, v4}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IIILjava/lang/String;)V

    aput-object v2, v0, v1

    iput-object v0, p0, Lcom/android/camera/data/data/config/ComponentManuallyISO;->mFullItems:[Lcom/android/camera/data/data/ComponentDataItem;

    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentManuallyISO;->mFullItems:[Lcom/android/camera/data/data/ComponentDataItem;

    return-object v0
.end method


# virtual methods
.method protected checkValueValid(Ljava/lang/String;)Z
    .locals 1

    const v0, 0x7f0f001a

    invoke-static {p1, v0}, Lcom/android/camera/Util;->isStringValueContained(Ljava/lang/Object;I)Z

    move-result p1

    return p1
.end method

.method public getComponentValue(I)Ljava/lang/String;
    .locals 3

    invoke-super {p0, p1}, Lcom/android/camera/data/data/ComponentData;->getComponentValue(I)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p0}, Lcom/android/camera/data/data/config/ComponentManuallyISO;->getItems()Ljava/util/List;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/List;->isEmpty()Z

    move-result v1

    if-eqz v1, :cond_0

    return-object p1

    :cond_0
    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v1

    add-int/lit8 v1, v1, -0x1

    invoke-interface {v0, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/data/data/ComponentDataItem;

    iget-object v0, v0, Lcom/android/camera/data/data/ComponentDataItem;->mValue:Ljava/lang/String;

    invoke-static {p1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v1

    invoke-static {v0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    if-le v1, v2, :cond_1

    return-object v0

    :cond_1
    return-object p1
.end method

.method public getDefaultValue(I)Ljava/lang/String;
    .locals 0

    const-string p1, "0"

    return-object p1
.end method

.method public getDisplayTitleString()I
    .locals 1

    const v0, 0x7f0900b2

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

    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentManuallyISO;->mItems:Ljava/util/List;

    return-object v0
.end method

.method public getKey(I)Ljava/lang/String;
    .locals 0

    const-string p1, "pref_qc_camera_iso_key"

    return-object p1
.end method

.method public reInit(IILcom/android/camera2/CameraCapabilities;)Ljava/util/List;
    .locals 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(II",
            "Lcom/android/camera2/CameraCapabilities;",
            ")",
            "Ljava/util/List<",
            "Lcom/android/camera/data/data/ComponentDataItem;",
            ">;"
        }
    .end annotation

    iget-object p1, p0, Lcom/android/camera/data/data/config/ComponentManuallyISO;->mItems:Ljava/util/List;

    if-nez p1, :cond_0

    new-instance p1, Ljava/util/ArrayList;

    invoke-direct {p1}, Ljava/util/ArrayList;-><init>()V

    iput-object p1, p0, Lcom/android/camera/data/data/config/ComponentManuallyISO;->mItems:Ljava/util/List;

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/android/camera/data/data/config/ComponentManuallyISO;->mItems:Ljava/util/List;

    invoke-interface {p1}, Ljava/util/List;->clear()V

    :goto_0
    if-nez p3, :cond_1

    const-string p1, "ComponentManuallyISO"

    const-string p2, "initItems: CameraCapabilities is null!!!"

    invoke-static {p1, p2}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p1, p0, Lcom/android/camera/data/data/config/ComponentManuallyISO;->mItems:Ljava/util/List;

    return-object p1

    :cond_1
    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentManuallyISO;->getFullItems()[Lcom/android/camera/data/data/ComponentDataItem;

    move-result-object p1

    iget-object p2, p0, Lcom/android/camera/data/data/config/ComponentManuallyISO;->mItems:Ljava/util/List;

    const/4 v0, 0x0

    aget-object v0, p1, v0

    invoke-interface {p2, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    invoke-virtual {p3}, Lcom/android/camera2/CameraCapabilities;->getIsoRange()Landroid/util/Range;

    move-result-object p2

    if-eqz p2, :cond_4

    invoke-virtual {p2}, Landroid/util/Range;->getLower()Ljava/lang/Comparable;

    move-result-object p3

    check-cast p3, Ljava/lang/Integer;

    invoke-virtual {p3}, Ljava/lang/Integer;->intValue()I

    move-result p3

    invoke-virtual {p2}, Landroid/util/Range;->getUpper()Ljava/lang/Comparable;

    move-result-object p2

    check-cast p2, Ljava/lang/Integer;

    invoke-virtual {p2}, Ljava/lang/Integer;->intValue()I

    move-result p2

    const/4 v0, 0x1

    :goto_1
    array-length v1, p1

    if-ge v0, v1, :cond_4

    aget-object v1, p1, v0

    iget-object v2, v1, Lcom/android/camera/data/data/ComponentDataItem;->mValue:Ljava/lang/String;

    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    if-eqz v0, :cond_2

    if-gt p3, v2, :cond_3

    if-gt v2, p2, :cond_3

    :cond_2
    iget-object v2, p0, Lcom/android/camera/data/data/config/ComponentManuallyISO;->mItems:Ljava/util/List;

    invoke-interface {v2, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    :cond_3
    add-int/lit8 v0, v0, 0x1

    goto :goto_1

    :cond_4
    iget-object p1, p0, Lcom/android/camera/data/data/config/ComponentManuallyISO;->mItems:Ljava/util/List;

    return-object p1
.end method

.method protected resetComponentValue(I)V
    .locals 1

    invoke-virtual {p0, p1}, Lcom/android/camera/data/data/config/ComponentManuallyISO;->getDefaultValue(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0, p1, v0}, Lcom/android/camera/data/data/config/ComponentManuallyISO;->setComponentValue(ILjava/lang/String;)V

    return-void
.end method
