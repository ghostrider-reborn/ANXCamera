.class public final Landroid/provider/MiuiSettings$Key;
.super Ljava/lang/Object;
.source "MiuiSettings.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Landroid/provider/MiuiSettings;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = "Key"
.end annotation


# static fields
.field public static final CLOSE_APP:Ljava/lang/String; = "close_app"

.field public static final CLOSE_TALKBACK:Ljava/lang/String; = "close_talkback"

.field public static final DISABLE_THREE_GESTURE:I = 0x0

.field public static final DOUBLE_CLICK_POWER_KEY:Ljava/lang/String; = "double_click_power_key"

.field public static final DUMP_LOG:Ljava/lang/String; = "dump_log"

.field public static final ENABLE_THREE_GESTURE:I = 0x1

.field public static final ENABLE_THREE_GESTURE_KEY:Ljava/lang/String; = "enable_three_gesture"

.field public static final GO_TO_SLEEP:Ljava/lang/String; = "go_to_sleep"

.field public static final KEY_AI_BUTTON_SETTINGS:Ljava/lang/String; = "key_ai_button_settings"

.field public static final KEY_BANK_CARD:Ljava/lang/String; = "key_bank_card_in_ese"

.field public static final KEY_BANK_CARD_DISABLE:I = 0x0

.field public static final KEY_COMBINATION_POWER_BACK:Ljava/lang/String; = "key_combination_power_back"

.field public static final KEY_COMBINATION_POWER_HOME:Ljava/lang/String; = "key_combination_power_home"

.field public static final KEY_COMBINATION_POWER_MENU:Ljava/lang/String; = "key_combination_power_menu"

.field public static final KEY_DOUBLE_CLICK_AI_BUTTON_SETTINGS:Ljava/lang/String; = "key_double_click_ai_button_settings"

.field public static final KEY_LONG_PRESS_DOWN_AI_BUTTON_SETTINGS:Ljava/lang/String; = "key_long_press_down_ai_button_settings"

.field public static final KEY_LONG_PRESS_UP_AI_BUTTON_SETTINGS:Ljava/lang/String; = "key_long_press_up_ai_button_settings"

.field public static final KEY_NONE:Ljava/lang/String; = "key_none"

.field public static final KEY_SINGLE_CLICK_AI_BUTTON_SETTINGS:Ljava/lang/String; = "key_single_click_ai_button_settings"

.field public static final KEY_TIPS:Ljava/lang/String; = "key_tips"

.field public static final KEY_TRANS_CARD:Ljava/lang/String; = "key_trans_card_in_ese"

.field public static final KEY_TRANS_CARD_DISABLE:I = 0x0

.field public static final KEY_UPDATED:Ljava/lang/String; = "key_updated"

.field public static final LAUNCH_CAMERA:Ljava/lang/String; = "launch_camera"

.field public static final LAUNCH_GOOGLE_SEARCH:Ljava/lang/String; = "launch_google_search"

.field public static final LAUNCH_RECENTS:Ljava/lang/String; = "launch_recents"

.field public static final LAUNCH_VOICE_ASSISTANT:Ljava/lang/String; = "launch_voice_assistant"

.field public static final LONG_PRESS_BACK_KEY:Ljava/lang/String; = "long_press_back_key"

.field public static final LONG_PRESS_HOME_KEY:Ljava/lang/String; = "long_press_home_key"

.field public static final LONG_PRESS_MENU_KEY:Ljava/lang/String; = "long_press_menu_key"

.field public static final LONG_PRESS_MENU_KEY_WHEN_LOCK:Ljava/lang/String; = "long_press_menu_key_when_lock"

.field public static final LONG_PRESS_POWER_KEY:Ljava/lang/String; = "long_press_power_key"

.field public static final LONG_PRESS_POWER_LAUNCH_XIAOAI:Ljava/lang/String; = "long_press_power_launch_xiaoai"

.field public static final LONG_PRESS_POWER_LAUNCH_XIAOAI_DISABLE:I = 0x0

.field public static final LONG_PRESS_POWER_LAUNCH_XIAOAI_ENABLE:I = 0x1

.field public static final LONG_PRESS_VOLUME_DOWN:Ljava/lang/String; = "key_long_press_volume_down"

.field public static final LONG_PRESS_VOLUME_DOWN_DEFAULT:Ljava/lang/String; = "none"

.field public static final LONG_PRESS_VOLUME_DOWN_PAY:Ljava/lang/String; = "public_transportation_shortcuts"

.field public static final LONG_PRESS_VOLUME_DOWN_STREET_SNAP:Ljava/lang/String; = "Street-snap"

.field public static final LONG_PRESS_VOLUME_DOWN_STREET_SNAP_MOVIE:Ljava/lang/String; = "Street-snap-movie"

.field public static final LONG_PRESS_VOLUME_DOWN_STREET_SNAP_PICTURE:Ljava/lang/String; = "Street-snap-picture"

.field public static final MI_PAY:Ljava/lang/String; = "mi_pay"

.field public static final NONE:Ljava/lang/String; = "none"

.field public static final SCREEN_SHOT:Ljava/lang/String; = "screen_shot"

.field public static final SEND_BACK_WHEN_XIAOAI_APPEAR:Ljava/lang/String; = "send_back_when_xiaoai_appear"

.field public static final SHOW_MENU:Ljava/lang/String; = "show_menu"

.field public static final SINGLE_KEY_USE_ACTION:Ljava/lang/String; = "single_key_use_enable"

.field public static final SINGLE_KEY_USE_DISABLE:I = 0x0

.field public static final SINGLE_KEY_USE_ENABLE:I = 0x1

.field public static final SPLIT_SCREEN:Ljava/lang/String; = "split_screen"

.field public static final THREE_GESTURE_DOWN:Ljava/lang/String; = "three_gesture_down"

.field public static final TURN_ON_TORCH:Ljava/lang/String; = "turn_on_torch"

.field public static final VOLUMEKEY_LAUNCH_CAMERA:Ljava/lang/String; = "volumekey_launch_camera"

.field public static final WIRED_HEADSET_LAUNCH_XIAOAI:Ljava/lang/String; = "wired_headset_launch_xiaoai"


# direct methods
.method public constructor <init>()V
    .registers 1

    .line 6642
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static getKeyAndGestureShortcutAction(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    .registers 4
    .param p0, "context"    # Landroid/content/Context;
    .param p1, "function"    # Ljava/lang/String;

    .line 6820
    const-string v0, "key_none"

    .line 6821
    .local v0, "action":Ljava/lang/String;
    if-eqz p1, :cond_9d

    .line 6822
    const-string v1, "long_press_power_key"

    invoke-static {p0, v1}, Landroid/provider/MiuiSettings$Key;->getKeyAndGestureShortcutFunction(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_14

    .line 6823
    const-string v0, "long_press_power_key"

    goto/16 :goto_9d

    .line 6824
    :cond_14
    const-string v1, "double_click_power_key"

    invoke-static {p0, v1}, Landroid/provider/MiuiSettings$Key;->getKeyAndGestureShortcutFunction(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_24

    .line 6825
    const-string v0, "double_click_power_key"

    goto/16 :goto_9d

    .line 6826
    :cond_24
    const-string/jumbo v1, "three_gesture_down"

    invoke-static {p0, v1}, Landroid/provider/MiuiSettings$Key;->getKeyAndGestureShortcutFunction(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_35

    .line 6827
    const-string/jumbo v0, "three_gesture_down"

    goto :goto_9d

    .line 6828
    :cond_35
    const-string v1, "long_press_menu_key_when_lock"

    invoke-static {p0, v1}, Landroid/provider/MiuiSettings$Key;->getKeyAndGestureShortcutFunction(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_44

    .line 6829
    const-string v0, "long_press_menu_key_when_lock"

    goto :goto_9d

    .line 6830
    :cond_44
    const-string v1, "long_press_home_key"

    invoke-static {p0, v1}, Landroid/provider/MiuiSettings$Key;->getKeyAndGestureShortcutFunction(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_53

    .line 6831
    const-string v0, "long_press_home_key"

    goto :goto_9d

    .line 6832
    :cond_53
    const-string v1, "long_press_menu_key"

    invoke-static {p0, v1}, Landroid/provider/MiuiSettings$Key;->getKeyAndGestureShortcutFunction(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_62

    .line 6833
    const-string v0, "long_press_menu_key"

    goto :goto_9d

    .line 6834
    :cond_62
    const-string v1, "long_press_back_key"

    invoke-static {p0, v1}, Landroid/provider/MiuiSettings$Key;->getKeyAndGestureShortcutFunction(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_71

    .line 6835
    const-string v0, "long_press_back_key"

    goto :goto_9d

    .line 6836
    :cond_71
    const-string v1, "key_combination_power_home"

    invoke-static {p0, v1}, Landroid/provider/MiuiSettings$Key;->getKeyAndGestureShortcutFunction(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_80

    .line 6837
    const-string v0, "key_combination_power_home"

    goto :goto_9d

    .line 6838
    :cond_80
    const-string v1, "key_combination_power_back"

    invoke-static {p0, v1}, Landroid/provider/MiuiSettings$Key;->getKeyAndGestureShortcutFunction(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_8f

    .line 6839
    const-string v0, "key_combination_power_back"

    goto :goto_9d

    .line 6840
    :cond_8f
    const-string v1, "key_combination_power_menu"

    invoke-static {p0, v1}, Landroid/provider/MiuiSettings$Key;->getKeyAndGestureShortcutFunction(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_9d

    .line 6841
    const-string v0, "key_combination_power_menu"

    .line 6844
    :cond_9d
    :goto_9d
    return-object v0
.end method

.method public static getKeyAndGestureShortcutFunction(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    .registers 4
    .param p0, "context"    # Landroid/content/Context;
    .param p1, "action"    # Ljava/lang/String;

    .line 6851
    invoke-static {p0, p1}, Landroid/provider/MiuiSettings$Key;->getKeyAndGestureShortcutSetFunction(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 6852
    .local v0, "function":Ljava/lang/String;
    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_7d

    .line 6853
    const-string v1, "long_press_power_key"

    invoke-virtual {v1, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_15

    .line 6854
    const/4 v0, 0x0

    goto/16 :goto_7d

    .line 6855
    :cond_15
    const-string v1, "double_click_power_key"

    invoke-virtual {v1, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_1f

    .line 6856
    const/4 v0, 0x0

    goto :goto_7d

    .line 6857
    :cond_1f
    const-string/jumbo v1, "three_gesture_down"

    invoke-virtual {v1, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_2c

    .line 6858
    const-string/jumbo v0, "screen_shot"

    goto :goto_7d

    .line 6859
    :cond_2c
    const-string v1, "long_press_home_key"

    invoke-virtual {v1, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_3e

    .line 6860
    sget-boolean v1, Lmiui/os/Build;->IS_GLOBAL_BUILD:Z

    if-nez v1, :cond_3b

    .line 6861
    const-string v0, "launch_voice_assistant"

    goto :goto_7d

    .line 6863
    :cond_3b
    const-string v0, "launch_google_search"

    goto :goto_7d

    .line 6865
    :cond_3e
    const-string v1, "long_press_menu_key"

    invoke-virtual {v1, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_4a

    .line 6866
    const-string/jumbo v0, "show_menu"

    goto :goto_7d

    .line 6867
    :cond_4a
    const-string v1, "long_press_menu_key_when_lock"

    invoke-virtual {v1, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_56

    .line 6868
    const-string/jumbo v0, "turn_on_torch"

    goto :goto_7d

    .line 6869
    :cond_56
    const-string v1, "long_press_back_key"

    invoke-virtual {v1, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_60

    .line 6870
    const/4 v0, 0x0

    goto :goto_7d

    .line 6871
    :cond_60
    const-string v1, "key_combination_power_home"

    invoke-virtual {v1, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_6a

    .line 6872
    const/4 v0, 0x0

    goto :goto_7d

    .line 6873
    :cond_6a
    const-string v1, "key_combination_power_back"

    invoke-virtual {v1, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_74

    .line 6874
    const/4 v0, 0x0

    goto :goto_7d

    .line 6875
    :cond_74
    const-string v1, "key_combination_power_menu"

    invoke-virtual {v1, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_7d

    .line 6876
    const/4 v0, 0x0

    .line 6879
    :cond_7d
    :goto_7d
    return-object v0
.end method

.method private static getKeyAndGestureShortcutSetFunction(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    .registers 4
    .param p0, "context"    # Landroid/content/Context;
    .param p1, "action"    # Ljava/lang/String;

    .line 6886
    if-eqz p0, :cond_c

    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    const/4 v1, -0x2

    invoke-static {v0, p1, v1}, Landroid/provider/Settings$System;->getStringForUser(Landroid/content/ContentResolver;Ljava/lang/String;I)Ljava/lang/String;

    move-result-object v0

    goto :goto_d

    .line 6887
    :cond_c
    const/4 v0, 0x0

    .line 6886
    :goto_d
    return-object v0
.end method

.method public static isTSMClientInstalled(Landroid/content/Context;)Z
    .registers 4
    .param p0, "context"    # Landroid/content/Context;

    .line 6678
    invoke-virtual {p0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v0

    .line 6679
    .local v0, "pm":Landroid/content/pm/PackageManager;
    const/4 v1, 0x0

    if-eqz v0, :cond_13

    .line 6681
    :try_start_7
    const-string v2, "com.miui.tsmclient"

    invoke-virtual {v0, v2, v1}, Landroid/content/pm/PackageManager;->getPackageInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object v2
    :try_end_d
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_7 .. :try_end_d} :catch_12

    .line 6682
    .local v2, "packageInfo":Landroid/content/pm/PackageInfo;
    if-eqz v2, :cond_11

    .line 6683
    const/4 v1, 0x1

    return v1

    .line 6687
    .end local v2    # "packageInfo":Landroid/content/pm/PackageInfo;
    :cond_11
    goto :goto_13

    .line 6685
    :catch_12
    move-exception v2

    .line 6689
    :cond_13
    :goto_13
    return v1
.end method

.method public static setPowerKeyLaunchVoiceAssistant(Landroid/content/Context;)V
    .registers 6
    .param p0, "context"    # Landroid/content/Context;

    .line 6933
    const-string v0, "launch_voice_assistant"

    invoke-static {p0, v0}, Landroid/provider/MiuiSettings$Key;->getKeyAndGestureShortcutAction(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 6934
    .local v0, "voiceAssistantAction":Ljava/lang/String;
    const-string v1, "key_none"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    const/4 v2, -0x2

    if-nez v1, :cond_23

    .line 6936
    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    const-string/jumbo v3, "none"

    invoke-static {v1, v0, v3, v2}, Landroid/provider/Settings$System;->putStringForUser(Landroid/content/ContentResolver;Ljava/lang/String;Ljava/lang/String;I)Z

    .line 6940
    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    const-string v3, "key_tips"

    invoke-static {v1, v3, v0, v2}, Landroid/provider/Settings$System;->putStringForUser(Landroid/content/ContentResolver;Ljava/lang/String;Ljava/lang/String;I)Z

    goto :goto_42

    .line 6942
    :cond_23
    const-string v1, "long_press_home_key"

    invoke-static {p0, v1}, Landroid/provider/MiuiSettings$Key;->getKeyAndGestureShortcutFunction(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    if-nez v1, :cond_42

    .line 6944
    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    const-string v3, "long_press_home_key"

    const-string/jumbo v4, "none"

    invoke-static {v1, v3, v4, v2}, Landroid/provider/Settings$System;->putStringForUser(Landroid/content/ContentResolver;Ljava/lang/String;Ljava/lang/String;I)Z

    .line 6948
    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    const-string v3, "key_tips"

    const-string v4, "long_press_home_key"

    invoke-static {v1, v3, v4, v2}, Landroid/provider/Settings$System;->putStringForUser(Landroid/content/ContentResolver;Ljava/lang/String;Ljava/lang/String;I)Z

    .line 6952
    :cond_42
    :goto_42
    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    const-string v3, "long_press_power_key"

    const-string v4, "launch_voice_assistant"

    invoke-static {v1, v3, v4, v2}, Landroid/provider/Settings$System;->putStringForUser(Landroid/content/ContentResolver;Ljava/lang/String;Ljava/lang/String;I)Z

    .line 6954
    return-void
.end method

.method public static updateOldKeyFunctionToNew(Landroid/content/Context;)V
    .registers 8
    .param p0, "context"    # Landroid/content/Context;

    .line 6894
    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    const-string v1, "key_updated"

    const/4 v2, -0x2

    const/4 v3, 0x0

    invoke-static {v0, v1, v3, v2}, Landroid/provider/Settings$System;->getIntForUser(Landroid/content/ContentResolver;Ljava/lang/String;II)I

    move-result v0

    if-nez v0, :cond_a6

    .line 6896
    const-string/jumbo v0, "screen_key_long_press_app_switch"

    invoke-static {p0, v0}, Landroid/provider/MiuiSettings$System;->getScreenKeyLongPressAction(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 6898
    .local v0, "menuAction":Ljava/lang/String;
    const-string/jumbo v1, "screen_key_long_press_home"

    invoke-static {p0, v1}, Landroid/provider/MiuiSettings$System;->getScreenKeyLongPressAction(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 6900
    .local v1, "homeAction":Ljava/lang/String;
    const-string/jumbo v3, "screen_key_long_press_back"

    invoke-static {p0, v3}, Landroid/provider/MiuiSettings$System;->getScreenKeyLongPressAction(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    .line 6902
    .local v3, "backAction":Ljava/lang/String;
    const-string/jumbo v4, "voice_assistant"

    invoke-virtual {v4, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_38

    .line 6903
    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v4

    const-string v5, "long_press_home_key"

    const-string v6, "launch_voice_assistant"

    invoke-static {v4, v5, v6, v2}, Landroid/provider/Settings$System;->putStringForUser(Landroid/content/ContentResolver;Ljava/lang/String;Ljava/lang/String;I)Z

    goto :goto_61

    .line 6905
    :cond_38
    const-string/jumbo v4, "voice_assistant"

    invoke-virtual {v4, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_4d

    .line 6906
    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v4

    const-string v5, "long_press_back_key"

    const-string v6, "launch_voice_assistant"

    invoke-static {v4, v5, v6, v2}, Landroid/provider/Settings$System;->putStringForUser(Landroid/content/ContentResolver;Ljava/lang/String;Ljava/lang/String;I)Z

    goto :goto_61

    .line 6908
    :cond_4d
    const-string/jumbo v4, "voice_assistant"

    invoke-virtual {v4, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_61

    .line 6909
    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v4

    const-string v5, "long_press_menu_key"

    const-string v6, "launch_voice_assistant"

    invoke-static {v4, v5, v6, v2}, Landroid/provider/Settings$System;->putStringForUser(Landroid/content/ContentResolver;Ljava/lang/String;Ljava/lang/String;I)Z

    .line 6913
    :cond_61
    :goto_61
    const-string v4, "close_app"

    invoke-virtual {v4, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_75

    .line 6914
    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v4

    const-string v5, "long_press_back_key"

    const-string v6, "close_app"

    invoke-static {v4, v5, v6, v2}, Landroid/provider/Settings$System;->putStringForUser(Landroid/content/ContentResolver;Ljava/lang/String;Ljava/lang/String;I)Z

    goto :goto_9c

    .line 6916
    :cond_75
    const-string v4, "close_app"

    invoke-virtual {v4, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_89

    .line 6917
    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v4

    const-string v5, "long_press_home_key"

    const-string v6, "close_app"

    invoke-static {v4, v5, v6, v2}, Landroid/provider/Settings$System;->putStringForUser(Landroid/content/ContentResolver;Ljava/lang/String;Ljava/lang/String;I)Z

    goto :goto_9c

    .line 6919
    :cond_89
    const-string v4, "close_app"

    invoke-virtual {v4, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_9c

    .line 6920
    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v4

    const-string v5, "long_press_menu_key"

    const-string v6, "close_app"

    invoke-static {v4, v5, v6, v2}, Landroid/provider/Settings$System;->putStringForUser(Landroid/content/ContentResolver;Ljava/lang/String;Ljava/lang/String;I)Z

    .line 6924
    :cond_9c
    :goto_9c
    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v4

    const-string v5, "key_updated"

    const/4 v6, 0x1

    invoke-static {v4, v5, v6, v2}, Landroid/provider/Settings$System;->putIntForUser(Landroid/content/ContentResolver;Ljava/lang/String;II)Z

    .line 6927
    .end local v0    # "menuAction":Ljava/lang/String;
    .end local v1    # "homeAction":Ljava/lang/String;
    .end local v3    # "backAction":Ljava/lang/String;
    :cond_a6
    return-void
.end method
