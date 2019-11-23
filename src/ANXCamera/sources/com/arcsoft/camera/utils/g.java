package com.arcsoft.camera.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import com.android.camera.storage.Storage;
import com.ss.android.ugc.effectmanager.common.EffectConstants;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: MediaManager */
public class g {

    /* renamed from: a  reason: collision with root package name */
    public static final String f73a = e(t);
    private static g aY = null;

    /* renamed from: b  reason: collision with root package name */
    public static final Uri f74b = MediaStore.Files.getContentUri("external");
    private static final String[] bh = {"max(_id) as newId", "_data", "_size", "datetaken", a.e, "bucket_id", "mime_type", "date_modified", "media_type", "resolution", "tags", "width", "height", "orientation", "duration"};
    public static final int c = 1;
    public static final int d = 3;
    public static final int e = 0;
    public static final int f = 1;
    public static final int g = 2;
    public static final int h = 3;
    public static final int i = 4;
    public static final int j = 5;
    public static final int k = 6;
    public static final int l = 7;
    public static final int m = 8;
    public static final int n = 9;
    public static final int o = 10;
    public static final int p = 11;
    public static final int q = 12;
    public static final int r = 13;
    public static final int s = 14;
    private static String t = (Environment.getExternalStorageDirectory().toString() + "/DCIM/WideSelfie/");
    private Context aZ = null;
    private ContentResolver ba = null;
    private final String bb = "image/jpeg";
    private final String bc = "image/gif";
    private final String bd = "video/3gpp";
    private final String be = "video/mp4";
    private final String[] bf = {"_id", "bucket_id", "bucket_display_name", "_data", "_display_name", "width", "height", "_size", "mime_type", "datetaken", "date_modified", "date_added", "latitude", "longitude", "duration", "resolution"};
    private final String[] bg = {"_id", "bucket_id", "bucket_display_name", "_data", "_display_name", "width", "height", "_size", "mime_type", "datetaken", "date_modified", "date_added", "latitude", "longitude", "orientation"};

    /* compiled from: MediaManager */
    private static final class a {

        /* renamed from: a  reason: collision with root package name */
        public static final String f75a = "_id";

        /* renamed from: b  reason: collision with root package name */
        public static final String f76b = "_data";
        public static final String c = "_size";
        public static final String d = "datetaken";
        public static final String e = ("case media_type when 1 then \"" + MediaStore.Images.Media.EXTERNAL_CONTENT_URI + "\" else \"" + MediaStore.Video.Media.EXTERNAL_CONTENT_URI + "\" end");
        public static final String f = "bucket_id";
        public static final String g = "mime_type";
        public static final String h = "date_modified";
        public static final String i = "latitude";
        public static final String j = "longitude";
        public static final String k = "orientation";
        public static final String l = "media_type";
        public static final String m = "duration";
        public static final String n = "resolution";
        public static final String o = "tags";
        public static final String p = "width";
        public static final String q = "height";
        public static final String r = "title";
        public static final String s = "_display_name";

        private a() {
        }
    }

    /* compiled from: MediaManager */
    public static class b {
        /* access modifiers changed from: private */

        /* renamed from: a  reason: collision with root package name */
        public boolean f77a;
        /* access modifiers changed from: private */

        /* renamed from: b  reason: collision with root package name */
        public Uri f78b;
        /* access modifiers changed from: private */
        public long c;
        /* access modifiers changed from: private */
        public long d;
        /* access modifiers changed from: private */
        public String e;
        /* access modifiers changed from: private */
        public String f;
        /* access modifiers changed from: private */
        public String g;
        /* access modifiers changed from: private */
        public int h;
        /* access modifiers changed from: private */
        public int i;
        /* access modifiers changed from: private */
        public long j;
        /* access modifiers changed from: private */
        public String k;
        /* access modifiers changed from: private */
        public String l;
        /* access modifiers changed from: private */
        public String m;
        /* access modifiers changed from: private */
        public String n;
        /* access modifiers changed from: private */
        public double o;
        /* access modifiers changed from: private */
        public double p;
        /* access modifiers changed from: private */
        public int q;
        /* access modifiers changed from: private */
        public long r;
        /* access modifiers changed from: private */
        public String s;
    }

    private g(Context context) {
        this.aZ = context;
        this.ba = this.aZ.getContentResolver();
    }

    public static Cursor a(ContentResolver contentResolver) {
        return contentResolver.query(f74b, bh, "(media_type=? or media_type=?) and bucket_id=? ", new String[]{String.valueOf(1), String.valueOf(3), f73a}, "_id DESC");
    }

    private static Uri a(Cursor cursor) {
        return ContentUris.withAppendedId(Uri.parse(cursor.getString(4)), cursor.getLong(0));
    }

    public static g a(Context context) {
        if (aY == null) {
            synchronized (g.class) {
                if (aY == null) {
                    aY = new g(context);
                }
            }
        }
        return aY;
    }

    private b b(Cursor cursor, boolean z) {
        if (cursor == null || cursor.getCount() <= 0) {
            return null;
        }
        b bVar = new b();
        boolean unused = bVar.f77a = z;
        if (z) {
            long unused2 = bVar.c = cursor.getLong(e.b((T[]) this.bf, "_id"));
            Uri unused3 = bVar.f78b = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, bVar.c);
            long unused4 = bVar.d = (long) cursor.getInt(e.b((T[]) this.bf, "bucket_id"));
            String unused5 = bVar.e = cursor.getString(e.b((T[]) this.bf, "bucket_display_name"));
            String unused6 = bVar.f = cursor.getString(e.b((T[]) this.bf, "_data"));
            String unused7 = bVar.g = cursor.getString(e.b((T[]) this.bf, "_display_name"));
            int unused8 = bVar.h = cursor.getInt(e.b((T[]) this.bf, "width"));
            int unused9 = bVar.i = cursor.getInt(e.b((T[]) this.bf, "height"));
            long unused10 = bVar.j = cursor.getLong(e.b((T[]) this.bf, "_size"));
            String unused11 = bVar.k = cursor.getString(e.b((T[]) this.bf, "mime_type"));
            String unused12 = bVar.l = cursor.getString(e.b((T[]) this.bf, "datetaken"));
            String unused13 = bVar.m = cursor.getString(e.b((T[]) this.bf, "date_modified"));
            String unused14 = bVar.n = cursor.getString(e.b((T[]) this.bf, "date_added"));
            double unused15 = bVar.o = cursor.getDouble(e.b((T[]) this.bf, "latitude"));
            double unused16 = bVar.p = cursor.getDouble(e.b((T[]) this.bf, "longitude"));
            long unused17 = bVar.r = cursor.getLong(e.b((T[]) this.bf, "duration"));
            String unused18 = bVar.s = cursor.getString(e.b((T[]) this.bf, "resolution"));
        } else {
            long unused19 = bVar.c = cursor.getLong(e.b((T[]) this.bg, "_id"));
            Uri unused20 = bVar.f78b = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, bVar.c);
            long unused21 = bVar.d = (long) cursor.getInt(e.b((T[]) this.bg, "bucket_id"));
            String unused22 = bVar.e = cursor.getString(e.b((T[]) this.bg, "bucket_display_name"));
            String unused23 = bVar.f = cursor.getString(e.b((T[]) this.bg, "_data"));
            String unused24 = bVar.g = cursor.getString(e.b((T[]) this.bg, "_display_name"));
            int unused25 = bVar.h = cursor.getInt(e.b((T[]) this.bg, "width"));
            int unused26 = bVar.i = cursor.getInt(e.b((T[]) this.bg, "height"));
            long unused27 = bVar.j = cursor.getLong(e.b((T[]) this.bg, "_size"));
            String unused28 = bVar.k = cursor.getString(e.b((T[]) this.bg, "mime_type"));
            String unused29 = bVar.l = cursor.getString(e.b((T[]) this.bg, "datetaken"));
            String unused30 = bVar.m = cursor.getString(e.b((T[]) this.bg, "date_modified"));
            String unused31 = bVar.n = cursor.getString(e.b((T[]) this.bg, "date_added"));
            double unused32 = bVar.o = cursor.getDouble(e.b((T[]) this.bg, "latitude"));
            double unused33 = bVar.p = cursor.getDouble(e.b((T[]) this.bg, "longitude"));
            int unused34 = bVar.q = cursor.getInt(e.b((T[]) this.bg, "orientation"));
        }
        return bVar;
    }

    private static String e(String str) {
        return String.valueOf(str.substring(0, str.lastIndexOf("/")).toLowerCase().hashCode());
    }

    private String j(String str) {
        String substring = str.substring(str.lastIndexOf(46));
        return (Storage.JPEG_SUFFIX.equalsIgnoreCase(substring) || ".jpeg".equalsIgnoreCase(substring)) ? "image/jpeg" : EffectConstants.GIF_FILE_SUFFIX.equalsIgnoreCase(substring) ? "image/gif" : (".3gp".equalsIgnoreCase(substring) || ".3gpp".equalsIgnoreCase(substring)) ? "video/3gpp" : ".mp4".equalsIgnoreCase(substring) ? "video/mp4" : "";
    }

    public Uri G() {
        Uri uri;
        Cursor a2 = a(this.ba);
        if (a2 == null || a2.getCount() <= 0) {
            uri = null;
        } else {
            a2.moveToFirst();
            uri = a(a2);
        }
        if (a2 != null) {
            a2.close();
        }
        return uri;
    }

    public Bitmap a(String str, float f2) {
        if (str == null) {
            str = a();
        }
        if (str == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        int i2 = (int) (((float) options.outHeight) / f2);
        if (i2 <= 0) {
            i2 = 1;
        }
        options.inSampleSize = i2;
        return BitmapFactory.decodeFile(str, options);
    }

    public Bitmap a(String str, BitmapFactory.Options options) {
        b k2 = k(str);
        if (k2 == null) {
            int i2 = 20;
            while (i2 > 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                b k3 = k(str);
                if (k3 != null) {
                    k2 = k3;
                } else {
                    i2--;
                }
            }
            return null;
        }
        return k2.f77a ? MediaStore.Video.Thumbnails.getThumbnail(this.ba, k2.c, 3, options) : MediaStore.Images.Thumbnails.getThumbnail(this.ba, k2.c, 3, options);
    }

    public Uri a(String str, int i2, int i3) {
        return a(str, i2, i3, (Location) null, 0);
    }

    public Uri a(String str, int i2, int i3, int i4) {
        return a(str, i2, i3, (Location) null, i4);
    }

    public Uri a(String str, int i2, int i3, Location location) {
        return a(str, i2, i3, location, 0);
    }

    public Uri a(String str, int i2, int i3, Location location, int i4) {
        a.isVideoFile(str);
        String j2 = j(str);
        File file = new File(str);
        String name = file.getName();
        String substring = name.substring(0, name.lastIndexOf("."));
        ContentValues contentValues = new ContentValues();
        contentValues.put("_data", file.getPath());
        contentValues.put("_display_name", name);
        contentValues.put("title", substring);
        contentValues.put("width", Integer.valueOf(i2));
        contentValues.put("height", Integer.valueOf(i3));
        contentValues.put("_size", Long.valueOf(file.length()));
        contentValues.put("mime_type", j2);
        contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        if (location != null) {
            contentValues.put("latitude", Double.valueOf(location.getLatitude()));
            contentValues.put("longitude", Double.valueOf(location.getLongitude()));
        }
        contentValues.put("orientation", Integer.valueOf(i4));
        Uri insert = this.ba.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        if (Build.VERSION.SDK_INT >= 14) {
            this.aZ.sendBroadcast(new Intent("android.hardware.action.NEW_PICTURE", insert));
        }
        return insert;
    }

    public String a() {
        String str;
        Cursor a2 = a(this.ba);
        if (a2 == null || a2.getCount() <= 0) {
            str = null;
        } else {
            a2.moveToFirst();
            str = a2.getString(1);
        }
        if (a2 != null) {
            a2.close();
        }
        return str;
    }

    public List<b> a(String str, boolean z) {
        Cursor cursor;
        if (str == null) {
            return Collections.emptyList();
        }
        if (str.endsWith(File.separator)) {
            str = str.substring(0, str.length() - 1);
        }
        int lastIndexOf = str.lastIndexOf(File.separator);
        if (-1 != lastIndexOf) {
            str = str.substring(lastIndexOf + 1);
        }
        if (z) {
            cursor = this.ba.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, this.bf, "bucket_display_name=?", new String[]{str}, "_id ASC");
        } else {
            cursor = this.ba.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, this.bg, "bucket_display_name=?", new String[]{str}, "_id ASC");
        }
        ArrayList arrayList = null;
        if (cursor != null && cursor.getCount() > 0) {
            arrayList = new ArrayList();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                arrayList.add(b(cursor, z));
                cursor.moveToNext();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return arrayList;
    }

    public boolean b(String str) {
        b k2 = k(str);
        return k2 != null && this.ba.delete(k2.f78b, (String) null, (String[]) null) > 0;
    }

    public boolean j(String str, String str2) {
        String str3;
        b k2 = k(str);
        if (k2 == null) {
            return false;
        }
        int lastIndexOf = str2.lastIndexOf(File.separator);
        if (-1 != lastIndexOf) {
            str2 = str2.substring(lastIndexOf + 1);
        }
        int lastIndexOf2 = str2.lastIndexOf(".");
        if (-1 != lastIndexOf2) {
            String str4 = str2;
            str2 = str2.substring(0, lastIndexOf2);
            str3 = str4;
        } else {
            str3 = str2 + "." + a.getExtension(str);
        }
        ContentValues contentValues = new ContentValues();
        if (k2.f77a) {
            contentValues.put("_display_name", str3);
            contentValues.put("title", str2);
        } else {
            contentValues.put("_display_name", str3);
            contentValues.put("title", str2);
        }
        return this.ba.update(k2.f78b, contentValues, (String) null, (String[]) null) > 0;
    }

    public b k(String str) {
        Cursor cursor;
        b bVar = null;
        if (str == null) {
            return null;
        }
        boolean isVideoFile = a.isVideoFile(str);
        if (isVideoFile) {
            cursor = this.ba.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, this.bf, "_data=?", new String[]{str}, "_id DESC");
        } else {
            cursor = this.ba.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, this.bg, "_data=?", new String[]{str}, "_id DESC");
        }
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            bVar = b(cursor, isVideoFile);
        }
        if (cursor != null) {
            cursor.close();
        }
        return bVar;
    }

    public int l(String str) {
        try {
            int attributeInt = new ExifInterface(str).getAttributeInt("Orientation", 1);
            if (attributeInt == 3) {
                return 180;
            }
            if (attributeInt != 6) {
                return attributeInt != 8 ? 0 : 270;
            }
            return 90;
        } catch (IOException e2) {
            e2.printStackTrace();
            return 0;
        }
    }
}
