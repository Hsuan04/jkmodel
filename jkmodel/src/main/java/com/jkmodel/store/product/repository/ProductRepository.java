package com.jkmodel.store.product.repository;

import com.jkmodel.store.product.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Override
    Optional<Product> findById(Integer productNo);

}
