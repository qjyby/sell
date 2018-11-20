package com.wgl.sell.service.impl;

import com.wgl.sell.service.SellerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SellerServiceImplTest {

    @Autowired
    private SellerService sellerService;

    @Test
    public void checkLogin() {
        sellerService.checkLogin("admin", "admi2");
    }
}