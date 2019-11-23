package androidx.versionedparcelable;

import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.NetworkOnMainThreadException;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseBooleanArray;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public abstract class VersionedParcel {
    private static final String TAG = "VersionedParcel";
    private static final int TYPE_STRING = 4;
    private static final int ao = -1;
    private static final int ap = -2;
    private static final int aq = -3;
    private static final int ar = -4;
    private static final int as = -5;
    private static final int at = -6;
    private static final int au = -7;
    private static final int av = -9;
    private static final int aw = 1;
    private static final int ax = 2;
    private static final int ay = 3;
    private static final int az = 5;

    public static class ParcelException extends RuntimeException {
        public ParcelException(Throwable th) {
            super(th);
        }
    }

    protected static <T extends g> T a(String str, VersionedParcel versionedParcel) {
        try {
            return (g) Class.forName(str, true, VersionedParcel.class.getClassLoader()).getDeclaredMethod("read", new Class[]{VersionedParcel.class}).invoke((Object) null, new Object[]{versionedParcel});
        } catch (IllegalAccessException e) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e);
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e2.getCause());
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e3);
        } catch (ClassNotFoundException e4) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e4);
        }
    }

    private static Class a(Class<? extends g> cls) throws ClassNotFoundException {
        return Class.forName(String.format("%s.%sParcelizer", new Object[]{cls.getPackage().getName(), cls.getSimpleName()}), false, cls.getClassLoader());
    }

    protected static <T extends g> void a(T t, VersionedParcel versionedParcel) {
        try {
            d(t).getDeclaredMethod("write", new Class[]{t.getClass(), VersionedParcel.class}).invoke((Object) null, new Object[]{t, versionedParcel});
        } catch (IllegalAccessException e) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e);
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e2.getCause());
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e3);
        } catch (ClassNotFoundException e4) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e4);
        }
    }

    private Exception b(int i, String str) {
        return c(i, str);
    }

    private <T> int c(T t) {
        if (t instanceof String) {
            return 4;
        }
        if (t instanceof Parcelable) {
            return 2;
        }
        if (t instanceof g) {
            return 1;
        }
        if (t instanceof Serializable) {
            return 3;
        }
        if (t instanceof IBinder) {
            return 5;
        }
        throw new IllegalArgumentException(t.getClass().getName() + " cannot be VersionedParcelled");
    }

    private Exception c(int i, String str) {
        switch (i) {
            case av /*-9*/:
                return (Exception) s();
            case -7:
                return new UnsupportedOperationException(str);
            case -6:
                return new NetworkOnMainThreadException();
            case -5:
                return new IllegalStateException(str);
            case -4:
                return new NullPointerException(str);
            case -3:
                return new IllegalArgumentException(str);
            case -2:
                return new BadParcelableException(str);
            case -1:
                return new SecurityException(str);
            default:
                return new RuntimeException("Unknown exception code: " + i + " msg " + str);
        }
    }

    private void c(g gVar) {
        try {
            writeString(a((Class<? extends g>) gVar.getClass()).getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(gVar.getClass().getSimpleName() + " does not have a Parcelizer", e);
        }
    }

    private static <T extends g> Class d(T t) throws ClassNotFoundException {
        return a((Class<? extends g>) t.getClass());
    }

    @NonNull
    protected static Throwable getRootCause(@NonNull Throwable th) {
        while (th.getCause() != null) {
            th = th.getCause();
        }
        return th;
    }

    private int readExceptionCode() {
        return readInt();
    }

    private void writeSerializable(Serializable serializable) {
        if (serializable == null) {
            writeString((String) null);
            return;
        }
        String name = serializable.getClass().getName();
        writeString(name);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(serializable);
            objectOutputStream.close();
            writeByteArray(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("VersionedParcelable encountered IOException writing serializable object (name = " + name + ")", e);
        }
    }

    public <T extends Parcelable> T a(T t, int i) {
        return !c(i) ? t : s();
    }

    public void a(byte b2, int i) {
        d(i);
        writeInt(b2);
    }

    public void a(double d, int i) {
        d(i);
        writeDouble(d);
    }

    public void a(float f, int i) {
        d(i);
        writeFloat(f);
    }

    public void a(int i, int i2) {
        d(i2);
        writeInt(i);
    }

    public void a(long j, int i) {
        d(i);
        writeLong(j);
    }

    public void a(Bundle bundle, int i) {
        d(i);
        writeBundle(bundle);
    }

    public void a(IBinder iBinder, int i) {
        d(i);
        writeStrongBinder(iBinder);
    }

    public void a(IInterface iInterface, int i) {
        d(i);
        writeStrongInterface(iInterface);
    }

    @RequiresApi(api = 21)
    public void a(Size size, int i) {
        d(i);
        writeBoolean(size != null);
        if (size != null) {
            writeInt(size.getWidth());
            writeInt(size.getHeight());
        }
    }

    @RequiresApi(api = 21)
    public void a(SizeF sizeF, int i) {
        d(i);
        writeBoolean(sizeF != null);
        if (sizeF != null) {
            writeFloat(sizeF.getWidth());
            writeFloat(sizeF.getHeight());
        }
    }

    public void a(SparseBooleanArray sparseBooleanArray, int i) {
        d(i);
        if (sparseBooleanArray == null) {
            writeInt(-1);
            return;
        }
        int size = sparseBooleanArray.size();
        writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            writeInt(sparseBooleanArray.keyAt(i2));
            writeBoolean(sparseBooleanArray.valueAt(i2));
        }
    }

    public void a(g gVar, int i) {
        d(i);
        b(gVar);
    }

    public void a(Serializable serializable, int i) {
        d(i);
        writeSerializable(serializable);
    }

    public void a(Exception exc, int i) {
        d(i);
        if (exc == null) {
            writeNoException();
            return;
        }
        int i2 = 0;
        if ((exc instanceof Parcelable) && exc.getClass().getClassLoader() == Parcelable.class.getClassLoader()) {
            i2 = av;
        } else if (exc instanceof SecurityException) {
            i2 = -1;
        } else if (exc instanceof BadParcelableException) {
            i2 = -2;
        } else if (exc instanceof IllegalArgumentException) {
            i2 = -3;
        } else if (exc instanceof NullPointerException) {
            i2 = -4;
        } else if (exc instanceof IllegalStateException) {
            i2 = -5;
        } else if (exc instanceof NetworkOnMainThreadException) {
            i2 = -6;
        } else if (exc instanceof UnsupportedOperationException) {
            i2 = -7;
        }
        writeInt(i2);
        if (i2 != 0) {
            writeString(exc.getMessage());
            if (i2 == av) {
                b((Parcelable) exc);
            }
        } else if (exc instanceof RuntimeException) {
            throw ((RuntimeException) exc);
        } else {
            throw new RuntimeException(exc);
        }
    }

    public void a(String str, int i) {
        d(i);
        writeString(str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0032, code lost:
        if (r0 >= r4) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0034, code lost:
        writeString((java.lang.String) r3.get(r0));
        r0 = r0 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0040, code lost:
        if (r0 >= r4) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0042, code lost:
        writeSerializable((java.io.Serializable) r3.get(r0));
        r0 = r0 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x004e, code lost:
        if (r0 >= r4) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0050, code lost:
        b((android.os.Parcelable) r3.get(r0));
        r0 = r0 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x005c, code lost:
        if (r0 >= r4) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x005e, code lost:
        b((androidx.versionedparcelable.g) r3.get(r0));
        r0 = r0 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0024, code lost:
        if (r0 >= r4) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0026, code lost:
        writeStrongBinder((android.os.IBinder) r3.get(r0));
        r0 = r0 + 1;
     */
    public <T> void a(List<T> list, int i) {
        d(i);
        if (list == null) {
            writeInt(-1);
            return;
        }
        int size = list.size();
        writeInt(size);
        if (size > 0) {
            int i2 = 0;
            int c = c(list.get(0));
            writeInt(c);
            switch (c) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    return;
            }
        }
    }

    public void a(boolean z, int i) {
        d(i);
        writeBoolean(z);
    }

    public void a(boolean z, boolean z2) {
    }

    public void a(byte[] bArr, int i) {
        d(i);
        writeByteArray(bArr);
    }

    public void a(byte[] bArr, int i, int i2, int i3) {
        d(i3);
        writeByteArray(bArr, i, i2);
    }

    public void a(char[] cArr, int i) {
        d(i);
        if (cArr != null) {
            writeInt(r4);
            for (char writeInt : cArr) {
                writeInt(writeInt);
            }
            return;
        }
        writeInt(-1);
    }

    public void a(double[] dArr, int i) {
        d(i);
        writeDoubleArray(dArr);
    }

    public void a(float[] fArr, int i) {
        d(i);
        writeFloatArray(fArr);
    }

    public void a(int[] iArr, int i) {
        d(i);
        writeIntArray(iArr);
    }

    public void a(long[] jArr, int i) {
        d(i);
        writeLongArray(jArr);
    }

    public <T> void a(T[] tArr, int i) {
        d(i);
        writeArray(tArr);
    }

    public void a(boolean[] zArr, int i) {
        d(i);
        writeBooleanArray(zArr);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    public <T> T[] a(T[] tArr) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList(readInt);
        if (readInt != 0) {
            int readInt2 = readInt();
            if (readInt >= 0) {
                switch (readInt2) {
                    case 1:
                        while (readInt > 0) {
                            arrayList.add(y());
                            readInt--;
                        }
                        break;
                    case 2:
                        while (readInt > 0) {
                            arrayList.add(s());
                            readInt--;
                        }
                        break;
                    case 3:
                        while (readInt > 0) {
                            arrayList.add(readSerializable());
                            readInt--;
                        }
                        break;
                    case 4:
                        while (readInt > 0) {
                            arrayList.add(readString());
                            readInt--;
                        }
                        break;
                    case 5:
                        while (readInt > 0) {
                            arrayList.add(readStrongBinder());
                            readInt--;
                        }
                        break;
                }
            } else {
                return null;
            }
        }
        return arrayList.toArray(tArr);
    }

    public byte b(byte b2, int i) {
        return !c(i) ? b2 : (byte) (readInt() & 255);
    }

    public double b(double d, int i) {
        return !c(i) ? d : readDouble();
    }

    public float b(float f, int i) {
        return !c(i) ? f : readFloat();
    }

    public long b(long j, int i) {
        return !c(i) ? j : readLong();
    }

    public Bundle b(Bundle bundle, int i) {
        return !c(i) ? bundle : readBundle();
    }

    public IBinder b(IBinder iBinder, int i) {
        return !c(i) ? iBinder : readStrongBinder();
    }

    @RequiresApi(api = 21)
    public Size b(Size size, int i) {
        if (!c(i)) {
            return size;
        }
        if (readBoolean()) {
            return new Size(readInt(), readInt());
        }
        return null;
    }

    @RequiresApi(api = 21)
    public SizeF b(SizeF sizeF, int i) {
        if (!c(i)) {
            return sizeF;
        }
        if (readBoolean()) {
            return new SizeF(readFloat(), readFloat());
        }
        return null;
    }

    public SparseBooleanArray b(SparseBooleanArray sparseBooleanArray, int i) {
        if (!c(i)) {
            return sparseBooleanArray;
        }
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        SparseBooleanArray sparseBooleanArray2 = new SparseBooleanArray(readInt);
        for (int i2 = 0; i2 < readInt; i2++) {
            sparseBooleanArray2.put(readInt(), readBoolean());
        }
        return sparseBooleanArray2;
    }

    public <T extends g> T b(T t, int i) {
        return !c(i) ? t : y();
    }

    public Exception b(Exception exc, int i) {
        if (!c(i)) {
            return exc;
        }
        int readExceptionCode = readExceptionCode();
        return readExceptionCode != 0 ? b(readExceptionCode, readString()) : exc;
    }

    public String b(String str, int i) {
        return !c(i) ? str : readString();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    public <T> List<T> b(List<T> list, int i) {
        if (!c(i)) {
            return list;
        }
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList(readInt);
        if (readInt != 0) {
            int readInt2 = readInt();
            if (readInt >= 0) {
                switch (readInt2) {
                    case 1:
                        while (readInt > 0) {
                            arrayList.add(y());
                            readInt--;
                        }
                        break;
                    case 2:
                        while (readInt > 0) {
                            arrayList.add(s());
                            readInt--;
                        }
                        break;
                    case 3:
                        while (readInt > 0) {
                            arrayList.add(readSerializable());
                            readInt--;
                        }
                        break;
                    case 4:
                        while (readInt > 0) {
                            arrayList.add(readString());
                            readInt--;
                        }
                        break;
                    case 5:
                        while (readInt > 0) {
                            arrayList.add(readStrongBinder());
                            readInt--;
                        }
                        break;
                }
            } else {
                return null;
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public abstract void b(Parcelable parcelable);

    /* access modifiers changed from: protected */
    public void b(g gVar) {
        if (gVar == null) {
            writeString((String) null);
            return;
        }
        c(gVar);
        VersionedParcel r = r();
        a(gVar, r);
        r.q();
    }

    public boolean b(boolean z, int i) {
        return !c(i) ? z : readBoolean();
    }

    public byte[] b(byte[] bArr, int i) {
        return !c(i) ? bArr : readByteArray();
    }

    public char[] b(char[] cArr, int i) {
        if (!c(i)) {
            return cArr;
        }
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        char[] cArr2 = new char[readInt];
        for (int i2 = 0; i2 < readInt; i2++) {
            cArr2[i2] = (char) readInt();
        }
        return cArr2;
    }

    public double[] b(double[] dArr, int i) {
        return !c(i) ? dArr : x();
    }

    public float[] b(float[] fArr, int i) {
        return !c(i) ? fArr : w();
    }

    public int[] b(int[] iArr, int i) {
        return !c(i) ? iArr : u();
    }

    public long[] b(long[] jArr, int i) {
        return !c(i) ? jArr : v();
    }

    public <T> T[] b(T[] tArr, int i) {
        return !c(i) ? tArr : a(tArr);
    }

    public boolean[] b(boolean[] zArr, int i) {
        return !c(i) ? zArr : t();
    }

    /* access modifiers changed from: protected */
    public abstract boolean c(int i);

    /* access modifiers changed from: protected */
    public abstract void d(int i);

    public boolean p() {
        return false;
    }

    /* access modifiers changed from: protected */
    public abstract void q();

    /* access modifiers changed from: protected */
    public abstract VersionedParcel r();

    /* access modifiers changed from: protected */
    public abstract boolean readBoolean();

    /* access modifiers changed from: protected */
    public abstract Bundle readBundle();

    /* access modifiers changed from: protected */
    public abstract byte[] readByteArray();

    /* access modifiers changed from: protected */
    public abstract double readDouble();

    /* access modifiers changed from: protected */
    public abstract float readFloat();

    /* access modifiers changed from: protected */
    public abstract int readInt();

    public int readInt(int i, int i2) {
        return !c(i2) ? i : readInt();
    }

    /* access modifiers changed from: protected */
    public abstract long readLong();

    /* access modifiers changed from: protected */
    public Serializable readSerializable() {
        String readString = readString();
        if (readString == null) {
            return null;
        }
        try {
            return (Serializable) new ObjectInputStream(new ByteArrayInputStream(readByteArray())) {
                /* access modifiers changed from: protected */
                public Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                    Class<?> cls = Class.forName(objectStreamClass.getName(), false, getClass().getClassLoader());
                    return cls != null ? cls : super.resolveClass(objectStreamClass);
                }
            }.readObject();
        } catch (IOException e) {
            throw new RuntimeException("VersionedParcelable encountered IOException reading a Serializable object (name = " + readString + ")", e);
        } catch (ClassNotFoundException e2) {
            throw new RuntimeException("VersionedParcelable encountered ClassNotFoundException reading a Serializable object (name = " + readString + ")", e2);
        }
    }

    /* access modifiers changed from: protected */
    public abstract String readString();

    /* access modifiers changed from: protected */
    public abstract IBinder readStrongBinder();

    /* access modifiers changed from: protected */
    public abstract <T extends Parcelable> T s();

    /* access modifiers changed from: protected */
    public boolean[] t() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        boolean[] zArr = new boolean[readInt];
        for (int i = 0; i < readInt; i++) {
            zArr[i] = readInt() != 0;
        }
        return zArr;
    }

    /* access modifiers changed from: protected */
    public int[] u() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        int[] iArr = new int[readInt];
        for (int i = 0; i < readInt; i++) {
            iArr[i] = readInt();
        }
        return iArr;
    }

    /* access modifiers changed from: protected */
    public long[] v() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        long[] jArr = new long[readInt];
        for (int i = 0; i < readInt; i++) {
            jArr[i] = readLong();
        }
        return jArr;
    }

    /* access modifiers changed from: protected */
    public float[] w() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        float[] fArr = new float[readInt];
        for (int i = 0; i < readInt; i++) {
            fArr[i] = readFloat();
        }
        return fArr;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002a, code lost:
        writeString((java.lang.String) r4[r1]);
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0034, code lost:
        if (r1 >= r0) goto L_0x0058;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0036, code lost:
        writeSerializable((java.io.Serializable) r4[r1]);
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0040, code lost:
        if (r1 >= r0) goto L_0x0058;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0042, code lost:
        b((android.os.Parcelable) r4[r1]);
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004c, code lost:
        if (r1 >= r0) goto L_0x0058;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004e, code lost:
        b((androidx.versionedparcelable.g) r4[r1]);
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001c, code lost:
        if (r1 >= r0) goto L_0x0058;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001e, code lost:
        writeStrongBinder((android.os.IBinder) r4[r1]);
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0028, code lost:
        if (r1 >= r0) goto L_0x0058;
     */
    public <T> void writeArray(T[] tArr) {
        if (tArr == null) {
            writeInt(-1);
            return;
        }
        int length = tArr.length;
        writeInt(length);
        if (length > 0) {
            int i = 0;
            int c = c(tArr[0]);
            writeInt(c);
            switch (c) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void writeBoolean(boolean z);

    /* access modifiers changed from: protected */
    public void writeBooleanArray(boolean[] zArr) {
        if (zArr != null) {
            writeInt(r0);
            for (boolean z : zArr) {
                writeInt(z ? 1 : 0);
            }
            return;
        }
        writeInt(-1);
    }

    /* access modifiers changed from: protected */
    public abstract void writeBundle(Bundle bundle);

    /* access modifiers changed from: protected */
    public abstract void writeByteArray(byte[] bArr);

    /* access modifiers changed from: protected */
    public abstract void writeByteArray(byte[] bArr, int i, int i2);

    /* access modifiers changed from: protected */
    public abstract void writeDouble(double d);

    /* access modifiers changed from: protected */
    public void writeDoubleArray(double[] dArr) {
        if (dArr != null) {
            writeInt(r0);
            for (double writeDouble : dArr) {
                writeDouble(writeDouble);
            }
            return;
        }
        writeInt(-1);
    }

    /* access modifiers changed from: protected */
    public abstract void writeFloat(float f);

    /* access modifiers changed from: protected */
    public void writeFloatArray(float[] fArr) {
        if (fArr != null) {
            writeInt(r0);
            for (float writeFloat : fArr) {
                writeFloat(writeFloat);
            }
            return;
        }
        writeInt(-1);
    }

    /* access modifiers changed from: protected */
    public abstract void writeInt(int i);

    /* access modifiers changed from: protected */
    public void writeIntArray(int[] iArr) {
        if (iArr != null) {
            writeInt(r0);
            for (int writeInt : iArr) {
                writeInt(writeInt);
            }
            return;
        }
        writeInt(-1);
    }

    /* access modifiers changed from: protected */
    public abstract void writeLong(long j);

    /* access modifiers changed from: protected */
    public void writeLongArray(long[] jArr) {
        if (jArr != null) {
            writeInt(r0);
            for (long writeLong : jArr) {
                writeLong(writeLong);
            }
            return;
        }
        writeInt(-1);
    }

    /* access modifiers changed from: protected */
    public void writeNoException() {
        writeInt(0);
    }

    public void writeParcelable(Parcelable parcelable, int i) {
        d(i);
        b(parcelable);
    }

    /* access modifiers changed from: protected */
    public abstract void writeString(String str);

    /* access modifiers changed from: protected */
    public abstract void writeStrongBinder(IBinder iBinder);

    /* access modifiers changed from: protected */
    public abstract void writeStrongInterface(IInterface iInterface);

    /* access modifiers changed from: protected */
    public double[] x() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        double[] dArr = new double[readInt];
        for (int i = 0; i < readInt; i++) {
            dArr[i] = readDouble();
        }
        return dArr;
    }

    /* access modifiers changed from: protected */
    public <T extends g> T y() {
        String readString = readString();
        if (readString == null) {
            return null;
        }
        return a(readString, r());
    }
}
