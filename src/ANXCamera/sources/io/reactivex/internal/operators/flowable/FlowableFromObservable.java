package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableFromObservable<T> extends Flowable<T> {
    private final Observable<T> upstream;

    static class SubscriberObserver<T> implements Observer<T>, Subscription {

        /* renamed from: d  reason: collision with root package name */
        private Disposable f277d;
        private final Subscriber<? super T> s;

        SubscriberObserver(Subscriber<? super T> subscriber) {
            this.s = subscriber;
        }

        public void cancel() {
            this.f277d.dispose();
        }

        public void onComplete() {
            this.s.onComplete();
        }

        public void onError(Throwable th) {
            this.s.onError(th);
        }

        public void onNext(T t) {
            this.s.onNext(t);
        }

        public void onSubscribe(Disposable disposable) {
            this.f277d = disposable;
            this.s.onSubscribe(this);
        }

        public void request(long j) {
        }
    }

    public FlowableFromObservable(Observable<T> observable) {
        this.upstream = observable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.upstream.subscribe(new SubscriberObserver(subscriber));
    }
}
