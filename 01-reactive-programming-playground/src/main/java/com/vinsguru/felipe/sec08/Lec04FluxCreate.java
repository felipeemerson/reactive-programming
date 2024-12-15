package com.vinsguru.felipe.sec08;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/*
    Flux.generate generates items on demand of the downstream
    Flux.create generates all items at once. Hard to handle backpressure.
 */
@Slf4j
public class Lec04FluxCreate {

    public static void main(String[] args) {
        System.setProperty("reactor.bufferSize.small", "16");

        var producer = Flux.create(sink -> {
                    for (int i = 1; i < 500 && !sink.isCancelled(); i++) {
                        log.info("generating {}", i);
                        sink.next(i);
                        Util.sleep(Duration.ofMillis(50));
                    }

                    sink.complete();
                })
                .subscribeOn(Schedulers.parallel());

        producer
                .cast(Integer.class)
                .publishOn(Schedulers.boundedElastic())
                .map(Lec04FluxCreate::timeConsumingTask)
                .subscribe();

        Util.sleepSeconds(20);
    }

    private static int timeConsumingTask(int i) {
        log.info("received: {}", i);
        Util.sleepSeconds(1);
        return i;
    }

}
