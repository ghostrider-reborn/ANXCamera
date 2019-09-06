package com.bumptech.glide.load.resource.gif;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.bumptech.glide.b.a;
import com.bumptech.glide.b.a.C0004a;
import com.bumptech.glide.b.c;
import com.bumptech.glide.b.d;
import com.bumptech.glide.b.e;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ImageHeaderParser.ImageType;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.h;
import com.bumptech.glide.util.l;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Queue;

public class ByteBufferGifDecoder implements h<ByteBuffer, b> {
    private static final GifDecoderFactory Lj = new GifDecoderFactory();
    private static final GifHeaderParserPool Mj = new GifHeaderParserPool();
    private static final String TAG = "BufferGifDecoder";
    private final GifHeaderParserPool Jj;
    private final GifDecoderFactory Kj;
    private final Context context;
    private final List<ImageHeaderParser> ne;
    private final a provider;

    @VisibleForTesting
    static class GifDecoderFactory {
        GifDecoderFactory() {
        }

        /* access modifiers changed from: 0000 */
        public a a(C0004a aVar, c cVar, ByteBuffer byteBuffer, int i) {
            return new e(aVar, cVar, byteBuffer, i);
        }
    }

    @VisibleForTesting
    static class GifHeaderParserPool {
        private final Queue<d> pool = l.createQueue(0);

        GifHeaderParserPool() {
        }

        /* access modifiers changed from: 0000 */
        public synchronized void a(d dVar) {
            dVar.clear();
            this.pool.offer(dVar);
        }

        /* access modifiers changed from: 0000 */
        public synchronized d d(ByteBuffer byteBuffer) {
            d dVar;
            dVar = (d) this.pool.poll();
            if (dVar == null) {
                dVar = new d();
            }
            return dVar.setData(byteBuffer);
        }
    }

    public ByteBufferGifDecoder(Context context2) {
        this(context2, com.bumptech.glide.c.get(context2).getRegistry().Gf(), com.bumptech.glide.c.get(context2).Cf(), com.bumptech.glide.c.get(context2).V());
    }

    public ByteBufferGifDecoder(Context context2, List<ImageHeaderParser> list, com.bumptech.glide.load.engine.bitmap_recycle.d dVar, b bVar) {
        this(context2, list, dVar, bVar, Mj, Lj);
    }

    @VisibleForTesting
    ByteBufferGifDecoder(Context context2, List<ImageHeaderParser> list, com.bumptech.glide.load.engine.bitmap_recycle.d dVar, b bVar, GifHeaderParserPool gifHeaderParserPool, GifDecoderFactory gifDecoderFactory) {
        this.context = context2.getApplicationContext();
        this.ne = list;
        this.Kj = gifDecoderFactory;
        this.provider = new a(dVar, bVar);
        this.Jj = gifHeaderParserPool;
    }

    private static int a(c cVar, int i, int i2) {
        int min = Math.min(cVar.getHeight() / i2, cVar.getWidth() / i);
        int max = Math.max(1, min == 0 ? 0 : Integer.highestOneBit(min));
        String str = TAG;
        if (Log.isLoggable(str, 2) && max > 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("Downsampling GIF, sampleSize: ");
            sb.append(max);
            sb.append(", target dimens: [");
            sb.append(i);
            String str2 = "x";
            sb.append(str2);
            sb.append(i2);
            sb.append("], actual dimens: [");
            sb.append(cVar.getWidth());
            sb.append(str2);
            sb.append(cVar.getHeight());
            sb.append("]");
            Log.v(str, sb.toString());
        }
        return max;
    }

    @Nullable
    private d a(ByteBuffer byteBuffer, int i, int i2, d dVar, g gVar) {
        String str = "Decoded GIF from stream in ";
        String str2 = TAG;
        long Gh = com.bumptech.glide.util.e.Gh();
        try {
            c ag = dVar.ag();
            if (ag.getNumFrames() > 0) {
                if (ag.getStatus() == 0) {
                    Config config = gVar.a(g.Yi) == DecodeFormat.PREFER_RGB_565 ? Config.RGB_565 : Config.ARGB_8888;
                    a a2 = this.Kj.a(this.provider, ag, byteBuffer, a(ag, i, i2));
                    a2.a(config);
                    a2.advance();
                    Bitmap nextFrame = a2.getNextFrame();
                    if (nextFrame == null) {
                        if (Log.isLoggable(str2, 2)) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(str);
                            sb.append(com.bumptech.glide.util.e.g(Gh));
                            Log.v(str2, sb.toString());
                        }
                        return null;
                    }
                    b bVar = new b(this.context, a2, com.bumptech.glide.load.b.b.get(), i, i2, nextFrame);
                    d dVar2 = new d(bVar);
                    if (Log.isLoggable(str2, 2)) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str);
                        sb2.append(com.bumptech.glide.util.e.g(Gh));
                        Log.v(str2, sb2.toString());
                    }
                    return dVar2;
                }
            }
            return null;
        } finally {
            if (Log.isLoggable(str2, 2)) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str);
                sb3.append(com.bumptech.glide.util.e.g(Gh));
                Log.v(str2, sb3.toString());
            }
        }
    }

    /* renamed from: a */
    public d b(@NonNull ByteBuffer byteBuffer, int i, int i2, @NonNull g gVar) {
        d d2 = this.Jj.d(byteBuffer);
        try {
            d a2 = a(byteBuffer, i, i2, d2, gVar);
            return a2;
        } finally {
            this.Jj.a(d2);
        }
    }

    public boolean a(@NonNull ByteBuffer byteBuffer, @NonNull g gVar) throws IOException {
        return !((Boolean) gVar.a(g.Wj)).booleanValue() && com.bumptech.glide.load.b.a(this.ne, byteBuffer) == ImageType.GIF;
    }
}
