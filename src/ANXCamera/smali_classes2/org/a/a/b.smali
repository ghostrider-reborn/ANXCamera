.class public Lorg/a/a/b;
.super Ljava/lang/Object;
.source "BufferedAudioRecorder.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lorg/a/a/b$a;
    }
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String; = "BufferedAudioRecorder"

.field protected static channelConfigOffset:I

.field protected static channelConfigSuggested:[I

.field protected static sampleRateOffset:I

.field protected static sampleRateSuggested:[I


# instance fields
.field audio:Landroid/media/AudioRecord;

.field audioFormat:I

.field bufferSizeInBytes:I

.field channelConfig:I

.field isRecording:Z

.field sampleRateInHz:I

.field yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

.field yu:Lorg/a/a/a;

.field yv:Z

.field yw:I


# direct methods
.method static constructor <clinit>()V
    .locals 2

    const/4 v0, -0x1

    sput v0, Lorg/a/a/b;->sampleRateOffset:I

    const/4 v1, 0x5

    new-array v1, v1, [I

    fill-array-data v1, :array_0

    sput-object v1, Lorg/a/a/b;->sampleRateSuggested:[I

    sput v0, Lorg/a/a/b;->channelConfigOffset:I

    const/4 v0, 0x3

    new-array v0, v0, [I

    fill-array-data v0, :array_1

    sput-object v0, Lorg/a/a/b;->channelConfigSuggested:[I

    return-void

    :array_0
    .array-data 4
        0xac44
        0x1f40
        0x2b11
        0x3e80
        0x5622
    .end array-data

    :array_1
    .array-data 4
        0xc
        0x10
        0x1
    .end array-data
.end method

.method public constructor <init>(Lorg/a/a/a;)V
    .locals 2

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, -0x1

    iput v0, p0, Lorg/a/a/b;->sampleRateInHz:I

    const/4 v1, 0x0

    iput v1, p0, Lorg/a/a/b;->bufferSizeInBytes:I

    iput v0, p0, Lorg/a/a/b;->channelConfig:I

    const/4 v0, 0x2

    iput v0, p0, Lorg/a/a/b;->audioFormat:I

    iput-boolean v1, p0, Lorg/a/a/b;->isRecording:Z

    iput-boolean v1, p0, Lorg/a/a/b;->yv:Z

    const/4 v0, 0x1

    iput v0, p0, Lorg/a/a/b;->yw:I

    iput-object p1, p0, Lorg/a/a/b;->yu:Lorg/a/a/a;

    return-void
.end method


# virtual methods
.method public a(DZ)V
    .locals 2

    const-string v0, "BufferedAudioRecorder"

    const-string v1, "startRecording() called"

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VELogUtil;->i(Ljava/lang/String;Ljava/lang/String;)V

    monitor-enter p0

    :try_start_0
    iget-boolean v0, p0, Lorg/a/a/b;->isRecording:Z

    if-eqz v0, :cond_1

    const-string v0, "BufferedAudioRecorder"

    const-string v1, "recorder is started"

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VELogUtil;->w(Ljava/lang/String;Ljava/lang/String;)V

    if-eqz p3, :cond_0

    invoke-virtual {p0, p1, p2}, Lorg/a/a/b;->d(D)Z

    :cond_0
    monitor-exit p0

    return-void

    :cond_1
    iget-object v0, p0, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    if-nez v0, :cond_2

    iget v0, p0, Lorg/a/a/b;->yw:I

    invoke-virtual {p0, v0}, Lorg/a/a/b;->init(I)V

    iget-object v0, p0, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    if-nez v0, :cond_2

    const-string p1, "BufferedAudioRecorder"

    const-string p2, "recorder is null"

    invoke-static {p1, p2}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    monitor-exit p0

    return-void

    :cond_2
    const/4 v0, 0x1

    iput-boolean v0, p0, Lorg/a/a/b;->isRecording:Z

    monitor-exit p0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    :try_start_1
    new-instance v0, Ljava/lang/Thread;

    new-instance v1, Lorg/a/a/b$a;

    invoke-direct {v1, p0, p1, p2, p3}, Lorg/a/a/b$a;-><init>(Lorg/a/a/b;DZ)V

    invoke-direct {v0, v1}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;)V

    invoke-virtual {v0}, Ljava/lang/Thread;->start()V
    :try_end_1
    .catch Ljava/lang/OutOfMemoryError; {:try_start_1 .. :try_end_1} :catch_0

    goto :goto_1

    :catch_0
    move-exception v0

    invoke-static {}, Ljava/lang/Runtime;->getRuntime()Ljava/lang/Runtime;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Runtime;->gc()V

    const-wide/16 v0, 0x64

    :try_start_2
    invoke-static {v0, v1}, Ljava/lang/Thread;->sleep(J)V
    :try_end_2
    .catch Ljava/lang/InterruptedException; {:try_start_2 .. :try_end_2} :catch_1

    goto :goto_0

    :catch_1
    move-exception v0

    :goto_0
    invoke-static {}, Ljava/lang/System;->runFinalization()V

    new-instance v0, Ljava/lang/Thread;

    new-instance v1, Lorg/a/a/b$a;

    invoke-direct {v1, p0, p1, p2, p3}, Lorg/a/a/b$a;-><init>(Lorg/a/a/b;DZ)V

    invoke-direct {v0, v1}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;)V

    invoke-virtual {v0}, Ljava/lang/Thread;->start()V

    :goto_1
    return-void

    :catchall_0
    move-exception p1

    :try_start_3
    monitor-exit p0
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_0

    throw p1
.end method

.method public ad(I)I
    .locals 1

    const/16 v0, 0x10

    if-ne v0, p1, :cond_0

    const/4 p1, 0x1

    return p1

    :cond_0
    const/4 p1, 0x2

    return p1
.end method

.method public d(D)Z
    .locals 3

    const-string v0, "BufferedAudioRecorder"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "startFeeding() called with: speed = ["

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1, p2}, Ljava/lang/StringBuilder;->append(D)Ljava/lang/StringBuilder;

    const-string v2, "]"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VELogUtil;->i(Ljava/lang/String;Ljava/lang/String;)V

    iget-boolean v0, p0, Lorg/a/a/b;->isRecording:Z

    const/4 v1, 0x1

    if-eqz v0, :cond_2

    iget-object v0, p0, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    invoke-virtual {v0}, Lcom/ss/android/medialib/audio/AudioDataProcessThread;->isProcessing()Z

    move-result v0

    const/4 v2, 0x0

    if-eqz v0, :cond_1

    const-string p1, "BufferedAudioRecorder"

    const-string p2, "startFeeding \u5931\u8d25\uff0c\u5df2\u7ecf\u8c03\u7528\u8fc7\u4e00\u6b21\u4e86"

    invoke-static {p1, p2}, Lcom/ss/android/vesdk/VELogUtil;->w(Ljava/lang/String;Ljava/lang/String;)V

    return v2

    :cond_1
    iput-boolean v2, p0, Lorg/a/a/b;->yv:Z

    iget-object v0, p0, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    iget v2, p0, Lorg/a/a/b;->sampleRateInHz:I

    invoke-virtual {v0, v2, p1, p2}, Lcom/ss/android/medialib/audio/AudioDataProcessThread;->startFeeding(ID)V

    return v1

    :cond_2
    :goto_0
    const-string v0, "BufferedAudioRecorder"

    const-string v2, "startFeeding \u5f55\u97f3\u672a\u542f\u52a8\uff0c\u5c06\u5148\u542f\u52a8startRecording"

    invoke-static {v0, v2}, Lcom/ss/android/vesdk/VELogUtil;->w(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {p0, p1, p2, v1}, Lorg/a/a/b;->a(DZ)V

    return v1
.end method

.method public discard()V
    .locals 1

    iget-object v0, p0, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    invoke-virtual {v0}, Lcom/ss/android/medialib/audio/AudioDataProcessThread;->discard()V

    :cond_0
    return-void
.end method

.method protected finalize()V
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Throwable;
        }
    .end annotation

    iget-object v0, p0, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    if-eqz v0, :cond_1

    :try_start_0
    iget-object v0, p0, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    invoke-virtual {v0}, Landroid/media/AudioRecord;->getState()I

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    invoke-virtual {v0}, Landroid/media/AudioRecord;->stop()V

    :cond_0
    iget-object v0, p0, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    invoke-virtual {v0}, Landroid/media/AudioRecord;->release()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    :goto_0
    const/4 v0, 0x0

    iput-object v0, p0, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    :cond_1
    invoke-super {p0}, Ljava/lang/Object;->finalize()V

    return-void
.end method

.method public init(I)V
    .locals 19

    move-object/from16 v1, p0

    move/from16 v8, p1

    iput v8, v1, Lorg/a/a/b;->yw:I

    iget-object v0, v1, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    if-eqz v0, :cond_0

    const-string v0, "BufferedAudioRecorder"

    const-string v1, "second time audio init(), skip"

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    return-void

    :cond_0
    const/4 v9, -0x1

    :try_start_0
    sget v0, Lorg/a/a/b;->channelConfigOffset:I

    if-eq v0, v9, :cond_1

    sget v0, Lorg/a/a/b;->sampleRateOffset:I

    if-eq v0, v9, :cond_1

    sget-object v0, Lorg/a/a/b;->channelConfigSuggested:[I

    sget v2, Lorg/a/a/b;->channelConfigOffset:I

    aget v0, v0, v2

    iput v0, v1, Lorg/a/a/b;->channelConfig:I

    sget-object v0, Lorg/a/a/b;->sampleRateSuggested:[I

    sget v2, Lorg/a/a/b;->sampleRateOffset:I

    aget v0, v0, v2

    iput v0, v1, Lorg/a/a/b;->sampleRateInHz:I

    iget v0, v1, Lorg/a/a/b;->sampleRateInHz:I

    iget v2, v1, Lorg/a/a/b;->channelConfig:I

    iget v3, v1, Lorg/a/a/b;->audioFormat:I

    invoke-static {v0, v2, v3}, Landroid/media/AudioRecord;->getMinBufferSize(III)I

    move-result v0

    iput v0, v1, Lorg/a/a/b;->bufferSizeInBytes:I

    new-instance v0, Landroid/media/AudioRecord;

    iget v4, v1, Lorg/a/a/b;->sampleRateInHz:I

    iget v5, v1, Lorg/a/a/b;->channelConfig:I

    iget v6, v1, Lorg/a/a/b;->audioFormat:I

    iget v7, v1, Lorg/a/a/b;->bufferSizeInBytes:I

    move-object v2, v0

    move v3, v8

    invoke-direct/range {v2 .. v7}, Landroid/media/AudioRecord;-><init>(IIIII)V

    iput-object v0, v1, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    :cond_1
    goto :goto_0

    :catch_0
    move-exception v0

    const-string v2, "BufferedAudioRecorder"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "\u4f7f\u7528\u9884\u8bbe\u914d\u7f6e"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget v4, Lorg/a/a/b;->channelConfigOffset:I

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v4, ","

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget v4, Lorg/a/a/b;->sampleRateOffset:I

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v4, "\u5b9e\u4f8b\u5316audio recorder\u5931\u8d25\uff0c\u91cd\u65b0\u6d4b\u8bd5\u914d\u7f6e\u3002"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v2, v0}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    iget-object v0, v1, Lorg/a/a/b;->yu:Lorg/a/a/a;

    invoke-interface {v0}, Lorg/a/a/a;->lackPermission()V

    :goto_0
    iget-object v0, v1, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    const/4 v10, 0x1

    if-nez v0, :cond_5

    nop

    sput v9, Lorg/a/a/b;->channelConfigOffset:I

    sget-object v11, Lorg/a/a/b;->channelConfigSuggested:[I

    array-length v12, v11

    const/4 v14, 0x0

    const/4 v15, 0x0

    :goto_1
    if-ge v14, v12, :cond_5

    aget v0, v11, v14

    iput v0, v1, Lorg/a/a/b;->channelConfig:I

    sget v0, Lorg/a/a/b;->channelConfigOffset:I

    add-int/2addr v0, v10

    sput v0, Lorg/a/a/b;->channelConfigOffset:I

    sput v9, Lorg/a/a/b;->sampleRateOffset:I

    sget-object v7, Lorg/a/a/b;->sampleRateSuggested:[I

    array-length v6, v7

    const/4 v5, 0x0

    :goto_2
    if-ge v5, v6, :cond_3

    aget v4, v7, v5

    sget v0, Lorg/a/a/b;->sampleRateOffset:I

    add-int/2addr v0, v10

    sput v0, Lorg/a/a/b;->sampleRateOffset:I

    :try_start_1
    iget v0, v1, Lorg/a/a/b;->channelConfig:I

    iget v2, v1, Lorg/a/a/b;->audioFormat:I

    invoke-static {v4, v0, v2}, Landroid/media/AudioRecord;->getMinBufferSize(III)I

    move-result v0

    iput v0, v1, Lorg/a/a/b;->bufferSizeInBytes:I

    const-string v0, "BufferedAudioRecorder"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u8bd5\u7528hz "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v3, " "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v3, v1, Lorg/a/a/b;->channelConfig:I

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v3, " "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v3, v1, Lorg/a/a/b;->audioFormat:I

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    iget v0, v1, Lorg/a/a/b;->bufferSizeInBytes:I

    if-lez v0, :cond_2

    iput v4, v1, Lorg/a/a/b;->sampleRateInHz:I

    new-instance v0, Landroid/media/AudioRecord;

    iget v3, v1, Lorg/a/a/b;->sampleRateInHz:I

    iget v2, v1, Lorg/a/a/b;->channelConfig:I

    iget v9, v1, Lorg/a/a/b;->audioFormat:I

    iget v13, v1, Lorg/a/a/b;->bufferSizeInBytes:I
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_2

    move/from16 v16, v2

    move-object v2, v0

    move/from16 v17, v3

    move v3, v8

    move/from16 v18, v4

    move/from16 v4, v17

    move/from16 v17, v5

    move/from16 v5, v16

    move/from16 v16, v6

    move v6, v9

    move-object v9, v7

    move v7, v13

    :try_start_2
    invoke-direct/range {v2 .. v7}, Landroid/media/AudioRecord;-><init>(IIIII)V

    iput-object v0, v1, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    nop

    nop

    move v15, v10

    goto :goto_5

    :cond_2
    move/from16 v18, v4

    move/from16 v17, v5

    move/from16 v16, v6

    move-object v9, v7

    sget v0, Lorg/a/a/b;->sampleRateOffset:I

    add-int/2addr v0, v10

    sput v0, Lorg/a/a/b;->sampleRateOffset:I
    :try_end_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_1

    nop

    const/4 v2, 0x0

    goto :goto_4

    :catch_1
    move-exception v0

    goto :goto_3

    :catch_2
    move-exception v0

    move/from16 v18, v4

    move/from16 v17, v5

    move/from16 v16, v6

    move-object v9, v7

    :goto_3
    const/4 v2, 0x0

    iput v2, v1, Lorg/a/a/b;->sampleRateInHz:I

    const/4 v3, 0x0

    iput-object v3, v1, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    const-string v3, "BufferedAudioRecorder"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "apply audio record sample rate "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move/from16 v5, v18

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v5, " failed: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v3, v0}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    sget v0, Lorg/a/a/b;->sampleRateOffset:I

    add-int/2addr v0, v10

    sput v0, Lorg/a/a/b;->sampleRateOffset:I

    nop

    :goto_4
    add-int/lit8 v5, v17, 0x1

    move-object v7, v9

    move/from16 v6, v16

    const/4 v9, -0x1

    goto/16 :goto_2

    :cond_3
    :goto_5
    const/4 v2, 0x0

    if-eqz v15, :cond_4

    goto :goto_6

    :cond_4
    add-int/lit8 v14, v14, 0x1

    const/4 v9, -0x1

    goto/16 :goto_1

    :cond_5
    :goto_6
    iget v0, v1, Lorg/a/a/b;->sampleRateInHz:I

    if-gtz v0, :cond_6

    const-string v0, "BufferedAudioRecorder"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "!Init audio recorder failed, hz "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, v1, Lorg/a/a/b;->sampleRateInHz:I

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    return-void

    :cond_6
    iget v0, v1, Lorg/a/a/b;->channelConfig:I

    const/16 v2, 0x10

    if-ne v0, v2, :cond_7

    goto :goto_7

    :cond_7
    const/4 v10, 0x2

    :goto_7
    iget-object v0, v1, Lorg/a/a/b;->yu:Lorg/a/a/a;

    iget v2, v1, Lorg/a/a/b;->sampleRateInHz:I

    invoke-interface {v0, v2, v10}, Lorg/a/a/a;->initAudioConfig(II)I

    const-string v0, "BufferedAudioRecorder"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Init audio recorder succeed, apply audio record sample rate "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v3, v1, Lorg/a/a/b;->sampleRateInHz:I

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v3, " buffer "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v3, v1, Lorg/a/a/b;->bufferSizeInBytes:I

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v3, " state "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, v1, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    invoke-virtual {v1}, Landroid/media/AudioRecord;->getState()I

    move-result v1

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method public declared-synchronized isProcessing()Z
    .locals 1

    monitor-enter p0

    :try_start_0
    iget-object v0, p0, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    invoke-virtual {v0}, Lcom/ss/android/medialib/audio/AudioDataProcessThread;->isProcessing()Z

    move-result v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    monitor-exit p0

    return v0

    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public kI()V
    .locals 1

    monitor-enter p0

    const/4 v0, 0x1

    :try_start_0
    iput-boolean v0, p0, Lorg/a/a/b;->yv:Z

    monitor-exit p0

    return-void

    :catchall_0
    move-exception v0

    monitor-exit p0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v0
.end method

.method public kJ()Z
    .locals 4

    const-string v0, "BufferedAudioRecorder"

    const-string v1, "stopFeeding() called"

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VELogUtil;->i(Ljava/lang/String;Ljava/lang/String;)V

    iget-boolean v0, p0, Lorg/a/a/b;->isRecording:Z

    const/4 v1, 0x1

    const/4 v2, 0x0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    if-nez v0, :cond_1

    const-string v0, "BufferedAudioRecorder"

    const-string v3, "stopFeeding: \u72b6\u6001\u5f02\u5e38\uff0c\u91cd\u7f6e\u72b6\u6001"

    invoke-static {v0, v3}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    iput-boolean v2, p0, Lorg/a/a/b;->isRecording:Z

    iput-boolean v1, p0, Lorg/a/a/b;->yv:Z

    iget-object v0, p0, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    invoke-virtual {v0}, Lcom/ss/android/medialib/audio/AudioDataProcessThread;->stop()V

    :cond_0
    return v2

    :cond_1
    iget-boolean v0, p0, Lorg/a/a/b;->isRecording:Z

    if-eqz v0, :cond_4

    iget-object v0, p0, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    if-nez v0, :cond_2

    goto :goto_0

    :cond_2
    iget-object v0, p0, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    invoke-virtual {v0}, Lcom/ss/android/medialib/audio/AudioDataProcessThread;->isProcessing()Z

    move-result v0

    if-nez v0, :cond_3

    const-string v0, "BufferedAudioRecorder"

    const-string v1, "stopFeeding \u5931\u8d25\uff0c\u8bf7\u5148startFeeding\u518dstopFeeding"

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    return v2

    :cond_3
    iget-object v0, p0, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    invoke-virtual {v0}, Lcom/ss/android/medialib/audio/AudioDataProcessThread;->stopFeeding()V

    return v1

    :cond_4
    :goto_0
    const-string v0, "BufferedAudioRecorder"

    const-string v1, "stopFeeding \u5931\u8d25\uff0c\u8bf7\u5148\u8c03\u7528startRecording"

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    return v2
.end method

.method public stopRecording()Z
    .locals 2

    monitor-enter p0

    :try_start_0
    const-string v0, "BufferedAudioRecorder"

    const-string v1, "stopRecording() called"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-boolean v0, p0, Lorg/a/a/b;->isRecording:Z

    const/4 v1, 0x0

    if-nez v0, :cond_0

    monitor-exit p0

    return v1

    :cond_0
    iput-boolean v1, p0, Lorg/a/a/b;->isRecording:Z

    iget-object v0, p0, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    invoke-virtual {v0}, Landroid/media/AudioRecord;->getState()I

    move-result v0

    if-eqz v0, :cond_2

    iget-object v0, p0, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    invoke-virtual {v0}, Landroid/media/AudioRecord;->stop()V

    goto :goto_0

    :cond_1
    const-string v0, "BufferedAudioRecorder"

    const-string v1, "\u672a\u542f\u52a8\u97f3\u9891\u6a21\u5757\u4f46\u8c03\u7528stopRecording"

    invoke-static {v0, v1}, Lcom/ss/android/medialib/common/LogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    :cond_2
    :goto_0
    iget-object v0, p0, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    if-eqz v0, :cond_3

    iget-object v0, p0, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    invoke-virtual {v0}, Lcom/ss/android/medialib/audio/AudioDataProcessThread;->stop()V

    :cond_3
    monitor-exit p0

    const/4 v0, 0x1

    return v0

    :catchall_0
    move-exception v0

    monitor-exit p0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v0
.end method

.method public unInit()V
    .locals 2

    iget-boolean v0, p0, Lorg/a/a/b;->isRecording:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lorg/a/a/b;->stopRecording()Z

    :cond_0
    iget-object v0, p0, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    if-eqz v0, :cond_2

    :try_start_0
    iget-object v0, p0, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    invoke-virtual {v0}, Landroid/media/AudioRecord;->getState()I

    move-result v0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    invoke-virtual {v0}, Landroid/media/AudioRecord;->stop()V

    :cond_1
    iget-object v0, p0, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    invoke-virtual {v0}, Landroid/media/AudioRecord;->release()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    :goto_0
    const/4 v0, 0x0

    iput-object v0, p0, Lorg/a/a/b;->audio:Landroid/media/AudioRecord;

    :cond_2
    const-string v0, "BufferedAudioRecorder"

    const-string v1, "unInit()"

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VELogUtil;->i(Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method public waitUtilAudioProcessDone()V
    .locals 1

    iget-object v0, p0, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lorg/a/a/b;->yt:Lcom/ss/android/medialib/audio/AudioDataProcessThread;

    invoke-virtual {v0}, Lcom/ss/android/medialib/audio/AudioDataProcessThread;->waitUtilAudioProcessDone()V

    :cond_0
    return-void
.end method
