package com.jkmodel.store.product.service;


import com.jkmodel.store.product.QueryParams.ProductQueryParams;
import com.jkmodel.store.product.dao.ProductDao;
import com.jkmodel.store.product.model.Product;
import com.jkmodel.store.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDao productDao;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Integer productNo, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(productNo);

        Product savedProduct = null;

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            product.setProductNo(productNo);
            savedProduct = productRepository.save(product);
        }
        return savedProduct;
    }

    @Override
    public void deletById(Integer productNo) {
        productRepository.deleteById(productNo);
    }

    @Override
    public Product findById(Integer productNo) {
        Optional<Product> product = productRepository.findById(productNo);
        return product.orElse(null);
    }

    public List<Product> getProducts(ProductQueryParams productQueryParams){
        return productDao.getProducts(productQueryParams);
    }
}
