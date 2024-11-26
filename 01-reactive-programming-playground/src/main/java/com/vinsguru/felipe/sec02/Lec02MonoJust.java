package com.vinsguru.felipe.sec02;

import com.vinsguru.felipe.sec01.subscriber.SubscriberImpl;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

public class Lec02MonoJust {

    public static void main(String[] args) {
        var mono = Mono.just("felipe");
        var subscriber = new SubscriberImpl();
        mono.subscribe(subscriber);
        // subscriber.getSubscription().request(2);
        subscriber.getSubscription().cancel();

        save(Mono.just("felipe"));
    }

    private static void save(Publisher<String> publisher) {

    }

}
