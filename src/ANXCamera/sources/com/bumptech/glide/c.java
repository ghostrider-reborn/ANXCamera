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
import com.bumptech.glide.load.a.l;
import com.bumptech.glide.load.b.a.a.C0006a;
import com.bumptech.glide.load.b.b.e;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.a.o;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.engine.prefill.a;
import com.bumptech.glide.load.h;
import com.bumptech.glide.load.model.A;
import com.bumptech.glide.load.model.B;
import com.bumptech.glide.load.model.C;
import com.bumptech.glide.load.model.C0100a;
import com.bumptech.glide.load.model.C0102c;
import com.bumptech.glide.load.model.D;
import com.bumptech.glide.load.model.q;
import com.bumptech.glide.load.model.u;
import com.bumptech.glide.load.model.y;
import com.bumptech.glide.load.model.z;
import com.bumptech.glide.load.resource.bitmap.C0104a;
import com.bumptech.glide.load.resource.bitmap.C0105b;
import com.bumptech.glide.load.resource.bitmap.VideoDecoder;
import com.bumptech.glide.load.resource.bitmap.i;
import com.bumptech.glide.load.resource.bitmap.m;
import com.bumptech.glide.load.resource.bitmap.v;
import com.bumptech.glide.load.resource.bitmap.x;
import com.bumptech.glide.load.resource.gif.ByteBufferGifDecoder;
import com.bumptech.glide.manager.n;
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
    private static final String Jb = "image_manager_disk_cache";
    private static volatile c Kb = null;
    private static volatile boolean Lb = false;
    private static final String TAG = "Glide";
    private final d Bb;
    private final o Cb;
    private final a Db;
    private final e Eb;
    private final n Fb;
    private final com.bumptech.glide.manager.d Gb;
    private final List<m> Hb = new ArrayList();
    private MemoryCategory Ib = MemoryCategory.NORMAL;
    private final b qa;
    private final Registry registry;
    private final Engine va;

    c(@NonNull Context context, @NonNull Engine engine, @NonNull o oVar, @NonNull d dVar, @NonNull b bVar, @NonNull n nVar, @NonNull com.bumptech.glide.manager.d dVar2, int i, @NonNull f fVar, @NonNull Map<Class<?>, n<?, ?>> map) {
        Context context2 = context;
        o oVar2 = oVar;
        d dVar3 = dVar;
        b bVar2 = bVar;
        this.va = engine;
        this.Bb = dVar3;
        this.qa = bVar2;
        this.Cb = oVar2;
        this.Fb = nVar;
        this.Gb = dVar2;
        this.Db = new a(oVar2, dVar3, (DecodeFormat) fVar.getOptions().a(com.bumptech.glide.load.resource.bitmap.o.Yi));
        Resources resources = context.getResources();
        this.registry = new Registry();
        this.registry.a((ImageHeaderParser) new m());
        com.bumptech.glide.load.resource.bitmap.o oVar3 = new com.bumptech.glide.load.resource.bitmap.o(this.registry.Gf(), resources.getDisplayMetrics(), dVar3, bVar2);
        ByteBufferGifDecoder byteBufferGifDecoder = new ByteBufferGifDecoder(context2, this.registry.Gf(), dVar3, bVar2);
        h c2 = VideoDecoder.c(dVar);
        i iVar = new i(oVar3);
        x xVar = new x(oVar3, bVar2);
        e eVar = new e(context2);
        com.bumptech.glide.load.model.y.c cVar = new com.bumptech.glide.load.model.y.c(resources);
        y.d dVar4 = new y.d(resources);
        y.b bVar3 = new y.b(resources);
        y.a aVar = new y.a(resources);
        com.bumptech.glide.load.resource.bitmap.e eVar2 = new com.bumptech.glide.load.resource.bitmap.e(bVar2);
        com.bumptech.glide.load.b.d.a aVar2 = new com.bumptech.glide.load.b.d.a();
        com.bumptech.glide.load.b.d.d dVar5 = new com.bumptech.glide.load.b.d.d();
        ContentResolver contentResolver = context.getContentResolver();
        y.a aVar3 = aVar;
        y.d dVar6 = dVar4;
        String str = Registry.nc;
        y.b bVar4 = bVar3;
        C0104a aVar4 = new C0104a(resources, (h<DataType, Bitmap>) iVar);
        String str2 = Registry.oc;
        com.bumptech.glide.load.resource.gif.h hVar = new com.bumptech.glide.load.resource.gif.h(this.registry.Gf(), byteBufferGifDecoder, bVar2);
        String str3 = Registry.mc;
        y.b bVar5 = bVar4;
        y.d dVar7 = dVar6;
        y.a aVar5 = aVar3;
        Context context3 = context;
        ContentResolver contentResolver2 = contentResolver;
        com.bumptech.glide.load.b.d.a aVar6 = aVar2;
        com.bumptech.glide.load.b.d.d dVar8 = dVar5;
        this.registry.a(ByteBuffer.class, (com.bumptech.glide.load.a<Data>) new com.bumptech.glide.load.model.e<Data>()).a(InputStream.class, (com.bumptech.glide.load.a<Data>) new z<Data>(bVar2)).a(str, ByteBuffer.class, Bitmap.class, iVar).a(str, InputStream.class, Bitmap.class, xVar).a(str, ParcelFileDescriptor.class, Bitmap.class, c2).a(str, AssetFileDescriptor.class, Bitmap.class, VideoDecoder.b(dVar)).a(Bitmap.class, Bitmap.class, (u<Model, Data>) B.a.getInstance()).a(str, Bitmap.class, Bitmap.class, new com.bumptech.glide.load.resource.bitmap.z()).a(Bitmap.class, (com.bumptech.glide.load.i<TResource>) eVar2).a(str2, ByteBuffer.class, BitmapDrawable.class, aVar4).a(str2, InputStream.class, BitmapDrawable.class, new C0104a(resources, (h<DataType, Bitmap>) xVar)).a(str2, ParcelFileDescriptor.class, BitmapDrawable.class, new C0104a(resources, c2)).a(BitmapDrawable.class, (com.bumptech.glide.load.i<TResource>) new C0105b<TResource>(dVar3, eVar2)).a(str3, InputStream.class, com.bumptech.glide.load.resource.gif.b.class, hVar).a(str3, ByteBuffer.class, com.bumptech.glide.load.resource.gif.b.class, byteBufferGifDecoder).a(com.bumptech.glide.load.resource.gif.b.class, (com.bumptech.glide.load.i<TResource>) new com.bumptech.glide.load.resource.gif.c<TResource>()).a(com.bumptech.glide.b.a.class, com.bumptech.glide.b.a.class, (u<Model, Data>) B.a.getInstance()).a(str, com.bumptech.glide.b.a.class, Bitmap.class, new com.bumptech.glide.load.resource.gif.f(dVar3)).a(Uri.class, Drawable.class, (h<Data, TResource>) eVar).a(Uri.class, Bitmap.class, (h<Data, TResource>) new v<Data,TResource>(eVar, dVar3)).a((com.bumptech.glide.load.a.e.a<?>) new C0006a<Object>()).a(File.class, ByteBuffer.class, (u<Model, Data>) new com.bumptech.glide.load.model.f.b<Model,Data>()).a(File.class, InputStream.class, (u<Model, Data>) new com.bumptech.glide.load.model.i.e<Model,Data>()).a(File.class, File.class, (h<Data, TResource>) new com.bumptech.glide.load.b.c.a<Data,TResource>()).a(File.class, ParcelFileDescriptor.class, (u<Model, Data>) new com.bumptech.glide.load.model.i.b<Model,Data>()).a(File.class, File.class, (u<Model, Data>) B.a.getInstance()).a((com.bumptech.glide.load.a.e.a<?>) new l.a<Object>(bVar2)).a(Integer.TYPE, InputStream.class, (u<Model, Data>) cVar).a(Integer.TYPE, ParcelFileDescriptor.class, (u<Model, Data>) bVar5).a(Integer.class, InputStream.class, (u<Model, Data>) cVar).a(Integer.class, ParcelFileDescriptor.class, (u<Model, Data>) bVar5).a(Integer.class, Uri.class, (u<Model, Data>) dVar7).a(Integer.TYPE, AssetFileDescriptor.class, (u<Model, Data>) aVar5).a(Integer.class, AssetFileDescriptor.class, (u<Model, Data>) aVar5).a(Integer.TYPE, Uri.class, (u<Model, Data>) dVar7).a(String.class, InputStream.class, (u<Model, Data>) new com.bumptech.glide.load.model.g.c<Model,Data>()).a(Uri.class, InputStream.class, (u<Model, Data>) new com.bumptech.glide.load.model.g.c<Model,Data>()).a(String.class, InputStream.class, (u<Model, Data>) new com.bumptech.glide.load.model.A.c<Model,Data>()).a(String.class, ParcelFileDescriptor.class, (u<Model, Data>) new A.b<Model,Data>()).a(String.class, AssetFileDescriptor.class, (u<Model, Data>) new A.a<Model,Data>()).a(Uri.class, InputStream.class, (u<Model, Data>) new com.bumptech.glide.load.model.a.c.a<Model,Data>()).a(Uri.class, InputStream.class, (u<Model, Data>) new com.bumptech.glide.load.model.C0100a.c<Model,Data>(context.getAssets())).a(Uri.class, ParcelFileDescriptor.class, (u<Model, Data>) new C0100a.b<Model,Data>(context.getAssets())).a(Uri.class, InputStream.class, (u<Model, Data>) new com.bumptech.glide.load.model.a.d.a<Model,Data>(context3)).a(Uri.class, InputStream.class, (u<Model, Data>) new com.bumptech.glide.load.model.a.e.a<Model,Data>(context3)).a(Uri.class, InputStream.class, (u<Model, Data>) new C.d<Model,Data>(contentResolver2)).a(Uri.class, ParcelFileDescriptor.class, (u<Model, Data>) new C.b<Model,Data>(contentResolver2)).a(Uri.class, AssetFileDescriptor.class, (u<Model, Data>) new C.a<Model,Data>(contentResolver2)).a(Uri.class, InputStream.class, (u<Model, Data>) new D.a<Model,Data>()).a(URL.class, InputStream.class, (u<Model, Data>) new com.bumptech.glide.load.model.a.f.a<Model,Data>()).a(Uri.class, File.class, (u<Model, Data>) new q.a<Model,Data>(context3)).a(com.bumptech.glide.load.model.l.class, InputStream.class, (u<Model, Data>) new com.bumptech.glide.load.model.a.b.a<Model,Data>()).a(byte[].class, ByteBuffer.class, (u<Model, Data>) new C0102c.a<Model,Data>()).a(byte[].class, InputStream.class, (u<Model, Data>) new C0102c.d<Model,Data>()).a(Uri.class, Uri.class, (u<Model, Data>) B.a.getInstance()).a(Drawable.class, Drawable.class, (u<Model, Data>) B.a.getInstance()).a(Drawable.class, Drawable.class, (h<Data, TResource>) new com.bumptech.glide.load.b.b.f<Data,TResource>()).a(Bitmap.class, BitmapDrawable.class, (com.bumptech.glide.load.b.d.e<TResource, Transcode>) new com.bumptech.glide.load.b.d.b<TResource,Transcode>(resources)).a(Bitmap.class, byte[].class, (com.bumptech.glide.load.b.d.e<TResource, Transcode>) aVar6).a(Drawable.class, byte[].class, (com.bumptech.glide.load.b.d.e<TResource, Transcode>) new com.bumptech.glide.load.b.d.c<TResource,Transcode>(dVar3, aVar6, dVar8)).a(com.bumptech.glide.load.resource.gif.b.class, byte[].class, (com.bumptech.glide.load.b.d.e<TResource, Transcode>) dVar8);
        e eVar3 = new e(context, bVar, this.registry, new com.bumptech.glide.request.target.i(), fVar, map, engine, i);
        this.Eb = eVar3;
    }

    @Nullable
    private static a Wj() {
        try {
            return (a) Class.forName("com.bumptech.glide.GeneratedAppGlideModuleImpl").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (ClassNotFoundException unused) {
            String str = TAG;
            if (Log.isLoggable(str, 5)) {
                Log.w(str, "Failed to find GeneratedAppGlideModule. You should include an annotationProcessor compile dependency on com.github.bumptech.glide:compiler in your application and a @GlideModule annotated AppGlideModule implementation or LibraryGlideModules will be silently ignored");
            }
            return null;
        } catch (InstantiationException e2) {
            c((Exception) e2);
            throw null;
        } catch (IllegalAccessException e3) {
            c((Exception) e3);
            throw null;
        } catch (NoSuchMethodException e4) {
            c((Exception) e4);
            throw null;
        } catch (InvocationTargetException e5) {
            c((Exception) e5);
            throw null;
        }
    }

    @NonNull
    public static m a(@NonNull FragmentActivity fragmentActivity) {
        return k(fragmentActivity).b(fragmentActivity);
    }

    @Deprecated
    @NonNull
    public static m b(@NonNull Fragment fragment) {
        return k(fragment.getActivity()).c(fragment);
    }

    @NonNull
    public static m b(@NonNull android.support.v4.app.Fragment fragment) {
        return k(fragment.getActivity()).c(fragment);
    }

    private static void b(@NonNull Context context, @NonNull d dVar) {
        Context applicationContext = context.getApplicationContext();
        a Wj = Wj();
        List<com.bumptech.glide.c.c> emptyList = Collections.emptyList();
        if (Wj == null || Wj.Kg()) {
            emptyList = new com.bumptech.glide.c.e(applicationContext).parse();
        }
        String str = TAG;
        if (Wj != null && !Wj.Lg().isEmpty()) {
            Set Lg = Wj.Lg();
            Iterator it = emptyList.iterator();
            while (it.hasNext()) {
                com.bumptech.glide.c.c cVar = (com.bumptech.glide.c.c) it.next();
                if (Lg.contains(cVar.getClass())) {
                    if (Log.isLoggable(str, 3)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("AppGlideModule excludes manifest GlideModule: ");
                        sb.append(cVar);
                        Log.d(str, sb.toString());
                    }
                    it.remove();
                }
            }
        }
        if (Log.isLoggable(str, 3)) {
            for (com.bumptech.glide.c.c cVar2 : emptyList) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Discovered GlideModule from manifest: ");
                sb2.append(cVar2.getClass());
                Log.d(str, sb2.toString());
            }
        }
        dVar.a(Wj != null ? Wj.Mg() : null);
        for (com.bumptech.glide.c.c a2 : emptyList) {
            a2.a(applicationContext, dVar);
        }
        if (Wj != null) {
            Wj.a(applicationContext, dVar);
        }
        c g = dVar.g(applicationContext);
        for (com.bumptech.glide.c.c a3 : emptyList) {
            a3.a(applicationContext, g, g.registry);
        }
        if (Wj != null) {
            Wj.a(applicationContext, g, g.registry);
        }
        applicationContext.registerComponentCallbacks(g);
        Kb = g;
    }

    private static void c(Exception exc) {
        throw new IllegalStateException("GeneratedAppGlideModuleImpl is implemented incorrectly. If you've manually implemented this class, remove your implementation. The Annotation processor will generate a correct implementation.", exc);
    }

    @NonNull
    public static m d(@NonNull Activity activity) {
        return k(activity).get(activity);
    }

    @NonNull
    public static m d(@NonNull View view) {
        return k(view.getContext()).get(view);
    }

    @Nullable
    public static File e(@NonNull Context context, @NonNull String str) {
        File cacheDir = context.getCacheDir();
        if (cacheDir != null) {
            File file = new File(cacheDir, str);
            if (file.mkdirs() || (file.exists() && file.isDirectory())) {
                return file;
            }
            return null;
        }
        String str2 = TAG;
        if (Log.isLoggable(str2, 6)) {
            Log.e(str2, "default disk cache dir is null");
        }
        return null;
    }

    @NonNull
    public static c get(@NonNull Context context) {
        if (Kb == null) {
            synchronized (c.class) {
                if (Kb == null) {
                    j(context);
                }
            }
        }
        return Kb;
    }

    @Nullable
    public static File h(@NonNull Context context) {
        return e(context, "image_manager_disk_cache");
    }

    @NonNull
    public static m i(@NonNull Context context) {
        return k(context).get(context);
    }

    @VisibleForTesting
    public static synchronized void init(@NonNull Context context, @NonNull d dVar) {
        synchronized (c.class) {
            if (Kb != null) {
                tearDown();
            }
            b(context, dVar);
        }
    }

    @VisibleForTesting
    @Deprecated
    public static synchronized void init(c cVar) {
        synchronized (c.class) {
            if (Kb != null) {
                tearDown();
            }
            Kb = cVar;
        }
    }

    private static void j(@NonNull Context context) {
        if (!Lb) {
            Lb = true;
            l(context);
            Lb = false;
            return;
        }
        throw new IllegalStateException("You cannot call Glide.get() in registerComponents(), use the provided Glide instance instead");
    }

    @NonNull
    private static n k(@Nullable Context context) {
        com.bumptech.glide.util.i.a(context, "You cannot start a load on a not yet attached View or a Fragment where getActivity() returns null (which usually occurs when getActivity() is called before the Fragment is attached or after the Fragment is destroyed).");
        return get(context).Ff();
    }

    private static void l(@NonNull Context context) {
        b(context, new d());
    }

    @VisibleForTesting
    public static synchronized void tearDown() {
        synchronized (c.class) {
            if (Kb != null) {
                Kb.getContext().getApplicationContext().unregisterComponentCallbacks(Kb);
                Kb.va.shutdown();
            }
            Kb = null;
        }
    }

    public void Bf() {
        com.bumptech.glide.util.l.Hh();
        this.va.Bf();
    }

    @NonNull
    public d Cf() {
        return this.Bb;
    }

    /* access modifiers changed from: 0000 */
    public com.bumptech.glide.manager.d Df() {
        return this.Gb;
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public e Ef() {
        return this.Eb;
    }

    @NonNull
    public n Ff() {
        return this.Fb;
    }

    public void G() {
        com.bumptech.glide.util.l.Ih();
        this.Cb.G();
        this.Bb.G();
        this.qa.G();
    }

    @NonNull
    public b V() {
        return this.qa;
    }

    @NonNull
    public MemoryCategory a(@NonNull MemoryCategory memoryCategory) {
        com.bumptech.glide.util.l.Ih();
        this.Cb.a(memoryCategory.getMultiplier());
        this.Bb.a(memoryCategory.getMultiplier());
        MemoryCategory memoryCategory2 = this.Ib;
        this.Ib = memoryCategory;
        return memoryCategory2;
    }

    public void a(@NonNull com.bumptech.glide.load.engine.prefill.c.a... aVarArr) {
        this.Db.b(aVarArr);
    }

    /* access modifiers changed from: 0000 */
    public boolean a(@NonNull com.bumptech.glide.request.target.o<?> oVar) {
        synchronized (this.Hb) {
            for (m e2 : this.Hb) {
                if (e2.e(oVar)) {
                    return true;
                }
            }
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(m mVar) {
        synchronized (this.Hb) {
            if (!this.Hb.contains(mVar)) {
                this.Hb.add(mVar);
            } else {
                throw new IllegalStateException("Cannot register already registered manager");
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void c(m mVar) {
        synchronized (this.Hb) {
            if (this.Hb.contains(mVar)) {
                this.Hb.remove(mVar);
            } else {
                throw new IllegalStateException("Cannot unregister not yet registered manager");
            }
        }
    }

    @NonNull
    public Context getContext() {
        return this.Eb.getBaseContext();
    }

    @NonNull
    public Registry getRegistry() {
        return this.registry;
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    public void onLowMemory() {
        G();
    }

    public void onTrimMemory(int i) {
        trimMemory(i);
    }

    public void trimMemory(int i) {
        com.bumptech.glide.util.l.Ih();
        this.Cb.trimMemory(i);
        this.Bb.trimMemory(i);
        this.qa.trimMemory(i);
    }
}
