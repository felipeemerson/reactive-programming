package com.vinsguru.felipe.sec12;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@Slf4j
public class Lec05MulticastDirectBestEffort {

    public static void main(String[] args) {
        demo3();

        Util.sleepSeconds(1);
    }

    private static void demo1() {
        System.setProperty("reactor.bufferSize.small", "16");

        // handle through which we would push items
        // onBackpressureBuffer - bounded queue
        var sink = Sinks.many().multicast().onBackpressureBuffer();
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sub1"));
        flux.delayElements(Duration.ofMillis(200))
                .subscribe(Util.subscriber("sub2"));

        for (int i = 1; i <= 100; i++) {
            var result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }
    }

    private static void demo2() {
        System.setProperty("reactor.bufferSize.small", "16");

        // DirectBestEffort - so slower subscriber doesn't affect the fast subscriber
        // the fast subscriber will receive all messages, while slower subscriber will receive
        // the messages sent when he can consume, so it will not receive all messages
        var sink = Sinks.many().multicast().directBestEffort();
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sub1"));
        flux.delayElements(Duration.ofMillis(200))
                .subscribe(Util.subscriber("sub2"));

        for (int i = 1; i <= 100; i++) {
            var result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }
    }

    private static void demo3() {
        System.setProperty("reactor.bufferSize.small", "16");

        // DirectBestEffort - so slower subscriber doesn't affect the fast subscriber
        // the fast subscriber will receive all messages, while slower subscriber will receive
        // the messages sent when he can consume, so it will not receive all messages
        var sink = Sinks.many().multicast().directBestEffort();
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sub1"));
        flux.onBackpressureBuffer() // save messages to buffer and process then later
                .delayElements(Duration.ofMillis(200))
                .subscribe(Util.subscriber("sub2"));

        for (int i = 1; i <= 100; i++) {
            var result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }
    }

}
