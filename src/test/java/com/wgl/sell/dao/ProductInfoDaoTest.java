package com.wgl.sell.dao;

import com.wgl.sell.entity.ProductInfo;
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
public class ProductInfoDaoTest {

    @Autowired
    private ProductInfoDao productInfoDao;


    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setCategoryType(2);
        productInfo.setProductId("1234");
        productInfo.setProductName("黄焖鸡");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("还不错");
        productInfo.setProductIcon("http://xxx");
        productInfo.setProductStatus(0);
        productInfoDao.save(productInfo);
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfos = productInfoDao.findByProductStatus(0);
        Assert.assertNotNull(productInfos);
    }
}