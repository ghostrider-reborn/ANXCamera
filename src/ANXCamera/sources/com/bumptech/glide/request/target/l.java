package com.bumptech.glide.request.target;

import android.support.annotation.NonNull;
import com.bumptech.glide.util.k;

/* compiled from: SimpleTarget */
public abstract class l<Z> extends b<Z> {
    private final int height;
    private final int width;

    public l() {
        this(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public l(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public final void a(@NonNull m mVar) {
        if (k.t(this.width, this.height)) {
            mVar.q(this.width, this.height);
            return;
        }
        throw new IllegalArgumentException("Width and height must both be > 0 or Target#SIZE_ORIGINAL, but given width: " + this.width + " and height: " + this.height + ", either provide dimensions in the constructor or call override()");
    }

    public void b(@NonNull m mVar) {
    }
}
