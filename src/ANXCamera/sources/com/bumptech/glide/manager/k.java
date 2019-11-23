package com.bumptech.glide.manager;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.bumptech.glide.c;
import com.bumptech.glide.i;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Deprecated
/* compiled from: RequestManagerFragment */
public class k extends Fragment {
    private static final String TAG = "RMFragment";
    @Nullable
    private i bJ;
    private final a nZ;
    private final m oa;
    private final Set<k> ob;
    @Nullable
    private k oc;
    @Nullable
    private Fragment od;

    /* compiled from: RequestManagerFragment */
    private class a implements m {
        a() {
        }

        @NonNull
        public Set<i> dz() {
            Set<k> dD = k.this.dD();
            HashSet hashSet = new HashSet(dD.size());
            for (k next : dD) {
                if (next.dB() != null) {
                    hashSet.add(next.dB());
                }
            }
            return hashSet;
        }

        public String toString() {
            return super.toString() + "{fragment=" + k.this + "}";
        }
    }

    public k() {
        this(new a());
    }

    @VisibleForTesting
    @SuppressLint({"ValidFragment"})
    k(@NonNull a aVar) {
        this.oa = new a();
        this.ob = new HashSet();
        this.nZ = aVar;
    }

    private void a(k kVar) {
        this.ob.add(kVar);
    }

    private void b(@NonNull Activity activity) {
        dF();
        this.oc = c.c(activity).R().e(activity);
        if (!equals(this.oc)) {
            this.oc.a(this);
        }
    }

    private void b(k kVar) {
        this.ob.remove(kVar);
    }

    @TargetApi(17)
    private boolean c(@NonNull Fragment fragment) {
        Fragment parentFragment = getParentFragment();
        while (true) {
            Fragment parentFragment2 = fragment.getParentFragment();
            if (parentFragment2 == null) {
                return false;
            }
            if (parentFragment2.equals(parentFragment)) {
                return true;
            }
            fragment = fragment.getParentFragment();
        }
    }

    @Nullable
    @TargetApi(17)
    private Fragment dE() {
        Fragment parentFragment = Build.VERSION.SDK_INT >= 17 ? getParentFragment() : null;
        return parentFragment != null ? parentFragment : this.od;
    }

    private void dF() {
        if (this.oc != null) {
            this.oc.b(this);
            this.oc = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void b(@Nullable Fragment fragment) {
        this.od = fragment;
        if (fragment != null && fragment.getActivity() != null) {
            b(fragment.getActivity());
        }
    }

    public void c(@Nullable i iVar) {
        this.bJ = iVar;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public a dA() {
        return this.nZ;
    }

    @Nullable
    public i dB() {
        return this.bJ;
    }

    @NonNull
    public m dC() {
        return this.oa;
    }

    /* access modifiers changed from: package-private */
    @TargetApi(17)
    @NonNull
    public Set<k> dD() {
        if (equals(this.oc)) {
            return Collections.unmodifiableSet(this.ob);
        }
        if (this.oc == null || Build.VERSION.SDK_INT < 17) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet();
        for (k next : this.oc.dD()) {
            if (c(next.getParentFragment())) {
                hashSet.add(next);
            }
        }
        return Collections.unmodifiableSet(hashSet);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            b(activity);
        } catch (IllegalStateException e) {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Unable to register fragment with root", e);
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.nZ.onDestroy();
        dF();
    }

    public void onDetach() {
        super.onDetach();
        dF();
    }

    public void onStart() {
        super.onStart();
        this.nZ.onStart();
    }

    public void onStop() {
        super.onStop();
        this.nZ.onStop();
    }

    public String toString() {
        return super.toString() + "{parent=" + dE() + "}";
    }
}
