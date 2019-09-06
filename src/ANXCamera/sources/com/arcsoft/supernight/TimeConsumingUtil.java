package com.arcsoft.supernight;

import java.util.HashMap;

public class TimeConsumingUtil {
    public static boolean DEBUG = false;

    /* renamed from: a reason: collision with root package name */
    private static HashMap<String, Long> f236a = new HashMap<>();

    /* renamed from: b reason: collision with root package name */
    private static final String f237b = "Performance";

    public static void startTheTimer(String str) {
        if (DEBUG) {
            HashMap<String, Long> hashMap = f236a;
            if (hashMap != null) {
                hashMap.put(str, Long.valueOf(System.currentTimeMillis()));
            }
        }
    }

    public static void stopTiming(String str) {
        if (DEBUG) {
            HashMap<String, Long> hashMap = f236a;
            if (hashMap != null && hashMap.containsKey(str)) {
                long longValue = ((Long) f236a.get(str)).longValue();
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(str);
                sb.append(" : ");
                sb.append(System.currentTimeMillis() - longValue);
                sb.append(" ms");
                LOG.d(f237b, sb.toString());
            }
        }
    }

    public static void stopTiming(String str, String str2) {
        if (DEBUG) {
            HashMap<String, Long> hashMap = f236a;
            if (hashMap != null && hashMap.containsKey(str2)) {
                long longValue = ((Long) f236a.get(str2)).longValue();
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(str2);
                sb.append(" : ");
                sb.append(System.currentTimeMillis() - longValue);
                LOG.d(str, sb.toString());
            }
        }
    }
}
