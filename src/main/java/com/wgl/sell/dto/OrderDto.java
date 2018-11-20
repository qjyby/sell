package com.wgl.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wgl.sell.entity.OrderDetail;
import com.wgl.sell.entity.OrderMaster;
import com.wgl.sell.enums.OrderStatusEnum;
import com.wgl.sell.enums.PayStatusEnum;
import com.wgl.sell.utils.EnumUtil;
import lombok.Data;

import java.util.List;

@Data
/*@JsonInclude(JsonInclude.Include.NON_NULL)*/
public class OrderDto extends OrderMaster {

    List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getBycode(getOrderStatus(), OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getBycode(getPayStatus(), PayStatusEnum.class);
    }
}
