package com.vinsguru.felipe.sec03;

import com.vinsguru.common.Util;
import com.vinsguru.felipe.sec03.helper.NameGenerator;

public class Lec07FluxVsList {

    public static void main(String[] args) {
        //var list = NameGenerator.getNamesList(10);
        var flux = NameGenerator.getNamesFlux(10);
        flux.subscribe(Util.subscriber());
    }

}
