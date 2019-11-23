package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.model.m;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* compiled from: ByteArrayLoader */
public class b<Data> implements m<byte[], Data> {
    private final C0008b<Data> ku;

    /* compiled from: ByteArrayLoader */
    public static class a implements n<byte[], ByteBuffer> {
        @NonNull
        public m<byte[], ByteBuffer> a(@NonNull q qVar) {
            return new b(new C0008b<ByteBuffer>() {
                public Class<ByteBuffer> aK() {
                    return ByteBuffer.class;
                }

                /* renamed from: g */
                public ByteBuffer h(byte[] bArr) {
                    return ByteBuffer.wrap(bArr);
                }
            });
        }

        public void cB() {
        }
    }

    /* renamed from: com.bumptech.glide.load.model.b$b  reason: collision with other inner class name */
    /* compiled from: ByteArrayLoader */
    public interface C0008b<Data> {
        Class<Data> aK();

        Data h(byte[] bArr);
    }

    /* compiled from: ByteArrayLoader */
    private static class c<Data> implements com.bumptech.glide.load.a.d<Data> {
        private final C0008b<Data> ku;
        private final byte[] kw;

        c(byte[] bArr, C0008b<Data> bVar) {
            this.kw = bArr;
            this.ku = bVar;
        }

        public void a(@NonNull Priority priority, @NonNull d.a<? super Data> aVar) {
            aVar.n(this.ku.h(this.kw));
        }

        @NonNull
        public Class<Data> aK() {
            return this.ku.aK();
        }

        @NonNull
        public DataSource aL() {
            return DataSource.LOCAL;
        }

        public void cancel() {
        }

        public void cleanup() {
        }
    }

    /* compiled from: ByteArrayLoader */
    public static class d implements n<byte[], InputStream> {
        @NonNull
        public m<byte[], InputStream> a(@NonNull q qVar) {
            return new b(new C0008b<InputStream>() {
                public Class<InputStream> aK() {
                    return InputStream.class;
                }

                /* renamed from: i */
                public InputStream h(byte[] bArr) {
                    return new ByteArrayInputStream(bArr);
                }
            });
        }

        public void cB() {
        }
    }

    public b(C0008b<Data> bVar) {
        this.ku = bVar;
    }

    /* renamed from: a */
    public m.a<Data> b(@NonNull byte[] bArr, int i, int i2, @NonNull f fVar) {
        return new m.a<>(new com.bumptech.glide.e.d(bArr), new c(bArr, this.ku));
    }

    /* renamed from: f */
    public boolean t(@NonNull byte[] bArr) {
        return true;
    }
}
