package com.bumptech.glide.load.model;

import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.model.m;
import java.io.InputStream;

/* compiled from: ResourceLoader */
public class r<Data> implements m<Integer, Data> {
    private static final String TAG = "ResourceLoader";
    private final m<Uri, Data> lo;
    private final Resources resources;

    /* compiled from: ResourceLoader */
    public static final class a implements n<Integer, AssetFileDescriptor> {
        private final Resources resources;

        public a(Resources resources2) {
            this.resources = resources2;
        }

        public m<Integer, AssetFileDescriptor> a(q qVar) {
            return new r(this.resources, qVar.b(Uri.class, AssetFileDescriptor.class));
        }

        public void cB() {
        }
    }

    /* compiled from: ResourceLoader */
    public static class b implements n<Integer, ParcelFileDescriptor> {
        private final Resources resources;

        public b(Resources resources2) {
            this.resources = resources2;
        }

        @NonNull
        public m<Integer, ParcelFileDescriptor> a(q qVar) {
            return new r(this.resources, qVar.b(Uri.class, ParcelFileDescriptor.class));
        }

        public void cB() {
        }
    }

    /* compiled from: ResourceLoader */
    public static class c implements n<Integer, InputStream> {
        private final Resources resources;

        public c(Resources resources2) {
            this.resources = resources2;
        }

        @NonNull
        public m<Integer, InputStream> a(q qVar) {
            return new r(this.resources, qVar.b(Uri.class, InputStream.class));
        }

        public void cB() {
        }
    }

    /* compiled from: ResourceLoader */
    public static class d implements n<Integer, Uri> {
        private final Resources resources;

        public d(Resources resources2) {
            this.resources = resources2;
        }

        @NonNull
        public m<Integer, Uri> a(q qVar) {
            return new r(this.resources, u.cN());
        }

        public void cB() {
        }
    }

    public r(Resources resources2, m<Uri, Data> mVar) {
        this.resources = resources2;
        this.lo = mVar;
    }

    @Nullable
    private Uri d(Integer num) {
        try {
            return Uri.parse("android.resource://" + this.resources.getResourcePackageName(num.intValue()) + '/' + this.resources.getResourceTypeName(num.intValue()) + '/' + this.resources.getResourceEntryName(num.intValue()));
        } catch (Resources.NotFoundException e) {
            if (!Log.isLoggable(TAG, 5)) {
                return null;
            }
            Log.w(TAG, "Received invalid resource id: " + num, e);
            return null;
        }
    }

    /* renamed from: a */
    public m.a<Data> b(@NonNull Integer num, int i, int i2, @NonNull f fVar) {
        Uri d2 = d(num);
        if (d2 == null) {
            return null;
        }
        return this.lo.b(d2, i, i2, fVar);
    }

    /* renamed from: e */
    public boolean t(@NonNull Integer num) {
        return true;
    }
}
