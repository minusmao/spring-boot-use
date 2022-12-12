package com.example.spring.boot.use.mybatis.plus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.boot.use.mybatis.plus.entity.PmsStudent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 人员管理-学生 服务类
 * </p>
 *
 * @author minus
 * @since 2022-11-21
 */
public interface PmsStudentService extends IService<PmsStudent> {

    void saveStudent(PmsStudent pmsStudent);

    void removeStudent(String id);

    PmsStudent updateStudent(PmsStudent pmsStudent);

    PmsStudent getStudentById(String id);

    Page<PmsStudent> pageStudent(Page<PmsStudent> pmsStudentPage);

}
