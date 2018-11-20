package com.wgl.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.xml.core.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /*加锁*/
    public  boolean  lock(String key,String value){
      if (redisTemplate.opsForValue().setIfAbsent(key,value)){
          return true;
      }
      String currenValue=redisTemplate.opsForValue().get(key);
      //如果锁过期
      if (!StringUtils.isEmpty(currenValue)&&Long.parseLong(currenValue)<System.currentTimeMillis()){
          //获取上一个锁的时间
          String oldValue=redisTemplate.opsForValue().getAndSet(key,value);
          if (!StringUtils.isEmpty(oldValue)&&oldValue.equals(currenValue)){
              return true;
          }
      }
      return  false;

    }
    public  void unlock(String key,String value){
        try{}
        catch (Exception e){
            log.error("redis分布式锁解锁异常,{}",e);
        }
    }
}
