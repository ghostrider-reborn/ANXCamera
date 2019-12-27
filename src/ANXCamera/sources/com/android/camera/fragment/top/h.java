package com.android.camera.fragment.top;

import com.android.camera.ui.ToggleSwitch;

/* compiled from: lambda */
public final /* synthetic */ class h implements ToggleSwitch.OnCheckedChangeListener {
    public static final /* synthetic */ h INSTANCE = new h();

    private /* synthetic */ h() {
    }

    public final void onCheckedChanged(ToggleSwitch toggleSwitch, boolean z) {
        FragmentTopAlert.a(toggleSwitch, z);
    }
}
