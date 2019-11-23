package com.android.camera.storage;

import android.content.ContentUris;
import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import com.android.camera.Exif;
import com.android.camera.Thumbnail;
import com.android.camera.Util;
import com.android.camera.db.DbRepository;
import com.android.camera.db.element.SaveTask;
import com.android.camera.log.Log;
import com.android.gallery3d.exif.ExifInterface;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.PictureInfo;
import com.xiaomi.camera.parallelservice.util.ParallelUtil;

public final class ParallelSaveRequest extends AbstractSaveRequest {
    private static final String TAG = ParallelSaveRequest.class.getSimpleName();
    private String mAlgorithmName;
    private Context mContext;
    private byte[] mData;
    private PictureInfo mInfo;
    private int mJpegRotation;
    private Location mLocation;
    private boolean mNeedThumbnail;
    private String mSavePath;
    private SaverCallback mSaverCallback;
    private int mSize = calculateMemoryUsed(this.mParallelTaskData);
    private long mTimestamp;

    public ParallelSaveRequest(ParallelTaskData parallelTaskData, SaverCallback saverCallback) {
        this.mParallelTaskData = parallelTaskData;
        setSaverCallback(saverCallback);
    }

    public int getSize() {
        return this.mSize;
    }

    public boolean isFinal() {
        return true;
    }

    public void onFinish() {
        PerformanceTracker.trackImageSaver(this.mData, 1);
        this.mData = null;
        this.mSaverCallback.onSaveFinish(this.mSize);
    }

    /* access modifiers changed from: protected */
    public void reFillSaveRequest(byte[] bArr, long j, long j2, Location location, int i, String str, int i2, int i3, boolean z, String str2, PictureInfo pictureInfo) {
        this.mData = bArr;
        this.mTimestamp = j;
        this.date = j2;
        this.mLocation = location == null ? null : new Location(location);
        this.mJpegRotation = i;
        this.mSavePath = str;
        this.width = i2;
        this.height = i3;
        this.mNeedThumbnail = z;
        this.mAlgorithmName = str2;
        this.mInfo = pictureInfo;
    }

    public void run() {
        save();
        onFinish();
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x01c6  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x01f3  */
    public void save() {
        boolean z;
        boolean z2;
        parserParallelTaskData();
        Log.d(TAG, "save: " + this.mSavePath + " | " + this.mTimestamp);
        synchronized (this.mSavePath.intern()) {
            SaveTask itemByPath = DbRepository.dbItemSaveTask().getItemByPath(this.mSavePath);
            if (itemByPath == null) {
                SaveTask saveTask = (SaveTask) DbRepository.dbItemSaveTask().generateItem(System.currentTimeMillis());
                saveTask.setPath(this.mSavePath);
                saveTask.setStartTime(-1L);
                DbRepository.dbItemSaveTask().endItemAndInsert(saveTask, 0);
                Log.w(TAG, "insert full size picture:" + this.mSavePath);
            }
            int i = this.width;
            int i2 = this.height;
            int orientation = Exif.getOrientation(this.mData);
            if ((this.mJpegRotation + orientation) % 180 != 0) {
                int i3 = i2;
                i2 = i;
                i = i3;
            }
            if (itemByPath != null) {
                if (itemByPath.isValid()) {
                    String fileTitleFromPath = Util.getFileTitleFromPath(this.mSavePath);
                    Uri resultUri = ParallelUtil.getResultUri(itemByPath.getMediaStoreId().longValue());
                    Log.d(TAG, "algo mark: uri: " + resultUri.toString() + " | " + itemByPath.getPath());
                    Log.d(TAG, "algo mark: " + this.mJpegRotation + " | " + i + " | " + i2 + " | " + orientation);
                    Storage.updateImageWithExtraExif(this.mContext, this.mData, (ExifInterface) null, ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, itemByPath.getMediaStoreId().longValue()), fileTitleFromPath, this.mLocation, orientation, i, i2, (String) null, false, false, this.mAlgorithmName, this.mInfo);
                    ParallelUtil.markTaskFinish(this.mContext, itemByPath, false);
                }
            }
            String fileTitleFromPath2 = this.mSavePath != null ? Util.getFileTitleFromPath(this.mSavePath) : Util.createJpegName(this.date);
            SaveTask saveTask2 = itemByPath;
            String str = fileTitleFromPath2;
            Uri addImage = Storage.addImage(this.mContext, fileTitleFromPath2, this.date, this.mLocation, orientation, this.mData, i, i2, false, false, false, false, itemByPath != null, this.mAlgorithmName, this.mInfo);
            if (addImage != null) {
                if (this.mNeedThumbnail) {
                    Thumbnail createThumbnail = Thumbnail.createThumbnail(this.mData, orientation, Integer.highestOneBit((int) Math.ceil(Math.max((double) i, (double) i2) / 512.0d)), addImage, false);
                    if (createThumbnail != null) {
                        z = true;
                        this.mSaverCallback.postUpdateThumbnail(createThumbnail, true);
                        z2 = true;
                        this.mSaverCallback.notifyNewMediaData(addImage, str, 2);
                        if (saveTask2 == null) {
                            Log.d(TAG, "algo mark: " + addImage.toString());
                            SaveTask saveTask3 = saveTask2;
                            saveTask3.setMediaStoreId(Long.valueOf(ContentUris.parseId(addImage)));
                            ParallelUtil.markTaskFinish(this.mContext, saveTask3, false);
                        } else {
                            if (!z2) {
                                Thumbnail createThumbnail2 = Thumbnail.createThumbnail(this.mData, orientation, Integer.highestOneBit((int) Math.ceil(Math.max((double) i, (double) i2) / 512.0d)), addImage, false);
                                if (createThumbnail2 != null) {
                                    this.mSaverCallback.postUpdateThumbnail(createThumbnail2, z);
                                }
                            }
                            ParallelUtil.insertImageToParallelService(this.mContext, ContentUris.parseId(addImage), this.mSavePath);
                        }
                    } else {
                        z = true;
                        this.mSaverCallback.postHideThumbnailProgressing();
                    }
                } else {
                    z = true;
                }
                z2 = false;
                this.mSaverCallback.notifyNewMediaData(addImage, str, 2);
                if (saveTask2 == null) {
                }
            }
        }
    }

    public void setContextAndCallback(Context context, SaverCallback saverCallback) {
        this.mContext = context;
        this.mSaverCallback = saverCallback;
    }
}
