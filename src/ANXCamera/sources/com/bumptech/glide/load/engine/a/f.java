package com.bumptech.glide.load.engine.a;

import android.content.Context;
import com.bumptech.glide.load.engine.a.a;
import com.bumptech.glide.load.engine.a.d;
import java.io.File;

@Deprecated
/* compiled from: ExternalCacheDiskCacheFactory */
public final class f extends d {
    public f(Context context) {
        this(context, a.C0005a.bi, a.C0005a.iY);
    }

    public f(Context context, int i) {
        this(context, a.C0005a.bi, i);
    }

    public f(final Context context, final String str, int i) {
        super((d.a) new d.a() {
            public File cd() {
                File externalCacheDir = context.getExternalCacheDir();
                if (externalCacheDir == null) {
                    return null;
                }
                return str != null ? new File(externalCacheDir, str) : externalCacheDir;
            }
        }, (long) i);
    }
}
