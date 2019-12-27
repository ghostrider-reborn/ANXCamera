package com.bumptech.glide.load.a;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.HttpException;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.model.l;
import com.bumptech.glide.util.e;
import com.ss.android.vesdk.runtime.cloudconfig.HttpRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

/* compiled from: HttpUrlFetcher */
public class k implements d<InputStream> {
    @VisibleForTesting
    static final b DEFAULT_CONNECTION_FACTORY = new a();
    private static final String TAG = "HttpUrlFetcher";
    private static final int Wd = 5;
    private static final int Xd = -1;
    private final l Td;
    private final b Ud;
    private volatile boolean Vd;
    private InputStream stream;
    private final int timeout;
    private HttpURLConnection urlConnection;

    /* compiled from: HttpUrlFetcher */
    private static class a implements b {
        a() {
        }

        public HttpURLConnection c(URL url) throws IOException {
            return (HttpURLConnection) url.openConnection();
        }
    }

    /* compiled from: HttpUrlFetcher */
    interface b {
        HttpURLConnection c(URL url) throws IOException;
    }

    public k(l lVar, int i) {
        this(lVar, i, DEFAULT_CONNECTION_FACTORY);
    }

    @VisibleForTesting
    k(l lVar, int i, b bVar) {
        this.Td = lVar;
        this.timeout = i;
        this.Ud = bVar;
    }

    private static boolean N(int i) {
        return i / 100 == 2;
    }

    private static boolean O(int i) {
        return i / 100 == 3;
    }

    private InputStream a(HttpURLConnection httpURLConnection) throws IOException {
        if (TextUtils.isEmpty(httpURLConnection.getContentEncoding())) {
            this.stream = com.bumptech.glide.util.b.a(httpURLConnection.getInputStream(), (long) httpURLConnection.getContentLength());
        } else {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Got non empty content encoding: " + httpURLConnection.getContentEncoding());
            }
            this.stream = httpURLConnection.getInputStream();
        }
        return this.stream;
    }

    private InputStream a(URL url, int i, URL url2, Map<String, String> map) throws IOException {
        if (i < 5) {
            if (url2 != null) {
                try {
                    if (url.toURI().equals(url2.toURI())) {
                        throw new HttpException("In re-direct loop");
                    }
                } catch (URISyntaxException unused) {
                }
            }
            this.urlConnection = this.Ud.c(url);
            for (Map.Entry next : map.entrySet()) {
                this.urlConnection.addRequestProperty((String) next.getKey(), (String) next.getValue());
            }
            this.urlConnection.setConnectTimeout(this.timeout);
            this.urlConnection.setReadTimeout(this.timeout);
            this.urlConnection.setUseCaches(false);
            this.urlConnection.setDoInput(true);
            this.urlConnection.setInstanceFollowRedirects(false);
            this.urlConnection.connect();
            this.stream = this.urlConnection.getInputStream();
            if (this.Vd) {
                return null;
            }
            int responseCode = this.urlConnection.getResponseCode();
            if (N(responseCode)) {
                return a(this.urlConnection);
            }
            if (O(responseCode)) {
                String headerField = this.urlConnection.getHeaderField(HttpRequest.HEADER_LOCATION);
                if (!TextUtils.isEmpty(headerField)) {
                    URL url3 = new URL(url, headerField);
                    cleanup();
                    return a(url3, i + 1, url, map);
                }
                throw new HttpException("Received empty or null redirect url");
            } else if (responseCode == -1) {
                throw new HttpException(responseCode);
            } else {
                throw new HttpException(this.urlConnection.getResponseMessage(), responseCode);
            }
        } else {
            throw new HttpException("Too many (> 5) redirects!");
        }
    }

    @NonNull
    public Class<InputStream> M() {
        return InputStream.class;
    }

    public void a(@NonNull Priority priority, @NonNull d.a<? super InputStream> aVar) {
        StringBuilder sb;
        long Gh = e.Gh();
        try {
            aVar.b(a(this.Td.toURL(), 0, (URL) null, this.Td.getHeaders()));
            if (Log.isLoggable(TAG, 2)) {
                sb = new StringBuilder();
                sb.append("Finished http url fetcher fetch in ");
                sb.append(e.g(Gh));
                Log.v(TAG, sb.toString());
            }
        } catch (IOException e2) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Failed to load data for url", e2);
            }
            aVar.b((Exception) e2);
            if (Log.isLoggable(TAG, 2)) {
                sb = new StringBuilder();
            }
        } catch (Throwable th) {
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "Finished http url fetcher fetch in " + e.g(Gh));
            }
            throw th;
        }
    }

    public void cancel() {
        this.Vd = true;
    }

    public void cleanup() {
        InputStream inputStream = this.stream;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
        HttpURLConnection httpURLConnection = this.urlConnection;
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        this.urlConnection = null;
    }

    @NonNull
    public DataSource r() {
        return DataSource.REMOTE;
    }
}
