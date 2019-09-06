package com.bumptech.glide.request.target;

import android.os.Handler.Callback;
import android.os.Message;

/* compiled from: PreloadTarget */
class k implements Callback {
    k() {
    }

    public boolean handleMessage(Message message) {
        if (message.what != 1) {
            return false;
        }
        ((l) message.obj).clear();
        return true;
    }
}
