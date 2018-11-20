package com.wgl.sell.dto;

import lombok.Data;

@Data
public class CarDto {
    /*商品ID*/
    private String productId;
    /*商品数量*/
    private Integer productQuantity;

    public CarDto(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
