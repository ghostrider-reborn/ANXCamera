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
    private static final String DEFAULT_OPTICAL_ZOOM_RATIO_COMBINATION = (b.isSupportedOpticalZoom() ? "1.0:2.0" : "1.0");
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
    private static final String OPTICAL_ZOOM_RATIO_COMBINATION = DataRepository.dataItemFeature().M(DEFAULT_OPTICAL_ZOOM_RATIO_COMBINATION);
    private static final String STANDALONE_MACRO_OPTICAL_ZOOM_RATIO_COMBINATION = DataRepository.dataItemFeature().N(DEFAULT_OPTICAL_ZOOM_RATIO_COMBINATION);
    public static final String STRING_ZOOM_RATIO_NONE = "1.0";
    public static final String STRING_ZOOM_RATIO_TELE = String.format(Locale.US, "%.1fx", new Object[]{Float.valueOf(2.0f)});
    public static final String STRING_ZOOM_RATIO_TELE_5X = String.format(Locale.US, "%.1fx", new Object[]{Float.valueOf(5.0f)});
    public static final String STRING_ZOOM_RATIO_ULTR = String.format(Locale.US, "%.1fx", new Object[]{Float.valueOf(0.6f)});
    public static final String STRING_ZOOM_RATIO_WIDE = String.format(Locale.US, "%.1fx", new Object[]{Float.valueOf(1.0f)});
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

    static {
        Throwable th;
        int i;
        Throwable th2;
        sMacroZoomRatioIndex = -1;
        sDefaultOpticalZoomRatioIndex = -1;
        sDefaultStandaloneMacroOpticalZoomRatioIndex = -1;
        ArrayList<String> arrayList = new ArrayList<>();
        Scanner scanner = new Scanner(OPTICAL_ZOOM_RATIO_COMBINATION);
        try {
            scanner.useDelimiter("\\s*[:,]\\s*");
            if (DataRepository.dataItemFeature().ii()) {
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
                    if (next.equals("1.0") && sDefaultOpticalZoomRatioIndex == -1) {
                        sDefaultOpticalZoomRatioIndex = i;
                    }
                }
            }
            $closeResource((Throwable) null, scanner);
            if (sDefaultOpticalZoomRatioIndex < 0 || arrayList.size() < 1) {
                throw new IllegalStateException("The supported optical zoom ratios are probably not configured correctly");
            }
            sSupportedOpticalZoomRatios = new float[arrayList.size()];
            int i2 = 0;
            for (String parseFloat : arrayList) {
                sSupportedOpticalZoomRatios[i2] = Float.parseFloat(parseFloat);
                i2++;
            }
            Log.d(TAG, Arrays.toString(sSupportedOpticalZoomRatios) + "[" + sDefaultOpticalZoomRatioIndex + "]");
            if (DataRepository.dataItemFeature().hH()) {
                arrayList.clear();
                Scanner scanner2 = new Scanner(STANDALONE_MACRO_OPTICAL_ZOOM_RATIO_COMBINATION);
                try {
                    scanner2.useDelimiter("\\s*[:,]\\s*");
                    int i3 = -1;
                    while (scanner2.hasNext()) {
                        String next2 = scanner2.next();
                        if (next2 != null && next2.length() > 0) {
                            arrayList.add(next2);
                            i3++;
                            if (next2.equals("1.0") && sDefaultStandaloneMacroOpticalZoomRatioIndex == -1) {
                                sDefaultStandaloneMacroOpticalZoomRatioIndex = i3;
                            }
                        }
                    }
                    $closeResource((Throwable) null, scanner2);
                    if (sDefaultStandaloneMacroOpticalZoomRatioIndex < 0 || arrayList.size() < 1) {
                        throw new IllegalStateException("The supported optical zoom ratios are probably not configured correctly");
                    }
                    sStandaloneMacroSupportedOpticalZoomRatios = new float[arrayList.size()];
                    int i4 = 0;
                    for (String parseFloat2 : arrayList) {
                        sStandaloneMacroSupportedOpticalZoomRatios[i4] = Float.parseFloat(parseFloat2);
                        i4++;
                    }
                    Log.d(TAG, Arrays.toString(sStandaloneMacroSupportedOpticalZoomRatios) + "[" + sDefaultStandaloneMacroOpticalZoomRatioIndex + "]");
                } catch (Throwable th3) {
                    $closeResource(th2, scanner2);
                    throw th3;
                }
            }
        } catch (Throwable th4) {
            $closeResource(th, scanner);
            throw th4;
        }
    }

    private HybridZoomingSystem() {
    }

    public static float add(float f, float f2) {
        return ((float) (((int) (f * 10.0f)) + ((int) (f2 * 10.0f)))) / 10.0f;
    }

    public static float clamp(float f, float f2, float f3) {
        return f > f3 ? f3 : f < f2 ? f2 : f;
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
        return sStandaloneMacroSupportedOpticalZoomRatios[sStandaloneMacroSupportedOpticalZoomRatios.length - 1];
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
        if (i2 < 0) {
            Log.e(TAG, "The given index must be in range [0, " + length + ")   curIndex error : " + i2);
            i2 = 0;
        } else if (i2 >= length) {
            Log.e(TAG, "The given index must be in range [0, " + length + ")   curIndex error : " + i2);
            i2 = length + -1;
        }
        return fArr[i2];
    }

    public static int getOpticalZoomRatioIndex(int i, float f) {
        float[] fArr = (i == 161 || i == 174) ? sFunOrLiveSupportedOpticalZoomRatios : sSupportedOpticalZoomRatios;
        for (int length = fArr.length - 1; length >= 0; length--) {
            if (f >= fArr[length]) {
                return length;
            }
        }
        throw new IllegalArgumentException("Illegal zoom ratio: " + f);
    }

    public static float[] getSupportedOpticalZoomRatios(int i) {
        return (i == 161 || i == 174) ? sFunOrLiveSupportedOpticalZoomRatios : sSupportedOpticalZoomRatios;
    }

    public static String getZoomRatioHistory(int i, String str) {
        if (i == 165) {
            i = 163;
        }
        String str2 = sZoomRatioHistory.get(Integer.valueOf(i));
        return str2 != null ? str2 : str;
    }

    public static int getZoomingSourceIdentity() {
        return sZoomingSourceIdentity.get();
    }

    public static boolean isOpticalZoomRatio(float f) {
        return Arrays.binarySearch(sSupportedOpticalZoomRatios, f) >= 0;
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
        Log.d(TAG, "setZoomingSourceIdentity(): " + i);
        sZoomingSourceIdentity.set(i);
    }

    public static float sub(float f, float f2) {
        return ((float) (((int) (f * 10.0f)) - ((int) (f2 * 10.0f)))) / 10.0f;
    }

    public static Rect toCropRegion(float f, Rect rect) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Zoom ratio must be greater than 0.0f");
        } else if (rect != null) {
            int width = rect.width() / 2;
            int height = rect.height() / 2;
            float f2 = 2.0f * f;
            int width2 = (int) (((float) rect.width()) / f2);
            int height2 = (int) (((float) rect.height()) / f2);
            Rect rect2 = new Rect();
            rect2.set(width - width2, height - height2, width + width2, height + height2);
            Log.d(TAG, "toCropRegion(): zoom ratio = " + f + ", crop region = " + rect2);
            return rect2;
        } else {
            throw new IllegalArgumentException("activeArraySize must be non null");
        }
    }

    public static float toDecimal(float f) {
        return ((float) ((int) (f * 10.0f))) / 10.0f;
    }

    public static float toFloat(String str, float f) {
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            Log.e(TAG, "Invalid zoom: " + str);
            return f;
        }
    }

    public static String toString(float f) {
        if (0.6f == f) {
            return STRING_ZOOM_RATIO_ULTR;
        }
        if (1.0f == f) {
            return STRING_ZOOM_RATIO_WIDE;
        }
        if (2.0f == f) {
            return STRING_ZOOM_RATIO_TELE;
        }
        if (5.0f == f) {
            return STRING_ZOOM_RATIO_TELE_5X;
        }
        if (0.0f == f) {
            return "macro";
        }
        float decimal = toDecimal(f);
        return String.format(Locale.US, "%.1fx", new Object[]{Float.valueOf(decimal)});
    }
}
