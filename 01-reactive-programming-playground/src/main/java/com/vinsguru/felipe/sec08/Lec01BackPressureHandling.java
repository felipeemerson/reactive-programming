package com.vinsguru.felipe.sec08;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
    Reactor automatically handles the backpressure.
    System.setProperty("reactor.bufferSize.small", "16");
 */
@Slf4j
public class Lec01BackPressureHandling {

    public static void main(String[] args) {
        System.setProperty("reactor.bufferSize.small", "16");

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
                .publishOn(Schedulers.boundedElastic())
                .map(Lec01BackPressureHandling::timeConsumingTask)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(20);
    }

    private static int timeConsumingTask(int i) {
        Util.sleepSeconds(1);
        return i;
    }

}
