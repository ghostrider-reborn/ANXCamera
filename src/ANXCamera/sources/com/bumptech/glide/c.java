package com.bumptech.glide;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentCallbacks2;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.a.e;
import com.bumptech.glide.load.a.k;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.a.j;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.engine.prefill.a;
import com.bumptech.glide.load.engine.prefill.c;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.model.a;
import com.bumptech.glide.load.model.a.b;
import com.bumptech.glide.load.model.a.c;
import com.bumptech.glide.load.model.a.d;
import com.bumptech.glide.load.model.a.e;
import com.bumptech.glide.load.model.a.f;
import com.bumptech.glide.load.model.b;
import com.bumptech.glide.load.model.d;
import com.bumptech.glide.load.model.e;
import com.bumptech.glide.load.model.f;
import com.bumptech.glide.load.model.k;
import com.bumptech.glide.load.model.r;
import com.bumptech.glide.load.model.s;
import com.bumptech.glide.load.model.t;
import com.bumptech.glide.load.model.u;
import com.bumptech.glide.load.model.v;
import com.bumptech.glide.load.model.w;
import com.bumptech.glide.load.resource.a.a;
import com.bumptech.glide.load.resource.b.e;
import com.bumptech.glide.load.resource.bitmap.VideoDecoder;
import com.bumptech.glide.load.resource.bitmap.i;
import com.bumptech.glide.load.resource.bitmap.m;
import com.bumptech.glide.load.resource.bitmap.n;
import com.bumptech.glide.load.resource.bitmap.t;
import com.bumptech.glide.load.resource.bitmap.v;
import com.bumptech.glide.load.resource.bitmap.x;
import com.bumptech.glide.load.resource.gif.ByteBufferGifDecoder;
import com.bumptech.glide.load.resource.gif.h;
import com.bumptech.glide.manager.l;
import com.bumptech.glide.request.f;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: Glide */
public class c implements ComponentCallbacks2 {
    private static final String TAG = "Glide";
    private static final String bi = "image_manager_disk_cache";
    private static volatile c bj;
    private static volatile boolean bk;
    private final Engine bl;
    private final d bm;
    private final j bn;
    private final a bo;
    private final e bp;
    private final Registry bq;
    private final b br;
    private final l bs;
    private final com.bumptech.glide.manager.d bt;
    private final List<i> bu = new ArrayList();
    private MemoryCategory bv = MemoryCategory.NORMAL;

    c(@NonNull Context context, @NonNull Engine engine, @NonNull j jVar, @NonNull d dVar, @NonNull b bVar, @NonNull l lVar, @NonNull com.bumptech.glide.manager.d dVar2, int i, @NonNull f fVar, @NonNull Map<Class<?>, j<?, ?>> map) {
        Context context2 = context;
        j jVar2 = jVar;
        d dVar3 = dVar;
        b bVar2 = bVar;
        this.bl = engine;
        this.bm = dVar3;
        this.br = bVar2;
        this.bn = jVar2;
        this.bs = lVar;
        this.bt = dVar2;
        this.bo = new a(jVar2, dVar3, (DecodeFormat) fVar.ba().a(n.mm));
        Resources resources = context.getResources();
        this.bq = new Registry();
        this.bq.a((ImageHeaderParser) new m());
        n nVar = new n(this.bq.X(), resources.getDisplayMetrics(), dVar3, bVar2);
        ByteBufferGifDecoder byteBufferGifDecoder = new ByteBufferGifDecoder(context2, this.bq.X(), dVar3, bVar2);
        g<ParcelFileDescriptor, Bitmap> c = VideoDecoder.c(dVar);
        i iVar = new i(nVar);
        v vVar = new v(nVar, bVar2);
        e eVar = new e(context2);
        r.c cVar = new r.c(resources);
        r.d dVar4 = new r.d(resources);
        r.b bVar3 = new r.b(resources);
        r.a aVar = new r.a(resources);
        com.bumptech.glide.load.resource.bitmap.e eVar2 = new com.bumptech.glide.load.resource.bitmap.e(bVar2);
        com.bumptech.glide.load.resource.d.a aVar2 = new com.bumptech.glide.load.resource.d.a();
        com.bumptech.glide.load.resource.d.d dVar5 = new com.bumptech.glide.load.resource.d.d();
        r.b bVar4 = bVar3;
        r.d dVar6 = dVar4;
        r.a aVar3 = aVar;
        Context context3 = context;
        ContentResolver contentResolver = context.getContentResolver();
        com.bumptech.glide.load.resource.d.a aVar4 = aVar2;
        com.bumptech.glide.load.resource.d.d dVar7 = dVar5;
        this.bq.b(ByteBuffer.class, new com.bumptech.glide.load.model.c()).b(InputStream.class, new s(bVar2)).a(Registry.ce, ByteBuffer.class, Bitmap.class, iVar).a(Registry.ce, InputStream.class, Bitmap.class, vVar).a(Registry.ce, ParcelFileDescriptor.class, Bitmap.class, c).a(Registry.ce, AssetFileDescriptor.class, Bitmap.class, VideoDecoder.b(dVar)).a(Bitmap.class, Bitmap.class, u.a.cO()).a(Registry.ce, Bitmap.class, Bitmap.class, new x()).b(Bitmap.class, eVar2).a(Registry.cf, ByteBuffer.class, BitmapDrawable.class, new com.bumptech.glide.load.resource.bitmap.a(resources, iVar)).a(Registry.cf, InputStream.class, BitmapDrawable.class, new com.bumptech.glide.load.resource.bitmap.a(resources, vVar)).a(Registry.cf, ParcelFileDescriptor.class, BitmapDrawable.class, new com.bumptech.glide.load.resource.bitmap.a(resources, c)).b(BitmapDrawable.class, new com.bumptech.glide.load.resource.bitmap.b(dVar3, eVar2)).a(Registry.cd, InputStream.class, com.bumptech.glide.load.resource.gif.b.class, new h(this.bq.X(), byteBufferGifDecoder, bVar2)).a(Registry.cd, ByteBuffer.class, com.bumptech.glide.load.resource.gif.b.class, byteBufferGifDecoder).b(com.bumptech.glide.load.resource.gif.b.class, new com.bumptech.glide.load.resource.gif.c()).a(com.bumptech.glide.b.a.class, com.bumptech.glide.b.a.class, u.a.cO()).a(Registry.ce, com.bumptech.glide.b.a.class, Bitmap.class, new com.bumptech.glide.load.resource.gif.f(dVar3)).a(Uri.class, Drawable.class, eVar).a(Uri.class, Bitmap.class, new t(eVar, dVar3)).a((e.a<?>) new a.C0010a()).a(File.class, ByteBuffer.class, new d.b()).a(File.class, InputStream.class, new f.e()).a(File.class, File.class, new com.bumptech.glide.load.resource.c.a()).a(File.class, ParcelFileDescriptor.class, new f.b()).a(File.class, File.class, u.a.cO()).a((e.a<?>) new k.a(bVar2)).a(Integer.TYPE, InputStream.class, cVar).a(Integer.TYPE, ParcelFileDescriptor.class, bVar4).a(Integer.class, InputStream.class, cVar).a(Integer.class, ParcelFileDescriptor.class, bVar4).a(Integer.class, Uri.class, dVar6).a(Integer.TYPE, AssetFileDescriptor.class, aVar3).a(Integer.class, AssetFileDescriptor.class, aVar3).a(Integer.TYPE, Uri.class, dVar6).a(String.class, InputStream.class, new e.c()).a(Uri.class, InputStream.class, new e.c()).a(String.class, InputStream.class, new t.c()).a(String.class, ParcelFileDescriptor.class, new t.b()).a(String.class, AssetFileDescriptor.class, new t.a()).a(Uri.class, InputStream.class, new c.a()).a(Uri.class, InputStream.class, new a.c(context.getAssets())).a(Uri.class, ParcelFileDescriptor.class, new a.b(context.getAssets())).a(Uri.class, InputStream.class, new d.a(context3)).a(Uri.class, InputStream.class, new e.a(context3)).a(Uri.class, InputStream.class, new v.d(contentResolver)).a(Uri.class, ParcelFileDescriptor.class, new v.b(contentResolver)).a(Uri.class, AssetFileDescriptor.class, new v.a(contentResolver)).a(Uri.class, InputStream.class, new w.a()).a(URL.class, InputStream.class, new f.a()).a(Uri.class, File.class, new k.a(context3)).a(com.bumptech.glide.load.model.g.class, InputStream.class, new b.a()).a(byte[].class, ByteBuffer.class, new b.a()).a(byte[].class, InputStream.class, new b.d()).a(Uri.class, Uri.class, u.a.cO()).a(Drawable.class, Drawable.class, u.a.cO()).a(Drawable.class, Drawable.class, new com.bumptech.glide.load.resource.b.f()).a(Bitmap.class, BitmapDrawable.class, new com.bumptech.glide.load.resource.d.b(resources)).a(Bitmap.class, byte[].class, aVar4).a(Drawable.class, byte[].class, new com.bumptech.glide.load.resource.d.c(dVar3, aVar4, dVar7)).a(com.bumptech.glide.load.resource.gif.b.class, byte[].class, dVar7);
        e eVar3 = new e(context3, bVar2, this.bq, new com.bumptech.glide.request.target.i(), fVar, map, engine, i);
        this.bp = eVar3;
    }

    @Nullable
    private static a K() {
        try {
            return (a) Class.forName("com.bumptech.glide.GeneratedAppGlideModuleImpl").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (ClassNotFoundException e) {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Failed to find GeneratedAppGlideModule. You should include an annotationProcessor compile dependency on com.github.bumptech.glide:compiler in your application and a @GlideModule annotated AppGlideModule implementation or LibraryGlideModules will be silently ignored");
            }
        } catch (InstantiationException e2) {
            a((Exception) e2);
        } catch (IllegalAccessException e3) {
            a((Exception) e3);
        } catch (NoSuchMethodException e4) {
            a((Exception) e4);
        } catch (InvocationTargetException e5) {
            a((Exception) e5);
        }
        return null;
    }

    @NonNull
    public static i a(@NonNull Activity activity) {
        return f(activity).c(activity);
    }

    @Deprecated
    @NonNull
    public static i a(@NonNull Fragment fragment) {
        return f(fragment.getActivity()).d(fragment);
    }

    @NonNull
    public static i a(@NonNull android.support.v4.app.Fragment fragment) {
        return f(fragment.getActivity()).b(fragment);
    }

    @NonNull
    public static i a(@NonNull FragmentActivity fragmentActivity) {
        return f(fragmentActivity).b(fragmentActivity);
    }

    @NonNull
    public static i a(@NonNull View view) {
        return f(view.getContext()).c(view);
    }

    @Nullable
    public static File a(@NonNull Context context, @NonNull String str) {
        File cacheDir = context.getCacheDir();
        if (cacheDir != null) {
            File file = new File(cacheDir, str);
            if (file.mkdirs() || (file.exists() && file.isDirectory())) {
                return file;
            }
            return null;
        }
        if (Log.isLoggable(TAG, 6)) {
            Log.e(TAG, "default disk cache dir is null");
        }
        return null;
    }

    private static void a(@NonNull Context context, @NonNull d dVar) {
        Context applicationContext = context.getApplicationContext();
        a K = K();
        List<com.bumptech.glide.c.c> emptyList = Collections.emptyList();
        if (K == null || K.dJ()) {
            emptyList = new com.bumptech.glide.c.e(applicationContext).dK();
        }
        if (K != null && !K.H().isEmpty()) {
            Set<Class<?>> H = K.H();
            Iterator<com.bumptech.glide.c.c> it = emptyList.iterator();
            while (it.hasNext()) {
                com.bumptech.glide.c.c next = it.next();
                if (H.contains(next.getClass())) {
                    if (Log.isLoggable(TAG, 3)) {
                        Log.d(TAG, "AppGlideModule excludes manifest GlideModule: " + next);
                    }
                    it.remove();
                }
            }
        }
        if (Log.isLoggable(TAG, 3)) {
            Iterator<com.bumptech.glide.c.c> it2 = emptyList.iterator();
            while (it2.hasNext()) {
                Log.d(TAG, "Discovered GlideModule from manifest: " + it2.next().getClass());
            }
        }
        dVar.a(K != null ? K.I() : null);
        for (com.bumptech.glide.c.c b2 : emptyList) {
            b2.b(applicationContext, dVar);
        }
        if (K != null) {
            K.b(applicationContext, dVar);
        }
        c h = dVar.h(applicationContext);
        for (com.bumptech.glide.c.c a2 : emptyList) {
            a2.a(applicationContext, h, h.bq);
        }
        if (K != null) {
            K.a(applicationContext, h, h.bq);
        }
        applicationContext.registerComponentCallbacks(h);
        bj = h;
    }

    private static void a(Exception exc) {
        throw new IllegalStateException("GeneratedAppGlideModuleImpl is implemented incorrectly. If you've manually implemented this class, remove your implementation. The Annotation processor will generate a correct implementation.", exc);
    }

    @Nullable
    public static File b(@NonNull Context context) {
        return a(context, "image_manager_disk_cache");
    }

    @NonNull
    public static c c(@NonNull Context context) {
        if (bj == null) {
            synchronized (c.class) {
                if (bj == null) {
                    d(context);
                }
            }
        }
        return bj;
    }

    private static void d(@NonNull Context context) {
        if (!bk) {
            bk = true;
            e(context);
            bk = false;
            return;
        }
        throw new IllegalStateException("You cannot call Glide.get() in registerComponents(), use the provided Glide instance instead");
    }

    private static void e(@NonNull Context context) {
        a(context, new d());
    }

    @NonNull
    private static l f(@Nullable Context context) {
        com.bumptech.glide.util.i.a(context, "You cannot start a load on a not yet attached View or a Fragment where getActivity() returns null (which usually occurs when getActivity() is called before the Fragment is attached or after the Fragment is destroyed).");
        return c(context).R();
    }

    @NonNull
    public static i g(@NonNull Context context) {
        return f(context).j(context);
    }

    @VisibleForTesting
    public static synchronized void init(@NonNull Context context, @NonNull d dVar) {
        synchronized (c.class) {
            if (bj != null) {
                tearDown();
            }
            a(context, dVar);
        }
    }

    @VisibleForTesting
    @Deprecated
    public static synchronized void init(c cVar) {
        synchronized (c.class) {
            if (bj != null) {
                tearDown();
            }
            bj = cVar;
        }
    }

    @VisibleForTesting
    public static synchronized void tearDown() {
        synchronized (c.class) {
            if (bj != null) {
                bj.getContext().getApplicationContext().unregisterComponentCallbacks(bj);
                bj.bl.shutdown();
            }
            bj = null;
        }
    }

    @NonNull
    public com.bumptech.glide.load.engine.bitmap_recycle.d L() {
        return this.bm;
    }

    @NonNull
    public com.bumptech.glide.load.engine.bitmap_recycle.b M() {
        return this.br;
    }

    /* access modifiers changed from: package-private */
    public com.bumptech.glide.manager.d N() {
        return this.bt;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public e O() {
        return this.bp;
    }

    public void P() {
        com.bumptech.glide.util.k.fo();
        this.bn.P();
        this.bm.P();
        this.br.P();
    }

    public void Q() {
        com.bumptech.glide.util.k.fp();
        this.bl.Q();
    }

    @NonNull
    public l R() {
        return this.bs;
    }

    @NonNull
    public Registry S() {
        return this.bq;
    }

    @NonNull
    public MemoryCategory a(@NonNull MemoryCategory memoryCategory) {
        com.bumptech.glide.util.k.fo();
        this.bn.b(memoryCategory.W());
        this.bm.b(memoryCategory.W());
        MemoryCategory memoryCategory2 = this.bv;
        this.bv = memoryCategory;
        return memoryCategory2;
    }

    /* access modifiers changed from: package-private */
    public void a(i iVar) {
        synchronized (this.bu) {
            if (!this.bu.contains(iVar)) {
                this.bu.add(iVar);
            } else {
                throw new IllegalStateException("Cannot register already registered manager");
            }
        }
    }

    public void a(@NonNull c.a... aVarArr) {
        this.bo.b(aVarArr);
    }

    /* access modifiers changed from: package-private */
    public boolean a(@NonNull com.bumptech.glide.request.target.n<?> nVar) {
        synchronized (this.bu) {
            for (i f : this.bu) {
                if (f.f(nVar)) {
                    return true;
                }
            }
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public void b(i iVar) {
        synchronized (this.bu) {
            if (this.bu.contains(iVar)) {
                this.bu.remove(iVar);
            } else {
                throw new IllegalStateException("Cannot unregister not yet registered manager");
            }
        }
    }

    @NonNull
    public Context getContext() {
        return this.bp.getBaseContext();
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    public void onLowMemory() {
        P();
    }

    public void onTrimMemory(int i) {
        trimMemory(i);
    }

    public void trimMemory(int i) {
        com.bumptech.glide.util.k.fo();
        this.bn.trimMemory(i);
        this.bm.trimMemory(i);
        this.br.trimMemory(i);
    }
}
