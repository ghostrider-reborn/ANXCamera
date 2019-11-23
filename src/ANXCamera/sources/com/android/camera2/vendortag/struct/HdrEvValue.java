package com.android.camera2.vendortag.struct;

import com.android.camera.log.Log;
import java.io.Serializable;

public class HdrEvValue implements Serializable {
    public static final String TAG = HdrEvValue.class.getSimpleName();
    private static final long serialVersionUID = 1;
    private int[] mHdrCheckerEvValue;
    private int mSequenceNum;

    public HdrEvValue(byte[] bArr) {
        if (bArr != 0 && bArr.length >= 1) {
            int i = 0;
            if (bArr[0] != 0) {
                this.mSequenceNum = bArr[0];
                if (this.mSequenceNum <= 6) {
                    this.mHdrCheckerEvValue = new int[this.mSequenceNum];
                    if (this.mSequenceNum > 0 && this.mSequenceNum < 6) {
                        while (i < this.mSequenceNum) {
                            int i2 = i + 1;
                            this.mHdrCheckerEvValue[i] = bArr[i2 * 4];
                            String str = TAG;
                            Log.d(str, "HdrEvValue: evValue[" + i + "]=" + bArr[i]);
                            i = i2;
                        }
                        return;
                    }
                    return;
                }
                throw new RuntimeException("wrong sequenceNum " + this.mSequenceNum);
            }
        }
        this.mSequenceNum = 3;
        this.mHdrCheckerEvValue = new int[]{-6, 0, 6};
    }

    public int[] getHdrCheckerEvValue() {
        return this.mHdrCheckerEvValue;
    }

    public int getSequenceNum() {
        return this.mSequenceNum;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(16);
        if (this.mHdrCheckerEvValue != null && this.mHdrCheckerEvValue.length > 0) {
            sb.append("[");
            for (int i = 0; i < this.mHdrCheckerEvValue.length; i++) {
                sb.append(this.mHdrCheckerEvValue[i]);
                if (i != this.mHdrCheckerEvValue.length - 1) {
                    sb.append(",");
                }
            }
            sb.append("]");
        }
        return sb.toString();
    }
}
