package com.example.spring.boot.use.valid.common.valid.validator;

import cn.hutool.core.util.PhoneUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义参数校验：电话号码校验
 *
 * @author minus
 * @since 2022/12/5 21:18
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {

    /**
     * 预处理
     *
     * @param constraintAnnotation 参数校验注解对象
     */
    @Override
    public void initialize(Phone constraintAnnotation) {
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
        return PhoneUtil.isPhone(value);
    }
}
