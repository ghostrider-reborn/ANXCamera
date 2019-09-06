package com.bumptech.glide.load.a;

import android.support.annotation.NonNull;
import com.bumptech.glide.util.i;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: DataRewinderRegistry */
public class g {
    private static final com.bumptech.glide.load.a.e.a<?> Sd = new f();
    private final Map<Class<?>, com.bumptech.glide.load.a.e.a<?>> Rd = new HashMap();

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

    public synchronized void a(@NonNull com.bumptech.glide.load.a.e.a<?> aVar) {
        this.Rd.put(aVar.M(), aVar);
    }

    @NonNull
    public synchronized <T> e<T> build(@NonNull T t) {
        com.bumptech.glide.load.a.e.a aVar;
        i.checkNotNull(t);
        aVar = (com.bumptech.glide.load.a.e.a) this.Rd.get(t.getClass());
        if (aVar == null) {
            Iterator it = this.Rd.values().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                com.bumptech.glide.load.a.e.a aVar2 = (com.bumptech.glide.load.a.e.a) it.next();
                if (aVar2.M().isAssignableFrom(t.getClass())) {
                    aVar = aVar2;
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
