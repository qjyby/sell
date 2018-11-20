package com.wgl.sell.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wgl.sell.enums.ProductStatusEnum;
import com.wgl.sell.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@DynamicUpdate
public class ProductInfo {

    @Id
    private String productId;
    /*姓名*/
    private String productName;
    /*单价*/
    private BigDecimal productPrice;
    /*库存*/
    private Integer productStock;
    /*描述*/
    private String productDescription;
    /*小图*/
    private String productIcon;
    /*类目编号*/
    private int categoryType;
    /*商品状态*/
    private Integer productStatus = ProductStatusEnum.UP.getCode();
    private Integer createTime;
    private Integer updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum() {
        return EnumUtil.getBycode(productStatus, ProductStatusEnum.class);
    }
}