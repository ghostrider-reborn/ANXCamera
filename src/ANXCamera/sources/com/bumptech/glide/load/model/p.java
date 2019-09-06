package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: LazyHeaders */
public final class p implements n {
    private volatile Map<String, String> Zh;
    private final Map<String, List<o>> headers;

    /* compiled from: LazyHeaders */
    public static final class a {
        private static final String Wh = "User-Agent";
        private static final String Xh = getSanitizedUserAgent();
        private static final Map<String, List<o>> Yh;
        private boolean Uh = true;
        private boolean Vh = true;
        private Map<String, List<o>> headers = Yh;

        static {
            HashMap hashMap = new HashMap(2);
            if (!TextUtils.isEmpty(Xh)) {
                hashMap.put("User-Agent", Collections.singletonList(new b(Xh)));
            }
            Yh = Collections.unmodifiableMap(hashMap);
        }

        private Map<String, List<o>> Bk() {
            HashMap hashMap = new HashMap(this.headers.size());
            for (Entry entry : this.headers.entrySet()) {
                hashMap.put(entry.getKey(), new ArrayList((Collection) entry.getValue()));
            }
            return hashMap;
        }

        private void Ck() {
            if (this.Uh) {
                this.Uh = false;
                this.headers = Bk();
            }
        }

        @VisibleForTesting
        static String getSanitizedUserAgent() {
            String property = System.getProperty("http.agent");
            if (TextUtils.isEmpty(property)) {
                return property;
            }
            int length = property.length();
            StringBuilder sb = new StringBuilder(property.length());
            for (int i = 0; i < length; i++) {
                char charAt = property.charAt(i);
                if ((charAt > 31 || charAt == 9) && charAt < 127) {
                    sb.append(charAt);
                } else {
                    sb.append('?');
                }
            }
            return sb.toString();
        }

        private List<o> z(String str) {
            List<o> list = (List) this.headers.get(str);
            if (list != null) {
                return list;
            }
            ArrayList arrayList = new ArrayList();
            this.headers.put(str, arrayList);
            return arrayList;
        }

        public a a(String str, o oVar) {
            if (this.Vh && "User-Agent".equalsIgnoreCase(str)) {
                return b(str, oVar);
            }
            Ck();
            z(str).add(oVar);
            return this;
        }

        public a addHeader(String str, String str2) {
            return a(str, new b(str2));
        }

        public a b(String str, o oVar) {
            Ck();
            if (oVar == null) {
                this.headers.remove(str);
            } else {
                List z = z(str);
                z.clear();
                z.add(oVar);
            }
            if (this.Vh && "User-Agent".equalsIgnoreCase(str)) {
                this.Vh = false;
            }
            return this;
        }

        public p build() {
            this.Uh = true;
            return new p(this.headers);
        }

        public a setHeader(String str, String str2) {
            return b(str, str2 == null ? null : new b(str2));
        }
    }

    /* compiled from: LazyHeaders */
    static final class b implements o {
        private final String value;

        b(String str) {
            this.value = str;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof b)) {
                return false;
            }
            return this.value.equals(((b) obj).value);
        }

        public int hashCode() {
            return this.value.hashCode();
        }

        public String o() {
            return this.value;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("StringHeaderFactory{value='");
            sb.append(this.value);
            sb.append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    p(Map<String, List<o>> map) {
        this.headers = Collections.unmodifiableMap(map);
    }

    private Map<String, String> Dk() {
        HashMap hashMap = new HashMap();
        for (Entry entry : this.headers.entrySet()) {
            String e2 = e((List) entry.getValue());
            if (!TextUtils.isEmpty(e2)) {
                hashMap.put(entry.getKey(), e2);
            }
        }
        return hashMap;
    }

    @NonNull
    private String e(@NonNull List<o> list) {
        StringBuilder sb = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            String o = ((o) list.get(i)).o();
            if (!TextUtils.isEmpty(o)) {
                sb.append(o);
                if (i != list.size() - 1) {
                    sb.append(',');
                }
            }
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof p)) {
            return false;
        }
        return this.headers.equals(((p) obj).headers);
    }

    public Map<String, String> getHeaders() {
        if (this.Zh == null) {
            synchronized (this) {
                if (this.Zh == null) {
                    this.Zh = Collections.unmodifiableMap(Dk());
                }
            }
        }
        return this.Zh;
    }

    public int hashCode() {
        return this.headers.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LazyHeaders{headers=");
        sb.append(this.headers);
        sb.append('}');
        return sb.toString();
    }
}
