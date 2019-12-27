package com.android.camera.storage;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import com.android.camera.FileCompat;
import com.android.camera.Thumbnail;
import com.android.camera.Util;
import com.android.camera.log.Log;
import java.io.File;

public class VideoSaveRequest implements SaveRequest {
    private static final String TAG = "VideoSaveRequest";
    private Context context;
    private ContentValues mContentValues;
    private boolean mIsFinal;
    public Uri mUri;
    private String mVideoPath;
    private SaverCallback saverCallback;

    public VideoSaveRequest(String str, ContentValues contentValues, boolean z) {
        this.mVideoPath = str;
        this.mContentValues = contentValues;
        this.mIsFinal = z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0066  */
    private Uri addVideoToMediaStore(String str, ContentValues contentValues) {
        long j;
        StringBuilder sb;
        ParcelFileDescriptor parcelFileDescriptor;
        Uri uri = null;
        if (!Storage.isUseDocumentMode()) {
            j = Util.getDuration(str);
            if (0 == j) {
                boolean delete = new File(str).delete();
                Log.e(TAG, "delete invalid video: " + str + ", deleted : " + delete);
                return null;
            }
        } else {
            try {
                parcelFileDescriptor = FileCompat.getParcelFileDescriptor(str, false);
                try {
                    j = Util.getDuration(parcelFileDescriptor.getFileDescriptor());
                    Util.closeSafely(parcelFileDescriptor);
                    FileCompat.removeDocumentFileForPath(str);
                } catch (Exception unused) {
                    Util.closeSafely(parcelFileDescriptor);
                    FileCompat.removeDocumentFileForPath(str);
                    j = 0;
                    if (0 == j) {
                    }
                    contentValues.put("_size", Long.valueOf(new File(str).length()));
                    contentValues.put("duration", Long.valueOf(j));
                    contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
                    long currentTimeMillis = System.currentTimeMillis();
                    uri = this.context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
                    Log.d(TAG, "addVideoToMediaStore: insert video cost: " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
                    sb = new StringBuilder();
                    sb.append("Current video URI: ");
                    sb.append(uri);
                    Log.d(TAG, sb.toString());
                    return uri;
                } catch (Throwable th) {
                    th = th;
                    Util.closeSafely(parcelFileDescriptor);
                    FileCompat.removeDocumentFileForPath(str);
                    throw th;
                }
            } catch (Exception unused2) {
                parcelFileDescriptor = null;
                Util.closeSafely(parcelFileDescriptor);
                FileCompat.removeDocumentFileForPath(str);
                j = 0;
                if (0 == j) {
                }
                contentValues.put("_size", Long.valueOf(new File(str).length()));
                contentValues.put("duration", Long.valueOf(j));
                contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
                long currentTimeMillis2 = System.currentTimeMillis();
                uri = this.context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
                Log.d(TAG, "addVideoToMediaStore: insert video cost: " + (System.currentTimeMillis() - currentTimeMillis2) + "ms");
                sb = new StringBuilder();
                sb.append("Current video URI: ");
                sb.append(uri);
                Log.d(TAG, sb.toString());
                return uri;
            } catch (Throwable th2) {
                th = th2;
                parcelFileDescriptor = null;
                Util.closeSafely(parcelFileDescriptor);
                FileCompat.removeDocumentFileForPath(str);
                throw th;
            }
            if (0 == j) {
                Log.e(TAG, "delete invalid video: " + str);
                FileCompat.deleteFile(str);
                return null;
            }
        }
        contentValues.put("_size", Long.valueOf(new File(str).length()));
        contentValues.put("duration", Long.valueOf(j));
        contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        try {
            long currentTimeMillis22 = System.currentTimeMillis();
            uri = this.context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
            Log.d(TAG, "addVideoToMediaStore: insert video cost: " + (System.currentTimeMillis() - currentTimeMillis22) + "ms");
            sb = new StringBuilder();
        } catch (Exception e2) {
            Log.e(TAG, "failed to add video to media store", e2);
            sb = new StringBuilder();
        } catch (Throwable th3) {
            Log.d(TAG, "Current video URI: " + uri);
            throw th3;
        }
        sb.append("Current video URI: ");
        sb.append(uri);
        Log.d(TAG, sb.toString());
        return uri;
    }

    private boolean checkExternalStorageThumbnailInterupt(String str) {
        boolean isSecondPhoneStorage = Storage.isSecondPhoneStorage(str);
        boolean isUsePhoneStorage = Storage.isUsePhoneStorage();
        if (!isSecondPhoneStorage || !isUsePhoneStorage) {
            return true;
        }
        Log.w(TAG, "save video: sd card was ejected");
        return false;
    }

    public int getSize() {
        return 0;
    }

    public boolean isFinal() {
        return this.mIsFinal;
    }

    public void onFinish() {
        Log.d(TAG, "onFinish: runnable process finished");
        this.saverCallback.onSaveFinish(getSize());
    }

    public void run() {
        save();
        onFinish();
    }

    public void save() {
        Log.d(TAG, "save video: start");
        String asString = this.mContentValues.getAsString("_data");
        if (!asString.equals(this.mVideoPath)) {
            if (new File(this.mVideoPath).renameTo(new File(asString))) {
                this.mVideoPath = asString;
            } else {
                this.mContentValues.put("_data", this.mVideoPath);
            }
        }
        boolean needThumbnail = this.saverCallback.needThumbnail(isFinal());
        this.mUri = addVideoToMediaStore(this.mVideoPath, this.mContentValues);
        if (this.mUri == null) {
            Log.w(TAG, "insert MediaProvider failed, attempt to find uri by path, " + this.mVideoPath);
            this.mUri = MediaProviderUtil.getContentUriFromPath(this.context, this.mVideoPath);
        }
        Log.d(TAG, "save video: media has been stored, Uri: " + this.mUri + ", has thumbnail: " + needThumbnail);
        if (this.mUri != null && checkExternalStorageThumbnailInterupt(this.mVideoPath)) {
            boolean z = false;
            if (needThumbnail) {
                Bitmap createVideoThumbnailBitmap = Thumbnail.createVideoThumbnailBitmap(this.mVideoPath, 512);
                if (createVideoThumbnailBitmap != null) {
                    this.saverCallback.postUpdateThumbnail(Thumbnail.createThumbnail(this.mUri, createVideoThumbnailBitmap, 0, false), true);
                } else {
                    this.saverCallback.postHideThumbnailProgressing();
                }
            }
            this.saverCallback.notifyNewMediaData(this.mUri, this.mContentValues.getAsString("title"), 1);
            Context context2 = this.context;
            String str = this.mVideoPath;
            if (this.mContentValues.get("latitude") == null && this.mContentValues.get("longitude") == null) {
                z = true;
            }
            Storage.saveToCloudAlbum(context2, str, -1, z);
        } else if (needThumbnail) {
            this.saverCallback.postHideThumbnailProgressing();
        }
        Log.d(TAG, "save video: end");
    }

    public void setContextAndCallback(Context context2, SaverCallback saverCallback2) {
        this.context = context2;
        this.saverCallback = saverCallback2;
    }
}
