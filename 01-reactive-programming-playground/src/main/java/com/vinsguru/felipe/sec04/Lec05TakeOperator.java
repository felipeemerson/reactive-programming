package com.vinsguru.felipe.sec04;

import com.vinsguru.common.Util;
import reactor.core.publisher.Flux;

import java.util.stream.IntStream;

public class Lec05TakeOperator {

    public static void main(String[] args) {
        takeUntil();
    }

    private static void take() {
        Flux.range(1, 10)
                .log("take")
                .take(3)
                .log("sub")
                .subscribe(Util.subscriber());
    }

    private static void takeWhile() {
        Flux.range(1, 10)
                .log("take")
                .takeWhile(n -> n < 6) // stop when the condition is not met
                .log("sub")
                .subscribe(Util.subscriber());
    }

    private static void takeUntil() {
        Flux.range(1, 10)
                .log("take")
                .takeUntil(n -> n < 6) // stop when the condition is met + allow the last item
                .log("sub")
                .subscribe(Util.subscriber());
    }

}
