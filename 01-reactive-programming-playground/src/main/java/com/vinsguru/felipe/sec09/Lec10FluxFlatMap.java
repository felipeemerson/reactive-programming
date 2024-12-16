package com.vinsguru.felipe.sec09;

import com.vinsguru.common.Util;
import com.vinsguru.felipe.sec09.applications.OrderService;
import com.vinsguru.felipe.sec09.applications.User;
import com.vinsguru.felipe.sec09.applications.UserService;

public class Lec10FluxFlatMap {

    public static void main(String[] args) {
        /*
            Get all orders from order service
         */

        UserService.getAllUsers()
                .map(User::id)
                .flatMap(OrderService::getUserOrders)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(3);
    }

}
