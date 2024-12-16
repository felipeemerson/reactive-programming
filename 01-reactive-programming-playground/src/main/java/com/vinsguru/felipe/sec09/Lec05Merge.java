package com.vinsguru.felipe.sec09;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
    Subscriber subscribes to all the producers at the same time.
    Who emits first, then subscriber receives first.
 */
@Slf4j
public class Lec05Merge {

    public static void main(String[] args) {
        demo2();

        Util.sleepSeconds(1);
    }

    private static void demo1() {
        Flux.merge(producer1(), producer2(), producer3())
                .take(2)
                .subscribe(Util.subscriber());
    }

    private static void demo2() {
        producer2()
                .mergeWith(producer3())
                .mergeWith(producer1())
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> producer1() {
        return Flux.just(1, 2, 3)
                .transform(Util.fluxLogger("producer1"))
                .delayElements(Duration.ofMillis(20));
    }

    private static Flux<Integer> producer2() {
        return Flux.just(51, 52, 53)
                .transform(Util.fluxLogger("producer2"))
                .delayElements(Duration.ofMillis(30));
    }

    private static Flux<Integer> producer3() {
        return Flux.just(11, 12, 13)
                .transform(Util.fluxLogger("producer3"))
                .delayElements(Duration.ofMillis(10));
    }

}
