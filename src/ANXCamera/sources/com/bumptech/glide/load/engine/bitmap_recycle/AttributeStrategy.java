package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.util.k;

class AttributeStrategy implements k {
    private final KeyPool ig = new KeyPool();
    private final g<Key, Bitmap> ii = new g<>();

    @VisibleForTesting
    static class Key implements l {
        private Bitmap.Config config;
        private int height;
        private final KeyPool ij;
        private int width;

        public Key(KeyPool keyPool) {
            this.ij = keyPool;
        }

        public void bO() {
            this.ij.a(this);
        }

        public void e(int i, int i2, Bitmap.Config config2) {
            this.width = i;
            this.height = i2;
            this.config = config2;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Key)) {
                return false;
            }
            Key key = (Key) obj;
            return this.width == key.width && this.height == key.height && this.config == key.config;
        }

        public int hashCode() {
            return (31 * ((this.width * 31) + this.height)) + (this.config != null ? this.config.hashCode() : 0);
        }

        public String toString() {
            return AttributeStrategy.d(this.width, this.height, this.config);
        }
    }

    @VisibleForTesting
    static class KeyPool extends c<Key> {
        KeyPool() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: bP */
        public Key bQ() {
            return new Key(this);
        }

        /* access modifiers changed from: package-private */
        public Key f(int i, int i2, Bitmap.Config config) {
            Key key = (Key) bR();
            key.e(i, i2, config);
            return key;
        }
    }

    AttributeStrategy() {
    }

    static String d(int i, int i2, Bitmap.Config config) {
        return "[" + i + "x" + i2 + "], " + config;
    }

    private static String g(Bitmap bitmap) {
        return d(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
    }

    public Bitmap b(int i, int i2, Bitmap.Config config) {
        return this.ii.b(this.ig.f(i, i2, config));
    }

    public Bitmap bN() {
        return this.ii.removeLast();
    }

    public String c(int i, int i2, Bitmap.Config config) {
        return d(i, i2, config);
    }

    public void d(Bitmap bitmap) {
        this.ii.a(this.ig.f(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig()), bitmap);
    }

    public String e(Bitmap bitmap) {
        return g(bitmap);
    }

    public int f(Bitmap bitmap) {
        return k.p(bitmap);
    }

    public String toString() {
        return "AttributeStrategy:\n  " + this.ii;
    }
}
