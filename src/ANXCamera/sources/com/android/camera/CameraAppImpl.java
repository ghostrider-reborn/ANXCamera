package com.android.camera;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import com.android.camera.network.util.NetworkUtils;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera2.vendortag.CameraCharacteristicsVendorTags;
import com.android.camera2.vendortag.CaptureRequestVendorTags;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.struct.MarshalQueryableRegister;
import com.mi.config.b;
import com.miui.filtersdk.BeautificationSDK;
import com.xiaomi.camera.imagecodec.ImagePool;
import miui.external.Application;

public class CameraAppImpl extends Application {
    private static CameraApplicationDelegate sApplicationDelegate;
    private boolean isMimojiNeedUpdate = true;
    private boolean sLaunched = true;

    public static Context getAndroidContext() {
        return CameraApplicationDelegate.getAndroidContext();
    }

    public void addActivity(Activity activity) {
        sApplicationDelegate.addActivity(activity);
    }

    public void closeAllActivitiesBut(Activity activity) {
        sApplicationDelegate.closeAllActivitiesBut(activity);
    }

    public boolean containsResumedCameraInStack() {
        return sApplicationDelegate.containsResumedCameraInStack();
    }

    public Activity getActivity(int i) {
        return sApplicationDelegate.getActivity(i);
    }

    public int getActivityCount() {
        return sApplicationDelegate.getActivityCount();
    }

    public boolean isApplicationFirstLaunched() {
        boolean z = this.sLaunched;
        if (!z) {
            return z;
        }
        this.sLaunched = !z;
        return !this.sLaunched;
    }

    public boolean isMainIntentActivityLaunched() {
        return sApplicationDelegate.isMainIntentActivityLaunched();
    }

    public boolean isMimojiNeedUpdate() {
        if (!this.isMimojiNeedUpdate) {
            return false;
        }
        this.isMimojiNeedUpdate = false;
        return true;
    }

    public boolean isNeedRestore() {
        return sApplicationDelegate.getSettingsFlag();
    }

    public CameraApplicationDelegate onCreateApplicationDelegate() {
        if (sApplicationDelegate == null) {
            sApplicationDelegate = new CameraApplicationDelegate(this);
        }
        MarshalQueryableRegister.preload();
        CameraCharacteristicsVendorTags.preload();
        CaptureRequestVendorTags.preload();
        CaptureResultVendorTags.preload();
        boolean z = false;
        try {
            if (getPackageManager().getApplicationInfo(getPackageName(), 128).metaData.getInt("com.xiaomi.camera.parallel.enable", 0) == 1) {
                z = true;
            }
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
        }
        if (z) {
            if (b.isMTKPlatform()) {
                ImagePool.init(15, 2);
            }
            AlgoConnector.getInstance().startService(this);
        }
        CrashHandler.getInstance().init(this);
        NetworkUtils.bind(this);
        BeautificationSDK.init(this);
        return sApplicationDelegate;
    }

    public void removeActivity(Activity activity) {
        sApplicationDelegate.removeActivity(activity);
    }

    public void resetRestoreFlag() {
        sApplicationDelegate.resetRestoreFlag();
    }
}
