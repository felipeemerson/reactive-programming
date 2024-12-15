package com.vinsguru.felipe.sec06;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class Lec02HotPublisher {

    public static void main(String[] args) {
        var movieFlux = movieStream().share();

        Util.sleepSeconds(2);

        movieFlux
                .take(1)
                .subscribe(Util.subscriber("sam"));

        Util.sleepSeconds(3);

        movieFlux
                .take(3)
                .subscribe(Util.subscriber("mike"));

        Util.sleepSeconds(15);
    }

    // movie theater
    private static Flux<String> movieStream() {
        return Flux.generate(
                        () -> {
                            log.info("received the request");
                            return 1;
                        },
                        (state, sink) -> {
                            var scene = "movie scene " + state;
                            log.info("playing the scene {}", scene);
                            sink.next(scene);
                            return ++state;
                        })
                    .take(10)
                    .delayElements(Duration.ofSeconds(1))
                    .cast(String.class);
    }

}
