package com.example.spring.boot.use.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 整合spring-cache框架
 * 参考文档1：<a href="https://blog.csdn.net/zhang19903848257/article/details/115144049">SpringCache基本使用及整合Redis</a>
 * 参考文档2：<a href="https://blog.csdn.net/baweiyang/article/details/102953735">SpringCache结合Redis</a>
 *
 * @author minus
 * @since 2022/12/12 15:14
 */
@SpringBootApplication
public class UseCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(UseCacheApplication.class, args);
    }

}
