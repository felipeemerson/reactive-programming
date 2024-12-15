package com.vinsguru.felipe.sec06.assignment;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RevenueService {

    private final Map<String, Integer> mapCategoryToRevenue;

    public RevenueService(Flux<Order> orders) {
        mapCategoryToRevenue = new HashMap<>();
        orders
                .doOnSubscribe(subscription -> log.info("revenue service subscribed"))
                .subscribe(order -> mapCategoryToRevenue.compute(order.category(), (key, value) -> value == null ? order.price() * order.quantity() : value + order.price() * order.quantity()));
    }

    public Flux<String> getRevenueUpdates() {
        return Flux.generate(sink -> {
                    StringBuilder stringBuilder = new StringBuilder("Hey, this is our revenue now:");

                    mapCategoryToRevenue.forEach((key, value) -> {
                        stringBuilder.append(String.format("\n%s: %s", key, value));
                    });

                    sink.next(stringBuilder.toString());
                })
                .delayElements(Duration.ofSeconds(2))
                .cast(String.class);
    }

}
