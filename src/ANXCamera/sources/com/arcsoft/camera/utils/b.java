package com.arcsoft.camera.utils;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: MPoint */
public class b implements Parcelable {

    /* renamed from: c  reason: collision with root package name */
    public static final Parcelable.Creator<b> f170c = new f();

    /* renamed from: a  reason: collision with root package name */
    public int f171a;

    /* renamed from: b  reason: collision with root package name */
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
        return "Point(" + this.f171a + ", " + this.f172b + ")";
    }

    public final void d(int i, int i2) {
        this.f171a += i;
        this.f172b += i2;
    }

    public void e(int i, int i2) {
        this.f171a = i;
        this.f172b = i2;
    }

    public boolean h(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && b.class == obj.getClass()) {
            b bVar = (b) obj;
            if (this.f171a == bVar.f171a && this.f172b == bVar.f172b) {
                return true;
            }
        }
        return false;
    }
}
