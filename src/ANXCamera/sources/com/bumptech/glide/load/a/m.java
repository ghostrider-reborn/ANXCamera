package com.bumptech.glide.load.a;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import java.io.FileNotFoundException;
import java.io.IOException;

/* compiled from: LocalUriFetcher */
public abstract class m<T> implements d<T> {
    private static final String TAG = "LocalUriFetcher";
    private final ContentResolver ae;
    private T data;
    private final Uri uri;

    public m(ContentResolver contentResolver, Uri uri2) {
        this.ae = contentResolver;
        this.uri = uri2;
    }

    /* access modifiers changed from: protected */
    public abstract T a(Uri uri2, ContentResolver contentResolver) throws FileNotFoundException;

    public final void a(@NonNull Priority priority, @NonNull d.a<? super T> aVar) {
        try {
            this.data = a(this.uri, this.ae);
            aVar.b(this.data);
        } catch (FileNotFoundException e2) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Failed to open Uri", e2);
            }
            aVar.b((Exception) e2);
        }
    }

    public void cancel() {
    }

    public void cleanup() {
        T t = this.data;
        if (t != null) {
            try {
                e(t);
            } catch (IOException unused) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void e(T t) throws IOException;

    @NonNull
    public DataSource r() {
        return DataSource.LOCAL;
    }
}
