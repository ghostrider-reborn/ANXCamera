package com.bumptech.glide.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

/* compiled from: DefaultConnectivityMonitor */
class e extends BroadcastReceiver {
    final /* synthetic */ f this$0;

    e(f fVar) {
        this.this$0 = fVar;
    }

    public void onReceive(@NonNull Context context, Intent intent) {
        f fVar = this.this$0;
        boolean z = fVar.isConnected;
        fVar.isConnected = fVar.isConnected(context);
        if (z != this.this$0.isConnected) {
            String str = "ConnectivityMonitor";
            if (Log.isLoggable(str, 3)) {
                StringBuilder sb = new StringBuilder();
                sb.append("connectivity changed, isConnected: ");
                sb.append(this.this$0.isConnected);
                Log.d(str, sb.toString());
            }
            f fVar2 = this.this$0;
            fVar2.listener.d(fVar2.isConnected);
        }
    }
}
