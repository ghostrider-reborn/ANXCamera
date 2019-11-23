package com.bumptech.glide.load.engine.bitmap_recycle;

/* compiled from: ByteArrayAdapter */
public final class f implements a<byte[]> {
    private static final String TAG = "ByteArrayPool";

    public int bM() {
        return 1;
    }

    /* renamed from: e */
    public int q(byte[] bArr) {
        return bArr.length;
    }

    public String getTag() {
        return TAG;
    }

    /* renamed from: u */
    public byte[] newArray(int i) {
        return new byte[i];
    }
}
