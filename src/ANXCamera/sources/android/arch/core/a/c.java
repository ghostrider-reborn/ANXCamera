package android.arch.core.a;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import java.util.concurrent.Executor;

@RestrictTo({Scope.LIBRARY_GROUP})
/* compiled from: ArchTaskExecutor */
public class c extends e {
    @NonNull
    private static final Executor ia = new b();
    private static volatile c sInstance;
    @NonNull
    private static final Executor sMainThreadExecutor = new a();
    @NonNull
    private e ga = this.ha;
    @NonNull
    private e ha = new d();

    private c() {
    }

    @NonNull
    public static Executor S() {
        return ia;
    }

    @NonNull
    public static c getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        synchronized (c.class) {
            if (sInstance == null) {
                sInstance = new c();
            }
        }
        return sInstance;
    }

    @NonNull
    public static Executor getMainThreadExecutor() {
        return sMainThreadExecutor;
    }

    public void a(@Nullable e eVar) {
        if (eVar == null) {
            eVar = this.ha;
        }
        this.ga = eVar;
    }

    public void a(Runnable runnable) {
        this.ga.a(runnable);
    }

    public void c(Runnable runnable) {
        this.ga.c(runnable);
    }

    public boolean isMainThread() {
        return this.ga.isMainThread();
    }
}
