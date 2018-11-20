package com.wgl.sell.controller;

import com.lly835.bestpay.model.PayResponse;
import com.wgl.sell.dto.OrderDto;
import com.wgl.sell.enums.ResultEnum;
import com.wgl.sell.exception.SellException;
import com.wgl.sell.service.OrderService;
import com.wgl.sell.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;


    /*模拟支付接口*/
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> map) {
        /*查询订单*/
   /*     OrderDto orderDto = orderService.findOne(orderId);
        if (orderDto == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }
        *//*发起支付*//*
        PayResponse payResponse = payService.create(orderDto);
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);
        return new ModelAndView("pay/create", map);

        以上代码为正式支付代码，下面为虚拟支付
        */

        //更改支付状态
        OrderDto orderDto = new OrderDto();
        orderDto = orderService.findOne(orderId);
        orderService.paid(orderDto);
        orderService.finsh(orderDto);
        map.put("returnUrl", returnUrl);
        return new ModelAndView("pay/create1", map);

    }

    @PostMapping("/notify")
    public ModelAndView notity(@RequestBody String notifyData) {
        payService.notify(notifyData);

        return new ModelAndView("pay/success");
    }
}
