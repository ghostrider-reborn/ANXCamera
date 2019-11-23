.class Lorg/webrtc/hwvideocodec/H264Encoder;
.super Ljava/lang/Object;
.source "MediaCodecVideoEncoder.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lorg/webrtc/hwvideocodec/H264Encoder$EncoderProperties;
    }
.end annotation


# static fields
.field private static final AVC_MIME_TYPE:Ljava/lang/String; = "video/avc"

.field private static final HEVC_MIME_TYPE:Ljava/lang/String; = "video/hevc"

.field private static final TAG:Ljava/lang/String; = "H264Encoder"

.field private static final VIDEO_ControlRateConstant:I = 0x2

.field private static isQcomPlatform:Z

.field private static final supportedColorList:[I

.field private static final supportedHwCodecPrefixes:[Ljava/lang/String;


# instance fields
.field private Constructed:Z

.field counter:I

.field private dequedBufferIndex:I

.field frameCounter:I

.field m_height:I

.field m_info:[B

.field m_width:I

.field private mediaCodec:Landroid/media/MediaCodec;

.field private nativeContext:J

.field private supportColorFormat:I


# direct methods
.method static constructor <clinit>()V
    .locals 9

    const/4 v0, 0x0

    sput-boolean v0, Lorg/webrtc/hwvideocodec/H264Encoder;->isQcomPlatform:Z

    const/4 v0, 0x2

    new-array v0, v0, [I

    fill-array-data v0, :array_0

    sput-object v0, Lorg/webrtc/hwvideocodec/H264Encoder;->supportedColorList:[I

    const-string v1, "OMX.qcom."

    const-string v2, "OMX.Nvidia."

    const-string v3, "OMX.IMG.TOPAZ"

    const-string v4, "OMX.Exynos"

    const-string v5, "OMX.MTK"

    const-string v6, "OMX.hantro"

    const-string v7, "OMX.Intel"

    const-string v8, "OMX.ARM"

    filled-new-array/range {v1 .. v8}, [Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lorg/webrtc/hwvideocodec/H264Encoder;->supportedHwCodecPrefixes:[Ljava/lang/String;

    return-void

    :array_0
    .array-data 4
        0x15
        0x13
    .end array-data
.end method

.method constructor <init>()V
    .locals 2

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    iput-object v0, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->m_info:[B

    const/4 v0, 0x0

    iput v0, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->counter:I

    iput v0, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->frameCounter:I

    iput-boolean v0, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->Constructed:Z

    const/4 v1, -0x1

    iput v1, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->dequedBufferIndex:I

    iput v0, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->supportColorFormat:I

    const-wide/16 v0, 0x0

    iput-wide v0, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->nativeContext:J

    return-void
.end method

.method public static byteArrayToInt([B)I
    .locals 4

    nop

    const/4 v0, 0x0

    move v1, v0

    :goto_0
    const/4 v2, 0x4

    if-ge v0, v2, :cond_0

    rsub-int/lit8 v2, v0, 0x3

    mul-int/lit8 v2, v2, 0x8

    aget-byte v3, p0, v0

    and-int/lit16 v3, v3, 0xff

    shl-int v2, v3, v2

    add-int/2addr v1, v2

    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_0
    return v1
.end method

.method private static findHwEncoder(Ljava/lang/String;)Lorg/webrtc/hwvideocodec/H264Encoder$EncoderProperties;
    .locals 17

    move-object/from16 v0, p0

    const/4 v1, 0x0

    :try_start_0
    const-string v2, "H264Encoder"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "sdk version is: "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget v4, Landroid/os/Build$VERSION;->SDK_INT:I

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    sget v2, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v3, 0x10

    if-ge v2, v3, :cond_0

    return-object v1

    :cond_0
    const/4 v2, 0x0

    move v3, v2

    :goto_0
    invoke-static {}, Landroid/media/MediaCodecList;->getCodecCount()I

    move-result v4

    if-ge v3, v4, :cond_3

    invoke-static {v3}, Landroid/media/MediaCodecList;->getCodecInfoAt(I)Landroid/media/MediaCodecInfo;

    move-result-object v4

    invoke-virtual {v4}, Landroid/media/MediaCodecInfo;->isEncoder()Z

    move-result v5

    if-nez v5, :cond_1

    goto :goto_2

    :cond_1
    invoke-virtual {v4}, Landroid/media/MediaCodecInfo;->getSupportedTypes()[Ljava/lang/String;

    move-result-object v5

    array-length v6, v5

    move v7, v2

    :goto_1
    if-ge v7, v6, :cond_2

    aget-object v8, v5, v7

    const-string v9, "H264Encoder"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "codec name: "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v10, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v8, " company:"

    invoke-virtual {v10, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Landroid/media/MediaCodecInfo;->getName()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v10, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v9, v8}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    add-int/lit8 v7, v7, 0x1

    goto :goto_1

    :cond_2
    :goto_2
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    :cond_3
    move v3, v2

    :goto_3
    invoke-static {}, Landroid/media/MediaCodecList;->getCodecCount()I

    move-result v4

    if-ge v3, v4, :cond_e

    invoke-static {v3}, Landroid/media/MediaCodecList;->getCodecInfoAt(I)Landroid/media/MediaCodecInfo;

    move-result-object v4

    invoke-virtual {v4}, Landroid/media/MediaCodecInfo;->isEncoder()Z

    move-result v5

    if-nez v5, :cond_4

    goto/16 :goto_b

    :cond_4
    nop

    invoke-virtual {v4}, Landroid/media/MediaCodecInfo;->getSupportedTypes()[Ljava/lang/String;

    move-result-object v5

    array-length v6, v5

    move v7, v2

    :goto_4
    if-ge v7, v6, :cond_6

    aget-object v8, v5, v7

    const-string v9, "H264Encoder"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "codec name: "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v10, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {v8, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    if-eqz v8, :cond_5

    invoke-virtual {v4}, Landroid/media/MediaCodecInfo;->getName()Ljava/lang/String;

    move-result-object v5

    goto :goto_5

    :cond_5
    add-int/lit8 v7, v7, 0x1

    goto :goto_4

    :cond_6
    move-object v5, v1

    :goto_5
    if-nez v5, :cond_7

    goto/16 :goto_b

    :cond_7
    const-string v6, "H264Encoder"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "Found candidate encoder "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {v4, v0}, Landroid/media/MediaCodecInfo;->getCapabilitiesForType(Ljava/lang/String;)Landroid/media/MediaCodecInfo$CodecCapabilities;

    move-result-object v4

    iget-object v6, v4, Landroid/media/MediaCodecInfo$CodecCapabilities;->colorFormats:[I

    array-length v7, v6

    move v8, v2

    :goto_6
    if-ge v8, v7, :cond_8

    aget v9, v6, v8

    const-string v10, "H264Encoder"

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    const-string v12, "   Color: 0x"

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {v9}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v11, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v10, v9}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    add-int/lit8 v8, v8, 0x1

    goto :goto_6

    :cond_8
    sget-object v6, Lorg/webrtc/hwvideocodec/H264Encoder;->supportedHwCodecPrefixes:[Ljava/lang/String;

    aget-object v6, v6, v2

    invoke-virtual {v5, v6}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v6

    sput-boolean v6, Lorg/webrtc/hwvideocodec/H264Encoder;->isQcomPlatform:Z

    sget-object v6, Lorg/webrtc/hwvideocodec/H264Encoder;->supportedHwCodecPrefixes:[Ljava/lang/String;

    array-length v7, v6

    move v8, v2

    :goto_7
    if-ge v8, v7, :cond_d

    aget-object v9, v6, v8

    invoke-virtual {v5, v9}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v9

    if-nez v9, :cond_9

    goto :goto_a

    :cond_9
    sget-object v9, Lorg/webrtc/hwvideocodec/H264Encoder;->supportedColorList:[I

    array-length v10, v9

    move v11, v2

    :goto_8
    if-ge v11, v10, :cond_c

    aget v12, v9, v11

    iget-object v13, v4, Landroid/media/MediaCodecInfo$CodecCapabilities;->colorFormats:[I

    array-length v14, v13

    move v15, v2

    :goto_9
    if-ge v15, v14, :cond_b

    aget v2, v13, v15

    if-ne v2, v12, :cond_a

    const-string v0, "H264Encoder"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Found target encoder "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v4, ". Color: 0x"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {v2}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v0, v3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    new-instance v0, Lorg/webrtc/hwvideocodec/H264Encoder$EncoderProperties;

    invoke-direct {v0, v5, v2}, Lorg/webrtc/hwvideocodec/H264Encoder$EncoderProperties;-><init>(Ljava/lang/String;I)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return-object v0

    :cond_a
    add-int/lit8 v15, v15, 0x1

    const/4 v2, 0x0

    goto :goto_9

    :cond_b
    add-int/lit8 v11, v11, 0x1

    const/4 v2, 0x0

    goto :goto_8

    :cond_c
    :goto_a
    add-int/lit8 v8, v8, 0x1

    const/4 v2, 0x0

    goto :goto_7

    :cond_d
    :goto_b
    add-int/lit8 v3, v3, 0x1

    const/4 v2, 0x0

    goto/16 :goto_3

    :cond_e
    return-object v1

    :catch_0
    move-exception v0

    const-string v2, "H264Encoder"

    const-string v3, "find exception at findHwEncoder:"

    invoke-static {v2, v3, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    return-object v1
.end method

.method public static intToByteArray(I[B)V
    .locals 2

    shr-int/lit8 v0, p0, 0x18

    and-int/lit16 v0, v0, 0xff

    int-to-byte v0, v0

    const/4 v1, 0x0

    aput-byte v0, p1, v1

    shr-int/lit8 v0, p0, 0x10

    and-int/lit16 v0, v0, 0xff

    int-to-byte v0, v0

    const/4 v1, 0x1

    aput-byte v0, p1, v1

    shr-int/lit8 v0, p0, 0x8

    and-int/lit16 v0, v0, 0xff

    int-to-byte v0, v0

    const/4 v1, 0x2

    aput-byte v0, p1, v1

    and-int/lit16 p0, p0, 0xff

    int-to-byte p0, p0

    const/4 v0, 0x3

    aput-byte p0, p1, v0

    return-void
.end method

.method private static isPlatformSupported()Z
    .locals 1

    const-string v0, "video/avc"

    invoke-static {v0}, Lorg/webrtc/hwvideocodec/H264Encoder;->findHwEncoder(Ljava/lang/String;)Lorg/webrtc/hwvideocodec/H264Encoder$EncoderProperties;

    move-result-object v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method


# virtual methods
.method public native SendFrame([BJJZ)V
.end method

.method public encodeFrameInput(Lorg/webrtc/hwvideocodec/H264I420Frame;Z)Z
    .locals 11

    const/4 v0, 0x0

    if-eqz p2, :cond_0

    :try_start_0
    const-string v1, "H264Encoder"

    const-string v2, "force a key frame"

    invoke-static {v1, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    :catch_0
    move-exception p1

    goto :goto_1

    :cond_0
    :goto_0
    iget-object v1, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->mediaCodec:Landroid/media/MediaCodec;

    const-wide/16 v2, -0x1

    invoke-virtual {v1, v2, v3}, Landroid/media/MediaCodec;->dequeueInputBuffer(J)I

    move-result v5

    if-eqz p2, :cond_1

    sget p2, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x13

    if-lt p2, v1, :cond_1

    new-instance p2, Landroid/os/Bundle;

    invoke-direct {p2}, Landroid/os/Bundle;-><init>()V

    const-string v1, "request-sync"

    invoke-virtual {p2, v1, v0}, Landroid/os/Bundle;->putInt(Ljava/lang/String;I)V

    iget-object v1, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {v1, p2}, Landroid/media/MediaCodec;->setParameters(Landroid/os/Bundle;)V

    :cond_1
    iget p2, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->counter:I

    const/4 v1, 0x1

    add-int/2addr p2, v1

    iput p2, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->counter:I

    if-gez v5, :cond_2

    return v0

    :cond_2
    iget-object p2, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {p2}, Landroid/media/MediaCodec;->getInputBuffers()[Ljava/nio/ByteBuffer;

    move-result-object p2

    iget v2, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->frameCounter:I

    add-int/2addr v2, v1

    iput v2, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->frameCounter:I

    aget-object p2, p2, v5

    invoke-virtual {p2}, Ljava/nio/ByteBuffer;->clear()Ljava/nio/Buffer;

    iget-object v2, p1, Lorg/webrtc/hwvideocodec/H264I420Frame;->buffer:Ljava/nio/ByteBuffer;

    invoke-virtual {p2, v2}, Ljava/nio/ByteBuffer;->put(Ljava/nio/ByteBuffer;)Ljava/nio/ByteBuffer;

    iget-object v4, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->mediaCodec:Landroid/media/MediaCodec;

    const/4 v6, 0x0

    iget v7, p1, Lorg/webrtc/hwvideocodec/H264I420Frame;->size:I

    iget-wide p1, p1, Lorg/webrtc/hwvideocodec/H264I420Frame;->timeStamp:J

    const-wide/16 v2, 0x3e8

    mul-long v8, p1, v2

    const/4 v10, 0x0

    invoke-virtual/range {v4 .. v10}, Landroid/media/MediaCodec;->queueInputBuffer(IIIJI)V

    invoke-virtual {p0, v0}, Lorg/webrtc/hwvideocodec/H264Encoder;->encodeFrameOutput(Z)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return v1

    :goto_1
    nop

    const-string p2, "H264Encoder"

    const-string v1, "find exception at encodeFrameInput encoder:"

    invoke-static {p2, v1, p1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    return v0
.end method

.method public encodeFrameOutput(Z)V
    .locals 18

    move-object/from16 v8, p0

    :try_start_0
    new-instance v9, Landroid/media/MediaCodec$BufferInfo;

    invoke-direct {v9}, Landroid/media/MediaCodec$BufferInfo;-><init>()V

    nop

    nop

    nop

    nop

    if-eqz p1, :cond_0

    const-string v0, "H264Encoder"

    const-string v1, "flush output queue"

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    :cond_0
    iget-object v0, v8, Lorg/webrtc/hwvideocodec/H264Encoder;->mediaCodec:Landroid/media/MediaCodec;

    const-wide/16 v10, 0x0

    invoke-virtual {v0, v9, v10, v11}, Landroid/media/MediaCodec;->dequeueOutputBuffer(Landroid/media/MediaCodec$BufferInfo;J)I

    move-result v0

    const/4 v12, 0x0

    const/4 v13, 0x1

    if-ltz v0, :cond_1

    move v1, v13

    goto :goto_0

    :cond_1
    nop

    move v1, v12

    :goto_0
    const/4 v14, 0x4

    if-eqz p1, :cond_3

    iget v1, v9, Landroid/media/MediaCodec$BufferInfo;->flags:I

    and-int/2addr v1, v14

    if-nez v1, :cond_2

    move v1, v13

    goto :goto_1

    :cond_2
    nop

    move v1, v12

    :cond_3
    :goto_1
    const/16 v2, 0x12c

    move v15, v2

    :cond_4
    :goto_2
    if-eqz v1, :cond_a

    if-lez v15, :cond_a

    if-ltz v0, :cond_7

    iget v1, v9, Landroid/media/MediaCodec$BufferInfo;->size:I

    iget-wide v2, v9, Landroid/media/MediaCodec$BufferInfo;->presentationTimeUs:J

    const-wide/16 v4, 0x3e8

    div-long v5, v2, v4

    iget-object v2, v8, Lorg/webrtc/hwvideocodec/H264Encoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {v2}, Landroid/media/MediaCodec;->getOutputBuffers()[Ljava/nio/ByteBuffer;

    move-result-object v2

    new-array v3, v1, [B

    iget v1, v8, Lorg/webrtc/hwvideocodec/H264Encoder;->frameCounter:I

    sub-int/2addr v1, v13

    iput v1, v8, Lorg/webrtc/hwvideocodec/H264Encoder;->frameCounter:I

    aget-object v1, v2, v0

    invoke-virtual {v1, v3}, Ljava/nio/ByteBuffer;->get([B)Ljava/nio/ByteBuffer;

    aget-byte v1, v3, v14

    and-int/lit8 v1, v1, 0x1f

    const/4 v2, 0x5

    if-lt v1, v2, :cond_5

    const/16 v2, 0x8

    if-gt v1, v2, :cond_5

    move v7, v13

    goto :goto_3

    :cond_5
    nop

    move v7, v12

    :goto_3
    if-eqz v7, :cond_6

    const-string v1, "H264Encoder"

    const-string v2, "h264 add frame header  cdr flag"

    invoke-static {v1, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    :cond_6
    iget-wide v1, v8, Lorg/webrtc/hwvideocodec/H264Encoder;->nativeContext:J

    move-wide/from16 v16, v1

    move-object v1, v8

    move-object v2, v3

    move-wide/from16 v3, v16

    invoke-virtual/range {v1 .. v7}, Lorg/webrtc/hwvideocodec/H264Encoder;->SendFrame([BJJZ)V

    iget-object v1, v8, Lorg/webrtc/hwvideocodec/H264Encoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {v1, v0, v12}, Landroid/media/MediaCodec;->releaseOutputBuffer(IZ)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_1

    goto :goto_4

    :cond_7
    const-wide/16 v0, 0xa

    :try_start_1
    invoke-static {v0, v1}, Ljava/lang/Thread;->sleep(J)V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    add-int/lit8 v15, v15, -0xa

    goto :goto_4

    :catch_0
    move-exception v0

    :try_start_2
    const-string v1, "H264Encoder"

    const-string v2, "find exception at ThreadSleep:"

    move-object v3, v0

    invoke-static {v1, v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :goto_4
    iget-object v0, v8, Lorg/webrtc/hwvideocodec/H264Encoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {v0, v9, v10, v11}, Landroid/media/MediaCodec;->dequeueOutputBuffer(Landroid/media/MediaCodec$BufferInfo;J)I

    move-result v0

    if-ltz v0, :cond_8

    move v1, v13

    goto :goto_5

    :cond_8
    nop

    move v1, v12

    :goto_5
    if-eqz p1, :cond_4

    iget v1, v9, Landroid/media/MediaCodec$BufferInfo;->flags:I
    :try_end_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_1

    and-int/2addr v1, v14

    if-nez v1, :cond_9

    move v1, v13

    goto :goto_2

    :cond_9
    nop

    move v1, v12

    goto :goto_2

    :cond_a
    return-void

    :catch_1
    move-exception v0

    const-string v1, "H264Encoder"

    const-string v2, "find exception at encodeFrameOutput:"

    invoke-static {v1, v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    return-void
.end method

.method public flush()Z
    .locals 1

    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lorg/webrtc/hwvideocodec/H264Encoder;->encodeFrameOutput(Z)V

    return v0
.end method

.method public initEncoder(IIIIIJZ)Z
    .locals 4

    const/4 v0, 0x0

    :try_start_0
    const-string v1, "H264Encoder"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "H264 encoder creat width"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v3, "height:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v3, "framerate:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v3, "bitrate:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v3, "this:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

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
    iput p2, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->m_width:I

    iput p3, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->m_height:I

    iput-wide p6, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->nativeContext:J

    iput-object v1, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->m_info:[B

    const/4 p6, -0x1

    iput p6, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->dequedBufferIndex:I

    invoke-static {p1}, Lorg/webrtc/hwvideocodec/H264Encoder;->findHwEncoder(Ljava/lang/String;)Lorg/webrtc/hwvideocodec/H264Encoder$EncoderProperties;

    move-result-object p6

    if-nez p6, :cond_2

    const-string p1, "H264Encoder"

    const-string p2, "Can not find HW AVC encoder"

    invoke-static {p1, p2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    return v0

    :cond_2
    iget-object p7, p6, Lorg/webrtc/hwvideocodec/H264Encoder$EncoderProperties;->codecName:Ljava/lang/String;

    invoke-static {p7}, Landroid/media/MediaCodec;->createByCodecName(Ljava/lang/String;)Landroid/media/MediaCodec;

    move-result-object p7

    iput-object p7, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->mediaCodec:Landroid/media/MediaCodec;

    iget-object p7, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->mediaCodec:Landroid/media/MediaCodec;

    if-nez p7, :cond_3

    const-string p1, "H264Encoder"

    const-string p2, "creatByCodecName failed"

    invoke-static {p1, p2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    return v0

    :cond_3
    invoke-static {p1, p2, p3}, Landroid/media/MediaFormat;->createVideoFormat(Ljava/lang/String;II)Landroid/media/MediaFormat;

    move-result-object p1

    const-string p2, "bitrate"

    invoke-virtual {p1, p2, p5}, Landroid/media/MediaFormat;->setInteger(Ljava/lang/String;I)V

    const-string p2, "frame-rate"

    invoke-virtual {p1, p2, p4}, Landroid/media/MediaFormat;->setInteger(Ljava/lang/String;I)V

    const/4 p2, 0x2

    if-nez p8, :cond_4

    const-string p3, "bitrate-mode"

    invoke-virtual {p1, p3, p2}, Landroid/media/MediaFormat;->setInteger(Ljava/lang/String;I)V

    :cond_4
    iget p3, p6, Lorg/webrtc/hwvideocodec/H264Encoder$EncoderProperties;->colorFormat:I

    iput p3, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->supportColorFormat:I

    const-string p3, "color-format"

    iget p4, p6, Lorg/webrtc/hwvideocodec/H264Encoder$EncoderProperties;->colorFormat:I

    invoke-virtual {p1, p3, p4}, Landroid/media/MediaFormat;->setInteger(Ljava/lang/String;I)V

    const-string p3, "i-frame-interval"

    invoke-virtual {p1, p3, p2}, Landroid/media/MediaFormat;->setInteger(Ljava/lang/String;I)V

    iget-object p2, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {p2, p1, v1, v1, v2}, Landroid/media/MediaCodec;->configure(Landroid/media/MediaFormat;Landroid/view/Surface;Landroid/media/MediaCrypto;I)V

    iget-object p1, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {p1}, Landroid/media/MediaCodec;->start()V

    iput-boolean v2, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->Constructed:Z

    const-string p1, "H264Encoder"

    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string p3, "avc encoder creat done, isSemiPlanar:"

    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget p3, p6, Lorg/webrtc/hwvideocodec/H264Encoder$EncoderProperties;->colorFormat:I

    const/16 p4, 0x15

    if-ne p3, p4, :cond_5

    move p3, v2

    goto :goto_1

    :cond_5
    move p3, v0

    :goto_1
    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-static {p1, p2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return v2

    :catch_0
    move-exception p1

    const-string p2, "H264Encoder"

    const-string p3, "find exception at initEncoder:"

    invoke-static {p2, p3, p1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    return v0
.end method

.method public isSemiPlanarSupport()Z
    .locals 2

    iget v0, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->supportColorFormat:I

    const/16 v1, 0x15

    if-ne v0, v1, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public release()V
    .locals 3

    const/4 v0, 0x0

    :try_start_0
    iput-boolean v0, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->Constructed:Z

    const-string v0, "H264Encoder"

    const-string v1, "avc encoder release begin"

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {v0}, Landroid/media/MediaCodec;->stop()V

    iget-object v0, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {v0}, Landroid/media/MediaCodec;->release()V

    const/4 v0, 0x0

    iput-object v0, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->mediaCodec:Landroid/media/MediaCodec;

    iput-object v0, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->m_info:[B

    const-string v0, "H264Encoder"

    const-string v1, "avc encoder release done"

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    const-string v1, "H264Encoder"

    const-string v2, "find exception at release encoder:"

    invoke-static {v1, v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :goto_0
    return-void
.end method

.method public reset()V
    .locals 3

    :try_start_0
    iget-boolean v0, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->Constructed:Z

    if-nez v0, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {v0}, Landroid/media/MediaCodec;->flush()V

    const-string v0, "H264Encoder"

    const-string v1, "avc encoder reset done"

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    const-string v1, "H264Encoder"

    const-string v2, "find exception at reset encoder:"

    invoke-static {v1, v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :goto_0
    return-void
.end method

.method public setBitrate(I)V
    .locals 3

    :try_start_0
    iget-boolean v0, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->Constructed:Z

    if-nez v0, :cond_0

    return-void

    :cond_0
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x13

    if-ge v0, v1, :cond_1

    return-void

    :cond_1
    const-string v0, "H264Encoder"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "setRates: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, " kbps "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    new-instance v0, Landroid/os/Bundle;

    invoke-direct {v0}, Landroid/os/Bundle;-><init>()V

    const-string v1, "video-bitrate"

    invoke-virtual {v0, v1, p1}, Landroid/os/Bundle;->putInt(Ljava/lang/String;I)V

    iget-object p1, p0, Lorg/webrtc/hwvideocodec/H264Encoder;->mediaCodec:Landroid/media/MediaCodec;

    invoke-virtual {p1, v0}, Landroid/media/MediaCodec;->setParameters(Landroid/os/Bundle;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return-void

    :catch_0
    move-exception p1

    const-string v0, "H264Encoder"

    const-string v1, "find exception at setBitrate encoder:"

    invoke-static {v0, v1, p1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    return-void
.end method
