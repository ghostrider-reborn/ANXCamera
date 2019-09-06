package com.arcsoft.camera.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.MediaStore.Files;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video;
import android.provider.MediaStore.Video.Thumbnails;
import com.android.camera.storage.Storage;
import com.ss.android.ugc.effectmanager.common.EffectConstants;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: MediaManager */
public class h {
    private static final String[] D = {"max(_id) as newId", "_data", "_size", "datetaken", a.f192e, "bucket_id", "mime_type", "date_modified", "media_type", "resolution", "tags", "width", "height", "orientation", "duration"};

    /* renamed from: a reason: collision with root package name */
    public static final String f182a = e(t);

    /* renamed from: b reason: collision with root package name */
    public static final Uri f183b = Files.getContentUri("external");

    /* renamed from: c reason: collision with root package name */
    public static final int f184c = 1;

    /* renamed from: d reason: collision with root package name */
    public static final int f185d = 3;

    /* renamed from: e reason: collision with root package name */
    public static final int f186e = 0;

    /* renamed from: f reason: collision with root package name */
    public static final int f187f = 1;
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
    private static String t;
    private static h u = null;
    private final String A = "video/mp4";
    private final String[] B = {"_id", "bucket_id", "bucket_display_name", "_data", "_display_name", "width", "height", "_size", "mime_type", "datetaken", "date_modified", "date_added", "latitude", "longitude", "duration", "resolution"};
    private final String[] C = {"_id", "bucket_id", "bucket_display_name", "_data", "_display_name", "width", "height", "_size", "mime_type", "datetaken", "date_modified", "date_added", "latitude", "longitude", "orientation"};
    private Context v = null;
    private ContentResolver w = null;
    private final String x = "image/jpeg";
    private final String y = "image/gif";
    private final String z = "video/3gpp";

    /* compiled from: MediaManager */
    private static final class a {

        /* renamed from: a reason: collision with root package name */
        public static final String f188a = "_id";

        /* renamed from: b reason: collision with root package name */
        public static final String f189b = "_data";

        /* renamed from: c reason: collision with root package name */
        public static final String f190c = "_size";

        /* renamed from: d reason: collision with root package name */
        public static final String f191d = "datetaken";

        /* renamed from: e reason: collision with root package name */
        public static final String f192e;

        /* renamed from: f reason: collision with root package name */
        public static final String f193f = "bucket_id";
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

        static {
            StringBuilder sb = new StringBuilder();
            sb.append("case media_type when 1 then \"");
            sb.append(Media.EXTERNAL_CONTENT_URI);
            sb.append("\" else \"");
            sb.append(Video.Media.EXTERNAL_CONTENT_URI);
            sb.append("\" end");
            f192e = sb.toString();
        }

        private a() {
        }
    }

    /* compiled from: MediaManager */
    public static class b {
        /* access modifiers changed from: private */

        /* renamed from: a reason: collision with root package name */
        public boolean f194a;
        /* access modifiers changed from: private */

        /* renamed from: b reason: collision with root package name */
        public Uri f195b;
        /* access modifiers changed from: private */

        /* renamed from: c reason: collision with root package name */
        public long f196c;
        /* access modifiers changed from: private */

        /* renamed from: d reason: collision with root package name */
        public long f197d;
        /* access modifiers changed from: private */

        /* renamed from: e reason: collision with root package name */
        public String f198e;
        /* access modifiers changed from: private */

        /* renamed from: f reason: collision with root package name */
        public String f199f;
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

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().toString());
        sb.append("/DCIM/WideSelfie/");
        t = sb.toString();
    }

    private h(Context context) {
        this.v = context;
        this.w = this.v.getContentResolver();
    }

    public static Cursor a(ContentResolver contentResolver) {
        return contentResolver.query(f183b, D, "(media_type=? or media_type=?) and bucket_id=? ", new String[]{String.valueOf(1), String.valueOf(3), f182a}, "_id DESC");
    }

    private static Uri a(Cursor cursor) {
        return ContentUris.withAppendedId(Uri.parse(cursor.getString(4)), cursor.getLong(0));
    }

    private b a(Cursor cursor, boolean z2) {
        Cursor cursor2 = cursor;
        boolean z3 = z2;
        if (cursor2 == null || cursor.getCount() <= 0) {
            return null;
        }
        b bVar = new b();
        bVar.f194a = z3;
        String str = "date_added";
        String str2 = "date_modified";
        String str3 = "datetaken";
        String str4 = "mime_type";
        String str5 = "_size";
        String str6 = "height";
        String str7 = "width";
        String str8 = "_display_name";
        String str9 = "_data";
        String str10 = "bucket_display_name";
        String str11 = "longitude";
        String str12 = "bucket_id";
        String str13 = "latitude";
        String str14 = "_id";
        if (z3) {
            String str15 = str;
            bVar.f196c = cursor2.getLong(e.b(this.B, str14));
            bVar.f195b = ContentUris.withAppendedId(Video.Media.EXTERNAL_CONTENT_URI, bVar.f196c);
            bVar.f197d = (long) cursor2.getInt(e.b(this.B, str12));
            bVar.f198e = cursor2.getString(e.b(this.B, str10));
            bVar.f199f = cursor2.getString(e.b(this.B, str9));
            bVar.g = cursor2.getString(e.b(this.B, str8));
            bVar.h = cursor2.getInt(e.b(this.B, str7));
            bVar.i = cursor2.getInt(e.b(this.B, str6));
            bVar.j = cursor2.getLong(e.b(this.B, str5));
            bVar.k = cursor2.getString(e.b(this.B, str4));
            bVar.l = cursor2.getString(e.b(this.B, str3));
            bVar.m = cursor2.getString(e.b(this.B, str2));
            bVar.n = cursor2.getString(e.b(this.B, str15));
            bVar.o = cursor2.getDouble(e.b(this.B, str13));
            bVar.p = cursor2.getDouble(e.b(this.B, str11));
            bVar.r = cursor2.getLong(e.b(this.B, "duration"));
            bVar.s = cursor2.getString(e.b(this.B, "resolution"));
        } else {
            String str16 = str;
            bVar.f196c = cursor2.getLong(e.b(this.C, str14));
            bVar.f195b = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, bVar.f196c);
            bVar.f197d = (long) cursor2.getInt(e.b(this.C, str12));
            bVar.f198e = cursor2.getString(e.b(this.C, str10));
            bVar.f199f = cursor2.getString(e.b(this.C, str9));
            bVar.g = cursor2.getString(e.b(this.C, str8));
            bVar.h = cursor2.getInt(e.b(this.C, str7));
            bVar.i = cursor2.getInt(e.b(this.C, str6));
            bVar.j = cursor2.getLong(e.b(this.C, str5));
            bVar.k = cursor2.getString(e.b(this.C, str4));
            bVar.l = cursor2.getString(e.b(this.C, str3));
            bVar.m = cursor2.getString(e.b(this.C, str2));
            bVar.n = cursor2.getString(e.b(this.C, str16));
            bVar.o = cursor2.getDouble(e.b(this.C, str13));
            bVar.p = cursor2.getDouble(e.b(this.C, str11));
            bVar.q = cursor2.getInt(e.b(this.C, "orientation"));
        }
        return bVar;
    }

    public static h a(Context context) {
        if (u == null) {
            synchronized (h.class) {
                if (u == null) {
                    u = new h(context);
                }
            }
        }
        return u;
    }

    private String d(String str) {
        String substring = str.substring(str.lastIndexOf(46));
        return (Storage.JPEG_SUFFIX.equalsIgnoreCase(substring) || ".jpeg".equalsIgnoreCase(substring)) ? "image/jpeg" : EffectConstants.GIF_FILE_SUFFIX.equalsIgnoreCase(substring) ? "image/gif" : (".3gp".equalsIgnoreCase(substring) || ".3gpp".equalsIgnoreCase(substring)) ? "video/3gpp" : ".mp4".equalsIgnoreCase(substring) ? "video/mp4" : "";
    }

    private static String e(String str) {
        return String.valueOf(str.substring(0, str.lastIndexOf("/")).toLowerCase().hashCode());
    }

    public Bitmap a(String str, float f2) {
        if (str == null) {
            str = a();
        }
        if (str == null) {
            return null;
        }
        Options options = new Options();
        options.inJustDecodeBounds = false;
        int i2 = (int) (((float) options.outHeight) / f2);
        if (i2 <= 0) {
            i2 = 1;
        }
        options.inSampleSize = i2;
        return BitmapFactory.decodeFile(str, options);
    }

    public Bitmap a(String str, Options options) {
        b a2 = a(str);
        if (a2 == null) {
            int i2 = 20;
            while (i2 > 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                b a3 = a(str);
                if (a3 != null) {
                    a2 = a3;
                } else {
                    i2--;
                }
            }
            return null;
        }
        return a2.f194a ? Thumbnails.getThumbnail(this.w, a2.f196c, 3, options) : Images.Thumbnails.getThumbnail(this.w, a2.f196c, 3, options);
    }

    public Uri a(String str, int i2, int i3) {
        return a(str, i2, i3, null, 0);
    }

    public Uri a(String str, int i2, int i3, int i4) {
        return a(str, i2, i3, null, i4);
    }

    public Uri a(String str, int i2, int i3, Location location) {
        return a(str, i2, i3, location, 0);
    }

    public Uri a(String str, int i2, int i3, Location location, int i4) {
        a.isVideoFile(str);
        String d2 = d(str);
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
        contentValues.put("mime_type", d2);
        contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        if (location != null) {
            contentValues.put("latitude", Double.valueOf(location.getLatitude()));
            contentValues.put("longitude", Double.valueOf(location.getLongitude()));
        }
        contentValues.put("orientation", Integer.valueOf(i4));
        Uri insert = this.w.insert(Media.EXTERNAL_CONTENT_URI, contentValues);
        if (VERSION.SDK_INT >= 14) {
            this.v.sendBroadcast(new Intent("android.hardware.action.NEW_PICTURE", insert));
        }
        return insert;
    }

    public b a(String str) {
        Cursor cursor;
        b bVar = null;
        if (str == null) {
            return null;
        }
        boolean isVideoFile = a.isVideoFile(str);
        if (isVideoFile) {
            cursor = this.w.query(Video.Media.EXTERNAL_CONTENT_URI, this.B, "_data=?", new String[]{str}, "_id DESC");
        } else {
            cursor = this.w.query(Media.EXTERNAL_CONTENT_URI, this.C, "_data=?", new String[]{str}, "_id DESC");
        }
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            bVar = a(cursor, isVideoFile);
        }
        if (cursor != null) {
            cursor.close();
        }
        return bVar;
    }

    public String a() {
        String str;
        Cursor a2 = a(this.w);
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

    public List<b> a(String str, boolean z2) {
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
        if (z2) {
            cursor = this.w.query(Video.Media.EXTERNAL_CONTENT_URI, this.B, "bucket_display_name=?", new String[]{str}, "_id ASC");
        } else {
            cursor = this.w.query(Media.EXTERNAL_CONTENT_URI, this.C, "bucket_display_name=?", new String[]{str}, "_id ASC");
        }
        ArrayList arrayList = null;
        if (cursor != null && cursor.getCount() > 0) {
            arrayList = new ArrayList();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                arrayList.add(a(cursor, z2));
                cursor.moveToNext();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return arrayList;
    }

    public boolean a(String str, String str2) {
        String str3;
        b a2 = a(str);
        if (a2 == null) {
            return false;
        }
        int lastIndexOf = str2.lastIndexOf(File.separator);
        if (-1 != lastIndexOf) {
            str2 = str2.substring(lastIndexOf + 1);
        }
        String str4 = ".";
        int lastIndexOf2 = str2.lastIndexOf(str4);
        if (-1 != lastIndexOf2) {
            String str5 = str2;
            str2 = str2.substring(0, lastIndexOf2);
            str3 = str5;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(str4);
            sb.append(a.getExtension(str));
            str3 = sb.toString();
        }
        ContentValues contentValues = new ContentValues();
        String str6 = "title";
        String str7 = "_display_name";
        if (a2.f194a) {
            contentValues.put(str7, str3);
            contentValues.put(str6, str2);
        } else {
            contentValues.put(str7, str3);
            contentValues.put(str6, str2);
        }
        return this.w.update(a2.f195b, contentValues, null, null) > 0;
    }

    public Uri b() {
        Uri uri;
        Cursor a2 = a(this.w);
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

    public boolean b(String str) {
        b a2 = a(str);
        return a2 != null && this.w.delete(a2.f195b, null, null) > 0;
    }

    public int c(String str) {
        int i2 = 0;
        try {
            int attributeInt = new ExifInterface(str).getAttributeInt("Orientation", 1);
            if (attributeInt == 3) {
                i2 = 180;
            } else if (attributeInt == 6) {
                i2 = 90;
            } else if (attributeInt == 8) {
                i2 = 270;
            }
            return i2;
        } catch (IOException e2) {
            e2.printStackTrace();
            return 0;
        }
    }
}
