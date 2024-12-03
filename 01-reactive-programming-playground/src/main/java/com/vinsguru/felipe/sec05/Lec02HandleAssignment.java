package com.vinsguru.felipe.sec05;

import com.vinsguru.common.Util;
import reactor.core.publisher.Flux;

public class Lec02HandleAssignment {

    public static void main(String[] args) {
        Flux.<String>generate(synchronousSink -> {
            synchronousSink.next(Util.faker().country().name());
        }).<String>handle((item, sink) -> {
            sink.next(item);

            if (item.equals("Canada")) {
                sink.complete();
            }
        }).subscribe(Util.subscriber());
    }

}
