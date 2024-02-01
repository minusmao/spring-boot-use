package com.example.spring.boot.use.websocket.config;

import com.example.spring.boot.use.websocket.common.ApiConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket配置类（使用STOMP协议）
 *
 * @author minus
 * @since 2024/1/31 16:24
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker    // 注解开启STOMP协议来传输基于代理的消息，此时控制器支持使用@MessageMapping
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final Map<String, LocalDateTime> CONNECTED = new ConcurrentHashMap<>();

    /**
     * 设置消息代理
     *
     * @param registry 消息代理选项的注册表
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // topic用来广播，user用来实现点对点
        registry.enableSimpleBroker("/topic", "/user");
    }

    /**
     * 注册StompEndpoint，即开放节点
     *
     * @param registry 注册
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册两个STOMP的endpoint端点，分别用于广播和点对点
        registry.addEndpoint(ApiConst.API_URL + "/publicServer").setAllowedOriginPatterns("*").withSockJS();     // 广播
        registry.addEndpoint(ApiConst.API_URL + "/privateServer").setAllowedOriginPatterns("*").withSockJS();    // 点对点
    }

    /**
     * 监听连接事件
     *
     * @param event 事件
     */
    @EventListener
    public void handleWebSocketConnect(SessionConnectedEvent event) {
        StompHeaderAccessor wrap = StompHeaderAccessor.wrap(event.getMessage());
        CONNECTED.put(wrap.getSessionId(), LocalDateTime.now());
        log.info("Session[{}]已连接，当前连接数[{}]", wrap.getSessionId(), CONNECTED.size());
    }

    /**
     * 监听断开连接事件
     *
     * @param event 事件
     */
    @EventListener
    public void handleWebSocketDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor wrap = StompHeaderAccessor.wrap(event.getMessage());
        CONNECTED.remove(wrap.getSessionId());
        log.info("Session[{}]已断开，当前连接数[{}]", wrap.getSessionId(), CONNECTED.size());
    }

}
