package com.wgl.sell.utils;

import java.util.Random;

public class KeyUtil {
    public static synchronized   String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(90000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);

    }
}
