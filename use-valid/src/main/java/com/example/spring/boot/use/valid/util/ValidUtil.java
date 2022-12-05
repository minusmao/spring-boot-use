package com.example.spring.boot.use.valid.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

/**
 * 参数校验工具类（手动调用参数校验）
 * 参考文档：<a href="https://www.cnblogs.com/kuangke/p/16880768.html">手动校验</a>
 */
@Component
public class ValidUtil {

    @Autowired
    Validator validator;

    /**
     * 手动执行参数校验
     *
     * @param object 带校验对象
     * @param groups 分组
     * @return 校验结果
     */
    public <T> Set<ConstraintViolation<T>> doValidate(T object, Class<?>... groups) {
        return validator.validate(object, groups);
    }

    /**
     * 手动执行参数校验（获得校验message列表）
     *
     * @param object 带校验对象
     * @param groups 分组
     * @return 校验message列表
     */
    public <T> List<String> doValidateForMessages(T object, Class<?>... groups) {
        return doValidate(object, groups).stream().map(ConstraintViolation::getMessage).toList();
    }

    /**
     * 手动执行参数校验（查看是否校验通过）
     *
     * @param object 带校验对象
     * @param groups 分组
     * @return 是否校验通过
     */
    public <T> boolean isValidate(T object, Class<?>... groups) {
        return doValidate(object, groups).isEmpty();
    }

}
