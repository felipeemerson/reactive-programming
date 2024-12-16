package com.vinsguru.felipe.sec09;

import com.vinsguru.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
    Zip
    - we will subscribe to all the producers at the same time
    - all or nothing (only returns combined items)
    - all producers will have to emit (at least) an item
 */
public class Lec07Zip {

    private record Car(String body, String engine, String tires){}

    public static void main(String[] args) {
        Flux.zip(
                getBody(),
                getEngine(),
                getTires()
        )
                .map(tuple -> new Car(tuple.getT1(), tuple.getT2(), tuple.getT3()))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(1);
    }

    private static Flux<String> getBody() {
        return Flux.range(1, 5)
                .map(i -> "body-" + i)
                .delayElements(Duration.ofMillis(100));
    }

    private static Flux<String> getEngine() {
        return Flux.range(1, 3)
                .map(i -> "engine-" + i)
                .delayElements(Duration.ofMillis(200));
    }

    private static Flux<String> getTires() {
        return Flux.range(1, 10)
                .map(i -> "tires-" + i)
                .delayElements(Duration.ofMillis(75));
    }

    private static Flux<Integer> producer4() {
        return Flux.range(1, 10);
    }

}
