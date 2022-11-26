package com.example.spring.boot.use.ftp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * 整合ftp
 * 参考文档1：https://blog.csdn.net/m0_58684193/article/details/124817438（搭建ftp服务）
 * 参考文档2：https://cloud.tencent.com/developer/article/1432737（自定义ftp连接池）
 * 参考文档3：https://blog.csdn.net/gggg989898/article/details/107819742（操作ftp）
 *
 * @author minus
 * @since 2022/11/25 23:17
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class UseFtpApplication {

    public static void main(String[] args) {
        SpringApplication.run(UseFtpApplication.class, args);
    }

}
