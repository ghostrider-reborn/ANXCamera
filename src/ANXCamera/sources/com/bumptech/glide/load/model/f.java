package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.g;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/* compiled from: ByteBufferFileLoader */
public class f implements t<File, ByteBuffer> {
    private static final String TAG = "ByteBufferFileLoader";

    /* compiled from: ByteBufferFileLoader */
    private static final class a implements d<ByteBuffer> {
        private final File file;

        a(File file2) {
            this.file = file2;
        }

        @NonNull
        public Class<ByteBuffer> M() {
            return ByteBuffer.class;
        }

        public void a(@NonNull Priority priority, @NonNull com.bumptech.glide.load.a.d.a<? super ByteBuffer> aVar) {
            try {
                aVar.b(com.bumptech.glide.util.a.fromFile(this.file));
            } catch (IOException e2) {
                String str = f.TAG;
                if (Log.isLoggable(str, 3)) {
                    Log.d(str, "Failed to obtain ByteBuffer for file", e2);
                }
                aVar.b((Exception) e2);
            }
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

    /* compiled from: ByteBufferFileLoader */
    public static class b implements u<File, ByteBuffer> {
        public void D() {
        }

        @NonNull
        public t<File, ByteBuffer> a(@NonNull x xVar) {
            return new f();
        }
    }

    public com.bumptech.glide.load.model.t.a<ByteBuffer> a(@NonNull File file, int i, int i2, @NonNull g gVar) {
        return new com.bumptech.glide.load.model.t.a<>(new com.bumptech.glide.e.d(file), new a(file));
    }

    /* renamed from: f */
    public boolean c(@NonNull File file) {
        return true;
    }
}
