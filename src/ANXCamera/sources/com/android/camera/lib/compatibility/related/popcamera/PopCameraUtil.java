package com.android.camera.lib.compatibility.related.popcamera;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import d.a.a;

public class PopCameraUtil {
    private static final String TAG = "PopCameraUtil";

    public static int getMotorStatus() {
        int i = -1;
        try {
            a asInterface = a.b.asInterface(ServiceManager.getService("popupcamera"));
            Log.d(TAG, "getMotorStatus: E...");
            i = asInterface.getMotorStatus();
            Log.d(TAG, "getMotorStatus: X...");
            return i;
        } catch (RemoteException e2) {
            Log.e(TAG, "PopupCameraManagerService connection failed", e2);
            return i;
        }
    }

    public static boolean popupMotor() {
        boolean z = false;
        try {
            a asInterface = a.b.asInterface(ServiceManager.getService("popupcamera"));
            Log.d(TAG, "popupMotor: E...");
            z = asInterface.popupMotor();
            Log.d(TAG, "popupMotor: X...");
            return z;
        } catch (RemoteException e2) {
            Log.e(TAG, "PopupCameraManagerService connection failed", e2);
            return z;
        }
    }

    public static boolean takebackMotor() {
        boolean z = false;
        try {
            a asInterface = a.b.asInterface(ServiceManager.getService("popupcamera"));
            Log.d(TAG, "takebackMotor: E...");
            z = asInterface.takebackMotor();
            Log.d(TAG, "takebackMotor: X...");
            return z;
        } catch (RemoteException e2) {
            Log.e(TAG, "PopupCameraManagerService connection failed", e2);
            return z;
        }
    }
}
