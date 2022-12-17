package com.example.spring.boot.use.rabbitmq.listener;

import com.example.spring.boot.use.rabbitmq.common.RabbitConst;
import com.example.spring.boot.use.rabbitmq.model.dto.MsgDTO;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * RabbitMQ消费者（短信）
 * 注：生产者、消费者一般不在同一服务中，这里为了方便写在了一起
 *
 * @author minus
 * @since 2022/12/18 2:31
 */
@Slf4j
@Component
// 使用queuesToDeclare属性，如果不存在则会创建队列；而queues属性，不存在则报错
// @RabbitListener也可单独写在方法上
// @RabbitListener写在类上，再配合方法上的@RabbitHandler，可以重载多个方法，不同类型的传参，系统会自动根据队列消息中的Java类型匹配到对应的方法上
@RabbitListener(queuesToDeclare = @Queue(RabbitConst.MSG_QUEUE_NAME))
public class RabbitMagConsumer {

    @RabbitHandler
    public void process(MsgDTO msgDTO, Message message, Channel channel) {
        // 处理发短信业务
        log.info("接收到队列消息，开始发送短信{}", msgDTO);

        // 手动确认（如果因为某种原因，没有成功手动确认的话，message会在队列中一致处于Unacked状态，直到消费者连接断开，message会被重新变为Ready状态）
        try {
            log.info("确认完成{}", message.getMessageProperties().getDeliveryTag());
            // deliveryTag：为一个自增的值，标识每一个message；multiple：true确认全部，false确认指定deliveryTag的消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            /*
                // 拒绝确认：用于当前消费者处理失败时（可能是某种原因出问题了），退回到消息队列，交给其它消费者处理
                channel.basicNack(deliveryTag, multiple, requeue);    // 拒绝，requeue：true则重新排队，false则丢弃
                channel.basicReject(deliveryTag, requeue);            // 同上，但是不能批量
             */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
