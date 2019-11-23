.class public Lcom/android/camera/ui/zoom/ZoomRatioView;
.super Landroid/widget/FrameLayout;
.source "ZoomRatioView.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "ZoomRatioView"

.field private static final UI_DEBUG_ENABLED:Z = false


# instance fields
.field private mZoomRatioIcon:Landroid/widget/ImageView;

.field private mZoomRatioIndex:I

.field private mZoomRatioText:Lcom/android/camera/ui/zoom/ZoomTextImageView;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 1

    const/4 v0, 0x0

    invoke-direct {p0, p1, v0}, Lcom/android/camera/ui/zoom/ZoomRatioView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 1

    const/4 v0, 0x0

    invoke-direct {p0, p1, p2, v0}, Lcom/android/camera/ui/zoom/ZoomRatioView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 1

    const/4 v0, 0x0

    invoke-direct {p0, p1, p2, p3, v0}, Lcom/android/camera/ui/zoom/ZoomRatioView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;II)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;II)V
    .locals 0

    invoke-direct {p0, p1, p2, p3, p4}, Landroid/widget/FrameLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;II)V

    return-void
.end method

.method private static debugUi(Ljava/lang/String;Ljava/lang/String;)V
    .locals 0

    return-void
.end method


# virtual methods
.method public getAlpha()F
    .locals 1

    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomRatioView;->mZoomRatioIcon:Landroid/widget/ImageView;

    invoke-virtual {v0}, Landroid/widget/ImageView;->getAlpha()F

    move-result v0

    return v0
.end method

.method public getIconView()Landroid/widget/ImageView;
    .locals 1

    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomRatioView;->mZoomRatioIcon:Landroid/widget/ImageView;

    return-object v0
.end method

.method public getTextView()Lcom/android/camera/ui/zoom/ZoomTextImageView;
    .locals 1

    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomRatioView;->mZoomRatioText:Lcom/android/camera/ui/zoom/ZoomTextImageView;

    return-object v0
.end method

.method public getZoomRatioIndex()I
    .locals 1

    iget v0, p0, Lcom/android/camera/ui/zoom/ZoomRatioView;->mZoomRatioIndex:I

    return v0
.end method

.method protected onFinishInflate()V
    .locals 1

    invoke-super {p0}, Landroid/widget/FrameLayout;->onFinishInflate()V

    const v0, 0x7f0e0181

    invoke-virtual {p0, v0}, Lcom/android/camera/ui/zoom/ZoomRatioView;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/android/camera/ui/zoom/ZoomRatioView;->mZoomRatioIcon:Landroid/widget/ImageView;

    const v0, 0x7f0e0182

    invoke-virtual {p0, v0}, Lcom/android/camera/ui/zoom/ZoomRatioView;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/android/camera/ui/zoom/ZoomTextImageView;

    iput-object v0, p0, Lcom/android/camera/ui/zoom/ZoomRatioView;->mZoomRatioText:Lcom/android/camera/ui/zoom/ZoomTextImageView;

    return-void
.end method

.method public setAlpha(F)V
    .locals 3

    const-string v0, "ZoomRatioView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "setAlpha(): index = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/android/camera/ui/zoom/ZoomRatioView;->mZoomRatioIndex:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, ", alpha = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/ui/zoom/ZoomRatioView;->debugUi(Ljava/lang/String;Ljava/lang/String;)V

    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomRatioView;->mZoomRatioIcon:Landroid/widget/ImageView;

    invoke-virtual {v0, p1}, Landroid/widget/ImageView;->setAlpha(F)V

    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomRatioView;->mZoomRatioText:Lcom/android/camera/ui/zoom/ZoomTextImageView;

    const/high16 v1, 0x3f800000    # 1.0f

    sub-float/2addr v1, p1

    invoke-virtual {v0, v1}, Lcom/android/camera/ui/zoom/ZoomTextImageView;->setAlpha(F)V

    return-void
.end method

.method public setCaptureCount(I)V
    .locals 5

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "%02d"

    const/4 v2, 0x1

    new-array v3, v2, [Ljava/lang/Object;

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    const/4 v4, 0x0

    aput-object p1, v3, v4

    invoke-static {v1, v3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object p1, p0, Lcom/android/camera/ui/zoom/ZoomRatioView;->mZoomRatioText:Lcom/android/camera/ui/zoom/ZoomTextImageView;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v2, v0}, Lcom/android/camera/ui/zoom/ZoomTextImageView;->setText(ILjava/lang/String;)V

    return-void
.end method

.method public setIconify(Z)V
    .locals 0

    if-eqz p1, :cond_0

    const/high16 p1, 0x3f800000    # 1.0f

    goto :goto_0

    :cond_0
    const/4 p1, 0x0

    :goto_0
    invoke-virtual {p0, p1}, Lcom/android/camera/ui/zoom/ZoomRatioView;->setAlpha(F)V

    return-void
.end method

.method public setVisibility(I)V
    .locals 3

    const-string v0, "ZoomRatioView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v2, "setVisibility(): index = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/android/camera/ui/zoom/ZoomRatioView;->mZoomRatioIndex:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, ", visibility = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {p1}, Lcom/android/camera/Util;->viewVisibilityToString(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/ui/zoom/ZoomRatioView;->debugUi(Ljava/lang/String;Ljava/lang/String;)V

    invoke-super {p0, p1}, Landroid/widget/FrameLayout;->setVisibility(I)V

    return-void
.end method

.method public setZoomRatio(F)V
    .locals 5

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const/4 v1, 0x0

    cmpl-float v1, p1, v1

    if-eqz v1, :cond_1

    invoke-static {p1}, Lcom/android/camera/HybridZoomingSystem;->toDecimal(F)F

    move-result v1

    float-to-int v2, v1

    const/high16 v3, 0x41200000    # 10.0f

    mul-float/2addr v3, v1

    mul-int/lit8 v4, v2, 0xa

    int-to-float v4, v4

    sub-float/2addr v3, v4

    float-to-int v3, v3

    if-nez v3, :cond_0

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    goto :goto_0

    :cond_0
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    :goto_0
    const-string v1, "ZoomRatioView"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v3, "setZoomRatio(): "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v1, p1}, Lcom/android/camera/ui/zoom/ZoomRatioView;->debugUi(Ljava/lang/String;Ljava/lang/String;)V

    iget-object p1, p0, Lcom/android/camera/ui/zoom/ZoomRatioView;->mZoomRatioText:Lcom/android/camera/ui/zoom/ZoomTextImageView;

    const/4 v1, 0x0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v1, v0}, Lcom/android/camera/ui/zoom/ZoomTextImageView;->setText(ILjava/lang/String;)V

    goto :goto_1

    :cond_1
    invoke-virtual {p0}, Lcom/android/camera/ui/zoom/ZoomRatioView;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    const v0, 0x7f0201c7

    invoke-static {p1, v0}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object p1

    iget-object v0, p0, Lcom/android/camera/ui/zoom/ZoomRatioView;->mZoomRatioText:Lcom/android/camera/ui/zoom/ZoomTextImageView;

    invoke-virtual {v0, p1}, Lcom/android/camera/ui/zoom/ZoomTextImageView;->setImage(Landroid/graphics/Bitmap;)V

    :goto_1
    return-void
.end method

.method public setZoomRatioIcon(F)V
    .locals 1

    const/4 v0, 0x0

    cmpl-float p1, p1, v0

    if-eqz p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/ui/zoom/ZoomRatioView;->mZoomRatioIcon:Landroid/widget/ImageView;

    const v0, 0x7f0201c5

    invoke-virtual {p1, v0}, Landroid/widget/ImageView;->setImageResource(I)V

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/android/camera/ui/zoom/ZoomRatioView;->mZoomRatioIcon:Landroid/widget/ImageView;

    const v0, 0x7f0201c6

    invoke-virtual {p1, v0}, Landroid/widget/ImageView;->setImageResource(I)V

    :goto_0
    return-void
.end method

.method public setZoomRatioIndex(I)V
    .locals 0

    iput p1, p0, Lcom/android/camera/ui/zoom/ZoomRatioView;->mZoomRatioIndex:I

    return-void
.end method
