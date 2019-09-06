package com.bumptech.glide.a;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

/* compiled from: StrictLineReader */
class c extends ByteArrayOutputStream {
    final /* synthetic */ d this$0;

    c(d dVar, int i) {
        this.this$0 = dVar;
        super(i);
    }

    public String toString() {
        int i = this.count;
        try {
            return new String(this.buf, 0, (i <= 0 || this.buf[i + -1] != 13) ? this.count : i - 1, this.this$0.charset.name());
        } catch (UnsupportedEncodingException e2) {
            throw new AssertionError(e2);
        }
    }
}
