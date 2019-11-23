package com.bumptech.glide.load.engine.a;

import android.support.annotation.NonNull;
import android.support.v4.util.Pools;
import com.bumptech.glide.load.c;
import com.bumptech.glide.util.a.a;
import com.bumptech.glide.util.f;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.k;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: SafeKeyGenerator */
public class m {
    private final f<c, String> jF = new f<>(1000);
    private final Pools.Pool<a> jG = com.bumptech.glide.util.a.a.b(10, new a.C0013a<a>() {
        /* renamed from: cp */
        public a create() {
            try {
                return new a(MessageDigest.getInstance("SHA-256"));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    });

    /* compiled from: SafeKeyGenerator */
    private static final class a implements a.c {
        private final com.bumptech.glide.util.a.c ge = com.bumptech.glide.util.a.c.fv();
        final MessageDigest messageDigest;

        a(MessageDigest messageDigest2) {
            this.messageDigest = messageDigest2;
        }

        @NonNull
        public com.bumptech.glide.util.a.c br() {
            return this.ge;
        }
    }

    private String i(c cVar) {
        a aVar = (a) i.checkNotNull(this.jG.acquire());
        try {
            cVar.updateDiskCacheKey(aVar.messageDigest);
            return k.j(aVar.messageDigest.digest());
        } finally {
            this.jG.release(aVar);
        }
    }

    public String h(c cVar) {
        String str;
        synchronized (this.jF) {
            str = this.jF.get(cVar);
        }
        if (str == null) {
            str = i(cVar);
        }
        synchronized (this.jF) {
            this.jF.put(cVar, str);
        }
        return str;
    }
}
