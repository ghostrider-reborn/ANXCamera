package org.webrtc.videoengine;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.Log;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VideoCaptureDeviceInfoAndroid {
    private static final String TAG = "WEBRTC-JC-VideoCaptureDeviceInfoAndroid";

    private static String deviceUniqueName(int i, CameraInfo cameraInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("Camera ");
        sb.append(i);
        sb.append(", Facing ");
        sb.append(isFrontFacing(cameraInfo) ? "front" : "back");
        sb.append(", Orientation ");
        sb.append(cameraInfo.orientation);
        return sb.toString();
    }

    private static String getDeviceInfo() {
        Camera camera;
        String str = TAG;
        try {
            JSONArray jSONArray = new JSONArray();
            int numberOfCameras = Camera.getNumberOfCameras();
            Log.d(str, "Number of cameras:");
            for (int i = 0; i < numberOfCameras; i++) {
                CameraInfo cameraInfo = new CameraInfo();
                Camera.getCameraInfo(i, cameraInfo);
                String deviceUniqueName = deviceUniqueName(i, cameraInfo);
                JSONObject jSONObject = new JSONObject();
                jSONArray.put(jSONObject);
                camera = null;
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Start open camera:");
                    sb.append(i);
                    Log.e(str, sb.toString());
                    camera = Camera.open(i);
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("success open camera:");
                    sb2.append(i);
                    Log.e(str, sb2.toString());
                    Parameters parameters = camera.getParameters();
                    List<Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
                    List<int[]> supportedPreviewFpsRange = parameters.getSupportedPreviewFpsRange();
                    Log.d(str, deviceUniqueName);
                    if (camera != null) {
                        camera.release();
                    }
                    JSONArray jSONArray2 = new JSONArray();
                    for (Size size : supportedPreviewSizes) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("width", size.width);
                        jSONObject2.put("height", size.height);
                        jSONArray2.put(jSONObject2);
                    }
                    JSONArray jSONArray3 = new JSONArray();
                    for (int[] iArr : supportedPreviewFpsRange) {
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("min_mfps", iArr[0]);
                        jSONObject3.put("max_mfps", iArr[1]);
                        jSONArray3.put(jSONObject3);
                    }
                    jSONObject.put("name", deviceUniqueName);
                    jSONObject.put("front_facing", isFrontFacing(cameraInfo)).put("orientation", cameraInfo.orientation).put("sizes", jSONArray2).put("mfpsRanges", jSONArray3);
                } catch (RuntimeException e2) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Failed to open ");
                    sb3.append(deviceUniqueName);
                    sb3.append(", skipping");
                    Log.e(str, sb3.toString(), e2);
                    if (camera != null) {
                        camera.release();
                    }
                }
            }
            return jSONArray.toString(2);
        } catch (JSONException e3) {
            throw new RuntimeException(e3);
        } catch (Throwable th) {
            if (camera != null) {
                camera.release();
            }
            throw th;
        }
    }

    private static boolean isFrontFacing(CameraInfo cameraInfo) {
        return cameraInfo.facing == 1;
    }
}
