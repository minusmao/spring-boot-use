package com.example.spring.boot.use.rabbitmq.service;

/**
 * 测试
 *
 * @author minus
 * @since 2022/12/17 23:11
 */
public interface TestService {

    void testSendMail(String sender, String receiver, String content);

}
