package com.example.spring.boot.use.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.boot.use.mybatis.plus.entity.PmsClass;
import com.example.spring.boot.use.mybatis.plus.mapper.PmsClassMapper;
import com.example.spring.boot.use.mybatis.plus.model.ResultVO;
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
    public ResultVO<Object> saveClass(PmsClass pmsClass) {
        return save(pmsClass) ? ResultVO.suc() : ResultVO.fail(500, "新增失败");
    }

    @Override
    public ResultVO<Object> removeClass(String id) {
        return removeById(id) ? ResultVO.suc() : ResultVO.fail(500, "删除失败");
    }

    @Override
    public ResultVO<Object> updateClass(PmsClass pmsClass) {
        return updateById(pmsClass) ? ResultVO.suc() : ResultVO.fail(500, "更新失败");
    }

    @Override
    public ResultVO<PmsClass> getClassById(String id) {
        return ResultVO.suc(getById(id));
    }

    @Override
    public ResultVO<Page<PmsClass>> pageClass(Page<PmsClass> pmsClassPage) {
        return ResultVO.suc(page(pmsClassPage));
    }

}
