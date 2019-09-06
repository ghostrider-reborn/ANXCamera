package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.a.c;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.i;
import com.bumptech.glide.util.l;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* compiled from: BitmapEncoder */
public class e implements i<Bitmap> {
    private static final String TAG = "BitmapEncoder";
    public static final f<Integer> ri = f.a("com.bumptech.glide.load.resource.bitmap.BitmapEncoder.CompressionQuality", Integer.valueOf(90));
    public static final f<CompressFormat> ti = f.q("com.bumptech.glide.load.resource.bitmap.BitmapEncoder.CompressionFormat");
    @Nullable
    private final b qa;

    @Deprecated
    public e() {
        this.qa = null;
    }

    public e(@NonNull b bVar) {
        this.qa = bVar;
    }

    private CompressFormat b(Bitmap bitmap, g gVar) {
        CompressFormat compressFormat = (CompressFormat) gVar.a(ti);
        return compressFormat != null ? compressFormat : bitmap.hasAlpha() ? CompressFormat.PNG : CompressFormat.JPEG;
    }

    @NonNull
    public EncodeStrategy a(@NonNull g gVar) {
        return EncodeStrategy.TRANSFORMED;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:21|(2:37|38)|39|40) */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x005f, code lost:
        if (r6 == null) goto L_0x0062;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:39:0x00b5 */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x005a A[Catch:{ all -> 0x0050 }] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00b2 A[SYNTHETIC, Splitter:B:37:0x00b2] */
    public boolean a(@NonNull A<Bitmap> a2, @NonNull File file, @NonNull g gVar) {
        String str = TAG;
        Bitmap bitmap = (Bitmap) a2.get();
        CompressFormat b2 = b(bitmap, gVar);
        Integer.valueOf(bitmap.getWidth());
        Integer.valueOf(bitmap.getHeight());
        long Gh = com.bumptech.glide.util.e.Gh();
        int intValue = ((Integer) gVar.a(ri)).intValue();
        boolean z = false;
        OutputStream outputStream = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                outputStream = this.qa != null ? new c(fileOutputStream, this.qa) : fileOutputStream;
                bitmap.compress(b2, intValue, outputStream);
                outputStream.close();
                z = true;
            } catch (IOException e2) {
                e = e2;
                outputStream = fileOutputStream;
                try {
                    if (Log.isLoggable(str, 3)) {
                    }
                } catch (Throwable th) {
                    th = th;
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                outputStream = fileOutputStream;
                if (outputStream != null) {
                }
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
            if (Log.isLoggable(str, 3)) {
                Log.d(str, "Failed to encode Bitmap", e);
            }
        }
        try {
            outputStream.close();
        } catch (IOException unused) {
        }
        if (Log.isLoggable(str, 2)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Compressed with type: ");
            sb.append(b2);
            sb.append(" of size ");
            sb.append(l.j(bitmap));
            sb.append(" in ");
            sb.append(com.bumptech.glide.util.e.g(Gh));
            sb.append(", options format: ");
            sb.append(gVar.a(ti));
            sb.append(", hasAlpha: ");
            sb.append(bitmap.hasAlpha());
            Log.v(str, sb.toString());
        }
        return z;
    }
}
