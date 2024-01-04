package com.jkmodel.store.redis.product_views.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkmodel.store.product.dto.Product;
import com.jkmodel.store.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class ProductRedisService {

    @Resource
    public RedisTemplate redisTemplate;

    @Value("${spring.redis.database}")
    private Integer database;

    @Autowired
    private ProductService productService;

    // 設定多久時間
    private static final long EXPIRATION_TIME_IN_SECONDS = 1800; // 30分鐘
    private static final long MINIMUM_TIME_DIFFERENCE = 10; // 10秒

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ================== 存取瀏覽次數的商業邏輯  ==================
    // 將商品添加到用戶的瀏覽歷史中
    public void addProductToHistory(String userIp, String productNo) {
        String userHistoryKey = userIp;

        // 檢查用戶是否存在
        if (!redisTemplate.hasKey(userHistoryKey)) {
            // 用戶不存在，創建一個新的用戶 key
            initUserHistory(userHistoryKey, productNo);
        } else {
            // 用戶已存在，更新瀏覽歷史
            updateProductView(userHistoryKey, productNo);
        }
    }

    private void initUserHistory(String userHistoryKey, String productNo) {
        Product product = productService.findById(Integer.valueOf(productNo));
        String category = product.getCategory();

        long currentTime = System.currentTimeMillis();

        // 將商品信息轉換為 JSON 字符串
        String productInfoJson = getProductInfoJson(productNo, category, 1, currentTime);

        // 使用opsForSet()方法將JSON字符串添加到集合中
        redisTemplate.opsForSet().add(userHistoryKey, productInfoJson);

        // 設定過期時間
        redisTemplate.expire(userHistoryKey, EXPIRATION_TIME_IN_SECONDS, TimeUnit.SECONDS);

        // 增加商品瀏覽次數
        incrementProductViewCount(productNo);
    }

    private void updateProductView(String userHistoryKey, String productNo) {
        Set<String> userHistorySet = redisTemplate.opsForSet().members(userHistoryKey);
        long currentTime = System.currentTimeMillis();

        // 判斷是否已存在相同的商品瀏覽紀錄
        String updatedProductInfoJson = null;
        for (String productInfoJson : userHistorySet) {
            if (isSameProduct(productInfoJson, productNo)) {
                // 更新瀏覽次數
                updatedProductInfoJson = updateProductViewCount(productInfoJson, currentTime);
                break;
            }
        }

        if (updatedProductInfoJson == null) {
            // 如果不存在相同的商品瀏覽紀錄，則添加新的瀏覽紀錄
            Product product = productService.findById(Integer.valueOf(productNo));
            String category = product.getCategory();
            updatedProductInfoJson = getProductInfoJson(productNo, category, 1, currentTime);

            // 使用opsForSet()方法將JSON字符串添加到集合中
            redisTemplate.opsForSet().add(userHistoryKey, updatedProductInfoJson);
        }

        // 將商品信息存入Hash
        Map<String, String> productInfoMap = getProductInfoMapFromJson(updatedProductInfoJson);

        // 使用opsForHash()方法將HashMap存入Redis
        redisTemplate.opsForHash().putAll(userHistoryKey, productInfoMap);

        // 設定過期時間
        redisTemplate.expire(userHistoryKey, EXPIRATION_TIME_IN_SECONDS, TimeUnit.SECONDS);

        // 增加商品瀏覽次數
        incrementProductViewCount(productNo);
    }

    private boolean isSameProduct(String productInfoJson, String productNo) {
        try {
            Map<String, String> productInfoMap = objectMapper.readValue(productInfoJson, Map.class);
            return productInfoMap.get("productNo").equals(productNo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String updateProductViewCount(String productInfoJson, long currentTime) {
        try {
            Map<String, String> productInfoMap = objectMapper.readValue(productInfoJson, Map.class);
            int viewCount = Integer.parseInt(productInfoMap.get("viewCount"));
            productInfoMap.put("viewCount", String.valueOf(viewCount + 1));
            productInfoMap.put("timestamp", String.valueOf(currentTime));
            return objectMapper.writeValueAsString(productInfoMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, String> getProductInfoMapFromJson(String productInfoJson) {
        try {
            return objectMapper.readValue(productInfoJson, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    private String getProductInfoJson(String productNo, String category, int viewCount, long timestamp) {
        Map<String, String> productInfoMap = new HashMap<>();
        productInfoMap.put("productNo", productNo);
        productInfoMap.put("category", category);
        productInfoMap.put("viewCount", String.valueOf(viewCount));
        productInfoMap.put("timestamp", String.valueOf(timestamp));
        try {
            return objectMapper.writeValueAsString(productInfoMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void incrementProductViewCount(String productNo) {
        String productViewCountKey = "product_view_count";

        // 增加商品瀏覽次數，設定每次新增就是1
        redisTemplate.opsForHash().increment(productViewCountKey, productNo, 1);

        // 可以在這裡觸發 WebSocket 通知
        // ...
    }

     //設定統計時間並且取得最多瀏覽的商品種類
    @Scheduled(fixedRate = 10000) // 10秒執行一次
    public void findMostViewedCategory() {
        // 修改這裡，使用 user_history:* 作為 key 的 pattern，獲取所有符合 pattern 的 keys
        Set<String> userHistoryKeys = redisTemplate.keys("user_history:*");

        Map<Object, Long> categoryViewCounts = new HashMap<>();
        String productViewCountKey = "product_view_count";

        for (String userHistoryKey : userHistoryKeys) {
            // 這裡假設 category 存在於 Hash 中
            Set<String> userHistorySet = redisTemplate.opsForSet().members(userHistoryKey + ":products");

            for (String productInfoJson : userHistorySet) {
                Long viewCount = Long.parseLong(getProductInfoMapFromJson(productInfoJson).get("viewCount"));

                // 考慮到 user_history:* 中有多個商品，需要累加同一 category 的 viewCount
                String category = getProductInfoMapFromJson(productInfoJson).get("category");
                categoryViewCounts.put(category, categoryViewCounts.getOrDefault(category, 0L) + viewCount);
            }
        }

        // 找到最多瀏覽次數的商品種類
        Object mostViewedCategory = Collections.max(categoryViewCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
        System.out.println("Most viewed category: " + mostViewedCategory);
    }
}
