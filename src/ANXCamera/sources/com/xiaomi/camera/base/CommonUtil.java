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
        boolean z = false;
        if (!(bArr == null || context == null)) {
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = context.openFileOutput(str, 0);
                fileOutputStream.write(bArr);
                z = true;
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e2) {
                    Log.w(TAG, "saveCameraCalibrationToFile: failed!", e2);
                }
            } catch (FileNotFoundException e3) {
                Log.w(TAG, "saveCameraCalibrationToFile: FileNotFoundException", e3);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e4) {
                Log.w(TAG, "saveCameraCalibrationToFile: IOException", e4);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (Throwable th) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e5) {
                    Log.w(TAG, "saveCameraCalibrationToFile: failed!", e5);
                }
                throw th;
            }
        }
        return z;
    }

    public static boolean saveCameraCalibrationToFile(Context context, byte[] bArr, boolean z) {
        return saveCameraCalibrationToFile(context, bArr, z ? "front_dual_camera_caldata.bin" : "back_dual_camera_caldata.bin");
    }
}
