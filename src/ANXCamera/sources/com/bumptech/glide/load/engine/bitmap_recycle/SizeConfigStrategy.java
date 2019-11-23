package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.util.k;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

@RequiresApi(19)
public class SizeConfigStrategy implements k {
    private static final int iM = 8;
    private static final Bitmap.Config[] iN;
    private static final Bitmap.Config[] iO = iN;
    private static final Bitmap.Config[] iP = {Bitmap.Config.RGB_565};
    private static final Bitmap.Config[] iQ = {Bitmap.Config.ARGB_4444};
    private static final Bitmap.Config[] iR = {Bitmap.Config.ALPHA_8};
    private final KeyPool iS = new KeyPool();
    private final g<Key, Bitmap> ii = new g<>();
    private final Map<Bitmap.Config, NavigableMap<Integer, Integer>> iu = new HashMap();

    /* renamed from: com.bumptech.glide.load.engine.bitmap_recycle.SizeConfigStrategy$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] iT = new int[Bitmap.Config.values().length];

        static {
            try {
                iT[Bitmap.Config.ARGB_8888.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iT[Bitmap.Config.RGB_565.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iT[Bitmap.Config.ARGB_4444.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iT[Bitmap.Config.ALPHA_8.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    @VisibleForTesting
    static final class Key implements l {
        private Bitmap.Config config;
        private final KeyPool iU;
        int size;

        public Key(KeyPool keyPool) {
            this.iU = keyPool;
        }

        @VisibleForTesting
        Key(KeyPool keyPool, int i, Bitmap.Config config2) {
            this(keyPool);
            c(i, config2);
        }

        public void bO() {
            this.iU.a(this);
        }

        public void c(int i, Bitmap.Config config2) {
            this.size = i;
            this.config = config2;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Key)) {
                return false;
            }
            Key key = (Key) obj;
            return this.size == key.size && k.d(this.config, key.config);
        }

        public int hashCode() {
            return (31 * this.size) + (this.config != null ? this.config.hashCode() : 0);
        }

        public String toString() {
            return SizeConfigStrategy.b(this.size, this.config);
        }
    }

    @VisibleForTesting
    static class KeyPool extends c<Key> {
        KeyPool() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: bZ */
        public Key bQ() {
            return new Key(this);
        }

        public Key d(int i, Bitmap.Config config) {
            Key key = (Key) bR();
            key.c(i, config);
            return key;
        }
    }

    static {
        Bitmap.Config[] configArr = {Bitmap.Config.ARGB_8888, null};
        if (Build.VERSION.SDK_INT >= 26) {
            configArr = (Bitmap.Config[]) Arrays.copyOf(configArr, configArr.length + 1);
            configArr[configArr.length - 1] = Bitmap.Config.RGBA_F16;
        }
        iN = configArr;
    }

    private Key a(int i, Bitmap.Config config) {
        Key d = this.iS.d(i, config);
        Bitmap.Config[] d2 = d(config);
        int length = d2.length;
        int i2 = 0;
        while (i2 < length) {
            Bitmap.Config config2 = d2[i2];
            Integer ceilingKey = c(config2).ceilingKey(Integer.valueOf(i));
            if (ceilingKey == null || ceilingKey.intValue() > i * 8) {
                i2++;
            } else {
                if (ceilingKey.intValue() == i) {
                    if (config2 == null) {
                        if (config == null) {
                            return d;
                        }
                    } else if (config2.equals(config)) {
                        return d;
                    }
                }
                this.iS.a(d);
                return this.iS.d(ceilingKey.intValue(), config2);
            }
        }
        return d;
    }

    private void a(Integer num, Bitmap bitmap) {
        NavigableMap<Integer, Integer> c = c(bitmap.getConfig());
        Integer num2 = (Integer) c.get(num);
        if (num2 == null) {
            throw new NullPointerException("Tried to decrement empty size, size: " + num + ", removed: " + e(bitmap) + ", this: " + this);
        } else if (num2.intValue() == 1) {
            c.remove(num);
        } else {
            c.put(num, Integer.valueOf(num2.intValue() - 1));
        }
    }

    static String b(int i, Bitmap.Config config) {
        return "[" + i + "](" + config + ")";
    }

    private NavigableMap<Integer, Integer> c(Bitmap.Config config) {
        NavigableMap<Integer, Integer> navigableMap = this.iu.get(config);
        if (navigableMap != null) {
            return navigableMap;
        }
        TreeMap treeMap = new TreeMap();
        this.iu.put(config, treeMap);
        return treeMap;
    }

    private static Bitmap.Config[] d(Bitmap.Config config) {
        if (Build.VERSION.SDK_INT >= 26 && Bitmap.Config.RGBA_F16.equals(config)) {
            return iO;
        }
        switch (AnonymousClass1.iT[config.ordinal()]) {
            case 1:
                return iN;
            case 2:
                return iP;
            case 3:
                return iQ;
            case 4:
                return iR;
            default:
                return new Bitmap.Config[]{config};
        }
    }

    @Nullable
    public Bitmap b(int i, int i2, Bitmap.Config config) {
        Key a2 = a(k.i(i, i2, config), config);
        Bitmap b2 = this.ii.b(a2);
        if (b2 != null) {
            a(Integer.valueOf(a2.size), b2);
            b2.reconfigure(i, i2, b2.getConfig() != null ? b2.getConfig() : Bitmap.Config.ARGB_8888);
        }
        return b2;
    }

    @Nullable
    public Bitmap bN() {
        Bitmap removeLast = this.ii.removeLast();
        if (removeLast != null) {
            a(Integer.valueOf(k.p(removeLast)), removeLast);
        }
        return removeLast;
    }

    public String c(int i, int i2, Bitmap.Config config) {
        return b(k.i(i, i2, config), config);
    }

    public void d(Bitmap bitmap) {
        Key d = this.iS.d(k.p(bitmap), bitmap.getConfig());
        this.ii.a(d, bitmap);
        NavigableMap<Integer, Integer> c = c(bitmap.getConfig());
        Integer num = (Integer) c.get(Integer.valueOf(d.size));
        Integer valueOf = Integer.valueOf(d.size);
        int i = 1;
        if (num != null) {
            i = 1 + num.intValue();
        }
        c.put(valueOf, Integer.valueOf(i));
    }

    public String e(Bitmap bitmap) {
        return b(k.p(bitmap), bitmap.getConfig());
    }

    public int f(Bitmap bitmap) {
        return k.p(bitmap);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SizeConfigStrategy{groupedMap=");
        sb.append(this.ii);
        sb.append(", sortedSizes=(");
        for (Map.Entry next : this.iu.entrySet()) {
            sb.append(next.getKey());
            sb.append('[');
            sb.append(next.getValue());
            sb.append("], ");
        }
        if (!this.iu.isEmpty()) {
            sb.replace(sb.length() - 2, sb.length(), "");
        }
        sb.append(")}");
        return sb.toString();
    }
}
