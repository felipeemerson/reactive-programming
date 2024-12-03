package com.vinsguru.felipe.sec04;

import com.vinsguru.common.Util;
import reactor.core.publisher.Flux;

public class Lec07FluxGenerateUntil {

    public static void main(String[] args) {
//        Flux.generate(synchronousSink -> {
//            var name = Util.faker().country().name();
//            synchronousSink.next(name);
//            if (name.equals("Canada")) synchronousSink.complete();
//        }).subscribe(Util.subscriber());

        Flux.generate(synchronousSink -> {
            synchronousSink.next(Util.faker().country().name());
        })
                .takeUntil(name -> name.equals("Canada"))
                .subscribe(Util.subscriber());
    }

}
