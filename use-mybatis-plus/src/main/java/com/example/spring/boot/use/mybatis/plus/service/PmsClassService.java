package com.example.spring.boot.use.mybatis.plus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.boot.use.mybatis.plus.entity.PmsClass;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 人员管理-班级 服务类
 * </p>
 *
 * @author minus
 * @since 2022-11-21
 */
public interface PmsClassService extends IService<PmsClass> {

    void saveClass(PmsClass pmsClass);

    void removeClass(String id);

    PmsClass updateClass(PmsClass pmsClass);

    PmsClass getClassById(String id);

    Page<PmsClass> pageClass(Page<PmsClass> pmsClassPage);

}
