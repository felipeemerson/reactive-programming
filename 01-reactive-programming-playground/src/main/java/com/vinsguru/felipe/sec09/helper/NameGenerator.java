package com.vinsguru.felipe.sec09.helper;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class NameGenerator {

    private final Set<String> redis = new HashSet<>();


    public Flux<String> generateNames() {
        return Flux.generate(sink -> {
            log.info("generating names");
            Util.sleepSeconds(1);
            var name = Util.faker().name().firstName();
            redis.add(name);
            sink.next(name);
        })
                .cast(String.class)
                .startWith(redis);
    }

}
