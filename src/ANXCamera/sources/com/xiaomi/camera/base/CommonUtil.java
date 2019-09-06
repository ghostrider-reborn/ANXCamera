package com.xiaomi.camera.base;

import android.content.Context;
import com.android.camera.log.Log;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public final class CommonUtil {
    private static final String TAG = "CommonUtil";

    private CommonUtil() {
    }

    private static boolean saveCameraCalibrationToFile(Context context, byte[] bArr, String str) {
        String str2 = "saveCameraCalibrationToFile: failed!";
        String str3 = TAG;
        boolean z = false;
        if (!(bArr == null || context == null)) {
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = context.openFileOutput(str, 0);
                fileOutputStream.write(bArr);
                z = true;
                try {
                } catch (Exception e2) {
                    Log.w(str3, str2, e2);
                }
            } catch (FileNotFoundException e3) {
                Log.w(str3, "saveCameraCalibrationToFile: FileNotFoundException", e3);
            } catch (IOException e4) {
                Log.w(str3, "saveCameraCalibrationToFile: IOException", e4);
            } finally {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e5) {
                    Log.w(str3, str2, e5);
                }
            }
        }
        return z;
    }

    public static boolean saveCameraCalibrationToFile(Context context, byte[] bArr, boolean z) {
        return saveCameraCalibrationToFile(context, bArr, z ? "front_dual_camera_caldata.bin" : "back_dual_camera_caldata.bin");
    }
}
