package miui.util.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import miui.util.concurrent.Queue;

public class ConcurrentRingQueue<T> implements Queue<T> {
    private int VO;
    private final boolean VP;
    private final boolean VQ;
    private final AtomicInteger VR = new AtomicInteger(0);
    private volatile Node<T> VS = new Node<>();
    private final AtomicInteger VT = new AtomicInteger(0);
    private volatile Node<T> VV = this.VS;
    private volatile int VW;

    private static class Node<T> {
        Node<T> VX;
        T element;

        private Node() {
        }
    }

    public ConcurrentRingQueue(int i, boolean z, boolean z2) {
        this.VO = i;
        this.VP = z;
        this.VQ = z2;
        Node<T> node = this.VS;
        for (int i2 = 0; i2 < i; i2++) {
            node.VX = new Node<>();
            node = node.VX;
        }
        node.VX = this.VS;
    }

    public boolean put(T t) {
        if (t == null) {
            return false;
        }
        while (true) {
            if (this.VT.get() == 0 && this.VT.compareAndSet(0, -1)) {
                break;
            }
            Thread.yield();
        }
        Node<T> node = this.VS;
        Node<T> node2 = this.VV;
        int i = this.VW;
        boolean z = true;
        if (node2.VX != node) {
            node2.element = t;
            if (node2.VX.VX != node && this.VQ && i > 0) {
                node2.VX = node2.VX.VX;
                this.VW = i - 1;
            }
            this.VV = node2.VX;
        } else if (this.VP || i < 0) {
            node2.VX = new Node<>();
            node2.VX.VX = node;
            node2.element = t;
            this.VW = i + 1;
            this.VV = node2.VX;
        } else {
            z = false;
        }
        this.VT.set(0);
        return z;
    }

    public T get() {
        while (true) {
            if (this.VR.get() == 0 && this.VR.compareAndSet(0, -1)) {
                break;
            }
            Thread.yield();
        }
        Node<T> node = this.VS;
        Node<T> node2 = this.VV;
        Node<T> node3 = node;
        T t = null;
        while (t == null && node3 != node2) {
            t = node3.element;
            node3.element = null;
            node3 = node3.VX;
            node2 = this.VV;
        }
        if (t != null) {
            this.VS = node3;
        }
        this.VR.set(0);
        return t;
    }

    public boolean remove(T t) {
        boolean z;
        if (t == null) {
            return false;
        }
        while (true) {
            if (this.VR.get() == 0 && this.VR.compareAndSet(0, -1)) {
                break;
            }
            Thread.yield();
        }
        Node<T> node = this.VS;
        while (true) {
            if (node == this.VV) {
                z = false;
                break;
            } else if (t.equals(node.element)) {
                node.element = null;
                z = true;
                break;
            } else {
                node = node.VX;
            }
        }
        this.VR.set(0);
        return z;
    }

    public int remove(Queue.Predicate<T> predicate) {
        if (predicate == null) {
            return 0;
        }
        while (true) {
            if (this.VR.get() != 0 || !this.VR.compareAndSet(0, -1)) {
                Thread.yield();
            } else {
                try {
                    break;
                } finally {
                    this.VR.set(0);
                }
            }
        }
        int i = 0;
        for (Node<T> node = this.VS; node != this.VV; node = node.VX) {
            if (predicate.apply(node.element)) {
                node.element = null;
                i++;
            }
        }
        return i;
    }

    public int clear() {
        while (true) {
            if (this.VR.get() == 0 && this.VR.compareAndSet(0, -1)) {
                break;
            }
            Thread.yield();
        }
        Node<T> node = this.VS;
        int i = 0;
        while (node != this.VV) {
            node.element = null;
            i++;
            node = node.VX;
        }
        this.VS = node;
        this.VR.set(0);
        return i;
    }

    public boolean isEmpty() {
        return this.VV == this.VS;
    }

    public int getCapacity() {
        int i = this.VW;
        return i > 0 ? this.VO + i : this.VO;
    }

    public void increaseCapacity(int i) {
        if (!this.VP && i > 0) {
            while (true) {
                if (this.VT.get() != 0 || !this.VT.compareAndSet(0, -1)) {
                    Thread.yield();
                } else {
                    this.VW = -i;
                    this.VO += i;
                    this.VT.set(0);
                    return;
                }
            }
        }
    }

    public void decreaseCapacity(int i) {
        if (this.VQ && i > 0) {
            while (true) {
                if (this.VT.get() != 0 || !this.VT.compareAndSet(0, -1)) {
                    Thread.yield();
                } else {
                    this.VO -= i;
                    this.VW = i;
                    this.VT.set(0);
                    return;
                }
            }
        }
    }
}
