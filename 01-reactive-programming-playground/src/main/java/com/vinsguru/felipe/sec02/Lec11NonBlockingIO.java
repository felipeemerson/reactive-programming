package com.vinsguru.felipe.sec02;

import com.vinsguru.felipe.common.Util;
import com.vinsguru.sec02.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec11NonBlockingIO {

    private static final Logger log = LoggerFactory.getLogger(Lec11NonBlockingIO.class);


    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        log.info("starting");

        for (int i = 1; i <= 100; i++) {
            client.getProductName(i)
                    .subscribe(Util.subscriber());
        }

        Util.sleepSeconds(2);
    }

}
