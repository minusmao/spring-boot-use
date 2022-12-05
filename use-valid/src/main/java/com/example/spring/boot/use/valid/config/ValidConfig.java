package com.example.spring.boot.use.valid.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 参数校验配置类
 *
 * @author minus
 * @since 2022/12/5 23:05
 */
@Configuration
public class ValidConfig {

    /**
     * 自定义Validator
     * 默认的Validator会校验所有属性，将所有校验结果一起返回，可设置为快速失败（即一个属性校验失败了，其它就不必校验了）
     *
     * @return 自定义Validator对象
     */
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)    // 快速失败
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

}
