package com.example.spring.boot.use.redis.service;

import com.example.spring.boot.use.redis.model.ResultVO;

/**
 * Redisson框架使用
 *
 * @author minus
 * @since 2022/12/12 1:28
 */
public interface RedissonService {

    ResultVO<Object> doWrite(String newValue);

    ResultVO<String> doRead();

    ResultVO<Integer> doAcquire();

    ResultVO<Integer> doRelease();

    ResultVO<Integer> doAwait();

    ResultVO<Integer> doCountDown();

}
