package com.bumptech.glide.request.a;

import android.graphics.drawable.Drawable;
import com.bumptech.glide.load.DataSource;

/* compiled from: DrawableCrossFadeFactory */
public class c implements g<Drawable> {
    private final boolean Nl;
    private d Pl;
    private final int duration;

    /* compiled from: DrawableCrossFadeFactory */
    public static class a {
        private static final int Ol = 300;
        private boolean Nl;
        private final int durationMillis;

        public a() {
            this(300);
        }

        public a(int i) {
            this.durationMillis = i;
        }

        public c build() {
            return new c(this.durationMillis, this.Nl);
        }

        public a setCrossFadeEnabled(boolean z) {
            this.Nl = z;
            return this;
        }
    }

    protected c(int i, boolean z) {
        this.duration = i;
        this.Nl = z;
    }

    private f<Drawable> dl() {
        if (this.Pl == null) {
            this.Pl = new d(this.duration, this.Nl);
        }
        return this.Pl;
    }

    public f<Drawable> a(DataSource dataSource, boolean z) {
        return dataSource == DataSource.MEMORY_CACHE ? e.get() : dl();
    }
}
