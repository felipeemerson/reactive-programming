package com.vinsguru.felipe.sec03;

import com.vinsguru.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Set;

public class Lec03FluxFromIterableOrArray {

    public static void main(String[] args) {
        var set = Set.of(1, 2, 3);
        Flux.fromIterable(set).subscribe(Util.subscriber());

        var arr = new String[] {"a", "b", "c"};
        Flux.fromArray(arr).subscribe(Util.subscriber());
    }

}
