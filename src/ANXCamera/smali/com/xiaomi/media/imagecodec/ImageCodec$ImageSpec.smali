.class public final Lcom/xiaomi/media/imagecodec/ImageCodec$ImageSpec;
.super Ljava/lang/Object;
.source "ImageCodec.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/xiaomi/media/imagecodec/ImageCodec;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = "ImageSpec"
.end annotation


# instance fields
.field public final format:I

.field public final height:I

.field public final width:I


# direct methods
.method private constructor <init>(III)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput p1, p0, Lcom/xiaomi/media/imagecodec/ImageCodec$ImageSpec;->width:I

    iput p2, p0, Lcom/xiaomi/media/imagecodec/ImageCodec$ImageSpec;->height:I

    iput p3, p0, Lcom/xiaomi/media/imagecodec/ImageCodec$ImageSpec;->format:I

    return-void
.end method

.method synthetic constructor <init>(IIILcom/xiaomi/media/imagecodec/ImageCodec$1;)V
    .locals 0

    invoke-direct {p0, p1, p2, p3}, Lcom/xiaomi/media/imagecodec/ImageCodec$ImageSpec;-><init>(III)V

    return-void
.end method


# virtual methods
.method public toString()Ljava/lang/String;
    .locals 5

    sget-object v0, Ljava/util/Locale;->US:Ljava/util/Locale;

    const-string v1, "ImageSpec(%d, %d, %d)"

    const/4 v2, 0x3

    new-array v2, v2, [Ljava/lang/Object;

    iget v3, p0, Lcom/xiaomi/media/imagecodec/ImageCodec$ImageSpec;->width:I

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    const/4 v4, 0x0

    aput-object v3, v2, v4

    iget v3, p0, Lcom/xiaomi/media/imagecodec/ImageCodec$ImageSpec;->height:I

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    const/4 v4, 0x1

    aput-object v3, v2, v4

    iget v3, p0, Lcom/xiaomi/media/imagecodec/ImageCodec$ImageSpec;->format:I

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    const/4 v4, 0x2

    aput-object v3, v2, v4

    invoke-static {v0, v1, v2}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method
