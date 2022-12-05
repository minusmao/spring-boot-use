package com.example.spring.boot.use.valid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 整合参数校验（JSR标准的两套实现）
 * 实现1：Spring Validator -> @Validated；可分组；无法嵌套验证，即不可放在成员属性上
 * 实现2：Hibernate Validator -> @Valid; 不可分组；支持嵌套验证，即可放在成员属性上
 * 参考文档1：<a href="https://www.cnblogs.com/cjsblog/p/8946768.html">参数校验的使用和自定义参数校验</a>
 * 参考文档2：<a href="https://blog.csdn.net/sinat_36645384/article/details/123672082">参数校验注解功能介绍</a>
 *
 * @author minus
 * @since 2022/12/5 12:27
 */
@SpringBootApplication
public class UseValidApplication {

    public static void main(String[] args) {
        SpringApplication.run(UseValidApplication.class);
    }

}
