package com.bumptech.glide.load.engine.a;

import android.content.Context;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.engine.a.f.a;
import java.io.File;

/* compiled from: ExternalPreferredCacheDiskCacheFactory */
class j implements a {
    final /* synthetic */ String Eg;
    final /* synthetic */ Context val$context;

    j(Context context, String str) {
        this.val$context = context;
        this.Eg = str;
    }

    @Nullable
    private File vk() {
        File cacheDir = this.val$context.getCacheDir();
        if (cacheDir == null) {
            return null;
        }
        String str = this.Eg;
        return str != null ? new File(cacheDir, str) : cacheDir;
    }

    public File w() {
        File vk = vk();
        if (vk != null && vk.exists()) {
            return vk;
        }
        File externalCacheDir = this.val$context.getExternalCacheDir();
        if (externalCacheDir == null || !externalCacheDir.canWrite()) {
            return vk;
        }
        String str = this.Eg;
        return str != null ? new File(externalCacheDir, str) : externalCacheDir;
    }
}
