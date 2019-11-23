.class public Lcom/android/camera/fragment/vv/FragmentVV;
.super Lcom/android/camera/fragment/BaseFragment;
.source "FragmentVV.java"

# interfaces
.implements Landroid/view/View$OnClickListener;
.implements Lcom/android/camera/fragment/vv/ResourceSelectedListener;
.implements Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;
.implements Lcom/android/camera/protocol/ModeProtocol$LiveVVChooser;


# instance fields
.field private mSelectedItem:Lcom/android/camera/fragment/vv/VVItem;

.field private mShotView:Landroid/view/View;


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/BaseFragment;-><init>()V

    return-void
.end method

.method private adjustViewBackground(Landroid/view/View;I)V
    .locals 0

    const p2, 0x7f0c0011

    invoke-virtual {p1, p2}, Landroid/view/View;->setBackgroundResource(I)V

    return-void
.end method

.method private initFragment(I)V
    .locals 3

    new-instance v0, Lcom/android/camera/fragment/vv/FragmentVVGallery;

    invoke-direct {v0}, Lcom/android/camera/fragment/vv/FragmentVVGallery;-><init>()V

    invoke-virtual {v0, p0}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->setResourceSelectedListener(Lcom/android/camera/fragment/vv/ResourceSelectedListener;)V

    invoke-virtual {v0}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->registerProtocol()V

    invoke-virtual {v0, p1}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->setPreviewData(I)V

    invoke-virtual {p0}, Lcom/android/camera/fragment/vv/FragmentVV;->getChildFragmentManager()Landroid/support/v4/app/FragmentManager;

    move-result-object p1

    invoke-virtual {v0}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->getFragmentTag()Ljava/lang/String;

    move-result-object v1

    const v2, 0x7f0e0103

    invoke-static {p1, v2, v0, v1}, Lcom/android/camera/fragment/FragmentUtils;->addFragmentWithTag(Landroid/support/v4/app/FragmentManager;ILandroid/support/v4/app/Fragment;Ljava/lang/String;)V

    return-void
.end method


# virtual methods
.method public getFragmentInto()I
    .locals 1

    const v0, 0xfff3

    return v0
.end method

.method protected getLayoutResourceId()I
    .locals 1

    const v0, 0x7f040045

    return v0
.end method

.method public hide()V
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/fragment/vv/FragmentVV;->getChildFragmentManager()Landroid/support/v4/app/FragmentManager;

    move-result-object v0

    const v1, 0xfff4

    invoke-static {v1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/fragment/FragmentUtils;->removeFragmentByTag(Landroid/support/v4/app/FragmentManager;Ljava/lang/String;)Z

    invoke-virtual {p0}, Lcom/android/camera/fragment/vv/FragmentVV;->getChildFragmentManager()Landroid/support/v4/app/FragmentManager;

    move-result-object v0

    const v1, 0xfff5

    invoke-static {v1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/fragment/FragmentUtils;->removeFragmentByTag(Landroid/support/v4/app/FragmentManager;Ljava/lang/String;)Z

    invoke-virtual {p0}, Lcom/android/camera/fragment/vv/FragmentVV;->getView()Landroid/view/View;

    move-result-object v0

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    return-void
.end method

.method protected initView(Landroid/view/View;)V
    .locals 2

    nop

    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup$MarginLayoutParams;

    const/4 v1, -0x2

    iput v1, v0, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    iget v0, p0, Lcom/android/camera/fragment/vv/FragmentVV;->mCurrentMode:I

    invoke-direct {p0, p1, v0}, Lcom/android/camera/fragment/vv/FragmentVV;->adjustViewBackground(Landroid/view/View;I)V

    const v0, 0x7f0e0104

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVV;->mShotView:Landroid/view/View;

    iget-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVV;->mShotView:Landroid/view/View;

    const/4 v0, 0x4

    invoke-virtual {p1, v0}, Landroid/view/View;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVV;->mShotView:Landroid/view/View;

    invoke-virtual {p1, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const/4 p1, 0x0

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/vv/FragmentVV;->initFragment(I)V

    return-void
.end method

.method public isPreviewShow()Z
    .locals 3

    invoke-virtual {p0}, Lcom/android/camera/fragment/vv/FragmentVV;->isShow()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    nop

    invoke-virtual {p0}, Lcom/android/camera/fragment/vv/FragmentVV;->getChildFragmentManager()Landroid/support/v4/app/FragmentManager;

    move-result-object v0

    const v2, 0xfff5

    invoke-static {v2}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Lcom/android/camera/fragment/FragmentUtils;->getFragmentByTag(Landroid/support/v4/app/FragmentManager;Ljava/lang/String;)Landroid/support/v4/app/Fragment;

    move-result-object v0

    check-cast v0, Lcom/android/camera/fragment/vv/FragmentVVPreview;

    if-eqz v0, :cond_1

    invoke-virtual {v0}, Lcom/android/camera/fragment/vv/FragmentVVPreview;->isVisible()Z

    move-result v0

    if-eqz v0, :cond_1

    const/4 v0, 0x1

    return v0

    :cond_1
    return v1
.end method

.method public isShow()Z
    .locals 1

    invoke-virtual {p0}, Lcom/android/camera/fragment/vv/FragmentVV;->isAdded()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/fragment/vv/FragmentVV;->getView()Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0}, Landroid/view/View;->getVisibility()I

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public onBackEvent(I)Z
    .locals 0

    const/4 p1, 0x0

    return p1
.end method

.method public onClick(Landroid/view/View;)V
    .locals 1

    invoke-virtual {p0}, Lcom/android/camera/fragment/vv/FragmentVV;->isEnableClick()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result p1

    const v0, 0x7f0e0104

    if-eq p1, v0, :cond_1

    goto :goto_0

    :cond_1
    invoke-virtual {p0}, Lcom/android/camera/fragment/vv/FragmentVV;->startShot()V

    :goto_0
    return-void
.end method

.method public onResourceReady()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/fragment/vv/FragmentVV;->mShotView:Landroid/view/View;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    return-void
.end method

.method public onResourceSelected(Lcom/android/camera/fragment/vv/VVItem;)V
    .locals 3

    const-string/jumbo v0, "vvSelected:"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    iget v2, p1, Lcom/android/camera/fragment/vv/VVItem;->index:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, " | "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p1, Lcom/android/camera/fragment/vv/VVItem;->name:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iput-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVV;->mSelectedItem:Lcom/android/camera/fragment/vv/VVItem;

    return-void
.end method

.method protected provideEnterAnimation(I)Landroid/view/animation/Animation;
    .locals 0

    const/4 p1, 0x2

    new-array p1, p1, [I

    fill-array-data p1, :array_0

    invoke-static {p1}, Lcom/android/camera/animation/FragmentAnimationFactory;->wrapperAnimation([I)Landroid/view/animation/Animation;

    move-result-object p1

    return-object p1

    nop

    :array_0
    .array-data 4
        0xa7
        0xa1
    .end array-data
.end method

.method protected provideExitAnimation(I)Landroid/view/animation/Animation;
    .locals 0

    const/4 p1, 0x2

    new-array p1, p1, [I

    fill-array-data p1, :array_0

    invoke-static {p1}, Lcom/android/camera/animation/FragmentAnimationFactory;->wrapperAnimation([I)Landroid/view/animation/Animation;

    move-result-object p1

    return-object p1

    nop

    :array_0
    .array-data 4
        0xa2
        0xa8
    .end array-data
.end method

.method protected register(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V
    .locals 1

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->register(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V

    const/16 v0, 0xe5

    invoke-interface {p1, v0, p0}, Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;->attachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    invoke-virtual {p0, p1, p0}, Lcom/android/camera/fragment/vv/FragmentVV;->registerBackStack(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;)V

    return-void
.end method

.method public show(I)V
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/fragment/vv/FragmentVV;->getView()Landroid/view/View;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/vv/FragmentVV;->initFragment(I)V

    return-void
.end method

.method public startShot()V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/fragment/vv/FragmentVV;->mSelectedItem:Lcom/android/camera/fragment/vv/VVItem;

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa4

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;

    if-nez v0, :cond_1

    return-void

    :cond_1
    invoke-virtual {p0}, Lcom/android/camera/fragment/vv/FragmentVV;->isPreviewShow()Z

    move-result v1

    iget-object v2, p0, Lcom/android/camera/fragment/vv/FragmentVV;->mSelectedItem:Lcom/android/camera/fragment/vv/VVItem;

    iget-object v2, v2, Lcom/android/camera/fragment/vv/VVItem;->name:Ljava/lang/String;

    invoke-static {v2, v1}, Lcom/android/camera/statistic/CameraStatUtil;->trackVVStartClick(Ljava/lang/String;Z)V

    iget-object v1, p0, Lcom/android/camera/fragment/vv/FragmentVV;->mSelectedItem:Lcom/android/camera/fragment/vv/VVItem;

    const/4 v2, 0x1

    const/4 v3, 0x0

    invoke-interface {v0, v1, v2, v3}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->configLiveVV(Lcom/android/camera/fragment/vv/VVItem;ZZ)V

    return-void
.end method

.method protected unRegister(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V
    .locals 1

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->unRegister(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V

    const/16 v0, 0xe5

    invoke-interface {p1, v0, p0}, Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;->detachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    invoke-virtual {p0, p1, p0}, Lcom/android/camera/fragment/vv/FragmentVV;->unRegisterBackStack(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;)V

    return-void
.end method
