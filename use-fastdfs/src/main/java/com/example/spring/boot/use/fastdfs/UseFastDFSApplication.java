package com.example.spring.boot.use.fastdfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 整合FastDFS
 * 参考文档1：https://www.cnblogs.com/gitBook/p/16864787.html（搭建FastDFS服务）
 * 参考文档2：https://www.cnblogs.com/niceyoo/p/13512089.html（springboot整合FastDFS）
 * 参考文档3：https://blog.csdn.net/jun8148/article/details/102840412（springboot整合FastDFS）
 *
 * @author minus
 * @since 2022/11/27 21:42
 */
@SpringBootApplication
public class UseFastDFSApplication {

    public static void main(String[] args) {
        SpringApplication.run(UseFastDFSApplication.class, args);
    }

}
