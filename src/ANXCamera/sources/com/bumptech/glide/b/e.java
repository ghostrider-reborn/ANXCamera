package com.bumptech.glide.b;

import android.graphics.Bitmap;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.bumptech.glide.b.a;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Iterator;

/* compiled from: StandardGifDecoder */
public class e implements a {
    private static final int MAX_STACK_SIZE = 4096;
    private static final String TAG = e.class.getSimpleName();
    private static final int dR = -1;
    private static final int dS = -1;
    private static final int dT = 4;
    @ColorInt
    private static final int dU = 0;
    private static final int dw = 255;
    @ColorInt
    private int[] act;
    private byte[] block;
    private ByteBuffer dP;
    private c dQ;
    @ColorInt
    private final int[] dV;
    private final a.C0003a dW;
    private d dX;
    private byte[] dY;
    @ColorInt
    private int[] dZ;
    private int ea;
    private Bitmap eb;
    private boolean ec;
    private int ed;
    private int ee;
    @Nullable
    private Boolean ef;
    @NonNull
    private Bitmap.Config eg;
    private byte[] pixelStack;
    private short[] prefix;
    private int sampleSize;
    private int status;
    private byte[] suffix;

    public e(@NonNull a.C0003a aVar) {
        this.dV = new int[256];
        this.eg = Bitmap.Config.ARGB_8888;
        this.dW = aVar;
        this.dQ = new c();
    }

    public e(@NonNull a.C0003a aVar, c cVar, ByteBuffer byteBuffer) {
        this(aVar, cVar, byteBuffer, 1);
    }

    public e(@NonNull a.C0003a aVar, c cVar, ByteBuffer byteBuffer, int i) {
        this(aVar);
        a(cVar, byteBuffer, i);
    }

    @ColorInt
    private int a(int i, int i2, int i3) {
        int i4 = i;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        while (i4 < this.sampleSize + i && i4 < this.dY.length && i4 < i2) {
            int i10 = this.act[this.dY[i4] & 255];
            if (i10 != 0) {
                i5 += (i10 >> 24) & 255;
                i6 += (i10 >> 16) & 255;
                i7 += (i10 >> 8) & 255;
                i8 += i10 & 255;
                i9++;
            }
            i4++;
        }
        int i11 = i + i3;
        int i12 = i11;
        while (i12 < this.sampleSize + i11 && i12 < this.dY.length && i12 < i2) {
            int i13 = this.act[this.dY[i12] & 255];
            if (i13 != 0) {
                i5 += (i13 >> 24) & 255;
                i6 += (i13 >> 16) & 255;
                i7 += (i13 >> 8) & 255;
                i8 += i13 & 255;
                i9++;
            }
            i12++;
        }
        if (i9 == 0) {
            return 0;
        }
        return ((i5 / i9) << 24) | ((i6 / i9) << 16) | ((i7 / i9) << 8) | (i8 / i9);
    }

    private Bitmap a(b bVar, b bVar2) {
        int[] iArr = this.dZ;
        int i = 0;
        if (bVar2 == null) {
            if (this.eb != null) {
                this.dW.c(this.eb);
            }
            this.eb = null;
            Arrays.fill(iArr, 0);
        }
        if (bVar2 != null && bVar2.dispose == 3 && this.eb == null) {
            Arrays.fill(iArr, 0);
        }
        if (bVar2 != null && bVar2.dispose > 0) {
            if (bVar2.dispose == 2) {
                if (!bVar.transparency) {
                    int i2 = this.dQ.bgColor;
                    if (bVar.lct == null || this.dQ.bgIndex != bVar.transIndex) {
                        i = i2;
                    }
                } else if (this.ea == 0) {
                    this.ef = true;
                }
                int i3 = bVar2.ih / this.sampleSize;
                int i4 = bVar2.iy / this.sampleSize;
                int i5 = bVar2.iw / this.sampleSize;
                int i6 = (i4 * this.ee) + (bVar2.ix / this.sampleSize);
                int i7 = (i3 * this.ee) + i6;
                while (i6 < i7) {
                    int i8 = i6 + i5;
                    for (int i9 = i6; i9 < i8; i9++) {
                        iArr[i9] = i;
                    }
                    i6 += this.ee;
                }
            } else if (bVar2.dispose == 3 && this.eb != null) {
                this.eb.getPixels(iArr, 0, this.ee, 0, 0, this.ee, this.ed);
            }
        }
        c(bVar);
        if (bVar.interlace || this.sampleSize != 1) {
            b(bVar);
        } else {
            a(bVar);
        }
        if (this.ec && (bVar.dispose == 0 || bVar.dispose == 1)) {
            if (this.eb == null) {
                this.eb = aH();
            }
            this.eb.setPixels(iArr, 0, this.ee, 0, 0, this.ee, this.ed);
        }
        Bitmap aH = aH();
        aH.setPixels(iArr, 0, this.ee, 0, 0, this.ee, this.ed);
        return aH;
    }

    private void a(b bVar) {
        b bVar2 = bVar;
        int[] iArr = this.dZ;
        int i = bVar2.ih;
        int i2 = bVar2.iy;
        int i3 = bVar2.iw;
        int i4 = bVar2.ix;
        boolean z = this.ea == 0;
        int i5 = this.ee;
        byte[] bArr = this.dY;
        int[] iArr2 = this.act;
        int i6 = 0;
        byte b2 = -1;
        while (i6 < i) {
            int i7 = (i6 + i2) * i5;
            int i8 = i7 + i4;
            int i9 = i8 + i3;
            int i10 = i7 + i5;
            if (i10 < i9) {
                i9 = i10;
            }
            byte b3 = b2;
            int i11 = bVar2.iw * i6;
            int i12 = i8;
            while (i12 < i9) {
                byte b4 = bArr[i11];
                int i13 = i;
                byte b5 = b4 & 255;
                if (b5 != b3) {
                    int i14 = iArr2[b5];
                    if (i14 != 0) {
                        iArr[i12] = i14;
                    } else {
                        b3 = b4;
                    }
                }
                i11++;
                i12++;
                i = i13;
                b bVar3 = bVar;
            }
            int i15 = i;
            i6++;
            b2 = b3;
            bVar2 = bVar;
        }
        this.ef = Boolean.valueOf(this.ef == null && z && b2 != -1);
    }

    @NonNull
    private d aG() {
        if (this.dX == null) {
            this.dX = new d();
        }
        return this.dX;
    }

    private Bitmap aH() {
        Bitmap a2 = this.dW.a(this.ee, this.ed, (this.ef == null || this.ef.booleanValue()) ? Bitmap.Config.ARGB_8888 : this.eg);
        a2.setHasAlpha(true);
        return a2;
    }

    private void b(b bVar) {
        int i;
        int i2;
        int i3;
        b bVar2 = bVar;
        int[] iArr = this.dZ;
        int i4 = bVar2.ih / this.sampleSize;
        int i5 = bVar2.iy / this.sampleSize;
        int i6 = bVar2.iw / this.sampleSize;
        int i7 = bVar2.ix / this.sampleSize;
        boolean z = this.ea == 0;
        int i8 = this.sampleSize;
        int i9 = this.ee;
        int i10 = this.ed;
        byte[] bArr = this.dY;
        int[] iArr2 = this.act;
        boolean z2 = this.ef;
        int i11 = 8;
        int i12 = 0;
        int i13 = 0;
        int i14 = 1;
        while (i13 < i4) {
            if (bVar2.interlace) {
                if (i12 >= i4) {
                    i14++;
                    switch (i14) {
                        case 2:
                            i12 = 4;
                            break;
                        case 3:
                            i11 = 4;
                            i12 = 2;
                            break;
                        case 4:
                            i11 = 2;
                            i12 = 1;
                            break;
                    }
                }
                i = i12 + i11;
            } else {
                i = i12;
                i12 = i13;
            }
            int i15 = i12 + i5;
            int i16 = i4;
            boolean z3 = i8 == 1;
            if (i15 < i10) {
                int i17 = i15 * i9;
                int i18 = i17 + i7;
                i3 = i5;
                int i19 = i18 + i6;
                int i20 = i17 + i9;
                if (i20 < i19) {
                    i19 = i20;
                }
                i2 = i6;
                int i21 = i13 * i8 * bVar2.iw;
                if (z3) {
                    for (int i22 = i18; i22 < i19; i22++) {
                        int i23 = iArr2[bArr[i21] & 255];
                        if (i23 != 0) {
                            iArr[i22] = i23;
                        } else if (z && z2 == null) {
                            z2 = true;
                        }
                        i21 += i8;
                    }
                } else {
                    int i24 = ((i19 - i18) * i8) + i21;
                    int i25 = i18;
                    while (i25 < i19) {
                        int i26 = i19;
                        int a2 = a(i21, i24, bVar2.iw);
                        if (a2 != 0) {
                            iArr[i25] = a2;
                        } else if (z && z2 == null) {
                            z2 = true;
                            i21 += i8;
                            i25++;
                            i19 = i26;
                        }
                        i21 += i8;
                        i25++;
                        i19 = i26;
                    }
                }
            } else {
                i3 = i5;
                i2 = i6;
            }
            i13++;
            i12 = i;
            i4 = i16;
            i5 = i3;
            i6 = i2;
        }
        if (this.ef == null) {
            Boolean bool = z2;
            this.ef = Boolean.valueOf(bool == null ? false : bool.booleanValue());
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v0, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r29v0, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v6, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v8, resolved type: byte} */
    /* JADX WARNING: Incorrect type for immutable var: ssa=short, code=int, for r4v19, types: [short] */
    /* JADX WARNING: Multi-variable type inference failed */
    private void c(b bVar) {
        int i;
        int i2;
        int i3;
        e eVar = this;
        b bVar2 = bVar;
        if (bVar2 != null) {
            eVar.dP.position(bVar2.dp);
        }
        if (bVar2 == null) {
            i = eVar.dQ.width * eVar.dQ.height;
        } else {
            i = bVar2.ih * bVar2.iw;
        }
        if (eVar.dY == null || eVar.dY.length < i) {
            eVar.dY = eVar.dW.p(i);
        }
        byte[] bArr = eVar.dY;
        if (eVar.prefix == null) {
            eVar.prefix = new short[4096];
        }
        short[] sArr = eVar.prefix;
        if (eVar.suffix == null) {
            eVar.suffix = new byte[4096];
        }
        byte[] bArr2 = eVar.suffix;
        if (eVar.pixelStack == null) {
            eVar.pixelStack = new byte[4097];
        }
        byte[] bArr3 = eVar.pixelStack;
        int readByte = readByte();
        int i4 = 1 << readByte;
        int i5 = i4 + 1;
        int i6 = i4 + 2;
        int i7 = readByte + 1;
        int i8 = (1 << i7) - 1;
        int i9 = 0;
        for (int i10 = 0; i10 < i4; i10++) {
            sArr[i10] = 0;
            bArr2[i10] = (byte) i10;
        }
        byte[] bArr4 = eVar.block;
        int i11 = i7;
        int i12 = i6;
        int i13 = i8;
        int i14 = 0;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        int i20 = 0;
        int i21 = -1;
        while (true) {
            if (i9 < i) {
                if (i14 == 0) {
                    i14 = readBlock();
                    if (i14 <= 0) {
                        eVar.status = 3;
                        break;
                    }
                    i18 = 0;
                }
                i17 += (bArr4[i18] & 255) << i16;
                i18++;
                i14--;
                int i22 = i16 + 8;
                int i23 = i21;
                int i24 = i19;
                int i25 = i12;
                int i26 = i15;
                int i27 = i9;
                int i28 = i11;
                while (true) {
                    if (i22 < i28) {
                        i12 = i25;
                        i19 = i24;
                        i21 = i23;
                        i11 = i28;
                        i9 = i27;
                        i15 = i26;
                        i16 = i22;
                        eVar = this;
                        break;
                    }
                    int i29 = i17 & i13;
                    i17 >>= i28;
                    i22 -= i28;
                    if (i29 == i4) {
                        i28 = i7;
                        i25 = i6;
                        i13 = i8;
                        i23 = -1;
                    } else if (i29 == i5) {
                        i21 = i23;
                        i11 = i28;
                        i9 = i27;
                        i15 = i26;
                        i12 = i25;
                        i19 = i24;
                        i16 = i22;
                        break;
                    } else if (i23 == -1) {
                        bArr[i26] = bArr2[i29];
                        i26++;
                        i27++;
                        i23 = i29;
                        i24 = i23;
                        eVar = this;
                    } else {
                        int i30 = i25;
                        if (i29 >= i30) {
                            i2 = i22;
                            bArr3[i20] = (byte) i24;
                            i20++;
                            i3 = i23;
                        } else {
                            i2 = i22;
                            i3 = i29;
                        }
                        while (i3 >= i4) {
                            bArr3[i20] = bArr2[i3];
                            i20++;
                            i3 = sArr[i3];
                        }
                        int i31 = bArr2[i3] & 255;
                        int i32 = i7;
                        byte b2 = (byte) i31;
                        bArr[i26] = b2;
                        i26++;
                        i27++;
                        while (i20 > 0) {
                            i20--;
                            bArr[i26] = bArr3[i20];
                            i26++;
                            i27++;
                        }
                        int i33 = i31;
                        if (i30 < 4096) {
                            sArr[i30] = (short) i23;
                            bArr2[i30] = b2;
                            i30++;
                            if ((i30 & i13) == 0) {
                                if (i30 < 4096) {
                                    i28++;
                                    i13 += i30;
                                }
                            }
                        }
                        i25 = i30;
                        i23 = i29;
                        i22 = i2;
                        i7 = i32;
                        i24 = i33;
                        eVar = this;
                    }
                }
            } else {
                break;
            }
        }
        Arrays.fill(bArr, i15, i, (byte) 0);
    }

    private int readBlock() {
        int readByte = readByte();
        if (readByte <= 0) {
            return readByte;
        }
        this.dP.get(this.block, 0, Math.min(readByte, this.dP.remaining()));
        return readByte;
    }

    private int readByte() {
        return this.dP.get() & 255;
    }

    public int a(@Nullable InputStream inputStream, int i) {
        if (inputStream != null) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i > 0 ? i + 4096 : 16384);
                byte[] bArr = new byte[16384];
                while (true) {
                    int read = inputStream.read(bArr, 0, bArr.length);
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                byteArrayOutputStream.flush();
                read(byteArrayOutputStream.toByteArray());
            } catch (IOException e) {
                Log.w(TAG, "Error reading data from stream", e);
            }
        } else {
            this.status = 2;
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e2) {
                Log.w(TAG, "Error closing stream", e2);
            }
        }
        return this.status;
    }

    public void a(@NonNull Bitmap.Config config) {
        if (config == Bitmap.Config.ARGB_8888 || config == Bitmap.Config.RGB_565) {
            this.eg = config;
            return;
        }
        throw new IllegalArgumentException("Unsupported format: " + config + ", must be one of " + Bitmap.Config.ARGB_8888 + " or " + Bitmap.Config.RGB_565);
    }

    public synchronized void a(@NonNull c cVar, @NonNull ByteBuffer byteBuffer) {
        a(cVar, byteBuffer, 1);
    }

    public synchronized void a(@NonNull c cVar, @NonNull ByteBuffer byteBuffer, int i) {
        if (i > 0) {
            int highestOneBit = Integer.highestOneBit(i);
            this.status = 0;
            this.dQ = cVar;
            this.ea = -1;
            this.dP = byteBuffer.asReadOnlyBuffer();
            this.dP.position(0);
            this.dP.order(ByteOrder.LITTLE_ENDIAN);
            this.ec = false;
            Iterator<b> it = cVar.dv.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (it.next().dispose == 3) {
                    this.ec = true;
                    break;
                }
            }
            this.sampleSize = highestOneBit;
            this.ee = cVar.width / highestOneBit;
            this.ed = cVar.height / highestOneBit;
            this.dY = this.dW.p(cVar.width * cVar.height);
            this.dZ = this.dW.q(this.ee * this.ed);
        } else {
            throw new IllegalArgumentException("Sample size must be >=0, not: " + i);
        }
    }

    public synchronized void a(@NonNull c cVar, @NonNull byte[] bArr) {
        a(cVar, ByteBuffer.wrap(bArr));
    }

    public int aA() {
        return this.dP.limit() + this.dY.length + (this.dZ.length * 4);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00ea, code lost:
        return null;
     */
    @Nullable
    public synchronized Bitmap aB() {
        if (this.dQ.frameCount <= 0 || this.ea < 0) {
            if (Log.isLoggable(TAG, 3)) {
                String str = TAG;
                Log.d(str, "Unable to decode frame, frameCount=" + this.dQ.frameCount + ", framePointer=" + this.ea);
            }
            this.status = 1;
        }
        if (this.status != 1) {
            if (this.status != 2) {
                this.status = 0;
                if (this.block == null) {
                    this.block = this.dW.p(255);
                }
                b bVar = this.dQ.dv.get(this.ea);
                int i = this.ea - 1;
                b bVar2 = i >= 0 ? this.dQ.dv.get(i) : null;
                this.act = bVar.lct != null ? bVar.lct : this.dQ.gct;
                if (this.act == null) {
                    if (Log.isLoggable(TAG, 3)) {
                        String str2 = TAG;
                        Log.d(str2, "No valid color table found for frame #" + this.ea);
                    }
                    this.status = 1;
                    return null;
                }
                if (bVar.transparency) {
                    System.arraycopy(this.act, 0, this.dV, 0, this.act.length);
                    this.act = this.dV;
                    this.act[bVar.transIndex] = 0;
                }
                return a(bVar, bVar2);
            }
        }
        if (Log.isLoggable(TAG, 3)) {
            String str3 = TAG;
            Log.d(str3, "Unable to decode frame, status=" + this.status);
        }
    }

    public void advance() {
        this.ea = (this.ea + 1) % this.dQ.frameCount;
    }

    public int av() {
        if (this.dQ.frameCount <= 0 || this.ea < 0) {
            return 0;
        }
        return getDelay(this.ea);
    }

    public int aw() {
        return this.ea;
    }

    public void ax() {
        this.ea = -1;
    }

    public int ay() {
        return this.dQ.loopCount;
    }

    public int az() {
        if (this.dQ.loopCount == -1) {
            return 1;
        }
        if (this.dQ.loopCount == 0) {
            return 0;
        }
        return this.dQ.loopCount + 1;
    }

    public void clear() {
        this.dQ = null;
        if (this.dY != null) {
            this.dW.c(this.dY);
        }
        if (this.dZ != null) {
            this.dW.b(this.dZ);
        }
        if (this.eb != null) {
            this.dW.c(this.eb);
        }
        this.eb = null;
        this.dP = null;
        this.ef = null;
        if (this.block != null) {
            this.dW.c(this.block);
        }
    }

    @NonNull
    public ByteBuffer getData() {
        return this.dP;
    }

    public int getDelay(int i) {
        if (i < 0 || i >= this.dQ.frameCount) {
            return -1;
        }
        return this.dQ.dv.get(i).delay;
    }

    public int getFrameCount() {
        return this.dQ.frameCount;
    }

    public int getHeight() {
        return this.dQ.height;
    }

    @Deprecated
    public int getLoopCount() {
        if (this.dQ.loopCount == -1) {
            return 1;
        }
        return this.dQ.loopCount;
    }

    public int getStatus() {
        return this.status;
    }

    public int getWidth() {
        return this.dQ.width;
    }

    public synchronized int read(@Nullable byte[] bArr) {
        this.dQ = aG().d(bArr).aD();
        if (bArr != null) {
            a(this.dQ, bArr);
        }
        return this.status;
    }
}
