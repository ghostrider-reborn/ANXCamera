.class public final synthetic Lcom/android/camera/module/impl/component/-$$Lambda$ConfigChangeImpl$zIw0eUH-9tnlZJI5itFPUZS9ILE;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Consumer;


# instance fields
.field private final synthetic f$0:[I


# direct methods
.method public synthetic constructor <init>([I)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/impl/component/-$$Lambda$ConfigChangeImpl$zIw0eUH-9tnlZJI5itFPUZS9ILE;->f$0:[I

    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/impl/component/-$$Lambda$ConfigChangeImpl$zIw0eUH-9tnlZJI5itFPUZS9ILE;->f$0:[I

    check-cast p1, Lcom/android/camera/module/BaseModule;

    invoke-static {v0, p1}, Lcom/android/camera/module/impl/component/ConfigChangeImpl;->lambda$closeMutexElement$14([ILcom/android/camera/module/BaseModule;)V

    return-void
.end method
