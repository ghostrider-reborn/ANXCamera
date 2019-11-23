package com.bumptech.glide.load.engine.bitmap_recycle;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* compiled from: LruBitmapPool */
public class j implements d {
    private static final Bitmap.Config DEFAULT_CONFIG = Bitmap.Config.ARGB_8888;
    private static final String TAG = "LruBitmapPool";
    private final k iC;
    private final Set<Bitmap.Config> iD;
    private final long iE;
    private final a iF;
    private long iG;
    private int iH;
    private int iI;
    private int iJ;
    private int iK;
    private long maxSize;

    /* compiled from: LruBitmapPool */
    private interface a {
        void j(Bitmap bitmap);

        void k(Bitmap bitmap);
    }

    /* compiled from: LruBitmapPool */
    private static final class b implements a {
        b() {
        }

        public void j(Bitmap bitmap) {
        }

        public void k(Bitmap bitmap) {
        }
    }

    /* compiled from: LruBitmapPool */
    private static class c implements a {
        private final Set<Bitmap> iL = Collections.synchronizedSet(new HashSet());

        private c() {
        }

        public void j(Bitmap bitmap) {
            if (!this.iL.contains(bitmap)) {
                this.iL.add(bitmap);
                return;
            }
            throw new IllegalStateException("Can't add already added bitmap: " + bitmap + " [" + bitmap.getWidth() + "x" + bitmap.getHeight() + "]");
        }

        public void k(Bitmap bitmap) {
            if (this.iL.contains(bitmap)) {
                this.iL.remove(bitmap);
                return;
            }
            throw new IllegalStateException("Cannot remove bitmap not in tracker");
        }
    }

    public j(long j) {
        this(j, bX(), bY());
    }

    j(long j, k kVar, Set<Bitmap.Config> set) {
        this.iE = j;
        this.maxSize = j;
        this.iC = kVar;
        this.iD = set;
        this.iF = new b();
    }

    public j(long j, Set<Bitmap.Config> set) {
        this(j, bX(), set);
    }

    private synchronized void b(long j) {
        while (this.iG > j) {
            Bitmap bN = this.iC.bN();
            if (bN == null) {
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "Size mismatch, resetting");
                    bW();
                }
                this.iG = 0;
                return;
            }
            this.iF.k(bN);
            this.iG -= (long) this.iC.f(bN);
            this.iK++;
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Evicting bitmap=" + this.iC.e(bN));
            }
            dump();
            bN.recycle();
        }
    }

    @TargetApi(26)
    private static void b(Bitmap.Config config) {
        if (Build.VERSION.SDK_INT >= 26 && config == Bitmap.Config.HARDWARE) {
            throw new IllegalArgumentException("Cannot create a mutable Bitmap with config: " + config + ". Consider setting Downsampler#ALLOW_HARDWARE_CONFIG to false in your RequestOptions and/or in GlideBuilder.setDefaultRequestOptions");
        }
    }

    private void bT() {
        b(this.maxSize);
    }

    private void bW() {
        Log.v(TAG, "Hits=" + this.iH + ", misses=" + this.iI + ", puts=" + this.iJ + ", evictions=" + this.iK + ", currentSize=" + this.iG + ", maxSize=" + this.maxSize + "\nStrategy=" + this.iC);
    }

    private static k bX() {
        return Build.VERSION.SDK_INT >= 19 ? new SizeConfigStrategy() : new AttributeStrategy();
    }

    @TargetApi(26)
    private static Set<Bitmap.Config> bY() {
        HashSet hashSet = new HashSet(Arrays.asList(Bitmap.Config.values()));
        if (Build.VERSION.SDK_INT >= 19) {
            hashSet.add((Object) null);
        }
        if (Build.VERSION.SDK_INT >= 26) {
            hashSet.remove(Bitmap.Config.HARDWARE);
        }
        return Collections.unmodifiableSet(hashSet);
    }

    @NonNull
    private static Bitmap createBitmap(int i, int i2, @Nullable Bitmap.Config config) {
        if (config == null) {
            config = DEFAULT_CONFIG;
        }
        return Bitmap.createBitmap(i, i2, config);
    }

    private void dump() {
        if (Log.isLoggable(TAG, 2)) {
            bW();
        }
    }

    @Nullable
    private synchronized Bitmap h(int i, int i2, @Nullable Bitmap.Config config) {
        Bitmap b2;
        b(config);
        b2 = this.iC.b(i, i2, config != null ? config : DEFAULT_CONFIG);
        if (b2 == null) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Missing bitmap=" + this.iC.c(i, i2, config));
            }
            this.iI++;
        } else {
            this.iH++;
            this.iG -= (long) this.iC.f(b2);
            this.iF.k(b2);
            h(b2);
        }
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "Get bitmap=" + this.iC.c(i, i2, config));
        }
        dump();
        return b2;
    }

    private static void h(Bitmap bitmap) {
        bitmap.setHasAlpha(true);
        i(bitmap);
    }

    @TargetApi(19)
    private static void i(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 19) {
            bitmap.setPremultiplied(true);
        }
    }

    public void P() {
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "clearMemory");
        }
        b(0);
    }

    @NonNull
    public Bitmap b(int i, int i2, Bitmap.Config config) {
        Bitmap h = h(i, i2, config);
        if (h == null) {
            return createBitmap(i, i2, config);
        }
        h.eraseColor(0);
        return h;
    }

    public synchronized void b(float f) {
        this.maxSize = (long) Math.round(((float) this.iE) * f);
        bT();
    }

    public synchronized void d(Bitmap bitmap) {
        if (bitmap == null) {
            throw new NullPointerException("Bitmap must not be null");
        } else if (!bitmap.isRecycled()) {
            if (bitmap.isMutable() && ((long) this.iC.f(bitmap)) <= this.maxSize) {
                if (this.iD.contains(bitmap.getConfig())) {
                    int f = this.iC.f(bitmap);
                    this.iC.d(bitmap);
                    this.iF.j(bitmap);
                    this.iJ++;
                    this.iG += (long) f;
                    if (Log.isLoggable(TAG, 2)) {
                        Log.v(TAG, "Put bitmap in pool=" + this.iC.e(bitmap));
                    }
                    dump();
                    bT();
                    return;
                }
            }
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "Reject bitmap from pool, bitmap: " + this.iC.e(bitmap) + ", is mutable: " + bitmap.isMutable() + ", is allowed config: " + this.iD.contains(bitmap.getConfig()));
            }
            bitmap.recycle();
        } else {
            throw new IllegalStateException("Cannot pool recycled bitmap");
        }
    }

    @NonNull
    public Bitmap g(int i, int i2, Bitmap.Config config) {
        Bitmap h = h(i, i2, config);
        return h == null ? createBitmap(i, i2, config) : h;
    }

    public long getMaxSize() {
        return this.maxSize;
    }

    @SuppressLint({"InlinedApi"})
    public void trimMemory(int i) {
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "trimMemory, level=" + i);
        }
        if (i >= 40) {
            P();
        } else if (i >= 20 || i == 15) {
            b(getMaxSize() / 2);
        }
    }
}
