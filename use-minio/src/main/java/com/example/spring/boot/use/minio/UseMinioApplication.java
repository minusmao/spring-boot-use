package com.example.spring.boot.use.minio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * 整合 MinIO <br>
 * 参考文档：<a href="https://blog.csdn.net/yueyue763184/article/details/131147025">SpringBoot整合minio服务（超详细）</a>
 *
 * @author minus
 * @since 2024/5/4 11:07
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class UseMinioApplication {
    public static void main(String[] args) {
        SpringApplication.run(UseMinioApplication.class, args);
    }
}
