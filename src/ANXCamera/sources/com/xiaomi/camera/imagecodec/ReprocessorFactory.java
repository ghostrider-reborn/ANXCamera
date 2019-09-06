package com.xiaomi.camera.imagecodec;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.Log;
import com.xiaomi.camera.imagecodec.impl.HardwareCodecReprocessor;
import com.xiaomi.camera.imagecodec.impl.SoftwareCodecReprocessor;
import com.xiaomi.camera.imagecodec.impl.VirtualCameraReprocessor;

public class ReprocessorFactory {
    private static final String TAG = "ReprocessorFactory";
    private static ReprocessingType sReprocessorType;

    /* renamed from: com.xiaomi.camera.imagecodec.ReprocessorFactory$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$camera$imagecodec$ReprocessorFactory$ReprocessingType = new int[ReprocessingType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            $SwitchMap$com$xiaomi$camera$imagecodec$ReprocessorFactory$ReprocessingType[ReprocessingType.HARDWARE_CODEC.ordinal()] = 1;
            $SwitchMap$com$xiaomi$camera$imagecodec$ReprocessorFactory$ReprocessingType[ReprocessingType.SOFTWARE_CODEC.ordinal()] = 2;
        }
    }

    public enum ReprocessingType {
        VIRTUAL_CAMERA,
        HARDWARE_CODEC,
        SOFTWARE_CODEC
    }

    private ReprocessorFactory() {
    }

    public static synchronized Reprocessor getReprocessor() {
        synchronized (ReprocessorFactory.class) {
            if (sReprocessorType != null) {
                int i = AnonymousClass1.$SwitchMap$com$xiaomi$camera$imagecodec$ReprocessorFactory$ReprocessingType[sReprocessorType.ordinal()];
                if (i == 1) {
                    Reprocessor reprocessor = (Reprocessor) HardwareCodecReprocessor.sInstance.get();
                    return reprocessor;
                } else if (i != 2) {
                    Reprocessor reprocessor2 = (Reprocessor) VirtualCameraReprocessor.sInstance.get();
                    return reprocessor2;
                } else {
                    Reprocessor reprocessor3 = (Reprocessor) SoftwareCodecReprocessor.sInstance.get();
                    return reprocessor3;
                }
            } else {
                throw new IllegalStateException("The global reprocessingType is not defined yet, make sure #setPreferredReprocessingType is called");
            }
        }
    }

    public static synchronized void setPreferredReprocessorType(Context context) {
        ReprocessingType reprocessingType;
        synchronized (ReprocessorFactory.class) {
            ReprocessingType reprocessingType2 = ReprocessingType.VIRTUAL_CAMERA;
            try {
                reprocessingType = ReprocessingType.values()[context.getResources().getInteger(R.integer.preferred_image_reprocessor_type)];
            } catch (NotFoundException | ArrayIndexOutOfBoundsException e2) {
                Log.d(TAG, "Failed to find the preferred reprocessor, use default (VIRTUAL_CAMERA) instead", e2);
            }
            if (sReprocessorType == null) {
                sReprocessorType = reprocessingType;
            } else if (sReprocessorType != reprocessingType) {
                throw new IllegalStateException("The type of preferred reprocessor is not allowed to be changed.");
            }
        }
    }
}
