package com.ss.android.ttve.nativePort;

import android.support.annotation.Keep;
import com.ss.android.vesdk.VEFrameAvailableListener;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@Keep
public class TEVideoUtilsCallback {
    private VEFrameAvailableListener listener;

    public static ByteBuffer allocateFrame(int i, int i2) {
        return ByteBuffer.allocateDirect(i * i2 * 4).order(ByteOrder.LITTLE_ENDIAN);
    }

    public static boolean onFrameAvailable(Object obj, ByteBuffer byteBuffer, int i, int i2, int i3) {
        if (!(obj instanceof TEVideoUtilsCallback)) {
            return false;
        }
        TEVideoUtilsCallback tEVideoUtilsCallback = (TEVideoUtilsCallback) obj;
        return (tEVideoUtilsCallback == null || tEVideoUtilsCallback.listener == null || !tEVideoUtilsCallback.listener.processFrame(byteBuffer, i, i2, i3)) ? false : true;
    }

    public boolean onFrameAvailable(ByteBuffer byteBuffer, int i, int i2, int i3) {
        return this.listener != null && this.listener.processFrame(byteBuffer, i, i2, i3);
    }

    public void setListener(Object obj) {
        this.listener = (VEFrameAvailableListener) obj;
    }
}
