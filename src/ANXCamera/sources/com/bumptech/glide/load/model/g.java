package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: DataUrlLoader */
public final class g<Model, Data> implements t<Model, Data> {
    private static final String Mh = "data:image";
    private static final String Nh = ";base64";
    private final a<Data> Lh;

    /* compiled from: DataUrlLoader */
    public interface a<Data> {
        Class<Data> M();

        Data decode(String str) throws IllegalArgumentException;

        void e(Data data) throws IOException;
    }

    /* compiled from: DataUrlLoader */
    private static final class b<Data> implements d<Data> {
        private final String Kh;
        private Data data;
        private final a<Data> reader;

        b(String str, a<Data> aVar) {
            this.Kh = str;
            this.reader = aVar;
        }

        @NonNull
        public Class<Data> M() {
            return this.reader.M();
        }

        public void a(@NonNull Priority priority, @NonNull com.bumptech.glide.load.a.d.a<? super Data> aVar) {
            try {
                this.data = this.reader.decode(this.Kh);
                aVar.b(this.data);
            } catch (IllegalArgumentException e2) {
                aVar.b((Exception) e2);
            }
        }

        public void cancel() {
        }

        public void cleanup() {
            try {
                this.reader.e(this.data);
            } catch (IOException unused) {
            }
        }

        @NonNull
        public DataSource r() {
            return DataSource.LOCAL;
        }
    }

    /* compiled from: DataUrlLoader */
    public static final class c<Model> implements u<Model, InputStream> {
        private final a<InputStream> me = new h(this);

        public void D() {
        }

        @NonNull
        public t<Model, InputStream> a(@NonNull x xVar) {
            return new g(this.me);
        }
    }

    public g(a<Data> aVar) {
        this.Lh = aVar;
    }

    public com.bumptech.glide.load.model.t.a<Data> a(@NonNull Model model, int i, int i2, @NonNull com.bumptech.glide.load.g gVar) {
        return new com.bumptech.glide.load.model.t.a<>(new com.bumptech.glide.e.d(model), new b(model.toString(), this.Lh));
    }

    public boolean c(@NonNull Model model) {
        return model.toString().startsWith(Mh);
    }
}
