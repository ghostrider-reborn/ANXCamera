package com.bumptech.glide.load.engine.a;

import com.bumptech.glide.util.a.d.a;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: SafeKeyGenerator */
class r implements a<a> {
    final /* synthetic */ s this$0;

    r(s sVar) {
        this.this$0 = sVar;
    }

    public a create() {
        try {
            return new a(MessageDigest.getInstance("SHA-256"));
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException(e2);
        }
    }
}
