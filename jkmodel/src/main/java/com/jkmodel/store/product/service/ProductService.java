package com.jkmodel.store.product.service;

import com.jkmodel.store.product.dto.ProductRequest;
import com.jkmodel.store.product.model.Product;

public interface ProductService {

    Product getProductByNo(Integer productNo);

    Integer insert(ProductRequest productRequest);
}
