package com.vinsguru.felipe.sec13;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class Lec01Context {

    public static void main(String[] args) {
        getWelcomeMessage()
                //.contextWrite(Context.of("user", "felipe"))
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

}
