package com.bumptech.glide.load.engine.a;

import android.util.Log;
import com.bumptech.glide.a.b;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.a.a;
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
        b.C0003b edit;
        String f2 = this.Hg.f(cVar);
        this.Ig.r(f2);
        try {
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "Put: Obtained: " + f2 + " for for Key: " + cVar);
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
                    throw new IllegalStateException("Had two simultaneous puts for: " + f2);
                }
            } catch (IOException e2) {
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "Unable to put to disk cache", e2);
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
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "Get: Obtained: " + f2 + " for for Key: " + cVar);
        }
        try {
            b.d dVar = n().get(f2);
            if (dVar != null) {
                return dVar.o(0);
            }
            return null;
        } catch (IOException e2) {
            if (!Log.isLoggable(TAG, 5)) {
                return null;
            }
            Log.w(TAG, "Unable to get from disk cache", e2);
            return null;
        }
    }

    public void c(c cVar) {
        try {
            n().remove(this.Hg.f(cVar));
        } catch (IOException e2) {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Unable to delete from disk cache", e2);
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
