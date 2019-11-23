.class public Lcom/ss/android/medialib/utils/DeviceInfoDetector;
.super Ljava/lang/Object;
.source "DeviceInfoDetector.java"


# static fields
.field private static abi:Ljava/lang/String;

.field private static appid:Ljava/lang/String;

.field private static cpu:Ljava/lang/String;

.field private static cpu_core:Ljava/lang/String;

.field private static cpu_freq:Ljava/lang/String;

.field private static deviceid:Ljava/lang/String;

.field private static external_storage:Ljava/lang/String;

.field private static gpu:Ljava/lang/String;

.field private static gpu_ver:Ljava/lang/String;

.field private static inited:Z

.field private static locale:Ljava/lang/String;

.field private static memory:Ljava/lang/String;

.field private static model:Ljava/lang/String;

.field private static nativeInited:Z

.field private static opengl_version:Ljava/lang/String;

.field private static os_sdk_int:Ljava/lang/String;

.field private static screen_height:Ljava/lang/String;

.field private static screen_width:Ljava/lang/String;

.field private static sim_operator:Ljava/lang/String;

.field private static storage:Ljava/lang/String;

.field private static ve_version:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    const/4 v0, 0x0

    sput-boolean v0, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->inited:Z

    sput-boolean v0, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->nativeInited:Z

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static get(Ljava/lang/String;)Ljava/lang/String;
    .locals 1

    invoke-static {}, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->toMap()Ljava/util/Map;

    move-result-object v0

    invoke-interface {v0, p0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/String;

    return-object p0
.end method

.method private static getCpuAbi()Ljava/lang/String;
    .locals 4

    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x15

    if-lt v0, v1, :cond_2

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v1, Landroid/os/Build;->SUPPORTED_ABIS:[Ljava/lang/String;

    if-eqz v1, :cond_1

    const/4 v2, 0x0

    :goto_0
    array-length v3, v1

    if-ge v2, v3, :cond_1

    aget-object v3, v1, v2

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    array-length v3, v1

    add-int/lit8 v3, v3, -0x1

    if-eq v2, v3, :cond_0

    const-string v3, ","

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    :cond_0
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :cond_1
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0

    :cond_2
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v1, Landroid/os/Build;->CPU_ABI:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, ","

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v1, Landroid/os/Build;->CPU_ABI2:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static init(Landroid/content/Context;)V
    .locals 2

    sget-boolean v0, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->inited:Z

    if-nez v0, :cond_1

    const-class v0, Lcom/ss/android/medialib/utils/DeviceInfoDetector;

    monitor-enter v0

    :try_start_0
    sget-boolean v1, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->inited:Z

    if-nez v1, :cond_0

    invoke-virtual {p0}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0}, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->initInternal(Landroid/content/Context;)V

    const/4 p0, 0x1

    sput-boolean p0, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->inited:Z

    :cond_0
    monitor-exit v0

    goto :goto_0

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0

    :cond_1
    :goto_0
    return-void
.end method

.method private static initInternal(Landroid/content/Context;)V
    .locals 4

    invoke-static {}, Ljava/util/Locale;->getDefault()Ljava/util/Locale;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/Locale;->getLanguage()Ljava/lang/String;

    move-result-object v0

    invoke-static {}, Ljava/util/Locale;->getDefault()Ljava/util/Locale;

    move-result-object v1

    invoke-virtual {v1}, Ljava/util/Locale;->getCountry()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2

    if-eqz v2, :cond_0

    const-string v1, ""

    goto :goto_0

    :cond_0
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "_"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/String;->toUpperCase()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    :goto_0
    sget-object v2, Landroid/os/Build;->MODEL:Ljava/lang/String;

    sput-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->model:Ljava/lang/String;

    invoke-static {}, Lcom/ss/android/medialib/utils/DeviceInfoUtils;->readCpuHardware()Ljava/lang/String;

    move-result-object v2

    sput-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->cpu:Ljava/lang/String;

    invoke-static {}, Lcom/ss/android/medialib/utils/DeviceInfoUtils;->getMaxCpuFreq()Ljava/lang/String;

    move-result-object v2

    sput-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->cpu_freq:Ljava/lang/String;

    invoke-static {}, Lcom/ss/android/medialib/utils/DeviceInfoUtils;->getNumOfCores()I

    move-result v2

    invoke-static {v2}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v2

    sput-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->cpu_core:Ljava/lang/String;

    invoke-static {}, Lcom/ss/android/medialib/utils/DeviceInfoUtils;->getTotalMemory()J

    move-result-wide v2

    invoke-static {v2, v3}, Ljava/lang/String;->valueOf(J)Ljava/lang/String;

    move-result-object v2

    sput-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->memory:Ljava/lang/String;

    invoke-static {}, Lcom/ss/android/medialib/utils/DeviceInfoUtils;->getInternalStorage()J

    move-result-wide v2

    invoke-static {v2, v3}, Ljava/lang/String;->valueOf(J)Ljava/lang/String;

    move-result-object v2

    sput-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->storage:Ljava/lang/String;

    invoke-static {p0}, Lcom/ss/android/medialib/utils/DeviceInfoUtils;->getExternalStorage(Landroid/content/Context;)J

    move-result-wide v2

    invoke-static {v2, v3}, Ljava/lang/String;->valueOf(J)Ljava/lang/String;

    move-result-object v2

    sput-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->external_storage:Ljava/lang/String;

    sget v2, Landroid/os/Build$VERSION;->SDK_INT:I

    invoke-static {v2}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v2

    sput-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->os_sdk_int:Ljava/lang/String;

    invoke-static {p0}, Lcom/ss/android/medialib/utils/DeviceInfoUtils;->getScreenWidth(Landroid/content/Context;)I

    move-result v2

    invoke-static {v2}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v2

    sput-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->screen_width:Ljava/lang/String;

    invoke-static {p0}, Lcom/ss/android/medialib/utils/DeviceInfoUtils;->getRealScreenHeight(Landroid/content/Context;)I

    move-result v2

    invoke-static {v2}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v2

    sput-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->screen_height:Ljava/lang/String;

    invoke-static {p0}, Lcom/ss/android/medialib/utils/DeviceIdHelper;->getIdentity(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v2

    sput-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->deviceid:Ljava/lang/String;

    invoke-virtual {p0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v2

    sput-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->appid:Ljava/lang/String;

    invoke-static {}, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->getCpuAbi()Ljava/lang/String;

    move-result-object v2

    sput-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->abi:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->locale:Ljava/lang/String;

    invoke-static {p0}, Lcom/ss/android/medialib/utils/DeviceInfoUtils;->getSimOperator(Landroid/content/Context;)Ljava/lang/String;

    move-result-object p0

    sput-object p0, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->sim_operator:Ljava/lang/String;

    return-void
.end method

.method public static toMap()Ljava/util/Map;
    .locals 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    const-string v1, "model"

    sget-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->model:Ljava/lang/String;

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v1, "cpu"

    sget-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->cpu:Ljava/lang/String;

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v1, "cpu_freq"

    sget-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->cpu_freq:Ljava/lang/String;

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v1, "cpu_core"

    sget-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->cpu_core:Ljava/lang/String;

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v1, "memory"

    sget-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->memory:Ljava/lang/String;

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string/jumbo v1, "storage"

    sget-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->storage:Ljava/lang/String;

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v1, "external_storage"

    sget-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->external_storage:Ljava/lang/String;

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v1, "screen_width"

    sget-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->screen_width:Ljava/lang/String;

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v1, "screen_height"

    sget-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->screen_height:Ljava/lang/String;

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v1, "os_sdk_int"

    sget-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->os_sdk_int:Ljava/lang/String;

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v1, "deviceid"

    sget-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->deviceid:Ljava/lang/String;

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v1, "appid"

    sget-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->appid:Ljava/lang/String;

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v1, "abi"

    sget-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->abi:Ljava/lang/String;

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v1, "locale"

    sget-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->locale:Ljava/lang/String;

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string/jumbo v1, "sim_operator"

    sget-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->sim_operator:Ljava/lang/String;

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    sget-boolean v1, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->nativeInited:Z

    if-nez v1, :cond_0

    invoke-static {}, Lcom/ss/android/medialib/config/GPUModelDetector;->getInstance()Lcom/ss/android/medialib/config/GPUModelDetector;

    move-result-object v1

    invoke-virtual {v1}, Lcom/ss/android/medialib/config/GPUModelDetector;->getGPUModel()Lcom/ss/android/medialib/config/GPUModelDetector$GPUModelInfo;

    move-result-object v1

    invoke-virtual {v1}, Lcom/ss/android/medialib/config/GPUModelDetector$GPUModelInfo;->getGPUSubModel()Lcom/ss/android/medialib/config/GPUModelDetector$ENvGpuSubModel;

    move-result-object v2

    invoke-virtual {v1}, Lcom/ss/android/medialib/config/GPUModelDetector$GPUModelInfo;->getGPUModelNumber()I

    move-result v1

    invoke-virtual {v2}, Lcom/ss/android/medialib/config/GPUModelDetector$ENvGpuSubModel;->name()Ljava/lang/String;

    move-result-object v2

    sput-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->gpu:Ljava/lang/String;

    invoke-static {v1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v1

    sput-object v1, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->gpu_ver:Ljava/lang/String;

    invoke-static {}, Lcom/ss/android/medialib/config/GPUModelDetector;->getInstance()Lcom/ss/android/medialib/config/GPUModelDetector;

    move-result-object v1

    invoke-virtual {v1}, Lcom/ss/android/medialib/config/GPUModelDetector;->getGLVersion()Ljava/lang/String;

    move-result-object v1

    sput-object v1, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->opengl_version:Ljava/lang/String;

    invoke-static {}, Lcom/ss/android/medialib/VideoSdkCore;->getSdkVersionCode()Ljava/lang/String;

    move-result-object v1

    sput-object v1, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->ve_version:Ljava/lang/String;

    const/4 v1, 0x1

    sput-boolean v1, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->nativeInited:Z

    :cond_0
    const-string v1, "gpu"

    sget-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->gpu:Ljava/lang/String;

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v1, "gpu_ver"

    sget-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->gpu_ver:Ljava/lang/String;

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v1, "opengl_version"

    sget-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->opengl_version:Ljava/lang/String;

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string/jumbo v1, "ve_version"

    sget-object v2, Lcom/ss/android/medialib/utils/DeviceInfoDetector;->ve_version:Ljava/lang/String;

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    return-object v0
.end method