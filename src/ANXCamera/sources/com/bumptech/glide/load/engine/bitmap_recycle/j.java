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
    private int hA;
    private final k hs;
    private final Set<Config> ht;
    private final long hu;
    private final a hv;
    private long hw;
    private int hx;
    private int hy;
    private int hz;
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
        private final Set<Bitmap> hB = Collections.synchronizedSet(new HashSet());

        private c() {
        }

        public void j(Bitmap bitmap) {
            if (!this.hB.contains(bitmap)) {
                this.hB.add(bitmap);
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

        public void k(Bitmap bitmap) {
            if (this.hB.contains(bitmap)) {
                this.hB.remove(bitmap);
                return;
            }
            throw new IllegalStateException("Cannot remove bitmap not in tracker");
        }
    }

    public j(long j) {
        this(j, bv(), bw());
    }

    j(long j, k kVar, Set<Config> set) {
        this.hu = j;
        this.maxSize = j;
        this.hs = kVar;
        this.ht = set;
        this.hv = new b();
    }

    public j(long j, Set<Config> set) {
        this(j, bv(), set);
    }

    private synchronized void b(long j) {
        while (this.hw > j) {
            Bitmap bl = this.hs.bl();
            if (bl == null) {
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "Size mismatch, resetting");
                    bu();
                }
                this.hw = 0;
                return;
            }
            this.hv.k(bl);
            this.hw -= (long) this.hs.f(bl);
            this.hA++;
            if (Log.isLoggable(TAG, 3)) {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Evicting bitmap=");
                sb.append(this.hs.e(bl));
                Log.d(str, sb.toString());
            }
            dump();
            bl.recycle();
        }
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

    private void br() {
        b(this.maxSize);
    }

    private void bu() {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Hits=");
        sb.append(this.hx);
        sb.append(", misses=");
        sb.append(this.hy);
        sb.append(", puts=");
        sb.append(this.hz);
        sb.append(", evictions=");
        sb.append(this.hA);
        sb.append(", currentSize=");
        sb.append(this.hw);
        sb.append(", maxSize=");
        sb.append(this.maxSize);
        sb.append("\nStrategy=");
        sb.append(this.hs);
        Log.v(str, sb.toString());
    }

    private static k bv() {
        return VERSION.SDK_INT >= 19 ? new SizeConfigStrategy() : new AttributeStrategy();
    }

    @TargetApi(26)
    private static Set<Config> bw() {
        HashSet hashSet = new HashSet(Arrays.asList(Config.values()));
        if (VERSION.SDK_INT >= 19) {
            hashSet.add(null);
        }
        if (VERSION.SDK_INT >= 26) {
            hashSet.remove(Config.HARDWARE);
        }
        return Collections.unmodifiableSet(hashSet);
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
            bu();
        }
    }

    @Nullable
    private synchronized Bitmap h(int i, int i2, @Nullable Config config) {
        Bitmap b2;
        b(config);
        b2 = this.hs.b(i, i2, config != null ? config : DEFAULT_CONFIG);
        if (b2 == null) {
            if (Log.isLoggable(TAG, 3)) {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Missing bitmap=");
                sb.append(this.hs.c(i, i2, config));
                Log.d(str, sb.toString());
            }
            this.hy++;
        } else {
            this.hx++;
            this.hw -= (long) this.hs.f(b2);
            this.hv.k(b2);
            h(b2);
        }
        if (Log.isLoggable(TAG, 2)) {
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Get bitmap=");
            sb2.append(this.hs.c(i, i2, config));
            Log.v(str2, sb2.toString());
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
        if (VERSION.SDK_INT >= 19) {
            bitmap.setPremultiplied(true);
        }
    }

    @NonNull
    public Bitmap b(int i, int i2, Config config) {
        Bitmap h = h(i, i2, config);
        if (h == null) {
            return createBitmap(i, i2, config);
        }
        h.eraseColor(0);
        return h;
    }

    public synchronized void b(float f) {
        this.maxSize = (long) Math.round(((float) this.hu) * f);
        br();
    }

    public synchronized void d(Bitmap bitmap) {
        if (bitmap == null) {
            throw new NullPointerException("Bitmap must not be null");
        } else if (!bitmap.isRecycled()) {
            if (bitmap.isMutable() && ((long) this.hs.f(bitmap)) <= this.maxSize) {
                if (this.ht.contains(bitmap.getConfig())) {
                    int f = this.hs.f(bitmap);
                    this.hs.d(bitmap);
                    this.hv.j(bitmap);
                    this.hz++;
                    this.hw += (long) f;
                    if (Log.isLoggable(TAG, 2)) {
                        String str = TAG;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Put bitmap in pool=");
                        sb.append(this.hs.e(bitmap));
                        Log.v(str, sb.toString());
                    }
                    dump();
                    br();
                    return;
                }
            }
            if (Log.isLoggable(TAG, 2)) {
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Reject bitmap from pool, bitmap: ");
                sb2.append(this.hs.e(bitmap));
                sb2.append(", is mutable: ");
                sb2.append(bitmap.isMutable());
                sb2.append(", is allowed config: ");
                sb2.append(this.ht.contains(bitmap.getConfig()));
                Log.v(str2, sb2.toString());
            }
            bitmap.recycle();
        } else {
            throw new IllegalStateException("Cannot pool recycled bitmap");
        }
    }

    @NonNull
    public Bitmap g(int i, int i2, Config config) {
        Bitmap h = h(i, i2, config);
        return h == null ? createBitmap(i, i2, config) : h;
    }

    public long getMaxSize() {
        return this.maxSize;
    }

    public void o() {
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "clearMemory");
        }
        b(0);
    }

    @SuppressLint({"InlinedApi"})
    public void trimMemory(int i) {
        if (Log.isLoggable(TAG, 3)) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("trimMemory, level=");
            sb.append(i);
            Log.d(str, sb.toString());
        }
        if (i >= 40) {
            o();
        } else if (i >= 20 || i == 15) {
            b(getMaxSize() / 2);
        }
    }
}