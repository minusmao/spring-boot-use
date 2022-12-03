package com.example.spring.boot.use.async.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 注解@Async相关配置
 * 描述：@Async默认使用spring-boot创建的线程池，也可以自行配置
 * 参考文档1：https://zhuanlan.zhihu.com/p/85855282（spring-boot默认线程池）
 * 参考文档2：https://blog.csdn.net/qq_30281443/article/details/83340909（自定义@Async的线程池）
 *
 * @author minus
 * @since 2022/11/30 23:25
 */
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfig implements AsyncConfigurer {

    private final Logger log = LoggerFactory.getLogger(AsyncConfig.class);

    @Override
    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        log.debug("Creating Async Task Executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);         //核心线程数
        executor.setMaxPoolSize(20);          //最大线程数
        executor.setQueueCapacity(1000);      //队列大小
        executor.setKeepAliveSeconds(300);    //线程最大空闲时间
        executor.setThreadNamePrefix("ics-Executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 拒绝策略

        /*
            拒绝策略常用有4种：
            ThreadPoolExecutor.AbortPolicy 丢弃任务并抛出RejectedExecutionException异常(默认)。
            ThreadPoolExecutor.DiscardPolicy 丢弃任务，但是不抛出异常。
            ThreadPoolExecutor.DiscardOldestPolicy 丢弃队列最前面的任务，然后重新尝试执行任务
            ThreadPoolExecutor.CallerRunsPolicy 由调用线程处理该任务
        */

        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
