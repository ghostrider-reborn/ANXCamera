package com.bumptech.glide.load.engine.a;

import android.util.Log;
import com.bumptech.glide.a.b;
import com.bumptech.glide.a.b.C0003b;
import com.bumptech.glide.a.b.d;
import com.bumptech.glide.load.c;
import java.io.File;
import java.io.IOException;

/* compiled from: DiskLruCacheWrapper */
public class g implements a {
    private static final int APP_VERSION = 1;
    private static final int Kg = 1;
    private static g Lg = null;
    private static final String TAG = "DiskLruCacheWrapper";
    private final s Hg;
    private final c Ig = new c();
    private b Jg;
    private final File directory;
    private final long maxSize;

    @Deprecated
    protected g(File file, long j) {
        this.directory = file;
        this.maxSize = j;
        this.Hg = new s();
    }

    @Deprecated
    public static synchronized a a(File file, long j) {
        g gVar;
        synchronized (g.class) {
            if (Lg == null) {
                Lg = new g(file, j);
            }
            gVar = Lg;
        }
        return gVar;
    }

    public static a create(File file, long j) {
        return new g(file, j);
    }

    private synchronized b n() throws IOException {
        if (this.Jg == null) {
            this.Jg = b.a(this.directory, 1, 1, this.maxSize);
        }
        return this.Jg;
    }

    private synchronized void uk() {
        this.Jg = null;
    }

    public void a(c cVar, a.b bVar) {
        C0003b edit;
        String str = TAG;
        String f2 = this.Hg.f(cVar);
        this.Ig.r(f2);
        try {
            if (Log.isLoggable(str, 2)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Put: Obtained: ");
                sb.append(f2);
                sb.append(" for for Key: ");
                sb.append(cVar);
                Log.v(str, sb.toString());
            }
            try {
                b n = n();
                if (n.get(f2) == null) {
                    edit = n.edit(f2);
                    if (edit != null) {
                        if (bVar.c(edit.o(0))) {
                            edit.commit();
                        }
                        edit.abortUnlessCommitted();
                        this.Ig.s(f2);
                        return;
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Had two simultaneous puts for: ");
                    sb2.append(f2);
                    throw new IllegalStateException(sb2.toString());
                }
            } catch (IOException e2) {
                if (Log.isLoggable(str, 5)) {
                    Log.w(str, "Unable to put to disk cache", e2);
                }
            } catch (Throwable th) {
                edit.abortUnlessCommitted();
                throw th;
            }
        } finally {
            this.Ig.s(f2);
        }
    }

    public File b(c cVar) {
        String f2 = this.Hg.f(cVar);
        String str = TAG;
        if (Log.isLoggable(str, 2)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Get: Obtained: ");
            sb.append(f2);
            sb.append(" for for Key: ");
            sb.append(cVar);
            Log.v(str, sb.toString());
        }
        try {
            d dVar = n().get(f2);
            if (dVar != null) {
                return dVar.o(0);
            }
            return null;
        } catch (IOException e2) {
            if (!Log.isLoggable(str, 5)) {
                return null;
            }
            Log.w(str, "Unable to get from disk cache", e2);
            return null;
        }
    }

    public void c(c cVar) {
        try {
            n().remove(this.Hg.f(cVar));
        } catch (IOException e2) {
            String str = TAG;
            if (Log.isLoggable(str, 5)) {
                Log.w(str, "Unable to delete from disk cache", e2);
            }
        }
    }

    public synchronized void clear() {
        try {
            n().delete();
        } catch (IOException e2) {
            try {
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "Unable to clear disk cache or disk cache cleared externally", e2);
                }
            } catch (Throwable th) {
                uk();
                throw th;
            }
        }
        uk();
    }
}
