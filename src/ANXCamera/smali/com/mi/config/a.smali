.class public Lcom/mi/config/a;
.super Lcom/android/camera/data/data/DataItemBase;
.source "DataItemFeature.java"

# interfaces
.implements Lcom/mi/config/c;


# static fields
.field private static final TAG:Ljava/lang/String; = "DataFeature"

.field private static final ro:Z = false


# instance fields
.field private rn:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/data/data/DataItemBase;-><init>()V

    invoke-virtual {p0}, Lcom/mi/config/a;->fx()V

    return-void
.end method

.method private K(Ljava/lang/String;)V
    .locals 4
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Lorg/json/JSONException;
        }
    .end annotation

    new-instance v0, Lorg/json/JSONObject;

    invoke-direct {v0, p1}, Lorg/json/JSONObject;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Lorg/json/JSONObject;->keys()Ljava/util/Iterator;

    move-result-object p1

    invoke-virtual {p0}, Lcom/mi/config/a;->getValues()Landroid/support/v4/util/SimpleArrayMap;

    move-result-object v1

    :goto_0
    invoke-interface {p1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_1

    invoke-interface {p1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    invoke-virtual {v0, v2}, Lorg/json/JSONObject;->opt(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v3

    invoke-virtual {v1, v2, v3}, Landroid/support/v4/util/SimpleArrayMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    if-nez v3, :cond_0

    goto :goto_0

    :cond_0
    new-instance p1, Ljava/lang/IllegalStateException;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "Duplicate key is found in the configuration file: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {p1, v0}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw p1

    :cond_1
    return-void
.end method

.method private O(Ljava/lang/String;)I
    .locals 1

    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x0

    invoke-virtual {p1, v0}, Ljava/lang/String;->charAt(I)C

    move-result p1

    invoke-static {p1}, Ljava/lang/Character;->isDigit(C)Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-static {p1}, Ljava/lang/String;->valueOf(C)Ljava/lang/String;

    move-result-object p1

    invoke-static {p1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result p1

    return p1

    :cond_0
    const/4 p1, -0x1

    return p1
.end method

.method private P(Ljava/lang/String;)Landroid/util/Size;
    .locals 3

    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_0

    const/16 v0, 0x3a

    invoke-virtual {p1, v0}, Ljava/lang/String;->indexOf(I)I

    move-result v0

    const/4 v1, 0x1

    add-int/2addr v0, v1

    invoke-virtual {p1, v0}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object p1

    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_0

    const-string v0, " "

    const-string v2, ""

    invoke-virtual {p1, v0, v2}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object p1

    const-string/jumbo v0, "x"

    invoke-virtual {p1, v0}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p1

    array-length v0, p1

    const/4 v2, 0x2

    if-lt v0, v2, :cond_0

    const/4 v0, 0x0

    aget-object v0, p1, v0

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    aget-object p1, p1, v1

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/Integer;->intValue()I

    move-result p1

    new-instance v1, Landroid/util/Size;

    invoke-direct {v1, v0, p1}, Landroid/util/Size;-><init>(II)V

    return-object v1

    :cond_0
    const/4 p1, 0x0

    return-object p1
.end method

.method private static a(Landroid/content/res/Resources;)Ljava/lang/String;
    .locals 3

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "feature_"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v1, Lcom/mi/config/b;->rp:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const v1, 0x7f090001

    :try_start_0
    invoke-virtual {p0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object p0

    if-eqz p0, :cond_1

    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v1

    if-eqz v1, :cond_1

    const-string v1, "default"

    invoke-virtual {v1, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    if-eqz v1, :cond_0

    goto :goto_0

    :cond_0
    return-object p0

    :cond_1
    :goto_0
    return-object v0

    :catch_0
    move-exception p0

    const-string v1, "DataFeature"

    const-string v2, "Device feature configuration file name undefined"

    invoke-static {v1, v2, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    return-object v0
.end method

.method private gj()Z
    .locals 2

    const-string v0, "c_s_a_u_q"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method


# virtual methods
.method public L(Ljava/lang/String;)Z
    .locals 1

    invoke-virtual {p0}, Lcom/mi/config/a;->getValues()Landroid/support/v4/util/SimpleArrayMap;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/support/v4/util/SimpleArrayMap;->containsKey(Ljava/lang/Object;)Z

    move-result p1

    return p1
.end method

.method public M(Ljava/lang/String;)Ljava/lang/String;
    .locals 1

    const-string v0, "c_28041_0x0000"

    invoke-virtual {p0, v0, p1}, Lcom/mi/config/a;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    return-object p1
.end method

.method public N(Ljava/lang/String;)Ljava/lang/String;
    .locals 1

    const-string v0, "c_22756_0x0001"

    invoke-virtual {p0, v0, p1}, Lcom/mi/config/a;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    return-object p1
.end method

.method public fA()Z
    .locals 3

    iget-object v0, p0, Lcom/mi/config/a;->rn:Ljava/lang/String;

    if-nez v0, :cond_0

    const-string v0, "ro.boot.hwc"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/mi/config/a;->rn:Ljava/lang/String;

    :cond_0
    const-string v0, "india"

    iget-object v1, p0, Lcom/mi/config/a;->rn:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_1

    return v1

    :cond_1
    iget-object v0, p0, Lcom/mi/config/a;->rn:Ljava/lang/String;

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_2

    iget-object v0, p0, Lcom/mi/config/a;->rn:Ljava/lang/String;

    sget-object v2, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {v0, v2}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v0

    const-string v2, "india_"

    invoke-virtual {v0, v2}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_2

    return v1

    :cond_2
    const/4 v0, 0x0

    return v0
.end method

.method public fB()Z
    .locals 2

    invoke-static {}, Lmiui/os/Build;->getRegion()Ljava/lang/String;

    move-result-object v0

    const-string v1, "IN"

    invoke-virtual {v0, v1}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v0

    return v0
.end method

.method public fC()Z
    .locals 2

    iget-object v0, p0, Lcom/mi/config/a;->rn:Ljava/lang/String;

    if-nez v0, :cond_0

    const-string v0, "ro.boot.hwc"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/mi/config/a;->rn:Ljava/lang/String;

    :cond_0
    const-string v0, "cn"

    iget-object v1, p0, Lcom/mi/config/a;->rn:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    return v0
.end method

.method public fD()Z
    .locals 2

    const-string v0, "s_i_a"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/mi/config/a;->fA()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v1, 0x1

    nop

    :cond_0
    return v1
.end method

.method public fE()Z
    .locals 2

    const-string v0, "c_0x0c"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/mi/config/a;->fA()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v1, 0x1

    nop

    :cond_0
    return v1
.end method

.method public fF()Z
    .locals 2

    const-string v0, "s_z_m"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public fG()Z
    .locals 2

    const-string v0, "a_e_d"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public fH()Z
    .locals 2

    const-string v0, "s_m_f"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public fI()Z
    .locals 2

    const-string v0, "i_l_m_d"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public fJ()Z
    .locals 2

    const-string v0, "s_p_l_b"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public fK()Z
    .locals 2

    const-string v0, "s_p_l_f"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public fL()Z
    .locals 2

    invoke-virtual {p0}, Lcom/mi/config/a;->fA()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    invoke-virtual {p0}, Lcom/mi/config/a;->fB()Z

    move-result v0

    if-eqz v0, :cond_1

    :cond_0
    const-string v0, "s_p_l_i_e"

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_1

    const/4 v1, 0x1

    nop

    :cond_1
    return v1
.end method

.method public fM()Z
    .locals 2

    const-string v0, "s_b_a"

    const/4 v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public fN()Z
    .locals 2

    const-string v0, "s_f_a"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public fO()Z
    .locals 2

    const-string v0, "s_p_a"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public fP()Z
    .locals 2

    const-string v0, "s_v_b"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public fQ()Z
    .locals 2

    const-string v0, "s_o_a_w"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public fR()Z
    .locals 2

    const-string v0, "c_0x4a"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public fS()Z
    .locals 2

    const-string v0, "c_0x4a_1"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public fT()Z
    .locals 2

    sget v0, Lcom/android/camera/Util;->sWindowHeight:I

    int-to-float v0, v0

    sget v1, Lcom/android/camera/Util;->sWindowWidth:I

    int-to-float v1, v1

    div-float/2addr v0, v1

    const v1, 0x400aaaab

    cmpl-float v0, v0, v1

    const/4 v1, 0x0

    if-ltz v0, :cond_0

    const-string v0, "s_f_s"

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v1, 0x1

    nop

    :cond_0
    return v1
.end method

.method public fU()Z
    .locals 2

    sget v0, Lcom/android/camera/Util;->sWindowHeight:I

    int-to-float v0, v0

    sget v1, Lcom/android/camera/Util;->sWindowWidth:I

    int-to-float v1, v1

    div-float/2addr v0, v1

    const v1, 0x400e38e4

    cmpl-float v0, v0, v1

    const/4 v1, 0x0

    if-ltz v0, :cond_0

    const-string v0, "s_20_9_s"

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v1, 0x1

    nop

    :cond_0
    return v1
.end method

.method public fV()Z
    .locals 4

    sget v0, Lcom/android/camera/Util;->sWindowHeight:I

    int-to-float v0, v0

    sget v1, Lcom/android/camera/Util;->sWindowWidth:I

    int-to-float v1, v1

    div-float/2addr v0, v1

    const v1, 0x40071c72

    sub-float/2addr v0, v1

    invoke-static {v0}, Ljava/lang/Math;->abs(F)F

    move-result v0

    float-to-double v0, v0

    const-wide v2, 0x3f947ae147ae147bL    # 0.02

    cmpg-double v0, v0, v2

    const/4 v1, 0x0

    if-gtz v0, :cond_0

    const-string v0, "s_19_9_s"

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v1, 0x1

    nop

    :cond_0
    return v1
.end method

.method public fW()Z
    .locals 4

    sget v0, Lcom/android/camera/Util;->sWindowHeight:I

    int-to-float v0, v0

    sget v1, Lcom/android/camera/Util;->sWindowWidth:I

    int-to-float v1, v1

    div-float/2addr v0, v1

    const v1, 0x40055555

    sub-float/2addr v0, v1

    invoke-static {v0}, Ljava/lang/Math;->abs(F)F

    move-result v0

    float-to-double v0, v0

    const-wide v2, 0x3f947ae147ae147bL    # 0.02

    cmpg-double v0, v0, v2

    const/4 v1, 0x0

    if-gez v0, :cond_0

    const-string v0, "s_18_7_5_9_s"

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v1, 0x1

    nop

    :cond_0
    return v1
.end method

.method public fX()Z
    .locals 2

    const-string v0, "s_m_c_t_f"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public fY()Z
    .locals 2

    const-string v0, "s_a_3"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public fZ()Z
    .locals 3

    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/4 v1, 0x0

    const/16 v2, 0x1c

    if-lt v0, v2, :cond_0

    const-string v0, "s_s_n"

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v1, 0x1

    nop

    :cond_0
    return v1
.end method

.method public fx()V
    .locals 5

    invoke-static {}, Lcom/android/camera/CameraAppImpl;->getAndroidContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-static {v0}, Lcom/mi/config/a;->a(Landroid/content/res/Resources;)Ljava/lang/String;

    move-result-object v1

    const-string v2, "raw"

    const-string v3, "com.android.camera"

    invoke-virtual {v0, v1, v2, v3}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    if-gtz v1, :cond_0

    const-string v0, "DataFeature"

    const-string v1, "feature list default"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_0
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    :try_start_0
    new-instance v3, Ljava/io/BufferedReader;

    new-instance v4, Ljava/io/InputStreamReader;

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->openRawResource(I)Ljava/io/InputStream;

    move-result-object v0

    invoke-direct {v4, v0}, Ljava/io/InputStreamReader;-><init>(Ljava/io/InputStream;)V

    invoke-direct {v3, v4}, Ljava/io/BufferedReader;-><init>(Ljava/io/Reader;)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_2
    .catch Lorg/json/JSONException; {:try_start_0 .. :try_end_0} :catch_2

    const/4 v0, 0x0

    :goto_0
    :try_start_1
    invoke-virtual {v3}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object v1

    if-eqz v1, :cond_1

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    goto :goto_0

    :cond_1
    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {p0, v1}, Lcom/mi/config/a;->K(Ljava/lang/String;)V
    :try_end_1
    .catch Ljava/lang/Throwable; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    :try_start_2
    invoke-virtual {v3}, Ljava/io/BufferedReader;->close()V
    :try_end_2
    .catch Ljava/io/IOException; {:try_start_2 .. :try_end_2} :catch_2
    .catch Lorg/json/JSONException; {:try_start_2 .. :try_end_2} :catch_2

    goto :goto_3

    :catchall_0
    move-exception v1

    goto :goto_1

    :catch_0
    move-exception v0

    :try_start_3
    throw v0
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_0

    :goto_1
    if-eqz v0, :cond_2

    :try_start_4
    invoke-virtual {v3}, Ljava/io/BufferedReader;->close()V
    :try_end_4
    .catch Ljava/lang/Throwable; {:try_start_4 .. :try_end_4} :catch_1
    .catch Ljava/io/IOException; {:try_start_4 .. :try_end_4} :catch_2
    .catch Lorg/json/JSONException; {:try_start_4 .. :try_end_4} :catch_2

    goto :goto_2

    :catch_1
    move-exception v2

    :try_start_5
    invoke-virtual {v0, v2}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    goto :goto_2

    :cond_2
    invoke-virtual {v3}, Ljava/io/BufferedReader;->close()V

    :goto_2
    throw v1
    :try_end_5
    .catch Ljava/io/IOException; {:try_start_5 .. :try_end_5} :catch_2
    .catch Lorg/json/JSONException; {:try_start_5 .. :try_end_5} :catch_2

    :catch_2
    move-exception v0

    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    :goto_3
    return-void
.end method

.method public fy()Z
    .locals 2

    const-string v0, "s_a"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public fz()I
    .locals 2

    const-string v0, "c_t_r"

    const/16 v1, 0x14

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getInt(Ljava/lang/String;I)I

    move-result v0

    return v0
.end method

.method public gA()I
    .locals 2

    const-string v0, "c_0x01_p_g_a_v"

    const/16 v1, 0x118

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getInt(Ljava/lang/String;I)I

    move-result v0

    return v0
.end method

.method public gB()F
    .locals 3

    const-string v0, "c_0x02_p_c_r_v"

    const-wide v1, 0x3fec0d1b80000000L    # 0.8766000270843506

    invoke-virtual {p0, v0, v1, v2}, Lcom/mi/config/a;->getDoubleFromValues(Ljava/lang/String;D)D

    move-result-wide v0

    double-to-float v0, v0

    return v0
.end method

.method public gC()Z
    .locals 2

    const-string v0, "c_0x03"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gD()Z
    .locals 2

    const-string v0, "c_0x37"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    sget-boolean v0, Lmiui/os/Build;->IS_INTERNATIONAL_BUILD:Z

    if-nez v0, :cond_0

    const/4 v1, 0x1

    nop

    :cond_0
    return v1
.end method

.method public gE()Z
    .locals 2

    const-string v0, "c_0x58"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gF()Z
    .locals 2

    const-string v0, "c_16001_0x0001"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gG()Z
    .locals 2

    const-string v0, "c_0x60"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gH()Z
    .locals 2

    const-string v0, "s_c_w_m"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gI()Ljava/lang/String;
    .locals 2

    const-string v0, "c_0x47"

    const-string/jumbo v1, "v0"

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public gJ()Z
    .locals 2

    const-string v0, "c_d_e_f_w"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gK()Z
    .locals 2

    const-string v0, "c_0x08"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gL()Z
    .locals 2

    const-string v0, "c_0x09"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gM()Z
    .locals 2

    const-string v0, "c_0x0a"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gN()Z
    .locals 2

    const-string v0, "c_0x10"

    const/4 v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gO()Z
    .locals 2

    const-string v0, "c_0x11"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gP()I
    .locals 2

    const-string v0, "c_0x13"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getInt(Ljava/lang/String;I)I

    move-result v0

    return v0
.end method

.method public gQ()Z
    .locals 2

    invoke-static {}, Lcom/android/camera/Util;->isGlobalVersion()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    const-string v0, "c_0x1a"

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gR()Z
    .locals 2

    const-string v0, "c_0x1b"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gS()Z
    .locals 2

    const-string v0, "c_0x1c"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gT()Z
    .locals 2

    const-string v0, "c_0x0e"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gU()Z
    .locals 2

    invoke-virtual {p0}, Lcom/mi/config/a;->gV()I

    move-result v0

    const/4 v1, 0x0

    if-ltz v0, :cond_1

    const-string v0, "c_0x0f"

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    goto :goto_1

    :cond_1
    :goto_0
    const/4 v1, 0x1

    :goto_1
    return v1
.end method

.method public gV()I
    .locals 2

    const-string v0, "c_19039_0x0004"

    const-string v1, ""

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/mi/config/a;->O(Ljava/lang/String;)I

    move-result v0

    return v0
.end method

.method public gW()I
    .locals 2

    const-string v0, "c_19040_0x0005"

    const-string v1, ""

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/mi/config/a;->O(Ljava/lang/String;)I

    move-result v0

    return v0
.end method

.method public gX()Z
    .locals 2

    const-string v0, "c_0x14"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gY()Z
    .locals 2

    const-string v0, "c_0x16"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gZ()Z
    .locals 2

    invoke-static {}, Lcom/mi/config/b;->kw()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x0

    return v0

    :cond_0
    const-string v0, "c_0x41"

    const/4 v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public ga()Z
    .locals 2

    const-string v0, "c_33066_0x0002"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gb()Z
    .locals 2

    const-string v0, "s_m_l"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gd()Z
    .locals 2

    const-string v0, "s_s_v"

    const/4 v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public ge()Z
    .locals 2

    const-string v0, "s_s_s"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gf()Z
    .locals 2

    const-string v0, "s_f_s_c"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gg()Z
    .locals 2

    const-string v0, "s_b_m"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gh()I
    .locals 2

    const-string v0, "c_0x0b"

    const/16 v1, 0xb4

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getInt(Ljava/lang/String;I)I

    move-result v0

    return v0
.end method

.method public gi()Z
    .locals 2

    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1c

    if-le v0, v1, :cond_0

    invoke-direct {p0}, Lcom/mi/config/a;->gj()Z

    move-result v0

    return v0

    :cond_0
    const-string v0, "s_a_u"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gk()Z
    .locals 2

    const-string v0, "s_f_z_i"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gl()Z
    .locals 2

    const-string v0, "s_v_f_m"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gm()Z
    .locals 2

    const-string v0, "s_f_9"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gn()Z
    .locals 2

    invoke-virtual {p0}, Lcom/mi/config/a;->gm()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    const-string v0, "s_s_m_t"

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v1, 0x1

    nop

    :cond_0
    return v1
.end method

.method public go()Z
    .locals 2

    invoke-virtual {p0}, Lcom/mi/config/a;->gm()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    invoke-virtual {p0}, Lcom/mi/config/a;->gn()Z

    move-result v0

    if-nez v0, :cond_0

    const-string v0, "c_22367_0x0000"

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v1, 0x1

    nop

    :cond_0
    return v1
.end method

.method public gp()Z
    .locals 2

    const-string v0, "s_f_9"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-nez v0, :cond_1

    const-string v0, "s_s_m_t"

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-nez v0, :cond_1

    const-string v0, "c_22367_0x0000"

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    goto :goto_1

    :cond_1
    :goto_0
    const/4 v1, 0x1

    :goto_1
    return v1
.end method

.method public gq()Z
    .locals 2

    const-string v0, "s_e_l"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gr()Ljava/lang/String;
    .locals 2

    const-string v0, "h_d_v"

    const-string v1, ""

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public gs()I
    .locals 2

    const-string v0, "a_l_l_l"

    const/16 v1, 0x15e

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getInt(Ljava/lang/String;I)I

    move-result v0

    return v0
.end method

.method public gt()I
    .locals 2

    const-string v0, "a_l_h_l"

    const/16 v1, 0x12c

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getInt(Ljava/lang/String;I)I

    move-result v0

    return v0
.end method

.method public gu()Z
    .locals 2

    const-string v0, "s_c_w_b"

    const/4 v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gv()Z
    .locals 2

    const-string v0, "c_0x04_i_l_h_d"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gw()Z
    .locals 2

    const-string v0, "c_0x17"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gx()Z
    .locals 2

    const-string v0, "c_0x60"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gy()Z
    .locals 2

    const-string v0, "c_0x19"

    const/4 v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public gz()Z
    .locals 2

    invoke-static {}, Lcom/android/camera/Util;->isGlobalVersion()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return v1

    :cond_0
    const-string v0, "c_0x00_s_l_s"

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hA()Ljava/lang/String;
    .locals 2

    const-string v0, "c_0x40"

    const-string v1, "common"

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public hB()Z
    .locals 2

    const-string v0, "c_0x42"

    invoke-virtual {p0}, Lcom/mi/config/a;->hP()Z

    move-result v1

    xor-int/lit8 v1, v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hC()Z
    .locals 2

    const-string v0, "c_0x42_m"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hD()Z
    .locals 2

    const-string v0, "c_0x45"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hE()Z
    .locals 2

    const-string v0, "c_0x46"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hF()Z
    .locals 2

    const-string v0, "c_0x48"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hG()Z
    .locals 2

    sget-boolean v0, Lcom/android/camera/HybridZoomingSystem;->IS_3_OR_MORE_SAT:Z

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    const-string v0, "c_0x49"

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hH()Z
    .locals 2

    const-string v0, "c_0x50"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hI()Z
    .locals 2

    const-string v0, "c_0x51"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hJ()Z
    .locals 2

    const-string v0, "c_0x52"

    const/4 v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hK()Z
    .locals 2

    const-string v0, "c_28041_0x0001"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hL()Z
    .locals 2

    const-string v0, "c_0x53"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hM()Z
    .locals 2

    const-string v0, "c_19039_0x0000"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hN()Z
    .locals 2

    const-string v0, "c_0x54"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hO()Z
    .locals 2

    const-string v0, "c_0x55"

    const/4 v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hP()Z
    .locals 2

    const-string v0, "c_0x56"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hQ()Z
    .locals 2

    const-string v0, "c_r_i_m_m"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hR()Z
    .locals 1

    const/4 v0, 0x1

    return v0
.end method

.method public hS()Z
    .locals 2

    const-string v0, "c_9006_0x0000"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hT()I
    .locals 2

    sget-boolean v0, Lcom/mi/config/b;->sU:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x6

    return v0

    :cond_0
    const-string v0, "c_9006_0x0001"

    const/4 v1, 0x5

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getInt(Ljava/lang/String;I)I

    move-result v0

    return v0
.end method

.method public hU()I
    .locals 2

    const-string v0, "c_0x57"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getInt(Ljava/lang/String;I)I

    move-result v0

    return v0
.end method

.method public hV()I
    .locals 2

    const-string v0, "c_0x5a"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getInt(Ljava/lang/String;I)I

    move-result v0

    return v0
.end method

.method public hW()Z
    .locals 2

    const-string v0, "c_22367_0x0001"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hX()Z
    .locals 2

    const-string v0, "c_0x59"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hY()Z
    .locals 2

    const-string v0, "c_19039_0x0001"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hZ()Z
    .locals 2

    const-string v0, "c_19039_0x0002"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public ha()Z
    .locals 2

    const-string v0, "c_0x20"

    const/4 v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hb()Z
    .locals 2

    const-string v0, "c_0x21"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hc()Z
    .locals 3

    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/4 v1, 0x0

    const/16 v2, 0x1c

    if-ge v0, v2, :cond_0

    return v1

    :cond_0
    const-string v0, "c_0x23"

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hd()Z
    .locals 2

    const-string v0, "i_s_e_r"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public he()Z
    .locals 2

    const-string v0, "c_0x24"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hf()Z
    .locals 2

    const-string v0, "i_q_a_u_m"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hg()Z
    .locals 2

    const-string v0, "c_0x25"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hh()Z
    .locals 2

    const-string v0, "c_e_f_a_l"

    const/4 v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hi()Z
    .locals 2

    const-string v0, "c_0x26"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hj()Z
    .locals 2

    const-string v0, "e_p_p_l_t"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hk()Z
    .locals 2

    const-string v0, "i_v_b_c_f_d"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hl()Z
    .locals 2

    const-string v0, "c_0x27"

    const/4 v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hm()Z
    .locals 2

    const-string v0, "i_s_s_b"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hn()Z
    .locals 2

    const-string v0, "i_s_q_c"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public ho()J
    .locals 2

    const-string v0, "s_b_m_d_t"

    const/4 v1, -0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getInt(Ljava/lang/String;I)I

    move-result v0

    int-to-long v0, v0

    return-wide v0
.end method

.method public hp()I
    .locals 2

    const-string v0, "s_b_m_s_c"

    const/4 v1, -0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getInt(Ljava/lang/String;I)I

    move-result v0

    return v0
.end method

.method public hq()Z
    .locals 2

    const-string v0, "c_0x31"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hr()Z
    .locals 2

    const-string v0, "c_0x33"

    const/4 v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hs()Z
    .locals 2

    const-string v0, "c_0x44"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public ht()Z
    .locals 1

    invoke-virtual {p0}, Lcom/mi/config/a;->gV()I

    move-result v0

    packed-switch v0, :pswitch_data_0

    const/4 v0, 0x0

    return v0

    :pswitch_0
    invoke-virtual {p0}, Lcom/mi/config/a;->hr()Z

    move-result v0

    return v0

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_0
        :pswitch_0
        :pswitch_0
    .end packed-switch
.end method

.method public hu()Z
    .locals 2

    invoke-virtual {p0}, Lcom/mi/config/a;->gV()I

    move-result v0

    const/4 v1, 0x3

    if-ne v1, v0, :cond_0

    const/4 v0, 0x1

    return v0

    :cond_0
    const/4 v0, 0x0

    return v0
.end method

.method public hv()Z
    .locals 2

    const-string v0, "c_0x34"

    const/4 v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hw()Z
    .locals 2

    const-string v0, "c_0x35"

    const/4 v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hx()Z
    .locals 2

    const-string v0, "c_0x36"

    const/4 v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hy()Z
    .locals 2

    const-string v0, "c_0x38"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public hz()Z
    .locals 2

    const-string v0, "c_0x39"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public iA()Z
    .locals 2

    const-string v0, "c_13254_0x01"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public iB()Z
    .locals 2

    const-string v0, "c_190920"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public iC()Z
    .locals 2

    const-string v0, "c_0x5c"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public iD()Ljava/lang/String;
    .locals 2

    const-string v0, "c_19039_xxxxx1"

    const-string v1, ""

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public ia()Z
    .locals 2

    const-string v0, "c_19039_0x0003"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public ib()Landroid/util/Size;
    .locals 2

    const-string v0, "c_19039_0x0004"

    const-string v1, ""

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/mi/config/a;->P(Ljava/lang/String;)Landroid/util/Size;

    move-result-object v0

    return-object v0
.end method

.method public ic()Landroid/util/Size;
    .locals 2

    const-string v0, "c_19040_0x0005"

    const-string v1, ""

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/mi/config/a;->P(Ljava/lang/String;)Landroid/util/Size;

    move-result-object v0

    return-object v0
.end method

.method public id()Z
    .locals 2

    const-string v0, "c_22367_0x0002"

    const/4 v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public ie()Z
    .locals 2

    const-string v0, "c_33066_0x0001"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public if()I
    .locals 2

    const-string v0, "s_p_r_n"

    const/4 v1, -0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getInt(Ljava/lang/String;I)I

    move-result v0

    return v0
.end method

.method public ig()Z
    .locals 2

    const-string v0, "c_19039_0x0005"

    const/4 v1, -0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getInt(Ljava/lang/String;I)I

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public ih()Z
    .locals 2

    const-string v0, "c_19039_0x0005"

    const/4 v1, -0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getInt(Ljava/lang/String;I)I

    move-result v0

    const/4 v1, 0x1

    if-ne v0, v1, :cond_0

    goto :goto_0

    :cond_0
    const/4 v1, 0x0

    :goto_0
    return v1
.end method

.method public ii()Z
    .locals 2

    const-string v0, "c_19039_0x0005"

    const/4 v1, -0x1

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getInt(Ljava/lang/String;I)I

    move-result v0

    const/4 v1, 0x2

    if-ne v0, v1, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public ij()Z
    .locals 2

    const-string v0, "c_19039_0x0006"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public ik()Z
    .locals 2

    const-string v0, "c_19039_0x0007"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public il()Z
    .locals 2

    const-string v0, "c_22367_0x0003"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public im()Z
    .locals 2

    const-string v0, "c_9006_0x0002"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public in()Z
    .locals 2

    const-string v0, "c_19039_0x0008"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public ip()Z
    .locals 2

    const-string v0, "c_19039_0x0009"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public iq()Z
    .locals 2

    const-string v0, "c_19039_0x0010"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public ir()I
    .locals 2

    const-string v0, "c_22367_0x000A"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getInt(Ljava/lang/String;I)I

    move-result v0

    return v0
.end method

.method public is()Z
    .locals 2

    const-string v0, "c_27845_0x0001"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method protected isMutable()Z
    .locals 1

    const/4 v0, 0x0

    return v0
.end method

.method public isSRRequireReprocess()Z
    .locals 2

    const-string v0, "c_9006_0x0004"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public isSupport4KUHDEIS()Z
    .locals 2

    const-string v0, "c_0x07"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public isSupport960VideoEditor()Z
    .locals 2

    const-string v0, "c_0x5b"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public isSupportBeautyBody()Z
    .locals 2

    const-string v0, "s_b_b"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public isSupportBokehAdjust()Z
    .locals 2

    const-string v0, "c_0x22"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public isSupportMacroMode()Z
    .locals 2

    const-string v0, "c_0x32"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public isSupportNormalWideLDC()Z
    .locals 2

    const-string v0, "c_0x05"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public isSupportShortVideoBeautyBody()Z
    .locals 2

    const-string v0, "c_0x28"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public isSupportUltraWide()Z
    .locals 2

    const-string v0, "s_u_w"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public isSupportUltraWideLDC()Z
    .locals 2

    const-string v0, "c_0x06"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public isTransient()Z
    .locals 1

    const/4 v0, 0x1

    return v0
.end method

.method public it()Z
    .locals 2

    const-string v0, "c_22367_0x0005"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public iu()Ljava/lang/String;
    .locals 2

    const-string v0, "c_22367_0x0006"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public iv()Z
    .locals 2

    const-string v0, "c_19039_0x0011"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public iw()Z
    .locals 2

    const-string v0, "c_22367_0x0007"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public ix()Z
    .locals 2

    const-string v0, "c_22367_0x0008"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getInt(Ljava/lang/String;I)I

    move-result v0

    if-nez v0, :cond_0

    const/4 v1, 0x1

    nop

    :cond_0
    return v1
.end method

.method public iy()Z
    .locals 3

    const-string v0, "c_22367_0x0008"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getInt(Ljava/lang/String;I)I

    move-result v0

    const/4 v2, 0x1

    if-ne v0, v2, :cond_0

    move v1, v2

    nop

    :cond_0
    return v1
.end method

.method public iz()Z
    .locals 2

    const-string v0, "c_22367_0x0009"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public p(Z)Ljava/lang/String;
    .locals 1

    if-eqz p1, :cond_0

    const-string p1, "c_0x29"

    const-string v0, "4.5"

    invoke-virtual {p0, p1, v0}, Lcom/mi/config/a;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    return-object p1

    :cond_0
    const-string p1, "c_0x30"

    const-string v0, "4"

    invoke-virtual {p0, p1, v0}, Lcom/mi/config/a;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    return-object p1
.end method

.method public provideKey()Ljava/lang/String;
    .locals 1

    const/4 v0, 0x0

    return-object v0
.end method

.method public q(Z)Z
    .locals 1

    const/4 v0, 0x0

    if-eqz p1, :cond_0

    const-string p1, "c_0x43"

    invoke-virtual {p0, p1, v0}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result p1

    if-eqz p1, :cond_0

    const/4 v0, 0x1

    nop

    :cond_0
    return v0
.end method

.method public r(Z)I
    .locals 1

    const/4 v0, 0x0

    if-nez p1, :cond_0

    return v0

    :cond_0
    const-string p1, "c_22367_0x0004"

    invoke-virtual {p0, p1, v0}, Lcom/mi/config/a;->getInt(Ljava/lang/String;I)I

    move-result p1

    return p1
.end method

.method public shouldCheckSatFallbackState()Z
    .locals 2

    const-string v0, "c_9006_0x0005"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mi/config/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method
