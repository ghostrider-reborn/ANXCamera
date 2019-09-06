package com.android.camera;

import android.content.Intent;
import android.util.Log;

public final class ThermalHelper {
    private static final String PACKAGE_NAME = "com.miui.powerkeeper";
    private static final String TAG = "ThermalHelper";

    private ThermalHelper() {
    }

    public static void notifyThermal4KRecordStart() {
        String str = TAG;
        if (Log.isLoggable(str, 3)) {
            Log.d(str, "notifyThermal4KRecordStart");
        }
        Intent intent = new Intent();
        intent.setPackage(PACKAGE_NAME);
        intent.setAction("record_start");
        CameraAppImpl.getAndroidContext().sendBroadcast(intent);
    }

    public static void notifyThermal4KRecordStop() {
        String str = TAG;
        if (Log.isLoggable(str, 3)) {
            Log.d(str, "notifyThermal4KRecordStop");
        }
        Intent intent = new Intent();
        intent.setPackage(PACKAGE_NAME);
        intent.setAction("record_end");
        CameraAppImpl.getAndroidContext().sendBroadcast(intent);
    }
}
