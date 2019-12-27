package com.bumptech.glide.load.a;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.a.e;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.resource.bitmap.RecyclableBufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: InputStreamRewinder */
public final class l implements e<InputStream> {
    private static final int _d = 5242880;
    private final RecyclableBufferedInputStream Zd;

    /* compiled from: InputStreamRewinder */
    public static final class a implements e.a<InputStream> {
        private final b Yd;

        public a(b bVar) {
            this.Yd = bVar;
        }

        @NonNull
        public Class<InputStream> M() {
            return InputStream.class;
        }

        @NonNull
        /* renamed from: e */
        public e<InputStream> build(InputStream inputStream) {
            return new l(inputStream, this.Yd);
        }
    }

    l(InputStream inputStream, b bVar) {
        this.Zd = new RecyclableBufferedInputStream(inputStream, bVar);
        this.Zd.mark(_d);
    }

    @NonNull
    public InputStream C() throws IOException {
        this.Zd.reset();
        return this.Zd;
    }

    public void cleanup() {
        this.Zd.release();
    }
}
