package com.android.camera.storage;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.android.camera.log.Log;
import java.io.File;

public class MediaProviderUtil {
    public static final String TAG = "MediaProviderUtil";

    private static /* synthetic */ void $closeResource(Throwable th, AutoCloseable autoCloseable) {
        if (th != null) {
            try {
                autoCloseable.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
        } else {
            autoCloseable.close();
        }
    }

    private MediaProviderUtil() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006b, code lost:
        r9 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x006c, code lost:
        r1 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0070, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0071, code lost:
        r8 = r1;
        r1 = r9;
        r9 = r8;
     */
    public static Uri getContentUriFromPath(Context context, String str) {
        Throwable th;
        Throwable th2;
        try {
            Cursor query = context.getContentResolver().query(MediaStore.Files.getContentUri("external"), new String[]{"_id", "media_type"}, "_data = ?", new String[]{str}, "");
            if (query.moveToNext()) {
                long j = (long) query.getInt(0);
                int i = query.getInt(1);
                if (i == 1) {
                    Uri withAppendedId = ContentUris.withAppendedId(MediaStore.Images.Media.getContentUri("external"), j);
                    if (query != null) {
                        $closeResource((Throwable) null, query);
                    }
                    return withAppendedId;
                } else if (i == 3) {
                    Uri withAppendedId2 = ContentUris.withAppendedId(MediaStore.Video.Media.getContentUri("external"), j);
                    if (query != null) {
                        $closeResource((Throwable) null, query);
                    }
                    return withAppendedId2;
                } else {
                    Uri withAppendedId3 = ContentUris.withAppendedId(MediaStore.Files.getContentUri("external"), j);
                    if (query != null) {
                        $closeResource((Throwable) null, query);
                    }
                    return withAppendedId3;
                }
            } else {
                if (query != null) {
                    $closeResource((Throwable) null, query);
                }
                return null;
            }
            if (query != null) {
                $closeResource(th2, query);
            }
            throw th;
        } catch (Exception e) {
        }
    }

    private static long getParent(Context context, String str) {
        Cursor query;
        Throwable th;
        Uri contentUri = MediaStore.Files.getContentUri("external");
        try {
            query = context.getContentResolver().query(contentUri, new String[]{"_id"}, "_data = ?", new String[]{new File(str).getParent()}, "");
            if (query.moveToNext()) {
                long j = query.getLong(0);
                if (query != null) {
                    $closeResource((Throwable) null, query);
                }
                return j;
            } else if (query == null) {
                return 0;
            } else {
                $closeResource((Throwable) null, query);
                return 0;
            }
        } catch (Exception e) {
            return 0;
        } catch (Throwable th2) {
            if (query != null) {
                $closeResource(th, query);
            }
            throw th2;
        }
    }

    public static Uri insertCameraDirectory(Context context, String str) {
        long parent = getParent(context, str);
        if (parent > 0) {
            return insertDirectory(context, str, parent);
        }
        Uri insertDirectory = insertDirectory(context, new File(str).getParent(), 0);
        if (insertDirectory == null) {
            return null;
        }
        return insertDirectory(context, str, ContentUris.parseId(insertDirectory));
    }

    public static Uri insertDirectory(Context context, String str, long j) {
        Uri contentUri = MediaStore.Files.getContentUri("external");
        ContentValues contentValues = new ContentValues();
        contentValues.put("format", 12289);
        contentValues.put("media_type", 0);
        contentValues.put("_data", str);
        contentValues.put("parent", Long.valueOf(j));
        File file = new File(str);
        if (file.exists()) {
            contentValues.put("date_modified", Long.valueOf(file.lastModified() / 1000));
        }
        try {
            return context.getContentResolver().insert(contentUri, contentValues);
        } catch (Exception e) {
            Log.w(TAG, "insertDirectory fail, path = " + str, e);
            return null;
        }
    }
}
