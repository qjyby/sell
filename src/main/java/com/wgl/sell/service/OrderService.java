package com.wgl.sell.service;

import com.wgl.sell.dto.OrderDto;
import com.wgl.sell.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    /*创建订单*/
    OrderDto create(OrderDto orderDto);

    /*查询单个订单*/
    OrderDto findOne(String orderId);

    /*查询订单列表*/
    Page<OrderDto> findList(String buyerOpenid, Pageable pageable);

    /*取消订单*/
    OrderDto cancel(OrderDto orderDto);

    /*完结订单*/
    OrderDto finsh(OrderDto orderDto);

    /*支付订单*/
    OrderDto paid(OrderDto orderDto);

    /*查询订单列表*/
    Page<OrderDto> findList(Pageable pageable);
}
