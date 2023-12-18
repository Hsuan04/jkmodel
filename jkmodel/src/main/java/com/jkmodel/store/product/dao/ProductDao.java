package com.jkmodel.store.product.dao;

import com.jkmodel.store.product.QueryParams.ProductQueryParams;
import com.jkmodel.store.product.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getProducts(ProductQueryParams productQueryParams);
}
