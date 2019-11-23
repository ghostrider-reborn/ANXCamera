package com.bumptech.glide.load.model;

import com.bumptech.glide.load.model.j;
import java.util.Collections;
import java.util.Map;

/* compiled from: Headers */
public interface h {
    @Deprecated
    public static final h kN = new h() {
        public Map<String, String> getHeaders() {
            return Collections.emptyMap();
        }
    };
    public static final h kO = new j.a().cJ();

    Map<String, String> getHeaders();
}
