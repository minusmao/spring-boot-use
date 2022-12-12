package com.example.spring.boot.use.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.boot.use.mybatis.plus.common.exception.OperationFailureException;
import com.example.spring.boot.use.mybatis.plus.entity.PmsClass;
import com.example.spring.boot.use.mybatis.plus.mapper.PmsClassMapper;
import com.example.spring.boot.use.mybatis.plus.service.PmsClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 人员管理-班级 服务实现类
 * </p>
 *
 * @author minus
 * @since 2022-11-21
 */
@Service
public class PmsClassServiceImpl extends ServiceImpl<PmsClassMapper, PmsClass> implements PmsClassService {

    @Override
    public void saveClass(PmsClass pmsClass) {
        if (!save(pmsClass)) {
            throw new OperationFailureException("新增失败");
        }
    }

    @Override
    public void removeClass(String id) {
        if (!removeById(id)) {
            throw new OperationFailureException("删除失败");
        }
    }

    @Override
    public PmsClass updateClass(PmsClass pmsClass) {
        if (!updateById(pmsClass)) {
            throw new OperationFailureException("更新失败");
        }
        return pmsClass;
    }

    @Override
    public PmsClass getClassById(String id) {
        return getById(id);
    }

    @Override
    public Page<PmsClass> pageClass(Page<PmsClass> pmsClassPage) {
        return page(pmsClassPage);
    }

}
