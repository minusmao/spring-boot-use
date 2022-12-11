package com.example.spring.boot.use.redis.controller;

import com.example.spring.boot.use.redis.common.ApiConst;
import com.example.spring.boot.use.redis.entity.PmsClass;
import com.example.spring.boot.use.redis.model.ResultVO;
import com.example.spring.boot.use.redis.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author minus
 * @since 2022/12/11 15:16
 */
@RestController
@RequestMapping(ApiConst.API_URL + "/test")
@Api(tags = "测试")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/{id}")
    @ApiOperation(value = "API-01-查询数据")
    public ResultVO<PmsClass> getClassById(
            @ApiParam("主键id") @PathVariable String id
    ) {
        return testService.getClassById(id);
    }

    @GetMapping("/local/lock/{id}")
    @ApiOperation(value = "API-01-查询数据（解决缓存击穿-本地锁方式）")
    public ResultVO<PmsClass> getClassByIdLocalLock(
            @ApiParam("主键id") @PathVariable String id
    ) {
        return testService.getClassByIdLocalLock(id);
    }

    @GetMapping("/redis/lock/{id}")
    @ApiOperation(value = "API-01-查询数据（解决缓存击穿-分布式锁方式）")
    public ResultVO<PmsClass> getClassByIdRedisLock(
            @ApiParam("主键id") @PathVariable String id
    ) {
        return testService.getClassByIdRedisLock(id);
    }

    @GetMapping("/redisson/lock/{id}")
    @ApiOperation(value = "API-01-查询数据（解决缓存击穿-分布式锁方式-Redisson框架）")
    public ResultVO<PmsClass> getClassByIdRedissonLock(
            @ApiParam("主键id") @PathVariable String id
    ) {
        return testService.getClassByIdRedissonLock(id);
    }

}
