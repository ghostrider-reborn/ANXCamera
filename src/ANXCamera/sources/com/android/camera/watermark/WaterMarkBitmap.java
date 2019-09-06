package com.android.camera.watermark;

import android.graphics.Bitmap;
import com.android.camera.log.Log;
import java.util.List;

public class WaterMarkBitmap {
    private static final String TAG = "WaterMarkBitmap";
    private List<WaterMarkData> mWaterInfos;
    private WaterMarkData mWaterMarkData = generateWaterMarkData();
    private BaseWaterMarkDrawable mWaterMarkDrawable;

    public WaterMarkBitmap(List<WaterMarkData> list) {
        this.mWaterInfos = list;
    }

    public WaterMarkData generateWaterMarkData() {
        List<WaterMarkData> list = this.mWaterInfos;
        WaterMarkData waterMarkData = null;
        if (list == null || list.isEmpty()) {
            Log.e(TAG, "The watermark data is empty.");
            return null;
        }
        int watermarkType = ((WaterMarkData) this.mWaterInfos.get(0)).getWatermarkType();
        if (watermarkType == 1) {
            this.mWaterMarkDrawable = new MagicMirrorWaterMarkDrawable(this.mWaterInfos);
            waterMarkData = this.mWaterMarkDrawable.getWaterMarkData();
        } else if (watermarkType != 2) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("unexpected watermark type ");
            sb.append(watermarkType);
            Log.w(str, sb.toString());
        } else {
            this.mWaterMarkDrawable = new AgeGenderWaterMarkDrawable(this.mWaterInfos);
            waterMarkData = this.mWaterMarkDrawable.getWaterMarkData();
        }
        return waterMarkData;
    }

    public WaterMarkData getWaterMarkData() {
        return this.mWaterMarkData;
    }

    public void releaseBitmap() {
        BaseWaterMarkDrawable baseWaterMarkDrawable = this.mWaterMarkDrawable;
        if (baseWaterMarkDrawable != null) {
            Bitmap bitmap = baseWaterMarkDrawable.getBitmap();
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
    }
}
