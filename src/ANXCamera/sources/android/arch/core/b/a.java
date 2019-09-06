package android.arch.core.b;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import java.util.HashMap;
import java.util.Map.Entry;

@RestrictTo({Scope.LIBRARY_GROUP})
/* compiled from: FastSafeIterableMap */
public class a<K, V> extends c<K, V> {
    private HashMap<K, C0002c<K, V>> oa = new HashMap<>();

    public boolean contains(K k) {
        return this.oa.containsKey(k);
    }

    public Entry<K, V> g(K k) {
        if (contains(k)) {
            return ((C0002c) this.oa.get(k)).ka;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public C0002c<K, V> get(K k) {
        return (C0002c) this.oa.get(k);
    }

    public V putIfAbsent(@NonNull K k, @NonNull V v) {
        C0002c cVar = get(k);
        if (cVar != null) {
            return cVar.mValue;
        }
        this.oa.put(k, put(k, v));
        return null;
    }

    public V remove(@NonNull K k) {
        V remove = super.remove(k);
        this.oa.remove(k);
        return remove;
    }
}
