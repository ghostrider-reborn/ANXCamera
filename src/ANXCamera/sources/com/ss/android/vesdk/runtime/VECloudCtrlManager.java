package com.ss.android.vesdk.runtime;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import com.ss.android.ttve.common.TECloudCtrlInvoker;
import com.ss.android.ttve.common.TESpdLogManager;
import com.ss.android.vesdk.VELogUtil;
import com.ss.android.vesdk.runtime.persistence.VESP;
import java.text.SimpleDateFormat;
import miui.reflect.Field;
import org.json.JSONObject;

public class VECloudCtrlManager {
    private static String[] COMMANDS = {"vesdk_log_command"};
    private static String TAG = "VECloudCtrlManager";
    private static volatile VECloudCtrlManager mTECloudCtrlManager = null;
    private TECloudCtrlInvoker mCloudCtrlInvoker;
    private boolean mLogStatus;
    private String mWorkpsace;

    private VECloudCtrlManager() {
        this.mLogStatus = false;
        this.mWorkpsace = Environment.getExternalStorageDirectory().toString();
        this.mLogStatus = false;
        this.mCloudCtrlInvoker = new TECloudCtrlInvoker();
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0020 A[Catch:{ Exception -> 0x017f }] */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x002b A[Catch:{ Exception -> 0x017f }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00e0 A[Catch:{ Exception -> 0x017f }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00e3 A[Catch:{ Exception -> 0x017f }] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0159 A[Catch:{ Exception -> 0x017f }] */
    private int doCommand(@NonNull String str, @NonNull String str2) {
        boolean z;
        boolean z2;
        try {
            JSONObject jSONObject = new JSONObject(str2);
            if (str.hashCode() == 2043549244) {
                if (str.equals("vesdk_log_command")) {
                    z = false;
                    if (!z) {
                        VELogUtil.e(TAG, " json contail invalid command ");
                        return -2;
                    }
                    String str3 = "false";
                    String str4 = "2018-12-08 00:00:00";
                    String str5 = "2018-12-08 00:00:00";
                    String str6 = "";
                    String str7 = "";
                    if (jSONObject.has("enable")) {
                        str3 = jSONObject.getString("enable");
                    }
                    if (jSONObject.has("starttime")) {
                        str4 = jSONObject.getString("starttime");
                    }
                    if (jSONObject.has("endtime")) {
                        str5 = jSONObject.getString("endtime");
                    }
                    if (jSONObject.has("level")) {
                        str6 = jSONObject.getString("level");
                    }
                    if (jSONObject.has("sign")) {
                        str7 = jSONObject.getString("sign");
                    }
                    if (!this.mCloudCtrlInvoker.verifyJson(str + str3 + str4 + str5 + str6, str7)) {
                        storeCommand(str, "");
                        VELogUtil.e(TAG, "Cloud Ctrl Command Json is doctored");
                        return -1;
                    }
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    long time = simpleDateFormat.parse(str4).getTime();
                    long time2 = simpleDateFormat.parse(str5).getTime();
                    long currentTimeMillis = System.currentTimeMillis();
                    TESpdLogManager.InfoLevel infoLevel = TESpdLogManager.InfoLevel.LEVEL3;
                    if (str6.hashCode() == 68) {
                        if (str6.equals(Field.DOUBLE_SIGNATURE_PRIMITIVE)) {
                            z2 = false;
                            TESpdLogManager.InfoLevel infoLevel2 = !z2 ? TESpdLogManager.InfoLevel.LEVEL3 : TESpdLogManager.InfoLevel.LEVEL0;
                            if (this.mLogStatus && str3.equals("true") && currentTimeMillis >= time && currentTimeMillis < time2) {
                                String str8 = this.mWorkpsace + "/vesdklog";
                                VELogUtil.d(TAG, str8);
                                if (TESpdLogManager.getInstance().initSpdLog(str8, infoLevel2.ordinal(), 3) < 0) {
                                    VELogUtil.e(TAG, " TESpdLog init fail " + r13);
                                    return -3;
                                }
                                TESpdLogManager.getInstance().setLevel(infoLevel2);
                                this.mLogStatus = true;
                            } else if (str3.equals("false") || (currentTimeMillis < time && currentTimeMillis >= time2)) {
                                if (this.mLogStatus) {
                                    TESpdLogManager.getInstance().close();
                                    this.mLogStatus = false;
                                }
                                VELogUtil.d(TAG, str + " expired");
                                storeCommand(str, "");
                            }
                            return 0;
                        }
                    }
                    z2 = true;
                    if (!z2) {
                    }
                    if (this.mLogStatus) {
                    }
                    if (this.mLogStatus) {
                    }
                    VELogUtil.d(TAG, str + " expired");
                    storeCommand(str, "");
                    return 0;
                }
            }
            z = true;
            if (!z) {
            }
        } catch (Exception e) {
            VELogUtil.e(TAG, " json parse failed " + e.toString());
            storeCommand(str, "");
            return -1;
        }
    }

    public static VECloudCtrlManager getInstance() {
        if (mTECloudCtrlManager == null) {
            synchronized (VECloudCtrlManager.class) {
                if (mTECloudCtrlManager == null) {
                    mTECloudCtrlManager = new VECloudCtrlManager();
                }
            }
        }
        return mTECloudCtrlManager;
    }

    private void storeCommand(@NonNull String str, @NonNull String str2) {
        VESP.getInstance().put(str, str2);
    }

    public void closeCloudControlRes() {
        if (this.mLogStatus) {
            TESpdLogManager.getInstance().close();
            this.mLogStatus = false;
        }
    }

    public void execStoredCommands(@NonNull String str) {
        this.mWorkpsace = str;
        for (String str2 : COMMANDS) {
            String str3 = (String) VESP.getInstance().get(str2, "");
            if (!str3.isEmpty()) {
                doCommand(str2, str3);
            }
        }
    }

    public void storeCloudControlCommand(@NonNull Context context, @NonNull String str) {
        VELogUtil.d(TAG, str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("command");
            VESP.getInstance().init(context);
            storeCommand(string, jSONObject.toString());
        } catch (Exception e) {
            String str2 = TAG;
            VELogUtil.e(str2, " json parse failed " + e.toString());
        }
    }
}
