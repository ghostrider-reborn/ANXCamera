.class Lcom/android/camera/fragment/vv/FragmentVVGallery$2;
.super Ljava/lang/Object;
.source "FragmentVVGallery.java"

# interfaces
.implements Lio/reactivex/CompletableOnSubscribe;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/fragment/vv/FragmentVVGallery;->loadItemList()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/fragment/vv/FragmentVVGallery;

.field final synthetic val$localVersion:Ljava/lang/String;


# direct methods
.method constructor <init>(Lcom/android/camera/fragment/vv/FragmentVVGallery;Ljava/lang/String;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery$2;->this$0:Lcom/android/camera/fragment/vv/FragmentVVGallery;

    iput-object p2, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery$2;->val$localVersion:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public subscribe(Lio/reactivex/CompletableEmitter;)V
    .locals 5
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    invoke-static {}, Lcom/android/camera/fragment/vv/ResourceManager;->getInstance()Lcom/android/camera/fragment/vv/ResourceManager;

    move-result-object v0

    const-string/jumbo v1, "vv/info.json"

    iget-object v2, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery$2;->this$0:Lcom/android/camera/fragment/vv/FragmentVVGallery;

    invoke-virtual {v2}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-virtual {v0, v1, v2}, Lcom/android/camera/fragment/vv/ResourceManager;->getAssetCache(Ljava/lang/String;Landroid/content/Context;)Ljava/lang/String;

    move-result-object v0

    :try_start_0
    new-instance v1, Lorg/json/JSONObject;

    invoke-direct {v1, v0}, Lorg/json/JSONObject;-><init>(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery$2;->this$0:Lcom/android/camera/fragment/vv/FragmentVVGallery;

    invoke-static {v0}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->access$100(Lcom/android/camera/fragment/vv/FragmentVVGallery;)Lcom/android/camera/fragment/vv/VVList;

    move-result-object v0

    invoke-virtual {v0, v1}, Lcom/android/camera/fragment/vv/VVList;->createResourcesList(Lorg/json/JSONObject;)V
    :try_end_0
    .catch Lorg/json/JSONException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    invoke-virtual {v0}, Lorg/json/JSONException;->printStackTrace()V

    :goto_0
    const/4 v0, 0x1

    iget-object v1, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery$2;->this$0:Lcom/android/camera/fragment/vv/FragmentVVGallery;

    invoke-static {v1}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->access$100(Lcom/android/camera/fragment/vv/FragmentVVGallery;)Lcom/android/camera/fragment/vv/VVList;

    move-result-object v1

    iget-object v1, v1, Lcom/android/camera/fragment/vv/VVList;->version:Ljava/lang/String;

    iget-object v2, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery$2;->val$localVersion:Ljava/lang/String;

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_0

    const/4 v0, 0x0

    :cond_0
    invoke-static {}, Lcom/android/camera/fragment/vv/ResourceManager;->getInstance()Lcom/android/camera/fragment/vv/ResourceManager;

    move-result-object v1

    iget-object v2, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery$2;->this$0:Lcom/android/camera/fragment/vv/FragmentVVGallery;

    invoke-virtual {v2}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->getContext()Landroid/content/Context;

    move-result-object v2

    iget-object v3, p0, Lcom/android/camera/fragment/vv/FragmentVVGallery$2;->this$0:Lcom/android/camera/fragment/vv/FragmentVVGallery;

    invoke-static {v3}, Lcom/android/camera/fragment/vv/FragmentVVGallery;->access$100(Lcom/android/camera/fragment/vv/FragmentVVGallery;)Lcom/android/camera/fragment/vv/VVList;

    move-result-object v3

    sget-object v4, Lcom/android/camera/module/impl/component/LiveSubVVImpl;->TEMPLATE_PATH:Ljava/lang/String;

    invoke-virtual {v1, v2, v3, v4, v0}, Lcom/android/camera/fragment/vv/ResourceManager;->decompressResource(Landroid/content/Context;Lcom/android/camera/fragment/vv/BaseResourceList;Ljava/lang/String;Z)V

    invoke-interface {p1}, Lio/reactivex/CompletableEmitter;->onComplete()V

    return-void
.end method
