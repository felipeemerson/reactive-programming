package com.vinsguru.felipe.sec11.client;

import com.vinsguru.felipe.common.AbstractHttpClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClientResponse;

public class ExternalServiceClient extends AbstractHttpClient {

    public Mono<String> getCountryName() {
        return this.get("/demo06/country");
    }

    public Mono<String> getProductName(int productId) {
        return this.get("/demo06/product/" + productId);
    }

    private Mono<String> get(String path) {
        return this.httpClient.get()
                .uri(path)
                .response(this::toResponse)
                .next();
    }

    private Flux<String> toResponse(HttpClientResponse httpClientResponse, ByteBufFlux byteBufFlux) {
        return switch (httpClientResponse.status().code()) {
            case 200 -> byteBufFlux.asString();
            case 400 -> Flux.error(new ClientError());
            default -> Flux.error(new ServerError());
        };
    }

}
