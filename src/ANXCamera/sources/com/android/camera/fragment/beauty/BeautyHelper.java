package com.android.camera.fragment.beauty;

import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;

public class BeautyHelper {
    public static void clearBeauty() {
        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        if (miBeautyProtocol != null) {
            miBeautyProtocol.clearBeauty();
        }
    }

    public static void onBeautyChanged() {
        ModeProtocol.OnFaceBeautyChangedProtocol onFaceBeautyChangedProtocol = (ModeProtocol.OnFaceBeautyChangedProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(199);
        if (onFaceBeautyChangedProtocol != null) {
            onFaceBeautyChangedProtocol.onBeautyChanged(false);
        }
    }

    public static void resetBeauty() {
        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        if (miBeautyProtocol != null) {
            miBeautyProtocol.resetBeauty();
        }
    }
}
