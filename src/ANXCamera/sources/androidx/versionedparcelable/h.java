package androidx.versionedparcelable;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.internal.view.SupportMenu;
import android.util.SparseArray;
import androidx.versionedparcelable.VersionedParcel.ParcelException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Set;

@RestrictTo({Scope.LIBRARY})
/* compiled from: VersionedParcelStream */
class h extends VersionedParcel {
    private static final int TYPE_BOOLEAN = 5;
    private static final int TYPE_DOUBLE = 7;
    private static final int TYPE_FLOAT = 13;
    private static final int TYPE_INT = 9;
    private static final int TYPE_LONG = 11;
    private static final int TYPE_NULL = 0;
    private static final int TYPE_STRING = 3;
    private static final int TYPE_STRING_ARRAY = 4;
    private static final Charset UTF_16 = Charset.forName("UTF-16");
    private static final int kb = 1;
    private static final int lb = 2;
    private static final int mb = 6;
    private static final int nb = 8;
    private static final int ob = 10;
    private static final int pb = 12;
    private static final int qb = 14;
    private final DataInputStream bb;
    private final DataOutputStream eb;
    private final SparseArray<b> fb = new SparseArray<>();
    private DataInputStream gb;
    private DataOutputStream hb;
    private a ib;
    private boolean jb;

    /* compiled from: VersionedParcelStream */
    private static class a {
        private final DataOutputStream mTarget;
        /* access modifiers changed from: private */
        public final ByteArrayOutputStream rb = new ByteArrayOutputStream();
        /* access modifiers changed from: private */
        public final DataOutputStream sb = new DataOutputStream(this.rb);
        private final int tb;

        a(int i, DataOutputStream dataOutputStream) {
            this.tb = i;
            this.mTarget = dataOutputStream;
        }

        /* access modifiers changed from: 0000 */
        public void ta() throws IOException {
            this.sb.flush();
            int size = this.rb.size();
            this.mTarget.writeInt((this.tb << 16) | (size >= 65535 ? 65535 : size));
            if (size >= 65535) {
                this.mTarget.writeInt(size);
            }
            this.rb.writeTo(this.mTarget);
        }
    }

    /* compiled from: VersionedParcelStream */
    private static class b {
        /* access modifiers changed from: private */
        public final DataInputStream mInputStream;
        private final int mSize;
        /* access modifiers changed from: private */
        public final int tb;

        b(int i, int i2, DataInputStream dataInputStream) throws IOException {
            this.mSize = i2;
            this.tb = i;
            byte[] bArr = new byte[this.mSize];
            dataInputStream.readFully(bArr);
            this.mInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        }
    }

    public h(InputStream inputStream, OutputStream outputStream) {
        DataOutputStream dataOutputStream = null;
        this.bb = inputStream != null ? new DataInputStream(inputStream) : null;
        if (outputStream != null) {
            dataOutputStream = new DataOutputStream(outputStream);
        }
        this.eb = dataOutputStream;
        this.gb = this.bb;
        this.hb = this.eb;
    }

    private void a(int i, String str, Bundle bundle) {
        switch (i) {
            case 0:
                bundle.putParcelable(str, null);
                return;
            case 1:
                bundle.putBundle(str, readBundle());
                return;
            case 2:
                bundle.putBundle(str, readBundle());
                return;
            case 3:
                bundle.putString(str, readString());
                return;
            case 4:
                bundle.putStringArray(str, (String[]) a(new String[0]));
                return;
            case 5:
                bundle.putBoolean(str, readBoolean());
                return;
            case 6:
                bundle.putBooleanArray(str, ma());
                return;
            case 7:
                bundle.putDouble(str, readDouble());
                return;
            case 8:
                bundle.putDoubleArray(str, na());
                return;
            case 9:
                bundle.putInt(str, readInt());
                return;
            case 10:
                bundle.putIntArray(str, pa());
                return;
            case 11:
                bundle.putLong(str, readLong());
                return;
            case 12:
                bundle.putLongArray(str, qa());
                return;
            case 13:
                bundle.putFloat(str, readFloat());
                return;
            case 14:
                bundle.putFloatArray(str, oa());
                return;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown type ");
                sb.append(i);
                throw new RuntimeException(sb.toString());
        }
    }

    private void writeObject(Object obj) {
        if (obj == null) {
            writeInt(0);
        } else if (obj instanceof Bundle) {
            writeInt(1);
            writeBundle((Bundle) obj);
        } else if (obj instanceof String) {
            writeInt(3);
            writeString((String) obj);
        } else if (obj instanceof String[]) {
            writeInt(4);
            writeArray((String[]) obj);
        } else if (obj instanceof Boolean) {
            writeInt(5);
            writeBoolean(((Boolean) obj).booleanValue());
        } else if (obj instanceof boolean[]) {
            writeInt(6);
            writeBooleanArray((boolean[]) obj);
        } else if (obj instanceof Double) {
            writeInt(7);
            writeDouble(((Double) obj).doubleValue());
        } else if (obj instanceof double[]) {
            writeInt(8);
            writeDoubleArray((double[]) obj);
        } else if (obj instanceof Integer) {
            writeInt(9);
            writeInt(((Integer) obj).intValue());
        } else if (obj instanceof int[]) {
            writeInt(10);
            writeIntArray((int[]) obj);
        } else if (obj instanceof Long) {
            writeInt(11);
            writeLong(((Long) obj).longValue());
        } else if (obj instanceof long[]) {
            writeInt(12);
            writeLongArray((long[]) obj);
        } else if (obj instanceof Float) {
            writeInt(13);
            writeFloat(((Float) obj).floatValue());
        } else if (obj instanceof float[]) {
            writeInt(14);
            writeFloatArray((float[]) obj);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Unsupported type ");
            sb.append(obj.getClass());
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public void b(Parcelable parcelable) {
        if (!this.jb) {
            throw new RuntimeException("Parcelables cannot be written to an OutputStream");
        }
    }

    public void c(boolean z, boolean z2) {
        if (z) {
            this.jb = z2;
            return;
        }
        throw new RuntimeException("Serialization of this object is not allowed");
    }

    public boolean g(int i) {
        b bVar = (b) this.fb.get(i);
        if (bVar != null) {
            this.fb.remove(i);
            this.gb = bVar.mInputStream;
            return true;
        }
        while (true) {
            try {
                int readInt = this.bb.readInt();
                int i2 = readInt & SupportMenu.USER_MASK;
                if (i2 == 65535) {
                    i2 = this.bb.readInt();
                }
                b bVar2 = new b((readInt >> 16) & SupportMenu.USER_MASK, i2, this.bb);
                if (bVar2.tb == i) {
                    this.gb = bVar2.mInputStream;
                    return true;
                }
                this.fb.put(bVar2.tb, bVar2);
            } catch (IOException unused) {
                return false;
            }
        }
    }

    public void h(int i) {
        ja();
        this.ib = new a(i, this.eb);
        this.hb = this.ib.sb;
    }

    public void ja() {
        a aVar = this.ib;
        if (aVar != null) {
            try {
                if (aVar.rb.size() != 0) {
                    this.ib.ta();
                }
                this.ib = null;
            } catch (IOException e2) {
                throw new ParcelException(e2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public VersionedParcel ka() {
        return new h(this.gb, this.hb);
    }

    public boolean la() {
        return true;
    }

    public <T extends Parcelable> T ra() {
        return null;
    }

    public boolean readBoolean() {
        try {
            return this.gb.readBoolean();
        } catch (IOException e2) {
            throw new ParcelException(e2);
        }
    }

    public Bundle readBundle() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        Bundle bundle = new Bundle();
        for (int i = 0; i < readInt; i++) {
            a(readInt(), readString(), bundle);
        }
        return bundle;
    }

    public byte[] readByteArray() {
        try {
            int readInt = this.gb.readInt();
            if (readInt <= 0) {
                return null;
            }
            byte[] bArr = new byte[readInt];
            this.gb.readFully(bArr);
            return bArr;
        } catch (IOException e2) {
            throw new ParcelException(e2);
        }
    }

    public double readDouble() {
        try {
            return this.gb.readDouble();
        } catch (IOException e2) {
            throw new ParcelException(e2);
        }
    }

    public float readFloat() {
        try {
            return this.gb.readFloat();
        } catch (IOException e2) {
            throw new ParcelException(e2);
        }
    }

    public int readInt() {
        try {
            return this.gb.readInt();
        } catch (IOException e2) {
            throw new ParcelException(e2);
        }
    }

    public long readLong() {
        try {
            return this.gb.readLong();
        } catch (IOException e2) {
            throw new ParcelException(e2);
        }
    }

    public String readString() {
        try {
            int readInt = this.gb.readInt();
            if (readInt <= 0) {
                return null;
            }
            byte[] bArr = new byte[readInt];
            this.gb.readFully(bArr);
            return new String(bArr, UTF_16);
        } catch (IOException e2) {
            throw new ParcelException(e2);
        }
    }

    public IBinder readStrongBinder() {
        return null;
    }

    public void writeBoolean(boolean z) {
        try {
            this.hb.writeBoolean(z);
        } catch (IOException e2) {
            throw new ParcelException(e2);
        }
    }

    public void writeBundle(Bundle bundle) {
        if (bundle != null) {
            try {
                Set<String> keySet = bundle.keySet();
                this.hb.writeInt(keySet.size());
                for (String str : keySet) {
                    writeString(str);
                    writeObject(bundle.get(str));
                }
            } catch (IOException e2) {
                throw new ParcelException(e2);
            }
        } else {
            this.hb.writeInt(-1);
        }
    }

    public void writeByteArray(byte[] bArr) {
        if (bArr != null) {
            try {
                this.hb.writeInt(bArr.length);
                this.hb.write(bArr);
            } catch (IOException e2) {
                throw new ParcelException(e2);
            }
        } else {
            this.hb.writeInt(-1);
        }
    }

    public void writeByteArray(byte[] bArr, int i, int i2) {
        if (bArr != null) {
            try {
                this.hb.writeInt(i2);
                this.hb.write(bArr, i, i2);
            } catch (IOException e2) {
                throw new ParcelException(e2);
            }
        } else {
            this.hb.writeInt(-1);
        }
    }

    public void writeDouble(double d2) {
        try {
            this.hb.writeDouble(d2);
        } catch (IOException e2) {
            throw new ParcelException(e2);
        }
    }

    public void writeFloat(float f2) {
        try {
            this.hb.writeFloat(f2);
        } catch (IOException e2) {
            throw new ParcelException(e2);
        }
    }

    public void writeInt(int i) {
        try {
            this.hb.writeInt(i);
        } catch (IOException e2) {
            throw new ParcelException(e2);
        }
    }

    public void writeLong(long j) {
        try {
            this.hb.writeLong(j);
        } catch (IOException e2) {
            throw new ParcelException(e2);
        }
    }

    public void writeString(String str) {
        if (str != null) {
            try {
                byte[] bytes = str.getBytes(UTF_16);
                this.hb.writeInt(bytes.length);
                this.hb.write(bytes);
            } catch (IOException e2) {
                throw new ParcelException(e2);
            }
        } else {
            this.hb.writeInt(-1);
        }
    }

    public void writeStrongBinder(IBinder iBinder) {
        if (!this.jb) {
            throw new RuntimeException("Binders cannot be written to an OutputStream");
        }
    }

    public void writeStrongInterface(IInterface iInterface) {
        if (!this.jb) {
            throw new RuntimeException("Binders cannot be written to an OutputStream");
        }
    }
}
