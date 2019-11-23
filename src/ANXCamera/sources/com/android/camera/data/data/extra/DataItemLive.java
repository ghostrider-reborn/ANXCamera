package com.android.camera.data.data.extra;

import com.android.camera.CameraSettings;
import com.android.camera.data.data.DataItemBase;
import com.android.camera.fragment.vv.VVItem;
import com.android.camera.module.impl.component.MimojiStatusManager;
import com.android.camera.module.impl.component.TimeSpeedModel;
import java.util.List;

public class DataItemLive extends DataItemBase {
    public static final String DATA_LIVE_ACTIVATION = "live_activation";
    public static final String DATA_LIVE_START_ORIENTATION = "live_start_orientation";
    public static final String DATA_VV_VERSION = "vv_version";
    private static final String KEY = "camera_settings_live";
    private MimojiStatusManager mMimojiStatusManager;
    private List<TimeSpeedModel> mRecordSegmentTimeInfo;
    private VVItem mVVItem;

    public void clearAll() {
        this.mRecordSegmentTimeInfo = null;
        if (this.mMimojiStatusManager != null) {
            this.mMimojiStatusManager.reset();
        }
    }

    public String getActivation() {
        return getString(DATA_LIVE_ACTIVATION, "");
    }

    public VVItem getCurrentVVItem() {
        return this.mVVItem;
    }

    public int getLiveFilter() {
        return getInt(CameraSettings.KEY_LIVE_FILTER, 0);
    }

    public int getLiveStartOrientation() {
        return getInt(DATA_LIVE_START_ORIENTATION, 0);
    }

    public MimojiStatusManager getMimojiStatusManager() {
        if (this.mMimojiStatusManager == null) {
            this.mMimojiStatusManager = new MimojiStatusManager();
        }
        return this.mMimojiStatusManager;
    }

    public List<TimeSpeedModel> getRecordSegmentTimeInfo() {
        return this.mRecordSegmentTimeInfo;
    }

    public String getVVVersion() {
        return getString(DATA_VV_VERSION, "");
    }

    public boolean isTransient() {
        return false;
    }

    public String provideKey() {
        return KEY;
    }

    public void setActivation(String str) {
        editor().putString(DATA_LIVE_ACTIVATION, str).apply();
    }

    public void setCurrentVVItem(VVItem vVItem) {
        this.mVVItem = vVItem;
    }

    public void setLiveFilter(int i) {
        putInt(CameraSettings.KEY_LIVE_FILTER, i);
    }

    public void setLiveStartOrientation(int i) {
        putInt(DATA_LIVE_START_ORIENTATION, i);
    }

    public void setRecordSegmentTimeInfo(List<TimeSpeedModel> list) {
        this.mRecordSegmentTimeInfo = list;
    }

    public void setVVVersion(String str) {
        editor().putString(DATA_VV_VERSION, str).apply();
    }
}
