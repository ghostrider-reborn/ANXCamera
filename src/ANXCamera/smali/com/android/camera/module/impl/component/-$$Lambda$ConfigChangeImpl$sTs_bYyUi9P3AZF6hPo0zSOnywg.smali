.class public final synthetic Lcom/android/camera/module/impl/component/-$$Lambda$ConfigChangeImpl$sTs_bYyUi9P3AZF6hPo0zSOnywg;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Consumer;


# instance fields
.field private final synthetic f$0:Lcom/android/camera/module/impl/component/ConfigChangeImpl;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/module/impl/component/ConfigChangeImpl;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/impl/component/-$$Lambda$ConfigChangeImpl$sTs_bYyUi9P3AZF6hPo0zSOnywg;->f$0:Lcom/android/camera/module/impl/component/ConfigChangeImpl;

    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/impl/component/-$$Lambda$ConfigChangeImpl$sTs_bYyUi9P3AZF6hPo0zSOnywg;->f$0:Lcom/android/camera/module/impl/component/ConfigChangeImpl;

    check-cast p1, Lcom/android/camera/module/BaseModule;

    invoke-static {v0, p1}, Lcom/android/camera/module/impl/component/ConfigChangeImpl;->lambda$configBackSoftLightSwitch$11(Lcom/android/camera/module/impl/component/ConfigChangeImpl;Lcom/android/camera/module/BaseModule;)V

    return-void
.end method
