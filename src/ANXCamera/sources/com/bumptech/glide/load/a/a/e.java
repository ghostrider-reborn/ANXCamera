package com.bumptech.glide.load.a.a;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/* compiled from: ThumbnailStreamOpener */
class e {
    private static final String TAG = "ThumbStreamOpener";
    private static final a oe = new a();
    private final b Yd;
    private final ContentResolver ae;
    private final List<ImageHeaderParser> ne;
    private final d query;
    private final a service;

    e(List<ImageHeaderParser> list, a aVar, d dVar, b bVar, ContentResolver contentResolver) {
        this.service = aVar;
        this.query = dVar;
        this.Yd = bVar;
        this.ae = contentResolver;
        this.ne = list;
    }

    e(List<ImageHeaderParser> list, d dVar, b bVar, ContentResolver contentResolver) {
        this(list, oe, dVar, bVar, contentResolver);
    }

    private boolean h(File file) {
        return this.service.exists(file) && 0 < this.service.d(file);
    }

    /* JADX INFO: finally extract failed */
    @Nullable
    private String k(@NonNull Uri uri) {
        Cursor b2 = this.query.b(uri);
        if (b2 != null) {
            try {
                if (b2.moveToFirst()) {
                    String string = b2.getString(0);
                    if (b2 != null) {
                        b2.close();
                    }
                    return string;
                }
            } catch (Throwable th) {
                if (b2 != null) {
                    b2.close();
                }
                throw th;
            }
        }
        if (b2 != null) {
            b2.close();
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public int g(Uri uri) {
        String str = TAG;
        InputStream inputStream = null;
        try {
            inputStream = this.ae.openInputStream(uri);
            int a2 = com.bumptech.glide.load.b.a(this.ne, inputStream, this.Yd);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException unused) {
                }
            }
            return a2;
        } catch (IOException | NullPointerException e2) {
            inputStream = Log.isLoggable(str, 3);
            if (inputStream) {
                StringBuilder sb = new StringBuilder();
                sb.append("Failed to open uri: ");
                sb.append(uri);
                Log.d(str, sb.toString(), e2);
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException unused2) {
                }
            }
            return -1;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException unused3) {
                }
            }
        }
    }

    public InputStream h(Uri uri) throws FileNotFoundException {
        String k = k(uri);
        if (TextUtils.isEmpty(k)) {
            return null;
        }
        File file = this.service.get(k);
        if (!h(file)) {
            return null;
        }
        Uri fromFile = Uri.fromFile(file);
        try {
            return this.ae.openInputStream(fromFile);
        } catch (NullPointerException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("NPE opening uri: ");
            sb.append(uri);
            sb.append(" -> ");
            sb.append(fromFile);
            throw ((FileNotFoundException) new FileNotFoundException(sb.toString()).initCause(e2));
        }
    }
}
