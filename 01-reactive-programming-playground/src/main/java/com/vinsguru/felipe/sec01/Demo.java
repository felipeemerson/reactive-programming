package com.vinsguru.felipe.sec01;

import com.vinsguru.felipe.sec01.publisher.PublisherImpl;
import com.vinsguru.felipe.sec01.subscriber.SubscriberImpl;

import java.time.Duration;

public class Demo {

    public static void main(String[] args) throws InterruptedException {
        demo4();
    }

    private static void demo1() {
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
    }

    private static void demo2() throws InterruptedException {
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);

        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));

        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));

        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));

        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));

        subscriber.getSubscription().request(3);
    }

    private static void demo3() {
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);

        subscriber.getSubscription().request(3);
        subscriber.getSubscription().cancel();
        subscriber.getSubscription().request(3);
    }

    private static void demo4() throws InterruptedException {
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);

        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));

        subscriber.getSubscription().request(12);
        Thread.sleep(Duration.ofSeconds(2));

        subscriber.getSubscription().request(3);
    }

}
