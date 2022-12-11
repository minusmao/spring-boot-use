package com.example.spring.boot.use.redis.controller;

import com.example.spring.boot.use.redis.common.ApiConst;
import com.example.spring.boot.use.redis.model.ResultVO;
import com.example.spring.boot.use.redis.service.RedissonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Redisson框架使用
 * 注：Redisson封装了一套与juc类似的锁机制，且基于Redis服务，用于分布式
 *
 * @author minus
 * @since 2022/12/12 1:27
 */
@RestController
@RequestMapping(ApiConst.API_URL + "/redisson")
@Api(tags = "Redisson框架使用")
public class RedissonController {

    @Autowired
    private RedissonService redissonService;

    @PutMapping("/readWrite")
    @ApiOperation(value = "API-01-读写锁测试-写")
    public ResultVO<Object> doWrite(@ApiParam("新值") @RequestParam String newValue) {
        return redissonService.doWrite(newValue);
    }

    @GetMapping("/readWrite")
    @ApiOperation(value = "API-02-读写锁测试-读")
    public ResultVO<String> doRead() {
        return redissonService.doRead();
    }

    @PutMapping("/acquire")
    @ApiOperation(value = "API-03-信号量测试-获取一个信号量")
    public ResultVO<Integer> doAcquire() {
        // 获取一个信号量，则Redis中的信号量数据减1
        return redissonService.doAcquire();
    }

    @PutMapping("/release")
    @ApiOperation(value = "API-04-信号量测试-释放一个信号量")
    public ResultVO<Integer> doRelease() {
        // 释放一个信号量，则Redis中的信号量数据加1
        return redissonService.doRelease();
    }

    @PutMapping("/waite")
    @ApiOperation(value = "API-05-闭锁测试-等待")
    public ResultVO<Integer> doAwait() {
        // 设置计数总数，并等待指定计数完成
        return redissonService.doAwait();
    }

    @PutMapping("/countDown")
    @ApiOperation(value = "API-06-闭锁测试-计数减1")
    public ResultVO<Integer> doCountDown() {
        // 执行一次计数，计数减1
        return redissonService.doCountDown();
    }

}
