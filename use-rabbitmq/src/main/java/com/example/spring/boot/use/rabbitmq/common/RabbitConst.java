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

    public static final String MSG_EXCHANGE_NAME = "send.msg.exchange";
    public static final String MSG_DELAY_QUEUE_NAME = "send.msg.queue.delay";    // 延时队列，设置了消息TTL存活时间
    public static final String MSG_ROUTE_KEY = "send.msg";                       // 消息路由键，绑定延时队列
    public static final String MSG_QUEUE_NAME = "send.msg.queue";                // 消费者处理队列
    public static final String MSG_DEAD_ROUTE_KEY = "send.msg.delay";            // 死信路由键，绑定消费者处理队列

}
