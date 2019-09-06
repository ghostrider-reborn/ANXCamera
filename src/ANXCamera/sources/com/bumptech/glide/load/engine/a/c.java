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
    private final Map<String, a> Bg = new HashMap();
    private final b Cg = new b();

    /* compiled from: DiskCacheWriteLocker */
    private static class a {
        final Lock lock = new ReentrantLock();
        int zg;

        a() {
        }
    }

    /* compiled from: DiskCacheWriteLocker */
    private static class b {
        private static final int Ag = 10;
        private final Queue<a> pool = new ArrayDeque();

        b() {
        }

        /* access modifiers changed from: 0000 */
        public void a(a aVar) {
            synchronized (this.pool) {
                if (this.pool.size() < 10) {
                    this.pool.offer(aVar);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public a obtain() {
            a aVar;
            synchronized (this.pool) {
                aVar = (a) this.pool.poll();
            }
            return aVar == null ? new a() : aVar;
        }
    }

    c() {
    }

    /* access modifiers changed from: 0000 */
    public void r(String str) {
        a aVar;
        synchronized (this) {
            aVar = (a) this.Bg.get(str);
            if (aVar == null) {
                aVar = this.Cg.obtain();
                this.Bg.put(str, aVar);
            }
            aVar.zg++;
        }
        aVar.lock.lock();
    }

    /* access modifiers changed from: 0000 */
    public void s(String str) {
        a aVar;
        synchronized (this) {
            Object obj = this.Bg.get(str);
            i.checkNotNull(obj);
            aVar = (a) obj;
            if (aVar.zg >= 1) {
                aVar.zg--;
                if (aVar.zg == 0) {
                    a aVar2 = (a) this.Bg.remove(str);
                    if (aVar2.equals(aVar)) {
                        this.Cg.a(aVar2);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Removed the wrong lock, expected to remove: ");
                        sb.append(aVar);
                        sb.append(", but actually removed: ");
                        sb.append(aVar2);
                        sb.append(", safeKey: ");
                        sb.append(str);
                        throw new IllegalStateException(sb.toString());
                    }
                }
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Cannot release a lock that is not held, safeKey: ");
                sb2.append(str);
                sb2.append(", interestedThreads: ");
                sb2.append(aVar.zg);
                throw new IllegalStateException(sb2.toString());
            }
        }
        aVar.lock.unlock();
    }
}
