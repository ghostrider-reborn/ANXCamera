package com.bumptech.glide.load.engine.b;

import android.util.Log;
import com.bumptech.glide.load.engine.b.b.C0008b;

/* compiled from: GlideExecutor */
class d implements C0008b {
    d() {
    }

    public void b(Throwable th) {
        if (th != null) {
            String str = "GlideExecutor";
            if (Log.isLoggable(str, 6)) {
                Log.e(str, "Request threw uncaught throwable", th);
            }
        }
    }
}
