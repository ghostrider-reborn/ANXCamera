package com.bumptech.glide.load.resource.a;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.a.e;
import java.nio.ByteBuffer;

/* compiled from: ByteBufferRewinder */
public class a implements e<ByteBuffer> {
    private final ByteBuffer buffer;

    /* renamed from: com.bumptech.glide.load.resource.a.a$a  reason: collision with other inner class name */
    /* compiled from: ByteBufferRewinder */
    public static class C0010a implements e.a<ByteBuffer> {
        @NonNull
        public Class<ByteBuffer> aK() {
            return ByteBuffer.class;
        }

        @NonNull
        /* renamed from: d */
        public e<ByteBuffer> o(ByteBuffer byteBuffer) {
            return new a(byteBuffer);
        }
    }

    public a(ByteBuffer byteBuffer) {
        this.buffer = byteBuffer;
    }

    public void cleanup() {
    }

    @NonNull
    /* renamed from: df */
    public ByteBuffer aN() {
        this.buffer.position(0);
        return this.buffer;
    }
}
