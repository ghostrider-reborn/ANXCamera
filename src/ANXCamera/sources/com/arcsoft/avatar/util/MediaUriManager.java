package com.arcsoft.avatar.util;

import android.content.Context;
import android.database.sqlite.SQLiteFullException;
import android.media.MediaScannerConnection;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

public class MediaUriManager implements MediaScannerConnection.MediaScannerConnectionClient {
    private static final int c = 100;

    /* renamed from: a  reason: collision with root package name */
    private Context f49a;

    /* renamed from: b  reason: collision with root package name */
    private MediaScannerConnection f50b;
    private List<Uri> d = new ArrayList();
    private String e;

    public MediaUriManager(Context context) {
        this.f49a = context;
        this.f50b = new MediaScannerConnection(this.f49a, this);
    }

    public void addPath(String str) {
        this.e = str;
        this.f50b.connect();
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
            this.f50b.scanFile(this.e, (String) null);
        } catch (SQLiteFullException e2) {
            e2.printStackTrace();
        }
    }

    public void onScanCompleted(String str, Uri uri) {
        try {
            if (this.d.size() > 100) {
                this.d.remove(this.d.size() - 1);
            }
            this.d.add(0, uri);
        } finally {
            this.f50b.disconnect();
        }
    }

    public void release() {
        if (this.f50b != null && this.f50b.isConnected()) {
            this.f50b.disconnect();
        }
    }
}
