package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.internal.base.zap;
import java.util.concurrent.atomic.AtomicReference;

public abstract class zal extends LifecycleCallback implements DialogInterface.OnCancelListener {
    protected volatile boolean mStarted;
    protected final GoogleApiAvailability zacd;
    protected final AtomicReference<zam> zadf;
    private final Handler zadg;

    protected zal(LifecycleFragment lifecycleFragment) {
        this(lifecycleFragment, GoogleApiAvailability.getInstance());
    }

    private zal(LifecycleFragment lifecycleFragment, GoogleApiAvailability googleApiAvailability) {
        super(lifecycleFragment);
        this.zadf = new AtomicReference<>((Object) null);
        this.zadg = new zap(Looper.getMainLooper());
        this.zacd = googleApiAvailability;
    }

    private static int zaa(zam zam) {
        if (zam == null) {
            return -1;
        }
        return zam.zar();
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0061  */
    public void onActivityResult(int i, int i2, Intent intent) {
        zam zam = this.zadf.get();
        boolean z = true;
        if (i == 1) {
            if (i2 != -1) {
                if (i2 == 0) {
                    int i3 = 13;
                    if (intent != null) {
                        i3 = intent.getIntExtra("<<ResolutionFailureErrorDetail>>", 13);
                    }
                    zam zam2 = new zam(new ConnectionResult(i3, (PendingIntent) null), zaa(zam));
                    this.zadf.set(zam2);
                    zam = zam2;
                    z = false;
                }
            }
            if (z) {
            }
        } else if (i == 2) {
            int isGooglePlayServicesAvailable = this.zacd.isGooglePlayServicesAvailable(getActivity());
            if (isGooglePlayServicesAvailable != 0) {
                z = false;
            }
            if (zam != null) {
                if (zam.getConnectionResult().getErrorCode() == 18 && isGooglePlayServicesAvailable == 18) {
                    return;
                }
                if (z) {
                    zaq();
                    return;
                } else if (zam != null) {
                    zaa(zam.getConnectionResult(), zam.zar());
                    return;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
        z = false;
        if (z) {
        }
    }

    public void onCancel(DialogInterface dialogInterface) {
        zaa(new ConnectionResult(13, (PendingIntent) null), zaa(this.zadf.get()));
        zaq();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.zadf.set(bundle.getBoolean("resolving_error", false) ? new zam(new ConnectionResult(bundle.getInt("failed_status"), (PendingIntent) bundle.getParcelable("failed_resolution")), bundle.getInt("failed_client_id", -1)) : null);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        zam zam = this.zadf.get();
        if (zam != null) {
            bundle.putBoolean("resolving_error", true);
            bundle.putInt("failed_client_id", zam.zar());
            bundle.putInt("failed_status", zam.getConnectionResult().getErrorCode());
            bundle.putParcelable("failed_resolution", zam.getConnectionResult().getResolution());
        }
    }

    public void onStart() {
        super.onStart();
        this.mStarted = true;
    }

    public void onStop() {
        super.onStop();
        this.mStarted = false;
    }

    /* access modifiers changed from: protected */
    public abstract void zaa(ConnectionResult connectionResult, int i);

    public final void zab(ConnectionResult connectionResult, int i) {
        zam zam = new zam(connectionResult, i);
        if (this.zadf.compareAndSet((Object) null, zam)) {
            this.zadg.post(new zan(this, zam));
        }
    }

    /* access modifiers changed from: protected */
    public abstract void zao();

    /* access modifiers changed from: protected */
    public final void zaq() {
        this.zadf.set((Object) null);
        zao();
    }
}
