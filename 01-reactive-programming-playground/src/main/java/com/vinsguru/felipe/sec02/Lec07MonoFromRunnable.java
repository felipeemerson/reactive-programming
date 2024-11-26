package com.vinsguru.felipe.sec02;

import com.vinsguru.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec07MonoFromRunnable {

    private static final Logger log = LoggerFactory.getLogger(Lec07MonoFromRunnable.class);

    public static void main(String[] args) {
        getProductName(1)
                .subscribe(Util.subscriber());

        getProductName(2)
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getProductName(int productId) {
        return switch (productId) {
            case 1 -> Mono.fromSupplier(() -> Util.faker().commerce().productName());
            default -> Mono.fromRunnable(() -> notifyBusiness(productId));
        };
    }

    private static void notifyBusiness(int productId) {
        log.info("notifying business on unavailable product {}", productId);
    }

}
