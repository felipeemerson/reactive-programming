package com.vinsguru.felipe.sec12;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class Lec03SinkThreadSafety {

    public static void main(String[] args) {
        demo2();
    }

    private static void demo1() {
        // handle through which we would push items
        // onBackpressureBuffer - unbounded queue
        var sink = Sinks.many().unicast().onBackpressureBuffer();
        var flux = sink.asFlux();

        var list = new ArrayList<>();
        flux.subscribe(list::add);

        for (int i = 0; i < 1000; i++) {
            var j = i;
            CompletableFuture.runAsync(() -> {
                log.info("emitting {}", j);
                sink.tryEmitNext(j);
            });
        }

        Util.sleepSeconds(2);

        log.info("list size: {}", list.size());
    }

    private static void demo2() {
        // handle through which we would push items
        // onBackpressureBuffer - unbounded queue
        var sink = Sinks.many().unicast().onBackpressureBuffer();
        var flux = sink.asFlux();

        var list = new ArrayList<>();
        flux.subscribe(list::add);

        for (int i = 0; i < 1000; i++) {
            var j = i;
            CompletableFuture.runAsync(() -> {
                log.info("emitting {}", j);
                sink.emitNext(j, (signalType, emitResult) -> Sinks.EmitResult.FAIL_NON_SERIALIZED.equals(emitResult));
            });
        }

        Util.sleepSeconds(2);

        log.info("list size: {}", list.size());
    }

}
