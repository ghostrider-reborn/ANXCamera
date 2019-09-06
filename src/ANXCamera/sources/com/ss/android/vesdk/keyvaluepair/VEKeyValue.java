package com.ss.android.vesdk.keyvaluepair;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class VEKeyValue {
    private boolean mIsFirst = true;
    private Map<String, String> mKVPair = new HashMap();
    private StringBuilder mKVSB = new StringBuilder();

    private void addSB(String str, String str2) {
        if (!this.mIsFirst) {
            this.mKVSB.append(",");
        }
        String str3 = "\"";
        this.mKVSB.append(str3);
        this.mKVSB.append(str);
        this.mKVSB.append(str3);
        this.mKVSB.append(":");
        this.mKVSB.append(str3);
        this.mKVSB.append(str2);
        this.mKVSB.append(str3);
        if (this.mIsFirst) {
            this.mIsFirst = false;
        }
    }

    public VEKeyValue add(String str, float f2) {
        Map<String, String> map = this.mKVPair;
        StringBuilder sb = new StringBuilder();
        sb.append(f2);
        String str2 = "";
        sb.append(str2);
        map.put(str, sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(f2);
        sb2.append(str2);
        addSB(str, sb2.toString());
        return this;
    }

    public VEKeyValue add(String str, int i) {
        Map<String, String> map = this.mKVPair;
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        String str2 = "";
        sb.append(str2);
        map.put(str, sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(i);
        sb2.append(str2);
        addSB(str, sb2.toString());
        return this;
    }

    public VEKeyValue add(String str, String str2) {
        this.mKVPair.put(str, str2);
        addSB(str, str2);
        return this;
    }

    @Nullable
    public JSONObject parseJsonObj() {
        try {
            JSONObject jSONObject = new JSONObject();
            for (String str : this.mKVPair.keySet()) {
                jSONObject.put(str, (String) this.mKVPair.get(str));
            }
            return jSONObject;
        } catch (JSONException unused) {
            return null;
        }
    }

    @NonNull
    public String parseJsonStr() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(this.mKVSB);
        sb.append("}");
        return sb.toString();
    }
}
