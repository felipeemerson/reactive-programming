package com.vinsguru.felipe.sec06.assignment;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class InventoryService {

    private final Map<String, Integer> mapCategoryToInventory;

    public InventoryService(Flux<Order> orders) {
        mapCategoryToInventory = new HashMap<>();

        orders
                .doOnSubscribe(subscription -> log.info("inventory service subscribed"))
                .subscribe(order -> {
            mapCategoryToInventory.compute(order.category(), (key, value) -> value == null ? 500 - order.quantity() : value - order.quantity());
        });
    }

    public Flux<String> getInventoryUpdates() {
        return Flux.generate(sink -> {
                        StringBuilder stringBuilder = new StringBuilder("Hey, this is our inventory now:");

                        mapCategoryToInventory.forEach((key, value) -> {
                            stringBuilder.append(String.format("\n%s: %s", key, value));
                        });

                        sink.next(stringBuilder.toString());
                    })
                    .delayElements(Duration.ofSeconds(2))
                    .cast(String.class);
    }

}
