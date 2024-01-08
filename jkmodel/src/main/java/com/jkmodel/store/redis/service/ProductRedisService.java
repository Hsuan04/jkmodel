package com.jkmodel.store.redis.service;

import com.jkmodel.store.product.dto.Product;
import com.jkmodel.store.product.repository.ProductRepository;
import com.jkmodel.store.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class ProductRedisService {

    @Autowired
    @Qualifier("redisTemplateDB01")
    private RedisTemplate<String, Object> redisTemplateDB01;

    @Autowired
    @Qualifier("redisTemplateDB02")
    private RedisTemplate<String, Object> redisTemplateDB02;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    public List<Product> saveUserViews(String uuid, String productNo){

        // 存各個商品觀看次數
        saveProductView(productNo);

        Product product = productService.findById(Integer.valueOf(productNo));
        String category = product.getCategory();

        // 判斷 uuid 是否已經有 category
        Boolean hasCategory = redisTemplateDB01.opsForHash().hasKey(uuid, category);

        if (hasCategory) {
            // 如果有，則將對應的值加一
            Long views = redisTemplateDB01.opsForHash().increment(uuid, category, 1);
            System.out.println("Incremented views for " + category + ": " + views);
        } else {
            // 如果沒有，則新增該 category
            redisTemplateDB01.opsForHash().put(uuid, category, 1);
            System.out.println("Added new category: " + category);
        }

        // 設定自動刪除時間
        redisTemplateDB01.expire(uuid, 30, TimeUnit.MINUTES);
        System.out.println("Set expiration for " + uuid + " to 30 minutes");

        // 取得最多瀏覽次數的種類
        String maxViewCategory = getMaxViewCategory(uuid);

        if(maxViewCategory == null){
            System.out.println("有一樣的觀看次數");
            List<Product> topProducts = productRepository.findTopProducts(PageRequest.of(0, 6));
            return topProducts;
        }
        System.out.println(maxViewCategory);
        List<Product> topProductsByCategory = productRepository.findTopProductsByCategory(maxViewCategory, PageRequest.of(0, 6));

        return topProductsByCategory;
    }

    // 判斷最多瀏覽次數的種類
    public String getMaxViewCategory(String uuid) {
        // 取得該 uuid 的所有 map
        Map<Object, Object> hashEntries = redisTemplateDB01.opsForHash().entries(uuid);

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

    // 存取網站商品觀看次數
    public void saveProductView(String productNo) {
        String key = "productViews";
        redisTemplateDB02.opsForZSet().incrementScore(key, productNo, 1.0);
    }

    // 獲取當日當前熱門商品
    public Set<Object> getTopProducts(int count) {
        String key = "productViews";
        return redisTemplateDB02.opsForZSet().reverseRange(key, 0, count - 1);
    }

    // 清除前一天商品瀏覽資訊
    @Scheduled(cron = "0 0 0 * * ?")
    public void clearDb02DataScheduled() {
        String key = "productViews";
    }

}
