package com.bumptech.glide.load.engine.a;

import android.content.Context;
import com.bumptech.glide.load.engine.a.a.C0007a;
import com.bumptech.glide.load.engine.a.f.a;

/* compiled from: InternalCacheDiskCacheFactory */
public final class m extends f {
    public m(Context context) {
        this(context, C0007a.Jb, 262144000);
    }

    public m(Context context, long j) {
        this(context, C0007a.Jb, j);
    }

    public m(Context context, String str, long j) {
        super((a) new l(context, str), j);
    }
}
