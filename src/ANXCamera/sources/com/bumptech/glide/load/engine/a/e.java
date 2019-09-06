package com.bumptech.glide.load.engine.a;

import com.bumptech.glide.load.engine.a.f.a;
import java.io.File;

/* compiled from: DiskLruCacheFactory */
class e implements a {
    final /* synthetic */ String Dg;
    final /* synthetic */ String Eg;

    e(String str, String str2) {
        this.Dg = str;
        this.Eg = str2;
    }

    public File w() {
        return new File(this.Dg, this.Eg);
    }
}
