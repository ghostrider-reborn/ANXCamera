package com.arcsoft.camera.utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: MPoint */
public class b implements Parcelable {

    /* renamed from: c reason: collision with root package name */
    public static final Creator<b> f170c = new f();

    /* renamed from: a reason: collision with root package name */
    public int f171a;

    /* renamed from: b reason: collision with root package name */
    public int f172b;

    public b() {
    }

    public b(int i, int i2) {
        this.f171a = i;
        this.f172b = i2;
    }

    public b(b bVar) {
        this.f171a = bVar.f171a;
        this.f172b = bVar.f172b;
    }

    public int a() {
        return 0;
    }

    public void a(Parcel parcel) {
        this.f171a = parcel.readInt();
        this.f172b = parcel.readInt();
    }

    public void a(Parcel parcel, int i) {
        parcel.writeInt(this.f171a);
        parcel.writeInt(this.f172b);
    }

    public final boolean a(int i, int i2) {
        return this.f171a == i && this.f172b == i2;
    }

    public int b() {
        return (this.f171a * 31) + this.f172b;
    }

    public final void c() {
        this.f171a = -this.f171a;
        this.f172b = -this.f172b;
    }

    public String d() {
        StringBuilder sb = new StringBuilder();
        sb.append("Point(");
        sb.append(this.f171a);
        sb.append(", ");
        sb.append(this.f172b);
        sb.append(")");
        return sb.toString();
    }

    public final void d(int i, int i2) {
        this.f171a += i;
        this.f172b += i2;
    }

    public void e(int i, int i2) {
        this.f171a = i;
        this.f172b = i2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001b, code lost:
        if (r3.f172b != r4.f172b) goto L_0x001d;
     */
    public boolean h(Object obj) {
        if (this != obj) {
            if (obj != null && b.class == obj.getClass()) {
                b bVar = (b) obj;
                if (this.f171a != bVar.f171a) {
                    return false;
                }
            }
            return false;
        }
        return true;
    }
}
