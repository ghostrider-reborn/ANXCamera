package com.android.camera.preferences;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.log.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListPreference extends CameraPreference {
    private static final String TAG = "ListPreference";
    private final CharSequence[] mDefaultValues;
    private CharSequence[] mEntries;
    private CharSequence[] mEntryValues;
    private final boolean mHasPopup;
    private final String mKey;
    private String mValue;

    public ListPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ListPreference, 0, 0);
        String string = obtainStyledAttributes.getString(4);
        Util.checkNotNull(string);
        this.mKey = string;
        String string2 = obtainStyledAttributes.getString(3);
        this.mHasPopup = string2 == null ? false : Boolean.valueOf(string2).booleanValue();
        TypedValue peekValue = obtainStyledAttributes.peekValue(0);
        if (peekValue == null || peekValue.type != 1) {
            this.mDefaultValues = new CharSequence[1];
            this.mDefaultValues[0] = obtainStyledAttributes.getString(0);
        } else {
            this.mDefaultValues = obtainStyledAttributes.getTextArray(0);
        }
        setEntries(obtainStyledAttributes.getTextArray(1));
        setEntryValues(obtainStyledAttributes.getTextArray(2));
        obtainStyledAttributes.recycle();
    }

    public void filterUnsupported(List<String> list) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int length = this.mEntryValues.length;
        for (int i = 0; i < length; i++) {
            if (list.indexOf(this.mEntryValues[i].toString()) >= 0) {
                arrayList.add(this.mEntries[i]);
                arrayList2.add(this.mEntryValues[i]);
            }
        }
        int size = arrayList.size();
        this.mEntries = (CharSequence[]) arrayList.toArray(new CharSequence[size]);
        this.mEntryValues = (CharSequence[]) arrayList2.toArray(new CharSequence[size]);
    }

    public void filterValue() {
        if (findIndexOfValue(getValue()) < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("filterValue index < 0, value=");
            sb.append(getValue());
            Log.e(TAG, sb.toString());
            print();
            setValueIndex(0);
        }
    }

    public int findIndexOfValue(String str) {
        int length = this.mEntryValues.length;
        for (int i = 0; i < length; i++) {
            if (Util.equals(this.mEntryValues[i], str)) {
                return i;
            }
        }
        return -1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0025, code lost:
        r1 = r1 + 1;
     */
    public String findSupportedDefaultValue() {
        int i = 0;
        while (i < this.mDefaultValues.length) {
            int i2 = 0;
            while (true) {
                CharSequence[] charSequenceArr = this.mEntryValues;
                if (i2 >= charSequenceArr.length) {
                    break;
                } else if (charSequenceArr[i2].equals(this.mDefaultValues[i])) {
                    return this.mDefaultValues[i].toString();
                } else {
                    i2++;
                }
            }
        }
        return null;
    }

    public CharSequence[] getEntries() {
        return this.mEntries;
    }

    public String getEntry() {
        int findIndexOfValue = findIndexOfValue(getValue());
        if (findIndexOfValue < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("getEntry index=");
            sb.append(findIndexOfValue);
            Log.e(TAG, sb.toString());
            print();
            setValue(findSupportedDefaultValue());
            findIndexOfValue = findIndexOfValue(getValue());
        }
        return this.mEntries[findIndexOfValue].toString();
    }

    public CharSequence[] getEntryValues() {
        return this.mEntryValues;
    }

    public String getKey() {
        return this.mKey;
    }

    public String getValue() {
        this.mValue = getSharedPreferences().getString(this.mKey, findSupportedDefaultValue());
        return this.mValue;
    }

    public boolean hasPopup() {
        return this.mHasPopup;
    }

    public boolean isDefaultValue() {
        String findSupportedDefaultValue = findSupportedDefaultValue();
        this.mValue = getSharedPreferences().getString(this.mKey, findSupportedDefaultValue);
        return Objects.equals(findSupportedDefaultValue, this.mValue);
    }

    /* access modifiers changed from: protected */
    public void persistStringValue(String str) {
        Editor edit = getSharedPreferences().edit();
        edit.putString(this.mKey, str);
        edit.apply();
    }

    public void print() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("Preference key=");
        sb.append(getKey());
        sb.append(". value=");
        sb.append(getValue());
        String sb2 = sb.toString();
        String str2 = TAG;
        Log.v(str2, sb2);
        int i = 0;
        while (true) {
            str = "]=";
            if (i >= this.mEntryValues.length) {
                break;
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append("entryValues[");
            sb3.append(i);
            sb3.append(str);
            sb3.append(this.mEntryValues[i]);
            Log.v(str2, sb3.toString());
            i++;
        }
        for (int i2 = 0; i2 < this.mEntries.length; i2++) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("mEntries[");
            sb4.append(i2);
            sb4.append(str);
            sb4.append(this.mEntries[i2]);
            Log.v(str2, sb4.toString());
        }
        for (int i3 = 0; i3 < this.mDefaultValues.length; i3++) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("mDefaultValues[");
            sb5.append(i3);
            sb5.append(str);
            sb5.append(this.mDefaultValues[i3]);
            Log.v(str2, sb5.toString());
        }
    }

    public void setEntries(CharSequence[] charSequenceArr) {
        if (charSequenceArr == null) {
            charSequenceArr = new CharSequence[0];
        }
        this.mEntries = charSequenceArr;
    }

    public void setEntryValues(int i) {
        setEntryValues(this.mContext.getResources().getTextArray(i));
    }

    public void setEntryValues(CharSequence[] charSequenceArr) {
        if (charSequenceArr == null) {
            charSequenceArr = new CharSequence[0];
        }
        this.mEntryValues = charSequenceArr;
    }

    public void setValue(String str) {
        if (findIndexOfValue(str) >= 0) {
            this.mValue = str;
            persistStringValue(str);
            return;
        }
        throw new IllegalArgumentException();
    }

    public void setValueIndex(int i) {
        setValue(this.mEntryValues[i].toString());
    }
}
