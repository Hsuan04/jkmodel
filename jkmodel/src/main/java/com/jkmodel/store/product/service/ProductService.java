package com.jkmodel.store.product.service;

import com.jkmodel.store.product.QueryParams.ProductQueryParams;
import com.jkmodel.store.product.dto.Product;
import com.jkmodel.store.product.dto.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Product saveProductRequest(ProductRequest ProductRequest);

    Product updateProductRequest(Integer productNo,ProductRequest ProductRequest);

    Product update(Integer productNo, Product product);

    void deletById(Integer productNo);

    Product findById(Integer productNo);

    Page<Product> getProducts(String search, String category, Double minPrice, Double maxPrice, Pageable pageable);



}
