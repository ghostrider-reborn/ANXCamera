package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import android.util.Base64;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.model.m;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: DataUrlLoader */
public final class e<Model, Data> implements m<Model, Data> {
    private static final String ky = "data:image";
    private static final String kz = ";base64";
    private final a<Data> kA;

    /* compiled from: DataUrlLoader */
    public interface a<Data> {
        Class<Data> aK();

        void m(Data data) throws IOException;

        Data y(String str) throws IllegalArgumentException;
    }

    /* compiled from: DataUrlLoader */
    private static final class b<Data> implements d<Data> {
        private Data data;
        private final String kB;
        private final a<Data> kC;

        b(String str, a<Data> aVar) {
            this.kB = str;
            this.kC = aVar;
        }

        public void a(@NonNull Priority priority, @NonNull d.a<? super Data> aVar) {
            try {
                this.data = this.kC.y(this.kB);
                aVar.n(this.data);
            } catch (IllegalArgumentException e) {
                aVar.b(e);
            }
        }

        @NonNull
        public Class<Data> aK() {
            return this.kC.aK();
        }

        @NonNull
        public DataSource aL() {
            return DataSource.LOCAL;
        }

        public void cancel() {
        }

        public void cleanup() {
            try {
                this.kC.m(this.data);
            } catch (IOException e) {
            }
        }
    }

    /* compiled from: DataUrlLoader */
    public static final class c<Model> implements n<Model, InputStream> {
        private final a<InputStream> kD = new a<InputStream>() {
            public Class<InputStream> aK() {
                return InputStream.class;
            }

            /* renamed from: e */
            public void m(InputStream inputStream) throws IOException {
                inputStream.close();
            }

            /* renamed from: z */
            public InputStream y(String str) {
                if (str.startsWith(e.ky)) {
                    int indexOf = str.indexOf(44);
                    if (indexOf == -1) {
                        throw new IllegalArgumentException("Missing comma in data URL.");
                    } else if (str.substring(0, indexOf).endsWith(e.kz)) {
                        return new ByteArrayInputStream(Base64.decode(str.substring(indexOf + 1), 0));
                    } else {
                        throw new IllegalArgumentException("Not a base64 image data URL.");
                    }
                } else {
                    throw new IllegalArgumentException("Not a valid image data URL.");
                }
            }
        };

        @NonNull
        public m<Model, InputStream> a(@NonNull q qVar) {
            return new e(this.kD);
        }

        public void cB() {
        }
    }

    public e(a<Data> aVar) {
        this.kA = aVar;
    }

    public m.a<Data> b(@NonNull Model model, int i, int i2, @NonNull f fVar) {
        return new m.a<>(new com.bumptech.glide.e.d(model), new b(model.toString(), this.kA));
    }

    public boolean t(@NonNull Model model) {
        return model.toString().startsWith(ky);
    }
}
