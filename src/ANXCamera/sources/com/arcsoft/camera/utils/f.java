package com.arcsoft.camera.utils;

/* compiled from: MSize */
public class f implements Comparable<f> {

    /* renamed from: a  reason: collision with root package name */
    private int f71a;

    /* renamed from: b  reason: collision with root package name */
    private int f72b;

    public f() {
    }

    public f(int i, int i2) {
        this.f71a = i;
        this.f72b = i2;
    }

    /* renamed from: a */
    public int e(f fVar) {
        if (fVar == null) {
            return 1;
        }
        return this.f71a == fVar.f71a ? this.f72b - fVar.f72b : this.f71a - fVar.f71a;
    }

    public String a() {
        return new String("[" + this.f71a + "," + this.f72b + "]");
    }

    public int b() {
        return (this.f71a * 31) + this.f72b;
    }

    public boolean b(int i, int i2) {
        return this.f71a == i && this.f72b == i2;
    }

    public boolean d(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof f) || obj == null) {
            return false;
        }
        f fVar = (f) obj;
        return b(fVar.f71a, fVar.f72b);
    }
}
