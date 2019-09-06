package com.bumptech.glide.load.a;

import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d.a;
import java.io.IOException;

/* compiled from: AssetPathFetcher */
public abstract class b<T> implements d<T> {
    private static final String TAG = "AssetPathFetcher";
    private final String Pd;
    private final AssetManager Qd;
    private T data;

    public b(AssetManager assetManager, String str) {
        this.Qd = assetManager;
        this.Pd = str;
    }

    public void a(@NonNull Priority priority, @NonNull a<? super T> aVar) {
        try {
            this.data = b(this.Qd, this.Pd);
            aVar.b(this.data);
        } catch (IOException e2) {
            String str = TAG;
            if (Log.isLoggable(str, 3)) {
                Log.d(str, "Failed to load data from asset manager", e2);
            }
            aVar.b((Exception) e2);
        }
    }

    /* access modifiers changed from: protected */
    public abstract T b(AssetManager assetManager, String str) throws IOException;

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
