package com.vinsguru.felipe.sec05.assignment;

import com.vinsguru.common.Util;

public class Assignment {

    public static void main(String[] args) {
        ProductService productService = new ProductService();

        for (int i = 0; i < 5; i++) {
            productService.getProductName(i)
                    .subscribe(Util.subscriber());
        }

        Util.sleepSeconds(3);
    }

}
