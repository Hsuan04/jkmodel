package com.jkmodel.store.product.service;

import com.jkmodel.store.product.dao.ProductDao;
import com.jkmodel.store.product.dto.ProductRequest;
import com.jkmodel.store.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements  ProductService{

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductByNo(Integer productNo) {
        return productDao.getByProductNo(productNo);
    }

    @Override
    public Integer insert(ProductRequest productRequest) {
        return productDao.insert(productRequest);
    }
}
