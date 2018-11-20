package com.wgl.sell.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.wgl.sell.dto.OrderDto;
import com.wgl.sell.enums.ResultEnum;
import com.wgl.sell.exception.SellException;
import com.wgl.sell.service.OrderService;
import com.wgl.sell.service.PayService;
import com.wgl.sell.utils.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PayServiceImpl implements PayService {
    private static final String ORDER_NAME = "微信公众号订单";

    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDto orderDto) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDto.getBuyerOpenid());
        payRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDto.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        PayResponse payResponse = bestPayService.pay(payRequest);
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        /*1.验证签名
         * 2.支付的状态
         * 3.支付的金额
         * 4.支付人*/
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        /*1.异步通知会，修改订单状态*/
        OrderDto orderDto = orderService.findOne(payResponse.getOrderId());
        /*判断订单是否存在*/
        if (orderDto == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }
        /*判断金额是否一致*/
        if (MathUtil.equals(payResponse.getOrderAmount(), orderDto.getOrderAmount().doubleValue())) {
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY);
        }
        orderService.finsh(orderDto);
        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDto orderDto) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        refundRequest.setOrderId(orderDto.getOrderId());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        RefundResponse refund = bestPayService.refund(refundRequest);
        return refund;
    }
}
