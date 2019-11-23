package com.bumptech.glide.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.FragmentManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;
import com.bumptech.glide.c;
import com.bumptech.glide.i;
import com.bumptech.glide.util.k;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* compiled from: RequestManagerRetriever */
public class l implements Handler.Callback {
    @VisibleForTesting
    static final String FRAGMENT_TAG = "com.bumptech.glide.manager";
    private static final String TAG = "RMRetriever";
    private static final int of = 1;
    private static final int og = 2;
    private static final String oh = "key";
    private static final a on = new a() {
        @NonNull
        public i a(@NonNull c cVar, @NonNull h hVar, @NonNull m mVar, @NonNull Context context) {
            return new i(cVar, hVar, mVar, context);
        }
    };
    private final Handler handler;
    private volatile i oi;
    private final a oj;
    private final ArrayMap<View, Fragment> ok = new ArrayMap<>();
    private final ArrayMap<View, android.app.Fragment> ol = new ArrayMap<>();
    private final Bundle om = new Bundle();
    @VisibleForTesting
    final Map<FragmentManager, k> pendingRequestManagerFragments = new HashMap();
    @VisibleForTesting
    final Map<android.support.v4.app.FragmentManager, SupportRequestManagerFragment> pendingSupportRequestManagerFragments = new HashMap();

    /* compiled from: RequestManagerRetriever */
    public interface a {
        @NonNull
        i a(@NonNull c cVar, @NonNull h hVar, @NonNull m mVar, @NonNull Context context);
    }

    public l(@Nullable a aVar) {
        this.oj = aVar == null ? on : aVar;
        this.handler = new Handler(Looper.getMainLooper(), this);
    }

    @Nullable
    @Deprecated
    private android.app.Fragment a(@NonNull View view, @NonNull Activity activity) {
        this.ol.clear();
        a(activity.getFragmentManager(), this.ol);
        View findViewById = activity.findViewById(16908290);
        android.app.Fragment fragment = null;
        while (!view.equals(findViewById)) {
            fragment = this.ol.get(view);
            if (fragment != null || !(view.getParent() instanceof View)) {
                break;
            }
            view = (View) view.getParent();
        }
        this.ol.clear();
        return fragment;
    }

    @Nullable
    private Fragment a(@NonNull View view, @NonNull FragmentActivity fragmentActivity) {
        this.ok.clear();
        a((Collection<Fragment>) fragmentActivity.getSupportFragmentManager().getFragments(), (Map<View, Fragment>) this.ok);
        View findViewById = fragmentActivity.findViewById(16908290);
        Fragment fragment = null;
        while (!view.equals(findViewById)) {
            fragment = this.ok.get(view);
            if (fragment != null || !(view.getParent() instanceof View)) {
                break;
            }
            view = (View) view.getParent();
        }
        this.ok.clear();
        return fragment;
    }

    @Deprecated
    @NonNull
    private i a(@NonNull Context context, @NonNull FragmentManager fragmentManager, @Nullable android.app.Fragment fragment, boolean z) {
        k a2 = a(fragmentManager, fragment, z);
        i dB = a2.dB();
        if (dB != null) {
            return dB;
        }
        i a3 = this.oj.a(c.c(context), a2.dA(), a2.dC(), context);
        a2.c(a3);
        return a3;
    }

    @NonNull
    private i a(@NonNull Context context, @NonNull android.support.v4.app.FragmentManager fragmentManager, @Nullable Fragment fragment, boolean z) {
        SupportRequestManagerFragment a2 = a(fragmentManager, fragment, z);
        i dB = a2.dB();
        if (dB != null) {
            return dB;
        }
        i a3 = this.oj.a(c.c(context), a2.dA(), a2.dC(), context);
        a2.c(a3);
        return a3;
    }

    @NonNull
    private SupportRequestManagerFragment a(@NonNull android.support.v4.app.FragmentManager fragmentManager, @Nullable Fragment fragment, boolean z) {
        SupportRequestManagerFragment supportRequestManagerFragment = (SupportRequestManagerFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (supportRequestManagerFragment == null) {
            supportRequestManagerFragment = this.pendingSupportRequestManagerFragments.get(fragmentManager);
            if (supportRequestManagerFragment == null) {
                supportRequestManagerFragment = new SupportRequestManagerFragment();
                supportRequestManagerFragment.c(fragment);
                if (z) {
                    supportRequestManagerFragment.dA().onStart();
                }
                this.pendingSupportRequestManagerFragments.put(fragmentManager, supportRequestManagerFragment);
                fragmentManager.beginTransaction().add((Fragment) supportRequestManagerFragment, FRAGMENT_TAG).commitAllowingStateLoss();
                this.handler.obtainMessage(2, fragmentManager).sendToTarget();
            }
        }
        return supportRequestManagerFragment;
    }

    @NonNull
    private k a(@NonNull FragmentManager fragmentManager, @Nullable android.app.Fragment fragment, boolean z) {
        k kVar = (k) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (kVar == null) {
            kVar = this.pendingRequestManagerFragments.get(fragmentManager);
            if (kVar == null) {
                kVar = new k();
                kVar.b(fragment);
                if (z) {
                    kVar.dA().onStart();
                }
                this.pendingRequestManagerFragments.put(fragmentManager, kVar);
                fragmentManager.beginTransaction().add(kVar, FRAGMENT_TAG).commitAllowingStateLoss();
                this.handler.obtainMessage(1, fragmentManager).sendToTarget();
            }
        }
        return kVar;
    }

    @TargetApi(26)
    @Deprecated
    private void a(@NonNull FragmentManager fragmentManager, @NonNull ArrayMap<View, android.app.Fragment> arrayMap) {
        if (Build.VERSION.SDK_INT >= 26) {
            for (android.app.Fragment next : fragmentManager.getFragments()) {
                if (next.getView() != null) {
                    arrayMap.put(next.getView(), next);
                    a(next.getChildFragmentManager(), arrayMap);
                }
            }
            return;
        }
        b(fragmentManager, arrayMap);
    }

    private static void a(@Nullable Collection<Fragment> collection, @NonNull Map<View, Fragment> map) {
        if (collection != null) {
            for (Fragment next : collection) {
                if (!(next == null || next.getView() == null)) {
                    map.put(next.getView(), next);
                    a((Collection<Fragment>) next.getChildFragmentManager().getFragments(), map);
                }
            }
        }
    }

    @Deprecated
    private void b(@NonNull FragmentManager fragmentManager, @NonNull ArrayMap<View, android.app.Fragment> arrayMap) {
        int i = 0;
        while (true) {
            int i2 = i + 1;
            this.om.putInt(oh, i);
            android.app.Fragment fragment = null;
            try {
                fragment = fragmentManager.getFragment(this.om, oh);
            } catch (Exception e) {
            }
            if (fragment != null) {
                if (fragment.getView() != null) {
                    arrayMap.put(fragment.getView(), fragment);
                    if (Build.VERSION.SDK_INT >= 17) {
                        a(fragment.getChildFragmentManager(), arrayMap);
                    }
                }
                i = i2;
            } else {
                return;
            }
        }
    }

    @TargetApi(17)
    private static void d(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= 17 && activity.isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a load for a destroyed activity");
        }
    }

    private static boolean f(Activity activity) {
        return !activity.isFinishing();
    }

    @NonNull
    private i i(@NonNull Context context) {
        if (this.oi == null) {
            synchronized (this) {
                if (this.oi == null) {
                    this.oi = this.oj.a(c.c(context.getApplicationContext()), new b(), new g(), context.getApplicationContext());
                }
            }
        }
        return this.oi;
    }

    @Nullable
    private Activity k(@NonNull Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return k(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    @NonNull
    public i b(@NonNull Fragment fragment) {
        com.bumptech.glide.util.i.a(fragment.getActivity(), "You cannot start a load on a fragment before it is attached or after it is destroyed");
        if (k.fr()) {
            return j(fragment.getActivity().getApplicationContext());
        }
        return a((Context) fragment.getActivity(), fragment.getChildFragmentManager(), fragment, fragment.isVisible());
    }

    @NonNull
    public i b(@NonNull FragmentActivity fragmentActivity) {
        if (k.fr()) {
            return j(fragmentActivity.getApplicationContext());
        }
        d((Activity) fragmentActivity);
        return a((Context) fragmentActivity, fragmentActivity.getSupportFragmentManager(), (Fragment) null, f(fragmentActivity));
    }

    @NonNull
    public i c(@NonNull Activity activity) {
        if (k.fr()) {
            return j(activity.getApplicationContext());
        }
        d(activity);
        return a((Context) activity, activity.getFragmentManager(), (android.app.Fragment) null, f(activity));
    }

    @NonNull
    public i c(@NonNull View view) {
        if (k.fr()) {
            return j(view.getContext().getApplicationContext());
        }
        com.bumptech.glide.util.i.checkNotNull(view);
        com.bumptech.glide.util.i.a(view.getContext(), "Unable to obtain a request manager for a view without a Context");
        Activity k = k(view.getContext());
        if (k == null) {
            return j(view.getContext().getApplicationContext());
        }
        if (k instanceof FragmentActivity) {
            Fragment a2 = a(view, (FragmentActivity) k);
            return a2 != null ? b(a2) : c(k);
        }
        android.app.Fragment a3 = a(view, k);
        return a3 == null ? c(k) : d(a3);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public SupportRequestManagerFragment c(FragmentActivity fragmentActivity) {
        return a(fragmentActivity.getSupportFragmentManager(), (Fragment) null, f(fragmentActivity));
    }

    @TargetApi(17)
    @Deprecated
    @NonNull
    public i d(@NonNull android.app.Fragment fragment) {
        if (fragment.getActivity() == null) {
            throw new IllegalArgumentException("You cannot start a load on a fragment before it is attached");
        } else if (k.fr() || Build.VERSION.SDK_INT < 17) {
            return j(fragment.getActivity().getApplicationContext());
        } else {
            return a((Context) fragment.getActivity(), fragment.getChildFragmentManager(), fragment, fragment.isVisible());
        }
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    @NonNull
    public k e(Activity activity) {
        return a(activity.getFragmentManager(), (android.app.Fragment) null, f(activity));
    }

    public boolean handleMessage(Message message) {
        Object obj;
        Object obj2 = null;
        boolean z = true;
        switch (message.what) {
            case 1:
                obj2 = (FragmentManager) message.obj;
                obj = this.pendingRequestManagerFragments.remove(obj2);
                break;
            case 2:
                obj2 = (android.support.v4.app.FragmentManager) message.obj;
                obj = this.pendingSupportRequestManagerFragments.remove(obj2);
                break;
            default:
                z = false;
                obj = null;
                break;
        }
        if (z && obj == null && Log.isLoggable(TAG, 5)) {
            Log.w(TAG, "Failed to remove expected request manager fragment, manager: " + obj2);
        }
        return z;
    }

    @NonNull
    public i j(@NonNull Context context) {
        if (context != null) {
            if (k.fq() && !(context instanceof Application)) {
                if (context instanceof FragmentActivity) {
                    return b((FragmentActivity) context);
                }
                if (context instanceof Activity) {
                    return c((Activity) context);
                }
                if (context instanceof ContextWrapper) {
                    return j(((ContextWrapper) context).getBaseContext());
                }
            }
            return i(context);
        }
        throw new IllegalArgumentException("You cannot start a load on a null Context");
    }
}
