package com.bumptech.glide.load.resource.bitmap;

import android.support.annotation.NonNull;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.util.i;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

/* compiled from: DefaultImageHeaderParser */
public final class m implements ImageHeaderParser {
    private static final String TAG = "DfltImageHeaderParser";
    private static final int lF = 4671814;
    private static final int lG = -1991225785;
    static final int lH = 65496;
    private static final int lI = 19789;
    private static final int lJ = 18761;
    private static final String lK = "Exif\u0000\u0000";
    static final byte[] lL = lK.getBytes(Charset.forName("UTF-8"));
    private static final int lM = 218;
    private static final int lN = 217;
    static final int lO = 255;
    static final int lP = 225;
    private static final int lQ = 274;
    private static final int[] lR = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8};
    private static final int lS = 1380533830;
    private static final int lT = 1464156752;
    private static final int lU = 1448097792;
    private static final int lV = -256;
    private static final int lW = 255;
    private static final int lX = 88;
    private static final int lY = 76;
    private static final int lZ = 16;
    private static final int ma = 8;

    /* compiled from: DefaultImageHeaderParser */
    private static final class a implements c {
        private final ByteBuffer byteBuffer;

        a(ByteBuffer byteBuffer2) {
            this.byteBuffer = byteBuffer2;
            byteBuffer2.order(ByteOrder.BIG_ENDIAN);
        }

        public int cT() {
            return ((getByte() << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (getByte() & 255);
        }

        public short cU() {
            return (short) (getByte() & 255);
        }

        public int getByte() {
            if (this.byteBuffer.remaining() < 1) {
                return -1;
            }
            return this.byteBuffer.get();
        }

        public int read(byte[] bArr, int i) {
            int min = Math.min(i, this.byteBuffer.remaining());
            if (min == 0) {
                return -1;
            }
            this.byteBuffer.get(bArr, 0, min);
            return min;
        }

        public long skip(long j) {
            int min = (int) Math.min((long) this.byteBuffer.remaining(), j);
            this.byteBuffer.position(this.byteBuffer.position() + min);
            return (long) min;
        }
    }

    /* compiled from: DefaultImageHeaderParser */
    private static final class b {
        private final ByteBuffer data;

        b(byte[] bArr, int i) {
            this.data = (ByteBuffer) ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN).limit(i);
        }

        private boolean m(int i, int i2) {
            return this.data.remaining() - i >= i2;
        }

        /* access modifiers changed from: package-private */
        public int F(int i) {
            if (m(i, 4)) {
                return this.data.getInt(i);
            }
            return -1;
        }

        /* access modifiers changed from: package-private */
        public short G(int i) {
            if (m(i, 2)) {
                return this.data.getShort(i);
            }
            return -1;
        }

        /* access modifiers changed from: package-private */
        public void a(ByteOrder byteOrder) {
            this.data.order(byteOrder);
        }

        /* access modifiers changed from: package-private */
        public int length() {
            return this.data.remaining();
        }
    }

    /* compiled from: DefaultImageHeaderParser */
    private interface c {
        int cT() throws IOException;

        short cU() throws IOException;

        int getByte() throws IOException;

        int read(byte[] bArr, int i) throws IOException;

        long skip(long j) throws IOException;
    }

    /* compiled from: DefaultImageHeaderParser */
    private static final class d implements c {
        private final InputStream is;

        d(InputStream inputStream) {
            this.is = inputStream;
        }

        public int cT() throws IOException {
            return ((this.is.read() << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (this.is.read() & 255);
        }

        public short cU() throws IOException {
            return (short) (this.is.read() & 255);
        }

        public int getByte() throws IOException {
            return this.is.read();
        }

        public int read(byte[] bArr, int i) throws IOException {
            int i2 = i;
            while (i2 > 0) {
                int read = this.is.read(bArr, i - i2, i2);
                if (read == -1) {
                    break;
                }
                i2 -= read;
            }
            return i - i2;
        }

        public long skip(long j) throws IOException {
            if (j < 0) {
                return 0;
            }
            long j2 = j;
            while (j2 > 0) {
                long skip = this.is.skip(j2);
                if (skip > 0) {
                    j2 -= skip;
                } else if (this.is.read() == -1) {
                    break;
                } else {
                    j2--;
                }
            }
            return j - j2;
        }
    }

    private static boolean E(int i) {
        return (i & lH) == lH || i == lI || i == lJ;
    }

    private static int a(b bVar) {
        ByteOrder byteOrder;
        int length = lK.length();
        short G = bVar.G(length);
        if (G == lJ) {
            byteOrder = ByteOrder.LITTLE_ENDIAN;
        } else if (G != lI) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Unknown endianness = " + G);
            }
            byteOrder = ByteOrder.BIG_ENDIAN;
        } else {
            byteOrder = ByteOrder.BIG_ENDIAN;
        }
        bVar.a(byteOrder);
        int F = bVar.F(length + 4) + length;
        short G2 = bVar.G(F);
        for (int i = 0; i < G2; i++) {
            int l = l(F, i);
            short G3 = bVar.G(l);
            if (G3 == 274) {
                short G4 = bVar.G(l + 2);
                if (G4 >= 1 && G4 <= 12) {
                    int F2 = bVar.F(l + 4);
                    if (F2 >= 0) {
                        if (Log.isLoggable(TAG, 3)) {
                            Log.d(TAG, "Got tagIndex=" + i + " tagType=" + G3 + " formatCode=" + G4 + " componentCount=" + F2);
                        }
                        int i2 = F2 + lR[G4];
                        if (i2 <= 4) {
                            int i3 = l + 8;
                            if (i3 < 0 || i3 > bVar.length()) {
                                if (Log.isLoggable(TAG, 3)) {
                                    Log.d(TAG, "Illegal tagValueOffset=" + i3 + " tagType=" + G3);
                                }
                            } else if (i2 >= 0 && i2 + i3 <= bVar.length()) {
                                return bVar.G(i3);
                            } else {
                                if (Log.isLoggable(TAG, 3)) {
                                    Log.d(TAG, "Illegal number of bytes for TI tag data tagType=" + G3);
                                }
                            }
                        } else if (Log.isLoggable(TAG, 3)) {
                            Log.d(TAG, "Got byte count > 4, not orientation, continuing, formatCode=" + G4);
                        }
                    } else if (Log.isLoggable(TAG, 3)) {
                        Log.d(TAG, "Negative tiff component count");
                    }
                } else if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Got invalid format code = " + G4);
                }
            }
        }
        return -1;
    }

    private int a(c cVar, com.bumptech.glide.load.engine.bitmap_recycle.b bVar) throws IOException {
        int cT = cVar.cT();
        if (!E(cT)) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Parser doesn't handle magic number: " + cT);
            }
            return -1;
        }
        int b2 = b(cVar);
        if (b2 == -1) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Failed to parse exif segment length, or exif segment not found");
            }
            return -1;
        }
        byte[] bArr = (byte[]) bVar.a(b2, byte[].class);
        try {
            return a(cVar, bArr, b2);
        } finally {
            bVar.put(bArr);
        }
    }

    private int a(c cVar, byte[] bArr, int i) throws IOException {
        int read = cVar.read(bArr, i);
        if (read != i) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Unable to read exif segment data, length: " + i + ", actually read: " + read);
            }
            return -1;
        } else if (c(bArr, i)) {
            return a(new b(bArr, i));
        } else {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Missing jpeg exif preamble");
            }
            return -1;
        }
    }

    @NonNull
    private ImageHeaderParser.ImageType a(c cVar) throws IOException {
        int cT = cVar.cT();
        if (cT == lH) {
            return ImageHeaderParser.ImageType.JPEG;
        }
        int cT2 = ((cT << 16) & SupportMenu.CATEGORY_MASK) | (cVar.cT() & SupportMenu.USER_MASK);
        if (cT2 == lG) {
            cVar.skip(21);
            return cVar.getByte() >= 3 ? ImageHeaderParser.ImageType.PNG_A : ImageHeaderParser.ImageType.PNG;
        } else if ((cT2 >> 8) == lF) {
            return ImageHeaderParser.ImageType.GIF;
        } else {
            if (cT2 != lS) {
                return ImageHeaderParser.ImageType.UNKNOWN;
            }
            cVar.skip(4);
            if ((((cVar.cT() << 16) & SupportMenu.CATEGORY_MASK) | (cVar.cT() & SupportMenu.USER_MASK)) != lT) {
                return ImageHeaderParser.ImageType.UNKNOWN;
            }
            int cT3 = ((cVar.cT() << 16) & SupportMenu.CATEGORY_MASK) | (cVar.cT() & SupportMenu.USER_MASK);
            if ((cT3 & -256) != lU) {
                return ImageHeaderParser.ImageType.UNKNOWN;
            }
            int i = cT3 & 255;
            if (i == 88) {
                cVar.skip(4);
                return (cVar.getByte() & 16) != 0 ? ImageHeaderParser.ImageType.WEBP_A : ImageHeaderParser.ImageType.WEBP;
            } else if (i != 76) {
                return ImageHeaderParser.ImageType.WEBP;
            } else {
                cVar.skip(4);
                return (cVar.getByte() & 8) != 0 ? ImageHeaderParser.ImageType.WEBP_A : ImageHeaderParser.ImageType.WEBP;
            }
        }
    }

    private int b(c cVar) throws IOException {
        short cU;
        int cT;
        long j;
        do {
            if (cVar.cU() != 255) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Unknown segmentId=" + r0);
                }
                return -1;
            }
            cU = cVar.cU();
            if (cU == 218) {
                return -1;
            }
            if (cU == 217) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Found MARKER_EOI in exif segment");
                }
                return -1;
            }
            cT = cVar.cT() - 2;
            if (cU == 225) {
                return cT;
            }
            j = (long) cT;
        } while (cVar.skip(j) == j);
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "Unable to skip enough data, type: " + cU + ", wanted to skip: " + cT + ", but actually skipped: " + r6);
        }
        return -1;
    }

    private boolean c(byte[] bArr, int i) {
        boolean z = bArr != null && i > lL.length;
        if (!z) {
            return z;
        }
        for (int i2 = 0; i2 < lL.length; i2++) {
            if (bArr[i2] != lL[i2]) {
                return false;
            }
        }
        return z;
    }

    private static int l(int i, int i2) {
        return i + 2 + (12 * i2);
    }

    public int a(@NonNull InputStream inputStream, @NonNull com.bumptech.glide.load.engine.bitmap_recycle.b bVar) throws IOException {
        return a((c) new d((InputStream) i.checkNotNull(inputStream)), (com.bumptech.glide.load.engine.bitmap_recycle.b) i.checkNotNull(bVar));
    }

    public int a(@NonNull ByteBuffer byteBuffer, @NonNull com.bumptech.glide.load.engine.bitmap_recycle.b bVar) throws IOException {
        return a((c) new a((ByteBuffer) i.checkNotNull(byteBuffer)), (com.bumptech.glide.load.engine.bitmap_recycle.b) i.checkNotNull(bVar));
    }

    @NonNull
    public ImageHeaderParser.ImageType b(@NonNull ByteBuffer byteBuffer) throws IOException {
        return a((c) new a((ByteBuffer) i.checkNotNull(byteBuffer)));
    }

    @NonNull
    public ImageHeaderParser.ImageType c(@NonNull InputStream inputStream) throws IOException {
        return a((c) new d((InputStream) i.checkNotNull(inputStream)));
    }
}
