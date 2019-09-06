package com.bumptech.glide;

import android.support.annotation.NonNull;
import com.bumptech.glide.n;
import com.bumptech.glide.request.a.e;
import com.bumptech.glide.request.a.g;
import com.bumptech.glide.request.a.h;
import com.bumptech.glide.request.a.j.a;
import com.bumptech.glide.util.i;

/* compiled from: TransitionOptions */
public abstract class n<CHILD extends n<CHILD, TranscodeType>, TranscodeType> implements Cloneable {
    private g<? super TranscodeType> Mc = e.getFactory();

    private CHILD self() {
        return this;
    }

    @NonNull
    public final CHILD Uf() {
        return a(e.getFactory());
    }

    /* access modifiers changed from: 0000 */
    public final g<? super TranscodeType> Vf() {
        return this.Mc;
    }

    @NonNull
    public final CHILD a(@NonNull g<? super TranscodeType> gVar) {
        i.checkNotNull(gVar);
        this.Mc = gVar;
        self();
        return this;
    }

    @NonNull
    public final CHILD a(@NonNull a aVar) {
        return a((g<? super TranscodeType>) new com.bumptech.glide.request.a.i<Object>(aVar));
    }

    public final CHILD clone() {
        try {
            return (n) super.clone();
        } catch (CloneNotSupportedException e2) {
            throw new RuntimeException(e2);
        }
    }

    @NonNull
    public final CHILD k(int i) {
        return a((g<? super TranscodeType>) new h<Object>(i));
    }
}
