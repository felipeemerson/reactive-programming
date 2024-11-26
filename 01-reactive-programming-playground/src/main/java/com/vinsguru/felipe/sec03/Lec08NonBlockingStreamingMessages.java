package com.vinsguru.felipe.sec03;

import com.vinsguru.common.Util;
import com.vinsguru.felipe.sec01.subscriber.SubscriberImpl;
import com.vinsguru.felipe.sec03.client.ExternalServiceClient;

public class Lec08NonBlockingStreamingMessages {

    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        client.getNames()
                .subscribe(Util.subscriber("sub1"));

        client.getNames()
                .subscribe(Util.subscriber("sub2"));

        Util.sleepSeconds(3);
    }

}
