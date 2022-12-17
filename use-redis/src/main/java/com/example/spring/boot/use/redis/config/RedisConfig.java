package com.example.spring.boot.use.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置类
 *
 * @author minus
 * @since 2022/12/11 14:45
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory,
            Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer
    ) {
        // 创建 RedisTemplate 对象
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);           // 设置连接工厂
        // 设置序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());          // key
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);        // value
        // 设置hash序列化规则
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());      // hash key
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);    // hash value

        return redisTemplate;
    }

}
