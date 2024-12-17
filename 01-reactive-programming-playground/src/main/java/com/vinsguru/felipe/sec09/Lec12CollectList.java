package com.vinsguru.felipe.sec09;

import com.vinsguru.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// To collect the items received via Flux. Assuming we will have finite items.
public class Lec12CollectList {

    public static void main(String[] args) {
        Flux.range(1, 10)
                .collectList()
                .subscribe(Util.subscriber());
    }

}
