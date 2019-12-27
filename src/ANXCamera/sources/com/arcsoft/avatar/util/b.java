package com.arcsoft.avatar.util;

import android.util.Log;

/* compiled from: LogUtil */
public class b {

    /* renamed from: a  reason: collision with root package name */
    private boolean f165a = false;

    /* renamed from: b  reason: collision with root package name */
    private String f166b;

    public b(String str) {
        this.f166b = str;
    }

    public void a(String str) {
        if (this.f165a) {
            Log.d(this.f166b, str);
        }
    }

    public void b(String str) {
        if (this.f165a) {
            Log.i(this.f166b, str);
        }
    }

    public void c(String str) {
        Log.e(this.f166b, str);
    }
}
