package com.vinsguru.felipe.sec09.assignment;


import com.vinsguru.common.Util;

public class ZipAssignment {

    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        for (int i = 1; i < 10; i++) {
            client.getProduct(i)
                    .subscribe(Util.subscriber());
        }

        Util.sleepSeconds(10);
    }

}
