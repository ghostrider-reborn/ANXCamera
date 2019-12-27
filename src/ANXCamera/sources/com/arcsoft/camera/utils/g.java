package com.arcsoft.camera.utils;

/* compiled from: MSize */
public class g implements Comparable<g> {

    /* renamed from: a  reason: collision with root package name */
    private int f180a;

    /* renamed from: b  reason: collision with root package name */
    private int f181b;

    public g() {
    }

    public g(int i, int i2) {
        this.f180a = i;
        this.f181b = i2;
    }

    /* renamed from: a */
    public int i(g gVar) {
        if (gVar == null) {
            return 1;
        }
        int i = this.f180a;
        int i2 = gVar.f180a;
        return i == i2 ? this.f181b - gVar.f181b : i - i2;
    }

    public String a() {
        return new String("[" + this.f180a + "," + this.f181b + "]");
    }

    public boolean a(int i, int i2) {
        return this.f180a == i && this.f181b == i2;
    }

    public int b() {
        return (this.f180a * 31) + this.f181b;
    }

    public boolean h(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof g) || obj == null) {
            return false;
        }
        g gVar = (g) obj;
        return a(gVar.f180a, gVar.f181b);
    }
}
