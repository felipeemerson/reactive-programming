package com.vinsguru.felipe.sec07;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class Lec01DefaultBehaviorDemo {

    public static void main(String[] args) {
        var flux = Flux.create(sink -> {
                            for (int i = 1; i < 3; i++) {
                                log.info("generating: {}", i);
                                sink.next(i);
                            }

                            sink.complete();
                        })
                        .doOnNext(v -> log.info("value: {}", v));

        Runnable runnable = () -> flux.subscribe(Util.subscriber("sub1"));

        Thread.ofPlatform().start(runnable);
    }

}
