package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.engine.DecodeJob;

/* compiled from: DecodeJob */
/* synthetic */ class h {
    static final /* synthetic */ int[] Oe = new int[DecodeJob.RunReason.values().length];
    static final /* synthetic */ int[] Pe = new int[DecodeJob.Stage.values().length];
    static final /* synthetic */ int[] Qe = new int[EncodeStrategy.values().length];

    /* JADX WARNING: Can't wrap try/catch for region: R(25:0|1|2|3|(2:5|6)|7|9|10|11|12|13|15|16|17|18|19|20|21|23|24|25|26|27|28|30) */
    /* JADX WARNING: Can't wrap try/catch for region: R(26:0|1|2|3|5|6|7|9|10|11|12|13|15|16|17|18|19|20|21|23|24|25|26|27|28|30) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0032 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0047 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0052 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0070 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x007a */
    static {
        try {
            Qe[EncodeStrategy.SOURCE.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            Qe[EncodeStrategy.TRANSFORMED.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        Pe[DecodeJob.Stage.RESOURCE_CACHE.ordinal()] = 1;
        Pe[DecodeJob.Stage.DATA_CACHE.ordinal()] = 2;
        Pe[DecodeJob.Stage.SOURCE.ordinal()] = 3;
        Pe[DecodeJob.Stage.FINISHED.ordinal()] = 4;
        Pe[DecodeJob.Stage.INITIALIZE.ordinal()] = 5;
        Oe[DecodeJob.RunReason.INITIALIZE.ordinal()] = 1;
        Oe[DecodeJob.RunReason.SWITCH_TO_SOURCE_SERVICE.ordinal()] = 2;
        try {
            Oe[DecodeJob.RunReason.DECODE_DATA.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
    }
}
