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

        String couponKey = "couponNo:" + savedCoupon.getCouponNo();  // 自行定義 key 的格式
        Integer quantity = savedCoupon.getQuantity();
        LocalDateTime releaseTime = savedCoupon.getReleaseTime();
        Integer ValidDays = savedCoupon.getValidDays();

        redisTemplateDB03.opsForHash().put(couponKey, couponKey, quantity);

        // 設定存活時間
        Duration timeToLive = Duration.between(LocalDateTime.now(), releaseTime.plusDays(ValidDays));
        redisTemplateDB03.expire(couponKey, timeToLive.toMillis(), TimeUnit.MILLISECONDS);

        System.out.println("timeToLive:" + timeToLive);
    }

    // 每10分鐘觸發檢查優惠卷並調用 WebSocket
//    @Scheduled(fixedRate = 600000)
    @Scheduled(fixedRate = 30000) //10秒
    public void checkCouponExpiry() {
        // 在這裡檢查 Redis 中的優惠券發送日期，並觸發相應的操作
        // 這裡可以調用 WebSocket 發送通知的邏輯
        Set<Object> couponNos = redisTemplateDB03.opsForHash().keys("couponNo");
        System.out.println("每30秒發送一次couponNos:" + couponNos);
        couponWebSocketHandler.sendCoupon("Hello Lawrence");
        // 發送消息給所有連接的客戶端
//        messagingTemplate.convertAndSend("/couponSocket", couponNos);
    }

    // 獲取優惠卷剩餘數量
    public int getRemainingQuantity(String couponId) {
        // 使用事務開始
//        redisTemplateDB03.multi();
        // 提交事務
//        redisTemplateDB03.exec();

        Object value = redisTemplateDB03.opsForHash().get("couponNo", couponId);
        return (value != null) ? (int) value : 0;
    }


}
