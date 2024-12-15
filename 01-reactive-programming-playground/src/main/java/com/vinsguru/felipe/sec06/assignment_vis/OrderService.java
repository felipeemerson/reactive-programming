package com.vinsguru.felipe.sec06.assignment_vis;

import com.vinsguru.felipe.common.AbstractHttpClient;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.Objects;

@Slf4j
public class OrderService extends AbstractHttpClient {

    public Flux<Order> orderFlux;

    public Flux<Order> orderStream() {
        if (Objects.isNull(orderFlux)) {
            this.orderFlux = getOrdersStream();
        }

        return this.orderFlux;
    }

    private Flux<Order> getOrdersStream() {
        return this.httpClient.get()
                .uri("http://localhost:7070/demo04/orders/stream")
                .responseContent()
                .asString()
                .map(this::parse)
                .doOnNext(order -> log.info("{}", order))
                .publish()
                .refCount(2);
    }

    private Order parse(String message) {
        var arr = message.split(":");

        return new Order(arr[1], Integer.parseInt(arr[2]), Integer.parseInt(arr[3]));
    }

}
