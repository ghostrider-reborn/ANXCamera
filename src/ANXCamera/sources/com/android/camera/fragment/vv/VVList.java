package com.android.camera.fragment.vv;

import com.google.android.apps.photos.api.PhotosOemApi;
import org.json.JSONArray;
import org.json.JSONObject;

public class VVList extends BaseResourceList<VVItem> {
    public static final int TYPE = 1;
    public String version;

    public JSONArray getJsonArray(JSONObject jSONObject) {
        return jSONObject.optJSONArray(PhotosOemApi.PATH_SPECIAL_TYPE_DATA);
    }

    public int getResourceType() {
        return 1;
    }

    public void parseExtraData(JSONObject jSONObject) {
        this.version = jSONObject.optString("version");
    }

    public VVItem parseSingleItem(JSONObject jSONObject, int i) {
        VVItem vVItem = new VVItem();
        vVItem.parseSummaryData(jSONObject, i);
        return vVItem;
    }
}
