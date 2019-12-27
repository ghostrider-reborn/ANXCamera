package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.util.l;
import java.util.NavigableMap;

@RequiresApi(19)
final class SizeStrategy implements k {
    private static final int tg = 8;
    private final KeyPool cg = new KeyPool();
    private final g<Key, Bitmap> dg = new g<>();
    private final NavigableMap<Integer, Integer> gg = new PrettyPrintTreeMap();

    @VisibleForTesting
    static final class Key implements l {
        private final KeyPool pool;
        int size;

        Key(KeyPool keyPool) {
            this.pool = keyPool;
        }

        public boolean equals(Object obj) {
            return (obj instanceof Key) && this.size == ((Key) obj).size;
        }

        public int hashCode() {
            return this.size;
        }

        public void init(int i) {
            this.size = i;
        }

        public String toString() {
            return SizeStrategy.r(this.size);
        }

        public void u() {
            this.pool.a(this);
        }
    }

    @VisibleForTesting
    static class KeyPool extends c<Key> {
        KeyPool() {
        }

        /* access modifiers changed from: protected */
        public Key create() {
            return new Key(this);
        }

        public Key get(int i) {
            Key key = (Key) super.get();
            key.init(i);
            return key;
        }
    }

    SizeStrategy() {
    }

    private void d(Integer num) {
        Integer num2 = (Integer) this.gg.get(num);
        if (num2.intValue() == 1) {
            this.gg.remove(num);
        } else {
            this.gg.put(num, Integer.valueOf(num2.intValue() - 1));
        }
    }

    private static String k(Bitmap bitmap) {
        return r(l.j(bitmap));
    }

    static String r(int i) {
        return "[" + i + "]";
    }

    public void a(Bitmap bitmap) {
        Key key = this.cg.get(l.j(bitmap));
        this.dg.a(key, bitmap);
        Integer num = (Integer) this.gg.get(Integer.valueOf(key.size));
        NavigableMap<Integer, Integer> navigableMap = this.gg;
        Integer valueOf = Integer.valueOf(key.size);
        int i = 1;
        if (num != null) {
            i = 1 + num.intValue();
        }
        navigableMap.put(valueOf, Integer.valueOf(i));
    }

    public String b(int i, int i2, Bitmap.Config config) {
        return r(l.g(i, i2, config));
    }

    public int c(Bitmap bitmap) {
        return l.j(bitmap);
    }

    @Nullable
    public Bitmap d(int i, int i2, Bitmap.Config config) {
        int g = l.g(i, i2, config);
        Key key = this.cg.get(g);
        Integer ceilingKey = this.gg.ceilingKey(Integer.valueOf(g));
        if (!(ceilingKey == null || ceilingKey.intValue() == g || ceilingKey.intValue() > g * 8)) {
            this.cg.a(key);
            key = this.cg.get(ceilingKey.intValue());
        }
        Bitmap b2 = this.dg.b(key);
        if (b2 != null) {
            b2.reconfigure(i, i2, config);
            d(ceilingKey);
        }
        return b2;
    }

    public String e(Bitmap bitmap) {
        return k(bitmap);
    }

    @Nullable
    public Bitmap removeLast() {
        Bitmap removeLast = this.dg.removeLast();
        if (removeLast != null) {
            d(Integer.valueOf(l.j(removeLast)));
        }
        return removeLast;
    }

    public String toString() {
        return "SizeStrategy:\n  " + this.dg + "\n  SortedSizes" + this.gg;
    }
}
