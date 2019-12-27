package com.bumptech.glide.load.model;

import android.content.res.AssetManager;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.a.i;
import com.bumptech.glide.load.a.n;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.model.t;
import com.ss.android.ugc.effectmanager.effect.model.ComposerHelper;
import java.io.InputStream;

/* renamed from: com.bumptech.glide.load.model.a  reason: case insensitive filesystem */
/* compiled from: AssetUriLoader */
public class C0100a<Data> implements t<Uri, Data> {
    private static final String Gh = "android_asset";
    private static final String Hh = "file:///android_asset/";
    private static final int Ih = 22;
    private final AssetManager Qd;
    private final C0009a<Data> factory;

    /* renamed from: com.bumptech.glide.load.model.a$a  reason: collision with other inner class name */
    /* compiled from: AssetUriLoader */
    public interface C0009a<Data> {
        d<Data> a(AssetManager assetManager, String str);
    }

    /* renamed from: com.bumptech.glide.load.model.a$b */
    /* compiled from: AssetUriLoader */
    public static class b implements u<Uri, ParcelFileDescriptor>, C0009a<ParcelFileDescriptor> {
        private final AssetManager Qd;

        public b(AssetManager assetManager) {
            this.Qd = assetManager;
        }

        public void D() {
        }

        public d<ParcelFileDescriptor> a(AssetManager assetManager, String str) {
            return new i(assetManager, str);
        }

        @NonNull
        public t<Uri, ParcelFileDescriptor> a(x xVar) {
            return new C0100a(this.Qd, this);
        }
    }

    /* renamed from: com.bumptech.glide.load.model.a$c */
    /* compiled from: AssetUriLoader */
    public static class c implements u<Uri, InputStream>, C0009a<InputStream> {
        private final AssetManager Qd;

        public c(AssetManager assetManager) {
            this.Qd = assetManager;
        }

        public void D() {
        }

        public d<InputStream> a(AssetManager assetManager, String str) {
            return new n(assetManager, str);
        }

        @NonNull
        public t<Uri, InputStream> a(x xVar) {
            return new C0100a(this.Qd, this);
        }
    }

    public C0100a(AssetManager assetManager, C0009a<Data> aVar) {
        this.Qd = assetManager;
        this.factory = aVar;
    }

    public t.a<Data> a(@NonNull Uri uri, int i, int i2, @NonNull g gVar) {
        return new t.a<>(new com.bumptech.glide.e.d(uri), this.factory.a(this.Qd, uri.toString().substring(Ih)));
    }

    /* renamed from: i */
    public boolean c(@NonNull Uri uri) {
        return ComposerHelper.COMPOSER_PATH.equals(uri.getScheme()) && !uri.getPathSegments().isEmpty() && Gh.equals(uri.getPathSegments().get(0));
    }
}
