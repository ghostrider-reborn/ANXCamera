.class public Lcom/ss/android/vesdk/VEBeautyParam;
.super Ljava/lang/Object;
.source "VEBeautyParam.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/ss/android/vesdk/VEBeautyParam$VEBeautyType;
    }
.end annotation


# static fields
.field public static final BEAUTY_BLUSHER:I = 0x12

.field public static final BEAUTY_LIPSTICK:I = 0x11

.field public static final BEAUTY_NASOLABIAL:I = 0x13

.field public static final BEAUTY_POUCH:I = 0x14

.field public static final BEAUTY_RESHAPE_CHEEK:I = 0x5

.field public static final BEAUTY_RESHAPE_EYE:I = 0x4

.field public static final BEAUTY_SHARP:I = 0x9

.field public static final BEAUTY_SMOOTH:I = 0x2

.field public static final BEAUTY_WHITE:I = 0x1


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static describle(I)Ljava/lang/String;
    .locals 0

    sparse-switch p0, :sswitch_data_0

    const-string p0, ""

    return-object p0

    :sswitch_0
    const-string p0, "beauty_pouch"

    return-object p0

    :sswitch_1
    const-string p0, "beauty_nasolabial"

    return-object p0

    :sswitch_2
    const-string p0, "beauty_blusher"

    return-object p0

    :sswitch_3
    const-string p0, "beauty_lipstick"

    return-object p0

    :sswitch_4
    const-string/jumbo p0, "sharp"

    return-object p0

    :sswitch_5
    const-string p0, "reshape cheek"

    return-object p0

    :sswitch_6
    const-string p0, "reshape_eye"

    return-object p0

    :sswitch_7
    const-string p0, "beauty_smooth"

    return-object p0

    :sswitch_8
    const-string p0, "beauty_white"

    return-object p0

    nop

    nop

    :sswitch_data_0
    .sparse-switch
        0x1 -> :sswitch_8
        0x2 -> :sswitch_7
        0x4 -> :sswitch_6
        0x5 -> :sswitch_5
        0x9 -> :sswitch_4
        0x11 -> :sswitch_3
        0x12 -> :sswitch_2
        0x13 -> :sswitch_1
        0x14 -> :sswitch_0
    .end sparse-switch
.end method
