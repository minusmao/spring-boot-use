package com.example.spring.boot.use.rabbitmq.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置类
 *
 * @author minus
 * @since 2022/12/17 20:28
 */
@EnableRabbit
@Configuration
public class RabbitConfig {

    /**
     * 设置消息处理为JSON格式
     */
    @Bean
    public MessageConverter messageConverter() {
        // 设置支持LocalDateTime的ObjectMapper，参考文档：https://blog.csdn.net/sswltt/article/details/108868480
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        return new Jackson2JsonMessageConverter(objectMapper);
    }

}
