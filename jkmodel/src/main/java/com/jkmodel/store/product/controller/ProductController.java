package com.jkmodel.store.product.controller;

import com.jkmodel.store.product.dto.ProductRequest;
import com.jkmodel.store.product.model.Product;
import com.jkmodel.store.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    //取得單一商品
    @GetMapping("/products/{productNo}")
    public ResponseEntity<Product> getProductByNo(@PathVariable Integer productNo) {
        Product product = productService.getProductByNo(productNo);
        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //新增商品
    @PostMapping("/products/{productNo}")
    public ResponseEntity<Product> insert(@RequestBody @Valid ProductRequest productRequest){
        Integer productNo = productService.insert(productRequest);

        Product product = productService.getProductByNo(productNo);

        return  ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

}
