package com.android.camera.fragment.dialog;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/* compiled from: lambda */
public final /* synthetic */ class a implements OnTouchListener {
    private final /* synthetic */ BaseDialogFragment ub;

    public /* synthetic */ a(BaseDialogFragment baseDialogFragment) {
        this.ub = baseDialogFragment;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        return this.ub.a(view, motionEvent);
    }
}
