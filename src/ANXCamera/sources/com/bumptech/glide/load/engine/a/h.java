package com.bumptech.glide.load.engine.a;

import android.content.Context;
import com.bumptech.glide.load.engine.a.f.a;
import java.io.File;

/* compiled from: ExternalCacheDiskCacheFactory */
class h implements a {
    final /* synthetic */ String Eg;
    final /* synthetic */ Context val$context;

    h(Context context, String str) {
        this.val$context = context;
        this.Eg = str;
    }

    public File w() {
        File externalCacheDir = this.val$context.getExternalCacheDir();
        if (externalCacheDir == null) {
            return null;
        }
        String str = this.Eg;
        return str != null ? new File(externalCacheDir, str) : externalCacheDir;
    }
}
