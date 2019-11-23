.class public Lorg/webrtc/videoengine/ViEAndroidGLES20;
.super Landroid/opengl/GLSurfaceView;
.source "ViEAndroidGLES20.java"

# interfaces
.implements Landroid/opengl/GLSurfaceView$Renderer;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lorg/webrtc/videoengine/ViEAndroidGLES20$ConfigChooser;,
        Lorg/webrtc/videoengine/ViEAndroidGLES20$ContextFactory;
    }
.end annotation


# static fields
.field private static final DEBUG:Z = true

.field private static TAG:Ljava/lang/String;


# instance fields
.field private nativeFunctionLock:Ljava/util/concurrent/locks/ReentrantLock;

.field private nativeFunctionsRegisted:Z

.field private nativeObject:J

.field private openGLCreated:Z

.field private surfaceCreated:Z

.field private viewHeight:I

.field private viewWidth:I


# direct methods
.method static constructor <clinit>()V
    .locals 1

    const-string v0, "WEBRTC-JR-ViEAndroidGLES20"

    sput-object v0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->TAG:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;)V
    .locals 2

    invoke-direct {p0, p1}, Landroid/opengl/GLSurfaceView;-><init>(Landroid/content/Context;)V

    const/4 p1, 0x0

    iput-boolean p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->surfaceCreated:Z

    iput-boolean p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->openGLCreated:Z

    iput-boolean p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeFunctionsRegisted:Z

    new-instance v0, Ljava/util/concurrent/locks/ReentrantLock;

    invoke-direct {v0}, Ljava/util/concurrent/locks/ReentrantLock;-><init>()V

    iput-object v0, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeFunctionLock:Ljava/util/concurrent/locks/ReentrantLock;

    const-wide/16 v0, 0x0

    iput-wide v0, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeObject:J

    iput p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->viewWidth:I

    iput p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->viewHeight:I

    invoke-direct {p0, p1, p1, p1}, Lorg/webrtc/videoengine/ViEAndroidGLES20;->init(ZII)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;ZII)V
    .locals 2

    invoke-direct {p0, p1}, Landroid/opengl/GLSurfaceView;-><init>(Landroid/content/Context;)V

    const/4 p1, 0x0

    iput-boolean p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->surfaceCreated:Z

    iput-boolean p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->openGLCreated:Z

    iput-boolean p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeFunctionsRegisted:Z

    new-instance v0, Ljava/util/concurrent/locks/ReentrantLock;

    invoke-direct {v0}, Ljava/util/concurrent/locks/ReentrantLock;-><init>()V

    iput-object v0, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeFunctionLock:Ljava/util/concurrent/locks/ReentrantLock;

    const-wide/16 v0, 0x0

    iput-wide v0, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeObject:J

    iput p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->viewWidth:I

    iput p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->viewHeight:I

    invoke-direct {p0, p2, p3, p4}, Lorg/webrtc/videoengine/ViEAndroidGLES20;->init(ZII)V

    return-void
.end method

.method private native CreateOpenGLNative(JII)I
.end method

.method private native DrawNative(J)V
.end method

.method public static IsSupported(Landroid/content/Context;)Z
    .locals 1

    const-string v0, "activity"

    invoke-virtual {p0, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Landroid/app/ActivityManager;

    invoke-virtual {p0}, Landroid/app/ActivityManager;->getDeviceConfigurationInfo()Landroid/content/pm/ConfigurationInfo;

    move-result-object p0

    iget p0, p0, Landroid/content/pm/ConfigurationInfo;->reqGlEsVersion:I

    const/high16 v0, 0x20000

    if-lt p0, v0, :cond_0

    const/4 p0, 0x1

    return p0

    :cond_0
    const/4 p0, 0x0

    return p0
.end method

.method public static UseOpenGL2(Ljava/lang/Object;)Z
    .locals 1

    const-class v0, Lorg/webrtc/videoengine/ViEAndroidGLES20;

    invoke-virtual {v0, p0}, Ljava/lang/Class;->isInstance(Ljava/lang/Object;)Z

    move-result p0

    return p0
.end method

.method static synthetic access$100()Ljava/lang/String;
    .locals 1

    sget-object v0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->TAG:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$200(Ljava/lang/String;Ljavax/microedition/khronos/egl/EGL10;)V
    .locals 0

    invoke-static {p0, p1}, Lorg/webrtc/videoengine/ViEAndroidGLES20;->checkEglError(Ljava/lang/String;Ljavax/microedition/khronos/egl/EGL10;)V

    return-void
.end method

.method private static checkEglError(Ljava/lang/String;Ljavax/microedition/khronos/egl/EGL10;)V
    .locals 5

    :goto_0
    invoke-interface {p1}, Ljavax/microedition/khronos/egl/EGL10;->eglGetError()I

    move-result v0

    const/16 v1, 0x3000

    if-eq v0, v1, :cond_0

    sget-object v1, Lorg/webrtc/videoengine/ViEAndroidGLES20;->TAG:Ljava/lang/String;

    const-string v2, "%s: EGL error: 0x%x"

    const/4 v3, 0x2

    new-array v3, v3, [Ljava/lang/Object;

    const/4 v4, 0x0

    aput-object p0, v3, v4

    const/4 v4, 0x1

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    aput-object v0, v3, v4

    invoke-static {v2, v3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    :cond_0
    return-void
.end method

.method private init(ZII)V
    .locals 16

    move-object/from16 v0, p0

    if-eqz p1, :cond_0

    invoke-virtual/range {p0 .. p0}, Lorg/webrtc/videoengine/ViEAndroidGLES20;->getHolder()Landroid/view/SurfaceHolder;

    move-result-object v2

    const/4 v3, -0x3

    invoke-interface {v2, v3}, Landroid/view/SurfaceHolder;->setFormat(I)V

    :cond_0
    new-instance v2, Lorg/webrtc/videoengine/ViEAndroidGLES20$ContextFactory;

    const/4 v3, 0x0

    invoke-direct {v2, v3}, Lorg/webrtc/videoengine/ViEAndroidGLES20$ContextFactory;-><init>(Lorg/webrtc/videoengine/ViEAndroidGLES20$1;)V

    invoke-virtual {v0, v2}, Lorg/webrtc/videoengine/ViEAndroidGLES20;->setEGLContextFactory(Landroid/opengl/GLSurfaceView$EGLContextFactory;)V

    if-eqz p1, :cond_1

    new-instance v1, Lorg/webrtc/videoengine/ViEAndroidGLES20$ConfigChooser;

    const/16 v5, 0x8

    const/16 v6, 0x8

    const/16 v7, 0x8

    const/16 v8, 0x8

    move-object v4, v1

    move/from16 v9, p2

    move/from16 v10, p3

    invoke-direct/range {v4 .. v10}, Lorg/webrtc/videoengine/ViEAndroidGLES20$ConfigChooser;-><init>(IIIIII)V

    goto :goto_0

    :cond_1
    new-instance v1, Lorg/webrtc/videoengine/ViEAndroidGLES20$ConfigChooser;

    const/4 v10, 0x5

    const/4 v11, 0x6

    const/4 v12, 0x5

    const/4 v13, 0x0

    move-object v9, v1

    move/from16 v14, p2

    move/from16 v15, p3

    invoke-direct/range {v9 .. v15}, Lorg/webrtc/videoengine/ViEAndroidGLES20$ConfigChooser;-><init>(IIIIII)V

    :goto_0
    invoke-virtual {v0, v1}, Lorg/webrtc/videoengine/ViEAndroidGLES20;->setEGLConfigChooser(Landroid/opengl/GLSurfaceView$EGLConfigChooser;)V

    invoke-virtual {v0, v0}, Lorg/webrtc/videoengine/ViEAndroidGLES20;->setRenderer(Landroid/opengl/GLSurfaceView$Renderer;)V

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lorg/webrtc/videoengine/ViEAndroidGLES20;->setRenderMode(I)V

    return-void
.end method


# virtual methods
.method public DeRegisterNativeObject()V
    .locals 2

    iget-object v0, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeFunctionLock:Ljava/util/concurrent/locks/ReentrantLock;

    invoke-virtual {v0}, Ljava/util/concurrent/locks/ReentrantLock;->lock()V

    const/4 v0, 0x0

    iput-boolean v0, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeFunctionsRegisted:Z

    iput-boolean v0, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->openGLCreated:Z

    const-wide/16 v0, 0x0

    iput-wide v0, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeObject:J

    iget-object v0, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeFunctionLock:Ljava/util/concurrent/locks/ReentrantLock;

    invoke-virtual {v0}, Ljava/util/concurrent/locks/ReentrantLock;->unlock()V

    return-void
.end method

.method public ReDraw()V
    .locals 1

    iget-boolean v0, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->surfaceCreated:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lorg/webrtc/videoengine/ViEAndroidGLES20;->requestRender()V

    :cond_0
    return-void
.end method

.method public RegisterNativeObject(J)V
    .locals 1

    iget-object v0, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeFunctionLock:Ljava/util/concurrent/locks/ReentrantLock;

    invoke-virtual {v0}, Ljava/util/concurrent/locks/ReentrantLock;->lock()V

    iput-wide p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeObject:J

    const/4 p1, 0x1

    iput-boolean p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeFunctionsRegisted:Z

    iget-object p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeFunctionLock:Ljava/util/concurrent/locks/ReentrantLock;

    invoke-virtual {p1}, Ljava/util/concurrent/locks/ReentrantLock;->unlock()V

    return-void
.end method

.method public onDrawFrame(Ljavax/microedition/khronos/opengles/GL10;)V
    .locals 3

    iget-object p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeFunctionLock:Ljava/util/concurrent/locks/ReentrantLock;

    invoke-virtual {p1}, Ljava/util/concurrent/locks/ReentrantLock;->lock()V

    iget-boolean p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeFunctionsRegisted:Z

    if-eqz p1, :cond_3

    iget-boolean p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->surfaceCreated:Z

    if-nez p1, :cond_0

    goto :goto_0

    :cond_0
    iget-boolean p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->openGLCreated:Z

    if-nez p1, :cond_2

    iget-wide v0, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeObject:J

    iget p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->viewWidth:I

    iget v2, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->viewHeight:I

    invoke-direct {p0, v0, v1, p1, v2}, Lorg/webrtc/videoengine/ViEAndroidGLES20;->CreateOpenGLNative(JII)I

    move-result p1

    if-eqz p1, :cond_1

    return-void

    :cond_1
    const/4 p1, 0x1

    iput-boolean p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->openGLCreated:Z

    :cond_2
    iget-wide v0, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeObject:J

    invoke-direct {p0, v0, v1}, Lorg/webrtc/videoengine/ViEAndroidGLES20;->DrawNative(J)V

    iget-object p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeFunctionLock:Ljava/util/concurrent/locks/ReentrantLock;

    invoke-virtual {p1}, Ljava/util/concurrent/locks/ReentrantLock;->unlock()V

    return-void

    :cond_3
    :goto_0
    iget-object p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeFunctionLock:Ljava/util/concurrent/locks/ReentrantLock;

    invoke-virtual {p1}, Ljava/util/concurrent/locks/ReentrantLock;->unlock()V

    return-void
.end method

.method public onSurfaceChanged(Ljavax/microedition/khronos/opengles/GL10;II)V
    .locals 2

    const/4 p1, 0x1

    iput-boolean p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->surfaceCreated:Z

    iput p2, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->viewWidth:I

    iput p3, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->viewHeight:I

    iget-object v0, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeFunctionLock:Ljava/util/concurrent/locks/ReentrantLock;

    invoke-virtual {v0}, Ljava/util/concurrent/locks/ReentrantLock;->lock()V

    iget-boolean v0, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeFunctionsRegisted:Z

    if-eqz v0, :cond_0

    iget-wide v0, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeObject:J

    invoke-direct {p0, v0, v1, p2, p3}, Lorg/webrtc/videoengine/ViEAndroidGLES20;->CreateOpenGLNative(JII)I

    move-result p2

    if-nez p2, :cond_0

    iput-boolean p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->openGLCreated:Z

    :cond_0
    iget-object p1, p0, Lorg/webrtc/videoengine/ViEAndroidGLES20;->nativeFunctionLock:Ljava/util/concurrent/locks/ReentrantLock;

    invoke-virtual {p1}, Ljava/util/concurrent/locks/ReentrantLock;->unlock()V

    return-void
.end method

.method public onSurfaceCreated(Ljavax/microedition/khronos/opengles/GL10;Ljavax/microedition/khronos/egl/EGLConfig;)V
    .locals 1

    const/4 p2, 0x0

    const/high16 v0, 0x3f000000    # 0.5f

    invoke-interface {p1, p2, p2, p2, v0}, Ljavax/microedition/khronos/opengles/GL10;->glClearColor(FFFF)V

    return-void
.end method
