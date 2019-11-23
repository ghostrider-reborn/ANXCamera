.class final enum Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;
.super Ljava/lang/Enum;
.source "MediaCodecVideoEncoder.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lorg/webrtc/videocodec/MediaCodecVideoEncoder;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x401a
    name = "CodecName"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

.field public static final enum EXYNOX_VP8:Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

.field public static final enum GOOGLE_VPX:Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

.field public static final enum HWH264:Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

.field public static final enum ON2_VP8:Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;


# direct methods
.method static constructor <clinit>()V
    .locals 6

    new-instance v0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

    const-string v1, "HWH264"

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;->HWH264:Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

    new-instance v0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

    const-string v1, "ON2_VP8"

    const/4 v3, 0x1

    invoke-direct {v0, v1, v3}, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;->ON2_VP8:Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

    new-instance v0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

    const-string v1, "GOOGLE_VPX"

    const/4 v4, 0x2

    invoke-direct {v0, v1, v4}, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;->GOOGLE_VPX:Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

    new-instance v0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

    const-string v1, "EXYNOX_VP8"

    const/4 v5, 0x3

    invoke-direct {v0, v1, v5}, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;->EXYNOX_VP8:Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

    const/4 v0, 0x4

    new-array v0, v0, [Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

    sget-object v1, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;->HWH264:Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

    aput-object v1, v0, v2

    sget-object v1, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;->ON2_VP8:Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

    aput-object v1, v0, v3

    sget-object v1, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;->GOOGLE_VPX:Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

    aput-object v1, v0, v4

    sget-object v1, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;->EXYNOX_VP8:Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

    aput-object v1, v0, v5

    sput-object v0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;->$VALUES:[Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;
    .locals 1

    const-class v0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

    return-object p0
.end method

.method public static values()[Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;
    .locals 1

    sget-object v0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;->$VALUES:[Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

    invoke-virtual {v0}, [Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lorg/webrtc/videocodec/MediaCodecVideoEncoder$CodecName;

    return-object v0
.end method
