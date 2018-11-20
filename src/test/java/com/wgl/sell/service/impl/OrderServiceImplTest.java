package com.wgl.sell.service.impl;

import com.wgl.sell.dto.CarDto;
import com.wgl.sell.dto.OrderDto;
import com.wgl.sell.entity.OrderDetail;
import com.wgl.sell.entity.ProductInfo;
import com.wgl.sell.enums.OrderStatusEnum;
import com.wgl.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;
    private final String buyerOpenid = "110110";

    @Test
    public void create() {
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName("吴广林");
        orderDto.setBuyerAddress("南岸");
        orderDto.setBuyerPhone("15505916860");
        orderDto.setBuyerOpenid(buyerOpenid);

        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("1234");
        orderDetail.setProductQuantity(3);

        OrderDetail orderDetai2 = new OrderDetail();
        orderDetai2.setProductId("12345");
        orderDetai2.setProductQuantity(2);
        orderDetails.add(orderDetail);
        orderDetails.add(orderDetai2);

        orderDto.setOrderDetailList(orderDetails);
        OrderDto result = orderService.create(orderDto);
        log.info("【创建订单】result={}", result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDto orderDto = orderService.findOne("1532603805231144936");
        log.info("订单表的信息+{}", orderDto);
        Assert.assertNotNull(orderDto);
    }

    @Test
    public void findList() {
        Pageable pageable = new PageRequest(0, 1);
        Page<OrderDto> orderDtoPage = orderService.findList("110110", pageable);
        Assert.assertNotEquals(0, orderDtoPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDto orderDto = orderService.findOne("1532604339732128619");
        OrderDto orderDto1 = orderService.cancel(orderDto);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), orderDto1.getOrderStatus());
    }

    @Test
    public void finsh() {
        OrderDto orderDto = orderService.findOne("1532604339732128619");
        OrderDto orderDto1 = orderService.finsh(orderDto);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), orderDto1.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDto orderDto = orderService.findOne("1532604339732128619");
        OrderDto orderDto1 = orderService.paid(orderDto);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), orderDto1.getPayStatus());
    }

    @Test
    public void list() {
        Pageable pageable = new PageRequest(0, 1);
        Page<OrderDto> orderDtoPage = orderService.findList( pageable);
        Assert.assertNotEquals(0, orderDtoPage.getTotalElements());
    }
}