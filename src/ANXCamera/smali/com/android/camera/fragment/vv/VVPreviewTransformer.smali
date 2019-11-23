.class public Lcom/android/camera/fragment/vv/VVPreviewTransformer;
.super Ljava/lang/Object;
.source "VVPreviewTransformer.java"

# interfaces
.implements Landroid/support/v4/view/ViewPager$PageTransformer;


# static fields
.field private static final MAX_ALPHA:F = 1.0f

.field private static final MAX_SCALE:F = 1.0f

.field private static final MIN_ALPHA:F = 1.0f

.field private static final MIN_SCALE:F = 0.92f

.field private static final alphaSlope:F = 0.0f

.field private static final scaleSlope:F = 0.07999998f


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public transformPage(Landroid/view/View;F)V
    .locals 3

    const/high16 v0, -0x40800000    # -1.0f

    cmpg-float v1, p2, v0

    const/high16 v2, 0x3f800000    # 1.0f

    if-gez v1, :cond_0

    nop

    move p2, v0

    goto :goto_0

    :cond_0
    cmpl-float v0, p2, v2

    if-lez v0, :cond_1

    nop

    move p2, v2

    :cond_1
    :goto_0
    const/4 v0, 0x0

    cmpg-float v1, p2, v0

    if-gez v1, :cond_2

    add-float/2addr p2, v2

    goto :goto_1

    :cond_2
    sub-float p2, v2, p2

    :goto_1
    mul-float/2addr v0, p2

    add-float/2addr v2, v0

    invoke-static {p1, v2}, Landroid/support/v4/view/ViewCompat;->setAlpha(Landroid/view/View;F)V

    const v0, 0x3f6b851f    # 0.92f

    const v1, 0x3da3d708    # 0.07999998f

    mul-float/2addr p2, v1

    add-float/2addr v0, p2

    invoke-static {p1, v0}, Landroid/support/v4/view/ViewCompat;->setScaleX(Landroid/view/View;F)V

    return-void
.end method
