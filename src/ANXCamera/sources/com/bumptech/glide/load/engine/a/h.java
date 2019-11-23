package com.bumptech.glide.load.engine.a;

import android.content.Context;
import com.bumptech.glide.load.engine.a.a;
import com.bumptech.glide.load.engine.a.d;
import java.io.File;

/* compiled from: InternalCacheDiskCacheFactory */
public final class h extends d {
    public h(Context context) {
        this(context, a.C0005a.bi, 262144000);
    }

    public h(Context context, long j) {
        this(context, a.C0005a.bi, j);
    }

    public h(final Context context, final String str, long j) {
        super((d.a) new d.a() {
            public File cd() {
                File cacheDir = context.getCacheDir();
                if (cacheDir == null) {
                    return null;
                }
                return str != null ? new File(cacheDir, str) : cacheDir;
            }
        }, j);
    }
}
