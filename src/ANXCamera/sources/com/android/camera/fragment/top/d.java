package com.android.camera.fragment.top;

import com.android.camera.ui.ToggleSwitch;
import com.android.camera.ui.ToggleSwitch.OnCheckedChangeListener;

/* compiled from: lambda */
public final /* synthetic */ class d implements OnCheckedChangeListener {
    public static final /* synthetic */ d INSTANCE = new d();

    private /* synthetic */ d() {
    }

    public final void onCheckedChanged(ToggleSwitch toggleSwitch, boolean z) {
        FragmentTopAlert.b(toggleSwitch, z);
    }
}
