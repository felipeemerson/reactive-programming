package com.vinsguru.felipe.sec11;

import com.vinsguru.common.Util;
import com.vinsguru.felipe.sec11.client.ExternalServiceClient;
import com.vinsguru.felipe.sec11.client.ServerError;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
public class Lec03ExternalServiceDemo {

    public static void main(String[] args) {
        retry();

        Util.sleepSeconds(10);
    }

    private static void repeat() {
        var client = new ExternalServiceClient();

        client.getCountryName()
                .repeat()
                .takeUntil(country -> country.equals("Brazil"))
                .subscribe(Util.subscriber());
    }

    private static void retry() {
        var client = new ExternalServiceClient();

        client.getProductName(2)
                .retryWhen(retryOnServerError())
                .subscribe(Util.subscriber());
    }

    private static Retry retryOnServerError() {
        return Retry.fixedDelay(20, Duration.ofSeconds(1))
                .filter(ServerError.class::equals)
                .doBeforeRetry(retrySignal -> log.info("retrying {}", retrySignal.failure().getMessage()));
    }

}
