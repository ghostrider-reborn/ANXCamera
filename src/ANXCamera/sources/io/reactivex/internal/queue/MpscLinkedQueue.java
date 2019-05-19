package io.reactivex.internal.queue;

import io.reactivex.annotations.Nullable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import java.util.concurrent.atomic.AtomicReference;

public final class MpscLinkedQueue<T> implements SimplePlainQueue<T> {
    private final AtomicReference<LinkedQueueNode<T>> consumerNode = new AtomicReference<>();
    private final AtomicReference<LinkedQueueNode<T>> producerNode = new AtomicReference<>();

    static final class LinkedQueueNode<E> extends AtomicReference<LinkedQueueNode<E>> {
        private static final long serialVersionUID = 2404266111789071508L;
        private E value;

        LinkedQueueNode() {
        }

        LinkedQueueNode(E e) {
            spValue(e);
        }

        public E getAndNullValue() {
            E lpValue = lpValue();
            spValue(null);
            return lpValue;
        }

        public E lpValue() {
            return this.value;
        }

        public LinkedQueueNode<E> lvNext() {
            return (LinkedQueueNode) get();
        }

        public void soNext(LinkedQueueNode<E> linkedQueueNode) {
            lazySet(linkedQueueNode);
        }

        public void spValue(E e) {
            this.value = e;
        }
    }

    public MpscLinkedQueue() {
        LinkedQueueNode linkedQueueNode = new LinkedQueueNode();
        spConsumerNode(linkedQueueNode);
        xchgProducerNode(linkedQueueNode);
    }

    public void clear() {
        while (poll() != null && !isEmpty()) {
        }
    }

    public boolean isEmpty() {
        return lvConsumerNode() == lvProducerNode();
    }

    /* access modifiers changed from: 0000 */
    public LinkedQueueNode<T> lpConsumerNode() {
        return (LinkedQueueNode) this.consumerNode.get();
    }

    /* access modifiers changed from: 0000 */
    public LinkedQueueNode<T> lvConsumerNode() {
        return (LinkedQueueNode) this.consumerNode.get();
    }

    /* access modifiers changed from: 0000 */
    public LinkedQueueNode<T> lvProducerNode() {
        return (LinkedQueueNode) this.producerNode.get();
    }

    public boolean offer(T t) {
        if (t != null) {
            LinkedQueueNode linkedQueueNode = new LinkedQueueNode(t);
            xchgProducerNode(linkedQueueNode).soNext(linkedQueueNode);
            return true;
        }
        throw new NullPointerException("Null is not a valid element");
    }

    public boolean offer(T t, T t2) {
        offer(t);
        offer(t2);
        return true;
    }

    @Nullable
    public T poll() {
        LinkedQueueNode lpConsumerNode = lpConsumerNode();
        LinkedQueueNode lvNext = lpConsumerNode.lvNext();
        if (lvNext != null) {
            T andNullValue = lvNext.getAndNullValue();
            spConsumerNode(lvNext);
            return andNullValue;
        } else if (lpConsumerNode == lvProducerNode()) {
            return null;
        } else {
            while (true) {
                LinkedQueueNode lvNext2 = lpConsumerNode.lvNext();
                if (lvNext2 != null) {
                    T andNullValue2 = lvNext2.getAndNullValue();
                    spConsumerNode(lvNext2);
                    return andNullValue2;
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void spConsumerNode(LinkedQueueNode<T> linkedQueueNode) {
        this.consumerNode.lazySet(linkedQueueNode);
    }

    /* access modifiers changed from: 0000 */
    public LinkedQueueNode<T> xchgProducerNode(LinkedQueueNode<T> linkedQueueNode) {
        return (LinkedQueueNode) this.producerNode.getAndSet(linkedQueueNode);
    }
}