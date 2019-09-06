package com.arcsoft.avatar.util;

import android.content.Context;
import android.database.sqlite.SQLiteFullException;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

public class MediaUriManager implements MediaScannerConnectionClient {

    /* renamed from: c reason: collision with root package name */
    private static final int f130c = 100;

    /* renamed from: a reason: collision with root package name */
    private Context f131a;

    /* renamed from: b reason: collision with root package name */
    private MediaScannerConnection f132b;

    /* renamed from: d reason: collision with root package name */
    private List<Uri> f133d = new ArrayList();

    /* renamed from: e reason: collision with root package name */
    private String f134e;

    public MediaUriManager(Context context) {
        this.f131a = context;
        this.f132b = new MediaScannerConnection(this.f131a, this);
    }

    public void addPath(String str) {
        this.f134e = str;
        this.f132b.connect();
    }

    public void addUri(Uri uri) {
        if (uri != null) {
            this.f133d.add(uri);
        }
    }

    public void addUris(List<Uri> list) {
        if (list != null && !list.isEmpty()) {
            this.f133d.addAll(list);
        }
    }

    public Uri getCurrentMediaUri() {
        if (this.f133d.isEmpty()) {
            return null;
        }
        return (Uri) this.f133d.get(0);
    }

    public List<Uri> getUris() {
        return this.f133d;
    }

    public boolean isEmpty() {
        List<Uri> list = this.f133d;
        return list == null || list.isEmpty();
    }

    public void onMediaScannerConnected() {
        try {
            this.f132b.scanFile(this.f134e, null);
        } catch (SQLiteFullException e2) {
            e2.printStackTrace();
        }
    }

    public void onScanCompleted(String str, Uri uri) {
        try {
            if (this.f133d.size() > 100) {
                this.f133d.remove(this.f133d.size() - 1);
            }
            this.f133d.add(0, uri);
        } finally {
            this.f132b.disconnect();
        }
    }

    public void release() {
        MediaScannerConnection mediaScannerConnection = this.f132b;
        if (mediaScannerConnection != null && mediaScannerConnection.isConnected()) {
            this.f132b.disconnect();
        }
    }
}
