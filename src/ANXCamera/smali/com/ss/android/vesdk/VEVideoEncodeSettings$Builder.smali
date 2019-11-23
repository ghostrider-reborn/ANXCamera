.class public Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
.super Ljava/lang/Object;
.source "VEVideoEncodeSettings.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/ss/android/vesdk/VEVideoEncodeSettings;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "Builder"
.end annotation


# instance fields
.field private exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

.field private mUsage:I


# direct methods
.method public constructor <init>(I)V
    .locals 1
    .param p1    # I
        .annotation build Landroid/support/annotation/IntRange;
            from = 0x1L
            to = 0x3L
        .end annotation
    .end param

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput p1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->mUsage:I

    new-instance p1, Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    const/4 v0, 0x0

    invoke-direct {p1, v0}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;-><init>(Lcom/ss/android/vesdk/VEVideoEncodeSettings$1;)V

    iput-object p1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    return-void
.end method

.method public constructor <init>(ILcom/ss/android/vesdk/VEVideoEncodeSettings;)V
    .locals 0
    .param p1    # I
        .annotation build Landroid/support/annotation/IntRange;
            from = 0x1L
            to = 0x3L
        .end annotation
    .end param
    .param p2    # Lcom/ss/android/vesdk/VEVideoEncodeSettings;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput p1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->mUsage:I

    iput-object p2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    return-void
.end method

.method private getCompileHardwareBitrateModeFromCloud()Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;
    .locals 1

    sget-object v0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;->ENCODE_BITRATE_ABR:Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    return-object v0
.end method

.method private getImportHardwareBitrateModeFromCloud()Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;
    .locals 1

    sget-object v0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;->ENCODE_BITRATE_ABR:Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    return-object v0
.end method

.method private getRecordHardwareBitrateModeFromCloud()Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;
    .locals 1

    sget-object v0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;->ENCODE_BITRATE_ABR:Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    return-object v0
.end method

.method private overrideWithCloudConfigForCompile()V
    .locals 5

    sget-object v0, Lcom/ss/android/vesdk/runtime/cloudconfig/PerformanceConfig;->sVECloudConfig:Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;

    iget v1, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mCompileEncodeMode:I

    const/4 v2, 0x1

    if-ne v1, v2, :cond_0

    goto :goto_0

    :cond_0
    const/4 v2, 0x0

    :goto_0
    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v1, v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1202(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Z)Z

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mCompileEncodeSWCRF:I

    invoke-static {v1, v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$502(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mCompileEncodeSWCRFPreset:I

    invoke-static {v1, v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1402(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    if-eqz v2, :cond_1

    invoke-direct {p0}, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->getCompileHardwareBitrateModeFromCloud()Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    move-result-object v3

    goto :goto_1

    :cond_1
    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mCompileSWBitrateMode:I

    invoke-static {v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;->fromInteger(I)Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    move-result-object v3

    :goto_1
    invoke-static {v1, v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$302(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;)Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    if-eqz v2, :cond_2

    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mCompileEncodeHWBPS:I

    goto :goto_2

    :cond_2
    iget-object v3, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$400(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v3

    :goto_2
    invoke-static {v1, v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$402(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    if-eqz v2, :cond_3

    iget-object v3, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1300(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v3

    goto :goto_3

    :cond_3
    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mCompileEncodeSWGOP:I

    :goto_3
    invoke-static {v1, v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1302(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    if-eqz v2, :cond_4

    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mCompileHwProfile:I

    goto :goto_4

    :cond_4
    iget-object v3, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1500(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v3

    :goto_4
    invoke-static {v1, v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1502(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    if-eqz v2, :cond_5

    iget-object v3, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1600(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)J

    move-result-wide v3

    goto :goto_5

    :cond_5
    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mCompileEncodeSWMaxrate:I

    int-to-long v3, v3

    :goto_5
    invoke-static {v1, v3, v4}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1602(Lcom/ss/android/vesdk/VEVideoEncodeSettings;J)J

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    if-eqz v2, :cond_6

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$600(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v0

    goto :goto_6

    :cond_6
    iget v0, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mCompileVideoSWQP:I

    :goto_6
    invoke-static {v1, v0}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$602(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    return-void
.end method

.method private overrideWithCloudConfigForImport()V
    .locals 5

    sget-object v0, Lcom/ss/android/vesdk/runtime/cloudconfig/PerformanceConfig;->sVECloudConfig:Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;

    iget v1, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mImportEncodeMode:I

    const/4 v2, 0x1

    if-ne v1, v2, :cond_0

    goto :goto_0

    :cond_0
    const/4 v2, 0x0

    :goto_0
    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v1, v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1202(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Z)Z

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mImportSWEncodeCRF:I

    invoke-static {v1, v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$502(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mImportVideoSWPreset:I

    invoke-static {v1, v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1402(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    if-eqz v2, :cond_1

    invoke-direct {p0}, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->getImportHardwareBitrateModeFromCloud()Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    move-result-object v3

    goto :goto_1

    :cond_1
    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mImportSWBitrateMode:I

    invoke-static {v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;->fromInteger(I)Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    move-result-object v3

    :goto_1
    invoke-static {v1, v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$302(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;)Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    if-eqz v2, :cond_2

    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mImportHWEncodeBPS:I

    goto :goto_2

    :cond_2
    iget-object v3, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$400(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v3

    :goto_2
    invoke-static {v1, v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$402(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    if-eqz v2, :cond_3

    iget-object v3, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1300(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v3

    goto :goto_3

    :cond_3
    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mImportVideoSWGop:I

    :goto_3
    invoke-static {v1, v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1302(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    if-eqz v2, :cond_4

    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mImportHwProfile:I

    goto :goto_4

    :cond_4
    iget-object v3, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1500(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v3

    :goto_4
    invoke-static {v1, v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1502(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    if-eqz v2, :cond_5

    iget-object v3, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1600(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)J

    move-result-wide v3

    goto :goto_5

    :cond_5
    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mImportVideoSWMaxrate:I

    int-to-long v3, v3

    :goto_5
    invoke-static {v1, v3, v4}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1602(Lcom/ss/android/vesdk/VEVideoEncodeSettings;J)J

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    if-eqz v2, :cond_6

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$600(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v0

    goto :goto_6

    :cond_6
    iget v0, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mImportVideoSWQP:I

    :goto_6
    invoke-static {v1, v0}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$602(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    return-void
.end method

.method private overrideWithCloudConfigForRecord()V
    .locals 6

    sget-object v0, Lcom/ss/android/vesdk/runtime/cloudconfig/PerformanceConfig;->sVECloudConfig:Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;

    iget v1, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mRecordEncodeMode:I

    const/4 v2, 0x1

    if-ne v1, v2, :cond_0

    goto :goto_0

    :cond_0
    const/4 v2, 0x0

    :goto_0
    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v1, v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1202(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Z)Z

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    new-instance v3, Lcom/ss/android/vesdk/VESize;

    iget v4, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mRecordResolutionWidth:I

    iget v5, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mRecordResolutionHeight:I

    invoke-direct {v3, v4, v5}, Lcom/ss/android/vesdk/VESize;-><init>(II)V

    invoke-static {v1, v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$702(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Lcom/ss/android/vesdk/VESize;)Lcom/ss/android/vesdk/VESize;

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mRecordSWEncodeCRF:I

    invoke-static {v1, v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$502(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mRecordVideoSWPreset:I

    invoke-static {v1, v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1402(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    if-eqz v2, :cond_1

    invoke-direct {p0}, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->getRecordHardwareBitrateModeFromCloud()Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    move-result-object v3

    goto :goto_1

    :cond_1
    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mRecordSWBitrateMode:I

    invoke-static {v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;->fromInteger(I)Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    move-result-object v3

    :goto_1
    invoke-static {v1, v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$302(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;)Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    if-eqz v2, :cond_2

    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mRecordHWEncodeBPS:I

    goto :goto_2

    :cond_2
    iget-object v3, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$400(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v3

    :goto_2
    invoke-static {v1, v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$402(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    if-eqz v2, :cond_3

    iget-object v3, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1300(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v3

    goto :goto_3

    :cond_3
    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mRecordVideoSWGop:I

    :goto_3
    invoke-static {v1, v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1302(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    if-eqz v2, :cond_4

    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mRecordHwProfile:I

    goto :goto_4

    :cond_4
    iget-object v3, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1500(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v3

    :goto_4
    invoke-static {v1, v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1502(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    if-eqz v2, :cond_5

    iget-object v3, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v3}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1600(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)J

    move-result-wide v3

    goto :goto_5

    :cond_5
    iget v3, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mRecordVideoSWMaxrate:I

    int-to-long v3, v3

    :goto_5
    invoke-static {v1, v3, v4}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1602(Lcom/ss/android/vesdk/VEVideoEncodeSettings;J)J

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    if-eqz v2, :cond_6

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$600(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v0

    goto :goto_6

    :cond_6
    iget v0, v0, Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;->mRecordVideoSWQP:I

    :goto_6
    invoke-static {v1, v0}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$602(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    return-void
.end method

.method private overrideWithUserConfig()V
    .locals 4

    new-instance v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;

    invoke-direct {v0}, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;-><init>()V

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1200(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)Z

    move-result v1

    iput-boolean v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->useHWEncoder:Z

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1200(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)Z

    move-result v1

    if-eqz v1, :cond_0

    iget-object v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mHWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoHWEncodeSettings;

    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$400(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v2

    int-to-long v2, v2

    iput-wide v2, v1, Lcom/ss/android/vesdk/settings/VEVideoHWEncodeSettings;->mBitrate:J

    iget-object v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mHWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoHWEncodeSettings;

    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1500(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v2

    iput v2, v1, Lcom/ss/android/vesdk/settings/VEVideoHWEncodeSettings;->mProfile:I

    iget-object v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mHWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoHWEncodeSettings;

    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1300(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v2

    iput v2, v1, Lcom/ss/android/vesdk/settings/VEVideoHWEncodeSettings;->mGop:I

    goto :goto_0

    :cond_0
    iget-object v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mSWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;

    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$300(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    move-result-object v2

    invoke-virtual {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;->ordinal()I

    move-result v2

    iput v2, v1, Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;->mBitrateMode:I

    iget-object v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mSWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;

    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$400(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v2

    iput v2, v1, Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;->mBps:I

    iget-object v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mSWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;

    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$500(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v2

    iput v2, v1, Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;->mCrf:I

    iget-object v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mSWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;

    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1600(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)J

    move-result-wide v2

    iput-wide v2, v1, Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;->mMaxRate:J

    iget-object v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mSWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;

    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1400(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v2

    iput v2, v1, Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;->mPreset:I

    iget-object v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mSWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;

    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1500(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v2

    iput v2, v1, Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;->mProfile:I

    iget-object v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mSWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;

    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1300(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v2

    iput v2, v1, Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;->mGop:I

    :goto_0
    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v1, v0}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$2202(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;)Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v1, v0}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$2302(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;)Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;

    return-void
.end method

.method private parseExternalSettingsJsonStr(Ljava/lang/String;)V
    .locals 3

    :try_start_0
    new-instance v0, Lorg/json/JSONObject;

    invoke-direct {v0, p1}, Lorg/json/JSONObject;-><init>(Ljava/lang/String;)V

    const-string p1, "compile"

    invoke-virtual {v0, p1}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object p1

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-direct {p0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->parseJsonToSetting(Lorg/json/JSONObject;)Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;

    move-result-object p1

    invoke-static {v1, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$2202(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;)Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;

    const-string/jumbo p1, "watermark_compile"

    invoke-virtual {v0, p1}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object p1

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-direct {p0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->parseJsonToSetting(Lorg/json/JSONObject;)Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;

    move-result-object p1

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$2302(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;)Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;
    :try_end_0
    .catch Lorg/json/JSONException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p1

    invoke-virtual {p1}, Lorg/json/JSONException;->printStackTrace()V

    const-string v0, "VEVideoEncodeSettings"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "external json str parse error : "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Lorg/json/JSONException;->getLocalizedMessage()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    :goto_0
    return-void
.end method

.method private parseJsonToSetting(Lorg/json/JSONObject;)Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;
    .locals 7

    new-instance v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;

    invoke-direct {v0}, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;-><init>()V

    :try_start_0
    const-string v1, "encode_mode"

    invoke-virtual {p1, v1}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    const-string/jumbo v2, "unknown"

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    const/4 v3, 0x2

    if-eqz v2, :cond_0

    iget v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->mUsage:I

    if-ne v2, v3, :cond_0

    iget-object v1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1200(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)Z

    move-result v1

    iput-boolean v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->useHWEncoder:Z

    goto :goto_0

    :cond_0
    const-string v2, "hw"

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    iput-boolean v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->useHWEncoder:Z

    :goto_0
    iget-object v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mHWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoHWEncodeSettings;

    iget v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->mUsage:I

    const/4 v4, -0x1

    if-ne v2, v3, :cond_2

    const-string v2, "hw"

    invoke-virtual {p1, v2}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object v2

    const-string v5, "bitrate"

    invoke-virtual {v2, v5}, Lorg/json/JSONObject;->getInt(Ljava/lang/String;)I

    move-result v2

    if-ne v2, v4, :cond_2

    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1200(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)Z

    move-result v2

    if-eqz v2, :cond_1

    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$400(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v2

    int-to-long v5, v2

    goto :goto_1

    :cond_1
    iget-object v2, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mHWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoHWEncodeSettings;

    iget-wide v5, v2, Lcom/ss/android/vesdk/settings/VEVideoHWEncodeSettings;->mBitrate:J

    goto :goto_1

    :cond_2
    const-string v2, "hw"

    invoke-virtual {p1, v2}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object v2

    const-string v5, "bitrate"

    invoke-virtual {v2, v5}, Lorg/json/JSONObject;->getInt(Ljava/lang/String;)I

    move-result v2

    int-to-long v5, v2

    :goto_1
    iput-wide v5, v1, Lcom/ss/android/vesdk/settings/VEVideoHWEncodeSettings;->mBitrate:J

    iget-object v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mHWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoHWEncodeSettings;

    iget v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->mUsage:I

    if-ne v2, v3, :cond_4

    const-string/jumbo v2, "unknown"

    const-string v5, "hw"

    invoke-virtual {p1, v5}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object v5

    const-string v6, "profile"

    invoke-virtual {v5, v6}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v2, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_4

    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1200(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)Z

    move-result v2

    if-eqz v2, :cond_3

    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1500(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v2

    goto :goto_2

    :cond_3
    iget-object v2, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mHWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoHWEncodeSettings;

    iget v2, v2, Lcom/ss/android/vesdk/settings/VEVideoHWEncodeSettings;->mProfile:I

    goto :goto_2

    :cond_4
    const-string v2, "hw"

    invoke-virtual {p1, v2}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object v2

    const-string v5, "profile"

    invoke-virtual {v2, v5}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    invoke-static {v2}, Lcom/ss/android/vesdk/settings/VEVideoEncodeProfile;->valueOfString(Ljava/lang/String;)Lcom/ss/android/vesdk/settings/VEVideoEncodeProfile;

    move-result-object v2

    invoke-virtual {v2}, Lcom/ss/android/vesdk/settings/VEVideoEncodeProfile;->ordinal()I

    move-result v2

    :goto_2
    iput v2, v1, Lcom/ss/android/vesdk/settings/VEVideoHWEncodeSettings;->mProfile:I

    iget-object v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mHWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoHWEncodeSettings;

    iget v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->mUsage:I

    if-ne v2, v3, :cond_6

    const-string v2, "hw"

    invoke-virtual {p1, v2}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object v2

    const-string v5, "gop"

    invoke-virtual {v2, v5}, Lorg/json/JSONObject;->getInt(Ljava/lang/String;)I

    move-result v2

    if-ne v2, v4, :cond_6

    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1200(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)Z

    move-result v2

    if-eqz v2, :cond_5

    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1300(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v2

    goto :goto_3

    :cond_5
    iget-object v2, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mHWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoHWEncodeSettings;

    iget v2, v2, Lcom/ss/android/vesdk/settings/VEVideoHWEncodeSettings;->mGop:I

    goto :goto_3

    :cond_6
    const-string v2, "hw"

    invoke-virtual {p1, v2}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object v2

    const-string v5, "gop"

    invoke-virtual {v2, v5}, Lorg/json/JSONObject;->getInt(Ljava/lang/String;)I

    move-result v2

    :goto_3
    iput v2, v1, Lcom/ss/android/vesdk/settings/VEVideoHWEncodeSettings;->mGop:I

    iget-object v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mSWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;

    sget-object v2, Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;->ENCODE_BITRATE_CRF:Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    invoke-virtual {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;->ordinal()I

    move-result v2

    iput v2, v1, Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;->mBitrateMode:I

    iget-object v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mSWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;

    iget v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->mUsage:I

    if-ne v2, v3, :cond_8

    const-string/jumbo v2, "sw"

    invoke-virtual {p1, v2}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object v2

    const-string v5, "crf"

    invoke-virtual {v2, v5}, Lorg/json/JSONObject;->getInt(Ljava/lang/String;)I

    move-result v2

    if-ne v2, v4, :cond_8

    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1200(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)Z

    move-result v2

    if-eqz v2, :cond_7

    iget-object v2, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mSWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;

    iget v2, v2, Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;->mCrf:I

    goto :goto_4

    :cond_7
    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$500(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v2

    goto :goto_4

    :cond_8
    const-string/jumbo v2, "sw"

    invoke-virtual {p1, v2}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object v2

    const-string v5, "crf"

    invoke-virtual {v2, v5}, Lorg/json/JSONObject;->getInt(Ljava/lang/String;)I

    move-result v2

    :goto_4
    iput v2, v1, Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;->mCrf:I

    iget-object v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mSWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;

    iget v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->mUsage:I

    if-ne v2, v3, :cond_a

    const-string/jumbo v2, "sw"

    invoke-virtual {p1, v2}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object v2

    const-string v5, "maxrate"

    invoke-virtual {v2, v5}, Lorg/json/JSONObject;->getInt(Ljava/lang/String;)I

    move-result v2

    if-ne v2, v4, :cond_a

    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1200(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)Z

    move-result v2

    if-eqz v2, :cond_9

    iget-object v2, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mSWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;

    iget-wide v5, v2, Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;->mMaxRate:J

    goto :goto_5

    :cond_9
    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1600(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)J

    move-result-wide v5

    goto :goto_5

    :cond_a
    const-string/jumbo v2, "sw"

    invoke-virtual {p1, v2}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object v2

    const-string v5, "maxrate"

    invoke-virtual {v2, v5}, Lorg/json/JSONObject;->getInt(Ljava/lang/String;)I

    move-result v2

    int-to-long v5, v2

    :goto_5
    iput-wide v5, v1, Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;->mMaxRate:J

    iget-object v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mSWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;

    iget v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->mUsage:I

    if-ne v2, v3, :cond_c

    const-string/jumbo v2, "sw"

    invoke-virtual {p1, v2}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object v2

    const-string v5, "preset"

    invoke-virtual {v2, v5}, Lorg/json/JSONObject;->getInt(Ljava/lang/String;)I

    move-result v2

    if-ne v2, v4, :cond_c

    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1200(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)Z

    move-result v2

    if-eqz v2, :cond_b

    iget-object v2, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mSWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;

    iget v2, v2, Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;->mPreset:I

    goto :goto_6

    :cond_b
    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1400(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v2

    goto :goto_6

    :cond_c
    const-string/jumbo v2, "sw"

    invoke-virtual {p1, v2}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object v2

    const-string v5, "preset"

    invoke-virtual {v2, v5}, Lorg/json/JSONObject;->getInt(Ljava/lang/String;)I

    move-result v2

    :goto_6
    iput v2, v1, Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;->mPreset:I

    iget-object v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mSWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;

    iget v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->mUsage:I

    if-ne v2, v3, :cond_e

    const-string/jumbo v2, "unknown"

    const-string/jumbo v5, "sw"

    invoke-virtual {p1, v5}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object v5

    const-string v6, "profile"

    invoke-virtual {v5, v6}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v2, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_e

    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1200(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)Z

    move-result v2

    if-eqz v2, :cond_d

    iget-object v2, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mSWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;

    iget v2, v2, Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;->mProfile:I

    goto :goto_7

    :cond_d
    iget-object v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1500(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result v2

    goto :goto_7

    :cond_e
    const-string/jumbo v2, "sw"

    invoke-virtual {p1, v2}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object v2

    const-string v5, "profile"

    invoke-virtual {v2, v5}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    invoke-static {v2}, Lcom/ss/android/vesdk/settings/VEVideoEncodeProfile;->valueOfString(Ljava/lang/String;)Lcom/ss/android/vesdk/settings/VEVideoEncodeProfile;

    move-result-object v2

    invoke-virtual {v2}, Lcom/ss/android/vesdk/settings/VEVideoEncodeProfile;->ordinal()I

    move-result v2

    :goto_7
    iput v2, v1, Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;->mProfile:I

    iget-object v1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mSWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;

    iget v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->mUsage:I

    if-ne v2, v3, :cond_10

    const-string/jumbo v2, "sw"

    invoke-virtual {p1, v2}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object v2

    const-string v3, "gop"

    invoke-virtual {v2, v3}, Lorg/json/JSONObject;->getInt(Ljava/lang/String;)I

    move-result v2

    if-ne v2, v4, :cond_10

    iget-object p1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1200(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)Z

    move-result p1

    if-eqz p1, :cond_f

    iget-object p1, v0, Lcom/ss/android/vesdk/settings/VEVideoCompileEncodeSettings;->mSWEncodeSetting:Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;

    iget p1, p1, Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;->mGop:I

    goto :goto_8

    :cond_f
    iget-object p1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1300(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)I

    move-result p1

    goto :goto_8

    :cond_10
    const-string/jumbo v2, "sw"

    invoke-virtual {p1, v2}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object p1

    const-string v2, "gop"

    invoke-virtual {p1, v2}, Lorg/json/JSONObject;->getInt(Ljava/lang/String;)I

    move-result p1

    :goto_8
    iput p1, v1, Lcom/ss/android/vesdk/settings/VEVideoSWEncodeSettings;->mGop:I
    :try_end_0
    .catch Lorg/json/JSONException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_9

    :catch_0
    move-exception p1

    invoke-virtual {p1}, Lorg/json/JSONException;->printStackTrace()V

    const-string v1, "VEVideoEncodeSettings"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "parseJsonToSetting : external json str parse error : "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Lorg/json/JSONException;->getLocalizedMessage()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v1, p1}, Lcom/ss/android/vesdk/VELogUtil;->e(Ljava/lang/String;Ljava/lang/String;)V

    :goto_9
    return-object v0
.end method


# virtual methods
.method public build()Lcom/ss/android/vesdk/VEVideoEncodeSettings;
    .locals 1

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$2100(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$2100(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->parseExternalSettingsJsonStr(Ljava/lang/String;)V

    goto :goto_0

    :cond_0
    invoke-direct {p0}, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->overrideWithUserConfig()V

    :goto_0
    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    return-object v0
.end method

.method public overrideWithCloudConfig()Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 3

    invoke-static {}, Lcom/ss/android/vesdk/runtime/VERuntime;->getInstance()Lcom/ss/android/vesdk/runtime/VERuntime;

    move-result-object v0

    invoke-virtual {v0}, Lcom/ss/android/vesdk/runtime/VERuntime;->isUseCloudConfig()Z

    move-result v0

    if-nez v0, :cond_0

    const-string v0, "VEVideoEncodeSettings"

    const-string v1, "UseCloudConfig is disabled. VEVideoEncodeSettings.overrideWithCloudConfig will do nothing!"

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-object p0

    :cond_0
    sget-object v0, Lcom/ss/android/vesdk/runtime/cloudconfig/PerformanceConfig;->sVECloudConfig:Lcom/ss/android/vesdk/runtime/cloudconfig/VECloudConfig;

    if-nez v0, :cond_1

    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Class;->getSimpleName()Ljava/lang/String;

    move-result-object v0

    const-string v1, "Override with Cloud Configs failed. CloudConfig == null"

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-object p0

    :cond_1
    iget v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->mUsage:I

    packed-switch v0, :pswitch_data_0

    new-instance v0, Ljava/lang/IllegalStateException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "CompileTime BUG, Unexpected usage = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->mUsage:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v0

    :pswitch_0
    invoke-direct {p0}, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->overrideWithCloudConfigForImport()V

    goto :goto_0

    :pswitch_1
    invoke-direct {p0}, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->overrideWithCloudConfigForCompile()V

    goto :goto_0

    :pswitch_2
    invoke-direct {p0}, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->overrideWithCloudConfigForRecord()V

    nop

    :goto_0
    return-object p0

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public setBps(I)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 2

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    sget-object v1, Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;->ENCODE_BITRATE_ABR:Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$302(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;)Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$402(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    return-object p0
.end method

.method public setCompileType(Lcom/ss/android/vesdk/VEVideoEncodeSettings$COMPILE_TYPE;)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 1
    .param p1    # Lcom/ss/android/vesdk/VEVideoEncodeSettings$COMPILE_TYPE;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    iput-object p1, v0, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->compileType:Lcom/ss/android/vesdk/VEVideoEncodeSettings$COMPILE_TYPE;

    return-object p0
.end method

.method public setEnableRemuxVideo(Z)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 1

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$102(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Z)Z

    return-object p0
.end method

.method public setEncodePreset(Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_PRESET;)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 1
    .param p1    # Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_PRESET;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-virtual {p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_PRESET;->ordinal()I

    move-result p1

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1402(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    return-object p0
.end method

.method public setEncodeProfile(Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_PROFILE;)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 1
    .param p1    # Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_PROFILE;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-virtual {p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_PROFILE;->ordinal()I

    move-result p1

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1502(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    return-object p0
.end method

.method public setEncodeStandard(Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_STANDARD;)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 1

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-virtual {p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_STANDARD;->ordinal()I

    move-result p1

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1802(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    return-object p0
.end method

.method public setExternalSettings(Ljava/lang/String;)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 1

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$2102(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Ljava/lang/String;)Ljava/lang/String;

    return-object p0
.end method

.method public setFps(I)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 1

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$202(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    return-object p0
.end method

.method public setGopSize(I)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 1

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1302(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    return-object p0
.end method

.method public setHasBFrame(Z)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 1

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1902(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Z)Z

    return-object p0
.end method

.method public setHwEnc(Z)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 1

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1202(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Z)Z

    return-object p0
.end method

.method public setQP(I)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 2

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    sget-object v1, Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;->ENCODE_BITRATE_QP:Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$302(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;)Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$602(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    return-object p0
.end method

.method public setResizeMode(I)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 1

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$902(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    return-object p0
.end method

.method public setResizeX(F)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 1

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1002(Lcom/ss/android/vesdk/VEVideoEncodeSettings;F)F

    return-object p0
.end method

.method public setResizeY(F)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 1

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1102(Lcom/ss/android/vesdk/VEVideoEncodeSettings;F)F

    return-object p0
.end method

.method public setRotate(I)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 1

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$802(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    return-object p0
.end method

.method public setSWCRF(I)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 2

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    sget-object v1, Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;->ENCODE_BITRATE_CRF:Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    invoke-static {v0, v1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$302(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;)Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$502(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    return-object p0
.end method

.method public setSpeed(F)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 1

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1702(Lcom/ss/android/vesdk/VEVideoEncodeSettings;F)F

    return-object p0
.end method

.method public setSwMaxrate(J)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 1

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0, p1, p2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$1602(Lcom/ss/android/vesdk/VEVideoEncodeSettings;J)J

    return-object p0
.end method

.method public setVideoBitrate(Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;I)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 2

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$302(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;)Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    sget-object v0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$2;->$SwitchMap$com$ss$android$vesdk$VEVideoEncodeSettings$ENCODE_BITRATE_MODE:[I

    invoke-virtual {p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;->ordinal()I

    move-result v1

    aget v0, v0, v1

    packed-switch v0, :pswitch_data_0

    new-instance p2, Ljava/lang/IllegalStateException;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "CompileTime BUG. Unhandled enum value "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-direct {p2, p1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw p2

    :pswitch_0
    iget-object p1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {p1, p2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$402(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    goto :goto_0

    :pswitch_1
    iget-object p1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {p1, p2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$602(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    goto :goto_0

    :pswitch_2
    iget-object p1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {p1, p2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$502(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    goto :goto_0

    :pswitch_3
    iget-object p1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {p1, p2}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$402(Lcom/ss/android/vesdk/VEVideoEncodeSettings;I)I

    nop

    :goto_0
    return-object p0

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public setVideoBitrateMode(Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 1

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$302(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;)Lcom/ss/android/vesdk/VEVideoEncodeSettings$ENCODE_BITRATE_MODE;

    return-object p0
.end method

.method public setVideoRes(II)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 1

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$700(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)Lcom/ss/android/vesdk/VESize;

    move-result-object v0

    iput p1, v0, Lcom/ss/android/vesdk/VESize;->width:I

    iget-object p1, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$700(Lcom/ss/android/vesdk/VEVideoEncodeSettings;)Lcom/ss/android/vesdk/VESize;

    move-result-object p1

    iput p2, p1, Lcom/ss/android/vesdk/VESize;->height:I

    return-object p0
.end method

.method public setWatermarkParam(Lcom/ss/android/vesdk/VEWatermarkParam;)Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;
    .locals 1

    iget-object v0, p0, Lcom/ss/android/vesdk/VEVideoEncodeSettings$Builder;->exportVideoEncodeSettings:Lcom/ss/android/vesdk/VEVideoEncodeSettings;

    invoke-static {v0, p1}, Lcom/ss/android/vesdk/VEVideoEncodeSettings;->access$2002(Lcom/ss/android/vesdk/VEVideoEncodeSettings;Lcom/ss/android/vesdk/VEWatermarkParam;)Lcom/ss/android/vesdk/VEWatermarkParam;

    return-object p0
.end method
