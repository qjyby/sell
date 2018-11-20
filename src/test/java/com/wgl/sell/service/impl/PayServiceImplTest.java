package com.wgl.sell.service.impl;

import com.wgl.sell.dto.OrderDto;
import com.wgl.sell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class PayServiceImplTest {
    @Autowired
    private PayServiceImpl payService;

    @Test
    public void create() {
        OrderDto orderDto = new OrderDto();
        payService.create(orderDto);
    }
}