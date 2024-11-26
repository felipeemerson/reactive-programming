package com.vinsguru.felipe.sec03;

import com.vinsguru.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

public class Lec04FluxFromStream {

    public static void main(String[] args) {
        var list = List.of(1, 2, 3);
        var flux = Flux.fromStream(list.stream());

        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));

        flux = Flux.fromStream(list::stream);

        flux.subscribe(Util.subscriber("sub3"));
        flux.subscribe(Util.subscriber("sub4"));
    }

}
