package com.vinsguru.felipe.sec09.assignment;

import com.vinsguru.felipe.common.AbstractHttpClient;
import reactor.core.publisher.Mono;

public class ExternalServiceClient extends AbstractHttpClient {

    public Mono<Product> getProduct(int id) {
        return Mono.zip(
                        getName(id),
                        getReview(id),
                        getPrice(id)
                    )
                .map(tuple -> new Product(
                        id,
                        tuple.getT1(),
                        tuple.getT2(),
                        tuple.getT3()
                ));
    }

    private Mono<String> getPrice(int id) {
        return this.httpClient.get()
                .uri("/demo05/price/" + id)
                .responseContent()
                .asString()
                .next();
    }

    private Mono<String> getName(int id) {
        return this.httpClient.get()
                .uri("/demo05/product/" + id)
                .responseContent()
                .asString()
                .next();
    }

    private Mono<String> getReview(int id) {
        return this.httpClient.get()
                .uri("/demo05/review/" + id)
                .responseContent()
                .asString()
                .next();
    }

}
