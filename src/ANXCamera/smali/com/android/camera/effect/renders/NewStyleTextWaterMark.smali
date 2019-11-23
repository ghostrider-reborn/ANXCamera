.class public Lcom/android/camera/effect/renders/NewStyleTextWaterMark;
.super Lcom/android/camera/effect/renders/WaterMark;
.source "NewStyleTextWaterMark.java"


# static fields
.field private static final RATIO:F = 0.87f

.field private static final TAG:Ljava/lang/String;

.field public static final TEXT_COLOR:I = -0x1

.field public static final TEXT_PIXEL_SIZE:F = 30.079576f


# instance fields
.field private mCenterX:I

.field private mCenterY:I

.field private mCharMargin:I

.field private mIsMiMovie:Z

.field private mPadding:I

.field private mPaddingX:I

.field private mPaddingY:I

.field private mWaterHeight:I

.field private mWaterText:Ljava/lang/String;

.field private mWaterTexture:Lcom/android/gallery3d/ui/BasicTexture;

.field private mWaterWidth:I


# direct methods
.method static constructor <clinit>()V
    .locals 1

    const-class v0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;

    invoke-virtual {v0}, Ljava/lang/Class;->getSimpleName()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->TAG:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>(Ljava/lang/String;IIIZ)V
    .locals 6

    invoke-direct {p0, p2, p3, p4}, Lcom/android/camera/effect/renders/WaterMark;-><init>(III)V

    iput-boolean p5, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mIsMiMovie:Z

    nop

    nop

    invoke-static {p2, p3}, Ljava/lang/Math;->min(II)I

    move-result p2

    int-to-float p2, p2

    const/high16 p3, 0x44870000    # 1080.0f

    div-float/2addr p2, p3

    iget-boolean p3, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mIsMiMovie:Z

    const-wide v0, 0x4045d7efb3f3733bL    # 43.687002653

    if-eqz p3, :cond_2

    invoke-static {}, Lcom/android/camera/Util;->getMiMovieMargin()I

    move-result p3

    const/16 p5, 0x5a

    if-eq p4, p5, :cond_1

    const/16 p5, 0x10e

    if-ne p4, p5, :cond_0

    goto :goto_0

    :cond_0
    int-to-double p3, p3

    add-double/2addr p3, v0

    goto :goto_1

    :cond_1
    :goto_0
    int-to-double p3, p3

    add-double/2addr p3, v0

    move-wide v4, p3

    move-wide p3, v0

    move-wide v0, v4

    goto :goto_1

    :cond_2
    move-wide p3, v0

    :goto_1
    iput-object p1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterText:Ljava/lang/String;

    iget-object p1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterText:Ljava/lang/String;

    const p5, 0x41f0a2f9

    mul-float/2addr p5, p2

    const/4 v2, -0x1

    const/4 v3, 0x2

    invoke-static {p1, p5, v2, v3}, Lcom/android/gallery3d/ui/StringTexture;->newInstance(Ljava/lang/String;FII)Lcom/android/gallery3d/ui/StringTexture;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterTexture:Lcom/android/gallery3d/ui/BasicTexture;

    iget-object p1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterTexture:Lcom/android/gallery3d/ui/BasicTexture;

    invoke-virtual {p1}, Lcom/android/gallery3d/ui/BasicTexture;->getWidth()I

    move-result p1

    iput p1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterWidth:I

    iget-object p1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterTexture:Lcom/android/gallery3d/ui/BasicTexture;

    invoke-virtual {p1}, Lcom/android/gallery3d/ui/BasicTexture;->getHeight()I

    move-result p1

    iput p1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterHeight:I

    iget p1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterHeight:I

    int-to-float p1, p1

    const p5, 0x3e051eb8    # 0.13f

    mul-float/2addr p1, p5

    const/high16 p5, 0x40000000    # 2.0f

    div-float/2addr p1, p5

    float-to-int p1, p1

    iput p1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mCharMargin:I

    float-to-double p1, p2

    mul-double/2addr v0, p1

    invoke-static {v0, v1}, Ljava/lang/Math;->round(D)J

    move-result-wide v0

    long-to-int p5, v0

    and-int/lit8 p5, p5, -0x2

    iput p5, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mPaddingX:I

    mul-double/2addr p3, p1

    invoke-static {p3, p4}, Ljava/lang/Math;->round(D)J

    move-result-wide p1

    long-to-int p1, p1

    iget p2, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mCharMargin:I

    sub-int/2addr p1, p2

    and-int/lit8 p1, p1, -0x2

    iput p1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mPaddingY:I

    invoke-direct {p0}, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->calcCenterAxis()V

    sget-boolean p1, Lcom/android/camera/Util;->sIsDumpLog:Z

    if-eqz p1, :cond_3

    invoke-direct {p0}, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->print()V

    :cond_3
    return-void
.end method

.method private calcCenterAxis()V
    .locals 2

    iget v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mOrientation:I

    if-eqz v0, :cond_3

    const/16 v1, 0x5a

    if-eq v0, v1, :cond_2

    const/16 v1, 0xb4

    if-eq v0, v1, :cond_1

    const/16 v1, 0x10e

    if-eq v0, v1, :cond_0

    goto :goto_0

    :cond_0
    iget v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mPaddingY:I

    iget v1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterHeight:I

    div-int/lit8 v1, v1, 0x2

    add-int/2addr v0, v1

    iput v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mCenterX:I

    iget v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mPictureHeight:I

    iget v1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mPaddingX:I

    sub-int/2addr v0, v1

    iget v1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterWidth:I

    div-int/lit8 v1, v1, 0x2

    sub-int/2addr v0, v1

    iput v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mCenterY:I

    goto :goto_0

    :cond_1
    iget v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mPaddingX:I

    iget v1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterWidth:I

    div-int/lit8 v1, v1, 0x2

    add-int/2addr v0, v1

    iput v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mCenterX:I

    iget v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mPaddingY:I

    iget v1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterHeight:I

    div-int/lit8 v1, v1, 0x2

    add-int/2addr v0, v1

    iput v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mCenterY:I

    goto :goto_0

    :cond_2
    iget v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mPictureWidth:I

    iget v1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mPaddingY:I

    sub-int/2addr v0, v1

    iget v1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterHeight:I

    div-int/lit8 v1, v1, 0x2

    sub-int/2addr v0, v1

    iput v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mCenterX:I

    iget v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mPaddingX:I

    iget v1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterWidth:I

    div-int/lit8 v1, v1, 0x2

    add-int/2addr v0, v1

    iput v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mCenterY:I

    goto :goto_0

    :cond_3
    iget v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mPictureWidth:I

    iget v1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mPaddingX:I

    sub-int/2addr v0, v1

    iget v1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterWidth:I

    div-int/lit8 v1, v1, 0x2

    sub-int/2addr v0, v1

    iput v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mCenterX:I

    iget v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mPictureHeight:I

    iget v1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mPaddingY:I

    sub-int/2addr v0, v1

    iget v1, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterHeight:I

    div-int/lit8 v1, v1, 0x2

    sub-int/2addr v0, v1

    iput v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mCenterY:I

    nop

    :goto_0
    return-void
.end method

.method private print()V
    .locals 3

    sget-object v0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "WaterMark pictureWidth="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mPictureWidth:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, " pictureHeight ="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mPictureHeight:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, " waterText="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterText:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, " centerX="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mCenterX:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, " centerY="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mCenterY:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, " waterWidth="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterWidth:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, " waterHeight="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterHeight:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, " padding="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mPadding:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method


# virtual methods
.method public getCenterX()I
    .locals 1

    iget v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mCenterX:I

    return v0
.end method

.method public getCenterY()I
    .locals 1

    iget v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mCenterY:I

    return v0
.end method

.method public getHeight()I
    .locals 1

    iget v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterHeight:I

    return v0
.end method

.method public bridge synthetic getLeft()I
    .locals 1

    invoke-super {p0}, Lcom/android/camera/effect/renders/WaterMark;->getLeft()I

    move-result v0

    return v0
.end method

.method public getPaddingX()I
    .locals 1

    iget v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mPaddingX:I

    return v0
.end method

.method public getPaddingY()I
    .locals 1

    iget v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mPaddingY:I

    return v0
.end method

.method public getTexture()Lcom/android/gallery3d/ui/BasicTexture;
    .locals 1

    iget-object v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterTexture:Lcom/android/gallery3d/ui/BasicTexture;

    return-object v0
.end method

.method public bridge synthetic getTop()I
    .locals 1

    invoke-super {p0}, Lcom/android/camera/effect/renders/WaterMark;->getTop()I

    move-result v0

    return v0
.end method

.method public getWidth()I
    .locals 1

    iget v0, p0, Lcom/android/camera/effect/renders/NewStyleTextWaterMark;->mWaterWidth:I

    return v0
.end method
