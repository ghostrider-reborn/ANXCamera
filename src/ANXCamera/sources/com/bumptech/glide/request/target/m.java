package com.bumptech.glide.request.target;

import android.support.annotation.NonNull;
import com.bumptech.glide.util.l;

/* compiled from: SimpleTarget */
public abstract class m<Z> extends b<Z> {
    private final int height;
    private final int width;

    public m() {
        this(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public m(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public void a(@NonNull n nVar) {
    }

    public final void b(@NonNull n nVar) {
        if (l.o(this.width, this.height)) {
            nVar.b(this.width, this.height);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Width and height must both be > 0 or Target#SIZE_ORIGINAL, but given width: ");
        sb.append(this.width);
        sb.append(" and height: ");
        sb.append(this.height);
        sb.append(", either provide dimensions in the constructor or call override()");
        throw new IllegalArgumentException(sb.toString());
    }
}
