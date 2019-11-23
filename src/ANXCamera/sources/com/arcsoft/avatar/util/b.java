package com.arcsoft.avatar.util;

import android.util.Log;

/* compiled from: LogUtil */
public class b {

    /* renamed from: a  reason: collision with root package name */
    private boolean f61a = false;

    /* renamed from: b  reason: collision with root package name */
    private String f62b;

    public b(String str) {
        this.f62b = str;
    }

    public void a(String str) {
        if (this.f61a) {
            Log.d(this.f62b, str);
        }
    }

    public void b(String str) {
        if (this.f61a) {
            Log.i(this.f62b, str);
        }
    }

    public void c(String str) {
        Log.e(this.f62b, str);
    }
}
