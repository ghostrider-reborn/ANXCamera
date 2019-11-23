package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.util.k;
import java.util.NavigableMap;

@RequiresApi(19)
final class SizeStrategy implements k {
    private static final int iM = 8;
    private final KeyPool iV = new KeyPool();
    private final NavigableMap<Integer, Integer> iW = new PrettyPrintTreeMap();
    private final g<Key, Bitmap> ii = new g<>();

    @VisibleForTesting
    static final class Key implements l {
        private final KeyPool iX;
        int size;

        Key(KeyPool keyPool) {
            this.iX = keyPool;
        }

        public void bO() {
            this.iX.a(this);
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
            return SizeStrategy.x(this.size);
        }
    }

    @VisibleForTesting
    static class KeyPool extends c<Key> {
        KeyPool() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: ca */
        public Key bQ() {
            return new Key(this);
        }

        public Key y(int i) {
            Key key = (Key) super.bR();
            key.init(i);
            return key;
        }
    }

    SizeStrategy() {
    }

    private void c(Integer num) {
        Integer num2 = (Integer) this.iW.get(num);
        if (num2.intValue() == 1) {
            this.iW.remove(num);
        } else {
            this.iW.put(num, Integer.valueOf(num2.intValue() - 1));
        }
    }

    private static String g(Bitmap bitmap) {
        return x(k.p(bitmap));
    }

    static String x(int i) {
        return "[" + i + "]";
    }

    @Nullable
    public Bitmap b(int i, int i2, Bitmap.Config config) {
        int i3 = k.i(i, i2, config);
        Key y = this.iV.y(i3);
        Integer ceilingKey = this.iW.ceilingKey(Integer.valueOf(i3));
        if (!(ceilingKey == null || ceilingKey.intValue() == i3 || ceilingKey.intValue() > i3 * 8)) {
            this.iV.a(y);
            y = this.iV.y(ceilingKey.intValue());
        }
        Bitmap b2 = this.ii.b(y);
        if (b2 != null) {
            b2.reconfigure(i, i2, config);
            c(ceilingKey);
        }
        return b2;
    }

    @Nullable
    public Bitmap bN() {
        Bitmap removeLast = this.ii.removeLast();
        if (removeLast != null) {
            c(Integer.valueOf(k.p(removeLast)));
        }
        return removeLast;
    }

    public String c(int i, int i2, Bitmap.Config config) {
        return x(k.i(i, i2, config));
    }

    public void d(Bitmap bitmap) {
        Key y = this.iV.y(k.p(bitmap));
        this.ii.a(y, bitmap);
        Integer num = (Integer) this.iW.get(Integer.valueOf(y.size));
        NavigableMap<Integer, Integer> navigableMap = this.iW;
        Integer valueOf = Integer.valueOf(y.size);
        int i = 1;
        if (num != null) {
            i = 1 + num.intValue();
        }
        navigableMap.put(valueOf, Integer.valueOf(i));
    }

    public String e(Bitmap bitmap) {
        return g(bitmap);
    }

    public int f(Bitmap bitmap) {
        return k.p(bitmap);
    }

    public String toString() {
        return "SizeStrategy:\n  " + this.ii + "\n  SortedSizes" + this.iW;
    }
}
