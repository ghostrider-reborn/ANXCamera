package com.bumptech.glide.load.resource.b;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.engine.p;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.g;
import java.util.List;

/* compiled from: ResourceDrawableDecoder */
public class e implements g<Uri, Drawable> {
    private static final int nc = 2;
    private static final int nd = 0;
    private static final int ne = 1;
    private static final int nf = 1;
    private static final int ng = 0;
    private final Context context;

    public e(Context context2) {
        this.context = context2.getApplicationContext();
    }

    @NonNull
    private Context a(Uri uri, String str) {
        try {
            return this.context.createPackageContext(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalArgumentException("Failed to obtain context or unrecognized Uri format for: " + uri, e);
        }
    }

    @DrawableRes
    private int m(Uri uri) {
        Integer num;
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments.size() == 2) {
            String authority = uri.getAuthority();
            String str = pathSegments.get(1);
            num = Integer.valueOf(this.context.getResources().getIdentifier(str, pathSegments.get(0), authority));
        } else {
            if (pathSegments.size() == 1) {
                try {
                    num = Integer.valueOf(pathSegments.get(0));
                } catch (NumberFormatException e) {
                }
            }
            num = null;
        }
        if (num == null) {
            throw new IllegalArgumentException("Unrecognized Uri format: " + uri);
        } else if (num.intValue() != 0) {
            return num.intValue();
        } else {
            throw new IllegalArgumentException("Failed to obtain resource id for: " + uri);
        }
    }

    public boolean a(@NonNull Uri uri, @NonNull f fVar) {
        return uri.getScheme().equals("android.resource");
    }

    @Nullable
    /* renamed from: b */
    public p<Drawable> a(@NonNull Uri uri, int i, int i2, @NonNull f fVar) {
        int m = m(uri);
        String authority = uri.getAuthority();
        return d.c(a.a(this.context, authority.equals(this.context.getPackageName()) ? this.context : a(uri, authority), m));
    }
}
