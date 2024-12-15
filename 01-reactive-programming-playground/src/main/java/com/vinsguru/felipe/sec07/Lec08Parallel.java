package com.vinsguru.felipe.sec07;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Lec08Parallel {

    public static void main(String[] args) {
        Flux.range(1, 10)
                .parallel(3)
                .runOn(Schedulers.parallel())
                .map(Lec08Parallel::process)
                //.sequential()
                .subscribe(Util.subscriber());

        Util.sleepSeconds(2);
    }

    private static int process(int i) {
        log.info("time consuming task {}", i);
        Util.sleepSeconds(1);
        return i * 2;
    }

}
