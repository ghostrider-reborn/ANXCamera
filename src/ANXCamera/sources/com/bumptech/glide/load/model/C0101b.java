package com.bumptech.glide.load.model;

import com.bumptech.glide.load.model.C0102c.a;
import com.bumptech.glide.load.model.C0102c.b;
import java.nio.ByteBuffer;

/* renamed from: com.bumptech.glide.load.model.b reason: case insensitive filesystem */
/* compiled from: ByteArrayLoader */
class C0101b implements b<ByteBuffer> {
    final /* synthetic */ a this$0;

    C0101b(a aVar) {
        this.this$0 = aVar;
    }

    public Class<ByteBuffer> M() {
        return ByteBuffer.class;
    }

    public ByteBuffer b(byte[] bArr) {
        return ByteBuffer.wrap(bArr);
    }
}
