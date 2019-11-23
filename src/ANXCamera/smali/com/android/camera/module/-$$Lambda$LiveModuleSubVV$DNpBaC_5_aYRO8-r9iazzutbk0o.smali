.class public final synthetic Lcom/android/camera/module/-$$Lambda$LiveModuleSubVV$DNpBaC_5_aYRO8-r9iazzutbk0o;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic f$0:Lcom/android/camera/module/LiveModuleSubVV;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/module/LiveModuleSubVV;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/-$$Lambda$LiveModuleSubVV$DNpBaC_5_aYRO8-r9iazzutbk0o;->f$0:Lcom/android/camera/module/LiveModuleSubVV;

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/-$$Lambda$LiveModuleSubVV$DNpBaC_5_aYRO8-r9iazzutbk0o;->f$0:Lcom/android/camera/module/LiveModuleSubVV;

    invoke-static {v0}, Lcom/android/camera/module/LiveModuleSubVV;->lambda$setOrientationParameter$2(Lcom/android/camera/module/LiveModuleSubVV;)V

    return-void
.end method
