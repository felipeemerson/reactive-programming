package com.vinsguru.felipe.sec07;

import com.vinsguru.felipe.common.Util;
import com.vinsguru.sec07.client.ExternalServiceClient;

public class Lec06EventLoopIssueFix {

    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        for (int i = 1; i <= 5; i++) {
            client.getProductName(i)
                    .map(Lec06EventLoopIssueFix::process)
                    .subscribe(Util.subscriber());
        }

        Util.sleepSeconds(5);
    }

    private static String process(String input) {
        Util.sleepSeconds(1);
        return input + "-processed";
    }

}
