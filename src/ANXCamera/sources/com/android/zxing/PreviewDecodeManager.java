package com.android.zxing;

import android.media.Image;
import com.android.camera2.Camera2Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PreviewDecodeManager {
    public static final int DECODE_TYPE_GOOGLE_LENS = 2;
    public static final int DECODE_TYPE_HAND_GESTURE = 1;
    public static final int DECODE_TYPE_QR = 0;
    /* access modifiers changed from: private */
    public ConcurrentHashMap<Integer, Decoder> mDecoders;
    private Camera2Proxy.PreviewCallback mPreviewCallback;

    private static class Singleton {
        public static final PreviewDecodeManager INSTANCE = new PreviewDecodeManager();

        private Singleton() {
        }
    }

    private PreviewDecodeManager() {
        this.mPreviewCallback = new Camera2Proxy.PreviewCallback() {
            public boolean onPreviewFrame(Image image, Camera2Proxy camera2Proxy, int i) {
                for (Map.Entry value : PreviewDecodeManager.this.mDecoders.entrySet()) {
                    Decoder decoder = (Decoder) value.getValue();
                    if (decoder.needPreviewFrame()) {
                        if (decoder.isNeedImage()) {
                            decoder.onPreviewFrame(image);
                        } else {
                            decoder.onPreviewFrame(new PreviewImage(image, i));
                        }
                    }
                }
                return true;
            }
        };
        this.mDecoders = new ConcurrentHashMap<>();
    }

    public static PreviewDecodeManager getInstance() {
        return Singleton.INSTANCE;
    }

    public Camera2Proxy.PreviewCallback getPreviewCallback() {
        return this.mPreviewCallback;
    }

    public String getScanResult() {
        return ((QrDecoder) this.mDecoders.get(0)).getScanResult();
    }

    public void init(int i, int i2) {
        switch (i2) {
            case 0:
                if (this.mDecoders.get(0) == null) {
                    this.mDecoders.put(0, new QrDecoder());
                    break;
                }
                break;
            case 1:
                if (this.mDecoders.get(1) == null) {
                    this.mDecoders.put(1, new HandGestureDecoder());
                    break;
                }
                break;
            case 2:
                if (this.mDecoders.get(2) == null) {
                    this.mDecoders.put(2, new GoogleLensDecoder());
                    break;
                }
                break;
        }
        for (Map.Entry<Integer, Decoder> value : this.mDecoders.entrySet()) {
            ((Decoder) value.getValue()).init(i);
        }
    }

    public void quit() {
        for (Map.Entry<Integer, Decoder> value : this.mDecoders.entrySet()) {
            Decoder decoder = (Decoder) value.getValue();
            decoder.stopDecode();
            decoder.quit();
        }
        this.mDecoders.clear();
    }

    public void reset() {
        for (Map.Entry<Integer, Decoder> value : this.mDecoders.entrySet()) {
            ((Decoder) value.getValue()).reset();
        }
    }

    public void resetScanResult() {
        Decoder decoder = this.mDecoders.get(0);
        if (decoder != null) {
            ((QrDecoder) decoder).resetScanResult();
        }
    }

    public void startDecode() {
        for (Map.Entry<Integer, Decoder> value : this.mDecoders.entrySet()) {
            ((Decoder) value.getValue()).startDecode();
        }
    }

    public void stopDecode(int i) {
        if (this.mDecoders.get(Integer.valueOf(i)) != null) {
            this.mDecoders.get(Integer.valueOf(i)).stopDecode();
        }
    }
}
