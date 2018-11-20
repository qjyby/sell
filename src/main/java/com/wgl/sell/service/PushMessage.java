package com.wgl.sell.service;

import com.wgl.sell.dto.OrderDto;

public interface PushMessage {

    void orderStatus(OrderDto orderDto);
}
