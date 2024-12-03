package com.vinsguru.felipe.sec04;

import com.vinsguru.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec06FluxGenerate {

    private static final Logger log = LoggerFactory.getLogger(Lec06FluxGenerate.class);

    public static void main(String[] args) {
        Flux.generate(synchronousSink -> {
                // only can emit one item at a time
                log.info("invoked");
                synchronousSink.next(1);
                //synchronousSink.next(2);
                //synchronousSink.complete();
            })
            .take(4)
            .subscribe(Util.subscriber());
    }

}
