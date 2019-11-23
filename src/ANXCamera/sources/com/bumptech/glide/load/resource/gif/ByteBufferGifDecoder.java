package com.bumptech.glide.load.resource.gif;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.bumptech.glide.b.a;
import com.bumptech.glide.b.c;
import com.bumptech.glide.b.d;
import com.bumptech.glide.b.e;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.g;
import com.bumptech.glide.util.k;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Queue;

public class ByteBufferGifDecoder implements g<ByteBuffer, b> {
    private static final String TAG = "BufferGifDecoder";
    private static final GifDecoderFactory nh = new GifDecoderFactory();
    private static final GifHeaderParserPool ni = new GifHeaderParserPool();
    private final Context context;
    private final List<ImageHeaderParser> fw;
    private final GifHeaderParserPool nj;
    private final GifDecoderFactory nk;
    private final a nl;

    @VisibleForTesting
    static class GifDecoderFactory {
        GifDecoderFactory() {
        }

        /* access modifiers changed from: package-private */
        public a a(a.C0003a aVar, c cVar, ByteBuffer byteBuffer, int i) {
            return new e(aVar, cVar, byteBuffer, i);
        }
    }

    @VisibleForTesting
    static class GifHeaderParserPool {
        private final Queue<d> jd = k.ab(0);

        GifHeaderParserPool() {
        }

        /* access modifiers changed from: package-private */
        public synchronized void a(d dVar) {
            dVar.clear();
            this.jd.offer(dVar);
        }

        /* access modifiers changed from: package-private */
        public synchronized d e(ByteBuffer byteBuffer) {
            d poll;
            poll = this.jd.poll();
            if (poll == null) {
                poll = new d();
            }
            return poll.a(byteBuffer);
        }
    }

    public ByteBufferGifDecoder(Context context2) {
        this(context2, com.bumptech.glide.c.c(context2).S().X(), com.bumptech.glide.c.c(context2).L(), com.bumptech.glide.c.c(context2).M());
    }

    public ByteBufferGifDecoder(Context context2, List<ImageHeaderParser> list, com.bumptech.glide.load.engine.bitmap_recycle.d dVar, b bVar) {
        this(context2, list, dVar, bVar, ni, nh);
    }

    @VisibleForTesting
    ByteBufferGifDecoder(Context context2, List<ImageHeaderParser> list, com.bumptech.glide.load.engine.bitmap_recycle.d dVar, b bVar, GifHeaderParserPool gifHeaderParserPool, GifDecoderFactory gifDecoderFactory) {
        this.context = context2.getApplicationContext();
        this.fw = list;
        this.nk = gifDecoderFactory;
        this.nl = new a(dVar, bVar);
        this.nj = gifHeaderParserPool;
    }

    private static int a(c cVar, int i, int i2) {
        int min = Math.min(cVar.getHeight() / i2, cVar.getWidth() / i);
        int max = Math.max(1, min == 0 ? 0 : Integer.highestOneBit(min));
        if (Log.isLoggable(TAG, 2) && max > 1) {
            Log.v(TAG, "Downsampling GIF, sampleSize: " + max + ", target dimens: [" + i + "x" + i2 + "], actual dimens: [" + cVar.getWidth() + "x" + cVar.getHeight() + "]");
        }
        return max;
    }

    @Nullable
    private d a(ByteBuffer byteBuffer, int i, int i2, d dVar, f fVar) {
        long fn = com.bumptech.glide.util.e.fn();
        try {
            c aD = dVar.aD();
            if (aD.aC() > 0) {
                if (aD.getStatus() == 0) {
                    Bitmap.Config config = fVar.a(g.mm) == DecodeFormat.PREFER_RGB_565 ? Bitmap.Config.RGB_565 : Bitmap.Config.ARGB_8888;
                    int i3 = i;
                    int i4 = i2;
                    a a2 = this.nk.a(this.nl, aD, byteBuffer, a(aD, i3, i4));
                    a2.a(config);
                    a2.advance();
                    Bitmap aB = a2.aB();
                    if (aB == null) {
                        if (Log.isLoggable(TAG, 2)) {
                            Log.v(TAG, "Decoded GIF from stream in " + com.bumptech.glide.util.e.f(fn));
                        }
                        return null;
                    }
                    b bVar = new b(this.context, a2, com.bumptech.glide.load.resource.b.cP(), i3, i4, aB);
                    d dVar2 = new d(bVar);
                    if (Log.isLoggable(TAG, 2)) {
                        Log.v(TAG, "Decoded GIF from stream in " + com.bumptech.glide.util.e.f(fn));
                    }
                    return dVar2;
                }
            }
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "Decoded GIF from stream in " + com.bumptech.glide.util.e.f(fn));
            }
            return null;
        } catch (Throwable th) {
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "Decoded GIF from stream in " + com.bumptech.glide.util.e.f(fn));
            }
            throw th;
        }
    }

    public boolean a(@NonNull ByteBuffer byteBuffer, @NonNull f fVar) throws IOException {
        return !((Boolean) fVar.a(g.nJ)).booleanValue() && com.bumptech.glide.load.b.a(this.fw, byteBuffer) == ImageHeaderParser.ImageType.GIF;
    }

    /* renamed from: b */
    public d a(@NonNull ByteBuffer byteBuffer, int i, int i2, @NonNull f fVar) {
        d e = this.nj.e(byteBuffer);
        try {
            d a2 = a(byteBuffer, i, i2, e, fVar);
            return a2;
        } finally {
            this.nj.a(e);
        }
    }
}
