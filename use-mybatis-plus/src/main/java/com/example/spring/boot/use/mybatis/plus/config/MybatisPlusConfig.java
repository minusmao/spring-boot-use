package com.example.spring.boot.use.mybatis.plus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * mybatis-plus配置类
 *
 * @author minus
 * @since 2022/11/21 1:21
 */
@Configuration
@MapperScan("com.example.spring.boot.use.mybatis.plus.mapper") // 自动生成的代码中Mapper层没加注解，只需在这里添加一个扫描Mapper层的注解即可
public class MybatisPlusConfig {

    /**
     * 配置mybatis-plus拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // 创建拦截器
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加：分页功能
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 添加：防止全表更新和删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        return interceptor;
    }

    /**
     * 配置mybatis-plus乐观锁拦截器
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 配置mybatis-plus的handler处理类
     * 方法insetFill()：新增时，填充 @TableField(fill = FieldFill.INSERT)修饰的字段
     * 方法updateFill()：更新时，填充 @TableField(fill = FieldFill.UPDATE)修饰的字段
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                final LocalDateTime now = LocalDateTime.now();
                setFieldValByName("createTime", now, metaObject);
                setFieldValByName("updateTime", now, metaObject);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                final LocalDateTime now = LocalDateTime.now();
                setFieldValByName("updateTime", now, metaObject);
            }
        };
    }

}
