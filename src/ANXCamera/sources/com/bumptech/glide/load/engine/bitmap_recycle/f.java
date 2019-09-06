package com.bumptech.glide.load.engine.bitmap_recycle;

/* compiled from: ByteArrayAdapter */
public final class f implements a<byte[]> {
    private static final String TAG = "ByteArrayPool";

    public int E() {
        return 1;
    }

    /* renamed from: e */
    public int a(byte[] bArr) {
        return bArr.length;
    }

    public String getTag() {
        return TAG;
    }

    public byte[] newArray(int i) {
        return new byte[i];
    }
}
