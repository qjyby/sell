package com.wgl.sell.dao;

import com.wgl.sell.entity.SellerInfo;
import com.wgl.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SellerInfoDaoTest {
    @Autowired
    SellerInfoDao sellerInfoDao;

    @Test
    public void save() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setOpenid("1111");
        sellerInfo.setPassword("admin");
        sellerInfo.setUsername("admin");
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        SellerInfo sellerInfo1 = sellerInfoDao.save(sellerInfo);
        Assert.assertNotNull(sellerInfo1);

    }

    @Test
    public void findByOpenid() {
        SellerInfo sellerInfo = sellerInfoDao.findByOpenid("1111");
        Assert.assertNotNull(sellerInfo);
    }

    @Test
    public void findByUsername() {
        SellerInfo sellerInfo = sellerInfoDao.findByUsername("admin");
        Assert.assertNotNull(sellerInfo);
    }
}