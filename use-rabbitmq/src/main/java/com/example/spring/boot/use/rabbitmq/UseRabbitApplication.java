package com.example.spring.boot.use.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 整合RabbitMQ服务
 * 参考文档：https://developer.aliyun.com/article/769883（详细RabbitMQ入门）
 * 参考文档：https://blog.csdn.net/qq_41712271/article/details/115625600（可靠性投递）
 *
 * @author minus
 * @since 2022/12/17 18:55
 */
@SpringBootApplication
public class UseRabbitApplication {

    public static void main(String[] args) {
        SpringApplication.run(UseRabbitApplication.class, args);
    }

}
