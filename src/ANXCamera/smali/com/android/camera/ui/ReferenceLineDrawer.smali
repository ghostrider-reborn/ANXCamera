.class public Lcom/android/camera/ui/ReferenceLineDrawer;
.super Landroid/view/View;
.source "ReferenceLineDrawer.java"


# static fields
.field private static final BORDER:I = 0x1

.field public static final TAG:Ljava/lang/String;


# instance fields
.field private isGradienterEnabled:Z

.field private mBottomVisible:Z

.field private mColumnCount:I

.field private mCurrentDirect:Lcom/android/camera/ui/GradienterDrawer$Direct;

.field private mDeviceRotation:F

.field private mFrameColor:I

.field private mFramePaint:Landroid/graphics/Paint;

.field private mLineColor:I

.field private mLinePaint:Landroid/graphics/Paint;

.field private mRowCount:I

.field private mTopVisible:Z


# direct methods
.method static constructor <clinit>()V
    .locals 1

    const-class v0, Lcom/android/camera/ui/ReferenceLineDrawer;

    invoke-virtual {v0}, Ljava/lang/Class;->getSimpleName()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/android/camera/ui/ReferenceLineDrawer;->TAG:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;)V
    .locals 0

    invoke-direct {p0, p1}, Landroid/view/View;-><init>(Landroid/content/Context;)V

    const/4 p1, 0x1

    iput p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    iput p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    iput-boolean p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mTopVisible:Z

    iput-boolean p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mBottomVisible:Z

    const p1, 0x6affffff

    iput p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mLineColor:I

    const/high16 p1, 0x18000000

    iput p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mFrameColor:I

    const/4 p1, 0x0

    iput p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mDeviceRotation:F

    sget-object p1, Lcom/android/camera/ui/GradienterDrawer$Direct;->NONE:Lcom/android/camera/ui/GradienterDrawer$Direct;

    iput-object p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mCurrentDirect:Lcom/android/camera/ui/GradienterDrawer$Direct;

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 0

    invoke-direct {p0, p1, p2}, Landroid/view/View;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    const/4 p1, 0x1

    iput p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    iput p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    iput-boolean p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mTopVisible:Z

    iput-boolean p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mBottomVisible:Z

    const p1, 0x6affffff

    iput p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mLineColor:I

    const/high16 p1, 0x18000000

    iput p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mFrameColor:I

    const/4 p1, 0x0

    iput p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mDeviceRotation:F

    sget-object p1, Lcom/android/camera/ui/GradienterDrawer$Direct;->NONE:Lcom/android/camera/ui/GradienterDrawer$Direct;

    iput-object p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mCurrentDirect:Lcom/android/camera/ui/GradienterDrawer$Direct;

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 0

    invoke-direct {p0, p1, p2, p3}, Landroid/view/View;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    const/4 p1, 0x1

    iput p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    iput p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    iput-boolean p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mTopVisible:Z

    iput-boolean p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mBottomVisible:Z

    const p1, 0x6affffff

    iput p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mLineColor:I

    const/high16 p1, 0x18000000

    iput p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mFrameColor:I

    const/4 p1, 0x0

    iput p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mDeviceRotation:F

    sget-object p1, Lcom/android/camera/ui/GradienterDrawer$Direct;->NONE:Lcom/android/camera/ui/GradienterDrawer$Direct;

    iput-object p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mCurrentDirect:Lcom/android/camera/ui/GradienterDrawer$Direct;

    return-void
.end method

.method private resetline(Landroid/graphics/Canvas;)V
    .locals 13

    sget-object v0, Lcom/android/camera/ui/ReferenceLineDrawer;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "resetline  rotationOffset : mDeviceRotation :"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mDeviceRotation:F

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0}, Lcom/android/camera/ui/ReferenceLineDrawer;->getWidth()I

    move-result v0

    const/4 v1, 0x1

    sub-int/2addr v0, v1

    invoke-virtual {p0}, Lcom/android/camera/ui/ReferenceLineDrawer;->getHeight()I

    move-result v2

    sub-int/2addr v2, v1

    move v3, v1

    :goto_0
    iget v4, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    const/4 v5, 0x2

    if-ge v3, v4, :cond_2

    iget-boolean v4, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->isGradienterEnabled:Z

    if-eqz v4, :cond_0

    iget-object v4, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mCurrentDirect:Lcom/android/camera/ui/GradienterDrawer$Direct;

    sget-object v6, Lcom/android/camera/ui/GradienterDrawer$Direct;->LEFT:Lcom/android/camera/ui/GradienterDrawer$Direct;

    if-ne v4, v6, :cond_0

    if-ne v3, v5, :cond_0

    new-instance v4, Landroid/graphics/Rect;

    mul-int v5, v3, v0

    iget v6, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v6, v5, v6

    iget v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v7, v5, v7

    add-int/lit8 v7, v7, 0x3

    iget v8, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v8, v2, v8

    sub-int/2addr v8, v1

    invoke-direct {v4, v6, v1, v7, v8}, Landroid/graphics/Rect;-><init>(IIII)V

    iget-object v6, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mFramePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v4, v6}, Landroid/graphics/Canvas;->drawRect(Landroid/graphics/Rect;Landroid/graphics/Paint;)V

    new-instance v4, Landroid/graphics/Rect;

    iget v6, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v6, v5, v6

    iget v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v7, v2, v7

    iget v8, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    sub-int/2addr v8, v1

    mul-int/2addr v7, v8

    sub-int/2addr v7, v1

    iget v8, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int/2addr v5, v8

    add-int/lit8 v5, v5, 0x3

    add-int/lit8 v8, v2, -0x1

    invoke-direct {v4, v6, v7, v5, v8}, Landroid/graphics/Rect;-><init>(IIII)V

    iget-object v5, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mFramePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v4, v5}, Landroid/graphics/Canvas;->drawRect(Landroid/graphics/Rect;Landroid/graphics/Paint;)V

    goto :goto_1

    :cond_0
    iget-boolean v4, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->isGradienterEnabled:Z

    if-eqz v4, :cond_1

    iget-object v4, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mCurrentDirect:Lcom/android/camera/ui/GradienterDrawer$Direct;

    sget-object v5, Lcom/android/camera/ui/GradienterDrawer$Direct;->RIGHT:Lcom/android/camera/ui/GradienterDrawer$Direct;

    if-ne v4, v5, :cond_1

    if-ne v3, v1, :cond_1

    new-instance v4, Landroid/graphics/Rect;

    mul-int v5, v3, v0

    iget v6, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v6, v5, v6

    iget v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v7, v5, v7

    add-int/lit8 v7, v7, 0x3

    iget v8, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v8, v2, v8

    sub-int/2addr v8, v1

    invoke-direct {v4, v6, v1, v7, v8}, Landroid/graphics/Rect;-><init>(IIII)V

    iget-object v6, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mFramePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v4, v6}, Landroid/graphics/Canvas;->drawRect(Landroid/graphics/Rect;Landroid/graphics/Paint;)V

    new-instance v4, Landroid/graphics/Rect;

    iget v6, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v6, v5, v6

    iget v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v7, v2, v7

    iget v8, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    sub-int/2addr v8, v1

    mul-int/2addr v7, v8

    sub-int/2addr v7, v1

    iget v8, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int/2addr v5, v8

    add-int/lit8 v5, v5, 0x3

    add-int/lit8 v8, v2, -0x1

    invoke-direct {v4, v6, v7, v5, v8}, Landroid/graphics/Rect;-><init>(IIII)V

    iget-object v5, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mFramePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v4, v5}, Landroid/graphics/Canvas;->drawRect(Landroid/graphics/Rect;Landroid/graphics/Paint;)V

    goto :goto_1

    :cond_1
    new-instance v4, Landroid/graphics/Rect;

    mul-int v5, v3, v0

    iget v6, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v6, v5, v6

    iget v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int/2addr v5, v7

    add-int/lit8 v5, v5, 0x3

    add-int/lit8 v7, v2, -0x1

    invoke-direct {v4, v6, v1, v5, v7}, Landroid/graphics/Rect;-><init>(IIII)V

    iget-object v5, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mFramePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v4, v5}, Landroid/graphics/Canvas;->drawRect(Landroid/graphics/Rect;Landroid/graphics/Paint;)V

    :goto_1
    add-int/lit8 v3, v3, 0x1

    goto/16 :goto_0

    :cond_2
    iget-boolean v3, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mBottomVisible:Z

    xor-int/2addr v3, v1

    const/4 v4, 0x0

    move v6, v4

    :goto_2
    iget v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    if-gt v6, v7, :cond_9

    if-eqz v6, :cond_3

    iget v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    if-ne v6, v7, :cond_5

    :cond_3
    if-nez v6, :cond_4

    iget-boolean v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mTopVisible:Z

    if-nez v7, :cond_5

    :cond_4
    iget v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    if-ne v6, v7, :cond_8

    iget-boolean v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mBottomVisible:Z

    if-eqz v7, :cond_8

    :cond_5
    iget-boolean v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->isGradienterEnabled:Z

    if-eqz v7, :cond_6

    iget-object v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mCurrentDirect:Lcom/android/camera/ui/GradienterDrawer$Direct;

    sget-object v8, Lcom/android/camera/ui/GradienterDrawer$Direct;->BOTTOM:Lcom/android/camera/ui/GradienterDrawer$Direct;

    if-ne v7, v8, :cond_6

    if-ne v6, v1, :cond_6

    new-instance v7, Landroid/graphics/Rect;

    mul-int v8, v6, v2

    iget v9, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v9, v8, v9

    iget v10, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v10, v0, v10

    sub-int/2addr v10, v3

    iget v11, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v11, v8, v11

    add-int/lit8 v11, v11, 0x3

    invoke-direct {v7, v3, v9, v10, v11}, Landroid/graphics/Rect;-><init>(IIII)V

    iget-object v9, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mFramePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v7, v9}, Landroid/graphics/Canvas;->drawRect(Landroid/graphics/Rect;Landroid/graphics/Paint;)V

    new-instance v7, Landroid/graphics/Rect;

    iget v9, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v9, v0, v9

    iget v10, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    sub-int/2addr v10, v1

    mul-int/2addr v9, v10

    sub-int/2addr v9, v3

    iget v10, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v10, v8, v10

    sub-int v11, v0, v3

    iget v12, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int/2addr v8, v12

    add-int/lit8 v8, v8, 0x3

    invoke-direct {v7, v9, v10, v11, v8}, Landroid/graphics/Rect;-><init>(IIII)V

    iget-object v8, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mFramePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v7, v8}, Landroid/graphics/Canvas;->drawRect(Landroid/graphics/Rect;Landroid/graphics/Paint;)V

    goto :goto_3

    :cond_6
    iget-boolean v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->isGradienterEnabled:Z

    if-eqz v7, :cond_7

    iget-object v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mCurrentDirect:Lcom/android/camera/ui/GradienterDrawer$Direct;

    sget-object v8, Lcom/android/camera/ui/GradienterDrawer$Direct;->TOP:Lcom/android/camera/ui/GradienterDrawer$Direct;

    if-ne v7, v8, :cond_7

    if-ne v6, v5, :cond_7

    new-instance v7, Landroid/graphics/Rect;

    mul-int v8, v6, v2

    iget v9, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v9, v8, v9

    iget v10, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v10, v0, v10

    sub-int/2addr v10, v3

    iget v11, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v11, v8, v11

    add-int/lit8 v11, v11, 0x3

    invoke-direct {v7, v3, v9, v10, v11}, Landroid/graphics/Rect;-><init>(IIII)V

    iget-object v9, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mFramePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v7, v9}, Landroid/graphics/Canvas;->drawRect(Landroid/graphics/Rect;Landroid/graphics/Paint;)V

    new-instance v7, Landroid/graphics/Rect;

    iget v9, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v9, v0, v9

    iget v10, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    sub-int/2addr v10, v1

    mul-int/2addr v9, v10

    sub-int/2addr v9, v3

    iget v10, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v10, v8, v10

    sub-int v11, v0, v3

    iget v12, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int/2addr v8, v12

    add-int/lit8 v8, v8, 0x3

    invoke-direct {v7, v9, v10, v11, v8}, Landroid/graphics/Rect;-><init>(IIII)V

    iget-object v8, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mFramePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v7, v8}, Landroid/graphics/Canvas;->drawRect(Landroid/graphics/Rect;Landroid/graphics/Paint;)V

    goto :goto_3

    :cond_7
    new-instance v7, Landroid/graphics/Rect;

    mul-int v8, v6, v2

    iget v9, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v9, v8, v9

    sub-int v10, v0, v3

    iget v11, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int/2addr v8, v11

    add-int/lit8 v8, v8, 0x3

    invoke-direct {v7, v3, v9, v10, v8}, Landroid/graphics/Rect;-><init>(IIII)V

    iget-object v8, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mFramePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v7, v8}, Landroid/graphics/Canvas;->drawRect(Landroid/graphics/Rect;Landroid/graphics/Paint;)V

    :cond_8
    :goto_3
    add-int/lit8 v6, v6, 0x1

    goto/16 :goto_2

    :cond_9
    move v6, v1

    :goto_4
    iget v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    if-ge v6, v7, :cond_c

    iget-boolean v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->isGradienterEnabled:Z

    if-eqz v7, :cond_a

    iget-object v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mCurrentDirect:Lcom/android/camera/ui/GradienterDrawer$Direct;

    sget-object v8, Lcom/android/camera/ui/GradienterDrawer$Direct;->RIGHT:Lcom/android/camera/ui/GradienterDrawer$Direct;

    if-ne v7, v8, :cond_a

    if-ne v6, v1, :cond_a

    new-instance v7, Landroid/graphics/Rect;

    mul-int v8, v6, v0

    iget v9, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v9, v8, v9

    iget v10, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v10, v8, v10

    add-int/2addr v10, v5

    iget v11, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v11, v2, v11

    sub-int/2addr v11, v1

    invoke-direct {v7, v9, v1, v10, v11}, Landroid/graphics/Rect;-><init>(IIII)V

    iget-object v9, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mLinePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v7, v9}, Landroid/graphics/Canvas;->drawRect(Landroid/graphics/Rect;Landroid/graphics/Paint;)V

    new-instance v7, Landroid/graphics/Rect;

    iget v9, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v9, v8, v9

    iget v10, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v10, v2, v10

    iget v11, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    sub-int/2addr v11, v1

    mul-int/2addr v10, v11

    sub-int/2addr v10, v1

    iget v11, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int/2addr v8, v11

    add-int/2addr v8, v5

    add-int/lit8 v11, v2, -0x1

    invoke-direct {v7, v9, v10, v8, v11}, Landroid/graphics/Rect;-><init>(IIII)V

    iget-object v8, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mLinePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v7, v8}, Landroid/graphics/Canvas;->drawRect(Landroid/graphics/Rect;Landroid/graphics/Paint;)V

    goto :goto_5

    :cond_a
    iget-boolean v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->isGradienterEnabled:Z

    if-eqz v7, :cond_b

    iget-object v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mCurrentDirect:Lcom/android/camera/ui/GradienterDrawer$Direct;

    sget-object v8, Lcom/android/camera/ui/GradienterDrawer$Direct;->LEFT:Lcom/android/camera/ui/GradienterDrawer$Direct;

    if-ne v7, v8, :cond_b

    if-ne v6, v5, :cond_b

    new-instance v7, Landroid/graphics/Rect;

    mul-int v8, v6, v0

    iget v9, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v9, v8, v9

    iget v10, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v10, v8, v10

    add-int/2addr v10, v5

    iget v11, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v11, v2, v11

    sub-int/2addr v11, v1

    invoke-direct {v7, v9, v1, v10, v11}, Landroid/graphics/Rect;-><init>(IIII)V

    iget-object v9, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mLinePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v7, v9}, Landroid/graphics/Canvas;->drawRect(Landroid/graphics/Rect;Landroid/graphics/Paint;)V

    new-instance v7, Landroid/graphics/Rect;

    iget v9, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v9, v8, v9

    iget v10, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v10, v2, v10

    iget v11, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    sub-int/2addr v11, v1

    mul-int/2addr v10, v11

    sub-int/2addr v10, v1

    iget v11, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int/2addr v8, v11

    add-int/2addr v8, v5

    add-int/lit8 v11, v2, -0x1

    invoke-direct {v7, v9, v10, v8, v11}, Landroid/graphics/Rect;-><init>(IIII)V

    iget-object v8, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mLinePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v7, v8}, Landroid/graphics/Canvas;->drawRect(Landroid/graphics/Rect;Landroid/graphics/Paint;)V

    goto :goto_5

    :cond_b
    new-instance v7, Landroid/graphics/Rect;

    mul-int v8, v6, v0

    iget v9, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v9, v8, v9

    iget v10, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int/2addr v8, v10

    add-int/2addr v8, v5

    add-int/lit8 v10, v2, -0x1

    invoke-direct {v7, v9, v1, v8, v10}, Landroid/graphics/Rect;-><init>(IIII)V

    iget-object v8, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mLinePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v7, v8}, Landroid/graphics/Canvas;->drawRect(Landroid/graphics/Rect;Landroid/graphics/Paint;)V

    :goto_5
    add-int/lit8 v6, v6, 0x1

    goto/16 :goto_4

    :cond_c
    :goto_6
    iget v6, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    if-gt v4, v6, :cond_13

    if-eqz v4, :cond_d

    iget v6, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    if-ne v4, v6, :cond_f

    :cond_d
    if-nez v4, :cond_e

    iget-boolean v6, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mTopVisible:Z

    if-nez v6, :cond_f

    :cond_e
    iget v6, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    if-ne v4, v6, :cond_12

    iget-boolean v6, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mBottomVisible:Z

    if-eqz v6, :cond_12

    :cond_f
    iget-boolean v6, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->isGradienterEnabled:Z

    if-eqz v6, :cond_10

    iget-object v6, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mCurrentDirect:Lcom/android/camera/ui/GradienterDrawer$Direct;

    sget-object v7, Lcom/android/camera/ui/GradienterDrawer$Direct;->BOTTOM:Lcom/android/camera/ui/GradienterDrawer$Direct;

    if-ne v6, v7, :cond_10

    if-ne v4, v1, :cond_10

    new-instance v6, Landroid/graphics/Rect;

    mul-int v7, v4, v2

    iget v8, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v8, v7, v8

    iget v9, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v9, v0, v9

    sub-int/2addr v9, v3

    iget v10, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v10, v7, v10

    add-int/2addr v10, v5

    invoke-direct {v6, v3, v8, v9, v10}, Landroid/graphics/Rect;-><init>(IIII)V

    iget-object v8, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mLinePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v6, v8}, Landroid/graphics/Canvas;->drawRect(Landroid/graphics/Rect;Landroid/graphics/Paint;)V

    new-instance v6, Landroid/graphics/Rect;

    iget v8, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v8, v0, v8

    iget v9, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    sub-int/2addr v9, v1

    mul-int/2addr v8, v9

    sub-int/2addr v8, v3

    iget v9, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v9, v7, v9

    sub-int v10, v0, v3

    iget v11, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int/2addr v7, v11

    add-int/2addr v7, v5

    invoke-direct {v6, v8, v9, v10, v7}, Landroid/graphics/Rect;-><init>(IIII)V

    iget-object v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mLinePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v6, v7}, Landroid/graphics/Canvas;->drawRect(Landroid/graphics/Rect;Landroid/graphics/Paint;)V

    goto :goto_7

    :cond_10
    iget-boolean v6, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->isGradienterEnabled:Z

    if-eqz v6, :cond_11

    iget-object v6, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mCurrentDirect:Lcom/android/camera/ui/GradienterDrawer$Direct;

    sget-object v7, Lcom/android/camera/ui/GradienterDrawer$Direct;->TOP:Lcom/android/camera/ui/GradienterDrawer$Direct;

    if-ne v6, v7, :cond_11

    if-ne v4, v5, :cond_11

    new-instance v6, Landroid/graphics/Rect;

    mul-int v7, v4, v2

    iget v8, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v8, v7, v8

    iget v9, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v9, v0, v9

    sub-int/2addr v9, v3

    iget v10, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v10, v7, v10

    add-int/2addr v10, v5

    invoke-direct {v6, v3, v8, v9, v10}, Landroid/graphics/Rect;-><init>(IIII)V

    iget-object v8, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mLinePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v6, v8}, Landroid/graphics/Canvas;->drawRect(Landroid/graphics/Rect;Landroid/graphics/Paint;)V

    new-instance v6, Landroid/graphics/Rect;

    iget v8, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    div-int v8, v0, v8

    iget v9, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    sub-int/2addr v9, v1

    mul-int/2addr v8, v9

    sub-int/2addr v8, v3

    iget v9, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v9, v7, v9

    sub-int v10, v0, v3

    iget v11, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int/2addr v7, v11

    add-int/2addr v7, v5

    invoke-direct {v6, v8, v9, v10, v7}, Landroid/graphics/Rect;-><init>(IIII)V

    iget-object v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mLinePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v6, v7}, Landroid/graphics/Canvas;->drawRect(Landroid/graphics/Rect;Landroid/graphics/Paint;)V

    goto :goto_7

    :cond_11
    new-instance v6, Landroid/graphics/Rect;

    mul-int v7, v4, v2

    iget v8, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int v8, v7, v8

    sub-int v9, v0, v3

    iget v10, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    div-int/2addr v7, v10

    add-int/2addr v7, v5

    invoke-direct {v6, v3, v8, v9, v7}, Landroid/graphics/Rect;-><init>(IIII)V

    iget-object v7, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mLinePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v6, v7}, Landroid/graphics/Canvas;->drawRect(Landroid/graphics/Rect;Landroid/graphics/Paint;)V

    :cond_12
    :goto_7
    add-int/lit8 v4, v4, 0x1

    goto/16 :goto_6

    :cond_13
    return-void
.end method

.method private updateView(Landroid/graphics/Canvas;)V
    .locals 3

    invoke-static {}, Lcom/android/camera/effect/EffectController;->getInstance()Lcom/android/camera/effect/EffectController;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/effect/EffectController;->getDeviceRotation()F

    move-result v0

    iput v0, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mDeviceRotation:F

    nop

    nop

    iget v0, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mDeviceRotation:F

    const/high16 v1, 0x42340000    # 45.0f

    cmpl-float v0, v0, v1

    const/high16 v1, 0x43070000    # 135.0f

    if-lez v0, :cond_0

    iget v0, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mDeviceRotation:F

    cmpg-float v0, v0, v1

    if-gez v0, :cond_0

    sget-object v0, Lcom/android/camera/ui/GradienterDrawer$Direct;->RIGHT:Lcom/android/camera/ui/GradienterDrawer$Direct;

    iget v1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mDeviceRotation:F

    const/high16 v2, 0x42b40000    # 90.0f

    sub-float/2addr v1, v2

    goto :goto_0

    :cond_0
    iget v0, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mDeviceRotation:F

    cmpl-float v0, v0, v1

    const/high16 v1, 0x43610000    # 225.0f

    if-ltz v0, :cond_1

    iget v0, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mDeviceRotation:F

    cmpg-float v0, v0, v1

    if-gez v0, :cond_1

    sget-object v0, Lcom/android/camera/ui/GradienterDrawer$Direct;->TOP:Lcom/android/camera/ui/GradienterDrawer$Direct;

    iget v1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mDeviceRotation:F

    const/high16 v2, 0x43340000    # 180.0f

    sub-float/2addr v1, v2

    goto :goto_0

    :cond_1
    iget v0, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mDeviceRotation:F

    cmpl-float v0, v0, v1

    if-lez v0, :cond_2

    iget v0, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mDeviceRotation:F

    const v1, 0x439d8000    # 315.0f

    cmpg-float v0, v0, v1

    if-gez v0, :cond_2

    sget-object v0, Lcom/android/camera/ui/GradienterDrawer$Direct;->LEFT:Lcom/android/camera/ui/GradienterDrawer$Direct;

    iget v1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mDeviceRotation:F

    const/high16 v2, 0x43870000    # 270.0f

    sub-float/2addr v1, v2

    goto :goto_0

    :cond_2
    sget-object v0, Lcom/android/camera/ui/GradienterDrawer$Direct;->BOTTOM:Lcom/android/camera/ui/GradienterDrawer$Direct;

    iget v1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mDeviceRotation:F

    const/high16 v2, 0x43960000    # 300.0f

    cmpl-float v1, v1, v2

    if-lez v1, :cond_3

    iget v1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mDeviceRotation:F

    const/high16 v2, 0x43b40000    # 360.0f

    sub-float/2addr v1, v2

    goto :goto_0

    :cond_3
    iget v1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mDeviceRotation:F

    :goto_0
    iget-object v2, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mCurrentDirect:Lcom/android/camera/ui/GradienterDrawer$Direct;

    if-eq v0, v2, :cond_4

    iput-object v0, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mCurrentDirect:Lcom/android/camera/ui/GradienterDrawer$Direct;

    :cond_4
    invoke-direct {p0, p1}, Lcom/android/camera/ui/ReferenceLineDrawer;->resetline(Landroid/graphics/Canvas;)V

    sget-object p1, Lcom/android/camera/ui/ReferenceLineDrawer;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v2, "updateView  rotationOffset : "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string v1, ";  mDeviceRotation :"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mDeviceRotation:F

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0}, Lcom/android/camera/ui/ReferenceLineDrawer;->invalidate()V

    return-void
.end method


# virtual methods
.method public initialize(II)V
    .locals 1

    const/4 v0, 0x1

    invoke-static {p2, v0}, Ljava/lang/Math;->max(II)I

    move-result p2

    iput p2, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mColumnCount:I

    invoke-static {p1, v0}, Ljava/lang/Math;->max(II)I

    move-result p1

    iput p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mRowCount:I

    new-instance p1, Landroid/graphics/Paint;

    invoke-direct {p1}, Landroid/graphics/Paint;-><init>()V

    iput-object p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mLinePaint:Landroid/graphics/Paint;

    new-instance p1, Landroid/graphics/Paint;

    invoke-direct {p1}, Landroid/graphics/Paint;-><init>()V

    iput-object p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mFramePaint:Landroid/graphics/Paint;

    iget-object p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mLinePaint:Landroid/graphics/Paint;

    const/high16 p2, 0x3f800000    # 1.0f

    invoke-virtual {p1, p2}, Landroid/graphics/Paint;->setStrokeWidth(F)V

    iget-object p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mFramePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, p2}, Landroid/graphics/Paint;->setStrokeWidth(F)V

    iget-object p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mLinePaint:Landroid/graphics/Paint;

    sget-object p2, Landroid/graphics/Paint$Style;->FILL:Landroid/graphics/Paint$Style;

    invoke-virtual {p1, p2}, Landroid/graphics/Paint;->setStyle(Landroid/graphics/Paint$Style;)V

    iget-object p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mFramePaint:Landroid/graphics/Paint;

    sget-object p2, Landroid/graphics/Paint$Style;->STROKE:Landroid/graphics/Paint$Style;

    invoke-virtual {p1, p2}, Landroid/graphics/Paint;->setStyle(Landroid/graphics/Paint$Style;)V

    iget-object p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mLinePaint:Landroid/graphics/Paint;

    iget p2, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mLineColor:I

    invoke-virtual {p1, p2}, Landroid/graphics/Paint;->setColor(I)V

    iget-object p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mFramePaint:Landroid/graphics/Paint;

    iget p2, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mFrameColor:I

    invoke-virtual {p1, p2}, Landroid/graphics/Paint;->setColor(I)V

    return-void
.end method

.method protected onDraw(Landroid/graphics/Canvas;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/ui/ReferenceLineDrawer;->updateView(Landroid/graphics/Canvas;)V

    invoke-super {p0, p1}, Landroid/view/View;->onDraw(Landroid/graphics/Canvas;)V

    return-void
.end method

.method protected onVisibilityChanged(Landroid/view/View;I)V
    .locals 0

    invoke-super {p0, p1, p2}, Landroid/view/View;->onVisibilityChanged(Landroid/view/View;I)V

    if-nez p2, :cond_0

    sget-object p1, Lcom/android/camera/ui/GradienterDrawer$Direct;->NONE:Lcom/android/camera/ui/GradienterDrawer$Direct;

    iput-object p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mCurrentDirect:Lcom/android/camera/ui/GradienterDrawer$Direct;

    :cond_0
    return-void
.end method

.method public setBorderVisible(ZZ)V
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mTopVisible:Z

    if-ne v0, p1, :cond_0

    iget-boolean v0, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mBottomVisible:Z

    if-eq v0, p2, :cond_1

    :cond_0
    iput-boolean p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mTopVisible:Z

    iput-boolean p2, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mBottomVisible:Z

    invoke-virtual {p0}, Lcom/android/camera/ui/ReferenceLineDrawer;->invalidate()V

    :cond_1
    return-void
.end method

.method public setGradienterEnabled(Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->isGradienterEnabled:Z

    invoke-virtual {p0}, Lcom/android/camera/ui/ReferenceLineDrawer;->getVisibility()I

    move-result p1

    if-nez p1, :cond_0

    sget-object p1, Lcom/android/camera/ui/GradienterDrawer$Direct;->NONE:Lcom/android/camera/ui/GradienterDrawer$Direct;

    iput-object p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mCurrentDirect:Lcom/android/camera/ui/GradienterDrawer$Direct;

    invoke-virtual {p0}, Lcom/android/camera/ui/ReferenceLineDrawer;->invalidate()V

    :cond_0
    return-void
.end method

.method public setLineColor(I)V
    .locals 0

    iput p1, p0, Lcom/android/camera/ui/ReferenceLineDrawer;->mLineColor:I

    return-void
.end method
