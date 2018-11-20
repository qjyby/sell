package com.wgl.sell.service.impl;

import com.wgl.sell.entity.ProductInfo;
import com.wgl.sell.enums.ProductStatusEnum;
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
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productService.findOne("1234");
        Assert.assertEquals("1234", productInfo.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfos = productService.findUpAll();
        Assert.assertNotEquals(0, productInfos.size());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setCategoryType(3);
        productInfo.setProductId("12345");
        productInfo.setProductName("沙县小吃");
        productInfo.setProductPrice(new BigDecimal(4.2));
        productInfo.setProductStock(200);
        productInfo.setProductDescription("还不错");
        productInfo.setProductIcon("http://xxx");
        productInfo.setProductStatus(0);
        productService.save(productInfo);
    }

    @Test
    public void findAll() {
        Pageable pageable = new PageRequest(0, 2);
        Page<ProductInfo> productInfos = productService.findAll(pageable);
        System.out.println(productInfos.getTotalElements());
    }

    @Test
    public void onSale() {
        ProductInfo productInfo = productService.offSale("1234");
        Assert.assertEquals(ProductStatusEnum.DOWN.getCode(), productInfo.getProductStatus());
    }
}