.class Lcom/android/camera/module/Camera2Module$MainHandler;
.super Landroid/os/Handler;
.source "Camera2Module.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/module/Camera2Module;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0xa
    name = "MainHandler"
.end annotation


# instance fields
.field private mModule:Ljava/lang/ref/WeakReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/WeakReference<",
            "Lcom/android/camera/module/Camera2Module;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>(Lcom/android/camera/module/Camera2Module;Landroid/os/Looper;)V
    .locals 0

    invoke-direct {p0, p2}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    new-instance p2, Ljava/lang/ref/WeakReference;

    invoke-direct {p2, p1}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object p2, p0, Lcom/android/camera/module/Camera2Module$MainHandler;->mModule:Ljava/lang/ref/WeakReference;

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 7

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module$MainHandler;->mModule:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/module/Camera2Module;

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0}, Lcom/android/camera/module/Camera2Module;->isCreated()Z

    move-result v1

    const/4 v2, 0x0

    if-nez v1, :cond_1

    invoke-virtual {p0, v2}, Lcom/android/camera/module/Camera2Module$MainHandler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    return-void

    :cond_1
    invoke-virtual {v0}, Lcom/android/camera/module/Camera2Module;->getActivity()Lcom/android/camera/Camera;

    move-result-object v1

    if-nez v1, :cond_2

    return-void

    :cond_2
    iget v1, p1, Landroid/os/Message;->what:I

    const/16 v3, 0x80

    const/4 v4, 0x4

    const/4 v5, 0x0

    const/4 v6, 0x1

    sparse-switch v1, :sswitch_data_0

    new-instance v0, Ljava/lang/RuntimeException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "no consumer for this message: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget p1, p1, Landroid/os/Message;->what:I

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-direct {v0, p1}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/String;)V

    throw v0

    :sswitch_0
    invoke-static {}, Lcom/android/camera/module/Camera2Module;->access$300()Ljava/lang/String;

    move-result-object p1

    const-string v1, "fallback timeout"

    invoke-static {p1, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iput-boolean v5, v0, Lcom/android/camera/module/Camera2Module;->mIsSatFallback:Z

    iput-boolean v5, v0, Lcom/android/camera/module/Camera2Module;->mFallbackProcessed:Z

    const/4 p1, -0x1

    iput p1, v0, Lcom/android/camera/module/Camera2Module;->mLastSatFallbackRequestId:I

    invoke-static {v0}, Lcom/android/camera/module/Camera2Module;->access$3800(Lcom/android/camera/module/Camera2Module;)Z

    move-result p1

    if-eqz p1, :cond_7

    invoke-virtual {v0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result p1

    if-ne p1, v6, :cond_7

    invoke-static {v0, v5}, Lcom/android/camera/module/Camera2Module;->access$3802(Lcom/android/camera/module/Camera2Module;Z)Z

    const/16 p1, 0x34

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module$MainHandler;->sendEmptyMessage(I)Z

    goto/16 :goto_2

    :sswitch_1
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa4

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;

    if-eqz v0, :cond_7

    iget v1, p1, Landroid/os/Message;->arg2:I

    if-ltz v1, :cond_3

    iget v1, p1, Landroid/os/Message;->arg2:I

    rem-int/lit16 v1, v1, 0x168

    goto :goto_0

    :cond_3
    iget v1, p1, Landroid/os/Message;->arg2:I

    rem-int/lit16 v1, v1, 0x168

    add-int/lit16 v1, v1, 0x168

    :goto_0
    rsub-int v1, v1, 0x168

    rem-int/lit16 v1, v1, 0x168

    iget p1, p1, Landroid/os/Message;->arg1:I

    invoke-interface {v0, p1, v1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->configRotationChange(II)V

    goto/16 :goto_2

    :sswitch_2
    invoke-static {}, Lcom/android/zxing/PreviewDecodeManager;->getInstance()Lcom/android/zxing/PreviewDecodeManager;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/zxing/PreviewDecodeManager;->reset()V

    goto/16 :goto_2

    :sswitch_3
    iget-object p1, v0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    if-eqz p1, :cond_7

    iget-object p1, v0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {p1, v6}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->isFaceExists(I)Z

    move-result p1

    if-eqz p1, :cond_7

    iget-object p1, v0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->isFocusViewVisible()Z

    move-result p1

    if-eqz p1, :cond_7

    iget-object p1, v0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz p1, :cond_7

    iget-object p1, v0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p1}, Lcom/android/camera2/Camera2Proxy;->getFocusMode()I

    move-result p1

    if-ne v4, p1, :cond_7

    iget-object p1, v0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    const/4 v0, 0x7

    invoke-interface {p1, v0}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->clearFocusView(I)V

    goto/16 :goto_2

    :sswitch_4
    invoke-virtual {v0}, Lcom/android/camera/module/Camera2Module;->getTriggerMode()I

    move-result p1

    invoke-virtual {v0, p1}, Lcom/android/camera/module/Camera2Module;->onShutterButtonClick(I)V

    goto/16 :goto_2

    :sswitch_5
    iget-object p1, v0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p1}, Lcom/android/camera/Camera;->isActivityPaused()Z

    move-result p1

    if-nez p1, :cond_7

    iput-boolean v6, v0, Lcom/android/camera/module/Camera2Module;->mOpenCameraFail:Z

    invoke-virtual {v0}, Lcom/android/camera/module/Camera2Module;->onCameraException()V

    goto/16 :goto_2

    :sswitch_6
    invoke-static {}, Lcom/android/camera/module/Camera2Module;->access$300()Ljava/lang/String;

    move-result-object p1

    const-string v1, "Oops, capture timeout later release timeout!"

    invoke-static {p1, v1}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {v0, v5}, Lcom/android/camera/module/Camera2Module;->onPictureTakenFinished(Z)V

    goto/16 :goto_2

    :sswitch_7
    invoke-virtual {v0}, Lcom/android/camera/module/Camera2Module;->isAlive()Z

    move-result p1

    if-nez p1, :cond_4

    return-void

    :cond_4
    invoke-static {v0}, Lcom/android/camera/module/Camera2Module;->access$2600(Lcom/android/camera/module/Camera2Module;)V

    invoke-static {v0}, Lcom/android/camera/module/Camera2Module;->access$000(Lcom/android/camera/module/Camera2Module;)Lio/reactivex/ObservableEmitter;

    move-result-object p1

    invoke-interface {p1}, Lio/reactivex/ObservableEmitter;->onComplete()V

    goto/16 :goto_2

    :sswitch_8
    invoke-virtual {v0, v6}, Lcom/android/camera/module/Camera2Module;->setCameraState(I)V

    goto/16 :goto_2

    :sswitch_9
    invoke-virtual {v0, v2}, Lcom/android/camera/module/Camera2Module;->setActivity(Lcom/android/camera/Camera;)V

    goto/16 :goto_2

    :sswitch_a
    invoke-virtual {v0}, Lcom/android/camera/module/Camera2Module;->restartModule()V

    goto/16 :goto_2

    :sswitch_b
    iget v1, p1, Landroid/os/Message;->arg1:I

    if-lez v1, :cond_5

    move v1, v6

    goto :goto_1

    :cond_5
    move v1, v5

    :goto_1
    iget p1, p1, Landroid/os/Message;->arg2:I

    if-lez p1, :cond_6

    move v5, v6

    nop

    :cond_6
    invoke-static {v0, v1, v5}, Lcom/android/camera/module/Camera2Module;->access$3700(Lcom/android/camera/module/Camera2Module;ZZ)V

    goto :goto_2

    :sswitch_c
    iget v1, p1, Landroid/os/Message;->arg1:I

    iget p1, p1, Landroid/os/Message;->arg2:I

    invoke-static {v0, v1, p1}, Lcom/android/camera/module/Camera2Module;->access$3500(Lcom/android/camera/module/Camera2Module;II)V

    goto :goto_2

    :sswitch_d
    invoke-static {v0}, Lcom/android/camera/module/Camera2Module;->access$3600(Lcom/android/camera/module/Camera2Module;)V

    goto :goto_2

    :sswitch_e
    const/16 p1, 0x11

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module$MainHandler;->removeMessages(I)V

    const/4 p1, 0x2

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module$MainHandler;->removeMessages(I)V

    invoke-virtual {v0}, Lcom/android/camera/module/Camera2Module;->getWindow()Landroid/view/Window;

    move-result-object v1

    invoke-virtual {v1, v3}, Landroid/view/Window;->addFlags(I)V

    invoke-virtual {v0}, Lcom/android/camera/module/Camera2Module;->getScreenDelay()I

    move-result v0

    int-to-long v0, v0

    invoke-virtual {p0, p1, v0, v1}, Lcom/android/camera/module/Camera2Module$MainHandler;->sendEmptyMessageDelayed(IJ)Z

    goto :goto_2

    :sswitch_f
    goto :goto_2

    :sswitch_10
    iget-object p1, v0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p1}, Lcom/android/camera/Camera;->isActivityPaused()Z

    move-result p1

    if-nez p1, :cond_7

    iput-boolean v6, v0, Lcom/android/camera/module/Camera2Module;->mOpenCameraFail:Z

    invoke-virtual {v0}, Lcom/android/camera/module/Camera2Module;->onCameraException()V

    goto :goto_2

    :sswitch_11
    iget-object p1, v0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {p1, v0}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->initializeFocusView(Lcom/android/camera/ui/FocusView$ExposureViewListener;)V

    iget-object p1, v0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {p1, v0}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->setObjectViewListener(Lcom/android/camera/ui/ObjectView$ObjectViewListener;)V

    goto :goto_2

    :sswitch_12
    invoke-virtual {v0}, Lcom/android/camera/module/Camera2Module;->checkActivityOrientation()V

    invoke-static {}, Landroid/os/SystemClock;->uptimeMillis()J

    move-result-wide v1

    invoke-static {v0}, Lcom/android/camera/module/Camera2Module;->access$3400(Lcom/android/camera/module/Camera2Module;)J

    move-result-wide v5

    sub-long/2addr v1, v5

    const-wide/16 v5, 0x1388

    cmp-long p1, v1, v5

    if-gez p1, :cond_7

    const-wide/16 v0, 0x64

    invoke-virtual {p0, v4, v0, v1}, Lcom/android/camera/module/Camera2Module$MainHandler;->sendEmptyMessageDelayed(IJ)Z

    goto :goto_2

    :sswitch_13
    invoke-virtual {v0}, Lcom/android/camera/module/Camera2Module;->getWindow()Landroid/view/Window;

    move-result-object p1

    invoke-virtual {p1, v3}, Landroid/view/Window;->clearFlags(I)V

    nop

    :cond_7
    :goto_2
    return-void

    :sswitch_data_0
    .sparse-switch
        0x2 -> :sswitch_13
        0x4 -> :sswitch_12
        0x9 -> :sswitch_11
        0xa -> :sswitch_10
        0xb -> :sswitch_f
        0x11 -> :sswitch_e
        0x1f -> :sswitch_d
        0x21 -> :sswitch_c
        0x23 -> :sswitch_b
        0x2c -> :sswitch_a
        0x2d -> :sswitch_9
        0x30 -> :sswitch_8
        0x31 -> :sswitch_7
        0x32 -> :sswitch_6
        0x33 -> :sswitch_5
        0x34 -> :sswitch_4
        0x38 -> :sswitch_3
        0x39 -> :sswitch_2
        0x3a -> :sswitch_1
        0x3c -> :sswitch_0
    .end sparse-switch
.end method
