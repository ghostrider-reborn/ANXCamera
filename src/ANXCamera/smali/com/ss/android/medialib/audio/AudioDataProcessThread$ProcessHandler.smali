.class Lcom/ss/android/medialib/audio/AudioDataProcessThread$ProcessHandler;
.super Landroid/os/Handler;
.source "AudioDataProcessThread.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/ss/android/medialib/audio/AudioDataProcessThread;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0xa
    name = "ProcessHandler"
.end annotation


# instance fields
.field private mProcessor:Ljava/lang/ref/WeakReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/WeakReference<",
            "Lcom/ss/android/medialib/audio/AudioDataProcessThread;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>(Lcom/ss/android/medialib/audio/AudioDataProcessThread;)V
    .locals 1

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-direct {v0, p1}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/ss/android/medialib/audio/AudioDataProcessThread$ProcessHandler;->mProcessor:Ljava/lang/ref/WeakReference;

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 5

    iget v0, p1, Landroid/os/Message;->what:I

    iget-object v1, p0, Lcom/ss/android/medialib/audio/AudioDataProcessThread$ProcessHandler;->mProcessor:Ljava/lang/ref/WeakReference;

    invoke-virtual {v1}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    if-nez v1, :cond_0

    const-string p1, "AudioDataProcessThread"

    const-string v0, "EncoderHandler.handleMessage: encoder is null"

    invoke-static {p1, v0}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    return-void

    :cond_0
    packed-switch v0, :pswitch_data_0

    goto :goto_0

    :pswitch_0
    iget-object v0, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast v0, [B

    iget p1, p1, Landroid/os/Message;->arg1:I

    invoke-static {v1}, Lcom/ss/android/medialib/audio/AudioDataProcessThread;->access$200(Lcom/ss/android/medialib/audio/AudioDataProcessThread;)Ljava/util/concurrent/atomic/AtomicInteger;

    move-result-object v2

    invoke-virtual {v2}, Ljava/util/concurrent/atomic/AtomicInteger;->decrementAndGet()I

    move-result v2

    invoke-static {v1}, Lcom/ss/android/medialib/audio/AudioDataProcessThread;->access$300(Lcom/ss/android/medialib/audio/AudioDataProcessThread;)Lcom/ss/android/medialib/audio/AudioDataProcessThread$OnProcessDataListener;

    move-result-object v3

    if-eqz v3, :cond_1

    invoke-static {v1}, Lcom/ss/android/medialib/audio/AudioDataProcessThread;->access$300(Lcom/ss/android/medialib/audio/AudioDataProcessThread;)Lcom/ss/android/medialib/audio/AudioDataProcessThread$OnProcessDataListener;

    move-result-object v1

    invoke-interface {v1, v0, p1}, Lcom/ss/android/medialib/audio/AudioDataProcessThread$OnProcessDataListener;->onProcessData([BI)I

    const-string v0, "AudioDataProcessThread"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Buffer processed, size="

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p1, ", "

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p1, " buffers remaining"

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VELogUtil;->d(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    :pswitch_1
    const-string p1, "AudioDataProcessThread"

    const-string v0, "Exit loop"

    invoke-static {p1, v0}, Lcom/ss/android/vesdk/VELogUtil;->i(Ljava/lang/String;Ljava/lang/String;)V

    invoke-static {v1}, Lcom/ss/android/medialib/audio/AudioDataProcessThread;->access$100(Lcom/ss/android/medialib/audio/AudioDataProcessThread;)V

    const/4 p1, 0x3

    invoke-virtual {p0, p1}, Lcom/ss/android/medialib/audio/AudioDataProcessThread$ProcessHandler;->removeMessages(I)V

    invoke-static {}, Landroid/os/Looper;->myLooper()Landroid/os/Looper;

    move-result-object p1

    invoke-virtual {p1}, Landroid/os/Looper;->quit()V

    goto :goto_0

    :pswitch_2
    invoke-static {v1}, Lcom/ss/android/medialib/audio/AudioDataProcessThread;->access$100(Lcom/ss/android/medialib/audio/AudioDataProcessThread;)V

    goto :goto_0

    :pswitch_3
    iget v0, p1, Landroid/os/Message;->arg1:I

    iget v2, p1, Landroid/os/Message;->arg2:I

    iget-object p1, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast p1, Ljava/lang/Double;

    invoke-virtual {p1}, Ljava/lang/Double;->doubleValue()D

    move-result-wide v3

    invoke-static {v1, v0, v2, v3, v4}, Lcom/ss/android/medialib/audio/AudioDataProcessThread;->access$000(Lcom/ss/android/medialib/audio/AudioDataProcessThread;IID)V

    nop

    :cond_1
    :goto_0
    return-void

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method
