package com.jkmodel.store.product.service;

import com.jkmodel.store.product.QueryParams.ProductQueryParams;
import com.jkmodel.store.product.dto.Product;
import com.jkmodel.store.product.dto.ProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    Product findById(Integer productNo);

//    Product save(Product product);

    void deletById(Integer productNo);

    Product update(Integer productNo, Product product);

    List<Product> getProducts(ProductQueryParams productQueryParams);

    void saveProductRequest(ProductRequest ProductRequest);
}
