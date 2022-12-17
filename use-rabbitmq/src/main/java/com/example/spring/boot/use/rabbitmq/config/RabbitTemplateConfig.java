package com.example.spring.boot.use.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * RabbitTemplate配置
 *
 * @author minus
 * @since 2022/12/17 23:56
 */
@Slf4j
@Configuration
public class RabbitTemplateConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 定制RabbitTemple
     * 设置可靠投递的回调函数，通过回调函数就可以知道发送失败的消息了
     * confirmCallback -> 消息是否到达交换机的回调
     * returnCallback -> 消息未到达队列的回调
     */
    @PostConstruct
    public void initRabbitTemple() {
        // 设置消息是否到达交换机的回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             * 是否到达交换机的回调函数
             * @param correlationData 消息的唯一标识（可以在发消息时指定一个UUID，此处就可以获得这个UUID）
             * @param ack 消息是否成功收到
             * @param cause 失败的原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                    log.info("消息{}达到交换机", correlationData.getId());
                    // 执行到达情况的业务（如更新该消息的状态）
                } else {
                    log.warn("消息{}未到达交换机，原因{}", correlationData.getId(), cause);
                    // 执行未到达情况的业务（如更新该消息的状态）
                }
            }
        });
        // 设置消息未到达队列的回调
        rabbitTemplate.setReturnsCallback((returned) -> {
            log.warn("消息未到达队列：{}", returned);
//            returned.getMessage();       // 消息
//            returned.getReplyCode();     // 回应码
//            returned.getReplyText();     // 回应消息
//            returned.getExchange();      // 交换机
//            returned.getRoutingKey();    // 路由键
            // 执行未到达情况的业务（如更新该消息的状态）
        });
    }

}
