.class public Lcom/ss/android/ttve/model/VEWaveformVisualizer;
.super Ljava/lang/Object;
.source "VEWaveformVisualizer.java"


# static fields
.field public static Default:I

.field public static MultiChannelMax:I

.field public static MultiChannelMean:I

.field public static SampleMax:I

.field public static SampleMean:I


# direct methods
.method static constructor <clinit>()V
    .locals 2

    const/4 v0, 0x1

    sput v0, Lcom/ss/android/ttve/model/VEWaveformVisualizer;->SampleMean:I

    const/4 v0, 0x0

    sput v0, Lcom/ss/android/ttve/model/VEWaveformVisualizer;->SampleMax:I

    const/4 v0, 0x2

    sput v0, Lcom/ss/android/ttve/model/VEWaveformVisualizer;->MultiChannelMean:I

    const/4 v0, 0x4

    sput v0, Lcom/ss/android/ttve/model/VEWaveformVisualizer;->MultiChannelMax:I

    sget v0, Lcom/ss/android/ttve/model/VEWaveformVisualizer;->MultiChannelMean:I

    sget v1, Lcom/ss/android/ttve/model/VEWaveformVisualizer;->SampleMax:I

    or-int/2addr v0, v1

    sput v0, Lcom/ss/android/ttve/model/VEWaveformVisualizer;->Default:I

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
