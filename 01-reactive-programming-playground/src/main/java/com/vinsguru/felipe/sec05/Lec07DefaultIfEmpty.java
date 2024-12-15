package com.vinsguru.felipe.sec05;

import com.vinsguru.common.Util;
import reactor.core.publisher.Mono;

public class Lec07DefaultIfEmpty {

    public static void main(String[] args) {
        Mono.just("felip")
                .defaultIfEmpty("fallback")
                .subscribe(Util.subscriber());
    }

}
