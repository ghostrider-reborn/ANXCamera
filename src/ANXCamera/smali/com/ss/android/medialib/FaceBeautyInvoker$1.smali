.class Lcom/ss/android/medialib/FaceBeautyInvoker$1;
.super Ljava/lang/Object;
.source "FaceBeautyInvoker.java"

# interfaces
.implements Lcom/ss/android/medialib/AVCEncoderInterface;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/ss/android/medialib/FaceBeautyInvoker;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;


# direct methods
.method constructor <init>(Lcom/ss/android/medialib/FaceBeautyInvoker;)V
    .locals 0

    iput-object p1, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public getProfile()I
    .locals 1

    iget-object v0, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v0}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$200(Lcom/ss/android/medialib/FaceBeautyInvoker;)Lcom/ss/android/medialib/AVCEncoder;

    move-result-object v0

    invoke-virtual {v0}, Lcom/ss/android/medialib/AVCEncoder;->getProfile()I

    move-result v0

    return v0
.end method

.method public onEncoderData(IIIZ)I
    .locals 2

    const-string v0, "FaceBeautyInvoker"

    const-string v1, "onEncoderData: ..."

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VELogUtil;->d(Ljava/lang/String;Ljava/lang/String;)V

    iget-object v0, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v0}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$200(Lcom/ss/android/medialib/FaceBeautyInvoker;)Lcom/ss/android/medialib/AVCEncoder;

    move-result-object v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v0}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$200(Lcom/ss/android/medialib/FaceBeautyInvoker;)Lcom/ss/android/medialib/AVCEncoder;

    move-result-object v0

    invoke-virtual {v0, p1, p2, p3, p4}, Lcom/ss/android/medialib/AVCEncoder;->encode(IIIZ)I

    move-result p1

    return p1

    :cond_0
    const/4 p1, 0x0

    return p1
.end method

.method public onEncoderData(Ljava/nio/ByteBuffer;IZ)V
    .locals 0

    const-string p1, "FaceBeautyInvoker"

    const-string p2, "onEncoderData: ..."

    invoke-static {p1, p2}, Lcom/ss/android/vesdk/VELogUtil;->d(Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method public onEncoderData([BIZ)V
    .locals 2

    const-string v0, "FaceBeautyInvoker"

    const-string v1, "FaceBeautyManager onEncoderData == enter"

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VELogUtil;->d(Ljava/lang/String;Ljava/lang/String;)V

    iget-object v0, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v0}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$200(Lcom/ss/android/medialib/FaceBeautyInvoker;)Lcom/ss/android/medialib/AVCEncoder;

    move-result-object v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v0}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$200(Lcom/ss/android/medialib/FaceBeautyInvoker;)Lcom/ss/android/medialib/AVCEncoder;

    move-result-object v0

    invoke-virtual {v0, p1, p2, p3}, Lcom/ss/android/medialib/AVCEncoder;->encode([BIZ)I

    :cond_0
    const-string p1, "FaceBeautyInvoker"

    const-string p2, "FaceBeautyManager onEncoderData == exit"

    invoke-static {p1, p2}, Lcom/ss/android/vesdk/VELogUtil;->d(Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method public onInitHardEncoder(IIIIIIZ)Landroid/view/Surface;
    .locals 12

    move-object v0, p0

    const-string v1, "FaceBeautyInvoker"

    const-string v2, "FaceBeautyManager onInitHardEncoder == enter"

    invoke-static {v1, v2}, Lcom/ss/android/vesdk/VELogUtil;->i(Ljava/lang/String;Ljava/lang/String;)V

    const-string v1, "FaceBeautyInvoker"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v3, "width = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move v3, p1

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v4, "\theight = "

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move v6, p2

    invoke-virtual {v2, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/ss/android/vesdk/VELogUtil;->i(Ljava/lang/String;Ljava/lang/String;)V

    iget-object v1, v0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v1}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$200(Lcom/ss/android/medialib/FaceBeautyInvoker;)Lcom/ss/android/medialib/AVCEncoder;

    move-result-object v1

    if-nez v1, :cond_0

    iget-object v1, v0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    new-instance v2, Lcom/ss/android/medialib/AVCEncoder;

    invoke-direct {v2}, Lcom/ss/android/medialib/AVCEncoder;-><init>()V

    invoke-static {v1, v2}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$202(Lcom/ss/android/medialib/FaceBeautyInvoker;Lcom/ss/android/medialib/AVCEncoder;)Lcom/ss/android/medialib/AVCEncoder;

    :cond_0
    iget-object v1, v0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v1}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$200(Lcom/ss/android/medialib/FaceBeautyInvoker;)Lcom/ss/android/medialib/AVCEncoder;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/ss/android/medialib/AVCEncoder;->setEncoderCaller(Lcom/ss/android/medialib/AVCEncoderInterface;)V

    iget-object v1, v0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v1}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$200(Lcom/ss/android/medialib/FaceBeautyInvoker;)Lcom/ss/android/medialib/AVCEncoder;

    move-result-object v4

    move v5, v3

    move v7, p3

    move/from16 v8, p4

    move/from16 v9, p5

    move/from16 v10, p6

    move/from16 v11, p7

    invoke-virtual/range {v4 .. v11}, Lcom/ss/android/medialib/AVCEncoder;->initAVCEncoder(IIIIIIZ)Landroid/view/Surface;

    move-result-object v1

    if-nez v1, :cond_1

    iget-object v1, v0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v1}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$200(Lcom/ss/android/medialib/FaceBeautyInvoker;)Lcom/ss/android/medialib/AVCEncoder;

    move-result-object v1

    invoke-virtual {v1}, Lcom/ss/android/medialib/AVCEncoder;->uninitAVCEncoder()V

    iget-object v1, v0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    const/4 v2, 0x0

    invoke-static {v1, v2}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$202(Lcom/ss/android/medialib/FaceBeautyInvoker;Lcom/ss/android/medialib/AVCEncoder;)Lcom/ss/android/medialib/AVCEncoder;

    iget-object v0, v0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lcom/ss/android/medialib/FaceBeautyInvoker;->setHardEncoderStatus(Z)I

    return-object v2

    :cond_1
    const-string v2, "FaceBeautyInvoker"

    const-string v3, "====== initAVCEncoder succeed ======"

    invoke-static {v2, v3}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    iget-object v0, v0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    const/4 v2, 0x1

    invoke-virtual {v0, v2}, Lcom/ss/android/medialib/FaceBeautyInvoker;->setHardEncoderStatus(Z)I

    const-string v0, "FaceBeautyInvoker"

    const-string v2, "FaceBeautyManager onInitHardEncoder == exit"

    invoke-static {v0, v2}, Lcom/ss/android/vesdk/VELogUtil;->i(Ljava/lang/String;Ljava/lang/String;)V

    return-object v1
.end method

.method public onInitHardEncoder(IIIIZ)Landroid/view/Surface;
    .locals 7

    const-string v0, "FaceBeautyInvoker"

    const-string v1, "FaceBeautyManager onInitHardEncoder == enter"

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VELogUtil;->i(Ljava/lang/String;Ljava/lang/String;)V

    const-string v0, "FaceBeautyInvoker"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v2, "width = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, "\theight = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VELogUtil;->i(Ljava/lang/String;Ljava/lang/String;)V

    iget-object v0, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v0}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$200(Lcom/ss/android/medialib/FaceBeautyInvoker;)Lcom/ss/android/medialib/AVCEncoder;

    move-result-object v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    new-instance v1, Lcom/ss/android/medialib/AVCEncoder;

    invoke-direct {v1}, Lcom/ss/android/medialib/AVCEncoder;-><init>()V

    invoke-static {v0, v1}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$202(Lcom/ss/android/medialib/FaceBeautyInvoker;Lcom/ss/android/medialib/AVCEncoder;)Lcom/ss/android/medialib/AVCEncoder;

    :cond_0
    iget-object v0, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v0}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$200(Lcom/ss/android/medialib/FaceBeautyInvoker;)Lcom/ss/android/medialib/AVCEncoder;

    move-result-object v0

    invoke-virtual {v0, p0}, Lcom/ss/android/medialib/AVCEncoder;->setEncoderCaller(Lcom/ss/android/medialib/AVCEncoderInterface;)V

    iget-object v0, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v0}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$200(Lcom/ss/android/medialib/FaceBeautyInvoker;)Lcom/ss/android/medialib/AVCEncoder;

    move-result-object v1

    move v2, p1

    move v3, p2

    move v4, p3

    move v5, p4

    move v6, p5

    invoke-virtual/range {v1 .. v6}, Lcom/ss/android/medialib/AVCEncoder;->initAVCEncoder(IIIIZ)Landroid/view/Surface;

    move-result-object p1

    if-nez p1, :cond_1

    iget-object p1, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {p1}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$200(Lcom/ss/android/medialib/FaceBeautyInvoker;)Lcom/ss/android/medialib/AVCEncoder;

    move-result-object p1

    invoke-virtual {p1}, Lcom/ss/android/medialib/AVCEncoder;->uninitAVCEncoder()V

    iget-object p1, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    const/4 p2, 0x0

    invoke-static {p1, p2}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$202(Lcom/ss/android/medialib/FaceBeautyInvoker;Lcom/ss/android/medialib/AVCEncoder;)Lcom/ss/android/medialib/AVCEncoder;

    iget-object p1, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    const/4 p3, 0x0

    invoke-virtual {p1, p3}, Lcom/ss/android/medialib/FaceBeautyInvoker;->setHardEncoderStatus(Z)I

    return-object p2

    :cond_1
    const-string p2, "FaceBeautyInvoker"

    const-string p3, "====== initAVCEncoder succeed ======"

    invoke-static {p2, p3}, Lcom/ss/android/vesdk/VELogUtil;->i(Ljava/lang/String;Ljava/lang/String;)V

    iget-object p2, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    const/4 p3, 0x1

    invoke-virtual {p2, p3}, Lcom/ss/android/medialib/FaceBeautyInvoker;->setHardEncoderStatus(Z)I

    const-string p2, "FaceBeautyInvoker"

    const-string p3, "FaceBeautyManager onInitHardEncoder == exit"

    invoke-static {p2, p3}, Lcom/ss/android/vesdk/VELogUtil;->i(Ljava/lang/String;Ljava/lang/String;)V

    return-object p1
.end method

.method public onSetCodecConfig(Ljava/nio/ByteBuffer;)V
    .locals 4

    const-string v0, "FaceBeautyInvoker"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onSetCodecConfig: data size = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/nio/ByteBuffer;->remaining()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VELogUtil;->d(Ljava/lang/String;Ljava/lang/String;)V

    iget-object v0, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v0}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$000(Lcom/ss/android/medialib/FaceBeautyInvoker;)J

    move-result-wide v0

    const-wide/16 v2, 0x0

    cmp-long v0, v0, v2

    if-nez v0, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    iget-object v1, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v1}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$000(Lcom/ss/android/medialib/FaceBeautyInvoker;)J

    move-result-wide v1

    invoke-virtual {p1}, Ljava/nio/ByteBuffer;->remaining()I

    move-result v3

    invoke-static {v0, v1, v2, p1, v3}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$600(Lcom/ss/android/medialib/FaceBeautyInvoker;JLjava/nio/ByteBuffer;I)I

    return-void
.end method

.method public onSwapGlBuffers()V
    .locals 4

    iget-object v0, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v0}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$000(Lcom/ss/android/medialib/FaceBeautyInvoker;)J

    move-result-wide v0

    const-wide/16 v2, 0x0

    cmp-long v0, v0, v2

    if-nez v0, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    iget-object v1, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v1}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$000(Lcom/ss/android/medialib/FaceBeautyInvoker;)J

    move-result-wide v1

    invoke-static {v0, v1, v2}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$500(Lcom/ss/android/medialib/FaceBeautyInvoker;J)V

    return-void
.end method

.method public onUninitHardEncoder()V
    .locals 2

    const-string v0, "FaceBeautyInvoker"

    const-string v1, "FaceBeautyManager onUninitHardEncoder == enter"

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VELogUtil;->i(Ljava/lang/String;Ljava/lang/String;)V

    iget-object v0, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v0}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$200(Lcom/ss/android/medialib/FaceBeautyInvoker;)Lcom/ss/android/medialib/AVCEncoder;

    move-result-object v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v0}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$200(Lcom/ss/android/medialib/FaceBeautyInvoker;)Lcom/ss/android/medialib/AVCEncoder;

    move-result-object v0

    invoke-virtual {v0}, Lcom/ss/android/medialib/AVCEncoder;->uninitAVCEncoder()V

    iget-object v0, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$202(Lcom/ss/android/medialib/FaceBeautyInvoker;Lcom/ss/android/medialib/AVCEncoder;)Lcom/ss/android/medialib/AVCEncoder;

    const-string v0, "FaceBeautyInvoker"

    const-string v1, "====== uninitAVCEncoder ======"

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VELogUtil;->i(Ljava/lang/String;Ljava/lang/String;)V

    :cond_0
    const-string v0, "FaceBeautyInvoker"

    const-string v1, "FaceBeautyManager onUninitHardEncoder == exit"

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VELogUtil;->i(Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method public onWriteFile(Ljava/nio/ByteBuffer;III)V
    .locals 7

    iget-object p3, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {p3}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$000(Lcom/ss/android/medialib/FaceBeautyInvoker;)J

    move-result-wide v0

    const-wide/16 v2, 0x0

    cmp-long p3, v0, v2

    if-nez p3, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    iget-object p3, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {p3}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$000(Lcom/ss/android/medialib/FaceBeautyInvoker;)J

    move-result-wide v1

    invoke-virtual {p1}, Ljava/nio/ByteBuffer;->remaining()I

    move-result v4

    move-object v3, p1

    move v5, p2

    move v6, p4

    invoke-static/range {v0 .. v6}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$300(Lcom/ss/android/medialib/FaceBeautyInvoker;JLjava/nio/ByteBuffer;III)I

    return-void
.end method

.method public onWriteFile(Ljava/nio/ByteBuffer;JJII)V
    .locals 12

    move-object v0, p0

    iget-object v1, v0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v1}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$000(Lcom/ss/android/medialib/FaceBeautyInvoker;)J

    move-result-wide v1

    const-wide/16 v3, 0x0

    cmp-long v1, v1, v3

    if-nez v1, :cond_0

    return-void

    :cond_0
    iget-object v2, v0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    iget-object v0, v0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v0}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$000(Lcom/ss/android/medialib/FaceBeautyInvoker;)J

    move-result-wide v3

    invoke-virtual {p1}, Ljava/nio/ByteBuffer;->remaining()I

    move-result v6

    move-object v5, p1

    move-wide v7, p2

    move-wide/from16 v9, p4

    move/from16 v11, p7

    invoke-static/range {v2 .. v11}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$400(Lcom/ss/android/medialib/FaceBeautyInvoker;JLjava/nio/ByteBuffer;IJJI)I

    return-void
.end method

.method public setColorFormat(I)V
    .locals 4

    iget-object v0, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v0}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$000(Lcom/ss/android/medialib/FaceBeautyInvoker;)J

    move-result-wide v0

    const-wide/16 v2, 0x0

    cmp-long v0, v0, v2

    if-nez v0, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    iget-object v1, p0, Lcom/ss/android/medialib/FaceBeautyInvoker$1;->this$0:Lcom/ss/android/medialib/FaceBeautyInvoker;

    invoke-static {v1}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$000(Lcom/ss/android/medialib/FaceBeautyInvoker;)J

    move-result-wide v1

    invoke-static {v0, v1, v2, p1}, Lcom/ss/android/medialib/FaceBeautyInvoker;->access$100(Lcom/ss/android/medialib/FaceBeautyInvoker;JI)I

    return-void
.end method
