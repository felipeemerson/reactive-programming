package com.vinsguru.felipe.sec03;

import com.vinsguru.common.Util;
import reactor.core.publisher.Flux;

public class Lec10FluxEmptyError {

    public static void main(String[] args) {
        Flux.empty()
                .subscribe(Util.subscriber());

        Flux.error(new RuntimeException("invalid"))
                .subscribe(Util.subscriber());
    }

}
