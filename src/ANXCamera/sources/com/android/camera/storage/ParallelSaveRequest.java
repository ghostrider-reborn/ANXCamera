package com.android.camera.storage;

import android.content.ContentUris;
import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import com.android.camera.Exif;
import com.android.camera.Thumbnail;
import com.android.camera.Util;
import com.android.camera.db.DbRepository;
import com.android.camera.db.element.SaveTask;
import com.android.camera.log.Log;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.PictureInfo;
import com.xiaomi.camera.parallelservice.util.ParallelUtil;

public final class ParallelSaveRequest extends AbstractSaveRequest {
    private static final String TAG = "ParallelSaveRequest";
    private String mAlgorithmName;
    private Context mContext;
    private byte[] mData;
    private PictureInfo mInfo;
    private int mJpegRotation;
    private Location mLocation;
    private boolean mNeedThumbnail;
    private String mSavePath;
    private SaverCallback mSaverCallback;
    private int mSize;
    private long mStartTime;

    public ParallelSaveRequest(ParallelTaskData parallelTaskData, SaverCallback saverCallback) {
        this.mParallelTaskData = parallelTaskData;
        setSaverCallback(saverCallback);
        this.mSize = caculateMemoryUsed(this.mParallelTaskData);
    }

    public ParallelSaveRequest(byte[] bArr, long j, Location location, int i, String str, int i2, int i3, boolean z, String str2, PictureInfo pictureInfo) {
        reFillSaveRequest(bArr, j, location, i, str, i2, i3, z, str2, pictureInfo);
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
    public void reFillSaveRequest(byte[] bArr, long j, Location location, int i, String str, int i2, int i3, boolean z, String str2, PictureInfo pictureInfo) {
        this.mData = bArr;
        this.mStartTime = j;
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

    /* JADX WARNING: Removed duplicated region for block: B:36:0x01c1  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x01ee  */
    public void save() {
        boolean z;
        boolean z2;
        parserParallelTaskData();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("save: ");
        sb.append(this.mSavePath);
        sb.append(" | ");
        sb.append(this.mStartTime);
        Log.d(str, sb.toString());
        synchronized (this.mSavePath.intern()) {
            SaveTask itemByPath = DbRepository.dbItemSaveTask().getItemByPath(this.mSavePath);
            if (itemByPath == null) {
                SaveTask saveTask = (SaveTask) DbRepository.dbItemSaveTask().generateItem(System.currentTimeMillis());
                saveTask.setPath(this.mSavePath);
                saveTask.setStartTime(Long.valueOf(-1));
                DbRepository.dbItemSaveTask().endItemAndInsert(saveTask, 0);
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("insert full size picture:");
                sb2.append(this.mSavePath);
                Log.w(str2, sb2.toString());
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
                    String str3 = TAG;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("algo mark: uri: ");
                    sb3.append(resultUri.toString());
                    sb3.append(" | ");
                    sb3.append(itemByPath.getPath());
                    Log.d(str3, sb3.toString());
                    String str4 = TAG;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("algo mark: ");
                    sb4.append(this.mJpegRotation);
                    sb4.append(" | ");
                    sb4.append(i);
                    sb4.append(" | ");
                    sb4.append(i2);
                    sb4.append(" | ");
                    sb4.append(orientation);
                    Log.d(str4, sb4.toString());
                    Storage.updateImageWithExtraExif(this.mContext, this.mData, null, ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, itemByPath.getMediaStoreId().longValue()), fileTitleFromPath, this.mLocation, orientation, i, i2, null, false, false, this.mAlgorithmName, this.mInfo);
                    ParallelUtil.markTaskFinish(this.mContext, itemByPath, false);
                }
            }
            long currentTimeMillis = System.currentTimeMillis();
            String fileTitleFromPath2 = this.mSavePath != null ? Util.getFileTitleFromPath(this.mSavePath) : Util.createJpegName(currentTimeMillis);
            SaveTask saveTask2 = itemByPath;
            String str5 = fileTitleFromPath2;
            Uri addImage = Storage.addImage(this.mContext, fileTitleFromPath2, currentTimeMillis, this.mLocation, orientation, this.mData, i, i2, false, false, false, false, itemByPath != null, this.mAlgorithmName, this.mInfo);
            if (addImage != null) {
                if (this.mNeedThumbnail) {
                    Thumbnail createThumbnail = Thumbnail.createThumbnail(this.mData, orientation, Integer.highestOneBit((int) Math.ceil(Math.max((double) i, (double) i2) / 512.0d)), addImage, false);
                    if (createThumbnail != null) {
                        z = true;
                        this.mSaverCallback.postUpdateThumbnail(createThumbnail, true);
                        z2 = true;
                        this.mSaverCallback.notifyNewMediaData(addImage, str5, 2);
                        if (saveTask2 == null) {
                            String str6 = TAG;
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append("algo mark: ");
                            sb5.append(addImage.toString());
                            Log.d(str6, sb5.toString());
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
                this.mSaverCallback.notifyNewMediaData(addImage, str5, 2);
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
