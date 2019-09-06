package com.android.camera;

import android.graphics.Rect;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.mi.config.b;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class HybridZoomingSystem {
    private static final String DEFAULT_OPTICAL_ZOOM_RATIO_COMBINATION;
    public static final float FLOAT_MICRO_SCENE_ZOOM_MAX = 1.0f;
    public static final float FLOAT_MOON_MODE_ZOOM_MAX = 20.0f;
    public static final float FLOAT_MOON_MODE_ZOOM_MIN = 1.0f;
    public static final float FLOAT_STEP_FOR_ZOOM_RATIO_CHANGE = 0.1f;
    public static final float FLOAT_ULTRA_WIDE_ZOOM_MAX = 6.0f;
    public static final float FLOAT_ZOOM_RATIO_10X = 10.0f;
    public static final float FLOAT_ZOOM_RATIO_5X = 5.0f;
    public static final float FLOAT_ZOOM_RATIO_MACRO = 0.0f;
    public static final float FLOAT_ZOOM_RATIO_NONE = 1.0f;
    public static final float FLOAT_ZOOM_RATIO_TELE = 2.0f;
    public static final float FLOAT_ZOOM_RATIO_ULTR = 0.6f;
    public static final float FLOAT_ZOOM_RATIO_WIDE = 1.0f;
    public static final boolean IS_2_OR_MORE_SAT = OPTICAL_ZOOM_RATIO_COMBINATION.matches("^\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+(\\s*:\\s*\\d+\\.\\d+)*$");
    public static final boolean IS_2_SAT = OPTICAL_ZOOM_RATIO_COMBINATION.matches("^\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+$");
    public static final boolean IS_3_OR_MORE_SAT = OPTICAL_ZOOM_RATIO_COMBINATION.matches("^\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+(\\s*:\\s*\\d+\\.\\d+)*$");
    public static final boolean IS_3_SAT = OPTICAL_ZOOM_RATIO_COMBINATION.matches("^\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+$");
    public static final boolean IS_4_OR_MORE_SAT = OPTICAL_ZOOM_RATIO_COMBINATION.matches("^\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+(\\s*:\\s*\\d+\\.\\d+)*$");
    public static final boolean IS_4_SAT = OPTICAL_ZOOM_RATIO_COMBINATION.matches("^\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+$");
    private static final String OPTICAL_ZOOM_RATIO_COMBINATION = DataRepository.dataItemFeature().k(DEFAULT_OPTICAL_ZOOM_RATIO_COMBINATION);
    private static final String STANDALONE_MACRO_OPTICAL_ZOOM_RATIO_COMBINATION = DataRepository.dataItemFeature().j(DEFAULT_OPTICAL_ZOOM_RATIO_COMBINATION);
    public static final String STRING_ZOOM_RATIO_NONE = "1.0";
    public static final String STRING_ZOOM_RATIO_TELE;
    public static final String STRING_ZOOM_RATIO_ULTR;
    public static final String STRING_ZOOM_RATIO_WIDE;
    private static final String TAG = "HybridZoomingSystem";
    public static final float TOLERANCE_FOR_ZOOM_RATIO_CHANGED = 0.01f;
    private static int sDefaultOpticalZoomRatioIndex;
    public static float sDefaultStandaloneMacroOpticalZoomRatio = 1.0f;
    private static int sDefaultStandaloneMacroOpticalZoomRatioIndex;
    private static float[] sFunOrLiveSupportedOpticalZoomRatios = {0.6f, 1.0f, 2.0f};
    private static int sFunOrLiveSupportedZoomRatioIndex = 1;
    private static int sMacroZoomRatioIndex;
    private static float[] sStandaloneMacroSupportedOpticalZoomRatios;
    private static float[] sSupportedOpticalZoomRatios;
    private static final Map<Integer, String> sZoomRatioHistory = new ConcurrentHashMap();
    private static final AtomicInteger sZoomingSourceIdentity = new AtomicInteger(0);

    private static /* synthetic */ void $closeResource(Throwable th, AutoCloseable autoCloseable) {
        if (th != null) {
            try {
                autoCloseable.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
        } else {
            autoCloseable.close();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0186, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0187, code lost:
        $closeResource(r0, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x018a, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0207, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0208, code lost:
        $closeResource(r0, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x020b, code lost:
        throw r1;
     */
    static {
        int i;
        String str = "\\s*[:,]\\s*";
        String str2 = "1.0";
        DEFAULT_OPTICAL_ZOOM_RATIO_COMBINATION = b.isSupportedOpticalZoom() ? "1.0:2.0" : str2;
        sMacroZoomRatioIndex = -1;
        sDefaultOpticalZoomRatioIndex = -1;
        sDefaultStandaloneMacroOpticalZoomRatioIndex = -1;
        ArrayList<String> arrayList = new ArrayList<>();
        Scanner scanner = new Scanner(OPTICAL_ZOOM_RATIO_COMBINATION);
        scanner.useDelimiter(str);
        if (DataRepository.dataItemFeature().mb()) {
            sMacroZoomRatioIndex = 0;
            arrayList.add("0");
            i = 0;
        } else {
            i = -1;
        }
        while (scanner.hasNext()) {
            String next = scanner.next();
            if (next != null && next.length() > 0) {
                arrayList.add(next);
                i++;
                if (next.equals(str2) && sDefaultOpticalZoomRatioIndex == -1) {
                    sDefaultOpticalZoomRatioIndex = i;
                }
            }
        }
        $closeResource(null, scanner);
        String str3 = "The supported optical zoom ratios are probably not configured correctly";
        if (sDefaultOpticalZoomRatioIndex < 0 || arrayList.size() < 1) {
            throw new IllegalStateException(str3);
        }
        sSupportedOpticalZoomRatios = new float[arrayList.size()];
        int i2 = 0;
        for (String parseFloat : arrayList) {
            int i3 = i2 + 1;
            sSupportedOpticalZoomRatios[i2] = Float.parseFloat(parseFloat);
            i2 = i3;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Arrays.toString(sSupportedOpticalZoomRatios));
        String str4 = "[";
        sb.append(str4);
        sb.append(sDefaultOpticalZoomRatioIndex);
        String str5 = "]";
        sb.append(str5);
        String sb2 = sb.toString();
        String str6 = TAG;
        Log.d(str6, sb2);
        if (DataRepository.dataItemFeature().ad()) {
            arrayList.clear();
            Scanner scanner2 = new Scanner(STANDALONE_MACRO_OPTICAL_ZOOM_RATIO_COMBINATION);
            scanner2.useDelimiter(str);
            int i4 = -1;
            while (scanner2.hasNext()) {
                String next2 = scanner2.next();
                if (next2 != null && next2.length() > 0) {
                    arrayList.add(next2);
                    i4++;
                    if (next2.equals(str2) && sDefaultStandaloneMacroOpticalZoomRatioIndex == -1) {
                        sDefaultStandaloneMacroOpticalZoomRatioIndex = i4;
                    }
                }
            }
            $closeResource(null, scanner2);
            if (sDefaultStandaloneMacroOpticalZoomRatioIndex < 0 || arrayList.size() < 1) {
                throw new IllegalStateException(str3);
            }
            sStandaloneMacroSupportedOpticalZoomRatios = new float[arrayList.size()];
            int i5 = 0;
            for (String parseFloat2 : arrayList) {
                int i6 = i5 + 1;
                sStandaloneMacroSupportedOpticalZoomRatios[i5] = Float.parseFloat(parseFloat2);
                i5 = i6;
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(Arrays.toString(sStandaloneMacroSupportedOpticalZoomRatios));
            sb3.append(str4);
            sb3.append(sDefaultStandaloneMacroOpticalZoomRatioIndex);
            sb3.append(str5);
            Log.d(str6, sb3.toString());
        }
        String str7 = "%.1fx";
        STRING_ZOOM_RATIO_ULTR = String.format(Locale.US, str7, new Object[]{Float.valueOf(0.6f)});
        STRING_ZOOM_RATIO_WIDE = String.format(Locale.US, str7, new Object[]{Float.valueOf(1.0f)});
        STRING_ZOOM_RATIO_TELE = String.format(Locale.US, str7, new Object[]{Float.valueOf(2.0f)});
    }

    private HybridZoomingSystem() {
    }

    public static float add(float f2, float f3) {
        return ((float) (((int) (f2 * 10.0f)) + ((int) (f3 * 10.0f)))) / 10.0f;
    }

    public static float clamp(float f2, float f3, float f4) {
        return f2 > f4 ? f4 : f2 < f3 ? f3 : f2;
    }

    public static void clearZoomRatioHistory() {
        Log.d(TAG, "clearZoomRatioHistory()");
        sZoomRatioHistory.clear();
        sZoomingSourceIdentity.set(0);
    }

    public static int getDefaultOpticalZoomRatioIndex(int i) {
        return (i == 161 || i == 174) ? sFunOrLiveSupportedZoomRatioIndex : sDefaultOpticalZoomRatioIndex;
    }

    public static int getMacroZoomRatioIndex(int i) {
        if (i == 161 || i == 174) {
            return -1;
        }
        return sMacroZoomRatioIndex;
    }

    public static float getMaximumOpticalZoomRatio(int i) {
        float[] fArr = (i == 161 || i == 174) ? sFunOrLiveSupportedOpticalZoomRatios : sSupportedOpticalZoomRatios;
        return fArr[fArr.length - 1];
    }

    public static float getMaximumStandaloneOpticalZoomRatio() {
        float[] fArr = sStandaloneMacroSupportedOpticalZoomRatios;
        return fArr[fArr.length - 1];
    }

    public static float getMinimumOpticalZoomRatio(int i) {
        return (i == 161 || i == 174) ? sFunOrLiveSupportedOpticalZoomRatios[0] : sMacroZoomRatioIndex != -1 ? sSupportedOpticalZoomRatios[1] : sSupportedOpticalZoomRatios[0];
    }

    public static float getMinimumStandaloneOpticalZoomRatio() {
        return sStandaloneMacroSupportedOpticalZoomRatios[0];
    }

    public static float getOpticalZoomRatioAt(int i, int i2) {
        float[] fArr = (i == 161 || i == 174) ? sFunOrLiveSupportedOpticalZoomRatios : sSupportedOpticalZoomRatios;
        int length = fArr.length;
        if (i2 >= 0 && i2 < length) {
            return fArr[i2];
        }
        StringBuilder sb = new StringBuilder();
        sb.append("The given index must be in range [0, ");
        sb.append(length);
        sb.append(")");
        throw new ArrayIndexOutOfBoundsException(sb.toString());
    }

    public static int getOpticalZoomRatioIndex(int i, float f2) {
        float[] fArr = (i == 161 || i == 174) ? sFunOrLiveSupportedOpticalZoomRatios : sSupportedOpticalZoomRatios;
        for (int length = fArr.length - 1; length >= 0; length--) {
            if (f2 >= fArr[length]) {
                return length;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Illegal zoom ratio: ");
        sb.append(f2);
        throw new IllegalArgumentException(sb.toString());
    }

    public static float[] getSupportedOpticalZoomRatios(int i) {
        return (i == 161 || i == 174) ? sFunOrLiveSupportedOpticalZoomRatios : sSupportedOpticalZoomRatios;
    }

    public static String getZoomRatioHistory(int i, String str) {
        if (i == 165) {
            i = 163;
        }
        String str2 = (String) sZoomRatioHistory.get(Integer.valueOf(i));
        return str2 != null ? str2 : str;
    }

    public static int getZoomingSourceIdentity() {
        return sZoomingSourceIdentity.get();
    }

    public static boolean isOpticalZoomRatio(float f2) {
        return Arrays.binarySearch(sSupportedOpticalZoomRatios, f2) >= 0;
    }

    static void preload() {
        Log.d(TAG, "preload");
    }

    public static void setZoomRatioHistory(int i, String str) {
        if (i == 165) {
            i = 163;
        }
        sZoomRatioHistory.put(Integer.valueOf(i), str);
    }

    public static void setZoomingSourceIdentity(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("setZoomingSourceIdentity(): ");
        sb.append(i);
        Log.d(TAG, sb.toString());
        sZoomingSourceIdentity.set(i);
    }

    public static float sub(float f2, float f3) {
        return ((float) (((int) (f2 * 10.0f)) - ((int) (f3 * 10.0f)))) / 10.0f;
    }

    public static Rect toCropRegion(float f2, Rect rect) {
        if (f2 <= 0.0f) {
            throw new IllegalArgumentException("Zoom ratio must be greater than 0.0f");
        } else if (rect != null) {
            int width = rect.width() / 2;
            int height = rect.height() / 2;
            float f3 = 2.0f * f2;
            int width2 = (int) (((float) rect.width()) / f3);
            int height2 = (int) (((float) rect.height()) / f3);
            Rect rect2 = new Rect();
            rect2.set(width - width2, height - height2, width + width2, height + height2);
            StringBuilder sb = new StringBuilder();
            sb.append("toCropRegion(): zoom ratio = ");
            sb.append(f2);
            sb.append(", crop region = ");
            sb.append(rect2);
            Log.d(TAG, sb.toString());
            return rect2;
        } else {
            throw new IllegalArgumentException("activeArraySize must be non null");
        }
    }

    public static float toDecimal(float f2) {
        return ((float) ((int) (f2 * 10.0f))) / 10.0f;
    }

    public static float toFloat(String str, float f2) {
        try {
            return Float.parseFloat(str);
        } catch (Exception unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid zoom: ");
            sb.append(str);
            Log.e(TAG, sb.toString());
            return f2;
        }
    }

    public static String toString(float f2) {
        if (0.6f == f2) {
            return STRING_ZOOM_RATIO_ULTR;
        }
        if (1.0f == f2) {
            return STRING_ZOOM_RATIO_WIDE;
        }
        if (2.0f == f2) {
            return STRING_ZOOM_RATIO_TELE;
        }
        float decimal = toDecimal(f2);
        return String.format(Locale.US, "%.1fx", new Object[]{Float.valueOf(decimal)});
    }
}
