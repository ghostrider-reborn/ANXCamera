package com.bumptech.glide.load.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.model.m;
import java.io.File;
import java.io.FileNotFoundException;

/* compiled from: MediaStoreFileLoader */
public final class k implements m<Uri, File> {
    private final Context context;

    /* compiled from: MediaStoreFileLoader */
    public static final class a implements n<Uri, File> {
        private final Context context;

        public a(Context context2) {
            this.context = context2;
        }

        @NonNull
        public m<Uri, File> a(q qVar) {
            return new k(this.context);
        }

        public void cB() {
        }
    }

    /* compiled from: MediaStoreFileLoader */
    private static class b implements d<File> {
        private static final String[] PROJECTION = {"_data"};
        private final Context context;
        private final Uri uri;

        b(Context context2, Uri uri2) {
            this.context = context2;
            this.uri = uri2;
        }

        public void a(@NonNull Priority priority, @NonNull d.a<? super File> aVar) {
            Cursor query = this.context.getContentResolver().query(this.uri, PROJECTION, (String) null, (String[]) null, (String) null);
            String str = null;
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        str = query.getString(query.getColumnIndexOrThrow("_data"));
                    }
                } finally {
                    query.close();
                }
            }
            if (TextUtils.isEmpty(str)) {
                aVar.b(new FileNotFoundException("Failed to find file path for: " + this.uri));
                return;
            }
            aVar.n(new File(str));
        }

        @NonNull
        public Class<File> aK() {
            return File.class;
        }

        @NonNull
        public DataSource aL() {
            return DataSource.LOCAL;
        }

        public void cancel() {
        }

        public void cleanup() {
        }
    }

    public k(Context context2) {
        this.context = context2;
    }

    /* renamed from: a */
    public m.a<File> b(@NonNull Uri uri, int i, int i2, @NonNull f fVar) {
        return new m.a<>(new com.bumptech.glide.e.d(uri), new b(this.context, uri));
    }

    /* renamed from: k */
    public boolean t(@NonNull Uri uri) {
        return com.bumptech.glide.load.a.a.b.c(uri);
    }
}
