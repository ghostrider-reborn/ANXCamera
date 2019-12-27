package com.bumptech.glide.util.a;

import android.support.annotation.NonNull;

/* compiled from: StateVerifier */
public abstract class g {
    private static final boolean DEBUG = false;

    /* compiled from: StateVerifier */
    private static class a extends g {
        private volatile RuntimeException jm;

        a() {
            super();
        }

        public void Mh() {
            if (this.jm != null) {
                throw new IllegalStateException("Already released", this.jm);
            }
        }

        /* access modifiers changed from: package-private */
        public void u(boolean z) {
            if (z) {
                this.jm = new RuntimeException("Released");
            } else {
                this.jm = null;
            }
        }
    }

    /* compiled from: StateVerifier */
    private static class b extends g {
        private volatile boolean Me;

        b() {
            super();
        }

        public void Mh() {
            if (this.Me) {
                throw new IllegalStateException("Already released");
            }
        }

        public void u(boolean z) {
            this.Me = z;
        }
    }

    private g() {
    }

    @NonNull
    public static g newInstance() {
        return new b();
    }

    public abstract void Mh();

    /* access modifiers changed from: package-private */
    public abstract void u(boolean z);
}
