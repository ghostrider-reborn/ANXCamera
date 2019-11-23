package com.bumptech.glide.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.bumptech.glide.c;
import com.bumptech.glide.i;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SupportRequestManagerFragment extends Fragment {
    private static final String TAG = "SupportRMFragment";
    @Nullable
    private i bJ;
    private final a nZ;
    private final m oa;
    private final Set<SupportRequestManagerFragment> ob;
    @Nullable
    private SupportRequestManagerFragment os;
    @Nullable
    private Fragment ot;

    private class a implements m {
        a() {
        }

        @NonNull
        public Set<i> dz() {
            Set<SupportRequestManagerFragment> dD = SupportRequestManagerFragment.this.dD();
            HashSet hashSet = new HashSet(dD.size());
            for (SupportRequestManagerFragment next : dD) {
                if (next.dB() != null) {
                    hashSet.add(next.dB());
                }
            }
            return hashSet;
        }

        public String toString() {
            return super.toString() + "{fragment=" + SupportRequestManagerFragment.this + "}";
        }
    }

    public SupportRequestManagerFragment() {
        this(new a());
    }

    @VisibleForTesting
    @SuppressLint({"ValidFragment"})
    public SupportRequestManagerFragment(@NonNull a aVar) {
        this.oa = new a();
        this.ob = new HashSet();
        this.nZ = aVar;
    }

    private void a(SupportRequestManagerFragment supportRequestManagerFragment) {
        this.ob.add(supportRequestManagerFragment);
    }

    private void b(SupportRequestManagerFragment supportRequestManagerFragment) {
        this.ob.remove(supportRequestManagerFragment);
    }

    private void d(@NonNull FragmentActivity fragmentActivity) {
        dF();
        this.os = c.c(fragmentActivity).R().c(fragmentActivity);
        if (!equals(this.os)) {
            this.os.a(this);
        }
    }

    private boolean d(@NonNull Fragment fragment) {
        Fragment dI = dI();
        while (true) {
            Fragment parentFragment = fragment.getParentFragment();
            if (parentFragment == null) {
                return false;
            }
            if (parentFragment.equals(dI)) {
                return true;
            }
            fragment = fragment.getParentFragment();
        }
    }

    private void dF() {
        if (this.os != null) {
            this.os.b(this);
            this.os = null;
        }
    }

    @Nullable
    private Fragment dI() {
        Fragment parentFragment = getParentFragment();
        return parentFragment != null ? parentFragment : this.ot;
    }

    /* access modifiers changed from: package-private */
    public void c(@Nullable Fragment fragment) {
        this.ot = fragment;
        if (fragment != null && fragment.getActivity() != null) {
            d(fragment.getActivity());
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
    @NonNull
    public Set<SupportRequestManagerFragment> dD() {
        if (this.os == null) {
            return Collections.emptySet();
        }
        if (equals(this.os)) {
            return Collections.unmodifiableSet(this.ob);
        }
        HashSet hashSet = new HashSet();
        for (SupportRequestManagerFragment next : this.os.dD()) {
            if (d(next.dI())) {
                hashSet.add(next);
            }
        }
        return Collections.unmodifiableSet(hashSet);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            d(getActivity());
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
        this.ot = null;
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
        return super.toString() + "{parent=" + dI() + "}";
    }
}
