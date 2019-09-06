package com.android.camera.snap;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.PowerManager;
import com.android.camera.permission.PermissionManager;
import com.mi.config.b;

@TargetApi(21)
public class SnapKeyReceiver extends BroadcastReceiver {
    public static final String KEY_ACTION = "key_action";
    public static final String KEY_CODE = "key_code";
    public static final String KEY_EVENT_TIME = "key_event_time";
    private static final String TAG = "SnapKeyReceiver";

    public void onReceive(Context context, Intent intent) {
        if (VERSION.SDK_INT >= 21 && b.rj()) {
            if ("miui.intent.action.CAMERA_KEY_BUTTON".equals(intent.getAction()) && SnapCamera.isSnapEnabled(context) && PermissionManager.checkCameraLaunchPermissions()) {
                boolean isScreenOn = ((PowerManager) context.getSystemService("power")).isScreenOn();
                String str = KEY_CODE;
                if ((isScreenOn || 26 == intent.getIntExtra(str, 0)) && !SnapTrigger.getInstance().isRunning()) {
                    SnapService.setScreenOn(true);
                    return;
                }
                boolean isRunning = SnapTrigger.getInstance().isRunning();
                String str2 = KEY_EVENT_TIME;
                String str3 = KEY_ACTION;
                if (isRunning) {
                    SnapTrigger.getInstance().handleKeyEvent(intent.getIntExtra(str, 0), intent.getIntExtra(str3, 0), intent.getLongExtra(str2, 0));
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt(str, intent.getIntExtra(str, 0));
                    bundle.putInt(str3, intent.getIntExtra(str3, 0));
                    bundle.putLong(str2, intent.getLongExtra(str2, 0));
                    SnapService.startJob(context, bundle);
                }
            }
        }
    }
}
