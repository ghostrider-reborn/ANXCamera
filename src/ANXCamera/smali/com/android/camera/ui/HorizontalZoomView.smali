.class public Lcom/android/camera/ui/HorizontalZoomView;
.super Lcom/android/camera/ui/BaseHorizontalZoomView;
.source "HorizontalZoomView.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "HorizontalZoomView"

.field private static final TOUCH_SCROLL_THRESHOLD:I = 0xa

.field private static final TOUCH_STATE_CLICK:I = 0x1

.field private static final TOUCH_STATE_IDLE:I = 0x0

.field private static final TOUCH_STATE_SCROLL:I = 0x2


# instance fields
.field private isOnlyDrawLine:Z

.field private mDrawAdapter:Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;

.field private mDrawEndX:F

.field private mDrawStartX:F

.field private mEnableCallBack:Z

.field private mFirstItemWidtch:F

.field protected mItemGap:F

.field private mItemWidthNormal:F

.field private mLastItemWidtch:F

.field private mMargin:I

.field private mOnPositionZoomSelectListener:Lcom/android/camera/ui/BaseHorizontalZoomView$OnPositionSelectListener;

.field private mSelectPointX:F

.field protected mTotalWidth:F

.field private mTouchStartTime:F

.field private mTouchStartX:F

.field private mTouchStartY:F

.field private mTouchState:I

.field private mTouchX:F


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 1

    invoke-direct {p0, p1}, Lcom/android/camera/ui/BaseHorizontalZoomView;-><init>(Landroid/content/Context;)V

    const/high16 v0, -0x40800000    # -1.0f

    iput v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawStartX:F

    iput v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawEndX:F

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mEnableCallBack:Z

    const/4 v0, 0x0

    iput v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchState:I

    invoke-virtual {p0, p1}, Lcom/android/camera/ui/HorizontalZoomView;->init(Landroid/content/Context;)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 0

    invoke-direct {p0, p1, p2}, Lcom/android/camera/ui/BaseHorizontalZoomView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    const/high16 p2, -0x40800000    # -1.0f

    iput p2, p0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawStartX:F

    iput p2, p0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawEndX:F

    const/4 p2, 0x1

    iput-boolean p2, p0, Lcom/android/camera/ui/HorizontalZoomView;->mEnableCallBack:Z

    const/4 p2, 0x0

    iput p2, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchState:I

    invoke-virtual {p0, p1}, Lcom/android/camera/ui/HorizontalZoomView;->init(Landroid/content/Context;)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 0

    invoke-direct {p0, p1, p2, p3}, Lcom/android/camera/ui/BaseHorizontalZoomView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    const/high16 p2, -0x40800000    # -1.0f

    iput p2, p0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawStartX:F

    iput p2, p0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawEndX:F

    const/4 p2, 0x1

    iput-boolean p2, p0, Lcom/android/camera/ui/HorizontalZoomView;->mEnableCallBack:Z

    const/4 p2, 0x0

    iput p2, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchState:I

    invoke-virtual {p0, p1}, Lcom/android/camera/ui/HorizontalZoomView;->init(Landroid/content/Context;)V

    return-void
.end method

.method private getFirstOrLastWidth(Z)F
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/ui/HorizontalZoomView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Lcom/android/camera/Util;->isLayoutRTL(Landroid/content/Context;)Z

    move-result v0

    if-eqz v0, :cond_0

    nop

    xor-int/lit8 p1, p1, 0x1

    :cond_0
    if-eqz p1, :cond_1

    iget p1, p0, Lcom/android/camera/ui/HorizontalZoomView;->mFirstItemWidtch:F

    goto :goto_0

    :cond_1
    iget p1, p0, Lcom/android/camera/ui/HorizontalZoomView;->mLastItemWidtch:F

    :goto_0
    iget v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mItemWidthNormal:F

    const/high16 v1, 0x40000000    # 2.0f

    div-float/2addr v0, v1

    sub-float/2addr p1, v0

    return p1
.end method

.method private getItemWidth(I)F
    .locals 1

    iget-object v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawAdapter:Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;

    invoke-virtual {v0, p1}, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->measureWidth(I)F

    move-result p1

    return p1
.end method

.method private setSelectPointXToEffectiveRang(F)V
    .locals 3

    iget v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawStartX:F

    const/4 v1, 0x1

    invoke-direct {p0, v1}, Lcom/android/camera/ui/HorizontalZoomView;->getFirstOrLastWidth(Z)F

    move-result v1

    add-float/2addr v0, v1

    iget v1, p0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawEndX:F

    const/4 v2, 0x0

    invoke-direct {p0, v2}, Lcom/android/camera/ui/HorizontalZoomView;->getFirstOrLastWidth(Z)F

    move-result v2

    sub-float/2addr v1, v2

    invoke-static {p1, v0, v1}, Lcom/android/camera/Util;->clamp(FFF)F

    move-result p1

    iput p1, p0, Lcom/android/camera/ui/HorizontalZoomView;->mSelectPointX:F

    return-void
.end method

.method private startScrollIfNeeded(Landroid/view/MotionEvent;)Z
    .locals 3

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v0

    float-to-int v0, v0

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getY()F

    move-result p1

    float-to-int p1, p1

    int-to-float v0, v0

    iget v1, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchStartX:F

    const/high16 v2, 0x41200000    # 10.0f

    sub-float/2addr v1, v2

    cmpg-float v1, v0, v1

    if-ltz v1, :cond_1

    iget v1, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchStartX:F

    add-float/2addr v1, v2

    cmpl-float v0, v0, v1

    if-gtz v0, :cond_1

    int-to-float p1, p1

    iget v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchStartY:F

    sub-float/2addr v0, v2

    cmpg-float v0, p1, v0

    if-ltz v0, :cond_1

    iget v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchStartY:F

    add-float/2addr v0, v2

    cmpl-float p1, p1, v0

    if-lez p1, :cond_0

    goto :goto_0

    :cond_0
    const/4 p1, 0x0

    return p1

    :cond_1
    :goto_0
    const/4 p1, 0x2

    iput p1, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchState:I

    const/4 p1, 0x1

    return p1
.end method


# virtual methods
.method protected init(Landroid/content/Context;)V
    .locals 2

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    const v0, 0x7f0d016d

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result p1

    iput p1, p0, Lcom/android/camera/ui/HorizontalZoomView;->mMargin:I

    iget p1, p0, Lcom/android/camera/ui/HorizontalZoomView;->mMargin:I

    int-to-float p1, p1

    iput p1, p0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawStartX:F

    sget p1, Lcom/android/camera/Util;->sWindowWidth:I

    iget v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mMargin:I

    sub-int/2addr p1, v0

    int-to-float p1, p1

    iput p1, p0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawEndX:F

    sget p1, Lcom/android/camera/Util;->sWindowWidth:I

    iget v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mMargin:I

    const/4 v1, 0x2

    mul-int/2addr v1, v0

    sub-int/2addr p1, v1

    int-to-float p1, p1

    iput p1, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTotalWidth:F

    return-void
.end method

.method protected onDraw(Landroid/graphics/Canvas;)V
    .locals 19

    move-object/from16 v0, p0

    move-object/from16 v1, p1

    iget v2, v0, Lcom/android/camera/ui/HorizontalZoomView;->mSelectPointX:F

    invoke-direct {v0, v2}, Lcom/android/camera/ui/HorizontalZoomView;->setSelectPointXToEffectiveRang(F)V

    nop

    invoke-virtual/range {p0 .. p0}, Lcom/android/camera/ui/HorizontalZoomView;->getHeight()I

    move-result v2

    int-to-float v2, v2

    const/high16 v3, 0x40000000    # 2.0f

    mul-float/2addr v2, v3

    const/high16 v4, 0x40a00000    # 5.0f

    div-float/2addr v2, v4

    iget-object v4, v0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawAdapter:Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;

    const/4 v6, 0x1

    if-eqz v4, :cond_d

    invoke-virtual/range {p0 .. p0}, Lcom/android/camera/ui/HorizontalZoomView;->getContext()Landroid/content/Context;

    move-result-object v4

    invoke-static {v4}, Lcom/android/camera/Util;->isLayoutRTL(Landroid/content/Context;)Z

    move-result v4

    if-eqz v4, :cond_0

    iget-object v7, v0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawAdapter:Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;

    invoke-virtual {v7}, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->getCount()I

    move-result v7

    sub-int/2addr v7, v6

    goto :goto_0

    :cond_0
    const/4 v7, 0x0

    :goto_0
    if-eqz v4, :cond_1

    const/4 v8, 0x0

    goto :goto_1

    :cond_1
    iget-object v8, v0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawAdapter:Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;

    invoke-virtual {v8}, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->getCount()I

    move-result v8

    sub-int/2addr v8, v6

    :goto_1
    if-eqz v4, :cond_2

    const/4 v10, -0x1

    goto :goto_2

    :cond_2
    nop

    move v10, v6

    :goto_2
    iget v11, v0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawStartX:F

    nop

    nop

    move v12, v6

    move v13, v11

    const/4 v11, 0x0

    const/4 v14, -0x1

    :goto_3
    iget-object v15, v0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawAdapter:Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;

    invoke-virtual {v15}, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->getCount()I

    move-result v15

    if-ge v11, v15, :cond_a

    mul-int v15, v11, v10

    add-int/2addr v15, v7

    invoke-direct {v0, v15}, Lcom/android/camera/ui/HorizontalZoomView;->getItemWidth(I)F

    move-result v16

    nop

    if-nez v11, :cond_3

    invoke-direct {v0, v6}, Lcom/android/camera/ui/HorizontalZoomView;->getFirstOrLastWidth(Z)F

    move-result v17

    add-float v17, v13, v17

    iget v9, v0, Lcom/android/camera/ui/HorizontalZoomView;->mSelectPointX:F

    sub-float v17, v17, v9

    :goto_4
    move/from16 v3, v17

    goto :goto_5

    :cond_3
    div-float v9, v16, v3

    add-float/2addr v9, v13

    iget v3, v0, Lcom/android/camera/ui/HorizontalZoomView;->mSelectPointX:F

    sub-float v17, v9, v3

    goto :goto_4

    :goto_5
    if-eqz v12, :cond_7

    iget v9, v0, Lcom/android/camera/ui/HorizontalZoomView;->mSelectPointX:F

    iget v5, v0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawStartX:F

    invoke-direct {v0, v6}, Lcom/android/camera/ui/HorizontalZoomView;->getFirstOrLastWidth(Z)F

    move-result v17

    add-float v5, v5, v17

    cmpg-float v5, v9, v5

    if-gtz v5, :cond_4

    nop

    nop

    move v14, v7

    :goto_6
    const/high16 v6, 0x40000000    # 2.0f

    :goto_7
    const/4 v12, 0x0

    goto :goto_8

    :cond_4
    iget v5, v0, Lcom/android/camera/ui/HorizontalZoomView;->mSelectPointX:F

    iget v9, v0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawEndX:F

    const/4 v6, 0x0

    invoke-direct {v0, v6}, Lcom/android/camera/ui/HorizontalZoomView;->getFirstOrLastWidth(Z)F

    move-result v17

    sub-float v9, v9, v17

    cmpl-float v5, v5, v9

    if-ltz v5, :cond_5

    nop

    nop

    move v14, v8

    goto :goto_6

    :cond_5
    invoke-static {v3}, Ljava/lang/Math;->abs(F)F

    move-result v5

    iget v6, v0, Lcom/android/camera/ui/HorizontalZoomView;->mItemWidthNormal:F

    const/high16 v9, 0x40000000    # 2.0f

    div-float/2addr v6, v9

    cmpg-float v5, v5, v6

    if-gtz v5, :cond_6

    nop

    nop

    move v14, v15

    goto :goto_6

    :cond_6
    iget-boolean v5, v0, Lcom/android/camera/ui/HorizontalZoomView;->isOnlyDrawLine:Z

    if-eqz v5, :cond_7

    invoke-static {v3}, Ljava/lang/Math;->abs(F)F

    move-result v3

    iget v5, v0, Lcom/android/camera/ui/HorizontalZoomView;->mItemGap:F

    const/high16 v6, 0x40000000    # 2.0f

    div-float/2addr v5, v6

    iget v9, v0, Lcom/android/camera/ui/HorizontalZoomView;->mItemWidthNormal:F

    div-float/2addr v9, v6

    add-float/2addr v5, v9

    cmpg-float v3, v3, v5

    if-gtz v3, :cond_8

    nop

    nop

    move v14, v15

    goto :goto_7

    :cond_7
    const/high16 v6, 0x40000000    # 2.0f

    :cond_8
    :goto_8
    invoke-virtual/range {p1 .. p1}, Landroid/graphics/Canvas;->save()I

    const/4 v3, 0x0

    invoke-virtual {v1, v3, v2}, Landroid/graphics/Canvas;->translate(FF)V

    iget-object v3, v0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawAdapter:Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;

    if-nez v12, :cond_9

    if-ne v14, v15, :cond_9

    const/4 v5, 0x1

    goto :goto_9

    :cond_9
    const/4 v5, 0x0

    :goto_9
    invoke-virtual {v3, v13, v15, v1, v5}, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->draw(FILandroid/graphics/Canvas;Z)V

    invoke-virtual/range {p1 .. p1}, Landroid/graphics/Canvas;->restore()V

    iget v3, v0, Lcom/android/camera/ui/HorizontalZoomView;->mItemGap:F

    add-float v16, v16, v3

    add-float v13, v13, v16

    add-int/lit8 v11, v11, 0x1

    move v3, v6

    const/4 v6, 0x1

    goto/16 :goto_3

    :cond_a
    invoke-virtual/range {p1 .. p1}, Landroid/graphics/Canvas;->save()I

    const/4 v3, 0x0

    invoke-virtual {v1, v3, v2}, Landroid/graphics/Canvas;->translate(FF)V

    if-eqz v12, :cond_b

    iget-object v2, v0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawAdapter:Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;

    iget v3, v0, Lcom/android/camera/ui/HorizontalZoomView;->mSelectPointX:F

    const/4 v5, -0x1

    invoke-virtual {v2, v3, v5, v1, v12}, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->draw(FILandroid/graphics/Canvas;Z)V

    :cond_b
    invoke-virtual/range {p1 .. p1}, Landroid/graphics/Canvas;->restore()V

    iget-object v1, v0, Lcom/android/camera/ui/HorizontalZoomView;->mOnPositionZoomSelectListener:Lcom/android/camera/ui/BaseHorizontalZoomView$OnPositionSelectListener;

    if-eqz v1, :cond_d

    iget-boolean v1, v0, Lcom/android/camera/ui/HorizontalZoomView;->mEnableCallBack:Z

    if-eqz v1, :cond_d

    iget v1, v0, Lcom/android/camera/ui/HorizontalZoomView;->mSelectPointX:F

    const/4 v2, 0x1

    invoke-direct {v0, v2}, Lcom/android/camera/ui/HorizontalZoomView;->getFirstOrLastWidth(Z)F

    move-result v3

    sub-float/2addr v1, v3

    iget v3, v0, Lcom/android/camera/ui/HorizontalZoomView;->mMargin:I

    int-to-float v3, v3

    sub-float/2addr v1, v3

    iget v3, v0, Lcom/android/camera/ui/HorizontalZoomView;->mTotalWidth:F

    invoke-direct {v0, v2}, Lcom/android/camera/ui/HorizontalZoomView;->getFirstOrLastWidth(Z)F

    move-result v5

    sub-float/2addr v3, v5

    const/4 v2, 0x0

    invoke-direct {v0, v2}, Lcom/android/camera/ui/HorizontalZoomView;->getFirstOrLastWidth(Z)F

    move-result v5

    sub-float/2addr v3, v5

    div-float/2addr v1, v3

    if-eqz v4, :cond_c

    const/high16 v2, 0x3f800000    # 1.0f

    sub-float v1, v2, v1

    :cond_c
    iget-object v2, v0, Lcom/android/camera/ui/HorizontalZoomView;->mOnPositionZoomSelectListener:Lcom/android/camera/ui/BaseHorizontalZoomView$OnPositionSelectListener;

    invoke-interface {v2, v0, v14, v1}, Lcom/android/camera/ui/BaseHorizontalZoomView$OnPositionSelectListener;->onPositionSelect(Landroid/view/View;IF)V

    :cond_d
    const/4 v1, 0x0

    iput-boolean v1, v0, Lcom/android/camera/ui/HorizontalZoomView;->isOnlyDrawLine:Z

    const/4 v1, 0x1

    iput-boolean v1, v0, Lcom/android/camera/ui/HorizontalZoomView;->mEnableCallBack:Z

    return-void
.end method

.method public onTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 5

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getAction()I

    move-result v0

    const/4 v1, 0x2

    const/4 v2, 0x1

    packed-switch v0, :pswitch_data_0

    goto :goto_1

    :pswitch_0
    iget v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchState:I

    if-ne v0, v2, :cond_0

    invoke-direct {p0, p1}, Lcom/android/camera/ui/HorizontalZoomView;->startScrollIfNeeded(Landroid/view/MotionEvent;)Z

    :cond_0
    iget v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchState:I

    if-ne v0, v1, :cond_3

    iget v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mSelectPointX:F

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v1

    iget v3, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchX:F

    sub-float/2addr v1, v3

    add-float/2addr v0, v1

    iput v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mSelectPointX:F

    invoke-virtual {p0}, Lcom/android/camera/ui/HorizontalZoomView;->invalidate()V

    goto :goto_1

    :pswitch_1
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v3

    long-to-float v0, v3

    iget v3, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchStartTime:F

    sub-float/2addr v0, v3

    invoke-static {}, Landroid/view/ViewConfiguration;->getLongPressTimeout()I

    move-result v3

    int-to-float v3, v3

    cmpl-float v0, v0, v3

    const/4 v3, 0x0

    if-lez v0, :cond_1

    iput v3, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchState:I

    return v2

    :cond_1
    iget v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchState:I

    if-ne v0, v1, :cond_2

    iget v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mSelectPointX:F

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v1

    iget v4, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchX:F

    sub-float/2addr v1, v4

    add-float/2addr v0, v1

    iput v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mSelectPointX:F

    goto :goto_0

    :cond_2
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v0

    iput v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mSelectPointX:F

    :goto_0
    iput v3, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchState:I

    iput-boolean v2, p0, Lcom/android/camera/ui/HorizontalZoomView;->isOnlyDrawLine:Z

    invoke-virtual {p0}, Lcom/android/camera/ui/HorizontalZoomView;->invalidate()V

    goto :goto_1

    :pswitch_2
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v3

    long-to-float v0, v3

    iput v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchStartTime:F

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v0

    iput v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchStartX:F

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getY()F

    move-result v0

    iput v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchStartY:F

    iget v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchState:I

    if-eq v0, v1, :cond_3

    iput v2, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchState:I

    :cond_3
    :goto_1
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result p1

    iput p1, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchX:F

    return v2

    nop

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_2
        :pswitch_1
        :pswitch_0
        :pswitch_1
    .end packed-switch
.end method

.method public setDrawAdapter(Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;)V
    .locals 3

    iput-object p1, p0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawAdapter:Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;

    nop

    const/4 p1, 0x0

    const/4 v0, 0x0

    move v1, p1

    :goto_0
    iget-object v2, p0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawAdapter:Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;

    invoke-virtual {v2}, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->getCount()I

    move-result v2

    if-ge v0, v2, :cond_3

    if-nez v0, :cond_0

    invoke-direct {p0, v0}, Lcom/android/camera/ui/HorizontalZoomView;->getItemWidth(I)F

    move-result v2

    iput v2, p0, Lcom/android/camera/ui/HorizontalZoomView;->mFirstItemWidtch:F

    goto :goto_1

    :cond_0
    iget-object v2, p0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawAdapter:Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;

    invoke-virtual {v2}, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->getCount()I

    move-result v2

    add-int/lit8 v2, v2, -0x1

    if-ne v0, v2, :cond_1

    invoke-direct {p0, v0}, Lcom/android/camera/ui/HorizontalZoomView;->getItemWidth(I)F

    move-result v2

    iput v2, p0, Lcom/android/camera/ui/HorizontalZoomView;->mLastItemWidtch:F

    goto :goto_1

    :cond_1
    iget v2, p0, Lcom/android/camera/ui/HorizontalZoomView;->mItemWidthNormal:F

    cmpl-float v2, v2, p1

    if-nez v2, :cond_2

    invoke-direct {p0, v0}, Lcom/android/camera/ui/HorizontalZoomView;->getItemWidth(I)F

    move-result v2

    iput v2, p0, Lcom/android/camera/ui/HorizontalZoomView;->mItemWidthNormal:F

    :cond_2
    :goto_1
    invoke-direct {p0, v0}, Lcom/android/camera/ui/HorizontalZoomView;->getItemWidth(I)F

    move-result v2

    add-float/2addr v1, v2

    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_3
    iget p1, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTotalWidth:F

    sub-float/2addr p1, v1

    iget-object v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawAdapter:Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;

    invoke-virtual {v0}, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->getCount()I

    move-result v0

    add-int/lit8 v0, v0, -0x1

    int-to-float v0, v0

    div-float/2addr p1, v0

    iput p1, p0, Lcom/android/camera/ui/HorizontalZoomView;->mItemGap:F

    invoke-virtual {p0}, Lcom/android/camera/ui/HorizontalZoomView;->invalidate()V

    return-void
.end method

.method public setEvent(Landroid/view/MotionEvent;)V
    .locals 1

    const/4 v0, 0x2

    iput v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTouchState:I

    invoke-virtual {p0, p1}, Lcom/android/camera/ui/HorizontalZoomView;->onTouchEvent(Landroid/view/MotionEvent;)Z

    return-void
.end method

.method public setJustifyEnabled(Z)V
    .locals 0

    return-void
.end method

.method public setOnPositionSelectListener(Lcom/android/camera/ui/BaseHorizontalZoomView$OnPositionSelectListener;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/ui/HorizontalZoomView;->mOnPositionZoomSelectListener:Lcom/android/camera/ui/BaseHorizontalZoomView$OnPositionSelectListener;

    return-void
.end method

.method public setSelection(FZ)V
    .locals 3

    invoke-virtual {p0}, Lcom/android/camera/ui/HorizontalZoomView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Lcom/android/camera/Util;->isLayoutRTL(Landroid/content/Context;)Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mDrawAdapter:Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;

    if-eqz v0, :cond_0

    const/high16 v0, 0x3f800000    # 1.0f

    sub-float p1, v0, p1

    :cond_0
    iget v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mTotalWidth:F

    const/4 v1, 0x1

    invoke-direct {p0, v1}, Lcom/android/camera/ui/HorizontalZoomView;->getFirstOrLastWidth(Z)F

    move-result v2

    sub-float/2addr v0, v2

    const/4 v2, 0x0

    invoke-direct {p0, v2}, Lcom/android/camera/ui/HorizontalZoomView;->getFirstOrLastWidth(Z)F

    move-result v2

    sub-float/2addr v0, v2

    mul-float/2addr v0, p1

    invoke-direct {p0, v1}, Lcom/android/camera/ui/HorizontalZoomView;->getFirstOrLastWidth(Z)F

    move-result v1

    add-float/2addr v0, v1

    iget v1, p0, Lcom/android/camera/ui/HorizontalZoomView;->mMargin:I

    int-to-float v1, v1

    add-float/2addr v0, v1

    iput v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mSelectPointX:F

    xor-int/lit8 v0, p2, 0x1

    iput-boolean v0, p0, Lcom/android/camera/ui/HorizontalZoomView;->mEnableCallBack:Z

    iput-boolean p2, p0, Lcom/android/camera/ui/HorizontalZoomView;->isOnlyDrawLine:Z

    const-string p2, "HorizontalZoomView"

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v1, "setSelection   "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lcom/android/camera/ui/HorizontalZoomView;->mSelectPointX:F

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string v1, "  "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p2, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0}, Lcom/android/camera/ui/HorizontalZoomView;->invalidate()V

    return-void
.end method
