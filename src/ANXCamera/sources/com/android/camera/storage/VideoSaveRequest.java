package com.android.camera.storage;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore.Video.Media;
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
        String str2 = "Current video URI: ";
        boolean isUseDocumentMode = Storage.isUseDocumentMode();
        String str3 = "delete invalid video: ";
        Uri uri = null;
        String str4 = TAG;
        if (!isUseDocumentMode) {
            j = Util.getDuration(str);
            if (0 == j) {
                boolean delete = new File(str).delete();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str3);
                sb2.append(str);
                sb2.append(", deleted : ");
                sb2.append(delete);
                Log.e(str4, sb2.toString());
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
                    uri = this.context.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("addVideoToMediaStore: insert video cost: ");
                    sb3.append(System.currentTimeMillis() - currentTimeMillis);
                    sb3.append("ms");
                    Log.d(str4, sb3.toString());
                    sb = new StringBuilder();
                    sb.append(str2);
                    sb.append(uri);
                    Log.d(str4, sb.toString());
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
                uri = this.context.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
                StringBuilder sb32 = new StringBuilder();
                sb32.append("addVideoToMediaStore: insert video cost: ");
                sb32.append(System.currentTimeMillis() - currentTimeMillis2);
                sb32.append("ms");
                Log.d(str4, sb32.toString());
                sb = new StringBuilder();
                sb.append(str2);
                sb.append(uri);
                Log.d(str4, sb.toString());
                return uri;
            } catch (Throwable th2) {
                th = th2;
                parcelFileDescriptor = null;
                Util.closeSafely(parcelFileDescriptor);
                FileCompat.removeDocumentFileForPath(str);
                throw th;
            }
            if (0 == j) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(str3);
                sb4.append(str);
                Log.e(str4, sb4.toString());
                FileCompat.deleteFile(str);
                return null;
            }
        }
        contentValues.put("_size", Long.valueOf(new File(str).length()));
        contentValues.put("duration", Long.valueOf(j));
        contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        try {
            long currentTimeMillis22 = System.currentTimeMillis();
            uri = this.context.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
            StringBuilder sb322 = new StringBuilder();
            sb322.append("addVideoToMediaStore: insert video cost: ");
            sb322.append(System.currentTimeMillis() - currentTimeMillis22);
            sb322.append("ms");
            Log.d(str4, sb322.toString());
            sb = new StringBuilder();
        } catch (Exception e2) {
            Log.e(str4, "failed to add video to media store", e2);
            sb = new StringBuilder();
        } catch (Throwable th3) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str2);
            sb5.append(uri);
            Log.d(str4, sb5.toString());
            throw th3;
        }
        sb.append(str2);
        sb.append(uri);
        Log.d(str4, sb.toString());
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
        String str = TAG;
        Log.d(str, "save video: start");
        String str2 = "_data";
        String asString = this.mContentValues.getAsString(str2);
        if (!asString.equals(this.mVideoPath)) {
            if (new File(this.mVideoPath).renameTo(new File(asString))) {
                this.mVideoPath = asString;
            } else {
                this.mContentValues.put(str2, this.mVideoPath);
            }
        }
        boolean needThumbnail = this.saverCallback.needThumbnail(isFinal());
        this.mUri = addVideoToMediaStore(this.mVideoPath, this.mContentValues);
        if (this.mUri == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("insert MediaProvider failed, attempt to find uri by path, ");
            sb.append(this.mVideoPath);
            Log.w(str, sb.toString());
            this.mUri = MediaProviderUtil.getContentUriFromPath(this.context, this.mVideoPath);
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("save video: media has been stored, Uri: ");
        sb2.append(this.mUri);
        sb2.append(", has thumbnail: ");
        sb2.append(needThumbnail);
        Log.d(str, sb2.toString());
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
            String str3 = this.mVideoPath;
            if (this.mContentValues.get("latitude") == null && this.mContentValues.get("longitude") == null) {
                z = true;
            }
            Storage.saveToCloudAlbum(context2, str3, -1, z);
        } else if (needThumbnail) {
            this.saverCallback.postHideThumbnailProgressing();
        }
        Log.d(str, "save video: end");
    }

    public void setContextAndCallback(Context context2, SaverCallback saverCallback2) {
        this.context = context2;
        this.saverCallback = saverCallback2;
    }
}
