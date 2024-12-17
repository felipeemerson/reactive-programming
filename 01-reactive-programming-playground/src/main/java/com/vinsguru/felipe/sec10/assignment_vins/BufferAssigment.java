package com.vinsguru.felipe.sec10.assignment_vins;

import com.vinsguru.common.Util;
import com.vinsguru.felipe.sec10.assignment_vins.buffer.BookOrder;
import com.vinsguru.felipe.sec10.assignment_vins.buffer.RevenueReport;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BufferAssigment {

    public static void main(String[] args) {
        var allowedCategories = Set.of("Science fiction", "Fantasy", "Suspense/Thriller");

        orderStream()
                .filter(bookOrder -> allowedCategories.contains(bookOrder.genre()))
                .buffer(Duration.ofSeconds(5))
                .map(BufferAssigment::generateReport)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(16);
    }

    private static Flux<BookOrder> orderStream() {
        return Flux.interval(Duration.ofMillis(200))
                .map(i -> BookOrder.create());
    }

    private static RevenueReport generateReport(List<BookOrder> orders) {
        Map<String, Integer> revenue = orders.stream()
                .collect(Collectors.groupingBy(
                        BookOrder::genre,
                        Collectors.summingInt(BookOrder::price)
                ));

        return new RevenueReport(LocalTime.now(), revenue);
    }

}
