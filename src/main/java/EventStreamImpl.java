import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * EventStreamImpl class
 */
class EventStreamImpl implements EventStream {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void consume(EventConsumer consumer) {
        observe()
                .subscribe(
                        consumer::consume,
                        e -> log.error("Error emitting event", e)
                );
    }

    private Observable<Event> observe() {
        return Observable
                .interval(1, TimeUnit.MILLISECONDS)
                .delay(x -> Observable.timer(RandomUtils.nextInt(0, 1_000), TimeUnit.MICROSECONDS))
                .map(x -> new Event(RandomUtils.nextInt(1_000, 1_100), UUID.randomUUID()))
                .flatMap(this::occasionallyDuplicate, 100)
                .observeOn(Schedulers.io());
    }

    private Observable<Event> occasionallyDuplicate(Event x) {
        final Observable<Event> event = Observable.just(x);
        if (Math.random() >= 0.01) {
            return event;
        }
        final Observable<Event> duplicated =
                event.delay(RandomUtils.nextInt(10, 5_000), TimeUnit.MILLISECONDS);
        return event.concatWith(duplicated);
    }

}
