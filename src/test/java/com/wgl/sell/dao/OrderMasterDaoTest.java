package com.wgl.sell.dao;

import com.wgl.sell.entity.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMasterDaoTest {

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Test
    public void save() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123456");
        orderMaster.setBuyerName("小风");
        orderMaster.setBuyerAddress("福建厦门");
        orderMaster.setBuyerOpenid("110112");
        orderMaster.setBuyerPhone("15505916860");
        orderMaster.setOrderAmount(new BigDecimal(99.9));

        OrderMaster orderMaster1 = orderMasterDao.save(orderMaster);
        Assert.assertNotNull(orderMaster1);
    }

    @Test
    public void findByBuyerOpenid() {
        Pageable pageable = new PageRequest(0, 1);
        Page<OrderMaster> pages = orderMasterDao.findByBuyerOpenid("110112", pageable);
        System.out.println(pages.getContent());

    }
}