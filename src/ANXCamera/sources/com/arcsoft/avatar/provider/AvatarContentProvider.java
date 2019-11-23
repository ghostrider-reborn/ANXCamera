package com.arcsoft.avatar.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.arcsoft.avatar.util.LOG;

public class AvatarContentProvider extends ContentProvider {

    /* renamed from: a  reason: collision with root package name */
    private static final String f26a = AvatarContentProvider.class.getSimpleName();

    /* renamed from: b  reason: collision with root package name */
    private DBHelper f27b;

    public class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, AvatarProfile.DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 2);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(AvatarProfile.SQL_CREATE_TABLE_AVATAR_DB);
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            sQLiteDatabase.execSQL(AvatarProfile.SQL_DROP_TABLE_AVATAR_DB);
            onCreate(sQLiteDatabase);
        }
    }

    /* access modifiers changed from: protected */
    public String a(Uri uri, String str, String str2) {
        String str3;
        String sb;
        String str4 = "" + ContentUris.parseId(uri);
        String str5 = str + " = " + str4;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str5);
        if (TextUtils.isEmpty(str2)) {
            str3 = "";
        } else {
            str3 = "and ( " + str2 + " )";
        }
        sb2.append(str3);
        LOG.d("DELETE", "newSelection : " + sb);
        return sb;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x005f  */
    public int delete(@NonNull Uri uri, @Nullable String str, @Nullable String[] strArr) {
        SQLiteDatabase sQLiteDatabase;
        int i;
        synchronized (this.f27b) {
            try {
                sQLiteDatabase = this.f27b.getWritableDatabase();
                try {
                    sQLiteDatabase.beginTransaction();
                    if (AvatarProfile.sUriMatcher.match(uri) != 1) {
                        i = -1;
                    } else {
                        i = sQLiteDatabase.delete(AvatarProfile.TABLE_NAME, "_id = " + str, strArr);
                    }
                    sQLiteDatabase.setTransactionSuccessful();
                    if (sQLiteDatabase.inTransaction()) {
                        sQLiteDatabase.endTransaction();
                    }
                } catch (Throwable th) {
                    th = th;
                    if (sQLiteDatabase.inTransaction()) {
                        sQLiteDatabase.endTransaction();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                sQLiteDatabase = null;
                if (sQLiteDatabase.inTransaction()) {
                }
                throw th;
            }
        }
        LOG.d(f26a, "DELETE count = " + i);
        return i;
    }

    @Nullable
    public String getType(@NonNull Uri uri) {
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0073 A[Catch:{ all -> 0x0069 }] */
    @Nullable
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Uri uri2;
        SQLiteDatabase sQLiteDatabase;
        synchronized (this.f27b) {
            uri2 = null;
            try {
                sQLiteDatabase = this.f27b.getWritableDatabase();
                try {
                    sQLiteDatabase.beginTransaction();
                    long j = -1;
                    if (AvatarProfile.sUriMatcher.match(uri) == 1) {
                        j = sQLiteDatabase.insert(AvatarProfile.TABLE_NAME, (String) null, contentValues);
                    }
                    sQLiteDatabase.setTransactionSuccessful();
                    if (j < 0) {
                        Log.e(f26a, "insert err:rowId=" + j);
                    } else {
                        uri2 = Uri.parse(uri + "/" + String.valueOf(j));
                    }
                    if (sQLiteDatabase.inTransaction()) {
                        sQLiteDatabase.endTransaction();
                    }
                } catch (Throwable th) {
                    th = th;
                    if (sQLiteDatabase.inTransaction()) {
                        sQLiteDatabase.endTransaction();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                sQLiteDatabase = null;
                if (sQLiteDatabase.inTransaction()) {
                }
                throw th;
            }
        }
        return uri2;
    }

    public boolean onCreate() {
        this.f27b = new DBHelper(getContext());
        return true;
    }

    @Nullable
    public Cursor query(@NonNull Uri uri, @Nullable String[] strArr, @Nullable String str, @Nullable String[] strArr2, @Nullable String str2) {
        Cursor cursor;
        SQLiteDatabase sQLiteDatabase;
        Uri uri2 = uri;
        LOG.d("DELETE", "URI = " + uri2);
        synchronized (this.f27b) {
            cursor = null;
            try {
                sQLiteDatabase = this.f27b.getReadableDatabase();
                try {
                    sQLiteDatabase.beginTransaction();
                    LOG.d("DELETE", "URI = " + uri2);
                    if (AvatarProfile.sUriMatcher.match(uri2) == 1) {
                        cursor = sQLiteDatabase.query(AvatarProfile.TABLE_NAME, strArr, str, strArr2, (String) null, (String) null, str2);
                    }
                    sQLiteDatabase.setTransactionSuccessful();
                    if (cursor == null) {
                        Log.e(f26a, "query err:retCursor==null");
                    } else {
                        cursor.setNotificationUri(getContext().getContentResolver(), uri2);
                    }
                    if (sQLiteDatabase != null) {
                        if (sQLiteDatabase.inTransaction()) {
                            sQLiteDatabase.endTransaction();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                sQLiteDatabase = null;
                sQLiteDatabase.endTransaction();
                throw th;
            }
        }
        return cursor;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0049 A[Catch:{ all -> 0x003f }] */
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String str, @Nullable String[] strArr) {
        SQLiteDatabase sQLiteDatabase;
        int i;
        synchronized (this.f27b) {
            try {
                sQLiteDatabase = this.f27b.getWritableDatabase();
                try {
                    sQLiteDatabase.beginTransaction();
                    if (AvatarProfile.sUriMatcher.match(uri) != 1) {
                        i = -1;
                    } else {
                        i = sQLiteDatabase.update(AvatarProfile.TABLE_NAME, contentValues, "_id = " + str, strArr);
                    }
                    sQLiteDatabase.setTransactionSuccessful();
                    if (sQLiteDatabase.inTransaction()) {
                        sQLiteDatabase.endTransaction();
                    }
                } catch (Throwable th) {
                    th = th;
                    if (sQLiteDatabase.inTransaction()) {
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                sQLiteDatabase = null;
                if (sQLiteDatabase.inTransaction()) {
                    sQLiteDatabase.endTransaction();
                }
                throw th;
            }
        }
        return i;
    }
}
