package com.example.spring.boot.use.valid.common.valid.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义参数校验注解：时间区间校验（即开始时间小于等于结束时间）
 * 支持的时间类型：LocalDate、LocalDateTime、Date
 *
 * @author minus
 * @since 2022/12/5 21:32
 */
@Target({TYPE, METHOD})
@Retention(RUNTIME)
@Repeatable(TimeInterval.List.class)    // 注解在同一个地方出现多次，可以用于根据不同group指定不同的属性值
@Documented
@Constraint(validatedBy = TimeIntervalValidator.class)
public @interface TimeInterval {

    String startFiled() default "from";

    String endFiled() default "to";

    String message() default "开始时间需要小于结束时间";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({TYPE, METHOD})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        TimeInterval[] value();
    }

}
