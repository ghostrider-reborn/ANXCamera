package com.bumptech.glide.load.a;

import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: StreamLocalUriFetcher */
public class o extends m<InputStream> {
    private static final int be = 1;
    private static final int ce = 2;
    private static final int de = 3;
    private static final int ee = 4;
    private static final int fe = 5;
    private static final UriMatcher ge = new UriMatcher(-1);

    static {
        ge.addURI("com.android.contacts", "contacts/lookup/*/#", 1);
        ge.addURI("com.android.contacts", "contacts/lookup/*", 1);
        ge.addURI("com.android.contacts", "contacts/#/photo", 2);
        ge.addURI("com.android.contacts", "contacts/#", 3);
        ge.addURI("com.android.contacts", "contacts/#/display_photo", 4);
        ge.addURI("com.android.contacts", "phone_lookup/*", 5);
    }

    public o(ContentResolver contentResolver, Uri uri) {
        super(contentResolver, uri);
    }

    private InputStream b(Uri uri, ContentResolver contentResolver) throws FileNotFoundException {
        int match = ge.match(uri);
        if (match != 1) {
            if (match == 3) {
                return openContactPhotoInputStream(contentResolver, uri);
            }
            if (match != 5) {
                return contentResolver.openInputStream(uri);
            }
        }
        Uri lookupContact = ContactsContract.Contacts.lookupContact(contentResolver, uri);
        if (lookupContact != null) {
            return openContactPhotoInputStream(contentResolver, lookupContact);
        }
        throw new FileNotFoundException("Contact cannot be found");
    }

    private InputStream openContactPhotoInputStream(ContentResolver contentResolver, Uri uri) {
        return ContactsContract.Contacts.openContactPhotoInputStream(contentResolver, uri, true);
    }

    @NonNull
    public Class<InputStream> M() {
        return InputStream.class;
    }

    /* access modifiers changed from: protected */
    public InputStream a(Uri uri, ContentResolver contentResolver) throws FileNotFoundException {
        InputStream b2 = b(uri, contentResolver);
        if (b2 != null) {
            return b2;
        }
        throw new FileNotFoundException("InputStream is null for " + uri);
    }

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public void e(InputStream inputStream) throws IOException {
        inputStream.close();
    }
}
