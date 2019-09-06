package com.android.camera.fragment.top;

import com.android.camera.ui.ToggleSwitch;
import com.android.camera.ui.ToggleSwitch.OnCheckedChangeListener;

/* compiled from: lambda */
public final /* synthetic */ class h implements OnCheckedChangeListener {
    public static final /* synthetic */ h INSTANCE = new h();

    private /* synthetic */ h() {
    }

    public final void onCheckedChanged(ToggleSwitch toggleSwitch, boolean z) {
        FragmentTopAlert.a(toggleSwitch, z);
    }
}
