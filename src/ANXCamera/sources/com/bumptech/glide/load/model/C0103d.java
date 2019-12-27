package com.bumptech.glide.load.model;

import com.bumptech.glide.load.model.C0102c;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/* renamed from: com.bumptech.glide.load.model.d  reason: case insensitive filesystem */
/* compiled from: ByteArrayLoader */
class C0103d implements C0102c.b<InputStream> {
    final /* synthetic */ C0102c.d this$0;

    C0103d(C0102c.d dVar) {
        this.this$0 = dVar;
    }

    public Class<InputStream> M() {
        return InputStream.class;
    }

    public InputStream b(byte[] bArr) {
        return new ByteArrayInputStream(bArr);
    }
}
