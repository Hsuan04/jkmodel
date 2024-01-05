package com.jkmodel.store.redis.product_views.service;

import com.jkmodel.store.product.dto.Product;
import com.jkmodel.store.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class ProductRedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ProductService productService;

    public String saveUserViews(String uuid, String productNo){

        Product product = productService.findById(Integer.valueOf(productNo));
        String category = product.getCategory();

        // 判斷 uuid 是否已經有 category
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

        // 設定自動刪除時間
        redisTemplate.expire(uuid, 30, TimeUnit.MINUTES);
        System.out.println("Set expiration for " + uuid + " to 30 minutes");

        // 取得最多瀏覽次數的種類
        String maxViewCategory = getMaxViewCategory(uuid);

        if(maxViewCategory == null){
            System.out.println("有一樣的觀看次數");
            return "same views in database";
        }
        System.out.println(maxViewCategory);

        return "success";
    }

    // 判斷最多瀏覽次數的種類
    public String getMaxViewCategory(String uuid) {
        // 取得該 uuid 的所有 map
        Map<Object, Object> hashEntries = redisTemplate.opsForHash().entries(uuid);

        if (!hashEntries.isEmpty()) {
            //初始化
            int maxViews = -1;
            String maxCategory = null;

            // 遍歷所有商品種類及其對應的 views
            for (Map.Entry<Object, Object> entry : hashEntries.entrySet()) {
                String category = (String) entry.getKey();  // 取得商品種類
                int views = (int) entry.getValue();  // 取得對應的 views

                // 判斷最多瀏覽次數
                if (views > maxViews) {
                    maxViews = views;
                    maxCategory = category;
                } else if (views == maxViews) {
                    return null;
                }
            }
            // 返回最大 views 對應的 category
            return maxCategory;
        }

        // 如果沒有商品種類，返回 null
        return null;
    }

    // 計時刪除該 uuid 的資料


}
