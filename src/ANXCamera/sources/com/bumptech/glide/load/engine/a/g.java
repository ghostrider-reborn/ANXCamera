package com.bumptech.glide.load.engine.a;

import android.content.Context;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.engine.a.a;
import com.bumptech.glide.load.engine.a.d;
import java.io.File;

/* compiled from: ExternalPreferredCacheDiskCacheFactory */
public final class g extends d {
    public g(Context context) {
        this(context, a.C0005a.bi, 262144000);
    }

    public g(Context context, long j) {
        this(context, a.C0005a.bi, j);
    }

    public g(final Context context, final String str, long j) {
        super((d.a) new d.a() {
            @Nullable
            private File cg() {
                File cacheDir = context.getCacheDir();
                if (cacheDir == null) {
                    return null;
                }
                return str != null ? new File(cacheDir, str) : cacheDir;
            }

            public File cd() {
                File cg = cg();
                if (cg != null && cg.exists()) {
                    return cg;
                }
                File externalCacheDir = context.getExternalCacheDir();
                return (externalCacheDir == null || !externalCacheDir.canWrite()) ? cg : str != null ? new File(externalCacheDir, str) : externalCacheDir;
            }
        }, j);
    }
}
