package com.jkmodel.store.product.dao;

import com.jkmodel.store.product.dto.ProductRequest;
import com.jkmodel.store.product.model.Product;

import java.util.List;

public interface ProductDao {

    Product getByProductNo(Integer productNo);

    Integer insert(ProductRequest productRequest);

}
