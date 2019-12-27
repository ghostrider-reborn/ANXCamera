package com.bumptech.glide.load.model;

import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.model.t;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: FileLoader */
public class i<Data> implements t<File, Data> {
    private static final String TAG = "FileLoader";
    private final d<Data> Oh;

    /* compiled from: FileLoader */
    public static class a<Data> implements u<File, Data> {
        private final d<Data> me;

        public a(d<Data> dVar) {
            this.me = dVar;
        }

        public final void D() {
        }

        @NonNull
        public final t<File, Data> a(@NonNull x xVar) {
            return new i(this.me);
        }
    }

    /* compiled from: FileLoader */
    public static class b extends a<ParcelFileDescriptor> {
        public b() {
            super(new j());
        }
    }

    /* compiled from: FileLoader */
    private static final class c<Data> implements com.bumptech.glide.load.a.d<Data> {
        private Data data;
        private final File file;
        private final d<Data> me;

        c(File file2, d<Data> dVar) {
            this.file = file2;
            this.me = dVar;
        }

        @NonNull
        public Class<Data> M() {
            return this.me.M();
        }

        public void a(@NonNull Priority priority, @NonNull d.a<? super Data> aVar) {
            try {
                this.data = this.me.b(this.file);
                aVar.b(this.data);
            } catch (FileNotFoundException e2) {
                if (Log.isLoggable(i.TAG, 3)) {
                    Log.d(i.TAG, "Failed to open file", e2);
                }
                aVar.b((Exception) e2);
            }
        }

        public void cancel() {
        }

        public void cleanup() {
            Data data2 = this.data;
            if (data2 != null) {
                try {
                    this.me.e(data2);
                } catch (IOException unused) {
                }
            }
        }

        @NonNull
        public DataSource r() {
            return DataSource.LOCAL;
        }
    }

    /* compiled from: FileLoader */
    public interface d<Data> {
        Class<Data> M();

        Data b(File file) throws FileNotFoundException;

        void e(Data data) throws IOException;
    }

    /* compiled from: FileLoader */
    public static class e extends a<InputStream> {
        public e() {
            super(new k());
        }
    }

    public i(d<Data> dVar) {
        this.Oh = dVar;
    }

    public t.a<Data> a(@NonNull File file, int i, int i2, @NonNull g gVar) {
        return new t.a<>(new com.bumptech.glide.e.d(file), new c(file, this.Oh));
    }

    /* renamed from: f */
    public boolean c(@NonNull File file) {
        return true;
    }
}
