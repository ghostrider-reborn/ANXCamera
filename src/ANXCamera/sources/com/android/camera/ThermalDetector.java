package com.android.camera;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ThermalDetector {
    public static String ACTION_TEMP_CHANGED = "action_temp_state_change";
    public static String EXTRA_TEMP_STAGE = "temp_state";
    public static final int GETSTAGE_CLOSE_BOTH = 3;
    public static final int STAGE_CLOSE_FRONT = 2;
    public static final int STAGE_CONTRAINT = 1;
    public static final int STAGE_FREE = 0;
    public static final int STAGE_INVALID = -1;
    /* access modifiers changed from: private */
    public static final String TAG = ThermalDetector.class.getSimpleName();
    public static String TEMP_STAGE_NODE = "/sys/class/thermal/thermal_message/temp_state";
    private Context mContext;
    private IntentFilter mFilter;
    private boolean mIsRegister;
    private BroadcastReceiver mReceiver;
    /* access modifiers changed from: private */
    public volatile int mTempStage;

    private static class InstanceHolder {
        /* access modifiers changed from: private */
        public static ThermalDetector sInstance = new ThermalDetector();

        private InstanceHolder() {
        }
    }

    private ThermalDetector() {
        this.mTempStage = 0;
        this.mIsRegister = false;
        this.mFilter = new IntentFilter(ACTION_TEMP_CHANGED);
        this.mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (intent != null && TextUtils.equals(intent.getAction(), ThermalDetector.ACTION_TEMP_CHANGED)) {
                    int intExtra = intent.getIntExtra(ThermalDetector.EXTRA_TEMP_STAGE, -1);
                    String access$200 = ThermalDetector.TAG;
                    Log.d(access$200, "onReceive stage = " + intExtra);
                    if (ThermalDetector.this.mTempStage != intExtra) {
                        int unused = ThermalDetector.this.mTempStage = intExtra;
                        ThermalDetector.this.onThermalNotification(ThermalDetector.this.mTempStage);
                    }
                }
            }
        };
    }

    public static ThermalDetector getInstance() {
        return InstanceHolder.sInstance;
    }

    public static boolean isTempStageFree(int i) {
        return i == 0 || i == -1;
    }

    /* access modifiers changed from: private */
    public void onThermalNotification(int i) {
        String str = TAG;
        Log.d(str, "onThermalNotification stage=" + i);
        if (i != -1) {
            ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
            if (configChanges == null) {
                Log.w(TAG, "onThermalNotification config is null");
                return;
            }
            try {
                configChanges.onThermalNotification(i);
            } catch (Exception e) {
                Log.w(TAG, "onThermalNotification error", e);
            }
        }
    }

    private static int readStageFromFile() {
        String str;
        int parseInt;
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(TEMP_STAGE_NODE))));
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                stringBuffer.append(readLine);
            }
            str = stringBuffer.toString();
            try {
                bufferedReader.close();
            } catch (IOException e) {
            }
        } catch (IOException e2) {
            str = "";
            Log.e(TAG, "readStageFromFile IOException");
            parseInt = Integer.parseInt(str);
            String str2 = TAG;
            Log.d(str2, "readStageFromFile value = " + parseInt);
            if (parseInt >= 0) {
            }
            return 0;
        } catch (Throwable th) {
            r2.addSuppressed(th);
        }
        try {
            parseInt = Integer.parseInt(str);
            String str22 = TAG;
            Log.d(str22, "readStageFromFile value = " + parseInt);
            if (parseInt >= 0 || parseInt > 3) {
                return 0;
            }
            return parseInt;
        } catch (Exception e3) {
            Log.d(TAG, "failed to readStageFromFile ", e3);
            return 0;
        }
        throw th;
    }

    public static boolean thermalConstrained(int i) {
        return i == 1;
    }

    @WorkerThread
    public void onCreate(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void onDestroy() {
        this.mContext = null;
    }

    public void onThermalNotification() {
        onThermalNotification(this.mTempStage);
    }

    public void registerReceiver() {
        if (this.mContext != null && !this.mIsRegister) {
            this.mContext.registerReceiver(this.mReceiver, this.mFilter);
            this.mIsRegister = true;
        }
    }

    public boolean thermalConstrained() {
        return this.mTempStage == 1;
    }

    public void unregisterReceiver() {
        if (this.mContext != null && this.mIsRegister) {
            this.mContext.unregisterReceiver(this.mReceiver);
            this.mIsRegister = false;
        }
    }
}
