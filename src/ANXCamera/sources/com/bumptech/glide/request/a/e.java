package com.bumptech.glide.request.a;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.request.a.f;

/* compiled from: NoTransition */
public class e<R> implements f<R> {
    static final e<?> Ql = new e<>();
    private static final g<?> Rl = new a();

    /* compiled from: NoTransition */
    public static class a<R> implements g<R> {
        public f<R> a(DataSource dataSource, boolean z) {
            return e.Ql;
        }
    }

    public static <R> f<R> get() {
        return Ql;
    }

    public static <R> g<R> getFactory() {
        return Rl;
    }

    public boolean a(Object obj, f.a aVar) {
        return false;
    }
}
