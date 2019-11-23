package com.bumptech.glide.load.model;

import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.model.m;
import java.io.File;
import java.io.InputStream;

/* compiled from: StringLoader */
public class t<Data> implements m<String, Data> {
    private final m<Uri, Data> lo;

    /* compiled from: StringLoader */
    public static final class a implements n<String, AssetFileDescriptor> {
        public m<String, AssetFileDescriptor> a(q qVar) {
            return new t(qVar.b(Uri.class, AssetFileDescriptor.class));
        }

        public void cB() {
        }
    }

    /* compiled from: StringLoader */
    public static class b implements n<String, ParcelFileDescriptor> {
        @NonNull
        public m<String, ParcelFileDescriptor> a(q qVar) {
            return new t(qVar.b(Uri.class, ParcelFileDescriptor.class));
        }

        public void cB() {
        }
    }

    /* compiled from: StringLoader */
    public static class c implements n<String, InputStream> {
        @NonNull
        public m<String, InputStream> a(q qVar) {
            return new t(qVar.b(Uri.class, InputStream.class));
        }

        public void cB() {
        }
    }

    public t(m<Uri, Data> mVar) {
        this.lo = mVar;
    }

    @Nullable
    private static Uri C(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.charAt(0) == '/') {
            return D(str);
        }
        Uri parse = Uri.parse(str);
        return parse.getScheme() == null ? D(str) : parse;
    }

    private static Uri D(String str) {
        return Uri.fromFile(new File(str));
    }

    /* renamed from: B */
    public boolean t(@NonNull String str) {
        return true;
    }

    /* renamed from: a */
    public m.a<Data> b(@NonNull String str, int i, int i2, @NonNull f fVar) {
        Uri C = C(str);
        if (C == null) {
            return null;
        }
        return this.lo.b(C, i, i2, fVar);
    }
}
