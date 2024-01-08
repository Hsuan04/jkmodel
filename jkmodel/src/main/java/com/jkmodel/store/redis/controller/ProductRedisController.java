package com.jkmodel.store.redis.controller;

import com.jkmodel.store.product.dto.Product;
import com.jkmodel.store.redis.dto.UserViewsRequest;
import com.jkmodel.store.redis.service.ProductRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductRedisController {

    @Autowired
    private ProductRedisService productRedisService;

    // 紀錄用戶習慣
    @PostMapping("/addUuidAndView")
    public List<Product> addUuidAndView(@RequestBody UserViewsRequest userViewsRequest) {

        System.out.println("UUID:" + userViewsRequest.getUuid());
        System.out.println("productNo:" + userViewsRequest.getProductNo());

        // json取得參數
        String uuid = userViewsRequest.getUuid();
        String productNo = userViewsRequest.getProductNo();

        List<Product> topProducts = productRedisService.saveUserViews(uuid, productNo);
        System.out.println("回傳topProducts給前端");

        return topProducts;
    }

}
