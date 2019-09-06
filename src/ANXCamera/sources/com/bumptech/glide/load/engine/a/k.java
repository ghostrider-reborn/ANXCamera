package com.bumptech.glide.load.engine.a;

import android.content.Context;
import com.bumptech.glide.load.engine.a.a.C0007a;
import com.bumptech.glide.load.engine.a.f.a;

/* compiled from: ExternalPreferredCacheDiskCacheFactory */
public final class k extends f {
    public k(Context context) {
        this(context, C0007a.Jb, 262144000);
    }

    public k(Context context, long j) {
        this(context, C0007a.Jb, j);
    }

    public k(Context context, String str, long j) {
        super((a) new j(context, str), j);
    }
}
