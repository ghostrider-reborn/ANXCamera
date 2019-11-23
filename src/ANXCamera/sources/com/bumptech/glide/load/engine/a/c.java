package com.bumptech.glide.load.engine.a;

import com.bumptech.glide.util.i;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: DiskCacheWriteLocker */
final class c {
    private final Map<String, a> iZ = new HashMap();
    private final b ja = new b();

    /* compiled from: DiskCacheWriteLocker */
    private static class a {
        int jb;
        final Lock lock = new ReentrantLock();

        a() {
        }
    }

    /* compiled from: DiskCacheWriteLocker */
    private static class b {
        private static final int jc = 10;
        private final Queue<a> jd = new ArrayDeque();

        b() {
        }

        /* access modifiers changed from: package-private */
        public void a(a aVar) {
            synchronized (this.jd) {
                if (this.jd.size() < 10) {
                    this.jd.offer(aVar);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public a cc() {
            a poll;
            synchronized (this.jd) {
                poll = this.jd.poll();
            }
            return poll == null ? new a() : poll;
        }
    }

    c() {
    }

    /* access modifiers changed from: package-private */
    public void u(String str) {
        a aVar;
        synchronized (this) {
            aVar = this.iZ.get(str);
            if (aVar == null) {
                aVar = this.ja.cc();
                this.iZ.put(str, aVar);
            }
            aVar.jb++;
        }
        aVar.lock.lock();
    }

    /* access modifiers changed from: package-private */
    public void x(String str) {
        a aVar;
        synchronized (this) {
            aVar = (a) i.checkNotNull(this.iZ.get(str));
            if (aVar.jb >= 1) {
                aVar.jb--;
                if (aVar.jb == 0) {
                    a remove = this.iZ.remove(str);
                    if (remove.equals(aVar)) {
                        this.ja.a(remove);
                    } else {
                        throw new IllegalStateException("Removed the wrong lock, expected to remove: " + aVar + ", but actually removed: " + remove + ", safeKey: " + str);
                    }
                }
            } else {
                throw new IllegalStateException("Cannot release a lock that is not held, safeKey: " + str + ", interestedThreads: " + aVar.jb);
            }
        }
        aVar.lock.unlock();
    }
}
