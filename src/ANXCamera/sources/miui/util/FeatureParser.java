package miui.util;

import android.content.res.Resources;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public static boolean getBoolean(String name, boolean defaultValue) {
        Boolean value = sBooleanMap.get(name);
        if (value != null) {
            return value.booleanValue();
        }
        return defaultValue;
    }

    public static String getString(String name) {
        return sStrMap.get(name);
    }

    public static int getInteger(String name, int defaultValue) {
        Integer value = sIntMap.get(name);
        if (value != null) {
            return value.intValue();
        }
        return defaultValue;
    }

    public static int[] getIntArray(String name) {
        ArrayList<Integer> intList = sIntArrMap.get(name);
        if (intList == null) {
            return null;
        }
        int length = intList.size();
        int[] intArr = new int[length];
        for (int i = 0; i < length; i++) {
            intArr[i] = intList.get(i).intValue();
        }
        return intArr;
    }

    public static String[] getStringArray(String name) {
        ArrayList<String> strList = sStrArrMap.get(name);
        if (strList != null) {
            return (String[]) strList.toArray(new String[0]);
        }
        return null;
    }

    public static Float getFloat(String name, float defaultValue) {
        Float value = sFloatMap.get(name);
        return Float.valueOf(value != null ? value.floatValue() : defaultValue);
    }

    public static boolean hasFeature(String name, int type) {
        switch (type) {
            case 1:
                return sBooleanMap.containsKey(name);
            case 2:
                return sIntMap.containsKey(name);
            case 3:
                return sStrMap.containsKey(name);
            case 4:
                return sStrArrMap.containsKey(name);
            case 5:
                return sIntArrMap.containsKey(name);
            case 6:
                return sFloatMap.containsKey(name);
            default:
                return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:73:0x01b3, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01b4, code lost:
        if (r1 != null) goto L_0x01b6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01bd, code lost:
        if (r1 != null) goto L_0x01bf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01c4, code lost:
        if (r1 != null) goto L_0x01c6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01c6, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x01b3 A[ExcHandler: all (r0v3 'th' java.lang.Throwable A[CUSTOM_DECLARE]), PHI: r1 
  PHI: (r1v3 'inputStream' java.io.InputStream) = (r1v0 'inputStream' java.io.InputStream), (r1v0 'inputStream' java.io.InputStream), (r1v5 'inputStream' java.io.InputStream), (r1v0 'inputStream' java.io.InputStream) binds: [B:1:0x0005, B:12:0x003d, B:28:0x00bb, B:17:0x005f] A[DONT_GENERATE, DONT_INLINE], Splitter:B:12:0x003d] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x01bc A[ExcHandler: XmlPullParserException (e org.xmlpull.v1.XmlPullParserException), PHI: r1 
  PHI: (r1v2 'inputStream' java.io.InputStream) = (r1v0 'inputStream' java.io.InputStream), (r1v0 'inputStream' java.io.InputStream), (r1v5 'inputStream' java.io.InputStream), (r1v0 'inputStream' java.io.InputStream) binds: [B:1:0x0005, B:12:0x003d, B:28:0x00bb, B:17:0x005f] A[DONT_GENERATE, DONT_INLINE], Splitter:B:1:0x0005] */
    private static void read() {
        ArrayList<String> strList = null;
        InputStream inputStream = null;
        String fileName = null;
        try {
            if (!"cancro".equals(Build.DEVICE)) {
                fileName = Build.DEVICE + ".xml";
            } else if (Build.MODEL.startsWith("MI 3")) {
                fileName = "cancro_MI3.xml";
            } else if (Build.MODEL.startsWith("MI 4")) {
                fileName = "cancro_MI4.xml";
            }
            inputStream = Resources.getSystem().getAssets().open(ASSET_DIR + fileName);
        } catch (IOException e) {
            Log.i(TAG, "can't find " + fileName + " in assets/" + ASSET_DIR + ",it may be in " + SYSTEM_DIR);
        } catch (XmlPullParserException e2) {
        } catch (Throwable th) {
        }
        if (inputStream == null) {
            File file = new File(SYSTEM_DIR, fileName);
            if (file.exists()) {
                inputStream = new FileInputStream(file);
            } else {
                Log.e(TAG, "both assets/device_features/ and /system/etc/device_features don't exist " + fileName);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                        return;
                    } catch (IOException e3) {
                        return;
                    }
                } else {
                    return;
                }
            }
        }
        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
        parser.setInput(inputStream, "UTF-8");
        String keyName = null;
        ArrayList<Integer> intList = null;
        for (int type = parser.getEventType(); 1 != type; type = parser.next()) {
            switch (type) {
                case 2:
                    String tagName = parser.getName();
                    if (parser.getAttributeCount() > 0) {
                        keyName = parser.getAttributeValue(0);
                    }
                    if (!TAG_INTEGER_ARRAY.equals(tagName)) {
                        if (!TAG_STRING_ARRAY.equals(tagName)) {
                            if (!TAG_BOOL.equals(tagName)) {
                                if (!TAG_INTEGER.equals(tagName)) {
                                    if (!TAG_STRING.equals(tagName)) {
                                        if (!TAG_FLOAT.equals(tagName)) {
                                            if (TAG_ITEM.equals(tagName)) {
                                                if (intList == null) {
                                                    if (strList != null) {
                                                        strList.add(parser.nextText());
                                                        break;
                                                    }
                                                } else {
                                                    intList.add(Integer.valueOf(parser.nextText()));
                                                    break;
                                                }
                                            }
                                        } else {
                                            sFloatMap.put(keyName, Float.valueOf(Float.parseFloat(parser.nextText())));
                                            break;
                                        }
                                    } else {
                                        sStrMap.put(keyName, parser.nextText());
                                        break;
                                    }
                                } else {
                                    sIntMap.put(keyName, Integer.valueOf(parser.nextText()));
                                    break;
                                }
                            } else {
                                sBooleanMap.put(keyName, Boolean.valueOf(parser.nextText()));
                                break;
                            }
                        } else {
                            strList = new ArrayList<>();
                            break;
                        }
                    } else {
                        intList = new ArrayList<>();
                        break;
                    }
                    break;
                case 3:
                    String end_tag_name = parser.getName();
                    if (!TAG_INTEGER_ARRAY.equals(end_tag_name)) {
                        if (TAG_STRING_ARRAY.equals(end_tag_name)) {
                            sStrArrMap.put(keyName, strList);
                            strList = null;
                            break;
                        }
                    } else {
                        sIntArrMap.put(keyName, intList);
                        intList = null;
                        break;
                    }
                    break;
            }
        }
        if (inputStream != null) {
            try {
                inputStream.close();
                return;
            } catch (IOException e4) {
                return;
            }
        } else {
            return;
        }
        throw th;
    }
}
