package com.bumptech.glide.b;

import android.support.annotation.ColorInt;
import java.util.ArrayList;
import java.util.List;

/* compiled from: GifHeader */
public class c {
    public static final int dq = 0;
    public static final int dt = -1;
    @ColorInt
    int bgColor;
    int bgIndex;
    b du;
    final List<b> dv = new ArrayList();
    int frameCount = 0;
    @ColorInt
    int[] gct = null;
    boolean gctFlag;
    int gctSize;
    int height;
    int loopCount = -1;
    int pixelAspect;
    int status = 0;
    int width;

    public int aC() {
        return this.frameCount;
    }

    public int getHeight() {
        return this.height;
    }

    public int getStatus() {
        return this.status;
    }

    public int getWidth() {
        return this.width;
    }
}
