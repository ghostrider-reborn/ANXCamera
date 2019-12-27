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
import com.bumptech.glide.m;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Deprecated
/* compiled from: RequestManagerFragment */
public class l extends Fragment {
    private static final String TAG = "RMFragment";
    private final a aa;
    private final o ba;
    private final Set<l> ca;
    @Nullable
    private m da;
    @Nullable
    private l ea;
    @Nullable
    private Fragment fa;

    /* compiled from: RequestManagerFragment */
    private class a implements o {
        a() {
        }

        @NonNull
        public Set<m> H() {
            Set<l> O = l.this.O();
            HashSet hashSet = new HashSet(O.size());
            for (l next : O) {
                if (next.Q() != null) {
                    hashSet.add(next.Q());
                }
            }
            return hashSet;
        }

        public String toString() {
            return super.toString() + "{fragment=" + l.this + "}";
        }
    }

    public l() {
        this(new a());
    }

    @VisibleForTesting
    @SuppressLint({"ValidFragment"})
    l(@NonNull a aVar) {
        this.ba = new a();
        this.ca = new HashSet();
        this.aa = aVar;
    }

    @Nullable
    @TargetApi(17)
    private Fragment Oj() {
        Fragment parentFragment = Build.VERSION.SDK_INT >= 17 ? getParentFragment() : null;
        return parentFragment != null ? parentFragment : this.fa;
    }

    private void Pj() {
        l lVar = this.ea;
        if (lVar != null) {
            lVar.b(this);
            this.ea = null;
        }
    }

    private void a(l lVar) {
        this.ca.add(lVar);
    }

    private void b(l lVar) {
        this.ca.remove(lVar);
    }

    @TargetApi(17)
    private boolean d(@NonNull Fragment fragment) {
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

    private void f(@NonNull Activity activity) {
        Pj();
        this.ea = c.get(activity).Ff().e(activity);
        if (!equals(this.ea)) {
            this.ea.a(this);
        }
    }

    /* access modifiers changed from: package-private */
    @TargetApi(17)
    @NonNull
    public Set<l> O() {
        if (equals(this.ea)) {
            return Collections.unmodifiableSet(this.ca);
        }
        if (this.ea == null || Build.VERSION.SDK_INT < 17) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet();
        for (l next : this.ea.O()) {
            if (d(next.getParentFragment())) {
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
            f(fragment.getActivity());
        }
    }

    public void a(@Nullable m mVar) {
        this.da = mVar;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            f(activity);
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
