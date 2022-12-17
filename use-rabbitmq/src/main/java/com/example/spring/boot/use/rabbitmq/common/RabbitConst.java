package com.example.spring.boot.use.rabbitmq.common;

/**
 * RabbitMQ的通用常量（路由键、绑定关系等）
 *
 * @author minus
 * @since 2022/12/17 22:44
 */
public class RabbitConst {

    public static final String EMAIL_QUEUE_NAME = "send.email.queue";
    public static final String EMAIL_EXCHANGE_NAME = "send.email.exchange";
    public static final String EMAIL_ROUTE_KEY = "send.email";

}
