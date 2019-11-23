.class Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;
.super Ljava/lang/Object;
.source "FragmentSubtitle.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/fragment/subtitle/FragmentSubtitle;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "RecognitionListener"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;


# direct methods
.method constructor <init>(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method onRecognitionListener(Ljava/lang/String;)V
    .locals 3
    .annotation build Landroid/support/annotation/RequiresApi;
        api = 0x15
    .end annotation

    iget-object v0, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {v0}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$500(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Landroid/os/Handler;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    iget-object v0, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {v0}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$000(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Landroid/widget/TextView;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object v0, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {v0}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$100(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Landroid/widget/TextView;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object v0, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {v0}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$200(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Landroid/widget/TextView;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object v0, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {v0}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$300(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Landroid/widget/TextView;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object v0, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-virtual {v0}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f0903ca

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    const/16 v0, 0x64

    if-eqz p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$000(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Landroid/widget/TextView;

    move-result-object p1

    const/4 v1, 0x0

    invoke-virtual {p1, v1}, Landroid/widget/TextView;->setLetterSpacing(F)V

    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$100(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Landroid/widget/TextView;

    move-result-object p1

    invoke-virtual {p1, v1}, Landroid/widget/TextView;->setLetterSpacing(F)V

    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$200(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Landroid/widget/TextView;

    move-result-object p1

    invoke-virtual {p1, v1}, Landroid/widget/TextView;->setLetterSpacing(F)V

    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$300(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Landroid/widget/TextView;

    move-result-object p1

    invoke-virtual {p1, v1}, Landroid/widget/TextView;->setLetterSpacing(F)V

    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$500(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Landroid/os/Handler;

    move-result-object p1

    const-wide/16 v1, 0x1388

    invoke-virtual {p1, v0, v1, v2}, Landroid/os/Handler;->sendEmptyMessageDelayed(IJ)Z

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$000(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Landroid/widget/TextView;

    move-result-object p1

    const v1, 0x3dcccccd    # 0.1f

    invoke-virtual {p1, v1}, Landroid/widget/TextView;->setLetterSpacing(F)V

    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$100(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Landroid/widget/TextView;

    move-result-object p1

    invoke-virtual {p1, v1}, Landroid/widget/TextView;->setLetterSpacing(F)V

    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$200(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Landroid/widget/TextView;

    move-result-object p1

    invoke-virtual {p1, v1}, Landroid/widget/TextView;->setLetterSpacing(F)V

    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$300(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Landroid/widget/TextView;

    move-result-object p1

    invoke-virtual {p1, v1}, Landroid/widget/TextView;->setLetterSpacing(F)V

    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$500(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Landroid/os/Handler;

    move-result-object p1

    const-wide/16 v1, 0x7d0

    invoke-virtual {p1, v0, v1, v2}, Landroid/os/Handler;->sendEmptyMessageDelayed(IJ)Z

    :goto_0
    return-void
.end method

.method onVoiceVolume(I)V
    .locals 4

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    iget-object v2, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {v2}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$600(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)J

    move-result-wide v2

    sub-long/2addr v0, v2

    const-wide/16 v2, 0x9c4

    cmp-long v0, v0, v2

    if-lez v0, :cond_0

    if-gtz p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    const/4 v0, 0x0

    invoke-static {p1, v0}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$702(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;Z)Z

    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$800(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Lcom/android/camera/fragment/subtitle/SoundWaveView;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/fragment/subtitle/SoundWaveView;->resetAnimation()V

    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$900(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Lcom/android/camera/fragment/subtitle/SoundWaveView;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/fragment/subtitle/SoundWaveView;->resetAnimation()V

    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$1000(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Lcom/android/camera/fragment/subtitle/SoundWaveView;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/fragment/subtitle/SoundWaveView;->resetAnimation()V

    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$1100(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Lcom/android/camera/fragment/subtitle/SoundWaveView;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/fragment/subtitle/SoundWaveView;->resetAnimation()V

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$700(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Z

    move-result p1

    if-nez p1, :cond_1

    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    invoke-static {p1, v0, v1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$602(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;J)J

    :cond_1
    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    const/4 v0, 0x1

    invoke-static {p1, v0}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$702(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;Z)Z

    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$800(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Lcom/android/camera/fragment/subtitle/SoundWaveView;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/fragment/subtitle/SoundWaveView;->getVisibility()I

    move-result p1

    if-nez p1, :cond_2

    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$800(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Lcom/android/camera/fragment/subtitle/SoundWaveView;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/fragment/subtitle/SoundWaveView;->startAnimation()V

    :cond_2
    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$900(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Lcom/android/camera/fragment/subtitle/SoundWaveView;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/fragment/subtitle/SoundWaveView;->getVisibility()I

    move-result p1

    if-nez p1, :cond_3

    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$900(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Lcom/android/camera/fragment/subtitle/SoundWaveView;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/fragment/subtitle/SoundWaveView;->startAnimation()V

    :cond_3
    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$1000(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Lcom/android/camera/fragment/subtitle/SoundWaveView;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/fragment/subtitle/SoundWaveView;->getVisibility()I

    move-result p1

    if-nez p1, :cond_4

    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$1000(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Lcom/android/camera/fragment/subtitle/SoundWaveView;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/fragment/subtitle/SoundWaveView;->startAnimation()V

    :cond_4
    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$1100(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Lcom/android/camera/fragment/subtitle/SoundWaveView;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/fragment/subtitle/SoundWaveView;->getVisibility()I

    move-result p1

    if-nez p1, :cond_5

    iget-object p1, p0, Lcom/android/camera/fragment/subtitle/FragmentSubtitle$RecognitionListener;->this$0:Lcom/android/camera/fragment/subtitle/FragmentSubtitle;

    invoke-static {p1}, Lcom/android/camera/fragment/subtitle/FragmentSubtitle;->access$1100(Lcom/android/camera/fragment/subtitle/FragmentSubtitle;)Lcom/android/camera/fragment/subtitle/SoundWaveView;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/fragment/subtitle/SoundWaveView;->startAnimation()V

    :cond_5
    :goto_0
    return-void
.end method
