package com.android.camera.storage;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import com.android.camera.Thumbnail;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.gallery3d.exif.ExifInterface;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.PictureInfo;

public final class ImageSaveRequest extends AbstractSaveRequest {
    private static final String TAG = "ImageSaveRequest";
    private String algorithmName;
    private Context context;
    private byte[] data;
    private long date;
    private ExifInterface exif;
    private boolean finalImage;
    private PictureInfo info;
    private boolean isHide;
    private boolean isMap;
    private boolean isParallelProcess;
    private Location loc;
    private boolean mirror;
    private boolean needThumbnail;
    public String oldTitle;
    private int previewThumbnailHash;
    private SaverCallback saverCallback;
    private int size;
    public String title;
    private Uri uri;

    ImageSaveRequest() {
    }

    ImageSaveRequest(ParallelTaskData parallelTaskData, SaverCallback saverCallback2) {
        this.mParallelTaskData = parallelTaskData;
        setSaverCallback(saverCallback2);
        this.size = caculateMemoryUsed(this.mParallelTaskData);
    }

    ImageSaveRequest(byte[] bArr, boolean z, String str, String str2, long j, Uri uri2, Location location, int i, int i2, ExifInterface exifInterface, int i3, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String str3, PictureInfo pictureInfo, int i4) {
        reFillSaveRequest(bArr, z, str, str2, j, uri2, location, i, i2, exifInterface, i3, z2, z3, z4, z5, z6, str3, pictureInfo, i4);
    }

    public int getSize() {
        return this.size;
    }

    public boolean isFinal() {
        return this.finalImage;
    }

    public void onFinish() {
        this.data = null;
        this.saverCallback.onSaveFinish(getSize());
    }

    /* access modifiers changed from: protected */
    public void reFillSaveRequest(byte[] bArr, boolean z, String str, String str2, long j, Uri uri2, Location location, int i, int i2, ExifInterface exifInterface, int i3, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String str3, PictureInfo pictureInfo, int i4) {
        Location location2 = location;
        this.data = bArr;
        this.needThumbnail = z;
        this.date = j;
        this.uri = uri2;
        this.title = str;
        this.oldTitle = str2;
        this.loc = location2 == null ? null : new Location(location);
        this.width = i;
        this.height = i2;
        this.exif = exifInterface;
        this.orientation = i3;
        this.isHide = z2;
        this.isMap = z3;
        this.finalImage = z4;
        this.mirror = z5;
        this.isParallelProcess = z6;
        this.algorithmName = str3;
        this.info = pictureInfo;
        this.previewThumbnailHash = i4;
    }

    public void run() {
        save();
        onFinish();
    }

    public void save() {
        boolean z;
        parserParallelTaskData();
        Uri uri2 = this.uri;
        if (uri2 != null) {
            z = true;
            Storage.updateImageWithExtraExif(this.context, this.data, this.exif, uri2, this.title, this.loc, this.orientation, this.width, this.height, this.oldTitle, this.mirror, this.isParallelProcess, this.algorithmName, this.info);
        } else {
            z = true;
            if (this.data != null) {
                String str = this.algorithmName;
                this.uri = Storage.addImage(this.context, this.title, this.date, this.loc, this.orientation, this.data, this.width, this.height, false, this.isHide, this.isMap, str != null && str.equals(Util.ALGORITHM_NAME_MIMOJI_CAPTURE), this.isParallelProcess, this.algorithmName, this.info);
            }
        }
        Storage.getAvailableSpace();
        boolean z2 = (!this.needThumbnail || !this.saverCallback.needThumbnail(isFinal())) ? false : z;
        Uri uri3 = this.uri;
        String str2 = TAG;
        if (uri3 != null) {
            if (z2) {
                int highestOneBit = Integer.highestOneBit((int) Math.ceil(Math.max((double) this.width, (double) this.height) / 512.0d));
                Log.d(str2, "image save try to create thumbnail");
                Thumbnail createThumbnailFromUri = this.isMap ? Thumbnail.createThumbnailFromUri(this.context.getContentResolver(), this.uri, this.mirror) : Thumbnail.createThumbnail(this.data, this.orientation, highestOneBit, this.uri, this.mirror);
                if (createThumbnailFromUri != null) {
                    this.saverCallback.postUpdateThumbnail(createThumbnailFromUri, z);
                } else {
                    this.saverCallback.postHideThumbnailProgressing();
                }
            } else {
                this.saverCallback.updatePreviewThumbnailUri(this.previewThumbnailHash, uri3);
            }
            this.saverCallback.notifyNewMediaData(this.uri, this.title, 2);
            Log.d(str2, "image save finished");
            return;
        }
        Log.e(str2, "image save failed");
        if (z2) {
            this.saverCallback.postHideThumbnailProgressing();
            return;
        }
        Log.e(str2, "set mWaitingForUri is false");
        this.saverCallback.updatePreviewThumbnailUri(this.previewThumbnailHash, null);
    }

    public void setContextAndCallback(Context context2, SaverCallback saverCallback2) {
        this.context = context2;
        this.saverCallback = saverCallback2;
    }
}
