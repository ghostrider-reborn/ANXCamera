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
import com.bumptech.glide.m;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SupportRequestManagerFragment extends Fragment {
    private static final String TAG = "SupportRMFragment";
    private final a aa;
    private final o ba;
    private final Set<SupportRequestManagerFragment> ca;
    @Nullable
    private m da;
    @Nullable
    private SupportRequestManagerFragment ea;
    @Nullable
    private Fragment fa;

    private class a implements o {
        a() {
        }

        @NonNull
        public Set<m> H() {
            Set<SupportRequestManagerFragment> O = SupportRequestManagerFragment.this.O();
            HashSet hashSet = new HashSet(O.size());
            for (SupportRequestManagerFragment next : O) {
                if (next.Q() != null) {
                    hashSet.add(next.Q());
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
        this.ba = new a();
        this.ca = new HashSet();
        this.aa = aVar;
    }

    @Nullable
    private Fragment Oj() {
        Fragment parentFragment = getParentFragment();
        return parentFragment != null ? parentFragment : this.fa;
    }

    private void Pj() {
        SupportRequestManagerFragment supportRequestManagerFragment = this.ea;
        if (supportRequestManagerFragment != null) {
            supportRequestManagerFragment.b(this);
            this.ea = null;
        }
    }

    private void a(SupportRequestManagerFragment supportRequestManagerFragment) {
        this.ca.add(supportRequestManagerFragment);
    }

    private void b(SupportRequestManagerFragment supportRequestManagerFragment) {
        this.ca.remove(supportRequestManagerFragment);
    }

    private void d(@NonNull FragmentActivity fragmentActivity) {
        Pj();
        this.ea = c.get(fragmentActivity).Ff().c(fragmentActivity);
        if (!equals(this.ea)) {
            this.ea.a(this);
        }
    }

    private boolean d(@NonNull Fragment fragment) {
        Fragment Oj = Oj();
        while (true) {
            Fragment parentFragment = fragment.getParentFragment();
            if (parentFragment == null) {
                return false;
            }
            if (parentFragment.equals(Oj)) {
                return true;
            }
            fragment = fragment.getParentFragment();
        }
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Set<SupportRequestManagerFragment> O() {
        SupportRequestManagerFragment supportRequestManagerFragment = this.ea;
        if (supportRequestManagerFragment == null) {
            return Collections.emptySet();
        }
        if (equals(supportRequestManagerFragment)) {
            return Collections.unmodifiableSet(this.ca);
        }
        HashSet hashSet = new HashSet();
        for (SupportRequestManagerFragment next : this.ea.O()) {
            if (d(next.Oj())) {
                hashSet.add(next);
            }
        }
        return Collections.unmodifiableSet(hashSet);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public a P() {
        return this.aa;
    }

    @Nullable
    public m Q() {
        return this.da;
    }

    @NonNull
    public o R() {
        return this.ba;
    }

    /* access modifiers changed from: package-private */
    public void a(@Nullable Fragment fragment) {
        this.fa = fragment;
        if (fragment != null && fragment.getActivity() != null) {
            d(fragment.getActivity());
        }
    }

    public void a(@Nullable m mVar) {
        this.da = mVar;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            d(getActivity());
        } catch (IllegalStateException e2) {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Unable to register fragment with root", e2);
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.aa.onDestroy();
        Pj();
    }

    public void onDetach() {
        super.onDetach();
        this.fa = null;
        Pj();
    }

    public void onStart() {
        super.onStart();
        this.aa.onStart();
    }

    public void onStop() {
        super.onStop();
        this.aa.onStop();
    }

    public String toString() {
        return super.toString() + "{parent=" + Oj() + "}";
    }
}
