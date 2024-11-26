package com.vinsguru.felipe.sec03;

import com.vinsguru.common.Util;
import reactor.core.publisher.Flux;

public class Lec05FluxRange {

    public static void main(String[] args) {
        Flux.range(0, 10).subscribe(Util.subscriber());

        // assignment - generate 10 random first names

        Flux
                .range(0, 10)
                .map(v -> Util.faker().name().firstName())
                .subscribe(Util.subscriber());
    }

}
