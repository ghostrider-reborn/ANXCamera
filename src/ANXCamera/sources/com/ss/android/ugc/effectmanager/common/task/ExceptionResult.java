package com.ss.android.ugc.effectmanager.common.task;

import android.accounts.NetworkErrorException;
import com.ss.android.ugc.effectmanager.common.ErrorConstants;
import com.ss.android.ugc.effectmanager.common.exception.MD5Exception;
import com.ss.android.ugc.effectmanager.common.exception.StatusCodeException;
import com.ss.android.ugc.effectmanager.common.exception.UnzipException;
import com.ss.android.ugc.effectmanager.common.exception.UrlNotExistException;
import com.ss.android.ugc.effectmanager.common.model.NetException;
import java.io.IOException;
import org.json.JSONException;

public class ExceptionResult {
    private int errorCode;
    private Exception exception;
    private String msg;
    private String remoteIp;
    private String requestUrl;
    private String selectedHost;

    public ExceptionResult(int i) {
        this.errorCode = -1;
        this.errorCode = i;
        this.msg = ErrorConstants.APIErrorHandle(i);
        this.exception = null;
    }

    public ExceptionResult(int i, Exception exc) {
        this.errorCode = -1;
        this.errorCode = i;
        this.msg = ErrorConstants.APIErrorHandle(i);
        this.exception = exc;
    }

    public ExceptionResult(Exception exc) {
        this(exc, null, null, null);
    }

    public ExceptionResult(Exception exc, String str, String str2, String str3) {
        this.errorCode = -1;
        this.requestUrl = str;
        this.selectedHost = str2;
        this.remoteIp = str3;
        this.exception = exc;
        if (exc instanceof NetException) {
            this.errorCode = ((NetException) exc).getStatus_code().intValue();
            this.msg = exc.getMessage();
        } else if (exc instanceof StatusCodeException) {
            this.errorCode = ((StatusCodeException) exc).getStatusCode();
            this.msg = exc.getMessage();
        } else if (exc instanceof JSONException) {
            this.errorCode = ErrorConstants.CODE_JSON_CONVERT_ERROR;
            this.msg = exc.getMessage();
        } else if (exc instanceof NetworkErrorException) {
            this.errorCode = ErrorConstants.CODE_DOWNLOAD_ERROR;
            this.msg = exc.getMessage();
        } else if (exc instanceof UrlNotExistException) {
            this.errorCode = ErrorConstants.CODE_URL_NOT_EXIST;
            this.msg = exc.getMessage();
        } else if (exc instanceof UnzipException) {
            this.errorCode = ErrorConstants.CODE_UNZIP_FAIL;
            this.msg = exc.getMessage();
        } else if (exc instanceof MD5Exception) {
            this.errorCode = ErrorConstants.CODE_MD5_ERROR;
            this.msg = exc.getMessage();
        } else if (exc instanceof IOException) {
            this.errorCode = ErrorConstants.CODE_IO_FAIL;
            this.msg = exc.getMessage();
        } else if (exc != null) {
            if (ErrorConstants.EXCEPTION_NO_NETWORK.equals(exc.getMessage())) {
                this.errorCode = ErrorConstants.CODE_NO_NETWORK;
            } else {
                this.errorCode = ErrorConstants.CODE_SDK_ERROR;
            }
            this.msg = exc.getMessage();
        } else {
            this.errorCode = 1;
            this.msg = ErrorConstants.APIErrorHandle(this.errorCode);
        }
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public Exception getException() {
        return this.exception;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setErrorCode(int i) {
        this.errorCode = i;
    }

    public void setException(Exception exc) {
        this.exception = exc;
    }

    public void setMsg(String str) {
        this.msg = str;
    }

    public void setTrackParams(String str, String str2, String str3) {
        this.requestUrl = str;
        this.selectedHost = str2;
        this.remoteIp = str3;
    }

    public String toString() {
        String str = ", remoteIp='";
        String str2 = ", selectedHost='";
        String str3 = ", requestUrl='";
        String str4 = ", msg='";
        String str5 = "ExceptionResult{errorCode=";
        if (this.exception != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str5);
            sb.append(this.errorCode);
            sb.append(str4);
            sb.append(this.msg);
            sb.append('\'');
            sb.append(str3);
            sb.append(this.requestUrl);
            sb.append('\'');
            sb.append(str2);
            sb.append(this.selectedHost);
            sb.append('\'');
            sb.append(str);
            sb.append(this.remoteIp);
            sb.append('\'');
            sb.append(", exception=");
            sb.append(this.exception.getMessage());
            sb.append('}');
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str5);
        sb2.append(this.errorCode);
        sb2.append(str4);
        sb2.append(this.msg);
        sb2.append(str3);
        sb2.append(this.requestUrl);
        sb2.append('\'');
        sb2.append(str2);
        sb2.append(this.selectedHost);
        sb2.append('\'');
        sb2.append(str);
        sb2.append(this.remoteIp);
        sb2.append('\'');
        sb2.append('}');
        return sb2.toString();
    }
}
