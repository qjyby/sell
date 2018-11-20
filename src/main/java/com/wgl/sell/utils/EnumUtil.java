package com.wgl.sell.utils;

import com.wgl.sell.enums.CodeEnum;

public class EnumUtil {

    public static <T extends CodeEnum> T getBycode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}

