package com.vinsguru.felipe.sec09;

import com.vinsguru.common.Util;
import com.vinsguru.felipe.sec09.assignment.ExternalServiceClient;
import reactor.core.publisher.Flux;

/*
    While flatMap is concurrently, concatMap is sequential.
 */
public class Lec11ConcatMap {

    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        Flux.range(1, 10)
                .concatMap(client::getProduct)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }

}
