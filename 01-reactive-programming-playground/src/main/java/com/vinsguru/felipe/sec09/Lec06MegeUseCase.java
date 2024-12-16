package com.vinsguru.felipe.sec09;

import com.vinsguru.common.Util;
import com.vinsguru.sec09.helper.Kayak;

public class Lec06MegeUseCase {

    public static void main(String[] args) {
        Kayak.getFlights()
                .subscribe(Util.subscriber());

        Util.sleepSeconds(2);
    }

}
