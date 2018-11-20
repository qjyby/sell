package com.wgl.sell.service;

import com.wgl.sell.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;

public interface BuyerService {
    /*查询一个订单*/
    OrderDto findOrderOne(String openid, String orderId);

    /*查询订单*/
    OrderDto cancelOrder(String openid, String orderId);
}
