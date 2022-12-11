package com.example.spring.boot.use.redis.config;

import com.example.spring.boot.use.redis.util.JacksonUtil;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 创建 RedisTemplate 对象
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);           // 设置连接工厂
        // 创建序列化规则对象
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        final ObjectMapper objectMapper = JacksonUtil.generateObjectMapper();
        /*
            默认RedisTemple反序列化后的到的结果是LinkedHashMap类型，如果要得到需要的类型需要两种办法：
              1、拿到结果后利用ObjectMapper对象的convertValue()方法自行类型转换。参考：https://blog.csdn.net/weixin_43888891/article/details/115635641
              2、调用activateDefaultTyping()方法，设置序列化后的JSON包括对象信息，反序列化就会自动转换为该对象。参考：https://www.cnblogs.com/exmyth/p/13794524.html
        */
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        // 设置序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());          // key
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);        // value
        // 设置hash序列化规则
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());      // hash key
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);    // hash value

        return redisTemplate;
    }

}
