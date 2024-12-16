package com.vinsguru.felipe.sec09;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

// Calls multiple publishers in a specific order
@Slf4j
public class Lec01StartWith {

    public static void main(String[] args) {
        demo4();

        Util.sleepSeconds(1);
    }

    private static void demo1() {
        producer1()
                .startWith(-1, 0)
                .take(2)
                .subscribe(Util.subscriber());
    }

    private static void demo2() {
        producer1()
                .startWith(List.of(-2, -1, 0))
                .take(2)
                .subscribe(Util.subscriber());
    }

    private static void demo3() {
        producer1()
                .startWith(producer2())
                .subscribe(Util.subscriber());
    }

    private static void demo4() {
        producer1()
                .startWith(0)
                .startWith(producer2())
                .startWith(49, 50)
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> producer1() {
        return Flux.just(1, 2, 3)
                .doOnSubscribe(s -> log.info("subscribing to producer1"))
                .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer2() {
        return Flux.just(51, 52, 53)
                .doOnSubscribe(s -> log.info("subscribing to producer2"))
                .delayElements(Duration.ofMillis(10));
    }

}
