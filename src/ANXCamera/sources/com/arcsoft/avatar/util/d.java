package com.arcsoft.avatar.util;

import java.util.HashMap;

/* compiled from: TimeConsumingUtil */
public class d {

    /* renamed from: a reason: collision with root package name */
    public static boolean f167a = false;

    /* renamed from: b reason: collision with root package name */
    private static HashMap<String, Long> f168b = new HashMap<>();

    /* renamed from: c reason: collision with root package name */
    private static final String f169c = "PERFORMANCE";

    public static void a(String str) {
        if (f167a) {
            HashMap<String, Long> hashMap = f168b;
            if (hashMap != null) {
                hashMap.put(str, Long.valueOf(System.currentTimeMillis()));
            }
        }
    }

    public static void a(String str, String str2) {
        if (f167a) {
            HashMap<String, Long> hashMap = f168b;
            if (hashMap != null && hashMap.containsKey(str2)) {
                long longValue = ((Long) f168b.get(str2)).longValue();
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(str2);
                sb.append(" : ");
                sb.append(System.currentTimeMillis() - longValue);
                LOG.d(str, sb.toString());
            }
        }
    }

    public static void b(String str) {
        if (f167a) {
            HashMap<String, Long> hashMap = f168b;
            if (hashMap != null && hashMap.containsKey(str)) {
                long longValue = ((Long) f168b.get(str)).longValue();
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(str);
                sb.append(" : ");
                sb.append(System.currentTimeMillis() - longValue);
                LOG.d(f169c, sb.toString());
            }
        }
    }
}
