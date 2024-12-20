package com.vinsguru.felipe.sec12;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@Slf4j
public class Lec06MulticastDirectAllOrNothing {

    public static void main(String[] args) {
        demo1();

        Util.sleepSeconds(2);
    }

    private static void demo1() {
        System.setProperty("reactor.bufferSize.small", "16");

        // if one subscriber is slow, then doesn't send messages to anyone
        var sink = Sinks.many().multicast().directAllOrNothing();
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sub1"));
        flux.delayElements(Duration.ofMillis(200))
                .subscribe(Util.subscriber("sub2"));

        for (int i = 1; i <= 100; i++) {
            var result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }
    }

}
