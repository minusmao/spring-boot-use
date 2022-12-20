package com.example.spring.boot.use.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 整合MongoDB数据库
 * 功能点1：分别使用MongoTemplate、MongoRepository操作数据库
 * 功能点2：实现文件存储，使用GridFS子模块
 * 参考文档：https://blog.csdn.net/qq_46112274/article/details/117425532（SpringBoot集成MongoDB）
 * 参考文档：https://developer.aliyun.com/article/868562（SpringBoot整合MongoDB超详细）
 * 参考文档：https://developer.aliyun.com/article/824643（SpringBoot整合MongoDB实现文件存储）
 *
 * @author minus
 * @since 2022/12/18 13:42
 */
@SpringBootApplication
public class UseMongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(UseMongoApplication.class, args);
    }

}
