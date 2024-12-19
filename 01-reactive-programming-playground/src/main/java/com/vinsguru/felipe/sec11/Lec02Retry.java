package com.vinsguru.felipe.sec11;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

// Retry operator simply resubscribes when it sees error signal.
@Slf4j
public class Lec02Retry {

    public static void main(String[] args) {
        demo5();

        Util.sleepSeconds(3);
    }

    private static void demo1() {
        getCountryName()
                .retry(2)
                .subscribe(Util.subscriber());
    }

    private static void demo2() {
        getCountryName()
                .retryWhen(Retry.indefinitely())
                .subscribe(Util.subscriber());
    }

    private static void demo3() {
        getCountryName()
                .retryWhen(Retry.max(2))
                .subscribe(Util.subscriber());
    }

    private static void demo4() {
        getCountryName()
                .retryWhen(
                        Retry.fixedDelay(10, Duration.ofSeconds(1))
                                .doBeforeRetry(retrySignal -> log.info("retrying {}", retrySignal.totalRetriesInARow() + 1))
                )
                .subscribe(Util.subscriber());
    }

    private static void demo5() {
        getCountryName()
                .retryWhen(
                        Retry.fixedDelay(2, Duration.ofSeconds(1))
                                .filter(ex -> RuntimeException.class.equals(ex.getClass()))
                                .onRetryExhaustedThrow((spec, signal) -> signal.failure())
                )
                .subscribe(Util.subscriber());
    }



    private static Mono<String> getCountryName() {
        var atomicInteger = new AtomicInteger(0);
        return Mono.fromSupplier(() -> {
            if (atomicInteger.incrementAndGet() < 6) {
                throw new RuntimeException("oops");
            }

            return Util.faker().country().name();
        })
                .doOnError(error -> log.info("ERROR: {}", error.getMessage()))
                .doOnSubscribe(s -> log.info("subscribing"));
    }

}
