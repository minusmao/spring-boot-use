package com.example.spring.boot.use.valid.common.valid.validator;

import cn.hutool.core.util.IdcardUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义参数校验：身份证号校验
 *
 * @author minus
 * @since 2022/12/5 18:08
 */
public class IdCardNumberValidator implements ConstraintValidator<IdCardNumber, String> {

    /**
     * 预处理
     *
     * @param constraintAnnotation 参数校验注解对象
     */
    @Override
    public void initialize(IdCardNumber constraintAnnotation) {
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
        return IdcardUtil.isValidCard(value);
    }
}
