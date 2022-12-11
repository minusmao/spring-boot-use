package com.example.spring.boot.use.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson配置类
 * 参考文档：https://github.com/redisson/redisson/wiki/Table-of-Content（Redisson的使用）
 *
 * @author minus
 * @since 2022/12/12 0:42
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redisson(RedisProperties redisProperties) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
                .setPassword(redisProperties.getPassword());
        // 设置锁的过期时间
        config.setLockWatchdogTimeout(20000L);
        return Redisson.create(config);
    }

}
