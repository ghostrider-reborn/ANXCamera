package com.arcsoft.camera.utils;

import android.content.Context;
import android.database.sqlite.SQLiteFullException;
import android.media.MediaScannerConnection;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

/* compiled from: MediaUriManager */
public class c implements MediaScannerConnection.MediaScannerConnectionClient {
    private static final int c = 100;

    /* renamed from: a  reason: collision with root package name */
    private Context f67a;

    /* renamed from: b  reason: collision with root package name */
    private MediaScannerConnection f68b;
    private List<Uri> d = new ArrayList();
    private String e;

    public c(Context context) {
        this.f67a = context;
        this.f68b = new MediaScannerConnection(this.f67a, this);
    }

    public void addPath(String str) {
        this.e = str;
        this.f68b.connect();
    }

    public void addUri(Uri uri) {
        if (uri != null) {
            this.d.add(uri);
        }
    }

    public void addUris(List<Uri> list) {
        if (list != null && !list.isEmpty()) {
            this.d.addAll(list);
        }
    }

    public Uri getCurrentMediaUri() {
        if (this.d.isEmpty()) {
            return null;
        }
        return this.d.get(0);
    }

    public List<Uri> getUris() {
        return this.d;
    }

    public boolean isEmpty() {
        return this.d == null || this.d.isEmpty();
    }

    public void onMediaScannerConnected() {
        try {
            this.f68b.scanFile(this.e, (String) null);
        } catch (SQLiteFullException e2) {
        }
    }

    public void onScanCompleted(String str, Uri uri) {
        try {
            if (this.d.size() > 100) {
                this.d.remove(this.d.size() - 1);
            }
            this.d.add(0, uri);
        } finally {
            this.f68b.disconnect();
        }
    }

    public void release() {
        if (this.f68b != null && this.f68b.isConnected()) {
            this.f68b.disconnect();
        }
    }
}
