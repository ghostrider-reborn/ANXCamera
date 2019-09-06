package com.android.camera.data.data;

import android.support.annotation.NonNull;
import java.util.List;

public class ComponentSimple<T> extends ComponentData {
    public <D extends DataItemBase> ComponentSimple(D d2) {
        super(d2);
    }

    @NonNull
    public String getDefaultValue(int i) {
        return null;
    }

    public int getDisplayTitleString() {
        return 0;
    }

    public List<ComponentDataItem> getItems() {
        return null;
    }

    public String getKey(int i) {
        return null;
    }

    public <T> T getValue(String str) {
        return null;
    }

    public <T> void putValue(int i, T t) {
    }
}
