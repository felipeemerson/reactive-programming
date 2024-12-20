package com.vinsguru.felipe.sec12;

import com.vinsguru.common.Util;
import reactor.core.publisher.Sinks;

public class Lec04Multicast {

    public static void main(String[] args) {
        demo2();
    }

    private static void demo1() {
        // handle through which we would push items
        // onBackpressureBuffer - bounded queue
        var sink = Sinks.many().multicast().onBackpressureBuffer();
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

    // warm up behavior
    private static void demo2() {
        // handle through which we would push items
        // onBackpressureBuffer - bounded queue
        var sink = Sinks.many().multicast().onBackpressureBuffer();
        var flux = sink.asFlux();

        for (int i = 0; i < 5; i++) {
            sink.tryEmitNext(Util.faker().internet().domainName());
        }

        Util.sleepSeconds(2);

        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));

        sink.tryEmitNext("new message");
    }

}
