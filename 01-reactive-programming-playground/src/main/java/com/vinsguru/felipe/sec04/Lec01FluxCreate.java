package com.vinsguru.felipe.sec04;

import com.vinsguru.common.Util;
import reactor.core.publisher.Flux;

public class Lec01FluxCreate {

    public static void main(String[] args) {
        Flux.create(fluxSink -> {
            fluxSink.next(1);
            fluxSink.next(2);
            fluxSink.complete();
        }).subscribe(Util.subscriber());

//        Flux.create(fluxSink -> {
//            for (int i = 0; i < 10; i++) {
//                fluxSink.next(Util.faker().country().name());
//            }
//
//            fluxSink.complete();
//        }).subscribe(Util.subscriber());

        Flux.create(fluxSink -> {
            while (true) {
                String countryName = Util.faker().country().name();
                fluxSink.next(countryName);

                if (countryName.equals("Brazil")) break;
            }

            fluxSink.complete();
        }).subscribe(Util.subscriber());


    }

}
