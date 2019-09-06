package com.bumptech.glide.d;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.ImageHeaderParser;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ImageHeaderParserRegistry */
public final class b {
    private final List<ImageHeaderParser> ne = new ArrayList();

    @NonNull
    public synchronized List<ImageHeaderParser> Ng() {
        return this.ne;
    }

    public synchronized void b(@NonNull ImageHeaderParser imageHeaderParser) {
        this.ne.add(imageHeaderParser);
    }
}
