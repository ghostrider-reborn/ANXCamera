package com.bumptech.glide.load.a;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.a.e;
import com.bumptech.glide.util.i;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: DataRewinderRegistry */
public class f {
    private static final e.a<?> eR = new e.a<Object>() {
        @NonNull
        public Class<Object> aK() {
            throw new UnsupportedOperationException("Not implemented");
        }

        @NonNull
        public e<Object> o(@NonNull Object obj) {
            return new a(obj);
        }
    };
    private final Map<Class<?>, e.a<?>> eQ = new HashMap();

    /* compiled from: DataRewinderRegistry */
    private static final class a implements e<Object> {
        private final Object data;

        a(@NonNull Object obj) {
            this.data = obj;
        }

        @NonNull
        public Object aN() {
            return this.data;
        }

        public void cleanup() {
        }
    }

    public synchronized void b(@NonNull e.a<?> aVar) {
        this.eQ.put(aVar.aK(), aVar);
    }

    @NonNull
    public synchronized <T> e<T> o(@NonNull T t) {
        e.a<?> aVar;
        i.checkNotNull(t);
        aVar = this.eQ.get(t.getClass());
        if (aVar == null) {
            Iterator<e.a<?>> it = this.eQ.values().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                e.a<?> next = it.next();
                if (next.aK().isAssignableFrom(t.getClass())) {
                    aVar = next;
                    break;
                }
            }
        }
        if (aVar == null) {
            aVar = eR;
        }
        return aVar.o(t);
    }
}
