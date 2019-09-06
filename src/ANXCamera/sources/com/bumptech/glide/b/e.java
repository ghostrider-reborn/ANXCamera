package com.bumptech.glide.b;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.bumptech.glide.b.a.C0004a;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Iterator;

/* compiled from: StandardGifDecoder */
public class e implements a {
    private static final int Fd = -1;
    private static final int Gd = -1;
    private static final int Hd = 4;
    @ColorInt
    private static final int Jd = 0;
    private static final int MAX_STACK_SIZE = 4096;
    private static final String TAG = "e";
    private static final int Yc = 255;
    private boolean Ad;
    private int Bd;
    private int Cd;
    @Nullable
    private Boolean Dd;
    @NonNull
    private Config Ed;
    private ByteBuffer Xc;
    @ColorInt
    private int[] act;
    private byte[] block;
    private c header;
    private d parser;
    private byte[] pixelStack;
    private short[] prefix;
    private int sampleSize;
    private int status;
    private byte[] suffix;
    @ColorInt
    private final int[] ud;
    private final C0004a vd;
    private byte[] wd;
    @ColorInt
    private int[] xd;
    private int yd;
    private Bitmap zd;

    public e(@NonNull C0004a aVar) {
        this.ud = new int[256];
        this.Ed = Config.ARGB_8888;
        this.vd = aVar;
        this.header = new c();
    }

    public e(@NonNull C0004a aVar, c cVar, ByteBuffer byteBuffer) {
        this(aVar, cVar, byteBuffer, 1);
    }

    public e(@NonNull C0004a aVar, c cVar, ByteBuffer byteBuffer, int i) {
        this(aVar);
        a(cVar, byteBuffer, i);
    }

    @NonNull
    private d Zj() {
        if (this.parser == null) {
            this.parser = new d();
        }
        return this.parser;
    }

    private Bitmap _j() {
        Boolean bool = this.Dd;
        Bitmap a2 = this.vd.a(this.Cd, this.Bd, (bool == null || bool.booleanValue()) ? Config.ARGB_8888 : this.Ed);
        a2.setHasAlpha(true);
        return a2;
    }

    private Bitmap a(b bVar, b bVar2) {
        int[] iArr = this.xd;
        int i = 0;
        if (bVar2 == null) {
            Bitmap bitmap = this.zd;
            if (bitmap != null) {
                this.vd.d(bitmap);
            }
            this.zd = null;
            Arrays.fill(iArr, 0);
        }
        if (bVar2 != null && bVar2.dispose == 3 && this.zd == null) {
            Arrays.fill(iArr, 0);
        }
        if (bVar2 != null) {
            int i2 = bVar2.dispose;
            if (i2 > 0) {
                if (i2 == 2) {
                    if (!bVar.transparency) {
                        c cVar = this.header;
                        int i3 = cVar.bgColor;
                        if (bVar.lct == null || cVar.bgIndex != bVar.transIndex) {
                            i = i3;
                        }
                    } else if (this.yd == 0) {
                        this.Dd = Boolean.valueOf(true);
                    }
                    int i4 = bVar2.ih;
                    int i5 = this.sampleSize;
                    int i6 = i4 / i5;
                    int i7 = bVar2.iy / i5;
                    int i8 = bVar2.iw / i5;
                    int i9 = bVar2.ix / i5;
                    int i10 = this.Cd;
                    int i11 = (i7 * i10) + i9;
                    int i12 = (i6 * i10) + i11;
                    while (i11 < i12) {
                        int i13 = i11 + i8;
                        for (int i14 = i11; i14 < i13; i14++) {
                            iArr[i14] = i;
                        }
                        i11 += this.Cd;
                    }
                } else if (i2 == 3) {
                    Bitmap bitmap2 = this.zd;
                    if (bitmap2 != null) {
                        int i15 = this.Cd;
                        bitmap2.getPixels(iArr, 0, i15, 0, 0, i15, this.Bd);
                    }
                }
            }
        }
        c(bVar);
        if (bVar.interlace || this.sampleSize != 1) {
            a(bVar);
        } else {
            b(bVar);
        }
        if (this.Ad) {
            int i16 = bVar.dispose;
            if (i16 == 0 || i16 == 1) {
                if (this.zd == null) {
                    this.zd = _j();
                }
                Bitmap bitmap3 = this.zd;
                int i17 = this.Cd;
                bitmap3.setPixels(iArr, 0, i17, 0, 0, i17, this.Bd);
            }
        }
        Bitmap _j = _j();
        int i18 = this.Cd;
        _j.setPixels(iArr, 0, i18, 0, 0, i18, this.Bd);
        return _j;
    }

    private void a(b bVar) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        b bVar2 = bVar;
        int[] iArr = this.xd;
        int i6 = bVar2.ih;
        int i7 = this.sampleSize;
        int i8 = i6 / i7;
        int i9 = bVar2.iy / i7;
        int i10 = bVar2.iw / i7;
        int i11 = bVar2.ix / i7;
        int i12 = this.yd;
        Boolean valueOf = Boolean.valueOf(true);
        boolean z = i12 == 0;
        int i13 = this.sampleSize;
        int i14 = this.Cd;
        int i15 = this.Bd;
        byte[] bArr = this.wd;
        int[] iArr2 = this.act;
        int i16 = 1;
        int i17 = 8;
        int i18 = 0;
        Boolean bool = this.Dd;
        int i19 = 0;
        while (i19 < i8) {
            Boolean bool2 = valueOf;
            if (bVar2.interlace) {
                if (i18 >= i8) {
                    i = i8;
                    i5 = i16 + 1;
                    if (i5 == 2) {
                        i18 = 4;
                    } else if (i5 == 3) {
                        i17 = 4;
                        i18 = 2;
                    } else if (i5 == 4) {
                        i18 = 1;
                        i17 = 2;
                    }
                } else {
                    i = i8;
                    i5 = i16;
                }
                i2 = i18 + i17;
                i16 = i5;
            } else {
                i = i8;
                i2 = i18;
                i18 = i19;
            }
            int i20 = i18 + i9;
            boolean z2 = i13 == 1;
            if (i20 < i15) {
                int i21 = i20 * i14;
                int i22 = i21 + i11;
                int i23 = i22 + i10;
                int i24 = i21 + i14;
                if (i24 < i23) {
                    i23 = i24;
                }
                i3 = i9;
                int i25 = i19 * i13 * bVar2.iw;
                if (z2) {
                    int i26 = i22;
                    while (i26 < i23) {
                        int i27 = i10;
                        int i28 = iArr2[bArr[i25] & 255];
                        if (i28 != 0) {
                            iArr[i26] = i28;
                        } else if (z && bool == null) {
                            bool = bool2;
                        }
                        i25 += i13;
                        i26++;
                        i10 = i27;
                    }
                } else {
                    i4 = i10;
                    int i29 = ((i23 - i22) * i13) + i25;
                    int i30 = i22;
                    while (i30 < i23) {
                        int i31 = i23;
                        int b2 = b(i25, i29, bVar2.iw);
                        if (b2 != 0) {
                            iArr[i30] = b2;
                        } else if (z && bool == null) {
                            bool = bool2;
                        }
                        i25 += i13;
                        i30++;
                        i23 = i31;
                    }
                    i19++;
                    i18 = i2;
                    i10 = i4;
                    valueOf = bool2;
                    i8 = i;
                    i9 = i3;
                }
            } else {
                i3 = i9;
            }
            i4 = i10;
            i19++;
            i18 = i2;
            i10 = i4;
            valueOf = bool2;
            i8 = i;
            i9 = i3;
        }
        if (this.Dd == null) {
            this.Dd = Boolean.valueOf(bool == null ? false : bool.booleanValue());
        }
    }

    @ColorInt
    private int b(int i, int i2, int i3) {
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        for (int i9 = i; i9 < this.sampleSize + i; i9++) {
            byte[] bArr = this.wd;
            if (i9 >= bArr.length || i9 >= i2) {
                break;
            }
            int i10 = this.act[bArr[i9] & 255];
            if (i10 != 0) {
                i4 += (i10 >> 24) & 255;
                i5 += (i10 >> 16) & 255;
                i6 += (i10 >> 8) & 255;
                i7 += i10 & 255;
                i8++;
            }
        }
        int i11 = i + i3;
        for (int i12 = i11; i12 < this.sampleSize + i11; i12++) {
            byte[] bArr2 = this.wd;
            if (i12 >= bArr2.length || i12 >= i2) {
                break;
            }
            int i13 = this.act[bArr2[i12] & 255];
            if (i13 != 0) {
                i4 += (i13 >> 24) & 255;
                i5 += (i13 >> 16) & 255;
                i6 += (i13 >> 8) & 255;
                i7 += i13 & 255;
                i8++;
            }
        }
        if (i8 == 0) {
            return 0;
        }
        return ((i4 / i8) << 24) | ((i5 / i8) << 16) | ((i6 / i8) << 8) | (i7 / i8);
    }

    private void b(b bVar) {
        b bVar2 = bVar;
        int[] iArr = this.xd;
        int i = bVar2.ih;
        int i2 = bVar2.iy;
        int i3 = bVar2.iw;
        int i4 = bVar2.ix;
        boolean z = this.yd == 0;
        int i5 = this.Cd;
        byte[] bArr = this.wd;
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
            int i11 = bVar2.iw * i6;
            int i12 = i8;
            while (i12 < i9) {
                byte b3 = bArr[i11];
                byte b4 = b3 & 255;
                if (b4 != b2) {
                    int i13 = iArr2[b4];
                    if (i13 != 0) {
                        iArr[i12] = i13;
                    } else {
                        b2 = b3;
                    }
                }
                i11++;
                i12++;
                b bVar3 = bVar;
            }
            i6++;
            bVar2 = bVar;
        }
        this.Dd = Boolean.valueOf(this.Dd == null && z && b2 != -1);
    }

    /* JADX WARNING: type inference failed for: r3v1, types: [short[]] */
    /* JADX WARNING: type inference failed for: r22v0 */
    /* JADX WARNING: type inference failed for: r22v1 */
    /* JADX WARNING: type inference failed for: r28v0 */
    /* JADX WARNING: type inference failed for: r28v1 */
    /* JADX WARNING: type inference failed for: r15v1 */
    /* JADX WARNING: type inference failed for: r22v2 */
    /* JADX WARNING: type inference failed for: r22v3 */
    /* JADX WARNING: type inference failed for: r17v4 */
    /* JADX WARNING: type inference failed for: r28v2 */
    /* JADX WARNING: type inference failed for: r22v4 */
    /* JADX WARNING: type inference failed for: r4v16, types: [short] */
    /* JADX WARNING: type inference failed for: r4v18, types: [int] */
    /* JADX WARNING: type inference failed for: r28v4 */
    /* JADX WARNING: type inference failed for: r28v6 */
    /* JADX WARNING: type inference failed for: r22v5 */
    /* JADX WARNING: type inference failed for: r17v6 */
    /* JADX WARNING: type inference failed for: r28v7 */
    /* JADX WARNING: type inference failed for: r4v22 */
    /* JADX WARNING: type inference failed for: r28v8 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=short, code=int, for r4v16, types: [short] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=short[], code=null, for r3v1, types: [short[]] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r22v3
  assigns: []
  uses: []
  mth insns count: 168
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 10 */
    private void c(b bVar) {
        int i;
        int i2;
        ? r22;
        int i3;
        ? r28;
        ? r4;
        int i4;
        int i5;
        ? r282;
        e eVar = this;
        b bVar2 = bVar;
        if (bVar2 != null) {
            eVar.Xc.position(bVar2.Pc);
        }
        if (bVar2 == null) {
            c cVar = eVar.header;
            i = cVar.width;
            i2 = cVar.height;
        } else {
            i = bVar2.iw;
            i2 = bVar2.ih;
        }
        int i6 = i * i2;
        byte[] bArr = eVar.wd;
        if (bArr == null || bArr.length < i6) {
            eVar.wd = eVar.vd.d(i6);
        }
        byte[] bArr2 = eVar.wd;
        if (eVar.prefix == null) {
            eVar.prefix = new short[4096];
        }
        ? r3 = eVar.prefix;
        if (eVar.suffix == null) {
            eVar.suffix = new byte[4096];
        }
        byte[] bArr3 = eVar.suffix;
        if (eVar.pixelStack == null) {
            eVar.pixelStack = new byte[4097];
        }
        byte[] bArr4 = eVar.pixelStack;
        int readByte = readByte();
        int i7 = 1 << readByte;
        int i8 = i7 + 1;
        int i9 = i7 + 2;
        int i10 = readByte + 1;
        int i11 = (1 << i10) - 1;
        int i12 = 0;
        for (int i13 = 0; i13 < i7; i13++) {
            r3[i13] = 0;
            bArr3[i13] = (byte) i13;
        }
        byte[] bArr5 = eVar.block;
        int i14 = i10;
        int i15 = i9;
        int i16 = i11;
        int i17 = 0;
        ? r17 = 0;
        int i18 = 0;
        int i19 = 0;
        int i20 = 0;
        ? r222 = 0;
        int i21 = 0;
        int i22 = -1;
        while (true) {
            if (i12 >= i6) {
                break;
            }
            if (i17 == 0) {
                i17 = readBlock();
                if (i17 <= 0) {
                    eVar.status = 3;
                    break;
                }
                i19 = 0;
            }
            i18 += (bArr5[i19] & 255) << r17;
            i19++;
            i17--;
            int i23 = r17 + 8;
            int i24 = i22;
            ? r283 = r222;
            int i25 = i15;
            int i26 = i20;
            int i27 = i12;
            int i28 = i14;
            while (true) {
                if (i23 < i28) {
                    i14 = i28;
                    r22 = r283;
                    i12 = i27;
                    i20 = i26;
                    i3 = i23;
                    i15 = i25;
                    i22 = i24;
                    eVar = this;
                    break;
                }
                byte b2 = i18 & i16;
                i18 >>= i28;
                i23 -= i28;
                if (b2 == i7) {
                    i28 = i10;
                    i25 = i9;
                    i16 = i11;
                    i24 = -1;
                    r28 = r283;
                } else if (b2 == i8) {
                    i3 = i23;
                    i14 = i28;
                    i12 = i27;
                    i20 = i26;
                    i15 = i25;
                    r22 = r283;
                    i22 = i24;
                    break;
                } else {
                    if (i24 == -1) {
                        bArr2[i26] = bArr3[b2];
                        i26++;
                        i27++;
                        i24 = b2;
                        i4 = i24;
                    } else {
                        int i29 = i25;
                        int i30 = i23;
                        if (b2 >= i29) {
                            bArr4[i21] = (byte) r283;
                            i21++;
                            i5 = i24;
                        } else {
                            i5 = b2;
                        }
                        while (r4 >= i7) {
                            bArr4[i21] = bArr3[r4];
                            i21++;
                            r4 = r3[r4];
                        }
                        byte b3 = bArr3[r4] & 255;
                        int i31 = i10;
                        byte b4 = (byte) b3;
                        bArr2[i26] = b4;
                        while (true) {
                            i26++;
                            i27++;
                            if (i21 <= 0) {
                                break;
                            }
                            i21--;
                            bArr2[i26] = bArr4[i21];
                        }
                        byte b5 = b3;
                        if (i29 < 4096) {
                            r3[i29] = (short) i24;
                            bArr3[i29] = b4;
                            i29++;
                            if ((i29 & i16) == 0 && i29 < 4096) {
                                i28++;
                                i16 += i29;
                            }
                        }
                        i24 = b2;
                        i23 = i30;
                        i10 = i31;
                        i4 = b5;
                        i25 = i29;
                    }
                    eVar = this;
                    r28 = r282;
                }
                r283 = r28;
            }
            r222 = r22;
            r17 = i3;
        }
        Arrays.fill(bArr2, i20, i6, 0);
    }

    private int readBlock() {
        int readByte = readByte();
        if (readByte <= 0) {
            return readByte;
        }
        ByteBuffer byteBuffer = this.Xc;
        byteBuffer.get(this.block, 0, Math.min(readByte, byteBuffer.remaining()));
        return readByte;
    }

    private int readByte() {
        return this.Xc.get() & 255;
    }

    public int F() {
        int i = this.header.loopCount;
        if (i == -1) {
            return 1;
        }
        if (i == 0) {
            return 0;
        }
        return i + 1;
    }

    public int I() {
        if (this.header.frameCount > 0) {
            int i = this.yd;
            if (i >= 0) {
                return getDelay(i);
            }
        }
        return 0;
    }

    public int N() {
        return this.header.loopCount;
    }

    public void a(@NonNull Config config) {
        if (config == Config.ARGB_8888 || config == Config.RGB_565) {
            this.Ed = config;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unsupported format: ");
        sb.append(config);
        sb.append(", must be one of ");
        sb.append(Config.ARGB_8888);
        sb.append(" or ");
        sb.append(Config.RGB_565);
        throw new IllegalArgumentException(sb.toString());
    }

    public synchronized void a(@NonNull c cVar, @NonNull ByteBuffer byteBuffer) {
        a(cVar, byteBuffer, 1);
    }

    public synchronized void a(@NonNull c cVar, @NonNull ByteBuffer byteBuffer, int i) {
        if (i > 0) {
            int highestOneBit = Integer.highestOneBit(i);
            this.status = 0;
            this.header = cVar;
            this.yd = -1;
            this.Xc = byteBuffer.asReadOnlyBuffer();
            this.Xc.position(0);
            this.Xc.order(ByteOrder.LITTLE_ENDIAN);
            this.Ad = false;
            Iterator it = cVar.frames.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (((b) it.next()).dispose == 3) {
                        this.Ad = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            this.sampleSize = highestOneBit;
            this.Cd = cVar.width / highestOneBit;
            this.Bd = cVar.height / highestOneBit;
            this.wd = this.vd.d(cVar.width * cVar.height);
            this.xd = this.vd.f(this.Cd * this.Bd);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Sample size must be >=0, not: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public synchronized void a(@NonNull c cVar, @NonNull byte[] bArr) {
        a(cVar, ByteBuffer.wrap(bArr));
    }

    public void advance() {
        this.yd = (this.yd + 1) % this.header.frameCount;
    }

    public void clear() {
        this.header = null;
        byte[] bArr = this.wd;
        if (bArr != null) {
            this.vd.d(bArr);
        }
        int[] iArr = this.xd;
        if (iArr != null) {
            this.vd.a(iArr);
        }
        Bitmap bitmap = this.zd;
        if (bitmap != null) {
            this.vd.d(bitmap);
        }
        this.zd = null;
        this.Xc = null;
        this.Dd = null;
        byte[] bArr2 = this.block;
        if (bArr2 != null) {
            this.vd.d(bArr2);
        }
    }

    @NonNull
    public ByteBuffer getData() {
        return this.Xc;
    }

    public int getDelay(int i) {
        if (i >= 0) {
            c cVar = this.header;
            if (i < cVar.frameCount) {
                return ((b) cVar.frames.get(i)).delay;
            }
        }
        return -1;
    }

    public int getFrameCount() {
        return this.header.frameCount;
    }

    public int getHeight() {
        return this.header.height;
    }

    @Deprecated
    public int getLoopCount() {
        int i = this.header.loopCount;
        if (i == -1) {
            return 1;
        }
        return i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00e9, code lost:
        return null;
     */
    @Nullable
    public synchronized Bitmap getNextFrame() {
        if (this.header.frameCount <= 0 || this.yd < 0) {
            if (Log.isLoggable(TAG, 3)) {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to decode frame, frameCount=");
                sb.append(this.header.frameCount);
                sb.append(", framePointer=");
                sb.append(this.yd);
                Log.d(str, sb.toString());
            }
            this.status = 1;
        }
        if (this.status != 1) {
            if (this.status != 2) {
                this.status = 0;
                if (this.block == null) {
                    this.block = this.vd.d(255);
                }
                b bVar = (b) this.header.frames.get(this.yd);
                int i = this.yd - 1;
                b bVar2 = i >= 0 ? (b) this.header.frames.get(i) : null;
                this.act = bVar.lct != null ? bVar.lct : this.header.gct;
                if (this.act == null) {
                    if (Log.isLoggable(TAG, 3)) {
                        String str2 = TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("No valid color table found for frame #");
                        sb2.append(this.yd);
                        Log.d(str2, sb2.toString());
                    }
                    this.status = 1;
                    return null;
                }
                if (bVar.transparency) {
                    System.arraycopy(this.act, 0, this.ud, 0, this.act.length);
                    this.act = this.ud;
                    this.act[bVar.transIndex] = 0;
                }
                return a(bVar, bVar2);
            }
        }
        if (Log.isLoggable(TAG, 3)) {
            String str3 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Unable to decode frame, status=");
            sb3.append(this.status);
            Log.d(str3, sb3.toString());
        }
    }

    public int getStatus() {
        return this.status;
    }

    public int getWidth() {
        return this.header.width;
    }

    public int read(@Nullable InputStream inputStream, int i) {
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
            } catch (IOException e2) {
                Log.w(TAG, "Error reading data from stream", e2);
            }
        } else {
            this.status = 2;
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e3) {
                Log.w(TAG, "Error closing stream", e3);
            }
        }
        return this.status;
    }

    public synchronized int read(@Nullable byte[] bArr) {
        this.header = Zj().setData(bArr).ag();
        if (bArr != null) {
            a(this.header, bArr);
        }
        return this.status;
    }

    public void s() {
        this.yd = -1;
    }

    public int v() {
        return this.yd;
    }

    public int x() {
        return this.Xc.limit() + this.wd.length + (this.xd.length * 4);
    }
}
