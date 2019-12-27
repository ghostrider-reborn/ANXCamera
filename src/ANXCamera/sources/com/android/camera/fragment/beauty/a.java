package com.android.camera.fragment.beauty;

import android.view.View;
import android.widget.AdapterView;

/* compiled from: lambda */
public final /* synthetic */ class a implements AdapterView.OnItemClickListener {
    private final /* synthetic */ RemodelingParamsFragment ub;

    public /* synthetic */ a(RemodelingParamsFragment remodelingParamsFragment) {
        this.ub = remodelingParamsFragment;
    }

    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
        this.ub.a(adapterView, view, i, j);
    }
}
