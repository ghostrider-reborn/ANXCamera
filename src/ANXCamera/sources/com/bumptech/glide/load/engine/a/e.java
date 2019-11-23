package com.bumptech.glide.load.engine.a;

import android.util.Log;
import com.bumptech.glide.a.a;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.a.a;
import java.io.File;
import java.io.IOException;

/* compiled from: DiskLruCacheWrapper */
public class e implements a {
    private static final String TAG = "DiskLruCacheWrapper";
    private static final int ji = 1;
    private static final int jj = 1;
    private static e jk;
    private final File directory;
    private final m jl;
    private final c jm = new c();
    private a jn;
    private final long maxSize;

    @Deprecated
    protected e(File file, long j) {
        this.directory = file;
        this.maxSize = j;
        this.jl = new m();
    }

    @Deprecated
    public static synchronized a a(File file, long j) {
        e eVar;
        synchronized (e.class) {
            if (jk == null) {
                jk = new e(file, j);
            }
            eVar = jk;
        }
        return eVar;
    }

    public static a b(File file, long j) {
        return new e(file, j);
    }

    private synchronized a ce() throws IOException {
        if (this.jn == null) {
            this.jn = a.a(this.directory, 1, 1, this.maxSize);
        }
        return this.jn;
    }

    private synchronized void cf() {
        this.jn = null;
    }

    public void a(c cVar, a.b bVar) {
        a.b q;
        String h = this.jl.h(cVar);
        this.jm.u(h);
        try {
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "Put: Obtained: " + h + " for for Key: " + cVar);
            }
            try {
                com.bumptech.glide.a.a ce = ce();
                if (ce.o(h) == null) {
                    q = ce.q(h);
                    if (q != null) {
                        if (bVar.f(q.m(0))) {
                            q.commit();
                        }
                        q.abortUnlessCommitted();
                        this.jm.x(h);
                        return;
                    }
                    throw new IllegalStateException("Had two simultaneous puts for: " + h);
                }
            } catch (IOException e) {
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "Unable to put to disk cache", e);
                }
            } catch (Throwable th) {
                q.abortUnlessCommitted();
                throw th;
            }
        } finally {
            this.jm.x(h);
        }
    }

    public synchronized void clear() {
        try {
            ce().delete();
        } catch (IOException e) {
            try {
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "Unable to clear disk cache or disk cache cleared externally", e);
                }
            } catch (Throwable th) {
                cf();
                throw th;
            }
        }
        cf();
    }

    public File e(c cVar) {
        String h = this.jl.h(cVar);
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "Get: Obtained: " + h + " for for Key: " + cVar);
        }
        try {
            a.d o = ce().o(h);
            if (o != null) {
                return o.m(0);
            }
            return null;
        } catch (IOException e) {
            if (!Log.isLoggable(TAG, 5)) {
                return null;
            }
            Log.w(TAG, "Unable to get from disk cache", e);
            return null;
        }
    }

    public void f(c cVar) {
        try {
            ce().remove(this.jl.h(cVar));
        } catch (IOException e) {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Unable to delete from disk cache", e);
            }
        }
    }
}
