package com.bumptech.glide.util;

import android.support.annotation.NonNull;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: MarkEnforcingInputStream */
public class g extends FilterInputStream {
    private static final int UNSET = Integer.MIN_VALUE;
    private static final int gq = -1;
    private int fq = Integer.MIN_VALUE;

    public g(@NonNull InputStream inputStream) {
        super(inputStream);
    }

    private long j(long j) {
        int i = this.fq;
        if (i == 0) {
            return -1;
        }
        return (i == Integer.MIN_VALUE || j <= ((long) i)) ? j : (long) i;
    }

    private void k(long j) {
        int i = this.fq;
        if (i != Integer.MIN_VALUE && j != -1) {
            this.fq = (int) (((long) i) - j);
        }
    }

    public int available() throws IOException {
        int i = this.fq;
        return i == Integer.MIN_VALUE ? super.available() : Math.min(i, super.available());
    }

    public synchronized void mark(int i) {
        super.mark(i);
        this.fq = i;
    }

    public int read() throws IOException {
        if (j(1) == -1) {
            return -1;
        }
        int read = super.read();
        k(1);
        return read;
    }

    public int read(@NonNull byte[] bArr, int i, int i2) throws IOException {
        int j = (int) j((long) i2);
        if (j == -1) {
            return -1;
        }
        int read = super.read(bArr, i, j);
        k((long) read);
        return read;
    }

    public synchronized void reset() throws IOException {
        super.reset();
        this.fq = Integer.MIN_VALUE;
    }

    public long skip(long j) throws IOException {
        long j2 = j(j);
        if (j2 == -1) {
            return 0;
        }
        long skip = super.skip(j2);
        k(skip);
        return skip;
    }
}
