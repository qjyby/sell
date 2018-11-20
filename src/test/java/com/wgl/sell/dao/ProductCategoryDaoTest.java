package com.wgl.sell.dao;

import com.wgl.sell.entity.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {


    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void findOneTest() {
        Optional<ProductCategory> productCategory = productCategoryDao.findById(1);
        ProductCategory productCategory1 = productCategory.get();
        System.out.println(productCategory1);
    }

    @Test
    public void save() {
        ProductCategory productCategory = productCategoryDao.findById(2).get();
        productCategory.setCategoryType(10);
        productCategoryDao.save(productCategory);
    }

    @Test
    public void findbyCategoryTypeInTest() {
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(10);
        List<ProductCategory> productCategories = productCategoryDao.findByCategoryTypeIn(list);
        System.out.println(productCategories);

    }
}
