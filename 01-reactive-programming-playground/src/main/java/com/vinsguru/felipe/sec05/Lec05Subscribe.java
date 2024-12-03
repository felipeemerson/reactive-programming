package com.vinsguru.felipe.sec05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec05Subscribe {

    private static final Logger log = LoggerFactory.getLogger(Lec05Subscribe.class);

    public static void main(String[] args) {
        Flux.range(1, 10)
                .doOnNext(value -> log.info("received: {}", value))
                .doOnError(error -> log.error("error", error))
                .doOnComplete(() -> log.info("completed"))
                .subscribe();
    }

}
