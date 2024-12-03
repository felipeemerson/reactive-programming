package com.vinsguru.felipe.sec04;

import com.vinsguru.felipe.common.Util;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class Lec08GenerateWithState {

    public static void main(String[] args) {
        demo2();
    }

    private static void demo1() {
        AtomicInteger counter = new AtomicInteger(0);

        Flux.generate(synchronousSink -> {
            var name = Util.faker().country().name();
            synchronousSink.next(name);
            counter.incrementAndGet();

            if (counter.get() == 10 || name.equals("Canada")) synchronousSink.complete();
        }).subscribe(Util.subscriber());
    }

    private static void demo2() {
        Flux.generate(
                () -> 0,
                (counter, synchronousSink) -> {
                    var name = Util.faker().country().name();
                    synchronousSink.next(name);
                    counter++;

                    if (counter == 10 || name.equals("Canada")) synchronousSink.complete();

                    return counter;
                }
        ).subscribe(Util.subscriber());
    }

}
