.class public Lcom/android/camera/fragment/subtitle/SoundWaveView;
.super Landroid/view/View;
.source "SoundWaveView.java"


# static fields
.field private static final COUNT_LINE:I = 0x5

.field private static final DEFAULT_DEGREES:I = 0x0

.field private static final INVALIDATE_SOUND_VIEW_MSG:I = 0x64


# instance fields
.field private LINE_SPACE:F

.field private LINE_WIDTH:F

.field private curHeights:[F

.field private handler:Landroid/os/Handler;
    .annotation build Landroid/annotation/SuppressLint;
        value = {
            "HandlerLeak"
        }
    .end annotation
.end field

.field private intervalHeights:[F

.field private isAnimationProcessing:Z

.field private isElongation:[Z

.field private mDegrees:I

.field private mMaxLineHeights:[F

.field private mMinLineHeights:[F

.field private mPaint:Landroid/graphics/Paint;

.field private mRect:Landroid/graphics/RectF;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 2

    const/4 v0, 0x0

    const/4 v1, -0x1

    invoke-direct {p0, p1, v0, v1}, Lcom/android/camera/fragment/subtitle/SoundWaveView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 1

    const/4 v0, -0x1

    invoke-direct {p0, p1, p2, v0}, Lcom/android/camera/fragment/subtitle/SoundWaveView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    sget-object v0, Lcom/android/camera/R$styleable;->SoundWaveView:[I

    invoke-virtual {p1, p2, v0}, Landroid/content/Context;->obtainStyledAttributes(Landroid/util/AttributeSet;[I)Landroid/content/res/TypedArray;

    move-result-object p1

    const/4 p2, 0x0

    invoke-virtual {p1, p2, p2}, Landroid/content/res/TypedArray;->getInt(II)I

    move-result p2

    iput p2, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mDegrees:I

    invoke-virtual {p1}, Landroid/content/res/TypedArray;->recycle()V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 0

    invoke-direct {p0, p1, p2, p3}, Landroid/view/View;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    const/4 p1, 0x0

    iput-boolean p1, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->isAnimationProcessing:Z

    const/4 p1, 0x5

    new-array p2, p1, [F

    fill-array-data p2, :array_0

    iput-object p2, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mMaxLineHeights:[F

    new-array p2, p1, [F

    fill-array-data p2, :array_1

    iput-object p2, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mMinLineHeights:[F

    new-array p2, p1, [F

    fill-array-data p2, :array_2

    iput-object p2, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->intervalHeights:[F

    new-array p2, p1, [F

    fill-array-data p2, :array_3

    iput-object p2, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->curHeights:[F

    new-array p1, p1, [Z

    fill-array-data p1, :array_4

    iput-object p1, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->isElongation:[Z

    new-instance p1, Lcom/android/camera/fragment/subtitle/SoundWaveView$1;

    invoke-direct {p1, p0}, Lcom/android/camera/fragment/subtitle/SoundWaveView$1;-><init>(Lcom/android/camera/fragment/subtitle/SoundWaveView;)V

    iput-object p1, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->handler:Landroid/os/Handler;

    invoke-direct {p0}, Lcom/android/camera/fragment/subtitle/SoundWaveView;->init()V

    return-void

    nop

    :array_0
    .array-data 4
        0x41b00000    # 22.0f
        0x41800000    # 16.0f
        0x41c00000    # 24.0f
        0x41800000    # 16.0f
        0x41b00000    # 22.0f
    .end array-data

    :array_1
    .array-data 4
        0x41000000    # 8.0f
        0x41600000    # 14.0f
        0x41000000    # 8.0f
        0x41600000    # 14.0f
        0x41000000    # 8.0f
    .end array-data

    :array_2
    .array-data 4
        0x3f333333    # 0.7f
        0x3dcccccd    # 0.1f
        0x3f4ccccd    # 0.8f
        0x3dcccccd    # 0.1f
        0x3f333333    # 0.7f
    .end array-data

    :array_3
    .array-data 4
        0x41000000    # 8.0f
        0x41800000    # 16.0f
        0x41c00000    # 24.0f
        0x41800000    # 16.0f
        0x41000000    # 8.0f
    .end array-data

    :array_4
    .array-data 1
        0x1t
        0x1t
        0x1t
        0x1t
        0x1t
    .end array-data
.end method

.method static synthetic access$000(Lcom/android/camera/fragment/subtitle/SoundWaveView;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->isAnimationProcessing:Z

    return p0
.end method

.method static synthetic access$100(Lcom/android/camera/fragment/subtitle/SoundWaveView;)[F
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->curHeights:[F

    return-object p0
.end method

.method static synthetic access$200(Lcom/android/camera/fragment/subtitle/SoundWaveView;)[F
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mMinLineHeights:[F

    return-object p0
.end method

.method static synthetic access$300(Lcom/android/camera/fragment/subtitle/SoundWaveView;)Landroid/os/Handler;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->handler:Landroid/os/Handler;

    return-object p0
.end method

.method private init()V
    .locals 3

    const/high16 v0, 0x3f800000    # 1.0f

    iput v0, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->LINE_WIDTH:F

    const/high16 v0, 0x41000000    # 8.0f

    iput v0, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->LINE_SPACE:F

    new-instance v0, Landroid/graphics/Paint;

    const/4 v1, 0x1

    invoke-direct {v0, v1}, Landroid/graphics/Paint;-><init>(I)V

    iput-object v0, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mPaint:Landroid/graphics/Paint;

    iget-object v0, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mPaint:Landroid/graphics/Paint;

    const/4 v2, -0x1

    invoke-virtual {v0, v2}, Landroid/graphics/Paint;->setColor(I)V

    iget-object v0, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mPaint:Landroid/graphics/Paint;

    iget v2, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->LINE_WIDTH:F

    invoke-virtual {v0, v2}, Landroid/graphics/Paint;->setStrokeWidth(F)V

    iget-object v0, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mPaint:Landroid/graphics/Paint;

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setAntiAlias(Z)V

    iget-object v0, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mPaint:Landroid/graphics/Paint;

    sget-object v1, Landroid/graphics/Paint$Style;->FILL:Landroid/graphics/Paint$Style;

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setStyle(Landroid/graphics/Paint$Style;)V

    new-instance v0, Landroid/graphics/RectF;

    invoke-direct {v0}, Landroid/graphics/RectF;-><init>()V

    iput-object v0, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mRect:Landroid/graphics/RectF;

    return-void
.end method


# virtual methods
.method protected onDraw(Landroid/graphics/Canvas;)V
    .locals 9

    invoke-super {p0, p1}, Landroid/view/View;->onDraw(Landroid/graphics/Canvas;)V

    iget v0, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->LINE_WIDTH:F

    const/high16 v1, 0x40a00000    # 5.0f

    add-float/2addr v0, v1

    iget v2, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->LINE_SPACE:F

    add-float/2addr v0, v2

    iget-object v2, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mRect:Landroid/graphics/RectF;

    if-nez v2, :cond_0

    new-instance v2, Landroid/graphics/RectF;

    invoke-direct {v2}, Landroid/graphics/RectF;-><init>()V

    iput-object v2, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mRect:Landroid/graphics/RectF;

    :cond_0
    const/4 v2, 0x0

    move v3, v2

    :goto_0
    const/4 v4, 0x5

    if-ge v3, v4, :cond_5

    iget v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mDegrees:I

    const/16 v5, 0x5a

    const/high16 v6, 0x41700000    # 15.0f

    const/high16 v7, 0x40000000    # 2.0f

    if-eq v4, v5, :cond_2

    iget v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mDegrees:I

    const/16 v5, 0x10e

    if-ne v4, v5, :cond_1

    goto :goto_1

    :cond_1
    invoke-virtual {p0}, Lcom/android/camera/fragment/subtitle/SoundWaveView;->getWidth()I

    move-result v4

    int-to-float v4, v4

    mul-float v5, v0, v1

    sub-float/2addr v4, v5

    iget v5, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->LINE_SPACE:F

    sub-float/2addr v4, v5

    div-float/2addr v4, v7

    invoke-virtual {p0}, Lcom/android/camera/fragment/subtitle/SoundWaveView;->getHeight()I

    move-result v5

    int-to-float v5, v5

    div-float/2addr v5, v7

    int-to-float v7, v3

    mul-float/2addr v7, v0

    add-float/2addr v4, v7

    add-float/2addr v4, v0

    iget-object v7, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mRect:Landroid/graphics/RectF;

    iput v4, v7, Landroid/graphics/RectF;->left:F

    iget-object v7, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mRect:Landroid/graphics/RectF;

    iget-object v8, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->curHeights:[F

    aget v8, v8, v3

    add-float/2addr v8, v5

    iput v8, v7, Landroid/graphics/RectF;->top:F

    iget-object v7, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mRect:Landroid/graphics/RectF;

    add-float/2addr v4, v1

    iput v4, v7, Landroid/graphics/RectF;->right:F

    iget-object v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mRect:Landroid/graphics/RectF;

    iget-object v7, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->curHeights:[F

    aget v7, v7, v3

    sub-float/2addr v5, v7

    iput v5, v4, Landroid/graphics/RectF;->bottom:F

    iget-object v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mRect:Landroid/graphics/RectF;

    iget-object v5, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mPaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v4, v6, v6, v5}, Landroid/graphics/Canvas;->drawRoundRect(Landroid/graphics/RectF;FFLandroid/graphics/Paint;)V

    goto :goto_2

    :cond_2
    :goto_1
    invoke-virtual {p0}, Lcom/android/camera/fragment/subtitle/SoundWaveView;->getHeight()I

    move-result v4

    int-to-float v4, v4

    mul-float v5, v0, v1

    sub-float/2addr v4, v5

    iget v5, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->LINE_SPACE:F

    sub-float/2addr v4, v5

    div-float/2addr v4, v7

    invoke-virtual {p0}, Lcom/android/camera/fragment/subtitle/SoundWaveView;->getWidth()I

    move-result v5

    int-to-float v5, v5

    div-float/2addr v5, v7

    int-to-float v7, v3

    mul-float/2addr v7, v0

    add-float/2addr v4, v7

    add-float/2addr v4, v0

    iget-object v7, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mRect:Landroid/graphics/RectF;

    iget-object v8, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->curHeights:[F

    aget v8, v8, v3

    sub-float v8, v5, v8

    iput v8, v7, Landroid/graphics/RectF;->left:F

    iget-object v7, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mRect:Landroid/graphics/RectF;

    iput v4, v7, Landroid/graphics/RectF;->top:F

    iget-object v7, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mRect:Landroid/graphics/RectF;

    iget-object v8, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->curHeights:[F

    aget v8, v8, v3

    add-float/2addr v5, v8

    iput v5, v7, Landroid/graphics/RectF;->right:F

    iget-object v5, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mRect:Landroid/graphics/RectF;

    add-float/2addr v4, v1

    iput v4, v5, Landroid/graphics/RectF;->bottom:F

    iget-object v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mRect:Landroid/graphics/RectF;

    iget-object v5, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mPaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v4, v6, v6, v5}, Landroid/graphics/Canvas;->drawRoundRect(Landroid/graphics/RectF;FFLandroid/graphics/Paint;)V

    nop

    :goto_2
    iget-object v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->isElongation:[Z

    aget-boolean v4, v4, v3

    const/4 v5, 0x1

    if-eqz v4, :cond_3

    iget-object v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->curHeights:[F

    aget v6, v4, v3

    iget-object v7, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->intervalHeights:[F

    aget v7, v7, v3

    add-float/2addr v6, v7

    aput v6, v4, v3

    iget-object v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->curHeights:[F

    aget v4, v4, v3

    iget-object v6, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mMaxLineHeights:[F

    aget v6, v6, v3

    cmpl-float v4, v4, v6

    if-ltz v4, :cond_4

    iget-object v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->isElongation:[Z

    aput-boolean v2, v4, v3

    iget-object v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->curHeights:[F

    iget-object v6, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mMaxLineHeights:[F

    aget v6, v6, v3

    aput v6, v4, v3

    if-nez v3, :cond_4

    iget-object v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->isElongation:[Z

    add-int/lit8 v6, v3, 0x1

    aput-boolean v5, v4, v6

    iget-object v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->isElongation:[Z

    add-int/lit8 v7, v3, 0x2

    aput-boolean v5, v4, v7

    iget-object v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->curHeights:[F

    iget-object v5, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mMinLineHeights:[F

    aget v5, v5, v6

    aput v5, v4, v7

    iget-object v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->curHeights:[F

    iget-object v5, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mMinLineHeights:[F

    aget v5, v5, v7

    aput v5, v4, v7

    goto :goto_3

    :cond_3
    iget-object v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->curHeights:[F

    aget v6, v4, v3

    iget-object v7, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->intervalHeights:[F

    aget v7, v7, v3

    sub-float/2addr v6, v7

    aput v6, v4, v3

    iget-object v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->curHeights:[F

    aget v4, v4, v3

    iget-object v6, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mMinLineHeights:[F

    aget v6, v6, v3

    cmpg-float v4, v4, v6

    if-gtz v4, :cond_4

    iget-object v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->curHeights:[F

    iget-object v6, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mMinLineHeights:[F

    aget v6, v6, v3

    aput v6, v4, v3

    iget-object v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->isElongation:[Z

    aput-boolean v5, v4, v3

    if-nez v3, :cond_4

    iget-object v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->isElongation:[Z

    add-int/lit8 v5, v3, 0x1

    aput-boolean v2, v4, v5

    iget-object v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->isElongation:[Z

    add-int/lit8 v6, v3, 0x2

    aput-boolean v2, v4, v6

    iget-object v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->curHeights:[F

    iget-object v7, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mMaxLineHeights:[F

    aget v7, v7, v5

    aput v7, v4, v5

    iget-object v4, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->curHeights:[F

    iget-object v5, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mMaxLineHeights:[F

    aget v5, v5, v6

    aput v5, v4, v6

    :cond_4
    :goto_3
    add-int/lit8 v3, v3, 0x1

    goto/16 :goto_0

    :cond_5
    return-void
.end method

.method protected onMeasure(II)V
    .locals 0

    invoke-super {p0, p1, p2}, Landroid/view/View;->onMeasure(II)V

    return-void
.end method

.method public resetAnimation()V
    .locals 1

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->isAnimationProcessing:Z

    return-void
.end method

.method public startAnimation()V
    .locals 3

    iget-boolean v0, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->isAnimationProcessing:Z

    if-eqz v0, :cond_0

    return-void

    :cond_0
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->isAnimationProcessing:Z

    iget-object v0, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->curHeights:[F

    const/4 v1, 0x0

    aget v0, v0, v1

    iget-object v2, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->mMinLineHeights:[F

    aget v1, v2, v1

    cmpg-float v0, v0, v1

    if-gtz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->handler:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    iget-object v0, p0, Lcom/android/camera/fragment/subtitle/SoundWaveView;->handler:Landroid/os/Handler;

    const/16 v1, 0x64

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    :cond_1
    return-void
.end method
