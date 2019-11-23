package org.webrtc.videoengine;

import android.hardware.Camera;
import android.util.Log;
import com.xiaomi.camera.core.PictureInfo;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VideoCaptureDeviceInfoAndroid {
    private static final String TAG = "WEBRTC-JC-VideoCaptureDeviceInfoAndroid";

    private static String deviceUniqueName(int i, Camera.CameraInfo cameraInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("Camera ");
        sb.append(i);
        sb.append(", Facing ");
        sb.append(isFrontFacing(cameraInfo) ? PictureInfo.SENSOR_TYPE_FRONT : "back");
        sb.append(", Orientation ");
        sb.append(cameraInfo.orientation);
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0111 A[SYNTHETIC, Splitter:B:32:0x0111] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x011a A[Catch:{ JSONException -> 0x0124 }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0114 A[SYNTHETIC] */
    private static String getDeviceInfo() {
        try {
            JSONArray jSONArray = new JSONArray();
            int numberOfCameras = Camera.getNumberOfCameras();
            Log.d(TAG, "Number of cameras:");
            for (int i = 0; i < numberOfCameras; i++) {
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(i, cameraInfo);
                String deviceUniqueName = deviceUniqueName(i, cameraInfo);
                JSONObject jSONObject = new JSONObject();
                jSONArray.put(jSONObject);
                Camera camera = null;
                try {
                    Log.e(TAG, "Start open camera:" + i);
                    Camera open = Camera.open(i);
                    try {
                        Log.e(TAG, "success open camera:" + i);
                        Camera.Parameters parameters = open.getParameters();
                        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
                        List<int[]> supportedPreviewFpsRange = parameters.getSupportedPreviewFpsRange();
                        Log.d(TAG, deviceUniqueName);
                        if (open != null) {
                            open.release();
                        }
                        JSONArray jSONArray2 = new JSONArray();
                        for (Camera.Size next : supportedPreviewSizes) {
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put("width", next.width);
                            jSONObject2.put("height", next.height);
                            jSONArray2.put(jSONObject2);
                        }
                        JSONArray jSONArray3 = new JSONArray();
                        for (int[] next2 : supportedPreviewFpsRange) {
                            JSONObject jSONObject3 = new JSONObject();
                            jSONObject3.put("min_mfps", next2[0]);
                            jSONObject3.put("max_mfps", next2[1]);
                            jSONArray3.put(jSONObject3);
                        }
                        jSONObject.put("name", deviceUniqueName);
                        jSONObject.put("front_facing", isFrontFacing(cameraInfo)).put("orientation", cameraInfo.orientation).put("sizes", jSONArray2).put("mfpsRanges", jSONArray3);
                    } catch (RuntimeException e) {
                        e = e;
                        camera = open;
                        try {
                            Log.e(TAG, "Failed to open " + deviceUniqueName + ", skipping", e);
                            if (camera == null) {
                            }
                        } catch (Throwable th) {
                            th = th;
                            if (camera != null) {
                                camera.release();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        camera = open;
                        if (camera != null) {
                        }
                        throw th;
                    }
                } catch (RuntimeException e2) {
                    e = e2;
                    Log.e(TAG, "Failed to open " + deviceUniqueName + ", skipping", e);
                    if (camera == null) {
                        camera.release();
                    }
                }
            }
            return jSONArray.toString(2);
        } catch (JSONException e3) {
            throw new RuntimeException(e3);
        }
    }

    private static boolean isFrontFacing(Camera.CameraInfo cameraInfo) {
        return cameraInfo.facing == 1;
    }
}
