package com.jkmodel.store.product.service;

import com.jkmodel.store.product.model.Product;

public interface ProductService {

    void save(Product product);

    void deleteById(Integer productNo);

    Product getById(Integer productNo);
    


}
