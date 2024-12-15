package com.vinsguru.felipe.sec06.assignment_vis;

import com.vinsguru.common.Util;
import reactor.core.scheduler.Schedulers;

public class Assignment {

    public static void main(String[] args) {
        var client = new OrderService();
        var inventoryService = new InventoryService();
        var revenueService = new RevenueService();

        client.orderStream()
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(inventoryService::consume);
        client.orderStream()
                .subscribe(revenueService::consume);

        inventoryService
                .stream()
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(Util.subscriber("inventory"));

        revenueService
                .stream()
                .subscribe(Util.subscriber("revenue"));

        Util.sleepSeconds(30);
    }

}
