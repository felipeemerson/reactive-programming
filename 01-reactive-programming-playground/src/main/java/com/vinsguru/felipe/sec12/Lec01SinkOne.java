package com.vinsguru.felipe.sec12;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Sinks;

@Slf4j
public class Lec01SinkOne {

    public static void main(String[] args) {
        demo5();
    }

    private static void demo1() {
        var sink = Sinks.one();
        var mono = sink.asMono();
        mono.subscribe(Util.subscriber());
        sink.tryEmitValue("hi");
    }

    private static void demo2() {
        var sink = Sinks.one();
        var mono = sink.asMono();
        mono.subscribe(Util.subscriber());
        sink.tryEmitEmpty();
    }

    private static void demo3() {
        var sink = Sinks.one();
        var mono = sink.asMono();
        mono.subscribe(Util.subscriber());
        sink.tryEmitError(new RuntimeException("oops"));
    }

    private static void demo4() {
        var sink = Sinks.one();
        var mono = sink.asMono();
        mono.subscribe(Util.subscriber("sub1"));
        mono.subscribe(Util.subscriber("sub2"));
        sink.tryEmitValue("hello");
    }

    private static void demo5() {
        var sink = Sinks.one();
        var mono = sink.asMono();

        // doesn't print anything as there is no error
        sink.emitValue("hi", (signalType, emitResult) -> {
            log.info("hi");
            log.info(signalType.name());
            log.info(emitResult.name());

            return false;
        });

        // as this is sink one, it only can emit one item, while trying to emit a second
        // item, then it will throw an error
        sink.emitValue("hi", (signalType, emitResult) -> {
            log.info("hello");
            log.info(signalType.name());
            log.info(emitResult.name());

            return false; // boolean flag for retry, false for no retry
        });

        mono.subscribe(Util.subscriber());
    }

}
