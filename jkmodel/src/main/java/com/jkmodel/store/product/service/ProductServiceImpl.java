package com.jkmodel.store.product.service;


import com.jkmodel.store.product.model.Product;
import com.jkmodel.store.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product findById(Integer productNo) {
        Optional<Product> product = productRepository.findById(productNo);
        return product.orElse(null);
    }
}
