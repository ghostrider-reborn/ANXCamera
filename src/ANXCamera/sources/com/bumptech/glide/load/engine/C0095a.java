package com.bumptech.glide.load.engine;

import android.os.Handler.Callback;
import android.os.Message;

/* renamed from: com.bumptech.glide.load.engine.a reason: case insensitive filesystem */
/* compiled from: ActiveResources */
class C0095a implements Callback {
    final /* synthetic */ ActiveResources this$0;

    C0095a(ActiveResources activeResources) {
        this.this$0 = activeResources;
    }

    public boolean handleMessage(Message message) {
        if (message.what != 1) {
            return false;
        }
        this.this$0.a((ResourceWeakReference) message.obj);
        return true;
    }
}
