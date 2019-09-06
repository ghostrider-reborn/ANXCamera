package com.bumptech.glide.request.target;

import android.view.View;
import android.view.View.OnAttachStateChangeListener;

/* compiled from: ViewTarget */
class q implements OnAttachStateChangeListener {
    final /* synthetic */ ViewTarget this$0;

    q(ViewTarget viewTarget) {
        this.this$0 = viewTarget;
    }

    public void onViewAttachedToWindow(View view) {
        this.this$0.Ch();
    }

    public void onViewDetachedFromWindow(View view) {
        this.this$0.Bh();
    }
}
