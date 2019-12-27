package com.bumptech.glide;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.AbsListView;
import com.bumptech.glide.request.target.n;
import com.bumptech.glide.request.target.o;
import com.bumptech.glide.util.l;
import java.util.List;
import java.util.Queue;

/* compiled from: ListPreloader */
public class f<T> implements AbsListView.OnScrollListener {
    private final int Tb;
    private final d Ub;
    private final a<T> Vb;
    private final b<T> Wb;
    private int Xb;
    private int Yb;
    private int Zb = -1;
    private int _b;
    private boolean ac = true;
    private final m da;

    /* compiled from: ListPreloader */
    public interface a<U> {
        @NonNull
        List<U> e(int i);

        @Nullable
        j<?> f(@NonNull U u);
    }

    /* compiled from: ListPreloader */
    public interface b<T> {
        @Nullable
        int[] a(@NonNull T t, int i, int i2);
    }

    /* compiled from: ListPreloader */
    private static final class c extends com.bumptech.glide.request.target.b<Object> {
        int vl;
        int wl;

        c() {
        }

        public void a(@NonNull n nVar) {
        }

        public void a(@NonNull Object obj, @Nullable com.bumptech.glide.request.a.f<? super Object> fVar) {
        }

        public void b(@NonNull n nVar) {
            nVar.b(this.wl, this.vl);
        }
    }

    /* compiled from: ListPreloader */
    private static final class d {
        private final Queue<c> queue;

        d(int i) {
            this.queue = l.createQueue(i);
            for (int i2 = 0; i2 < i; i2++) {
                this.queue.offer(new c());
            }
        }

        public c f(int i, int i2) {
            c poll = this.queue.poll();
            this.queue.offer(poll);
            poll.wl = i;
            poll.vl = i2;
            return poll;
        }
    }

    public f(@NonNull m mVar, @NonNull a<T> aVar, @NonNull b<T> bVar, int i) {
        this.da = mVar;
        this.Vb = aVar;
        this.Wb = bVar;
        this.Tb = i;
        this.Ub = new d(i + 1);
    }

    private void a(int i, boolean z) {
        if (this.ac != z) {
            this.ac = z;
            cancelAll();
        }
        i(i, (z ? this.Tb : -this.Tb) + i);
    }

    private void a(List<T> list, int i, boolean z) {
        int size = list.size();
        if (z) {
            for (int i2 = 0; i2 < size; i2++) {
                c(list.get(i2), i, i2);
            }
            return;
        }
        for (int i3 = size - 1; i3 >= 0; i3--) {
            c(list.get(i3), i, i3);
        }
    }

    private void c(@Nullable T t, int i, int i2) {
        if (t != null) {
            int[] a2 = this.Wb.a(t, i, i2);
            if (a2 != null) {
                j<?> f2 = this.Vb.f(t);
                if (f2 != null) {
                    f2.c(this.Ub.f(a2[0], a2[1]));
                }
            }
        }
    }

    private void cancelAll() {
        for (int i = 0; i < this.Tb; i++) {
            this.da.d((o<?>) this.Ub.f(0, 0));
        }
    }

    private void i(int i, int i2) {
        int i3;
        int i4;
        if (i < i2) {
            i3 = Math.max(this.Xb, i);
            i4 = i2;
        } else {
            i4 = Math.min(this.Yb, i);
            i3 = i2;
        }
        int min = Math.min(this._b, i4);
        int min2 = Math.min(this._b, Math.max(0, i3));
        if (i < i2) {
            for (int i5 = min2; i5 < min; i5++) {
                a(this.Vb.e(i5), i5, true);
            }
        } else {
            for (int i6 = min - 1; i6 >= min2; i6--) {
                a(this.Vb.e(i6), i6, false);
            }
        }
        this.Yb = min2;
        this.Xb = min;
    }

    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        this._b = i3;
        int i4 = this.Zb;
        if (i > i4) {
            a(i2 + i, true);
        } else if (i < i4) {
            a(i, false);
        }
        this.Zb = i;
    }

    public void onScrollStateChanged(AbsListView absListView, int i) {
    }
}
