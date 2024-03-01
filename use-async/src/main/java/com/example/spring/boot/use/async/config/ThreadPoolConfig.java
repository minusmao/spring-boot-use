package com.example.spring.boot.use.async.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池
 * 参考文档：https://www.jianshu.com/p/c82cacd6e356
 * 线程池合理配置：
 *     CPU密集型 -> 核心线程数 = CPU核数 + 1
 *     IO密集型 -> 核心线程数 = CPU核数 / (1 - 阻塞系数)
 *                注：阻塞系数 = 阻塞时间 / 总时间 = IO 耗时 / (IO 耗时 + CPU 耗时)，一般认为在0.8~0.9之间
 *     参考文档1：https://zhuanlan.zhihu.com/p/407807591
 *     参考文档2：https://blog.csdn.net/weixin_44777693/article/details/95246059
 *
 * @author minus
 * @since 2022/12/1 0:08
 */
@Configuration
public class ThreadPoolConfig {

    @Value("${thread-pool.coreSize}")
    private int coreSize;

    @Value("${thread-pool.maxSize}")
    private int maxSize;

    @Value("${thread-pool.keepAliveTime}")
    private int keepAliveTime;

    @Value("${thread-pool.dequeSize}")
    private int dequeSize;

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(
                coreSize,                                // 核心线程池大小
                maxSize,                                 // 最大线程数
                keepAliveTime,                           // 活跃时间
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(dequeSize),    // 任务队列
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()     // 拒绝策略
        );
    }

    /*
        可以使用Executors创建线程池（阿里手册不推荐），方法如下：
            Executors.newFixedThreadPool(4);
            Executors.newSingleThreadExecutor();
            Executors.newCachedThreadPool();
            Executors.newScheduledThreadPool(4);
     */

}
