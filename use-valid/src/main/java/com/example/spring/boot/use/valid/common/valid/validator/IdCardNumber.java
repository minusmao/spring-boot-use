package com.example.spring.boot.use.valid.common.valid.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义参数校验注解：身份证号校验
 *
 * @author minus
 * @since 2022/12/5 15:37
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(IdCardNumber.List.class)    // 注解在同一个地方出现多次，可以用于根据不同group指定不同的属性值
@Documented
@Constraint(validatedBy = IdCardNumberValidator.class)
public @interface IdCardNumber {

    String message() default "身份证号码格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        IdCardNumber[] value();
    }

}
