package miui.util.concurrent;

import java.util.Iterator;
import miui.util.concurrent.Queue.Predicate;

public class ConcurrentLinkedQueue<T> implements Queue<T> {
    private final java.util.concurrent.ConcurrentLinkedQueue<T> mQueue = new java.util.concurrent.ConcurrentLinkedQueue<>();

    public int clear() {
        int size = this.mQueue.size();
        this.mQueue.clear();
        return size;
    }

    public T get() {
        return this.mQueue.poll();
    }

    public int getCapacity() {
        return -1;
    }

    public boolean isEmpty() {
        return this.mQueue.isEmpty();
    }

    public boolean put(T t) {
        return this.mQueue.offer(t);
    }

    public int remove(Predicate<T> predicate) {
        int i = 0;
        Iterator it = this.mQueue.iterator();
        while (it.hasNext()) {
            if (predicate.apply(it.next())) {
                it.remove();
                i++;
            }
        }
        return i;
    }

    public boolean remove(T t) {
        return this.mQueue.remove(t);
    }
}
