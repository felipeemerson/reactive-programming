package com.vinsguru.felipe.sec05;

import com.vinsguru.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec06ErrorHandling {

    private static final Logger log = LoggerFactory.getLogger(Lec05Subscribe.class);

    public static void main(String[] args) {
        Flux.range(1, 10)
                .map(i -> i == 5 ? 5/0 : i)
                .onErrorReturn(-1)
                .subscribe(Util.subscriber());
    }

}
