package com.wgl.sell.service.impl;

import com.wgl.sell.controller.WebSocket;
import com.wgl.sell.dao.OrderDetailDao;
import com.wgl.sell.dao.OrderMasterDao;
import com.wgl.sell.dto.CarDto;
import com.wgl.sell.dto.OrderDto;
import com.wgl.sell.entity.OrderDetail;
import com.wgl.sell.entity.OrderMaster;
import com.wgl.sell.entity.ProductInfo;
import com.wgl.sell.enums.OrderStatusEnum;
import com.wgl.sell.enums.PayStatusEnum;
import com.wgl.sell.enums.ResultEnum;
import com.wgl.sell.exception.SellException;
import com.wgl.sell.service.*;
import com.wgl.sell.utils.ConvertUtil;
import com.wgl.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Autowired
    private PayService payService;

    @Autowired
    private PushMessage pushMessage;

    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDto create(OrderDto orderDto) {
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        /*查询商品，数量，价格*/
        for (OrderDetail orderDetail : orderDto.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            /*计算总价*/
            orderAmount = productInfo.getProductPrice().
                    multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
            /*订单详情入库*/
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetailDao.save(orderDetail);
        }
        /*订单主表写入数据库*/
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        orderDto.setOrderId(orderId);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMasterDao.save(orderMaster);
        /*扣除库存*/
        List<CarDto> carDtoList = orderDto.getOrderDetailList().stream().map(e ->
                new CarDto(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productService.decreaseStock(carDtoList);
        webSocket.sendMessage(orderDto.getOrderId());
        return orderDto;
    }

    @Override
    public OrderDto findOne(String orderId) {

        OrderMaster orderMaster;
        try {
            orderMaster = orderMasterDao.findById(orderId).get();
        } catch (Exception e) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }

        List<OrderDetail> list = orderDetailDao.findByOrderId(orderId);
        if (list.size() == 0) {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIT);
        }
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster, orderDto);
        orderDto.setOrderDetailList(list);
        return orderDto;
    }

    @Override
    public Page<OrderDto> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderDtos = orderMasterDao.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDto> orderDtoList = ConvertUtil.convertList(orderDtos.getContent());
        Page<OrderDto> orderDtoPage = new PageImpl<OrderDto>(orderDtoList, pageable, orderDtos.getTotalElements());
        return orderDtoPage;
    }

    @Override
    @Transactional
    public OrderDto cancel(OrderDto orderDto) {
        OrderMaster orderMaster = new OrderMaster();
        /*判断订单状态*/
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.ENUM.getCode())) {
            log.error("订单状态异常，orderId={},orderStatus={}", orderDto.getOrderId(), orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        /*修改订单状态*/
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster orderMasterUp = orderMasterDao.save(orderMaster);
        if (orderMasterUp == null) {
            log.error("取消订单失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        /*返回库存*/
        if (orderDto.getOrderDetailList().size() == 0) {
            log.error("订单无商品详情，orderDto={}", orderDto);
        }
        List<CarDto> carDtoList = orderDto.getOrderDetailList().stream().map(e ->
                new CarDto(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(carDtoList);
        /*如果已支付，需要退款*/
        if (orderDto.getOrderStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            payService.refund(orderDto);
        }
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto finsh(OrderDto orderDto) {
        /*1.判断状态*/
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.ENUM.getCode())) {
            log.error("订单状态异常 orderid={}", orderDto.getOrderId());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        /*2.修改状态*/
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster orderMasterUp = orderMasterDao.save(orderMaster);
        if (orderMasterUp == null) {
            log.error("完结订单失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        /*推送微信模板 */
    /*    pushMessage.orderStatus(orderDto);*/
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto paid(OrderDto orderDto) {
        /*判断订单状态*/
        /*1.判断状态*/
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.ENUM.getCode())) {
            log.error("订单状态异常 orderid={}", orderDto.getOrderId());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        /*判断支付状态*/
        /*1.判断状态*/
        if (!orderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("订单状态异常 orderid={}", orderDto.getOrderId());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        /*修改支付状态*/
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster orderMasterUp = orderMasterDao.save(orderMaster);
        if (orderMasterUp == null) {
            log.error("支付订单失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDto;
    }

    @Override
    public Page<OrderDto> findList(Pageable pageable) {

        Page<OrderMaster> orderMasters = orderMasterDao.findAll(pageable);
        List<OrderDto> orderDtoList = ConvertUtil.convertList(orderMasters.getContent());
        Page<OrderDto> orderDtoPage = new PageImpl<>(orderDtoList, pageable, orderMasters.getTotalElements());
        return orderDtoPage;
    }
}
