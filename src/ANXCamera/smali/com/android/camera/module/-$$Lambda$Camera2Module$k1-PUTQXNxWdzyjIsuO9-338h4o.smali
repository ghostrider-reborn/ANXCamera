.class public final synthetic Lcom/android/camera/module/-$$Lambda$Camera2Module$k1-PUTQXNxWdzyjIsuO9-338h4o;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic f$0:Lcom/android/camera/module/Camera2Module;

.field private final synthetic f$1:[Lcom/android/camera2/CameraHardwareFace;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/module/Camera2Module;[Lcom/android/camera2/CameraHardwareFace;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/-$$Lambda$Camera2Module$k1-PUTQXNxWdzyjIsuO9-338h4o;->f$0:Lcom/android/camera/module/Camera2Module;

    iput-object p2, p0, Lcom/android/camera/module/-$$Lambda$Camera2Module$k1-PUTQXNxWdzyjIsuO9-338h4o;->f$1:[Lcom/android/camera2/CameraHardwareFace;

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/-$$Lambda$Camera2Module$k1-PUTQXNxWdzyjIsuO9-338h4o;->f$0:Lcom/android/camera/module/Camera2Module;

    iget-object v1, p0, Lcom/android/camera/module/-$$Lambda$Camera2Module$k1-PUTQXNxWdzyjIsuO9-338h4o;->f$1:[Lcom/android/camera2/CameraHardwareFace;

    invoke-static {v0, v1}, Lcom/android/camera/module/Camera2Module;->lambda$onFaceDetected$4(Lcom/android/camera/module/Camera2Module;[Lcom/android/camera2/CameraHardwareFace;)V

    return-void
.end method
