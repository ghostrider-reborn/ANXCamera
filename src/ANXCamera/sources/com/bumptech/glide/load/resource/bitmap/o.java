package com.bumptech.glide.load.resource.bitmap;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ImageHeaderParser.ImageType;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy.SampleSizeRounding;
import com.bumptech.glide.util.e;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.l;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/* compiled from: Downsampler */
public final class o {
    private static final int Kd = 10485760;
    static final String TAG = "Downsampler";
    public static final f<DecodeFormat> Yi = f.a("com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeFormat", DecodeFormat.DEFAULT);
    @Deprecated
    public static final f<DownsampleStrategy> Zi = DownsampleStrategy.Wi;
    public static final f<Boolean> _i = f.a("com.bumptech.glide.load.resource.bitmap.Downsampler.FixBitmapSize", Boolean.valueOf(false));
    public static final f<Boolean> aj = f.q("com.bumtpech.glide.load.resource.bitmap.Downsampler.AllowHardwareDecode");
    private static final String bj = "image/vnd.wap.wbmp";
    private static final String cj = "image/x-ico";
    private static final Set<String> dj = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{bj, cj})));
    private static final a ej = new n();
    private static final Set<ImageType> fj = Collections.unmodifiableSet(EnumSet.of(ImageType.JPEG, ImageType.PNG_A, ImageType.PNG));
    private static final Queue<Options> gj = l.createQueue(0);
    private final d Bb;
    private final DisplayMetrics Wg;
    private final t Xi = t.getInstance();
    private final b Yd;
    private final List<ImageHeaderParser> ne;

    /* compiled from: Downsampler */
    public interface a {
        void a(d dVar, Bitmap bitmap) throws IOException;

        void t();
    }

    public o(List<ImageHeaderParser> list, DisplayMetrics displayMetrics, d dVar, b bVar) {
        this.ne = list;
        i.checkNotNull(displayMetrics);
        this.Wg = displayMetrics;
        i.checkNotNull(dVar);
        this.Bb = dVar;
        i.checkNotNull(bVar);
        this.Yd = bVar;
    }

    private static synchronized Options Gk() {
        Options options;
        synchronized (o.class) {
            synchronized (gj) {
                options = (Options) gj.poll();
            }
            if (options == null) {
                options = new Options();
                d(options);
            }
        }
        return options;
    }

    private Bitmap a(InputStream inputStream, Options options, DownsampleStrategy downsampleStrategy, DecodeFormat decodeFormat, boolean z, int i, int i2, boolean z2, a aVar) throws IOException {
        o oVar;
        int i3;
        int i4;
        int i5;
        InputStream inputStream2 = inputStream;
        Options options2 = options;
        a aVar2 = aVar;
        long Gh = e.Gh();
        int[] b2 = b(inputStream2, options2, aVar2, this.Bb);
        boolean z3 = false;
        int i6 = b2[0];
        int i7 = b2[1];
        String str = options2.outMimeType;
        boolean z4 = (i6 == -1 || i7 == -1) ? false : z;
        int a2 = com.bumptech.glide.load.b.a(this.ne, inputStream2, this.Yd);
        int v = y.v(a2);
        boolean w = y.w(a2);
        int i8 = i;
        int i9 = i2;
        int i10 = i8 == Integer.MIN_VALUE ? i6 : i8;
        int i11 = i9 == Integer.MIN_VALUE ? i7 : i9;
        ImageType b3 = com.bumptech.glide.load.b.b(this.ne, inputStream2, this.Yd);
        d dVar = this.Bb;
        ImageType imageType = b3;
        a(b3, inputStream, aVar, dVar, downsampleStrategy, v, i6, i7, i10, i11, options);
        int i12 = a2;
        String str2 = str;
        int i13 = i7;
        int i14 = i6;
        a aVar3 = aVar2;
        Options options3 = options2;
        a(inputStream, decodeFormat, z4, w, options, i10, i11);
        if (VERSION.SDK_INT >= 19) {
            z3 = true;
        }
        int i15 = options3.inSampleSize;
        String str3 = TAG;
        if (i15 == 1 || z3) {
            oVar = this;
            if (oVar.a(imageType)) {
                if (i14 < 0 || i13 < 0 || !z2 || !z3) {
                    float f2 = b(options) ? ((float) options3.inTargetDensity) / ((float) options3.inDensity) : 1.0f;
                    int i16 = options3.inSampleSize;
                    float f3 = (float) i16;
                    int ceil = (int) Math.ceil((double) (((float) i14) / f3));
                    int ceil2 = (int) Math.ceil((double) (((float) i13) / f3));
                    i5 = Math.round(((float) ceil) * f2);
                    i4 = Math.round(((float) ceil2) * f2);
                    if (Log.isLoggable(str3, 2)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Calculated target [");
                        sb.append(i5);
                        String str4 = "x";
                        sb.append(str4);
                        sb.append(i4);
                        sb.append("] for source [");
                        sb.append(i14);
                        sb.append(str4);
                        sb.append(i13);
                        sb.append("], sampleSize: ");
                        sb.append(i16);
                        sb.append(", targetDensity: ");
                        sb.append(options3.inTargetDensity);
                        sb.append(", density: ");
                        sb.append(options3.inDensity);
                        sb.append(", density multiplier: ");
                        sb.append(f2);
                        Log.v(str3, sb.toString());
                    }
                } else {
                    i5 = i10;
                    i4 = i11;
                }
                if (i5 > 0 && i4 > 0) {
                    a(options3, oVar.Bb, i5, i4);
                }
            }
        } else {
            oVar = this;
        }
        Bitmap a3 = a(inputStream, options3, aVar3, oVar.Bb);
        aVar3.a(oVar.Bb, a3);
        if (Log.isLoggable(str3, 2)) {
            i3 = i12;
            a(i14, i13, str2, options, a3, i, i2, Gh);
        } else {
            i3 = i12;
        }
        Bitmap bitmap = null;
        if (a3 != null) {
            a3.setDensity(oVar.Wg.densityDpi);
            bitmap = y.a(oVar.Bb, a3, i3);
            if (!a3.equals(bitmap)) {
                oVar.Bb.a(a3);
            }
        }
        return bitmap;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:23|24) */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        throw r1;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x005d */
    private static Bitmap a(InputStream inputStream, Options options, a aVar, d dVar) throws IOException {
        String str = TAG;
        if (options.inJustDecodeBounds) {
            inputStream.mark(Kd);
        } else {
            aVar.t();
        }
        int i = options.outWidth;
        int i2 = options.outHeight;
        String str2 = options.outMimeType;
        y.Fg().lock();
        try {
            Bitmap decodeStream = BitmapFactory.decodeStream(inputStream, null, options);
            y.Fg().unlock();
            if (options.inJustDecodeBounds) {
                inputStream.reset();
            }
            return decodeStream;
        } catch (IllegalArgumentException e2) {
            IOException a2 = a(e2, i, i2, str2, options);
            if (Log.isLoggable(str, 3)) {
                Log.d(str, "Failed to decode with inBitmap, trying again without Bitmap re-use", a2);
            }
            if (options.inBitmap != null) {
                inputStream.reset();
                dVar.a(options.inBitmap);
                options.inBitmap = null;
                Bitmap a3 = a(inputStream, options, aVar, dVar);
                y.Fg().unlock();
                return a3;
            }
            throw a2;
        } catch (Throwable th) {
            y.Fg().unlock();
            throw th;
        }
    }

    private static IOException a(IllegalArgumentException illegalArgumentException, int i, int i2, String str, Options options) {
        StringBuilder sb = new StringBuilder();
        sb.append("Exception decoding bitmap, outWidth: ");
        sb.append(i);
        sb.append(", outHeight: ");
        sb.append(i2);
        sb.append(", outMimeType: ");
        sb.append(str);
        sb.append(", inBitmap: ");
        sb.append(a(options));
        return new IOException(sb.toString(), illegalArgumentException);
    }

    private static String a(Options options) {
        return k(options.inBitmap);
    }

    private static void a(int i, int i2, String str, Options options, Bitmap bitmap, int i3, int i4, long j) {
        StringBuilder sb = new StringBuilder();
        sb.append("Decoded ");
        sb.append(k(bitmap));
        sb.append(" from [");
        sb.append(i);
        String str2 = "x";
        sb.append(str2);
        sb.append(i2);
        sb.append("] ");
        sb.append(str);
        sb.append(" with inBitmap ");
        sb.append(a(options));
        sb.append(" for [");
        sb.append(i3);
        sb.append(str2);
        sb.append(i4);
        sb.append("], sample size: ");
        sb.append(options.inSampleSize);
        sb.append(", density: ");
        sb.append(options.inDensity);
        sb.append(", target density: ");
        sb.append(options.inTargetDensity);
        sb.append(", thread: ");
        sb.append(Thread.currentThread().getName());
        sb.append(", duration: ");
        sb.append(e.g(j));
        Log.v(TAG, sb.toString());
    }

    @TargetApi(26)
    private static void a(Options options, d dVar, int i, int i2) {
        Config config;
        if (VERSION.SDK_INT < 26) {
            config = null;
        } else if (options.inPreferredConfig != Config.HARDWARE) {
            config = options.outConfig;
        } else {
            return;
        }
        if (config == null) {
            config = options.inPreferredConfig;
        }
        options.inBitmap = dVar.c(i, i2, config);
    }

    private static void a(ImageType imageType, InputStream inputStream, a aVar, d dVar, DownsampleStrategy downsampleStrategy, int i, int i2, int i3, int i4, int i5, Options options) throws IOException {
        int i6;
        int i7;
        int i8;
        double d2;
        ImageType imageType2 = imageType;
        DownsampleStrategy downsampleStrategy2 = downsampleStrategy;
        int i9 = i;
        int i10 = i2;
        int i11 = i3;
        int i12 = i4;
        int i13 = i5;
        Options options2 = options;
        String str = "]";
        String str2 = TAG;
        String str3 = "x";
        if (i10 <= 0 || i11 <= 0) {
            String str4 = str2;
            String str5 = str3;
            if (Log.isLoggable(str4, 3)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to determine dimensions for: ");
                sb.append(imageType2);
                sb.append(" with target [");
                sb.append(i12);
                sb.append(str5);
                sb.append(i13);
                sb.append(str);
                Log.d(str4, sb.toString());
            }
            return;
        }
        float b2 = (i9 == 90 || i9 == 270) ? downsampleStrategy2.b(i11, i10, i12, i13) : downsampleStrategy2.b(i10, i11, i12, i13);
        String str6 = "], target: [";
        if (b2 > 0.0f) {
            SampleSizeRounding a2 = downsampleStrategy2.a(i10, i11, i12, i13);
            if (a2 != null) {
                float f2 = (float) i10;
                float f3 = (float) i11;
                String str7 = str2;
                String str8 = str3;
                int round = i10 / round((double) (b2 * f2));
                int round2 = i11 / round((double) (b2 * f3));
                int max = a2 == SampleSizeRounding.MEMORY ? Math.max(round, round2) : Math.min(round, round2);
                if (VERSION.SDK_INT > 23 || !dj.contains(options2.outMimeType)) {
                    int max2 = Math.max(1, Integer.highestOneBit(max));
                    i6 = (a2 != SampleSizeRounding.MEMORY || ((float) max2) >= 1.0f / b2) ? max2 : max2 << 1;
                } else {
                    i6 = 1;
                }
                options2.inSampleSize = i6;
                if (imageType2 == ImageType.JPEG) {
                    float min = (float) Math.min(i6, 8);
                    i7 = (int) Math.ceil((double) (f2 / min));
                    i8 = (int) Math.ceil((double) (f3 / min));
                    int i14 = i6 / 8;
                    if (i14 > 0) {
                        i7 /= i14;
                        i8 /= i14;
                    }
                } else {
                    if (imageType2 == ImageType.PNG || imageType2 == ImageType.PNG_A) {
                        float f4 = (float) i6;
                        i7 = (int) Math.floor((double) (f2 / f4));
                        d2 = Math.floor((double) (f3 / f4));
                    } else if (imageType2 == ImageType.WEBP || imageType2 == ImageType.WEBP_A) {
                        if (VERSION.SDK_INT >= 24) {
                            float f5 = (float) i6;
                            i7 = Math.round(f2 / f5);
                            i8 = Math.round(f3 / f5);
                        } else {
                            float f6 = (float) i6;
                            i7 = (int) Math.floor((double) (f2 / f6));
                            d2 = Math.floor((double) (f3 / f6));
                        }
                    } else if (i10 % i6 == 0 && i11 % i6 == 0) {
                        i7 = i10 / i6;
                        i8 = i11 / i6;
                    } else {
                        int[] b3 = b(inputStream, options2, aVar, dVar);
                        int i15 = b3[0];
                        i8 = b3[1];
                        i7 = i15;
                    }
                    i8 = (int) d2;
                }
                double b4 = (double) downsampleStrategy2.b(i7, i8, i12, i13);
                if (VERSION.SDK_INT >= 19) {
                    options2.inTargetDensity = b(b4);
                    options2.inDensity = c(b4);
                }
                if (b(options)) {
                    options2.inScaled = true;
                } else {
                    options2.inTargetDensity = 0;
                    options2.inDensity = 0;
                }
                String str9 = str7;
                if (Log.isLoggable(str9, 2)) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Calculate scaling, source: [");
                    sb2.append(i10);
                    String str10 = str8;
                    sb2.append(str10);
                    sb2.append(i11);
                    sb2.append(str6);
                    sb2.append(i12);
                    sb2.append(str10);
                    sb2.append(i13);
                    sb2.append("], power of two scaled: [");
                    sb2.append(i7);
                    sb2.append(str10);
                    sb2.append(i8);
                    sb2.append("], exact scale factor: ");
                    sb2.append(b2);
                    sb2.append(", power of 2 sample size: ");
                    sb2.append(i6);
                    sb2.append(", adjusted scale factor: ");
                    sb2.append(b4);
                    sb2.append(", target density: ");
                    sb2.append(options2.inTargetDensity);
                    sb2.append(", density: ");
                    sb2.append(options2.inDensity);
                    Log.v(str9, sb2.toString());
                }
                return;
            }
            throw new IllegalArgumentException("Cannot round with null rounding");
        }
        String str11 = str3;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Cannot scale with factor: ");
        sb3.append(b2);
        sb3.append(" from: ");
        sb3.append(downsampleStrategy2);
        sb3.append(", source: [");
        sb3.append(i10);
        sb3.append(str11);
        sb3.append(i11);
        sb3.append(str6);
        sb3.append(i12);
        sb3.append(str11);
        sb3.append(i13);
        sb3.append(str);
        throw new IllegalArgumentException(sb3.toString());
    }

    private void a(InputStream inputStream, DecodeFormat decodeFormat, boolean z, boolean z2, Options options, int i, int i2) {
        if (!this.Xi.a(i, i2, options, decodeFormat, z, z2)) {
            if (decodeFormat == DecodeFormat.PREFER_ARGB_8888 || decodeFormat == DecodeFormat.PREFER_ARGB_8888_DISALLOW_HARDWARE || VERSION.SDK_INT == 16) {
                options.inPreferredConfig = Config.ARGB_8888;
                return;
            }
            boolean z3 = false;
            try {
                z3 = com.bumptech.glide.load.b.b(this.ne, inputStream, this.Yd).hasAlpha();
            } catch (IOException e2) {
                String str = TAG;
                if (Log.isLoggable(str, 3)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Cannot determine whether the image has alpha or not from header, format ");
                    sb.append(decodeFormat);
                    Log.d(str, sb.toString(), e2);
                }
            }
            options.inPreferredConfig = z3 ? Config.ARGB_8888 : Config.RGB_565;
            if (options.inPreferredConfig == Config.RGB_565) {
                options.inDither = true;
            }
        }
    }

    private boolean a(ImageType imageType) {
        if (VERSION.SDK_INT >= 19) {
            return true;
        }
        return fj.contains(imageType);
    }

    private static int b(double d2) {
        int c2 = c(d2);
        int round = round(((double) c2) * d2);
        return round((d2 / ((double) (((float) round) / ((float) c2)))) * ((double) round));
    }

    private static boolean b(Options options) {
        int i = options.inTargetDensity;
        if (i > 0) {
            int i2 = options.inDensity;
            if (i2 > 0 && i != i2) {
                return true;
            }
        }
        return false;
    }

    private static int[] b(InputStream inputStream, Options options, a aVar, d dVar) throws IOException {
        options.inJustDecodeBounds = true;
        a(inputStream, options, aVar, dVar);
        options.inJustDecodeBounds = false;
        return new int[]{options.outWidth, options.outHeight};
    }

    private static int c(double d2) {
        if (d2 > 1.0d) {
            d2 = 1.0d / d2;
        }
        return (int) Math.round(d2 * 2.147483647E9d);
    }

    private static void c(Options options) {
        d(options);
        synchronized (gj) {
            gj.offer(options);
        }
    }

    private static void d(Options options) {
        options.inTempStorage = null;
        options.inDither = false;
        options.inScaled = false;
        options.inSampleSize = 1;
        options.inPreferredConfig = null;
        options.inJustDecodeBounds = false;
        options.inDensity = 0;
        options.inTargetDensity = 0;
        options.outWidth = 0;
        options.outHeight = 0;
        options.outMimeType = null;
        options.inBitmap = null;
        options.inMutable = true;
    }

    @Nullable
    @TargetApi(19)
    private static String k(Bitmap bitmap) {
        String str;
        if (bitmap == null) {
            return null;
        }
        if (VERSION.SDK_INT >= 19) {
            StringBuilder sb = new StringBuilder();
            sb.append(" (");
            sb.append(bitmap.getAllocationByteCount());
            sb.append(")");
            str = sb.toString();
        } else {
            str = "";
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("[");
        sb2.append(bitmap.getWidth());
        sb2.append("x");
        sb2.append(bitmap.getHeight());
        sb2.append("] ");
        sb2.append(bitmap.getConfig());
        sb2.append(str);
        return sb2.toString();
    }

    private static int round(double d2) {
        return (int) (d2 + 0.5d);
    }

    public A<Bitmap> a(InputStream inputStream, int i, int i2, g gVar) throws IOException {
        return a(inputStream, i, i2, gVar, ej);
    }

    public A<Bitmap> a(InputStream inputStream, int i, int i2, g gVar, a aVar) throws IOException {
        g gVar2 = gVar;
        i.a(inputStream.markSupported(), "You must provide an InputStream that supports mark()");
        byte[] bArr = (byte[]) this.Yd.a(65536, byte[].class);
        Options Gk = Gk();
        Gk.inTempStorage = bArr;
        DecodeFormat decodeFormat = (DecodeFormat) gVar2.a(Yi);
        try {
            return f.a(a(inputStream, Gk, (DownsampleStrategy) gVar2.a(DownsampleStrategy.Wi), decodeFormat, decodeFormat == DecodeFormat.PREFER_ARGB_8888_DISALLOW_HARDWARE ? false : gVar2.a(aj) != null && ((Boolean) gVar2.a(aj)).booleanValue(), i, i2, ((Boolean) gVar2.a(_i)).booleanValue(), aVar), this.Bb);
        } finally {
            c(Gk);
            this.Yd.put(bArr);
        }
    }

    public boolean b(ByteBuffer byteBuffer) {
        return true;
    }

    public boolean f(InputStream inputStream) {
        return true;
    }
}
