package com.sumauto.data;

import org.junit.Test;

import java.util.concurrent.Callable;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class RxTest {
    @Test
    public void addition_isCorrect() throws InterruptedException {



        Disposable hello = Flowable.just("Hello").subscribe(System.out::println);

        Flowable<String> source = Flowable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(800);
                return "Done";
            }
        });

        Flowable<String> ioFlow = source.subscribeOn(Schedulers.io());
        Flowable<String> observeOn = ioFlow.observeOn(Schedulers.single());
        observeOn.subscribe(System.out::println, Throwable::printStackTrace);
        Thread.sleep(900);
        testFlow1();
        testObserve1();
    }

    private void testObserve1() {
        Observable<Object> objectObservable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                while (!emitter.isDisposed()) {
                    long time = System.currentTimeMillis();
                    emitter.onNext(time);
                    if (time % 2 != 0) {
                        emitter.onError(new IllegalStateException("Odd millisecond!"));
                        break;
                    }
                }
            }
        });
        objectObservable.subscribe(System.out::println, Throwable::printStackTrace);
    }

    private void testFlow1() {
        Flowable<Integer> filter = Flowable.range(1, 20).map(integer -> integer * integer).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Throwable {

                return integer % 3 == 0;
            }
        });
        subscribePrint(filter);

    }

    private <T> void subscribePrint(Flowable<T> flowable) {
        flowable.subscribe(System.out::println);
    }

    private <T> void subscribePrintThrow(Flowable<T> flowable) {
        flowable.subscribe(System.out::println, Throwable::printStackTrace);
    }
}