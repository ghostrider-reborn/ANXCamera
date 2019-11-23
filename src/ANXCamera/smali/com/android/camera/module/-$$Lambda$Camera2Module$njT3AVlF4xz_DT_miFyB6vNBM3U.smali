.class public final synthetic Lcom/android/camera/module/-$$Lambda$Camera2Module$njT3AVlF4xz_DT_miFyB6vNBM3U;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic f$0:Lcom/android/camera/module/Camera2Module;

.field private final synthetic f$1:Z


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/module/Camera2Module;Z)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/-$$Lambda$Camera2Module$njT3AVlF4xz_DT_miFyB6vNBM3U;->f$0:Lcom/android/camera/module/Camera2Module;

    iput-boolean p2, p0, Lcom/android/camera/module/-$$Lambda$Camera2Module$njT3AVlF4xz_DT_miFyB6vNBM3U;->f$1:Z

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/-$$Lambda$Camera2Module$njT3AVlF4xz_DT_miFyB6vNBM3U;->f$0:Lcom/android/camera/module/Camera2Module;

    iget-boolean v1, p0, Lcom/android/camera/module/-$$Lambda$Camera2Module$njT3AVlF4xz_DT_miFyB6vNBM3U;->f$1:Z

    invoke-static {v0, v1}, Lcom/android/camera/module/Camera2Module;->lambda$handleSuperNightResultIfNeed$6(Lcom/android/camera/module/Camera2Module;Z)V

    return-void
.end method
