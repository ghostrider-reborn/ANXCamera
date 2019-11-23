.class public Lcom/android/camera/ui/zoom/ZoomTextImageView;
.super Landroid/view/View;
.source "ZoomTextImageView.java"


# static fields
.field public static final TYPE_IMAGE:I = 0x2

.field public static final TYPE_TEXT_NORMAL:I = 0x0

.field public static final TYPE_TEXT_SNAP:I = 0x1

.field private static final mTextActivatedColorState:[I


# instance fields
.field private mBitmapMatrix:Landroid/graphics/Matrix;

.field private mCurrentImage:Landroid/graphics/Bitmap;

.field private mCurrentText:Ljava/lang/String;

.field private mCurrentType:I

.field private mImagePaint:Landroid/graphics/Paint;

.field private mNormalTextColor:Landroid/content/res/ColorStateList;

.field private mNormalTextSize:I

.field private mSnapTextColor:Landroid/content/res/ColorStateList;

.field private mSnapTextSize:I

.field private mTextPaint:Landroid/graphics/Paint;

.field private mXTextColor:Landroid/content/res/ColorStateList;

.field private mXTextPaint:Landroid/graphics/Paint;

.field private mXTextSize:I


# direct methods
.method static constructor <clinit>()V
    .locals 3

    const/4 v0, 0x1

    new-array v0, v0, [I

    const/4 v1, 0x0

    const v2, 0x10102fe

    aput v2, v0, v1

    sput-object v0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mTextActivatedColorState:[I

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;)V
    .locals 0

    invoke-direct {p0, p1}, Landroid/view/View;-><init>(Landroid/content/Context;)V

    invoke-direct {p0, p1}, Lcom/android/camera/ui/zoom/ZoomTextImageView;->init(Landroid/content/Context;)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 0

    invoke-direct {p0, p1, p2}, Landroid/view/View;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    invoke-direct {p0, p1}, Lcom/android/camera/ui/zoom/ZoomTextImageView;->init(Landroid/content/Context;)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 0

    invoke-direct {p0, p1, p2, p3}, Landroid/view/View;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    invoke-direct {p0, p1}, Lcom/android/camera/ui/zoom/ZoomTextImageView;->init(Landroid/content/Context;)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;II)V
    .locals 0

    invoke-direct {p0, p1, p2, p3, p4}, Landroid/view/View;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;II)V

    invoke-direct {p0, p1}, Lcom/android/camera/ui/zoom/ZoomTextImageView;->init(Landroid/content/Context;)V

    return-void
.end method

.method private init(Landroid/content/Context;)V
    .locals 6

    new-instance v0, Landroid/graphics/Paint;

    invoke-direct {v0}, Landroid/graphics/Paint;-><init>()V

    iput-object v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mTextPaint:Landroid/graphics/Paint;

    new-instance v0, Landroid/graphics/Paint;

    invoke-direct {v0}, Landroid/graphics/Paint;-><init>()V

    iput-object v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mImagePaint:Landroid/graphics/Paint;

    new-instance v0, Landroid/graphics/Paint;

    invoke-direct {v0}, Landroid/graphics/Paint;-><init>()V

    iput-object v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mXTextPaint:Landroid/graphics/Paint;

    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mImagePaint:Landroid/graphics/Paint;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setAntiAlias(Z)V

    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mImagePaint:Landroid/graphics/Paint;

    sget-object v2, Landroid/graphics/Paint$Style;->STROKE:Landroid/graphics/Paint$Style;

    invoke-virtual {v0, v2}, Landroid/graphics/Paint;->setStyle(Landroid/graphics/Paint$Style;)V

    const/4 v0, 0x2

    new-array v0, v0, [I

    fill-array-data v0, :array_0

    const v2, 0x7f0b001b

    invoke-virtual {p1, v2, v0}, Landroid/content/Context;->obtainStyledAttributes(I[I)Landroid/content/res/TypedArray;

    move-result-object v2

    const/4 v3, 0x0

    invoke-virtual {v2, v3}, Landroid/content/res/TypedArray;->getIndex(I)I

    move-result v4

    iget v5, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mNormalTextSize:I

    invoke-virtual {v2, v4, v5}, Landroid/content/res/TypedArray;->getDimensionPixelSize(II)I

    move-result v4

    iput v4, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mNormalTextSize:I

    invoke-virtual {v2, v1}, Landroid/content/res/TypedArray;->getIndex(I)I

    move-result v4

    invoke-virtual {v2, v4}, Landroid/content/res/TypedArray;->getColorStateList(I)Landroid/content/res/ColorStateList;

    move-result-object v2

    iput-object v2, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mNormalTextColor:Landroid/content/res/ColorStateList;

    const v2, 0x7f0b001c

    invoke-virtual {p1, v2, v0}, Landroid/content/Context;->obtainStyledAttributes(I[I)Landroid/content/res/TypedArray;

    move-result-object v2

    invoke-virtual {v2, v3}, Landroid/content/res/TypedArray;->getIndex(I)I

    move-result v4

    iget v5, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mXTextSize:I

    invoke-virtual {v2, v4, v5}, Landroid/content/res/TypedArray;->getDimensionPixelSize(II)I

    move-result v4

    iput v4, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mXTextSize:I

    invoke-virtual {v2, v1}, Landroid/content/res/TypedArray;->getIndex(I)I

    move-result v4

    invoke-virtual {v2, v4}, Landroid/content/res/TypedArray;->getColorStateList(I)Landroid/content/res/ColorStateList;

    move-result-object v2

    iput-object v2, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mXTextColor:Landroid/content/res/ColorStateList;

    const v2, 0x7f0b001a

    invoke-virtual {p1, v2, v0}, Landroid/content/Context;->obtainStyledAttributes(I[I)Landroid/content/res/TypedArray;

    move-result-object p1

    invoke-virtual {p1, v3}, Landroid/content/res/TypedArray;->getIndex(I)I

    move-result v0

    iget v2, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mSnapTextSize:I

    invoke-virtual {p1, v0, v2}, Landroid/content/res/TypedArray;->getDimensionPixelSize(II)I

    move-result v0

    iput v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mSnapTextSize:I

    invoke-virtual {p1, v1}, Landroid/content/res/TypedArray;->getIndex(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/content/res/TypedArray;->getColorStateList(I)Landroid/content/res/ColorStateList;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mSnapTextColor:Landroid/content/res/ColorStateList;

    return-void

    nop

    :array_0
    .array-data 4
        0x1010095
        0x1010098
    .end array-data
.end method


# virtual methods
.method public onDraw(Landroid/graphics/Canvas;)V
    .locals 10

    iget v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mCurrentType:I

    const/4 v1, 0x0

    const/high16 v2, 0x3f800000    # 1.0f

    const/4 v3, 0x0

    const/high16 v4, 0x40000000    # 2.0f

    packed-switch v0, :pswitch_data_0

    goto/16 :goto_1

    :pswitch_0
    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mBitmapMatrix:Landroid/graphics/Matrix;

    if-nez v0, :cond_0

    new-instance v0, Landroid/graphics/Matrix;

    invoke-direct {v0}, Landroid/graphics/Matrix;-><init>()V

    iput-object v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mBitmapMatrix:Landroid/graphics/Matrix;

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mBitmapMatrix:Landroid/graphics/Matrix;

    invoke-virtual {v0}, Landroid/graphics/Matrix;->reset()V

    :goto_0
    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mCurrentImage:Landroid/graphics/Bitmap;

    invoke-virtual {v0}, Landroid/graphics/Bitmap;->isRecycled()Z

    move-result v0

    if-nez v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mBitmapMatrix:Landroid/graphics/Matrix;

    invoke-virtual {p0}, Lcom/android/camera/ui/zoom/ZoomTextImageView;->getWidth()I

    move-result v1

    div-int/lit8 v1, v1, 0x2

    iget-object v2, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mCurrentImage:Landroid/graphics/Bitmap;

    invoke-virtual {v2}, Landroid/graphics/Bitmap;->getWidth()I

    move-result v2

    div-int/lit8 v2, v2, 0x2

    sub-int/2addr v1, v2

    int-to-float v1, v1

    invoke-virtual {p0}, Lcom/android/camera/ui/zoom/ZoomTextImageView;->getHeight()I

    move-result v2

    div-int/lit8 v2, v2, 0x2

    iget-object v3, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mCurrentImage:Landroid/graphics/Bitmap;

    invoke-virtual {v3}, Landroid/graphics/Bitmap;->getHeight()I

    move-result v3

    div-int/lit8 v3, v3, 0x2

    sub-int/2addr v2, v3

    int-to-float v2, v2

    invoke-virtual {v0, v1, v2}, Landroid/graphics/Matrix;->postTranslate(FF)Z

    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mCurrentImage:Landroid/graphics/Bitmap;

    iget-object v1, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mBitmapMatrix:Landroid/graphics/Matrix;

    iget-object v2, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mImagePaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v0, v1, v2}, Landroid/graphics/Canvas;->drawBitmap(Landroid/graphics/Bitmap;Landroid/graphics/Matrix;Landroid/graphics/Paint;)V

    goto/16 :goto_1

    :pswitch_1
    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mCurrentText:Ljava/lang/String;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mTextPaint:Landroid/graphics/Paint;

    iget-object v5, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mSnapTextColor:Landroid/content/res/ColorStateList;

    sget-object v6, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mTextActivatedColorState:[I

    invoke-virtual {v5, v6, v3}, Landroid/content/res/ColorStateList;->getColorForState([II)I

    move-result v3

    invoke-virtual {v0, v3}, Landroid/graphics/Paint;->setColor(I)V

    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mTextPaint:Landroid/graphics/Paint;

    iget v3, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mSnapTextSize:I

    int-to-float v3, v3

    invoke-virtual {v0, v3}, Landroid/graphics/Paint;->setTextSize(F)V

    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mTextPaint:Landroid/graphics/Paint;

    iget-object v3, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mCurrentText:Ljava/lang/String;

    invoke-virtual {v0, v3}, Landroid/graphics/Paint;->measureText(Ljava/lang/String;)F

    move-result v0

    iget-object v3, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mTextPaint:Landroid/graphics/Paint;

    invoke-virtual {v3}, Landroid/graphics/Paint;->ascent()F

    move-result v3

    iget-object v5, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mTextPaint:Landroid/graphics/Paint;

    invoke-virtual {v5}, Landroid/graphics/Paint;->descent()F

    move-result v5

    add-float/2addr v3, v5

    invoke-virtual {p1}, Landroid/graphics/Canvas;->save()I

    invoke-virtual {p0}, Lcom/android/camera/ui/zoom/ZoomTextImageView;->getHeight()I

    move-result v5

    div-int/lit8 v5, v5, 0x2

    int-to-float v5, v5

    invoke-virtual {p1, v1, v5}, Landroid/graphics/Canvas;->translate(FF)V

    iget-object v1, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mCurrentText:Ljava/lang/String;

    invoke-virtual {p0}, Lcom/android/camera/ui/zoom/ZoomTextImageView;->getWidth()I

    move-result v5

    div-int/lit8 v5, v5, 0x2

    int-to-float v5, v5

    div-float/2addr v0, v4

    sub-float/2addr v5, v0

    neg-float v0, v3

    div-float/2addr v0, v4

    add-float/2addr v0, v2

    iget-object v2, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mTextPaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v1, v5, v0, v2}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    invoke-virtual {p1}, Landroid/graphics/Canvas;->restore()V

    goto/16 :goto_1

    :pswitch_2
    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mCurrentText:Ljava/lang/String;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mTextPaint:Landroid/graphics/Paint;

    iget-object v5, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mNormalTextColor:Landroid/content/res/ColorStateList;

    sget-object v6, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mTextActivatedColorState:[I

    invoke-virtual {v5, v6, v3}, Landroid/content/res/ColorStateList;->getColorForState([II)I

    move-result v5

    invoke-virtual {v0, v5}, Landroid/graphics/Paint;->setColor(I)V

    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mTextPaint:Landroid/graphics/Paint;

    iget v5, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mNormalTextSize:I

    int-to-float v5, v5

    invoke-virtual {v0, v5}, Landroid/graphics/Paint;->setTextSize(F)V

    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mCurrentText:Ljava/lang/String;

    const-string v5, "X"

    const-string v6, ""

    invoke-virtual {v0, v5, v6}, Ljava/lang/String;->replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iget-object v5, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mTextPaint:Landroid/graphics/Paint;

    invoke-virtual {v5, v0}, Landroid/graphics/Paint;->measureText(Ljava/lang/String;)F

    move-result v5

    iget-object v6, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mTextPaint:Landroid/graphics/Paint;

    invoke-virtual {v6}, Landroid/graphics/Paint;->ascent()F

    move-result v6

    iget-object v7, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mTextPaint:Landroid/graphics/Paint;

    invoke-virtual {v7}, Landroid/graphics/Paint;->descent()F

    move-result v7

    add-float/2addr v6, v7

    iget-object v7, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mXTextPaint:Landroid/graphics/Paint;

    iget-object v8, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mXTextColor:Landroid/content/res/ColorStateList;

    sget-object v9, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mTextActivatedColorState:[I

    invoke-virtual {v8, v9, v3}, Landroid/content/res/ColorStateList;->getColorForState([II)I

    move-result v3

    invoke-virtual {v7, v3}, Landroid/graphics/Paint;->setColor(I)V

    iget-object v3, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mXTextPaint:Landroid/graphics/Paint;

    iget v7, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mXTextSize:I

    int-to-float v7, v7

    invoke-virtual {v3, v7}, Landroid/graphics/Paint;->setTextSize(F)V

    iget-object v3, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mXTextPaint:Landroid/graphics/Paint;

    const-string v7, "X"

    invoke-virtual {v3, v7}, Landroid/graphics/Paint;->measureText(Ljava/lang/String;)F

    move-result v3

    iget-object v7, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mXTextPaint:Landroid/graphics/Paint;

    invoke-virtual {v7}, Landroid/graphics/Paint;->ascent()F

    move-result v7

    iget-object v8, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mXTextPaint:Landroid/graphics/Paint;

    invoke-virtual {v8}, Landroid/graphics/Paint;->descent()F

    move-result v8

    add-float/2addr v7, v8

    invoke-virtual {p1}, Landroid/graphics/Canvas;->save()I

    invoke-virtual {p0}, Lcom/android/camera/ui/zoom/ZoomTextImageView;->getHeight()I

    move-result v8

    div-int/lit8 v8, v8, 0x2

    int-to-float v8, v8

    invoke-virtual {p1, v1, v8}, Landroid/graphics/Canvas;->translate(FF)V

    invoke-virtual {p0}, Lcom/android/camera/ui/zoom/ZoomTextImageView;->getWidth()I

    move-result v1

    div-int/lit8 v1, v1, 0x2

    int-to-float v1, v1

    add-float v8, v5, v3

    div-float/2addr v8, v4

    sub-float/2addr v1, v8

    neg-float v6, v6

    div-float/2addr v6, v4

    add-float/2addr v6, v2

    iget-object v8, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mTextPaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v0, v1, v6, v8}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    const-string v0, "X"

    invoke-virtual {p0}, Lcom/android/camera/ui/zoom/ZoomTextImageView;->getWidth()I

    move-result v1

    int-to-float v1, v1

    add-float/2addr v1, v5

    sub-float/2addr v1, v3

    div-float/2addr v1, v4

    neg-float v3, v7

    div-float/2addr v3, v4

    add-float/2addr v3, v2

    iget-object v2, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mXTextPaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v0, v1, v3, v2}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    invoke-virtual {p1}, Landroid/graphics/Canvas;->restore()V

    nop

    :cond_1
    :goto_1
    return-void

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public setImage(Landroid/graphics/Bitmap;)V
    .locals 1

    const/4 v0, 0x2

    iput v0, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mCurrentType:I

    iput-object p1, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mCurrentImage:Landroid/graphics/Bitmap;

    invoke-virtual {p0}, Lcom/android/camera/ui/zoom/ZoomTextImageView;->invalidate()V

    return-void
.end method

.method public setText(ILjava/lang/String;)V
    .locals 0

    iput p1, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mCurrentType:I

    iput-object p2, p0, Lcom/android/camera/ui/zoom/ZoomTextImageView;->mCurrentText:Ljava/lang/String;

    invoke-virtual {p0}, Lcom/android/camera/ui/zoom/ZoomTextImageView;->invalidate()V

    return-void
.end method
