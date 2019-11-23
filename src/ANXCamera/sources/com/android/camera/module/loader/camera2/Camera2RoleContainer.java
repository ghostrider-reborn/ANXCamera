package com.android.camera.module.loader.camera2;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.hardware.camera2.CameraManager;
import android.util.SparseArray;
import com.android.camera.CameraAppImpl;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

@SuppressLint({"MissingPermission"})
@TargetApi(21)
public class Camera2RoleContainer extends Camera2DataContainer {
    private static final int BOKEH_ROLE_ID = 61;
    private static final int DEPTH_ROLE_ID = 25;
    private static final int FRONT_AUX_ROLE_ID = 40;
    private static final int FRONT_BOKEH_ROLE_ID = 81;
    private static final int FRONT_SAT_ROLE_ID = 80;
    private static final int MACRO_ROLE_ID = 22;
    private static final int MACRO_TELE_ROLE_ID = 24;
    private static final int MAIN_BACK_ROLE_ID = 0;
    private static final int MAIN_FRONT_ROLE_ID = 1;
    private static final int PIP_ROLE_ID = 64;
    private static final int SAT_ROLE_ID = 60;
    private static final String TAG = Camera2RoleContainer.class.getSimpleName();
    private static final int TELE_ROLE_ID = 20;
    private static final int ULTRA_WIDE_BOKEH_ROLE_ID = 63;
    private static final int ULTRA_WIDE_ROLE_ID = 21;
    private static final int VIRTUAL_BACK_ROLE_ID = 100;
    private static final int VIRTUAL_FRONT_ROLE_ID = 101;
    private static final Camera2RoleContainer sInstance = new Camera2RoleContainer();
    private volatile HashMap<Integer, Integer> mCameraRoleIdMap;

    protected Camera2RoleContainer() {
    }

    private void dumpCameraIds() {
        for (Integer intValue : this.mCameraRoleIdMap.keySet()) {
            int intValue2 = intValue.intValue();
            int intValue3 = this.mCameraRoleIdMap.get(Integer.valueOf(intValue2)).intValue();
            Set<String> physicalCameraIds = ((CameraCapabilities) this.mCapabilities.get(intValue3)).getPhysicalCameraIds();
            float viewAngle = ((CameraCapabilities) this.mCapabilities.get(intValue3)).getViewAngle(false);
            if (physicalCameraIds == null || physicalCameraIds.isEmpty()) {
                Log.d(TAG, String.format("role: %3d (%5.1f°) <-> %2d", new Object[]{Integer.valueOf(intValue2), Float.valueOf(viewAngle), Integer.valueOf(intValue3)}));
            } else {
                Log.d(TAG, String.format("role: %3d (%5.1f°) <-> %2d = %s", new Object[]{Integer.valueOf(intValue2), Float.valueOf(viewAngle), Integer.valueOf(intValue3), physicalCameraIds}));
            }
        }
    }

    public static Camera2RoleContainer getInstance() {
        synchronized (sInstance) {
            if (!sInstance.isInitialized()) {
                sInstance.init((CameraManager) CameraAppImpl.getAndroidContext().getSystemService("camera"));
            }
        }
        return sInstance;
    }

    public static Camera2RoleContainer getInstance(CameraManager cameraManager) {
        synchronized (sInstance) {
            if (!sInstance.isInitialized()) {
                sInstance.init(cameraManager);
            }
        }
        return sInstance;
    }

    private void init(CameraManager cameraManager) {
        Log.d(TAG, "E: init()");
        try {
            reset();
            String[] cameraIdList = cameraManager.getCameraIdList();
            Log.d(TAG, "All available camera ids: " + Arrays.deepToString(cameraIdList));
            this.mCapabilities = new SparseArray(cameraIdList.length);
            this.mCameraRoleIdMap = new HashMap<>(cameraIdList.length);
            for (String str : cameraIdList) {
                try {
                    int parseInt = Integer.parseInt(str);
                    CameraCapabilities cameraCapabilities = new CameraCapabilities(cameraManager.getCameraCharacteristics(str), parseInt);
                    this.mCapabilities.put(parseInt, cameraCapabilities);
                    this.mCameraRoleIdMap.put(Integer.valueOf(cameraCapabilities.getCameraRoleId()), Integer.valueOf(parseInt));
                } catch (NumberFormatException e) {
                    Log.e(TAG, "non-integer camera id: " + str);
                }
            }
            dumpCameraIds();
        } catch (Exception e2) {
            Log.e(TAG, "Failed to init Camera2RoleContainer: " + e2);
            reset();
        }
        Log.d(TAG, "X: init()");
    }

    public synchronized int getAuxCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getAuxCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.getOrDefault(20, -1).intValue();
    }

    public synchronized int getAuxFrontCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getAuxFrontCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.getOrDefault(40, -1).intValue();
    }

    public synchronized int getBokehCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getBokehCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.getOrDefault(61, -1).intValue();
    }

    public synchronized int getBokehFrontCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getBokehFrontCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.getOrDefault(81, -1).intValue();
    }

    public synchronized int getFrontCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getFrontCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.getOrDefault(1, -1).intValue();
    }

    public synchronized int getMainBackCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getMainBackCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.getOrDefault(0, -1).intValue();
    }

    public synchronized int getSATCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getSATCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.getOrDefault(60, -1).intValue();
    }

    public synchronized int getSATFrontCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getSATFrontCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.getOrDefault(80, -1).intValue();
    }

    public synchronized int getStandaloneMacroCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getStandaloneMacroCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.getOrDefault(22, -1).intValue();
    }

    public synchronized int getUltraTeleCameraId() {
        return -1;
    }

    public synchronized int getUltraWideBokehCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getUltraWideBokehCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.getOrDefault(63, -1).intValue();
    }

    public synchronized int getUltraWideCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getUltraWideCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.getOrDefault(21, -1).intValue();
    }

    public synchronized int getVirtualBackCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getVirtualBackCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.getOrDefault(100, -1).intValue();
    }

    public synchronized int getVirtualFrontCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getVirtualFrontCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.getOrDefault(101, -1).intValue();
    }

    public synchronized boolean hasBokehCamera() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: hasBokehCamera(): #init() failed.");
            return false;
        }
        return this.mCameraRoleIdMap.containsKey(61);
    }

    public boolean hasPortraitCamera() {
        return hasBokehCamera();
    }

    public synchronized boolean hasSATCamera() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: hasSATCamera(): #init() failed.");
            return false;
        }
        return this.mCameraRoleIdMap.containsKey(60);
    }

    /* access modifiers changed from: protected */
    public boolean isInitialized() {
        return (this.mCapabilities == null || this.mCameraRoleIdMap == null) ? false : true;
    }

    public synchronized void reset() {
        Log.d(TAG, "E: reset()");
        this.mCurrentOpenedCameraId = -1;
        this.mCapabilities = null;
        this.mCameraRoleIdMap = null;
        Log.d(TAG, "X: reset()");
    }
}
