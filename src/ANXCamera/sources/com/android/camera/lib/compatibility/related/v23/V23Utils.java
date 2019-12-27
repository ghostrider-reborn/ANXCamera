package com.android.camera.lib.compatibility.related.v23;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.storage.DiskInfo;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.telecom.TelecomManager;
import android.util.Log;
import java.io.File;
import java.util.Iterator;
import java.util.List;

@TargetApi(23)
public class V23Utils {
    private static final String TAG = "TelecomManagerProxy-v23";

    public static String getSdcardPath(Context context) {
        VolumeInfo volumeInfo;
        String str = null;
        if (Build.VERSION.SDK_INT >= 23) {
            List volumes = ((StorageManager) context.getSystemService("storage")).getVolumes();
            if (volumes != null) {
                Iterator it = volumes.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    volumeInfo = (VolumeInfo) it.next();
                    if (volumeInfo.getType() == 0 && volumeInfo.isMountedWritable()) {
                        DiskInfo disk = volumeInfo.getDisk();
                        Log.d(TAG, "getSdcardPath: diskInfo = " + disk);
                        if (disk != null && disk.isSd()) {
                            break;
                        }
                    }
                }
            }
        }
        volumeInfo = null;
        Log.d(TAG, "getSdcardPath: sdcardVolume = " + volumeInfo);
        if (volumeInfo != null) {
            File path = volumeInfo.getPath();
            if (path != null) {
                str = path.getPath();
            }
            Log.v(TAG, "getSdcardPath sd=" + str);
        }
        return str;
    }

    public static final boolean isInVideoCall(Context context) {
        Log.d(TAG, "isInVideoCall: start");
        if (Build.VERSION.SDK_INT < 21) {
            return false;
        }
        TelecomManager telecomManager = (TelecomManager) context.getSystemService("telecom");
        if (telecomManager != null) {
            return telecomManager.isInCall();
        }
        return false;
    }
}
