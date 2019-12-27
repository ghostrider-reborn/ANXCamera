package com.bumptech.glide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.a.a;
import com.bumptech.glide.load.engine.a.m;
import com.bumptech.glide.load.engine.a.o;
import com.bumptech.glide.load.engine.a.q;
import com.bumptech.glide.load.engine.b.b;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.engine.bitmap_recycle.i;
import com.bumptech.glide.load.engine.bitmap_recycle.j;
import com.bumptech.glide.manager.g;
import com.bumptech.glide.manager.n;
import com.bumptech.glide.request.f;
import java.util.Map;

/* compiled from: GlideBuilder */
public final class d {
    private com.bumptech.glide.load.engine.bitmap_recycle.d Bb;
    private o Cb;
    private com.bumptech.glide.manager.d Gb;
    private b Mb;
    private b Nb;
    private a.C0007a Ob;
    private q Pb;
    @Nullable
    private n.a Qb;
    private b Rb;
    private boolean Sb;
    private int logLevel = 4;
    private com.bumptech.glide.load.engine.bitmap_recycle.b qa;
    private f ta = new f();
    private final Map<Class<?>, n<?, ?>> ua = new ArrayMap();
    private Engine va;

    /* access modifiers changed from: package-private */
    public d a(Engine engine) {
        this.va = engine;
        return this;
    }

    @NonNull
    public d a(@Nullable a.C0007a aVar) {
        this.Ob = aVar;
        return this;
    }

    @NonNull
    public d a(@Nullable o oVar) {
        this.Cb = oVar;
        return this;
    }

    @NonNull
    public d a(@NonNull q.a aVar) {
        return a(aVar.build());
    }

    @NonNull
    public d a(@Nullable q qVar) {
        this.Pb = qVar;
        return this;
    }

    @NonNull
    public d a(@Nullable b bVar) {
        this.Rb = bVar;
        return this;
    }

    @NonNull
    public d a(@Nullable com.bumptech.glide.load.engine.bitmap_recycle.b bVar) {
        this.qa = bVar;
        return this;
    }

    @NonNull
    public d a(@Nullable com.bumptech.glide.load.engine.bitmap_recycle.d dVar) {
        this.Bb = dVar;
        return this;
    }

    @NonNull
    public d a(@Nullable com.bumptech.glide.manager.d dVar) {
        this.Gb = dVar;
        return this;
    }

    @NonNull
    public d a(@Nullable f fVar) {
        this.ta = fVar;
        return this;
    }

    @NonNull
    public <T> d a(@NonNull Class<T> cls, @Nullable n<?, T> nVar) {
        this.ua.put(cls, nVar);
        return this;
    }

    /* access modifiers changed from: package-private */
    public void a(@Nullable n.a aVar) {
        this.Qb = aVar;
    }

    @NonNull
    public d b(@Nullable b bVar) {
        this.Nb = bVar;
        return this;
    }

    @Deprecated
    public d c(@Nullable b bVar) {
        return d(bVar);
    }

    @NonNull
    public d d(@Nullable b bVar) {
        this.Mb = bVar;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public c g(@NonNull Context context) {
        if (this.Mb == null) {
            this.Mb = b.Bg();
        }
        if (this.Nb == null) {
            this.Nb = b.Ag();
        }
        if (this.Rb == null) {
            this.Rb = b.zg();
        }
        if (this.Pb == null) {
            this.Pb = new q.a(context).build();
        }
        if (this.Gb == null) {
            this.Gb = new g();
        }
        if (this.Bb == null) {
            int wg = this.Pb.wg();
            if (wg > 0) {
                this.Bb = new j((long) wg);
            } else {
                this.Bb = new e();
            }
        }
        if (this.qa == null) {
            this.qa = new i(this.Pb.vg());
        }
        if (this.Cb == null) {
            this.Cb = new com.bumptech.glide.load.engine.a.n((long) this.Pb.xg());
        }
        if (this.Ob == null) {
            this.Ob = new m(context);
        }
        if (this.va == null) {
            Engine engine = new Engine(this.Cb, this.Ob, this.Nb, this.Mb, b.Cg(), b.zg(), this.Sb);
            this.va = engine;
        }
        Context context2 = context;
        c cVar = new c(context2, this.va, this.Cb, this.Bb, this.qa, new n(this.Qb), this.Gb, this.logLevel, this.ta.lock(), this.ua);
        return cVar;
    }

    @NonNull
    public d o(boolean z) {
        this.Sb = z;
        return this;
    }

    @NonNull
    public d setLogLevel(int i) {
        if (i < 2 || i > 6) {
            throw new IllegalArgumentException("Log level must be one of Log.VERBOSE, Log.DEBUG, Log.INFO, Log.WARN, or Log.ERROR");
        }
        this.logLevel = i;
        return this;
    }
}
