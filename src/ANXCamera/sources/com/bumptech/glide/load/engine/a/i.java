package com.bumptech.glide.load.engine.a;

import android.content.Context;
import com.bumptech.glide.load.engine.a.a.C0007a;
import com.bumptech.glide.load.engine.a.f.a;

@Deprecated
/* compiled from: ExternalCacheDiskCacheFactory */
public final class i extends f {
    public i(Context context) {
        this(context, C0007a.Jb, C0007a.wr);
    }

    public i(Context context, int i) {
        this(context, C0007a.Jb, i);
    }

    public i(Context context, String str, int i) {
        super((a) new h(context, str), (long) i);
    }
}
