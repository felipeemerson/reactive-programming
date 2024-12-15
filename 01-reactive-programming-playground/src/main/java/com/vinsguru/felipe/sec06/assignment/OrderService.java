package com.vinsguru.felipe.sec06.assignment;

import com.vinsguru.common.Util;
import com.vinsguru.felipe.common.AbstractHttpClient;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class OrderService extends AbstractHttpClient {

    public Flux<Order> getOrders() {
        return this.httpClient.get()
                .uri("http://localhost:7070/demo04/orders/stream")
                .responseContent()
                .asString()
                .map(order -> {
                    var orderAttributes = order.split(":");

                    return new Order(
                            orderAttributes[0],
                            orderAttributes[1],
                            Integer.parseInt(orderAttributes[2]),
                            Integer.parseInt(orderAttributes[3])
                    );
                })
                .doOnNext(order -> log.info("received new order: {}", order))
                .publish()
                .refCount(2);
    }

}
