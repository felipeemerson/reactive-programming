package com.vinsguru.felipe.sec08;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
    Reactor automatically handles the backpressure.
    We can also adjust the limit.
 */
@Slf4j
public class Lec02LimitRate {

    public static void main(String[] args) {
        var producer = Flux.generate(
                () -> 1,
                (state, sink) -> {
                    log.info("generating {}", state);
                    sink.next(state);
                    return ++state;
                }
        ).subscribeOn(Schedulers.parallel());

        producer
                .cast(Integer.class)
                .limitRate(1)
                .publishOn(Schedulers.boundedElastic())
                .map(Lec02LimitRate::timeConsumingTask)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(20);
    }

    private static int timeConsumingTask(int i) {
        log.info("{}", i);
        Util.sleepSeconds(1);
        return i;
    }

}
