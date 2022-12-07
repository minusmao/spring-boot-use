package com.example.spring.boot.use.async.controller;

import com.example.spring.boot.use.async.common.ApiConst;
import com.example.spring.boot.use.async.model.ResultVO;
import com.example.spring.boot.use.async.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author minus
 * @since 2022/12/1 0:33
 */
@RestController
@RequestMapping(ApiConst.API_URL + "/test")
@Api(tags = "测试")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/async")
    @ApiOperation(value = "API-01-测试@Async注解")
    public ResultVO<Object> testAsync() {
        testService.testAsync();
        return ResultVO.suc();
    }

    @GetMapping("/completableFuture")
    @ApiOperation(value = "API-02-测试异步编排CompletableFuture")
    public ResultVO<Object> testCompletableFuture() {
        testService.testCompletableFuture();
        return ResultVO.suc();
    }

    @GetMapping("/asyncManager")
    @ApiOperation(value = "API-03-测试异步任务管理")
    public ResultVO<Object> testAsyncTaskManager() {
        testService.testAsyncTaskManager();
        return ResultVO.suc();
    }

}
