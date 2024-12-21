package com.vinsguru.felipe.sec13;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

// Context is an immutable map. We can append additional info!
@Slf4j
public class Lec02ContextAppendUpdate {

    public static void main(String[] args) {
        update();
    }

    private static void append() {
        getWelcomeMessage()
                .contextWrite(Context.of("a", "b").put("c", "d").put("e", "f"))
                .contextWrite(Context.of("user", "felipe"))
                .subscribe(Util.subscriber());
    }

    private static void update() {
        getWelcomeMessage()
                .contextWrite(ctx -> Context.of("user", "mike"))
                .contextWrite(Context.of("a", "b").put("c", "d").put("e", "f"))
                .contextWrite(Context.of("user", "felipe"))
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getWelcomeMessage() {
        return Mono.deferContextual(ctx -> {
            log.info("{}", ctx);
            if (ctx.hasKey("user")) {
                return Mono.just("Welcome %s".formatted(ctx.<String>get("user")));
            }

            return Mono.error(new RuntimeException("unauthenticated"));
        });
    }

}
