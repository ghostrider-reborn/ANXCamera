package com.android.camera.lib.compatibility.related.popcamera;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import d.a.a;
import d.a.a.b;

public class PopCameraUtil {
    private static final String TAG = "PopCameraUtil";

    public static int getMotorStatus() {
        String str = TAG;
        int i = -1;
        try {
            a asInterface = b.asInterface(ServiceManager.getService("popupcamera"));
            Log.d(str, "getMotorStatus: E...");
            i = asInterface.getMotorStatus();
            Log.d(str, "getMotorStatus: X...");
            return i;
        } catch (RemoteException e2) {
            Log.e(str, "PopupCameraManagerService connection failed", e2);
            return i;
        }
    }

    public static boolean popupMotor() {
        String str = TAG;
        boolean z = false;
        try {
            a asInterface = b.asInterface(ServiceManager.getService("popupcamera"));
            Log.d(str, "popupMotor: E...");
            z = asInterface.popupMotor();
            Log.d(str, "popupMotor: X...");
            return z;
        } catch (RemoteException e2) {
            Log.e(str, "PopupCameraManagerService connection failed", e2);
            return z;
        }
    }

    public static boolean takebackMotor() {
        String str = TAG;
        boolean z = false;
        try {
            a asInterface = b.asInterface(ServiceManager.getService("popupcamera"));
            Log.d(str, "takebackMotor: E...");
            z = asInterface.takebackMotor();
            Log.d(str, "takebackMotor: X...");
            return z;
        } catch (RemoteException e2) {
            Log.e(str, "PopupCameraManagerService connection failed", e2);
            return z;
        }
    }
}
