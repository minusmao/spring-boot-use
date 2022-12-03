package com.example.spring.boot.use.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 整合异步功能
 * 功能点1：线程池和@Async注解的使用
 * 功能点2：使用CompletableFuture异步编排
 *
 * @author minus
 * @since 2022/11/30 22:52
 */
@SpringBootApplication
public class UseAsyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(UseAsyncApplication.class, args);
    }

}
