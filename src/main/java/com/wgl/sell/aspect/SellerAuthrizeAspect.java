package com.wgl.sell.aspect;

import com.wgl.sell.constant.CookieConstant;
import com.wgl.sell.constant.RedisConstant;
import com.wgl.sell.exception.SellerAuthrizeException;
import com.wgl.sell.utils.CookieUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class SellerAuthrizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.wgl.sell.controller.Seller*.*(..))"+
            "&&!execution(public * com.wgl.sell.controller.SellerUserController.*(..))")
    public void verify(){

    }


    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        /*查询cookie*/
        Cookie cookie=CookieUtil.get(request,CookieConstant.TOKEN);
        if (cookie==null){
            throw new SellerAuthrizeException();
        }
        /*去redis里查询*/
         String tokenValue=redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
         if (StringUtils.isEmpty(tokenValue)){
             throw new SellerAuthrizeException();
         }

    }
}
