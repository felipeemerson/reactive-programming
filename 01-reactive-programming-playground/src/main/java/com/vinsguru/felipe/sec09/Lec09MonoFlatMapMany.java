package com.vinsguru.felipe.sec09;

import com.vinsguru.common.Util;
import com.vinsguru.felipe.sec09.applications.OrderService;
import com.vinsguru.felipe.sec09.applications.UserService;

/*
    Sequential non-blocking IO calls!
    flatMap is used to flatten the inner publisher / to subscribe to the inner publisher
    Mono is supposed to be 1 item - what if the flatMap returns multiple items?!
 */
public class Lec09MonoFlatMapMany {

    public static void main(String[] args) {
        /*
            We have username.
            Get user account balance
         */
        UserService.getUserId("jake")
                .flatMapMany(OrderService::getUserOrders)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(2);
    }

}
