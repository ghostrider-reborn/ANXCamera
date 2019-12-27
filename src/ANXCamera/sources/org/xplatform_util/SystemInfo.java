package org.xplatform_util;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.provider.MiuiSettings;
import java.io.File;
import java.util.ArrayList;

public class SystemInfo {
    public static WifiInfo[] getAvailableWifiInfo(Context context) {
        ArrayList arrayList = new ArrayList();
        new StringBuilder();
        for (ScanResult next : ((WifiManager) context.getSystemService(MiuiSettings.System.WIFI_SHARE)).getScanResults()) {
            WifiInfo wifiInfo = new WifiInfo();
            wifiInfo.SSID = next.SSID;
            wifiInfo.BSSID = next.BSSID;
            arrayList.add(wifiInfo);
        }
        return (WifiInfo[]) arrayList.toArray(new WifiInfo[arrayList.size()]);
    }

    public static WifiInfo getCurrentWifiInfo(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(MiuiSettings.System.WIFI_SHARE);
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
        if (context == null) {
            return "";
        }
        File filesDir = context.getFilesDir();
        return filesDir == null ? "" : filesDir.toString();
    }
}
