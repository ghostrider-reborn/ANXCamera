package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.util.l;

class AttributeStrategy implements k {
    private final KeyPool cg = new KeyPool();
    private final g<Key, Bitmap> dg = new g<>();

    @VisibleForTesting
    static class Key implements l {
        private Config config;
        private int height;
        private final KeyPool pool;
        private int width;

        public Key(KeyPool keyPool) {
            this.pool = keyPool;
        }

        public void e(int i, int i2, Config config2) {
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
            int i = ((this.width * 31) + this.height) * 31;
            Config config2 = this.config;
            return i + (config2 != null ? config2.hashCode() : 0);
        }

        public String toString() {
            return AttributeStrategy.f(this.width, this.height, this.config);
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

        /* access modifiers changed from: 0000 */
        public Key d(int i, int i2, Config config) {
            Key key = (Key) get();
            key.e(i, i2, config);
            return key;
        }
    }

    AttributeStrategy() {
    }

    static String f(int i, int i2, Config config) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(i);
        sb.append("x");
        sb.append(i2);
        sb.append("], ");
        sb.append(config);
        return sb.toString();
    }

    private static String k(Bitmap bitmap) {
        return f(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
    }

    public void a(Bitmap bitmap) {
        this.dg.a(this.cg.d(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig()), bitmap);
    }

    public String b(int i, int i2, Config config) {
        return f(i, i2, config);
    }

    public int c(Bitmap bitmap) {
        return l.j(bitmap);
    }

    public Bitmap d(int i, int i2, Config config) {
        return (Bitmap) this.dg.b(this.cg.d(i, i2, config));
    }

    public String e(Bitmap bitmap) {
        return k(bitmap);
    }

    public Bitmap removeLast() {
        return (Bitmap) this.dg.removeLast();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AttributeStrategy:\n  ");
        sb.append(this.dg);
        return sb.toString();
    }
}
