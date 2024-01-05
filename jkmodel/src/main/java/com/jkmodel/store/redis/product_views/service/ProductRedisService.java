package com.jkmodel.store.redis.product_views.service;

import com.jkmodel.store.product.dto.Product;
import com.jkmodel.store.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductRedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ProductService productService;

    public String saveUserViews(String uuid, String productNo){

        Product product = productService.findById(Integer.valueOf(productNo));
        String category = product.getCategory();

        // 判斷 uuid 下是否已經有 category
        Boolean hasCategory = redisTemplate.opsForHash().hasKey(uuid, category);

        if (hasCategory) {
            // 如果有，則將對應的值加一
            Long views = redisTemplate.opsForHash().increment(uuid, category, 1);
            System.out.println("Incremented views for " + category + ": " + views);
        } else {
            // 如果沒有，則新增該 category
            redisTemplate.opsForHash().put(uuid, category, 1);
            System.out.println("Added new category: " + category);
        }

        return "success";
//
//        // 取得用戶的資料
//        Map<Object, Object> userData = redisTemplate.opsForHash().entries(hashKey);
//
//        if (userData.containsKey(category)) {
//            // 商品種類已存在，將觀看次數 +1
//            int currentViews = (int) userData.get(category);
//            userData.put(category, currentViews + 1);
//        } else {
//            // 商品種類不存在，新增一筆資料，觀看次數預設為 1
//            userData.put(category, 1);
//        }
//
//        // 更新 Hash 中的值
//        redisTemplate.opsForHash().putAll(hashKey, userData);
//
//        return "Views updated successfully";



        // 取得 Hash 中的 JSON 資料
//        Object storedData = redisTemplate.opsForHash().get(hashKey, productNo);
//
//        if (storedData != null) {
//
//            // 更新 views
//            int currentViews = ((UserViewsRequest) storedData).getViews();
//            ((UserViewsRequest) storedData).setViews(currentViews + 1);
//
//            // 更新 Hash 中的值
//            redisTemplate.opsForHash().put(hashKey, productNo, storedData);
//
//            return "Views updated successfully";
//        } else {
//            // 創建用戶訊息
//            UserViewsRequest userInfo = new UserViewsRequest();
//            userInfo.setUuid(hashKey);
//            userInfo.setProductNo(productNo);
//            userInfo.setCategory(category);
//            userInfo.setViews(1);
//
//            // 修改 RedisTemplate 的 value 序列化器为 GenericJackson2JsonRedisSerializer
//            redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//
//            // 存進redis
//            redisTemplate.opsForHash().put(hashKey, productNo, userInfo);
//
//            return "New userInfo added successfully";
//        }
//
    }

}
