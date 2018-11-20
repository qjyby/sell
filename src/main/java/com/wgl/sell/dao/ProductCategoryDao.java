package com.wgl.sell.dao;

import com.wgl.sell.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ProductCategoryDao extends JpaRepository<ProductCategory, Integer> {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryList);
}
