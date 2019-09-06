package com.bumptech.glide.load.engine.b;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/* compiled from: RuntimeCompat */
class f implements FilenameFilter {
    final /* synthetic */ Pattern rh;

    f(Pattern pattern) {
        this.rh = pattern;
    }

    public boolean accept(File file, String str) {
        return this.rh.matcher(str).matches();
    }
}
