.class public Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;
.super Lcom/ss/android/ugc/effectmanager/common/task/NormalTask;
.source "CheckUpdateTask.java"


# static fields
.field private static final APP_VERSION:Ljava/lang/String; = "app_version"

.field public static final CHECK_CATEGORY_UPDATE:I = 0x1

.field public static final CHECK_PANEL_UPDATE:I = 0x0

.field public static final CHECK_PANEL_UPDATE_PAGE:I = 0x2

.field private static final VERSION:Ljava/lang/String; = "version"


# instance fields
.field private mCategory:Ljava/lang/String;

.field private mCheckType:I

.field private mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

.field private mEffectContext:Lcom/ss/android/ugc/effectmanager/context/EffectContext;

.field private mPanel:Ljava/lang/String;

.field private mVersion:Ljava/lang/String;


# direct methods
.method public constructor <init>(Lcom/ss/android/ugc/effectmanager/context/EffectContext;Ljava/lang/String;Landroid/os/Handler;Ljava/lang/String;Ljava/lang/String;I)V
    .locals 1

    const-string v0, "NETWORK"

    invoke-direct {p0, p3, p2, v0}, Lcom/ss/android/ugc/effectmanager/common/task/NormalTask;-><init>(Landroid/os/Handler;Ljava/lang/String;Ljava/lang/String;)V

    iput-object p1, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mEffectContext:Lcom/ss/android/ugc/effectmanager/context/EffectContext;

    iget-object p1, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mEffectContext:Lcom/ss/android/ugc/effectmanager/context/EffectContext;

    invoke-virtual {p1}, Lcom/ss/android/ugc/effectmanager/context/EffectContext;->getEffectConfiguration()Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    move-result-object p1

    iput-object p1, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    iput-object p4, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mPanel:Ljava/lang/String;

    iput-object p5, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mCategory:Ljava/lang/String;

    iput p6, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mCheckType:I

    return-void
.end method

.method private buildRequest()Lcom/ss/android/ugc/effectmanager/common/EffectRequest;
    .locals 5

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iget-object v1, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mEffectContext:Lcom/ss/android/ugc/effectmanager/context/EffectContext;

    invoke-virtual {v1}, Lcom/ss/android/ugc/effectmanager/context/EffectContext;->getLinkSelector()Lcom/ss/android/ugc/effectmanager/link/LinkSelector;

    move-result-object v1

    nop

    const/4 v2, 0x0

    if-eqz v1, :cond_0

    invoke-virtual {v1}, Lcom/ss/android/ugc/effectmanager/link/LinkSelector;->getContext()Landroid/content/Context;

    move-result-object v1

    const-string/jumbo v3, "version"

    invoke-virtual {v1, v3, v2}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v1

    const-string v2, "app_version"

    const-string v3, ""

    invoke-interface {v1, v2, v3}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    iget-object v3, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v3}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getAppVersion()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    xor-int/lit8 v2, v2, 0x1

    if-eqz v2, :cond_0

    invoke-interface {v1}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v1

    const-string v3, "app_version"

    iget-object v4, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v4}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getAppVersion()Ljava/lang/String;

    move-result-object v4

    invoke-interface {v1, v3, v4}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    invoke-interface {v1}, Landroid/content/SharedPreferences$Editor;->commit()Z

    :cond_0
    iget-object v1, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v1}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getAccessKey()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_1

    const-string v1, "access_key"

    iget-object v3, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v3}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getAccessKey()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v1, v3}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_1
    iget-object v1, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v1}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getAppVersion()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_2

    const-string v1, "app_version"

    iget-object v3, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v3}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getAppVersion()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v1, v3}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_2
    iget-object v1, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v1}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getSdkVersion()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_3

    const-string v1, "sdk_version"

    iget-object v3, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v3}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getSdkVersion()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v1, v3}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_3
    iget-object v1, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v1}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getChannel()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_4

    const-string v1, "channel"

    iget-object v3, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v3}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getChannel()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v1, v3}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_4
    iget-object v1, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v1}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getPlatform()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_5

    const-string v1, "device_platform"

    iget-object v3, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v3}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getPlatform()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v1, v3}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_5
    iget-object v1, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v1}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getDeviceId()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_6

    const-string v1, "device_id"

    iget-object v3, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v3}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getDeviceId()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v1, v3}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_6
    iget-object v1, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v1}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getRegion()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_7

    const-string v1, "region"

    iget-object v3, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v3}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getRegion()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v1, v3}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_7
    iget-object v1, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v1}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getDeviceType()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_8

    const-string v1, "device_type"

    iget-object v3, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v3}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getDeviceType()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v1, v3}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_8
    iget-object v1, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v1}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getAppID()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_9

    const-string v1, "aid"

    iget-object v3, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v3}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getAppID()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v1, v3}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_9
    iget-object v1, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v1}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getAppLanguage()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_a

    const-string v1, "app_language"

    iget-object v3, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v3}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getAppLanguage()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v1, v3}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_a
    iget-object v1, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v1}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getSysLanguage()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_b

    const-string v1, "language"

    iget-object v3, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v3}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getSysLanguage()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v1, v3}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_b
    const-string v1, "panel"

    iget-object v3, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mPanel:Ljava/lang/String;

    invoke-virtual {v0, v1, v3}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    iget v1, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mCheckType:I

    packed-switch v1, :pswitch_data_0

    const-string v1, "/panel/check"

    goto :goto_0

    :pswitch_0
    const-string v1, "/panel/check"

    goto :goto_0

    :pswitch_1
    const-string v1, "/category/check"

    const-string v3, "category"

    iget-object v4, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mCategory:Ljava/lang/String;

    invoke-virtual {v0, v3, v4}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :goto_0
    if-eqz v2, :cond_c

    const-string/jumbo v2, "version"

    const-string v3, ""

    invoke-virtual {v0, v2, v3}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_1

    :cond_c
    const-string/jumbo v2, "version"

    iget-object v3, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mVersion:Ljava/lang/String;

    invoke-virtual {v0, v2, v3}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :goto_1
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v3, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mEffectContext:Lcom/ss/android/ugc/effectmanager/context/EffectContext;

    invoke-virtual {v3}, Lcom/ss/android/ugc/effectmanager/context/EffectContext;->getLinkSelector()Lcom/ss/android/ugc/effectmanager/link/LinkSelector;

    move-result-object v3

    invoke-virtual {v3}, Lcom/ss/android/ugc/effectmanager/link/LinkSelector;->getBestHostUrl()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v3, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v3}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getApiAdress()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/ss/android/ugc/effectmanager/common/utils/NetworkUtils;->buildRequestUrl(Ljava/util/Map;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    new-instance v1, Lcom/ss/android/ugc/effectmanager/common/EffectRequest;

    const-string v2, "GET"

    invoke-direct {v1, v2, v0}, Lcom/ss/android/ugc/effectmanager/common/EffectRequest;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    return-object v1

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private checkedChannelCache()Z
    .locals 6

    iget v0, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mCheckType:I

    packed-switch v0, :pswitch_data_0

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "effect_version"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mPanel:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    goto :goto_0

    :pswitch_0
    iget-object v0, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mPanel:Ljava/lang/String;

    invoke-static {v0}, Lcom/ss/android/ugc/effectmanager/common/utils/EffectCacheKeyGenerator;->generatePanelInfoVersionKey(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    goto :goto_0

    :pswitch_1
    iget-object v0, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mPanel:Ljava/lang/String;

    iget-object v1, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mCategory:Ljava/lang/String;

    invoke-static {v0, v1}, Lcom/ss/android/ugc/effectmanager/common/utils/EffectCacheKeyGenerator;->generateCategoryVersionKey(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    :goto_0
    iget-object v1, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v1}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getCache()Lcom/ss/android/ugc/effectmanager/common/listener/ICache;

    move-result-object v1

    invoke-interface {v1, v0}, Lcom/ss/android/ugc/effectmanager/common/listener/ICache;->queryToStream(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object v0

    const/4 v1, 0x0

    const/4 v2, 0x0

    if-nez v0, :cond_0

    return v2

    :cond_0
    :try_start_0
    new-instance v3, Landroid/util/JsonReader;

    new-instance v4, Ljava/io/InputStreamReader;

    invoke-direct {v4, v0}, Ljava/io/InputStreamReader;-><init>(Ljava/io/InputStream;)V

    invoke-direct {v3, v4}, Landroid/util/JsonReader;-><init>(Ljava/io/Reader;)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_1
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    :try_start_1
    invoke-virtual {v3}, Landroid/util/JsonReader;->beginObject()V

    :goto_1
    invoke-virtual {v3}, Landroid/util/JsonReader;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_4

    invoke-virtual {v3}, Landroid/util/JsonReader;->nextName()Ljava/lang/String;

    move-result-object v0

    const/4 v1, -0x1

    invoke-virtual {v0}, Ljava/lang/String;->hashCode()I

    move-result v4

    const v5, 0x14f51cd8

    if-eq v4, v5, :cond_1

    goto :goto_2

    :cond_1
    const-string/jumbo v4, "version"

    invoke-virtual {v0, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    move v1, v2

    :cond_2
    :goto_2
    if-eqz v1, :cond_3

    invoke-virtual {v3}, Landroid/util/JsonReader;->nextString()Ljava/lang/String;

    goto :goto_1

    :cond_3
    invoke-virtual {v3}, Landroid/util/JsonReader;->nextString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mVersion:Ljava/lang/String;
    :try_end_1
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    nop

    :cond_4
    const/4 v0, 0x1

    invoke-static {v3}, Lcom/ss/android/ugc/effectmanager/common/utils/CloseUtil;->close(Ljava/io/Closeable;)V

    return v0

    :catchall_0
    move-exception v0

    goto :goto_3

    :catch_0
    move-exception v0

    goto :goto_4

    :catchall_1
    move-exception v0

    move-object v3, v1

    :goto_3
    invoke-static {v3}, Lcom/ss/android/ugc/effectmanager/common/utils/CloseUtil;->close(Ljava/io/Closeable;)V

    throw v0

    :catch_1
    move-exception v0

    move-object v3, v1

    :goto_4
    nop

    invoke-static {v3}, Lcom/ss/android/ugc/effectmanager/common/utils/CloseUtil;->close(Ljava/io/Closeable;)V

    return v2

    nop

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method


# virtual methods
.method public execute()V
    .locals 7

    invoke-direct {p0}, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->checkedChannelCache()Z

    move-result v0

    const/4 v1, 0x0

    const/16 v2, 0xd

    if-eqz v0, :cond_2

    invoke-direct {p0}, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->buildRequest()Lcom/ss/android/ugc/effectmanager/common/EffectRequest;

    move-result-object v0

    invoke-virtual {p0}, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->isCanceled()Z

    move-result v3

    const/4 v4, 0x0

    if-eqz v3, :cond_0

    new-instance v3, Lcom/ss/android/ugc/effectmanager/effect/task/result/EffectCheckUpdateResult;

    new-instance v5, Lcom/ss/android/ugc/effectmanager/common/task/ExceptionResult;

    const/16 v6, 0x2711

    invoke-direct {v5, v6}, Lcom/ss/android/ugc/effectmanager/common/task/ExceptionResult;-><init>(I)V

    invoke-direct {v3, v4, v5}, Lcom/ss/android/ugc/effectmanager/effect/task/result/EffectCheckUpdateResult;-><init>(ZLcom/ss/android/ugc/effectmanager/common/task/ExceptionResult;)V

    invoke-virtual {p0, v2, v3}, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->sendMessage(ILcom/ss/android/ugc/effectmanager/common/task/BaseTaskResult;)V

    :cond_0
    :try_start_0
    iget-object v3, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v3}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getEffectNetWorker()Lcom/ss/android/ugc/effectmanager/network/EffectNetWorkerWrapper;

    move-result-object v3

    iget-object v5, p0, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->mConfiguration:Lcom/ss/android/ugc/effectmanager/EffectConfiguration;

    invoke-virtual {v5}, Lcom/ss/android/ugc/effectmanager/EffectConfiguration;->getJsonConverter()Lcom/ss/android/ugc/effectmanager/common/listener/IJsonConverter;

    move-result-object v5

    const-class v6, Lcom/ss/android/ugc/effectmanager/effect/model/net/EffectCheckUpdateResponse;

    invoke-virtual {v3, v0, v5, v6}, Lcom/ss/android/ugc/effectmanager/network/EffectNetWorkerWrapper;->execute(Lcom/ss/android/ugc/effectmanager/common/EffectRequest;Lcom/ss/android/ugc/effectmanager/common/listener/IJsonConverter;Ljava/lang/Class;)Lcom/ss/android/ugc/effectmanager/common/model/BaseNetResponse;

    move-result-object v0

    check-cast v0, Lcom/ss/android/ugc/effectmanager/effect/model/net/EffectCheckUpdateResponse;

    if-eqz v0, :cond_1

    new-instance v3, Lcom/ss/android/ugc/effectmanager/effect/task/result/EffectCheckUpdateResult;

    invoke-virtual {v0}, Lcom/ss/android/ugc/effectmanager/effect/model/net/EffectCheckUpdateResponse;->isUpdated()Z

    move-result v0

    invoke-direct {v3, v0, v1}, Lcom/ss/android/ugc/effectmanager/effect/task/result/EffectCheckUpdateResult;-><init>(ZLcom/ss/android/ugc/effectmanager/common/task/ExceptionResult;)V

    invoke-virtual {p0, v2, v3}, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->sendMessage(ILcom/ss/android/ugc/effectmanager/common/task/BaseTaskResult;)V

    goto :goto_0

    :cond_1
    new-instance v0, Lcom/ss/android/ugc/effectmanager/effect/task/result/EffectCheckUpdateResult;

    new-instance v1, Lcom/ss/android/ugc/effectmanager/common/task/ExceptionResult;

    const/16 v3, 0x2712

    invoke-direct {v1, v3}, Lcom/ss/android/ugc/effectmanager/common/task/ExceptionResult;-><init>(I)V

    invoke-direct {v0, v4, v1}, Lcom/ss/android/ugc/effectmanager/effect/task/result/EffectCheckUpdateResult;-><init>(ZLcom/ss/android/ugc/effectmanager/common/task/ExceptionResult;)V

    invoke-virtual {p0, v2, v0}, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->sendMessage(ILcom/ss/android/ugc/effectmanager/common/task/BaseTaskResult;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    :goto_0
    goto :goto_1

    :catch_0
    move-exception v0

    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    new-instance v1, Lcom/ss/android/ugc/effectmanager/effect/task/result/EffectCheckUpdateResult;

    new-instance v3, Lcom/ss/android/ugc/effectmanager/common/task/ExceptionResult;

    invoke-direct {v3, v0}, Lcom/ss/android/ugc/effectmanager/common/task/ExceptionResult;-><init>(Ljava/lang/Exception;)V

    invoke-direct {v1, v4, v3}, Lcom/ss/android/ugc/effectmanager/effect/task/result/EffectCheckUpdateResult;-><init>(ZLcom/ss/android/ugc/effectmanager/common/task/ExceptionResult;)V

    invoke-virtual {p0, v2, v1}, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->sendMessage(ILcom/ss/android/ugc/effectmanager/common/task/BaseTaskResult;)V

    :goto_1
    goto :goto_2

    :cond_2
    new-instance v0, Lcom/ss/android/ugc/effectmanager/effect/task/result/EffectCheckUpdateResult;

    const/4 v3, 0x1

    invoke-direct {v0, v3, v1}, Lcom/ss/android/ugc/effectmanager/effect/task/result/EffectCheckUpdateResult;-><init>(ZLcom/ss/android/ugc/effectmanager/common/task/ExceptionResult;)V

    invoke-virtual {p0, v2, v0}, Lcom/ss/android/ugc/effectmanager/effect/task/task/CheckUpdateTask;->sendMessage(ILcom/ss/android/ugc/effectmanager/common/task/BaseTaskResult;)V

    :goto_2
    return-void
.end method
