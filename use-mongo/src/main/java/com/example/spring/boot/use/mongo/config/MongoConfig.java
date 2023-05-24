package com.example.spring.boot.use.mongo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * Mongo配置类
 *
 * @author minus
 * @since 2023/5/24 21:58
 */
@Configuration
@EnableMongoAuditing    // 开启自定填充
public class MongoConfig implements AuditorAware<String> {

    /**
     * 填充创建人、更新人<br>
     * <a href="https://blog.csdn.net/u010234516/article/details/121830953">解决 Not annotated method overrides method annotated with @NonNullApi 问题</a>
     *
     * @return 人员账号
     */
    @NonNull
    @Override
    public Optional<String> getCurrentAuditor() {
        // TODO 获取当前用户id
        String userId = "test";
        return Optional.of(userId);
    }

}
