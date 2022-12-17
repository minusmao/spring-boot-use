package com.example.spring.boot.use.rabbitmq.config;

import com.example.spring.boot.use.rabbitmq.common.RabbitConst;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMQ配置（短信相关的交换机、队列）
 * 测试延时队列
 * 延时也可单独给消息Message设置存活时间，但是存在问题：就是先入的消息没过期的话，后入的消息即时过期了，也许要等先入的消息过期
 *
 * @author minus
 * @since 2022/12/18 1:59
 */
@Configuration
public class RabbitMsgConfig {

    /**
     * 延时队列，设置了消息TTL存活时间
     */
    @Bean
    public Queue msgDelayQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", RabbitConst.MSG_EXCHANGE_NAME);        // 设置死信转发的交换机
        arguments.put("x-dead-letter-routing-key", RabbitConst.MSG_DEAD_ROUTE_KEY);    // 设置死信的路由键，路由到消费者队列
        arguments.put("x-message-ttl", 60000);    // 设置TTL时间，单位：毫秒

        return new Queue(RabbitConst.MSG_DELAY_QUEUE_NAME, true, false, false, arguments);
    }

    /**
     * 普通消费者队列
     */
    @Bean
    public Queue msgQueue() {
        /*
           1、name: 队列名称
           2、durable: 是否持久化
           3、exclusive: 是否独享、排外的。如果设置为true，定义为排他队列。则只有创建者可以使用此队列。也就是private私有的。
           4、autoDelete: 是否自动删除。也就是临时队列。当最后一个消费者断开连接后，会自动删除。
         */
        return new Queue(RabbitConst.MSG_QUEUE_NAME, true, false, false);
    }

    /**
     * 交换机
     */
    @Bean
    public DirectExchange msgExchange() {
        /*
           1、name: 交换机名称
           2、durable: 是否持久化
           3、autoDelete: 是否自动删除
         */
        return new DirectExchange(RabbitConst.MSG_EXCHANGE_NAME, true, false);
    }

    /**
     * 设置延时队列绑定关系
     */
    @Bean
    public Binding bindDelayMsg() {
        return new Binding(
                RabbitConst.MSG_DELAY_QUEUE_NAME,   // 目的地：延时队列
                Binding.DestinationType.QUEUE,      // 目的地类型
                RabbitConst.MSG_EXCHANGE_NAME,      // 交换机
                RabbitConst.MSG_ROUTE_KEY,          // 绑定路由键
                null                      // 参数
        );
    }

    /**
     * 设置绑定关系
     */
    @Bean
    public Binding bindMsg() {
        return new Binding(
                RabbitConst.MSG_QUEUE_NAME,         // 目的地：消费者队列
                Binding.DestinationType.QUEUE,      // 目的地类型
                RabbitConst.MSG_EXCHANGE_NAME,      // 交换机
                RabbitConst.MSG_DEAD_ROUTE_KEY,     // 绑定路由键：死信路由键
                null                      // 参数
        );
    }


}
