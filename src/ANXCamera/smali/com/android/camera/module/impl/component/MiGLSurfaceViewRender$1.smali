.class Lcom/android/camera/module/impl/component/MiGLSurfaceViewRender$1;
.super Ljava/lang/Object;
.source "MiGLSurfaceViewRender.java"

# interfaces
.implements Landroid/graphics/SurfaceTexture$OnFrameAvailableListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/module/impl/component/MiGLSurfaceViewRender;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/module/impl/component/MiGLSurfaceViewRender;


# direct methods
.method constructor <init>(Lcom/android/camera/module/impl/component/MiGLSurfaceViewRender;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/impl/component/MiGLSurfaceViewRender$1;->this$0:Lcom/android/camera/module/impl/component/MiGLSurfaceViewRender;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onFrameAvailable(Landroid/graphics/SurfaceTexture;)V
    .locals 3

    const-string v0, "MiGLSurfaceViewRender"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "camera surceface texture available: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p1, p0, Lcom/android/camera/module/impl/component/MiGLSurfaceViewRender$1;->this$0:Lcom/android/camera/module/impl/component/MiGLSurfaceViewRender;

    invoke-static {p1}, Lcom/android/camera/module/impl/component/MiGLSurfaceViewRender;->access$000(Lcom/android/camera/module/impl/component/MiGLSurfaceViewRender;)Landroid/opengl/GLSurfaceView;

    move-result-object p1

    invoke-virtual {p1}, Landroid/opengl/GLSurfaceView;->requestRender()V

    return-void
.end method
