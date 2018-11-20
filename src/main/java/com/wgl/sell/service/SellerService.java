package com.wgl.sell.service;

import com.wgl.sell.entity.SellerInfo;

public interface SellerService {

    SellerInfo findSellerInfoByOpenid(String openid);

    /*验证账号密码*/
    void  checkLogin(String username,String password);
}
