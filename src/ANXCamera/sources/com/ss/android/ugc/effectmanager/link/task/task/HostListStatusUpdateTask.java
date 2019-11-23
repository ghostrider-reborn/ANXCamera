package com.ss.android.ugc.effectmanager.link.task.task;

import android.os.Handler;
import com.ss.android.ugc.effectmanager.common.EffectConstants;
import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.common.task.NormalTask;
import com.ss.android.ugc.effectmanager.common.utils.LogUtils;
import com.ss.android.ugc.effectmanager.link.LinkSelector;
import com.ss.android.ugc.effectmanager.link.model.host.Host;
import com.ss.android.ugc.effectmanager.link.model.host.HostStatus;
import com.ss.android.ugc.effectmanager.link.task.result.HostListStatusUpdateTaskResult;
import com.ss.android.ugc.effectmanager.link.task.result.HostStatusUpdateResult;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HostListStatusUpdateTask extends NormalTask {
    private static final long MAX_SORT_TIME = 2147483647L;
    private static final String TAG = "HostListStatusUpdateTask";
    private List<Host> mHosts = new ArrayList();
    private LinkSelector mLinkSelector;
    private String mSpeedApi;

    public HostListStatusUpdateTask(LinkSelector linkSelector, Handler handler, String str) {
        super(handler, str, EffectConstants.NORMAL);
        this.mHosts.clear();
        this.mHosts.addAll(linkSelector.getOriginHosts());
        this.mSpeedApi = linkSelector.getSpeedApi();
        this.mLinkSelector = linkSelector;
    }

    /* JADX WARNING: type inference failed for: r5v6, types: [java.net.URLConnection] */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0103, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0104, code lost:
        r10 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x016b, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x016d, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x016e, code lost:
        r18 = r9;
        r19 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0173, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0175, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0176, code lost:
        r19 = r10;
        r18 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x017a, code lost:
        r10 = r0;
        r11 = r22;
        r9 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0194, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0195, code lost:
        r5 = r9;
        r23 = r14;
        r24 = r15;
        r14 = 2147483647L;
        r10 = r0;
        r9 = null;
        r11 = r5;
        r4 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x01a3, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x01a4, code lost:
        r5 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x01a7, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x01a8, code lost:
        r5 = r9;
        r23 = r14;
        r24 = r15;
        r14 = 2147483647L;
        r10 = r0;
        r9 = null;
        r11 = r5;
        r4 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x01b2, code lost:
        r5 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01f1, code lost:
        r12.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01fc, code lost:
        r5.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:?, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:19:0x0098, B:35:0x012f] */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:19:0x0098, B:38:0x013c] */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:19:0x0098, B:41:0x015f] */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0173 A[ExcHandler: all (th java.lang.Throwable), PHI: r22 
  PHI: (r22v0 java.net.HttpURLConnection) = (r22v2 java.net.HttpURLConnection), (r22v2 java.net.HttpURLConnection), (r22v2 java.net.HttpURLConnection), (r22v2 java.net.HttpURLConnection), (r22v2 java.net.HttpURLConnection), (r22v7 java.net.HttpURLConnection), (r22v7 java.net.HttpURLConnection), (r22v7 java.net.HttpURLConnection), (r22v7 java.net.HttpURLConnection) binds: [B:35:0x012f, B:36:?, B:38:0x013c, B:41:0x015f, B:42:?, B:19:0x0098, B:20:?, B:22:0x00aa, B:23:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:19:0x0098] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x01a3 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:6:0x0048] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x01f1  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x01fc  */
    /* JADX WARNING: Removed duplicated region for block: B:85:? A[RETURN, SYNTHETIC] */
    private void getHostStatus(Host host, long j) {
        HttpURLConnection httpURLConnection;
        long j2;
        StringBuilder sb;
        long j3;
        HttpURLConnection httpURLConnection2;
        Exception exc;
        String str;
        long j4;
        int i;
        HttpURLConnection httpURLConnection3;
        int responseCode;
        long j5;
        int i2;
        StringBuilder sb2;
        long j6;
        long j7;
        String headerField;
        HttpURLConnection httpURLConnection4;
        int i3;
        long j8;
        Host host2 = host;
        if (host2 != null) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(host.getSchema());
            sb3.append("://");
            sb3.append(host.getHost());
            sb3.append(this.mSpeedApi);
            sb3.append(System.currentTimeMillis());
            long currentTimeMillis = System.currentTimeMillis();
            try {
                URL url = new URL(sb3.toString());
                HttpURLConnection openConnection = url.openConnection();
                try {
                    openConnection.setConnectTimeout(this.mLinkSelector.getSpeedTimeOut());
                    openConnection.setReadTimeout(this.mLinkSelector.getSpeedTimeOut());
                    openConnection.setRequestProperty("X-SS-No-Cookie", "true");
                    responseCode = openConnection.getResponseCode();
                    j4 = System.currentTimeMillis() - currentTimeMillis;
                    headerField = openConnection.getHeaderField("X-TT-LOGID");
                    if (responseCode == 200) {
                        try {
                            host2.setSortTime(j4 + j);
                            host.resetStatus();
                            long j9 = j4;
                            i3 = responseCode;
                            httpURLConnection4 = openConnection;
                            sb = sb3;
                            j2 = currentTimeMillis;
                            j3 = 2147483647L;
                            try {
                                sendEvent(url.toString(), host2, responseCode, j4, currentTimeMillis, headerField, (Exception) null, true);
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append("sort speed time = ");
                                j8 = j9;
                                sb4.append(j8);
                                sb4.append(" ");
                                sb4.append(host.getSchema());
                                sb4.append("://");
                                sb4.append(host.getHost());
                                LogUtils.d(TAG, sb4.toString());
                                LogUtils.d(TAG, "sort weight time = " + host.getWeightTime() + " " + host.getSchema() + "://" + host.getHost());
                            } catch (Exception e) {
                                e = e;
                            } catch (Throwable th) {
                            }
                        } catch (Exception e2) {
                            e = e2;
                            i3 = responseCode;
                            httpURLConnection4 = openConnection;
                            sb = sb3;
                            j2 = currentTimeMillis;
                            j3 = 2147483647L;
                            long j10 = j4;
                            str = headerField;
                            i = i3;
                            httpURLConnection2 = httpURLConnection4;
                            exc = e;
                            try {
                                LogUtils.e(TAG, "sort speed error = " + exc);
                                host2.setSortTime(j3);
                                exc.printStackTrace();
                                httpURLConnection3 = httpURLConnection2;
                            } catch (Throwable th2) {
                                th = th2;
                                httpURLConnection3 = httpURLConnection2;
                                httpURLConnection = httpURLConnection3;
                                if (httpURLConnection != null) {
                                }
                                throw th;
                            }
                            try {
                                sendEvent(sb.toString(), host2, i, j4, j2, str, exc, false);
                                if (httpURLConnection3 != null) {
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                httpURLConnection = httpURLConnection3;
                                if (httpURLConnection != null) {
                                }
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            httpURLConnection4 = openConnection;
                            httpURLConnection = httpURLConnection4;
                            if (httpURLConnection != null) {
                            }
                            throw th;
                        }
                    } else {
                        int i4 = responseCode;
                        httpURLConnection4 = openConnection;
                        sb2 = sb3;
                        j6 = currentTimeMillis;
                        j7 = 2147483647L;
                        long j11 = j4;
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("sort speed error code = ");
                        int i5 = i4;
                        sb5.append(i5);
                        LogUtils.e(TAG, sb5.toString());
                        host2.setSortTime(MAX_SORT_TIME);
                        i2 = i5;
                        j5 = j11;
                        sendEvent(url.toString(), host2, i5, j11, j6, headerField, (Exception) null, false);
                    }
                    HttpURLConnection httpURLConnection5 = httpURLConnection4;
                    if (httpURLConnection5 != null) {
                        httpURLConnection5.disconnect();
                        return;
                    }
                    return;
                } catch (Exception e3) {
                    j5 = j4;
                    i2 = responseCode;
                    HttpURLConnection httpURLConnection6 = openConnection;
                    sb2 = sb3;
                    j6 = currentTimeMillis;
                    j7 = 2147483647L;
                    exc = e3;
                    str = null;
                    httpURLConnection2 = httpURLConnection6;
                    i = i2;
                    j4 = j5;
                    LogUtils.e(TAG, "sort speed error = " + exc);
                    host2.setSortTime(j3);
                    exc.printStackTrace();
                    httpURLConnection3 = httpURLConnection2;
                    sendEvent(sb.toString(), host2, i, j4, j2, str, exc, false);
                    if (httpURLConnection3 != null) {
                    }
                } catch (Throwable th5) {
                }
            } catch (Exception e4) {
                sb = sb3;
                j2 = currentTimeMillis;
                j3 = 2147483647L;
                exc = e4;
                j4 = -1;
                str = null;
                httpURLConnection2 = null;
                i = -1;
                LogUtils.e(TAG, "sort speed error = " + exc);
                host2.setSortTime(j3);
                exc.printStackTrace();
                httpURLConnection3 = httpURLConnection2;
                sendEvent(sb.toString(), host2, i, j4, j2, str, exc, false);
                if (httpURLConnection3 != null) {
                }
            } catch (Throwable th6) {
                th = th6;
                httpURLConnection = null;
                if (httpURLConnection != null) {
                }
                throw th;
            }
        } else {
            return;
        }
        j4 = j8;
        str = headerField;
        i = i3;
        httpURLConnection2 = httpURLConnection4;
        exc = e;
        LogUtils.e(TAG, "sort speed error = " + exc);
        host2.setSortTime(j3);
        exc.printStackTrace();
        httpURLConnection3 = httpURLConnection2;
        sendEvent(sb.toString(), host2, i, j4, j2, str, exc, false);
        if (httpURLConnection3 != null) {
        }
    }

    private void sendEvent(String str, Host host, int i, long j, long j2, String str2, Exception exc, boolean z) {
        HostStatus hostStatus = new HostStatus(str, host, i, j, j2, str2, exc, z);
        sendMessage(30, new HostStatusUpdateResult(hostStatus, (ExceptionResult) null));
    }

    private void sendResults() {
        sendMessage(31, new HostListStatusUpdateTaskResult(this.mHosts, (ExceptionResult) null));
    }

    private void sortHost() {
        Collections.sort(this.mHosts, new Comparator<Host>() {
            public int compare(Host host, Host host2) {
                return (int) (host.getSortTime() - host2.getSortTime());
            }
        });
        ArrayList arrayList = new ArrayList(this.mHosts);
        arrayList.clear();
        arrayList.addAll(this.mHosts);
        int i = 0;
        while (i < this.mHosts.size()) {
            Host host = this.mHosts.get(i);
            LogUtils.d(TAG, "weight sort = " + host.getSortTime() + " " + host.getSchema() + "://" + host.getHost() + this.mSpeedApi);
            i++;
            for (int i2 = i; i2 < this.mHosts.size(); i2++) {
                Host host2 = this.mHosts.get(i2);
                if (host.getHost().equals(host2.getHost())) {
                    arrayList.remove(host2);
                }
            }
        }
        this.mHosts.clear();
        this.mHosts.addAll(arrayList);
        LogUtils.d(TAG, "speed distinct = " + this.mHosts.size() + " thread = " + Thread.currentThread());
    }

    private void speedMeasure() {
        for (int i = 0; i < this.mHosts.size(); i++) {
            this.mHosts.get(i).setSortTime(0);
            for (int i2 = 0; i2 < this.mLinkSelector.getRepeatTime(); i2++) {
                getHostStatus(this.mHosts.get(i), this.mHosts.get(i).getSortTime());
            }
        }
    }

    public void execute() {
        speedMeasure();
        sortHost();
        sendResults();
    }
}
