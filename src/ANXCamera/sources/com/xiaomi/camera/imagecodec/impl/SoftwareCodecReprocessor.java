package com.xiaomi.camera.imagecodec.impl;

import android.content.Context;
import com.xiaomi.camera.imagecodec.ReprocessData;
import com.xiaomi.camera.imagecodec.Reprocessor;

public class SoftwareCodecReprocessor implements Reprocessor {
    public static final Reprocessor.Singleton<SoftwareCodecReprocessor> sInstance = new Reprocessor.Singleton<SoftwareCodecReprocessor>() {
        /* access modifiers changed from: protected */
        public SoftwareCodecReprocessor create() {
            return new SoftwareCodecReprocessor();
        }
    };

    private SoftwareCodecReprocessor() {
    }

    public void deInit() {
    }

    public void init(Context context) {
    }

    public void setJpegOutputSize(int i, int i2) {
    }

    public void setVirtualCameraIds(String str, String str2) {
    }

    public void submit(ReprocessData reprocessData) {
    }
}
