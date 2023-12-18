package com.jkmodel.store.product.service;

import com.jkmodel.store.product.dao.ProductDao;
import com.jkmodel.store.product.dto.ProductRequest;
import com.jkmodel.store.product.model.Product;
import com.jkmodel.store.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductServiceImpl{

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findALL(){
        Iterable<Product> productIterable = productRepository.findAll();
        List<Product> products = new ArrayList<>();

        for (Product product : productIterable) {
            products.add(product);
        }
        return products;
    }

}
