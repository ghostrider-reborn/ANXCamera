package com.bumptech.glide.load.engine.a;

import com.bumptech.glide.load.engine.a.a;
import java.io.File;

/* compiled from: DiskLruCacheFactory */
public class d implements a.C0005a {
    private final long je;
    private final a jf;

    /* compiled from: DiskLruCacheFactory */
    public interface a {
        File cd();
    }

    public d(a aVar, long j) {
        this.je = j;
        this.jf = aVar;
    }

    public d(final String str, long j) {
        this((a) new a() {
            public File cd() {
                return new File(str);
            }
        }, j);
    }

    public d(final String str, final String str2, long j) {
        this((a) new a() {
            public File cd() {
                return new File(str, str2);
            }
        }, j);
    }

    public a cb() {
        File cd = this.jf.cd();
        if (cd == null) {
            return null;
        }
        if (cd.mkdirs() || (cd.exists() && cd.isDirectory())) {
            return e.b(cd, this.je);
        }
        return null;
    }
}
