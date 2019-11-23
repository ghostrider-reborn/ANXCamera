package com.bumptech.glide.load.model.a;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.g;
import com.bumptech.glide.load.model.h;
import com.bumptech.glide.load.model.m;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* compiled from: BaseGlideUrlLoader */
public abstract class a<Model> implements m<Model, InputStream> {
    private final m<g, InputStream> lu;
    @Nullable
    private final ModelCache<Model, g> lv;

    protected a(m<g, InputStream> mVar) {
        this(mVar, (ModelCache) null);
    }

    protected a(m<g, InputStream> mVar, @Nullable ModelCache<Model, g> modelCache) {
        this.lu = mVar;
        this.lv = modelCache;
    }

    private static List<c> a(Collection<String> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        for (String gVar : collection) {
            arrayList.add(new g(gVar));
        }
        return arrayList;
    }

    @Nullable
    public m.a<InputStream> b(@NonNull Model model, int i, int i2, @NonNull f fVar) {
        g c = this.lv != null ? this.lv.c(model, i, i2) : null;
        if (c == null) {
            String c2 = c(model, i, i2, fVar);
            if (TextUtils.isEmpty(c2)) {
                return null;
            }
            g gVar = new g(c2, e(model, i, i2, fVar));
            if (this.lv != null) {
                this.lv.a(model, i, i2, gVar);
            }
            c = gVar;
        }
        List<String> d = d(model, i, i2, fVar);
        m.a<InputStream> b2 = this.lu.b(c, i, i2, fVar);
        return (b2 == null || d.isEmpty()) ? b2 : new m.a<>(b2.fK, a(d), b2.kZ);
    }

    /* access modifiers changed from: protected */
    public abstract String c(Model model, int i, int i2, f fVar);

    /* access modifiers changed from: protected */
    public List<String> d(Model model, int i, int i2, f fVar) {
        return Collections.emptyList();
    }

    /* access modifiers changed from: protected */
    @Nullable
    public h e(Model model, int i, int i2, f fVar) {
        return h.kO;
    }
}
