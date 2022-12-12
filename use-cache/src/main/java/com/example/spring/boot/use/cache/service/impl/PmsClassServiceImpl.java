package com.example.spring.boot.use.cache.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.spring.boot.use.cache.common.exception.OperationFailureException;
import com.example.spring.boot.use.cache.entity.PmsClass;
import com.example.spring.boot.use.cache.mapper.PmsClassMapper;
import com.example.spring.boot.use.cache.model.ResultVO;
import com.example.spring.boot.use.cache.service.PmsClassService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
@CacheConfig(cacheNames = "pms-class")    // 设置统一的缓存配置
public class PmsClassServiceImpl extends ServiceImpl<PmsClassMapper, PmsClass> implements PmsClassService {

    @Override
    public ResultVO<Object> saveClass(PmsClass pmsClass) {
        if (!save(pmsClass)) {
            throw new OperationFailureException("新增失败");
        }
        return ResultVO.suc();
    }

    @CacheEvict()
    @Override
    public ResultVO<Object> removeClass(String id) {
        if (!removeById(id)) {
            throw new OperationFailureException("删除失败");
        }
        return ResultVO.suc();
    }

    @Override
    public ResultVO<Object> updateClass(PmsClass pmsClass) {
        if (!updateById(pmsClass)) {
            throw new OperationFailureException("更新失败");
        }
        return ResultVO.suc();
    }

    /*
        @Cacheable属性：
            value等同于cacheNames -> 设置缓存名称，与key组合形成真正的的键：cacheNames::key
            key -> 设置键，支持EL表达式
            condition -> 符合条件的才缓存，支持EL表达式
            unless -> 符合条件的不缓存，支持EL表达式
            sync -> 加本地锁，防止缓存击穿
        默认的key生成策略是：类名+方法名+参数
     */
    @Cacheable(sync = true)
    @Override
    public ResultVO<PmsClass> getClassById(String id) {
        return ResultVO.suc(getById(id));
    }

    @Override
    public ResultVO<Page<PmsClass>> pageClass(Page<PmsClass> pmsClassPage) {
        return ResultVO.suc(page(pmsClassPage));
    }

}
