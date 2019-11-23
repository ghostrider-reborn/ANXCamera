.class Lcom/android/camera/fragment/vv/FragmentVVPreview$2;
.super Ljava/lang/Object;
.source "FragmentVVPreview.java"

# interfaces
.implements Landroid/view/View$OnTouchListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/fragment/vv/FragmentVVPreview;->initViewPager()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field nextItem:I

.field final synthetic this$0:Lcom/android/camera/fragment/vv/FragmentVVPreview;

.field final synthetic val$layoutWidth:I

.field final synthetic val$sideWidth:F


# direct methods
.method constructor <init>(Lcom/android/camera/fragment/vv/FragmentVVPreview;FI)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVVPreview$2;->this$0:Lcom/android/camera/fragment/vv/FragmentVVPreview;

    iput p2, p0, Lcom/android/camera/fragment/vv/FragmentVVPreview$2;->val$sideWidth:F

    iput p3, p0, Lcom/android/camera/fragment/vv/FragmentVVPreview$2;->val$layoutWidth:I

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 p1, -0x1

    iput p1, p0, Lcom/android/camera/fragment/vv/FragmentVVPreview$2;->nextItem:I

    return-void
.end method


# virtual methods
.method public onTouch(Landroid/view/View;Landroid/view/MotionEvent;)Z
    .locals 3

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getAction()I

    move-result p1

    const/4 v0, -0x1

    const/4 v1, 0x1

    const/4 v2, 0x0

    packed-switch p1, :pswitch_data_0

    return v2

    :pswitch_0
    iget-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVVPreview$2;->this$0:Lcom/android/camera/fragment/vv/FragmentVVPreview;

    invoke-static {p1}, Lcom/android/camera/fragment/vv/FragmentVVPreview;->access$200(Lcom/android/camera/fragment/vv/FragmentVVPreview;)Landroid/support/v4/view/ViewPager;

    move-result-object p1

    if-eqz p1, :cond_2

    iget-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVVPreview$2;->this$0:Lcom/android/camera/fragment/vv/FragmentVVPreview;

    invoke-static {p1}, Lcom/android/camera/fragment/vv/FragmentVVPreview;->access$300(Lcom/android/camera/fragment/vv/FragmentVVPreview;)Lcom/android/camera/fragment/BaseFragmentPagerAdapter;

    move-result-object p1

    if-nez p1, :cond_0

    goto :goto_0

    :cond_0
    iget p1, p0, Lcom/android/camera/fragment/vv/FragmentVVPreview$2;->nextItem:I

    if-ltz p1, :cond_1

    iget-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVVPreview$2;->this$0:Lcom/android/camera/fragment/vv/FragmentVVPreview;

    invoke-static {p1}, Lcom/android/camera/fragment/vv/FragmentVVPreview;->access$200(Lcom/android/camera/fragment/vv/FragmentVVPreview;)Landroid/support/v4/view/ViewPager;

    move-result-object p1

    iget p2, p0, Lcom/android/camera/fragment/vv/FragmentVVPreview$2;->nextItem:I

    invoke-virtual {p1, p2}, Landroid/support/v4/view/ViewPager;->setCurrentItem(I)V

    iput v0, p0, Lcom/android/camera/fragment/vv/FragmentVVPreview$2;->nextItem:I

    :cond_1
    return v1

    :cond_2
    :goto_0
    return v2

    :pswitch_1
    iget-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVVPreview$2;->this$0:Lcom/android/camera/fragment/vv/FragmentVVPreview;

    invoke-static {p1}, Lcom/android/camera/fragment/vv/FragmentVVPreview;->access$200(Lcom/android/camera/fragment/vv/FragmentVVPreview;)Landroid/support/v4/view/ViewPager;

    move-result-object p1

    if-eqz p1, :cond_8

    iget-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVVPreview$2;->this$0:Lcom/android/camera/fragment/vv/FragmentVVPreview;

    invoke-static {p1}, Lcom/android/camera/fragment/vv/FragmentVVPreview;->access$300(Lcom/android/camera/fragment/vv/FragmentVVPreview;)Lcom/android/camera/fragment/BaseFragmentPagerAdapter;

    move-result-object p1

    if-nez p1, :cond_3

    goto :goto_2

    :cond_3
    invoke-virtual {p2}, Landroid/view/MotionEvent;->getX()F

    move-result p1

    iget p2, p0, Lcom/android/camera/fragment/vv/FragmentVVPreview$2;->val$sideWidth:F

    cmpg-float p2, p1, p2

    if-gez p2, :cond_5

    iget-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVVPreview$2;->this$0:Lcom/android/camera/fragment/vv/FragmentVVPreview;

    invoke-static {p1}, Lcom/android/camera/fragment/vv/FragmentVVPreview;->access$200(Lcom/android/camera/fragment/vv/FragmentVVPreview;)Landroid/support/v4/view/ViewPager;

    move-result-object p1

    invoke-virtual {p1}, Landroid/support/v4/view/ViewPager;->getCurrentItem()I

    move-result p1

    if-lez p1, :cond_4

    add-int/2addr p1, v0

    iput p1, p0, Lcom/android/camera/fragment/vv/FragmentVVPreview$2;->nextItem:I

    :cond_4
    goto :goto_1

    :cond_5
    iget p2, p0, Lcom/android/camera/fragment/vv/FragmentVVPreview$2;->val$layoutWidth:I

    int-to-float p2, p2

    iget v0, p0, Lcom/android/camera/fragment/vv/FragmentVVPreview$2;->val$sideWidth:F

    sub-float/2addr p2, v0

    cmpg-float p1, p1, p2

    if-gez p1, :cond_6

    return v2

    :cond_6
    iget-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVVPreview$2;->this$0:Lcom/android/camera/fragment/vv/FragmentVVPreview;

    invoke-static {p1}, Lcom/android/camera/fragment/vv/FragmentVVPreview;->access$200(Lcom/android/camera/fragment/vv/FragmentVVPreview;)Landroid/support/v4/view/ViewPager;

    move-result-object p1

    invoke-virtual {p1}, Landroid/support/v4/view/ViewPager;->getCurrentItem()I

    move-result p1

    iget-object p2, p0, Lcom/android/camera/fragment/vv/FragmentVVPreview$2;->this$0:Lcom/android/camera/fragment/vv/FragmentVVPreview;

    invoke-static {p2}, Lcom/android/camera/fragment/vv/FragmentVVPreview;->access$300(Lcom/android/camera/fragment/vv/FragmentVVPreview;)Lcom/android/camera/fragment/BaseFragmentPagerAdapter;

    move-result-object p2

    invoke-virtual {p2}, Lcom/android/camera/fragment/BaseFragmentPagerAdapter;->getCount()I

    move-result p2

    sub-int/2addr p2, v1

    if-ge p1, p2, :cond_7

    add-int/2addr p1, v1

    iput p1, p0, Lcom/android/camera/fragment/vv/FragmentVVPreview$2;->nextItem:I

    :cond_7
    :goto_1
    return v1

    :cond_8
    :goto_2
    return v2

    nop

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method
