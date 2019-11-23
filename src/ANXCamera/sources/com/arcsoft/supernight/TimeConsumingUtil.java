package com.arcsoft.supernight;

import java.util.HashMap;

public class TimeConsumingUtil {
    public static boolean DEBUG = false;

    /* renamed from: a  reason: collision with root package name */
    private static HashMap<String, Long> f96a = new HashMap<>();

    /* renamed from: b  reason: collision with root package name */
    private static final String f97b = "Performance";

    public static void startTheTimer(String str) {
        if (DEBUG && f96a != null) {
            f96a.put(str, Long.valueOf(System.currentTimeMillis()));
        }
    }

    public static void stopTiming(String str) {
        if (DEBUG && f96a != null && f96a.containsKey(str)) {
            long longValue = f96a.get(str).longValue();
            LOG.d(f97b, "" + str + " : " + (System.currentTimeMillis() - longValue) + " ms");
        }
    }

    public static void stopTiming(String str, String str2) {
        if (DEBUG && f96a != null && f96a.containsKey(str2)) {
            long longValue = f96a.get(str2).longValue();
            LOG.d(str, "" + str2 + " : " + (System.currentTimeMillis() - longValue));
        }
    }
}
