package com.vinsguru.felipe.sec03;

import com.vinsguru.common.Util;
import reactor.core.publisher.Flux;

public class Lec02MultipleSubscribers {

    public static void main(String[] args) {
        var flux = Flux.just(1, 2, 3, 4, 5, 6);

        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));

        flux.filter(v -> v > 3).subscribe(Util.subscriber("sub1"));
    }

}
