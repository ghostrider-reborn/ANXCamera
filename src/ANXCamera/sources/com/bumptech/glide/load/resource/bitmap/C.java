package com.bumptech.glide.load.resource.bitmap;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.f.a;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* compiled from: VideoDecoder */
class C implements a<Integer> {
    private final ByteBuffer buffer = ByteBuffer.allocate(4);

    C() {
    }

    public void a(@NonNull byte[] bArr, @NonNull Integer num, @NonNull MessageDigest messageDigest) {
        if (num != null) {
            messageDigest.update(bArr);
            synchronized (this.buffer) {
                this.buffer.position(0);
                messageDigest.update(this.buffer.putInt(num.intValue()).array());
            }
        }
    }
}
