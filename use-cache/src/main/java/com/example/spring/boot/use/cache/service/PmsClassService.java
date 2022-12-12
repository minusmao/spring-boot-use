package com.example.spring.boot.use.cache.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.spring.boot.use.cache.entity.PmsClass;
import com.example.spring.boot.use.cache.model.ResultVO;

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
