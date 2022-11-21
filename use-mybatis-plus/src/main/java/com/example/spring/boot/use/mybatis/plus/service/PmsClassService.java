package com.example.spring.boot.use.mybatis.plus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.boot.use.mybatis.plus.entity.PmsClass;
import com.example.spring.boot.use.mybatis.plus.model.ResultVO;
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

    ResultVO<Object> saveClass(PmsClass pmsClass);

    ResultVO<Object> removeClass(String id);

    ResultVO<Object> updateClass(PmsClass pmsClass);

    ResultVO<PmsClass> getClassById(String id);

    ResultVO<Page<PmsClass>> pageClass(Page<PmsClass> pmsClassPage);

}
