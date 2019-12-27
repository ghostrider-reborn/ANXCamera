package com.android.camera.permission;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.android.camera.CameraAppImpl;
import com.android.camera.log.Log;
import com.mi.config.b;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PermissionManager {
    private static final int CAM_REQUEST_CODE_ASK_RUNTIME_PERMISSIONS = 100;
    private static final String TAG = "PermissionManager";
    private static List<String> mLaunchPermissionList = new ArrayList();
    private static List<String> sLocationPermissionList = new ArrayList();
    private static List<String> sRuntimePermissions = new ArrayList();
    private static List<String> sStoragePermissionList = new ArrayList();

    static {
        sLocationPermissionList.add("android.permission.ACCESS_FINE_LOCATION");
        sLocationPermissionList.add("android.permission.ACCESS_COARSE_LOCATION");
        sStoragePermissionList.add("android.permission.READ_EXTERNAL_STORAGE");
        sStoragePermissionList.add("android.permission.WRITE_EXTERNAL_STORAGE");
        mLaunchPermissionList.add("android.permission.CAMERA");
        mLaunchPermissionList.add("android.permission.RECORD_AUDIO");
        mLaunchPermissionList.addAll(sStoragePermissionList);
        sRuntimePermissions.addAll(mLaunchPermissionList);
        sRuntimePermissions.addAll(sLocationPermissionList);
        if (b.isMTKPlatform()) {
            sRuntimePermissions.add("android.permission.READ_PHONE_STATE");
        }
    }

    public static boolean checkCameraLaunchPermissions() {
        if (Build.VERSION.SDK_INT >= 23 && miui.os.Build.IS_INTERNATIONAL_BUILD) {
            if (getNeedCheckPermissionList(mLaunchPermissionList).size() > 0) {
                return false;
            }
            Log.i(TAG, "CheckCameraPermissions(), all on");
        }
        return true;
    }

    public static boolean checkCameraLocationPermissions() {
        if (Build.VERSION.SDK_INT >= 23 && miui.os.Build.IS_INTERNATIONAL_BUILD) {
            if (getNeedCheckPermissionList(sLocationPermissionList).size() > 0) {
                return false;
            }
            Log.i(TAG, "checkCameraLocationPermissions(), all on");
        }
        return true;
    }

    public static boolean checkPhoneStatePermission(Activity activity) {
        return Build.VERSION.SDK_INT < 23 || !miui.os.Build.IS_INTERNATIONAL_BUILD || ContextCompat.checkSelfPermission(activity, "android.permission.READ_PHONE_STATE") == 0;
    }

    public static boolean checkStoragePermissions() {
        if (Build.VERSION.SDK_INT >= 23 && miui.os.Build.IS_INTERNATIONAL_BUILD) {
            if (getNeedCheckPermissionList(sStoragePermissionList).size() > 0) {
                return false;
            }
            Log.i(TAG, "checkStoragePermissions on");
        }
        return true;
    }

    public static int getCameraRuntimePermissionRequestCode() {
        return 100;
    }

    private static List<String> getNeedCheckPermissionList(List<String> list) {
        if (list.size() <= 0) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        for (String next : list) {
            if (ContextCompat.checkSelfPermission(CameraAppImpl.getAndroidContext(), next) != 0) {
                Log.i(TAG, "getNeedCheckPermissionList() permission =" + next);
                arrayList.add(next);
            }
        }
        Log.i(TAG, "getNeedCheckPermissionList() listSize =" + arrayList.size());
        return arrayList;
    }

    public static boolean isCameraLaunchPermissionsResultReady(String[] strArr, int[] iArr) {
        HashMap hashMap = new HashMap();
        hashMap.put("android.permission.CAMERA", 0);
        hashMap.put("android.permission.RECORD_AUDIO", 0);
        hashMap.put("android.permission.WRITE_EXTERNAL_STORAGE", 0);
        for (int i = 0; i < strArr.length; i++) {
            hashMap.put(strArr[i], Integer.valueOf(iArr[i]));
        }
        return ((Integer) hashMap.get("android.permission.CAMERA")).intValue() == 0 && ((Integer) hashMap.get("android.permission.RECORD_AUDIO")).intValue() == 0 && ((Integer) hashMap.get("android.permission.WRITE_EXTERNAL_STORAGE")).intValue() == 0;
    }

    public static boolean isCameraLocationPermissionsResultReady(String[] strArr, int[] iArr) {
        HashMap hashMap = new HashMap();
        hashMap.put("android.permission.ACCESS_COARSE_LOCATION", 0);
        hashMap.put("android.permission.ACCESS_FINE_LOCATION", 0);
        for (int i = 0; i < strArr.length; i++) {
            hashMap.put(strArr[i], Integer.valueOf(iArr[i]));
        }
        return ((Integer) hashMap.get("android.permission.ACCESS_COARSE_LOCATION")).intValue() == 0 && ((Integer) hashMap.get("android.permission.ACCESS_FINE_LOCATION")).intValue() == 0;
    }

    public static boolean requestCameraRuntimePermissions(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23 && miui.os.Build.IS_INTERNATIONAL_BUILD) {
            List<String> needCheckPermissionList = getNeedCheckPermissionList(sRuntimePermissions);
            if (needCheckPermissionList.size() > 0) {
                Log.i(TAG, "requestCameraRuntimePermissions(), user check");
                ActivityCompat.requestPermissions(activity, (String[]) needCheckPermissionList.toArray(new String[needCheckPermissionList.size()]), 100);
                return false;
            }
            Log.i(TAG, "requestCameraRuntimePermissions(), all on");
        }
        return true;
    }
}
