package miui.util.concurrent;

import java.util.Iterator;
import miui.util.concurrent.Queue;

public class ConcurrentLinkedQueue<T> implements Queue<T> {
    private final java.util.concurrent.ConcurrentLinkedQueue<T> VM = new java.util.concurrent.ConcurrentLinkedQueue<>();

    public boolean put(T t) {
        return this.VM.offer(t);
    }

    public T get() {
        return this.VM.poll();
    }

    public boolean remove(T t) {
        return this.VM.remove(t);
    }

    public int remove(Queue.Predicate<T> predicate) {
        Iterator<T> it = this.VM.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (predicate.apply(it.next())) {
                it.remove();
                i++;
            }
        }
        return i;
    }

    public int clear() {
        int size = this.VM.size();
        this.VM.clear();
        return size;
    }

    public boolean isEmpty() {
        return this.VM.isEmpty();
    }

    public int getCapacity() {
        return -1;
    }
}
