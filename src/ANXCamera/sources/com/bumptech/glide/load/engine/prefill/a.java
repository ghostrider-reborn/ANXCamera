package com.bumptech.glide.load.engine.prefill;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.a.o;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.engine.prefill.c;
import com.bumptech.glide.util.l;
import java.util.HashMap;

/* compiled from: BitmapPreFiller */
public final class a {
    private final d Bb;
    private final DecodeFormat Bh;
    private final o Cb;
    private BitmapPreFillRunner current;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public a(o oVar, d dVar, DecodeFormat decodeFormat) {
        this.Cb = oVar;
        this.Bb = dVar;
        this.Bh = decodeFormat;
    }

    private static int a(c cVar) {
        return l.g(cVar.getWidth(), cVar.getHeight(), cVar.getConfig());
    }

    public void b(c.a... aVarArr) {
        BitmapPreFillRunner bitmapPreFillRunner = this.current;
        if (bitmapPreFillRunner != null) {
            bitmapPreFillRunner.cancel();
        }
        c[] cVarArr = new c[aVarArr.length];
        for (int i = 0; i < aVarArr.length; i++) {
            c.a aVar = aVarArr[i];
            if (aVar.getConfig() == null) {
                DecodeFormat decodeFormat = this.Bh;
                aVar.setConfig((decodeFormat == DecodeFormat.PREFER_ARGB_8888 || decodeFormat == DecodeFormat.PREFER_ARGB_8888_DISALLOW_HARDWARE) ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            }
            cVarArr[i] = aVar.build();
        }
        this.current = new BitmapPreFillRunner(this.Bb, this.Cb, generateAllocationOrder(cVarArr));
        this.handler.post(this.current);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public b generateAllocationOrder(c... cVarArr) {
        long maxSize = (this.Cb.getMaxSize() - this.Cb.J()) + this.Bb.getMaxSize();
        int i = 0;
        for (c weight : cVarArr) {
            i += weight.getWeight();
        }
        float f2 = ((float) maxSize) / ((float) i);
        HashMap hashMap = new HashMap();
        for (c cVar : cVarArr) {
            hashMap.put(cVar, Integer.valueOf(Math.round(((float) cVar.getWeight()) * f2) / a(cVar)));
        }
        return new b(hashMap);
    }
}
