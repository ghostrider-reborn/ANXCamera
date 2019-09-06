package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.h;
import com.bumptech.glide.util.c;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: StreamBitmapDecoder */
public class x implements h<InputStream, Bitmap> {
    private final b Yd;
    private final o wi;

    /* compiled from: StreamBitmapDecoder */
    static class a implements com.bumptech.glide.load.resource.bitmap.o.a {
        private final RecyclableBufferedInputStream Zd;
        private final c rj;

        a(RecyclableBufferedInputStream recyclableBufferedInputStream, c cVar) {
            this.Zd = recyclableBufferedInputStream;
            this.rj = cVar;
        }

        public void a(d dVar, Bitmap bitmap) throws IOException {
            IOException exception = this.rj.getException();
            if (exception != null) {
                if (bitmap != null) {
                    dVar.a(bitmap);
                }
                throw exception;
            }
        }

        public void t() {
            this.Zd.Kj();
        }
    }

    public x(o oVar, b bVar) {
        this.wi = oVar;
        this.Yd = bVar;
    }

    /* renamed from: a */
    public A<Bitmap> b(@NonNull InputStream inputStream, int i, int i2, @NonNull g gVar) throws IOException {
        RecyclableBufferedInputStream recyclableBufferedInputStream;
        boolean z;
        if (inputStream instanceof RecyclableBufferedInputStream) {
            recyclableBufferedInputStream = (RecyclableBufferedInputStream) inputStream;
            z = false;
        } else {
            RecyclableBufferedInputStream recyclableBufferedInputStream2 = new RecyclableBufferedInputStream(inputStream, this.Yd);
            z = true;
            recyclableBufferedInputStream = recyclableBufferedInputStream2;
        }
        c h = c.h(recyclableBufferedInputStream);
        try {
            return this.wi.a((InputStream) new com.bumptech.glide.util.g(h), i, i2, gVar, (com.bumptech.glide.load.resource.bitmap.o.a) new a(recyclableBufferedInputStream, h));
        } finally {
            h.release();
            if (z) {
                recyclableBufferedInputStream.release();
            }
        }
    }

    public boolean a(@NonNull InputStream inputStream, @NonNull g gVar) {
        return this.wi.f(inputStream);
    }
}
