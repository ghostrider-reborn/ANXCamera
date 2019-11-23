package com.bumptech.glide.manager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.manager.c;
import com.bumptech.glide.util.i;

/* compiled from: DefaultConnectivityMonitor */
final class e implements c {
    private static final String TAG = "ConnectivityMonitor";
    private final Context context;
    boolean isConnected;
    final c.a nU;
    private boolean nV;
    private final BroadcastReceiver nW = new BroadcastReceiver() {
        public void onReceive(@NonNull Context context, Intent intent) {
            boolean z = e.this.isConnected;
            e.this.isConnected = e.this.isConnected(context);
            if (z != e.this.isConnected) {
                if (Log.isLoggable(e.TAG, 3)) {
                    Log.d(e.TAG, "connectivity changed, isConnected: " + e.this.isConnected);
                }
                e.this.nU.d(e.this.isConnected);
            }
        }
    };

    e(@NonNull Context context2, @NonNull c.a aVar) {
        this.context = context2.getApplicationContext();
        this.nU = aVar;
    }

    private void register() {
        if (!this.nV) {
            this.isConnected = isConnected(this.context);
            try {
                this.context.registerReceiver(this.nW, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                this.nV = true;
            } catch (SecurityException e) {
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "Failed to register", e);
                }
            }
        }
    }

    private void unregister() {
        if (this.nV) {
            this.context.unregisterReceiver(this.nW);
            this.nV = false;
        }
    }

    /* access modifiers changed from: package-private */
    @SuppressLint({"MissingPermission"})
    public boolean isConnected(@NonNull Context context2) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) i.checkNotNull((ConnectivityManager) context2.getSystemService("connectivity"))).getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (RuntimeException e) {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Failed to determine connectivity status when connectivity changed", e);
            }
            return true;
        }
    }

    public void onDestroy() {
    }

    public void onStart() {
        register();
    }

    public void onStop() {
        unregister();
    }
}
