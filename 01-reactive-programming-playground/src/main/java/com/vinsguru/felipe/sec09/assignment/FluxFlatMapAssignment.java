package com.vinsguru.felipe.sec09.assignment;

import com.vinsguru.common.Util;
import reactor.core.publisher.Flux;

public class FluxFlatMapAssignment {

    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        Flux.range(1, 10)
                .flatMap(client::getProduct)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(2);
    }

}
