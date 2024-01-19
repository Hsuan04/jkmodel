package com.jkmodel.store.redis.service;

import com.jkmodel.store.coupon.dto.Coupon;
import com.jkmodel.store.webSocket.handler.CouponWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class CouponRedisService {

    @Autowired
    @Qualifier("redisTemplateDB03")
    private RedisTemplate<String, Object> redisTemplateDB03;

    @Autowired
    private CouponWebSocketHandler couponWebSocketHandler;

    public void saveToRedis(Coupon savedCoupon){
        String couponKey = String.valueOf(savedCoupon.getCouponNo());
        Integer quantity = savedCoupon.getQuantity();
        LocalDateTime releaseTime = savedCoupon.getReleaseTime();
        Integer validDays = savedCoupon.getValidDays();

        //判斷是否有發送天數
        if (validDays == null){
            redisTemplateDB03.opsForValue().set(couponKey, quantity);
        } else {
            redisTemplateDB03.opsForValue().set(couponKey, quantity);

            // 設定存活時間
            Duration timeToLive = Duration.between(LocalDateTime.now(), releaseTime.plusDays(validDays));
//          redisTemplateDB03.expire(couponKey, 6000000, TimeUnit.MILLISECONDS);
            redisTemplateDB03.expire(couponKey, timeToLive.toMillis(), TimeUnit.MILLISECONDS);
        }
    }

    // 每10分鐘觸發檢查優惠卷並調用 WebSocket
    @Scheduled(fixedRate = 10000) //10秒
    public void checkCouponExpiry() {
        Map<String, Integer> coupons = new HashMap<>();

        Set<String> keys = redisTemplateDB03.keys("*");
        if(keys.size() == 0){
            System.out.println("沒有優惠卷在redis裡面");
        }
        for (String key : keys) {
            Integer quantity = (Integer) redisTemplateDB03.opsForValue().get(key);
            coupons.put(key, quantity);
            System.out.println(coupons);
        }

        couponWebSocketHandler.sendCoupon(coupons);
    }


}
