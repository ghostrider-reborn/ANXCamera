package com.android.camera.module;

import android.graphics.Rect;
import com.android.camera2.CameraHardwareFace;

public class DebugInfoUtil {
    public static final String TAG = DebugInfoUtil.class.getSimpleName();

    public static String getFaceInfoString(CameraHardwareFace[] cameraHardwareFaceArr) {
        if (cameraHardwareFaceArr == null || cameraHardwareFaceArr.length <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder(128);
        sb.append("size:" + cameraHardwareFaceArr.length + "  value:");
        for (CameraHardwareFace cameraHardwareFace : cameraHardwareFaceArr) {
            Rect rect = cameraHardwareFace.rect;
            sb.append("[" + rect.left + "," + rect.top + "," + rect.right + "," + rect.bottom + "] ");
        }
        return sb.toString();
    }
}
