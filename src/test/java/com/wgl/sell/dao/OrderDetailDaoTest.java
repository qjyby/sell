package com.wgl.sell.dao;

import com.wgl.sell.entity.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderDetailDaoTest {
    @Autowired
    private OrderDetailDao detailDao;

    @Test
    public void save() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123456");
        orderDetail.setOrderId("12345");
        orderDetail.setProductIcon("http://xxx.png");
        orderDetail.setProductId("1234");
        orderDetail.setProductName("黄焖鸡");
        orderDetail.setProductPrice(new BigDecimal(188));
        orderDetail.setProductQuantity(2);
        OrderDetail orderDetail1 = detailDao.save(orderDetail);
        Assert.assertNotNull(orderDetail1);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail>list=detailDao.findByOrderId("1532601267738110877");
    }
}