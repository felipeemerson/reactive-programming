package com.vinsguru.felipe.sec06.assignment;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Assignment {

    public static void main(String[] args) {
        var orderService = new OrderService();
        var ordersFlux = orderService.getOrders();
        var inventoryService = new InventoryService(ordersFlux);
        var revenueService = new RevenueService(ordersFlux);

        inventoryService.getInventoryUpdates()
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(log::info)
                .subscribe();
        revenueService.getRevenueUpdates()
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(log::info)
                .subscribe();

        Util.sleepSeconds(15);
    }

}
