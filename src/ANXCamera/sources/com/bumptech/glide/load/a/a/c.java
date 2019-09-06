package com.bumptech.glide.load.a.a;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Images.Thumbnails;
import android.provider.MediaStore.Video;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.a.h;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: ThumbFetcher */
public class c implements d<InputStream> {
    private static final String TAG = "MediaStoreThumbFetcher";
    private InputStream inputStream;
    private final Uri le;
    private final e me;

    /* compiled from: ThumbFetcher */
    static class a implements d {
        private static final String[] je = {"_data"};
        private static final String ke = "kind = 1 AND image_id = ?";
        private final ContentResolver ae;

        a(ContentResolver contentResolver) {
            this.ae = contentResolver;
        }

        public Cursor b(Uri uri) {
            String lastPathSegment = uri.getLastPathSegment();
            return this.ae.query(Thumbnails.EXTERNAL_CONTENT_URI, je, ke, new String[]{lastPathSegment}, null);
        }
    }

    /* compiled from: ThumbFetcher */
    static class b implements d {
        private static final String[] je = {"_data"};
        private static final String ke = "kind = 1 AND video_id = ?";
        private final ContentResolver ae;

        b(ContentResolver contentResolver) {
            this.ae = contentResolver;
        }

        public Cursor b(Uri uri) {
            String lastPathSegment = uri.getLastPathSegment();
            return this.ae.query(Video.Thumbnails.EXTERNAL_CONTENT_URI, je, ke, new String[]{lastPathSegment}, null);
        }
    }

    @VisibleForTesting
    c(Uri uri, e eVar) {
        this.le = uri;
        this.me = eVar;
    }

    private static c a(Context context, Uri uri, d dVar) {
        return new c(uri, new e(com.bumptech.glide.c.get(context).getRegistry().Gf(), dVar, com.bumptech.glide.c.get(context).V(), context.getContentResolver()));
    }

    public static c b(Context context, Uri uri) {
        return a(context, uri, new a(context.getContentResolver()));
    }

    public static c c(Context context, Uri uri) {
        return a(context, uri, new b(context.getContentResolver()));
    }

    private InputStream ck() throws FileNotFoundException {
        InputStream h = this.me.h(this.le);
        int g = h != null ? this.me.g(this.le) : -1;
        return g != -1 ? new h(h, g) : h;
    }

    @NonNull
    public Class<InputStream> M() {
        return InputStream.class;
    }

    public void a(@NonNull Priority priority, @NonNull com.bumptech.glide.load.a.d.a<? super InputStream> aVar) {
        try {
            this.inputStream = ck();
            aVar.b(this.inputStream);
        } catch (FileNotFoundException e2) {
            String str = TAG;
            if (Log.isLoggable(str, 3)) {
                Log.d(str, "Failed to find thumbnail file", e2);
            }
            aVar.b((Exception) e2);
        }
    }

    public void cancel() {
    }

    public void cleanup() {
        InputStream inputStream2 = this.inputStream;
        if (inputStream2 != null) {
            try {
                inputStream2.close();
            } catch (IOException unused) {
            }
        }
    }

    @NonNull
    public DataSource r() {
        return DataSource.LOCAL;
    }
}
