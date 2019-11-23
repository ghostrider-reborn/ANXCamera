package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.a.c;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.p;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.h;
import com.bumptech.glide.util.k;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* compiled from: BitmapEncoder */
public class e implements h<Bitmap> {
    private static final String TAG = "BitmapEncoder";
    public static final com.bumptech.glide.load.e<Integer> lB = com.bumptech.glide.load.e.a("com.bumptech.glide.load.resource.bitmap.BitmapEncoder.CompressionQuality", 90);
    public static final com.bumptech.glide.load.e<Bitmap.CompressFormat> lC = com.bumptech.glide.load.e.r("com.bumptech.glide.load.resource.bitmap.BitmapEncoder.CompressionFormat");
    @Nullable
    private final b br;

    @Deprecated
    public e() {
        this.br = null;
    }

    public e(@NonNull b bVar) {
        this.br = bVar;
    }

    private Bitmap.CompressFormat a(Bitmap bitmap, f fVar) {
        Bitmap.CompressFormat compressFormat = (Bitmap.CompressFormat) fVar.a(lC);
        return compressFormat != null ? compressFormat : bitmap.hasAlpha() ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG;
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0065 A[Catch:{ all -> 0x0059 }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x006e A[SYNTHETIC, Splitter:B:30:0x006e] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x007b A[Catch:{ all -> 0x00d0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00ca A[SYNTHETIC, Splitter:B:40:0x00ca] */
    public boolean a(@NonNull p<Bitmap> pVar, @NonNull File file, @NonNull f fVar) {
        Bitmap bitmap = pVar.get();
        Bitmap.CompressFormat a2 = a(bitmap, fVar);
        com.bumptech.glide.util.a.b.a("encode: [%dx%d] %s", Integer.valueOf(bitmap.getWidth()), Integer.valueOf(bitmap.getHeight()), a2);
        try {
            long fn = com.bumptech.glide.util.e.fn();
            int intValue = ((Integer) fVar.a(lB)).intValue();
            boolean z = false;
            c cVar = null;
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    cVar = this.br != null ? new c(fileOutputStream, this.br) : fileOutputStream;
                    bitmap.compress(a2, intValue, cVar);
                    cVar.close();
                    z = true;
                } catch (IOException e) {
                    e = e;
                    cVar = fileOutputStream;
                    try {
                        if (Log.isLoggable(TAG, 3)) {
                        }
                        if (cVar != null) {
                        }
                        if (Log.isLoggable(TAG, 2)) {
                        }
                        return z;
                    } catch (Throwable th) {
                        th = th;
                        if (cVar != null) {
                            try {
                                cVar.close();
                            } catch (IOException e2) {
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    cVar = fileOutputStream;
                    if (cVar != null) {
                    }
                    throw th;
                }
                try {
                    cVar.close();
                } catch (IOException e3) {
                }
            } catch (IOException e4) {
                e = e4;
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Failed to encode Bitmap", e);
                }
                if (cVar != null) {
                    cVar.close();
                }
                if (Log.isLoggable(TAG, 2)) {
                }
                return z;
            }
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "Compressed with type: " + a2 + " of size " + k.p(bitmap) + " in " + com.bumptech.glide.util.e.f(fn) + ", options format: " + fVar.a(lC) + ", hasAlpha: " + bitmap.hasAlpha());
            }
            return z;
        } finally {
            com.bumptech.glide.util.a.b.endSection();
        }
    }

    @NonNull
    public EncodeStrategy b(@NonNull f fVar) {
        return EncodeStrategy.TRANSFORMED;
    }
}
