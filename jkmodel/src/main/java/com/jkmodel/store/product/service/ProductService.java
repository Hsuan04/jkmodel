package com.jkmodel.store.product.service;

import com.jkmodel.store.product.QueryParams.ProductQueryParams;
import com.jkmodel.store.product.model.Product;

import java.util.List;

public interface ProductService {

    Product findById(Integer productNo);

    Product save(Product product);

    void deletById(Integer productNo);

    Product update(Integer productNo, Product product);

    List<Product> getProducts(ProductQueryParams productQueryParams);
}
