package com.android.camera.fragment.vv;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.statistics.E2EScenario;
import com.android.camera.Util;
import com.ss.android.ugc.effectmanager.effect.model.ComposerHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class VVItem extends BaseResourceItem {
    public static final Creator<VVItem> CREATOR = new Creator<VVItem>() {
        public VVItem createFromParcel(Parcel parcel) {
            return new VVItem(parcel);
        }

        public VVItem[] newArray(int i) {
            return new VVItem[i];
        }
    };
    public String composeJsonPath;
    public String configJsonPath;
    public String coverPath;
    public List<Long> durationList;
    public String filterPath;
    public transient int index;
    public String musicPath;
    public String name;
    public String previewVideoPath;
    public long totalDuration;

    public VVItem() {
    }

    protected VVItem(Parcel parcel) {
        this.name = parcel.readString();
        this.coverPath = parcel.readString();
        this.previewVideoPath = parcel.readString();
        this.filterPath = parcel.readString();
        this.configJsonPath = parcel.readString();
        this.composeJsonPath = parcel.readString();
        this.musicPath = parcel.readString();
        this.durationList = new ArrayList();
        parcel.readList(this.durationList, Long.class.getClassLoader());
        this.totalDuration = parcel.readLong();
        this.id = parcel.readString();
        this.versionCode = parcel.readInt();
        this.uri = parcel.readString();
        this.archivesPath = parcel.readString();
    }

    public VVItem(String str, String str2, String str3) {
        this.id = str;
        this.name = str2;
        this.coverPath = str3;
    }

    public int describeContents() {
        return 0;
    }

    public void fillDetailData(JSONObject jSONObject) {
    }

    public long getDuration(int i) {
        return ((Long) this.durationList.get(i)).longValue();
    }

    public int getEssentialFragmentSize() {
        return this.durationList.size();
    }

    public long getTotalDuration() {
        return this.totalDuration;
    }

    public void onDecompressFinished(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("pv/cover.png");
        this.coverPath = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append("pv/preview.mov");
        this.previewVideoPath = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append("filter.png");
        this.filterPath = sb3.toString();
        StringBuilder sb4 = new StringBuilder();
        sb4.append(str);
        sb4.append(ComposerHelper.CONFIG_FILE_NAME);
        this.configJsonPath = sb4.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(str);
        sb5.append("compose.json");
        this.composeJsonPath = sb5.toString();
        StringBuilder sb6 = new StringBuilder();
        sb6.append(str);
        sb6.append("bgm.mp3");
        this.musicPath = sb6.toString();
    }

    public void parseSummaryData(JSONObject jSONObject, int i) {
        this.index = i;
        this.id = jSONObject.optString("id");
        this.uri = jSONObject.optString("uri");
        JSONArray optJSONArray = jSONObject.optJSONArray("fragments");
        this.durationList = new ArrayList();
        for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
            this.durationList.add(Long.valueOf(optJSONArray.optLong(i2)));
            this.totalDuration += optJSONArray.optLong(i2);
        }
        String str = Util.sRegion;
        JSONArray optJSONArray2 = jSONObject.optJSONArray("i18n");
        for (int i3 = 0; i3 < optJSONArray2.length(); i3++) {
            JSONObject optJSONObject = optJSONArray2.optJSONObject(i3);
            String optString = optJSONObject.optString("lang");
            String str2 = "name";
            if (optString.equalsIgnoreCase(E2EScenario.DEFAULT_CATEGORY)) {
                this.name = optJSONObject.optString(str2);
            } else if (optString.equalsIgnoreCase(str)) {
                this.name = optJSONObject.optString(str2);
                return;
            }
        }
    }

    public boolean simpleVerification(String str) {
        return new File(str, ComposerHelper.CONFIG_FILE_NAME).exists() && new File(str, "compose.json").exists();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.coverPath);
        parcel.writeString(this.previewVideoPath);
        parcel.writeString(this.filterPath);
        parcel.writeString(this.configJsonPath);
        parcel.writeString(this.composeJsonPath);
        parcel.writeString(this.musicPath);
        parcel.writeList(this.durationList);
        parcel.writeLong(this.totalDuration);
        parcel.writeString(this.id);
        parcel.writeInt(this.versionCode);
        parcel.writeString(this.uri);
        parcel.writeString(this.archivesPath);
    }
}
