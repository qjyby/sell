package com.wgl.sell.service;

public interface SeckillService {
    String querySecKillProductInfo(String productId);

    void orderProductMockDiffUser(String productId);
}
