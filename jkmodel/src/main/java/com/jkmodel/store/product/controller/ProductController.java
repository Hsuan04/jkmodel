package com.jkmodel.store.product.controller;

import com.jkmodel.store.product.QueryParams.ProductQueryParams;
import com.jkmodel.store.product.dto.Product;
import com.jkmodel.store.product.dto.ProductRequest;
import com.jkmodel.store.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.List;


@RestController
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 新增商品，接收圖片文件
    @PostMapping("/products")
    public ResponseEntity<String> save(@ModelAttribute ProductRequest ProductRequest) {
        System.out.println("有呼叫controller save方法");
        productService.saveProductRequest(ProductRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //更新商品
    @PutMapping("/products/{productNo}")
    public ResponseEntity<Product> update(@PathVariable Integer productNo,
                                          @RequestBody Product product) {
        Product updatedProduct = productService.update(productNo, product);
        System.out.println("執行product update方法");
        if (updatedProduct != null) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //刪除商品
    @DeleteMapping("/products/{productNo}")
    public ResponseEntity<Product> deletById(@PathVariable Integer productNo){
        System.out.println("執行product deletById方法");
        productService.deletById(productNo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //取得單一商品
    @GetMapping("/products/{productNo}")
    public ResponseEntity<Product> findById(@PathVariable Integer productNo){
        System.out.println("執行product findById方法");
        Product product = productService.findById(productNo);
        System.out.println("商品名稱為" + product.getName());
        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //取得所有商品(搜尋、種類、排序)
    @GetMapping("/products")
    public ResponseEntity<Iterable<Product>> findAll(
            @RequestParam(required = false) String search,                      //關鍵字
            @RequestParam(required = false) String category,                    //種類條件
            @RequestParam(defaultValue = "lastModifiedTime") String orderBy,         //預設排序條件
            @RequestParam(defaultValue = "desc") String sort,                   //預設排序方式
            @RequestParam(defaultValue = "12") @Max(1000) @Min(0) Integer limit, //呈現資料筆數
            @RequestParam(defaultValue = "0") @Min(0) Integer offset){          //跳過幾筆資料

        System.out.println("執行product findAll方法");

        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setSearch(search);
        productQueryParams.setCategory(category);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        Iterable<Product> products = productService.getProducts(productQueryParams);
        if(products != null){
            return ResponseEntity.status(HttpStatus.OK).body(products);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

}
