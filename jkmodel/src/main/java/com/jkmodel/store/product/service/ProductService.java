package com.jkmodel.store.product.service;

import com.jkmodel.store.product.QueryParams.ProductQueryParams;
import com.jkmodel.store.product.dto.Product;
import com.jkmodel.store.product.dto.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Product findById(Integer productNo);

    Product findProduct(Integer productNo);

//    Product save(Product product);

    void deletById(Integer productNo);

    Product update(Integer productNo, Product product);

//    List<Product> getProducts(ProductQueryParams productQueryParams);
    Page<Product> getProducts(String search, String category, Integer minPrice, Integer maxPrice, Pageable pageable);

    Product saveProductRequest(ProductRequest ProductRequest);

    Product updateProductRequest(Integer productNo,ProductRequest ProductRequest);
}
