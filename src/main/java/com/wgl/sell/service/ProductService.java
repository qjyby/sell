package com.wgl.sell.service;

import com.wgl.sell.dto.CarDto;
import com.wgl.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    /*根据商品Id查找*/
    ProductInfo findOne(String productId);

    /*查询所有上架商品*/
    List<ProductInfo> findUpAll();


    /*增加商品*/
    ProductInfo save(ProductInfo productInfo);

    /*带分页查询所有商品*/
    Page<ProductInfo> findAll(Pageable pageable);

    /*增加库存*/
    void increaseStock(List<CarDto> carDtoList);

    /*减烧库存*/
    void decreaseStock(List<CarDto> carDtoList);

    public ProductInfo onSale(String productId);

    public ProductInfo offSale(String proiductId);
}
