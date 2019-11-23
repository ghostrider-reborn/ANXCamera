.class public Lcom/android/camera/ToastUtils;
.super Ljava/lang/Object;
.source "ToastUtils.java"


# static fields
.field private static final SHORT_DURATION_TIMEOUT:J = 0x7d0L

.field public static final TAG:Ljava/lang/String; = "ToastUtils"

.field private static sGravity:I

.field private static sOldMsg:Ljava/lang/String;

.field private static sOneTime:J

.field protected static sToast:Landroid/widget/Toast;

.field private static sTwoTime:J

.field private static sXOffset:I

.field private static sYOffset:I


# direct methods
.method static constructor <clinit>()V
    .locals 2

    const/4 v0, 0x0

    sput-object v0, Lcom/android/camera/ToastUtils;->sToast:Landroid/widget/Toast;

    const-wide/16 v0, 0x0

    sput-wide v0, Lcom/android/camera/ToastUtils;->sOneTime:J

    sput-wide v0, Lcom/android/camera/ToastUtils;->sTwoTime:J

    const/16 v0, 0x11

    sput v0, Lcom/android/camera/ToastUtils;->sGravity:I

    const/4 v0, 0x0

    sput v0, Lcom/android/camera/ToastUtils;->sXOffset:I

    sput v0, Lcom/android/camera/ToastUtils;->sYOffset:I

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method private static getWindowParams(Landroid/widget/Toast;)Landroid/view/WindowManager$LayoutParams;
    .locals 5

    const/4 v0, 0x0

    if-nez p0, :cond_0

    return-object v0

    :cond_0
    nop

    :try_start_0
    const-class v1, Landroid/widget/Toast;

    const-string v2, "getWindowParams"

    const/4 v3, 0x0

    new-array v4, v3, [Ljava/lang/Class;

    invoke-virtual {v1, v2, v4}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v1

    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Ljava/lang/reflect/Method;->setAccessible(Z)V

    new-array v2, v3, [Ljava/lang/Object;

    invoke-virtual {v1, p0, v2}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Landroid/view/WindowManager$LayoutParams;
    :try_end_0
    .catch Ljava/lang/NoSuchMethodException; {:try_start_0 .. :try_end_0} :catch_2
    .catch Ljava/lang/IllegalAccessException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/lang/reflect/InvocationTargetException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p0

    :catch_0
    move-exception p0

    const-string v1, "ToastUtils"

    const-string v2, "getWindowParams: invoke failed: "

    invoke-static {v1, v2, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_1

    :catch_1
    move-exception p0

    const-string v1, "ToastUtils"

    const-string v2, "getWindowParams: cannot access"

    invoke-static {v1, v2, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_0

    :catch_2
    move-exception p0

    const-string v1, "ToastUtils"

    const-string v2, "getWindowParams: no such method"

    invoke-static {v1, v2, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :goto_0
    nop

    :goto_1
    const-string p0, "ToastUtils"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getWindowsParam: ret: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {p0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-object v0
.end method

.method private static prepareShowOnKeyguard(Landroid/widget/Toast;)V
    .locals 2

    invoke-static {p0}, Lcom/android/camera/ToastUtils;->getWindowParams(Landroid/widget/Toast;)Landroid/view/WindowManager$LayoutParams;

    move-result-object p0

    if-eqz p0, :cond_0

    iget v0, p0, Landroid/view/WindowManager$LayoutParams;->flags:I

    const/high16 v1, 0x80000

    or-int/2addr v0, v1

    iput v0, p0, Landroid/view/WindowManager$LayoutParams;->flags:I

    :cond_0
    return-void
.end method

.method public static showToast(Landroid/content/Context;I)V
    .locals 7

    nop

    if-eqz p0, :cond_0

    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    if-eqz v0, :cond_0

    new-instance v1, Ljava/lang/ref/WeakReference;

    invoke-direct {v1, p0}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v2

    const/16 v3, 0x11

    const/4 v4, 0x0

    const/4 v5, 0x0

    const/4 v6, 0x0

    invoke-static/range {v1 .. v6}, Lcom/android/camera/ToastUtils;->showToast(Ljava/lang/ref/WeakReference;Ljava/lang/String;IIIZ)V

    :cond_0
    return-void
.end method

.method public static showToast(Landroid/content/Context;Ljava/lang/String;)V
    .locals 6

    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-direct {v0, p0}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    const/16 v2, 0x11

    const/4 v3, 0x0

    const/4 v4, 0x0

    const/4 v5, 0x0

    move-object v1, p1

    invoke-static/range {v0 .. v5}, Lcom/android/camera/ToastUtils;->showToast(Ljava/lang/ref/WeakReference;Ljava/lang/String;IIIZ)V

    return-void
.end method

.method public static showToast(Landroid/content/Context;Ljava/lang/String;I)V
    .locals 7

    nop

    if-eqz p0, :cond_0

    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    if-eqz v0, :cond_0

    new-instance v1, Ljava/lang/ref/WeakReference;

    invoke-direct {v1, p0}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    const/4 v4, 0x0

    const/4 v5, 0x0

    const/4 v6, 0x0

    move-object v2, p1

    move v3, p2

    invoke-static/range {v1 .. v6}, Lcom/android/camera/ToastUtils;->showToast(Ljava/lang/ref/WeakReference;Ljava/lang/String;IIIZ)V

    :cond_0
    return-void
.end method

.method public static showToast(Landroid/content/Context;Ljava/lang/String;III)V
    .locals 7

    nop

    if-eqz p0, :cond_0

    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    if-eqz v0, :cond_0

    new-instance v1, Ljava/lang/ref/WeakReference;

    invoke-direct {v1, p0}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    const/4 v6, 0x0

    move-object v2, p1

    move v3, p2

    move v4, p3

    move v5, p4

    invoke-static/range {v1 .. v6}, Lcom/android/camera/ToastUtils;->showToast(Ljava/lang/ref/WeakReference;Ljava/lang/String;IIIZ)V

    :cond_0
    return-void
.end method

.method public static showToast(Landroid/content/Context;Ljava/lang/String;Z)V
    .locals 6

    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-direct {v0, p0}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    const/16 v2, 0x11

    const/4 v3, 0x0

    const/4 v4, 0x0

    move-object v1, p1

    move v5, p2

    invoke-static/range {v0 .. v5}, Lcom/android/camera/ToastUtils;->showToast(Ljava/lang/ref/WeakReference;Ljava/lang/String;IIIZ)V

    return-void
.end method

.method private static showToast(Ljava/lang/ref/WeakReference;Ljava/lang/String;IIIZ)V
    .locals 4
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/ref/WeakReference<",
            "Landroid/content/Context;",
            ">;",
            "Ljava/lang/String;",
            "IIIZ)V"
        }
    .end annotation

    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    :cond_0
    sget-object v0, Lcom/android/camera/ToastUtils;->sToast:Landroid/widget/Toast;

    const/4 v1, 0x0

    if-nez v0, :cond_3

    invoke-virtual {p0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Landroid/content/Context;

    if-eqz p0, :cond_2

    const/4 v0, 0x0

    :try_start_0
    invoke-virtual {p0}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0, p1, v1}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object p0

    sput-object p0, Lcom/android/camera/ToastUtils;->sToast:Landroid/widget/Toast;

    sget-object p0, Lcom/android/camera/ToastUtils;->sToast:Landroid/widget/Toast;

    invoke-virtual {p0, p2, p3, p4}, Landroid/widget/Toast;->setGravity(III)V

    if-eqz p5, :cond_1

    sget-object p0, Lcom/android/camera/ToastUtils;->sToast:Landroid/widget/Toast;

    invoke-static {p0}, Lcom/android/camera/ToastUtils;->prepareShowOnKeyguard(Landroid/widget/Toast;)V

    :cond_1
    sget-object p0, Lcom/android/camera/ToastUtils;->sToast:Landroid/widget/Toast;

    invoke-virtual {p0}, Landroid/widget/Toast;->show()V

    sput-object p1, Lcom/android/camera/ToastUtils;->sOldMsg:Ljava/lang/String;

    sput p2, Lcom/android/camera/ToastUtils;->sGravity:I

    sput p3, Lcom/android/camera/ToastUtils;->sXOffset:I

    sput p4, Lcom/android/camera/ToastUtils;->sYOffset:I

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide p0

    sput-wide p0, Lcom/android/camera/ToastUtils;->sOneTime:J
    :try_end_0
    .catch Landroid/view/InflateException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p0

    sput-object v0, Lcom/android/camera/ToastUtils;->sToast:Landroid/widget/Toast;

    invoke-virtual {p0}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_1

    :catch_1
    move-exception p0

    sput-object v0, Lcom/android/camera/ToastUtils;->sToast:Landroid/widget/Toast;

    invoke-virtual {p0}, Landroid/view/InflateException;->printStackTrace()V

    :goto_0
    nop

    :cond_2
    :goto_1
    goto :goto_4

    :cond_3
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v2

    sput-wide v2, Lcom/android/camera/ToastUtils;->sTwoTime:J

    nop

    sget-object p0, Lcom/android/camera/ToastUtils;->sOldMsg:Ljava/lang/String;

    const/4 p5, 0x1

    if-eqz p0, :cond_4

    sget-object p0, Lcom/android/camera/ToastUtils;->sOldMsg:Ljava/lang/String;

    invoke-virtual {p0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    if-nez p0, :cond_4

    sput-object p1, Lcom/android/camera/ToastUtils;->sOldMsg:Ljava/lang/String;

    sget-object p0, Lcom/android/camera/ToastUtils;->sToast:Landroid/widget/Toast;

    invoke-virtual {p0, p1}, Landroid/widget/Toast;->setText(Ljava/lang/CharSequence;)V

    nop

    move v1, p5

    :cond_4
    sget p0, Lcom/android/camera/ToastUtils;->sGravity:I

    if-ne p2, p0, :cond_6

    sget p0, Lcom/android/camera/ToastUtils;->sXOffset:I

    if-ne p0, p3, :cond_6

    sget p0, Lcom/android/camera/ToastUtils;->sYOffset:I

    if-eq p0, p4, :cond_5

    goto :goto_2

    :cond_5
    move p5, v1

    goto :goto_3

    :cond_6
    :goto_2
    sget-object p0, Lcom/android/camera/ToastUtils;->sToast:Landroid/widget/Toast;

    invoke-virtual {p0, p2, p3, p4}, Landroid/widget/Toast;->setGravity(III)V

    sput p2, Lcom/android/camera/ToastUtils;->sGravity:I

    sput p3, Lcom/android/camera/ToastUtils;->sXOffset:I

    sput p4, Lcom/android/camera/ToastUtils;->sYOffset:I

    nop

    :goto_3
    if-nez p5, :cond_7

    sget-wide p0, Lcom/android/camera/ToastUtils;->sTwoTime:J

    sget-wide p2, Lcom/android/camera/ToastUtils;->sOneTime:J

    sub-long/2addr p0, p2

    const-wide/16 p2, 0x7d0

    cmp-long p0, p0, p2

    if-lez p0, :cond_8

    :cond_7
    sget-wide p0, Lcom/android/camera/ToastUtils;->sTwoTime:J

    sput-wide p0, Lcom/android/camera/ToastUtils;->sOneTime:J

    sget-object p0, Lcom/android/camera/ToastUtils;->sToast:Landroid/widget/Toast;

    invoke-virtual {p0}, Landroid/widget/Toast;->show()V

    :cond_8
    :goto_4
    return-void
.end method
