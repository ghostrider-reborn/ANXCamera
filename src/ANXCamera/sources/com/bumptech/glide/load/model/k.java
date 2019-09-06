package com.bumptech.glide.load.model;

import com.bumptech.glide.load.model.i.d;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: FileLoader */
class k implements d<InputStream> {
    k() {
    }

    public Class<InputStream> M() {
        return InputStream.class;
    }

    public InputStream b(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    /* renamed from: d */
    public void e(InputStream inputStream) throws IOException {
        inputStream.close();
    }
}
