package miui.reflect;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.WeakHashMap;

public class ReflectUtils {
    public static final String OBJECT_CONSTRUCTOR = "<init>";
    private static final String OD = "this$0";
    private static Class<?>[] OE = {Boolean.TYPE, Byte.TYPE, Character.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Void.TYPE};
    private static String[] OF = {Field.BOOLEAN_SIGNATURE_PRIMITIVE, Field.BYTE_SIGNATURE_PRIMITIVE, Field.CHAR_SIGNATURE_PRIMITIVE, "S", Field.INT_SIGNATURE_PRIMITIVE, Field.LONG_SIGNATURE_PRIMITIVE, Field.FLOAT_SIGNATURE_PRIMITIVE, Field.DOUBLE_SIGNATURE_PRIMITIVE, "V"};
    private static final WeakHashMap<Object, HashMap<String, Object>> OG = new WeakHashMap<>();

    protected ReflectUtils() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    public static Object getSurroundingThis(Object obj) {
        return Field.of(obj.getClass(), OD, getSignature(obj.getClass())).get(obj);
    }

    public static String getSignature(Class<?> cls) {
        for (int i = 0; i < OE.length; i++) {
            if (cls == OE[i]) {
                return OF[i];
            }
        }
        return getSignature(cls.getName());
    }

    public static String getSignature(String str) {
        for (int i = 0; i < OE.length; i++) {
            if (OE[i].getName().equals(str)) {
                str = OF[i];
            }
        }
        String replace = str.replace(".", "/");
        if (replace.startsWith("[")) {
            return replace;
        }
        return "L" + replace + ";";
    }

    public static String getSignature(Class<?>[] clsArr, Class<?> cls) {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        if (clsArr != null) {
            for (Class<?> signature : clsArr) {
                sb.append(getSignature(signature));
            }
        }
        sb.append(')');
        sb.append(getSignature(cls));
        return sb.toString();
    }

    public static void updateField(Class<?> cls, Object obj, Object obj2, Object obj3) throws IllegalArgumentException {
        if (cls == null && obj == null) {
            throw new IllegalArgumentException("clazz and holder cannot be all null");
        }
        if (cls == null) {
            cls = obj.getClass();
        }
        while (cls != null) {
            Field[] declaredFields = cls.getDeclaredFields();
            int length = declaredFields.length;
            int i = 0;
            while (i < length) {
                Field field = declaredFields[i];
                field.setAccessible(true);
                try {
                    if (field.get(obj) == obj2) {
                        field.set(obj, obj3);
                    }
                    i++;
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            cls = cls.getSuperclass();
        }
    }

    public static Object setAdditionalField(Object obj, String str, Object obj2) {
        HashMap hashMap;
        Object put;
        if (obj == null) {
            throw new NullPointerException("object must not be null");
        } else if (str != null) {
            synchronized (OG) {
                hashMap = OG.get(obj);
                if (hashMap == null) {
                    hashMap = new HashMap();
                    OG.put(obj, hashMap);
                }
            }
            synchronized (hashMap) {
                put = hashMap.put(str, obj2);
            }
            return put;
        } else {
            throw new NullPointerException("fieldName must not be null");
        }
    }

    public static Object getAdditionalField(Object obj, String str) {
        Object obj2;
        if (obj == null) {
            throw new NullPointerException("object must not be null");
        } else if (str != null) {
            synchronized (OG) {
                HashMap hashMap = OG.get(obj);
                if (hashMap == null) {
                    return null;
                }
                synchronized (hashMap) {
                    obj2 = hashMap.get(str);
                }
                return obj2;
            }
        } else {
            throw new NullPointerException("fieldName must not be null");
        }
    }

    public static Object removeAdditionalField(Object obj, String str) {
        Object remove;
        if (obj == null) {
            throw new NullPointerException("object must not be null");
        } else if (str != null) {
            synchronized (OG) {
                HashMap hashMap = OG.get(obj);
                if (hashMap == null) {
                    return null;
                }
                synchronized (hashMap) {
                    remove = hashMap.remove(str);
                }
                return remove;
            }
        } else {
            throw new NullPointerException("fieldName must not be null");
        }
    }
}
