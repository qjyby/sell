package com.wgl.sell.utils;

public class MathUtil {
    private static final Double MONEY_RANGE = 0.01;
    /*比较金额是否相等*/

    public static boolean equals(Double one, Double two) {
        Double result = Math.abs(one - two);
        if (result < MONEY_RANGE) {
            return true;
        } else {
            return false;
        }
    }
}
