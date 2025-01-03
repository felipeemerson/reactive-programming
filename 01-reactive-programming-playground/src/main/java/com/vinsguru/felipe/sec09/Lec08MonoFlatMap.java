package com.vinsguru.felipe.sec09;

import com.vinsguru.common.Util;
import com.vinsguru.felipe.sec09.applications.PaymentService;
import com.vinsguru.felipe.sec09.applications.UserService;

/*
    Sequential non-blocking IO calls!
    flatMap is used to flatten the inner publisher / to subscribe to the inner publisher
 */
public class Lec08MonoFlatMap {

    public static void main(String[] args) {
        /*
            We have username.
            Get user account balance
         */
        UserService.getUserId("mike")
                .flatMap(PaymentService::getUserBalance)
                .subscribe(Util.subscriber());
    }

}
