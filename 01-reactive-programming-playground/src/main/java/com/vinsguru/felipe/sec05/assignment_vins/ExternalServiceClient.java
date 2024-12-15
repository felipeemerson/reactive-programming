package com.vinsguru.felipe.sec05.assignment_vins;

import com.vinsguru.common.Util;
import com.vinsguru.felipe.common.AbstractHttpClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class ExternalServiceClient extends AbstractHttpClient {

    private Mono<String> getProductName(int productId) {
        var defaultPath = "/demo03/product/" + productId;
        var emptyPath = "/demo03/empty-fallback/product/" + productId;
        var timeoutPath = "/demo03/timeout-fallback/product/" + productId;

        return getProductName(defaultPath)
                .timeout(Duration.ofSeconds(2), getProductName(timeoutPath))
                .switchIfEmpty(getProductName(emptyPath));
    }

    private Mono<String> getProductName(String path) {
        return this.httpClient.get()
                .uri(path)
                .responseContent()
                .asString()
                .next();
    }

    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        for (int i = 0; i < 5; i++) {
            client.getProductName(i)
                    .subscribe(Util.subscriber());
        }

        Util.sleepSeconds(3);
    }

}
