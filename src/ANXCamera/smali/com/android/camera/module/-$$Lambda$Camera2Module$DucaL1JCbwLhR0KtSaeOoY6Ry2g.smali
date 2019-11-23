.class public final synthetic Lcom/android/camera/module/-$$Lambda$Camera2Module$DucaL1JCbwLhR0KtSaeOoY6Ry2g;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic f$0:Z


# direct methods
.method public synthetic constructor <init>(Z)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-boolean p1, p0, Lcom/android/camera/module/-$$Lambda$Camera2Module$DucaL1JCbwLhR0KtSaeOoY6Ry2g;->f$0:Z

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/module/-$$Lambda$Camera2Module$DucaL1JCbwLhR0KtSaeOoY6Ry2g;->f$0:Z

    invoke-static {v0}, Lcom/android/camera/module/Camera2Module;->lambda$onHdrSceneChanged$3(Z)V

    return-void
.end method
