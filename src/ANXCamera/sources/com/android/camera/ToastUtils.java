package com.android.camera;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.InflateException;
import android.view.WindowManager;
import android.widget.Toast;
import com.android.camera.log.Log;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ToastUtils {
    private static final long SHORT_DURATION_TIMEOUT = 2000;
    public static final String TAG = "ToastUtils";
    private static int sGravity = 17;
    private static String sOldMsg;
    private static long sOneTime = 0;
    protected static Toast sToast = null;
    private static long sTwoTime = 0;
    private static int sXOffset = 0;
    private static int sYOffset = 0;

    private static WindowManager.LayoutParams getWindowParams(Toast toast) {
        if (toast == null) {
            return null;
        }
        try {
            Method method = Toast.class.getMethod("getWindowParams", new Class[0]);
            method.setAccessible(true);
            return (WindowManager.LayoutParams) method.invoke(toast, new Object[0]);
        } catch (NoSuchMethodException e) {
            Log.d(TAG, "getWindowParams: no such method", e);
            Log.d(TAG, "getWindowsParam: ret: " + null);
            return null;
        } catch (IllegalAccessException e2) {
            Log.d(TAG, "getWindowParams: cannot access", e2);
            Log.d(TAG, "getWindowsParam: ret: " + null);
            return null;
        } catch (InvocationTargetException e3) {
            Log.d(TAG, "getWindowParams: invoke failed: ", e3);
            Log.d(TAG, "getWindowsParam: ret: " + null);
            return null;
        }
    }

    private static void prepareShowOnKeyguard(Toast toast) {
        WindowManager.LayoutParams windowParams = getWindowParams(toast);
        if (windowParams != null) {
            windowParams.flags |= 524288;
        }
    }

    public static void showToast(Context context, int i) {
        if (context != null) {
            Resources resources = context.getResources();
            if (resources != null) {
                showToast(new WeakReference(context), resources.getString(i), 17, 0, 0, false);
            }
        }
    }

    public static void showToast(Context context, String str) {
        showToast(new WeakReference(context), str, 17, 0, 0, false);
    }

    public static void showToast(Context context, String str, int i) {
        if (context != null && context.getResources() != null) {
            showToast(new WeakReference(context), str, i, 0, 0, false);
        }
    }

    public static void showToast(Context context, String str, int i, int i2, int i3) {
        if (context != null && context.getResources() != null) {
            showToast(new WeakReference(context), str, i, i2, i3, false);
        }
    }

    public static void showToast(Context context, String str, boolean z) {
        showToast(new WeakReference(context), str, 17, 0, 0, z);
    }

    private static void showToast(WeakReference<Context> weakReference, String str, int i, int i2, int i3, boolean z) {
        if (!TextUtils.isEmpty(str)) {
            boolean z2 = false;
            if (sToast == null) {
                Context context = (Context) weakReference.get();
                if (context != null) {
                    try {
                        sToast = Toast.makeText(context.getApplicationContext(), str, 0);
                        sToast.setGravity(i, i2, i3);
                        if (z) {
                            prepareShowOnKeyguard(sToast);
                        }
                        sToast.show();
                        sOldMsg = str;
                        sGravity = i;
                        sXOffset = i2;
                        sYOffset = i3;
                        sOneTime = System.currentTimeMillis();
                    } catch (InflateException e) {
                        sToast = null;
                        e.printStackTrace();
                    } catch (Exception e2) {
                        sToast = null;
                        e2.printStackTrace();
                    }
                }
            } else {
                sTwoTime = System.currentTimeMillis();
                boolean z3 = true;
                if (sOldMsg != null && !sOldMsg.equals(str)) {
                    sOldMsg = str;
                    sToast.setText(str);
                    z2 = true;
                }
                if (i == sGravity && sXOffset == i2 && sYOffset == i3) {
                    z3 = z2;
                } else {
                    sToast.setGravity(i, i2, i3);
                    sGravity = i;
                    sXOffset = i2;
                    sYOffset = i3;
                }
                if (z3 || sTwoTime - sOneTime > 2000) {
                    sOneTime = sTwoTime;
                    sToast.show();
                }
            }
        }
    }
}
