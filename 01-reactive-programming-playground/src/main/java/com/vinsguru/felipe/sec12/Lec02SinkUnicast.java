package com.vinsguru.felipe.sec12;

import com.vinsguru.common.Util;
import reactor.core.publisher.Sinks;

// We can emit multiple messages, but there will be only one subscriber.
public class Lec02SinkUnicast {

    public static void main(String[] args) {
        demo2();
    }

    private static void demo1() {
        // handle through which we would push items
        // onBackpressureBuffer - unbounded queue
        var sink = Sinks.many().unicast().onBackpressureBuffer();
        var flux = sink.asFlux();

        for (int i = 0; i < 5; i++) {
            sink.tryEmitNext(Util.faker().internet().domainName());
        }

        sink.tryEmitComplete();

        flux.subscribe(Util.subscriber());
    }

    private static void demo2() {
        // handle through which we would push items
        // onBackpressureBuffer - unbounded queue
        var sink = Sinks.many().unicast().onBackpressureBuffer();
        var flux = sink.asFlux();

        for (int i = 0; i < 5; i++) {
            sink.tryEmitNext(Util.faker().internet().domainName());
        }

        sink.tryEmitComplete();

        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));
    }

}
