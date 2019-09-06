package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.util.l;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

@RequiresApi(19)
public class SizeConfigStrategy implements k {
    private static final int tg = 8;
    private static final Config[] ug;
    private static final Config[] vg = ug;
    private static final Config[] wg = {Config.RGB_565};
    private static final Config[] xg = {Config.ARGB_4444};
    private static final Config[] yg = {Config.ALPHA_8};
    private final KeyPool cg = new KeyPool();
    private final g<Key, Bitmap> dg = new g<>();
    private final Map<Config, NavigableMap<Integer, Integer>> gg = new HashMap();

    @VisibleForTesting
    static final class Key implements l {
        private Config config;
        private final KeyPool pool;
        int size;

        public Key(KeyPool keyPool) {
            this.pool = keyPool;
        }

        @VisibleForTesting
        Key(KeyPool keyPool, int i, Config config2) {
            this(keyPool);
            b(i, config2);
        }

        public void b(int i, Config config2) {
            this.size = i;
            this.config = config2;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Key)) {
                return false;
            }
            Key key = (Key) obj;
            return this.size == key.size && l.d((Object) this.config, (Object) key.config);
        }

        public int hashCode() {
            int i = this.size * 31;
            Config config2 = this.config;
            return i + (config2 != null ? config2.hashCode() : 0);
        }

        public String toString() {
            return SizeConfigStrategy.c(this.size, this.config);
        }

        public void u() {
            this.pool.a(this);
        }
    }

    @VisibleForTesting
    static class KeyPool extends c<Key> {
        KeyPool() {
        }

        public Key a(int i, Config config) {
            Key key = (Key) get();
            key.b(i, config);
            return key;
        }

        /* access modifiers changed from: protected */
        public Key create() {
            return new Key(this);
        }
    }

    static {
        Config[] configArr = {Config.ARGB_8888, null};
        if (VERSION.SDK_INT >= 26) {
            configArr = (Config[]) Arrays.copyOf(configArr, configArr.length + 1);
            configArr[configArr.length - 1] = Config.RGBA_F16;
        }
        ug = configArr;
    }

    private void a(Integer num, Bitmap bitmap) {
        NavigableMap d2 = d(bitmap.getConfig());
        Integer num2 = (Integer) d2.get(num);
        if (num2 == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Tried to decrement empty size, size: ");
            sb.append(num);
            sb.append(", removed: ");
            sb.append(e(bitmap));
            sb.append(", this: ");
            sb.append(this);
            throw new NullPointerException(sb.toString());
        } else if (num2.intValue() == 1) {
            d2.remove(num);
        } else {
            d2.put(num, Integer.valueOf(num2.intValue() - 1));
        }
    }

    static String c(int i, Config config) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(i);
        sb.append("](");
        sb.append(config);
        sb.append(")");
        return sb.toString();
    }

    private static Config[] c(Config config) {
        if (VERSION.SDK_INT >= 26 && Config.RGBA_F16.equals(config)) {
            return vg;
        }
        int i = m.sg[config.ordinal()];
        if (i == 1) {
            return ug;
        }
        if (i == 2) {
            return wg;
        }
        if (i == 3) {
            return xg;
        }
        if (i == 4) {
            return yg;
        }
        return new Config[]{config};
    }

    private Key d(int i, Config config) {
        Key a2 = this.cg.a(i, config);
        Config[] c2 = c(config);
        int length = c2.length;
        int i2 = 0;
        while (i2 < length) {
            Config config2 = c2[i2];
            Integer num = (Integer) d(config2).ceilingKey(Integer.valueOf(i));
            if (num == null || num.intValue() > i * 8) {
                i2++;
            } else {
                if (num.intValue() == i) {
                    if (config2 == null) {
                        if (config == null) {
                            return a2;
                        }
                    } else if (config2.equals(config)) {
                        return a2;
                    }
                }
                this.cg.a(a2);
                return this.cg.a(num.intValue(), config2);
            }
        }
        return a2;
    }

    private NavigableMap<Integer, Integer> d(Config config) {
        NavigableMap<Integer, Integer> navigableMap = (NavigableMap) this.gg.get(config);
        if (navigableMap != null) {
            return navigableMap;
        }
        TreeMap treeMap = new TreeMap();
        this.gg.put(config, treeMap);
        return treeMap;
    }

    public void a(Bitmap bitmap) {
        Key a2 = this.cg.a(l.j(bitmap), bitmap.getConfig());
        this.dg.a(a2, bitmap);
        NavigableMap d2 = d(bitmap.getConfig());
        Integer num = (Integer) d2.get(Integer.valueOf(a2.size));
        Integer valueOf = Integer.valueOf(a2.size);
        int i = 1;
        if (num != null) {
            i = 1 + num.intValue();
        }
        d2.put(valueOf, Integer.valueOf(i));
    }

    public String b(int i, int i2, Config config) {
        return c(l.g(i, i2, config), config);
    }

    public int c(Bitmap bitmap) {
        return l.j(bitmap);
    }

    @Nullable
    public Bitmap d(int i, int i2, Config config) {
        Key d2 = d(l.g(i, i2, config), config);
        Bitmap bitmap = (Bitmap) this.dg.b(d2);
        if (bitmap != null) {
            a(Integer.valueOf(d2.size), bitmap);
            bitmap.reconfigure(i, i2, bitmap.getConfig() != null ? bitmap.getConfig() : Config.ARGB_8888);
        }
        return bitmap;
    }

    public String e(Bitmap bitmap) {
        return c(l.j(bitmap), bitmap.getConfig());
    }

    @Nullable
    public Bitmap removeLast() {
        Bitmap bitmap = (Bitmap) this.dg.removeLast();
        if (bitmap != null) {
            a(Integer.valueOf(l.j(bitmap)), bitmap);
        }
        return bitmap;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SizeConfigStrategy{groupedMap=");
        sb.append(this.dg);
        sb.append(", sortedSizes=(");
        for (Entry entry : this.gg.entrySet()) {
            sb.append(entry.getKey());
            sb.append('[');
            sb.append(entry.getValue());
            sb.append("], ");
        }
        if (!this.gg.isEmpty()) {
            sb.replace(sb.length() - 2, sb.length(), "");
        }
        sb.append(")}");
        return sb.toString();
    }
}
