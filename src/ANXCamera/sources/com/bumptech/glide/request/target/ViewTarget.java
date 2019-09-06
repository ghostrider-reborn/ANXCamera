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
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.WindowManager;
import com.bumptech.glide.request.c;
import com.bumptech.glide.util.i;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ViewTarget<T extends View, Z> extends b<Z> {
    private static boolean Fl = false;
    @Nullable
    private static Integer Gl = null;
    private static final String TAG = "ViewTarget";
    private final SizeDeterminer Bl;
    @Nullable
    private OnAttachStateChangeListener Cl;
    private boolean Dl;
    private boolean El;
    protected final T view;

    @VisibleForTesting
    static final class SizeDeterminer {
        private static final int Ll = 0;
        @Nullable
        @VisibleForTesting
        static Integer maxDisplayLength;
        boolean Jl;
        @Nullable
        private a Kl;
        private final View view;
        private final List<n> wf = new ArrayList();

        private static final class a implements OnPreDrawListener {
            private final WeakReference<SizeDeterminer> Il;

            a(@NonNull SizeDeterminer sizeDeterminer) {
                this.Il = new WeakReference<>(sizeDeterminer);
            }

            public boolean onPreDraw() {
                String str = ViewTarget.TAG;
                if (Log.isLoggable(str, 2)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("OnGlobalLayoutListener called attachStateListener=");
                    sb.append(this);
                    Log.v(str, sb.toString());
                }
                SizeDeterminer sizeDeterminer = (SizeDeterminer) this.Il.get();
                if (sizeDeterminer != null) {
                    sizeDeterminer.Eh();
                }
                return true;
            }
        }

        SizeDeterminer(@NonNull View view2) {
            this.view = view2;
        }

        private boolean U(int i) {
            return i > 0 || i == Integer.MIN_VALUE;
        }

        private int bl() {
            int paddingTop = this.view.getPaddingTop() + this.view.getPaddingBottom();
            LayoutParams layoutParams = this.view.getLayoutParams();
            return c(this.view.getHeight(), layoutParams != null ? layoutParams.height : 0, paddingTop);
        }

        private int c(int i, int i2, int i3) {
            int i4 = i2 - i3;
            if (i4 > 0) {
                return i4;
            }
            if (this.Jl && this.view.isLayoutRequested()) {
                return 0;
            }
            int i5 = i - i3;
            if (i5 > 0) {
                return i5;
            }
            if (this.view.isLayoutRequested() || i2 != -2) {
                return 0;
            }
            String str = ViewTarget.TAG;
            if (Log.isLoggable(str, 4)) {
                Log.i(str, "Glide treats LayoutParams.WRAP_CONTENT as a request for an image the size of this device's screen dimensions. If you want to load the original image and are ok with the corresponding memory cost and OOMs (depending on the input size), use .override(Target.SIZE_ORIGINAL). Otherwise, use LayoutParams.MATCH_PARENT, set layout_width and layout_height to fixed dimension, or use .override() with fixed dimensions.");
            }
            return o(this.view.getContext());
        }

        private int cl() {
            int paddingLeft = this.view.getPaddingLeft() + this.view.getPaddingRight();
            LayoutParams layoutParams = this.view.getLayoutParams();
            return c(this.view.getWidth(), layoutParams != null ? layoutParams.width : 0, paddingLeft);
        }

        private static int o(@NonNull Context context) {
            if (maxDisplayLength == null) {
                WindowManager windowManager = (WindowManager) context.getSystemService("window");
                i.checkNotNull(windowManager);
                Display defaultDisplay = windowManager.getDefaultDisplay();
                Point point = new Point();
                defaultDisplay.getSize(point);
                maxDisplayLength = Integer.valueOf(Math.max(point.x, point.y));
            }
            return maxDisplayLength.intValue();
        }

        private boolean r(int i, int i2) {
            return U(i) && U(i2);
        }

        private void s(int i, int i2) {
            Iterator it = new ArrayList(this.wf).iterator();
            while (it.hasNext()) {
                ((n) it.next()).b(i, i2);
            }
        }

        /* access modifiers changed from: 0000 */
        public void Eh() {
            if (!this.wf.isEmpty()) {
                int cl = cl();
                int bl = bl();
                if (r(cl, bl)) {
                    s(cl, bl);
                    Fh();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void Fh() {
            ViewTreeObserver viewTreeObserver = this.view.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeOnPreDrawListener(this.Kl);
            }
            this.Kl = null;
            this.wf.clear();
        }

        /* access modifiers changed from: 0000 */
        public void a(@NonNull n nVar) {
            this.wf.remove(nVar);
        }

        /* access modifiers changed from: 0000 */
        public void b(@NonNull n nVar) {
            int cl = cl();
            int bl = bl();
            if (r(cl, bl)) {
                nVar.b(cl, bl);
                return;
            }
            if (!this.wf.contains(nVar)) {
                this.wf.add(nVar);
            }
            if (this.Kl == null) {
                ViewTreeObserver viewTreeObserver = this.view.getViewTreeObserver();
                this.Kl = new a(this);
                viewTreeObserver.addOnPreDrawListener(this.Kl);
            }
        }
    }

    public ViewTarget(@NonNull T t) {
        i.checkNotNull(t);
        this.view = (View) t;
        this.Bl = new SizeDeterminer(t);
    }

    @Deprecated
    public ViewTarget(@NonNull T t, boolean z) {
        this(t);
        if (z) {
            Dh();
        }
    }

    public static void H(int i) {
        if (Gl != null || Fl) {
            throw new IllegalArgumentException("You cannot set the tag id more than once or change the tag id after the first request has been made");
        }
        Gl = Integer.valueOf(i);
    }

    private void _k() {
        OnAttachStateChangeListener onAttachStateChangeListener = this.Cl;
        if (onAttachStateChangeListener != null && !this.El) {
            this.view.addOnAttachStateChangeListener(onAttachStateChangeListener);
            this.El = true;
        }
    }

    private void al() {
        OnAttachStateChangeListener onAttachStateChangeListener = this.Cl;
        if (onAttachStateChangeListener != null && this.El) {
            this.view.removeOnAttachStateChangeListener(onAttachStateChangeListener);
            this.El = false;
        }
    }

    @Nullable
    private Object getTag() {
        Integer num = Gl;
        return num == null ? this.view.getTag() : this.view.getTag(num.intValue());
    }

    private void setTag(@Nullable Object obj) {
        Integer num = Gl;
        if (num == null) {
            Fl = true;
            this.view.setTag(obj);
            return;
        }
        this.view.setTag(num.intValue(), obj);
    }

    @NonNull
    public final ViewTarget<T, Z> Ah() {
        if (this.Cl != null) {
            return this;
        }
        this.Cl = new q(this);
        _k();
        return this;
    }

    /* access modifiers changed from: 0000 */
    public void Bh() {
        c request = getRequest();
        if (request != null && !request.isCancelled() && !request.isPaused()) {
            this.Dl = true;
            request.pause();
            this.Dl = false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void Ch() {
        c request = getRequest();
        if (request != null && request.isPaused()) {
            request.begin();
        }
    }

    @NonNull
    public final ViewTarget<T, Z> Dh() {
        this.Bl.Jl = true;
        return this;
    }

    @CallSuper
    public void a(@NonNull n nVar) {
        this.Bl.a(nVar);
    }

    @CallSuper
    public void b(@Nullable Drawable drawable) {
        super.b(drawable);
        this.Bl.Fh();
        if (!this.Dl) {
            al();
        }
    }

    @CallSuper
    public void b(@NonNull n nVar) {
        this.Bl.b(nVar);
    }

    @CallSuper
    public void c(@Nullable Drawable drawable) {
        super.c(drawable);
        _k();
    }

    public void f(@Nullable c cVar) {
        setTag(cVar);
    }

    @Nullable
    public c getRequest() {
        Object tag = getTag();
        if (tag == null) {
            return null;
        }
        if (tag instanceof c) {
            return (c) tag;
        }
        throw new IllegalArgumentException("You must not call setTag() on a view Glide is targeting");
    }

    @NonNull
    public T getView() {
        return this.view;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Target for: ");
        sb.append(this.view);
        return sb.toString();
    }
}
