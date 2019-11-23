package com.arcsoft.avatar.util;

import java.util.HashMap;

/* compiled from: TimeConsumingUtil */
public class d {

    /* renamed from: a  reason: collision with root package name */
    public static boolean f63a = false;

    /* renamed from: b  reason: collision with root package name */
    private static HashMap<String, Long> f64b = new HashMap<>();
    private static final String c = "PERFORMANCE";

    public static void a(String str) {
        if (f63a && f64b != null) {
            f64b.put(str, Long.valueOf(System.currentTimeMillis()));
        }
    }

    public static void a(String str, String str2) {
        if (f63a && f64b != null && f64b.containsKey(str2)) {
            long longValue = f64b.get(str2).longValue();
            LOG.d(str, "" + str2 + " : " + (System.currentTimeMillis() - longValue));
        }
    }

    public static void b(String str) {
        if (f63a && f64b != null && f64b.containsKey(str)) {
            long longValue = f64b.get(str).longValue();
            LOG.d(c, "" + str + " : " + (System.currentTimeMillis() - longValue));
        }
    }
}
