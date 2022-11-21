package com.example.spring.boot.use.mybatis.plus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.boot.use.mybatis.plus.entity.PmsStudent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.spring.boot.use.mybatis.plus.model.ResultVO;

/**
 * <p>
 * 人员管理-学生 服务类
 * </p>
 *
 * @author minus
 * @since 2022-11-21
 */
public interface PmsStudentService extends IService<PmsStudent> {

    ResultVO<Object> saveStudent(PmsStudent pmsStudent);

    ResultVO<Object> removeStudent(String id);

    ResultVO<Object> updateStudent(PmsStudent pmsStudent);

    ResultVO<PmsStudent> getStudentById(String id);

    ResultVO<Page<PmsStudent>> pageStudent(Page<PmsStudent> pmsStudentPage);

}
