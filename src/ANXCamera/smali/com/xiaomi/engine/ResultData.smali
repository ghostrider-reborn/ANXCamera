.class public Lcom/xiaomi/engine/ResultData;
.super Ljava/lang/Object;
.source "ResultData.java"


# static fields
.field private static final TAG:Ljava/lang/String;


# instance fields
.field private mFlawResult:I

.field private mResultId:I


# direct methods
.method static constructor <clinit>()V
    .locals 1

    const-class v0, Lcom/xiaomi/engine/ResultData;

    invoke-virtual {v0}, Ljava/lang/Class;->getSimpleName()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/xiaomi/engine/ResultData;->TAG:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public getFlawResult()I
    .locals 1

    iget v0, p0, Lcom/xiaomi/engine/ResultData;->mFlawResult:I

    return v0
.end method

.method public getResultId()I
    .locals 1

    iget v0, p0, Lcom/xiaomi/engine/ResultData;->mResultId:I

    return v0
.end method

.method public setFlawResult(I)V
    .locals 0

    iput p1, p0, Lcom/xiaomi/engine/ResultData;->mFlawResult:I

    return-void
.end method

.method public setResultId(I)V
    .locals 0

    iput p1, p0, Lcom/xiaomi/engine/ResultData;->mResultId:I

    return-void
.end method

.method public toString()Ljava/lang/String;
    .locals 2

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "ResultData{ mResultId="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lcom/xiaomi/engine/ResultData;->mResultId:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, ", mFlawResult="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lcom/xiaomi/engine/ResultData;->mFlawResult:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const/16 v1, 0x7d

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method
