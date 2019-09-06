package android.arch.core.a;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;

@RestrictTo({Scope.LIBRARY_GROUP})
/* compiled from: TaskExecutor */
public abstract class e {
    public abstract void a(@NonNull Runnable runnable);

    public void b(@NonNull Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            c(runnable);
        }
    }

    public abstract void c(@NonNull Runnable runnable);

    public abstract boolean isMainThread();
}
