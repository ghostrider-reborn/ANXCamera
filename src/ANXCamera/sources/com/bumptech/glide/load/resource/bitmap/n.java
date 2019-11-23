package com.bumptech.glide.load.resource.bitmap;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.e;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.engine.p;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.k;
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
public final class n {
    static final String TAG = "Downsampler";
    private static final int eG = 10485760;
    public static final e<DecodeFormat> mm = e.a("com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeFormat", DecodeFormat.eq);
    @Deprecated
    public static final e<DownsampleStrategy> mn = DownsampleStrategy.mi;
    public static final e<Boolean> mo = e.a("com.bumptech.glide.load.resource.bitmap.Downsampler.FixBitmapSize", false);
    public static final e<Boolean> mp = e.r("com.bumtpech.glide.load.resource.bitmap.Downsampler.AllowHardwareDecode");
    private static final String mq = "image/vnd.wap.wbmp";
    private static final String mr = "image/x-ico";
    private static final Set<String> ms = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{mq, mr})));
    private static final a mt = new a() {
        public void a(d dVar, Bitmap bitmap) {
        }

        public void cW() {
        }
    };
    private static final Set<ImageHeaderParser.ImageType> mu = Collections.unmodifiableSet(EnumSet.of(ImageHeaderParser.ImageType.JPEG, ImageHeaderParser.ImageType.PNG_A, ImageHeaderParser.ImageType.PNG));
    private static final Queue<BitmapFactory.Options> mv = k.ab(0);
    private final d bm;
    private final b ff;
    private final List<ImageHeaderParser> fw;
    private final DisplayMetrics jE;
    private final r mw = r.cY();

    /* compiled from: Downsampler */
    public interface a {
        void a(d dVar, Bitmap bitmap) throws IOException;

        void cW();
    }

    public n(List<ImageHeaderParser> list, DisplayMetrics displayMetrics, d dVar, b bVar) {
        this.fw = list;
        this.jE = (DisplayMetrics) i.checkNotNull(displayMetrics);
        this.bm = (d) i.checkNotNull(dVar);
        this.ff = (b) i.checkNotNull(bVar);
    }

    private static int a(double d) {
        int b2 = b(d);
        int c = c(((double) b2) * d);
        return c((d / ((double) (((float) c) / ((float) b2)))) * ((double) c));
    }

    private Bitmap a(InputStream inputStream, BitmapFactory.Options options, DownsampleStrategy downsampleStrategy, DecodeFormat decodeFormat, boolean z, int i, int i2, boolean z2, a aVar) throws IOException {
        int i3;
        int i4;
        int i5;
        InputStream inputStream2 = inputStream;
        BitmapFactory.Options options2 = options;
        a aVar2 = aVar;
        long fn = com.bumptech.glide.util.e.fn();
        int[] a2 = a(inputStream2, options2, aVar2, this.bm);
        boolean z3 = false;
        int i6 = a2[0];
        int i7 = a2[1];
        String str = options2.outMimeType;
        boolean z4 = (i6 == -1 || i7 == -1) ? false : z;
        int b2 = com.bumptech.glide.load.b.b(this.fw, inputStream2, this.ff);
        int H = w.H(b2);
        boolean I = w.I(b2);
        int i8 = i;
        int i9 = i8 == Integer.MIN_VALUE ? i6 : i8;
        int i10 = i2;
        int i11 = i10 == Integer.MIN_VALUE ? i7 : i10;
        ImageHeaderParser.ImageType a3 = com.bumptech.glide.load.b.a(this.fw, inputStream2, this.ff);
        ImageHeaderParser.ImageType imageType = a3;
        a(a3, inputStream2, aVar2, this.bm, downsampleStrategy, H, i6, i7, i9, i11, options2);
        int i12 = b2;
        String str2 = str;
        int i13 = i7;
        int i14 = i6;
        a aVar3 = aVar2;
        BitmapFactory.Options options3 = options2;
        a(inputStream2, decodeFormat, z4, I, options2, i9, i11);
        if (Build.VERSION.SDK_INT >= 19) {
            z3 = true;
        }
        if ((options3.inSampleSize == 1 || z3) && a(imageType)) {
            if (i14 < 0 || i13 < 0 || !z2 || !z3) {
                float f = a(options) ? ((float) options3.inTargetDensity) / ((float) options3.inDensity) : 1.0f;
                float f2 = (float) options3.inSampleSize;
                i5 = Math.round(((float) ((int) Math.ceil((double) (((float) i14) / f2)))) * f);
                i4 = Math.round(((float) ((int) Math.ceil((double) (((float) i13) / f2)))) * f);
                if (Log.isLoggable(TAG, 2)) {
                    Log.v(TAG, "Calculated target [" + i5 + "x" + i4 + "] for source [" + i14 + "x" + i13 + "], sampleSize: " + r2 + ", targetDensity: " + options3.inTargetDensity + ", density: " + options3.inDensity + ", density multiplier: " + f);
                }
            } else {
                i5 = i9;
                i4 = i11;
            }
            if (i5 > 0 && i4 > 0) {
                a(options3, this.bm, i5, i4);
            }
        }
        Bitmap b3 = b(inputStream, options3, aVar3, this.bm);
        aVar3.a(this.bm, b3);
        if (Log.isLoggable(TAG, 2)) {
            i3 = i12;
            a(i14, i13, str2, options3, b3, i, i2, fn);
        } else {
            i3 = i12;
        }
        Bitmap bitmap = null;
        if (b3 != null) {
            b3.setDensity(this.jE.densityDpi);
            bitmap = w.a(this.bm, b3, i3);
            if (!b3.equals(bitmap)) {
                this.bm.d(b3);
            }
        }
        return bitmap;
    }

    private static IOException a(IllegalArgumentException illegalArgumentException, int i, int i2, String str, BitmapFactory.Options options) {
        return new IOException("Exception decoding bitmap, outWidth: " + i + ", outHeight: " + i2 + ", outMimeType: " + str + ", inBitmap: " + b(options), illegalArgumentException);
    }

    private static void a(int i, int i2, String str, BitmapFactory.Options options, Bitmap bitmap, int i3, int i4, long j) {
        Log.v(TAG, "Decoded " + g(bitmap) + " from [" + i + "x" + i2 + "] " + str + " with inBitmap " + b(options) + " for [" + i3 + "x" + i4 + "], sample size: " + options.inSampleSize + ", density: " + options.inDensity + ", target density: " + options.inTargetDensity + ", thread: " + Thread.currentThread().getName() + ", duration: " + com.bumptech.glide.util.e.f(j));
    }

    @TargetApi(26)
    private static void a(BitmapFactory.Options options, d dVar, int i, int i2) {
        Bitmap.Config config;
        if (Build.VERSION.SDK_INT < 26) {
            config = null;
        } else if (options.inPreferredConfig != Bitmap.Config.HARDWARE) {
            config = options.outConfig;
        } else {
            return;
        }
        if (config == null) {
            config = options.inPreferredConfig;
        }
        options.inBitmap = dVar.g(i, i2, config);
    }

    private static void a(ImageHeaderParser.ImageType imageType, InputStream inputStream, a aVar, d dVar, DownsampleStrategy downsampleStrategy, int i, int i2, int i3, int i4, int i5, BitmapFactory.Options options) throws IOException {
        int i6;
        int i7;
        int i8;
        ImageHeaderParser.ImageType imageType2 = imageType;
        DownsampleStrategy downsampleStrategy2 = downsampleStrategy;
        int i9 = i;
        int i10 = i2;
        int i11 = i3;
        int i12 = i4;
        int i13 = i5;
        BitmapFactory.Options options2 = options;
        if (i10 > 0 && i11 > 0) {
            float a2 = (i9 == 90 || i9 == 270) ? downsampleStrategy2.a(i11, i10, i12, i13) : downsampleStrategy2.a(i10, i11, i12, i13);
            if (a2 > 0.0f) {
                DownsampleStrategy.SampleSizeRounding b2 = downsampleStrategy2.b(i10, i11, i12, i13);
                if (b2 != null) {
                    float f = (float) i10;
                    float f2 = (float) i11;
                    int c = i10 / c((double) (a2 * f));
                    int c2 = i11 / c((double) (a2 * f2));
                    int max = b2 == DownsampleStrategy.SampleSizeRounding.MEMORY ? Math.max(c, c2) : Math.min(c, c2);
                    if (Build.VERSION.SDK_INT > 23 || !ms.contains(options2.outMimeType)) {
                        int max2 = Math.max(1, Integer.highestOneBit(max));
                        i6 = (b2 != DownsampleStrategy.SampleSizeRounding.MEMORY || ((float) max2) >= 1.0f / a2) ? max2 : max2 << 1;
                    } else {
                        i6 = 1;
                    }
                    options2.inSampleSize = i6;
                    if (imageType2 == ImageHeaderParser.ImageType.JPEG) {
                        float min = (float) Math.min(i6, 8);
                        i7 = (int) Math.ceil((double) (f / min));
                        i8 = (int) Math.ceil((double) (f2 / min));
                        int i14 = i6 / 8;
                        if (i14 > 0) {
                            i7 /= i14;
                            i8 /= i14;
                        }
                    } else if (imageType2 == ImageHeaderParser.ImageType.PNG || imageType2 == ImageHeaderParser.ImageType.PNG_A) {
                        float f3 = (float) i6;
                        i7 = (int) Math.floor((double) (f / f3));
                        i8 = (int) Math.floor((double) (f2 / f3));
                    } else if (imageType2 == ImageHeaderParser.ImageType.WEBP || imageType2 == ImageHeaderParser.ImageType.WEBP_A) {
                        if (Build.VERSION.SDK_INT >= 24) {
                            float f4 = (float) i6;
                            i7 = Math.round(f / f4);
                            i8 = Math.round(f2 / f4);
                        } else {
                            float f5 = (float) i6;
                            i7 = (int) Math.floor((double) (f / f5));
                            i8 = (int) Math.floor((double) (f2 / f5));
                        }
                    } else if (i10 % i6 == 0 && i11 % i6 == 0) {
                        i7 = i10 / i6;
                        i8 = i11 / i6;
                    } else {
                        int[] a3 = a(inputStream, options2, aVar, dVar);
                        i7 = a3[0];
                        i8 = a3[1];
                    }
                    double a4 = (double) downsampleStrategy2.a(i7, i8, i12, i13);
                    if (Build.VERSION.SDK_INT >= 19) {
                        options2.inTargetDensity = a(a4);
                        options2.inDensity = b(a4);
                    }
                    if (a(options)) {
                        options2.inScaled = true;
                    } else {
                        options2.inTargetDensity = 0;
                        options2.inDensity = 0;
                    }
                    if (Log.isLoggable(TAG, 2)) {
                        Log.v(TAG, "Calculate scaling, source: [" + i10 + "x" + i11 + "], target: [" + i12 + "x" + i13 + "], power of two scaled: [" + i7 + "x" + i8 + "], exact scale factor: " + a2 + ", power of 2 sample size: " + i6 + ", adjusted scale factor: " + a4 + ", target density: " + options2.inTargetDensity + ", density: " + options2.inDensity);
                        return;
                    }
                    return;
                }
                throw new IllegalArgumentException("Cannot round with null rounding");
            }
            throw new IllegalArgumentException("Cannot scale with factor: " + a2 + " from: " + downsampleStrategy2 + ", source: [" + i10 + "x" + i11 + "], target: [" + i12 + "x" + i13 + "]");
        } else if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "Unable to determine dimensions for: " + imageType2 + " with target [" + i12 + "x" + i13 + "]");
        }
    }

    private void a(InputStream inputStream, DecodeFormat decodeFormat, boolean z, boolean z2, BitmapFactory.Options options, int i, int i2) {
        boolean z3;
        if (!this.mw.a(i, i2, options, decodeFormat, z, z2)) {
            if (decodeFormat == DecodeFormat.PREFER_ARGB_8888 || decodeFormat == DecodeFormat.PREFER_ARGB_8888_DISALLOW_HARDWARE || Build.VERSION.SDK_INT == 16) {
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                return;
            }
            try {
                z3 = com.bumptech.glide.load.b.a(this.fw, inputStream, this.ff).hasAlpha();
            } catch (IOException e) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Cannot determine whether the image has alpha or not from header, format " + decodeFormat, e);
                }
                z3 = false;
            }
            options.inPreferredConfig = z3 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
            if (options.inPreferredConfig == Bitmap.Config.RGB_565) {
                options.inDither = true;
            }
        }
    }

    private static boolean a(BitmapFactory.Options options) {
        return options.inTargetDensity > 0 && options.inDensity > 0 && options.inTargetDensity != options.inDensity;
    }

    private boolean a(ImageHeaderParser.ImageType imageType) {
        if (Build.VERSION.SDK_INT >= 19) {
            return true;
        }
        return mu.contains(imageType);
    }

    private static int[] a(InputStream inputStream, BitmapFactory.Options options, a aVar, d dVar) throws IOException {
        options.inJustDecodeBounds = true;
        b(inputStream, options, aVar, dVar);
        options.inJustDecodeBounds = false;
        return new int[]{options.outWidth, options.outHeight};
    }

    private static int b(double d) {
        if (d > 1.0d) {
            d = 1.0d / d;
        }
        return (int) Math.round(2.147483647E9d * d);
    }

    private static Bitmap b(InputStream inputStream, BitmapFactory.Options options, a aVar, d dVar) throws IOException {
        IOException a2;
        if (options.inJustDecodeBounds) {
            inputStream.mark(eG);
        } else {
            aVar.cW();
        }
        int i = options.outWidth;
        int i2 = options.outHeight;
        String str = options.outMimeType;
        w.dd().lock();
        try {
            Bitmap decodeStream = BitmapFactory.decodeStream(inputStream, (Rect) null, options);
            w.dd().unlock();
            if (options.inJustDecodeBounds) {
                inputStream.reset();
            }
            return decodeStream;
        } catch (IOException e) {
            throw a2;
        } catch (IllegalArgumentException e2) {
            a2 = a(e2, i, i2, str, options);
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Failed to decode with inBitmap, trying again without Bitmap re-use", a2);
            }
            if (options.inBitmap != null) {
                inputStream.reset();
                dVar.d(options.inBitmap);
                options.inBitmap = null;
                Bitmap b2 = b(inputStream, options, aVar, dVar);
                w.dd().unlock();
                return b2;
            }
            throw a2;
        } catch (Throwable th) {
            w.dd().unlock();
            throw th;
        }
    }

    private static String b(BitmapFactory.Options options) {
        return g(options.inBitmap);
    }

    private static int c(double d) {
        return (int) (d + 0.5d);
    }

    private static void c(BitmapFactory.Options options) {
        d(options);
        synchronized (mv) {
            mv.offer(options);
        }
    }

    private static synchronized BitmapFactory.Options cV() {
        BitmapFactory.Options poll;
        synchronized (n.class) {
            synchronized (mv) {
                poll = mv.poll();
            }
            if (poll == null) {
                poll = new BitmapFactory.Options();
                d(poll);
            }
        }
        return poll;
    }

    private static void d(BitmapFactory.Options options) {
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
    private static String g(Bitmap bitmap) {
        String str;
        if (bitmap == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            str = " (" + bitmap.getAllocationByteCount() + ")";
        } else {
            str = "";
        }
        return "[" + bitmap.getWidth() + "x" + bitmap.getHeight() + "] " + bitmap.getConfig() + str;
    }

    public p<Bitmap> a(InputStream inputStream, int i, int i2, f fVar) throws IOException {
        return a(inputStream, i, i2, fVar, mt);
    }

    public p<Bitmap> a(InputStream inputStream, int i, int i2, f fVar, a aVar) throws IOException {
        f fVar2 = fVar;
        i.a(inputStream.markSupported(), "You must provide an InputStream that supports mark()");
        byte[] bArr = (byte[]) this.ff.a(65536, byte[].class);
        BitmapFactory.Options cV = cV();
        cV.inTempStorage = bArr;
        DecodeFormat decodeFormat = (DecodeFormat) fVar2.a(mm);
        try {
            return f.a(a(inputStream, cV, (DownsampleStrategy) fVar2.a(DownsampleStrategy.mi), decodeFormat, decodeFormat == DecodeFormat.PREFER_ARGB_8888_DISALLOW_HARDWARE ? false : fVar2.a(mp) != null && ((Boolean) fVar2.a(mp)).booleanValue(), i, i2, ((Boolean) fVar2.a(mo)).booleanValue(), aVar), this.bm);
        } finally {
            c(cV);
            this.ff.put(bArr);
        }
    }

    public boolean c(ByteBuffer byteBuffer) {
        return true;
    }

    public boolean f(InputStream inputStream) {
        return true;
    }
}
