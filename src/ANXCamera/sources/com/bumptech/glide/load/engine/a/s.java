package com.bumptech.glide.load.engine.a;

import android.support.annotation.NonNull;
import android.support.v4.util.Pools.Pool;
import com.bumptech.glide.load.c;
import com.bumptech.glide.util.a.d;
import com.bumptech.glide.util.a.g;
import com.bumptech.glide.util.f;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.l;
import java.security.MessageDigest;

/* compiled from: SafeKeyGenerator */
public class s {
    private final f<c, String> ah = new f<>(1000);
    private final Pool<a> bh = d.b(10, new r(this));

    /* compiled from: SafeKeyGenerator */
    private static final class a implements d.c {
        private final g Re = g.newInstance();
        final MessageDigest messageDigest;

        a(MessageDigest messageDigest2) {
            this.messageDigest = messageDigest2;
        }

        @NonNull
        public g getVerifier() {
            return this.Re;
        }
    }

    private String j(c cVar) {
        Object acquire = this.bh.acquire();
        i.checkNotNull(acquire);
        a aVar = (a) acquire;
        try {
            cVar.updateDiskCacheKey(aVar.messageDigest);
            return l.g(aVar.messageDigest.digest());
        } finally {
            this.bh.release(aVar);
        }
    }

    public String f(c cVar) {
        String str;
        synchronized (this.ah) {
            str = (String) this.ah.get(cVar);
        }
        if (str == null) {
            str = j(cVar);
        }
        synchronized (this.ah) {
            this.ah.put(cVar, str);
        }
        return str;
    }
}
