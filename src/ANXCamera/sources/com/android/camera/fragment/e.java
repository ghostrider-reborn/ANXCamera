package com.android.camera.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/* compiled from: lambda */
public final /* synthetic */ class e implements OnClickListener {
    private final /* synthetic */ GoogleLensFragment ub;

    public /* synthetic */ e(GoogleLensFragment googleLensFragment) {
        this.ub = googleLensFragment;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.ub.a(dialogInterface, i);
    }
}
