package com.bumptech.glide.load.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.bumptech.glide.load.c;
import com.bumptech.glide.util.i;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Map;

/* compiled from: GlideUrl */
public class l implements c {
    private static final String Th = "@#&=*+-_.,:!?()/~'%;$";
    @Nullable
    private final String Ph;
    @Nullable
    private String Qh;
    @Nullable
    private URL Rh;
    @Nullable
    private volatile byte[] Sh;
    private int hashCode;
    private final n headers;
    @Nullable
    private final URL url;

    public l(String str) {
        this(str, n.DEFAULT);
    }

    public l(String str, n nVar) {
        this.url = null;
        i.u(str);
        this.Ph = str;
        i.checkNotNull(nVar);
        this.headers = nVar;
    }

    public l(URL url2) {
        this(url2, n.DEFAULT);
    }

    public l(URL url2, n nVar) {
        i.checkNotNull(url2);
        this.url = url2;
        this.Ph = null;
        i.checkNotNull(nVar);
        this.headers = nVar;
    }

    private URL Ak() throws MalformedURLException {
        if (this.Rh == null) {
            this.Rh = new URL(zk());
        }
        return this.Rh;
    }

    private byte[] yk() {
        if (this.Sh == null) {
            this.Sh = getCacheKey().getBytes(c.CHARSET);
        }
        return this.Sh;
    }

    private String zk() {
        if (TextUtils.isEmpty(this.Qh)) {
            String str = this.Ph;
            if (TextUtils.isEmpty(str)) {
                URL url2 = this.url;
                i.checkNotNull(url2);
                str = url2.toString();
            }
            this.Qh = Uri.encode(str, Th);
        }
        return this.Qh;
    }

    public String Dg() {
        return zk();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof l)) {
            return false;
        }
        l lVar = (l) obj;
        return getCacheKey().equals(lVar.getCacheKey()) && this.headers.equals(lVar.headers);
    }

    public String getCacheKey() {
        String str = this.Ph;
        if (str != null) {
            return str;
        }
        URL url2 = this.url;
        i.checkNotNull(url2);
        return url2.toString();
    }

    public Map<String, String> getHeaders() {
        return this.headers.getHeaders();
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = getCacheKey().hashCode();
            this.hashCode = (this.hashCode * 31) + this.headers.hashCode();
        }
        return this.hashCode;
    }

    public String toString() {
        return getCacheKey();
    }

    public URL toURL() throws MalformedURLException {
        return Ak();
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(yk());
    }
}
