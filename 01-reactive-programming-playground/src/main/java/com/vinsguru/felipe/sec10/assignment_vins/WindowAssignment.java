package com.vinsguru.felipe.sec10.assignment_vins;

import com.vinsguru.common.Util;
import com.vinsguru.felipe.sec10.assignment_vins.window.FileWriter;
import reactor.core.publisher.Flux;

import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class WindowAssignment {

    public static void main(String[] args) {
        var counter = new AtomicInteger(0);
        var fileNameFormat = "src/main/resources/sec10/file%d.txt";

        eventStream()
                .window(Duration.ofMillis(1800))
                .flatMap(flux -> FileWriter.create(flux, Path.of(String.format(fileNameFormat, counter.incrementAndGet()))))
                .subscribe();

        Util.sleepSeconds(10);
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(500))
                .map(i -> "event-" + (i + 1));
    }

}
