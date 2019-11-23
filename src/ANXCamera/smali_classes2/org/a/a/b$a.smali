.class Lorg/a/a/b$a;
.super Ljava/lang/Object;
.source "BufferedAudioRecorder.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lorg/a/a/b;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "a"
.end annotation


# instance fields
.field private speed:D

.field yx:Z

.field final synthetic yy:Lorg/a/a/b;


# direct methods
.method public constructor <init>(Lorg/a/a/b;DZ)V
    .locals 0

    iput-object p1, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-wide p2, p0, Lorg/a/a/b$a;->speed:D

    iput-boolean p4, p0, Lorg/a/a/b$a;->yx:Z

    return-void
.end method


# virtual methods
.method public run()V
    .locals 9

    iget-object v0, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget v0, v0, Lorg/a/a/b;->bufferSizeInBytes:I

    new-array v0, v0, [B

    nop

    iget-object v1, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    const/4 v2, 0x0

    iput-boolean v2, v1, Lorg/a/a/b;->yv:Z

    nop

    iget-object v1, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    new-instance v3, Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    iget-object v4, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-object v4, v4, Lorg/a/a/b;->yu:Lorg/a/a/a;

    iget-object v5, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-object v5, v5, Lorg/a/a/b;->yu:Lorg/a/a/a;

    invoke-direct {v3, v4, v5}, Lcom/ss/android/medialib/audio/AudioDataProcessThread;-><init>(Lorg/a/a/a;Lcom/ss/android/medialib/audio/AudioDataProcessThread$OnProcessDataListener;)V

    iput-object v3, v1, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    iget-object v1, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-object v1, v1, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    invoke-virtual {v1}, Lcom/ss/android/medialib/audio/AudioDataProcessThread;->start()V

    iget-boolean v1, p0, Lorg/a/a/b$a;->yx:Z

    if-eqz v1, :cond_0

    iget-object v1, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-object v1, v1, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    iget-object v3, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget v3, v3, Lorg/a/a/b;->sampleRateInHz:I

    iget-wide v4, p0, Lorg/a/a/b$a;->speed:D

    invoke-virtual {v1, v3, v4, v5}, Lcom/ss/android/medialib/audio/AudioDataProcessThread;->startFeeding(ID)V

    :cond_0
    :try_start_0
    iget-object v1, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-object v1, v1, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    if-eqz v1, :cond_9

    iget-object v1, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-object v1, v1, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    invoke-virtual {v1}, Landroid/media/AudioRecord;->startRecording()V

    iget-object v1, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-object v1, v1, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    const/4 v3, 0x1

    const/4 v4, 0x3

    if-eqz v1, :cond_1

    iget-object v1, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-object v1, v1, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    invoke-virtual {v1}, Landroid/media/AudioRecord;->getRecordingState()I

    move-result v1

    if-eq v1, v4, :cond_1

    nop

    iget-object v1, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-object v1, v1, Lorg/a/a/b;->yu:Lorg/a/a/a;

    invoke-interface {v1, v2}, Lorg/a/a/a;->recordStatus(Z)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_2

    move v1, v3

    goto :goto_0

    :cond_1
    move v1, v2

    :goto_0
    move v5, v1

    move v1, v2

    :goto_1
    iget-object v6, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-boolean v6, v6, Lorg/a/a/b;->isRecording:Z

    if-eqz v6, :cond_8

    iget-object v6, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-object v6, v6, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    if-eqz v6, :cond_2

    iget-object v1, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-object v1, v1, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    iget-object v6, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget v6, v6, Lorg/a/a/b;->bufferSizeInBytes:I

    invoke-virtual {v1, v0, v2, v6}, Landroid/media/AudioRecord;->read([BII)I

    move-result v1

    :cond_2
    const/4 v6, -0x3

    if-eq v6, v1, :cond_7

    if-lez v1, :cond_5

    :try_start_1
    iget-object v6, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-boolean v6, v6, Lorg/a/a/b;->isRecording:Z

    if-eqz v6, :cond_3

    iget-object v6, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-object v6, v6, Lorg/a/a/b;->yu:Lorg/a/a/a;

    invoke-interface {v6, v0, v1}, Lorg/a/a/a;->addPCMData([BI)I

    :cond_3
    iget-object v6, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-object v6, v6, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    invoke-virtual {v6}, Lcom/ss/android/medialib/audio/AudioDataProcessThread;->isProcessing()Z

    move-result v6

    if-eqz v6, :cond_4

    iget-object v6, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-boolean v6, v6, Lorg/a/a/b;->yv:Z

    if-nez v6, :cond_4

    iget-object v6, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-object v6, v6, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    invoke-virtual {v6, v0, v1}, Lcom/ss/android/medialib/audio/AudioDataProcessThread;->feed([BI)V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    goto :goto_2

    :catch_0
    move-exception v6

    :cond_4
    :goto_2
    goto :goto_1

    :cond_5
    iget-object v6, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-object v6, v6, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    if-eqz v6, :cond_6

    iget-object v6, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-object v6, v6, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    invoke-virtual {v6}, Landroid/media/AudioRecord;->getRecordingState()I

    move-result v6

    if-eq v6, v4, :cond_6

    if-nez v5, :cond_6

    nop

    iget-object v5, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-object v5, v5, Lorg/a/a/b;->yu:Lorg/a/a/a;

    invoke-interface {v5, v2}, Lorg/a/a/a;->recordStatus(Z)V

    move v5, v3

    :cond_6
    const-wide/16 v6, 0x32

    :try_start_2
    invoke-static {v6, v7}, Ljava/lang/Thread;->sleep(J)V
    :try_end_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_1

    :goto_3
    goto :goto_1

    :catch_1
    move-exception v6

    goto :goto_3

    :cond_7
    const-string v6, "BufferedAudioRecorder"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "bad audio buffer len "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_1

    :cond_8
    return-void

    :cond_9
    return-void

    :catch_2
    move-exception v0

    :try_start_3
    iget-object v1, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-object v1, v1, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    if-eqz v1, :cond_a

    iget-object v1, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    iget-object v1, v1, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    invoke-virtual {v1}, Landroid/media/AudioRecord;->release()V
    :try_end_3
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_3} :catch_3

    :cond_a
    goto :goto_4

    :catch_3
    move-exception v1

    :goto_4
    iget-object v1, p0, Lorg/a/a/b$a;->yy:Lorg/a/a/b;

    const/4 v2, 0x0

    iput-object v2, v1, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    const-string v1, "BufferedAudioRecorder"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "audio recording failed!"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method
