package com.vinsguru.felipe.sec09;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

/*
    "then" could be helpful when we are not interested in the result of a publisher /
    we need to have sequential execution of asynchronous tasks.
 */
@Slf4j
public class Lec13Then {

    public static void main(String[] args) {
        var records = List.of("a", "b", "c");

        saveRecords(records)
                .then(sendNotification(records))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(2);
    }

    private static Flux<String> saveRecords(List<String> records) {
        return Flux.fromIterable(records)
                .map(record -> record + " saved!")
                .delayElements(Duration.ofMillis(500));
    }

    private static Mono<Void> sendNotification(List<String> records) {
        return Mono.fromRunnable(() -> log.info("all these {} records saved successfully", records));
    }

}
