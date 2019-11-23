package com.android.camera.data.data.config;

import android.support.annotation.NonNull;
import com.android.camera.R;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.List;

public class ComponentConfigMiMovie extends ComponentData {
    public ComponentConfigMiMovie(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    @NonNull
    public String getDefaultValue(int i) {
        return null;
    }

    public int getDisplayTitleString() {
        return 0;
    }

    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    public String getKey(int i) {
        return "is_mimovie";
    }

    public boolean isMiMovieOn(int i) {
        return this.mParentDataItem.getBoolean(getKey(i), false);
    }

    public List<ComponentDataItem> reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        if (this.mItems == null) {
            this.mItems = new ArrayList();
        } else {
            this.mItems.clear();
        }
        if (i == 171) {
            this.mItems.add(new ComponentDataItem((int) R.drawable.ic_new_ai_scene_off, (int) R.drawable.ic_new_ai_scene_on, (int) R.string.accessibility_mimovie_on, "on"));
        }
        return this.mItems;
    }

    public void setMiMovie(int i, boolean z) {
        this.mParentDataItem.editor().putBoolean(getKey(i), z).apply();
    }
}
