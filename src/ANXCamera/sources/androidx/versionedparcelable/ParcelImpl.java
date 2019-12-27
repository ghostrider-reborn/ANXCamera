package androidx.versionedparcelable;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public class ParcelImpl implements Parcelable {
    public static final Parcelable.Creator<ParcelImpl> CREATOR = new d();
    private final i mParcel;

    protected ParcelImpl(Parcel parcel) {
        this.mParcel = new g(parcel).sa();
    }

    public ParcelImpl(i iVar) {
        this.mParcel = iVar;
    }

    public int describeContents() {
        return 0;
    }

    public <T extends i> T ia() {
        return this.mParcel;
    }

    public void writeToParcel(Parcel parcel, int i) {
        new g(parcel).b(this.mParcel);
    }
}
