package com.example.spring.boot.use.redis.service;

import com.example.spring.boot.use.redis.entity.PmsClass;
import com.example.spring.boot.use.redis.model.ResultVO;

/**
 * 测试
 *
 * @author minus
 * @since 2022/12/11 15:17
 */
public interface TestService {

    ResultVO<PmsClass> getClassById(String id);

    ResultVO<PmsClass> getClassByIdLocalLock(String id);

    ResultVO<PmsClass> getClassByIdRedisLock(String id);

    ResultVO<PmsClass> getClassByIdRedissonLock(String id);

}
