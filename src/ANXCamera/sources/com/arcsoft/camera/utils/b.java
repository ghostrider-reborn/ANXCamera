package com.arcsoft.camera.utils;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: MPoint */
public class b implements Parcelable {
    public static final Parcelable.Creator<b> aX = new Parcelable.Creator() {
        /* renamed from: c */
        public b d(Parcel parcel) {
            b bVar = new b();
            bVar.b(parcel);
            return bVar;
        }

        /* renamed from: f */
        public b[] g(int i) {
            return new b[i];
        }
    };

    /* renamed from: a  reason: collision with root package name */
    public int f65a;

    /* renamed from: b  reason: collision with root package name */
    public int f66b;

    public b() {
    }

    public b(int i, int i2) {
        this.f65a = i;
        this.f66b = i2;
    }

    public b(b bVar) {
        this.f65a = bVar.f65a;
        this.f66b = bVar.f66b;
    }

    public int a() {
        return 0;
    }

    public void a(Parcel parcel, int i) {
        parcel.writeInt(this.f65a);
        parcel.writeInt(this.f66b);
    }

    public int b() {
        return (31 * this.f65a) + this.f66b;
    }

    public void b(Parcel parcel) {
        this.f65a = parcel.readInt();
        this.f66b = parcel.readInt();
    }

    public final boolean b(int i, int i2) {
        return this.f65a == i && this.f66b == i2;
    }

    public final void c() {
        this.f65a = -this.f65a;
        this.f66b = -this.f66b;
    }

    public final void c(int i, int i2) {
        this.f65a += i;
        this.f66b += i2;
    }

    public String d() {
        return "Point(" + this.f65a + ", " + this.f66b + ")";
    }

    public void d(int i, int i2) {
        this.f65a = i;
        this.f66b = i2;
    }

    public boolean d(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        b bVar = (b) obj;
        return this.f65a == bVar.f65a && this.f66b == bVar.f66b;
    }
}
