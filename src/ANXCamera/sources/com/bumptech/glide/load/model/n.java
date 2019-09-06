package com.bumptech.glide.load.model;

import com.bumptech.glide.load.model.p.a;
import java.util.Map;

/* compiled from: Headers */
public interface n {
    public static final n DEFAULT = new a().build();
    @Deprecated
    public static final n NONE = new m();

    Map<String, String> getHeaders();
}
