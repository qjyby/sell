<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
<#--边栏-->
    <#include "../common/nav.ftl">
<#--主题内容-->
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-4 column">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>订单id</th>
                            <th>订单总金额</th>

                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                            ${orderDto.orderId}
                            </td>
                            <td>
                            ${orderDto.orderAmount}
                            </td>

                        </tbody>
                    </table>
                </div>
            </div>
        <#--订单详情-->
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>商品id</th>
                            <th>商品名称</th>
                            <th>单价</th>
                            <th>数量</th>
                            <th>总额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>   <#list  orderDto.orderDetailList as orderDetail>
                            <td>
                                ${orderDetail.productId}
                            </td>
                            <td>
                                ${orderDetail.productName}
                            </td>
                            <td>
                                ${orderDetail.productPrice}
                            </td>
                            <td>${orderDetail.productQuantity}
                            </td>
                            <td>${orderDetail.productQuantity * orderDetail.productPrice}
                            </td>
                </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        <#--操作-->
            <div class="col-md-12 column">
      <#if orderDto.getOrderStatusEnum().msg=="新订单">
          <a href="/sell/seller/order/finish?orderId=${orderDto.orderId}" type="button"
             class="btn btn-default btn-primary">接受订单</a>
          <a href="/sell/seller/order/cancel?orderId=${orderDto.orderId}" type="button"
             class="btn btn-default btn-danger">取消订单</a>
      </#if>
            </div>

        </div>
    </div>
</div>
</body>

</html>