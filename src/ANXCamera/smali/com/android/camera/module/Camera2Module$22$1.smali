.class Lcom/android/camera/module/Camera2Module$22$1;
.super Ljava/lang/Object;
.source "Camera2Module.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/module/Camera2Module$22;->onSessionStatusFlawResultData(II)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/android/camera/module/Camera2Module$22;

.field final synthetic val$config:Lcom/android/camera/fragment/top/FragmentTopConfig;

.field final synthetic val$flawResult:I


# direct methods
.method constructor <init>(Lcom/android/camera/module/Camera2Module$22;ILcom/android/camera/fragment/top/FragmentTopConfig;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/Camera2Module$22$1;->this$1:Lcom/android/camera/module/Camera2Module$22;

    iput p2, p0, Lcom/android/camera/module/Camera2Module$22$1;->val$flawResult:I

    iput-object p3, p0, Lcom/android/camera/module/Camera2Module$22$1;->val$config:Lcom/android/camera/fragment/top/FragmentTopConfig;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 5

    iget v0, p0, Lcom/android/camera/module/Camera2Module$22$1;->val$flawResult:I

    const-wide/16 v1, 0xbb8

    const/4 v3, 0x0

    packed-switch v0, :pswitch_data_0

    goto :goto_0

    :pswitch_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module$22$1;->val$config:Lcom/android/camera/fragment/top/FragmentTopConfig;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module$22$1;->val$config:Lcom/android/camera/fragment/top/FragmentTopConfig;

    const v4, 0x7f0903c4

    invoke-virtual {v0, v3, v4, v1, v2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertAiDetectTipHint(IIJ)V

    goto :goto_0

    :pswitch_1
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module$22$1;->val$config:Lcom/android/camera/fragment/top/FragmentTopConfig;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module$22$1;->val$config:Lcom/android/camera/fragment/top/FragmentTopConfig;

    const v4, 0x7f0903c3

    invoke-virtual {v0, v3, v4, v1, v2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertAiDetectTipHint(IIJ)V

    goto :goto_0

    :pswitch_2
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module$22$1;->val$config:Lcom/android/camera/fragment/top/FragmentTopConfig;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module$22$1;->val$config:Lcom/android/camera/fragment/top/FragmentTopConfig;

    const v4, 0x7f0903c5

    invoke-virtual {v0, v3, v4, v1, v2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertAiDetectTipHint(IIJ)V

    goto :goto_0

    :pswitch_3
    nop

    :cond_0
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
