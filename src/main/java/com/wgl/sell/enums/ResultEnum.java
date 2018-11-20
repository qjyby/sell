package com.wgl.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PRODUCT_STOCK_ERROR(1, "库存不足"),
    PRODUCT_NOT_EXIT(0, "商品不存在"),
    PRODUCT_STATUS_ERROR(31, "商品状态异常"),
    ORDER_NOT_EXIT(2, "订单不存在"),
    ORDER_DETAIL_NOT_EXIT(3, "订单详情为空"),
    ORDER_STATUS_ERROR(4, "订单状态异常"),
    ORDER_UPDATE_FAIL(5, "取消订单失败"),
    PARM_ERROR(100, "传入参数不正确"),
    WECHAT_MP_ERROR(20, "微信公众号账号错误"),
    CART_ERROR(101, "购物车不能为空"),
    ORDER_OWNER_ERROR(6, "该用户不属于当前"),
    SUCCESS(102, "成功取消"),
    WXPAY_NOTIFY_MONEY_VERIFY(21, "微信金额校验失败"),
    CATEGORY_ERROR(23, "类目异常"),
    LOGIN_FAIL(24, "登陆失败，找不到相关用户"),
    LOGOUT_SUCCESS(25, "退出成功"),
    LOGIN_PASSWORD_ERROR(27, "登陆密码错误");


    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
