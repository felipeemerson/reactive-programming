package com.vinsguru.felipe.sec10.assignment;

import com.vinsguru.common.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class BufferAssignment {

    private record BookOrder(String genre, String title, Integer price) {}

    private static final Set<String> genresToWatch = Set.of("Science fiction", "Fantasy", "Suspense/Thriller");

    public static void main(String[] args) {
        bookStream()
                .buffer(Duration.ofSeconds(5))
                .map(bookOrders -> {
                    Map<String, Integer> mapGenresToRevenue = new HashMap<>();

                    bookOrders.forEach(bookOrder -> {
                        if (!genresToWatch.contains(bookOrder.genre)) return;

                        var currentRevenue = mapGenresToRevenue.getOrDefault(bookOrder.genre(), 0);
                        mapGenresToRevenue.put(bookOrder.genre(), currentRevenue + bookOrder.price());
                    });

                    return mapGenresToRevenue.toString();
                })
                .subscribe(Util.subscriber());

        Util.sleepSeconds(16);
    }

    private static Flux<BookOrder> bookStream() {
        return Flux.interval(Duration.ofMillis(200))
                .map(i -> new BookOrder(
                        Util.faker().book().genre(),
                        Util.faker().book().title(),
                        Util.faker().random().nextInt(10, 100)
                ))
                .doOnNext(bookOrder -> log.info("{}", bookOrder));
    }

}
