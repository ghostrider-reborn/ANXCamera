package com.bumptech.glide.e;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.bumptech.glide.load.c;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: ApplicationVersionSignature */
public final class a {
    private static final String TAG = "AppVersionSignature";
    private static final ConcurrentMap<String, c> qL = new ConcurrentHashMap();

    private a() {
    }

    @NonNull
    private static String a(@Nullable PackageInfo packageInfo) {
        return packageInfo != null ? String.valueOf(packageInfo.versionCode) : UUID.randomUUID().toString();
    }

    @NonNull
    public static c n(@NonNull Context context) {
        String packageName = context.getPackageName();
        c cVar = (c) qL.get(packageName);
        if (cVar != null) {
            return cVar;
        }
        c o = o(context);
        c putIfAbsent = qL.putIfAbsent(packageName, o);
        return putIfAbsent == null ? o : putIfAbsent;
    }

    @NonNull
    private static c o(@NonNull Context context) {
        return new d(a(p(context)));
    }

    @Nullable
    private static PackageInfo p(@NonNull Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Cannot resolve info for" + context.getPackageName(), e);
            return null;
        }
    }

    @VisibleForTesting
    static void reset() {
        qL.clear();
    }
}
