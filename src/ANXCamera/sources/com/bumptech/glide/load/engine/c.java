package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.a.a;
import com.bumptech.glide.load.f;
import java.io.File;

/* compiled from: DataCacheWriter */
class c<DataType> implements a.b {
    private final DataType data;
    private final com.bumptech.glide.load.a<DataType> fQ;
    private final f fR;

    c(com.bumptech.glide.load.a<DataType> aVar, DataType datatype, f fVar) {
        this.fQ = aVar;
        this.data = datatype;
        this.fR = fVar;
    }

    public boolean f(@NonNull File file) {
        return this.fQ.a(this.data, file, this.fR);
    }
}
