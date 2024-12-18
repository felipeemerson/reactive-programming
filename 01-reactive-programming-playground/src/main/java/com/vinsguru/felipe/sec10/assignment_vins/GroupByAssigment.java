package com.vinsguru.felipe.sec10.assignment_vins;

import com.vinsguru.common.Util;
import com.vinsguru.felipe.sec10.assignment_vins.groupby.OrderProcessingService;
import com.vinsguru.felipe.sec10.assignment_vins.groupby.PurchaseOrder;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class GroupByAssigment {

    public static void main(String[] args) {
        orderStream()
                .filter(OrderProcessingService.canProcess())
                .groupBy(PurchaseOrder::category)
                .flatMap(groupedFlux -> groupedFlux
                        .transform(OrderProcessingService.getProcessor(groupedFlux.key())))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(20);
    }

    private static Flux<PurchaseOrder> orderStream() {
        return Flux.interval(Duration.ofMillis(200))
                .map(i -> PurchaseOrder.create());
    }

}
