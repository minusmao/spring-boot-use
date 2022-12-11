package com.example.spring.boot.use.valid.common.valid.validator;

import org.springframework.scheduling.support.CronExpression;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义参数校验：cron表达式
 *
 * @author minus
 * @since 2022/12/11 11:20
 */
public class CronValidator implements ConstraintValidator<Cron, String> {

    /**
     * 预处理
     *
     * @param constraintAnnotation 参数校验注解对象
     */
    @Override
    public void initialize(Cron constraintAnnotation) {
    }

    /**
     * 开始校验
     *
     * @param value   待校验的参数值
     * @param context 上下文
     * @return 校验是否通过
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return CronExpression.isValidExpression(value);
    }

}
