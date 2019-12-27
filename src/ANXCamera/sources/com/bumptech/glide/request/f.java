package com.bumptech.glide.request;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.CheckResult;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.Priority;
import com.bumptech.glide.e.b;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.d;
import com.bumptech.glide.load.engine.o;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.j;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.load.resource.bitmap.VideoDecoder;
import com.bumptech.glide.load.resource.bitmap.k;
import com.bumptech.glide.load.resource.bitmap.l;
import com.bumptech.glide.load.resource.bitmap.r;
import com.bumptech.glide.load.resource.bitmap.s;
import com.bumptech.glide.load.resource.gif.e;
import com.bumptech.glide.util.CachedHashCodeArrayMap;
import com.bumptech.glide.util.i;
import java.util.Map;

/* compiled from: RequestOptions */
public class f implements Cloneable {
    private static final int FALLBACK = 8192;
    private static final int PRIORITY = 8;
    private static final int Qk = 2;
    private static final int Rk = 4;
    private static final int SIGNATURE = 1024;
    private static final int Sk = 16;
    private static final int THEME = 32768;
    private static final int Tk = 32;
    private static final int UNSET = -1;
    private static final int Uk = 64;
    private static final int Vk = 128;
    private static final int Wk = 256;
    private static final int Xk = 512;
    private static final int Yk = 4096;
    private static final int Zk = 16384;
    private static final int _k = 65536;
    private static final int al = 131072;
    private static final int bl = 262144;
    private static final int cl = 524288;
    private static final int dl = 1048576;
    @Nullable
    private static f el = null;
    @Nullable
    private static f fl = null;
    @Nullable
    private static f gl = null;
    @Nullable
    private static f hl = null;
    @Nullable
    private static f il = null;
    @Nullable
    private static f jl = null;
    @Nullable
    private static f kl = null;
    @Nullable
    private static f ll = null;
    private static final int qi = 2048;
    private boolean Af;
    @NonNull
    private Class<?> Be = Object.class;
    private float Ek = 1.0f;
    @NonNull
    private o Fe = o.AUTOMATIC;
    @Nullable
    private Drawable Fk;
    private boolean Ge;
    private int Gk;
    private boolean He = true;
    @Nullable
    private Drawable Hk;
    private int Ik;
    private int Jk = -1;
    private int Kk = -1;
    @NonNull
    private Map<Class<?>, j<?>> Ld = new CachedHashCodeArrayMap();
    private boolean Lk = true;
    @Nullable
    private Drawable Mk;
    private int Nk;
    private boolean Ok;
    private boolean Pk;
    private boolean Ye;
    private int fields;
    private boolean isLocked;
    @NonNull
    private g options = new g();
    @NonNull
    private Priority priority = Priority.NORMAL;
    @NonNull
    private c signature = b.obtain();
    @Nullable
    private Resources.Theme theme;
    private boolean yf = true;

    @CheckResult
    @NonNull
    public static f C(@IntRange(from = 0) int i) {
        return m(i, i);
    }

    @CheckResult
    @NonNull
    public static f E(@DrawableRes int i) {
        return new f().D(i);
    }

    @CheckResult
    @NonNull
    public static f G(@IntRange(from = 0) int i) {
        return new f().F(i);
    }

    @CheckResult
    @NonNull
    public static f Qg() {
        if (il == null) {
            il = new f().Pg().Og();
        }
        return il;
    }

    @NonNull
    private f Qk() {
        if (!this.isLocked) {
            return this;
        }
        throw new IllegalStateException("You cannot modify locked RequestOptions, consider clone()");
    }

    @CheckResult
    @NonNull
    public static f Sg() {
        if (hl == null) {
            hl = new f().Rg().Og();
        }
        return hl;
    }

    @CheckResult
    @NonNull
    public static f Ug() {
        if (jl == null) {
            jl = new f().Tg().Og();
        }
        return jl;
    }

    @CheckResult
    @NonNull
    public static f Yg() {
        if (gl == null) {
            gl = new f().fitCenter().Og();
        }
        return gl;
    }

    @CheckResult
    @NonNull
    public static f a(@NonNull j<Bitmap> jVar) {
        return new f().c(jVar);
    }

    @NonNull
    private f a(@NonNull j<Bitmap> jVar, boolean z) {
        if (this.Ok) {
            return clone().a(jVar, z);
        }
        r rVar = new r(jVar, z);
        a(Bitmap.class, jVar, z);
        a(Drawable.class, rVar, z);
        a(BitmapDrawable.class, rVar.Eg(), z);
        a(com.bumptech.glide.load.resource.gif.b.class, new e(jVar), z);
        Qk();
        return this;
    }

    @NonNull
    private f a(@NonNull DownsampleStrategy downsampleStrategy, @NonNull j<Bitmap> jVar, boolean z) {
        f b2 = z ? b(downsampleStrategy, jVar) : a(downsampleStrategy, jVar);
        b2.He = true;
        return b2;
    }

    @NonNull
    private <T> f a(@NonNull Class<T> cls, @NonNull j<T> jVar, boolean z) {
        if (this.Ok) {
            return clone().a(cls, jVar, z);
        }
        i.checkNotNull(cls);
        i.checkNotNull(jVar);
        this.Ld.put(cls, jVar);
        this.fields |= 2048;
        this.Lk = true;
        this.fields |= 65536;
        this.He = false;
        if (z) {
            this.fields |= 131072;
            this.Ge = true;
        }
        Qk();
        return this;
    }

    @CheckResult
    @NonNull
    public static f b(@NonNull Bitmap.CompressFormat compressFormat) {
        return new f().a(compressFormat);
    }

    @CheckResult
    @NonNull
    public static f b(@NonNull Priority priority2) {
        return new f().a(priority2);
    }

    @CheckResult
    @NonNull
    public static f b(@NonNull DecodeFormat decodeFormat) {
        return new f().a(decodeFormat);
    }

    @CheckResult
    @NonNull
    public static f b(@NonNull o oVar) {
        return new f().a(oVar);
    }

    @CheckResult
    @NonNull
    public static <T> f b(@NonNull com.bumptech.glide.load.f<T> fVar, @NonNull T t) {
        return new f().a(fVar, t);
    }

    @CheckResult
    @NonNull
    public static f b(@NonNull DownsampleStrategy downsampleStrategy) {
        return new f().a(downsampleStrategy);
    }

    @NonNull
    private f c(@NonNull DownsampleStrategy downsampleStrategy, @NonNull j<Bitmap> jVar) {
        return a(downsampleStrategy, jVar, false);
    }

    @NonNull
    private f d(@NonNull DownsampleStrategy downsampleStrategy, @NonNull j<Bitmap> jVar) {
        return a(downsampleStrategy, jVar, true);
    }

    @CheckResult
    @NonNull
    public static f f(@IntRange(from = 0) long j) {
        return new f().e(j);
    }

    @CheckResult
    @NonNull
    public static f g(@Nullable Drawable drawable) {
        return new f().f(drawable);
    }

    @CheckResult
    @NonNull
    public static f h(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        return new f().g(f2);
    }

    @CheckResult
    @NonNull
    public static f h(@NonNull c cVar) {
        return new f().g(cVar);
    }

    private boolean isSet(int i) {
        return q(this.fields, i);
    }

    @CheckResult
    @NonNull
    public static f j(@Nullable Drawable drawable) {
        return new f().i(drawable);
    }

    @CheckResult
    @NonNull
    public static f k(@NonNull Class<?> cls) {
        return new f().j(cls);
    }

    @CheckResult
    @NonNull
    public static f m(@IntRange(from = 0) int i, @IntRange(from = 0) int i2) {
        return new f().l(i, i2);
    }

    private static boolean q(int i, int i2) {
        return (i & i2) != 0;
    }

    @CheckResult
    @NonNull
    public static f r(boolean z) {
        if (z) {
            if (el == null) {
                el = new f().q(true).Og();
            }
            return el;
        }
        if (fl == null) {
            fl = new f().q(false).Og();
        }
        return fl;
    }

    @CheckResult
    @NonNull
    public static f uh() {
        if (ll == null) {
            ll = new f().Wg().Og();
        }
        return ll;
    }

    @CheckResult
    @NonNull
    public static f vh() {
        if (kl == null) {
            kl = new f().Xg().Og();
        }
        return kl;
    }

    @CheckResult
    @NonNull
    public static f y(@IntRange(from = 0, to = 100) int i) {
        return new f().x(i);
    }

    @CheckResult
    @NonNull
    public static f z(@DrawableRes int i) {
        return new f().error(i);
    }

    @CheckResult
    @NonNull
    public f A(@DrawableRes int i) {
        if (this.Ok) {
            return clone().A(i);
        }
        this.Nk = i;
        this.fields |= 16384;
        Qk();
        return this;
    }

    @CheckResult
    @NonNull
    public f B(int i) {
        return l(i, i);
    }

    @CheckResult
    @NonNull
    public f D(@DrawableRes int i) {
        if (this.Ok) {
            return clone().D(i);
        }
        this.Ik = i;
        this.fields |= 128;
        Qk();
        return this;
    }

    @CheckResult
    @NonNull
    public f F(@IntRange(from = 0) int i) {
        return a(com.bumptech.glide.load.model.a.b.TIMEOUT, Integer.valueOf(i));
    }

    @NonNull
    public f Og() {
        if (!this.isLocked || this.Ok) {
            this.Ok = true;
            return lock();
        }
        throw new IllegalStateException("You cannot auto lock an already locked options object, try clone() first");
    }

    @CheckResult
    @NonNull
    public f Pg() {
        return b(DownsampleStrategy.Ui, (j<Bitmap>) new com.bumptech.glide.load.resource.bitmap.j());
    }

    @CheckResult
    @NonNull
    public f Rg() {
        return d(DownsampleStrategy.CENTER_INSIDE, new k());
    }

    @CheckResult
    @NonNull
    public f Tg() {
        return b(DownsampleStrategy.CENTER_INSIDE, (j<Bitmap>) new l());
    }

    @CheckResult
    @NonNull
    public f Vg() {
        return a(com.bumptech.glide.load.resource.bitmap.o.aj, false);
    }

    @CheckResult
    @NonNull
    public f Wg() {
        return a(com.bumptech.glide.load.resource.gif.g.Wj, true);
    }

    @CheckResult
    @NonNull
    public f Xg() {
        if (this.Ok) {
            return clone().Xg();
        }
        this.Ld.clear();
        this.fields &= -2049;
        this.Ge = false;
        this.fields &= -131073;
        this.Lk = false;
        this.fields |= 65536;
        this.He = true;
        Qk();
        return this;
    }

    public final int Zg() {
        return this.Gk;
    }

    @Nullable
    public final Drawable _g() {
        return this.Fk;
    }

    @CheckResult
    @NonNull
    public f a(@Nullable Resources.Theme theme2) {
        if (this.Ok) {
            return clone().a(theme2);
        }
        this.theme = theme2;
        this.fields |= 32768;
        Qk();
        return this;
    }

    @CheckResult
    @NonNull
    public f a(@NonNull Bitmap.CompressFormat compressFormat) {
        com.bumptech.glide.load.f<Bitmap.CompressFormat> fVar = com.bumptech.glide.load.resource.bitmap.e.ti;
        i.checkNotNull(compressFormat);
        return a(fVar, compressFormat);
    }

    @CheckResult
    @NonNull
    public f a(@NonNull Priority priority2) {
        if (this.Ok) {
            return clone().a(priority2);
        }
        i.checkNotNull(priority2);
        this.priority = priority2;
        this.fields |= 8;
        Qk();
        return this;
    }

    @CheckResult
    @NonNull
    public f a(@NonNull DecodeFormat decodeFormat) {
        i.checkNotNull(decodeFormat);
        return a(com.bumptech.glide.load.resource.bitmap.o.Yi, decodeFormat).a(com.bumptech.glide.load.resource.gif.g.Yi, decodeFormat);
    }

    @CheckResult
    @NonNull
    public f a(@NonNull o oVar) {
        if (this.Ok) {
            return clone().a(oVar);
        }
        i.checkNotNull(oVar);
        this.Fe = oVar;
        this.fields |= 4;
        Qk();
        return this;
    }

    @CheckResult
    @NonNull
    public <T> f a(@NonNull com.bumptech.glide.load.f<T> fVar, @NonNull T t) {
        if (this.Ok) {
            return clone().a(fVar, t);
        }
        i.checkNotNull(fVar);
        i.checkNotNull(t);
        this.options.a(fVar, t);
        Qk();
        return this;
    }

    @CheckResult
    @NonNull
    public f a(@NonNull DownsampleStrategy downsampleStrategy) {
        com.bumptech.glide.load.f<DownsampleStrategy> fVar = DownsampleStrategy.Wi;
        i.checkNotNull(downsampleStrategy);
        return a(fVar, downsampleStrategy);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public final f a(@NonNull DownsampleStrategy downsampleStrategy, @NonNull j<Bitmap> jVar) {
        if (this.Ok) {
            return clone().a(downsampleStrategy, jVar);
        }
        a(downsampleStrategy);
        return a(jVar, false);
    }

    @CheckResult
    @NonNull
    public <T> f a(@NonNull Class<T> cls, @NonNull j<T> jVar) {
        return a(cls, jVar, false);
    }

    @CheckResult
    @NonNull
    public f a(@NonNull j<Bitmap>... jVarArr) {
        return a((j<Bitmap>) new d((j<T>[]) jVarArr), true);
    }

    @Nullable
    public final Drawable ah() {
        return this.Mk;
    }

    @CheckResult
    @NonNull
    public f b(@NonNull j<Bitmap> jVar) {
        return a(jVar, false);
    }

    /* access modifiers changed from: package-private */
    @CheckResult
    @NonNull
    public final f b(@NonNull DownsampleStrategy downsampleStrategy, @NonNull j<Bitmap> jVar) {
        if (this.Ok) {
            return clone().b(downsampleStrategy, jVar);
        }
        a(downsampleStrategy);
        return c(jVar);
    }

    @CheckResult
    @NonNull
    public f b(@NonNull f fVar) {
        if (this.Ok) {
            return clone().b(fVar);
        }
        if (q(fVar.fields, 2)) {
            this.Ek = fVar.Ek;
        }
        if (q(fVar.fields, 262144)) {
            this.Pk = fVar.Pk;
        }
        if (q(fVar.fields, 1048576)) {
            this.Af = fVar.Af;
        }
        if (q(fVar.fields, 4)) {
            this.Fe = fVar.Fe;
        }
        if (q(fVar.fields, 8)) {
            this.priority = fVar.priority;
        }
        if (q(fVar.fields, 16)) {
            this.Fk = fVar.Fk;
        }
        if (q(fVar.fields, 32)) {
            this.Gk = fVar.Gk;
        }
        if (q(fVar.fields, 64)) {
            this.Hk = fVar.Hk;
        }
        if (q(fVar.fields, 128)) {
            this.Ik = fVar.Ik;
        }
        if (q(fVar.fields, 256)) {
            this.yf = fVar.yf;
        }
        if (q(fVar.fields, 512)) {
            this.Kk = fVar.Kk;
            this.Jk = fVar.Jk;
        }
        if (q(fVar.fields, 1024)) {
            this.signature = fVar.signature;
        }
        if (q(fVar.fields, 4096)) {
            this.Be = fVar.Be;
        }
        if (q(fVar.fields, 8192)) {
            this.Mk = fVar.Mk;
        }
        if (q(fVar.fields, 16384)) {
            this.Nk = fVar.Nk;
        }
        if (q(fVar.fields, 32768)) {
            this.theme = fVar.theme;
        }
        if (q(fVar.fields, 65536)) {
            this.Lk = fVar.Lk;
        }
        if (q(fVar.fields, 131072)) {
            this.Ge = fVar.Ge;
        }
        if (q(fVar.fields, 2048)) {
            this.Ld.putAll(fVar.Ld);
            this.He = fVar.He;
        }
        if (q(fVar.fields, 524288)) {
            this.Ye = fVar.Ye;
        }
        if (!this.Lk) {
            this.Ld.clear();
            this.fields &= -2049;
            this.Ge = false;
            this.fields &= -131073;
            this.He = true;
        }
        this.fields |= fVar.fields;
        this.options.b(fVar.options);
        Qk();
        return this;
    }

    @CheckResult
    @NonNull
    public <T> f b(@NonNull Class<T> cls, @NonNull j<T> jVar) {
        return a(cls, jVar, true);
    }

    public final int bh() {
        return this.Nk;
    }

    @CheckResult
    @NonNull
    public f c(@NonNull j<Bitmap> jVar) {
        return a(jVar, true);
    }

    @CheckResult
    public f clone() {
        try {
            f fVar = (f) super.clone();
            fVar.options = new g();
            fVar.options.b(this.options);
            fVar.Ld = new CachedHashCodeArrayMap();
            fVar.Ld.putAll(this.Ld);
            fVar.isLocked = false;
            fVar.Ok = false;
            return fVar;
        } catch (CloneNotSupportedException e2) {
            throw new RuntimeException(e2);
        }
    }

    public final boolean dh() {
        return this.Ye;
    }

    @CheckResult
    @NonNull
    public f e(@IntRange(from = 0) long j) {
        return a(VideoDecoder.Bj, Long.valueOf(j));
    }

    @NonNull
    public final o eg() {
        return this.Fe;
    }

    public final int eh() {
        return this.Jk;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof f)) {
            return false;
        }
        f fVar = (f) obj;
        return Float.compare(fVar.Ek, this.Ek) == 0 && this.Gk == fVar.Gk && com.bumptech.glide.util.l.d((Object) this.Fk, (Object) fVar.Fk) && this.Ik == fVar.Ik && com.bumptech.glide.util.l.d((Object) this.Hk, (Object) fVar.Hk) && this.Nk == fVar.Nk && com.bumptech.glide.util.l.d((Object) this.Mk, (Object) fVar.Mk) && this.yf == fVar.yf && this.Jk == fVar.Jk && this.Kk == fVar.Kk && this.Ge == fVar.Ge && this.Lk == fVar.Lk && this.Pk == fVar.Pk && this.Ye == fVar.Ye && this.Fe.equals(fVar.Fe) && this.priority == fVar.priority && this.options.equals(fVar.options) && this.Ld.equals(fVar.Ld) && this.Be.equals(fVar.Be) && com.bumptech.glide.util.l.d((Object) this.signature, (Object) fVar.signature) && com.bumptech.glide.util.l.d((Object) this.theme, (Object) fVar.theme);
    }

    @CheckResult
    @NonNull
    public f error(@DrawableRes int i) {
        if (this.Ok) {
            return clone().error(i);
        }
        this.Gk = i;
        this.fields |= 32;
        Qk();
        return this;
    }

    @CheckResult
    @NonNull
    public f f(@Nullable Drawable drawable) {
        if (this.Ok) {
            return clone().f(drawable);
        }
        this.Fk = drawable;
        this.fields |= 16;
        Qk();
        return this;
    }

    public final int fh() {
        return this.Kk;
    }

    @CheckResult
    @NonNull
    public f fitCenter() {
        return d(DownsampleStrategy.FIT_CENTER, new s());
    }

    @CheckResult
    @NonNull
    public f g(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        if (this.Ok) {
            return clone().g(f2);
        }
        if (f2 < 0.0f || f2 > 1.0f) {
            throw new IllegalArgumentException("sizeMultiplier must be between 0 and 1");
        }
        this.Ek = f2;
        this.fields |= 2;
        Qk();
        return this;
    }

    @CheckResult
    @NonNull
    public f g(@NonNull c cVar) {
        if (this.Ok) {
            return clone().g(cVar);
        }
        i.checkNotNull(cVar);
        this.signature = cVar;
        this.fields |= 1024;
        Qk();
        return this;
    }

    @NonNull
    public final g getOptions() {
        return this.options;
    }

    @NonNull
    public final Priority getPriority() {
        return this.priority;
    }

    @NonNull
    public final c getSignature() {
        return this.signature;
    }

    @Nullable
    public final Resources.Theme getTheme() {
        return this.theme;
    }

    @NonNull
    public final Map<Class<?>, j<?>> getTransformations() {
        return this.Ld;
    }

    @Nullable
    public final Drawable gh() {
        return this.Hk;
    }

    @CheckResult
    @NonNull
    public f h(@Nullable Drawable drawable) {
        if (this.Ok) {
            return clone().h(drawable);
        }
        this.Mk = drawable;
        this.fields |= 8192;
        Qk();
        return this;
    }

    public int hashCode() {
        return com.bumptech.glide.util.l.a((Object) this.theme, com.bumptech.glide.util.l.a((Object) this.signature, com.bumptech.glide.util.l.a((Object) this.Be, com.bumptech.glide.util.l.a((Object) this.Ld, com.bumptech.glide.util.l.a((Object) this.options, com.bumptech.glide.util.l.a((Object) this.priority, com.bumptech.glide.util.l.a((Object) this.Fe, com.bumptech.glide.util.l.d(this.Ye, com.bumptech.glide.util.l.d(this.Pk, com.bumptech.glide.util.l.d(this.Lk, com.bumptech.glide.util.l.d(this.Ge, com.bumptech.glide.util.l.n(this.Kk, com.bumptech.glide.util.l.n(this.Jk, com.bumptech.glide.util.l.d(this.yf, com.bumptech.glide.util.l.a((Object) this.Mk, com.bumptech.glide.util.l.n(this.Nk, com.bumptech.glide.util.l.a((Object) this.Hk, com.bumptech.glide.util.l.n(this.Ik, com.bumptech.glide.util.l.a((Object) this.Fk, com.bumptech.glide.util.l.n(this.Gk, com.bumptech.glide.util.l.hashCode(this.Ek)))))))))))))))))))));
    }

    public final int hh() {
        return this.Ik;
    }

    @CheckResult
    @NonNull
    public f i(@Nullable Drawable drawable) {
        if (this.Ok) {
            return clone().i(drawable);
        }
        this.Hk = drawable;
        this.fields |= 64;
        Qk();
        return this;
    }

    public final float ih() {
        return this.Ek;
    }

    public final boolean isLocked() {
        return this.isLocked;
    }

    @CheckResult
    @NonNull
    public f j(@NonNull Class<?> cls) {
        if (this.Ok) {
            return clone().j(cls);
        }
        i.checkNotNull(cls);
        this.Be = cls;
        this.fields |= 4096;
        Qk();
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean jg() {
        return this.He;
    }

    public final boolean jh() {
        return this.Af;
    }

    public final boolean kh() {
        return this.Pk;
    }

    @CheckResult
    @NonNull
    public f l(int i, int i2) {
        if (this.Ok) {
            return clone().l(i, i2);
        }
        this.Kk = i;
        this.Jk = i2;
        this.fields |= 512;
        Qk();
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean lh() {
        return this.Ok;
    }

    @NonNull
    public f lock() {
        this.isLocked = true;
        return this;
    }

    public final boolean mh() {
        return isSet(4);
    }

    public final boolean nh() {
        return this.yf;
    }

    public final boolean oh() {
        return isSet(8);
    }

    @CheckResult
    @NonNull
    public f p(boolean z) {
        if (this.Ok) {
            return clone().p(z);
        }
        this.Ye = z;
        this.fields |= 524288;
        Qk();
        return this;
    }

    public final boolean ph() {
        return isSet(256);
    }

    @CheckResult
    @NonNull
    public f q(boolean z) {
        if (this.Ok) {
            return clone().q(true);
        }
        this.yf = !z;
        this.fields |= 256;
        Qk();
        return this;
    }

    public final boolean qh() {
        return this.Lk;
    }

    public final boolean rh() {
        return this.Ge;
    }

    @CheckResult
    @NonNull
    public f s(boolean z) {
        if (this.Ok) {
            return clone().s(z);
        }
        this.Af = z;
        this.fields |= 1048576;
        Qk();
        return this;
    }

    public final boolean sh() {
        return isSet(2048);
    }

    @CheckResult
    @NonNull
    public f t(boolean z) {
        if (this.Ok) {
            return clone().t(z);
        }
        this.Pk = z;
        this.fields |= 262144;
        Qk();
        return this;
    }

    public final boolean th() {
        return com.bumptech.glide.util.l.o(this.Kk, this.Jk);
    }

    @CheckResult
    @NonNull
    public f wh() {
        return a(DownsampleStrategy.Ui, (j<Bitmap>) new com.bumptech.glide.load.resource.bitmap.j());
    }

    @CheckResult
    @NonNull
    public f x(@IntRange(from = 0, to = 100) int i) {
        return a(com.bumptech.glide.load.resource.bitmap.e.ri, Integer.valueOf(i));
    }

    @CheckResult
    @NonNull
    public f xh() {
        return c(DownsampleStrategy.CENTER_INSIDE, new k());
    }

    @CheckResult
    @NonNull
    public f yh() {
        return a(DownsampleStrategy.Ui, (j<Bitmap>) new l());
    }

    @NonNull
    public final Class<?> z() {
        return this.Be;
    }

    @CheckResult
    @NonNull
    public f zh() {
        return c(DownsampleStrategy.FIT_CENTER, new s());
    }
}
