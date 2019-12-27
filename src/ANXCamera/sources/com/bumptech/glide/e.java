package com.bumptech.glide;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.widget.ImageView;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.request.f;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.target.i;
import java.util.Map;

/* compiled from: GlideContext */
public class e extends ContextWrapper {
    @VisibleForTesting
    static final n<?, ?> DEFAULT_TRANSITION_OPTIONS = new b();
    private final int logLevel;
    private final Handler pa = new Handler(Looper.getMainLooper());
    private final b qa;
    private final i ra;
    private final Registry registry;
    private final f ta;
    private final Map<Class<?>, n<?, ?>> ua;
    private final Engine va;

    public e(@NonNull Context context, @NonNull b bVar, @NonNull Registry registry2, @NonNull i iVar, @NonNull f fVar, @NonNull Map<Class<?>, n<?, ?>> map, @NonNull Engine engine, int i) {
        super(context.getApplicationContext());
        this.qa = bVar;
        this.registry = registry2;
        this.ra = iVar;
        this.ta = fVar;
        this.ua = map;
        this.va = engine;
        this.logLevel = i;
    }

    @NonNull
    public b V() {
        return this.qa;
    }

    public f W() {
        return this.ta;
    }

    @NonNull
    public Engine X() {
        return this.va;
    }

    @NonNull
    public Handler Y() {
        return this.pa;
    }

    @NonNull
    public <T> n<?, T> a(@NonNull Class<T> cls) {
        n<?, T> nVar = this.ua.get(cls);
        if (nVar == null) {
            for (Map.Entry next : this.ua.entrySet()) {
                if (((Class) next.getKey()).isAssignableFrom(cls)) {
                    nVar = (n) next.getValue();
                }
            }
        }
        return nVar == null ? DEFAULT_TRANSITION_OPTIONS : nVar;
    }

    @NonNull
    public <X> ViewTarget<ImageView, X> a(@NonNull ImageView imageView, @NonNull Class<X> cls) {
        return this.ra.b(imageView, cls);
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    @NonNull
    public Registry getRegistry() {
        return this.registry;
    }
}
