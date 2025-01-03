package com.vinsguru.felipe.common;

import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;

public abstract class AbstractHttpClient {

    private static final String BASE_URL = "http://localhost:7070";
    protected final HttpClient httpClient;

    public AbstractHttpClient() {
        var loopSources = LoopResources.create("felipe", 1, true);
        this.httpClient = HttpClient
                .create()
                .runOn(loopSources)
                .baseUrl(BASE_URL);
    }

}
