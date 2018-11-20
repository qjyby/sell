package com.wgl.sell.service.impl;

import com.wgl.sell.dao.SellerInfoDao;
import com.wgl.sell.entity.SellerInfo;
import com.wgl.sell.enums.ResultEnum;
import com.wgl.sell.exception.SellException;
import com.wgl.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerInfoDao sellerInfoDao;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoDao.findByOpenid(openid);
    }

    @Override
    public void checkLogin(String username, String password) {
        SellerInfo sellerInfo = sellerInfoDao.findByUsername(username);
        if (sellerInfo == null) {
            throw new SellException(ResultEnum.LOGIN_FAIL);
        }
        if (!sellerInfo.getPassword().equals(password)){
            throw new SellException(ResultEnum.LOGIN_PASSWORD_ERROR);
        }
        System.out.println("认证成功");
    }
}
