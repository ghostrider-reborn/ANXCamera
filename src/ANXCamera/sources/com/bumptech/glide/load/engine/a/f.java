package com.bumptech.glide.load.engine.a;

import com.bumptech.glide.load.engine.a.a;
import java.io.File;

/* compiled from: DiskLruCacheFactory */
public class f implements a.C0007a {
    private final long Fg;
    private final a Gg;

    /* compiled from: DiskLruCacheFactory */
    public interface a {
        File w();
    }

    public f(a aVar, long j) {
        this.Fg = j;
        this.Gg = aVar;
    }

    public f(String str, long j) {
        this((a) new d(str), j);
    }

    public f(String str, String str2, long j) {
        this((a) new e(str, str2), j);
    }

    public a build() {
        File w = this.Gg.w();
        if (w == null) {
            return null;
        }
        if (w.mkdirs() || (w.exists() && w.isDirectory())) {
            return g.create(w, this.Fg);
        }
        return null;
    }
}
