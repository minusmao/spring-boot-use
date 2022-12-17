package com.example.spring.boot.use.rabbitmq.config;

import com.example.spring.boot.use.rabbitmq.common.RabbitConst;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置（邮件相关的交换机、队列）
 *
 * @author minus
 * @since 2022/12/17 22:53
 */
@Configuration
public class RabbitEmailConfig {

    @Bean
    public Queue emailQueue() {
        /*
           1、name: 队列名称
           2、durable: 是否持久化
           3、exclusive: 是否独享、排外的。如果设置为true，定义为排他队列。则只有创建者可以使用此队列。也就是private私有的。
           4、autoDelete: 是否自动删除。也就是临时队列。当最后一个消费者断开连接后，会自动删除。
         */
        return new Queue(RabbitConst.EMAIL_QUEUE_NAME, true, false, false);
    }

    @Bean
    public DirectExchange emailExchange() {
        /*
           1、name: 交换机名称
           2、durable: 是否持久化
           3、autoDelete: 是否自动删除
         */
        return new DirectExchange(RabbitConst.EMAIL_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Binding bindDirect() {
        return BindingBuilder
                .bind(emailQueue())     // 队列
                .to(emailExchange())    // 交换机
                .with(RabbitConst.EMAIL_ROUTE_KEY);    // 路由键
    }

}
