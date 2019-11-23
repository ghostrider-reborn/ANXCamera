package com.bumptech.glide.util;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.model.l;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

/* compiled from: Util */
public final class k {
    private static final int rc = 31;
    private static final int rd = 17;
    private static final char[] re = "0123456789abcdef".toCharArray();
    private static final char[] rf = new char[64];

    /* renamed from: com.bumptech.glide.util.k$1  reason: invalid class name */
    /* compiled from: Util */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] iT = new int[Bitmap.Config.values().length];

        static {
            try {
                iT[Bitmap.Config.ALPHA_8.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iT[Bitmap.Config.RGB_565.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iT[Bitmap.Config.ARGB_4444.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iT[Bitmap.Config.RGBA_F16.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iT[Bitmap.Config.ARGB_8888.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    private k() {
    }

    public static int a(@Nullable Object obj, int i) {
        return u(obj == null ? 0 : obj.hashCode(), i);
    }

    @NonNull
    private static String a(@NonNull byte[] bArr, @NonNull char[] cArr) {
        for (int i = 0; i < bArr.length; i++) {
            byte b2 = bArr[i] & 255;
            int i2 = i * 2;
            cArr[i2] = re[b2 >>> 4];
            cArr[i2 + 1] = re[b2 & 15];
        }
        return new String(cArr);
    }

    private static boolean aa(int i) {
        return i > 0 || i == Integer.MIN_VALUE;
    }

    @NonNull
    public static <T> Queue<T> ab(int i) {
        return new ArrayDeque(i);
    }

    public static int c(float f, int i) {
        return u(Float.floatToIntBits(f), i);
    }

    public static int c(boolean z, int i) {
        return u(z ? 1 : 0, i);
    }

    @NonNull
    public static <T> List<T> c(@NonNull Collection<T> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        for (T next : collection) {
            if (next != null) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    public static boolean d(@Nullable Object obj, @Nullable Object obj2) {
        return obj == null ? obj2 == null : obj.equals(obj2);
    }

    public static boolean e(@Nullable Object obj, @Nullable Object obj2) {
        return obj == null ? obj2 == null : obj instanceof l ? ((l) obj).u(obj2) : obj.equals(obj2);
    }

    private static int f(@Nullable Bitmap.Config config) {
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }
        switch (AnonymousClass1.iT[config.ordinal()]) {
            case 1:
                return 1;
            case 2:
            case 3:
                return 2;
            case 4:
                return 8;
            default:
                return 4;
        }
    }

    @Deprecated
    public static int f(@NonNull Bitmap bitmap) {
        return p(bitmap);
    }

    public static void fo() {
        if (!fq()) {
            throw new IllegalArgumentException("You must call this method on the main thread");
        }
    }

    public static void fp() {
        if (!fr()) {
            throw new IllegalArgumentException("You must call this method on a background thread");
        }
    }

    public static boolean fq() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static boolean fr() {
        return !fq();
    }

    public static int hashCode(float f) {
        return c(f, 17);
    }

    public static int hashCode(int i) {
        return u(i, 17);
    }

    public static int hashCode(boolean z) {
        return c(z, 17);
    }

    public static int i(int i, int i2, @Nullable Bitmap.Config config) {
        return i * i2 * f(config);
    }

    @NonNull
    public static String j(@NonNull byte[] bArr) {
        String a2;
        synchronized (rf) {
            a2 = a(bArr, rf);
        }
        return a2;
    }

    @TargetApi(19)
    public static int p(@NonNull Bitmap bitmap) {
        if (!bitmap.isRecycled()) {
            if (Build.VERSION.SDK_INT >= 19) {
                try {
                    return bitmap.getAllocationByteCount();
                } catch (NullPointerException e) {
                }
            }
            return bitmap.getHeight() * bitmap.getRowBytes();
        }
        throw new IllegalStateException("Cannot obtain size for recycled Bitmap: " + bitmap + "[" + bitmap.getWidth() + "x" + bitmap.getHeight() + "] " + bitmap.getConfig());
    }

    public static boolean t(int i, int i2) {
        return aa(i) && aa(i2);
    }

    public static int u(int i, int i2) {
        return (i2 * 31) + i;
    }
}
