package com.wgl.sell.service.impl;

import com.wgl.sell.dto.OrderDto;
import com.wgl.sell.enums.ResultEnum;
import com.wgl.sell.exception.SellException;
import com.wgl.sell.service.BuyerService;
import com.wgl.sell.service.OrderService;
import com.wgl.sell.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderService orderService;

    @Override
    public OrderDto findOrderOne(String openid, String orderId) {
        OrderDto orderDto = checkeOpenid(openid, orderId);
        return orderDto;
    }

    @Override
    public OrderDto cancelOrder(String openid, String orderId) {
        OrderDto orderDto = checkeOpenid(openid, orderId);
        if (orderDto == null) {
            log.error("取消订单异常");
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }
        return orderService.cancel(orderDto);
    }

    private OrderDto checkeOpenid(String openid, String orderId) {
        OrderDto orderDto = orderService.findOne(orderId);
        if (openid == null) {
            return null;
        }
     /*   if (orderDto.getBuyerOpenid().equals(openid)) {
            log.error("查询订单异常，登陆用户openid异常");
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }*/
        return orderDto;
    }
}
