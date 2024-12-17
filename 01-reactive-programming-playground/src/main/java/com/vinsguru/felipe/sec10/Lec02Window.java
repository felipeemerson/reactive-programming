package com.vinsguru.felipe.sec10;

import com.vinsguru.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Lec02Window {

    public static void main(String[] args) {
        eventStream()
                .window(Duration.ofMillis(1800))
                .flatMap(Lec02Window::processEvents)
                .subscribe();

        Util.sleepSeconds(5);
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(500))
                .map(i -> "event-" + (i + 1));
    }

    private static Mono<Void> processEvents(Flux<String> flux) {
        return flux.doOnNext(event -> System.out.print("*"))
                .doOnComplete(System.out::println)
                .then();
    }

}
