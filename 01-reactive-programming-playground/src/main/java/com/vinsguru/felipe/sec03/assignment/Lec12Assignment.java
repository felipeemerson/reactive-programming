package com.vinsguru.felipe.sec03.assignment;

import com.vinsguru.common.Util;
import com.vinsguru.felipe.sec03.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Lec12Assignment {

    private static final Logger log = LoggerFactory.getLogger(Lec12Assignment.class);

    public static void main(String[] args) {
        var investments = new ArrayList<Integer>();
        AtomicInteger priceToSell = new AtomicInteger();
        var externalService = new ExternalServiceClient();

        externalService.getPriceChanges()
                .takeWhile(value -> priceToSell.get() == 0)
                .subscribe(
                        stock -> {
                            if (stock < 90) {
                                investments.add(stock);
                                log.info("current stock price US$ {}", stock);
                            } else if (stock > 110) {
                                priceToSell.set(stock);
                                log.info("sell stock price US$ {}", stock);
                            }
                        },
                        error -> log.error("An error occurred", error),
                        () -> {
                            int profit = investments.stream().mapToInt(value -> priceToSell.get() - value).sum();
                            log.info("The profit made was US$ {}", profit);
                            log.info("completed");
                        }
                );

        Util.sleepSeconds(20);
    }

}
