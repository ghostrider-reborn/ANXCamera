package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.engine.p;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.resource.bitmap.n;
import com.bumptech.glide.util.c;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: StreamBitmapDecoder */
public class v implements g<InputStream, Bitmap> {
    private final b ff;
    private final n lD;

    /* compiled from: StreamBitmapDecoder */
    static class a implements n.a {
        private final RecyclableBufferedInputStream fe;
        private final c mK;

        a(RecyclableBufferedInputStream recyclableBufferedInputStream, c cVar) {
            this.fe = recyclableBufferedInputStream;
            this.mK = cVar;
        }

        public void a(d dVar, Bitmap bitmap) throws IOException {
            IOException fm = this.mK.fm();
            if (fm != null) {
                if (bitmap != null) {
                    dVar.d(bitmap);
                }
                throw fm;
            }
        }

        public void cW() {
            this.fe.dc();
        }
    }

    public v(n nVar, b bVar) {
        this.lD = nVar;
        this.ff = bVar;
    }

    public p<Bitmap> a(@NonNull InputStream inputStream, int i, int i2, @NonNull f fVar) throws IOException {
        RecyclableBufferedInputStream recyclableBufferedInputStream;
        boolean z;
        if (inputStream instanceof RecyclableBufferedInputStream) {
            recyclableBufferedInputStream = (RecyclableBufferedInputStream) inputStream;
            z = false;
        } else {
            RecyclableBufferedInputStream recyclableBufferedInputStream2 = new RecyclableBufferedInputStream(inputStream, this.ff);
            z = true;
            recyclableBufferedInputStream = recyclableBufferedInputStream2;
        }
        c i3 = c.i(recyclableBufferedInputStream);
        try {
            return this.lD.a((InputStream) new com.bumptech.glide.util.g(i3), i, i2, fVar, (n.a) new a(recyclableBufferedInputStream, i3));
        } finally {
            i3.release();
            if (z) {
                recyclableBufferedInputStream.release();
            }
        }
    }

    public boolean a(@NonNull InputStream inputStream, @NonNull f fVar) {
        return this.lD.f(inputStream);
    }
}
