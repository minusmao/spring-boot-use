package com.example.spring.boot.use.rabbitmq.service.impl;

import com.example.spring.boot.use.rabbitmq.service.BlrTestService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.stereotype.Service;

/**
 * 测试编程不良人 rabbitmq 教程（spring-boot 集成）
 * <a href="https://www.bilibili.com/video/BV1dE411K7MG/">教程地址</a>
 *
 * @author minus
 * @since 2020/12/6 20:51
 */
@Service
public class BootBlrTestServiceImpl implements BlrTestService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public BootBlrTestServiceImpl(RabbitTemplateConfigurer configurer, ConnectionFactory connectionFactory) {
        // 为了避免与之前练习创建的 rabbitTemplate 冲突，这里重新创建一个
        RabbitTemplate template = new RabbitTemplate();
        configurer.configure(template, connectionFactory);
        this.rabbitTemplate = template;
    }

    @Override
    public void testModel1(String msg) {
        rabbitTemplate.convertAndSend("boot.model1", "boot.model1 -> " + msg);
    }

    /**
     * 注册模式一消费者
     * 模型一（Hello world，直连）：channel.queueDeclare()，一个生产者，一个消费者
     */
    @RabbitListener(queuesToDeclare = @Queue("boot.model1"), ackMode = "AUTO")
    public void model1Consumer(String msg) {
        System.out.println("boot.model1 消费者：" + msg);
    }

    @Override
    public void testModel2(String msg) {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("boot.model2", "boot.model2 -> " + msg);
        }
    }

    /**
     * 注册模式二消费者 1
     * 模型二（work queues）：channel.queueDeclare()，一个生产者，多个消费者
     */
    @RabbitListener(queuesToDeclare = @Queue("boot.model2"), ackMode = "AUTO")
    public void model2Consumer1(String msg) {
        System.out.println("boot.model2 消费者1：" + msg);
    }

    /**
     * 注册模式二消费者 2
     * 模型二（work queues）：channel.queueDeclare()，一个生产者，多个消费者
     * 默认在 Spring AMQP 实现中 Work 这种方式就是公平调度，如果需要实现能者多劳需要额外配置
     */
    @RabbitListener(queuesToDeclare = @Queue("boot.model2"))
    public void model2Consumer2(String msg) {
        System.out.println("boot.model2 消费者2：" + msg);
    }

    @Override
    public void testModel3(String msg) {
        rabbitTemplate.convertAndSend("boot.model3", "", "boot.model3 -> " + msg);
    }

    /**
     * 注册模式三消费者 1
     * 模型三（fanout，即广播）：channel.exchangeDeclare("交换机名称","fanout")
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name = "boot.model3", type = "fanout")
    ))
    public void model3Consumer1(String msg) {
        System.out.println("boot.model3 消费者1：" + msg);
    }

    /**
     * 注册模式三消费者 2
     * 模型三（fanout，即广播）：channel.exchangeDeclare("交换机名称","fanout")
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name = "boot.model3", type = "fanout")
    ))
    public void model3Consumer2(String msg) {
        System.out.println("boot.model3 消费者2：" + msg);
    }

    @Override
    public void testModel4(String msg, String routingKey) {
        rabbitTemplate.convertAndSend("boot.model4", routingKey, "boot.model4 -> " + msg);
    }

    /**
     * 注册模式四消费者 1
     * 模型四（Routing-Direct，直连）：channel.exchangeDeclare("交换机名称","direct")
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name = "boot.model4", type = "direct"),
            key = {"info", "warn", "error"}
    ))
    public void model4Consumer1(String msg) {
        System.out.println("boot.model4 消费者1：" + msg);
    }

    /**
     * 注册模式四消费者 2
     * 模型四（Routing-Direct，直连）：channel.exchangeDeclare("交换机名称","direct")
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name = "boot.model4", type = "direct"),
            key = {"info", "warn"}
    ))
    public void model4Consumer2(String msg) {
        System.out.println("boot.model4 消费者2：" + msg);
    }

    @Override
    public void testModel5(String msg, String routingKey) {
        rabbitTemplate.convertAndSend("boot.model5", routingKey, "boot.model5 -> " + msg);
    }

    /**
     * 注册模式五消费者 1
     * 模型五（Routing-Topics，订阅）：channel.exchangeDeclare("交换机名称","topic")
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name = "boot.model5", type = "topic"),
            key = {"info.*", "*.error"}
    ))
    public void model5Consumer1(String msg) {
        System.out.println("boot.model5 消费者1：" + msg);
    }

    /**
     * 注册模式五消费者 2
     * 模型五（Routing-Topics，订阅）：channel.exchangeDeclare("交换机名称","topic")
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name = "boot.model5", type = "topic"),
            key = {"info.#", "#.error"}
    ))
    public void model5Consumer2(String msg) {
        System.out.println("boot.model5 消费者2：" + msg);
    }

}
