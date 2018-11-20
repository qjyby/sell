package com.wgl.sell.service.impl;

import com.wgl.sell.exception.SellException;
import com.wgl.sell.service.RedisLock;
import com.wgl.sell.service.SeckillService;
import com.wgl.sell.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SecKillServiceImpl implements SeckillService {

    @Autowired
    private RedisLock redisLock;
    @Autowired
    private static  final  int TIMEOUT=10*1000;//超时时间10s

    static Map<String, Integer> products;
    static Map<String, Integer> stock;
    static Map<String, String> orders;

    {
        /*信息表，库存表，秒杀成功订单表*/
        products = new HashMap<>();
        stock = new HashMap<>();
        orders=new HashMap<>();
        products.put("123456", 100000);
        stock.put("123456", 100000);
    }

    private String queryMap(String productId) {
        return "国庆活动，皮蛋粥特价，限量份" + products.get(productId) + "还剩下" + stock.get(productId)
                + "份" +"， 该商品一共下单了"+ orders.size() + "人";
    }


    @Override
    public String querySecKillProductInfo(String productId) {
        return this.queryMap(productId);
    }

    @Override
    public synchronized void  orderProductMockDiffUser(String productId) {

        //加锁
        Long time=System.currentTimeMillis()+TIMEOUT;
         if (!redisLock.lock(productId,String.valueOf(time))){
             throw new SellException(666,"人太多了，换个时间试试");
         }


        //.查询该商品的库存，为0则活动结束
        int stockNum = stock.get(productId);
        if (stockNum == 0) {
            throw new SellException(100, "商品结束");
        } else {
            //2.下单
            orders.put(KeyUtil.genUniqueKey(), productId);
            stockNum = stockNum - 1;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stock.put(productId, stockNum);
        }
         //解锁
        redisLock.unlock(productId,String.valueOf(time));

    }
}
