.class public Lcom/android/camera/CameraBackupAgent;
.super Lmiui/app/backup/FullBackupAgent;
.source "CameraBackupAgent.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "CameraBackupAgent"


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Lmiui/app/backup/FullBackupAgent;-><init>()V

    return-void
.end method


# virtual methods
.method protected onRestoreEnd(Lmiui/app/backup/BackupMeta;)I
    .locals 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    const-string v0, "CameraBackupAgent"

    const-string v1, "onRestoreEnd: update watermark for vendor"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/Util;->generateWatermark2File()Landroid/graphics/Bitmap;

    invoke-super {p0, p1}, Lmiui/app/backup/FullBackupAgent;->onRestoreEnd(Lmiui/app/backup/BackupMeta;)I

    move-result p1

    return p1
.end method
