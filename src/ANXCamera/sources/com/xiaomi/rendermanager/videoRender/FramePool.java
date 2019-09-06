package com.xiaomi.rendermanager.videoRender;

import android.util.Log;
import com.xiaomi.rendermanager.videoRender.VideoRenderer.I420Frame;
import java.util.LinkedList;

class FramePool {
    private static final int MAX_DIMENSION = 2048;
    private static final String TAG = "FramePool";
    private static int frameCount = 16;
    private BufferPoolInfo poolInfo = new BufferPoolInfo(this);

    class BufferPoolInfo {
        LinkedList<I420Frame> freeFrameList = new LinkedList<>();
        final /* synthetic */ FramePool this$0;
        int totalAllocateCount = 2;

        public BufferPoolInfo(FramePool framePool) {
            this.this$0 = framePool;
            int[] iArr = {2048, 1024, 1024};
            int i = 0;
            while (i < this.totalAllocateCount) {
                int[] iArr2 = iArr;
                int[] iArr3 = iArr;
                I420Frame i420Frame = r2;
                I420Frame i420Frame2 = new I420Frame(2048, 2048, false, false, iArr2, null, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0);
                this.freeFrameList.add(i420Frame);
                i++;
                iArr = iArr3;
            }
        }
    }

    FramePool() {
    }

    private static long summarizeFrameDimensions(I420Frame i420Frame) {
        long j = ((((long) i420Frame.width) * 2048) + ((long) i420Frame.height)) * 2048;
        int[] iArr = i420Frame.yuvStrides;
        return ((((j + ((long) iArr[0])) * 2048) + ((long) iArr[1])) * 2048) + ((long) iArr[2]);
    }

    public static boolean validateDimensions(I420Frame i420Frame) {
        if (i420Frame.width <= 2048 && i420Frame.height <= 2048) {
            int[] iArr = i420Frame.yuvStrides;
            if (iArr[0] <= 2048 && iArr[1] <= 2048 && iArr[2] <= 2048) {
                return true;
            }
        }
        return false;
    }

    public int getFrameSize() {
        return frameCount;
    }

    public void returnFrame(I420Frame i420Frame) {
        BufferPoolInfo bufferPoolInfo = this.poolInfo;
        if (bufferPoolInfo != null) {
            synchronized (bufferPoolInfo) {
                this.poolInfo.freeFrameList.add(i420Frame);
                frameCount++;
            }
            return;
        }
        throw new IllegalArgumentException("Unexpected frame dimensions");
    }

    public I420Frame takeFrame(I420Frame i420Frame) {
        I420Frame i420Frame2;
        synchronized (this.poolInfo) {
            LinkedList<I420Frame> linkedList = this.poolInfo.freeFrameList;
            if (i420Frame.width > 2048 || i420Frame.height > 2048) {
                StringBuilder sb = new StringBuilder();
                sb.append("resolution is out of boundary, width: ");
                sb.append(i420Frame.width);
                sb.append(", height: ");
                sb.append(i420Frame.height);
                throw new RuntimeException(sb.toString());
            } else if (!linkedList.isEmpty()) {
                i420Frame2 = (I420Frame) linkedList.pop();
                i420Frame2.localPreview = i420Frame.localPreview;
                i420Frame2.backCamera = i420Frame.backCamera;
                i420Frame2.width = i420Frame.width;
                i420Frame2.height = i420Frame.height;
                i420Frame2.yuvStrides = i420Frame.yuvStrides;
                i420Frame2.offset = i420Frame.offset;
                i420Frame2.slope = i420Frame.offset;
                i420Frame2.sourceCoff = i420Frame.sourceCoff;
                i420Frame2.sharpCoff = i420Frame.sharpCoff;
                i420Frame2.sharpStrength = i420Frame.sharpStrength;
                i420Frame2.rotateAngle = i420Frame.rotateAngle;
                frameCount--;
            } else {
                String str = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Buffer pool new a frame, totalAllocateCount: ");
                sb2.append(this.poolInfo.totalAllocateCount);
                sb2.append(" size:");
                sb2.append(i420Frame.width);
                sb2.append("x");
                sb2.append(i420Frame.height);
                sb2.append(" for strid:");
                sb2.append(i420Frame.yuvStrides[0]);
                sb2.append(" ");
                sb2.append(i420Frame.yuvStrides[1]);
                sb2.append(" ");
                sb2.append(i420Frame.yuvStrides[2]);
                Log.e(str, sb2.toString());
                i420Frame2 = null;
            }
        }
        return i420Frame2;
    }
}
