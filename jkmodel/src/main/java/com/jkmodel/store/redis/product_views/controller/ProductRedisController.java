package com.jkmodel.store.redis.product_views.controller;

import com.jkmodel.store.product.dto.Product;
import com.jkmodel.store.product.service.ProductService;
import com.jkmodel.store.redis.product_views.dto.UserViewsRequest;
import com.jkmodel.store.redis.product_views.service.ProductRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRedisController {

    @Autowired
    private ProductRedisService productRedisService;

    // 紀錄用戶習慣
    @PostMapping("/testRedis")
    public String testResdis(@RequestBody UserViewsRequest userViewsRequest) {

        System.out.println("UUID:" + userViewsRequest.getUuid());
        System.out.println("productNo:" + userViewsRequest.getProductNo());

        // json取得參數
        String uuid = userViewsRequest.getUuid();
        String productNo = userViewsRequest.getProductNo();

        productRedisService.saveUserViews(uuid, productNo);

        return "success save userInfo";


    }
}
