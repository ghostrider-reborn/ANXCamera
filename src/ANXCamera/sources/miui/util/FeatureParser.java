package miui.util;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import miui.os.Build;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class FeatureParser {
    private static final String ASSET_DIR = "device_features/";
    private static final String SYSTEM_DIR = "/system/etc/device_features";
    private static final String TAG = "FeatureParser";
    private static final String TAG_BOOL = "bool";
    private static final String TAG_FLOAT = "float";
    private static final String TAG_INTEGER = "integer";
    private static final String TAG_INTEGER_ARRAY = "integer-array";
    private static final String TAG_ITEM = "item";
    private static final String TAG_STRING = "string";
    private static final String TAG_STRING_ARRAY = "string-array";
    public static final int TYPE_BOOL = 1;
    public static final int TYPE_FLOAT = 6;
    public static final int TYPE_INTEGER = 2;
    public static final int TYPE_INTEGER_ARRAY = 5;
    public static final int TYPE_STRING = 3;
    public static final int TYPE_STRING_ARRAY = 4;
    private static HashMap<String, Boolean> sBooleanMap = new HashMap<>();
    private static HashMap<String, Float> sFloatMap = new HashMap<>();
    private static HashMap<String, ArrayList<Integer>> sIntArrMap = new HashMap<>();
    private static HashMap<String, Integer> sIntMap = new HashMap<>();
    private static HashMap<String, ArrayList<String>> sStrArrMap = new HashMap<>();
    private static HashMap<String, String> sStrMap = new HashMap<>();

    static {
        read();
    }

    public static boolean getBoolean(String str, boolean z) {
        Boolean bool = (Boolean) sBooleanMap.get(str);
        return bool != null ? bool.booleanValue() : z;
    }

    public static Float getFloat(String str, float f2) {
        Float f3 = (Float) sFloatMap.get(str);
        return Float.valueOf(f3 != null ? f3.floatValue() : f2);
    }

    public static int[] getIntArray(String str) {
        ArrayList arrayList = (ArrayList) sIntArrMap.get(str);
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = ((Integer) arrayList.get(i)).intValue();
        }
        return iArr;
    }

    public static int getInteger(String str, int i) {
        Integer num = (Integer) sIntMap.get(str);
        return num != null ? num.intValue() : i;
    }

    public static String getString(String str) {
        return (String) sStrMap.get(str);
    }

    public static String[] getStringArray(String str) {
        ArrayList arrayList = (ArrayList) sStrArrMap.get(str);
        if (arrayList != null) {
            return (String[]) arrayList.toArray(new String[0]);
        }
        return null;
    }

    public static boolean hasFeature(String str, int i) {
        switch (i) {
            case 1:
                return sBooleanMap.containsKey(str);
            case 2:
                return sIntMap.containsKey(str);
            case 3:
                return sStrMap.containsKey(str);
            case 4:
                return sStrArrMap.containsKey(str);
            case 5:
                return sIntArrMap.containsKey(str);
            case 6:
                return sFloatMap.containsKey(str);
            default:
                return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r6 = new java.lang.StringBuilder();
        r6.append("can't find ");
        r6.append(r4);
        r6.append(" in assets/");
        r6.append(r1);
        r6.append(",it may be in ");
        r6.append(r0);
        android.util.Log.i(r2, r6.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x01ab, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x01ac, code lost:
        if (r3 != null) goto L_0x01ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x01ab A[ExcHandler:  FINALLY, PHI: r3 
  PHI: (r3v3 java.io.InputStream) = (r3v0 java.io.InputStream), (r3v0 java.io.InputStream), (r3v5 java.io.InputStream), (r3v5 java.io.InputStream), (r3v0 java.io.InputStream) binds: [B:1:0x000a, B:12:0x0042, B:28:0x00b5, B:37:0x00d9, B:16:0x0060] A[DONT_GENERATE, DONT_INLINE], Splitter:B:12:0x0042] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x01b4 A[ExcHandler: XmlPullParserException (e org.xmlpull.v1.XmlPullParserException), PHI: r3 
  PHI: (r3v2 java.io.InputStream) = (r3v0 java.io.InputStream), (r3v0 java.io.InputStream), (r3v5 java.io.InputStream), (r3v5 java.io.InputStream), (r3v0 java.io.InputStream) binds: [B:1:0x000a, B:12:0x0042, B:28:0x00b5, B:37:0x00d9, B:16:0x0060] A[DONT_GENERATE, DONT_INLINE], Splitter:B:1:0x000a] */
    private static void read() {
        String str = SYSTEM_DIR;
        String str2 = ASSET_DIR;
        String str3 = TAG;
        boolean z = null;
        String str4 = null;
        try {
            z = "cancro".equals(Build.DEVICE);
            if (!z) {
                StringBuilder sb = new StringBuilder();
                sb.append(Build.DEVICE);
                sb.append(".xml");
                str4 = sb.toString();
            } else if (Build.MODEL.startsWith("MI 3")) {
                str4 = "cancro_MI3.xml";
            } else if (Build.MODEL.startsWith("MI 4")) {
                str4 = "cancro_MI4.xml";
            }
            AssetManager assets = Resources.getSystem().getAssets();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(str4);
            z = assets.open(sb2.toString());
            if (z == null) {
                File file = new File(str, str4);
                z = file.exists();
                if (z) {
                    z = new FileInputStream(file);
                } else {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("both assets/device_features/ and /system/etc/device_features don't exist ");
                    sb3.append(str4);
                    Log.e(str3, sb3.toString());
                    if (z != null) {
                        try {
                            z.close();
                        } catch (IOException e2) {
                        }
                    }
                    return;
                }
            }
            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
            newPullParser.setInput(z, "UTF-8");
            Object obj = null;
            ArrayList arrayList = null;
            ArrayList arrayList2 = null;
            for (int eventType = newPullParser.getEventType(); 1 != eventType; eventType = newPullParser.next()) {
                String str5 = TAG_STRING_ARRAY;
                String str6 = TAG_INTEGER_ARRAY;
                if (eventType == 2) {
                    String name = newPullParser.getName();
                    if (newPullParser.getAttributeCount() > 0) {
                        obj = newPullParser.getAttributeValue(0);
                    }
                    if (str6.equals(name)) {
                        arrayList = new ArrayList();
                    } else if (str5.equals(name)) {
                        arrayList2 = new ArrayList();
                    } else if (TAG_BOOL.equals(name)) {
                        sBooleanMap.put(obj, Boolean.valueOf(newPullParser.nextText()));
                    } else if (TAG_INTEGER.equals(name)) {
                        sIntMap.put(obj, Integer.valueOf(newPullParser.nextText()));
                    } else if (TAG_STRING.equals(name)) {
                        sStrMap.put(obj, newPullParser.nextText());
                    } else if (TAG_FLOAT.equals(name)) {
                        sFloatMap.put(obj, Float.valueOf(Float.parseFloat(newPullParser.nextText())));
                    } else if (TAG_ITEM.equals(name)) {
                        if (arrayList != null) {
                            arrayList.add(Integer.valueOf(newPullParser.nextText()));
                        } else if (arrayList2 != null) {
                            arrayList2.add(newPullParser.nextText());
                        }
                    }
                } else if (eventType == 3) {
                    String name2 = newPullParser.getName();
                    if (str6.equals(name2)) {
                        sIntArrMap.put(obj, arrayList);
                        arrayList = null;
                    } else if (str5.equals(name2)) {
                        sStrArrMap.put(obj, arrayList2);
                        arrayList2 = null;
                    }
                }
            }
            try {
                z.close();
            } catch (IOException e3) {
            }
        } catch (IOException e4) {
            if (z != null) {
                z.close();
            }
        } catch (XmlPullParserException e5) {
        } finally {
        }
        return;
    }
}
