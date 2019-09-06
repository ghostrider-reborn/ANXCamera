package com.bumptech.glide.util;

import android.graphics.Bitmap.Config;

/* compiled from: Util */
/* synthetic */ class k {
    static final /* synthetic */ int[] sg = new int[Config.values().length];

    /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
    static {
        sg[Config.ALPHA_8.ordinal()] = 1;
        sg[Config.RGB_565.ordinal()] = 2;
        sg[Config.ARGB_4444.ordinal()] = 3;
        sg[Config.RGBA_F16.ordinal()] = 4;
        sg[Config.ARGB_8888.ordinal()] = 5;
    }
}
