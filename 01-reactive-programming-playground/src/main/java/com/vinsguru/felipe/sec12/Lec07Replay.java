package com.vinsguru.felipe.sec12;

import com.vinsguru.common.Util;
import reactor.core.publisher.Sinks;

import java.time.Duration;

public class Lec07Replay {

    public static void main(String[] args) {
        demo3();
    }

    private static void demo1() {
        // handle through which we would push items
        // unbounded queue
        // all to replay all messages
        var sink = Sinks.many().replay().all();
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));

        for (int i = 0; i < 5; i++) {
            sink.tryEmitNext(Util.faker().internet().domainName());
        }

        sink.tryEmitComplete();

        Util.sleepSeconds(1);

        flux.subscribe(Util.subscriber("sub3"));
    }

    private static void demo2() {
        // handle through which we would push items
        // unbounded queue
        // limit to only store the n latest messages
        var sink = Sinks.many().replay().limit(1);
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));

        for (int i = 0; i < 5; i++) {
            sink.tryEmitNext(Util.faker().internet().domainName());
        }

        sink.tryEmitComplete();

        Util.sleepSeconds(1);

        flux.subscribe(Util.subscriber("sub3"));
    }

    private static void demo3() {
        // handle through which we would push items
        // unbounded queue
        // limit to only store the n latest messages that were sent on lasts 500 ms
        var sink = Sinks.many().replay().limit(2, Duration.ofMillis(500));
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));

        for (int i = 0; i < 5; i++) {
            sink.tryEmitNext(Util.faker().internet().domainName());
        }

        Util.sleepSeconds(1);

        sink.tryEmitNext("later message 1");
        sink.tryEmitNext("later message 2");
        sink.tryEmitNext("later message 3");

        flux.subscribe(Util.subscriber("sub3"));
    }

}
