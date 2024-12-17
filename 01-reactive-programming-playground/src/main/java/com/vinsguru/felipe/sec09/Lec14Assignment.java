package com.vinsguru.felipe.sec09;

import com.vinsguru.common.Util;
import com.vinsguru.felipe.sec09.applications.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/*
    Get all users and build 1 object as shown here.
    record UserInformation(Integer userId, String username, Integer balance, List<Order> orders) {}
 */
public class Lec14Assignment {

    record UserInformation(Integer userId, String username, Integer balance, List<Order> orders) {}

    public static void main(String[] args) {
        vinsVersion();

        Util.sleepSeconds(10);


    }

    private static void myVersion() {
        UserService.getAllUsers()
                .flatMap(user ->
                        Flux.zip(PaymentService.getUserBalance(user.id()),
                                        OrderService.getUserOrders(user.id())
                                                .collectList()
                                )
                                .map(tuple -> new UserInformation(user.id(), user.username(), tuple.getT1(), tuple.getT2()))
                )
                .subscribe(Util.subscriber());
    }

    private static void vinsVersion() {
        UserService.getAllUsers()
                .flatMap(Lec14Assignment::getUserInformation)
                .subscribe(Util.subscriber());
    }

    private static Mono<UserInformation> getUserInformation(User user) {
        return Mono.zip(
                PaymentService.getUserBalance(user.id()),
                OrderService.getUserOrders(user.id())
                        .collectList()
                )
                .map(tuple -> new UserInformation(user.id(), user.username(), tuple.getT1(), tuple.getT2())
        );
    }

}
