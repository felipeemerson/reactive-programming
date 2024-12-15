package com.vinsguru.felipe.sec07.client;

import com.vinsguru.felipe.common.AbstractHttpClient;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class ExternalServiceClient extends AbstractHttpClient {

    public Mono<String> getProductName(int productId) {
        return this.httpClient.get()
                .uri("/demo01/product/" + productId)
                .responseContent()
                .asString()
                .doOnNext(message -> log.info("next: {}", message))
                .next();
    }
}
