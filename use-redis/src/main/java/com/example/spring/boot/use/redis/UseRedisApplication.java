package com.example.spring.boot.use.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 整合redis（本模块参考了谷粒商城教程：https://www.bilibili.com/video/BV1np4y1C7Yf?p=151）
 * 常用框架：Jedis、Lettuce（spring-boot默认）、Redisson
 * 参考文档：https://zhuanlan.zhihu.com/p/383876242（RedisTemplate常用方法总结）
 * 参考文档：https://github.com/redisson/redisson/wiki/Table-of-Content（Redisson的使用）
 * 可视化界面客户端：https://github.com/qishibo/AnotherRedisDesktopManager
 * 三大Redis经典问题-缓存穿透：使用不存在数据的key进行大量的高并发查询，这导致缓存无法命中，每次请求都要到后端数据库系统进行查询；
 *                解决方法：将不存在数据的key也进行缓存，缓存null值，其次做好安全验证防止恶意攻击
 * 三大Redis经典问题-缓存雪崩：大量缓存集中在某一个时间段内失效，给后端数据库造成瞬时的负载升高的压力；
 *                解决方法：让缓存的过期时间分散分布
 * 三大Redis经典问题-缓存击穿：大量请求同一个key且该key刚好过期，会导致这些请求都会走数据库；
 *                解决方法：加锁，获得锁的请求走数据库并放入缓存，其余请求走缓存
 *
 * @author minus
 * @since 2022/12/11 11:14
 */
@SpringBootApplication
public class UseRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(UseRedisApplication.class, args);
    }

}
