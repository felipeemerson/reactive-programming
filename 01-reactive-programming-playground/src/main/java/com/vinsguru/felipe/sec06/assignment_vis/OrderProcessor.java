package com.vinsguru.felipe.sec06.assignment_vis;

import reactor.core.publisher.Flux;

public interface OrderProcessor {

    void consume(Order order);

    Flux<String> stream();

}
