package com.bumptech.glide.load.engine.b;

import android.os.Process;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;

/* compiled from: GlideExecutor */
class a extends Thread {
    final /* synthetic */ a this$0;

    a(a aVar, Runnable runnable, String str) {
        this.this$0 = aVar;
        super(runnable, str);
    }

    public void run() {
        Process.setThreadPriority(9);
        if (this.this$0.eh) {
            StrictMode.setThreadPolicy(new Builder().detectNetwork().penaltyDeath().build());
        }
        try {
            super.run();
        } catch (Throwable th) {
            this.this$0.dh.b(th);
        }
    }
}
