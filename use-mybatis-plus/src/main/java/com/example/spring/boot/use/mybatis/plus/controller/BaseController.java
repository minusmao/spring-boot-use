package com.example.spring.boot.use.mybatis.plus.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * controller层的公共类
 *
 * @author minus
 * @since 2022/11/21 1:32
 */
public class BaseController {

    @Autowired
    HttpServletRequest request;

    /**
     * 统一处理用户请求的分页参数
     *
     * @param <T> 数据类型
     * @return 分页对象
     */
    public <T> Page<T> getPage() {
        // 得到请求的参数值
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);    // 页数，默认为1
        int size = ServletRequestUtils.getIntParameter(request, "size", 10);         // 个数，默认为10

        return new Page<>(current, size);
    }

}
