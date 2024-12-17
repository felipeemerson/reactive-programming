package com.vinsguru.felipe.sec10.assignment;

import com.vinsguru.common.Util;
import com.vinsguru.felipe.sec10.Lec02Window;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class WindowAssignment {

    private static Integer fileCount = 1;

    public static void main(String[] args) {
        eventStream()
                .window(Duration.ofMillis(1800))
                .flatMap(WindowAssignment::processEvents)
                .subscribe();

        Util.sleepSeconds(10);
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(500))
                .map(i -> "event-" + (i + 1));
    }

    private static Mono<Void> processEvents(Flux<String> flux) {
        Path path = Paths.get("src/main/resources/sec04/");
        String filename = String.format("file-%s.txt", fileCount);

        return flux.doOnNext(event -> writeToFile(path.resolve(filename)))
                .doOnComplete(() -> ++fileCount)
                .then();
    }

    private static void writeToFile(Path path) {
        try {            Files.writeString(path, "*");
        } catch (IOException e) {
            throw new RuntimeException("oops", e);
        }
    }

}
