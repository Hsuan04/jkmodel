package com.jkmodel.store.config;

import com.jkmodel.store.webSocket.handler.CouponWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;



@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/coupon-websocket")
                .setAllowedOrigins("*")  // 允许所有来源
                .withSockJS();
    }

    @Bean(name = "CouponWebSocketHandler")
    public CouponWebSocketHandler couponWebSocketHandler() {
        return new CouponWebSocketHandler();
    }

}







