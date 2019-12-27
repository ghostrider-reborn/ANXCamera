package com.bumptech.glide.load.a;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.a.e;
import com.bumptech.glide.util.i;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: DataRewinderRegistry */
public class g {
    private static final e.a<?> Sd = new f();
    private final Map<Class<?>, e.a<?>> Rd = new HashMap();

    /* compiled from: DataRewinderRegistry */
    private static final class a implements e<Object> {
        private final Object data;

        a(@NonNull Object obj) {
            this.data = obj;
        }

        @NonNull
        public Object C() {
            return this.data;
        }

        public void cleanup() {
        }
    }

    public synchronized void a(@NonNull e.a<?> aVar) {
        this.Rd.put(aVar.M(), aVar);
    }

    @NonNull
    public synchronized <T> e<T> build(@NonNull T t) {
        e.a<?> aVar;
        i.checkNotNull(t);
        aVar = this.Rd.get(t.getClass());
        if (aVar == null) {
            Iterator<e.a<?>> it = this.Rd.values().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                e.a<?> next = it.next();
                if (next.M().isAssignableFrom(t.getClass())) {
                    aVar = next;
                    break;
                }
            }
        }
        if (aVar == null) {
            aVar = Sd;
        }
        return aVar.build(t);
    }
}
