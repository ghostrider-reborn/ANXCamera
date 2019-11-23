package android.arch.core.b;

import android.arch.core.b.b;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import java.util.HashMap;
import java.util.Map;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* compiled from: FastSafeIterableMap */
public class a<K, V> extends b<K, V> {
    private HashMap<K, b.c<K, V>> ac = new HashMap<>();

    /* access modifiers changed from: protected */
    public b.c<K, V> a(K k) {
        return this.ac.get(k);
    }

    public Map.Entry<K, V> b(K k) {
        if (contains(k)) {
            return this.ac.get(k).ai;
        }
        return null;
    }

    public boolean contains(K k) {
        return this.ac.containsKey(k);
    }

    public V putIfAbsent(@NonNull K k, @NonNull V v) {
        b.c a2 = a(k);
        if (a2 != null) {
            return a2.mValue;
        }
        this.ac.put(k, a(k, v));
        return null;
    }

    public V remove(@NonNull K k) {
        V remove = super.remove(k);
        this.ac.remove(k);
        return remove;
    }
}
