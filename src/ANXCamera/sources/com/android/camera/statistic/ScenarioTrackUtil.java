package com.android.camera.statistic;

import android.content.Context;
import android.os.SystemClock;
import android.os.statistics.E2EScenario;
import android.os.statistics.E2EScenarioPayload;
import android.os.statistics.E2EScenarioPerfTracer;
import android.os.statistics.E2EScenarioSettings;
import android.provider.MiuiSettings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.android.camera.log.Log;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

public class ScenarioTrackUtil {
    /* access modifiers changed from: private */
    public static final String TAG = ScenarioTrackUtil.class.getSimpleName();
    public static final CameraEventScenario sCaptureTimeScenario = new CameraEventScenario("CaptureTime");
    public static final CameraEventScenario sLaunchTimeScenario = new CameraEventScenario("CameraLaunchTime");
    public static final CameraEventScenario sStartVideoRecordTimeScenario = new CameraEventScenario("StartVideoRecordTime");
    public static final CameraEventScenario sStopVideoRecordTimeScenario = new CameraEventScenario("StopVideoRecordTime");
    private static final CameraEventScenario sSwitchCameraTimeScenario = new CameraEventScenario("SwitchCameraTime");
    public static final CameraEventScenario sSwitchModeTimeScenario = new CameraEventScenario("SwitchModeTime");
    private static final E2EScenarioSettings scenarioSettings = new E2EScenarioSettings();

    public static class CameraEventScenario {
        private static final String CAMERA_PACKAGE = "com.android.camera";
        private static final String CATEGORY_PERFORMANCE = "Performance";
        E2EScenario e2eScenario;
        public volatile boolean isTrackStarted = false;
        String mEventName;

        CameraEventScenario(String str) {
            this.e2eScenario = initE2EScenario(str);
            this.mEventName = str;
        }

        private E2EScenario initE2EScenario(String str) {
            try {
                return new E2EScenario(CAMERA_PACKAGE, CATEGORY_PERFORMANCE, str);
            } catch (Exception e) {
                String access$000 = ScenarioTrackUtil.TAG;
                Log.w(access$000, str + " initializer failed: " + e.getMessage());
                return null;
            }
        }

        public String toString() {
            return this.mEventName;
        }
    }

    static {
        scenarioSettings.setStatisticsMode(7);
        scenarioSettings.setHistoryLimitPerDay(200);
    }

    private static void abortScenario(@NonNull CameraEventScenario cameraEventScenario) {
        if (cameraEventScenario.e2eScenario == null) {
            String str = TAG;
            Log.w(str, "track " + cameraEventScenario.toString() + " event start cancel due to scenario is null!");
        } else if (cameraEventScenario.isTrackStarted) {
            E2EScenarioPerfTracer.abortScenario(cameraEventScenario.e2eScenario);
        }
    }

    private static void beginScenario(@NonNull CameraEventScenario cameraEventScenario) {
        beginScenario(cameraEventScenario, (E2EScenarioPayload) null);
    }

    private static void beginScenario(@NonNull CameraEventScenario cameraEventScenario, @Nullable E2EScenarioPayload e2EScenarioPayload) {
        if (cameraEventScenario.e2eScenario == null) {
            String str = TAG;
            Log.w(str, "track " + cameraEventScenario.toString() + " event start cancel due to scenario is null!");
            return;
        }
        if (cameraEventScenario.isTrackStarted) {
            E2EScenarioPerfTracer.abortScenario(cameraEventScenario.e2eScenario);
        }
        try {
            E2EScenarioPerfTracer.asyncBeginScenario(cameraEventScenario.e2eScenario, scenarioSettings, e2EScenarioPayload);
            cameraEventScenario.isTrackStarted = true;
        } catch (Exception e) {
            String str2 = TAG;
            Log.w(str2, "track " + cameraEventScenario.toString() + " event start failed: " + e.getMessage());
        }
    }

    private static void finishScenario(@NonNull CameraEventScenario cameraEventScenario, @Nullable E2EScenarioPayload e2EScenarioPayload) {
        if (cameraEventScenario.e2eScenario == null) {
            String str = TAG;
            Log.w(str, "track " + cameraEventScenario.toString() + " event end cancel, due to scenario is null!");
        } else if (!cameraEventScenario.isTrackStarted) {
            String str2 = TAG;
            Log.w(str2, "track " + cameraEventScenario.toString() + " event end cancel, due to scenario has not started!");
        } else {
            if (e2EScenarioPayload != null) {
                try {
                    E2EScenarioPerfTracer.finishScenario(cameraEventScenario.e2eScenario, e2EScenarioPayload);
                } catch (Exception e) {
                    String str3 = TAG;
                    Log.w(str3, "track " + cameraEventScenario.toString() + " event end failed: " + e.getMessage());
                    return;
                }
            } else {
                E2EScenarioPerfTracer.finishScenario(cameraEventScenario.e2eScenario);
            }
            cameraEventScenario.isTrackStarted = false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0047  */
    public static void trackAppLunchTimeEnd(@Nullable Map map, Context context) {
        JSONObject jSONObject;
        String string = MiuiSettings.System.getString(context.getContentResolver(), "camera_boost");
        E2EScenarioPayload e2EScenarioPayload = new E2EScenarioPayload();
        if (string != null) {
            boolean z = false;
            try {
                jSONObject = new JSONObject(string);
                try {
                    if (SystemClock.uptimeMillis() - Long.parseLong(jSONObject.optString("time")) < 1000) {
                        z = true;
                    }
                    jSONObject.remove("time");
                } catch (Exception e) {
                    e = e;
                    Log.w(TAG, "Exception", e);
                    if (z) {
                    }
                    e2EScenarioPayload.putAll(map);
                    finishScenario(sLaunchTimeScenario, e2EScenarioPayload);
                }
            } catch (Exception e2) {
                e = e2;
                jSONObject = null;
                Log.w(TAG, "Exception", e);
                if (z) {
                }
                e2EScenarioPayload.putAll(map);
                finishScenario(sLaunchTimeScenario, e2EScenarioPayload);
            }
            if (z) {
                Iterator<String> keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    e2EScenarioPayload.put(next, jSONObject.opt(next));
                }
            }
        }
        e2EScenarioPayload.putAll(map);
        finishScenario(sLaunchTimeScenario, e2EScenarioPayload);
    }

    public static void trackAppLunchTimeStart(@NonNull boolean z) {
        E2EScenarioPayload e2EScenarioPayload = new E2EScenarioPayload();
        e2EScenarioPayload.put("LaunchMode", z ? "COLD" : "HOT");
        beginScenario(sLaunchTimeScenario, e2EScenarioPayload);
    }

    public static void trackCaptureTimeEnd() {
        finishScenario(sCaptureTimeScenario, (E2EScenarioPayload) null);
    }

    public static void trackCaptureTimeStart(@NonNull boolean z, @NonNull int i) {
        E2EScenarioPayload e2EScenarioPayload = new E2EScenarioPayload();
        e2EScenarioPayload.putValues(new Object[]{"CameraID", CameraStatUtil.cameraIdToName(z), "Module", CameraStatUtil.modeIdToName(i)});
        beginScenario(sCaptureTimeScenario, e2EScenarioPayload);
    }

    public static void trackScenarioAbort(@NonNull CameraEventScenario cameraEventScenario) {
        abortScenario(cameraEventScenario);
    }

    public static void trackStartVideoRecordEnd() {
        finishScenario(sStartVideoRecordTimeScenario, (E2EScenarioPayload) null);
    }

    public static void trackStartVideoRecordStart(@NonNull String str, @NonNull boolean z) {
        E2EScenarioPayload e2EScenarioPayload = new E2EScenarioPayload();
        e2EScenarioPayload.putValues(new Object[]{"mode", str, "cameraId", CameraStatUtil.cameraIdToName(z)});
        beginScenario(sStartVideoRecordTimeScenario, e2EScenarioPayload);
    }

    public static void trackStopVideoRecordEnd() {
        finishScenario(sStopVideoRecordTimeScenario, (E2EScenarioPayload) null);
    }

    public static void trackStopVideoRecordStart(@NonNull String str, @NonNull boolean z) {
        E2EScenarioPayload e2EScenarioPayload = new E2EScenarioPayload();
        e2EScenarioPayload.putValues(new Object[]{"mode", str, "cameraId", CameraStatUtil.cameraIdToName(z)});
        beginScenario(sStopVideoRecordTimeScenario, e2EScenarioPayload);
    }

    public static void trackSwitchCameraEnd() {
        finishScenario(sSwitchCameraTimeScenario, (E2EScenarioPayload) null);
    }

    public static void trackSwitchCameraStart(@NonNull boolean z, @NonNull boolean z2, @NonNull int i) {
        E2EScenarioPayload e2EScenarioPayload = new E2EScenarioPayload();
        e2EScenarioPayload.putValues(new Object[]{"from", CameraStatUtil.cameraIdToName(z), "to", CameraStatUtil.cameraIdToName(z2), "inMode", CameraStatUtil.modeIdToName(i)});
        beginScenario(sSwitchCameraTimeScenario, e2EScenarioPayload);
    }

    public static void trackSwitchModeEnd() {
        finishScenario(sSwitchModeTimeScenario, (E2EScenarioPayload) null);
    }

    public static void trackSwitchModeStart(@NonNull int i, @NonNull int i2, @NonNull boolean z) {
        E2EScenarioPayload e2EScenarioPayload = new E2EScenarioPayload();
        e2EScenarioPayload.putValues(new Object[]{"from", CameraStatUtil.modeIdToName(i), "to", CameraStatUtil.modeIdToName(i2), "cameraId", CameraStatUtil.cameraIdToName(z)});
        beginScenario(sSwitchModeTimeScenario, e2EScenarioPayload);
    }
}
