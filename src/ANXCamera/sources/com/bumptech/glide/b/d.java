package com.bumptech.glide.b;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/* compiled from: GifHeaderParser */
public class d {
    private static final String TAG = "GifHeaderParser";
    private static final int Yc = 255;
    private static final int Zc = 44;
    private static final int _c = 33;
    private static final int ad = 59;
    private static final int bd = 249;
    private static final int cd = 255;
    private static final int dd = 254;
    private static final int ed = 1;
    private static final int gd = 28;
    private static final int hd = 2;
    private static final int jd = 1;
    private static final int kd = 128;
    private static final int ld = 64;
    private static final int md = 7;
    private static final int nd = 128;
    private static final int od = 7;
    static final int pd = 2;
    static final int rd = 10;
    private static final int td = 256;
    private ByteBuffer Xc;
    private final byte[] block = new byte[256];
    private int blockSize = 0;
    private c header;

    private void M(int i) {
        boolean z = false;
        while (!z && !err() && this.header.frameCount <= i) {
            int read = read();
            if (read == 33) {
                int read2 = read();
                if (read2 == 1) {
                    skip();
                } else if (read2 == 249) {
                    this.header.Uc = new b();
                    readGraphicControlExt();
                } else if (read2 == 254) {
                    skip();
                } else if (read2 != 255) {
                    skip();
                } else {
                    readBlock();
                    StringBuilder sb = new StringBuilder();
                    for (int i2 = 0; i2 < 11; i2++) {
                        sb.append((char) this.block[i2]);
                    }
                    if (sb.toString().equals("NETSCAPE2.0")) {
                        readNetscapeExt();
                    } else {
                        skip();
                    }
                }
            } else if (read == 44) {
                c cVar = this.header;
                if (cVar.Uc == null) {
                    cVar.Uc = new b();
                }
                readBitmap();
            } else if (read != 59) {
                this.header.status = 1;
            } else {
                z = true;
            }
        }
    }

    private void Yj() {
        read();
        skip();
    }

    private boolean err() {
        return this.header.status != 0;
    }

    private int read() {
        try {
            return this.Xc.get() & 255;
        } catch (Exception unused) {
            this.header.status = 1;
            return 0;
        }
    }

    private void readBitmap() {
        this.header.Uc.ix = readShort();
        this.header.Uc.iy = readShort();
        this.header.Uc.iw = readShort();
        this.header.Uc.ih = readShort();
        int read = read();
        boolean z = false;
        boolean z2 = (read & 128) != 0;
        int pow = (int) Math.pow(2.0d, (double) ((read & 7) + 1));
        b bVar = this.header.Uc;
        if ((read & 64) != 0) {
            z = true;
        }
        bVar.interlace = z;
        if (z2) {
            this.header.Uc.lct = readColorTable(pow);
        } else {
            this.header.Uc.lct = null;
        }
        this.header.Uc.Pc = this.Xc.position();
        Yj();
        if (!err()) {
            c cVar = this.header;
            cVar.frameCount++;
            cVar.frames.add(cVar.Uc);
        }
    }

    private void readBlock() {
        this.blockSize = read();
        if (this.blockSize > 0) {
            int i = 0;
            int i2 = 0;
            while (i < this.blockSize) {
                try {
                    i2 = this.blockSize - i;
                    this.Xc.get(this.block, i, i2);
                    i += i2;
                } catch (Exception e2) {
                    String str = TAG;
                    if (Log.isLoggable(str, 3)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Error Reading Block n: ");
                        sb.append(i);
                        sb.append(" count: ");
                        sb.append(i2);
                        sb.append(" blockSize: ");
                        sb.append(this.blockSize);
                        Log.d(str, sb.toString(), e2);
                    }
                    this.header.status = 1;
                    return;
                }
            }
        }
    }

    @Nullable
    private int[] readColorTable(int i) {
        byte[] bArr = new byte[(i * 3)];
        int[] iArr = null;
        try {
            this.Xc.get(bArr);
            iArr = new int[256];
            int i2 = 0;
            int i3 = 0;
            while (i2 < i) {
                int i4 = i3 + 1;
                int i5 = i4 + 1;
                int i6 = i5 + 1;
                int i7 = i2 + 1;
                iArr[i2] = ((bArr[i3] & 255) << 16) | -16777216 | ((bArr[i4] & 255) << 8) | (bArr[i5] & 255);
                i3 = i6;
                i2 = i7;
            }
        } catch (BufferUnderflowException e2) {
            String str = TAG;
            if (Log.isLoggable(str, 3)) {
                Log.d(str, "Format Error Reading Color Table", e2);
            }
            this.header.status = 1;
        }
        return iArr;
    }

    private void readContents() {
        M(Integer.MAX_VALUE);
    }

    private void readGraphicControlExt() {
        read();
        int read = read();
        b bVar = this.header.Uc;
        bVar.dispose = (read & 28) >> 2;
        boolean z = true;
        if (bVar.dispose == 0) {
            bVar.dispose = 1;
        }
        b bVar2 = this.header.Uc;
        if ((read & 1) == 0) {
            z = false;
        }
        bVar2.transparency = z;
        int readShort = readShort();
        if (readShort < 2) {
            readShort = 10;
        }
        b bVar3 = this.header.Uc;
        bVar3.delay = readShort * 10;
        bVar3.transIndex = read();
        read();
    }

    private void readHeader() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append((char) read());
        }
        if (!sb.toString().startsWith("GIF")) {
            this.header.status = 1;
            return;
        }
        readLSD();
        if (this.header.gctFlag && !err()) {
            c cVar = this.header;
            cVar.gct = readColorTable(cVar.gctSize);
            c cVar2 = this.header;
            cVar2.bgColor = cVar2.gct[cVar2.bgIndex];
        }
    }

    private void readLSD() {
        this.header.width = readShort();
        this.header.height = readShort();
        int read = read();
        this.header.gctFlag = (read & 128) != 0;
        this.header.gctSize = (int) Math.pow(2.0d, (double) ((read & 7) + 1));
        this.header.bgIndex = read();
        this.header.pixelAspect = read();
    }

    private void readNetscapeExt() {
        do {
            readBlock();
            byte[] bArr = this.block;
            if (bArr[0] == 1) {
                this.header.loopCount = ((bArr[2] & 255) << 8) | (bArr[1] & 255);
            }
            if (this.blockSize <= 0) {
                return;
            }
        } while (!err());
    }

    private int readShort() {
        return this.Xc.getShort();
    }

    private void reset() {
        this.Xc = null;
        Arrays.fill(this.block, 0);
        this.header = new c();
        this.blockSize = 0;
    }

    private void skip() {
        int read;
        do {
            read = read();
            this.Xc.position(Math.min(this.Xc.position() + read, this.Xc.limit()));
        } while (read > 0);
    }

    @NonNull
    public c ag() {
        if (this.Xc == null) {
            throw new IllegalStateException("You must call setData() before parseHeader()");
        } else if (err()) {
            return this.header;
        } else {
            readHeader();
            if (!err()) {
                readContents();
                c cVar = this.header;
                if (cVar.frameCount < 0) {
                    cVar.status = 1;
                }
            }
            return this.header;
        }
    }

    public void clear() {
        this.Xc = null;
        this.header = null;
    }

    public boolean isAnimated() {
        readHeader();
        if (!err()) {
            M(2);
        }
        return this.header.frameCount > 1;
    }

    public d setData(@NonNull ByteBuffer byteBuffer) {
        reset();
        this.Xc = byteBuffer.asReadOnlyBuffer();
        this.Xc.position(0);
        this.Xc.order(ByteOrder.LITTLE_ENDIAN);
        return this;
    }

    public d setData(@Nullable byte[] bArr) {
        if (bArr != null) {
            setData(ByteBuffer.wrap(bArr));
        } else {
            this.Xc = null;
            this.header.status = 2;
        }
        return this;
    }
}
