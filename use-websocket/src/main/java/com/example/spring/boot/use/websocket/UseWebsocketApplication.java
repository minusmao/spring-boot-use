package com.example.spring.boot.use.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 整合websocket<br>
 * 原生方式：<a href="https://how2j.cn/k/springboot/springboot-websocket/2519.html">参考How2J教程</a><br>
 * 结合Stomp协议：<a href="https://developer.aliyun.com/article/953288">Springboot整合WebSocket，使用STOMP协议</a><br>
 * WebSocket和Stomp协议：<a href="https://www.jianshu.com/p/db21502518b9">WebSocket和Stomp协议</a><br>
 *
 * @author minus
 * @since 2022/12/7 23:21
 */
@SpringBootApplication
public class UseWebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(UseWebsocketApplication.class, args);
    }

}
