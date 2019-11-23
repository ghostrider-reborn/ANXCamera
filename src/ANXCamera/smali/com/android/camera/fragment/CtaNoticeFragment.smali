.class public Lcom/android/camera/fragment/CtaNoticeFragment;
.super Landroid/app/DialogFragment;
.source "CtaNoticeFragment.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/fragment/CtaNoticeFragment$Type;,
        Lcom/android/camera/fragment/CtaNoticeFragment$OnCtaNoticeClickListener;,
        Lcom/android/camera/fragment/CtaNoticeFragment$CTA;
    }
.end annotation


# static fields
.field public static final TAG:Ljava/lang/String; = "CtaNoticeFragment"

.field public static final TYPE_LIVE_VIDEO:I = 0x1

.field public static final TYPE_STICKER:I = 0x3

.field public static final TYPE_VOICE_CAPTION:I = 0x2


# instance fields
.field private mClickListener:Lcom/android/camera/fragment/CtaNoticeFragment$OnCtaNoticeClickListener;

.field private mShowRemindButton:Z

.field private mType:I


# direct methods
.method public constructor <init>(ZLcom/android/camera/fragment/CtaNoticeFragment$OnCtaNoticeClickListener;I)V
    .locals 0

    invoke-direct {p0}, Landroid/app/DialogFragment;-><init>()V

    iput-boolean p1, p0, Lcom/android/camera/fragment/CtaNoticeFragment;->mShowRemindButton:Z

    iput-object p2, p0, Lcom/android/camera/fragment/CtaNoticeFragment;->mClickListener:Lcom/android/camera/fragment/CtaNoticeFragment$OnCtaNoticeClickListener;

    iput p3, p0, Lcom/android/camera/fragment/CtaNoticeFragment;->mType:I

    return-void
.end method

.method public static checkCta(Landroid/app/FragmentManager;I)Z
    .locals 1

    const/4 v0, 0x1

    invoke-static {p0, v0, p1}, Lcom/android/camera/fragment/CtaNoticeFragment;->checkCta(Landroid/app/FragmentManager;ZI)Z

    move-result p0

    return p0
.end method

.method public static checkCta(Landroid/app/FragmentManager;ZI)Z
    .locals 1

    const/4 v0, 0x0

    invoke-static {p0, p1, v0, p2}, Lcom/android/camera/fragment/CtaNoticeFragment;->checkCta(Landroid/app/FragmentManager;ZLcom/android/camera/fragment/CtaNoticeFragment$OnCtaNoticeClickListener;I)Z

    move-result p0

    return p0
.end method

.method public static checkCta(Landroid/app/FragmentManager;ZLcom/android/camera/fragment/CtaNoticeFragment$OnCtaNoticeClickListener;I)Z
    .locals 0

    invoke-static {p0, p1, p2, p3}, Lcom/android/camera/fragment/CtaNoticeFragment;->showCta(Landroid/app/FragmentManager;ZLcom/android/camera/fragment/CtaNoticeFragment$OnCtaNoticeClickListener;I)Lcom/android/camera/fragment/CtaNoticeFragment;

    move-result-object p0

    if-nez p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public static synthetic lambda$onCreateDialog$0(Lcom/android/camera/fragment/CtaNoticeFragment;Landroid/content/DialogInterface;I)V
    .locals 2

    iget-boolean v0, p0, Lcom/android/camera/fragment/CtaNoticeFragment;->mShowRemindButton:Z

    const/4 v1, 0x1

    if-eqz v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/fragment/CtaNoticeFragment;->getDialog()Landroid/app/Dialog;

    move-result-object v0

    check-cast v0, Lmiui/app/AlertDialog;

    invoke-virtual {v0}, Lmiui/app/AlertDialog;->isChecked()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    move v0, v1

    :goto_1
    invoke-static {v0, v1}, Lcom/android/camera/fragment/CtaNoticeFragment$CTA;->setCanConnectNetwork(ZZ)V

    iget-object v0, p0, Lcom/android/camera/fragment/CtaNoticeFragment;->mClickListener:Lcom/android/camera/fragment/CtaNoticeFragment$OnCtaNoticeClickListener;

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/fragment/CtaNoticeFragment;->mClickListener:Lcom/android/camera/fragment/CtaNoticeFragment$OnCtaNoticeClickListener;

    invoke-interface {v0, p1, p2}, Lcom/android/camera/fragment/CtaNoticeFragment$OnCtaNoticeClickListener;->onPositiveClick(Landroid/content/DialogInterface;I)V

    :cond_2
    return-void
.end method

.method public static synthetic lambda$onCreateDialog$1(Lcom/android/camera/fragment/CtaNoticeFragment;Landroid/content/DialogInterface;I)V
    .locals 2

    iget-boolean v0, p0, Lcom/android/camera/fragment/CtaNoticeFragment;->mShowRemindButton:Z

    const/4 v1, 0x0

    if-eqz v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/fragment/CtaNoticeFragment;->getDialog()Landroid/app/Dialog;

    move-result-object v0

    check-cast v0, Lmiui/app/AlertDialog;

    invoke-virtual {v0}, Lmiui/app/AlertDialog;->isChecked()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    move v0, v1

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    invoke-static {v0, v1}, Lcom/android/camera/fragment/CtaNoticeFragment$CTA;->setCanConnectNetwork(ZZ)V

    iget-object v0, p0, Lcom/android/camera/fragment/CtaNoticeFragment;->mClickListener:Lcom/android/camera/fragment/CtaNoticeFragment$OnCtaNoticeClickListener;

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/fragment/CtaNoticeFragment;->mClickListener:Lcom/android/camera/fragment/CtaNoticeFragment$OnCtaNoticeClickListener;

    invoke-interface {v0, p1, p2}, Lcom/android/camera/fragment/CtaNoticeFragment$OnCtaNoticeClickListener;->onNegativeClick(Landroid/content/DialogInterface;I)V

    :cond_2
    return-void
.end method

.method public static showCta(Landroid/app/FragmentManager;ZLcom/android/camera/fragment/CtaNoticeFragment$OnCtaNoticeClickListener;I)Lcom/android/camera/fragment/CtaNoticeFragment;
    .locals 1

    invoke-static {}, Lcom/android/camera/fragment/CtaNoticeFragment$CTA;->canConnectNetwork()Z

    move-result v0

    if-nez v0, :cond_1

    const-string v0, "CtaNoticeFragment"

    invoke-virtual {p0, v0}, Landroid/app/FragmentManager;->findFragmentByTag(Ljava/lang/String;)Landroid/app/Fragment;

    move-result-object v0

    if-nez v0, :cond_0

    new-instance v0, Lcom/android/camera/fragment/CtaNoticeFragment;

    invoke-direct {v0, p1, p2, p3}, Lcom/android/camera/fragment/CtaNoticeFragment;-><init>(ZLcom/android/camera/fragment/CtaNoticeFragment$OnCtaNoticeClickListener;I)V

    const-string p1, "CtaNoticeFragment"

    invoke-virtual {v0, p0, p1}, Lcom/android/camera/fragment/CtaNoticeFragment;->show(Landroid/app/FragmentManager;Ljava/lang/String;)V

    return-object v0

    :cond_0
    check-cast v0, Lcom/android/camera/fragment/CtaNoticeFragment;

    return-object v0

    :cond_1
    const/4 p0, 0x0

    return-object p0
.end method


# virtual methods
.method public onCreate(Landroid/os/Bundle;)V
    .locals 0

    invoke-super {p0, p1}, Landroid/app/DialogFragment;->onCreate(Landroid/os/Bundle;)V

    const/4 p1, 0x0

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/CtaNoticeFragment;->setCancelable(Z)V

    return-void
.end method

.method public onCreateDialog(Landroid/os/Bundle;)Landroid/app/Dialog;
    .locals 8

    const-string p1, "%s_%s"

    const/4 v0, 0x2

    new-array v1, v0, [Ljava/lang/Object;

    invoke-static {}, Ljava/util/Locale;->getDefault()Ljava/util/Locale;

    move-result-object v2

    invoke-virtual {v2}, Ljava/util/Locale;->getLanguage()Ljava/lang/String;

    move-result-object v2

    const/4 v3, 0x0

    aput-object v2, v1, v3

    invoke-static {}, Ljava/util/Locale;->getDefault()Ljava/util/Locale;

    move-result-object v2

    invoke-virtual {v2}, Ljava/util/Locale;->getCountry()Ljava/lang/String;

    move-result-object v2

    const/4 v4, 0x1

    aput-object v2, v1, v4

    invoke-static {p1, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    new-array v1, v4, [Ljava/lang/Object;

    aput-object p1, v1, v3

    const v2, 0x7f0903b1

    invoke-virtual {p0, v2, v1}, Lcom/android/camera/fragment/CtaNoticeFragment;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    new-array v2, v4, [Ljava/lang/Object;

    aput-object p1, v2, v3

    const v5, 0x7f0903b2

    invoke-virtual {p0, v5, v2}, Lcom/android/camera/fragment/CtaNoticeFragment;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    const-string v5, "CtaNoticeFragment"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "onCreateDialog: lang = "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string p1, ", linkUserAgreement = "

    invoke-virtual {v6, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string p1, ", linkPrivacyPolicy = "

    invoke-virtual {v6, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v5, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget p1, p0, Lcom/android/camera/fragment/CtaNoticeFragment;->mType:I

    const/16 v5, 0x3f

    packed-switch p1, :pswitch_data_0

    const p1, 0x7f0903b7

    new-array v0, v0, [Ljava/lang/Object;

    aput-object v1, v0, v3

    aput-object v2, v0, v4

    invoke-virtual {p0, p1, v0}, Lcom/android/camera/fragment/CtaNoticeFragment;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    invoke-static {p1, v5}, Landroid/text/Html;->fromHtml(Ljava/lang/String;I)Landroid/text/Spanned;

    move-result-object p1

    goto :goto_0

    :pswitch_0
    const p1, 0x7f0903b9

    new-array v0, v0, [Ljava/lang/Object;

    aput-object v1, v0, v3

    aput-object v2, v0, v4

    invoke-virtual {p0, p1, v0}, Lcom/android/camera/fragment/CtaNoticeFragment;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    invoke-static {p1, v5}, Landroid/text/Html;->fromHtml(Ljava/lang/String;I)Landroid/text/Spanned;

    move-result-object p1

    goto :goto_0

    :pswitch_1
    const p1, 0x7f0903b8

    new-array v0, v0, [Ljava/lang/Object;

    aput-object v1, v0, v3

    aput-object v2, v0, v4

    invoke-virtual {p0, p1, v0}, Lcom/android/camera/fragment/CtaNoticeFragment;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    invoke-static {p1, v5}, Landroid/text/Html;->fromHtml(Ljava/lang/String;I)Landroid/text/Spanned;

    move-result-object p1

    nop

    :goto_0
    const-string v0, "CtaNoticeFragment"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onCreateDialog: messageRes = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    new-instance v0, Lmiui/app/AlertDialog$Builder;

    invoke-virtual {p0}, Lcom/android/camera/fragment/CtaNoticeFragment;->getActivity()Landroid/app/Activity;

    move-result-object v1

    invoke-direct {v0, v1}, Lmiui/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    const v1, 0x7f0901f4

    invoke-virtual {v0, v1}, Lmiui/app/AlertDialog$Builder;->setTitle(I)Lmiui/app/AlertDialog$Builder;

    move-result-object v0

    invoke-virtual {v0, p1}, Lmiui/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Lmiui/app/AlertDialog$Builder;

    move-result-object p1

    const v0, 0x7f0903b6

    new-instance v1, Lcom/android/camera/fragment/-$$Lambda$CtaNoticeFragment$uBZ8QYsXzgmTMjDTFAeHf3avewE;

    invoke-direct {v1, p0}, Lcom/android/camera/fragment/-$$Lambda$CtaNoticeFragment$uBZ8QYsXzgmTMjDTFAeHf3avewE;-><init>(Lcom/android/camera/fragment/CtaNoticeFragment;)V

    invoke-virtual {p1, v0, v1}, Lmiui/app/AlertDialog$Builder;->setPositiveButton(ILandroid/content/DialogInterface$OnClickListener;)Lmiui/app/AlertDialog$Builder;

    move-result-object p1

    const/high16 v0, 0x1040000

    new-instance v1, Lcom/android/camera/fragment/-$$Lambda$CtaNoticeFragment$Cjk6qTdu86iDNcKmyeL_M2i908k;

    invoke-direct {v1, p0}, Lcom/android/camera/fragment/-$$Lambda$CtaNoticeFragment$Cjk6qTdu86iDNcKmyeL_M2i908k;-><init>(Lcom/android/camera/fragment/CtaNoticeFragment;)V

    invoke-virtual {p1, v0, v1}, Lmiui/app/AlertDialog$Builder;->setNegativeButton(ILandroid/content/DialogInterface$OnClickListener;)Lmiui/app/AlertDialog$Builder;

    move-result-object p1

    iget-boolean v0, p0, Lcom/android/camera/fragment/CtaNoticeFragment;->mShowRemindButton:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/fragment/CtaNoticeFragment;->getActivity()Landroid/app/Activity;

    move-result-object v0

    const v1, 0x7f0901f1

    invoke-virtual {v0, v1}, Landroid/app/Activity;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v4, v0}, Lmiui/app/AlertDialog$Builder;->setCheckBox(ZLjava/lang/CharSequence;)Lmiui/app/AlertDialog$Builder;

    :cond_0
    invoke-virtual {p1}, Lmiui/app/AlertDialog$Builder;->create()Lmiui/app/AlertDialog;

    move-result-object p1

    return-object p1

    :pswitch_data_0
    .packed-switch 0x2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public onStart()V
    .locals 2

    invoke-super {p0}, Landroid/app/DialogFragment;->onStart()V

    invoke-virtual {p0}, Lcom/android/camera/fragment/CtaNoticeFragment;->getDialog()Landroid/app/Dialog;

    move-result-object v0

    if-eqz v0, :cond_0

    check-cast v0, Lmiui/app/AlertDialog;

    invoke-virtual {v0}, Lmiui/app/AlertDialog;->getMessageView()Landroid/widget/TextView;

    move-result-object v0

    invoke-static {}, Landroid/text/method/LinkMovementMethod;->getInstance()Landroid/text/method/MovementMethod;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setMovementMethod(Landroid/text/method/MovementMethod;)V

    :cond_0
    return-void
.end method
