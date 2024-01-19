package com.jkmodel.store.webSocket.handler;

import com.jkmodel.store.coupon.dto.Coupon;
import com.jkmodel.store.coupon.service.CouponService;
import com.jkmodel.store.webSocket.dto.CouponInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class CouponWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private CouponService couponService;

    @MessageMapping("/sendCoupon")
    public void sendCoupon(Map<String, Integer> coupons) {
        System.out.println("handler接收到的物件:" + coupons);
        List<CouponInfo> couponInfoList = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : coupons.entrySet()) {
            String couponNo = entry.getKey();
            Integer quantity = entry.getValue();
            Coupon resultCoupon = couponService.findById(Integer.valueOf(couponNo)).orElse(null);

            // 判斷是否在發送期限內，若有則送到前端
            if (resultCoupon != null && resultCoupon.getReleaseTime().isBefore(LocalDateTime.now())) {
                CouponInfo couponInfo = new CouponInfo();
                couponInfo.setCoupon(resultCoupon);
                couponInfo.setRemainingQuantity(quantity);

                couponInfoList.add(couponInfo);
            }

        }
        // 發送coupon訊息到("/sendCoupon")
        messagingTemplate.convertAndSend("/topic/coupons", couponInfoList);
        System.out.println("發送couponInfoList:" + couponInfoList);
    }

}
