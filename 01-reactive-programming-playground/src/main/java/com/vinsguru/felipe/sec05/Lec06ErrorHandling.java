package com.vinsguru.felipe.sec05;

import com.vinsguru.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec06ErrorHandling {

    private static final Logger log = LoggerFactory.getLogger(Lec05Subscribe.class);

    public static void main(String[] args) {
        onErrorContinue();
    }

    private static void onErrorReturn() {
        Flux.range(1, 10)
                .map(i -> i == 5 ? 5/0 : i)
                .onErrorReturn(IllegalArgumentException.class, -1)
                .onErrorReturn(ArithmeticException.class, -2)
                .onErrorReturn(-3)
                .subscribe(Util.subscriber());
    }

    private static void onErrorResume() {
        Mono.error(new RuntimeException())
                //.map(i -> i == 5 ? 5/0 : i)
                .onErrorResume(ArithmeticException.class, ex -> fallback1())
                .onErrorResume(ex -> fallback2())
                .onErrorReturn(-5)
                .subscribe(Util.subscriber());
    }

    private static void onErrorContinue() {
        Flux.range(1, 10)
                .map(i -> i == 5 ? 5/0 : i)
                .onErrorContinue((ex, obj) -> log.error("==> {}", obj, ex))
                .subscribe(Util.subscriber());
    }

    private static void onErrorComplete() {
        Mono.error(new RuntimeException("oops"))
                .onErrorComplete()
                .subscribe(Util.subscriber());
    }

    private static Mono<Integer> fallback1() {
        return Mono.fromSupplier(() -> Util.faker().random().nextInt(10, 100));
    }

//    private static Mono<Integer> fallback2() {
//        return Mono.fromSupplier(() -> Util.faker().random().nextInt(100, 1000));
//    }

    private static Mono<Integer> fallback2() {
        return Mono.just(1);
    }

}
