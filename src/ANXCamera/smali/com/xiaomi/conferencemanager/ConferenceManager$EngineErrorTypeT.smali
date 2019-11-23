.class public final enum Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;
.super Ljava/lang/Enum;
.source "ConferenceManager.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/xiaomi/conferencemanager/ConferenceManager;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "EngineErrorTypeT"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

.field public static final enum ENGINE_CAMERA_CHANGE_MODE_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

.field public static final enum ENGINE_CONFERENCE_PARAM_INVALID:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

.field public static final enum ENGINE_CONNECTION_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

.field public static final enum ENGINE_CONNECTION_LOST:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

.field public static final enum ENGINE_CONSTRUT_CLIENT_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

.field public static final enum ENGINE_FAILED_GET_VOICEPRO_STATE:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

.field public static final enum ENGINE_NO_CAMERA:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

.field public static final enum ENGINE_NO_ERROR:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

.field public static final enum ENGINE_PEER_BUSY:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

.field public static final enum ENGINE_PEER_ENDED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

.field public static final enum ENGINE_PEER_REJECTED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

.field public static final enum ENGINE_RUN_CRASHED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

.field public static final enum ENGINE_SETUP_CAMERA_ERROR:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

.field public static final enum ENGINE_SET_CAMERA_FRAMERATE_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

.field public static final enum ENGINE_SHARE_DESKTOP_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

.field public static final enum ENGINE_SHARE_WINDOW_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

.field public static final enum ENGINE_START_CAMERA_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

.field public static final enum ENGINE_START_MIC_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

.field public static final enum ENGINE_START_SPEAKER_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

.field public static final enum ENGINE_UNKNOWN_ERROR:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;


# direct methods
.method static constructor <clinit>()V
    .locals 17

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const-string v1, "ENGINE_NO_ERROR"

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_NO_ERROR:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const-string v1, "ENGINE_CONNECTION_FAILED"

    const/4 v3, 0x1

    invoke-direct {v0, v1, v3}, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_CONNECTION_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const-string v1, "ENGINE_CONNECTION_LOST"

    const/4 v4, 0x2

    invoke-direct {v0, v1, v4}, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_CONNECTION_LOST:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const-string v1, "ENGINE_PEER_BUSY"

    const/4 v5, 0x3

    invoke-direct {v0, v1, v5}, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_PEER_BUSY:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const-string v1, "ENGINE_PEER_REJECTED"

    const/4 v6, 0x4

    invoke-direct {v0, v1, v6}, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_PEER_REJECTED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const-string v1, "ENGINE_PEER_ENDED"

    const/4 v7, 0x5

    invoke-direct {v0, v1, v7}, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_PEER_ENDED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const-string v1, "ENGINE_CONFERENCE_PARAM_INVALID"

    const/4 v8, 0x6

    invoke-direct {v0, v1, v8}, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_CONFERENCE_PARAM_INVALID:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const-string v1, "ENGINE_START_CAMERA_FAILED"

    const/4 v9, 0x7

    invoke-direct {v0, v1, v9}, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_START_CAMERA_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const-string v1, "ENGINE_CAMERA_CHANGE_MODE_FAILED"

    const/16 v10, 0x8

    invoke-direct {v0, v1, v10}, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_CAMERA_CHANGE_MODE_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const-string v1, "ENGINE_SET_CAMERA_FRAMERATE_FAILED"

    const/16 v11, 0x9

    invoke-direct {v0, v1, v11}, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_SET_CAMERA_FRAMERATE_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const-string v1, "ENGINE_NO_CAMERA"

    const/16 v12, 0xa

    invoke-direct {v0, v1, v12}, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_NO_CAMERA:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const-string v1, "ENGINE_SETUP_CAMERA_ERROR"

    const/16 v13, 0xb

    invoke-direct {v0, v1, v13}, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_SETUP_CAMERA_ERROR:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const-string v1, "ENGINE_FAILED_GET_VOICEPRO_STATE"

    const/16 v14, 0xc

    invoke-direct {v0, v1, v14}, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_FAILED_GET_VOICEPRO_STATE:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const-string v1, "ENGINE_START_MIC_FAILED"

    const/16 v15, 0xd

    invoke-direct {v0, v1, v15}, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_START_MIC_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const-string v1, "ENGINE_START_SPEAKER_FAILED"

    const/16 v15, 0xe

    invoke-direct {v0, v1, v15}, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_START_SPEAKER_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const-string v1, "ENGINE_SHARE_WINDOW_FAILED"

    const/16 v15, 0xf

    invoke-direct {v0, v1, v15}, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_SHARE_WINDOW_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const-string v1, "ENGINE_SHARE_DESKTOP_FAILED"

    const/16 v15, 0x10

    invoke-direct {v0, v1, v15}, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_SHARE_DESKTOP_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const-string v1, "ENGINE_CONSTRUT_CLIENT_FAILED"

    const/16 v15, 0x11

    invoke-direct {v0, v1, v15}, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_CONSTRUT_CLIENT_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const-string v1, "ENGINE_RUN_CRASHED"

    const/16 v15, 0x12

    invoke-direct {v0, v1, v15}, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_RUN_CRASHED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const-string v1, "ENGINE_UNKNOWN_ERROR"

    const/16 v15, 0x13

    invoke-direct {v0, v1, v15}, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_UNKNOWN_ERROR:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const/16 v0, 0x14

    new-array v0, v0, [Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_NO_ERROR:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    aput-object v1, v0, v2

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_CONNECTION_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    aput-object v1, v0, v3

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_CONNECTION_LOST:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    aput-object v1, v0, v4

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_PEER_BUSY:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    aput-object v1, v0, v5

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_PEER_REJECTED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    aput-object v1, v0, v6

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_PEER_ENDED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    aput-object v1, v0, v7

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_CONFERENCE_PARAM_INVALID:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    aput-object v1, v0, v8

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_START_CAMERA_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    aput-object v1, v0, v9

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_CAMERA_CHANGE_MODE_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    aput-object v1, v0, v10

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_SET_CAMERA_FRAMERATE_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    aput-object v1, v0, v11

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_NO_CAMERA:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    aput-object v1, v0, v12

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_SETUP_CAMERA_ERROR:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    aput-object v1, v0, v13

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_FAILED_GET_VOICEPRO_STATE:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    aput-object v1, v0, v14

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_START_MIC_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const/16 v2, 0xd

    aput-object v1, v0, v2

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_START_SPEAKER_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const/16 v2, 0xe

    aput-object v1, v0, v2

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_SHARE_WINDOW_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const/16 v2, 0xf

    aput-object v1, v0, v2

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_SHARE_DESKTOP_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const/16 v2, 0x10

    aput-object v1, v0, v2

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_CONSTRUT_CLIENT_FAILED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const/16 v2, 0x11

    aput-object v1, v0, v2

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_RUN_CRASHED:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const/16 v2, 0x12

    aput-object v1, v0, v2

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->ENGINE_UNKNOWN_ERROR:Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    const/16 v2, 0x13

    aput-object v1, v0, v2

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->$VALUES:[Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;
    .locals 1

    const-class v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    return-object p0
.end method

.method public static values()[Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;
    .locals 1

    sget-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->$VALUES:[Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    invoke-virtual {v0}, [Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/xiaomi/conferencemanager/ConferenceManager$EngineErrorTypeT;

    return-object v0
.end method
