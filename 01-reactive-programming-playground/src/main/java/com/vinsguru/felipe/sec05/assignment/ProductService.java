package com.vinsguru.felipe.sec05.assignment;

import com.vinsguru.felipe.common.AbstractHttpClient;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class ProductService extends AbstractHttpClient {

    public Flux<String> getProductName(int id) {
        return getProductNameDefault(id)
                .timeout(Duration.ofSeconds(2), getProductNameTimeoutFallback(id))
                .switchIfEmpty(getProductNameEmptyFallback(id));
    }

    private Flux<String> getProductNameDefault(int id) {
        return this.httpClient.get()
                .uri("/demo03/product/" + id)
                .responseContent()
                .asString();
    }

    private Flux<String> getProductNameTimeoutFallback(int id) {
        return this.httpClient.get()
                .uri("/demo03/timeout-fallback/product/" + id)
                .responseContent()
                .asString();
    }

    private Flux<String> getProductNameEmptyFallback(int id) {
        return this.httpClient.get()
                .uri("/demo03/empty-fallback/product/" + id)
                .responseContent()
                .asString();
    }

}
