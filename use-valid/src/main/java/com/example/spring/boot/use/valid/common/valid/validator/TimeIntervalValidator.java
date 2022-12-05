package com.example.spring.boot.use.valid.common.valid.validator;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 自定义参数校验：时间区间校验（即开始时间小于等于结束时间）
 * 支持的时间类型：LocalDate、LocalDateTime、Date
 *
 * @author minus
 * @since 2022/12/5 21:32
 */
public class TimeIntervalValidator implements ConstraintValidator<TimeInterval, Object> {

    private String startFiled;

    private String endFiled;

    /**
     * 预处理：时间区间参数名
     *
     * @param constraintAnnotation 参数校验注解对象
     */
    @Override
    public void initialize(TimeInterval constraintAnnotation) {
        this.startFiled = constraintAnnotation.startFiled();
        this.endFiled = constraintAnnotation.endFiled();
    }

    /**
     * 开始校验
     *
     * @param value   待校验的参数值
     * @param context 上下文
     * @return 校验是否通过
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        // 拿到开始时间何结束时间
        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        Object start = beanWrapper.getPropertyValue(startFiled);
        Object end = beanWrapper.getPropertyValue(endFiled);
        // 为空跳过
        if (start == null || end == null) {
            return true;
        }
        // 判断时间大小
        if (start instanceof LocalDate && end instanceof LocalDate) {
            return ((LocalDate) start).compareTo((LocalDate) end) <= 0;
        }
        if (start instanceof LocalDateTime && end instanceof LocalDateTime) {
            return ((LocalDateTime) start).compareTo((LocalDateTime) end) <= 0;
        }
        if (start instanceof Date && end instanceof Date) {
            return ((Date) start).compareTo((Date) end) <= 0;
        }

        return true;
    }

}
