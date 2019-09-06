package org.xplatform_util;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import java.io.File;
import java.util.ArrayList;

public class SystemInfo {
    public static WifiInfo[] getAvailableWifiInfo(Context context) {
        ArrayList arrayList = new ArrayList();
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        new StringBuilder();
        for (ScanResult scanResult : wifiManager.getScanResults()) {
            WifiInfo wifiInfo = new WifiInfo();
            wifiInfo.SSID = scanResult.SSID;
            wifiInfo.BSSID = scanResult.BSSID;
            arrayList.add(wifiInfo);
        }
        return (WifiInfo[]) arrayList.toArray(new WifiInfo[arrayList.size()]);
    }

    public static WifiInfo getCurrentWifiInfo(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        WifiInfo connectionInfo = wifiManager == null ? null : wifiManager.getConnectionInfo();
        WifiInfo wifiInfo = new WifiInfo();
        if (connectionInfo != null) {
            wifiInfo.BSSID = String.valueOf(connectionInfo.getBSSID());
            wifiInfo.SSID = String.valueOf(connectionInfo.getSSID());
        }
        return wifiInfo;
    }

    static String getStoragePath(Context context) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        String str = "";
        if (context == null) {
            return str;
        }
        File filesDir = context.getFilesDir();
        return filesDir == null ? str : filesDir.toString();
    }
}
