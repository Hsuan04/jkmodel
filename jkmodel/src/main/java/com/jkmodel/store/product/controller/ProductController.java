package com.jkmodel.store.product.controller;

import com.jkmodel.store.product.QueryParams.ProductQueryParams;
import com.jkmodel.store.product.dto.Product;
import com.jkmodel.store.product.dto.ProductRequest;
import com.jkmodel.store.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 新增商品
    @PostMapping("/products")
    public ResponseEntity<?> save(@ModelAttribute @Valid ProductRequest productRequest,
                                       BindingResult bindingResult) {
//        System.out.println("有呼叫controller save方法");

        //錯誤驗證
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(errors);
        }

        // 新增商品
        productService.saveProductRequest(productRequest);

        // 建立成功新增商品訊息
        Map<String, String> succesMsg = new HashMap<>();
        succesMsg.put("message", "成功新增商品");

        // 設定定向到網頁網址
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("https://www.google.com"));
        return new ResponseEntity<>(succesMsg, headers, HttpStatus.FOUND);
    }

    //更新商品
    @PutMapping("/products/{productNo}")
    public ResponseEntity<?> update(@PathVariable Integer productNo,
                                    @ModelAttribute @Valid ProductRequest productRequest,
                                    BindingResult bindingResult) {
//        System.out.println("有呼叫controller update方法");

        //錯誤驗證
        if (bindingResult.hasErrors()) {
            System.out.println("有執行錯誤驗證");
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(errors);
        }
        productService.updateProductRequest(productNo, productRequest);
        return null;
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
        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //取得所有商品(搜尋、種類、排序)
//    @GetMapping("/products")
//    public ResponseEntity<Iterable<?>> findAll(
//            @RequestParam(required = false) String search,                      //關鍵字
//            @RequestParam(required = false) String category,                    //種類條件
//            @RequestParam(defaultValue = "lastModifiedTime") String orderBy,    //預設排序條件
//            @RequestParam(defaultValue = "desc") String sort,                   //預設排序方式
//            @RequestParam(defaultValue = "12") @Max(1000) @Min(0) Integer limit,//呈現資料筆數
//            @RequestParam(defaultValue = "0") @Min(0) Integer offset,           //跳過幾筆資料
//            @RequestParam(required = false) @Min(0) Integer minPrice,            // 最低價格
//            @RequestParam(required = false) @Min(0) Integer maxPrice){           // 最高價格
//
//        System.out.println("執行product findAll方法");
//        System.out.println("關鍵字是："+ search);
//
//        ProductQueryParams productQueryParams = new ProductQueryParams();
//        productQueryParams.setSearch(search);
//        productQueryParams.setCategory(category);
//        productQueryParams.setOrderBy(orderBy);
//        productQueryParams.setSort(sort);
//        productQueryParams.setLimit(limit);
//        productQueryParams.setOffset(offset);
//        productQueryParams.setMinPrice(minPrice);
//        productQueryParams.setMaxPrice(maxPrice);
//
//        Iterable<Product> products = productService.getProducts(productQueryParams);
//        System.out.println("controller返回資料");
//        if(products != null){
//            return ResponseEntity.status(HttpStatus.OK).body(products);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }

    @GetMapping("/products")
    public ResponseEntity<Iterable<Product>> findAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "lastModifiedTime") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(defaultValue = "12") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice) {

        System.out.println("執行 product findAll 方法");
        System.out.println("關鍵字是：" + search);

        // pageRequest 分頁與排序
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.Direction.fromString(sort), orderBy);

        // service 執行條件查詢
        Page<Product> productPage = productService.getProducts(search, category, minPrice, maxPrice, pageRequest);

        System.out.println("controller 返回資料");
        if (productPage.hasContent()) {
            return ResponseEntity.status(HttpStatus.OK).body(productPage.getContent());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
