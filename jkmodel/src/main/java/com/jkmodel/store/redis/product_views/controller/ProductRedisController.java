package com.jkmodel.store.redis.product_views.controller;

import com.jkmodel.store.redis.product_views.service.ProductRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ProductRedisController {

    @Autowired
    private ProductRedisService productRedisService;

    @GetMapping("/addProductView/{productNo}")
    public String addProductView(@PathVariable String productNo, HttpServletRequest request) {

        System.out.println("有呼叫到 addProductView");

        // 取得用戶IP
        String userIp = request.getRemoteAddr();
        System.out.println("userIp:" + userIp);

        // 調用ProductService中的addProductToHistory方法，將商品添加到用戶的瀏覽歷史中
        productRedisService.addProductToHistory(userIp, productNo);

        return "新增成功";
    }


    // ================== 設置哪個db  ==================
//    private void setDatabaseIndex(Integer dbIndex){
//        if(dbIndex == null || dbIndex > 15 || dbIndex < 0){
//            dbIndex = database;
//        }
//        LettuceConnectionFactory redisConnectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
//        if (redisConnectionFactory == null){
//            return;
//        }
//        redisConnectionFactory.setDatabase(dbIndex);
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        redisConnectionFactory.afterPropertiesSet();
//        redisConnectionFactory.resetConnection();;
//    }
//
//    public <T> void save(final String key, final T value){
//        redisTemplate.opsForValue().set(key, value);
//    }
//
//    public <T> void save(Integer index, final String key, final T value){
//        setDatabaseIndex(index);
//        redisTemplate.opsForValue().set(key, value);
//    }



}

