package com.android.camera.data.data;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import com.android.camera.Util;
import com.android.camera.log.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public abstract class ComponentData {
    protected List<ComponentDataItem> mItems = new ArrayList();
    protected Object mItemsLock = new Object();
    protected DataItemBase mParentDataItem;

    public <D extends DataItemBase> ComponentData(D d2) {
        this.mParentDataItem = d2;
    }

    /* access modifiers changed from: protected */
    public boolean checkValueValid(String str) {
        return true;
    }

    public boolean disableUpdate() {
        return false;
    }

    public int findIndexOfValue(String str) {
        List items = getItems();
        for (int i = 0; i < items.size(); i++) {
            if (str.equals(((ComponentDataItem) items.get(i)).mValue)) {
                return i;
            }
        }
        return -1;
    }

    public String getComponentValue(int i) {
        String defaultValue = getDefaultValue(i);
        String string = this.mParentDataItem.getString(getKey(i), defaultValue);
        if (string == null || string.equals(defaultValue) || checkValueValid(string)) {
            return string;
        }
        String simpleName = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder();
        sb.append("reset invalid value ");
        sb.append(string);
        Log.e(simpleName, sb.toString());
        resetComponentValue(i);
        return this.mParentDataItem.getString(getKey(i), defaultValue);
    }

    @NonNull
    public abstract String getDefaultValue(int i);

    @StringRes
    public int getDefaultValueDisplayString(int i) {
        return 0;
    }

    @StringRes
    public int getDisableReasonString() {
        return 0;
    }

    @StringRes
    public abstract int getDisplayTitleString();

    public abstract List<ComponentDataItem> getItems();

    public abstract String getKey(int i);

    public String getPersistValue(int i) {
        return getComponentValue(i);
    }

    @StringRes
    public int getValueDisplayString(int i) {
        String componentValue = getComponentValue(i);
        List<ComponentDataItem> items = getItems();
        for (ComponentDataItem componentDataItem : items) {
            if (componentDataItem.mValue.equals(componentValue)) {
                return componentDataItem.mDisplayNameRes;
            }
        }
        String format = String.format(Locale.ENGLISH, "mode %1$d, invalid value %2$s for %3$s, items = %4$s", new Object[]{Integer.valueOf(i), componentValue, getKey(i), Arrays.toString(items.toArray())});
        Log.e(getClass().getSimpleName(), format);
        if (!Util.isDebugOsBuild()) {
            return -1;
        }
        throw new IllegalArgumentException(format);
    }

    @DrawableRes
    public int getValueSelectedDrawable(int i) {
        String componentValue = getComponentValue(i);
        List<ComponentDataItem> items = getItems();
        for (ComponentDataItem componentDataItem : items) {
            if (componentDataItem.mValue.equals(componentValue)) {
                return componentDataItem.mIconSelectedRes;
            }
        }
        String format = String.format(Locale.ENGLISH, "mode %1$d, invalid value %2$s for %3$s, items = %4$s", new Object[]{Integer.valueOf(i), componentValue, getKey(i), Arrays.toString(items.toArray())});
        Log.e(getClass().getSimpleName(), format);
        if (!Util.isDebugOsBuild()) {
            return -1;
        }
        throw new IllegalArgumentException(format);
    }

    public boolean isEmpty() {
        List<ComponentDataItem> list = this.mItems;
        return list == null || list.isEmpty();
    }

    public void reset(int i) {
        setComponentValue(i, getDefaultValue(i));
    }

    /* access modifiers changed from: protected */
    public void resetComponentValue(int i) {
    }

    public void setComponentValue(int i, String str) {
        if (this.mParentDataItem.isTransient()) {
            this.mParentDataItem.putString(getKey(i), str);
        } else {
            this.mParentDataItem.editor().putString(getKey(i), str).apply();
        }
    }
}
