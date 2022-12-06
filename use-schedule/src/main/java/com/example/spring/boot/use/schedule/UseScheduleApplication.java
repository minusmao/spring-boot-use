package com.example.spring.boot.use.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 整合定时器
 * 功能点1：基于注解，使用@Scheduled定义定时任务
 * 功能点2：基于接口，定义接口+数据库的定时任务管理
 * 参考文档：https://blog.csdn.net/lcczpp/article/details/125006079
 *
 * @author minus
 * @since 2022/12/4 20:02
 */
@SpringBootApplication
public class UseScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(UseScheduleApplication.class);
    }

}
