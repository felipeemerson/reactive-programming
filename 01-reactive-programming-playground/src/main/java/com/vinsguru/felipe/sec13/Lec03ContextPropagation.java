package com.vinsguru.felipe.sec13;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

@Slf4j
public class Lec03ContextPropagation {

    public static void main(String[] args) {
        getWelcomeMessage()
                .concatWith(Flux.merge(producer1(), producer2().contextWrite(ctx -> Context.empty())))
                .contextWrite(Context.of("user", "felipe"))
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getWelcomeMessage() {
        return Mono.deferContextual(ctx -> {
            if (ctx.hasKey("user")) {
                return Mono.just("Welcome %s".formatted(ctx.<String>get("user")));
            }

            return Mono.error(new RuntimeException("unauthenticated"));
        });
    }

    private static Mono<String> producer1() {
        return Mono.<String>deferContextual(ctx -> {
            log.info("producer1: {}", ctx);
            return Mono.empty();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private static Mono<String> producer2() {
        return Mono.<String>deferContextual(ctx -> {
            log.info("producer2: {}", ctx);
            return Mono.empty();
        }).subscribeOn(Schedulers.parallel());
    }

}
