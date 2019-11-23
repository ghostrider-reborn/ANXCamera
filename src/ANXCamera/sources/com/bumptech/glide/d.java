package com.bumptech.glide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.a.a;
import com.bumptech.glide.load.engine.a.h;
import com.bumptech.glide.load.engine.a.j;
import com.bumptech.glide.load.engine.a.l;
import com.bumptech.glide.load.engine.b.a;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.engine.bitmap_recycle.i;
import com.bumptech.glide.manager.l;
import com.bumptech.glide.request.f;
import java.util.Map;

/* compiled from: GlideBuilder */
public final class d {
    private l bA;
    private f bB = new f();
    @Nullable
    private l.a bC;
    private a bD;
    private boolean bE;
    private Engine bl;
    private com.bumptech.glide.load.engine.bitmap_recycle.d bm;
    private j bn;
    private b br;
    private com.bumptech.glide.manager.d bt;
    private final Map<Class<?>, j<?, ?>> bw = new ArrayMap();
    private a bx;
    private a by;
    private a.C0005a bz;
    private int logLevel = 4;

    /* access modifiers changed from: package-private */
    public d a(Engine engine) {
        this.bl = engine;
        return this;
    }

    @NonNull
    public d a(@Nullable a.C0005a aVar) {
        this.bz = aVar;
        return this;
    }

    @NonNull
    public d a(@Nullable j jVar) {
        this.bn = jVar;
        return this;
    }

    @NonNull
    public d a(@NonNull l.a aVar) {
        return a(aVar.cm());
    }

    @NonNull
    public d a(@Nullable com.bumptech.glide.load.engine.a.l lVar) {
        this.bA = lVar;
        return this;
    }

    @Deprecated
    public d a(@Nullable com.bumptech.glide.load.engine.b.a aVar) {
        return b(aVar);
    }

    @NonNull
    public d a(@Nullable b bVar) {
        this.br = bVar;
        return this;
    }

    @NonNull
    public d a(@Nullable com.bumptech.glide.load.engine.bitmap_recycle.d dVar) {
        this.bm = dVar;
        return this;
    }

    @NonNull
    public d a(@Nullable com.bumptech.glide.manager.d dVar) {
        this.bt = dVar;
        return this;
    }

    @NonNull
    public d a(@Nullable f fVar) {
        this.bB = fVar;
        return this;
    }

    @NonNull
    public <T> d a(@NonNull Class<T> cls, @Nullable j<?, T> jVar) {
        this.bw.put(cls, jVar);
        return this;
    }

    /* access modifiers changed from: package-private */
    public void a(@Nullable l.a aVar) {
        this.bC = aVar;
    }

    @NonNull
    public d b(@Nullable com.bumptech.glide.load.engine.b.a aVar) {
        this.bx = aVar;
        return this;
    }

    @NonNull
    public d c(@Nullable com.bumptech.glide.load.engine.b.a aVar) {
        this.by = aVar;
        return this;
    }

    @NonNull
    public d c(boolean z) {
        this.bE = z;
        return this;
    }

    @NonNull
    public d d(@Nullable com.bumptech.glide.load.engine.b.a aVar) {
        this.bD = aVar;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public c h(@NonNull Context context) {
        if (this.bx == null) {
            this.bx = com.bumptech.glide.load.engine.b.a.cs();
        }
        if (this.by == null) {
            this.by = com.bumptech.glide.load.engine.b.a.cq();
        }
        if (this.bD == null) {
            this.bD = com.bumptech.glide.load.engine.b.a.cu();
        }
        if (this.bA == null) {
            this.bA = new l.a(context).cm();
        }
        if (this.bt == null) {
            this.bt = new com.bumptech.glide.manager.f();
        }
        if (this.bm == null) {
            int ck = this.bA.ck();
            if (ck > 0) {
                this.bm = new com.bumptech.glide.load.engine.bitmap_recycle.j((long) ck);
            } else {
                this.bm = new e();
            }
        }
        if (this.br == null) {
            this.br = new i(this.bA.cl());
        }
        if (this.bn == null) {
            this.bn = new com.bumptech.glide.load.engine.a.i((long) this.bA.cj());
        }
        if (this.bz == null) {
            this.bz = new h(context);
        }
        if (this.bl == null) {
            Engine engine = new Engine(this.bn, this.bz, this.by, this.bx, com.bumptech.glide.load.engine.b.a.ct(), com.bumptech.glide.load.engine.b.a.cu(), this.bE);
            this.bl = engine;
        }
        Context context2 = context;
        c cVar = new c(context2, this.bl, this.bn, this.bm, this.br, new com.bumptech.glide.manager.l(this.bC), this.bt, this.logLevel, this.bB.eo(), this.bw);
        return cVar;
    }

    @NonNull
    public d i(int i) {
        if (i < 2 || i > 6) {
            throw new IllegalArgumentException("Log level must be one of Log.VERBOSE, Log.DEBUG, Log.INFO, Log.WARN, or Log.ERROR");
        }
        this.logLevel = i;
        return this;
    }
}
