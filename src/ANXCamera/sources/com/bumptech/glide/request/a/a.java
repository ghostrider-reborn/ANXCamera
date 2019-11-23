package com.bumptech.glide.request.a;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.request.a.f;

/* compiled from: BitmapContainerTransitionFactory */
public abstract class a<R> implements g<R> {
    private final g<Drawable> qz;

    /* renamed from: com.bumptech.glide.request.a.a$a  reason: collision with other inner class name */
    /* compiled from: BitmapContainerTransitionFactory */
    private final class C0011a implements f<R> {
        private final f<Drawable> qA;

        C0011a(f<Drawable> fVar) {
            this.qA = fVar;
        }

        public boolean a(R r, f.a aVar) {
            return this.qA.a(new BitmapDrawable(aVar.getView().getResources(), a.this.B(r)), aVar);
        }
    }

    public a(g<Drawable> gVar) {
        this.qz = gVar;
    }

    /* access modifiers changed from: protected */
    public abstract Bitmap B(R r);

    public f<R> a(DataSource dataSource, boolean z) {
        return new C0011a(this.qz.a(dataSource, z));
    }
}
