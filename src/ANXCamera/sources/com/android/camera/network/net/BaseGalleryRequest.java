package com.android.camera.network.net;

import com.android.camera.fragment.CtaNoticeFragment.CTA;
import com.android.camera.log.Log;
import com.android.camera.network.net.base.ErrorCode;
import com.android.camera.network.net.base.RequestError;
import com.android.camera.network.net.json.BaseJsonRequest;
import com.android.camera.network.util.NetworkUtils;
import org.json.JSONObject;

public class BaseGalleryRequest extends BaseJsonRequest<GalleryResponse> {
    private static final String RESPONSE_CODE_TAG = "code";
    private static final String RESPONSE_DATA_TAG = "data";
    private static final String RESPONSE_DESCRIPTION_TAG = "description";
    private static final String RESPONSE_LAST_PAGE_TAG = "lastPage";
    private static final String RESPONSE_SYNC_TAG = "syncTag";
    private static final String RESPONSE_SYNC_TOKEN_TAG = "syncToken";
    private static final String TAG = "BaseGalleryRequest";

    public BaseGalleryRequest(int i) {
        this(i, null);
    }

    public BaseGalleryRequest(int i, String str) {
        super(i, str);
    }

    private boolean checkExecuteCondition() {
        if (!CTA.canConnectNetwork()) {
            handleError(ErrorCode.NETWORK_NOT_CONNECTED, "CTA not confirmed.", null);
            return false;
        } else if (isUseCache() || NetworkUtils.isNetworkConnected()) {
            return true;
        } else {
            handleError(ErrorCode.NETWORK_NOT_CONNECTED, "Network not connected.", null);
            return false;
        }
    }

    public final void execute() {
        if (checkExecuteCondition()) {
            super.execute();
        }
    }

    public final Object[] executeSync() throws RequestError {
        if (checkExecuteCondition()) {
            return super.executeSync();
        }
        throw this.mRequestError;
    }

    /* access modifiers changed from: protected */
    public final void handleResponse(JSONObject jSONObject) {
        String str = "data";
        String str2 = RESPONSE_CODE_TAG;
        if (jSONObject != null) {
            try {
                if (jSONObject.has(str2)) {
                    if (jSONObject.getInt(str2) != ErrorCode.SUCCESS.CODE) {
                        handleError(ErrorCode.SERVER_ERROR, jSONObject.optString(RESPONSE_DESCRIPTION_TAG), jSONObject);
                        return;
                    } else if (jSONObject.isNull(str)) {
                        handleError(ErrorCode.BODY_EMPTY, "response empty data", jSONObject);
                        return;
                    } else {
                        GalleryResponse galleryResponse = new GalleryResponse();
                        galleryResponse.data = jSONObject.optJSONObject(str);
                        galleryResponse.syncTag = jSONObject.optString(RESPONSE_SYNC_TAG, null);
                        galleryResponse.syncToken = jSONObject.optString("syncToken", null);
                        galleryResponse.isLastPage = jSONObject.optBoolean("lastPage", true);
                        onRequestSuccess(galleryResponse);
                        return;
                    }
                }
            } catch (Exception e2) {
                handleError(ErrorCode.HANDLE_ERROR, e2.getMessage(), e2);
                return;
            }
        }
        handleError(ErrorCode.PARSE_ERROR, "response has no code", null);
    }

    public void onRequestError(ErrorCode errorCode, String str, Object obj) {
        deliverError(errorCode, str, obj);
        String format = String.format("%s onRequestError:%s | %s ", new Object[]{getClass().getSimpleName(), errorCode, str});
        String str2 = TAG;
        Log.w(str2, format);
        if (obj instanceof Throwable) {
            Log.w(str2, (Throwable) obj);
        } else if (obj != null) {
            Log.d(str2, obj.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void onRequestSuccess(GalleryResponse galleryResponse) throws Exception {
        onRequestSuccess(galleryResponse.data);
    }

    /* access modifiers changed from: protected */
    public void onRequestSuccess(JSONObject jSONObject) throws Exception {
        deliverResponse(jSONObject);
    }
}
