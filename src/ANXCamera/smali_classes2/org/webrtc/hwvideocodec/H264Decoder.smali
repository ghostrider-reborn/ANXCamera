.class Lorg/webrtc/hwvideocodec/H264Decoder;
.super Ljava/lang/Object;
.source "MediaCodecVideoDecoder.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lorg/webrtc/hwvideocodec/H264Decoder$DecoderProperties;
    }
.end annotation


# static fields
.field private static final AVC_MIME_TYPE:Ljava/lang/String; = "video/avc"

.field private static final COLOR_QCOM_FORMATYUV420PackedSemiPlanar32m:I = 0x7fa30c04

.field private static final DEQUEUE_TIMEOUT:I = 0x186a0

.field private static final HEVC_MIME_TYPE:Ljava/lang/String; = "video/hevc"

.field private static final TAG:Ljava/lang/String; = "H264Decoder"

.field private static final supportedColorList:[I

.field private static final supportedHwCodecPrefixes:[Ljava/lang/String;


# instance fields
.field private colorFormat:I

.field counter:I

.field private cropBottom:I

.field private cropLeft:I

.field private cropRight:I

.field private cropTop:I

.field private dequedBufferIndex:I

.field private drop_frame:Z

.field frame_rate:I

.field private height:I

.field private inputBuffers:[Ljava/nio/ByteBuffer;

.field last_drop:Z

.field private mediaCodec:Landroid/media/MediaCodec;

.field private nativeContext:J

.field next_want_time:J

.field private outputBuffers:[Ljava/nio/ByteBuffer;

.field private outputColorFormat:I

.field private outputThread:Ljava/lang/Thread;

.field pre_time:J

.field private queue:Ljava/util/Queue;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Queue<",
            "Ljava/lang/Integer;",
            ">;"
        }
    .end annotation
.end field

.field private volatile running:Z

.field private sliceHeight:I

.field private stride:I

.field sum_time:I

.field timeStamp:J

.field private width:I


# direct methods
.method static constructor <clinit>()V
    .locals 8

    const-string v0, "OMX.qcom."

    const-string v1, "OMX.Nvidia."

    const-string v2, "OMX.IMG.TOPAZ"

    const-string v3, "OMX.Exynos"

    const-string v4, "OMX.MTK"

    const-string v5, "OMX.hantro"

    const-string v6, "OMX.Intel"

    const-string v7, "OMX.IMG.MSVDX"

    filled-new-array/range {v0 .. v7}, [Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lorg/webrtc/hwvideocodec/H264Decoder;->supportedHwCodecPrefixes:[Ljava/lang/String;

    const/4 v0, 0x4

    new-array v0, v0, [I

    fill-array-data v0, :array_0

    sput-object v0, Lorg/webrtc/hwvideocodec/H264Decoder;->supportedColorList:[I

    return-void

    nop

    :array_0
    .array-data 4
        0x13
        0x15
        0x7fa30c00
        0x7fa30c04
    .end array-data
.end method

.method public constructor <init>()V
    .locals 3

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, -0x1

    iput v0, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->dequedBufferIndex:I

    const/4 v0, 0x0

    iput v0, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->counter:I

    new-instance v1, Ljava/util/LinkedList;

    invoke-direct {v1}, Ljava/util/LinkedList;-><init>()V

    iput-object v1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->queue:Ljava/util/Queue;

    const/16 v1, 0x1e

    iput v1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->frame_rate:I

    iput v0, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->sum_time:I

    const-wide/16 v1, 0x0

    iput-wide v1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->next_want_time:J

    iput-wide v1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->pre_time:J

    iput-boolean v0, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->last_drop:Z

    return-void
.end method

.method static synthetic access$000(Lorg/webrtc/hwvideocodec/H264Decoder;)Z
    .locals 0

    iget-boolean p0, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->running:Z

    return p0
.end method

.method private averageFrameRate(J)V
    .locals 4

    :try_start_0
    iget-boolean v0, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->drop_frame:Z

    if-nez v0, :cond_0

    return-void

    :cond_0
    iget-wide v0, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->pre_time:J

    const-wide/16 v2, 0x0

    cmp-long v0, v0, v2

    if-eqz v0, :cond_2

    iget-wide v0, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->pre_time:J

    sub-long v0, p1, v0

    long-to-int v0, v0

    iget-object v1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->queue:Ljava/util/Queue;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-interface {v1, v2}, Ljava/util/Queue;->offer(Ljava/lang/Object;)Z

    iget v1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->sum_time:I

    add-int/2addr v1, v0

    iput v1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->sum_time:I

    iget-object v0, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->queue:Ljava/util/Queue;

    invoke-interface {v0}, Ljava/util/Queue;->size()I

    move-result v0

    const/16 v1, 0x19

    if-le v0, v1, :cond_1

    iget v0, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->sum_time:I

    iget-object v1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->queue:Ljava/util/Queue;

    invoke-interface {v1}, Ljava/util/Queue;->poll()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/Integer;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v1

    sub-int/2addr v0, v1

    iput v0, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->sum_time:I

    :cond_1
    iget-object v0, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->queue:Ljava/util/Queue;

    invoke-interface {v0}, Ljava/util/Queue;->size()I

    move-result v0

    mul-int/lit16 v0, v0, 0x3e8

    iget v1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->sum_time:I

    div-int/2addr v0, v1

    iput v0, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->frame_rate:I

    :cond_2
    iput-wide p1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->pre_time:J
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    nop

    return-void

    :catch_0
    move-exception p1

    const-string p2, "H264Decoder"

    const-string v0, "find exception at averageFrameRate:"

    invoke-static {p2, v0, p1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    return-void
.end method

.method private createOutputThread()Ljava/lang/Thread;
    .locals 2

    new-instance v0, Lorg/webrtc/hwvideocodec/H264Decoder$1;

    const-string v1, "Mediacodec_outputThread"

    invoke-direct {v0, p0, v1}, Lorg/webrtc/hwvideocodec/H264Decoder$1;-><init>(Lorg/webrtc/hwvideocodec/H264Decoder;Ljava/lang/String;)V

    return-object v0
.end method

.method private dequeueInputBuffer()I
    .locals 3

    :try_start_0
    iget-object v0, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->mediaCodec:Landroid/media/MediaCodec;

    const-wide/32 v1, 0x186a0

    invoke-virtual {v0, v1, v2}, Landroid/media/MediaCodec;->dequeueInputBuffer(J)I

    move-result v0
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return v0

    :catch_0
    move-exception v0

    const-string v1, "H264Decoder"

    const-string v2, "find exception at dequeueIntputBuffer:"

    invoke-static {v1, v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    const/4 v0, -0x2

    return v0
.end method

.method private dequeueOutputBuffer()I
    .locals 11

    const/4 v0, -0x2

    :try_start_0
    new-instance v1, Landroid/media/MediaCodec$BufferInfo;

    invoke-direct {v1}, Landroid/media/MediaCodec$BufferInfo;-><init>()V

    iget-object v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->mediaCodec:Landroid/media/MediaCodec;

    const-wide/32 v3, 0x186a0

    invoke-virtual {v2, v1, v3, v4}, Landroid/media/MediaCodec;->dequeueOutputBuffer(Landroid/media/MediaCodec$BufferInfo;J)I

    move-result v2

    iget-wide v5, v1, Landroid/media/MediaCodec$BufferInfo;->presentationTimeUs:J

    iput-wide v5, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->timeStamp:J

    :goto_0
    const/4 v5, -0x3

    if-eq v2, v5, :cond_2

    if-ne v2, v0, :cond_0

    goto :goto_1

    :cond_0
    iget v1, v1, Landroid/media/MediaCodec$BufferInfo;->flags:I

    and-int/lit8 v0, v1, 0x4

    if-eqz v0, :cond_1

    return v5

    :cond_1
    return v2

    :cond_2
    :goto_1
    if-ne v2, v5, :cond_3

    iget-object v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {v2}, Landroid/media/MediaCodec;->getOutputBuffers()[Ljava/nio/ByteBuffer;

    move-result-object v2

    iput-object v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->outputBuffers:[Ljava/nio/ByteBuffer;

    goto/16 :goto_4

    :cond_3
    if-ne v2, v0, :cond_d

    iget-object v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {v2}, Landroid/media/MediaCodec;->getOutputFormat()Landroid/media/MediaFormat;

    move-result-object v2

    const-string v5, "H264Decoder"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Format changed: "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Landroid/media/MediaFormat;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const-string v5, "width"

    invoke-virtual {v2, v5}, Landroid/media/MediaFormat;->getInteger(Ljava/lang/String;)I

    move-result v5

    iput v5, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->width:I

    const-string v5, "height"

    invoke-virtual {v2, v5}, Landroid/media/MediaFormat;->getInteger(Ljava/lang/String;)I

    move-result v5

    iput v5, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->height:I

    const-string v5, "H264Decoder"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "new width: "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v7, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->width:I

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v7, "new height:"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v7, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->height:I

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const-string v5, "crop-top"

    invoke-virtual {v2, v5}, Landroid/media/MediaFormat;->containsKey(Ljava/lang/String;)Z

    move-result v5

    if-eqz v5, :cond_4

    const-string v5, "crop-top"

    invoke-virtual {v2, v5}, Landroid/media/MediaFormat;->getInteger(Ljava/lang/String;)I

    move-result v5

    iput v5, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->cropTop:I

    const-string v5, "H264Decoder"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Crop-top:"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v7, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->cropTop:I

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_4
    const-string v5, "crop-bottom"

    invoke-virtual {v2, v5}, Landroid/media/MediaFormat;->containsKey(Ljava/lang/String;)Z

    move-result v5

    if-eqz v5, :cond_5

    const-string v5, "crop-bottom"

    invoke-virtual {v2, v5}, Landroid/media/MediaFormat;->getInteger(Ljava/lang/String;)I

    move-result v5

    iput v5, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->cropBottom:I

    const-string v5, "H264Decoder"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Crop-bottom:"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v7, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->cropBottom:I

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_5
    const-string v5, "crop-left"

    invoke-virtual {v2, v5}, Landroid/media/MediaFormat;->containsKey(Ljava/lang/String;)Z

    move-result v5

    if-eqz v5, :cond_6

    const-string v5, "crop-left"

    invoke-virtual {v2, v5}, Landroid/media/MediaFormat;->getInteger(Ljava/lang/String;)I

    move-result v5

    iput v5, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->cropLeft:I

    const-string v5, "H264Decoder"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Crop-left:"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v7, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->cropLeft:I

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_6
    const-string v5, "crop-right"

    invoke-virtual {v2, v5}, Landroid/media/MediaFormat;->containsKey(Ljava/lang/String;)Z

    move-result v5

    if-eqz v5, :cond_7

    const-string v5, "crop-right"

    invoke-virtual {v2, v5}, Landroid/media/MediaFormat;->getInteger(Ljava/lang/String;)I

    move-result v5

    iput v5, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->cropRight:I

    const-string v5, "H264Decoder"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Crop-right:"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v7, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->cropRight:I

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_7
    const-string v5, "color-format"

    invoke-virtual {v2, v5}, Landroid/media/MediaFormat;->containsKey(Ljava/lang/String;)Z

    move-result v5

    if-eqz v5, :cond_a

    const-string v5, "color-format"

    invoke-virtual {v2, v5}, Landroid/media/MediaFormat;->getInteger(Ljava/lang/String;)I

    move-result v5

    iput v5, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->colorFormat:I

    iput v5, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->outputColorFormat:I

    const-string v5, "H264Decoder"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Color: 0x"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v7, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->colorFormat:I

    invoke-static {v7}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    nop

    sget-object v5, Lorg/webrtc/hwvideocodec/H264Decoder;->supportedColorList:[I

    array-length v6, v5

    const/4 v7, 0x0

    move v8, v7

    :goto_2
    if-ge v8, v6, :cond_9

    aget v9, v5, v8

    iget v10, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->colorFormat:I

    if-ne v10, v9, :cond_8

    nop

    nop

    const/4 v7, 0x1

    goto :goto_3

    :cond_8
    add-int/lit8 v8, v8, 0x1

    goto :goto_2

    :cond_9
    :goto_3
    if-nez v7, :cond_a

    const-string v1, "H264Decoder"

    const-string v2, "Non supported color format"

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return v0

    :cond_a
    const-string v5, "stride"

    invoke-virtual {v2, v5}, Landroid/media/MediaFormat;->containsKey(Ljava/lang/String;)Z

    move-result v5

    if-eqz v5, :cond_b

    const-string v5, "stride"

    invoke-virtual {v2, v5}, Landroid/media/MediaFormat;->getInteger(Ljava/lang/String;)I

    move-result v5

    iput v5, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->stride:I

    :cond_b
    const-string v5, "slice-height"

    invoke-virtual {v2, v5}, Landroid/media/MediaFormat;->containsKey(Ljava/lang/String;)Z

    move-result v5

    if-eqz v5, :cond_c

    const-string v5, "slice-height"

    invoke-virtual {v2, v5}, Landroid/media/MediaFormat;->getInteger(Ljava/lang/String;)I

    move-result v2

    iput v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->sliceHeight:I

    :cond_c
    const-string v2, "H264Decoder"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "Frame stride and slice height: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v6, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->stride:I

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v6, " x "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v6, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->sliceHeight:I

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v2, v5}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    iget v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->width:I

    iget v5, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->stride:I

    invoke-static {v2, v5}, Ljava/lang/Math;->max(II)I

    move-result v2

    iput v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->stride:I

    iget v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->height:I

    iget v5, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->sliceHeight:I

    invoke-static {v2, v5}, Ljava/lang/Math;->max(II)I

    move-result v2

    iput v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->sliceHeight:I

    :cond_d
    :goto_4
    iget-object v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {v2, v1, v3, v4}, Landroid/media/MediaCodec;->dequeueOutputBuffer(Landroid/media/MediaCodec$BufferInfo;J)I

    move-result v2

    iget-wide v5, v1, Landroid/media/MediaCodec$BufferInfo;->presentationTimeUs:J

    iput-wide v5, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->timeStamp:J
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto/16 :goto_0

    :catch_0
    move-exception v1

    const-string v2, "H264Decoder"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "find exception at dequeueOutputBuffer:"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v2, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return v0
.end method

.method private dropFrame(IJ)Z
    .locals 6

    const/16 v0, 0x78

    const/4 v1, 0x0

    :try_start_0
    invoke-static {p1, v0}, Ljava/lang/Math;->min(II)I

    move-result p1

    const/16 v0, 0x8

    invoke-static {p1, v0}, Ljava/lang/Math;->max(II)I

    move-result p1

    const/16 v0, 0x3e8

    div-int/2addr v0, p1

    nop

    iget-wide v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->next_want_time:J

    sub-long v2, p2, v2

    const-wide/16 v4, 0x0

    cmp-long p1, v2, v4

    if-lez p1, :cond_0

    iget-wide v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->next_want_time:J

    cmp-long p1, v2, v4

    if-eqz p1, :cond_0

    const/4 p1, 0x1

    goto :goto_0

    :cond_0
    nop

    move p1, v1

    :goto_0
    iget-wide v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->next_want_time:J

    cmp-long v2, v2, v4

    if-nez v2, :cond_1

    int-to-long v2, v0

    add-long/2addr v2, p2

    iput-wide v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->next_want_time:J

    goto :goto_1

    :cond_1
    iget-wide v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->next_want_time:J

    int-to-long v4, v0

    add-long/2addr v2, v4

    iput-wide v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->next_want_time:J

    :goto_1
    iget-wide v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->next_want_time:J

    sub-long v2, p2, v2

    invoke-static {v2, v3}, Ljava/lang/Math;->abs(J)J

    move-result-wide v2

    const/4 v4, 0x3

    mul-int/2addr v4, v0

    int-to-long v4, v4

    cmp-long v2, v2, v4

    if-lez v2, :cond_2

    int-to-long v2, v0

    add-long/2addr p2, v2

    iput-wide p2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->next_want_time:J
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    :cond_2
    return p1

    :catch_0
    move-exception p1

    const-string p2, "H264Decoder"

    const-string p3, "find exception at averageFrameRate:"

    invoke-static {p2, p3, p1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    return v1
.end method

.method private static findHwDecoder(Ljava/lang/String;)Lorg/webrtc/hwvideocodec/H264Decoder$DecoderProperties;
    .locals 17

    move-object/from16 v0, p0

    const/4 v1, 0x0

    :try_start_0
    sget v2, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v3, 0x13

    if-ge v2, v3, :cond_0

    const-string v0, "H264Decoder"

    const-string v2, "sdk version too low"

    invoke-static {v0, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    return-object v1

    :cond_0
    const/4 v3, 0x0

    :goto_0
    invoke-static {}, Landroid/media/MediaCodecList;->getCodecCount()I

    move-result v4

    if-ge v3, v4, :cond_b

    invoke-static {v3}, Landroid/media/MediaCodecList;->getCodecInfoAt(I)Landroid/media/MediaCodecInfo;

    move-result-object v4

    invoke-virtual {v4}, Landroid/media/MediaCodecInfo;->isEncoder()Z

    move-result v5

    if-eqz v5, :cond_1

    goto/16 :goto_8

    :cond_1
    nop

    invoke-virtual {v4}, Landroid/media/MediaCodecInfo;->getSupportedTypes()[Ljava/lang/String;

    move-result-object v5

    array-length v6, v5

    const/4 v7, 0x0

    :goto_1
    if-ge v7, v6, :cond_3

    aget-object v8, v5, v7

    invoke-virtual {v8, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_2

    const-string v5, "H264Decoder"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "mimeType is "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {v4}, Landroid/media/MediaCodecInfo;->getName()Ljava/lang/String;

    move-result-object v5

    const-string v6, "H264Decoder"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "name is  "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_2

    :cond_2
    add-int/lit8 v7, v7, 0x1

    goto :goto_1

    :cond_3
    move-object v5, v1

    :goto_2
    if-nez v5, :cond_4

    goto/16 :goto_8

    :cond_4
    const-string v6, "H264Decoder"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "Found candidate decoder "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {v4, v0}, Landroid/media/MediaCodecInfo;->getCapabilitiesForType(Ljava/lang/String;)Landroid/media/MediaCodecInfo$CodecCapabilities;

    move-result-object v4

    iget-object v6, v4, Landroid/media/MediaCodecInfo$CodecCapabilities;->colorFormats:[I

    array-length v7, v6

    const/4 v8, 0x0

    :goto_3
    if-ge v8, v7, :cond_5

    aget v9, v6, v8

    const-string v10, "H264Decoder"

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    const-string v12, "   Color: 0x"

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {v9}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v11, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v10, v9}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    add-int/lit8 v8, v8, 0x1

    goto :goto_3

    :cond_5
    sget-object v6, Lorg/webrtc/hwvideocodec/H264Decoder;->supportedHwCodecPrefixes:[Ljava/lang/String;

    array-length v7, v6

    const/4 v8, 0x0

    :goto_4
    if-ge v8, v7, :cond_a

    aget-object v9, v6, v8

    const-string v10, "H264Decoder"

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    const-string v12, " hwCodecPrefix :"

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v11, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v11

    invoke-static {v10, v11}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {v5, v9}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v9

    if-nez v9, :cond_6

    goto :goto_7

    :cond_6
    sget-object v9, Lorg/webrtc/hwvideocodec/H264Decoder;->supportedColorList:[I

    array-length v10, v9

    const/4 v11, 0x0

    :goto_5
    if-ge v11, v10, :cond_9

    aget v12, v9, v11

    iget-object v13, v4, Landroid/media/MediaCodecInfo$CodecCapabilities;->colorFormats:[I

    array-length v14, v13

    const/4 v15, 0x0

    :goto_6
    if-ge v15, v14, :cond_8

    aget v2, v13, v15

    if-ne v2, v12, :cond_7

    const-string v0, "H264Decoder"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Found target decoder "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v4, ". Color: 0x"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {v2}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v0, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    new-instance v0, Lorg/webrtc/hwvideocodec/H264Decoder$DecoderProperties;

    invoke-direct {v0, v5, v2}, Lorg/webrtc/hwvideocodec/H264Decoder$DecoderProperties;-><init>(Ljava/lang/String;I)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return-object v0

    :cond_7
    add-int/lit8 v15, v15, 0x1

    goto :goto_6

    :cond_8
    add-int/lit8 v11, v11, 0x1

    goto :goto_5

    :cond_9
    :goto_7
    add-int/lit8 v8, v8, 0x1

    goto :goto_4

    :cond_a
    :goto_8
    add-int/lit8 v3, v3, 0x1

    goto/16 :goto_0

    :cond_b
    return-object v1

    :catch_0
    move-exception v0

    const-string v2, "H264Decoder"

    const-string v3, "find exception at findHwDecoder:"

    invoke-static {v2, v3, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    return-object v1
.end method

.method private static isPlatformSupported()Z
    .locals 1

    const-string v0, "video/avc"

    invoke-static {v0}, Lorg/webrtc/hwvideocodec/H264Decoder;->findHwDecoder(Ljava/lang/String;)Lorg/webrtc/hwvideocodec/H264Decoder$DecoderProperties;

    move-result-object v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method private queueInputBuffer(IIJ)Z
    .locals 9

    const/4 v0, 0x0

    :try_start_0
    iget-object v1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->inputBuffers:[Ljava/nio/ByteBuffer;

    aget-object v1, v1, p1

    invoke-virtual {v1, v0}, Ljava/nio/ByteBuffer;->position(I)Ljava/nio/Buffer;

    iget-object v1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->inputBuffers:[Ljava/nio/ByteBuffer;

    aget-object v1, v1, p1

    invoke-virtual {v1, p2}, Ljava/nio/ByteBuffer;->limit(I)Ljava/nio/Buffer;

    iget-object v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->mediaCodec:Landroid/media/MediaCodec;

    const/4 v4, 0x0

    const/4 v8, 0x0

    move v3, p1

    move v5, p2

    move-wide v6, p3

    invoke-virtual/range {v2 .. v8}, Landroid/media/MediaCodec;->queueInputBuffer(IIIJI)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    const/4 p1, 0x1

    return p1

    :catch_0
    move-exception p1

    const-string p2, "H264Decoder"

    const-string p3, "find exception at queueInputBuffer:"

    invoke-static {p2, p3, p1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    return v0
.end method

.method private releaseOutputBuffer(I)Z
    .locals 3

    const/4 v0, 0x0

    :try_start_0
    iget-object v1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {v1, p1, v0}, Landroid/media/MediaCodec;->releaseOutputBuffer(IZ)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    const/4 p1, 0x1

    return p1

    :catch_0
    move-exception p1

    const-string v1, "H264Decoder"

    const-string v2, "find exception at releaseOutputBuffer:"

    invoke-static {v1, v2, p1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    return v0
.end method


# virtual methods
.method public native DeliverFrame(Ljava/nio/ByteBuffer;JIIIIIIIJI)V
.end method

.method public decodeFrameInputStream(Lorg/webrtc/hwvideocodec/H264I420Frame;)Z
    .locals 5

    const/4 v0, 0x0

    :try_start_0
    invoke-direct {p0}, Lorg/webrtc/hwvideocodec/H264Decoder;->dequeueInputBuffer()I

    move-result v1

    nop

    if-ltz v1, :cond_0

    iget-object v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->inputBuffers:[Ljava/nio/ByteBuffer;

    aget-object v2, v2, v1

    invoke-virtual {v2}, Ljava/nio/ByteBuffer;->clear()Ljava/nio/Buffer;

    iget-object v3, p1, Lorg/webrtc/hwvideocodec/H264I420Frame;->buffer:Ljava/nio/ByteBuffer;

    invoke-virtual {v2, v3}, Ljava/nio/ByteBuffer;->put(Ljava/nio/ByteBuffer;)Ljava/nio/ByteBuffer;

    iget v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->counter:I

    add-int/lit8 v2, v2, 0x1

    iput v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->counter:I

    iget v2, p1, Lorg/webrtc/hwvideocodec/H264I420Frame;->size:I

    iget-wide v3, p1, Lorg/webrtc/hwvideocodec/H264I420Frame;->timeStamp:J

    invoke-direct {p0, v1, v2, v3, v4}, Lorg/webrtc/hwvideocodec/H264Decoder;->queueInputBuffer(IIJ)Z

    move-result p1

    goto :goto_0

    :cond_0
    const-string p1, "H264Decoder"

    const-string v1, " get inputBuffer error, maybe discard a frame"

    invoke-static {p1, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    move p1, v0

    :goto_0
    return p1

    :catch_0
    move-exception p1

    const-string v1, "H264Decoder"

    const-string v2, "find exception at decodeFrameInputStream:"

    invoke-static {v1, v2, p1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    return v0
.end method

.method public decodeFramePushPicture(Z)V
    .locals 21

    move-object/from16 v15, p0

    nop

    if-eqz p1, :cond_0

    :try_start_0
    const-string v0, "H264Decoder"

    const-string v1, "flush decoder output queue"

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    :catch_0
    move-exception v0

    goto/16 :goto_7

    :cond_0
    :goto_0
    invoke-direct/range {p0 .. p0}, Lorg/webrtc/hwvideocodec/H264Decoder;->dequeueOutputBuffer()I

    move-result v0

    const/16 v16, 0x0

    const/16 v17, 0x1

    if-ltz v0, :cond_1

    move/from16 v1, v17

    goto :goto_1

    :cond_1
    nop

    move/from16 v1, v16

    :goto_1
    const/4 v14, -0x3

    if-eqz p1, :cond_3

    if-ne v0, v14, :cond_2

    move/from16 v1, v17

    goto :goto_2

    :cond_2
    nop

    move/from16 v1, v16

    :cond_3
    :goto_2
    const/16 v2, 0x12c

    move/from16 v18, v2

    :goto_3
    if-eqz v1, :cond_9

    if-lez v18, :cond_9

    if-ltz v0, :cond_5

    iget v1, v15, Lorg/webrtc/hwvideocodec/H264Decoder;->counter:I

    add-int/lit8 v1, v1, -0x1

    iput v1, v15, Lorg/webrtc/hwvideocodec/H264Decoder;->counter:I

    iget-wide v1, v15, Lorg/webrtc/hwvideocodec/H264Decoder;->nativeContext:J

    const-wide/16 v3, 0x0

    cmp-long v1, v1, v3

    if-eqz v1, :cond_4

    iget-object v1, v15, Lorg/webrtc/hwvideocodec/H264Decoder;->outputBuffers:[Ljava/nio/ByteBuffer;

    aget-object v2, v1, v0

    iget-wide v3, v15, Lorg/webrtc/hwvideocodec/H264Decoder;->nativeContext:J

    iget v5, v15, Lorg/webrtc/hwvideocodec/H264Decoder;->width:I

    iget v6, v15, Lorg/webrtc/hwvideocodec/H264Decoder;->height:I

    iget v7, v15, Lorg/webrtc/hwvideocodec/H264Decoder;->stride:I

    iget v8, v15, Lorg/webrtc/hwvideocodec/H264Decoder;->cropTop:I

    iget v9, v15, Lorg/webrtc/hwvideocodec/H264Decoder;->cropBottom:I

    iget v10, v15, Lorg/webrtc/hwvideocodec/H264Decoder;->cropLeft:I

    iget v11, v15, Lorg/webrtc/hwvideocodec/H264Decoder;->cropRight:I

    iget-wide v12, v15, Lorg/webrtc/hwvideocodec/H264Decoder;->timeStamp:J

    iget v1, v15, Lorg/webrtc/hwvideocodec/H264Decoder;->outputColorFormat:I

    move/from16 v19, v1

    move-object v1, v15

    move/from16 v14, v19

    invoke-virtual/range {v1 .. v14}, Lorg/webrtc/hwvideocodec/H264Decoder;->DeliverFrame(Ljava/nio/ByteBuffer;JIIIIIIIJI)V

    :cond_4
    invoke-direct {v15, v0}, Lorg/webrtc/hwvideocodec/H264Decoder;->releaseOutputBuffer(I)Z
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_4

    :cond_5
    const-wide/16 v0, 0xa

    :try_start_1
    invoke-static {v0, v1}, Ljava/lang/Thread;->sleep(J)V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_1

    add-int/lit8 v18, v18, -0xa

    goto :goto_4

    :catch_1
    move-exception v0

    move-object v1, v0

    :try_start_2
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    :goto_4
    invoke-direct/range {p0 .. p0}, Lorg/webrtc/hwvideocodec/H264Decoder;->dequeueOutputBuffer()I

    move-result v0
    :try_end_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_0

    if-ltz v0, :cond_6

    move/from16 v1, v17

    goto :goto_5

    :cond_6
    nop

    move/from16 v1, v16

    :goto_5
    if-eqz p1, :cond_8

    const/4 v2, -0x3

    if-eq v0, v2, :cond_7

    move/from16 v1, v17

    goto :goto_6

    :cond_7
    nop

    move/from16 v1, v16

    :goto_6
    move v14, v2

    goto :goto_3

    :cond_8
    const/4 v14, -0x3

    goto :goto_3

    :cond_9
    return-void

    :goto_7
    nop

    const-string v1, "H264Decoder"

    const-string v2, "find exception at decodeFramePushPicture:"

    invoke-static {v1, v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    return-void
.end method

.method public deliverDecodedFrame()V
    .locals 16

    move-object/from16 v0, p0

    :try_start_0
    invoke-direct/range {p0 .. p0}, Lorg/webrtc/hwvideocodec/H264Decoder;->dequeueOutputBuffer()I

    move-result v15

    if-ltz v15, :cond_2

    iget-wide v1, v0, Lorg/webrtc/hwvideocodec/H264Decoder;->nativeContext:J

    const-wide/16 v3, 0x0

    cmp-long v1, v1, v3

    if-eqz v1, :cond_1

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v1

    invoke-direct {v0, v1, v2}, Lorg/webrtc/hwvideocodec/H264Decoder;->averageFrameRate(J)V

    iget-boolean v1, v0, Lorg/webrtc/hwvideocodec/H264Decoder;->drop_frame:Z

    if-eqz v1, :cond_0

    iget-boolean v1, v0, Lorg/webrtc/hwvideocodec/H264Decoder;->last_drop:Z

    if-nez v1, :cond_0

    iget v1, v0, Lorg/webrtc/hwvideocodec/H264Decoder;->frame_rate:I

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v2

    invoke-direct {v0, v1, v2, v3}, Lorg/webrtc/hwvideocodec/H264Decoder;->dropFrame(IJ)Z

    move-result v1

    if-eqz v1, :cond_0

    const-string v1, "H264Decoder"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "drop this frame! frame rate: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v3, v0, Lorg/webrtc/hwvideocodec/H264Decoder;->frame_rate:I

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v1, 0x1

    iput-boolean v1, v0, Lorg/webrtc/hwvideocodec/H264Decoder;->last_drop:Z

    goto :goto_0

    :cond_0
    iget-object v1, v0, Lorg/webrtc/hwvideocodec/H264Decoder;->outputBuffers:[Ljava/nio/ByteBuffer;

    aget-object v2, v1, v15

    iget-wide v3, v0, Lorg/webrtc/hwvideocodec/H264Decoder;->nativeContext:J

    iget v5, v0, Lorg/webrtc/hwvideocodec/H264Decoder;->width:I

    iget v6, v0, Lorg/webrtc/hwvideocodec/H264Decoder;->height:I

    iget v7, v0, Lorg/webrtc/hwvideocodec/H264Decoder;->stride:I

    iget v8, v0, Lorg/webrtc/hwvideocodec/H264Decoder;->cropTop:I

    iget v9, v0, Lorg/webrtc/hwvideocodec/H264Decoder;->cropBottom:I

    iget v10, v0, Lorg/webrtc/hwvideocodec/H264Decoder;->cropLeft:I

    iget v11, v0, Lorg/webrtc/hwvideocodec/H264Decoder;->cropRight:I

    iget-wide v12, v0, Lorg/webrtc/hwvideocodec/H264Decoder;->timeStamp:J

    iget v14, v0, Lorg/webrtc/hwvideocodec/H264Decoder;->outputColorFormat:I

    move-object v1, v0

    invoke-virtual/range {v1 .. v14}, Lorg/webrtc/hwvideocodec/H264Decoder;->DeliverFrame(Ljava/nio/ByteBuffer;JIIIIIIIJI)V

    const/4 v1, 0x0

    iput-boolean v1, v0, Lorg/webrtc/hwvideocodec/H264Decoder;->last_drop:Z

    :cond_1
    :goto_0
    invoke-direct {v0, v15}, Lorg/webrtc/hwvideocodec/H264Decoder;->releaseOutputBuffer(I)Z
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    :cond_2
    nop

    return-void

    :catch_0
    move-exception v0

    const-string v1, "H264Decoder"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "find exception at deliverOutPutsTimer:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public flush()Z
    .locals 1

    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lorg/webrtc/hwvideocodec/H264Decoder;->decodeFramePushPicture(Z)V

    return v0
.end method

.method public initDecoder(IIIIZJ)Z
    .locals 7
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    const/4 v0, 0x0

    :try_start_0
    const-string v1, "H264Decoder"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "decoder init with:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v3, " height:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v3, " getWidth:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, " context:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p6, p7}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    const-string v3, " frameRate:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    nop

    const/4 v1, 0x0

    const/4 v2, 0x1

    if-nez p1, :cond_0

    const-string p1, "video/avc"

    goto :goto_0

    :cond_0
    if-ne p1, v2, :cond_1

    const-string p1, "video/hevc"

    goto :goto_0

    :cond_1
    move-object p1, v1

    :goto_0
    invoke-static {p1}, Lorg/webrtc/hwvideocodec/H264Decoder;->findHwDecoder(Ljava/lang/String;)Lorg/webrtc/hwvideocodec/H264Decoder$DecoderProperties;

    move-result-object v3

    if-nez v3, :cond_2

    return v0

    :cond_2
    const-string v4, "H264Decoder"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "Java initDecode: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v6, " x "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v6, " drop_frame "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, p5}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v6, ". Color: 0x"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v6, v3, Lorg/webrtc/hwvideocodec/H264Decoder$DecoderProperties;->colorFormat:I

    invoke-static {v6}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iput p2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->width:I

    iput p3, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->height:I

    iput-boolean p5, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->drop_frame:Z

    iput p2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->stride:I

    iput p3, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->sliceHeight:I

    iput v0, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->cropTop:I

    add-int/lit8 p5, p3, -0x1

    iput p5, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->cropBottom:I

    iput v0, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->cropLeft:I

    add-int/lit8 p5, p2, -0x1

    iput p5, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->cropRight:I

    const/4 p5, -0x1

    iput p5, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->dequedBufferIndex:I

    iput-wide p6, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->nativeContext:J

    const-wide/16 p5, 0x0

    iput-wide p5, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->timeStamp:J

    invoke-static {p1, p2, p3}, Landroid/media/MediaFormat;->createVideoFormat(Ljava/lang/String;II)Landroid/media/MediaFormat;

    move-result-object p1

    if-nez p4, :cond_3

    const/16 p4, 0x3c

    :cond_3
    const-string p2, "frame-rate"

    int-to-float p3, p4

    invoke-virtual {p1, p2, p3}, Landroid/media/MediaFormat;->setFloat(Ljava/lang/String;F)V

    iget-object p2, v3, Lorg/webrtc/hwvideocodec/H264Decoder$DecoderProperties;->codecName:Ljava/lang/String;

    invoke-static {p2}, Landroid/media/MediaCodec;->createByCodecName(Ljava/lang/String;)Landroid/media/MediaCodec;

    move-result-object p2

    iput-object p2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->mediaCodec:Landroid/media/MediaCodec;

    iget-object p2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->mediaCodec:Landroid/media/MediaCodec;

    if-nez p2, :cond_4

    const-string p1, "hevc decoder"

    const-string p2, "decoder init error null"

    invoke-static {p1, p2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    return v0

    :cond_4
    iget-object p2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {p2, p1, v1, v1, v0}, Landroid/media/MediaCodec;->configure(Landroid/media/MediaFormat;Landroid/view/Surface;Landroid/media/MediaCrypto;I)V

    iget-object p1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {p1}, Landroid/media/MediaCodec;->start()V

    iget p1, v3, Lorg/webrtc/hwvideocodec/H264Decoder$DecoderProperties;->colorFormat:I

    iput p1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->colorFormat:I

    iget-object p1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {p1}, Landroid/media/MediaCodec;->getOutputBuffers()[Ljava/nio/ByteBuffer;

    move-result-object p1

    iput-object p1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->outputBuffers:[Ljava/nio/ByteBuffer;

    iget-object p1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {p1}, Landroid/media/MediaCodec;->getInputBuffers()[Ljava/nio/ByteBuffer;

    move-result-object p1

    iput-object p1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->inputBuffers:[Ljava/nio/ByteBuffer;

    const-string p1, "H264Decoder"

    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string p3, "Input buffers: "

    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object p3, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->inputBuffers:[Ljava/nio/ByteBuffer;

    array-length p3, p3

    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p3, ". Output buffers: "

    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object p3, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->outputBuffers:[Ljava/nio/ByteBuffer;

    array-length p3, p3

    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-static {p1, p2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {p1}, Landroid/media/MediaCodec;->getOutputFormat()Landroid/media/MediaFormat;

    move-result-object p1

    const-string p2, "color-format"

    invoke-virtual {p1, p2}, Landroid/media/MediaFormat;->containsKey(Ljava/lang/String;)Z

    move-result p2

    if-eqz p2, :cond_5

    const-string p2, "color-format"

    invoke-virtual {p1, p2}, Landroid/media/MediaFormat;->getInteger(Ljava/lang/String;)I

    move-result p1

    iput p1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->outputColorFormat:I

    :cond_5
    iput-boolean v2, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->running:Z

    invoke-direct {p0}, Lorg/webrtc/hwvideocodec/H264Decoder;->createOutputThread()Ljava/lang/Thread;

    move-result-object p1

    iput-object p1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->outputThread:Ljava/lang/Thread;

    iget-object p1, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->outputThread:Ljava/lang/Thread;

    invoke-virtual {p1}, Ljava/lang/Thread;->start()V

    const-string p1, "H264Decoder"

    const-string p2, "decoder init done"

    invoke-static {p1, p2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return v2

    :catch_0
    move-exception p1

    const-string p2, "H264Decoder"

    const-string p3, "find exception at initDecode :"

    invoke-static {p2, p3, p1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    return v0
.end method

.method public release()V
    .locals 3

    :try_start_0
    const-string v0, "H264Decoder"

    const-string v1, "decoder release begin"

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x0

    iput-boolean v0, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->running:Z

    iget-object v0, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {v0}, Landroid/media/MediaCodec;->stop()V

    iget-object v0, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {v0}, Landroid/media/MediaCodec;->release()V

    const/4 v0, 0x0

    iput-object v0, p0, Lorg/webrtc/hwvideocodec/H264Decoder;->mediaCodec:Landroid/media/MediaCodec;

    const-string v0, "H264Decoder"

    const-string v1, "decoder release done"

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    const-string v1, "H264Decoder"

    const-string v2, "find exception at release:"

    invoke-static {v1, v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :goto_0
    return-void
.end method
