package com.bumptech.glide.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.FragmentManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
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
import com.bumptech.glide.m;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.l;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* compiled from: RequestManagerRetriever */
public class n implements Callback {
    @VisibleForTesting
    static final String FRAGMENT_TAG = "com.bumptech.glide.manager";
    private static final a Sd = new m();
    private static final String TAG = "RMRetriever";
    private static final int mk = 1;
    private static final int nk = 2;
    private static final String pk = "key";
    private final a factory;
    private final Handler handler;
    private volatile m ik;
    private final ArrayMap<View, Fragment> jk = new ArrayMap<>();
    private final ArrayMap<View, android.app.Fragment> kk = new ArrayMap<>();
    private final Bundle lk = new Bundle();
    @VisibleForTesting
    final Map<FragmentManager, l> pendingRequestManagerFragments = new HashMap();
    @VisibleForTesting
    final Map<android.support.v4.app.FragmentManager, SupportRequestManagerFragment> pendingSupportRequestManagerFragments = new HashMap();

    /* compiled from: RequestManagerRetriever */
    public interface a {
        @NonNull
        m a(@NonNull c cVar, @NonNull i iVar, @NonNull o oVar, @NonNull Context context);
    }

    public n(@Nullable a aVar) {
        if (aVar == null) {
            aVar = Sd;
        }
        this.factory = aVar;
        this.handler = new Handler(Looper.getMainLooper(), this);
    }

    @Nullable
    @Deprecated
    private android.app.Fragment a(@NonNull View view, @NonNull Activity activity) {
        this.kk.clear();
        a(activity.getFragmentManager(), this.kk);
        View findViewById = activity.findViewById(16908290);
        android.app.Fragment fragment = null;
        while (!view.equals(findViewById)) {
            fragment = (android.app.Fragment) this.kk.get(view);
            if (fragment != null || !(view.getParent() instanceof View)) {
                break;
            }
            view = (View) view.getParent();
        }
        this.kk.clear();
        return fragment;
    }

    @Nullable
    private Fragment a(@NonNull View view, @NonNull FragmentActivity fragmentActivity) {
        this.jk.clear();
        a((Collection<Fragment>) fragmentActivity.getSupportFragmentManager().getFragments(), (Map<View, Fragment>) this.jk);
        View findViewById = fragmentActivity.findViewById(16908290);
        Fragment fragment = null;
        while (!view.equals(findViewById)) {
            fragment = (Fragment) this.jk.get(view);
            if (fragment != null || !(view.getParent() instanceof View)) {
                break;
            }
            view = (View) view.getParent();
        }
        this.jk.clear();
        return fragment;
    }

    @Deprecated
    @NonNull
    private m a(@NonNull Context context, @NonNull FragmentManager fragmentManager, @Nullable android.app.Fragment fragment, boolean z) {
        l a2 = a(fragmentManager, fragment, z);
        m Q = a2.Q();
        if (Q != null) {
            return Q;
        }
        m a3 = this.factory.a(c.get(context), a2.P(), a2.R(), context);
        a2.a(a3);
        return a3;
    }

    @NonNull
    private m a(@NonNull Context context, @NonNull android.support.v4.app.FragmentManager fragmentManager, @Nullable Fragment fragment, boolean z) {
        SupportRequestManagerFragment a2 = a(fragmentManager, fragment, z);
        m Q = a2.Q();
        if (Q != null) {
            return Q;
        }
        m a3 = this.factory.a(c.get(context), a2.P(), a2.R(), context);
        a2.a(a3);
        return a3;
    }

    @NonNull
    private SupportRequestManagerFragment a(@NonNull android.support.v4.app.FragmentManager fragmentManager, @Nullable Fragment fragment, boolean z) {
        String str = FRAGMENT_TAG;
        SupportRequestManagerFragment supportRequestManagerFragment = (SupportRequestManagerFragment) fragmentManager.findFragmentByTag(str);
        if (supportRequestManagerFragment == null) {
            supportRequestManagerFragment = (SupportRequestManagerFragment) this.pendingSupportRequestManagerFragments.get(fragmentManager);
            if (supportRequestManagerFragment == null) {
                supportRequestManagerFragment = new SupportRequestManagerFragment();
                supportRequestManagerFragment.a(fragment);
                if (z) {
                    supportRequestManagerFragment.P().onStart();
                }
                this.pendingSupportRequestManagerFragments.put(fragmentManager, supportRequestManagerFragment);
                fragmentManager.beginTransaction().add((Fragment) supportRequestManagerFragment, str).commitAllowingStateLoss();
                this.handler.obtainMessage(2, fragmentManager).sendToTarget();
            }
        }
        return supportRequestManagerFragment;
    }

    @NonNull
    private l a(@NonNull FragmentManager fragmentManager, @Nullable android.app.Fragment fragment, boolean z) {
        String str = FRAGMENT_TAG;
        l lVar = (l) fragmentManager.findFragmentByTag(str);
        if (lVar == null) {
            lVar = (l) this.pendingRequestManagerFragments.get(fragmentManager);
            if (lVar == null) {
                lVar = new l();
                lVar.a(fragment);
                if (z) {
                    lVar.P().onStart();
                }
                this.pendingRequestManagerFragments.put(fragmentManager, lVar);
                fragmentManager.beginTransaction().add(lVar, str).commitAllowingStateLoss();
                this.handler.obtainMessage(1, fragmentManager).sendToTarget();
            }
        }
        return lVar;
    }

    @TargetApi(26)
    @Deprecated
    private void a(@NonNull FragmentManager fragmentManager, @NonNull ArrayMap<View, android.app.Fragment> arrayMap) {
        if (VERSION.SDK_INT >= 26) {
            for (android.app.Fragment fragment : fragmentManager.getFragments()) {
                if (fragment.getView() != null) {
                    arrayMap.put(fragment.getView(), fragment);
                    a(fragment.getChildFragmentManager(), arrayMap);
                }
            }
            return;
        }
        b(fragmentManager, arrayMap);
    }

    private static void a(@Nullable Collection<Fragment> collection, @NonNull Map<View, Fragment> map) {
        if (collection != null) {
            for (Fragment fragment : collection) {
                if (!(fragment == null || fragment.getView() == null)) {
                    map.put(fragment.getView(), fragment);
                    a((Collection<Fragment>) fragment.getChildFragmentManager().getFragments(), map);
                }
            }
        }
    }

    @Deprecated
    private void b(@NonNull FragmentManager fragmentManager, @NonNull ArrayMap<View, android.app.Fragment> arrayMap) {
        int i = 0;
        while (true) {
            Bundle bundle = this.lk;
            int i2 = i + 1;
            String str = pk;
            bundle.putInt(str, i);
            android.app.Fragment fragment = null;
            try {
                fragment = fragmentManager.getFragment(this.lk, str);
            } catch (Exception unused) {
            }
            if (fragment != null) {
                if (fragment.getView() != null) {
                    arrayMap.put(fragment.getView(), fragment);
                    if (VERSION.SDK_INT >= 17) {
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
    private static void g(@NonNull Activity activity) {
        if (VERSION.SDK_INT >= 17 && activity.isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a load for a destroyed activity");
        }
    }

    private static boolean h(Activity activity) {
        return !activity.isFinishing();
    }

    @Nullable
    private Activity m(@NonNull Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return m(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    @NonNull
    private m n(@NonNull Context context) {
        if (this.ik == null) {
            synchronized (this) {
                if (this.ik == null) {
                    this.ik = this.factory.a(c.get(context.getApplicationContext()), new b(), new h(), context.getApplicationContext());
                }
            }
        }
        return this.ik;
    }

    @NonNull
    public m b(@NonNull FragmentActivity fragmentActivity) {
        if (l.Jh()) {
            return get(fragmentActivity.getApplicationContext());
        }
        g(fragmentActivity);
        return a((Context) fragmentActivity, fragmentActivity.getSupportFragmentManager(), (Fragment) null, h(fragmentActivity));
    }

    @TargetApi(17)
    @Deprecated
    @NonNull
    public m c(@NonNull android.app.Fragment fragment) {
        if (fragment.getActivity() == null) {
            throw new IllegalArgumentException("You cannot start a load on a fragment before it is attached");
        } else if (l.Jh() || VERSION.SDK_INT < 17) {
            return get(fragment.getActivity().getApplicationContext());
        } else {
            return a((Context) fragment.getActivity(), fragment.getChildFragmentManager(), fragment, fragment.isVisible());
        }
    }

    @NonNull
    public m c(@NonNull Fragment fragment) {
        i.a(fragment.getActivity(), "You cannot start a load on a fragment before it is attached or after it is destroyed");
        if (l.Jh()) {
            return get(fragment.getActivity().getApplicationContext());
        }
        return a((Context) fragment.getActivity(), fragment.getChildFragmentManager(), fragment, fragment.isVisible());
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public SupportRequestManagerFragment c(FragmentActivity fragmentActivity) {
        return a(fragmentActivity.getSupportFragmentManager(), (Fragment) null, h(fragmentActivity));
    }

    /* access modifiers changed from: 0000 */
    @Deprecated
    @NonNull
    public l e(Activity activity) {
        return a(activity.getFragmentManager(), (android.app.Fragment) null, h(activity));
    }

    @NonNull
    public m get(@NonNull Activity activity) {
        if (l.Jh()) {
            return get(activity.getApplicationContext());
        }
        g(activity);
        return a((Context) activity, activity.getFragmentManager(), (android.app.Fragment) null, h(activity));
    }

    @NonNull
    public m get(@NonNull Context context) {
        if (context != null) {
            if (l.Kh() && !(context instanceof Application)) {
                if (context instanceof FragmentActivity) {
                    return b((FragmentActivity) context);
                }
                if (context instanceof Activity) {
                    return get((Activity) context);
                }
                if (context instanceof ContextWrapper) {
                    return get(((ContextWrapper) context).getBaseContext());
                }
            }
            return n(context);
        }
        throw new IllegalArgumentException("You cannot start a load on a null Context");
    }

    @NonNull
    public m get(@NonNull View view) {
        if (l.Jh()) {
            return get(view.getContext().getApplicationContext());
        }
        i.checkNotNull(view);
        i.a(view.getContext(), "Unable to obtain a request manager for a view without a Context");
        Activity m = m(view.getContext());
        if (m == null) {
            return get(view.getContext().getApplicationContext());
        }
        if (m instanceof FragmentActivity) {
            Fragment a2 = a(view, (FragmentActivity) m);
            return a2 != null ? c(a2) : get(m);
        }
        android.app.Fragment a3 = a(view, m);
        return a3 == null ? get(m) : c(a3);
    }

    public boolean handleMessage(Message message) {
        Object obj;
        int i = message.what;
        Object obj2 = null;
        boolean z = true;
        if (i == 1) {
            obj2 = (FragmentManager) message.obj;
            obj = this.pendingRequestManagerFragments.remove(obj2);
        } else if (i != 2) {
            z = false;
            obj = null;
        } else {
            obj2 = (android.support.v4.app.FragmentManager) message.obj;
            obj = this.pendingSupportRequestManagerFragments.remove(obj2);
        }
        if (z && obj == null) {
            String str = TAG;
            if (Log.isLoggable(str, 5)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Failed to remove expected request manager fragment, manager: ");
                sb.append(obj2);
                Log.w(str, sb.toString());
            }
        }
        return z;
    }
}
