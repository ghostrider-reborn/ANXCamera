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
    static final int Ai = 65496;
    private static final int Bi = 19789;
    private static final int Ci = 18761;
    private static final String Di = "Exif\u0000\u0000";
    static final byte[] Ei = Di.getBytes(Charset.forName("UTF-8"));
    private static final int Fi = 218;
    private static final int Gi = 217;
    static final int Hi = 255;
    static final int Ii = 225;
    private static final int Ji = 274;
    private static final int[] Ki = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8};
    private static final int Li = 1380533830;
    private static final int Mi = 1464156752;
    private static final int Ni = 1448097792;
    private static final int Oi = -256;
    private static final int Pi = 255;
    private static final int Qi = 88;
    private static final int Ri = 76;
    private static final int Si = 16;
    private static final String TAG = "DfltImageHeaderParser";
    private static final int Ti = 8;
    private static final int yi = 4671814;
    private static final int zi = -1991225785;

    /* compiled from: DefaultImageHeaderParser */
    private static final class a implements c {
        private final ByteBuffer byteBuffer;

        a(ByteBuffer byteBuffer2) {
            this.byteBuffer = byteBuffer2;
            byteBuffer2.order(ByteOrder.BIG_ENDIAN);
        }

        public int B() {
            return (getByte() & 255) | ((getByte() << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
        }

        public short L() {
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
            ByteBuffer byteBuffer2 = this.byteBuffer;
            byteBuffer2.position(byteBuffer2.position() + min);
            return (long) min;
        }
    }

    /* compiled from: DefaultImageHeaderParser */
    private static final class b {
        private final ByteBuffer data;

        b(byte[] bArr, int i) {
            this.data = (ByteBuffer) ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN).limit(i);
        }

        private boolean isAvailable(int i, int i2) {
            return this.data.remaining() - i >= i2;
        }

        /* access modifiers changed from: package-private */
        public int length() {
            return this.data.remaining();
        }

        /* access modifiers changed from: package-private */
        public void order(ByteOrder byteOrder) {
            this.data.order(byteOrder);
        }

        /* access modifiers changed from: package-private */
        public short t(int i) {
            if (isAvailable(i, 2)) {
                return this.data.getShort(i);
            }
            return -1;
        }

        /* access modifiers changed from: package-private */
        public int u(int i) {
            if (isAvailable(i, 4)) {
                return this.data.getInt(i);
            }
            return -1;
        }
    }

    /* compiled from: DefaultImageHeaderParser */
    private interface c {
        int B() throws IOException;

        short L() throws IOException;

        int getByte() throws IOException;

        int read(byte[] bArr, int i) throws IOException;

        long skip(long j) throws IOException;
    }

    /* compiled from: DefaultImageHeaderParser */
    private static final class d implements c {
        private final InputStream xi;

        d(InputStream inputStream) {
            this.xi = inputStream;
        }

        public int B() throws IOException {
            return (this.xi.read() & 255) | ((this.xi.read() << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
        }

        public short L() throws IOException {
            return (short) (this.xi.read() & 255);
        }

        public int getByte() throws IOException {
            return this.xi.read();
        }

        public int read(byte[] bArr, int i) throws IOException {
            int i2 = i;
            while (i2 > 0) {
                int read = this.xi.read(bArr, i - i2, i2);
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
                long skip = this.xi.skip(j2);
                if (skip <= 0) {
                    if (this.xi.read() == -1) {
                        break;
                    }
                    skip = 1;
                }
                j2 -= skip;
            }
            return j - j2;
        }
    }

    private static boolean S(int i) {
        return (i & Ai) == Ai || i == Bi || i == Ci;
    }

    private static int a(b bVar) {
        ByteOrder byteOrder;
        short t = bVar.t(6);
        if (t == Ci) {
            byteOrder = ByteOrder.LITTLE_ENDIAN;
        } else if (t != Bi) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Unknown endianness = " + t);
            }
            byteOrder = ByteOrder.BIG_ENDIAN;
        } else {
            byteOrder = ByteOrder.BIG_ENDIAN;
        }
        bVar.order(byteOrder);
        int u = bVar.u(10) + 6;
        short t2 = bVar.t(u);
        for (int i = 0; i < t2; i++) {
            int p = p(u, i);
            short t3 = bVar.t(p);
            if (t3 == 274) {
                short t4 = bVar.t(p + 2);
                if (t4 >= 1 && t4 <= 12) {
                    int u2 = bVar.u(p + 4);
                    if (u2 >= 0) {
                        if (Log.isLoggable(TAG, 3)) {
                            Log.d(TAG, "Got tagIndex=" + i + " tagType=" + t3 + " formatCode=" + t4 + " componentCount=" + u2);
                        }
                        int i2 = u2 + Ki[t4];
                        if (i2 <= 4) {
                            int i3 = p + 8;
                            if (i3 < 0 || i3 > bVar.length()) {
                                if (Log.isLoggable(TAG, 3)) {
                                    Log.d(TAG, "Illegal tagValueOffset=" + i3 + " tagType=" + t3);
                                }
                            } else if (i2 >= 0 && i2 + i3 <= bVar.length()) {
                                return bVar.t(i3);
                            } else {
                                if (Log.isLoggable(TAG, 3)) {
                                    Log.d(TAG, "Illegal number of bytes for TI tag data tagType=" + t3);
                                }
                            }
                        } else if (Log.isLoggable(TAG, 3)) {
                            Log.d(TAG, "Got byte count > 4, not orientation, continuing, formatCode=" + t4);
                        }
                    } else if (Log.isLoggable(TAG, 3)) {
                        Log.d(TAG, "Negative tiff component count");
                    }
                } else if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Got invalid format code = " + t4);
                }
            }
        }
        return -1;
    }

    private int a(c cVar, com.bumptech.glide.load.engine.bitmap_recycle.b bVar) throws IOException {
        int B = cVar.B();
        if (!S(B)) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Parser doesn't handle magic number: " + B);
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
        int B = cVar.B();
        if (B == Ai) {
            return ImageHeaderParser.ImageType.JPEG;
        }
        int B2 = ((B << 16) & SupportMenu.CATEGORY_MASK) | (cVar.B() & SupportMenu.USER_MASK);
        if (B2 == zi) {
            cVar.skip(21);
            return cVar.getByte() >= 3 ? ImageHeaderParser.ImageType.PNG_A : ImageHeaderParser.ImageType.PNG;
        } else if ((B2 >> 8) == yi) {
            return ImageHeaderParser.ImageType.GIF;
        } else {
            if (B2 != Li) {
                return ImageHeaderParser.ImageType.UNKNOWN;
            }
            cVar.skip(4);
            if ((((cVar.B() << 16) & SupportMenu.CATEGORY_MASK) | (cVar.B() & SupportMenu.USER_MASK)) != Mi) {
                return ImageHeaderParser.ImageType.UNKNOWN;
            }
            int B3 = ((cVar.B() << 16) & SupportMenu.CATEGORY_MASK) | (cVar.B() & SupportMenu.USER_MASK);
            if ((B3 & -256) != Ni) {
                return ImageHeaderParser.ImageType.UNKNOWN;
            }
            int i = B3 & 255;
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
        short L;
        int B;
        long j;
        do {
            if (cVar.L() != 255) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Unknown segmentId=" + r8);
                }
                return -1;
            }
            L = cVar.L();
            if (L == 218) {
                return -1;
            }
            if (L == 217) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Found MARKER_EOI in exif segment");
                }
                return -1;
            }
            B = cVar.B() - 2;
            if (L == 225) {
                return B;
            }
            j = (long) B;
        } while (cVar.skip(j) == j);
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "Unable to skip enough data, type: " + L + ", wanted to skip: " + B + ", but actually skipped: " + r6);
        }
        return -1;
    }

    private boolean c(byte[] bArr, int i) {
        boolean z = bArr != null && i > Ei.length;
        if (z) {
            int i2 = 0;
            while (true) {
                byte[] bArr2 = Ei;
                if (i2 >= bArr2.length) {
                    break;
                } else if (bArr[i2] != bArr2[i2]) {
                    return false;
                } else {
                    i2++;
                }
            }
        }
        return z;
    }

    private static int p(int i, int i2) {
        return i + 2 + (i2 * 12);
    }

    public int a(@NonNull InputStream inputStream, @NonNull com.bumptech.glide.load.engine.bitmap_recycle.b bVar) throws IOException {
        i.checkNotNull(inputStream);
        d dVar = new d(inputStream);
        i.checkNotNull(bVar);
        return a((c) dVar, bVar);
    }

    public int a(@NonNull ByteBuffer byteBuffer, @NonNull com.bumptech.glide.load.engine.bitmap_recycle.b bVar) throws IOException {
        i.checkNotNull(byteBuffer);
        a aVar = new a(byteBuffer);
        i.checkNotNull(bVar);
        return a((c) aVar, bVar);
    }

    @NonNull
    public ImageHeaderParser.ImageType a(@NonNull InputStream inputStream) throws IOException {
        i.checkNotNull(inputStream);
        return a((c) new d(inputStream));
    }

    @NonNull
    public ImageHeaderParser.ImageType a(@NonNull ByteBuffer byteBuffer) throws IOException {
        i.checkNotNull(byteBuffer);
        return a((c) new a(byteBuffer));
    }
}
