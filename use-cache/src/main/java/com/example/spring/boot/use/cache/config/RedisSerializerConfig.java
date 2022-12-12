package com.example.spring.boot.use.cache.config;

import com.example.spring.boot.use.cache.util.JacksonUtil;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * Redis序列化器配置类
 *
 * @author minus
 * @since 2022/12/13 1:07
 */
@Configuration
public class RedisSerializerConfig {

    /**
     * 基于Jackson框架的JSON序列化器
     *
     * @return JSON序列化器
     */
    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
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

        return jackson2JsonRedisSerializer;
    }

}
