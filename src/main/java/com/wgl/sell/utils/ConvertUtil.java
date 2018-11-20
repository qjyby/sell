package com.wgl.sell.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wgl.sell.dto.OrderDto;
import com.wgl.sell.entity.OrderDetail;
import com.wgl.sell.entity.OrderMaster;
import com.wgl.sell.form.OrderForm;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertUtil {

    public static OrderDto convert(OrderMaster orderMaster) {
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster, orderDto);
        return orderDto;
    }

    public static List<OrderDto> convertList(List<OrderMaster> orderMasters) {
        return orderMasters.stream().map(e -> convert(e)).collect(Collectors.toList());
    }

    public static OrderDto orderFromToOrderDto(OrderForm orderForm) {
        Gson gson = new Gson();

        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerOpenid(orderForm.getOpenid());
        List<OrderDetail> orderDetails = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
        }.getType());
        orderDto.setOrderDetailList(orderDetails);
        return orderDto;
    }
}
