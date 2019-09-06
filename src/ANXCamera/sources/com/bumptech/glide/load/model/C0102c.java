package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.g;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* renamed from: com.bumptech.glide.load.model.c reason: case insensitive filesystem */
/* compiled from: ByteArrayLoader */
public class C0102c<Data> implements t<byte[], Data> {
    private final b<Data> Jh;

    /* renamed from: com.bumptech.glide.load.model.c$a */
    /* compiled from: ByteArrayLoader */
    public static class a implements u<byte[], ByteBuffer> {
        public void D() {
        }

        @NonNull
        public t<byte[], ByteBuffer> a(@NonNull x xVar) {
            return new C0102c(new C0101b(this));
        }
    }

    /* renamed from: com.bumptech.glide.load.model.c$b */
    /* compiled from: ByteArrayLoader */
    public interface b<Data> {
        Class<Data> M();

        Data b(byte[] bArr);
    }

    /* renamed from: com.bumptech.glide.load.model.c$c reason: collision with other inner class name */
    /* compiled from: ByteArrayLoader */
    private static class C0010c<Data> implements com.bumptech.glide.load.a.d<Data> {
        private final b<Data> Jh;
        private final byte[] model;

        C0010c(byte[] bArr, b<Data> bVar) {
            this.model = bArr;
            this.Jh = bVar;
        }

        @NonNull
        public Class<Data> M() {
            return this.Jh.M();
        }

        public void a(@NonNull Priority priority, @NonNull com.bumptech.glide.load.a.d.a<? super Data> aVar) {
            aVar.b(this.Jh.b(this.model));
        }

        public void cancel() {
        }

        public void cleanup() {
        }

        @NonNull
        public DataSource r() {
            return DataSource.LOCAL;
        }
    }

    /* renamed from: com.bumptech.glide.load.model.c$d */
    /* compiled from: ByteArrayLoader */
    public static class d implements u<byte[], InputStream> {
        public void D() {
        }

        @NonNull
        public t<byte[], InputStream> a(@NonNull x xVar) {
            return new C0102c(new C0103d(this));
        }
    }

    public C0102c(b<Data> bVar) {
        this.Jh = bVar;
    }

    public com.bumptech.glide.load.model.t.a<Data> a(@NonNull byte[] bArr, int i, int i2, @NonNull g gVar) {
        return new com.bumptech.glide.load.model.t.a<>(new com.bumptech.glide.e.d(bArr), new C0010c(bArr, this.Jh));
    }

    /* renamed from: f */
    public boolean c(@NonNull byte[] bArr) {
        return true;
    }
}
