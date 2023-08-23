package com.example.spring.boot.use.rabbitmq.service;

/**
 * 测试编程不良人 rabbitmq 教程
 * <a href="https://www.bilibili.com/video/BV1dE411K7MG/">教程地址</a>
 *
 * @author minus
 * @since 2020/12/6 20:51
 */
public interface BlrTestService {

    /**
     * 模型一（Hello world，直连）：channel.queueDeclare()，一个生产者，一个消费者
     *
     * @param msg 消息
     */
    void testModel1(String msg);

    /**
     * 模型二（work queues）：channel.queueDeclare()，一个生产者，多个消费者
     *
     * @param msg 消息
     */
    void testModel2(String msg);

    /**
     * 模型三（fanout，即广播）：channel.exchangeDeclare("交换机名称","fanout")
     *
     * @param msg 消息
     */
    void testModel3(String msg);

    /**
     * 模型四（Routing-Direct，直连）：channel.exchangeDeclare("交换机名称","direct")
     *
     * @param msg        消息
     * @param routingKey 路由键
     */
    void testModel4(String msg, String routingKey);

    /**
     * 模型五（Routing-Topics，订阅）：channel.exchangeDeclare("交换机名称","topic")
     *
     * @param msg        消息
     * @param routingKey 路由键
     */
    void testModel5(String msg, String routingKey);

}
