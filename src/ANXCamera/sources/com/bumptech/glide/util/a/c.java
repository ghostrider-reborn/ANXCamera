package com.bumptech.glide.util.a;

import android.support.annotation.NonNull;

/* compiled from: StateVerifier */
public abstract class c {
    private static final boolean DEBUG = false;

    /* compiled from: StateVerifier */
    private static class a extends c {
        private volatile RuntimeException rm;

        a() {
            super();
        }

        public void fw() {
            if (this.rm != null) {
                throw new IllegalStateException("Already released", this.rm);
            }
        }

        /* access modifiers changed from: package-private */
        public void o(boolean z) {
            if (z) {
                this.rm = new RuntimeException("Released");
            } else {
                this.rm = null;
            }
        }
    }

    /* compiled from: StateVerifier */
    private static class b extends c {
        private volatile boolean gC;

        b() {
            super();
        }

        public void fw() {
            if (this.gC) {
                throw new IllegalStateException("Already released");
            }
        }

        public void o(boolean z) {
            this.gC = z;
        }
    }

    private c() {
    }

    @NonNull
    public static c fv() {
        return new b();
    }

    public abstract void fw();

    /* access modifiers changed from: package-private */
    public abstract void o(boolean z);
}
