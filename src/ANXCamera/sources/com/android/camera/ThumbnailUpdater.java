package com.android.camera;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.AsyncTask;
import com.android.camera.log.Log;
import com.android.camera.module.Module;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol.ActionProcessing;
import java.io.File;

public class ThumbnailUpdater {
    private static final String TAG = "ThumbnailUpdater";
    /* access modifiers changed from: private */
    public ActivityBase mActivityBase;
    /* access modifiers changed from: private */
    public ContentResolver mContentResolver = this.mActivityBase.getContentResolver();
    private AsyncTask<Void, Void, Thumbnail> mLoadThumbnailTask;
    /* access modifiers changed from: private */
    public Thumbnail mThumbnail;

    private class LoadThumbnailTask extends AsyncTask<Void, Void, Thumbnail> {
        private boolean mLookAtCache;

        public LoadThumbnailTask(boolean z) {
            this.mLookAtCache = z;
        }

        /* access modifiers changed from: protected */
        public Thumbnail doInBackground(Void... voidArr) {
            int i;
            StringBuilder sb = new StringBuilder();
            sb.append("LoadThumbnailTask execute, lookatcache=");
            sb.append(this.mLookAtCache);
            String sb2 = sb.toString();
            String str = ThumbnailUpdater.TAG;
            Log.d(str, sb2);
            if (isCancelled()) {
                return null;
            }
            if (ThumbnailUpdater.this.mThumbnail != null) {
                Uri uri = ThumbnailUpdater.this.mThumbnail.getUri();
                if (Util.isUriValid(uri, ThumbnailUpdater.this.mContentResolver) && uri.equals(Thumbnail.getLastThumbnailUri(ThumbnailUpdater.this.mContentResolver))) {
                    return ThumbnailUpdater.this.mThumbnail;
                }
                Module currentModule = ThumbnailUpdater.this.mActivityBase.getCurrentModule();
                if (currentModule != null && currentModule.shouldReleaseLater()) {
                    return ThumbnailUpdater.this.mThumbnail;
                }
            }
            Thumbnail lastThumbnailFromFile = (((ThumbnailUpdater.this.mActivityBase.startFromSecureKeyguard() || ThumbnailUpdater.this.mActivityBase.isGalleryLocked()) && (ThumbnailUpdater.this.mActivityBase.getSecureUriList() == null || ThumbnailUpdater.this.mActivityBase.getSecureUriList().size() <= 0)) || !this.mLookAtCache) ? null : Thumbnail.getLastThumbnailFromFile(ThumbnailUpdater.this.mActivityBase.getFilesDir(), ThumbnailUpdater.this.mContentResolver);
            if (isCancelled()) {
                return null;
            }
            Uri uri2 = lastThumbnailFromFile != null ? lastThumbnailFromFile.getUri() : null;
            Thumbnail[] thumbnailArr = new Thumbnail[1];
            if (ThumbnailUpdater.this.mActivityBase.startFromSecureKeyguard() || ThumbnailUpdater.this.mActivityBase.isGalleryLocked()) {
                i = Thumbnail.getLastThumbnailFromUriList(ThumbnailUpdater.this.mContentResolver, thumbnailArr, ThumbnailUpdater.this.mActivityBase.getSecureUriList(), uri2);
                StringBuilder sb3 = new StringBuilder();
                sb3.append("get last thumbnail from uri list, code is ");
                sb3.append(i);
                Log.d(str, sb3.toString());
            } else {
                i = Thumbnail.getLastThumbnailFromContentResolver(ThumbnailUpdater.this.mContentResolver, thumbnailArr, uri2);
                StringBuilder sb4 = new StringBuilder();
                sb4.append("get last thumbnail from provider, code is ");
                sb4.append(i);
                Log.d(str, sb4.toString());
            }
            if (i == -1) {
                return lastThumbnailFromFile;
            }
            if (i == 0) {
                return null;
            }
            if (i == 1) {
                return thumbnailArr[0];
            }
            if (i != 2) {
                if (i != 3) {
                }
                return null;
            }
            cancel(true);
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Thumbnail thumbnail) {
            StringBuilder sb = new StringBuilder();
            sb.append("LoadThumbnailTask onPostExecute, thumbnai is ");
            sb.append(thumbnail);
            sb.append(isCancelled() ? ", canceled" : ", not canceled");
            Log.d(ThumbnailUpdater.TAG, sb.toString());
            if (!isCancelled()) {
                ThumbnailUpdater.this.setThumbnail(thumbnail, true, false);
            }
        }
    }

    private class SaveThumbnailTask extends AsyncTask<Thumbnail, Void, Void> {
        private SaveThumbnailTask() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Thumbnail... thumbnailArr) {
            File filesDir = ThumbnailUpdater.this.mActivityBase.getFilesDir();
            for (Thumbnail saveLastThumbnailToFile : thumbnailArr) {
                saveLastThumbnailToFile.saveLastThumbnailToFile(filesDir);
            }
            return null;
        }
    }

    public ThumbnailUpdater(ActivityBase activityBase) {
        this.mActivityBase = activityBase;
    }

    public void cancelTask() {
        AsyncTask<Void, Void, Thumbnail> asyncTask = this.mLoadThumbnailTask;
        if (asyncTask != null) {
            asyncTask.cancel(true);
            this.mLoadThumbnailTask = null;
        }
    }

    public void getLastThumbnail() {
        StringBuilder sb = new StringBuilder();
        sb.append("getLastThumbnail, current thumbnailtask is ");
        sb.append(this.mLoadThumbnailTask);
        Log.d(TAG, sb.toString());
        AsyncTask<Void, Void, Thumbnail> asyncTask = this.mLoadThumbnailTask;
        if (asyncTask != null) {
            asyncTask.cancel(true);
        }
        this.mLoadThumbnailTask = new LoadThumbnailTask(true).execute(new Void[0]);
    }

    public void getLastThumbnailUncached() {
        AsyncTask<Void, Void, Thumbnail> asyncTask = this.mLoadThumbnailTask;
        if (asyncTask != null) {
            asyncTask.cancel(true);
        }
        this.mLoadThumbnailTask = new LoadThumbnailTask(false).execute(new Void[0]);
    }

    public Thumbnail getThumbnail() {
        return this.mThumbnail;
    }

    public void saveThumbnailToFile() {
        Thumbnail thumbnail = this.mThumbnail;
        if (thumbnail != null && !thumbnail.fromFile()) {
            new SaveThumbnailTask().execute(new Thumbnail[]{this.mThumbnail});
        }
    }

    public void setThumbnail(Thumbnail thumbnail, boolean z, boolean z2) {
        this.mThumbnail = thumbnail;
        if (z) {
            updateThumbnailView(z2);
        }
    }

    public void updatePreviewThumbnailUri(Uri uri) {
        Thumbnail thumbnail = this.mThumbnail;
        if (thumbnail != null) {
            thumbnail.setUri(uri);
        }
    }

    public void updateThumbnailView(final boolean z) {
        this.mActivityBase.runOnUiThread(new Runnable() {
            public void run() {
                ActionProcessing actionProcessing = (ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
                if (actionProcessing == null) {
                    Log.e(ThumbnailUpdater.TAG, "won't update thumbnail", new RuntimeException());
                    return;
                }
                actionProcessing.updateThumbnail(ThumbnailUpdater.this.mThumbnail, z, ThumbnailUpdater.this.mActivityBase.hashCode());
            }
        });
    }
}
