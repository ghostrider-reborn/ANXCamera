package miui.external;

import android.util.Log;

/* compiled from: SdkConstants */
class SdkEntranceHelper implements SdkConstants {
    private static final String SDK_ENTRANCE_CLASS = "miui.core.SdkManager";
    private static final String SDK_ENTRANCE_FALLBACK_CLASS = "com.miui.internal.core.SdkManager";

    SdkEntranceHelper() {
    }

    public static Class<?> getSdkEntrance() throws ClassNotFoundException {
        String str = SdkConstants.LOG_TAG;
        try {
            return Class.forName(SDK_ENTRANCE_CLASS);
        } catch (ClassNotFoundException unused) {
            try {
                Class cls = Class.forName(SDK_ENTRANCE_FALLBACK_CLASS);
                Log.w(str, "using legacy sdk");
                return cls;
            } catch (ClassNotFoundException e2) {
                Log.e(str, "no sdk found");
                throw e2;
            }
        }
    }
}
