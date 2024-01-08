package com.jkmodel.store.webSocket.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class CouponWebSocketHandler extends TextWebSocketHandler {

    private final SimpMessagingTemplate messagingTemplate;

    public CouponWebSocketHandler(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendCoupon")
    public void sendCoupon(String couponMessage) {
        System.out.println("sendCoupon方法接收到的訊息為:" + couponMessage);
        // 處理接收coupon消息，加入其他商業

        // 發送coupon訊息到指定位置
        messagingTemplate.convertAndSend("/topic/coupons", couponMessage);
    }



}
