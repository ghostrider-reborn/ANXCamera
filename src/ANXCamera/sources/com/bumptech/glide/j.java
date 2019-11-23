package com.bumptech.glide;

import android.support.annotation.NonNull;
import com.bumptech.glide.j;
import com.bumptech.glide.request.a.e;
import com.bumptech.glide.request.a.g;
import com.bumptech.glide.request.a.h;
import com.bumptech.glide.request.a.j;
import com.bumptech.glide.util.i;

/* compiled from: TransitionOptions */
public abstract class j<CHILD extends j<CHILD, TranscodeType>, TranscodeType> implements Cloneable {
    private g<? super TranscodeType> cV = e.fi();

    private CHILD aq() {
        return this;
    }

    @NonNull
    public final CHILD an() {
        return b(e.fi());
    }

    /* renamed from: ao */
    public final CHILD clone() {
        try {
            return (j) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: package-private */
    public final g<? super TranscodeType> ap() {
        return this.cV;
    }

    @NonNull
    public final CHILD b(@NonNull g<? super TranscodeType> gVar) {
        this.cV = (g) i.checkNotNull(gVar);
        return aq();
    }

    @NonNull
    public final CHILD b(@NonNull j.a aVar) {
        return b(new com.bumptech.glide.request.a.i(aVar));
    }

    @NonNull
    public final CHILD k(int i) {
        return b(new h(i));
    }
}
