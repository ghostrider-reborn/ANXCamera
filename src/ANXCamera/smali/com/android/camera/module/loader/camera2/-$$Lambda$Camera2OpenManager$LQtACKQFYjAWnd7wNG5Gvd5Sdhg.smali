.class public final synthetic Lcom/android/camera/module/loader/camera2/-$$Lambda$Camera2OpenManager$LQtACKQFYjAWnd7wNG5Gvd5Sdhg;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Lio/reactivex/functions/Function;


# instance fields
.field private final synthetic f$0:Lcom/android/camera/module/loader/camera2/Camera2OpenManager;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/module/loader/camera2/Camera2OpenManager;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/loader/camera2/-$$Lambda$Camera2OpenManager$LQtACKQFYjAWnd7wNG5Gvd5Sdhg;->f$0:Lcom/android/camera/module/loader/camera2/Camera2OpenManager;

    return-void
.end method


# virtual methods
.method public final apply(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/loader/camera2/-$$Lambda$Camera2OpenManager$LQtACKQFYjAWnd7wNG5Gvd5Sdhg;->f$0:Lcom/android/camera/module/loader/camera2/Camera2OpenManager;

    check-cast p1, Ljava/lang/Throwable;

    invoke-static {v0, p1}, Lcom/android/camera/module/loader/camera2/Camera2OpenManager;->lambda$attachInObservable$1(Lcom/android/camera/module/loader/camera2/Camera2OpenManager;Ljava/lang/Throwable;)Lio/reactivex/ObservableSource;

    move-result-object p1

    return-object p1
.end method
