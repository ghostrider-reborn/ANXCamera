.class Lorg/webrtc/videocodec/MediaCodecVideoEncoder$EncodeHandler;
.super Landroid/os/Handler;
.source "MediaCodecVideoEncoder.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lorg/webrtc/videocodec/MediaCodecVideoEncoder;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "EncodeHandler"
.end annotation


# instance fields
.field final synthetic this$0:Lorg/webrtc/videocodec/MediaCodecVideoEncoder;


# direct methods
.method constructor <init>(Lorg/webrtc/videocodec/MediaCodecVideoEncoder;)V
    .locals 0

    iput-object p1, p0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$EncodeHandler;->this$0:Lorg/webrtc/videocodec/MediaCodecVideoEncoder;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 4

    :try_start_0
    iget p1, p1, Landroid/os/Message;->what:I

    const-wide/16 v0, 0xa

    packed-switch p1, :pswitch_data_0

    goto :goto_0

    :pswitch_0
    sget-object p1, Lorg/webrtc/videocodec/MediaCodecVideoEncoder;->DEBUG:Ljava/lang/Boolean;

    invoke-virtual {p1}, Ljava/lang/Boolean;->booleanValue()Z

    move-result p1

    if-eqz p1, :cond_0

    invoke-static {}, Lorg/webrtc/videocodec/MediaCodecVideoEncoder;->access$700()Ljava/lang/String;

    move-result-object p1

    const-string v0, "handleMessage EXIT."

    invoke-static {p1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_0
    return-void

    :pswitch_1
    iget-object p1, p0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$EncodeHandler;->this$0:Lorg/webrtc/videocodec/MediaCodecVideoEncoder;

    iget-object v2, p0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$EncodeHandler;->this$0:Lorg/webrtc/videocodec/MediaCodecVideoEncoder;

    invoke-static {v2}, Lorg/webrtc/videocodec/MediaCodecVideoEncoder;->access$100(Lorg/webrtc/videocodec/MediaCodecVideoEncoder;)Landroid/media/MediaCodec;

    move-result-object v2

    invoke-static {p1, v2}, Lorg/webrtc/videocodec/MediaCodecVideoEncoder;->access$200(Lorg/webrtc/videocodec/MediaCodecVideoEncoder;Landroid/media/MediaCodec;)Z

    move-result p1

    if-nez p1, :cond_1

    return-void

    :cond_1
    iget-object p1, p0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$EncodeHandler;->this$0:Lorg/webrtc/videocodec/MediaCodecVideoEncoder;

    invoke-static {p1}, Lorg/webrtc/videocodec/MediaCodecVideoEncoder;->access$500(Lorg/webrtc/videocodec/MediaCodecVideoEncoder;)V

    nop

    iget-object p1, p0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$EncodeHandler;->this$0:Lorg/webrtc/videocodec/MediaCodecVideoEncoder;

    invoke-static {p1}, Lorg/webrtc/videocodec/MediaCodecVideoEncoder;->access$600(Lorg/webrtc/videocodec/MediaCodecVideoEncoder;)Lorg/webrtc/videocodec/MediaCodecVideoEncoder$EncodeHandler;

    move-result-object p1

    iget-object v2, p0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$EncodeHandler;->this$0:Lorg/webrtc/videocodec/MediaCodecVideoEncoder;

    invoke-static {v2}, Lorg/webrtc/videocodec/MediaCodecVideoEncoder;->access$600(Lorg/webrtc/videocodec/MediaCodecVideoEncoder;)Lorg/webrtc/videocodec/MediaCodecVideoEncoder$EncodeHandler;

    move-result-object v2

    const/4 v3, 0x1

    invoke-virtual {v2, v3}, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$EncodeHandler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v2

    invoke-virtual {p1, v2, v0, v1}, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$EncodeHandler;->sendMessageDelayed(Landroid/os/Message;J)Z

    goto :goto_0

    :pswitch_2
    iget-object p1, p0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$EncodeHandler;->this$0:Lorg/webrtc/videocodec/MediaCodecVideoEncoder;

    iget-object v2, p0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$EncodeHandler;->this$0:Lorg/webrtc/videocodec/MediaCodecVideoEncoder;

    invoke-static {v2}, Lorg/webrtc/videocodec/MediaCodecVideoEncoder;->access$100(Lorg/webrtc/videocodec/MediaCodecVideoEncoder;)Landroid/media/MediaCodec;

    move-result-object v2

    invoke-static {p1, v2}, Lorg/webrtc/videocodec/MediaCodecVideoEncoder;->access$200(Lorg/webrtc/videocodec/MediaCodecVideoEncoder;Landroid/media/MediaCodec;)Z

    move-result p1

    if-nez p1, :cond_2

    return-void

    :cond_2
    iget-object p1, p0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$EncodeHandler;->this$0:Lorg/webrtc/videocodec/MediaCodecVideoEncoder;

    invoke-static {p1}, Lorg/webrtc/videocodec/MediaCodecVideoEncoder;->access$300(Lorg/webrtc/videocodec/MediaCodecVideoEncoder;)V

    nop

    iget-object p1, p0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$EncodeHandler;->this$0:Lorg/webrtc/videocodec/MediaCodecVideoEncoder;

    invoke-static {p1}, Lorg/webrtc/videocodec/MediaCodecVideoEncoder;->access$400(Lorg/webrtc/videocodec/MediaCodecVideoEncoder;)Lorg/webrtc/videocodec/MediaCodecVideoEncoder$EncodeHandler;

    move-result-object p1

    iget-object v2, p0, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$EncodeHandler;->this$0:Lorg/webrtc/videocodec/MediaCodecVideoEncoder;

    invoke-static {v2}, Lorg/webrtc/videocodec/MediaCodecVideoEncoder;->access$400(Lorg/webrtc/videocodec/MediaCodecVideoEncoder;)Lorg/webrtc/videocodec/MediaCodecVideoEncoder$EncodeHandler;

    move-result-object v2

    const/4 v3, 0x0

    invoke-virtual {v2, v3}, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$EncodeHandler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v2

    invoke-virtual {p1, v2, v0, v1}, Lorg/webrtc/videocodec/MediaCodecVideoEncoder$EncodeHandler;->sendMessageDelayed(Landroid/os/Message;J)Z
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    nop

    :goto_0
    goto :goto_1

    :catch_0
    move-exception p1

    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    :goto_1
    return-void

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method
