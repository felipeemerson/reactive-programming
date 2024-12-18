package com.vinsguru.felipe.sec10.assignment;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;

import java.time.Duration;

@Slf4j
public class GroupByAssigment {

    private record PurchaseOrder(String item, String category, Integer price) {

        public static PurchaseOrder create() {
            return new PurchaseOrder(
                    Util.faker().commerce().productName(),
                    Util.faker().commerce().department(),
                    Util.faker().random().nextInt(10, 100)
            );
        }

    }

    public static void main(String[] args) {
        Flux.interval(Duration.ofMillis(500))
                .map(i -> PurchaseOrder.create())
                .doOnNext(order -> log.info("received order: {}", order))
                .filter(order -> order.category().equals("Kids") || order.category().equals("Automotive"))
                .groupBy(PurchaseOrder::category)
                .flatMap(GroupByAssigment::processEvents)
                .subscribe();

        Util.sleepSeconds(60);
    }

    private static Flux<PurchaseOrder> processEvents(GroupedFlux<String, PurchaseOrder> groupedFlux) {
        return groupedFlux
                .flatMap(order -> {
                    if (groupedFlux.key().equals("Kids")) {
                        return Flux
                                .just(order)
                                .concatWithValues(new PurchaseOrder(order.item(), order.category(), 0));
                    }
                       return Flux.just(new PurchaseOrder(order.item(), order.category(), order.price() + 100));

                })
                .doOnNext(i -> log.info("key: {}, item: {}", groupedFlux.key(), i));
    }



}
