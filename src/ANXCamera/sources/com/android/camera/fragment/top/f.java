package com.android.camera.fragment.top;

import android.content.DialogInterface;

/* compiled from: lambda */
public final /* synthetic */ class f implements DialogInterface.OnClickListener {
    private final /* synthetic */ FragmentTopAlert ub;

    public /* synthetic */ f(FragmentTopAlert fragmentTopAlert) {
        this.ub = fragmentTopAlert;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.ub.b(dialogInterface, i);
    }
}
