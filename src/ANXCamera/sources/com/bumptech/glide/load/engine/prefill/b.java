package com.bumptech.glide.load.engine.prefill;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: PreFillQueue */
final class b {
    private final Map<c, Integer> Ch;
    private final List<c> Dh;
    private int Eh;
    private int Fh;

    public b(Map<c, Integer> map) {
        this.Ch = map;
        this.Dh = new ArrayList(map.keySet());
        for (Integer intValue : map.values()) {
            this.Eh += intValue.intValue();
        }
    }

    public int getSize() {
        return this.Eh;
    }

    public boolean isEmpty() {
        return this.Eh == 0;
    }

    public c remove() {
        c cVar = (c) this.Dh.get(this.Fh);
        Integer num = (Integer) this.Ch.get(cVar);
        if (num.intValue() == 1) {
            this.Ch.remove(cVar);
            this.Dh.remove(this.Fh);
        } else {
            this.Ch.put(cVar, Integer.valueOf(num.intValue() - 1));
        }
        this.Eh--;
        this.Fh = this.Dh.isEmpty() ? 0 : (this.Fh + 1) % this.Dh.size();
        return cVar;
    }
}
