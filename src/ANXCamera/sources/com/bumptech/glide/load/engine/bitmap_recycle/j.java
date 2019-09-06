package com.bumptech.glide.load.engine.bitmap_recycle;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* compiled from: LruBitmapPool */
public class j implements d {
    private static final Config DEFAULT_CONFIG = Config.ARGB_8888;
    private static final String TAG = "LruBitmapPool";
    private long ig;
    private final Set<Config> lg;
    private long maxSize;
    private final long mg;
    private final a ng;
    private int og;
    private int pg;
    private int qg;
    private int rg;
    private final k strategy;

    /* compiled from: LruBitmapPool */
    private interface a {
        void b(Bitmap bitmap);

        void f(Bitmap bitmap);
    }

    /* compiled from: LruBitmapPool */
    private static final class b implements a {
        b() {
        }

        public void b(Bitmap bitmap) {
        }

        public void f(Bitmap bitmap) {
        }
    }

    /* compiled from: LruBitmapPool */
    private static class c implements a {
        private final Set<Bitmap> kg = Collections.synchronizedSet(new HashSet());

        private c() {
        }

        public void b(Bitmap bitmap) {
            if (!this.kg.contains(bitmap)) {
                this.kg.add(bitmap);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Can't add already added bitmap: ");
            sb.append(bitmap);
            sb.append(" [");
            sb.append(bitmap.getWidth());
            sb.append("x");
            sb.append(bitmap.getHeight());
            sb.append("]");
            throw new IllegalStateException(sb.toString());
        }

        public void f(Bitmap bitmap) {
            if (this.kg.contains(bitmap)) {
                this.kg.remove(bitmap);
                return;
            }
            throw new IllegalStateException("Cannot remove bitmap not in tracker");
        }
    }

    public j(long j) {
        this(j, tk(), sk());
    }

    j(long j, k kVar, Set<Config> set) {
        this.mg = j;
        this.maxSize = j;
        this.strategy = kVar;
        this.lg = set;
        this.ng = new b();
    }

    public j(long j, Set<Config> set) {
        this(j, tk(), set);
    }

    @TargetApi(26)
    private static void b(Config config) {
        if (VERSION.SDK_INT >= 26 && config == Config.HARDWARE) {
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot create a mutable Bitmap with config: ");
            sb.append(config);
            sb.append(". Consider setting Downsampler#ALLOW_HARDWARE_CONFIG to false in your RequestOptions and/or in GlideBuilder.setDefaultRequestOptions");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    @NonNull
    private static Bitmap createBitmap(int i, int i2, @Nullable Config config) {
        if (config == null) {
            config = DEFAULT_CONFIG;
        }
        return Bitmap.createBitmap(i, i2, config);
    }

    private void dump() {
        if (Log.isLoggable(TAG, 2)) {
            rk();
        }
    }

    @Nullable
    private synchronized Bitmap h(int i, int i2, @Nullable Config config) {
        Bitmap d2;
        b(config);
        d2 = this.strategy.d(i, i2, config != null ? config : DEFAULT_CONFIG);
        if (d2 == null) {
            if (Log.isLoggable(TAG, 3)) {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Missing bitmap=");
                sb.append(this.strategy.b(i, i2, config));
                Log.d(str, sb.toString());
            }
            this.pg++;
        } else {
            this.og++;
            this.ig -= (long) this.strategy.c(d2);
            this.ng.f(d2);
            m(d2);
        }
        if (Log.isLoggable(TAG, 2)) {
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Get bitmap=");
            sb2.append(this.strategy.b(i, i2, config));
            Log.v(str2, sb2.toString());
        }
        dump();
        return d2;
    }

    private synchronized void h(long j) {
        while (this.ig > j) {
            Bitmap removeLast = this.strategy.removeLast();
            if (removeLast == null) {
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "Size mismatch, resetting");
                    rk();
                }
                this.ig = 0;
                return;
            }
            this.ng.f(removeLast);
            this.ig -= (long) this.strategy.c(removeLast);
            this.rg++;
            if (Log.isLoggable(TAG, 3)) {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Evicting bitmap=");
                sb.append(this.strategy.e(removeLast));
                Log.d(str, sb.toString());
            }
            dump();
            removeLast.recycle();
        }
    }

    @TargetApi(19)
    private static void l(Bitmap bitmap) {
        if (VERSION.SDK_INT >= 19) {
            bitmap.setPremultiplied(true);
        }
    }

    private static void m(Bitmap bitmap) {
        bitmap.setHasAlpha(true);
        l(bitmap);
    }

    private void pk() {
        h(this.maxSize);
    }

    private void rk() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hits=");
        sb.append(this.og);
        sb.append(", misses=");
        sb.append(this.pg);
        sb.append(", puts=");
        sb.append(this.qg);
        sb.append(", evictions=");
        sb.append(this.rg);
        sb.append(", currentSize=");
        sb.append(this.ig);
        sb.append(", maxSize=");
        sb.append(this.maxSize);
        sb.append("\nStrategy=");
        sb.append(this.strategy);
        Log.v(TAG, sb.toString());
    }

    @TargetApi(26)
    private static Set<Config> sk() {
        HashSet hashSet = new HashSet(Arrays.asList(Config.values()));
        if (VERSION.SDK_INT >= 19) {
            hashSet.add(null);
        }
        if (VERSION.SDK_INT >= 26) {
            hashSet.remove(Config.HARDWARE);
        }
        return Collections.unmodifiableSet(hashSet);
    }

    private static k tk() {
        return VERSION.SDK_INT >= 19 ? new SizeConfigStrategy() : new AttributeStrategy();
    }

    public void G() {
        String str = TAG;
        if (Log.isLoggable(str, 3)) {
            Log.d(str, "clearMemory");
        }
        h(0);
    }

    public synchronized void a(float f2) {
        this.maxSize = (long) Math.round(((float) this.mg) * f2);
        pk();
    }

    public synchronized void a(Bitmap bitmap) {
        if (bitmap != null) {
            try {
                if (!bitmap.isRecycled()) {
                    if (bitmap.isMutable() && ((long) this.strategy.c(bitmap)) <= this.maxSize) {
                        if (this.lg.contains(bitmap.getConfig())) {
                            int c2 = this.strategy.c(bitmap);
                            this.strategy.a(bitmap);
                            this.ng.b(bitmap);
                            this.qg++;
                            this.ig += (long) c2;
                            if (Log.isLoggable(TAG, 2)) {
                                String str = TAG;
                                StringBuilder sb = new StringBuilder();
                                sb.append("Put bitmap in pool=");
                                sb.append(this.strategy.e(bitmap));
                                Log.v(str, sb.toString());
                            }
                            dump();
                            pk();
                            return;
                        }
                    }
                    if (Log.isLoggable(TAG, 2)) {
                        String str2 = TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Reject bitmap from pool, bitmap: ");
                        sb2.append(this.strategy.e(bitmap));
                        sb2.append(", is mutable: ");
                        sb2.append(bitmap.isMutable());
                        sb2.append(", is allowed config: ");
                        sb2.append(this.lg.contains(bitmap.getConfig()));
                        Log.v(str2, sb2.toString());
                    }
                    bitmap.recycle();
                    return;
                }
                throw new IllegalStateException("Cannot pool recycled bitmap");
            } finally {
            }
        } else {
            throw new NullPointerException("Bitmap must not be null");
        }
    }

    @NonNull
    public Bitmap c(int i, int i2, Config config) {
        Bitmap h = h(i, i2, config);
        return h == null ? createBitmap(i, i2, config) : h;
    }

    @NonNull
    public Bitmap d(int i, int i2, Config config) {
        Bitmap h = h(i, i2, config);
        if (h == null) {
            return createBitmap(i, i2, config);
        }
        h.eraseColor(0);
        return h;
    }

    public long getMaxSize() {
        return this.maxSize;
    }

    @SuppressLint({"InlinedApi"})
    public void trimMemory(int i) {
        String str = TAG;
        if (Log.isLoggable(str, 3)) {
            StringBuilder sb = new StringBuilder();
            sb.append("trimMemory, level=");
            sb.append(i);
            Log.d(str, sb.toString());
        }
        if (i >= 40) {
            G();
        } else if (i >= 20 || i == 15) {
            h(getMaxSize() / 2);
        }
    }
}
