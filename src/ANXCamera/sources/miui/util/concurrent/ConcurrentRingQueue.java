package miui.util.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import miui.util.concurrent.Queue;

public class ConcurrentRingQueue<T> implements Queue<T> {
    private volatile int mAdditional;
    private final boolean mAllowExtendCapacity;
    private final boolean mAutoReleaseCapacity;
    private int mCapacity;
    private volatile Node<T> mReadCursor = new Node<>();
    private final AtomicInteger mReadLock = new AtomicInteger(0);
    private volatile Node<T> mWriteCursor = this.mReadCursor;
    private final AtomicInteger mWriteLock = new AtomicInteger(0);

    private static class Node<T> {
        T element;
        Node<T> next;

        private Node() {
        }
    }

    public ConcurrentRingQueue(int i, boolean z, boolean z2) {
        this.mCapacity = i;
        this.mAllowExtendCapacity = z;
        this.mAutoReleaseCapacity = z2;
        Node<T> node = this.mReadCursor;
        for (int i2 = 0; i2 < i; i2++) {
            node.next = new Node<>();
            node = node.next;
        }
        node.next = this.mReadCursor;
    }

    public int clear() {
        int i = this.mReadLock.get();
        while (true) {
            if (i == 0 && this.mReadLock.compareAndSet(0, -1)) {
                break;
            }
            Thread.yield();
            i = this.mReadLock.get();
        }
        int i2 = 0;
        Node<T> node = this.mReadCursor;
        while (node != this.mWriteCursor) {
            node.element = null;
            i2++;
            node = node.next;
        }
        this.mReadCursor = node;
        this.mReadLock.set(0);
        return i2;
    }

    public void decreaseCapacity(int i) {
        if (this.mAutoReleaseCapacity && i > 0) {
            int i2 = this.mWriteLock.get();
            while (true) {
                if (i2 != 0 || !this.mWriteLock.compareAndSet(0, -1)) {
                    Thread.yield();
                    i2 = this.mWriteLock.get();
                } else {
                    this.mCapacity -= i;
                    this.mAdditional = i;
                    this.mWriteLock.set(0);
                    return;
                }
            }
        }
    }

    public T get() {
        int i = this.mReadLock.get();
        while (true) {
            if (i == 0 && this.mReadLock.compareAndSet(0, -1)) {
                break;
            }
            Thread.yield();
            i = this.mReadLock.get();
        }
        T t = null;
        Node<T> node = this.mReadCursor;
        Node<T> node2 = this.mWriteCursor;
        while (t == null && node != node2) {
            t = node.element;
            node.element = null;
            node = node.next;
            node2 = this.mWriteCursor;
        }
        if (t != null) {
            this.mReadCursor = node;
        }
        this.mReadLock.set(0);
        return t;
    }

    public int getCapacity() {
        int i = this.mAdditional;
        return i > 0 ? this.mCapacity + i : this.mCapacity;
    }

    public void increaseCapacity(int i) {
        if (!this.mAllowExtendCapacity && i > 0) {
            int i2 = this.mWriteLock.get();
            while (true) {
                if (i2 != 0 || !this.mWriteLock.compareAndSet(0, -1)) {
                    Thread.yield();
                    i2 = this.mWriteLock.get();
                } else {
                    this.mAdditional = -i;
                    this.mCapacity += i;
                    this.mWriteLock.set(0);
                    return;
                }
            }
        }
    }

    public boolean isEmpty() {
        return this.mWriteCursor == this.mReadCursor;
    }

    public boolean put(T t) {
        if (t == null) {
            return false;
        }
        int i = this.mWriteLock.get();
        while (true) {
            if (i == 0 && this.mWriteLock.compareAndSet(0, -1)) {
                break;
            }
            Thread.yield();
            i = this.mWriteLock.get();
        }
        boolean z = false;
        Node<T> node = this.mReadCursor;
        Node<T> node2 = this.mWriteCursor;
        int i2 = this.mAdditional;
        if (node2.next != node) {
            node2.element = t;
            if (node2.next.next != node && this.mAutoReleaseCapacity && i2 > 0) {
                node2.next = node2.next.next;
                this.mAdditional = i2 - 1;
            }
            this.mWriteCursor = node2.next;
            z = true;
        } else if (this.mAllowExtendCapacity || i2 < 0) {
            node2.next = new Node<>();
            node2.next.next = node;
            node2.element = t;
            this.mAdditional = i2 + 1;
            this.mWriteCursor = node2.next;
            z = true;
        }
        this.mWriteLock.set(0);
        return z;
    }

    public int remove(Queue.Predicate<T> predicate) {
        if (predicate == null) {
            return 0;
        }
        int i = this.mReadLock.get();
        while (true) {
            if (i == 0 && this.mReadLock.compareAndSet(0, -1)) {
                break;
            }
            Thread.yield();
            i = this.mReadLock.get();
        }
        int i2 = 0;
        try {
            for (Node<T> node = this.mReadCursor; node != this.mWriteCursor; node = node.next) {
                if (predicate.apply(node.element)) {
                    node.element = null;
                    i2++;
                }
            }
            return i2;
        } finally {
            this.mReadLock.set(0);
        }
    }

    public boolean remove(T t) {
        if (t == null) {
            return false;
        }
        int i = this.mReadLock.get();
        while (true) {
            if (i == 0 && this.mReadLock.compareAndSet(0, -1)) {
                break;
            }
            Thread.yield();
            i = this.mReadLock.get();
        }
        boolean z = false;
        Node<T> node = this.mReadCursor;
        while (true) {
            if (node == this.mWriteCursor) {
                break;
            } else if (t.equals(node.element)) {
                node.element = null;
                z = true;
                break;
            } else {
                node = node.next;
            }
        }
        this.mReadLock.set(0);
        return z;
    }
}
