package com.example.spring.boot.use.rabbitmq.service.impl;

import com.example.spring.boot.use.rabbitmq.common.RabbitConst;
import com.example.spring.boot.use.rabbitmq.model.dto.MailDTO;
import com.example.spring.boot.use.rabbitmq.model.dto.MsgDTO;
import com.example.spring.boot.use.rabbitmq.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 测试
 *
 * @author minus
 * @since 2022/12/17 23:12
 */
@Slf4j
@Service
class TestServiceImpl implements TestService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void testSendMail(String sender, String receiver, String content) {
        // 消息的唯一标识，发布消息时使用，存储在消息的headers中
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        log.info("发送消息到邮件队列，消息id为{}", correlationData.getId());
        rabbitTemplate.convertAndSend(
                RabbitConst.EMAIL_EXCHANGE_NAME,
                RabbitConst.EMAIL_ROUTE_KEY,
                new MailDTO(sender, receiver, content, LocalDateTime.now()),
                correlationData
        );
    }

    @Override
    public void testSendMsgDelay(String sender, String receiver, String content) {
        // 消息的唯一标识，发布消息时使用，存储在消息的headers中
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        log.info("发送消息到短信队列，消息id为{}", correlationData.getId());
        rabbitTemplate.convertAndSend(
                RabbitConst.MSG_EXCHANGE_NAME,
                RabbitConst.MSG_ROUTE_KEY,
                new MsgDTO(sender, receiver, content, LocalDateTime.now()),
                correlationData
        );
    }

}
