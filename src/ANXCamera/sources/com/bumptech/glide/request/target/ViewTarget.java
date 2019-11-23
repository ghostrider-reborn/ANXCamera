package com.bumptech.glide.request.target;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import com.bumptech.glide.request.c;
import com.bumptech.glide.util.i;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ViewTarget<T extends View, Z> extends b<Z> {
    private static final String TAG = "ViewTarget";
    private static boolean qn;
    @Nullable
    private static Integer qo;
    private final SizeDeterminer qp;
    @Nullable
    private View.OnAttachStateChangeListener qq;
    private boolean qr;
    private boolean qt;
    protected final T view;

    @VisibleForTesting
    static final class SizeDeterminer {
        @Nullable
        @VisibleForTesting
        static Integer maxDisplayLength;
        private static final int qv = 0;
        private final List<m> hx = new ArrayList();
        boolean qw;
        @Nullable
        private a qx;
        private final View view;

        private static final class a implements ViewTreeObserver.OnPreDrawListener {
            private final WeakReference<SizeDeterminer> qy;

            a(@NonNull SizeDeterminer sizeDeterminer) {
                this.qy = new WeakReference<>(sizeDeterminer);
            }

            public boolean onPreDraw() {
                if (Log.isLoggable(ViewTarget.TAG, 2)) {
                    Log.v(ViewTarget.TAG, "OnGlobalLayoutListener called attachStateListener=" + this);
                }
                SizeDeterminer sizeDeterminer = (SizeDeterminer) this.qy.get();
                if (sizeDeterminer == null) {
                    return true;
                }
                sizeDeterminer.fc();
                return true;
            }
        }

        SizeDeterminer(@NonNull View view2) {
            this.view = view2;
        }

        private boolean Y(int i) {
            return i > 0 || i == Integer.MIN_VALUE;
        }

        private int b(int i, int i2, int i3) {
            int i4 = i2 - i3;
            if (i4 > 0) {
                return i4;
            }
            if (this.qw && this.view.isLayoutRequested()) {
                return 0;
            }
            int i5 = i - i3;
            if (i5 > 0) {
                return i5;
            }
            if (this.view.isLayoutRequested() || i2 != -2) {
                return 0;
            }
            if (Log.isLoggable(ViewTarget.TAG, 4)) {
                Log.i(ViewTarget.TAG, "Glide treats LayoutParams.WRAP_CONTENT as a request for an image the size of this device's screen dimensions. If you want to load the original image and are ok with the corresponding memory cost and OOMs (depending on the input size), use .override(Target.SIZE_ORIGINAL). Otherwise, use LayoutParams.MATCH_PARENT, set layout_width and layout_height to fixed dimension, or use .override() with fixed dimensions.");
            }
            return l(this.view.getContext());
        }

        private int fe() {
            int paddingTop = this.view.getPaddingTop() + this.view.getPaddingBottom();
            ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
            return b(this.view.getHeight(), layoutParams != null ? layoutParams.height : 0, paddingTop);
        }

        private int ff() {
            int paddingLeft = this.view.getPaddingLeft() + this.view.getPaddingRight();
            ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
            return b(this.view.getWidth(), layoutParams != null ? layoutParams.width : 0, paddingLeft);
        }

        private static int l(@NonNull Context context) {
            if (maxDisplayLength == null) {
                Display defaultDisplay = ((WindowManager) i.checkNotNull((WindowManager) context.getSystemService("window"))).getDefaultDisplay();
                Point point = new Point();
                defaultDisplay.getSize(point);
                maxDisplayLength = Integer.valueOf(Math.max(point.x, point.y));
            }
            return maxDisplayLength.intValue();
        }

        private void r(int i, int i2) {
            Iterator it = new ArrayList(this.hx).iterator();
            while (it.hasNext()) {
                ((m) it.next()).q(i, i2);
            }
        }

        private boolean s(int i, int i2) {
            return Y(i) && Y(i2);
        }

        /* access modifiers changed from: package-private */
        public void a(@NonNull m mVar) {
            int ff = ff();
            int fe = fe();
            if (s(ff, fe)) {
                mVar.q(ff, fe);
                return;
            }
            if (!this.hx.contains(mVar)) {
                this.hx.add(mVar);
            }
            if (this.qx == null) {
                ViewTreeObserver viewTreeObserver = this.view.getViewTreeObserver();
                this.qx = new a(this);
                viewTreeObserver.addOnPreDrawListener(this.qx);
            }
        }

        /* access modifiers changed from: package-private */
        public void b(@NonNull m mVar) {
            this.hx.remove(mVar);
        }

        /* access modifiers changed from: package-private */
        public void fc() {
            if (!this.hx.isEmpty()) {
                int ff = ff();
                int fe = fe();
                if (s(ff, fe)) {
                    r(ff, fe);
                    fd();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void fd() {
            ViewTreeObserver viewTreeObserver = this.view.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeOnPreDrawListener(this.qx);
            }
            this.qx = null;
            this.hx.clear();
        }
    }

    public ViewTarget(@NonNull T t) {
        this.view = (View) i.checkNotNull(t);
        this.qp = new SizeDeterminer(t);
    }

    @Deprecated
    public ViewTarget(@NonNull T t, boolean z) {
        this(t);
        if (z) {
            eZ();
        }
    }

    public static void X(int i) {
        if (qo != null || qn) {
            throw new IllegalArgumentException("You cannot set the tag id more than once or change the tag id after the first request has been made");
        }
        qo = Integer.valueOf(i);
    }

    private void fa() {
        if (this.qq != null && !this.qt) {
            this.view.addOnAttachStateChangeListener(this.qq);
            this.qt = true;
        }
    }

    private void fb() {
        if (this.qq != null && this.qt) {
            this.view.removeOnAttachStateChangeListener(this.qq);
            this.qt = false;
        }
    }

    @Nullable
    private Object getTag() {
        return qo == null ? this.view.getTag() : this.view.getTag(qo.intValue());
    }

    private void setTag(@Nullable Object obj) {
        if (qo == null) {
            qn = true;
            this.view.setTag(obj);
            return;
        }
        this.view.setTag(qo.intValue(), obj);
    }

    @CallSuper
    public void a(@NonNull m mVar) {
        this.qp.a(mVar);
    }

    @CallSuper
    public void b(@NonNull m mVar) {
        this.qp.b(mVar);
    }

    @CallSuper
    public void d(@Nullable Drawable drawable) {
        super.d(drawable);
        this.qp.fd();
        if (!this.qr) {
            fb();
        }
    }

    @Nullable
    public c dS() {
        Object tag = getTag();
        if (tag == null) {
            return null;
        }
        if (tag instanceof c) {
            return (c) tag;
        }
        throw new IllegalArgumentException("You must not call setTag() on a view Glide is targeting");
    }

    @CallSuper
    public void e(@Nullable Drawable drawable) {
        super.e(drawable);
        fa();
    }

    @NonNull
    public final ViewTarget<T, Z> eW() {
        if (this.qq != null) {
            return this;
        }
        this.qq = new View.OnAttachStateChangeListener() {
            public void onViewAttachedToWindow(View view) {
                ViewTarget.this.eX();
            }

            public void onViewDetachedFromWindow(View view) {
                ViewTarget.this.eY();
            }
        };
        fa();
        return this;
    }

    /* access modifiers changed from: package-private */
    public void eX() {
        c dS = dS();
        if (dS != null && dS.isPaused()) {
            dS.begin();
        }
    }

    /* access modifiers changed from: package-private */
    public void eY() {
        c dS = dS();
        if (dS != null && !dS.isCancelled() && !dS.isPaused()) {
            this.qr = true;
            dS.pause();
            this.qr = false;
        }
    }

    @NonNull
    public final ViewTarget<T, Z> eZ() {
        this.qp.qw = true;
        return this;
    }

    @NonNull
    public T getView() {
        return this.view;
    }

    public void j(@Nullable c cVar) {
        setTag(cVar);
    }

    public String toString() {
        return "Target for: " + this.view;
    }
}
