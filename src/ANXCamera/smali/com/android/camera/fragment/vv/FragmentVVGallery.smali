.class public Lcom/android/camera/fragment/vv/FragmentVVGallery;
.super Lcom/android/camera/fragment/BaseFragment;
.source "FragmentVVGallery.java"

# interfaces
.implements Landroid/view/View$OnClickListener;
.implements Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/fragment/vv/FragmentVVGallery$EffectItemPadding;
    }
.end annotation


# instance fields
.field private mGalleryAdapter:Lcom/android/camera/fragment/vv/VVGalleryAdapter;

.field private mHolderWidth:I

.field private mLayoutManager:Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;

.field private mPreviewIndex:I

.field private mProgressView:Landroid/view/View;

.field private mRecyclerView:Landroid/support/v7/widget/RecyclerView;

.field private mRecyclerViewLayout:Landroid/view/View;

.field private mResourceSelectedListener:Lcom/android/camera/fragment/vv/ResourceSelectedListener;

.field private mTotalWidth:I

.field private mVVList:Lcom/android/camera/fragment/vv/VVList;


# direct methods
.method public constructor <init>()V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/BaseFragment;-><init>()V

    const/4 v0, -0x1

    iput v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mPreviewIndex:I

    return-void
.end method

.method static synthetic access$000(Lcom/android/camera/fragment/vv/FragmentVVGallery;)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->initList()V

    return-void
.end method

.method static synthetic access$100(Lcom/android/camera/fragment/vv/FragmentVVGallery;)Lcom/android/camera/fragment/vv/VVList;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mVVList:Lcom/android/camera/fragment/vv/VVList;

    return-object p0
.end method

.method private initList()V
    .locals 8

    invoke-virtual {p0}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->isAdded()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mVVList:Lcom/android/camera/fragment/vv/VVList;

    iget-object v1, v1, Lcom/android/camera/fragment/vv/VVList;->version:Ljava/lang/String;

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/extra/DataItemLive;->setVVVersion(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mResourceSelectedListener:Lcom/android/camera/fragment/vv/ResourceSelectedListener;

    invoke-interface {v0}, Lcom/android/camera/fragment/vv/ResourceSelectedListener;->onResourceReady()V

    iget v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mPreviewIndex:I

    const/4 v1, 0x0

    if-ltz v0, :cond_1

    iget v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mPreviewIndex:I

    iget-object v2, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mVVList:Lcom/android/camera/fragment/vv/VVList;

    invoke-virtual {v2}, Lcom/android/camera/fragment/vv/VVList;->getSize()I

    move-result v2

    if-ge v0, v2, :cond_1

    iget-object v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mResourceSelectedListener:Lcom/android/camera/fragment/vv/ResourceSelectedListener;

    iget-object v2, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mVVList:Lcom/android/camera/fragment/vv/VVList;

    iget v3, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mPreviewIndex:I

    invoke-virtual {v2, v3}, Lcom/android/camera/fragment/vv/VVList;->getItem(I)Lcom/android/camera/fragment/vv/BaseResourceItem;

    move-result-object v2

    check-cast v2, Lcom/android/camera/fragment/vv/VVItem;

    invoke-interface {v0, v2}, Lcom/android/camera/fragment/vv/ResourceSelectedListener;->onResourceSelected(Lcom/android/camera/fragment/vv/VVItem;)V

    goto :goto_0

    :cond_1
    iget-object v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mResourceSelectedListener:Lcom/android/camera/fragment/vv/ResourceSelectedListener;

    iget-object v2, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mVVList:Lcom/android/camera/fragment/vv/VVList;

    invoke-virtual {v2, v1}, Lcom/android/camera/fragment/vv/VVList;->getItem(I)Lcom/android/camera/fragment/vv/BaseResourceItem;

    move-result-object v2

    check-cast v2, Lcom/android/camera/fragment/vv/VVItem;

    invoke-interface {v0, v2}, Lcom/android/camera/fragment/vv/ResourceSelectedListener;->onResourceSelected(Lcom/android/camera/fragment/vv/VVItem;)V

    :goto_0
    iget-object v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mRecyclerViewLayout:Landroid/view/View;

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    iget-object v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mProgressView:Landroid/view/View;

    const/16 v2, 0x8

    invoke-virtual {v0, v2}, Landroid/view/View;->setVisibility(I)V

    new-instance v0, Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;

    invoke-virtual {p0}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->getContext()Landroid/content/Context;

    move-result-object v2

    const-string/jumbo v3, "vv_gallery"

    invoke-direct {v0, v2, v3}, Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;-><init>(Landroid/content/Context;Ljava/lang/String;)V

    iput-object v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mLayoutManager:Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;

    iget-object v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mLayoutManager:Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;

    invoke-virtual {v0, v1}, Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;->setOrientation(I)V

    new-instance v0, Lcom/android/camera/fragment/vv/VVGalleryAdapter;

    iget-object v3, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mVVList:Lcom/android/camera/fragment/vv/VVList;

    iget-object v4, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mLayoutManager:Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;

    iget v5, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mPreviewIndex:I

    iget-object v7, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mResourceSelectedListener:Lcom/android/camera/fragment/vv/ResourceSelectedListener;

    move-object v2, v0

    move-object v6, p0

    invoke-direct/range {v2 .. v7}, Lcom/android/camera/fragment/vv/VVGalleryAdapter;-><init>(Lcom/android/camera/fragment/vv/VVList;Landroid/support/v7/widget/LinearLayoutManager;ILandroid/view/View$OnClickListener;Lcom/android/camera/fragment/vv/ResourceSelectedListener;)V

    iput-object v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mGalleryAdapter:Lcom/android/camera/fragment/vv/VVGalleryAdapter;

    iget-object v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mRecyclerView:Landroid/support/v7/widget/RecyclerView;

    iget-object v1, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mLayoutManager:Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;

    invoke-virtual {v0, v1}, Landroid/support/v7/widget/RecyclerView;->setLayoutManager(Landroid/support/v7/widget/RecyclerView$LayoutManager;)V

    new-instance v0, Lcom/android/camera/fragment/vv/FragmentVVGallery$EffectItemPadding;

    invoke-virtual {p0}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-direct {v0, v1}, Lcom/android/camera/fragment/vv/FragmentVVGallery$EffectItemPadding;-><init>(Landroid/content/Context;)V

    iget-object v1, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mRecyclerView:Landroid/support/v7/widget/RecyclerView;

    invoke-virtual {v1, v0}, Landroid/support/v7/widget/RecyclerView;->addItemDecoration(Landroid/support/v7/widget/RecyclerView$ItemDecoration;)V

    iget-object v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mRecyclerView:Landroid/support/v7/widget/RecyclerView;

    iget-object v1, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mGalleryAdapter:Lcom/android/camera/fragment/vv/VVGalleryAdapter;

    invoke-virtual {v0, v1}, Landroid/support/v7/widget/RecyclerView;->setAdapter(Landroid/support/v7/widget/RecyclerView$Adapter;)V

    iget v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mPreviewIndex:I

    if-ltz v0, :cond_2

    iget v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mPreviewIndex:I

    invoke-direct {p0, v0}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->setItemInCenter(I)V

    :cond_2
    new-instance v0, Lcom/android/camera/fragment/DefaultItemAnimator;

    invoke-direct {v0}, Lcom/android/camera/fragment/DefaultItemAnimator;-><init>()V

    const-wide/16 v1, 0x96

    invoke-virtual {v0, v1, v2}, Landroid/support/v7/widget/RecyclerView$ItemAnimator;->setChangeDuration(J)V

    invoke-virtual {v0, v1, v2}, Landroid/support/v7/widget/RecyclerView$ItemAnimator;->setMoveDuration(J)V

    invoke-virtual {v0, v1, v2}, Landroid/support/v7/widget/RecyclerView$ItemAnimator;->setAddDuration(J)V

    iget-object v1, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mRecyclerView:Landroid/support/v7/widget/RecyclerView;

    invoke-virtual {v1, v0}, Landroid/support/v7/widget/RecyclerView;->setItemAnimator(Landroid/support/v7/widget/RecyclerView$ItemAnimator;)V

    return-void
.end method

.method private loadItemList()V
    .locals 2

    invoke-static {}, Lcom/android/camera/fragment/vv/ResourceManager;->getInstance()Lcom/android/camera/fragment/vv/ResourceManager;

    move-result-object v0

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/android/camera/fragment/vv/ResourceManager;->getResourceList(I)Lcom/android/camera/fragment/vv/BaseResourceList;

    move-result-object v0

    check-cast v0, Lcom/android/camera/fragment/vv/VVList;

    iput-object v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mVVList:Lcom/android/camera/fragment/vv/VVList;

    iget-object v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mVVList:Lcom/android/camera/fragment/vv/VVList;

    if-eqz v0, :cond_0

    invoke-direct {p0}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->initList()V

    goto :goto_0

    :cond_0
    new-instance v0, Lcom/android/camera/fragment/vv/VVList;

    invoke-direct {v0}, Lcom/android/camera/fragment/vv/VVList;-><init>()V

    iput-object v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mVVList:Lcom/android/camera/fragment/vv/VVList;

    iget-object v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mRecyclerViewLayout:Landroid/view/View;

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    iget-object v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mProgressView:Landroid/view/View;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/extra/DataItemLive;->getVVVersion()Ljava/lang/String;

    move-result-object v0

    new-instance v1, Lcom/android/camera/fragment/vv/FragmentVVGallery$2;

    invoke-direct {v1, p0, v0}, Lcom/android/camera/fragment/vv/FragmentVVGallery$2;-><init>(Lcom/android/camera/fragment/vv/FragmentVVGallery;Ljava/lang/String;)V

    invoke-static {v1}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object v0

    invoke-static {}, Lio/reactivex/schedulers/Schedulers;->io()Lio/reactivex/Scheduler;

    move-result-object v1

    invoke-virtual {v0, v1}, Lio/reactivex/Completable;->subscribeOn(Lio/reactivex/Scheduler;)Lio/reactivex/Completable;

    move-result-object v0

    invoke-static {}, Lio/reactivex/android/schedulers/AndroidSchedulers;->mainThread()Lio/reactivex/Scheduler;

    move-result-object v1

    invoke-virtual {v0, v1}, Lio/reactivex/Completable;->observeOn(Lio/reactivex/Scheduler;)Lio/reactivex/Completable;

    move-result-object v0

    new-instance v1, Lcom/android/camera/fragment/vv/FragmentVVGallery$1;

    invoke-direct {v1, p0}, Lcom/android/camera/fragment/vv/FragmentVVGallery$1;-><init>(Lcom/android/camera/fragment/vv/FragmentVVGallery;)V

    invoke-virtual {v0, v1}, Lio/reactivex/Completable;->subscribe(Lio/reactivex/functions/Action;)Lio/reactivex/disposables/Disposable;

    :goto_0
    return-void
.end method

.method private setItemInCenter(I)V
    .locals 2

    iget v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mTotalWidth:I

    div-int/lit8 v0, v0, 0x2

    iget v1, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mHolderWidth:I

    div-int/lit8 v1, v1, 0x2

    sub-int/2addr v0, v1

    iget-object v1, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mLayoutManager:Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;

    invoke-virtual {v1, p1, v0}, Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;->scrollToPositionWithOffset(II)V

    return-void
.end method

.method private transformToPreview(ILandroid/view/View;)V
    .locals 2

    new-instance v0, Lcom/android/camera/fragment/vv/FragmentVVPreview;

    invoke-direct {v0}, Lcom/android/camera/fragment/vv/FragmentVVPreview;-><init>()V

    iget-object v1, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mVVList:Lcom/android/camera/fragment/vv/VVList;

    invoke-virtual {v0, p1, v1}, Lcom/android/camera/fragment/vv/FragmentVVPreview;->setPreviewData(ILcom/android/camera/fragment/vv/VVList;)V

    iget-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mResourceSelectedListener:Lcom/android/camera/fragment/vv/ResourceSelectedListener;

    invoke-virtual {v0, p1}, Lcom/android/camera/fragment/vv/FragmentVVPreview;->setResourceSelectedListener(Lcom/android/camera/fragment/vv/ResourceSelectedListener;)V

    invoke-virtual {v0}, Lcom/android/camera/fragment/vv/FragmentVVPreview;->registerProtocol()V

    invoke-virtual {p0}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->getFragmentManager()Landroid/support/v4/app/FragmentManager;

    move-result-object p1

    invoke-virtual {p1}, Landroid/support/v4/app/FragmentManager;->beginTransaction()Landroid/support/v4/app/FragmentTransaction;

    move-result-object p1

    invoke-static {p2}, Landroid/support/v4/view/ViewCompat;->getTransitionName(Landroid/view/View;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1, p2, v1}, Landroid/support/v4/app/FragmentTransaction;->addSharedElement(Landroid/view/View;Ljava/lang/String;)Landroid/support/v4/app/FragmentTransaction;

    move-result-object p1

    invoke-virtual {p0}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->getFragmentTag()Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/support/v4/app/FragmentTransaction;->addToBackStack(Ljava/lang/String;)Landroid/support/v4/app/FragmentTransaction;

    move-result-object p1

    invoke-virtual {v0}, Lcom/android/camera/fragment/vv/FragmentVVPreview;->getFragmentTag()Ljava/lang/String;

    move-result-object p2

    const v1, 0x7f0e0103

    invoke-virtual {p1, v1, v0, p2}, Landroid/support/v4/app/FragmentTransaction;->add(ILandroid/support/v4/app/Fragment;Ljava/lang/String;)Landroid/support/v4/app/FragmentTransaction;

    move-result-object p1

    invoke-virtual {p1, p0}, Landroid/support/v4/app/FragmentTransaction;->hide(Landroid/support/v4/app/Fragment;)Landroid/support/v4/app/FragmentTransaction;

    move-result-object p1

    invoke-virtual {p1}, Landroid/support/v4/app/FragmentTransaction;->commitAllowingStateLoss()I

    return-void
.end method


# virtual methods
.method public getFragmentInto()I
    .locals 1

    const v0, 0xfff4

    return v0
.end method

.method protected getLayoutResourceId()I
    .locals 1

    const v0, 0x7f040047

    return v0
.end method

.method protected initView(Landroid/view/View;)V
    .locals 1

    const v0, 0x7f0e0109

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mProgressView:Landroid/view/View;

    const v0, 0x7f0e010a

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mRecyclerViewLayout:Landroid/view/View;

    const v0, 0x7f0e010b

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/support/v7/widget/RecyclerView;

    iput-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mRecyclerView:Landroid/support/v7/widget/RecyclerView;

    invoke-virtual {p0}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->getContext()Landroid/content/Context;

    move-result-object p1

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v0

    iget v0, v0, Landroid/util/DisplayMetrics;->widthPixels:I

    iput v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mTotalWidth:I

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    const v0, 0x7f0d01b9

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result p1

    iput p1, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mHolderWidth:I

    invoke-direct {p0}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->loadItemList()V

    return-void
.end method

.method public onBackEvent(I)Z
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->isVisible()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    const/4 v0, 0x2

    if-ne p1, v0, :cond_1

    return v1

    :cond_1
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v0, 0xa4

    invoke-virtual {p1, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;

    const/16 v0, 0xd8

    invoke-interface {p1, v0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    const/4 p1, 0x1

    return p1
.end method

.method public onClick(Landroid/view/View;)V
    .locals 2

    invoke-virtual {p1}, Landroid/view/View;->getTag()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    const v1, 0x7f0e010c

    invoke-virtual {p1, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    invoke-direct {p0, v0, p1}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->transformToPreview(ILandroid/view/View;)V

    return-void
.end method

.method public onHiddenChanged(Z)V
    .locals 4

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->onHiddenChanged(Z)V

    if-nez p1, :cond_0

    iget p1, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mPreviewIndex:I

    const/4 v0, -0x1

    if-eq p1, v0, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mGalleryAdapter:Lcom/android/camera/fragment/vv/VVGalleryAdapter;

    if-eqz p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mGalleryAdapter:Lcom/android/camera/fragment/vv/VVGalleryAdapter;

    iget v1, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mPreviewIndex:I

    const/4 v2, 0x0

    const/4 v3, 0x0

    invoke-virtual {p1, v1, v2, v3}, Lcom/android/camera/fragment/vv/VVGalleryAdapter;->onSelected(ILandroid/view/View;Z)V

    iget p1, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mPreviewIndex:I

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->setItemInCenter(I)V

    iput v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mPreviewIndex:I

    :cond_0
    return-void
.end method

.method protected register(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V
    .locals 0

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->register(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V

    invoke-virtual {p0, p1, p0}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->registerBackStack(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;)V

    return-void
.end method

.method public setPreviewData(I)V
    .locals 0

    iput p1, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mPreviewIndex:I

    return-void
.end method

.method public setResourceSelectedListener(Lcom/android/camera/fragment/vv/ResourceSelectedListener;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery;->mResourceSelectedListener:Lcom/android/camera/fragment/vv/ResourceSelectedListener;

    return-void
.end method

.method protected unRegister(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V
    .locals 0

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->unRegister(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V

    invoke-virtual {p0, p1, p0}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->unRegisterBackStack(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;)V

    return-void
.end method
