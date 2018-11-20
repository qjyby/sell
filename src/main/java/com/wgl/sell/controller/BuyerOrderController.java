package com.wgl.sell.controller;

import com.wgl.sell.dto.OrderDto;
import com.wgl.sell.enums.ResultEnum;
import com.wgl.sell.exception.SellException;
import com.wgl.sell.form.OrderForm;
import com.wgl.sell.service.BuyerService;
import com.wgl.sell.service.OrderService;
import com.wgl.sell.utils.ConvertUtil;
import com.wgl.sell.utils.ResultVoUtil;
import com.wgl.sell.vo.ResultVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/buyer/order")
@EnableSwagger2 //让Swagger2生成接口文档
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerService buyerService;

    /*创建订单*/
    @PostMapping("/create")
    @ApiOperation("创建订单")
    public ResultVo<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("参数不正确，orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARM_ERROR);
        }
        OrderDto orderDto = ConvertUtil.orderFromToOrderDto(orderForm);
        if (orderDto.getOrderDetailList().size() == 0) {
            log.error("购物车不能为空，");
            throw new SellException(ResultEnum.CART_ERROR);
        }
        OrderDto result = orderService.create(orderDto);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());
        return ResultVoUtil.success(map);
    }

    /*取消订单*/
    @ApiOperation("取消订单")
    @PostMapping("/cancel")
    public ResultVo cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderid") String orderid) {

        buyerService.cancelOrder(openid, orderid);
        return ResultVoUtil.success();
    }


    /*订单详情*/
    @ApiOperation("订单详情")
    @GetMapping("/detail")
    public ResultVo<OrderDto> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderid) {
        if (StringUtils.isEmpty(openid)) {
            log.error("openid不能为空");
            throw new SellException(ResultEnum.PARM_ERROR);
        }
        OrderDto orderDto;
        orderDto = buyerService.findOrderOne(openid, orderid);
        Long date = orderDto.getCreateTime().getTime() / 1000;
        Date date1 = new Date();
        date1.setTime(date);
        orderDto.setCreateTime(date1);
        return ResultVoUtil.success(orderDto);
    }

    /*订单列表*/
    @ApiOperation("订单列表")
    @GetMapping("/list")
    public ResultVo<List<OrderDto>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {

        if (StringUtils.isEmpty(openid)) {
            log.error("openid不能为空");
            throw new SellException(ResultEnum.PARM_ERROR);
        }
        Pageable pageable = new PageRequest(page, size);
        Page<OrderDto> result = orderService.findList(openid, pageable);
        return ResultVoUtil.success(result.getContent());

    }
}
