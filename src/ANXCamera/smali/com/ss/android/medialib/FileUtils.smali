.class public Lcom/ss/android/medialib/FileUtils;
.super Ljava/lang/Object;
.source "FileUtils.java"


# static fields
.field private static final DEFAULT_FOLDER_NAME:Ljava/lang/String; = "BDMedia"

.field protected static msFolderPath:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static checkFileExists(Ljava/lang/String;)Z
    .locals 1

    invoke-static {p0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    new-instance v0, Ljava/io/File;

    invoke-direct {v0, p0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result p0

    return p0
.end method

.method public static createFile(Ljava/lang/String;Z)Ljava/io/File;
    .locals 1

    invoke-static {p0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_3

    new-instance v0, Ljava/io/File;

    invoke-direct {v0, p0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result p0

    if-nez p0, :cond_2

    if-nez p1, :cond_0

    invoke-virtual {v0}, Ljava/io/File;->mkdirs()Z

    goto :goto_0

    :cond_0
    :try_start_0
    invoke-virtual {v0}, Ljava/io/File;->getParentFile()Ljava/io/File;

    move-result-object p0

    invoke-virtual {p0}, Ljava/io/File;->exists()Z

    move-result p1

    if-nez p1, :cond_1

    invoke-virtual {p0}, Ljava/io/File;->mkdirs()Z

    :cond_1
    invoke-virtual {v0}, Ljava/io/File;->createNewFile()Z
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p0

    invoke-virtual {p0}, Ljava/io/IOException;->printStackTrace()V

    :cond_2
    :goto_0
    return-object v0

    :cond_3
    const/4 p0, 0x0

    return-object p0
.end method

.method public static fileChannelCopy(Ljava/lang/String;Ljava/lang/String;)Z
    .locals 11

    invoke-static {}, Lcom/ss/android/medialib/FileUtils;->isSdcardWritable()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    nop

    nop

    nop

    nop

    const/4 v0, 0x0

    :try_start_0
    new-instance v2, Ljava/io/FileInputStream;

    invoke-direct {v2, p0}, Ljava/io/FileInputStream;-><init>(Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/io/FileNotFoundException; {:try_start_0 .. :try_end_0} :catch_11
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_c
    .catchall {:try_start_0 .. :try_end_0} :catchall_4

    :try_start_1
    new-instance p0, Ljava/io/FileOutputStream;

    invoke-direct {p0, p1}, Ljava/io/FileOutputStream;-><init>(Ljava/lang/String;)V
    :try_end_1
    .catch Ljava/io/FileNotFoundException; {:try_start_1 .. :try_end_1} :catch_b
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_1} :catch_a
    .catchall {:try_start_1 .. :try_end_1} :catchall_3

    :try_start_2
    invoke-virtual {v2}, Ljava/io/FileInputStream;->getChannel()Ljava/nio/channels/FileChannel;

    move-result-object p1
    :try_end_2
    .catch Ljava/io/FileNotFoundException; {:try_start_2 .. :try_end_2} :catch_9
    .catch Ljava/io/IOException; {:try_start_2 .. :try_end_2} :catch_8
    .catchall {:try_start_2 .. :try_end_2} :catchall_2

    :try_start_3
    invoke-virtual {p0}, Ljava/io/FileOutputStream;->getChannel()Ljava/nio/channels/FileChannel;

    move-result-object v9
    :try_end_3
    .catch Ljava/io/FileNotFoundException; {:try_start_3 .. :try_end_3} :catch_7
    .catch Ljava/io/IOException; {:try_start_3 .. :try_end_3} :catch_6
    .catchall {:try_start_3 .. :try_end_3} :catchall_1

    const-wide/16 v4, 0x0

    :try_start_4
    invoke-virtual {p1}, Ljava/nio/channels/FileChannel;->size()J

    move-result-wide v6

    move-object v3, p1

    move-object v8, v9

    invoke-virtual/range {v3 .. v8}, Ljava/nio/channels/FileChannel;->transferTo(JJLjava/nio/channels/WritableByteChannel;)J
    :try_end_4
    .catch Ljava/io/FileNotFoundException; {:try_start_4 .. :try_end_4} :catch_5
    .catch Ljava/io/IOException; {:try_start_4 .. :try_end_4} :catch_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_0

    nop

    :try_start_5
    invoke-virtual {v2}, Ljava/io/FileInputStream;->close()V
    :try_end_5
    .catch Ljava/io/IOException; {:try_start_5 .. :try_end_5} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_1

    :goto_0
    nop

    :goto_1
    if-eqz p1, :cond_1

    :try_start_6
    invoke-virtual {p1}, Ljava/nio/channels/FileChannel;->close()V
    :try_end_6
    .catch Ljava/io/IOException; {:try_start_6 .. :try_end_6} :catch_1

    goto :goto_2

    :catch_1
    move-exception p1

    invoke-virtual {p1}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_3

    :cond_1
    :goto_2
    nop

    :goto_3
    nop

    :try_start_7
    invoke-virtual {p0}, Ljava/io/FileOutputStream;->close()V
    :try_end_7
    .catch Ljava/io/IOException; {:try_start_7 .. :try_end_7} :catch_2

    goto :goto_4

    :catch_2
    move-exception p0

    invoke-virtual {p0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_5

    :goto_4
    nop

    :goto_5
    if-eqz v9, :cond_2

    :try_start_8
    invoke-virtual {v9}, Ljava/nio/channels/FileChannel;->close()V
    :try_end_8
    .catch Ljava/io/IOException; {:try_start_8 .. :try_end_8} :catch_3

    goto :goto_6

    :catch_3
    move-exception p0

    invoke-virtual {p0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_7

    :cond_2
    :goto_6
    nop

    :goto_7
    const/4 p0, 0x1

    return p0

    :catchall_0
    move-exception v0

    move-object v10, v0

    move-object v0, p0

    move-object p0, v10

    goto/16 :goto_1a

    :catch_4
    move-exception v0

    move-object v10, v2

    move-object v2, p0

    move-object p0, v0

    move-object v0, v10

    goto/16 :goto_8

    :catch_5
    move-exception v0

    move-object v10, v2

    move-object v2, p0

    move-object p0, v0

    move-object v0, v10

    goto/16 :goto_11

    :catchall_1
    move-exception v1

    move-object v9, v0

    move-object v0, p0

    move-object p0, v1

    goto/16 :goto_1a

    :catch_6
    move-exception v3

    move-object v9, v0

    move-object v0, v2

    move-object v2, p0

    move-object p0, v3

    goto/16 :goto_8

    :catch_7
    move-exception v3

    move-object v9, v0

    move-object v0, v2

    move-object v2, p0

    move-object p0, v3

    goto/16 :goto_11

    :catchall_2
    move-exception p1

    move-object v9, v0

    move-object v0, p0

    move-object p0, p1

    move-object p1, v9

    goto/16 :goto_1a

    :catch_8
    move-exception p1

    move-object v9, v0

    move-object v0, v2

    move-object v2, p0

    move-object p0, p1

    move-object p1, v9

    goto :goto_8

    :catch_9
    move-exception p1

    move-object v9, v0

    move-object v0, v2

    move-object v2, p0

    move-object p0, p1

    move-object p1, v9

    goto/16 :goto_11

    :catchall_3
    move-exception p0

    move-object p1, v0

    move-object v9, p1

    goto/16 :goto_1a

    :catch_a
    move-exception p0

    move-object p1, v0

    move-object v9, p1

    move-object v0, v2

    move-object v2, v9

    goto :goto_8

    :catch_b
    move-exception p0

    move-object p1, v0

    move-object v9, p1

    move-object v0, v2

    move-object v2, v9

    goto :goto_11

    :catchall_4
    move-exception p0

    move-object p1, v0

    move-object v2, p1

    move-object v9, v2

    goto/16 :goto_1a

    :catch_c
    move-exception p0

    move-object p1, v0

    move-object v2, p1

    move-object v9, v2

    :goto_8
    :try_start_9
    invoke-virtual {p0}, Ljava/io/IOException;->printStackTrace()V
    :try_end_9
    .catchall {:try_start_9 .. :try_end_9} :catchall_5

    nop

    if-eqz v0, :cond_3

    :try_start_a
    invoke-virtual {v0}, Ljava/io/FileInputStream;->close()V
    :try_end_a
    .catch Ljava/io/IOException; {:try_start_a .. :try_end_a} :catch_d

    goto :goto_9

    :catch_d
    move-exception p0

    invoke-virtual {p0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_a

    :cond_3
    :goto_9
    nop

    :goto_a
    if-eqz p1, :cond_4

    :try_start_b
    invoke-virtual {p1}, Ljava/nio/channels/FileChannel;->close()V
    :try_end_b
    .catch Ljava/io/IOException; {:try_start_b .. :try_end_b} :catch_e

    goto :goto_b

    :catch_e
    move-exception p0

    invoke-virtual {p0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_c

    :cond_4
    :goto_b
    nop

    :goto_c
    if-eqz v2, :cond_5

    :try_start_c
    invoke-virtual {v2}, Ljava/io/FileOutputStream;->close()V
    :try_end_c
    .catch Ljava/io/IOException; {:try_start_c .. :try_end_c} :catch_f

    goto :goto_d

    :catch_f
    move-exception p0

    invoke-virtual {p0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_e

    :cond_5
    :goto_d
    nop

    :goto_e
    if-eqz v9, :cond_6

    :try_start_d
    invoke-virtual {v9}, Ljava/nio/channels/FileChannel;->close()V
    :try_end_d
    .catch Ljava/io/IOException; {:try_start_d .. :try_end_d} :catch_10

    goto :goto_f

    :catch_10
    move-exception p0

    invoke-virtual {p0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_10

    :cond_6
    :goto_f
    nop

    :goto_10
    return v1

    :catch_11
    move-exception p0

    move-object p1, v0

    move-object v2, p1

    move-object v9, v2

    :goto_11
    :try_start_e
    invoke-virtual {p0}, Ljava/io/FileNotFoundException;->printStackTrace()V
    :try_end_e
    .catchall {:try_start_e .. :try_end_e} :catchall_5

    nop

    if-eqz v0, :cond_7

    :try_start_f
    invoke-virtual {v0}, Ljava/io/FileInputStream;->close()V
    :try_end_f
    .catch Ljava/io/IOException; {:try_start_f .. :try_end_f} :catch_12

    goto :goto_12

    :catch_12
    move-exception p0

    invoke-virtual {p0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_13

    :cond_7
    :goto_12
    nop

    :goto_13
    if-eqz p1, :cond_8

    :try_start_10
    invoke-virtual {p1}, Ljava/nio/channels/FileChannel;->close()V
    :try_end_10
    .catch Ljava/io/IOException; {:try_start_10 .. :try_end_10} :catch_13

    goto :goto_14

    :catch_13
    move-exception p0

    invoke-virtual {p0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_15

    :cond_8
    :goto_14
    nop

    :goto_15
    if-eqz v2, :cond_9

    :try_start_11
    invoke-virtual {v2}, Ljava/io/FileOutputStream;->close()V
    :try_end_11
    .catch Ljava/io/IOException; {:try_start_11 .. :try_end_11} :catch_14

    goto :goto_16

    :catch_14
    move-exception p0

    invoke-virtual {p0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_17

    :cond_9
    :goto_16
    nop

    :goto_17
    if-eqz v9, :cond_a

    :try_start_12
    invoke-virtual {v9}, Ljava/nio/channels/FileChannel;->close()V
    :try_end_12
    .catch Ljava/io/IOException; {:try_start_12 .. :try_end_12} :catch_15

    goto :goto_18

    :catch_15
    move-exception p0

    invoke-virtual {p0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_19

    :cond_a
    :goto_18
    nop

    :goto_19
    return v1

    :catchall_5
    move-exception p0

    move-object v10, v2

    move-object v2, v0

    move-object v0, v10

    :goto_1a
    if-eqz v2, :cond_b

    :try_start_13
    invoke-virtual {v2}, Ljava/io/FileInputStream;->close()V
    :try_end_13
    .catch Ljava/io/IOException; {:try_start_13 .. :try_end_13} :catch_16

    goto :goto_1b

    :catch_16
    move-exception v1

    invoke-virtual {v1}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_1c

    :cond_b
    :goto_1b
    nop

    :goto_1c
    if-eqz p1, :cond_c

    :try_start_14
    invoke-virtual {p1}, Ljava/nio/channels/FileChannel;->close()V
    :try_end_14
    .catch Ljava/io/IOException; {:try_start_14 .. :try_end_14} :catch_17

    goto :goto_1d

    :catch_17
    move-exception p1

    invoke-virtual {p1}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_1e

    :cond_c
    :goto_1d
    nop

    :goto_1e
    if-eqz v0, :cond_d

    :try_start_15
    invoke-virtual {v0}, Ljava/io/FileOutputStream;->close()V
    :try_end_15
    .catch Ljava/io/IOException; {:try_start_15 .. :try_end_15} :catch_18

    goto :goto_1f

    :catch_18
    move-exception p1

    invoke-virtual {p1}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_20

    :cond_d
    :goto_1f
    nop

    :goto_20
    if-eqz v9, :cond_e

    :try_start_16
    invoke-virtual {v9}, Ljava/nio/channels/FileChannel;->close()V
    :try_end_16
    .catch Ljava/io/IOException; {:try_start_16 .. :try_end_16} :catch_19

    goto :goto_21

    :catch_19
    move-exception p1

    invoke-virtual {p1}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_22

    :cond_e
    :goto_21
    nop

    :goto_22
    throw p0
.end method

.method public static getPath()Ljava/lang/String;
    .locals 2

    sget-object v0, Lcom/ss/android/medialib/FileUtils;->msFolderPath:Ljava/lang/String;

    if-nez v0, :cond_0

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-static {}, Landroid/os/Environment;->getExternalStorageDirectory()Ljava/io/File;

    move-result-object v1

    invoke-virtual {v1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v1, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "BDMedia"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/ss/android/medialib/FileUtils;->msFolderPath:Ljava/lang/String;

    new-instance v0, Ljava/io/File;

    sget-object v1, Lcom/ss/android/medialib/FileUtils;->msFolderPath:Ljava/lang/String;

    invoke-direct {v0, v1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v1

    if-nez v1, :cond_0

    invoke-virtual {v0}, Ljava/io/File;->mkdirs()Z

    :cond_0
    sget-object v0, Lcom/ss/android/medialib/FileUtils;->msFolderPath:Ljava/lang/String;

    return-object v0
.end method

.method public static isSdcardWritable()Z
    .locals 2

    :try_start_0
    const-string v0, "mounted"

    invoke-static {}, Landroid/os/Environment;->getExternalStorageState()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return v0

    :catch_0
    move-exception v0

    const/4 v0, 0x0

    return v0
.end method
